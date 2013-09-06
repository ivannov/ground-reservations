package org.groundres.services;

import static org.junit.Assert.*;

import javax.inject.Inject;

import org.groundres.model.User;
import org.jboss.arquillian.junit.Arquillian;
import org.junit.Test;
import org.junit.runner.RunWith;

@RunWith(Arquillian.class)
public class UserBeanTest extends ArquillianTestsParent {
    
    @Inject
    private UserBean userBean;

    @Test
    public void testAddUser() throws Exception {
        User newUser = new User("ivan_stefanov", "abc123", "Ивн Ст. Иванов", null);
        User returnedUser = userBean.saveUser(newUser);
        assertEquals(newUser, returnedUser);
        
        User queryUser = em.createQuery("SELECT u FROM User u WHERE u.username = 'ivan_stefanov'", 
                User.class).getSingleResult();
        assertEquals(newUser, queryUser);      
    }
    
    @Test
    public void testFindUserByUsernameAndPassword() throws Exception {
        User user = userBean.findUserByUsernameAndPassword("baydancho", "abc123");
        assertNotNull(user);
        assertEquals("бай Данчо", user.getRealName());
        
        assertNull(userBean.findUserByUsernameAndPassword("baydancho", "wrong"));
        assertNull(userBean.findUserByUsernameAndPassword("blah", "blah"));
    }
    
    @Test
    public void testUpdateOffer() throws Exception {
        User newUser = new User("ivan_stefanov", "abc123", "Ивн Ст. Иванов", null);
        User returnedUser = userBean.saveUser(newUser);

        returnedUser.setPassword("abcd1234");
        User queryUser = em.createQuery("SELECT u FROM User u WHERE u.username = 'ivan_stefanov'", 
                User.class).getSingleResult();
        assertEquals("abcd1234", queryUser.getPassword());      
    }
}
