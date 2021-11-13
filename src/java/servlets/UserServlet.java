package servlets;

import dataaccess.ConnectionPool;
import dataaccess.RoleDB;
import dataaccess.UserDB;
import java.io.IOException;
import java.io.PrintWriter;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import models.Role;
import models.User;

public class UserServlet extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        HttpSession session = request.getSession();

        //initialze all variables
        session.setAttribute("editFlag", false);

        //get database results
        UserDB userDB = new UserDB();
        RoleDB roleDB = new RoleDB();

        try {

            ArrayList<User> userList = userDB.getAll();
            ArrayList<Role> roleList = roleDB.getAll();

            session.setAttribute("users", userList);
            session.setAttribute("roles", roleList);

        } catch (SQLException ex) {

            Logger.getLogger(UserServlet.class.getName()).log(Level.SEVERE, null, ex);
        }

        getServletContext().getRequestDispatcher("/WEB-INF/users.jsp").forward(request, response);
        return;

    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        HttpSession session = request.getSession();
        
        //find out what needs to be done
        String action = request.getParameter("action");
        
        //reset the error message
        session.setAttribute("errorMessage", "");

        //access the user DB
        UserDB userDB = new UserDB();

        switch (action) {

            case "Add":

                String email = request.getParameter("email");
                String firstname = request.getParameter("firstname");
                String lastname = request.getParameter("lastname");
                String password = request.getParameter("password");
                String role = request.getParameter("role");

                if (email != null && firstname != null && lastname != null && password != null && role != null) {

                    User newUser = new User(email, true, firstname, lastname, password, role);
                    try {
                        userDB.insert(newUser);
                        session.setAttribute("users", userDB.getAll());
                    } catch (SQLException s) {
                        System.out.println("Failed to add new user to the database");
                        session.setAttribute("errorMessage", "The email already exists in the database");
                    }

                    getServletContext().getRequestDispatcher("/WEB-INF/users.jsp").forward(request, response);
                    return;
                } else {
                    getServletContext().getRequestDispatcher("/WEB-INF/users.jsp").forward(request, response);
                    return;
                }

            case "edit":
                
                session.setAttribute("editFlag", true);
                email = request.getParameter("user");
                
                User user = null;
                try {
                    user = userDB.getUser(email);
                    
                } catch (SQLException s) {
                    System.out.println("Failed to retrieve user information from the database");
                }
                
                session.setAttribute("newemail", user.getEmail());
                session.setAttribute("newfirstname", user.getFirstname());
                session.setAttribute("newlastname", user.getLastname());
                session.setAttribute("newrole", user.getRole());

                
                getServletContext().getRequestDispatcher("/WEB-INF/users.jsp").forward(request, response);
                return;

            case "delete":
                
                session.setAttribute("editFlag", false);
                email = request.getParameter("user");
                try {
                    userDB.delete(email);
                    session.setAttribute("users", userDB.getAll());
                } catch (SQLException s) {
                    System.out.println("Failed to delete user from the database");
                }
                
                getServletContext().getRequestDispatcher("/WEB-INF/users.jsp").forward(request, response);
                return;

            case "Save":
                
                session.setAttribute("editFlag", false);
                String newEmail = request.getParameter("newemail");
                String newFirst = request.getParameter("newfirstname");
                String newLast = request.getParameter("newlastname");
                String newRole = request.getParameter("newrole");
                
                
                try {
                    userDB.update(newEmail, newFirst, newLast, newRole);
                    session.setAttribute("users", userDB.getAll());
                } catch (SQLException e) {
                    System.out.println("Failed to update user");
                }
                
                getServletContext().getRequestDispatcher("/WEB-INF/users.jsp").forward(request, response);
                return;

            case "Cancel":
                session.setAttribute("editFlag", false);
                
                getServletContext().getRequestDispatcher("/WEB-INF/users.jsp").forward(request, response);
                return;
              
            default:
                return;
        }
    }
}
