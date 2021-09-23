package r13.javafx.Varastonhallinta.models;

//import javafx.beans.property.SimpleStringProperty; tarvitaan että voidaan muokata kenttiä, setText ei kuitenkaan toiminut tän kanssa

public class Order {

	private String orderDate, orderSize, orderNumber;


	public Order(String orderNumber, String orderDate, String orderSize) {
		
		this.orderNumber = orderNumber;
		this.orderDate = orderDate;
		this.orderSize = orderSize;
	}

	public String getOrderNumber() {
		return orderNumber;
	}

	public void setOrderNumber(String orderNumber) {
		this.orderNumber = orderNumber;
	}

	public String getOrderDate() {
		return orderDate;
	}

	public void setOrderDate(String orderDate) {
		this.orderDate = orderDate;
	}

	public String getOrderSize() {
		return orderSize;
	}

	public void setOrderSize(String orderSize) {
		this.orderSize = orderSize;
	}
	
	public String toString()	{
	        return String.format("%s %s", orderNumber, orderDate);
	}

	
	
	
	
}
