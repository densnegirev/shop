package frontend;

import FormValidators.FormValidator;
import main.AccountService;
import main.Globals;
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
	private String errors;

	public SignUpServlet(AccountService accountService) {
		this.accountService = accountService;
		this.errors = "";
	}

	public void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		Map<String, Object> pageVariables = new HashMap<>();

		pageVariables.put("errors", "");

		String content = PageGenerator.getPage("server_tpl/include/signup_panel.inc", pageVariables);

		pageVariables.put("CONTENT", content);
		pageVariables.put("TITLE", Globals.SITE_TITLE + " | Регистрация");
		pageVariables.put("HEADER", "");
		response.setCharacterEncoding("UTF-8");
		response.getWriter().println(PageGenerator.getPage("server_tpl/index.html", pageVariables));
		response.setStatus(HttpServletResponse.SC_OK);
	}

	public void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		String login = request.getParameter("login");
		String password = request.getParameter("password");
		String passwordAgain = request.getParameter("password_again");
		String email =  request.getParameter("email");
		String familiya = request.getParameter("familiya");
		String imya = request.getParameter("imya");
		String otchestvo = request.getParameter("otchestvo");
		String phone = request.getParameter("phone");
		String address = request.getParameter("address");
		UserProfile user = new UserProfile(login, password, email, familiya, imya, otchestvo, phone, address);
		FormValidator[] validators = new FormValidator[] {
				FormValidator.create(FormValidator.Types.LOGIN, login, "Логин должен состоять хотя бы из одного символа"),
				FormValidator.create(FormValidator.Types.PASSWORD, password, "Пароль должен быть не менее 8 символов")
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
			accountService.addUser(login, user);
			response.sendRedirect("/index");

			System.out.println("Reg: " + login + " " + password);
		} else {
			Map<String, Object> pageVariables = new HashMap<>();

			errors = "<ul>" + errors + "</ul>";

			pageVariables.put("errors", errors);

			String content = PageGenerator.getPage("server_tpl/include/signup_panel.inc", pageVariables);

			pageVariables.put("CONTENT", content);
			pageVariables.put("TITLE", Globals.SITE_TITLE + " | Регистрация");
			pageVariables.put("HEADER", "");

			response.setCharacterEncoding("UTF-8");
			response.getWriter().println(PageGenerator.getPage("server_tpl/index.html", pageVariables));
			response.setStatus(HttpServletResponse.SC_OK);
		}
	}
}
