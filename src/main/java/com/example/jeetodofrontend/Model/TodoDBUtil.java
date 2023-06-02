package com.example.jeetodofrontend.Model;

import javax.sql.DataSource;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class TodoDBUtil {
    private DataSource dataSource;
    public TodoDBUtil(DataSource theDataSource) {
        this.dataSource = theDataSource;
    }

    public TodoDBUtil() {

    }

    public List<Todo> getTodos() throws Exception {
        List<Todo> todos = new ArrayList<Todo>();
        Connection myConn=null;
        Statement myStmt = null;
        ResultSet myRs= null;
        try {
            // Get a database connection
            myConn = dataSource.getConnection();
            myStmt= myConn.createStatement();
            // Create a SQL statement
            String sql= "select * from todo";
            myRs = myStmt.executeQuery(sql);
            // Get the parameters for the statement
            while(myRs.next()){
                int id = myRs.getInt("id");
                String description = myRs.getString("description");
                int statut = myRs.getInt("statut");
                Todo tempTodo = new Todo(id,description,statut);
                todos.add(tempTodo);
            }
            return todos;
        } finally{
            close(myConn,myStmt,myRs);
        }
    }
    private void close(Connection myConn, Statement myStmt, ResultSet myRs) {
        try{
            if(myStmt!=null)
                myStmt.close();
            if(myRs!=null)
                myRs.close();
            if(myConn!=null)
                myConn.close();
        }catch(Exception e){
            System.out.println(e.getMessage());
        }
    }
    public boolean authenticateUser(String username, String password) {
        Connection connection = null;
        PreparedStatement statement = null;
        ResultSet resultSet = null;

        try {
            // Get a database connection
            connection = dataSource.getConnection();

            // Prepare SQL statement
            String sql = "SELECT * FROM users WHERE username = ? AND password = ?";
            statement = connection.prepareStatement(sql);
            statement.setString(1, username);
            statement.setString(2, password);

            // Execute query
            resultSet = statement.executeQuery();

            // Check if the user exists and the password matches
            return resultSet.next();
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            // Close JDBC objects
            close(connection, statement, resultSet);
        }

        return false;
    }

    public List<Todo> fetchTodo(String username) {
        Connection myConn=null;
        Statement myStmt = null;
        ResultSet myRs= null;
        Todo todo =null;
        try {
            myConn = dataSource.getConnection();
            myStmt= myConn.createStatement();
            String sql = "SELECT * FROM todo WHERE username =\'"+ username+"'";
            myRs = myStmt.executeQuery(sql);
            List<Todo> todoList = new ArrayList<>();
            while(myRs.next()){
                int id = myRs.getInt("id");
                String description = myRs.getString("description");
                int statut = myRs.getInt("statut");
                todo = new Todo(id,description,statut);
                todoList.add(todo);
            }
            return todoList;
        }catch(Exception e){
            System.out.println(e.getMessage());
            return null;
        } finally{
            close(myConn,myStmt,myRs);
        }
    }

    public void updateTodoStatut(int id) {
        Connection myConn = null;
        PreparedStatement myStmt = null;
        try {
            myConn = dataSource.getConnection();
            String sql = "UPDATE todo SET statut = 1 WHERE id = ?";
            myStmt = myConn.prepareStatement(sql);
            myStmt.setInt(1, id);
            myStmt.executeUpdate();
        } catch (Exception e) {
            System.out.println(e.getMessage());
        } finally {
            close(myConn, myStmt, null);
        }
    }

}
