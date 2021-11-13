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
import models.Role;
import models.User;
import servlets.UserServlet;

/**
 *
 * @author mujta
 * @author Therin
 */
public class RoleDB {
    private List<Role> roleList;
    
    public RoleDB() {
        roleList = null;
    }

    public List<Role> getAll() {
        
        EntityManager em = DBUtil.getEmFactory().createEntityManager();
        
        try {
            roleList = em.createNamedQuery("Role.findAll", Role.class).getResultList();
        } finally {
            em.close();
        }

        
        
        return roleList;
    }

    
}
