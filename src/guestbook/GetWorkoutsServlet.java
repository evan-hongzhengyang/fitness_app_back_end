package guestbook;

import java.io.IOException;
import java.util.List;
import java.util.logging.Logger;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.google.appengine.api.datastore.DatastoreService;
import com.google.appengine.api.datastore.DatastoreServiceFactory;
import com.google.appengine.api.datastore.Entity;
import com.google.appengine.api.datastore.FetchOptions;
import com.google.appengine.api.datastore.Query;

public class GetWorkoutsServlet extends HttpServlet {

	private static final Logger log = Logger.getLogger(SignGuestbookServlet.class.getName());
	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp)
			throws ServletException, IOException {
		// TODO Auto-generated method stub
//		super.doGet(req, resp);
		//get all the workout data
		DatastoreService datastore = DatastoreServiceFactory.getDatastoreService();
		Query query = new Query("Workout").addSort("id", Query.SortDirection.ASCENDING);
		List<Entity> workouts = datastore.prepare(query).asList(FetchOptions.Builder.withDefaults());
		
		//generate an xml using MyXMLGenerator
		MyXMLGenerator generator = MyXMLGenerator.getInstance();
		String xmlResult = generator.create(workouts);
		log.info("the xml: ");
		log.info(xmlResult);
		resp.setContentType("text/plain");
		resp.getWriter().println(xmlResult);
	}

}
