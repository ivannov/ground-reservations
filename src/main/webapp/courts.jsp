<%@page import="org.groundres.services.OfferBean"%>
<%@page import="org.groundres.model.User"%>
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
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>Всички кортове</title>
<STYLE type="text/css">
      TABLE  { background: silver; empty-cells: show; border: none; border-spacing: 1pt; }
      TD     { background: white; font-family: Verdana, Arial; font-size: 80%; }
      TH     { background: silver; font-family: Verdana, Arial; font-size: 80%; }
</STYLE>
</head>
<body>
    <%
        User loggedUser = (User) request.getSession().getAttribute("loggedUser");
        if (loggedUser != null) {
    %>
    <div align="right">
        Добре дошъл, <%= loggedUser.getRealName() %>
    </div>
    <% } %>
    
    <%
    @SuppressWarnings("unchecked")
    List<Court> courts = (List<Court>) request.getAttribute("courts");
    @SuppressWarnings("unchecked")
    Map<Court, List<Offer>> offers = (Map<Court, List<Offer>>) request.getAttribute("offers");
    @SuppressWarnings("unchecked")
    List<List<Offer>> bestOffers = (List<List<Offer>>) request.getAttribute("bestOffers");
    
    @SuppressWarnings("unchecked")
    List<Integer> timeSlots = (List<Integer>) session.getAttribute("timeSlots");
    @SuppressWarnings("unchecked")
    List<Offer> offersForLoggedInUser = (List<Offer>) session.getAttribute("offersForLoggedInUser");
    %>

    <form action="court" method="post">
    <table width="100%">
        <tr>
            <th>Обект</th>
            <%
            for(int timeSlot : timeSlots) {
                out.println("<th>" + Util.formatTimeSlot(timeSlot) + "</th>");
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
                <% 
                for (int i = 0; i < offersForCourt.size(); i++) {
                    Offer offer = offersForCourt.get(i);
                    boolean isBestOffer = bestOffers.get(i).contains(offer);
                    String price = Util.formatPrice(offer);
                    
                    if (!"".equals(price)) {
                        price += " лв.";
                    }
                %>
                <td align="center"><% 
                    if (isBestOffer) { %>
                        <b>
                    <% }  %>
                    <%= price %>
                    <% if (isBestOffer) { %>
                        </b>
                    <% } %></td>
                <% } %>
                <td align="center"><%= court.getPhone() %></td>
            </tr>
            <%
        } %>
            
    <% if (offersForLoggedInUser != null) { %>
        <tr>
        <td><a href="court?id=<%= loggedUser.getCourt().getId() %>"><%= loggedUser.getCourt().getName() %></a></td>
        <%
            int index = 0;
            for (Offer offer : offersForLoggedInUser) {
                %>
                <td><input type="text" value="<%= Util.formatPrice(offer) %>" name="<%= index %>"></input></td>
                <%
                index++;
            }
        %>
        <td><input type="submit" value="Смени"></td>
        </tr>
   <% } %>
     </table>
   </form>
    
</body>
</html>