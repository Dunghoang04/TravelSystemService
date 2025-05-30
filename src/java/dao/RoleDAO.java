/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package dao;

import dal.DBContext;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Vector;
import model.Role;

/**
 *
 * @author Nhat Anh
 */
public class RoleDAO extends DBContext {

    public Vector<Role> getAllRoles(String sql) {
        Vector<Role> listRoles = new Vector<>();
        try {
            PreparedStatement ptm = connection.prepareStatement(sql);
            ResultSet rs = ptm.executeQuery();

            while (rs.next()) {
                Role r = new Role(rs.getInt(1), rs.getString(2));
                listRoles.add(r);
            }
        } catch (SQLException ex) {
            ex.getStackTrace();
        }

        return listRoles;
    }

    public void insertRole(Role r) {
        String sql = "INSERT INTO [dbo].[Role]\n"
                + "           ([roleName])\n"
                + "     VALUES (?)";
        try {
            PreparedStatement ptm = connection.prepareStatement(sql);
            ptm.setString(1, r.getRoleName());
            ptm.executeUpdate();
        } catch (SQLException ex) {
            ex.getStackTrace();
        }

    }

    /*
    

    public void changeStatusRole(int rollID, int newStatus) {
        String sql = "UPDATE [dbo].[Role]\n"
                + "   SET [status] = ?\n"
                + " WHERE roleID = ?";
        try {
            PreparedStatement ptm = connection.prepareStatement(sql);
            ptm.setInt(1, newStatus);
            ptm.setInt(2, rollID);
            ptm.executeUpdate();
        } catch (SQLException ex) {
            ex.getStackTrace();
        }
    }

    public int deleteRole(int rollID) {
        int n = 0;
        String deleteSql = "DELETE FROM [dbo].[Role]\n"
                + "      WHERE roleID = ?";
        String checkSql = "select * from Account where roleID = ?";
        try {
            PreparedStatement checkPtm = connection.prepareStatement(checkSql);
            checkPtm.setInt(1, rollID);
            ResultSet rs = checkPtm.executeQuery();
            if (rs.next()) {
                changeStatusRole(rollID, 0);
                return n;
            }
            PreparedStatement deletePtm = connection.prepareStatement(deleteSql);
            deletePtm.setInt(1, rollID);
            n = deletePtm.executeUpdate();
        } catch (SQLException ex) {
            ex.getStackTrace();
        }
        return n;
    }*/
    
    public Role searchRole(int id) {
        String sql = "select * from Role where roleID = ?";
        try {
            PreparedStatement ptm = connection.prepareStatement(sql);
            ptm.setInt(1, id);
            ResultSet rs = ptm.executeQuery();
            if (rs.next()) {
                Role r = new Role(id, rs.getString(2));
                return r;
            }
        } catch (SQLException ex) {
            ex.getStackTrace();
        }
        return null;
    }

    
    public void updateRole(Role r) {
        String sql = "UPDATE [dbo].[Role]\n"
                + "   SET [roleName] = ?\n"
                + " WHERE roleID = ?";
        try {
            PreparedStatement ptm = connection.prepareStatement(sql);
            ptm.setString(1, r.getRoleName());
            ptm.setInt(2, r.getRoleID());
            ptm.executeUpdate();
        } catch (SQLException ex) {
            ex.getStackTrace();
        }

    }
    
    
    public static void main(String[] args) {
        String sql = "select * from Role";
        RoleDAO rdao = new RoleDAO();
        Vector<Role> list = rdao.getAllRoles(sql);
        /*for (Role role : list) {
            System.out.println(role);
        }*/
        
        

        /*Role r = new Role("Travel Agent");
        Role seRole = rdao.searchRole(r.getRoleID());
        if (seRole != null) {
            System.out.println("exist");
        } else {
            rdao.insertRole(r);
        }
        
        list = rdao.getAllRoles(sql);*/

        
        

        Role r1 = rdao.searchRole(3);
        if (r1 != null) {
            System.out.println("found");
            rdao.updateRole(new Role(r1.getRoleID(), "afdgq"));
        }
        list = rdao.getAllRoles(sql);
        for (Role role : list) {
            System.out.println(role);
        }      
        
                

        /*int n = rdao.deleteRole(1);
        if (n > 0 )
            System.out.println("Delete success");
        else
            System.out.println("Delete fail");
        list = rdao.getAllRoles(sql);
        for (Role role : list) {
            System.out.println(role);
        }*/

        for (Role role : list) {
            System.out.println(role);
        }
    }
    
}

