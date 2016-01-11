package shop.admin;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import main.Globals;
import shop.Item;
import shop.Order;
import shop.TrashItem;
import templater.PageGenerator;

public class PurchasesPanel {
	public String getContent() {
		String rows = "";
		Map<String, Object> pv = new HashMap<>();
		ArrayList<Order> userOrders = Globals.DB_SERVICE.getOrders();

		for (Order order : userOrders) {
			int totalSum = 0;

			pv.put("login", Globals.DB_SERVICE.getUser(order.getUserId()).getLogin());
			pv.put("orderDate", order.getOrderDate());
			pv.put("deliveryDate", order.getDeliveryDate());

			rows += PageGenerator.getPage("server_tpl/include/orders_table_row_header_2.inc", pv);

			ArrayList<TrashItem> trashItems = order.getItems();
			String itemsList = "(" + trashItems.get(0).getItemId();

			for (int i = 1; i < trashItems.size(); ++i) {
				itemsList += ", " + trashItems.get(i).getItemId();
			}

			itemsList += ")";

			ArrayList<Item> items = Globals.DB_SERVICE.getItemsFromList(itemsList);

			for (int i = 0; i < items.size(); ++i) {
				Item item = items.get(i);
				TrashItem trashItem = trashItems.get(i);

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
