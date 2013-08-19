package org.groundres.services;

import static org.junit.Assert.*;
import static org.groundres.test.TestDataBuilder.*;

import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Map;

import javax.inject.Inject;
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

    @Test
    public void testFindOffersForCourt() {
        List<Offer> offersForCourt = offerBean.findAllOffersForCourt(new Court("Славия", "Овча купел"));
        assertEquals(24, offersForCourt.size());
        assertEquals("Славия", offersForCourt.get(0).getCourt().getName());
    }
    
    @Test
    public void testFindOffersForTimeframe() throws Exception {
        Date startDate = buildDate(10, 15, 23);
        Date endDate = buildDate(18, 21, 11);
        
        List<Offer> offersForTimeframe = offerBean.findAllOffersForTimeframe(startDate, endDate);
        assertEquals(8, offersForTimeframe.size());
        
        Calendar cal = Calendar.getInstance();
        for (Offer offer : offersForTimeframe) {
            cal.setTime(offer.getTimeSlot());
            int hourOfOffer = cal.get(Calendar.HOUR_OF_DAY);
            assertTrue(hourOfOffer > 10 && hourOfOffer <= 18);
            assertTrue(hourOfOffer % 2 == 0);
        }
    }
    
    @Test
    public void testFindAllOfferPricesForNextHours() throws Exception {
        Map<Court, List<Offer>> offerPrices = offerBean.findAllOfferPricesForNextHours(6);
        assertEquals(2, offerPrices.keySet().size());
        List<Offer> offers = offerPrices.get(new Court("Славия", "Овча купел"));
        assertEquals(6, offers.size());
        for (Offer offer : offers) {
            String formattedPrice = Util.formatPrice(offer);
            assertTrue("".equals(formattedPrice) || "10".equals(formattedPrice));
        }
    }

    @Test
    public void testAddCourt() {
        Date now = new Date();
        Offer newOffer = new Offer(now, 5f, null);
        Offer returnedOffer = offerBean.addOffer(newOffer);
        assertEquals(newOffer, returnedOffer);
        
        TypedQuery<Offer> query = em.createQuery("SELECT o FROM Offer o WHERE o.timeSlot = :now", Offer.class);
        query.setParameter("now", now);
        Offer queryOffer = query.getSingleResult();
        assertEquals(newOffer, queryOffer);        
    }
    

}
