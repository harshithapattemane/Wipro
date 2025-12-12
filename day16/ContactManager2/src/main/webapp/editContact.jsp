<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ page import="com.contact.Contact" %>

<%
Contact c = (Contact) request.getAttribute("contact");
int id = (int) request.getAttribute("id");
%>

<h2>Edit Contact</h2>

<form action="update" method="post">
    <input type="hidden" name="id" value="<%= id %>">

    Name: <input type="text" name="name" value="<%= c.name %>"><br><br>
    Phone: <input type="text" name="phone" value="<%= c.phone %>"><br><br>
    Email: <input type="text" name="email" value="<%= c.email %>"><br><br>

    <button type="submit">Update Contact</button>
</form>

<a href="list">Back</a>
