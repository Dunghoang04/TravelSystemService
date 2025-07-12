/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package dao;

import java.sql.Date;
import java.util.Vector;
import model.VAT;
import java.sql.SQLException;

/**
 *
 * @author Hà Thị Duyên
 */
public interface IVATDAO {

    Vector<VAT> getAllVAT(String sql) throws SQLException;

    VAT getVATActive();

    void insertVAT(VAT vat) throws SQLException;

    void changeVATStatus(int vatID, int status) throws SQLException;

    VAT getVATByID(int vatID) throws SQLException;

    boolean checkOverlappingVAT(double vatRate, Date startDate, Date endDate, int excludeVatID) throws SQLException;

    void deactivateAllActiveVATs() throws SQLException;
}
