package org.groundres.services;

import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;
import java.util.TimeZone;

import org.groundres.model.Offer;

public class Util {
    
    public static String formatPrice(Offer offer) {
        if (offer == null) {
            return "";
        }
        return new DecimalFormat("#.00").format(offer.getPrice());
    }
    
    public static List<String> getNextTimeSlotsFormatted(int nextHours) {
        Calendar cal = Calendar.getInstance(TimeZone.getTimeZone(OfferBean.BG_TIME_ZONE_ID));
        List<String> result = new ArrayList<String>();
        
        for (int i = 0; i < nextHours; i++) {
            cal.add(Calendar.HOUR_OF_DAY, 1);
            result.add(new DecimalFormat("00").format(cal.get(Calendar.HOUR_OF_DAY)) + ":00");
        }
        
        return result;
    }
}
