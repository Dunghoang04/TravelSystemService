/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package dao;

import java.util.Vector;
import model.VAT;
import java.sql.SQLException;

/**
 *
 * @author Hà Thị Duyên
 */
public interface IVATDAO {
    Vector<VAT> getAllVAT(String sql) throws SQLException;
}
