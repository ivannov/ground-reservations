package org.groundres.services;

import javax.inject.Inject;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.transaction.UserTransaction;

import org.groundres.model.Court;
import org.groundres.model.Offer;
import org.groundres.model.User;
import org.groundres.test.TestDataBuilder;
import org.jboss.arquillian.container.test.api.Deployment;
import org.jboss.shrinkwrap.api.ShrinkWrap;
import org.jboss.shrinkwrap.api.asset.EmptyAsset;
import org.jboss.shrinkwrap.api.spec.JavaArchive;
import org.junit.After;
import org.junit.Before;

public abstract class ArquillianTestsParent {

    @PersistenceContext
    protected EntityManager em;
    @Inject
    private UserTransaction utx;

    @Deployment
    public static JavaArchive createDeployment() {
        return ShrinkWrap.create(JavaArchive.class, "persistenceTest.jar")
                .addPackage(OfferBean.class.getPackage())
                .addPackage(ArquillianTestsParent.class.getPackage())
                .addPackage(Offer.class.getPackage())
                .addClass(TestDataBuilder.class)
                .addAsManifestResource("META-INF/persistence.xml", "persistence.xml")
                .addAsManifestResource(EmptyAsset.INSTANCE, "beans.xml");
    }

    @Before
    public void setup() throws Exception {
        clearData();
        insertData();
        startTransaction();
    }

    private void clearData() throws Exception {
        utx.begin();
        em.joinTransaction();
        em.createQuery("DELETE FROM Offer").executeUpdate();
        em.createQuery("DELETE FROM Court").executeUpdate();
        em.createQuery("DELETE FROM User").executeUpdate();
        utx.commit();
    }

    private void insertData() throws Exception {
        utx.begin();
        em.joinTransaction();
        
        TestDataBuilder builder = TestDataBuilder.build(true);
        
        for (User user : builder.getUsers()) {            
            em.persist(user);
        }

        for (Court court : builder.getCourts()) {            
            em.persist(court);
        }
        
        for (Offer offer : builder.getOffers()) {
            em.persist(offer);
        }
        
        utx.commit();
        em.clear();
    }

    private void startTransaction() throws Exception {
        utx.begin();
        em.joinTransaction();
    }

    
    @After
    public void tearDown() throws Exception {
        utx.commit();
    }
}
