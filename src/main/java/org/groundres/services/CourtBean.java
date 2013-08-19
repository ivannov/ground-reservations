package org.groundres.services;

import java.util.List;

import javax.ejb.LocalBean;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.TypedQuery;

import org.groundres.model.Court;
import org.groundres.model.User;

@Stateless
@LocalBean
public class CourtBean {
    
    @PersistenceContext
    private EntityManager em;
    
    public Court findCourtById(long id) {
        return em.find(Court.class, id);
    }
    
    public Court findCourtByName(String name) {
        TypedQuery<Court> query = em.createNamedQuery("findCourtByName", Court.class);
        query.setParameter("name", name);
        return query.getSingleResult();
    }
    
    public Court findCourtByHost(User host) {
        TypedQuery<Court> query = em.createNamedQuery("findCourtByHost", Court.class);
        query.setParameter("hostUsername", host.getUsername());
        return query.getSingleResult();        
    }
    
    public List<Court> findAllCourts() {
        TypedQuery<Court> query = em.createNamedQuery("findAllCourts", Court.class);
        return query.getResultList();
    }
    
    public Court addCourt(Court court) {
        em.persist(court);
        return court;
    }
}