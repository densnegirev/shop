package frontend;

import main.AccountService;
import main.Globals;
import main.UserGroup;
import main.UserProfile;
import shop.Trash;
import templater.PageGenerator;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

public class TrashServlet extends HttpServlet {
	private AccountService accountService;

	public TrashServlet(AccountService accountService) {
		this.accountService = accountService;
	}

	public void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		HttpSession session = request.getSession();
		Map<String, Object> pageVariables = new HashMap<>();
		Trash trash = new Trash();
		String header;

		response.setContentType("text/html");
		response.setCharacterEncoding(Globals.ENCODING);
		pageVariables.put("TITLE", Globals.SITE_TITLE + " | Главная");
		pageVariables.put("CONTENT", trash.getContent(0, 0));

		if (accountService.getSessions(session.getId()) != null) {
			Map<String, Object> pv = new HashMap<>();
			UserProfile up = accountService.getSessions(session.getId());
			UserGroup ug = Globals.DB_SERVICE.getGroup(up.getGroupID());

			pv.put("USERNAME", up.getLogin());
			pv.put("USERGROUP", ug.getName());
			pv.put("USERGROUPCOLOR", ug.getColor());
			header = PageGenerator.getPage("server_tpl/include/user_panel.inc", pv);
		} else {
			header = PageGenerator.getPage("server_tpl/include/login_panel.inc", pageVariables);
		}

		pageVariables.put("HEADER", header);
		response.getWriter().println(PageGenerator.getPage("server_tpl/index.html", pageVariables));
		response.setStatus(HttpServletResponse.SC_OK);
	}
}
