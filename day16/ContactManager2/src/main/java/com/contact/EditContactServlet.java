package com.contact;

import java.io.IOException;
import javax.servlet.*;
import javax.servlet.http.*;

public class EditContactServlet extends HttpServlet {

    protected void doGet(HttpServletRequest req, HttpServletResponse res)
            throws ServletException, IOException {

        int id = Integer.parseInt(req.getParameter("id"));

        req.setAttribute("contact", ContactService.get(id));
        req.setAttribute("id", id);

        req.getRequestDispatcher("editContact.jsp").forward(req, res);
    }
}
