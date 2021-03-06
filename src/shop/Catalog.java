package shop;

import main.Globals;
import templater.PageGenerator;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class Catalog {
	public String getContent() {
		Map<String, Object> pageVariables = new HashMap<>();
		ArrayList<Item> items = Globals.DB_SERVICE.getItems();
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
			pageVariables.put("itemId", item.getId());

			if (item.getCount() == 0) {
				pageVariables.put("countColor", "notAvailable");
				pageVariables.put("count", "Нет в наличии");
			} else {
				pageVariables.put("countColor", "available");
				pageVariables.put("count", "В наличии: " + item.getCount());
			}

			rows += PageGenerator.getPage("server_tpl/include/products_table_row.inc", pageVariables);
		}

		pageVariables.put("rows", rows);

		return PageGenerator.getPage("server_tpl/include/products_table.inc", pageVariables);
	}
}
