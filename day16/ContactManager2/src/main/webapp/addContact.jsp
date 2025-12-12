<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ page import="java.util.*" %>

<h2>Add Contact</h2>

<%
String msg = (String) request.getAttribute("msg");
if (msg != null) {
    out.println("<p>" + msg + "</p>");
}
%>

<form action="add" method="post">
    Name: <input type="text" name="name"><br><br>
    Phone: <input type="text" name="phone"><br><br>
    Email: <input type="text" name="email"><br><br>

    <button type="submit">Save Contact</button>
</form>

<a href="index.jsp">Home</a>
