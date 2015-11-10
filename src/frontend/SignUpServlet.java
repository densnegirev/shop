package frontend;

import main.AccountService;
import main.UserProfile;
import templater.PageGenerator;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

public class SignUpServlet extends HttpServlet {
	private AccountService accountService;

	public SignUpServlet(AccountService accountService) {
		this.accountService = accountService;
	}

	public void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		Map<String, Object> pageVariables = new HashMap<>();
		String content = PageGenerator.getPage("server_tpl/include/signup_panel.inc", pageVariables);

		pageVariables.put("CONTENT", content);
		pageVariables.put("TITLE", "Registration");
		pageVariables.put("HEADER", " ");
		response.setCharacterEncoding("UTF-8");
		response.getWriter().println(PageGenerator.getPage("server_tpl/index.html", pageVariables));
		response.setStatus(HttpServletResponse.SC_OK);
	}

	public void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		String login = request.getParameter("login");
		String password = request.getParameter("password");
		String email =  request.getParameter("email");
		String familiya = request.getParameter("familiya");
		String imya = request.getParameter("imya");
		String otchestvo = request.getParameter("otchestvo");
		String phone = request.getParameter("phone");
		String address = request.getParameter("address");
		Map<String, Object> pageVariables = new HashMap<>();
		UserProfile user = new UserProfile(login, password, email, familiya, imya, otchestvo, phone, address);

		if (accountService.addUser(login, user)) {
			//pageVariables.put("status", "OK");

			System.out.println("REG: " + login + " " + password);
		}
		/*
		else {
			pageVariables.put("status", "Exist");
		}
		*/
		response.sendRedirect("/index");
	}
}
