<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<!DOCTYPE html>
<html>
<head>
<title>Show Map</title>
<meta name="viewport" content="initial-scale=1.0, user-scalable=no" />
    <style type="text/css">
      html { height: 100% }
      body { height: 100%; margin: 0; padding: 0 }
      #map_canvas { height: 100% }
    </style>
    <script type="text/javascript"
      src="http://maps.googleapis.com/maps/api/js?key=AIzaSyAi2TM0HyD-ESefzHTtjUAHaDvM9vIzPDE&sensor=true">
    </script>
    <%
    	String latitude = request.getParameter("lat");
    	String longitude = request.getParameter("lng");
    	
    	if(latitude == null)
    	{
    		latitude = "-37.73332";
    	}    		
    	if(longitude == null)
    	{
    		longitude = "122.47540";
    	}
    	Double lat = new Double(latitude);
    	Double lng = new Double(longitude);
    %>
    <script type="text/javascript">
      function initialize() {
        var myOptions = {
          center: new google.maps.LatLng(<%= lat %>, <%= lng %>),
          zoom: 16,
          mapTypeId: google.maps.MapTypeId.ROADMAP  
        };
        var map = new google.maps.Map(document.getElementById("map_canvas"),
            myOptions);
      }
    </script>
</head>
<body onload="initialize()">
	<div id="map_canvas" style="width:100%; height:100%"></div>
</body>
</html>