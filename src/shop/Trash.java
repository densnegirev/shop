package shop;

import java.util.HashMap;
import java.util.LinkedList;

public class Trash {
	private HashMap<Integer, LinkedList<TrashItem>> items;

	public Trash() {
		items = new HashMap<>();
	}

	public void addItem(int userId, int itemId, int amount) {
		LinkedList<TrashItem> userItems = items.get(userId);

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
		LinkedList<TrashItem> userItems = items.get(userId);

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

	public String getContent(int userId) {
		LinkedList<TrashItem> userItems = items.get(userId);

		if (userItems != null && !userItems.isEmpty()) {
			String result = "";

			for (TrashItem item : userItems) {
				result = result + item.getItemId() + " : " + item.getAmount() + "<br />";
			}

			return result;
		}
		/*
		Map<String, Object> pageVariables = new HashMap<>();
		ArrayList<Item> items = Globals.DB_SERVICE.getTrashItems(page, cnt);
		String rows = "";

		for (int i = 0; i < items.size(); ++i) {
			Item item = items.get(i);

			pageVariables.put("image", "images/items/" + item.getId() + ".png");
			pageVariables.put("fabricName", item.getFabricName());
			pageVariables.put("fabricCountry", item.getFabricCountry());
			pageVariables.put("type", item.getType());
			pageVariables.put("format", item.getFormat());
			pageVariables.put("resolution", item.getResolution());
			pageVariables.put("model", item.getModel());
			pageVariables.put("diagonal", item.getDiagonal());
			pageVariables.put("price", item.getPrice());

			rows += PageGenerator.getPage("server_tpl/include/products_table_row.inc", pageVariables);
		}

		pageVariables.put("rows", rows);

		return PageGenerator.getPage("server_tpl/include/products_table.inc", pageVariables);
		*/

		return "Нет товаров";
	}
}
