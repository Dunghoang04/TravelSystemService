/*
 * Copyright (C) 2025, Group 6.
 * ProjectCode/Short Name of Application: TravelSystemService 
 * Support Management and Provide Travel Service System 
 *
 * Record of change:
 * DATE        Version    AUTHOR     DESCRIPTION
 * 2025-06-24  1.0        Hưng       First implementation
 */

/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Interface.java to edit this template
 */
package dao;

import java.util.ArrayList;
import model.HistoryUpdateVoucher;

/**
 * Interface IHistoryUpdateVoucherDAO cung cấp các phương thức để truy xuất lịch sử cập nhật voucher
 * từ cơ sở dữ liệu dựa trên ID của voucher.
 *
 * @author Hung
 */
public interface IHistoryUpdateVoucherDAO {
    /**
     * Lấy danh sách lịch sử cập nhật voucher dựa trên ID của voucher.
     *
     * @param id ID của voucher cần lấy lịch sử cập nhật (phải là số nguyên dương).
     * @return ArrayList chứa các đối tượng HistoryUpdateVoucher tương ứng với ID được cung cấp.
     *         Trả về danh sách rỗng (new ArrayList<>()) nếu không tìm thấy dữ liệu.
     */
    ArrayList<HistoryUpdateVoucher> getHistoryUpdateVoucherById(int id);
}