package guestbook;

import java.io.StringWriter;
import java.util.List;
import java.util.logging.Logger;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerConfigurationException;
import javax.xml.transform.TransformerException;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;

import org.mortbay.log.Log;
import org.w3c.dom.Attr;
import org.w3c.dom.Document;
import org.w3c.dom.Element;

import com.google.appengine.api.datastore.Entity;
import com.google.appengine.api.datastore.Text;

public class MyXMLGenerator {
	
	private static final Logger log = Logger.getLogger(SignGuestbookServlet.class.getName());
	private MyXMLGenerator(){}

	//use factory to create a new XMLGenerator
	public static MyXMLGenerator getInstance()
	{
		return new MyXMLGenerator();
	}
	
	public String create(List<Entity> workouts)
	{
		DocumentBuilderFactory documentBuilderFactory =
				DocumentBuilderFactory.newInstance();
		DocumentBuilder documentBuilder = null;
		try {
			documentBuilder = documentBuilderFactory.newDocumentBuilder();
		} catch (ParserConfigurationException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		//create the root element
		Document document = documentBuilder.newDocument();
		Element rootElement = document.createElement("workouts");
		document.appendChild(rootElement);
		
		//create a workout element for every workout
		for(Entity workout : workouts)
		{
			log.info("the eneity is: " + workout.toString());
			Element workoutElement = document.createElement("workout");
			rootElement.appendChild(workoutElement);
			
			//create the attributions fo this workout
			//id
			Attr id = document.createAttribute("id");
			id.setValue(String.valueOf(workout.getProperty("id")));
			workoutElement.setAttributeNode(id);
			//steps
			Element stepsElement = document.createElement("steps");
			stepsElement.appendChild(document.createTextNode
					(String.valueOf(workout.getProperty("steps"))));
			workoutElement.appendChild(stepsElement);
			//starttime
			Element starttimeElement = document.createElement("starttime");
			starttimeElement.appendChild(document.createTextNode
					(String.valueOf(workout.getProperty("starttime"))));
			workoutElement.appendChild(starttimeElement);
			//distance
			Element distanceElement = document.createElement("distance");
			distanceElement.appendChild(document.createTextNode
					(String.valueOf(workout.getProperty("distance"))));
			workoutElement.appendChild(distanceElement);
			//duration
			Element durationElement = document.createElement("duration");
			durationElement.appendChild(document.createTextNode
					(String.valueOf(workout.getProperty("duration"))));
			workoutElement.appendChild(durationElement);
			//avgspeed
			Element avgspeedElement = document.createElement("avgspeed");
			avgspeedElement.appendChild(document.createTextNode
					(String.valueOf(workout.getProperty("avgspeed"))));
			workoutElement.appendChild(avgspeedElement);
			//startlocation
			Element startlocationElement = document.createElement("startlocation");
			startlocationElement.appendChild(document.createTextNode
					(String.valueOf(workout.getProperty("startlocation"))));
			workoutElement.appendChild(startlocationElement);
			//path
			
			Text pathText = (Text) workout.getProperty("path");
			String pathString = pathText.getValue();
			
			//java.lang.ClassCastException: com.google.appengine.api.datastore.Text cannot be cast to java.lang.String
//			String path = (String) workout.getProperty("path");
			
			Element pathElement = document.createElement("path");
			pathElement.appendChild(document.createTextNode
					(pathString));
//					(String.valueOf(workout.getProperty("path"))));
			workoutElement.appendChild(pathElement);
		}
		TransformerFactory transformerFactory = 
				TransformerFactory.newInstance();
		Transformer transformer = null;
		try {
			transformer = transformerFactory.newTransformer();
		} catch (TransformerConfigurationException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		DOMSource domSource = new DOMSource(document);
		StringWriter writer = new StringWriter();
		StreamResult result = new StreamResult(writer);
		try {
			transformer.transform(domSource, result);
		} catch (TransformerException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		writer.flush();
		return writer.toString();
//		StreamResult result = new StreamResult();
//		
//		try {
//			transformer.transform(domSource, result);
//		} catch (TransformerException e) {
//			// TODO Auto-generated catch block
//			e.printStackTrace();
//		}
	}
}
