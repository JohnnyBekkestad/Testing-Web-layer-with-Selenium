package com.bekkestad.examples.tutorial;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.persistence.Transient;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

@Entity
public class ProductEntity implements Serializable{

	private static final long serialVersionUID = 1L;
	
	public static ProductEntity fromDAO(ProductDAO productDAO){
		ProductEntity productEntity = new ProductEntity();
		productEntity.setName(productDAO.getName());
		productEntity.setDescription(productDAO.getDescription());
		productEntity.setPrice(productDAO.getPrice());
		
		if(productDAO.getOrder() != null){
			productEntity.setOrder(OrderEntity.fromDAO(productDAO.getOrder()));
		}
		
		return productEntity;
	}
	
	@Id
	@GeneratedValue	
	private Long id;
	
	@NotNull
	@Size(min=3, max=50)
	private String name;
	
	@Size(max=2000)
	private String description;
	
	@NotNull
	@Column(precision=10, scale=2)
	private Double price;
	
	@ManyToOne
	private OrderEntity order;
	
	@Transient 
	public ProductDAO asDAO(){
		ProductDAO productDAO = new ProductDAO();
		productDAO.setId(this.id);
		productDAO.setName(this.name);
		productDAO.setDescription(this.description);
		productDAO.setPrice(this.price);
		productDAO.setOrder(this.order.asDAO());
		
		return productDAO;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		ProductEntity other = (ProductEntity) obj;
		if (id == null) {
			if (other.id != null)
				return false;
		} else if (!id.equals(other.id))
			return false;
		return true;
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

	public OrderEntity getOrder() {
		return order;
	}

	public Double getPrice() {
		return price;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((id == null) ? 0 : id.hashCode());
		return result;
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

	public void setOrder(OrderEntity order) {
		this.order = order;
		if(!order.getProducts().contains(this)){
			order.getProducts().add(this);
		}
	}

	public void setPrice(Double price) {
		this.price = price;
	}

	@Override
    public String toString() {
        return "Product@" + hashCode() + "[id:" + id + "; name:" + name + ";]";
    }
	
}
