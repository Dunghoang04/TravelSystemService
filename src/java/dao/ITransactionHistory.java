/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Interface.java to edit this template
 */
package dao;

import java.util.ArrayList;
import model.TransactionHistory;

/**
 *
 * @author Hung
 */
public interface ITransactionHistory {
    
    ArrayList<TransactionHistory> getAllTransactionHistoryByUserId(int userID);
    
    boolean insertTransaction(int userID, double amount, String type, String description);
}
