package DataAccessObjectTests;


import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.ArrayList;
import java.util.List;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;

import r13.javafx.Varastonhallinta.models.Product;
import r13.javafx.Varastonhallinta.models.dao.ProductAccessObject;



public class ProductAccessObjectTest {
	
	private ProductAccessObject productDAO = new ProductAccessObject();
	

	private Product product;
	private Product productToRemove;
	private Product newProduct;
	private boolean productState = false;
	private String productId;
	
	@BeforeEach
	public void beforeEach() {
		

		
		product = new Product("123", "Boots", 99.99, "Big red boots", 66, "BBBBB");

		
	}
	
	@AfterEach
	public void afterEach() {
		

		productDAO.removeProduct(productId);

		
	}
	
	@Test
	@Order(1)
	@DisplayName("Test for adding a product")
	public void testAdd() {
		Product newProduct = productDAO.addProduct(product);
		
		boolean productState = false;
		if(newProduct != null)
			productState = true;
		assertTrue(productState, "createTilasto() ei toimi");	
		assertTrue((product = (Product) productDAO.getProduct(newProduct.getId())) != null, "readTilasto() ei toimi");
		
		
		assertEquals("Boots", product.getName(), "Wrong name");
		assertEquals(99.99, product.getPrice(), "Wrong price");
		assertEquals("Big red boots", product.getDescription(), "Wrong description");
		assertEquals(66, product.getStock(), "Wrong stock");
		assertEquals("BBBBB", product.getLocation(), "Wrong location");	
		productId = newProduct.getId();
	}
	
	
	/*
	@Test
	@DisplayName("Identical products should not exist")
	@Order(2)
	public void testAddIdentical() {
		
		Product newProduct = productDAO.addProduct(product);
		productState = false;
		if(newProduct != null)
			productState = true;
		assertTrue(productState, "addProduct() - adding a product does not work");
		
		Product newProduct2 = productDAO.addProduct(product);
		productState = false;
		if(newProduct2 != null)
			productState = true;
		assertFalse(productState, "addProduct(): The same product can be added twice");
		
		
		
	}
	
	
	/*
	@Test
	@DisplayName("Test for deleting a product")
	@Order(3)
	public void testRemove() {
		assertTrue(productDAO.addProduct(products.get(0)), "addProduct() - adding a product does not work.");
		assertTrue(productDAO.removeProduct(products.get(0)), "removeProduct() - removing a product does not work.");
		assertTrue(productDAO.getProduct(products.get(0).getId()) == null, "deleteProduct() - a deleted product was still in the database");
	}
	
	@Test
	@DisplayName("Removing a non-existent product should not work")
	@Order(5)
	public void testRemoveNull() {
		assertFalse(productDAO.removeProduct(products.get(0)), "removeProduct() - Non-existent product was removed");
	}
	
	@Test
	@DisplayName("Editing the details of a product should work")
	@Order(6)
	public void testEdit() {
		assertTrue(productDAO.addProduct(product), "addProduct() - adding a product does not work.");

		products.get(0).setName("New boots");
		products.get(0).setDescription("Orange boots");
		
		assertEquals(productDAO.editProduct(products.get(0)), products.get(0), "editProduct() - editing a product failed");
		product = productDAO.getProduct(products.get(0).getId());
		assertEquals("New boots", product.getName(), "editProduct() - name of the product is wrong");
	}
	*/
	

}
