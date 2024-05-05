package com.nick.ms.repository;

import java.util.ArrayList;
import java.util.List;

import org.springframework.stereotype.Repository;

import com.nick.ms.error.ProductNotCreatedException;
import com.nick.ms.error.ProductNotFoundException;
import com.nick.ms.model.Product;

@Repository
public class ProductRepository {

	public static List<Product> productList = new ArrayList<Product>();

	public List<Product> getAllProducts() {

		return productList;
	}

	public Product createProduct(Product product) {

		validateProduct(product, true);
		productList.add(product);
		return product;
	}

	public Product getProductByName(String name) {
		
		return findProductByName(name, true);
	}

	public void updateProductByName(Product product, String name) {
		
		Product prod = findProductByName(name, true); // proceed if not null, otherwise throw exception
		
		boolean checkName = false;
		
		if (!product.getName().equals(name)) {     // if objects not the same, check if name is unique
			
			checkName = true;
		} else {
			
			checkName = false;                    // otherwise not
			
		}
		
		validateProduct(product, checkName);
		
		prod.setName(product.getName());
		prod.setDescription(product.getDescription());
		prod.setPrice(product.getPrice());
		prod.setInStock(product.isInStock());
	}

	public void deleteProductByName(String name) {

		productList.remove(findProductByName(name, false));
	}
	
	private void validateProduct(Product product, boolean uniqueName) {
		
		StringBuilder errors = new StringBuilder();
		
		// name validation
		String name = product.getName();
		
		
		if (findProductByName(name, false) != null && uniqueName) {
			errors.append("name should be unique").append(";");
		}
		
		if (name.isEmpty() || name == null) {
			errors.append("name should not be empty").append(";");
		}
		if (name.length() > 255) {
			errors.append("name exceed length of 255. Your length is " + name.length()).append(";");
		}
		
		// description validation
		String description = product.getDescription();
		if (description.length() > 4096) {
			errors.append("description exceed length of 4096. Your length is " + description.length()).append(";");
		}
		
		// price validation
		if (product.getPrice() < 0) {
			errors.append("price can't be negative").append(";");
		}
		
		if (errors.toString().length() != 0) {
			
			throw new ProductNotCreatedException(errors.toString());
		}
	}
	

	private Product findProductByName(String name, boolean isThrowing) {

		Product product = null;

		for (Product prod : productList) {

			if (prod.getName().equals(name)) {

				product = prod;
				break;
			}
		}
		
		if (product == null && isThrowing) {
			throw new ProductNotFoundException("product with name " + name + " not found");
		}
		
		return product;
	}
}
