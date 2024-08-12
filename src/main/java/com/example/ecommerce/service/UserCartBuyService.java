package com.example.ecommerce.service;

import java.math.BigDecimal;
import java.util.Collections;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.ecommerce.model.UserCartDetails;
import com.example.ecommerce.model.UserDetails;
import com.example.ecommerce.repository.UserDetailsRepository;

import jakarta.servlet.http.HttpSession;

@Service
public class UserCartBuyService {

	@Autowired
	private UserDetailsRepository userDetailsRepository;

	public boolean doBuyCartProducts(String currentLoginUserID) {
		Boolean isPurchaseCompleted = false;
		UserDetails currentUserDetails = userDetailsRepository.findByUserID(currentLoginUserID);
		if (currentUserDetails != null) {
			List<UserCartDetails> userCartList = currentUserDetails.getCartDetails();
			BigDecimal balanceAmount = currentUserDetails.getCredit();
			for (UserCartDetails eachProduct : userCartList) {
				BigDecimal amountToAdd = eachProduct.getPrice().multiply(BigDecimal.valueOf(eachProduct.getQuantity()));
				balanceAmount = balanceAmount.subtract(amountToAdd);
			}
			currentUserDetails.setCredit(balanceAmount);
			currentUserDetails.setCartDetails(Collections.emptyList());
			userDetailsRepository.save(currentUserDetails);

			isPurchaseCompleted = true;
		}
		return isPurchaseCompleted;
	}

}
