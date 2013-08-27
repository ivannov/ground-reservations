<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>Admin panel</title>
</head>
<body>
<form action="admin" method="post">
<table>
  <tr>
    <td><label>Име на корта:</label></td>
    <td><input type="text" name="courtName"></td>
  </tr>
  <tr>
    <td><label>Описание на корта:</label></td>
    <td><input type="text" name="courtDescription"></td>
  </tr>
  <tr>
    <td><label>Адрес на корта:</label></td>
    <td><input type="text" name="courtAddress"></td>
  </tr>
  <tr>
    <td><label>Телефон за връзка:</label></td>
    <td><input type="text" name="phone"></td>
  </tr>
  <tr>
    <td><label>Цена на час:</label></td>
    <td><input type="text" name="defaultPrice"></td>
  </tr>
  <tr>
    <td><label>Потребителско име:</label></td>
    <td><input type="text" name="userName"></td>
  </tr>
  <tr>
    <td><label>Име на администратора:</label></td>
    <td><input type="text" name="realName"></td>
  </tr>
  <tr>
  <td></td><td><input type="submit" value="Създай"></td>
  </table>
</form>
</body>
</html>