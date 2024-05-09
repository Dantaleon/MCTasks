package com.nick.ms.model;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Positive;
import jakarta.validation.constraints.Size;

@Entity
@Table(name = "product")
public class Product {
	
	@Id
	@Column(name = "name", nullable = false, length = 255)
	@NotBlank(message = "name should not be blank")
	@Size(min = 1, max = 255, message = "name length should be between 1 and 255")
	private String name;
	
	@Column(name = "description", nullable = false, length = 4095)
	@Size(max = 4095, message = "description length should not be bigger than [4095]")
	private String description;
	
	@Column(name = "price", nullable = false)
	@Positive(message = "price should be positive")
	private double price = (Double) 0.0;
	
	@Column(name = "inStock", nullable = false, columnDefinition = "boolean NOT NULL DEFAULT false")
	private boolean isInStock;
	
	public Product() {}
	
	public Product(String name, String description, Double price, Boolean isInStock) {
		super();
		this.name = name;
		this.description = description;
		this.price = price;
		this.isInStock = isInStock;
	}
	
	public Product(String name, String description) {
		super();
		this.name = name;
		this.description = description;
	}
	

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public double getPrice() {
		return price;
	}

	public void setPrice(double price) {
		this.price = price;
	}

	public boolean isInStock() {
		return isInStock;
	}

	public void setInStock(boolean isInStock) {
		this.isInStock = isInStock;
	}
	
}
