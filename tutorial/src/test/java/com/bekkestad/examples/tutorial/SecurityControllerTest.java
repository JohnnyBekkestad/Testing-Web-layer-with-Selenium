package com.bekkestad.examples.tutorial;

import java.io.File;
import java.net.URL;

import junit.framework.Assert;

import org.jboss.arquillian.container.test.api.Deployment;
import org.jboss.arquillian.container.test.api.RunAsClient;
import org.jboss.arquillian.drone.api.annotation.Drone;
import org.jboss.arquillian.junit.Arquillian;
import org.jboss.arquillian.junit.InSequence;
import org.jboss.arquillian.test.api.ArquillianResource;
import org.jboss.shrinkwrap.api.Archive;
import org.jboss.shrinkwrap.api.ShrinkWrap;
import org.jboss.shrinkwrap.api.asset.EmptyAsset;
import org.jboss.shrinkwrap.api.asset.StringAsset;
import org.jboss.shrinkwrap.api.spec.WebArchive;
import org.junit.Test;
import org.junit.runner.RunWith;

import com.thoughtworks.selenium.DefaultSelenium;

@RunWith(Arquillian.class)
public class SecurityControllerTest {

	private static final String WEBAPP_SRC = "src/main/webapp";
	
	@Deployment(testable=false)
	public static Archive<?> createDeployment(){		
		return ShrinkWrap.create(WebArchive.class, "login.war")
				.addClasses(UserEntity.class, UserDAO.class, UserRepository.class, UserRepositoryImpl.class,
						UserService.class, UserServiceImpl.class, Credentials.class, SecurityController.class)
				.addAsResource("test-persistence.xml", "META-INF/persistence.xml")
				.addAsResource("test-import.sql", "import.sql")
				.addAsWebInfResource("jbossas-ds.xml")				
				.addAsWebResource(new File(WEBAPP_SRC, "login.xhtml"))
				.addAsWebResource(new File(WEBAPP_SRC, "home.xhtml"))
				.addAsWebInfResource(EmptyAsset.INSTANCE, "beans.xml")
				.addAsWebInfResource(
						new StringAsset("<faces-config version=\"2.0\"/>"),"faces-config.xml");
	}	
	
	@Drone
	DefaultSelenium browser;
	
	@Test
	@InSequence(1)
	@RunAsClient
	public void should_be_able_to_login(@ArquillianResource	URL deploymentUrl){		
		browser.open(deploymentUrl + "login.jsf");
		
		browser.type("id=loginForm:username", "demo");
		browser.type("id=loginForm:password", "password");
		browser.click("id=loginForm:login");
		browser.waitForPageToLoad("15000");
		
		Assert.assertTrue("User should be logged in!", 
				browser.isElementPresent("xpath=//li[contains(text(), 'Welcome')]"));
		Assert.assertTrue("Username should be shown!", 
				browser.isElementPresent("xpath=//h1[contains(text(), 'You are signed in as demo.')]"));
	}				
}
