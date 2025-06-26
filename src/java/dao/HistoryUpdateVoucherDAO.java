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
 * Click nfs://.netbeans.org/filesystem/designer/src/org/netbeans/modules/form/resources/license.txt to change this license
 * Click nfs://netbeans.org/filesystem/designer/src/org/netbeans/modules/form/resources/Class.java to edit this template
 */
package dao;

import dal.DBContext;
import java.util.ArrayList;
import model.HistoryUpdateVoucher;
import java.sql.*;

/**
 * Class HistoryUpdateVoucherDAO thực hiện các thao tác truy vấn dữ liệu lịch sử cập nhật voucher
 * từ cơ sở dữ liệu, kế thừa từ DBContext và triển khai interface IHistoryUpdateVoucherDAO.
 *
 * @author Hung
 */
public class HistoryUpdateVoucherDAO extends DBContext implements IHistoryUpdateVoucherDAO {

    /**
     * Lấy danh sách lịch sử cập nhật voucher dựa trên ID của voucher từ cơ sở dữ liệu.
     * Sử dụng truy vấn SQL với LEFT JOIN để lấy thông tin người cập nhật từ bảng [User].
     *
     * @param id ID của voucher cần lấy lịch sử cập nhật (phải là số nguyên dương).
     * @return ArrayList chứa các đối tượng HistoryUpdateVoucher tương ứng với ID.
     *         Trả về null nếu có lỗi xảy ra trong quá trình truy vấn.
     */
    @Override
    public ArrayList<HistoryUpdateVoucher> getHistoryUpdateVoucherById(int id) {
        String sql = "SELECT h.historyID, h.voucherID, h.fieldName, h.oldValue, h.newValue, h.updateDate, h.updatedBy, "
                + "u.firstName + ' ' + u.lastName AS updatedByName "
                + "FROM HistoryUpdateVoucher h "
                + "LEFT JOIN [User] u ON h.updatedBy = u.userID "
                + "WHERE h.voucherID = ? "
                + "ORDER BY h.updateDate DESC";
        Connection conn = null;
        PreparedStatement stmt = null;
        ResultSet rs = null;
        ArrayList<HistoryUpdateVoucher> list = new ArrayList<>();
        try {
            conn = getConnection(); // Lấy kết nối từ DBContext
            stmt = conn.prepareStatement(sql);
            stmt.setInt(1, id); // Gán tham số ID vào câu truy vấn
            rs = stmt.executeQuery(); // Thực thi truy vấn
            while (rs.next()) {
                HistoryUpdateVoucher h = new HistoryUpdateVoucher();
                h.setHistoryID(rs.getInt("historyID")); // Gán ID lịch sử
                h.setVoucherID(rs.getInt("voucherID")); // Gán ID voucher
                h.setFieldName(rs.getString("fieldName")); // Gán tên trường được cập nhật
                h.setOldValue(rs.getString("oldValue")); // Gán giá trị cũ
                h.setNewValue(rs.getString("newValue")); // Gán giá trị mới
                h.setUpdateDate(rs.getTimestamp("updateDate")); // Gán ngày cập nhật
                h.setUpdateBy(rs.getInt("updatedBy")); // Gán ID người cập nhật
                h.setUpdatedByName(rs.getString("updatedByName")); // Gán tên người cập nhật
                list.add(h); // Thêm đối tượng vào danh sách
            }
            return list; // Trả về danh sách kết quả
        } catch (Exception e) {
            e.printStackTrace(); // In lỗi ra console nếu có
        } finally {
            // Đóng các tài nguyên để tránh rò rỉ
            try {
                if (rs != null) rs.close();
                if (stmt != null) stmt.close();
                if (conn != null) conn.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
        return null; // Trả về null nếu có lỗi
    }
    
    /**
     * Phương thức main để kiểm tra chức năng getHistoryUpdateVoucherById.
     * Dùng để debug và kiểm tra kết quả trả về từ cơ sở dữ liệu với ID cố định.
     *
     * @param args Tham số dòng lệnh (không sử dụng trong trường hợp này).
     */
    public static void main(String[] args) {
        HistoryUpdateVoucherDAO dao = new HistoryUpdateVoucherDAO();
        ArrayList<HistoryUpdateVoucher> list = dao.getHistoryUpdateVoucherById(1);
        for (HistoryUpdateVoucher h : list) {
            System.out.println(h); // In thông tin từng đối tượng HistoryUpdateVoucher
        }
    }
}