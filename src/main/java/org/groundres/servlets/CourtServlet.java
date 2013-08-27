package org.groundres.servlets;

import java.io.IOException;
import java.util.List;
import java.util.Map;

import javax.ejb.EJB;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.groundres.model.Court;
import org.groundres.model.Offer;
import org.groundres.model.User;
import org.groundres.services.CourtBean;
import org.groundres.services.OfferBean;
import static org.groundres.services.Util.*;

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
        
        if (idAttribute != null) {            
            Court court = courtBean.findCourtById(Long.parseLong(idAttribute));
            request.setAttribute("court", court);
            request.getRequestDispatcher("court.jsp").forward(request, response);
        } else {
            List<Court> courts = courtBean.findAllCourts();
            Map<Court, List<Offer>> nextOffers = offerBean.findAllOffersForNextHoursGroupedByCourt(courts, 6);
            
            User loggedUser = (User) request.getSession().getAttribute("loggedUser");
            if (loggedUser != null) {
                for (Court court : nextOffers.keySet()) {
                    if (court.getHost().equals(loggedUser)) {
                        request.getSession().setAttribute("offersForLoggedInUser", nextOffers.get(court));
                    }
                }                
            }            
            request.setAttribute("courts", courts);
            request.setAttribute("offers", nextOffers);
            request.setAttribute("bestOffers", offerBean.getBestOffers(nextOffers));
            request.getSession().setAttribute("timeSlots", getNextTimeSlots(6));

            request.getRequestDispatcher("courts.jsp").forward(request, response);            
        }
	}

    @Override
    @SuppressWarnings("unchecked")
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        List<Offer> offersBefore = (List<Offer>) request.getSession().getAttribute("offersForLoggedInUser");
        if (offersBefore != null) {
            int index = 0;
            for (Offer offer : offersBefore) {
                String offerPriceString = request.getParameter("" + index);
                if ("".equals(offerPriceString)) {
                    offerBean.deleteOffer(offer);
                } else {
                    if (offer == null) {
                        offer = new Offer();
                        offer.setCourt(((User) request.getSession().getAttribute("loggedUser")).getCourt());
                        offer.setTimeSlot(toDate(((List<Integer>)request.getSession().getAttribute("timeSlots")).get(index)));
                    }
                    offer.setPrice(Integer.valueOf(offerPriceString));
                    offerBean.saveOffer(offer);
                }
                index++;
            }
        }
        
        response.sendRedirect("court");
    }

    
}
