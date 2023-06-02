package com.example.jeetodofrontend.Controller;

import com.example.jeetodofrontend.Model.Todo;
import com.example.jeetodofrontend.Model.TodoDBUtil;

import javax.naming.Context;
import javax.naming.InitialContext;
import javax.naming.NamingException;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.sql.DataSource;
import java.io.IOException;

@WebServlet(name = "DoneTodoServlet", value = "/DoneTodoServlet")
public class DoneTodoServlet extends HttpServlet {
    private static final long serialVersionUID = 1L;
    private int id;
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

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        id = Integer.parseInt(request.getParameter("todoId"));
        //Todo todo = todoDBUtil.fetchTodo(id);
        todoDBUtil.updateTodoStatut(id);
        request.getRequestDispatcher("TodoControllerServlet").forward(request, response);

    }

    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        /*String description = request.getParameter("description");
        int statut = Integer.parseInt(request.getParameter("statut"));
        Todo todo = new Todo(id, description, statut);
        todoDBUtil.updateTodoStatut(todo);
        response.sendRedirect("TodoControllerServlet");*/
    }
}
