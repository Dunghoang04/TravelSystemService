<%--
 * Copyright (C) 2025, Group 6.
 * ProjectCode/Short Name of Application: TravelSystemService 
 * Support Management and Provide Travel Service System 
 *
 * Record of change:
 * DATE        Version    AUTHOR            DESCRIPTION
 * 2025-07-03  1.0        Hà Thị Duyên      First implementation for book detail management
--%>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<!DOCTYPE html>
<html lang="vi">
<head>
    <meta charset="utf-8">
    <meta http-equiv="X-UA-Compatible" content="IE=edge">
    <meta name="viewport" content="width=device-width, initial-scale=1, shrink-to-fit=no">
    <title>Chi Tiết Đơn Đặt Tour</title>
    <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.2.3/dist/css/bootstrap.min.css" rel="stylesheet" />
    <link href="${pageContext.request.contextPath}/assets/css/styles2.css" rel="stylesheet" />
    <link href="https://cdnjs.cloudflare.com/ajax/libs/font-awesome/5.10.0/css/all.min.css" rel="stylesheet">
    <link href="https://cdn.jsdelivr.net/npm/bootstrap-icons@1.4.1/font/bootstrap-icons.css" rel="stylesheet">
    <style>
        body {
            font-family: "Segoe UI", Roboto, sans-serif;
        }
        .card-header {
            background-color: #007bff;
            color: white;
        }
        .detail-box {
            background-color: #f8f9fa;
            border: 1px solid #dee2e6;
            padding: 20px;
            border-radius: 6px;
            margin-bottom: 30px;
        }
        .detail-box h5 {
            font-size: 1.25rem;
            font-weight: 600;
        }
        .detail-item {
            margin-bottom: 10px;
        }
        .detail-item label {
            font-weight: bold;
            width: 150px;
            display: inline-block;
        }
    </style>
</head>
<body class="sb-nav-fixed">
    <%@include file="../layout/headerAdmin.jsp" %>
    <div id="layoutSidenav">
        <%@include file="../layout/sideNavOptionAdmin.jsp" %>
        <div id="layoutSidenav_content">
            <main>
                <div class="container-fluid px-4">
                    <h1 class="mt-4">Chi Tiết Đơn Đặt Tour</h1>

                    <div class="card shadow-sm">
                        <div class="card-header">
                            <h5 class="mb-0">Thông Tin Đơn Đặt</h5>
                        </div>
                        <div class="card-body">
                            <c:if test="${empty bookDetail}">
                                <div class="alert alert-warning">Không tìm thấy thông tin đơn đặt.</div>
                            </c:if>
                            <c:if test="${not empty bookDetail}">
                                <div class="detail-box">
                                    <div class="detail-item">
                                        <label>Mã đơn:</label>
                                        <span>${bookDetail.bookID}</span>
                                    </div>
                                    <div class="detail-item">
                                        <label>Tên tour:</label>
                                        <span>${tour.tourName}</span>
                                    </div>
                                    <div class="detail-item">
                                        <label>Khách hàng:</label>
                                        <span>${empty bookDetail.firstName && empty bookDetail.lastName ? 'Không có tên' : bookDetail.firstName} ${bookDetail.lastName}</span>
                                    </div>
                                    <div class="detail-item">
                                        <label>Số điện thoại:</label>
                                        <span>${bookDetail.phone}</span>
                                    </div>
                                    <div class="detail-item">
                                        <label>Email:</label>
                                        <span>${bookDetail.gmail}</span>
                                    </div>
                                    <div class="detail-item">
                                        <label>Số người lớn:</label>
                                        <span>${bookDetail.numberAdult}</span>
                                    </div>
                                    <div class="detail-item">
                                        <label>Số trẻ em:</label>
                                        <span>${bookDetail.numberChildren}</span>
                                    </div>
                                    <div class="detail-item">
                                        <label>Tổng tiền:</label>
                                        <span><fmt:formatNumber value="${bookDetail.totalPrice}" type="currency" currencySymbol="₫" maxFractionDigits="0"/></span>
                                    </div>
                                    <div class="detail-item">
                                        <label>Ngày đặt:</label>
                                        <span><fmt:formatDate value="${bookDetail.bookDate}" pattern="dd/MM/yyyy"/></span>
                                    </div>
                                    <div class="detail-item">
                                        <label>Trạng thái:</label>
                                        <span>
                                            <c:choose>
                                                <c:when test="${bookDetail.status == 1}">
                                                    <span class="text-success">Đã thanh toán</span>
                                                </c:when>
                                                <c:when test="${bookDetail.status == 2}">
                                                    <span class="text-danger">Bị hủy bởi người dùng</span>
                                                </c:when>
                                                <c:when test="${bookDetail.status == 3}">
                                                    <span class="text-danger">Bị hủy bởi đại lý</span>
                                                </c:when>
                                                <c:when test="${bookDetail.status == 4}">
                                                    <span class="text-primary">Đã hoàn thành</span>
                                                </c:when>
                                                <c:when test="${bookDetail.status == 5}">
                                                    <span class="text-warning">Yêu cầu hoàn tiền</span>
                                                </c:when>
                                                <c:when test="${bookDetail.status == 6}">
                                                    <span class="text-info">Đã hoàn tiền</span>
                                                </c:when>
                                                <c:when test="${bookDetail.status == 7}">
                                                    <span class="text-warning">Chờ thanh toán</span>
                                                </c:when>
                                                <c:otherwise>
                                                    <span class="text-muted">Không xác định</span>
                                                </c:otherwise>
                                            </c:choose>
                                        </span>
                                    </div>
                                    <div class="detail-item">
                                        <label>Voucher:</label>
                                        <span>${bookDetail.voucherID == 0 ? 'Không sử dụng' : bookDetail.voucherID}</span>
                                    </div>
                                    <div class="detail-item">
                                        <label>Ghi chú:</label>
                                        <span>${empty bookDetail.note ? 'Không có' : bookDetail.note}</span>
                                    </div>
                                    <div class="detail-item">
                                        <label>Đặt cho người khác:</label>
                                        <span>${bookDetail.isBookedForOther == 1 ? 'Có' : 'Không'}</span>
                                    </div>
                                </div>
                                <a href="RevenueManagementServlet" class="btn btn-secondary">Quay lại</a>
                            </c:if>
                        </div>
                    </div>
                </div>
            </main>
            <%@include file="../layout/footerStaff.jsp" %>
        </div>
    </div>
    <script src="https://cdn.jsdelivr.net/npm/bootstrap@5.2.3/dist/js/bootstrap.bundle.min.js" crossorigin="anonymous"></script>
    <script src="${pageContext.request.contextPath}/assets/js/scripts.js"></script>
</body>
</html>