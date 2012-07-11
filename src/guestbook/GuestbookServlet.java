package guestbook;
import java.io.IOException;

import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.google.appengine.api.users.User;
import com.google.appengine.api.users.UserService;
import com.google.appengine.api.users.UserServiceFactory;

/*
 * getting started code:
@SuppressWarnings("serial")
public class GuestbookServlet extends HttpServlet {
	public void doGet(HttpServletRequest req, HttpServletResponse resp) throws IOException {
		resp.setContentType("text/plain");
		resp.getWriter().println("Hello, world");
	}
}
*/
public class GuestbookServlet extends HttpServlet
{
	public void doGet(HttpServletRequest req, HttpServletResponse resp)
		throws IOException
		{
			// use the Users API to check if the user is signed in 
			// with a Google Account.			
			UserService userService = UserServiceFactory.getUserService();
			User user = userService.getCurrentUser();
			
			if (user != null)
			{
				
				resp.setContentType("text/plain");
				resp.getWriter().println("HELLO, " + user.getNickname());
				/*
				resp.sendRedirect(userService.createLogoutURL(req.getRequestURI()));*/
			}
			else
			{
				// the user hasn't signed in, redirect to the Google Accounts 
				//sign-in screen
				resp.sendRedirect(userService.createLoginURL(req.getRequestURI()));
//				resp.sendRedirect(userService.createLogoutURL("www.google.com"));
			}
		}
}