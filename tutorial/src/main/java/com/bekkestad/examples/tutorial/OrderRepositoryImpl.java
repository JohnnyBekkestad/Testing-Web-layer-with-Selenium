package com.bekkestad.examples.tutorial;

import java.util.List;

import javax.ejb.Lock;
import javax.ejb.LockType;
import javax.ejb.Singleton;
import javax.persistence.EntityManager;
import javax.persistence.NoResultException;
import javax.persistence.PersistenceContext;

@Singleton
@Lock(LockType.READ)
public class OrderRepositoryImpl implements OrderRepository {
	
	@PersistenceContext
	EntityManager em;
	
	@Override
	@Lock(LockType.WRITE)
	//@TransactionAttribute(TransactionAttributeType.SUPPORTS)
	public Long placeOrder(List<ProductDAO> products) {
		
		OrderEntity orderEntity = new OrderEntity();
		try {
			for(ProductDAO productDAO : products){
				ProductEntity productEntity = ProductEntity.fromDAO(productDAO);
				em.persist(productEntity);
				orderEntity.addProduct(productEntity);					
			}
		} catch (Exception e1) {			
			e1.printStackTrace();
		}
				
		
		try {
			em.persist(orderEntity);
			em.flush();	
		} catch (Exception e) {		
			e.printStackTrace();
		}
		
		return orderEntity.getId();
	}

	@Override
	public OrderDAO getOrder(Long orderId) {		
		OrderEntity orderEntity = null;
		
		try {						
			orderEntity = (OrderEntity)em.createNamedQuery(OrderEntity.FIND_ORDER)
				.setParameter("orderId", orderId).getSingleResult();
					
		} catch (NoResultException nre) {
			nre.printStackTrace();						
		}
		
		return orderEntity.asDAO();
	}

}
