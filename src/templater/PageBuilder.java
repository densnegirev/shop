package templater;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import main.Globals;
import main.UserGroup;
import main.UserProfile;

public class PageBuilder {
	private String title;
	private String content;
	private HttpServletRequest request;
	private HttpServletResponse response;

	public PageBuilder(HttpServletRequest request, HttpServletResponse response) {
		title = "";
		content = "";
		this.request = request;
		this.response = response;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public void setContent(String content) {
		this.content = content;
	}

	public void buildPage() throws ServletException, IOException {
		HttpSession session = request.getSession();
		Map<String, Object> pageVariables = new HashMap<>();
		String header;

		if (Globals.ACCOUNT_SERVICE.getSessions(session.getId()) != null) {
			UserProfile up = Globals.ACCOUNT_SERVICE.getSessions(session.getId());
			UserGroup ug = Globals.DB_SERVICE.getGroup(up.getGroupID());

			pageVariables.put("USERNAME", up.getLogin());
			pageVariables.put("USERGROUP", ug.getName());
			pageVariables.put("USERGROUPCOLOR", ug.getColor());
			header = PageGenerator.getPage("server_tpl/include/user_panel.inc", pageVariables);
		} else {
			header = PageGenerator.getPage("server_tpl/include/login_panel.inc", pageVariables);
		}

		pageVariables.put("TITLE", title);
		pageVariables.put("HEADER", header);
		pageVariables.put("CONTENT", content);
		response.setContentType("text/html");
		response.setCharacterEncoding(Globals.ENCODING);
		response.getWriter().println(PageGenerator.getPage("server_tpl/index.html", pageVariables));
		response.setStatus(HttpServletResponse.SC_OK);
	}
}
