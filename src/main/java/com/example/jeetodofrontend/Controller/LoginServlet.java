package com.example.jeetodofrontend.Controller;

import com.example.jeetodofrontend.Model.TodoDBUtil;
import javax.naming.Context;
import javax.naming.InitialContext;
import javax.naming.NamingException;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.*;
import javax.sql.DataSource;
import java.io.IOException;
import java.net.URLEncoder;

@WebServlet(name = "LoginServlet", value = "/LoginServlet")
public class LoginServlet extends HttpServlet {
    private TodoDBUtil todoDBUtil;
    private DataSource dataSource;

    private DataSource getDataSource() throws NamingException {
        String jndi = "java:comp/env/jdbc/tododb";
        Context context = new InitialContext();
        DataSource dataSource = (DataSource) context.lookup(jndi);
        return dataSource;
    }

    @Override
    public void init() throws ServletException {
        super.init();
        try {
            dataSource = getDataSource();
            todoDBUtil = new TodoDBUtil(dataSource);
        } catch (NamingException e) {
            e.printStackTrace();
        }
    }
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        Cookie [] cookies = request.getCookies();
        if(cookies!=null){
            for(Cookie cookie:cookies){
                if(cookie.getName().equals("nomUser"))
                    request.setAttribute("nomUser", cookie.getValue());
            }
        }

        request.getRequestDispatcher("/WEB-INF/login.jsp").forward(request, response);
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        // Get the username and password from the login form
        String username = request.getParameter("username");
        String password = request.getParameter("password");

        // Validate the user's credentials
        boolean authenticated = todoDBUtil.authenticateUser(username, password);

        if (authenticated) {
            Cookie cookie = new Cookie("nomUser", username);
            cookie.setMaxAge(60*60*24);
            response.addCookie(cookie);

            // Set up the session and store the username
            HttpSession session = request.getSession();
            session.setAttribute("username", username);
            response.sendRedirect("TodoControllerServlet");
/*
            // Redirect to the appropriate page based on user role
            String role = todoDBUtil.getUserRole(username);
            session.setAttribute("role", role);

            if ("admin".equalsIgnoreCase(role)) {
                System.out.println("je suis un ADMIN");
                response.sendRedirect("StudentControllerServlet");
            } else if ("instructor".equalsIgnoreCase(role)) {
                System.out.println("je suis un INSTRUCTOR");
                response.sendRedirect("StudentControllerServlet");
            } else {
                // Invalid role,
                response.sendRedirect("../WEB-INF/error.jsp?errorMessage=" + URLEncoder.encode("Invalid role", "UTF-8"));
            }*/
        } else {
            // Authentication failed, redirect back to the login page with an error message
            String errorMessage = "Authentication failed. Please try again.";
            request.getRequestDispatcher("/WEB-INF/error.jsp").forward(request,response);
            //response.sendRedirect("/WEB-INF/error.jsp?errorMessage=" + URLEncoder.encode(errorMessage, "UTF-8"));

        }
    }
}
