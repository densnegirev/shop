package shop;

import java.util.ArrayList;
import main.Globals;

public class PurchaseHistory {
	public String getContent(int userId) {
		String content = "";
		ArrayList<Order> userOrders = Globals.DB_SERVICE.getUserOrders(userId);

		for (Order order : userOrders) {
			content += order.getOrderDate() + ", " + order.getDeliveryDate() + "<br />";

			for (TrashItem item : order.getItems()) {
				content += "Товар: " + item.getItemId() + ", " + item.getAmount() + "<br />";
			}
		}

		return content;
	}
}
