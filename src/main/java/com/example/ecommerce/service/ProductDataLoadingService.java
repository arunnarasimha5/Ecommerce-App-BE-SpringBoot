package com.example.ecommerce.service;

import java.io.File;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.ecommerce.model.ProductInventoryDetails;
import com.example.ecommerce.repository.ProductRepository;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;

import jakarta.annotation.PostConstruct;

@Service
public class ProductDataLoadingService {

	@Autowired
	private ProductRepository productRepository;

	@Autowired
	private ObjectMapper objectMapper;

	@PostConstruct
	public void init() {
		try {
			List<ProductInventoryDetails> productInventoryDetails = objectMapper.readValue(
					new File("src/main/resources/productDetails.json"), new TypeReference<List<ProductInventoryDetails>>() {
					});
			productRepository.saveAll(productInventoryDetails);

		} catch (Exception e) {
			System.out.println(e);
			System.out.println("Initial data load from the JSON Failed");
		}
	}
	
	public List<ProductInventoryDetails> getAllInputData() {
		return productRepository.findAll();
	}

}
