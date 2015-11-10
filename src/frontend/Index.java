package frontend;

import main.AccountService;
import main.UserProfile;
import templater.PageGenerator;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
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
		String content = "CONTENT";
		String title = "Интернет магазин ТВ";
		String header;

		response.setContentType("text/html");
		response.setCharacterEncoding("UTF-8");
		pageVariables.put("TITLE", title);
		pageVariables.put("CONTENT", content);

		if (accountService.getSessions(session.getId()) != null) {
			Map<String, Object> pv = new HashMap<>();
			UserProfile up = accountService.getSessions(session.getId());

			pv.put("USERNAME", up.getImya() + " " + up.getFamiliya());
			header = PageGenerator.getPage("server_tpl/include/user_panel.inc", pv);
		} else {
			header = PageGenerator.getPage("server_tpl/include/login_panel.inc", pageVariables);
		}
		
		pageVariables.put("HEADER",header);
		response.getWriter().println(PageGenerator.getPage("server_tpl/index.html", pageVariables));
		response.setStatus(HttpServletResponse.SC_OK);
	}
}
