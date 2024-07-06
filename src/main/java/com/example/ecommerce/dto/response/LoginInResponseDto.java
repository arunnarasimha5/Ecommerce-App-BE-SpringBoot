package com.example.ecommerce.dto.response;

import java.math.BigDecimal;
import java.util.List;

public class LoginInResponseDto {
	private String userID;
	private String name;
	private String email;
	private BigDecimal credit;
	private List<UserCartResponseDto> cartDetails;
	private List<ProductCategoryDetailsResponseDto> productCategoryDetails;

	public String getUserID() {
		return userID;
	}

	public void setUserID(String userID) {
		this.userID = userID;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public BigDecimal getCredit() {
		return credit;
	}

	public void setCredit(BigDecimal credit) {
		this.credit = credit;
	}

	public List<UserCartResponseDto> getCartDetails() {
		return cartDetails;
	}

	public void setCartDetails(List<UserCartResponseDto> cartDetails) {
		this.cartDetails = cartDetails;
	}

	public List<ProductCategoryDetailsResponseDto> getProductCategoryDetails() {
		return productCategoryDetails;
	}

	public void setProductCategoryDetails(List<ProductCategoryDetailsResponseDto> productCategoryDetails) {
		this.productCategoryDetails = productCategoryDetails;
	}

}
