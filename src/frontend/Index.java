package frontend;

import main.AccountService;
import main.Globals;
import main.UserProfile;
import product.Item;
import templater.PageGenerator;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class Index extends HttpServlet {
	private AccountService accountService;

	public Index(AccountService accountService) {
		this.accountService = accountService;
	}

	public void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		HttpSession session = request.getSession();
		Map<String, Object> pageVariables = new HashMap<>();
		String rows = "";
		String header;

		ArrayList<Item> items = Globals.DB_SERVICE.getItems(0, 0);

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

		String content = PageGenerator.getPage("server_tpl/include/products_table.inc", pageVariables);

		response.setContentType("text/html");
		response.setCharacterEncoding(Globals.ENCODING);
		pageVariables.put("TITLE", Globals.SITE_TITLE + " | Главная");
		pageVariables.put("CONTENT", content);

		if (accountService.getSessions(session.getId()) != null) {
			Map<String, Object> pv = new HashMap<>();
			UserProfile up = accountService.getSessions(session.getId());

			pv.put("USERNAME", up.getLogin());
			header = PageGenerator.getPage("server_tpl/include/user_panel.inc", pv);
		} else {
			header = PageGenerator.getPage("server_tpl/include/login_panel.inc", pageVariables);
		}
		
		pageVariables.put("HEADER", header);
		response.getWriter().println(PageGenerator.getPage("server_tpl/index.html", pageVariables));
		response.setStatus(HttpServletResponse.SC_OK);
	}
}
