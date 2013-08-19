package org.groundres.services;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.TimeZone;

import javax.ejb.LocalBean;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.TypedQuery;

import org.groundres.model.Court;
import org.groundres.model.Offer;

@Stateless
@LocalBean
public class OfferBean {
    
    static final String BG_TIME_ZONE_ID = "Europe/Sofia";
    private static final int MILLIS_PER_HOUR = 1000 * 60 * 60;
    
    @PersistenceContext
    EntityManager em;
    
    public List<Offer> findAllOffersForCourt(Court court) {
        TypedQuery<Offer> query = em.createNamedQuery("findAllOffersForCourt", Offer.class);
        query.setParameter("courtName", court.getName());

        return query.getResultList();
    }
    
    public List<Offer> findAllOffersForTimeframe(Date fromTime, Date toTime) {
        
        TypedQuery<Offer> query = em.createNamedQuery("findAllOffersForTimeframe", Offer.class);
        query.setParameter("fromTime", fromTime);
        query.setParameter("toTime", toTime);
        
        return query.getResultList();
    }

    public Map<Court, List<Offer>> findAllOfferPricesForNextHours(int nextHours) {
        Map<Court, List<Offer>> groupedOffers = new HashMap<Court, List<Offer>>();
        
        Date now = new Date();
        List<Offer> allOffersForTimeframe = getAllOffersForNextHours(now, nextHours);                
        
        for (Offer offer : allOffersForTimeframe) {
            Court court = offer.getCourt();
            List<Offer> courtOffers = groupedOffers.get(court);
            
            if (courtOffers == null) {
                courtOffers = initializeCourtOffers(nextHours);
                groupedOffers.put(court, courtOffers);
            }
            
            int hourDifference = (int) (offer.getTimeSlot().getTime() - now.getTime())
                    / MILLIS_PER_HOUR;
            courtOffers.set(hourDifference, offer);
        }
        
        return groupedOffers;
    }

    private List<Offer> initializeCourtOffers(int nextHours) {
        List<Offer> result = new ArrayList<Offer>();
        
        for (int i = 0; i < nextHours; i++) {
            result.add(null);
        }
        
        return result;
    }

    private List<Offer> getAllOffersForNextHours(Date currentTimestamp, int nextHours) {
        Calendar nextCal = Calendar.getInstance(TimeZone.getTimeZone(BG_TIME_ZONE_ID));
        nextCal.setTime(currentTimestamp);
        nextCal.add(Calendar.HOUR_OF_DAY, nextHours);
        return findAllOffersForTimeframe(currentTimestamp, nextCal.getTime());
    }
    
    public Offer addOffer(Offer offer) {
        em.persist(offer);
        return offer;
    }

}