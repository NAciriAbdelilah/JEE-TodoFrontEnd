<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<html>
<head>
    <title>Todo Projet</title>
    <link type="text/css" rel="stylesheet" href="./css/style.css">
</head>
<body>
<!-- ${TODO_LIST}-->
<div id="wrapper">
    <div id="header">
        <h2>LIST OF YOUR TODOS</h2>
    </div>
</div>
<br>
<!-- this code like a guard to redirect to Login page JSP if you are not logged -->
<c:choose>
    <c:when test="${not empty sessionScope.username}">
        <strong/><p>Welcome ${sessionScope.username}</p>
    </c:when>
    <c:otherwise>
        <script>
            window.location.href = "WEB-INF/login.jsp";
        </script>
    </c:otherwise>
</c:choose>
<br>
<!-- Add Student Button -->
<%--<c:if test="${sessionScope.role eq 'admin'}">
    <div>
        <form action="AddStudentServlet" method="get">
            <button type="submit">Add new Student</button>
        </form>
    </div>
</c:if>--%>
<!-- Logout Button -->
    <br>
    <div>
        <form action="LogoutServlet" method="get">
            <button type="submit">Logout</button>
        </form>
    </div>

<div id="container">
    <div id="content">
        <table>
                <tr>
                    <th>Id </th>
                    <th>Description </th>
                    <th>Statut </th>
<%--                    <c:if test="${sessionScope.role eq 'admin'}">
                        <th>Action </th>
                    </c:if>--%>
                </tr>

            <c:forEach var="tempTodo" items="${TODO_LIST}">
                <c:url var="doneLink" value="DoneTodoServlet">
                    <c:param name="todoId" value="${tempTodo.id}" />
                </c:url>
                <%--<c:url var="deleteLink" value="DeleteStudentServlet">
                    <c:param name="studentId" value="${tempTodo.id}" />
                </c:url>--%>
                <tr>
                    <td>${tempTodo.id}</td>
                    <td>${tempTodo.description}</td>
                    <td>${tempTodo.statut}</td>

                    <td>
                        <c:if test="${tempTodo.statut eq '0'}">
                            <a href="${doneLink}">Done</a>
                        </c:if>
                    </td>

<%--                    <c:if test="${sessionScope.role eq 'admin'}">
                        <td><a href="${doneLink}">Done </a></td>
                    </c:if>--%>
                </tr>
            </c:forEach>

        </table>
    </div>
</div>
</body>
</html>