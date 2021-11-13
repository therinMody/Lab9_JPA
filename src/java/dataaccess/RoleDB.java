package dataaccess;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import models.Role;
import models.User;
import servlets.UserServlet;

/**
 *
 * @author mujta
 * @author Therin
 */
public class RoleDB {
    private ArrayList<Role> roleList;
    
    public RoleDB() {
        roleList = null;
    }

    public ArrayList<Role> getAll() {
        
        ConnectionPool pool = ConnectionPool.getInstance();
        Connection connection = pool.getConnection();
        PreparedStatement ps;
        
        String role = "Select * From Role;";
        
        roleList = new ArrayList<>();
        
        try {
            //for role
            ps = connection.prepareStatement(role);
            ResultSet roles = ps.executeQuery();
            
            
            while (roles.next()) {
                int roleID = roles.getInt(1);
                String roleName = roles.getString(2);
                Role r = new Role(roleID, roleName);
                roleList.add(r);
            }
            
            pool.freeConnection(connection);
                   
        } catch (SQLException ex) {
            
            Logger.getLogger(UserServlet.class.getName()).log(Level.SEVERE, null, ex);
        }
        
        return roleList;
    }

    
}
