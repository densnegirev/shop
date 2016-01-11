package shop;

import java.util.ArrayList;

public class Order {
	private int userId;
	private String orderDate;
	private String deliveryDate;
	private ArrayList<TrashItem> items;

	public Order(int userId, String orderDate, String deliveryDate, ArrayList<TrashItem> items) {
		this.userId = userId;
		this.orderDate = orderDate;
		this.deliveryDate = deliveryDate;
		this.items = items;
	}

	public int getUserId() {
		return userId;
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
