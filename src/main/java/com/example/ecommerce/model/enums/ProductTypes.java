package com.example.ecommerce.model.enums;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonValue;

public enum ProductTypes {
	SMARTPHONE("smartphone"), LAPTOP("laptop"), SMARTWATCH("smartwatch");

	private final String productCategory;

	@JsonCreator
	private ProductTypes(String productCategory) {
		this.productCategory = productCategory;
	}
	
	@JsonValue
	public String getProductCategory() {
		return productCategory;
	}
	

	
	public static ProductTypes getEnumValueFromString(String category) {
		for(ProductTypes eachType: values()) {
			if(eachType.getProductCategory().equalsIgnoreCase(category)) {
				return eachType;
			}
		}
		return null;
	}
}
