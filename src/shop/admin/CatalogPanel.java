package shop.admin;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import main.Globals;
import shop.Item;
import templater.PageGenerator;

public class CatalogPanel {
	public String getContent() {

		Map<String, Object> pageVariables = new HashMap<>();
		ArrayList<Item> items = Globals.DB_SERVICE.getItems();
		String rows = "";

		for (int i = 0; i < items.size(); ++i) {
			Item item = items.get(i);

			pageVariables.put("image", "images/items/" + item.getId() + ".png");
			pageVariables.put("itemId", item.getId());
			pageVariables.put("fabricName", item.getFabricName());
			pageVariables.put("fabricCountry", item.getFabricCountry());
			pageVariables.put("type", item.getType());
			pageVariables.put("hdFormat", item.getFormat());
			pageVariables.put("resolution", item.getResolution());
			pageVariables.put("model", item.getModel());
			pageVariables.put("diagonal", item.getDiagonal());
			pageVariables.put("price", item.getPrice());
			pageVariables.put("count", item.getCount());

			rows += PageGenerator.getPage("server_tpl/include/catalog_table_row.inc", pageVariables);
		}

		pageVariables.put("rows", rows);

		return PageGenerator.getPage("server_tpl/include/catalog_table.inc", pageVariables);
	}
}
