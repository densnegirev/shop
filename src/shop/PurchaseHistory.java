package shop;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import main.Globals;
import templater.PageGenerator;

public class PurchaseHistory {
	public String getContent(int userId) {
		String rows = "";
		Map<String, Object> pv = new HashMap<>();
		ArrayList<Order> userOrders = Globals.DB_SERVICE.getUserOrders(userId);

		for (Order order : userOrders) {
			int totalSum = 0;

			pv.put("orderDate", order.getOrderDate());
			pv.put("deliveryDate", order.getDeliveryDate());

			rows += PageGenerator.getPage("server_tpl/include/orders_table_row_header.inc", pv);

			for (TrashItem trashItem : order.getItems()) {
				Item item = Globals.DB_SERVICE.getItem(trashItem.getItemId());

				pv.put("image", "images/items/" + item.getId() + ".png");
				pv.put("model", item.getModel());
				pv.put("amount", trashItem.getAmount());
				pv.put("price", item.getPrice());

				rows += PageGenerator.getPage("server_tpl/include/orders_table_row.inc", pv);
				totalSum += item.getPrice() * trashItem.getAmount();
			}

			rows += "<tr><td class=\"right\" colspan=\"4\">Итого: " + totalSum + "&nbsp;</td></tr>";
		}

		pv.put("rows", rows);

		return PageGenerator.getPage("server_tpl/include/orders_table.inc", pv);
	}
}
