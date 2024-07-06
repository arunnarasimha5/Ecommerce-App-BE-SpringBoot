package com.example.ecommerce.model;

import java.math.BigDecimal;

import com.example.ecommerce.model.enums.ProductColor;

import jakarta.persistence.Embeddable;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;

@Embeddable
public class ProductVarientDetails {

	private BigDecimal price;
	
	@Enumerated(EnumType.STRING)
	private ProductColor color;

	public BigDecimal getPrice() {
		return price;
	}

	public void setPrice(BigDecimal price) {
		this.price = price;
	}

	public ProductColor getColor() {
		return color;
	}

	public void setColor(ProductColor color) {
		this.color = color;
	}

}
