package com.bekkestad.examples.tutorial;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.OneToMany;
import javax.persistence.Transient;

@Entity
@NamedQueries({
	@NamedQuery(name=OrderEntity.FIND_ORDER, 
			query="select o from OrderEntity o WHERE o.id = :orderId")
	})
public class OrderEntity {

	public static final String FIND_ORDER = "findOrder";
	
	@Id
	@GeneratedValue	
	private Long id;
	
	@OneToMany(mappedBy="order", fetch=FetchType.LAZY, cascade=CascadeType.ALL)
	private List<ProductEntity> products = new ArrayList<ProductEntity>();
	
	private String status;

	public void addProduct(ProductEntity product){
		this.products.add(product);
		if(product.getOrder() != this){
			product.setOrder(this);
		}
	}
	
	public OrderEntity(){
		this.status = "CREATED";
	}
	
	public OrderEntity(Long orderId, List<ProductEntity> products) {
		super();
		this.id = orderId;
		this.products = products;
		this.status = "CREATED";
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}
	
	public List<ProductEntity> getProducts() {
		return products;
	}

	public void setProducts(List<ProductEntity> products) {
		this.products = products;
	}
	
	@Override
    public String toString() {
        return "Order@" + hashCode() + "[id:" + id + "; status:" + status + "; products:" + (products==null?"NULL":products.size())+ "]";
    }

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((id == null) ? 0 : id.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		OrderEntity other = (OrderEntity) obj;
		if (id == null) {
			if (other.id != null)
				return false;
		} else if (!id.equals(other.id))
			return false;
		return true;
	}
	
	@Transient
	public OrderDAO asDAO(){
		OrderDAO orderDAO = new OrderDAO();
		orderDAO.setOrderId(this.id);
		orderDAO.setStatus(this.status);
		List<ProductDAO> products = new ArrayList<ProductDAO>();
		for(ProductEntity product : this.products){
			ProductDAO productDAO = new ProductDAO();
			productDAO.setId(product.getId());
			productDAO.setName(product.getName());
			productDAO.setDescription(product.getDescription());
			productDAO.setPrice(product.getPrice());
			productDAO.setOrder(orderDAO);
			products.add(productDAO);
		}
		orderDAO.setProducts(products);
		
		return orderDAO;
	}
	
	public static OrderEntity fromDAO(OrderDAO orderDAO){
		
		OrderEntity orderEntity = new OrderEntity();
		orderEntity.setStatus(orderDAO.getStatus());
		List<ProductEntity> productList = new ArrayList<ProductEntity>();
		for(ProductDAO dao : orderDAO.getProducts()){
			productList.add(ProductEntity.fromDAO(dao));
		}
		orderEntity.setProducts(productList);
		return orderEntity;
	}
	
}
