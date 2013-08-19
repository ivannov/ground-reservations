<%@page import="java.util.Date"%>
<%@page import="java.text.SimpleDateFormat"%>
<%@page import="org.groundres.services.Util"%>
<%@page import="org.groundres.model.Offer"%>
<%@page import="java.net.URLEncoder"%>
<%@page import="org.groundres.model.Court"%>
<%@page import="java.util.List"%>
<%@page import="java.util.Map"%>
<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; UTF-8">
<title>Insert title here</title>
</head>
<body>
    <%
    List<Court> courts = (List<Court>) request.getAttribute("courts");
    Map<Court, List<Offer>> offers = (Map<Court, List<Offer>>) request.getAttribute("offers");
    List<String> timeSlots = (List<String>) request.getAttribute("timeSlots");
    %>
    <table>
        <tr>
            <th>Обект</th>
            <%
            for(String timeSlot : timeSlots) {
                out.println("<th>" + timeSlot + "</th>");
            }
            %>
            <th>Телефон</th>
        </tr>
        <% 
        for (Court court : courts) {
            List<Offer> offersForCourt = offers.get(court);
            %>
            <tr>
                <td><a href="court?id=<%= court.getId() %>"><%= court.getName() %></a></td>
                <% for (Offer offer : offersForCourt) { %>
                <td><%= Util.formatPrice(offer) %> </td>
                <% } %>
                <td><%= court.getPhone() %></td>
            </tr>
            <%
        }
        %>
    </table>
</body>
</html>