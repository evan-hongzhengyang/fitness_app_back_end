package guestbook;

import java.io.IOException;
import java.util.logging.Logger;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.google.appengine.api.datastore.DatastoreService;
import com.google.appengine.api.datastore.DatastoreServiceFactory;
import com.google.appengine.api.datastore.Entity;
import com.google.appengine.api.datastore.Text;

public class UploadTaskServlet extends HttpServlet {
	private static final Logger log = Logger.getLogger(SignGuestbookServlet.class.getName());

	@Override
	protected void doPost(HttpServletRequest req, HttpServletResponse resp)
			throws ServletException, IOException {
		// TODO Auto-generated method stub
//		super.doPost(req, resp);					
		
		Entity workout = new Entity("Workout");
		
		String id = req.getParameter("id");
		String steps = req.getParameter("steps");
		String starttime = req.getParameter("starttime");
		String distance = req.getParameter("distance");
		String duration = req.getParameter("duration");
		String avgspeed = req.getParameter("avgspeed");
		String startlocation = req.getParameter("startlocation");
		Text path = new Text(req.getParameter("path"));
//		Text t = new Text("text");
		log.info("the enetity is: ");
		log.info(" id: " + id +
					" steps: " + steps +
					" starttime: " + starttime +
					" distance: " + distance +
					" duration: " + duration +
					" avgspeed: " + avgspeed +
					" startlocaton: " + startlocation +
					" path: " + path.getValue());
		resp.setContentType("text/plain");
		resp.getWriter().println("ok!");
		
		workout.setProperty("id", id);
		workout.setProperty("steps", steps);
		workout.setProperty("starttime", starttime);
		workout.setProperty("distance", distance);
		workout.setProperty("duration", duration);
		workout.setProperty("avgspeed", avgspeed);
		workout.setProperty("startlocation", startlocation);
		workout.setProperty("path", path);
		DatastoreService datastore = DatastoreServiceFactory.getDatastoreService();
		datastore.put(workout);
//		
//		log.info("put a new entity into datastore.");
		log.info("entityis: " + workout.toString());
		resp.setContentType("text/plain");
		resp.getWriter().println("ok");
	}
}
