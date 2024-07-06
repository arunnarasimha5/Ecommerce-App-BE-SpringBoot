package com.example.ecommerce.model;

import java.math.BigDecimal;
import java.util.List;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Embedded;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;

@Entity
public class UserDetails {

	@Id
	private String userID;

	private String name;
	private String password;
	private String email;
	private BigDecimal credit;

	@OneToMany(cascade = CascadeType.ALL, fetch = FetchType.LAZY)
	@Embedded
	private List<UserCartDetails> cartDetails;

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

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
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

	public List<UserCartDetails> getCartDetails() {
		return cartDetails;
	}

	public void setCartDetails(List<UserCartDetails> cartDetails) {
		this.cartDetails = cartDetails;
	}

}
