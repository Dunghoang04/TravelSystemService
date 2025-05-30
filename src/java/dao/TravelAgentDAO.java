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
import model.TravelAgent;
import java.sql.Date;
import java.sql.Statement;
import java.time.LocalDate;

/**
 *
 * @author Nhat Anh
 */
public class TravelAgentDAO extends DBContext {

    public Vector<TravelAgent> getAllTravelAgent() {
        Vector<TravelAgent> list = new Vector<>();
        String sql = "SELECT t.travelAgentID, t.travelAgentName, t.travelAgentAddress, t.travelAgentGmail, t.hotLine, t.taxCode, "
                + "t.establishmentDate, t.representativeIDCard, t.dateOfIssue, u.userID, u.gmail, u.roleID, u.password, u.firstName, "
                + "u.lastName, u.dob, u.gender, u.address, u.phone, u.create_at, u.update_at, u.status "
                + "FROM TravelAgent t JOIN [User] u ON t.userID = u.userID";
        try {
            PreparedStatement ptm = connection.prepareStatement(sql);
            ResultSet rs = ptm.executeQuery();
            while (rs.next()) {
                TravelAgent agent = new TravelAgent(
                        rs.getInt("travelAgentID"),
                        rs.getString("travelAgentName"),
                        rs.getString("travelAgentAddress"),
                        rs.getString("travelAgentGmail"),
                        rs.getString("hotLine"),
                        rs.getString("taxCode"),
                        rs.getDate("establishmentDate"),
                        rs.getString("representativeIDCard"),
                        rs.getDate("dateOfIssue"),
                        rs.getInt("userID"),
                        rs.getString("gmail"),
                        rs.getInt("roleID"),
                        rs.getString("password"),
                        rs.getString("firstName"),
                        rs.getString("lastName"),
                        rs.getDate("dob"),
                        rs.getString("gender"),
                        rs.getString("address"),
                        rs.getString("phone"),
                        rs.getDate("create_at"),
                        rs.getDate("update_at"),
                        rs.getInt("status")
                );
                list.add(agent);
            }
        } catch (SQLException ex) {
            ex.printStackTrace();
        }
        return list;
    }

    public boolean isEmailExists(String email) {
        String sql = "SELECT COUNT(*) FROM [User] WHERE gmail = ?";
        try {
            PreparedStatement ptm = connection.prepareStatement(sql);
            ptm.setString(1, email);
            ResultSet rs = ptm.executeQuery();
            if (rs.next()) {
                return rs.getInt(1) > 0;
            }
        } catch (SQLException ex) {
            ex.printStackTrace();
        }
        return false;
    }

    public void insertTravelAgent(TravelAgent agent) {

        int userID = -1;
        String userSql = "INSERT INTO [User] (gmail, roleID, password, firstName, lastName, dob, gender, address, phone, create_at, update_at, status) VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?)";
        try {
            PreparedStatement userPtm = connection.prepareStatement(userSql, Statement.RETURN_GENERATED_KEYS);
            userPtm.setString(1, agent.getGmail());
            userPtm.setInt(2, agent.getRoleID());
            userPtm.setString(3, agent.getPassword());
            userPtm.setString(4, agent.getFirstName());
            userPtm.setString(5, agent.getLastName());
            userPtm.setDate(6, agent.getDob());
            userPtm.setString(7, agent.getGender());
            userPtm.setString(8, agent.getAddress());
            userPtm.setString(9, agent.getPhone());
            userPtm.setDate(10, new Date(agent.getCreateDate().getTime()));
            userPtm.setDate(11, new Date(agent.getUpdateDate().getTime()));
            userPtm.setInt(12, agent.getStatus());

            int affectedRows = userPtm.executeUpdate();
            if (affectedRows > 0) {
                ResultSet rs = userPtm.getGeneratedKeys();
                if (rs.next()) {
                    userID = rs.getInt(1);
                }
            }

            if (userID != -1) {
                String travelAgentSql = "INSERT INTO TravelAgent (travelAgentName, travelAgentAddress, travelAgentGmail, hotLine, taxCode, establishmentDate, representativeIDCard, dateOfIssue, userID) VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?)";
                PreparedStatement travelAgentPtm = connection.prepareStatement(travelAgentSql);
                travelAgentPtm.setString(1, agent.getTravelAgentName());
                travelAgentPtm.setString(2, agent.getTravelAgentAddress());
                travelAgentPtm.setString(3, agent.getTravelAgentGmail());
                travelAgentPtm.setString(4, agent.getHotLine());
                travelAgentPtm.setString(5, agent.getTaxCode());
                travelAgentPtm.setDate(6, agent.getEstablishmentDate());
                travelAgentPtm.setString(7, agent.getRepresentativeIDCard());
                travelAgentPtm.setDate(8, agent.getDateOfIssue());
                travelAgentPtm.setInt(9, userID);
                travelAgentPtm.executeUpdate();
            }
        } catch (SQLException ex) {
            ex.printStackTrace();
        }
    }

