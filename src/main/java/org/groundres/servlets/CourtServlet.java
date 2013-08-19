package org.groundres.servlets;

import java.io.IOException;

import javax.ejb.EJB;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.groundres.model.Court;
import org.groundres.services.CourtBean;
import org.groundres.services.OfferBean;
import org.groundres.services.Util;

@WebServlet(urlPatterns = "/court")
public class CourtServlet extends HttpServlet {
    
    private static final long serialVersionUID = 4839917444838372387L;

    @EJB
    private CourtBean courtBean;
    
    @EJB
    private OfferBean offerBean;
    
    @Override
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String idAttribute = request.getParameter("id");
        
        if (idAttribute == null) {
            request.setAttribute("courts", courtBean.findAllCourts());
            request.setAttribute("offers", offerBean.findAllOfferPricesForNextHours(6));
            request.setAttribute("timeSlots", Util.getNextTimeSlotsFormatted(6));
            request.getRequestDispatcher("courts.jsp").forward(request, response);            
        } else {
            Court court = courtBean.findCourtById(Long.parseLong(idAttribute));
            request.setAttribute("court", court);
            request.getRequestDispatcher("court.jsp").forward(request, response);
        }
	}

    @Override
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
	}

}
