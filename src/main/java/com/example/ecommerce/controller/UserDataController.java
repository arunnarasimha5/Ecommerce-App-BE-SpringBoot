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

@RestController
@CrossOrigin(origins = "http://localhost:3000")
public class UserDataController {

	@Autowired
	CartAdditonDeletionService cartAdditonDeletionService;

	@Autowired
	UserDataUpdationService userDataUpdationService;
	
	@Autowired
	ModelMapper modelMapper;

	private ResponseEntity<CommonResponse> createErrorResponse(HttpStatus status, String message) {
		CommonResponse response = new CommonResponse(status.value(), message);
		return ResponseEntity.status(status).body(response);
	}

	@GetMapping("/getcurrentuserdata")
	public ResponseEntity<CommonResponse> getCurrentLoginUserData() {
		try {

			LoginInResponseDto currentLoginUserDetails = modelMapper.map(cartAdditonDeletionService.getCurrentLoginUserDetailsFromDb(), LoginInResponseDto.class);
			if (currentLoginUserDetails != null) {

				CommonResponse response = new CommonResponse(200, ResponseMessages.LOGINSUCCESS.getResponsesMessage(),
						currentLoginUserDetails);

				return ResponseEntity.ok(response);
			} else {
				return createErrorResponse(HttpStatus.EXPECTATION_FAILED,
						ResponseMessages.USERDATAFETCHFAILED.getResponsesMessage());
			}

		} catch (Exception e) {
			System.out.println(e);
			return createErrorResponse(HttpStatus.EXPECTATION_FAILED,
					ResponseMessages.USERDATAFETCHFAILED.getResponsesMessage());
		}
	}

	@PostMapping("/updateuserdata")
	public ResponseEntity<CommonResponse> updateCurrentUserDetails(
			@RequestBody UserDataUpdateRequestDto userDataUpdateRequestDto) {
		try {
			String responseMsg = userDataUpdationService.doUserUpdation(userDataUpdateRequestDto);
			if (responseMsg.equals(ResponseMessages.USERUPDATESUCCESS.getResponsesMessage())) {
				UserDetails currentLoginUserDetails = cartAdditonDeletionService.getCurrentLoginUserDetailsFromDb();
				if (currentLoginUserDetails != null) {
					CommonResponse response = new CommonResponse(200,
							ResponseMessages.LOGINSUCCESS.getResponsesMessage(), currentLoginUserDetails);
					return ResponseEntity.ok(response);
				}
			}

			return createErrorResponse(HttpStatus.EXPECTATION_FAILED,
					ResponseMessages.USERUPDATEFAILED.getResponsesMessage());
		}

		catch (Exception e) {
			return createErrorResponse(HttpStatus.EXPECTATION_FAILED,
					ResponseMessages.USERUPDATEFAILED.getResponsesMessage());
		}
	}

}
