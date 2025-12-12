package com.contact;

import java.io.IOException;
import javax.servlet.*;
import javax.servlet.http.*;

public class AddContactServlet extends HttpServlet {

    protected void doPost(HttpServletRequest req, HttpServletResponse res)
            throws ServletException, IOException {

        String name = req.getParameter("name");
        String phone = req.getParameter("phone");
        String email = req.getParameter("email");

        if (name.isEmpty() || phone.isEmpty() || email.isEmpty()) {
            req.setAttribute("msg", "Error: All fields are required!");
            req.getRequestDispatcher("addContact.jsp").forward(req, res);
            return;
        }

        Contact c = new Contact(name, phone, email);
        ContactService.add(c);

        req.setAttribute("msg", "Contact Added Successfully!");
        req.getRequestDispatcher("addContact.jsp").forward(req, res);
    }
}
