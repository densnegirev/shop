package frontend;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.*;
import main.Globals;
import main.UserProfile;

public class SignInServlet extends HttpServlet {
	public void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		response.setCharacterEncoding(Globals.ENCODING);
		response.setStatus(HttpServletResponse.SC_NOT_FOUND);
		response.sendError(404);

		System.out.println("SignIn GET");
	}

	public void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		String login = request.getParameter("login");
		String password = request.getParameter("password");
		HttpSession session = request.getSession();
		UserProfile user = Globals.ACCOUNT_SERVICE.getUser(login);

		if (user != null && user.getPassword().equals(password)) {
			Globals.ACCOUNT_SERVICE.addSessions(session.getId(), user);
		}

		response.sendRedirect("/index");

		System.out.println("SignIn POST");
	}
}
