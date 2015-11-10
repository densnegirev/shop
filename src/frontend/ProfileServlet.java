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

public class ProfileServlet extends HttpServlet {
	private AccountService accountService;

	public ProfileServlet(AccountService accountService) {
		this.accountService = accountService;
	}

	public void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		Map<String, Object> pageVariables = new HashMap<>();
		HttpSession session = request.getSession();
		String content;
		String title = "Profile";
		String header;

		response.setCharacterEncoding("UTF-8");
		pageVariables.put("TITLE", title);

		if (accountService.getSessions(session.getId()) != null) {
			Map<String, Object> pv = new HashMap<>();
			UserProfile up = accountService.getSessions(session.getId());

			pv.put("USERNAME", up.getImya() + " " + up.getFamiliya());
			pv.put("password", up.getPassword());
			pv.put("email", up.getEmail());
			pv.put("familiya", up.getFamiliya());
			pv.put("imya", up.getImya());
			pv.put("otchestvo", up.getOtchestvo());
			pv.put("address", up.getAddress());
			pv.put("phone", up.getPhone());

			header = PageGenerator.getPage("server_tpl/include/user_panel.inc", pv);
			content = PageGenerator.getPage("server_tpl/include/profile_panel.inc", pv);
			pageVariables.put("HEADER", header);
			pageVariables.put("CONTENT", content);
			response.getWriter().println(PageGenerator.getPage("server_tpl/index.html", pageVariables));
			response.setStatus(HttpServletResponse.SC_OK);
		} else {
			response.sendRedirect("/index");
		}
	}

	public void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		String password = request.getParameter("password");
		String email =  request.getParameter("email");
		String familiya = request.getParameter("familiya");
		String imya = request.getParameter("imya");
		String otchestvo = request.getParameter("otchestvo");
		String phone = request.getParameter("phone");
		String address = request.getParameter("address");
		HttpSession session = request.getSession();

		accountService.updateUser(session.getId(), password, email, familiya, imya, otchestvo, phone, address);
		response.sendRedirect("/index");
	}
}
