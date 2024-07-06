package com.example.ecommerce.service;

import java.util.ArrayList;
import java.util.List;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.ecommerce.dto.request.LoginRequestDto;
import com.example.ecommerce.dto.response.LoginInResponseDto;
import com.example.ecommerce.dto.response.ProductCategoryDetailsResponseDto;
import com.example.ecommerce.model.UserDetails;
import com.example.ecommerce.model.enums.ProductTypes;
import com.example.ecommerce.repository.ProductRepository;
import com.example.ecommerce.repository.UserDetailsRepository;

@Service
public class UserLoginService {

	@Autowired
	private UserDetailsRepository userDetailsRepository;

	@Autowired
	private ProductRepository productRepository;

	@Autowired
	ModelMapper modelMapper;

	private boolean isDataAlreadyPresent(List<ProductCategoryDetailsResponseDto> categoryDetails, String searchString) {
		Boolean found = false;
		for (ProductCategoryDetailsResponseDto eachCategory : categoryDetails) {
			if (eachCategory.getValue().equals(searchString)) {
				found = true;
				break;
			}
		}

		return found;
	}

	private List<ProductCategoryDetailsResponseDto> getProductCategories() {
		List<ProductCategoryDetailsResponseDto> categoryDetails = new ArrayList<>();

		productRepository.findAll().forEach(obj -> {
			ProductTypes category = obj.getProductCategory();
			String productCategory = category.getProductCategory();
			if (!isDataAlreadyPresent(categoryDetails, productCategory)) {
				ProductCategoryDetailsResponseDto elementToPush = new ProductCategoryDetailsResponseDto();
				elementToPush.setName(category.name());
				elementToPush.setValue(productCategory);
				categoryDetails.add(elementToPush);
			}
		});
		;

		return categoryDetails;
	}

	public LoginInResponseDto authenticateUser(LoginRequestDto loginUserDetails) {
		UserDetails user = userDetailsRepository.findByUserID(loginUserDetails.getUserID());
		if (user != null && user.getPassword().equals(loginUserDetails.getPassword())) {
			LoginInResponseDto loginResponse = modelMapper.map(user, LoginInResponseDto.class);
			loginResponse.setProductCategoryDetails(getProductCategories());
			return loginResponse;
		} else {
			return null;
		}

	}

}
