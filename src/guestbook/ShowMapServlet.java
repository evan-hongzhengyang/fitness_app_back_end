package guestbook;

import java.io.IOException;
import java.io.PrintWriter;
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
import com.google.appengine.api.datastore.Text;

public class ShowMapServlet extends HttpServlet 
{
	private static final Logger log = Logger.getLogger(SignGuestbookServlet.class.getName());
	private final String[] colors =
		{"red", "orange", "yellow", "green", "blue", "violet", "black", "gray", "brown", 
			"purple", "tan", "teal"};
	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp)
			throws ServletException, IOException {
		// TODO Auto-generated method stub
//		super.doGet(req, resp);
		
		//get all the workout data
		DatastoreService datastore = DatastoreServiceFactory.getDatastoreService();
		Query query = new Query("Workout").addSort("id", Query.SortDirection.ASCENDING);
		List<Entity> workouts = datastore.prepare(query).asList(FetchOptions.Builder.withDefaults());
		
		StringBuilder mapVarContent = new StringBuilder();
		for(int i=0; i < workouts.size(); i++)
		{
			//for each entity, create 2 variables:
			//1. pathCoordinates0: store all the locations
			//2. pathVar: receive the 1st variable and set colors, weight
			Entity workout = workouts.get(i);
			Text pathText = (Text)workout.getProperty("path");
			String path = pathText.getValue();
			String[] pathList = path.split(", ");
			
			mapVarContent.append("var pathCoordinates" + i + "=[");
			for(int j = 0; j < ((pathList.length)/2-1); j++)
			{
				mapVarContent.append("new google.maps.LatLng(" + pathList[2*j] +
										", " + pathList[2*j+1] + "),");
			}
			mapVarContent.append("new google.maps.LatLng(" + 
								pathList[2*(pathList.length/2-1)] + ", " +
								pathList[2*(pathList.length/2-1)+1] + ")");
			mapVarContent.append("];");
			mapVarContent.append("var pathVar" + i + " = new google.maps.Polyline({" +
								"path: pathCoordinates" + i + "," +
								"strokeColor: \"" + colors[i % colors.length] + "\"," +
								"strokeOpacity: 1.0, " +
								"strokeWeight: 2" +
								"});");
			mapVarContent.append("pathVar" + i + ".setMap(map);");			
		}
		resp.setContentType("text/html");
		PrintWriter out = resp.getWriter();
		out.println(PolyLineDrawer.draw(mapVarContent.toString()));
		
	}

}
