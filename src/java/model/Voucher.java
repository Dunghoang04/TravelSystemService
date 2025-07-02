/*
* Copyright (C) 2025, Group 6.
 * ProjectCode/Short Name of Application: TravelSystemService 
 * Support Management and Provide Travel Service System 
 *
 * Record of change:
 * DATE        Version    AUTHOR            DESCRIPTION
 * 2025-06-07  1.0        Hưng              First implementation
 */
package model;

import java.sql.Date;

/**
 *
 * @author Nhat Anh
 */
public class Voucher {
    private int voucherId;                // Unique identifier for the voucher
    private String voucherCode;          // Code used to apply the voucher
    private String voucherName;          // Name/title of the voucher
    private String description;          // Description or notes about the voucher
    private int percentDiscount;       // Percentage discount offered by the voucher
    private int maxDiscountAmount;     // Maximum amount that can be discounted
    private int minAmountApply;        // Minimum order value required to apply the voucher
    private Date startDate;              // Start date of voucher validity
    private Date endDate;                // End date of voucher validity
    private int quantity;                // Number of vouchers available
    private int status;                  // Status of the voucher (e.g., 0 = inactive, 1 = active)

    /**
     * Default constructor.
     * Creates an empty Voucher object.
     */
    public Voucher() {
    }

    /**
     * Constructs a Voucher object with all fields specified.
     * 
     * @param voucherId unique identifier of the voucher
     * @param voucherCode code to be entered by users to redeem the voucher
     * @param voucherName name/title of the voucher
     * @param description detailed description of the voucher
     * @param percentDiscount percentage of discount (e.g., 10 for 10%)
     * @param maxDiscountAmount the maximum discount amount allowed
     * @param minAmountApply minimum amount required to apply this voucher
     * @param startDate start date when the voucher becomes valid
     * @param endDate end date when the voucher expires
     * @param quantity total available quantity of this voucher
     * @param status status of the voucher (e.g., 1 = active, 0 = inactive)
     */
    public Voucher(int voucherId, String voucherCode, String voucherName, String description, 
                   int percentDiscount, int maxDiscountAmount, int minAmountApply, 
                   Date startDate, Date endDate, int quantity, int status) {
        this.voucherId = voucherId;
        this.voucherCode = voucherCode;
        this.voucherName = voucherName;
        this.description = description;
        this.percentDiscount = percentDiscount;
        this.maxDiscountAmount = maxDiscountAmount;
        this.minAmountApply = minAmountApply;
        this.startDate = startDate;
        this.endDate = endDate;
        this.quantity = quantity;
        this.status = status;
    }

    /**
     * @return the unique ID of the voucher
     */
    public int getVoucherId() {
        return voucherId;
    }

    /**
     * @param voucherId the ID to set for the voucher
     */
    public void setVoucherId(int voucherId) {
        this.voucherId = voucherId;
    }

    /**
     * @return the voucher code used for redemption
     */
    public String getVoucherCode() {
        return voucherCode;
    }

    /**
     * @param voucherCode the voucher code to set
     */
    public void setVoucherCode(String voucherCode) {
        this.voucherCode = voucherCode;
    }

    /**
     * @return the name/title of the voucher
     */
    public String getVoucherName() {
        return voucherName;
    }

    /**
     * @param voucherName the name/title to set
     */
    public void setVoucherName(String voucherName) {
        this.voucherName = voucherName;
    }

    /**
     * @return the description of the voucher
     */
    public String getDescription() {
        return description;
    }

    /**
     * @param description the description to set
     */
    public void setDescription(String description) {
        this.description = description;
    }

    /**
     * @return the discount percentage offered
     */
    public float getPercentDiscount() {
        return percentDiscount;
    }

    /**
     * @param percentDiscount the discount percentage to set
     */
    public void setPercentDiscount(int percentDiscount) {
        this.percentDiscount = percentDiscount;
    }

    /**
     * @return the maximum amount allowed for discount
     */
    public float getMaxDiscountAmount() {
        return maxDiscountAmount;
    }

    /**
     * @param maxDiscountAmount the maximum discount amount to set
     */
    public void setMaxDiscountAmount(int maxDiscountAmount) {
        this.maxDiscountAmount = maxDiscountAmount;
    }

    /**
     * @return the minimum amount required to apply the voucher
     */
    public float getMinAmountApply() {
        return minAmountApply;
    }

    /**
     * @param minAmountApply the minimum order amount to apply the voucher
     */
    public void setMinAmountApply(int minAmountApply) {
        this.minAmountApply = minAmountApply;
    }

    /**
     * @return the start date of the voucher's validity
     */
    public Date getStartDate() {
        return startDate;
    }

    /**
     * @param startDate the start date to set
     */
    public void setStartDate(Date startDate) {
        this.startDate = startDate;
    }

    /**
     * @return the end date of the voucher's validity
     */
    public Date getEndDate() {
        return endDate;
    }

    /**
     * @param endDate the end date to set
     */
    public void setEndDate(Date endDate) {
        this.endDate = endDate;
    }

    /**
     * @return the quantity of vouchers available
     */
    public int getQuantity() {
        return quantity;
    }

    /**
     * @param quantity the number of vouchers to set
     */
    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }

    /**
     * @return the status of the voucher (e.g., active or inactive)
     */
    public int getStatus() {
        return status;
    }

    /**
     * @param status the status of the voucher to set
     */
    public void setStatus(int status) {
        this.status = status;
    }

    /**
     * @return a string representation of the voucher object
     */
    @Override
    public String toString() {
        return "Voucher{" +
               "voucherId=" + voucherId +
               ", voucherCode='" + voucherCode + '\'' +
               ", voucherName='" + voucherName + '\'' +
               ", description='" + description + '\'' +
               ", percentDiscount=" + percentDiscount +
               ", maxDiscountAmount=" + maxDiscountAmount +
               ", minAmountApply=" + minAmountApply +
               ", startDate=" + startDate +
               ", endDate=" + endDate +
               ", quantity=" + quantity +
               ", status=" + status +
               '}';
    }  
}
