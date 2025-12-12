package com.contact;


import java.io.IOException;
import java.util.ArrayList;
import javax.servlet.ServletException;
import javax.servlet.http.*;

public class AddContactServlet extends HttpServlet {

    public static ArrayList<Contact> contactList = new ArrayList<>();

    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        String name = request.getParameter("name");
        String phone = request.getParameter("phone");
        String email = request.getParameter("email");

        Contact c = new Contact(name, phone, email);
        contactList.add(c);

        response.sendRedirect("list");
    }
}
