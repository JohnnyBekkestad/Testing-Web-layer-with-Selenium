package com.bekkestad.examples.tutorial;

import java.util.List;

import javax.ejb.Local;

@Local
public interface OrderRepository {

	Long placeOrder(List<ProductDAO> order);	
	OrderDAO getOrder(Long orderId);	
}
