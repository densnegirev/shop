package shop.admin;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import main.Globals;
import main.UserProfile;
import templater.PageGenerator;

public class UsersPanel {
	public String getContent() {
		Map<String, Object> pv = new HashMap<>();
		ArrayList<UserProfile> users = Globals.DB_SERVICE.getUsers();
		String rows = "";

		for (UserProfile user : users) {
			pv.put("login", user.getLogin());
			pv.put("familiya", user.getFamiliya());
			pv.put("imya", user.getImya());
			pv.put("otchestvo", user.getOtchestvo());
			pv.put("email", user.getEmail());
			pv.put("address", user.getAddress());
			pv.put("phone", user.getPhone());

			rows += PageGenerator.getPage("server_tpl/include/users_table_row.inc", pv);
		}

		pv.put("rows", rows);

		return PageGenerator.getPage("server_tpl/include/users_table.inc", pv);
	}
}
