package com.nick.ms.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import com.nick.ms.error.GeneralErrorResponse;
import com.nick.ms.error.ProductNotCreatedException;
import com.nick.ms.error.ProductNotFoundException;
import com.nick.ms.model.Product;
import com.nick.ms.repository.ProductRepository;

import jakarta.validation.Valid;

@RestController
public class ProductController {
	
	
	ProductRepository productRep;
	
	@Autowired
	public ProductController(ProductRepository productRep) {
		this.productRep = productRep;
	}
	
	

	@PostMapping("/products")
	@ResponseStatus(HttpStatus.CREATED)
	public Product createProduct(@RequestBody @Valid Product product, BindingResult errorMessages) {
		
		if (errorMessages.hasErrors()) {
			throw new ProductNotCreatedException(errorMessages.getFieldErrors().toString());
		}
		
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
	@ResponseStatus(HttpStatus.NO_CONTENT)
	public void updateProductByName(@RequestBody @Valid Product product, BindingResult errorMessages,
			@PathVariable String name) {
		
		if (errorMessages.hasErrors()) {
			throw new ProductNotCreatedException(errorMessages.getAllErrors().toString());
		}
		
		productRep.updateProductByName(product, name);
	}
	
	@DeleteMapping("/products/{name}")
	@ResponseStatus(HttpStatus.NO_CONTENT)
	public void deleteProductByName(@PathVariable String name) {
		
		productRep.deleteProductByName(name);
	}
	
	// Exception handlers
	@ExceptionHandler({ProductNotCreatedException.class})
	public ResponseEntity<GeneralErrorResponse> handleProductException(ProductNotCreatedException e) {
		
		GeneralErrorResponse errorResponse = new GeneralErrorResponse(e.getMessage());
		
		return new ResponseEntity<>(errorResponse, HttpStatus.BAD_REQUEST);
	}
	
	@ExceptionHandler({ProductNotFoundException.class})
	public ResponseEntity<GeneralErrorResponse> handleProductException(ProductNotFoundException e) {
		
		GeneralErrorResponse errorResponse = new GeneralErrorResponse(e.getMessage());
		
		return new ResponseEntity<>(errorResponse, HttpStatus.NOT_FOUND);
	}
	// Exception handlers END
}
