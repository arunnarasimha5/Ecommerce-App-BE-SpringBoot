package com.example.ecommerce.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.ecommerce.model.ProductInventoryDetails;
import com.example.ecommerce.model.enums.ProductTypes;
import com.example.ecommerce.repository.ProductRepository;

@Service
public class ProductInventoryService {

	@Autowired
	private ProductRepository productRepository;

	public List<ProductInventoryDetails> getProductDetailsByCategory(ProductTypes Category) {

		return productRepository.findByProductCategory(Category);
	}

}
