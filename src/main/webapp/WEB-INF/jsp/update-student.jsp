<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
    <head>
        <title>Update Students</title>
        <link href="<c:url value="/css/common.css"/>" rel="stylesheet" type="text/css">
    </head>
    <body>
        <c:if test="${updateStudentSuccess}">
            <div>Successfully updated student with name: ${savedStudent.name}</div>
        </c:if>

        <c:url var="update_student_url" value="/student/update/{id}"/>
        <form:form action="${update_student_url}" method="post" modelAttribute="student">
            <form:label path="id">ID: </form:label> <form:input path="id"/>
            <form:label path="name">Name: </form:label> <form:input type="text" path="name"/>
            <form:label path="age">Age: </form:label> <form:input path="age"/>
            <input type="submit" value="submit"/>
        </form:form>
    </body>
</html>