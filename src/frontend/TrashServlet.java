package frontend;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import main.Globals;
import main.UserGroup;
import main.UserProfile;
import templater.PageGenerator;

public class TrashServlet extends HttpServlet {
	public void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		HttpSession session = request.getSession();

		if (Globals.ACCOUNT_SERVICE.getSessions(session.getId()) == null) {
			response.sendRedirect("/index");
		}

		Map<String, Object> pageVariables = new HashMap<>();
		UserProfile up = Globals.ACCOUNT_SERVICE.getSessions(session.getId());
		UserGroup ug = Globals.DB_SERVICE.getGroup(up.getGroupID());
		String action = request.getParameter("action");
		String itemId = request.getParameter("itemid");

		if (action != null && itemId != null) {
			int itemIdNum = Integer.parseInt(itemId);

			if (action.equals("add")) {
				Globals.TRASH.addItem(up.getId(), itemIdNum, 1);
			} else if (action.equals("delete")) {
				Globals.TRASH.deleteItem(up.getId(), itemIdNum);
			}

			response.sendRedirect("/index");
		}

		response.setContentType("text/html");
		response.setCharacterEncoding(Globals.ENCODING);
		pageVariables.put("TITLE", Globals.SITE_TITLE + " | Корзина");

		pageVariables.put("USERNAME", up.getLogin());
		pageVariables.put("USERGROUP", ug.getName());
		pageVariables.put("USERGROUPCOLOR", ug.getColor());

		String header = PageGenerator.getPage("server_tpl/include/user_panel.inc", pageVariables);

		pageVariables.put("CONTENT", Globals.TRASH.getContent(up.getId()));
		pageVariables.put("HEADER", header);
		response.getWriter().println(PageGenerator.getPage("server_tpl/index.html", pageVariables));
		response.setStatus(HttpServletResponse.SC_OK);
	}
	/*
	public void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

	}
	*/
}
