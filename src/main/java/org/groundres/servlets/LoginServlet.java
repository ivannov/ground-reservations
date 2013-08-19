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

@WebServlet("/login")
public class LoginServlet extends HttpServlet {

    private static final long serialVersionUID = 2291552633298694322L;
    
    @EJB
    private UserBean userBean;
    
    public LoginServlet() {
        super();
    }

	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		request.getRequestDispatcher("index.html").forward(request, response);
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		String username = request.getParameter("username");
		String password = request.getParameter("password");
		
		User user = userBean.findUserByUsernameAndPassword(username, password);
		
		if (user != null) {
		    request.getSession().setAttribute("loggedUser", user);
		    getServletContext().getRequestDispatcher("/court").forward(request, response);
		} else {
		    request.getRequestDispatcher("index.html").forward(request, response);
		}		
	}

}
