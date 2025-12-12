package com.contact;

import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.http.*;

public class ListContactServlet extends HttpServlet {

    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        response.setContentType("text/html");

        response.getWriter().println("<h2>All Contacts</h2>");

        for (Contact c : AddContactServlet.contactList) {
            response.getWriter().println("<p>");
            response.getWriter().println("Name: " + c.name + "<br>");
            response.getWriter().println("Phone: " + c.phone + "<br>");
            response.getWriter().println("Email: " + c.email + "<br>");
            response.getWriter().println("</p><hr>");
        }
    }
}
