package com.nick.ms.model;

public class Product {
	
	private String name;
	private String description;
	private double price;
	private boolean isInStock;
	
	public Product() {
		super();
	}
	
	public Product(String name, String description, double price, boolean isInStock) {
		super();
		this.name = name;
		this.description = description;
		this.price = price;
		this.isInStock = isInStock;
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
