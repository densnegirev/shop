package main;

import frontend.*;
import org.eclipse.jetty.server.Handler;
import org.eclipse.jetty.server.Server;
import org.eclipse.jetty.server.handler.HandlerList;
import org.eclipse.jetty.server.handler.ResourceHandler;
import org.eclipse.jetty.servlet.ServletContextHandler;
import org.eclipse.jetty.servlet.ServletHolder;
import javax.servlet.Servlet;

public class Main {
	public static void main(String[] args) throws Exception {
		int port = 8080;

		if (args.length == 1) {
			String portString = args[0];

			port = Integer.valueOf(portString);
		}

		System.out.append("Starting at port: ").append(String.valueOf(port)).append('\n');

		Servlet signIn = new SignInServlet();
		Servlet signUp = new SignUpServlet();
		Servlet logOut = new SignOutServlet();
		Servlet index = new Index();
		Servlet profile = new ProfileServlet();
		Servlet trash = new TrashServlet();
		ServletContextHandler context = new ServletContextHandler(ServletContextHandler.SESSIONS);
		
		context.addServlet(new ServletHolder(signIn), "/signin");
		context.addServlet(new ServletHolder(signUp), "/signup");
		context.addServlet(new ServletHolder(logOut), "/logout");
		context.addServlet(new ServletHolder(index), "/index");
		context.addServlet(new ServletHolder(profile), "/profile");
		context.addServlet(new ServletHolder(trash), "/trash");

		ResourceHandler resource_handler = new ResourceHandler();
		HandlerList handlers = new HandlerList();
		Server server = new Server(port);

		resource_handler.setDirectoriesListed(false);
		resource_handler.setResourceBase("public_html");
		handlers.setHandlers(new Handler[] {resource_handler, context});
		server.setHandler(handlers);
		server.start();
		server.join();
	}
}