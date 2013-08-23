package org.groundres.test;

import javax.annotation.PostConstruct;
import javax.ejb.EJB;
import javax.ejb.LocalBean;
import javax.ejb.Singleton;
import javax.ejb.Startup;

import org.groundres.model.Court;
import org.groundres.model.Offer;
import org.groundres.model.User;
import org.groundres.services.CourtBean;
import org.groundres.services.OfferBean;
import org.groundres.services.UserBean;

@Singleton
@LocalBean
@Startup
public class TestDataInserter {

    @EJB
    private UserBean userBean;
    @EJB
    private CourtBean courtBean;
    @EJB
    private OfferBean offerBean;
    
    @PostConstruct
    public void insertData() {
        TestDataBuilder data = TestDataBuilder.build();
        
        for (User user : data.getUsers()) {            
            userBean.addUser(user);
        }
        
        for (Court court : data.getCourts()) {
            courtBean.addCourt(court);
        }
        
        for (Offer offer : data.getOffers()) {
            offerBean.saveOffer(offer);
        }
    }
}