<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<!DOCTYPE html>
<html>
<head>
    <title>Login Page</title>
    <link type="text/css" rel="stylesheet" href="./css/style.css">
</head>
<body>

<form action="LoginServlet" method="post">
    <fieldset>
        <legend>Login Details:</legend>
        <div class="form-group">
            <label for="username">Username:</label>
            <input type="text" id="username" name="username" value="${nomUser}"  required>
        </div>
        <div class="form-group">
            <label for="password">Password:</label>
            <input type="password" id="password" name="password" required>
        </div>
        <div class="form-group">
            <input type="submit" value="Login">
        </div>

        <c:if test="${not empty param['error']}">
            <strong/><span style="color: red">${param['error']}</span>
        </c:if>

    </fieldset>
</form>

</body>
</html>
