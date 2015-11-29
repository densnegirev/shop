package frontend;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import formvalidators.FormValidator;
import main.Globals;
import main.UserProfile;
import templater.PageBuilder;
import templater.PageGenerator;

public class SignUpServlet extends HttpServlet {
	private String errors;

	public SignUpServlet() {
		this.errors = "";
	}

	public void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		PageBuilder pageBuilder = new PageBuilder(request, response);

		pageBuilder.setTitle(Globals.SITE_TITLE + " | Регистрация");
		pageBuilder.setContent(getContent());
		pageBuilder.buildPage();

		errors = "";
	}

	public void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		String login = request.getParameter("login");
		String password = request.getParameter("password");
		String passwordAgain = request.getParameter("password_again");
		String familiya = request.getParameter("familiya");
		String imya = request.getParameter("imya");
		String otchestvo = request.getParameter("otchestvo");
		String email =  request.getParameter("email");
		String address = request.getParameter("address");
		String phone = request.getParameter("phone");
		UserProfile user = new UserProfile(login, password, familiya, imya, otchestvo, email, address, phone);
		FormValidator[] validators = new FormValidator[] {
				FormValidator.create(FormValidator.Types.LOGIN, login),
				FormValidator.create(FormValidator.Types.PASSWORD, password)
		};

		errors = "";

		for (int i = 0; i < validators.length; ++i) {
			if (!validators[i].validate()) {
				errors += "<li>" + validators[i].getErrorMsg() + "</li>";
			}
		}

		if (!password.equals(passwordAgain)) {
			errors += "<li>Пароли не совпадают</li>";
		}

		if (errors.length() == 0) {
			Globals.ACCOUNT_SERVICE.addUser(login, user);
			response.sendRedirect("/index");

			System.out.println("Reg: " + login + " " + password);
		} else {
			errors = "<ul>" + errors + "</ul>";

			response.sendRedirect("/signup");
		}
	}

	private String getContent() {
		Map<String, Object> pageVariables = new HashMap<>();

		pageVariables.put("errors", errors);

		return PageGenerator.getPage("server_tpl/include/signup_panel.inc", pageVariables);
	}
}
