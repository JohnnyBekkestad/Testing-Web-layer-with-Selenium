package com.bekkestad.examples.tutorial;

import java.util.ArrayList;
import java.util.List;

import javax.ejb.EJB;

import junit.framework.Assert;

import org.jboss.arquillian.container.test.api.Deployment;
import org.jboss.arquillian.junit.Arquillian;
import org.jboss.arquillian.junit.InSequence;
import org.jboss.shrinkwrap.api.Archive;
import org.jboss.shrinkwrap.api.ShrinkWrap;
import org.jboss.shrinkwrap.api.asset.EmptyAsset;
import org.jboss.shrinkwrap.api.spec.WebArchive;
import org.junit.Test;
import org.junit.runner.RunWith;

@RunWith(Arquillian.class)
public class OrderRepositoryTest {
	
	private static Long orderId = 0L;
	
	@Deployment
	public static Archive<?> createDeployment(){
		return ShrinkWrap.create(WebArchive.class, "test.war")
				.addClasses(ProductDAO.class, OrderDAO.class, OrderEntity.class, ProductEntity.class, OrderRepository.class, OrderRepositoryImpl.class)
				.addAsResource("test-persistence.xml", "META-INF/persistence.xml")
				.addAsWebInfResource("jbossas-ds.xml")
				.addAsWebInfResource(EmptyAsset.INSTANCE, "beans.xml");		 
	}
	
	@EJB
	OrderRepository repository;
	
	@Test
	@InSequence(1)
	public void should_be_able_to_place_order(){
		List<ProductDAO> products = new ArrayList<ProductDAO>();
		
		ProductDAO product1 = new ProductDAO();
		product1.setName("PRODUCT1");
		product1.setDescription("PRODUCT1 Description");
		product1.setPrice(10D);
		
		ProductDAO product2 = new ProductDAO();
		product2.setName("PRODUCT2");
		product2.setDescription("PRODUCT2 Description");
		product2.setPrice(20D);
		
		products.add(product1);
		products.add(product2);
		
		orderId = repository.placeOrder(products);	
		
		Assert.assertTrue(orderId != 0L);
	}
	
	@Test
	@InSequence(2)
	public void should_be_able_to_get_order(){		
		OrderDAO order = repository.getOrder(orderId);
		
		List<ProductDAO> products = order.getProducts();
		Assert.assertEquals(2, products.size());
		Assert.assertEquals(orderId, order.getOrderId());
	}

}
