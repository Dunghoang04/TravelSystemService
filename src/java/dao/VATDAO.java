/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package dao;

import dal.DBContext;
import model.VAT;
import java.sql.*;
import java.util.Vector;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author Hung
 */
public class VATDAO extends DBContext implements IVATDAO{

    public VAT getVATActive() {
        String sql = "SELECT TOP 1 *\n"
                + "FROM VAT\n"
                + "WHERE status = 1\n"
                + "  AND CAST(GETDATE() AS DATE) BETWEEN startDate AND endDate\n"
                + "ORDER BY updateDate DESC;";
        
        PreparedStatement stmt = null;
        ResultSet rs = null;
        Connection conn = null;
        try {
            conn = getConnection();
            stmt = conn.prepareStatement(sql);
            rs = stmt.executeQuery();
            while(rs.next()){
                VAT vat = new VAT();
                vat.setVatID(rs.getInt("vatID"));
                vat.setVatRate(rs.getDouble("vatRate"));
                vat.setDescription(rs.getString("description"));
                vat.setStartDate(rs.getDate("startDate"));
                vat.setEndDate(rs.getDate("endDate"));
                vat.setStatus(rs.getInt("status"));
                vat.setCreateDate(rs.getDate("createDate"));
                vat.setUpdateDate(rs.getDate("updateDate"));
                
                return vat;
            }
            
        } catch (Exception e) {
            e.printStackTrace();
        }finally{
            if(conn!=null)try {
                conn.close();
            } catch (SQLException ex) {
                Logger.getLogger(VATDAO.class.getName()).log(Level.SEVERE, null, ex);
            }
            if(stmt!=null)try {
                stmt.close();
            } catch (SQLException ex) {
                Logger.getLogger(VATDAO.class.getName()).log(Level.SEVERE, null, ex);
            }
            if(rs!=null)try {
                rs.close();
            } catch (SQLException ex) {
                Logger.getLogger(VATDAO.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
        return null;
    }
    
    public static void main(String[] args) {
        VATDAO dao = new VATDAO();
        VAT vat = dao.getVATActive();
        System.out.println(vat);
    }

    @Override
    public Vector<VAT> getAllVAT(String sql) throws SQLException {
        throw new UnsupportedOperationException("Not supported yet."); // Generated from nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/GeneratedMethodBody
    }
}
