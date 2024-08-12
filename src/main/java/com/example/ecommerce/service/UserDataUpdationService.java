package com.example.ecommerce.service;

import org.modelmapper.ModelMapper;
import org.modelmapper.PropertyMap;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.ecommerce.dto.request.UserDataUpdateRequestDto;
import com.example.ecommerce.model.UserDetails;
import com.example.ecommerce.model.enums.ResponseMessages;
import com.example.ecommerce.repository.UserDetailsRepository;

import jakarta.servlet.http.HttpSession;

@Service
public class UserDataUpdationService {

	@Autowired
	private UserDetailsRepository userDetailsRepository;

	public String doUserUpdation(UserDataUpdateRequestDto userDataUpdateRequestDto, String currentLoginUserID) {
		UserDetails currentLoginUserDetails = userDetailsRepository.findByUserID(currentLoginUserID);
		if (currentLoginUserDetails != null) {
			Boolean isOldPasswordCorrect = currentLoginUserDetails.getPassword()
					.equals(userDataUpdateRequestDto.getOldPassword());
			if (isOldPasswordCorrect && currentLoginUserDetails.getUserID().equals(currentLoginUserID)) {
				ModelMapper localModelMapper = new ModelMapper();
				localModelMapper.addMappings(new PropertyMap<UserDataUpdateRequestDto, UserDetails>() {
					@Override
					protected void configure() {
						skip(destination.getPassword());
					}
				});
				UserDetails updatedUserDetails = localModelMapper.map(userDataUpdateRequestDto, UserDetails.class);
				updatedUserDetails.setUserID(currentLoginUserID);
				updatedUserDetails.setPassword(userDataUpdateRequestDto.getNewPassword());
				updatedUserDetails.setCartDetails(currentLoginUserDetails.getCartDetails());
				userDetailsRepository.save(updatedUserDetails);
				return ResponseMessages.USERUPDATESUCCESS.getResponsesMessage();
			} else {
				return ResponseMessages.USERUPDATEPASSMISMATCH.getResponsesMessage();
			}
		} else {
			return ResponseMessages.USERDATAFETCHFAILED.getResponsesMessage();
		}
	}

}
