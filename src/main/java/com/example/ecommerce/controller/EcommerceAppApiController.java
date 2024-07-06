package com.example.ecommerce.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RestController;

import com.example.ecommerce.dto.request.CartAdditonDeletionRequestDto;
import com.example.ecommerce.dto.request.LoginRequestDto;
import com.example.ecommerce.dto.request.SignUpRequestDto;
import com.example.ecommerce.dto.request.UserDataUpdateRequestDto;
import com.example.ecommerce.dto.response.LoginInResponseDto;
import com.example.ecommerce.model.ProductInventoryDetails;
import com.example.ecommerce.model.UserDetails;
import com.example.ecommerce.model.enums.ProductTypes;
import com.example.ecommerce.model.enums.ResponseMessages;
import com.example.ecommerce.service.CartAdditonDeletionService;
import com.example.ecommerce.service.ProductDataLoadingService;
import com.example.ecommerce.service.ProductInventoryService;
import com.example.ecommerce.service.UserCartBuyService;
import com.example.ecommerce.service.UserDataUpdationService;
import com.example.ecommerce.service.UserLoginService;
import com.example.ecommerce.service.UserSignUpService;

import jakarta.servlet.http.HttpSession;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

@RestController
public class EcommerceAppApiController {

	@Autowired
	ProductDataLoadingService productDataLoadingService;

	@Autowired
	UserLoginService userLoginService;

	@Autowired
	UserSignUpService userSignUpService;

	@Autowired
	ProductInventoryService productInventoryService;

	@Autowired
	CartAdditonDeletionService cartAdditonDeletionService;

	@Autowired
	UserDataUpdationService userDataUpdationService;

	@Autowired
	UserCartBuyService userCartBuyService;

	@PostMapping("/login")
	public ResponseEntity<Object> doLogin(@RequestBody LoginRequestDto loginDetailsFromFrontEnd, HttpSession session) {
		try {
			LoginInResponseDto loginUser = userLoginService.authenticateUser(loginDetailsFromFrontEnd);
			if (loginUser != null) {
				session.setAttribute("UserID", loginUser.getUserID());
				return ResponseEntity.ok(loginUser);
			} else {
				return ResponseEntity.status(HttpStatus.UNAUTHORIZED)
						.body(ResponseMessages.LOGINERROR.getResponsesMessage());
			}
		} catch (Exception e) {
			return ResponseEntity.status(HttpStatus.EXPECTATION_FAILED)
					.body(ResponseMessages.LOGINFAILED.getResponsesMessage());
		}
	}

	@GetMapping("/logout")
	public ResponseEntity<Object> doLogOut(HttpSession session) {
		session.invalidate();
		return ResponseEntity.ok(ResponseMessages.LOGOUTSUCCESS.getResponsesMessage());
	}

	@PostMapping("/signup")
	public ResponseEntity<String> doSignUp(@RequestBody SignUpRequestDto signUpUserDetails) {
		try {
			Boolean signUpStatus = userSignUpService.signUpUserRegistration(signUpUserDetails);
			if (signUpStatus) {
				return ResponseEntity.ok(ResponseMessages.SIGNUPSUCCESS.getResponsesMessage());
			} else {
				return ResponseEntity.status(HttpStatus.EXPECTATION_FAILED)
						.body(ResponseMessages.SIGNUPUSEREXISTS.getResponsesMessage());
			}
		} catch (Exception e) {
			return ResponseEntity.status(HttpStatus.EXPECTATION_FAILED)
					.body(ResponseMessages.SIGNUPFAILED.getResponsesMessage());
		}
	}

	@GetMapping("/getproductdata/{category}")
	public ResponseEntity<Object> giveProductDetailsByCategory(@PathVariable String category) {
		List<ProductInventoryDetails> productList = productInventoryService
				.getProductDetailsByCategory(ProductTypes.getEnumValueFromString(category));
		if (productList != null && productList.size() != 0) {
			return ResponseEntity.ok(productList);
		} else {
			return ResponseEntity.status(HttpStatus.EXPECTATION_FAILED)
					.body(ResponseMessages.NOPRODUCTFOUND.getResponsesMessage());
		}

	}

