package dataaccess;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.persistence.EntityManager;
import javax.persistence.EntityTransaction;
import models.Role;
import models.User;
import servlets.UserServlet;

/**
 *
 * @author mujta
 * @author Therin
 */
public class UserDB {

    private List<Role> roleList;
    private List<User> userList;


    public List<User> getAll() throws SQLException {
        
        EntityManager em = DBUtil.getEmFactory().createEntityManager();
        
        try {
            userList = em.createNamedQuery("User.findAll", User.class).getResultList();
        } finally {
            em.close();
        }

        
        return userList;
    }
    
    public User get(String email){
        
        EntityManager em = DBUtil.getEmFactory().createEntityManager();
        User user = null;
        try {
            user = em.find(User.class, email);
        } finally {
            em.close();
        }
    
        return user;
    }

    public void insert(User user) throws SQLException {
        
        EntityManager em = DBUtil.getEmFactory().createEntityManager();
        EntityTransaction trans = em.getTransaction();
        
        try {
            //Role role = user.getRole();
            trans.begin();
            em.persist(user);
            //em.merge(role);
            trans.commit();
        } catch (Exception ex){
            trans.rollback();
        } finally {
            em.close();
        }
       
    }

    public void delete(String email) throws SQLException {

        EntityManager em = DBUtil.getEmFactory().createEntityManager();
        EntityTransaction trans = em.getTransaction();
        
        try {
            trans.begin();
            em.remove(em.find(User.class, email));
            trans.commit();
        } catch (Exception ex){
            trans.rollback();
        } finally {
            em.close();
        }

    }

    public void update(String email, String newFirst, String newLast, String newRole){
        
        EntityManager em = DBUtil.getEmFactory().createEntityManager();
        EntityTransaction trans = em.getTransaction();
        
        //access the user DB
        UserDB userDB = new UserDB();
        User user = userDB.get(email);
        user.setFirstName(newFirst);
        user.setLastName(newLast);
        
        
        int checkRole = 0;
        switch (newRole) {
            case "system admin":
                checkRole = 1;
                break;
            case "regular user":
                checkRole = 2;
                break;
            case "company admin":
                checkRole = 3;
                break;
        }
        
        user.setRole(new Role(checkRole));

        
        try {
            trans.begin();
            em.merge(user);
            trans.commit();
        } catch (Exception ex){
            trans.rollback();
        } finally {
            em.close();
        }

    }


}
