package frontend;

import formvalidators.FormValidator;
import main.AccountService;
import main.Globals;
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
	private String errors;

	public ProfileServlet(AccountService accountService) {
		this.accountService = accountService;
	}

	public void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		Map<String, Object> pageVariables = new HashMap<>();
		HttpSession session = request.getSession();
		String content;
		String header;

		response.setCharacterEncoding("UTF-8");
		pageVariables.put("TITLE", Globals.SITE_TITLE + " | Профиль");

		if (accountService.getSessions(session.getId()) != null) {
			UserProfile up = accountService.getSessions(session.getId());

			pageVariables.put("USERNAME", up.getLogin());
			pageVariables.put("password", up.getPassword());
			pageVariables.put("password_again", up.getPassword());
			pageVariables.put("familiya", up.getFamiliya());
			pageVariables.put("imya", up.getImya());
			pageVariables.put("otchestvo", up.getOtchestvo());
			pageVariables.put("email", up.getEmail());
			pageVariables.put("address", up.getAddress());
			pageVariables.put("phone", up.getPhone());
			pageVariables.put("errors", "");

			header = PageGenerator.getPage("server_tpl/include/user_panel.inc", pageVariables);
			content = PageGenerator.getPage("server_tpl/include/profile_panel.inc", pageVariables);

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
		String passwordAgain = request.getParameter("password_again");
		String familiya = request.getParameter("familiya");
		String imya = request.getParameter("imya");
		String otchestvo = request.getParameter("otchestvo");
		String email =  request.getParameter("email");
		String address = request.getParameter("address");
		String phone = request.getParameter("phone");
		HttpSession session = request.getSession();
		FormValidator[] validators = new FormValidator[] {
				FormValidator.create(FormValidator.Types.PASSWORD, password, "Пароль должен быть длинной не менее 8 символов")
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
			accountService.updateUser(session.getId(), password, familiya, imya, otchestvo, email, address, phone);
			response.sendRedirect("/index");
		} else {
			Map<String, Object> pageVariables = new HashMap<>();
			UserProfile up = accountService.getSessions(session.getId());

			errors = "<ul>" + errors + "</ul>";

			pageVariables.put("USERNAME", up.getLogin());
			pageVariables.put("password", up.getPassword());
			pageVariables.put("password_again", up.getPassword());
			pageVariables.put("familiya", up.getFamiliya());
			pageVariables.put("imya", up.getImya());
			pageVariables.put("otchestvo", up.getOtchestvo());
			pageVariables.put("email", up.getEmail());
			pageVariables.put("address", up.getAddress());
			pageVariables.put("phone", up.getPhone());
			pageVariables.put("errors", errors);

			String header = PageGenerator.getPage("server_tpl/include/user_panel.inc", pageVariables);
			String content = PageGenerator.getPage("server_tpl/include/profile_panel.inc", pageVariables);

			pageVariables.put("CONTENT", content);
			pageVariables.put("TITLE", Globals.SITE_TITLE + " | Профиль");
			pageVariables.put("HEADER", header);

			response.setCharacterEncoding("UTF-8");
			response.getWriter().println(PageGenerator.getPage("server_tpl/index.html", pageVariables));
			response.setStatus(HttpServletResponse.SC_OK);
		}
	}
}
