<%--
 * Copyright (C) 2025, Group 6.
 * ProjectCode/Short Name of Application: TravelSystemService 
 * Support Management and Provide Travel Service System 
 *
 * Record of change:
 * DATE        Version    AUTHOR            DESCRIPTION
 * 2025-07-03  1.0       Hà Thị Duyên       First implementation for revenue management
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
    <title>Quản Lý Doanh Thu</title>
    <link href="https://cdn.jsdelivr.net/npm/simple-datatables@7.1.2/dist/style.min.css" rel="stylesheet" />
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
        .table th, .table td {
            vertical-align: middle;
        }
        .summary-box {
            background-color: #fff3cd;
            border: 1px solid #ffeeba;
            padding: 20px;
            border-radius: 6px;
            margin-bottom: 30px;
        }
        .summary-box h5 {
            font-size: 1.25rem;
            font-weight: 600;
        }
        .pagination a, .pagination span {
            margin: 0 5px;
            padding: 8px 12px;
            text-decoration: none;
            border: 1px solid #ddd;
            border-radius: 5px;
            color: #007bff;
        }
        .pagination a:hover {
            background-color: #007bff;
            color: #fff;
        }
        .pagination span {
            background-color: #007bff;
            color: #fff;
            border-color: #007bff;
        }
        .dataTable-search input {
            width: 200px;
            margin-bottom: 10px;
        }
        .status-filter {
            margin-bottom: 15px;
            max-width: 200px;
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
                    <h1 class="mt-4">Quản Lý Doanh Thu</h1>

                    <!-- Summary -->
                    <div class="summary-box text-center shadow-sm">
                        <h5 class="mb-2 text-dark">Tổng Doanh Thu</h5>
                        <h3 class="text-success fw-bold">
                            <fmt:formatNumber value="${totalRevenue}" type="currency" currencySymbol="₫" maxFractionDigits="0"/>
                        </h3>
                        <p class="text-muted">Tổng số đơn đặt: ${totalBooks}</p>
                    </div>

                    <!-- Revenue Table with DataTable -->
                    <div class="card shadow-sm">
                        <div class="card-header">
                            <h5 class="mb-0">Danh Sách Đơn Đặt</h5>
                        </div>
                        <div class="card-body">
                            <!-- Status Filter -->
                            <div class="status-filter">
                                <label for="statusFilter" class="form-label">Lọc theo trạng thái:</label>
                                <select id="statusFilter" class="form-select">
                                    <option value="">Tất cả</option>
                                    <option value="Đã thanh toán">Đã thanh toán</option>
                                    <option value="Bị hủy bởi người dùng">Bị hủy bởi người dùng</option>
                                    <option value="Bị hủy bởi đại lý">Bị hủy bởi đại lý</option>
                                    <option value="Đã hoàn thành">Đã hoàn thành</option>
                                    <option value="Yêu cầu hoàn tiền">Yêu cầu hoàn tiền</option>
                                    <option value="Đã hoàn tiền">Đã hoàn tiền</option>
                                    <option value="Chờ thanh toán">Chờ thanh toán</option>
                                </select>
                            </div>

                            <c:if test="${empty bookList}">
                                <div class="alert alert-warning">Không có đơn đặt nào.</div>
                            </c:if>
                            <c:if test="${not empty bookList}">
                                <div class="table-responsive">
                                    <table class="table table-striped" id="revenueTable">
                                        <thead>
                                            <tr>
                                                <th>Mã đơn</th>
                                                <th>Tên tour</th>
                                                <th>Khách hàng</th>
                                                <th>Số tiền</th>
                                                <th>Ngày đặt</th>
                                                <th>Trạng thái</th>
                                                <th>Hành động</th>
                                            </tr>
                                        </thead>
                                        <tbody>
                                            <c:forEach var="book" items="${bookList}">
                                                <tr>
                                                    <td>${book.bookID}</td>
                                                    <td>${tourNames[book.tourID]}</td>
                                                    <td>${book.firstName} ${book.lastName}</td>
                                                    <td><fmt:formatNumber value="${book.totalPrice}" type="currency" currencySymbol="₫" maxFractionDigits="0"/></td>
                                                    <td><fmt:formatDate value="${book.bookDate}" pattern="dd/MM/yyyy"/></td>
                                                    <td>
                                                        <c:choose>
                                                            <c:when test="${book.status == 1}">
                                                                <span class="text-success">Đã thanh toán</span>
                                                            </c:when>
                                                            <c:when test="${book.status == 2}">
                                                                <span class="text-danger">Bị hủy bởi người dùng</span>
                                                            </c:when>
                                                            <c:when test="${book.status == 3}">
                                                                <span class="text-danger">Bị hủy bởi đại lý</span>
                                                            </c:when>
                                                            <c:when test="${book.status == 4}">
                                                                <span class="text-primary">Đã hoàn thành</span>
                                                            </c:when>
                                                            <c:when test="${book.status == 5}">
                                                                <span class="text-warning">Yêu cầu hoàn tiền</span>
                                                            </c:when>
                                                            <c:when test="${book.status == 6}">
                                                                <span class="text-info">Đã hoàn tiền</span>
                                                            </c:when>
                                                            <c:when test="${book.status == 7}">
                                                                <span class="text-warning">Chờ thanh toán</span>
                                                            </c:when>
                                                            <c:otherwise>
                                                                <span class="text-muted">Không xác định</span>
                                                            </c:otherwise>
                                                        </c:choose>
                                                    </td>
                                                    <td>
                                                        <a href="BookDetailManagement?tourId=${book.tourID}&bookId=${book.bookID}" class="btn btn-primary btn-sm">
                                                            Chi tiết <i class="bi bi-eye"></i>
                                                        </a>
                                                    </td>
                                                </tr>
                                            </c:forEach>
                                        </tbody>
                                    </table>
                                </div>
                                <c:if test="${totalPages > 1}">
                                    <nav class="pagination mt-4">
                                        <c:forEach begin="1" end="${totalPages}" var="i">
                                            <c:choose>
                                                <c:when test="${i == currentPage}">
                                                    <span>${i}</span>
                                                </c:when>
                                                <c:otherwise>
                                                    <a href="RevenueManagementServlet?page=${i}">${i}</a>
                                                </c:otherwise>
                                            </c:choose>
                                        </c:forEach>
                                    </nav>
                                </c:if>
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
    <script src="https://cdn.jsdelivr.net/npm/simple-datatables@7.1.2/dist/umd/simple-datatables.min.js" crossorigin="anonymous"></script>
    <script>
        document.addEventListener('DOMContentLoaded', function () {
            const dataTable = new simpleDatatables.DataTable('#revenueTable', {
                searchable: true,
                sortable: true,
                perPage: 10,
                perPageSelect: [5, 10, 15, 20],
                labels: {
                    placeholder: "Tìm kiếm...",
                    perPage: "",
                    noRows: "Không tìm thấy dữ liệu",
                    info: "Hiển thị {start} đến {end} của {rows} mục"
                }
            });

            // Thêm sự kiện lọc trạng thái
            const statusFilter = document.getElementById('statusFilter');
            statusFilter.addEventListener('change', function () {
                const selectedStatus = this.value;
                dataTable.search(selectedStatus, [5]); // Lọc trên cột "Trạng thái" (cột thứ 6, index 5)
            });
        });
    </script>
</body>
</html>