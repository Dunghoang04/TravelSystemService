/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package dao;
import dal.DBContext;
import java.util.ArrayList;
import model.Wallet;
import java.sql.*;
/**
 *
 * @author Hung
 */
public class WalletDAO extends DBContext {
    
    public Wallet getWalletByUserId(int userID){
        String sql = "SELECT * FROM Wallet WHERE userID = ?;";
        
        PreparedStatement stmt = null;
        ResultSet rs = null;
        Connection conn = null;
        
        try{
            conn = getConnection();
            stmt = conn.prepareStatement(sql);
            stmt.setInt(1, userID);
            rs = stmt.executeQuery();
            Wallet w = new Wallet();
            while(rs.next()){
                
                w.setUserId(rs.getInt("userID"));
                w.setBalance(rs.getDouble("balance"));
                w.setCreateDate(rs.getDate("createDate"));
                
                
            }
            return w;
            
        }catch(Exception e){
            e.printStackTrace();
        }
        return null;
    }
    
    public boolean paymentForTour(double price, int userID){
        String sql = "UPDATE Wallet SET balance = balance - ? WHERE userID = ? AND balance >= ?";
        PreparedStatement stmt = null;
        Connection conn = null;
        try {
            conn = getConnection();
            stmt = conn.prepareStatement(sql);
            stmt.setDouble(1, price);
            stmt.setInt(2, userID);
            stmt.setDouble(3, price);
            
            int check = stmt.executeUpdate();
            return check>0;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return false;
    }
    
    public static void main(String[] args) {
        WalletDAO dao = new WalletDAO();
        Wallet list = dao.getWalletByUserId(5);
        System.out.println(list);
    }
}
