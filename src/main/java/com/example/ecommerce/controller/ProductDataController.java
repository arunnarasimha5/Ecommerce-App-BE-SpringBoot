package com.example.ecommerce.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.example.ecommerce.dto.request.CartAdditonDeletionRequestDto;
import com.example.ecommerce.dto.response.CommonResponse;
import com.example.ecommerce.model.ProductInventoryDetails;
import com.example.ecommerce.model.UserDetails;
import com.example.ecommerce.model.enums.ProductTypes;
import com.example.ecommerce.model.enums.ResponseMessages;
import com.example.ecommerce.service.CartAdditonDeletionService;
import com.example.ecommerce.service.ProductInventoryService;
import com.example.ecommerce.service.UserCartBuyService;
import com.example.ecommerce.service.UserDetailsFetchService;

import jakarta.servlet.http.HttpSession;

@RestController
@CrossOrigin(origins = "http://localhost:3000")
public class ProductDataController {

	@Autowired
	ProductInventoryService productInventoryService;

	@Autowired
	CartAdditonDeletionService cartAdditonDeletionService;

	@Autowired
	UserCartBuyService userCartBuyService;

	@Autowired
	UserDetailsFetchService userDetailsFetchService;

	private String getCurrentUserID(HttpSession session) {
		return (String) session.getAttribute("UserID");
	}

	private UserDetails getCurrentUserData(String currentLoginUserID) {
		return userDetailsFetchService.getCurrentLoginUserFromDB(currentLoginUserID);
	}

	private ResponseEntity<CommonResponse> createErrorResponse(HttpStatus status, String message) {
		CommonResponse response = new CommonResponse(status.value(), message);
		return ResponseEntity.status(status).body(response);
	}

	private ResponseEntity<CommonResponse> createErrorResponseForProductBuy(HttpStatus status, String message,
			String currentLoginUserID) {
		CommonResponse response = new CommonResponse(status.value(), message, getCurrentUserData(currentLoginUserID));
		return ResponseEntity.status(status).body(response);
	}

	@GetMapping("/getproductdata/{category}")
	public ResponseEntity<CommonResponse> giveProductDetailsByCategory(@PathVariable String category) {
		List<ProductInventoryDetails> productList = productInventoryService
				.getProductDetailsByCategory(ProductTypes.getEnumValueFromString(category));
		if (productList != null && productList.size() != 0) {
			CommonResponse commonResponse = new CommonResponse(200,
					ResponseMessages.PRODUCTFETCHSUCCESS.getResponsesMessage(), productList);
			return ResponseEntity.ok(commonResponse);
		} else {
			return createErrorResponse(HttpStatus.EXPECTATION_FAILED,
					ResponseMessages.NOPRODUCTFOUND.getResponsesMessage());
		}

	}

	@PostMapping("/addtocart")
	public ResponseEntity<CommonResponse> addToCart(
			@RequestBody CartAdditonDeletionRequestDto cartAdditonDeletionRequestDto, HttpSession session) {
		try {
			String currentLoginUserID = getCurrentUserID(session);
			String responseMsg = cartAdditonDeletionService.addToCartService(cartAdditonDeletionRequestDto,
					currentLoginUserID);
			if (responseMsg.equals(ResponseMessages.ADDTOCARTSUCCESS.getResponsesMessage())) {
				UserDetails currentLoginUserDetails = getCurrentUserData(currentLoginUserID);
				CommonResponse commonResponse = new CommonResponse(200, responseMsg, currentLoginUserDetails);
				return ResponseEntity.ok(commonResponse);
			} else {
				return createErrorResponse(HttpStatus.EXPECTATION_FAILED, responseMsg);

			}
		} catch (Exception e) {
			return createErrorResponse(HttpStatus.EXPECTATION_FAILED,
					ResponseMessages.ADDTOCARTFAILED.getResponsesMessage());

		}
	}

	@PostMapping("/deletefromcart")
	public ResponseEntity<CommonResponse> deleteFromCart(
			@RequestBody CartAdditonDeletionRequestDto cartAdditonDeletionRequestDto, HttpSession session) {
		try {
			String currentLoginUserID = getCurrentUserID(session);
			String responseMsg = cartAdditonDeletionService.deleteFromCart(cartAdditonDeletionRequestDto,
					currentLoginUserID);
			if (responseMsg.equals(ResponseMessages.DELETECARTSUCCESS.getResponsesMessage())) {
				UserDetails currentLoginUserDetails = getCurrentUserData(currentLoginUserID);
				if (currentLoginUserDetails != null) {
					CommonResponse commonResponse = new CommonResponse(200, responseMsg, currentLoginUserDetails);
					return ResponseEntity.ok(commonResponse);
				}
			}
			return createErrorResponse(HttpStatus.EXPECTATION_FAILED, responseMsg);
		} catch (Exception e) {
			return createErrorResponse(HttpStatus.EXPECTATION_FAILED,
					ResponseMessages.DELETECARTFAILED.getResponsesMessage());

		}
	}

	@GetMapping("/buycartproducts")
	public ResponseEntity<CommonResponse> buyProductsInCart(HttpSession session) {
		String currentLoginUserID = getCurrentUserID(session);
		try {
			Boolean purchaseStatus = userCartBuyService.doBuyCartProducts(currentLoginUserID);
			if (purchaseStatus) {
				CommonResponse commonResponse = new CommonResponse(200,
						ResponseMessages.PRODUCTBUYSUCCESS.getResponsesMessage(),
						getCurrentUserData(currentLoginUserID));
				return ResponseEntity.ok(commonResponse);
			} else {
				return createErrorResponseForProductBuy(HttpStatus.EXPECTATION_FAILED,
						ResponseMessages.PRODUCTBUYFAILED.getResponsesMessage(), currentLoginUserID);
			}
		} catch (Exception e) {
			return createErrorResponseForProductBuy(HttpStatus.EXPECTATION_FAILED,
					ResponseMessages.PRODUCTBUYFAILED.getResponsesMessage(), currentLoginUserID);
		}
	}

}
