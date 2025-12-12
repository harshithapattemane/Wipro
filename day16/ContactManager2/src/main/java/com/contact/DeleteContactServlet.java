package com.contact;

import java.io.IOException;
import javax.servlet.*;
import javax.servlet.http.*;

public class DeleteContactServlet extends HttpServlet {

    protected void doGet(HttpServletRequest req, HttpServletResponse res)
            throws ServletException, IOException {

        int id = Integer.parseInt(req.getParameter("id"));
        ContactService.delete(id);

        res.sendRedirect("list");
    }
}
