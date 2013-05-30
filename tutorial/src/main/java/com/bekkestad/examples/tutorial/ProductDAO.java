package com.bekkestad.examples.tutorial;

public class ProductDAO {
	private Long id;
	private String description;
	private String name;
	private Double price; 
	private OrderDAO order;
	/**
	 * 
	 */
	public ProductDAO() {
		super();
	}

	public String getDescription() {
		return description;
	}

	public Long getId() {
		return id;
	}

	public String getName() {
		return name;
	}

	public OrderDAO getOrder() {
		return order;
	}

	public Double getPrice() {
		return price;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public void setName(String name) {
		this.name = name;
	}

	public void setOrder(OrderDAO order) {
		this.order = order;
	}

	public void setPrice(Double price) {
		this.price = price;
	}
}
