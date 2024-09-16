package com.example.ecommerce.service;

import java.math.BigDecimal;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.ecommerce.dto.request.CartAdditonDeletionRequestDto;
import com.example.ecommerce.model.UserCartDetails;
import com.example.ecommerce.model.UserDetails;
import com.example.ecommerce.model.enums.ResponseMessages;
import com.example.ecommerce.repository.ProductRepository;
import com.example.ecommerce.repository.UserDetailsRepository;


@Service
public class CartAdditonDeletionService {

	@Autowired
	private UserDetailsRepository userDetailsRepository;

	@Autowired
	private ProductRepository productRepository;

	public String addToCartService(CartAdditonDeletionRequestDto addToCartObject, String currentLoginUserID) {
		UserDetails currentUserDetails = userDetailsRepository.findByUserID(currentLoginUserID);
		if (currentUserDetails != null) {
			BigDecimal currentCreditAmount = currentUserDetails.getCredit();
			BigDecimal totalAmountInCart = addToCartObject.getPrice()
					.multiply(BigDecimal.valueOf(addToCartObject.getQuantity()));
			Boolean isCreditEnough = currentCreditAmount.compareTo(totalAmountInCart) > 0
					|| currentCreditAmount.equals(totalAmountInCart);
			if (isCreditEnough) {
				List<UserCartDetails> alreadyPresentCartDetails = currentUserDetails.getCartDetails();
				Boolean isProductAlreadyPresentInCart = alreadyPresentCartDetails.parallelStream()
						.anyMatch(product -> product.getProductId().equals(addToCartObject.getProductId()));
				if (!isProductAlreadyPresentInCart) {
					String productNameFromDB = productRepository.findByProductId(addToCartObject.getProductId())
							.getProductName();
					UserCartDetails cartDetailsToPush = new UserCartDetails();
					cartDetailsToPush.setProductId(addToCartObject.getProductId());
					cartDetailsToPush.setColor(addToCartObject.getColor());
					cartDetailsToPush.setPrice(addToCartObject.getPrice());
					cartDetailsToPush.setProductName(productNameFromDB);
					cartDetailsToPush.setQuantity(addToCartObject.getQuantity());
					alreadyPresentCartDetails.add(cartDetailsToPush);
					currentUserDetails.setCartDetails(alreadyPresentCartDetails);
					userDetailsRepository.save(currentUserDetails);
					return ResponseMessages.ADDTOCARTSUCCESS.getResponsesMessage();
				} else {
					return ResponseMessages.ADDCARTPRESENTALREADY.getResponsesMessage();
				}

			} else {
				return ResponseMessages.ADDCARTNOCREDIT.getResponsesMessage();
			}

		} else {
			return ResponseMessages.ADDTOCARTFAILED.getResponsesMessage();
		}

	};

	public String deleteFromCart(CartAdditonDeletionRequestDto deleteFromCartObject, String currentLoginUserID) {
		UserDetails currentUserDetails = userDetailsRepository.findByUserID(currentLoginUserID);
		if (currentUserDetails != null) {
			currentUserDetails.getCartDetails()
					.removeIf(product -> product.getProductId().equals(deleteFromCartObject.getProductId()));
			userDetailsRepository.save(currentUserDetails);
			return ResponseMessages.DELETECARTSUCCESS.getResponsesMessage();
		} else {
			return ResponseMessages.DELETECARTFAILED.getResponsesMessage();
		}
	}

}
