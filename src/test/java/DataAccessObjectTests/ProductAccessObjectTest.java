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
	
	private static ArrayList<Product> products;
	private Product product;
	
	@BeforeEach
	public void beforeEach() {
		
		products = new ArrayList<Product>();
		
		products.add(new Product("123", "Boots", 99.99, "Big red boots", 66, "12A4"));
		products.add(new Product("124", "Jacket", 49.99, "Blue jacket", 55, "12A6"));
		products.add(new Product("125", "Jeans", 69.99, "Red jeans", 15, "12A7"));
		products.add(new Product("126", "Hat", 69.99, "Big hat", 12, "12A9"));
		
	}
	
	@AfterEach
	public void afterEach() {
		
		for (int i = 0; i < products.size(); i++) {
			productDAO.removeProduct(products.get(i));
		}
		
	}
	
	@Test
	@Order(1)
	@DisplayName("Test for adding a product")
	public void testAdd() {
		assertTrue(productDAO.addProduct(products.get(0)), "createTilasto() ei toimi");	
		assertTrue((product = (Product) productDAO.getProduct(products.get(0).getId())) != null, "readTilasto() ei toimi");
		
		assertEquals("123", product.getId(), "Wrong ID");
		assertEquals("Boots", product.getName(), "Wrong name");
		assertEquals(99.99, product.getPrice(), "Wrong price");
		assertEquals("Big red boots", product.getDescription(), "Wrong description");
		assertEquals(66, product.getStock(), "Wrong stock");
		assertEquals(11.5, product.getLocation(), "Wrong location");	
	}
	
	@Test
	@Order(2)
	@DisplayName("Searching a non-existent product should return null")
	public void testSearchNull() {
		assertTrue(productDAO.addProduct(products.get(0)), "Creating a product failed");
		assertTrue((product = (Product) productDAO.getProduct(products.get(1).getId())) == null, "Non-existent product was not null");			
	}
	
	@Test
	@DisplayName("Identical products should not exist")
	@Order(3)
	public void testAddIdentical() {
		assertTrue(productDAO.addProduct(products.get(0)), "addProduct() - adding a product does not work");
		assertFalse(productDAO.addProduct(products.get(0)), "addProduct(): The same product can be added twice");
	}
	
	@Test
	@DisplayName("getProducts() should return all products")
	@Order(4)
	public void testSearchAll() {
		
		for (int i = 0; i < products.size(); i++) {
			productDAO.addProduct(products.get(i));
		}
		
		List t = productDAO.getProducts();
		
		for (int i = 0; i < products.size(); i++) {
			assertEquals(((Product) t.get(i)).getId(), products.get(i).getId(),"Wrong product ID");
			assertEquals(((Product) t.get(i)).getName(), products.get(i).getName(),"Wrong product name");
			assertEquals(((Product) t.get(i)).getPrice(), products.get(i).getPrice(),"Wrong product price");
			assertEquals(((Product) t.get(i)).getDescription(), products.get(i).getDescription(),"Wrong product description");
			assertEquals(((Product) t.get(i)).getStock(), products.get(i).getStock(),"Wrong product stock");
			assertEquals(((Product) t.get(i)).getLocation(), products.get(i).getLocation(),"Wrong product location");
			
		}			
	}
	
	@Test
	@DisplayName("Test for deleting a product")
	@Order(5)
	public void testRemove() {
		assertTrue(productDAO.addProduct(products.get(0)), "addProduct() - adding a product does not work.");
		assertTrue(productDAO.removeProduct(products.get(0)), "removeProduct() - removing a product does not work.");
		assertTrue(productDAO.getProduct(products.get(0).getId()) == null, "deleteProduct() - a deleted product was still in the database");
	}
	
	@Test
	@DisplayName("Removing a non-existent product should not work")
	@Order(6)
	public void testRemoveNull() {
		assertFalse(productDAO.removeProduct(products.get(0)), "removeProduct() - Non-existent product was removed");
	}
	
	@Test
	@DisplayName("Editing the details of a product should work")
	@Order(7)
	public void testEdit() {
		assertTrue(productDAO.addProduct(product), "addProduct() - adding a product does not work.");

		products.get(0).setName("New boots");
		products.get(0).setDescription("Orange boots");
		
		assertEquals(productDAO.editProduct(products.get(0)), products.get(0), "editProduct() - editing a product failed");
		product = productDAO.getProduct(products.get(0).getId());
		assertEquals("New boots", product.getName(), "editProduct() - name of the product is wrong");
	}
	

}
