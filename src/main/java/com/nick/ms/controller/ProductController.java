package com.nick.ms.controller;

import java.util.List;

import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.nick.ms.model.Product;
import com.nick.ms.repository.ProductRepository;

@RestController
public class ProductController {
	
	ProductRepository productRep = new ProductRepository();

	@PostMapping("/products")
	public Product createProduct(Product product) {
		
		productRep.createProduct(product);
		return product;
	}
	
	@GetMapping("/products/{name}")
	public Product getProductByName(@PathVariable String name) {
		
		return productRep.getProductByName(name);
	}
	
	@GetMapping("/products")
	public List<Product> getProducts(){
		
		return productRep.getAllProducts();
	}
	
	@PutMapping("/products/{name}")
	public void updateProductByName(Product product, @PathVariable String name) {
		
		productRep.updateProductByName(product, name);
	}
	
	@DeleteMapping("/products/{name}")
	public void deleteProductByName(@PathVariable String name) {
		
		productRep.deleteProductByName(name);
	}
}
