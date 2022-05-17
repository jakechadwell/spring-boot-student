<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
    <head>
        <title>Login</title>
        <link href="<c:url value="/css/common.css"/>" rel="stylesheet" type="text/css">
    </head>
    <body>
        <c:url var="login_user_url" value="/"/>
        <form:form action="${login_user_url}" method="post" modelAttribute="user">
            <form:label path="username">Username: </form:label> <form:input type="text" path="username"/>
            <form:label path="password">Password: </form:label> <form:input type="password" path="password"/>
            <input type="submit" value="submit"/>
        </form:form>
    </body>
</html>