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
    <tr><td>Цена:</td><td><%= new DecimalFormat("#.##").format(court.getDefaultPrice()) %> лв.</td></tr>
    <tr><td>Лице за контакт:</td><td><%= court.getHost().getRealName() %></td></tr>    
    </table>

    <a href="court">Назад</a>

</body>
</html>