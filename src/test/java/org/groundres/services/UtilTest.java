package org.groundres.services;

import static org.groundres.services.Util.*;
import static org.junit.Assert.*;

import java.util.Calendar;
import java.util.Date;
import java.util.List;

import org.groundres.model.Offer;
import org.junit.Test;

public class UtilTest {

    @Test
    public void testGetNextTimeSlotsFormatted() {
        List<Integer> nextTimeSlots = getNextTimeSlots(6);
        assertEquals(6, nextTimeSlots.size());
    }
    
    @Test
    public void testFormatHour() throws Exception {
        assertEquals("00:00", formatTimeSlot(0));
        assertEquals("15:00", formatTimeSlot(15));
    }
    
    @Test
    public void testFormatPrice() throws Exception {
        assertEquals("5", formatPrice(new Offer(new Date(), 5, null)));
        assertEquals("", formatPrice(null));
    }
    
    @Test
    public void testToDate() throws Exception {
        Calendar cal = getCalendar();
        cal.setTime(toDate(10));
        assertEquals(10, cal.get(Calendar.HOUR));
    }

}