	@PostMapping("/addtocart")
	public ResponseEntity<Object> addToCart(@RequestBody CartAdditonDeletionRequestDto cartAdditonDeletionRequestDto) {
		try {
			String responseMsg = cartAdditonDeletionService.addToCartService(cartAdditonDeletionRequestDto);
			if (responseMsg.equals(ResponseMessages.ADDTOCARTSUCCESS.getResponsesMessage())) {
				return ResponseEntity.ok(responseMsg);
			} else {
				return ResponseEntity.status(HttpStatus.EXPECTATION_FAILED).body(responseMsg);

			}
		} catch (Exception e) {
			System.out.print(e);
			return ResponseEntity.status(HttpStatus.EXPECTATION_FAILED)
					.body(ResponseMessages.ADDTOCARTFAILED.getResponsesMessage());

		}
	}

	@PostMapping("/deletefromcart")
	public ResponseEntity<Object> deleteFromCart(
			@RequestBody CartAdditonDeletionRequestDto cartAdditonDeletionRequestDto) {
		try {
			String responseMsg = cartAdditonDeletionService.deleteFromCart(cartAdditonDeletionRequestDto);
			if (responseMsg.equals(ResponseMessages.DELETECARTSUCCESS.getResponsesMessage())) {
				UserDetails currentLoginUserDetails = cartAdditonDeletionService.getCurrentLoginUserDetailsFromDb();
				if (currentLoginUserDetails != null) {
					return ResponseEntity.ok(currentLoginUserDetails);
				} else {
					return ResponseEntity.status(HttpStatus.EXPECTATION_FAILED)
							.body(ResponseMessages.DELETECARTFAILED.getResponsesMessage());
				}

			} else {
				return ResponseEntity.status(HttpStatus.EXPECTATION_FAILED).body(responseMsg);
			}

		} catch (Exception e) {
			return ResponseEntity.status(HttpStatus.EXPECTATION_FAILED)
					.body(ResponseMessages.DELETECARTFAILED.getResponsesMessage());
		}
	}

	@GetMapping("/getcurrentuserdata")
	public ResponseEntity<Object> getCurrentLoginUserData() {
		try {
			UserDetails currentLoginUserDetails = cartAdditonDeletionService.getCurrentLoginUserDetailsFromDb();
			if (currentLoginUserDetails != null) {
				return ResponseEntity.ok(currentLoginUserDetails);
			} else {
				return ResponseEntity.status(HttpStatus.EXPECTATION_FAILED)
						.body(ResponseMessages.USERDATAFETCHFAILED.getResponsesMessage());
			}

		} catch (Exception e) {
			return ResponseEntity.status(HttpStatus.EXPECTATION_FAILED)
					.body(ResponseMessages.USERDATAFETCHFAILED.getResponsesMessage());
		}
	}

	@PostMapping("/updateuserdata")
	public ResponseEntity<Object> updateCurrentUserDetails(
			@RequestBody UserDataUpdateRequestDto userDataUpdateRequestDto) {
		try {
			String responseMsg = userDataUpdationService.doUserUpdation(userDataUpdateRequestDto);
			if (responseMsg.equals(ResponseMessages.USERUPDATESUCCESS.getResponsesMessage())) {
				UserDetails currentLoginUserDetails = cartAdditonDeletionService.getCurrentLoginUserDetailsFromDb();
				if (currentLoginUserDetails != null) {
					return ResponseEntity.ok(currentLoginUserDetails);
				} else {
					return ResponseEntity.status(HttpStatus.EXPECTATION_FAILED)
							.body(ResponseMessages.USERUPDATEFAILED.getResponsesMessage());
				}

			} else {
				return ResponseEntity.status(HttpStatus.EXPECTATION_FAILED).body(responseMsg);
			}

		} catch (Exception e) {
			return ResponseEntity.status(HttpStatus.EXPECTATION_FAILED)
					.body(ResponseMessages.USERUPDATEFAILED.getResponsesMessage());
		}
	}

	@GetMapping("/buycartproducts")
	public ResponseEntity<Object> buyProductsInCart() {
		try {
			Boolean purchaseStatus = userCartBuyService.doBuyCartProducts();
			if (purchaseStatus) {
				return ResponseEntity.ok(ResponseMessages.PRODUCTBUYSUCCESS.getResponsesMessage());
			} else {
				return ResponseEntity.status(HttpStatus.EXPECTATION_FAILED)
						.body(cartAdditonDeletionService.getCurrentLoginUserDetailsFromDb());
			}
		} catch (Exception e) {
			return ResponseEntity.status(HttpStatus.EXPECTATION_FAILED)
					.body(cartAdditonDeletionService.getCurrentLoginUserDetailsFromDb());
		}
	}
}
