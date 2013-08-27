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

    static final String BG_TIME_ZONE_ID = "Europe/Sofia";

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
    
    public static Date toDate(int hours) {
        return toDate(hours, 0, 0);
    }
    
    public static Date toDate(int hours, int minutes, int seconds) {
        Calendar cal = getCalendar();
        cal.set(HOUR_OF_DAY, hours);
        cal.set(MINUTE, minutes);
        cal.set(SECOND, seconds);
        cal.set(MILLISECOND, 0);
        return cal.getTime();        
    }

    public static Calendar getCalendar() {
        return getInstance(TimeZone.getTimeZone(BG_TIME_ZONE_ID));
    }
    
    
}
