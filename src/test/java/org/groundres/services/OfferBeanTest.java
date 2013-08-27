package org.groundres.services;

import static org.junit.Assert.*;

import java.util.Arrays;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.inject.Inject;
import javax.persistence.NoResultException;
import javax.persistence.TypedQuery;

import org.groundres.model.Court;
import org.groundres.model.Offer;
import org.jboss.arquillian.junit.Arquillian;
import org.junit.Test;
import org.junit.runner.RunWith;

@RunWith(Arquillian.class)
public class OfferBeanTest extends ArquillianTestsParent {

    @Inject
    private OfferBean offerBean;
    
    @Inject
    private CourtBean courtBean;

    @Test
    public void testFindOffersForCourt() {
        List<Offer> offersForCourt = offerBean.findAllOffersForCourt(new Court("Славия", "Овча купел"));
        assertEquals(24, offersForCourt.size());
        assertEquals("Славия", offersForCourt.get(0).getCourt().getName());
    }
    
    @Test
    public void testFindOffersForTimeframe() throws Exception {
        Date startDate = Util.toDate(10, 15, 23);
        Date endDate = Util.toDate(18, 21, 11);
        
        List<Offer> offersForTimeframe = offerBean.findAllOffersForTimeframe(startDate, endDate);
        assertEquals(8, offersForTimeframe.size());
        
        Calendar cal = Util.getCalendar();
        for (Offer offer : offersForTimeframe) {
            cal.setTime(offer.getTimeSlot());
            int hourOfOffer = cal.get(Calendar.HOUR_OF_DAY);
            assertTrue(hourOfOffer > 10 && hourOfOffer <= 18);
            assertTrue(hourOfOffer % 2 == 0);
        }
    }
    
    @Test
    public void testFindAllOfferPricesForNextHours() throws Exception {
        Map<Court, List<Offer>> offerPrices = offerBean.findAllOffersForNextHoursGroupedByCourt(courtBean.findAllCourts(), 6);
        assertEquals(2, offerPrices.keySet().size());
        List<Offer> offers = offerPrices.get(new Court("Славия", "Овча купел"));
        assertEquals(6, offers.size());
        for (Offer offer : offers) {
            String formattedPrice = Util.formatPrice(offer);
            assertTrue("".equals(formattedPrice) || "10".equals(formattedPrice));
        }
    }

    @Test
    public void testAddOffer() {
        Date now = new Date();
        Offer newOffer = saveSampleOffer(now);
        
        assertEquals(newOffer, getQueriedOffer(now));        
    }

    @Test
    public void testUpdateOffer() throws Exception {
        Date now = new Date();
        Offer newOffer = saveSampleOffer(now);
        
        newOffer.setPrice(10);
        assertEquals(Integer.valueOf(10), getQueriedOffer(now).getPrice());        
    }

    @Test
    public void testDeleteOffer() throws Exception {
        Date now = new Date();
        Offer newOffer = saveSampleOffer(now);
        assertNotNull(getQueriedOffer(now));
        
        offerBean.deleteOffer(newOffer);
        try {
            getQueriedOffer(now);
            fail();
        } catch (NoResultException nre) {
            // OK
        }
    }
    
    @Test
    public void testDeleteNonExistingOffer() throws Exception {
        offerBean.deleteOffer(new Offer(new Date(), 10, null));
        offerBean.deleteOffer(null);
    }
    
    @Test
    public void testGetBestOffers() throws Exception {
        Map<Court, List<Offer>> offersForCourts = buildTestOffersMap();
        List<List<Offer>> bestOffers = offerBean.getBestOffers(offersForCourts);
        assertEquals(4, bestOffers.size());
        assertEquals(1, bestOffers.get(0).size());
        assertEquals("Akademik", bestOffers.get(0).get(0).getCourt().getName());
        assertEquals(1, bestOffers.get(1).size());
        assertEquals("Slavia", bestOffers.get(1).get(0).getCourt().getName());
        assertEquals(2, bestOffers.get(2).size());
        assertTrue(bestOffers.get(2).contains(new Offer(Util.toDate(12), 5, new Court("Slavia", "Ovcha kupel"))));
        assertTrue(bestOffers.get(2).contains(new Offer(Util.toDate(12), 5, new Court("Malinova dolina", "Malinova dolina"))));
        assertEquals(0, bestOffers.get(3).size());
        assertFalse(bestOffers.get(3).contains(new Offer(Util.toDate(12), 5, new Court("Malinova dolina", "Malinova dolina"))));
    }
    
    @Test
    public void testInsertDaylyOffers() throws Exception {
        em.createQuery("DELETE FROM Offer").executeUpdate();
        
        Court slavia = courtBean.findCourtByName("Славия");        
        offerBean.insertDaylyOffers(slavia, Util.getCalendar().get(Calendar.DAY_OF_YEAR));
        
        List<Offer> daylyOffers = offerBean.findAllOffersForCourt(slavia);
        assertEquals(Court.DEFAULT_END_HOUR - Court.DEFAULT_START_HOUR + 1, daylyOffers.size());
        assertEquals("Славия", daylyOffers.get(0).getCourt().getName());
        assertEquals(slavia.getDefaultPrice(), daylyOffers.get(0).getPrice());
        for (Offer offer : daylyOffers) {
            assertTrue(offer.getTimeSlot().compareTo(Util.toDate(Court.DEFAULT_START_HOUR)) >= 0);
            assertTrue(offer.getTimeSlot().compareTo(Util.toDate(Court.DEFAULT_END_HOUR)) <= 0);            
        }
    }
    
    private Offer saveSampleOffer(Date now) {
        Offer newOffer = new Offer(now, 5, null);
        return offerBean.saveOffer(newOffer);
    }
    
    private Offer getQueriedOffer(Date now) {
        TypedQuery<Offer> query = em.createQuery("SELECT o FROM Offer o WHERE o.timeSlot = :now", Offer.class);
        query.setParameter("now", now);
        return query.getSingleResult();
    }
    
    private Map<Court, List<Offer>> buildTestOffersMap() {
        Map<Court, List<Offer>> offersForCourts = new HashMap<Court, List<Offer>>();
        
        Court court1 = new Court("Akademik", "Geo Milev");
        List<Offer> offers1 = Arrays.asList(new Offer(Util.toDate(10), 5, court1),
                null,
                new Offer(Util.toDate(12), 9, court1),
                null);
        offersForCourts.put(court1, offers1);
        
        Court court2 = new Court("Slavia", "Ovcha kupel");
        List<Offer> offers2 = Arrays.asList(new Offer(Util.toDate(10), 8, court2),
                new Offer(Util.toDate(11), 7, court2),
                new Offer(Util.toDate(12), 5, court2),
                null);
        offersForCourts.put(court2, offers2);
        
        Court court3 = new Court("Malinova dolina", "Malinova dolina");
        List<Offer> offers3 = Arrays.asList(new Offer(Util.toDate(10), 6, court3),
                new Offer(Util.toDate(11), 10, court3),
                new Offer(Util.toDate(12), 5, court3),
                null);
        offersForCourts.put(court3, offers3);
        
        return offersForCourts;
    }
}
