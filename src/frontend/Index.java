package frontend;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import main.Globals;
import shop.Catalog;
import templater.PageBuilder;

public class Index extends HttpServlet {
	public void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		PageBuilder pageBuilder = new PageBuilder(request, response);
		Catalog catalog = new Catalog();

		pageBuilder.setTitle(Globals.SITE_TITLE + " | Главная");
		pageBuilder.setContent(catalog.getContent());
		pageBuilder.buildPage();
	}
}
