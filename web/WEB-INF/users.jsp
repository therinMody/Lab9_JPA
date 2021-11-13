<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html>

<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>Users</title>
    </head>
    <body style="text-align: center; padding: 1%; background-color: black; color: #d4af37 ">
        
        <div style="float: left; padding:1%; width: 15%; margin-right: 1%;">
            <form method="POST" action="">
                <h1>Add User</h1>
                <input type="text" name="email" placeholder="Email" required><br>
                <input type="text" name="firstname" placeholder="First Name" required><br>
                <input type="text" name="lastname" placeholder="Last Name" required><br>
                <input type="password" name="password" placeholder="Password" required><br>
                <select name="role" style="width: 13.3em; height: 1.9em;">
                    <option value="system admin">System Admin</option>
                    <option value="regular user">Regular User</option>
                    <option value="company admin">Company Admin</option>
                </select><br>
                <input type="submit" value="Add" name="action" style="width: 13.3em; height: 2em;">
            </form>
        </div>
        
        <div style=" float: left; padding:1%; width: 62%; margin: auto;">
            <form method="POST" action="">
                <h1>Manage User</h1>
                <table style="text-align: center; width: 100%; border: 1px solid black;">
                    <tr>
                        <td style="border: 1px solid black;">Email</td>
                        <td style="border: 1px solid black;">First Name</td>
                        <td style="border: 1px solid black;">Last Name</td>
                        <td style="border: 1px solid black;">Role</td>
                        <td style="border: 1px solid black;">Edit</td>
                        <td style="border: 1px solid black;">Delete</td>
                    </tr>

                    <tr>
                    </tr>
                    <tr>
                    </tr>
                    <tr>
                    </tr>

                    <c:forEach var="user" items="${users}">

                        <tr>
                            <td>${user.email}</td>
                            <td>${user.firstName}</td>
                            <td>${user.lastName}</td>
                            <td>${user.role}</td>
                            <td>
                                <button type="submit" value="${user.email}" name="user">Edit</button>
                                <input type="hidden" name="action" value="edit">
                            </td>
                            <td>
                                <form method="POST" action="">
                                    <button type="submit" value="${user.email}" name="user">Delete</button>
                                    <input type="hidden" name="action" value="delete">
                                </form>

                            </td>
                            
                            
                        </tr>

                    </c:forEach>

                </table>
            </form>
        </div>
        
        <c:if test="${editFlag}">
            <div style="float: right; padding:1%; width: 15%; margin: auto;">
                <form method="POST" action="">
                    <h1>Edit User</h1>
                    <input type="text" name="newemail" value="${newemail}" readonly><br>
                    <input type="text" name="newfirstname" value="${newfirstname}" required><br>
                    <input type="text" name="newlastname" value="${newlastname}" required><br>
                    <c:if test="${newrole == 'system admin'}">
                        <select name="newrole" value="${newrole}" style="width: 13.3em; height: 1.9em;">
                            <option value="system admin" selected>System Admin</option>
                            <option value="regular user" >Regular User</option>
                            <option value="company admin">Company Admin</option>
                        </select><br>
                    </c:if>
                    <c:if test="${newrole == 'regular user'}">
                        <select name="newrole" value="${newrole}" style="width: 13.3em; height: 1.9em;">
                            <option value="system admin">System Admin</option>
                            <option value="regular user" selected>Regular User</option>
                            <option value="company admin">Company Admin</option>
                        </select><br>
                    </c:if>
                    <c:if test="${newrole == 'company admin'}">
                        <select name="newrole" value="${newrole}" style="width: 13.3em; height: 1.9em;">
                            <option value="system admin">System Admin</option>
                            <option value="regular user">Regular User</option>
                            <option value="company admin" selected>Company Admin</option>
                        </select><br>
                    </c:if>
                    
                    <input type="submit" value="Save" name="action" style="width: 13.3em; height: 2em;"><br>
                    <input type="submit" value="Cancel" name="action" style="width: 13.3em; height: 2em;">
                </form>
            </div>
        </c:if>
        
        <h2 style="color: red;">${errorMessage}</h2>
    </body>
</html>