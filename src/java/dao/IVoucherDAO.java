/*
 * Interface IVoucherDAO
 * 
 * Description: This interface defines methods for interacting with the `Voucher` table.
 * It helps decouple the implementation (DAO) from usage (e.g., in Servlets or Services),
 * improving modularity, testability (e.g., with mocks), and following object-oriented principles.
 */

package dao;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Date;
import model.Voucher;

public interface IVoucherDAO {

    /**
     * Retrieves all vouchers from the database.
     * 
     * @return a list of all vouchers
     * @throws SQLException if a database access error occurs
     */
    ArrayList<Voucher> getAllVoucher() throws SQLException;

    /**
     * Retrieves a voucher by its ID.
     * 
     * @param id the ID of the voucher to retrieve
     * @return the Voucher object if found, otherwise null
     * @throws SQLException if a database access error occurs
     */
    Voucher getVoucherById(int id) throws SQLException;

    /**
     * Retrieves all vouchers with the specified status.
     * 
     * @param status the status of the vouchers to retrieve (0 = inactive, 1 = active)
     * @return a list of vouchers matching the given status
     * @throws SQLException if a database access error occurs
     */
    ArrayList<Voucher> getVoucherByStatus(int status) throws SQLException;

    /**
     * Updates an existing voucher identified by its ID.
     * 
     * @param voucherId the ID of the voucher to update
     * @param voucherName the name of the voucher
     * @param description the description of the voucher
     * @param percentDiscount the discount percentage
     * @param maxDiscountAmount the maximum discount amount
     * @param minAmountApply the minimum order value required to apply the voucher
     * @param startDate the start date of the voucher's validity
     * @param endDate the end date of the voucher's validity
     * @param quantity the quantity available
     * @param status the status of the voucher (1 = active, 0 = inactive)
     * @return true if the update was successful, false otherwise
     * @throws SQLException if a database access error occurs
     */
    boolean updateVoucher(int voucherId, String voucherName, String description, float percentDiscount,
                          float maxDiscountAmount, float minAmountApply, Date startDate,
                          Date endDate, int quantity, int status) throws SQLException;

    /**
     * Changes the status of a voucher by its ID.
     * 
     * @param voucherId the ID of the voucher to update
     * @param status the new status to set
     * @return true if the update was successful, false otherwise
     * @throws SQLException if a database access error occurs
     */
    boolean changeStatusById(int voucherId, int status) throws SQLException;

    /**
     * Searches for vouchers by voucher code (supports partial match using SQL LIKE).
     * 
     * @param voucherCode the voucher code or substring to search for
     * @return a list of vouchers matching the search criteria
     * @throws SQLException if a database access error occurs
     */
    ArrayList<Voucher> getVoucherByVoucherCode(String voucherCode) throws SQLException;

    /**
     * Adds a new voucher to the database.
     * 
     * @param voucherCode the voucher code
     * @param voucherName the name of the voucher
     * @param description the description of the voucher
     * @param percentDiscount the discount percentage
     * @param maxDiscountAmount the maximum discount amount
     * @param minAmountApply the minimum order value required to apply the voucher
     * @param startDate the start date of the voucher's validity
     * @param endDate the end date of the voucher's validity
     * @param quantity the available quantity
     * @param status the status of the voucher (1 = active, 0 = inactive)
     * @return true if the voucher was added successfully, false otherwise
     * @throws SQLException if a database access error occurs
     */
    boolean addVoucher(String voucherCode, String voucherName, String description, float percentDiscount,
                       float maxDiscountAmount, float minAmountApply, Date startDate,
                       Date endDate, int quantity, int status) throws SQLException;
}
