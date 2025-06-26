/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package dao;

import dal.DBContext;
import java.util.ArrayList;
import model.TransactionHistory;
import java.sql.*;

/**
 *
 * @author Hung
 */
public class TransactionHistoryDAO extends DBContext implements ITransactionHistory{

    public ArrayList<TransactionHistory> getAllTransactionHistoryByUserId(int userID) {
        String sql = "SELECT \n"
                + "    th.transactionID,\n"
                + "    th.userID,\n"
                + "    th.amount,\n"
                + "    th.transactionType,\n"
                + "    th.description,\n"
                + "    th.transactionDate\n"
                + "FROM \n"
                + "    TransactionHistory th\n"
                + "WHERE \n"
                + "    th.userID = ?\n"
                + "ORDER BY \n"
                + "    th.transactionDate DESC;";
        ArrayList<TransactionHistory> list = new ArrayList<>();
        PreparedStatement stmt = null;
        ResultSet rs = null;
        Connection conn = null;
        try {
            conn = getConnection();
            stmt = conn.prepareStatement(sql);
            stmt.setInt(1, userID);
            rs = stmt.executeQuery();
            
            while(rs.next()){
                TransactionHistory tran = new TransactionHistory();
                tran.setTransactionId(rs.getInt("transactionID"));
                tran.setUserId(rs.getInt("userID"));
                tran.setAmount(rs.getDouble("amount"));
                tran.setTransactionType(rs.getString("transactionType"));
                tran.setDescription(rs.getString("description"));
                tran.setTransactionDate(rs.getDate("transactionDate"));
                
                list.add(tran);
            }
            return list;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }
    
    public boolean insertTransaction(int userID, double amount, String type, String description){
        String sql = "INSERT INTO TransactionHistory (userID, amount, transactionType, description) VALUES (?, ?, ?, ?)";
        Connection conn = null;
        PreparedStatement stmt = null;
        
        try {
            conn = getConnection();
            stmt= conn.prepareStatement(sql);

            stmt.setInt(1, userID);
            stmt.setDouble(2, amount);
            stmt.setString(3, type);
            stmt.setString(4, description);

            return stmt.executeUpdate() > 0;

        } catch (Exception e) {
            e.printStackTrace();
        }
        return false;
    
    }
    
    public static void main(String[] args) {
        TransactionHistoryDAO dao = new TransactionHistoryDAO();
        String type = "REFUND";
        String description = "Thanh toán tour";
        if(dao.insertTransaction(5, 100000, type, description)){
            System.out.println("Thành công");
        }else{
            System.out.println("False");
        }
    }
}
