package org.groundres.services;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

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

    public Map<Court, List<Offer>> findAllOffersForNextHoursGroupedByCourt(List<Court> courts, int nextHours) {
        Map<Court, List<Offer>> result = new HashMap<Court, List<Offer>>(courts.size());
        
        for (Court court : courts) {
            result.put(court, initializeCourtOffers(nextHours));
        }
        
        Date now = new Date();
        List<Offer> allOffersForTimeframe = findAllOffersForTimeframe(now, getTimeStampAfter(now, nextHours));                
        
        for (Offer offer : allOffersForTimeframe) {
            Court court = offer.getCourt();
            List<Offer> courtOffers = result.get(court);
            
            int hourDifference = (int) (offer.getTimeSlot().getTime() - now.getTime())
                    / MILLIS_PER_HOUR;
            courtOffers.set(hourDifference, offer);
        }
        
        return result;
    }
    
    private Date getTimeStampAfter(Date currentTimestamp, int nextHours) {
        Calendar nextCal = Util.getCalendar();
        nextCal.setTime(currentTimestamp);
        nextCal.add(Calendar.HOUR_OF_DAY, nextHours);
        return nextCal.getTime();
    }

    private List<Offer> initializeCourtOffers(int nextHours) {
        List<Offer> result = new ArrayList<Offer>();
        
        for (int i = 0; i < nextHours; i++) {
            result.add(null);
        }
        
        return result;
    }
    
    public Offer saveOffer(Offer offer) {
        Long id = offer.getId();
        if (id == null || em.find(Offer.class, offer.getId()) == null) {
            em.persist(offer);
        } else {
            em.merge(offer);
        }
        return offer;
    }
    
    public void deleteOffer(Offer offer) {
        if (offer != null && offer.getId() != null) {
            em.remove(em.find(Offer.class, offer.getId()));
        }
    }

    public List<List<Offer>> getBestOffers(Map<Court, List<Offer>> offersForCourts) {
        List<List<Offer>> bestOffers = new ArrayList<List<Offer>>();
        
        for (Court court : offersForCourts.keySet()) {
            List<Offer> offers = offersForCourts.get(court);
            for (int i = 0; i < offers.size(); i++) {
                Offer currentOffer = offers.get(i);
                if (bestOffers.size() == i) {
                    bestOffers.add(initializeBestOffersWithOffer(currentOffer));
                } else {
                    if (currentOffer != null) {
                        List<Offer> bestOffersForTimeslot = bestOffers.get(i);
                        if (bestOffersForTimeslot.size() > 0) {
                            Offer currentBest = bestOffersForTimeslot.get(0);
                            if (currentBest.getPrice() > currentOffer.getPrice()) {
                                bestOffersForTimeslot.clear();
                                bestOffersForTimeslot.add(currentOffer);
                            } else if (currentBest.getPrice() == currentOffer.getPrice()) {
                                bestOffersForTimeslot.add(currentOffer);
                            }
                        } else {
                            bestOffersForTimeslot.add(currentOffer);
                        }
                    }
                }
            }
        }
        
        return bestOffers;
    }
    
    private List<Offer> initializeBestOffersWithOffer(Offer currentOffer) {
        List<Offer> currentBestOffers = new ArrayList<Offer>();
        if (currentOffer != null) {
            currentBestOffers.add(currentOffer);
        }
        return currentBestOffers;
    }
    
    public void insertDaylyOffers(Court court, int dayOfYear) {
        for (int i = Court.DEFAULT_START_HOUR; i <= Court.DEFAULT_END_HOUR; i++) {
            Calendar cal = Util.getCalendar();
            cal.setTime(Util.toDate(i));
            cal.set(Calendar.DAY_OF_YEAR, dayOfYear);
            saveOffer(new Offer(cal.getTime(), court.getDefaultPrice(), court));
        }
    }

}