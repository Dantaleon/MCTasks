package com.nick.ms.repository;

import java.util.List;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.query.Query;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.EnableTransactionManagement;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.nick.ms.error.ProductNotCreatedException;
import com.nick.ms.error.ProductNotFoundException;
import com.nick.ms.model.Product;

@Repository
@Transactional
public class ProductRepository {
	
	@Autowired
	private SessionFactory sessionFactory;
	
	public ProductRepository() {}
	
	@Transactional
	public List<Product> getAllProducts() {

		Session session = this.sessionFactory.getCurrentSession();
		List<Product> productList = session.createQuery("select p from Product p", Product.class)
				.getResultList();
		
		return productList;
	}
	
	@Transactional
	public Product createProduct(Product product) {

		Session session = sessionFactory.getCurrentSession();
		
		if (session.get(Product.class, product.getName()) != null) {
			throw new ProductNotCreatedException("Product already exists, name: " + product.getName());
		}
		
		session.save(product);
		
		return product;
	}
	
	@Transactional(readOnly = true)
	public Product getProductByName(String name) {
		
		Session session = sessionFactory.getCurrentSession();
		
		Product product = session.get(Product.class, name);
		
		if (product == null) {
			
			throw new ProductNotFoundException("Product not found, name: " + name);
		}
		
		return product;
	}
	
	@Transactional
	public void updateProductByName(Product product, String name) {
		
		Session session = sessionFactory.getCurrentSession();
		
		Product productToUpdate = session.get(Product.class, name);
		Product productBody = session.get(Product.class, product.getName());
			
		if (productToUpdate == null) {
			throw new ProductNotFoundException("Can't edit. Product not found, name: " + name);
		}
		
		// if url name does not match json name AND json product found in DB
		if (!productToUpdate.getName().equals(product.getName()) && productBody != null) {
			
			throw new ProductNotCreatedException("Can't edit. Product name already taken, name: " + product.getName());
		}
		
		String hql = "UPDATE " + Product.class.getName() + " SET "
				+ "name = :name, "
				+ "description = :description, "
				+ "price = :price, "
				+ "isInStock = :inStock "
				+ "WHERE name = :urlName";
		
		Query query = session.createQuery(hql);
		query.setParameter("name", product.getName());
		query.setParameter("description", product.getDescription());
		query.setParameter("price", product.getPrice());
		query.setParameter("inStock", product.isInStock());
		query.setParameter("urlName", name);
		
		query.executeUpdate();
		
		
		
	}
	
	@Transactional
	public void deleteProductByName(String name) {

		Session session = sessionFactory.getCurrentSession();
		
		Product product = getProductByName(name);
		
		session.remove(product);
	}
	
}
