/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Interface.java to edit this template
 */
package dao;

import java.sql.Date;
import java.sql.SQLException;
import java.util.ArrayList;
import model.BookDetail;


/**
 *
 * @author Hung
 */
public interface IBookTour {
    
    ArrayList<BookDetail> getBookDetailsByAgent(int agentId) throws SQLException;
    ArrayList<BookDetail> getBookDetailByStatus(int status) throws SQLException;
    ArrayList<BookDetail> createBookDetail(int userId, int tourID,int voucherID,Date bookDate,int numberAdult,int numberChildren,String payMethod,String firstName,String lastName,String phone,String gmail,String note,int isBookedForOther,float totalPrice,int status, int paymentMethodID) throws SQLException;
    boolean insertBookDetail(int userID, int tourID, Integer voucherID, int numberAdult, int numberChildren, String firstName, String lastName, String phone, String gmail, String note, int isBookedForOther, double totalPrice, int status, int paymentMethodID, long bookCode) throws SQLException;
    boolean updateBookStatusByBookCode(long bookCode, int newStatus) throws SQLException;
    BookDetail getBookDetailByBookCode(long bookCode) throws SQLException;
}
