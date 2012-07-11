package guestbook;

public class PolyLineDrawer 
{
	public static String draw(String polyLines)
	{
		String returnValue = 
				"<!DOCTYPE html>" +
				"<html>" +
				  "<head>" +
				    "<meta name=\"viewport\" content=\"initial-scale=1.0, user-scalable=no\" />" +
				    "<style type=\"text/css\">" +
				      "html { height: 100% }" +
				      "body { height: 100%; margin: 0; padding: 0 }" +
				      "#map_canvas { height: 100% }" +
				    "</style>" +
				    "<script type=\"text/javascript\"" +
				      "src=\"http://maps.googleapis.com/maps/api/js?key=AIzaSyAi2TM0HyD-ESefzHTtjUAHaDvM9vIzPDE&sensor=true\">" +
				    "</script>" +
				    "<script type=\"text/javascript\">" +
						"function initialize() {"+
				        "var myLatLng = new google.maps.LatLng(37.73452, -122.47659);"+
				        "var myOptions = {"+
				          "zoom: 15,"+
				          "center: myLatLng,"+
				          "mapTypeId: google.maps.MapTypeId.ROADMAP"+
				        "};"+

				        "var map = new google.maps.Map(document.getElementById(\"map_canvas\"), myOptions);"+
				        polyLines +
				      "}"+
				    "</script>"+
				  "</head>"+
				  "<body onload=\"initialize()\">"+
				    "<div id=\"map_canvas\" style=\"width:100%; height:100%\"></div>"+
				  "</body>"+
				"</html>";
		return returnValue;
	}

}
