package com.example.ecommerce.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.example.ecommerce.model.UserDetails;

public interface UserDetailsRepository extends JpaRepository<UserDetails, String> {

	UserDetails findByUserID(String userID);

}
