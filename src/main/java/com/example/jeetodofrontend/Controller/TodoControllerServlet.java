package com.example.jeetodofrontend.Controller;

import com.example.jeetodofrontend.Model.Todo;
import com.example.jeetodofrontend.Model.TodoDBUtil;

import javax.naming.Context;
import javax.naming.InitialContext;
import javax.naming.NamingException;
import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.sql.DataSource;
import java.io.IOException;
import java.util.List;

@WebServlet(name = "TodoControllerServlet", value = "/TodoControllerServlet")
public class TodoControllerServlet extends HttpServlet {
    private TodoDBUtil todoDBUtil;
    private DataSource dataSource;
    public TodoControllerServlet() {
        super();
    }
    private DataSource getDataSource() throws NamingException {
        String jndi="java:comp/env/jdbc/tododb" ;
        Context context = new InitialContext();
        DataSource dataSource = (DataSource) context.lookup(jndi);
        return dataSource;
    }
    @Override
    public void init() throws ServletException {
        super.init();
        try {
            dataSource= getDataSource();
            todoDBUtil = new TodoDBUtil(dataSource);
        } catch (NamingException e) {
            e.printStackTrace();
        }
    }


    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        try {
            listTodos(request,response);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    private void listTodos(HttpServletRequest request, HttpServletResponse response) throws Exception{

        List<Todo> todos = todoDBUtil.getTodos();
        request.setAttribute("TODO_LIST", todos);
        RequestDispatcher dispatcher = request.getRequestDispatcher("WEB-INF/list-todos.jsp");
        dispatcher.forward(request, response);
    }
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

    }
}
