package controller.admin;

import dao.VATDAO;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.sql.SQLException;
import java.time.LocalDate;
import java.time.format.DateTimeParseException;
import model.VAT;
import java.sql.Date;
import java.util.Vector;
import java.util.logging.Level;
import java.util.logging.Logger;

@WebServlet(name = "ManageVAT", urlPatterns = {"/ManageVAT"})
public class ManageVAT extends HttpServlet {

    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        response.setContentType("text/html;charset=UTF-8");
        VATDAO vdao = new VATDAO();
        String service = request.getParameter("service");

        if (service == null || service.isEmpty()) {
            request.getRequestDispatcher("/view/admin/addVAT.jsp").forward(request, response);
            return;
        }

        if (service.equals("addVAT")) {
            String vatRateStr = request.getParameter("vatRate");
            String description = request.getParameter("description");
            String startDateStr = request.getParameter("startDate");
            String endDateStr = request.getParameter("endDate");
            String statusStr = request.getParameter("status");

            // Lưu các giá trị để hiển thị lại nếu có lỗi
            request.setAttribute("vatRate", vatRateStr);
            request.setAttribute("description", description);
            request.setAttribute("startDate", startDateStr);
            request.setAttribute("endDate", endDateStr);
            request.setAttribute("status", statusStr);

            boolean hasError = validateInput(request, vatRateStr, description, startDateStr, endDateStr, statusStr);

            if (!hasError) {
                try {
                    double vatRate = Double.parseDouble(vatRateStr.trim());
                    LocalDate startDate = LocalDate.parse(startDateStr.trim());
                    LocalDate endDate = LocalDate.parse(endDateStr.trim());
                    int status = Integer.parseInt(statusStr.trim());
                    LocalDate now = LocalDate.now();
                    Date createDate = Date.valueOf(now);
                    Date updateDate = Date.valueOf(now);

                    if (endDate.isBefore(now)) {
                        request.setAttribute("endDateError", "Ngày kết thúc không được là quá khứ.");
                        request.getRequestDispatcher("/view/admin/addVAT.jsp").forward(request, response);
                        return;
                    }

                    VAT newVAT = new VAT(
                            0, // vatID (tự động sinh)
                            vatRate,
                            description != null ? description.trim() : "",
                            Date.valueOf(startDate),
                            Date.valueOf(endDate),
                            status,
                            createDate,
                            updateDate
                    );

                    if (status == 1) {
                        Vector<VAT> overlappingVATs = vdao.getOverlappingVATs(newVAT.getStartDate(), newVAT.getEndDate(), 0);
                        if (!overlappingVATs.isEmpty()) {
                            StringBuilder message = new StringBuilder("Thêm VAT thành công!");                           
                            request.setAttribute("successMessage", message.toString());
                        } else {
                            request.setAttribute("successMessage", "Thêm VAT thành công!");
                        }
                    } else {
                        request.setAttribute("successMessage", "Thêm VAT thành công!");
                    }

                    vdao.insertVAT(newVAT);

                    // Xóa các giá trị nhập liệu để làm mới form
                    request.removeAttribute("vatRate");
                    request.removeAttribute("description");
                    request.removeAttribute("startDate");
                    request.removeAttribute("endDate");
                    request.removeAttribute("status");
                    request.removeAttribute("vatRateError");
                    request.removeAttribute("descriptionError");
                    request.removeAttribute("startDateError");
                    request.removeAttribute("endDateError");
                    request.removeAttribute("statusError");

                    request.getRequestDispatcher("/view/admin/addVAT.jsp").forward(request, response);
                    return;
                } catch (NumberFormatException e) {
                    request.setAttribute("generalError", "Dữ liệu số không hợp lệ. Vui lòng kiểm tra lại.");
                } catch (DateTimeParseException e) {
                    request.setAttribute("generalError", "Định dạng ngày không hợp lệ. Vui lòng sử dụng định dạng YYYY-MM-DD.");
                } catch (SQLException e) {
                    request.setAttribute("generalError", e.getMessage());
                } catch (Exception e) {
                    Logger.getLogger(ManageVAT.class.getName()).log(Level.SEVERE, "Lỗi khi thêm VAT", e);
                    request.setAttribute("generalError", "Lỗi không xác định. Vui lòng thử lại.");
                }
            }
            request.getRequestDispatcher("/view/admin/addVAT.jsp").forward(request, response);
        } else if (service.equals("updateVAT")) {
            request.setAttribute("generalError", "Cập nhật VAT không được hỗ trợ. Vui lòng tạo một VAT mới.");
            request.getRequestDispatcher("/view/admin/addVAT.jsp").forward(request, response);
        } else if (service.equals("viewVAT")) {
            try {
                int vatID = Integer.parseInt(request.getParameter("vatID"));
                VAT vat = vdao.getVATByID(vatID);
                if (vat == null) {
                    request.setAttribute("generalError", "Không tìm thấy VAT với ID: " + vatID);
                    request.getRequestDispatcher("/view/common/error.jsp").forward(request, response);
                    return;
                }
                request.setAttribute("vat", vat);
                request.getRequestDispatcher("/view/admin/viewDetailVAT.jsp").forward(request, response);
            } catch (NumberFormatException e) {
                request.setAttribute("generalError", "ID VAT không hợp lệ.");
                request.getRequestDispatcher("/view/common/error.jsp").forward(request, response);
            } catch (SQLException e) {
                request.setAttribute("generalError", "Lỗi khi tải thông tin VAT: " + e.getMessage());
                request.getRequestDispatcher("/view/common/error.jsp").forward(request, response);
            }
        }
    }

    private boolean validateInput(HttpServletRequest request, String vatRateStr, String description,
            String startDateStr, String endDateStr, String statusStr) {
        boolean hasError = false;

        if (vatRateStr == null || vatRateStr.trim().isEmpty()) {
            request.setAttribute("vatRateError", "Tỷ lệ VAT là bắt buộc.");
            hasError = true;
        } else {
            try {
                double vatRate = Double.parseDouble(vatRateStr.trim());
                if (vatRate < 0) {
                    request.setAttribute("vatRateError", "Tỷ lệ VAT không được âm.");
                    hasError = true;
                }
            } catch (NumberFormatException e) {
                request.setAttribute("vatRateError", "Định dạng tỷ lệ VAT không hợp lệ.");
                hasError = true;
            }
        }

        if (description == null || description.trim().isEmpty()) {
            request.setAttribute("descriptionError", "Mô tả là bắt buộc.");
            hasError = true;
        }

        if (startDateStr == null || startDateStr.trim().isEmpty()) {
            request.setAttribute("startDateError", "Ngày bắt đầu là bắt buộc.");
            hasError = true;
        } else {
            try {
                LocalDate startDate = LocalDate.parse(startDateStr.trim());
                LocalDate endDate = LocalDate.parse(endDateStr.trim());
                if (startDate.isAfter(endDate)) {
                    request.setAttribute("startDateError", "Ngày bắt đầu phải trước ngày kết thúc.");
                    hasError = true;
                }
            } catch (DateTimeParseException e) {
                request.setAttribute("startDateError", "Định dạng ngày bắt đầu không hợp lệ (sử dụng YYYY-MM-DD).");
                hasError = true;
            }
        }

        if (endDateStr == null || endDateStr.trim().isEmpty()) {
            request.setAttribute("endDateError", "Ngày kết thúc là bắt buộc.");
            hasError = true;
        } else {
            try {
                LocalDate endDate = LocalDate.parse(endDateStr.trim());
                LocalDate startDate = startDateStr != null && !startDateStr.trim().isEmpty()
                        ? LocalDate.parse(startDateStr.trim()) : null;
                if (startDate != null && endDate.isBefore(startDate)) {
                    request.setAttribute("endDateError", "Ngày kết thúc phải sau ngày bắt đầu.");
                    hasError = true;
                }
            } catch (DateTimeParseException e) {
                request.setAttribute("endDateError", "Định dạng ngày kết thúc không hợp lệ (sử dụng YYYY-MM-DD).");
                hasError = true;
            }
        }

        if (statusStr == null || statusStr.trim().isEmpty()) {
            request.setAttribute("statusError", "Trạng thái là bắt buộc.");
            hasError = true;
        } else {
            try {
                int status = Integer.parseInt(statusStr.trim());
                if (status != 0 && status != 1 && status != 2) {
                    request.setAttribute("statusError", "Trạng thái phải là 0 (Không kích hoạt), 1 (Kích hoạt), hoặc 2 (Hết hiệu lực).");
                    hasError = true;
                }
            } catch (NumberFormatException e) {
                request.setAttribute("statusError", "Định dạng trạng thái không hợp lệ.");
                hasError = true;
            }
        }

        return hasError;
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        processRequest(request, response);
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        processRequest(request, response);
    }

    @Override
    public String getServletInfo() {
        return "Servlet để quản lý bản ghi VAT";
    }
}