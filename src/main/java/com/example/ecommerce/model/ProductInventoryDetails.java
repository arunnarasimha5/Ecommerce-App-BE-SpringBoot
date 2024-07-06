package com.example.ecommerce.model;

import java.util.List;
import com.example.ecommerce.model.enums.ProductTypes;

import jakarta.persistence.ElementCollection;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.Id;
import jakarta.persistence.Lob;

@Entity
public class ProductInventoryDetails {

	@Id
	private String productId;

	private String productName;
	private String productDetails;
	
	@ElementCollection
	private List<ProductVarientDetails> productColorVarientAndPrice;
	
	@Enumerated(EnumType.STRING)
	private ProductTypes productCategory;
	
	@Lob
	private byte[] productImage;

	public String getProductId() {
		return productId;
	}

	public void setProductId(String productId) {
		this.productId = productId;
	}

	public String getProductName() {
		return productName;
	}

	public void setProductName(String productName) {
		this.productName = productName;
	}

	public String getProductDetails() {
		return productDetails;
	}

	public void setProductDetails(String productDetails) {
		this.productDetails = productDetails;
	}

	public List<ProductVarientDetails> getProductColorVarientAndPrice() {
		return productColorVarientAndPrice;
	}

	public void setProductColorVarientAndPrice(List<ProductVarientDetails> productColorVarientAndPrice) {
		this.productColorVarientAndPrice = productColorVarientAndPrice;
	}

	public ProductTypes getProductCategory() {
		return productCategory;
	}

	public void setProductCategory(ProductTypes productCategory) {
		this.productCategory = productCategory;
	}

	public byte[] getProductImage() {
		return productImage;
	}

	public void setProductImage(byte[] productImage) {
		this.productImage = productImage;
	}

	

}
