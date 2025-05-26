/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package dao;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Vector;
import model.TravelAgent;

/**
 *
 * @author Nhat Anh
 */
public class TravelAgentDAO {
    public Vector<TravelAgent> getAllTravelAgent(String sql) {
        Vector<TravelAgent> lisProducts = new Vector<>();
        try {
            PreparedStatement ptm = connection.prepareStatement(sql);
            ResultSet rs = ptm.executeQuery();
            while (rs.next()) {
                Product d = new Product(rs.getInt(1), rs.getString(2),
                        rs.getString(3), rs.getInt(4),
                        rs.getString(5), rs.getDouble(6),
                        rs.getInt(7), rs.getInt(8), rs.getString(9),
                        rs.getString(10), rs.getDate(11), rs.getInt(12));

                lisProducts.add(d);
            }

        } catch (SQLException ex) {
            ex.getStackTrace();
        }

        return lisProducts;
    }
    
    
    public void insertProduct(Product p) {
        String sql = "INSERT INTO [dbo].[tblProducts]\n"
                + "           ([productName]\n"
                + "           ,[supplierName]\n"
                + "           ,[categoryID]\n"
                + "           ,[size]\n"
                + "           ,[price]\n"
                + "           ,[quantityInStock]\n"
                + "           ,[quantitySold]\n"
                + "           ,[image]\n"
                + "           ,[describe]\n"
                + "           ,[releaseDate]\n"
                + "           ,[status])\n"
                + "     VALUES(?,?,?,?,?,?,?,?,?,?,?)";
        try {
            PreparedStatement ptm = connection.prepareStatement(sql);
            ptm.setString(1, p.getProductName());
            ptm.setString(2, p.getSupplierName());
            ptm.setInt(3, p.getCategoryID());
            ptm.setString(4, p.getSize());
            ptm.setDouble(5, p.getPrice());
            ptm.setInt(6, p.getQuantityInStock());
            ptm.setInt(7, p.getQuantitySold());
            ptm.setString(8, p.getImage());
            ptm.setString(9, p.getDescribe());
            ptm.setDate(10, p.getReleaseDate());
            ptm.setInt(11, p.getStatus());
            ptm.executeUpdate(); // tra ve so luong ban ghi
        } catch (SQLException ex) {
            ex.getStackTrace();
        }
    }
    
    public void updateProduct(Product p) {
        String sql = "UPDATE [dbo].[tblProducts]\n"
                + "   SET [productName] = ?\n"
                + "      ,[supplierName] = ?\n"
                + "      ,[categoryID] = ?\n"
                + "      ,[size] = ?\n"
                + "      ,[price] = ?\n"
                + "      ,[quantityInStock] = ?\n"
                + "      ,[quantitySold] = ?\n"
                + "      ,[image] = ?\n"
                + "      ,[describe] = ?\n"
                + "      ,[releaseDate] = ?\n"
                + "       ,[status] = ?\n"
                + " WHERE productID = ?";
        try {
            PreparedStatement ptm = connection.prepareStatement(sql);
            ptm.setString(1, p.getProductName());
            ptm.setString(2, p.getSupplierName());
            ptm.setInt(3, p.getCategoryID());
            ptm.setString(4, p.getSize());
            ptm.setDouble(5, p.getPrice());
            ptm.setInt(6, p.getQuantityInStock());
            ptm.setInt(7, p.getQuantitySold());
            ptm.setString(8, p.getImage());
            ptm.setString(9, p.getDescribe());
            ptm.setDate(10, p.getReleaseDate());
            ptm.setInt(11, p.getStatus());
            ptm.setInt(12, p.getProductID());
            ptm.executeUpdate();
        } catch (SQLException ex) {
            ex.getStackTrace();
        }

    }

    public void changeStatusProduct(int productID, int newStatus) {
        String sql = "UPDATE [dbo].[tblProducts] SET [status] = ? WHERE [productID] = ?";
        try {
            PreparedStatement ptm = connection.prepareStatement(sql);
            ptm.setInt(1, newStatus);
            ptm.setInt(2, productID);
            ptm.executeUpdate();

        } catch (SQLException ex) {
            ex.getStackTrace();
        }
    }

    public int deleteProduct(int productID) {

        int n = 0;
        String sqlDelete = "DELETE FROM [dbo].[tblProducts] WHERE productID = ?";
        String checkSql1 = "SELECT * FROM tblOrderDetails WHERE productID = ?";
        try {
            // Kiểm tra xem sản phẩm có xuất hiện trong tblOrderDetails không
            PreparedStatement checkStmt1 = connection.prepareStatement(checkSql1);
            checkStmt1.setInt(1, productID);
            ResultSet rs1 = checkStmt1.executeQuery();

            if (rs1.next()) {
                // Nếu tồn tại, đổi trạng thái thay vì xóa
                changeStatusProduct(productID, 0);
                return n; // Không xóa, chỉ đổi trạng thái
            }

            // Nếu không tồn tại, tiến hành xóa
            PreparedStatement deleteStmt = connection.prepareStatement(sqlDelete);
            deleteStmt.setInt(1, productID);
            n = deleteStmt.executeUpdate();
        } catch (SQLException ex) {
            ex.getStackTrace();
        }
        return n;
    }
    
}
