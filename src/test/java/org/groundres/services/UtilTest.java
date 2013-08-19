package org.groundres.services;

import static org.junit.Assert.*;

import java.util.List;

import org.junit.Test;

public class UtilTest {

    @Test
    public void testGetNextTimeSlotsFormatted() {
        List<String> nextTimeSlots = Util.getNextTimeSlotsFormatted(6);
        assertEquals(6, nextTimeSlots.size());
        
        for (String timeSlot : nextTimeSlots) {
            assertEquals(5, timeSlot.length());
            assertEquals(':', timeSlot.charAt(2));
        }
    }

}
