package org.groundres.services;

import static java.util.Calendar.*;

import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.TimeZone;

import org.groundres.model.Offer;

public class Util {
    
    public static String formatPrice(Offer offer) {        
        return offer == null ? "" : offer.getPrice().toString();
    }
    
    public static List<Integer> getNextTimeSlots(int nextHours) {
        Calendar cal = getCalendar();
        List<Integer> result = new ArrayList<Integer>();
        
        for (int i = 0; i < nextHours; i++) {
            cal.add(HOUR_OF_DAY, 1);
            result.add(cal.get(HOUR_OF_DAY));
        }
        
        return result;
    }
    
    public static String formatTimeSlot(int hour) {
        return new DecimalFormat("00").format(hour) + ":00";
    }
    
    public static Date toDate(int hour) {
        Calendar cal = getCalendar();
        cal.set(HOUR_OF_DAY, hour);
        cal.set(MINUTE, 0);
        cal.set(SECOND, 0);
        cal.set(MILLISECOND, 0);
        
        return cal.getTime();
    }

    static Calendar getCalendar() {
        return getInstance(TimeZone.getTimeZone(OfferBean.BG_TIME_ZONE_ID));
    }
    
    
}
