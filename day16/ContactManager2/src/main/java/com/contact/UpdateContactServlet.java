package com.contact;

import java.io.IOException;
import javax.servlet.*;
import javax.servlet.http.*;

public class UpdateContactServlet extends HttpServlet {

    protected void doPost(HttpServletRequest req, HttpServletResponse res)
            throws ServletException, IOException {

        int id = Integer.parseInt(req.getParameter("id"));

        String name = req.getParameter("name");
        String phone = req.getParameter("phone");
        String email = req.getParameter("email");

        Contact updated = new Contact(name, phone, email);
        ContactService.update(id, updated);

        res.sendRedirect("list");
    }
}
