package com.bekkestad.examples.tutorial;

import java.util.List;

public class OrderDAO {
	private Long orderId;
	private String status;
	private List<ProductDAO> products;


	public OrderDAO() {
		super();
	}


	public Long getOrderId() {
		return orderId;
	}


	public List<ProductDAO> getProducts() {
		return products;
	}


	public String getStatus() {
		return status;
	}


	public void setOrderId(Long orderId) {
		this.orderId = orderId;
	}


	public void setProducts(List<ProductDAO> products) {
		this.products = products;
	}


	public void setStatus(String status) {
		this.status = status;
	}

}
