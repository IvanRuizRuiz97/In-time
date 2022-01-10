package com.app.selenium;


import static org.junit.Assert.assertTrue;

import java.util.HashMap;
import java.util.Map;


import org.junit.After;
import org.junit.Before;
import org.junit.FixMethodOrder;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.MethodSorters;
import org.openqa.selenium.By;

import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;




import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.core.annotation.Order;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import com.app.siget.dominio.User;
import com.app.siget.persistencia.UserDAO;

@RunWith(SpringJUnit4ClassRunner.class)
@SpringBootTest(classes = TestIntime.class)
@FixMethodOrder(MethodSorters.NAME_ASCENDING)

public class TestIntime {
	
	private WebDriver driver;
	private Map<String, Object> vars;
	  JavascriptExecutor js;
	  
	  @Before
	  public void setUp() {
		System.setProperty("webdriver.chrome.driver", "chromedriver.exe");
	    driver = new ChromeDriver();
		js = (JavascriptExecutor) driver;
	    vars = new HashMap<String, Object>();
	  }
	  @After
	  public void tearDown() {
	    driver.quit();
	  }

	  @Test
	  @Order(10)
	  public void testALoginCorrecto() {
		  String nombre = "pedro";
		  String contrasena = "123456a.";
		  
		  driver.get("http://localhost:8080/");
		  
		  	
		    driver.findElement(By.cssSelector("#username")).clear();
		    driver.findElement(By.cssSelector("#username")).sendKeys(nombre);
		    driver.findElement(By.cssSelector("#password")).clear();
		    driver.findElement(By.cssSelector("#password")).sendKeys(contrasena);
		    driver.findElement(By.cssSelector("#btnLogin")).click();
		  
		    try {
				Thread.sleep(2000);
			     } catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			     }
		    
		    WebElement confirm = driver.findElement(By.cssSelector("body > div.col-md-6.col-sm-8.col-xl-6.col-lg-4.boxHeader > h1"));
		    
		    boolean compro = confirm.getText().equals("USUARIO");
		    assertTrue(compro);
		 
	  }
	  
	@Test
	  @Order(20)
	  public void testBLoginIncorrecto() {
		String nombre = "juan";
		  String contrasena = "123456asdas";
		  
		  driver.get("http://localhost:8080/");
		  
		  	
		    driver.findElement(By.cssSelector("#username")).clear();
		    driver.findElement(By.cssSelector("#username")).sendKeys(nombre);
		    driver.findElement(By.cssSelector("#password")).clear();
		    driver.findElement(By.cssSelector("#password")).sendKeys(contrasena);
		    driver.findElement(By.cssSelector("#btnLogin")).click();
		  
		    try {
				Thread.sleep(2000);
			     } catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			     }
		    
		    WebElement confirmpwd = driver.findElement(By.cssSelector("#password"));
		    WebElement confirmname = driver.findElement(By.cssSelector("#username"));
		    
		    String bgpwd = confirmpwd.getCssValue("background-color");
		    
		    String bgname = confirmname.getCssValue("background-color");
			assertTrue(bgpwd != "#d8d8d8" || bgname  != "#d8d8d8");		  
		 
	  }
	@Test
	  @Order(30)
	  public void testCRegistroCorrecto() {
		String nameRegistro = "pruebaRegistro"; 
		String pwdRegistro = "123456a.";
		String mailRegistro = "pruebaRegistro@mail.com";
		
		
		
		User u = UserDAO.findUser(nameRegistro);
		if( u != null) {
			UserDAO.eliminar(u, false);
		}
		
			driver.get("http://localhost:8080/register.html");
			
			driver.findElement(By.cssSelector("#username")).clear();
		    driver.findElement(By.cssSelector("#username")).sendKeys(nameRegistro);
		    driver.findElement(By.cssSelector("#email")).clear();
		    driver.findElement(By.cssSelector("#email")).sendKeys(mailRegistro);
		    driver.findElement(By.cssSelector("#pwd1")).clear();
		    driver.findElement(By.cssSelector("#pwd1")).sendKeys(pwdRegistro);
		    driver.findElement(By.cssSelector("#pwd2")).clear();
		    driver.findElement(By.cssSelector("#pwd2")).sendKeys(pwdRegistro);
		    
		    driver.findElement(By.cssSelector("#btnregister")).click();
		    

		    try {
				Thread.sleep(1000);
			     } catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			     }
		    User uFinal = UserDAO.findUser(nameRegistro);
		    assertTrue(uFinal != null);
		 
		
		
	}
	
	  
	  
	  
	  
}
