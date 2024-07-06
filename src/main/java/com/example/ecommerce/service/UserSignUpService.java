package com.example.ecommerce.service;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.ecommerce.dto.request.SignUpRequestDto;
import com.example.ecommerce.model.UserDetails;
import com.example.ecommerce.repository.UserDetailsRepository;

@Service
public class UserSignUpService {

	@Autowired
	private UserDetailsRepository userDetailsRepository;

	@Autowired
	private ModelMapper modelMapper;

	public boolean signUpUserRegistration(SignUpRequestDto userDetails) {
		UserDetails userAlreadyExists = userDetailsRepository.findByUserID(userDetails.getUserID());
		if (userAlreadyExists != null) {
			return false;
		} else {
			UserDetails newUserData = modelMapper.map(userDetails, UserDetails.class);
			userDetailsRepository.save(newUserData);
			return true;
		}
	}

}
