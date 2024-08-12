package com.example.ecommerce.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.ecommerce.model.UserDetails;
import com.example.ecommerce.repository.UserDetailsRepository;

@Service
public class UserDetailsFetchService {

	@Autowired
	private UserDetailsRepository userDetailsRepository;

	public UserDetails getCurrentLoginUserFromDB(String currentLoginUserID) {
		return userDetailsRepository.findByUserID(currentLoginUserID);
	}
}
