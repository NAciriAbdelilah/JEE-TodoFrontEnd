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

   /* public void addStudent(Todo todo) throws Exception {
        Connection myConn = null;
        PreparedStatement myStmt = null;

        try {
            // Get a database connection
            myConn = dataSource.getConnection();

            // Create a SQL statement
            String sql = "INSERT INTO student (first_name, last_name, email) VALUES (?, ?, ?)";
            myStmt = myConn.prepareStatement(sql);

            // Set the parameters for the prepared statement
            myStmt.setString(1, todo.getFirstName());
            myStmt.setString(2, todo.getLastName());
            myStmt.setString(3, todo.getEmail());

            // Execute the SQL statement
            myStmt.executeUpdate();
        } finally {
            // Close the database resources
            close(myConn, myStmt, null);
        }
    }

    public Todo fetchStudent(int id) {
        Connection myConn=null;
        Statement myStmt = null;
        ResultSet myRs= null;
        Todo todo =null;
        try {
            myConn = dataSource.getConnection();
            myStmt= myConn.createStatement();
            String sql= "select * from student where id="+id;
            myRs = myStmt.executeQuery(sql);
            while(myRs.next()){
                String firstName=myRs.getString("first_name");
                String lastName=myRs.getString("last_name");
                String email = myRs.getString("email");
                todo = new Todo(id,firstName,lastName,email);
            }
            return todo;
        }catch(Exception e){
            System.out.println(e.getMessage());
            return null;
        } finally{
            close(myConn,myStmt,myRs);
        }

    }

    public void updateStudent(Todo todo) {
        Connection myConn=null;
        PreparedStatement myStmt = null;
        try {
            myConn = dataSource.getConnection();
            String sql = "update student set first_name=?, last_name=?,email=? where id=?";
            myStmt = myConn.prepareStatement(sql);
            myStmt.setString(1, todo.getFirstName());
            myStmt.setString(2, todo.getLastName());
            myStmt.setString(3, todo.getEmail());
            myStmt.setInt(4, todo.getId());
            myStmt.execute();
        }
        catch(Exception e){
            System.out.println(e.getMessage());
        }
        finally{
            close(myConn,myStmt,null);
        }

    }

    public void deleteStudent(int studentId) {
        Connection myConn=null;
        PreparedStatement myStmt = null;

        try {
            // Get a connection to the database
            myConn = dataSource.getConnection();

            // Create the SQL statement
            String sql = "DELETE FROM student WHERE id = ?";

            // Create the prepared statement
            myStmt = myConn.prepareStatement(sql);

            // Set the parameters
            myStmt.setInt(1, studentId);

            // Execute the statement
            myStmt.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            // Close the resources
            close(myConn, myStmt, null);
        }
    }
*/
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

    public Todo fetchTodo(int id) {
        Connection myConn=null;
        Statement myStmt = null;
        ResultSet myRs= null;
        Todo todo =null;
        try {
            myConn = dataSource.getConnection();
            myStmt= myConn.createStatement();
            String sql = "SELECT * FROM todo WHERE id = ?";
            myRs = myStmt.executeQuery(sql);
            while(myRs.next()){
                String description=myRs.getString("description");
                int statut =myRs.getInt("statut");
                todo = new Todo(id,description,statut);
            }
            return todo;
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



    /*public void updateTodoStatut(Todo todo) {
        Connection myConn=null;
        PreparedStatement myStmt = null;
        try {
            myConn = dataSource.getConnection();
            String sql = "update todo set description=?,statut=1 where id=?";
            myStmt = myConn.prepareStatement(sql);
            myStmt.setString(1, todo.getDescription());
            myStmt.setInt(2, todo.getStatut());
            myStmt.setInt(3, todo.getId());
            myStmt.execute();
        }
        catch(Exception e){
            System.out.println(e.getMessage());
        }
        finally{
            close(myConn,myStmt,null);
        }

    }*/



/*    public String getUserRole(String username) {
        Connection connection = null;
        PreparedStatement statement = null;
        ResultSet resultSet = null;

        try {
            // Get a database connection
            connection = dataSource.getConnection();

            // Prepare SQL statement
            String sql = "SELECT * FROM users u " +
                    "JOIN roles r ON u.username = r.username " +
                    "WHERE u.username = ?";
            statement = connection.prepareStatement(sql);
            statement.setString(1, username);

            // Execute query
            resultSet = statement.executeQuery();

            // Process result
            if (resultSet.next()) {
                return resultSet.getString("role");
            }
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            // Close JDBC objects
            close(connection, statement, resultSet);
        }

        return null;
    }*/
}
