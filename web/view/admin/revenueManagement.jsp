<%--
 * Copyright (C) 2025, Group 6.
 * ProjectCode/Short Name of Application: TravelSystemService 
 * Support Management and Provide Travel Service System 
 *
 * Record of change:
 * DATE        Version    AUTHOR            DESCRIPTION
 * 2025-07-03  1.0        Hà Thị Duyên      First implementation for revenue management
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
        <title>Quản Lý Doanh Thu và Thuế</title>
        <link href="https://cdn.datatables.net/1.13.4/css/dataTables.bootstrap5.min.css" rel="stylesheet" />
        <link href="${pageContext.request.contextPath}/assets/css/styles2.css" rel="stylesheet" />
        <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.0/dist/css/bootstrap.min.css" rel="stylesheet" />
        <script src="https://use.fontawesome.com/releases/v6.3.0/js/all.js" crossorigin="anonymous"></script>
        <style>
            body {
                font-family: "Segoe UI", Roboto, sans-serif;
            }
            .card-header {
                background-color: #007bff;
                color: white;
                padding: 15px;
                font-size: 1.25rem;
                font-weight: 600;
            }
            .table th, .table td {
                vertical-align: middle;
            }
            .summary-box {
                background-color: #fff3cd;
                border: 1px solid #ffeeba;
                padding: 20px;
                border-radius: 8px;
                margin-bottom: 30px;
                box-shadow: 0 2px 4px rgba(0, 0, 0, 0.1);
            }
            .summary-box h5 {
                font-size: 1.25rem;
                font-weight: 600;
                color: #333;
            }
            .summary-box .text-success, .summary-box .text-danger {
                font-size: 1.5rem;
            }
            .search-bar {
                margin-bottom: 20px;
                max-width: 350px;
            }
            .date-filter {
                margin-bottom: 20px;
                padding: 15px;
                background-color: #f8f9fa;
                border-radius: 8px;
                box-shadow: 0 2px 4px rgba(0, 0, 0, 0.1);
            }
            .date-filter form {
                display: flex;
                align-items: flex-end;
                gap: 15px;
                flex-wrap: wrap;
            }
            .date-filter .form-label {
                font-weight: 500;
                color: #333;
                margin-bottom: 8px;
            }
            .date-filter .form-control {
                border-radius: 5px;
                border: 1px solid #ced4da;
                padding: 8px;
                transition: border-color 0.3s ease;
            }
            .date-filter .form-control:focus {
                border-color: #007bff;
                box-shadow: 0 0 5px rgba(0, 123, 255, 0.3);
            }
            .date-filter .btn-primary {
                background-color: #007bff;
                border: none;
                padding: 10px 20px;
                border-radius: 5px;
                font-weight: 500;
                transition: background-color 0.3s ease, transform 0.2s ease;
            }
            .date-filter .btn-primary:hover {
                background-color: #0056b3;
                transform: translateY(-2px);
            }
            .date-filter .btn-primary:active {
                transform: translateY(0);
            }
            @media (max-width: 768px) {
                .date-filter form {
                    flex-direction: column;
                    align-items: stretch;
                }
                .date-filter .col-md-5, .date-filter .col-md-2 {
                    width: 100%;
                }
                .date-filter .btn-primary {
                    width: 100%;
                }
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
                        <h1 class="mt-4">Quản Lý Doanh Thu và Thuế</h1>

                        <!-- Summary -->
                        <div class="summary-box text-center shadow-sm">
                            <h5 class="mb-2 text-dark">Tổng Doanh Thu (Chưa bao gồm VAT)</h5>
                            <h3 class="text-success fw-bold">
                                <fmt:formatNumber value="${totalRevenue}" type="currency" currencySymbol="₫" maxFractionDigits="0"/>
                            </h3>
                            <h5 class="mb-2 text-dark">Tổng VAT cần nộp (Tỷ lệ VAT: ${vatRate}%)</h5>
                            <h3 class="text-danger fw-bold">
                                <fmt:formatNumber value="${totalVAT}" type="currency" currencySymbol="₫" maxFractionDigits="0"/>
                            </h3>
                            <p class="text-muted">Tổng số đơn đặt: ${totalBooks}</p>
                        </div>

                        <!-- Revenue Table with DataTable -->
                        <div class="card shadow-sm">
                            <div class="card-header">
                                <h5 class="mb-0">Danh Sách Đơn Đặt (Đã Hoàn Thành)</h5>
                            </div>
                            <div class="card-body">
                                <!-- Search Bar -->
                                <div class="search-bar">
                                    <label for="searchInput" class="form-label">Tìm kiếm:</label>
                                    <input type="text" id="searchInput" class="form-control" placeholder="Tìm theo mã đơn hoặc tên khách hàng">
                                </div>
                                <!-- Date Range Filter -->
                                <div class="date-filter">
                                    <form action="RevenueManagementServlet" method="get" class="row g-3">
                                        <div class="col-md-5">
                                            <label for="startDate" class="form-label">Từ ngày:</label>
                                            <input type="date" id="startDate" name="startDate" class="form-control" value="${startDate}">
                                        </div>
                                        <div class="col-md-5">
                                            <label for="endDate" class="form-label">Đến ngày:</label>
                                            <input type="date" id="endDate" name="endDate" class="form-control" value="${endDate}">
                                        </div>
                                        <div class="col-md-2 d-flex align-items-end">
                                            <button type="submit" class="btn btn-primary w-100">Lọc</button>
                                        </div>
                                    </form>
                                </div>

                                <c:if test="${empty bookList}">
                                    <div class="alert alert-warning">Không có đơn đặt nào trong khoảng thời gian này.</div>
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
                                                    <th>VAT (${vatRate}%)</th>
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
                                                        <td><fmt:formatNumber value="${book.totalPrice / (1 + vatRate / 100)}" type="currency" currencySymbol="₫" maxFractionDigits="0"/></td>
                                                        <td><fmt:formatNumber value="${book.totalPrice * (vatRate / 100) / (1 + vatRate / 100)}" type="currency" currencySymbol="₫" maxFractionDigits="0"/></td>
                                                        <td><fmt:formatDate value="${book.bookDate}" pattern="dd/MM/yyyy"/></td>
                                                        <td>
                                                            <c:choose>
                                                                <c:when test="${book.status == 4}">
                                                                    <span class="text-primary">Đã hoàn thành</span>
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
                                </c:if>
                            </div>
                        </div>
                    </div>
                </main>
                <footer class="bg-light py-4 mt-auto">
                    <div class="container-fluid px-4">
                        <div class="text-muted text-center">Copyright © TravelSystemService 2025</div>
                    </div>
                </footer>
            </div>
        </div>
        <script src="https://cdn.jsdelivr.net/npm/bootstrap@5.3.0/dist/js/bootstrap.bundle.min.js" crossorigin="anonymous"></script>
        <script src="https://code.jquery.com/jquery-3.6.0.min.js" crossorigin="anonymous"></script>
        <script src="https://cdn.datatables.net/1.13.4/js/jquery.dataTables.min.js" crossorigin="anonymous"></script>
        <script src="https://cdn.datatables.net/1.13.4/js/dataTables.bootstrap5.min.js" crossorigin="anonymous"></script>
        <script src="${pageContext.request.contextPath}/assets/js/scripts.js"></script>
        <script src="https://cdn.jsdelivr.net/npm/sweetalert2@11"></script>
        <script>
            $(document).ready(function () {
                const dataTable = $('#revenueTable').DataTable({
                    language: {
                        search: "Tìm kiếm:",
                        emptyTable: "Không tìm thấy dữ liệu",
                        info: "Hiển thị _START_ đến _END_ của _TOTAL_ mục",
                        lengthMenu: "Hiển thị _MENU_ mục",
                        paginate: {
                            first: "Đầu",
                            last: "Cuối",
                            next: "Tiếp",
                            previous: "Trước"
                        }
                    },
                    pageLength: 10,
                    lengthMenu: [5, 10, 15, 20],
                    order: [[0, 'desc']],
                    columnDefs: [
                        {targets: [7], orderable: false} // Không cho phép sắp xếp cột Hành động
                    ]
                });

                // Tìm kiếm theo mã đơn hoặc tên khách hàng
                $('#searchInput').on('keyup', function () {
                    const searchValue = this.value.toLowerCase();
                    dataTable.search(searchValue).draw();
                });
            });
        </script>
    </body>
</html>