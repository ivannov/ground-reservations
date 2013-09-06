<%@page import="org.groundres.model.User"%>
<%@page import="java.text.DecimalFormat"%>
<%@page import="org.groundres.model.Court"%>
<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<% Court court = (Court) request.getAttribute("court"); %>
<title>Корт <%= court.getName() %></title>
</head>
<body>
    <table>
	    <tr><td>Име:</td><td><%= court.getName() %></td></tr>
	    <tr><td>Описание:</td><td><%= court.getDescription() %></td></tr>
	    <tr><td>Адрес:</td><td><%= court.getAddress() %></td></tr>
	    <tr><td>Телефон:</td><td><%= court.getPhone() %></td></tr>
	    <tr><td>Цена:</td><td><%= court.getDefaultPrice() %> лв.</td></tr>
	    <tr><td>Лице за контакт:</td><td><%= court.getHost().getRealName() %></td></tr>    
    </table>

    <%
    User loggedUser = (User) request.getSession().getAttribute("loggedUser");
    if (loggedUser != null && court.getHost().equals(loggedUser)) {
        if (request.getParameter("confirmFail") != null) { %>
           <font color="red">Двете пароли трябва да съвпадат</font> 
        <% }
    %>
    
    <form action="updatePassword" method="post">
        <table>
            <tr><td>Нова парола:</td><td><input type="password" name="newPassword"></td></tr>
            <tr><td>Потвърждение:</td><td><input type="password" name="confirmNewPassword"></td></tr>
            <tr><td></td><td><input type="submit" value="Смени"></td></tr>
        </table>
    </form>
    
    <% } %>
    <a href="court">Назад</a>

</body>
</html>