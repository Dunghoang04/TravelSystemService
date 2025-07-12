package dao;

import dal.DBContext;
import model.VAT;
import java.sql.*;
import java.time.LocalDate;
import java.util.Vector;
import java.util.logging.Level;
import java.util.logging.Logger;

public class VATDAO extends DBContext implements IVATDAO {

    @Override
    public VAT getVATActive() {
        String sql = "SELECT TOP 1 * FROM VAT WHERE status = 1 AND CAST(GETDATE() AS DATE) BETWEEN startDate AND endDate ORDER BY updateDate DESC";
        PreparedStatement stmt = null;
        ResultSet rs = null;
        Connection conn = null;
        try {
            conn = getConnection();
            stmt = conn.prepareStatement(sql);
            rs = stmt.executeQuery();
            while (rs.next()) {
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
        } catch (SQLException e) {
            Logger.getLogger(VATDAO.class.getName()).log(Level.SEVERE, "Error retrieving active VAT", e);
        } finally {
            closeResources(rs, stmt, conn);
        }
        return null;
    }

    @Override
    public Vector<VAT> getAllVAT(String sql) throws SQLException {
        Vector<VAT> listVAT = new Vector<>();
        Connection connection = null;
        PreparedStatement ptm = null;
        ResultSet rs = null;
        try {
            connection = getConnection();
            ptm = connection.prepareStatement(sql);
            rs = ptm.executeQuery();
            while (rs.next()) {
                VAT v = new VAT(rs.getInt(1), rs.getDouble(2), rs.getString(3),
                        rs.getDate(4), rs.getDate(5), rs.getInt(6),
                        rs.getDate(7), rs.getDate(8));
                listVAT.add(v);
            }
        } finally {
            closeResources(rs, ptm, connection);
        }
        return listVAT;
    }

    @Override
    public boolean checkOverlappingVAT(double vatRate, Date startDate, Date endDate, int excludeVatID) throws SQLException {
        String sql = "SELECT * FROM VAT WHERE status = 1 AND ((startDate <= ? AND endDate >= ?) OR (startDate <= ? AND endDate >= ?)) AND vatID != ?";
        Connection connection = null;
        PreparedStatement ptm = null;
        ResultSet rs = null;
        try {
            connection = getConnection();
            ptm = connection.prepareStatement(sql);
            ptm.setDate(1, endDate);
            ptm.setDate(2, startDate);
            ptm.setDate(3, startDate);
            ptm.setDate(4, endDate);
            ptm.setInt(5, excludeVatID);
            rs = ptm.executeQuery();
            return rs.next();
        } finally {
            closeResources(rs, ptm, connection);
        }
    }

    // Phương thức mới: Lấy danh sách VAT đang hoạt động trong khoảng thời gian
    public Vector<VAT> getOverlappingVATs(Date startDate, Date endDate, int excludeVatID) throws SQLException {
        Vector<VAT> overlappingVATs = new Vector<>();
        String sql = "SELECT * FROM VAT WHERE status = 1 AND ((startDate <= ? AND endDate >= ?) OR (startDate <= ? AND endDate >= ?)) AND vatID != ?";
        Connection connection = null;
        PreparedStatement ptm = null;
        ResultSet rs = null;
        try {
            connection = getConnection();
            ptm = connection.prepareStatement(sql);
            ptm.setDate(1, endDate);
            ptm.setDate(2, startDate);
            ptm.setDate(3, startDate);
            ptm.setDate(4, endDate);
            ptm.setInt(5, excludeVatID);
            rs = ptm.executeQuery();
            while (rs.next()) {
                VAT vat = new VAT();
                vat.setVatID(rs.getInt("vatID"));
                vat.setVatRate(rs.getDouble("vatRate"));
                vat.setDescription(rs.getString("description"));
                vat.setStartDate(rs.getDate("startDate"));
                vat.setEndDate(rs.getDate("endDate"));
                vat.setStatus(rs.getInt("status"));
                vat.setCreateDate(rs.getDate("createDate"));
                vat.setUpdateDate(rs.getDate("updateDate"));
                overlappingVATs.add(vat);
            }
        } finally {
            closeResources(rs, ptm, connection);
        }
        return overlappingVATs;
    }

    @Override
    public void insertVAT(VAT vat) throws SQLException {
        // Kiểm tra chồng lấn VAT
        if (vat.getStatus() == 1) {
            Vector<VAT> overlappingVATs = getOverlappingVATs(vat.getStartDate(), vat.getEndDate(), 0);
            if (!overlappingVATs.isEmpty()) {
                // Hủy kích hoạt các VAT đang hoạt động trong cùng khoảng thời gian
                for (VAT oldVAT : overlappingVATs) {
                    changeVATStatus(oldVAT.getVatID(), 0); // Đặt status = 0 cho VAT cũ
                }
            }
        }
        String sql = "INSERT INTO VAT (vatRate, description, startDate, endDate, status, createDate, updateDate) VALUES (?, ?, ?, ?, ?, ?, ?)";
        Connection connection = null;
        PreparedStatement ptm = null;
        try {
            connection = getConnection();
            ptm = connection.prepareStatement(sql);
            ptm.setDouble(1, vat.getVatRate());
            ptm.setString(2, vat.getDescription());
            ptm.setDate(3, vat.getStartDate());
            ptm.setDate(4, vat.getEndDate());
            ptm.setInt(5, vat.getStatus());
            ptm.setDate(6, vat.getCreateDate());
            ptm.setDate(7, vat.getUpdateDate());
            ptm.executeUpdate();
        } finally {
            closeResources(null, ptm, connection);
        }
    }

    @Override
    public void deactivateAllActiveVATs() throws SQLException {
        String sql = "UPDATE VAT SET status = 0, updateDate = ? WHERE status = 1 AND endDate >= ?";
        Connection connection = null;
        PreparedStatement ptm = null;
        try {
            connection = getConnection();
            ptm = connection.prepareStatement(sql);
            ptm.setDate(1, Date.valueOf(LocalDate.now()));
            ptm.setDate(2, Date.valueOf(LocalDate.now()));
            ptm.executeUpdate();
        } finally {
            closeResources(null, ptm, connection);
        }
    }

    @Override
    public void changeVATStatus(int vatID, int status) throws SQLException {
        VAT vat = getVATByID(vatID);
        if (vat == null) {
            throw new SQLException("Không tìm thấy bản ghi VAT.");
        }
        if (vat.getEndDate().before(Date.valueOf(LocalDate.now()))) {
            throw new SQLException("Không thể thay đổi trạng thái của bản ghi VAT đã hết hạn.");
        }
        String sql = "UPDATE VAT SET status = ?, updateDate = ? WHERE vatID = ?";
        Connection connection = null;
        PreparedStatement ptm = null;
        try {
            connection = getConnection();
            ptm = connection.prepareStatement(sql);
            ptm.setInt(1, status);
            ptm.setDate(2, Date.valueOf(LocalDate.now()));
            ptm.setInt(3, vatID);
            ptm.executeUpdate();
        } finally {
            closeResources(null, ptm, connection);
        }
    }

    @Override
    public VAT getVATByID(int vatID) throws SQLException {
        String sql = "SELECT * FROM VAT WHERE vatID = ?";
        Connection connection = null;
        PreparedStatement stmt = null;
        ResultSet rs = null;
        try {
            connection = getConnection();
            stmt = connection.prepareStatement(sql);
            stmt.setInt(1, vatID);
            rs = stmt.executeQuery();
            if (rs.next()) {
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
        } finally {
            closeResources(rs, stmt, connection);
        }
        return null;
    }

    public void updateExpiredVATs() throws SQLException {
        String sql = "UPDATE VAT SET status = 0, updateDate = ? WHERE endDate < ? AND status = 1";
        Connection connection = null;
        PreparedStatement ptm = null;
        try {
            connection = getConnection();
            ptm = connection.prepareStatement(sql);
            ptm.setDate(1, Date.valueOf(LocalDate.now()));
            ptm.setDate(2, Date.valueOf(LocalDate.now()));
            ptm.executeUpdate();
        } finally {
            closeResources(null, ptm, connection);
        }
    }

    private void closeResources(ResultSet rs, PreparedStatement stmt, Connection conn) {
        if (rs != null) {
            try {
                rs.close();
            } catch (SQLException e) {
                Logger.getLogger(VATDAO.class.getName()).log(Level.SEVERE, "Error closing ResultSet", e);
            }
        }
        if (stmt != null) {
            try {
                stmt.close();
            } catch (SQLException e) {
                Logger.getLogger(VATDAO.class.getName()).log(Level.SEVERE, "Error closing PreparedStatement", e);
            }
        }
        if (conn != null) {
            try {
                conn.close();
            } catch (SQLException e) {
                Logger.getLogger(VATDAO.class.getName()).log(Level.SEVERE, "Error closing Connection", e);
            }
        }
    }

    public static void main(String[] args) {
        VATDAO vdao = new VATDAO();

        // Test 1: getVATActive
        System.out.println("Kiểm tra getVATActive:");
        try {
            VAT activeVAT = vdao.getVATActive();
            if (activeVAT != null) {
                System.out.println("VAT hoạt động: ID=" + activeVAT.getVatID() + ", Tỷ lệ=" + activeVAT.getVatRate()
                        + ", Mô tả=" + activeVAT.getDescription() + ", Trạng thái=" + activeVAT.getStatus());
            } else {
                System.out.println("Không tìm thấy VAT hoạt động.");
            }
        } catch (Exception e) {
            Logger.getLogger(VATDAO.class.getName()).log(Level.SEVERE, "Lỗi khi kiểm tra getVATActive", e);
        }
        System.out.println("--------------------------------");

        // Test 2: getAllVAT
        System.out.println("Kiểm tra getAllVAT:");
        try {
            Vector<VAT> list = vdao.getAllVAT("SELECT * FROM VAT");
            if (list.isEmpty()) {
                System.out.println("Không tìm thấy bản ghi VAT nào.");
            } else {
                for (VAT vat : list) {
                    System.out.println("VAT: ID=" + vat.getVatID() + ", Tỷ lệ=" + vat.getVatRate()
                            + ", Mô tả=" + vat.getDescription() + ", Trạng thái=" + vat.getStatus());
                }
            }
        } catch (SQLException e) {
            Logger.getLogger(VATDAO.class.getName()).log(Level.SEVERE, "Lỗi khi kiểm tra getAllVAT", e);
        }
        System.out.println("--------------------------------");

        // Test 3: checkOverlappingVAT
        System.out.println("Kiểm tra checkOverlappingVAT:");
        try {
            double vatRate = 8.0;
            Date startDate = Date.valueOf("2025-01-01");
            Date endDate = Date.valueOf("2025-12-31");
            int excludeVatID = 0; // 0 cho thêm mới, sử dụng vatID thực tế cho cập nhật
            boolean isOverlapping = vdao.checkOverlappingVAT(vatRate, startDate, endDate, excludeVatID);
            System.out.println("Tỷ lệ VAT=" + vatRate + " có chồng lấn: " + isOverlapping);
        } catch (SQLException e) {
            Logger.getLogger(VATDAO.class.getName()).log(Level.SEVERE, "Lỗi khi kiểm tra checkOverlappingVAT", e);
        }
        System.out.println("--------------------------------");

        // Test 4: insertVAT
        System.out.println("Kiểm tra insertVAT:");
        try {
            VAT newVAT = new VAT();
            newVAT.setVatRate(10.0);
            newVAT.setDescription("VAT thử nghiệm 10%");
            newVAT.setStartDate(Date.valueOf("2026-01-01"));
            newVAT.setEndDate(Date.valueOf("2026-12-31"));
            newVAT.setStatus(1);
            newVAT.setCreateDate(Date.valueOf(LocalDate.now()));
            newVAT.setUpdateDate(Date.valueOf(LocalDate.now()));
            vdao.insertVAT(newVAT);
            System.out.println("Thêm VAT thành công.");
        } catch (SQLException e) {
            Logger.getLogger(VATDAO.class.getName()).log(Level.SEVERE, "Lỗi khi kiểm tra insertVAT", e);
        }
        System.out.println("--------------------------------");

        // Test 5: changeVATStatus
        System.out.println("Kiểm tra changeVATStatus:");
        try {
            int vatID = 1; // Thay bằng ID VAT hiện có
            int newStatus = 0; // Chuyển sang không hoạt động
            vdao.changeVATStatus(vatID, newStatus);
            System.out.println("Thay đổi trạng thái VAT thành công.");
        } catch (SQLException e) {
            Logger.getLogger(VATDAO.class.getName()).log(Level.SEVERE, "Lỗi khi kiểm tra changeVATStatus", e);
        }
        System.out.println("--------------------------------");

        // Test 6: getVATByID
        System.out.println("Kiểm tra getVATByID:");
        try {
            int vatID = 1; // Thay bằng ID VAT hiện có
            VAT vat = vdao.getVATByID(vatID);
            if (vat != null) {
                System.out.println("VAT: ID=" + vat.getVatID() + ", Tỷ lệ=" + vat.getVatRate()
                        + ", Mô tả=" + vat.getDescription() + ", Trạng thái=" + vat.getStatus());
            } else {
                System.out.println("Không tìm thấy VAT với ID=" + vatID);
            }
        } catch (SQLException e) {
            Logger.getLogger(VATDAO.class.getName()).log(Level.SEVERE, "Lỗi khi kiểm tra getVATByID", e);
        }
        System.out.println("--------------------------------");

        // Test 7: updateExpiredVATs
        System.out.println("Kiểm tra updateExpiredVATs:");
        try {
            vdao.updateExpiredVATs();
            System.out.println("Cập nhật các VAT đã hết hạn thành công.");
        } catch (SQLException e) {
            Logger.getLogger(VATDAO.class.getName()).log(Level.SEVERE, "Lỗi khi kiểm tra updateExpiredVATs", e);
        }
    }
}
