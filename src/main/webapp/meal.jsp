<%--
  Created by IntelliJ IDEA.
  User: Дмитрий
  Date: 11.06.2019
  Time: 13:29
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html; charset=UTF-8" language="java" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<html>
<head>
    <meta charset="utf-8">
    <title>Add(Edit) meal</title>
</head>
<body>
    <h2>Meal details</h2>
    <form method="POST" action='meals' name="frmAddMeal">
        <input type="hidden" readonly="readonly" name="id"
                         value="<c:out value="${meal.id}" />" /> <br />
        Date time : <input
            type="datetime-local" name="datetime"
            value="<c:out value="${meal.dateTime}" />" /> <br />
        Description : <input
            type="text" name="description"
            value="<c:out value="${meal.description}" />" /> <br />
        Calories : <input
            type="number" name="calories"
            value="<c:out value="${meal.calories}" />" /> <br />
        <br /> <input type="submit" value="Save" />
    </form>

    <br/>
    <p><a href="meals?action=listMeals">List meals</a> </p>
</body>
</html>
