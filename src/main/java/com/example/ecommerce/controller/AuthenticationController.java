package com.example.ecommerce.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.example.ecommerce.dto.request.LoginRequestDto;
import com.example.ecommerce.dto.request.SignUpRequestDto;
import com.example.ecommerce.dto.response.CommonResponse;
import com.example.ecommerce.dto.response.LoginInResponseDto;
import com.example.ecommerce.model.enums.ResponseMessages;
import com.example.ecommerce.service.UserLoginService;
import com.example.ecommerce.service.UserSignUpService;

import jakarta.servlet.http.HttpSession;

@RestController
@CrossOrigin(origins = "http://localhost:3000")
public class AuthenticationController {

	@Autowired
	UserLoginService userLoginService;

	@Autowired
	UserSignUpService userSignUpService;

	private ResponseEntity<CommonResponse> createErrorResponse(HttpStatus status, String message) {
		CommonResponse response = new CommonResponse(status.value(), message);
		return ResponseEntity.status(status).body(response);
	}

	@PostMapping("/login")
	public ResponseEntity<CommonResponse> doLogin(@RequestBody LoginRequestDto loginDetailsFromFrontEnd,
			HttpSession session) {

		try {
			LoginInResponseDto loginUser = userLoginService.authenticateUser(loginDetailsFromFrontEnd);

			if (loginUser != null) {
				session.setAttribute("UserID", loginUser.getUserID());
				CommonResponse response = new CommonResponse(200, ResponseMessages.LOGINSUCCESS.getResponsesMessage(),
						loginUser);
				return ResponseEntity.ok(response);
			} else {
				return createErrorResponse(HttpStatus.UNAUTHORIZED, ResponseMessages.LOGINERROR.getResponsesMessage());
			}
		} catch (Exception e) {
			return createErrorResponse(HttpStatus.EXPECTATION_FAILED,
					ResponseMessages.LOGINFAILED.getResponsesMessage());
		}
	}

	@GetMapping("/logout")
	public ResponseEntity<CommonResponse> doLogOut(HttpSession session) {
		session.invalidate();
		CommonResponse response = new CommonResponse(200, ResponseMessages.LOGOUTSUCCESS.getResponsesMessage());
		return ResponseEntity.ok(response);
	}

	@PostMapping("/signup")
	public ResponseEntity<CommonResponse> doSignUp(@RequestBody SignUpRequestDto signUpUserDetails) {
		try {
			Boolean signUpStatus = userSignUpService.signUpUserRegistration(signUpUserDetails);
			if (signUpStatus) {
				CommonResponse response = new CommonResponse(200, ResponseMessages.SIGNUPSUCCESS.getResponsesMessage());
				return ResponseEntity.ok(response);
			} else {

				return createErrorResponse(HttpStatus.UNAUTHORIZED,
						ResponseMessages.SIGNUPUSEREXISTS.getResponsesMessage());
			}
		} catch (Exception e) {
			return createErrorResponse(HttpStatus.EXPECTATION_FAILED,
					ResponseMessages.SIGNUPFAILED.getResponsesMessage());

		}
	}

}
