package com.contact;

import java.io.IOException;
import javax.servlet.*;
import javax.servlet.http.*;

public class ListContactServlet extends HttpServlet {

    protected void doGet(HttpServletRequest req, HttpServletResponse res)
            throws ServletException, IOException {

        req.setAttribute("list", ContactService.getAll());
        req.getRequestDispatcher("listContacts.jsp").forward(req, res);
    }
}
