package com.example.ecommerce.controller;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.example.ecommerce.dto.request.UserDataUpdateRequestDto;
import com.example.ecommerce.dto.response.CommonResponse;
import com.example.ecommerce.dto.response.LoginInResponseDto;
import com.example.ecommerce.model.UserDetails;
import com.example.ecommerce.model.enums.ResponseMessages;
import com.example.ecommerce.service.CartAdditonDeletionService;
import com.example.ecommerce.service.UserDataUpdationService;
import com.example.ecommerce.service.UserDetailsFetchService;

import jakarta.servlet.http.HttpSession;

@RestController
@CrossOrigin(origins = "http://localhost:3000")
public class UserDataController {

	@Autowired
	CartAdditonDeletionService cartAdditonDeletionService;

	@Autowired
	UserDataUpdationService userDataUpdationService;

	@Autowired
	ModelMapper modelMapper;

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

	@GetMapping("/getcurrentuserdata")
	public ResponseEntity<CommonResponse> getCurrentLoginUserData(HttpSession session) {
		try {
			String currentLoginUserID = getCurrentUserID(session);
			LoginInResponseDto currentLoginUserDetails = modelMapper.map(getCurrentUserData(currentLoginUserID),
					LoginInResponseDto.class);
			if (currentLoginUserDetails != null) {

				CommonResponse response = new CommonResponse(200, ResponseMessages.LOGINSUCCESS.getResponsesMessage(),
						currentLoginUserDetails);

				return ResponseEntity.ok(response);
			} else {
				return createErrorResponse(HttpStatus.EXPECTATION_FAILED,
						ResponseMessages.USERDATAFETCHFAILED.getResponsesMessage());
			}

		} catch (Exception e) {
			return createErrorResponse(HttpStatus.EXPECTATION_FAILED,
					ResponseMessages.USERDATAFETCHFAILED.getResponsesMessage());
		}
	}

	@PostMapping("/updateuserdata")
	public ResponseEntity<CommonResponse> updateCurrentUserDetails(
			@RequestBody UserDataUpdateRequestDto userDataUpdateRequestDto, HttpSession session) {
		try {
			String currentLoginUserID = getCurrentUserID(session);
			String responseMsg = userDataUpdationService.doUserUpdation(userDataUpdateRequestDto, currentLoginUserID);
			if (responseMsg.equals(ResponseMessages.USERUPDATESUCCESS.getResponsesMessage())) {
				UserDetails currentLoginUserDetails = getCurrentUserData(currentLoginUserID);
				if (currentLoginUserDetails != null) {
					CommonResponse response = new CommonResponse(200,
							ResponseMessages.USERUPDATESUCCESS.getResponsesMessage(), currentLoginUserDetails);
					return ResponseEntity.ok(response);
				}
			}

			return createErrorResponse(HttpStatus.EXPECTATION_FAILED, responseMsg);
		}

		catch (Exception e) {
			return createErrorResponse(HttpStatus.EXPECTATION_FAILED,
					ResponseMessages.USERUPDATEFAILED.getResponsesMessage());
		}
	}

}