    public Vector<TravelAgent> searchByTravelAgentName(String name) {
        Vector<TravelAgent> list = new Vector<>();
        String sql = "SELECT t.travelAgentID, t.travelAgentName, t.travelAgentAddress, t.travelAgentGmail, t.hotLine, t.taxCode, "
                + "t.establishmentDate, t.representativeIDCard, t.dateOfIssue, u.userID, u.gmail, u.roleID, u.password, u.firstName, "
                + "u.lastName, u.dob, u.gender, u.address, u.phone, u.create_at, u.update_at, u.status "
                + "FROM TravelAgent t JOIN [User] u ON t.userID = u.userID WHERE t.travelAgentName LIKE ?";
        try {
            PreparedStatement ptm = connection.prepareStatement(sql);
            ptm.setString(1, "%" + name + "%");
            ResultSet rs = ptm.executeQuery();
            while (rs.next()) {
                TravelAgent agent = new TravelAgent(
                        rs.getInt("travelAgentID"),
                        rs.getString("travelAgentName"),
                        rs.getString("travelAgentAddress"),
                        rs.getString("travelAgentGmail"),
                        rs.getString("hotLine"),
                        rs.getString("taxCode"),
                        rs.getDate("establishmentDate"),
                        rs.getString("representativeIDCard"),
                        rs.getDate("dateOfIssue"),
                        rs.getInt("userID"),
                        rs.getString("gmail"),
                        rs.getInt("roleID"),
                        rs.getString("password"),
                        rs.getString("firstName"),
                        rs.getString("lastName"),
                        rs.getDate("dob"),
                        rs.getString("gender"),
                        rs.getString("address"),
                        rs.getString("phone"),
                        rs.getDate("create_at"),
                        rs.getDate("update_at"),
                        rs.getInt("status")
                );
                list.add(agent);
            }
        } catch (SQLException ex) {
            ex.printStackTrace();
        }
        return list;
    }
    
    
    public TravelAgent searchByTravelAgentGmail(String gmail) {
        Vector<TravelAgent> list = new Vector<>();
        String sql = "SELECT t.travelAgentID, t.travelAgentName, t.travelAgentAddress, t.travelAgentGmail, t.hotLine, t.taxCode, "
                + "t.establishmentDate, t.representativeIDCard, t.dateOfIssue, u.userID, u.gmail, u.roleID, u.password, u.firstName, "
                + "u.lastName, u.dob, u.gender, u.address, u.phone, u.create_at, u.update_at, u.status "
                + "FROM TravelAgent t JOIN [User] u ON t.userID = u.userID WHERE u.gmail = ?";
        try {
            PreparedStatement ptm = connection.prepareStatement(sql);
            ptm.setString(1, gmail);
            ResultSet rs = ptm.executeQuery();
            while (rs.next()) {
                return new TravelAgent(
                        rs.getInt("travelAgentID"),
                        rs.getString("travelAgentName"),
                        rs.getString("travelAgentAddress"),
                        rs.getString("travelAgentGmail"),
                        rs.getString("hotLine"),
                        rs.getString("taxCode"),
                        rs.getDate("establishmentDate"),
                        rs.getString("representativeIDCard"),
                        rs.getDate("dateOfIssue"),
                        rs.getInt("userID"),
                        rs.getString("gmail"),
                        rs.getInt("roleID"),
                        rs.getString("password"),
                        rs.getString("firstName"),
                        rs.getString("lastName"),
                        rs.getDate("dob"),
                        rs.getString("gender"),
                        rs.getString("address"),
                        rs.getString("phone"),
                        rs.getDate("create_at"),
                        rs.getDate("update_at"),
                        rs.getInt("status")
                );
                
            }
        } catch (SQLException ex) {
            ex.printStackTrace();
        }
        return null;
    }

