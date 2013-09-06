package org.groundres.servlets;

import java.io.IOException;

import javax.ejb.EJB;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.groundres.model.User;
import org.groundres.services.UserBean;

/**
 * Servlet implementation class PasswordServlet
 */
@WebServlet("/updatePassword")
public class PasswordServlet extends HttpServlet {

    private static final long serialVersionUID = -8725806212776263300L;

    @EJB
    private UserBean userBean;
       
    public PasswordServlet() {
        super();
    }

	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        response.sendRedirect("court");
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
	    User loggedUser = (User) request.getSession().getAttribute("loggedUser");
	    
	    if (loggedUser != null) {
            String password = request.getParameter("newPassword");
            String confirmPassword = request.getParameter("confirmNewPassword");
            
            if (!password.equals(confirmPassword)) {
                response.sendRedirect("court?id=" + loggedUser.getCourt().getId() + "&confirmFail");
            } else {                
                loggedUser.setPassword(password);
                userBean.saveUser(loggedUser);                
                response.sendRedirect("court");
            }            
	    } else {	        
	        response.sendRedirect("court");
	    }
	    
	}

}
