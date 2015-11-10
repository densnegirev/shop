package frontend;

import main.AccountService;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

public class SignOutServlet extends HttpServlet {
	private AccountService accountService;

	public SignOutServlet(AccountService accountService) {
		this.accountService = accountService;
	}

	public void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		accountService.removeSessions(request.getSession().getId());
		response.sendRedirect("/index");
	}
}
