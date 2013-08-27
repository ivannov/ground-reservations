package org.groundres.servlets;

import java.io.IOException;

import javax.ejb.EJB;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.groundres.model.Court;
import org.groundres.model.User;
import org.groundres.services.CourtBean;
import org.groundres.services.UserBean;

@WebServlet("/admin")
public class AdminServlet extends HttpServlet {
       
    private static final long serialVersionUID = -7200746844902370749L;

    @EJB
    private UserBean userBean;
    
    @EJB
    private CourtBean courtBean;
    
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException,
            IOException {
        User loggedUser = (User) request.getSession().getAttribute("loggedUser");
        
        if (loggedUser != null && loggedUser.getUsername().equals("admin")) {
            request.getRequestDispatcher("admin.jsp").forward(request, response);            
        } else {
            response.sendRedirect("court");            
        }
    }

    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        request.setCharacterEncoding("UTF-8");
	    User host = new User();
	    host.setUsername(request.getParameter("userName"));
	    host.setPassword("abc123");
	    host.setRealName(request.getParameter("realName"));
	    userBean.addUser(host);
	    
	    Court court = new Court();
	    court.setName(request.getParameter("courtName"));
	    court.setDescription(request.getParameter("courtDescription"));
	    court.setAddress(request.getParameter("courtAddress"));
	    court.setPhone(request.getParameter("phone"));
	    court.setDefaultPrice(Integer.parseInt(request.getParameter("defaultPrice")));
	    court.setHost(host);
	    courtBean.addCourt(court);
	    
	    response.sendRedirect("court");
	}

}
