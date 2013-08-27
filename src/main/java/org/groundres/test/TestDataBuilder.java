package org.groundres.test;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashSet;
import java.util.List;

import org.groundres.model.Court;
import org.groundres.model.Offer;
import org.groundres.model.User;
import org.groundres.services.Util;

import static org.groundres.services.Util.*;

public class TestDataBuilder {

    private static List<User> users;
    private static List<Court> courts;
    private static List<Offer> offers;
    
    private TestDataBuilder() {
        users = new ArrayList<User>();
        courts = new ArrayList<Court>();
        offers = new ArrayList<Offer>();
    }
    
    public static TestDataBuilder build(boolean insertTestOffers) {
        TestDataBuilder instance = new TestDataBuilder();
        User bayDancho = new User("baydancho", "abc123", "бай Данчо", null);
        User dencho = new User("dencho", "abc123", "Денчо Митев", null);
        User admin = new User("admin", "abc123", "Цветан Цветанов", null);
        users.add(bayDancho);
        users.add(dencho);
        users.add(admin);
        Court slavia = new Court("Славия", "Кортовете на стадион Славия", "0888888888", "Овча купел",
                10, bayDancho, new HashSet<Offer>());
        Court akademik = new Court("Академик", "Кортовете на стадион Академик до зала Фестивална",
                "0888888888", "Гео Милев", 10, dencho, new HashSet<Offer>());
        courts.add(slavia);
        courts.add(akademik);
        if (insertTestOffers) {
            for (int i = 0; i < 24; i++) {
                if (i % 2 == 0) {
                    offers.add(new Offer(toDate(i), 10, slavia));
                    offers.add(new Offer(toDate(i), 10, akademik));
                    offers.add(new Offer(buildDateTomorrow(i), 10, slavia));
                    offers.add(new Offer(buildDateTomorrow(i), 10, akademik));
                }
            }            
        }
        
        return instance;
    }
    
    public List<User> getUsers() {
        return users;
    }
    
    public List<Court> getCourts() {
        return courts;
    }

    public List<Offer> getOffers() {
        return offers;
    }
    
    public static Date buildDateTomorrow(int hours) {
        Calendar cal = Util.getCalendar();
        cal.setTime(toDate(hours));
        cal.add(Calendar.DAY_OF_YEAR, 1);
        return cal.getTime();
    }
    
}