    public void updateTravelAgent(TravelAgent agent) {

        String userSql = "UPDATE [User] SET gmail=?, roleID=?, password=?, firstName=?, lastName=?, dob=?, gender=?, address=?, phone=?, update_at=?, status=? WHERE gmail=?";
        try {
            PreparedStatement userPtm = connection.prepareStatement(userSql);
            userPtm.setString(1, agent.getGmail());
            userPtm.setInt(2, agent.getRoleID());
            userPtm.setString(3, agent.getPassword());
            userPtm.setString(4, agent.getFirstName());
            userPtm.setString(5, agent.getLastName());
            userPtm.setDate(6, agent.getDob());
            userPtm.setString(7, agent.getGender());
            userPtm.setString(8, agent.getAddress());
            userPtm.setString(9, agent.getPhone());
            userPtm.setDate(10, Date.valueOf(LocalDate.now()));
            userPtm.setInt(11, agent.getStatus());
            userPtm.setString(12, agent.getGmail());
            userPtm.executeUpdate();
        } catch (SQLException ex) {
            ex.printStackTrace();
        }

        // Update TravelAgent table
        String travelAgentSql = "UPDATE TravelAgent SET travelAgentName=?, travelAgentAddress=?, travelAgentGmail=?, hotLine=?, taxCode=?, establishmentDate=?, representativeIDCard=?, dateOfIssue=? WHERE travelAgentID=?";
        try {
            PreparedStatement travelAgentPtm = connection.prepareStatement(travelAgentSql);
            travelAgentPtm.setString(1, agent.getTravelAgentName());
            travelAgentPtm.setString(2, agent.getTravelAgentAddress());
            travelAgentPtm.setString(3, agent.getTravelAgentGmail());
            travelAgentPtm.setString(4, agent.getHotLine());
            travelAgentPtm.setString(5, agent.getTaxCode());
            travelAgentPtm.setDate(6, agent.getEstablishmentDate());
            travelAgentPtm.setString(7, agent.getRepresentativeIDCard());
            travelAgentPtm.setDate(8, agent.getDateOfIssue());
            travelAgentPtm.setInt(9, agent.getTravelAgentID());
            travelAgentPtm.executeUpdate();
        } catch (SQLException ex) {
            ex.printStackTrace();
        }
    }

    public void deleteTravelAgent(int travelAgentID) {
        int n = 0;
        String checkTourSql = "SELECT * FROM Tour WHERE travelAgentID = ?";
        String deleteTravelAgentSql = "DELETE FROM TravelAgent WHERE travelAgentID = ?";
        String findUserSql = "SELECT userID FROM TravelAgent WHERE travelAgentID = ?";
        String deleteUserSql = "DELETE FROM User WHERE userID = ?";

        try {
            PreparedStatement checkTourPtm = connection.prepareStatement(checkTourSql);
            PreparedStatement findUserPtm = connection.prepareStatement(findUserSql);
            checkTourPtm.setInt(1, travelAgentID);
            findUserPtm.setInt(1, travelAgentID);
            ResultSet u = findUserPtm.executeQuery();
            ResultSet t = checkTourPtm.executeQuery();
            int userID = u.getInt("userID");
            if (t.next()) {
                changeStatusTravelAgent(userID, 0);
            }

            PreparedStatement deleteTravelPtm = connection.prepareStatement(deleteTravelAgentSql);
            deleteTravelPtm.setInt(1, travelAgentID);
            n = deleteTravelPtm.executeUpdate();

            PreparedStatement deleteUserPtm = connection.prepareStatement(deleteUserSql);
            deleteUserPtm.setInt(1, userID);
            deleteUserPtm.executeUpdate();

        } catch (SQLException ex) {
            ex.printStackTrace();
        }
    }

    public void changeStatusTravelAgent(int userID, int newStatus) {
        String sql = "UPDATE [dbo].[User] SET [status] = ? WHERE [userID] = ?";
        try {
            PreparedStatement ptm = connection.prepareStatement(sql);
            ptm.setInt(1, newStatus);
            ptm.setInt(2, userID);
            ptm.executeUpdate();
        } catch (SQLException ex) {
            ex.printStackTrace();
        }
    }
    
    public static void main(String[] args) {
        TravelAgentDAO dao = new TravelAgentDAO();
        //dao.updateTravelAgent(new TravelAgent("sdfnlqw", "sdn qwl", "jd@gmail.com", "0984383828", "0984383828", Date.valueOf("2025-05-23"), "098438382804", Date.valueOf("2025-05-23"), 3, "234@fpt.edu.vn", 4, "sdfjkqf", "Hoang", "Lan", Date.valueOf("2025-05-23"), "Nam", "sjkqn", "0928192912", Date.valueOf("2025-05-23"), Date.valueOf("2025-05-23"), 2));
        TravelAgent a = dao.searchByTravelAgentGmail("234@fpt.edu.vn");
        a.setLastName("sjkdqw kslfnw lkasndq kwfnq");
        a.setHotLine("0000000001");
        dao.updateTravelAgent(a);
    }
}
