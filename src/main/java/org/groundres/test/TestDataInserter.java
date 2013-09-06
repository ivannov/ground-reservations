package org.groundres.test;

import java.util.Calendar;

import javax.annotation.PostConstruct;
import javax.ejb.EJB;
import javax.ejb.LocalBean;
import javax.ejb.Singleton;
import javax.ejb.Startup;

import org.groundres.model.Court;
import org.groundres.model.User;
import org.groundres.services.CourtBean;
import org.groundres.services.OfferBean;
import org.groundres.services.UserBean;
import org.groundres.services.Util;

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
        TestDataBuilder data = TestDataBuilder.build(false);
        
        for (User user : data.getUsers()) {            
            userBean.saveUser(user);
        }
        
        for (Court court : data.getCourts()) {
            courtBean.addCourt(court);
            offerBean.insertDaylyOffers(court, Util.getCalendar().get(Calendar.DAY_OF_YEAR));
            offerBean.insertDaylyOffers(court, Util.getCalendar().get(Calendar.DAY_OF_YEAR) + 1);
        }        
    }
}