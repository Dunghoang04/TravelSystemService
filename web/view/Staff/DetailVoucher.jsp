<%-- 
    Document   : DetailVoucher
    Created on : May 31, 2025, 1:19:48 PM
    Author     : Hung
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>

<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html>
<html lang="en">
    <head>
        <meta charset="utf-8" />
        <meta http-equiv="X-UA-Compatible" content="IE=edge" />
        <meta name="viewport" content="width=device-width, initial-scale=1, shrink-to-fit=no" />
        <meta name="description" content="" />
        <meta name="author" content="" />
        <title>Dashboard - SB Admin</title>
        <link href="https://cdn.jsdelivr.net/npm/simple-datatables@7.1.2/dist/style.min.css" rel="stylesheet" />
        <link href="./assets/css/styles2.css" rel="stylesheet" />
        <script src="https://use.fontawesome.com/releases/v6.3.0/js/all.js" crossorigin="anonymous"></script>
        <meta charset="UTF-8">
        <title>Chi tiết thẻ khuyến mãi</title>

        <style>
            .voucher-detail-card {
                max-width: 800px;
                margin: 30px auto;
                border-radius: 12px;
                box-shadow: 0 4px 12px rgba(0,0,0,0.1);
                background: #fff;
                padding: 30px;
            }
            .voucher-detail-card h2 {
                text-align: center;
                margin-bottom: 20px;
            }
            .voucher-detail-item {
                margin-bottom: 15px;
            }
            .voucher-detail-item label {
                font-weight: bold;
                display: block;
            }
            .voucher-detail-item span {
                font-size: 15px;
                color: #333;
            }
            .back-btn {
                display: block;
                margin-top: 20px;
                text-align: center;
            }
        </style>
    </head>
    <body class="sb-nav-fixed">
        <%@ include file="../layout/HeaderAdmin.jsp" %>

        <div id="layoutSidenav">
            <div id="layoutSidenav_nav">
                <%-- Reuse sidebar from admin.jsp --%>
                <nav class="sb-sidenav accordion sb-sidenav-dark" id="sidenavAccordion">
                    <div class="sb-sidenav-menu">
                        <div class="nav">
                            <div class="sb-sidenav-menu-heading">Bảng điều khiển</div>
                            <a class="nav-link" href="statical">
                                <div class="sb-nav-link-icon"><i class="fas fa-tachometer-alt"></i></div>
                                Thống kê
                            </a>
                            <a class="nav-link" href="admin">
                                <div class="sb-nav-link-icon"><i class="fas fa-tachometer-alt"></i></div>
                                Quản lí tài khoản
                            </a>
                            <a class="nav-link" href="tourmanagement">
                                <div class="sb-nav-link-icon"><i class="fas fa-tachometer-alt"></i></div>
                                Quản lí chuyến đi
                            </a>
                            <a class="nav-link" href="requestuploadtour">
                                <div class="sb-nav-link-icon"><i class="fas fa-tachometer-alt"></i></div>
                                Yêu cầu xét duyệt
                            </a>
                        </div>
                    </div>
                </nav>
            </div>
            <div id="layoutSidenav_content">
                <main>
                    <div class="container-fluid px-4">
                        <div class="voucher-detail-card">
                            <h2>Chi tiết thẻ khuyến mãi</h2>
                            <div class="voucher-detail-item">
                                <label>ID:</label>
                                <span>${voucher.voucherId}</span>
                            </div>
                            <div class="voucher-detail-item">
                                <label>Mã Voucher:</label>
                                <span>${voucher.voucherCode}</span>
                            </div>
                            <div class="voucher-detail-item">
                                <label>Tên:</label>
                                <span>${voucher.voucherName}</span>
                            </div>
                            <div class="voucher-detail-item">
                                <label>Chi tiết:</label>
                                <span>${voucher.description}</span>
                            </div>
                            <div class="voucher-detail-item">
                                <label>Phần trăm khuyến mãi:</label>
                                <span>${voucher.percentDiscount}%</span>
                            </div>
                            <div class="voucher-detail-item">
                                <label>Giảm tối đa:</label>
                                <span>${voucher.maxDiscountAmount}</span>
                            </div>
                            <div class="voucher-detail-item">
                                <label>Giá trị đơn tối thiểu áp dụng:</label>
                                <span>${voucher.minAmountApply}</span>
                            </div>
                            <div class="voucher-detail-item">
                                <label>Ngày bắt đầu:</label>
                                <span>${voucher.startDate}</span>
                            </div>
                            <div class="voucher-detail-item">
                                <label>Ngày kết thúc:</label>
                                <span>${voucher.endDate}</span>
                            </div>
                            <div class="voucher-detail-item">
                                <label>Số lượng:</label>
                                <span>${voucher.quantity}</span>
                            </div>
                            <div class="voucher-detail-item">
                                <label>Trạng thái:</label>
                                <span>
                                    <c:choose>
                                        <c:when test="${voucher.status == 1}">Hoạt động</c:when>
                                        <c:otherwise>Không hoạt động</c:otherwise>
                                    </c:choose>
                                </span>
                            </div>

                            <div class="back-btn">
                                <a href="listvoucher" class="btn btn-secondary">
                                    <i class="fas fa-arrow-left"></i> Quay lại danh sách
                                </a>
                            </div>
                        </div>
                    </div>
                </main>
                <footer class="py-4 bg-light mt-auto">
                    <div class="container-fluid px-4">
                        <div class="d-flex align-items-center justify-content-between small">
                            <div class="text-muted">Copyright &copy; Go Việt</div>
                            <div>
                                <a href="#">Điều khoản</a>
                                &middot;
                                <a href="#">Terms &amp; Conditions</a>
                            </div>
                        </div>
                    </div>
                </footer>
            </div>
        </div>
        <script src="https://cdn.jsdelivr.net/npm/bootstrap@5.2.3/dist/js/bootstrap.bundle.min.js" crossorigin="anonymous"></script>
        <script src="./assets/js/scripts.js"></script>
        <script src="https://cdnjs.cloudflare.com/ajax/libs/Chart.js/2.8.0/Chart.min.js" crossorigin="anonymous"></script>
        <script src="./assets/demo/chart-area-demo.js"></script>
        <script src="./assets/demo/chart-bar-demo.js"></script>
        <script src="https://cdn.jsdelivr.net/npm/simple-datatables@7.1.2/dist/umd/simple-datatables.min.js" crossorigin="anonymous"></script>
        <script src="./assets/js/datatables-simple-demo.js"></script>
        <script src="https://cdn.jsdelivr.net/npm/bootstrap@5.2.3/dist/js/bootstrap.bundle.min.js"></script>
    </body>
</html>

