package shop;

import main.Globals;
import templater.PageGenerator;

import java.util.HashMap;
import java.util.LinkedList;
import java.util.Map;

public class Trash {
	private HashMap<Integer, LinkedList<TrashItem>> items;

	public Trash() {
		items = new HashMap<>();
	}

	public void addItem(int userId, int itemId, int amount) {
		LinkedList<TrashItem> userItems = getUserItems(userId);

		if (userItems == null) {
			userItems = new LinkedList<>();

			items.put(userId, userItems);
		}

		for (TrashItem item : userItems) {
			if (item.getItemId() == itemId) {
				item.setAmount(item.getAmount() + amount);

				return;
			}
		}

		userItems.add(new TrashItem(itemId, amount));
	}

	public void deleteItem(int userId, int itemId) {
		LinkedList<TrashItem> userItems = getUserItems(userId);

		if (userItems == null) {
			return;
		}

		for (TrashItem item : userItems) {
			if (item.getItemId() == itemId) {
				userItems.remove(item);

				break;
			}
		}
	}

	public int getItemAmount(int userId, int itemId) {
		LinkedList<TrashItem> userItems = getUserItems(userId);

		if (userItems == null) {
			return 0;
		}

		for (TrashItem item : userItems) {
			if (item.getItemId() == itemId) {
				return item.getAmount();
			}
		}

		return 0;
	}

	public LinkedList<TrashItem> getUserItems(int userId) {
		return items.get(userId);
	}

	public String getContent(int userId) {
		LinkedList<TrashItem> userItems = getUserItems(userId);
		Map<String, Object> pv = new HashMap<>();

		if (userItems != null && !userItems.isEmpty()) {
			String rows = "";
			int totalSum = 0;

			for (TrashItem trashItem : userItems) {
				Item item = Globals.DB_SERVICE.getItem(trashItem.getItemId());

				pv.put("image", "images/items/" + item.getId() + ".png");
				pv.put("model", item.getModel());
				pv.put("amount", trashItem.getAmount());
				pv.put("price", item.getPrice());
				pv.put("itemId", item.getId());

				rows += PageGenerator.getPage("server_tpl/include/trash_table_row.inc", pv);
				totalSum += item.getPrice() * trashItem.getAmount();
			}

			rows += "<tr><td class=\"right\" colspan=\"4\">Итого: " + totalSum + "&nbsp;</td><td></td></tr>";

			pv.put("rows", rows);

			return PageGenerator.getPage("server_tpl/include/trash_table.inc", pv);
		}

		return "Нет товаров";
	}
}
