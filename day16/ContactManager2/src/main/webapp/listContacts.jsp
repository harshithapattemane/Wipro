<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ page import="java.util.*, com.contact.Contact" %>

<h2>All Contacts</h2>

<%
List<Contact> list = (List<Contact>) request.getAttribute("list");
int i = 0;
for (Contact c : list) {
%>
    <p>
        Name: <%= c.name %><br>
        Phone: <%= c.phone %><br>
        Email: <%= c.email %><br>
        <a href="edit?id=<%= i %>">Edit</a> |
        <a href="delete?id=<%= i %>">Delete</a>
    </p>
    <hr>
<%
i++;
}
%>

<a href="index.jsp">Home</a>
