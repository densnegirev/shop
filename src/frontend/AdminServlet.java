package frontend;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import main.Globals;
import main.UserProfile;
import templater.PageBuilder;

public class AdminServlet extends HttpServlet {
	public void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		HttpSession session = request.getSession();
		UserProfile up = Globals.ACCOUNT_SERVICE.getSessions(session.getId());
		PageBuilder pageBuilder = new PageBuilder(request, response);

		if (up == null || up.getGroupID() != 2) {
			response.sendRedirect("/index");

			return;
		}

		String content = "<a href=\"/admin_catalog\">Список товаров</a><br />";

		content += "<a href=\"/admin_purchases\">Список заказов</a><br />";
		content += "<a href=\"/admin_users\">Список пользователей</a><br />";

		pageBuilder.setTitle(Globals.SITE_TITLE + " | Панель управления");
		pageBuilder.setContent(content);
		pageBuilder.buildPage();
	}
}
