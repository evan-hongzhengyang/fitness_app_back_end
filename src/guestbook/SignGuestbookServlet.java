package guestbook;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.Date;
import java.util.logging.Logger;

import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.google.appengine.api.datastore.DatastoreService;
import com.google.appengine.api.datastore.DatastoreServiceFactory;
import com.google.appengine.api.datastore.Entity;
import com.google.appengine.api.datastore.Key;
import com.google.appengine.api.datastore.KeyFactory;
import com.google.appengine.api.users.User;
import com.google.appengine.api.users.UserService;
import com.google.appengine.api.users.UserServiceFactory;

public class SignGuestbookServlet extends HttpServlet 
{
	private static final Logger log = Logger.getLogger(SignGuestbookServlet.class.getName());
	
	public void doPost(HttpServletRequest req, HttpServletResponse resp)
				throws IOException{
		UserService userService = UserServiceFactory.getUserService();
		User user=userService.getCurrentUser();				
		
//		String content = req.getParameter("content");
//		if(content == null)
//		{
//			content = "(No Greeting)";
//		}
//		if (user != null)
//		{
//			log.info("greeting posted by the user" + user.getNickname() + ":" + content);
//		} else {
//			log.info("Greeting posted anonnymously: " + content);
//		}
//		resp.sendRedirect("/guestbook.jsp");
		
		
		
		
		
		/*
		log.info("the whole request is: ");
		StringBuilder stringBuilder = new StringBuilder();
		BufferedReader bufferedReader = null;
		try
		{
			InputStream inputStream = req.getInputStream();
			if(inputStream != null)
			{
				bufferedReader = new BufferedReader(new InputStreamReader(inputStream));
				char [] charBuffer = new char[128];
				int bytesRead = -1;
				while ((bytesRead = bufferedReader.read(charBuffer)) > 0)
				{
					stringBuilder.append(charBuffer, 0, bytesRead);
				}
			} else {
				stringBuilder.append("");
			}
		} catch (IOException e){
			throw e;
		}
		log.info(stringBuilder.toString());
		 */
				
		
		// We have one entity group per Guestbook with all Greetings residing
        // in the same entity group as the Guestbook to which they belong.
        // This lets us run an ancestor query to retrieve all Greetings for a
        // given Guestbook. However, the write rate to each Guestbook should be
        // limited to ~1/second.
		String guestbookName = req.getParameter("guestbookName");		
		Key guestbookKey = KeyFactory.createKey("Guestbook", guestbookName);
		String content = req.getParameter("content");
		Date date = new Date();
		// the entity "greeting" is an entity in the key parent(ancestor)
		Entity greeting = new Entity("Greeting", guestbookKey);
		greeting.setProperty("user", user);
		greeting.setProperty("date", date);
		greeting.setProperty("content", content);
		
		DatastoreService datastore =
				DatastoreServiceFactory.getDatastoreService();
		datastore.put(greeting);
		
		
		resp.sendRedirect("/guestbook.jsp?guestbookName="
				+ guestbookName);
		
	}

}
