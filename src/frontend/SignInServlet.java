package frontend;

import main.AccountService;
import main.UserProfile;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.*;

public class SignInServlet extends HttpServlet {
	private AccountService accountService;

	public SignInServlet(AccountService accountService) {
		this.accountService = accountService;
	}

	public void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		response.setCharacterEncoding("UTF-8");
		response.setStatus(HttpServletResponse.SC_NOT_FOUND);
		response.sendError(404);

		System.out.println("SignIn GET");
	}

	public void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		String login = request.getParameter("login");
		String password = request.getParameter("password");
		HttpSession session = request.getSession();
		UserProfile user = accountService.getUser(login);

		if (user != null && user.getPassword().equals(password)) {
			accountService.addSessions(session.getId(), user);
		}

		response.sendRedirect("/index");

		System.out.println("SignIn POST");
	}
}
