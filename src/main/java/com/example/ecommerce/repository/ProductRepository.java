package com.example.ecommerce.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.example.ecommerce.model.ProductInventoryDetails;
import com.example.ecommerce.model.enums.ProductTypes;

public interface ProductRepository extends JpaRepository<ProductInventoryDetails, String> {

	List<ProductInventoryDetails> findByProductCategory(ProductTypes productCategory);

	ProductInventoryDetails findByProductId(String productId);

}
