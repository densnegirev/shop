package shop;

import java.util.ArrayList;

public class Order {
	private String orderDate;
	private String deliveryDate;
	private ArrayList<TrashItem> items;

	public Order(String orderDate, String deliveryDate, ArrayList<TrashItem> items) {
		this.orderDate = orderDate;
		this.deliveryDate = deliveryDate;
		this.items = items;
	}

	public String getOrderDate() {
		return orderDate;
	}

	public String getDeliveryDate() {
		return deliveryDate;
	}

	public ArrayList<TrashItem> getItems() {
		return items;
	}
}
