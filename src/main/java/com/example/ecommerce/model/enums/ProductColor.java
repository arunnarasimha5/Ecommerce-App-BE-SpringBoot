package com.example.ecommerce.model.enums;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonValue;

public enum ProductColor {
	BLACK("Black"), WHITE("White");

	private final String color;
	
	@JsonCreator
	private ProductColor(String color) {
		this.color = color;
	}
	
	@JsonValue
	public String getColor() {
		return color;
	}

}
