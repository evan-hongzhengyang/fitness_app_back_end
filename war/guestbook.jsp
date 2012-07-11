<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ page import="com.google.appengine.api.users.User" %>
<%@ page import="com.google.appengine.api.users.UserService" %>
<%@ page import="com.google.appengine.api.users.UserServiceFactory"  %>

<%@ page import="java.util.List" %>
<%@ page import="com.google.appengine.api.datastore.DatastoreServiceFactory" %>
<%@ page import="com.google.appengine.api.datastore.DatastoreService" %>
<%@ page import="com.google.appengine.api.datastore.Query" %>
<%@ page import="com.google.appengine.api.datastore.Entity" %>
<%@ page import="com.google.appengine.api.datastore.FetchOptions" %>
<%@ page import="com.google.appengine.api.datastore.Key" %>
<%@ page import="com.google.appengine.api.datastore.KeyFactory" %>

<html>
	<head>
		<link type="text/css" rel="stylesheet" href="/stylesheets/main.css" />
	</head>	
	<body>
		<%
			String guestbookName = request.getParameter("guestbookName");
		if (guestbookName == null){
			guestbookName = "default";
		}
			UserService userService = UserServiceFactory.getUserService();
			User user = userService.getCurrentUser();
			if (user != null)
			{			
		%>
		<p>hello, <%= user.getNickname() %>! (you can
		<a href="<%= userService.createLogoutURL(request.getRequestURI()) %>">sign out</a>.)</p>
		<%
			} 
			else
			{	
		%>
		<p>hello!
		<a href="<%= userService.createLoginURL(request.getRequestURI()) %>">sign in</a>
		to include your name with greetings you post.</p>
		<%
			}
		%>
		<%
			DatastoreService datastore = DatastoreServiceFactory.getDatastoreService();
			Key guestbookKey = KeyFactory.createKey("Guestbook", guestbookName);
			// Run an ancestor query to ensure we see the most up-to-date
		    // view of the Greetings belonging to the selected Guestbook.
		    
		    //retrieving the stored greetings
		    Query query = new Query("Greeting", guestbookKey).addSort("date", Query.SortDirection.DESCENDING);
			//Query query = new Query("Greeting").addSort("date", Query.SortDirection.DESCENDING);
			List<Entity> greetings = datastore.prepare(query).asList(FetchOptions.Builder.withLimit(5));
			//List<Entity> greet = datastore.prepare(query).asList(FetchOptions.Builder.withDefaults());
			if (greetings.isEmpty()){
				%>
				<p>Guestbook '<%= guestbookName %>' has no messages.</p>
				<%
			} else {
				%>
				<p>Messages in Guestbook '<%= guestbookName %>'.</p>
				<%
				for (Entity greeting : greetings){
					if (greeting.getProperty("user") == null){
						%>
						<p>an anonymous person wrote:</p>
						<%
					} else {
						%>
						<p><b><%= ((User) greeting.getProperty("user")).getNickname() %></b> wrote:</p>
						<%
					}
					%>
					<blockquote><%= greeting.getProperty("content") %></blockquote>
					<%
				}
			}
		%>
		<form action="/sign" method="post">
			<div><textarea name="content" rows="3" cols="60"></textarea></div>
			<div><input type="submit" value="Post Greeting"/></div>
			<input type="hidden" name="guestbookName" value="<%= guestbookName %>" />
		</form>
	</body>
</html>
