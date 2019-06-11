<%--
  Created by IntelliJ IDEA.
  User: Дмитрий
  Date: 11.06.2019
  Time: 10:28
  To change this template use File | Settings | File Templates.
--%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib uri="http://sargue.net/jsptags/time" prefix="javatime" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>Meals</title>
</head>
<body>
    <h1>List meals</h1>
    <table border="1">
        <thead>
            <tr>
                <th>Date and time meal</th>
                <th>Description meal</th>
                <th>Calories</th>
                <th>Excess</th>
            </tr>
        </thead>
        <tbody>
        <c:forEach var="meal" items="${listMeal}">
            <tr style="${meal.excess ? 'background-color: crimson' : 'background-color: forestgreen'}">
                <td>
                    <javatime:format value="${meal.dateTime}" style="MS" />
                </td>
                <td><c:out value="${meal.description}"/></td>
                <td><c:out value="${meal.calories}"/></td>
                <td><c:out value="${meal.excess}"/></td>
            </tr>
        </c:forEach>
        </tbody>
    </table>
</body>
</html>
