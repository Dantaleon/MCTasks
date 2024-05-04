package com.nick.ms.repository;

import java.util.ArrayList;
import java.util.List;

import org.springframework.stereotype.Repository;

import com.nick.ms.model.Product;

@Repository
public class ProductRepository {
	
	public static List<Product> productList = new ArrayList<Product>();
	
	public List<Product> getAllProducts(){
		
		return productList;
	}
	
	public Product createProduct(Product product) {
		
		productList.add(product);
		return product;
	}
	
	public Product getProductByName(String name) {
		
		return findProductByName(name);
	}
	
	public void updateProductByName(Product product, String name) {
		
		Product prod = findProductByName(name);
		
		prod.setName(product.getName());
		prod.setDescription(product.getDescription());
		prod.setPrice(product.getPrice());
		prod.setInStock(product.isInStock()); 
	}
	
	public void deleteProductByName(String name) {
		
		productList.remove(findProductByName(name));
	}
	
	
	private Product findProductByName(String name) {
		
		Product product = null;
		
		for (Product prod : productList) {
			
			if (prod.getName().equals(name)) {
				
				product = prod;
				break;
			}
		}
		
		if (product == null) {
			// raise error
		}
		
		return product;
	}
}
