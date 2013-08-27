package org.groundres.services;

import static org.junit.Assert.*;

import java.util.HashSet;
import java.util.List;

import javax.inject.Inject;

import org.groundres.model.Court;
import org.groundres.model.Offer;
import org.groundres.model.User;
import org.jboss.arquillian.junit.Arquillian;
import org.junit.Test;
import org.junit.runner.RunWith;

@RunWith(Arquillian.class)
public class CourtBeanTest extends ArquillianTestsParent {

    @Inject
    private CourtBean courtBean;
    
    @Test
    public void testAddCourt() {
        Court newCourt = new Court("Малинова Долина", "Кортовете на Малинова Долина", "0888888888", "кв. Малинова Долина", 10, null, new HashSet<Offer>());
        Court returnedCourt = courtBean.addCourt(newCourt);
        assertEquals(newCourt, returnedCourt);
        
        Court queryCourt = em.createQuery("SELECT c FROM Court c WHERE c.name = 'Малинова Долина'", Court.class).getSingleResult();
        assertEquals(newCourt, queryCourt);        
    }
    
    @Test
    public void testFindCourtByName() throws Exception {
        Court returnedCourt = courtBean.findCourtByName("Славия");
        assertNotNull(returnedCourt);
        assertEquals("Овча купел", returnedCourt.getAddress());
    }
    
    @Test
    public void testFindCourtByHost() throws Exception {
        Court returnedCourt = courtBean.findCourtByHost(new User("baydancho", null, null, null));
        assertNotNull(returnedCourt);
        assertEquals("Славия", returnedCourt.getName());
    }
    
    @Test
    public void testFindAllCourts() throws Exception {
        List<Court> allCourts = courtBean.findAllCourts();
        assertEquals(2, allCourts.size());
        assertTrue(allCourts.contains(new Court("Славия", "Овча купел")));
    }
}
