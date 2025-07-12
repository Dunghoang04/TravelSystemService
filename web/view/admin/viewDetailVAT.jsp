<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html lang="en">
    <head>
        <meta charset="utf-8" />
        <meta http-equiv="X-UA-Compatible" content="IE=edge" />
        <meta name="viewport" content="width=device-width, initial-scale=1, shrink-to-fit=no" />
        <meta name="description" content="View VAT Details for TravelSystemService" />
        <meta name="author" content="Group 6" />
        <title>Chi Tiết VAT - Admin</title>
        <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.0/dist/css/bootstrap.min.css" rel="stylesheet" />
        <link href="${pageContext.request.contextPath}/assets/css/styles2.css" rel="stylesheet" />
        <script src="https://use.fontawesome.com/releases/v6.3.0/js/all.js" crossorigin="anonymous"></script>
        <style>
            .content {
                margin: 0 auto !important;
                width: 1200px !important;
            }
            .card {
                transition: transform 0.2s !important;
            }
            .card:hover {
                transform: translateY(-5px) !important;
            }
            .form-label {
                font-weight: 500 !important;
            }
            .error-message {
                color: #dc3545 !important;
                font-size: 0.875rem !important;
                margin-top: 0.25rem !important;
            }
            .form-section {
                margin-bottom: 2rem !important;
            }
            .btn-back {
                background-color: #6c757d !important;
                border-color: #6c757d !important;
            }
            .btn-back:hover {
                background-color: #5a6268 !important;
                border-color: #545b62 !important;
            }
            .table th, .table td {
                text-align: left;
                vertical-align: middle;
                padding: 8px;
                border: none;
            }
            .status-active {
                background-color: #28a745;
                color: white;
                padding: 4px 12px;
                border-radius: 20px;
                border: none;
                cursor: default;
            }
            .status-inactive {
                background-color: #dc3545;
                color: white;
                padding: 4px 12px;
                border-radius: 20px;
                border: none;
                cursor: default;
            }
            .status-expired {
                background-color: #6c757d;
                color: white;
                padding: 4px 12px;
                border-radius: 20px;
                border: none;
                cursor: default;
            }
        </style>
    </head>
    <body class="sb-nav-fixed">
        <%@include file="../layout/headerAdmin.jsp" %>
        <div id="layoutSidenav">
            <%@include file="../layout/sideNavOptionAdmin.jsp" %>
            <div id="layoutSidenav_content">
                <main class="content">
                    <div class="container-fluid px-4">
                        <h1 class="mt-4">Chi Tiết VAT</h1>
                        <div class="card mb-4">
                            <div class="card-header">
                                <i class="fas fa-info-circle me-1"></i>
                                Thông Tin VAT
                            </div>
                            <div class="card-body">
                                <c:if test="${not empty generalError}">
                                    <div class="error-message">${generalError}</div>
                                </c:if>
                                <c:if test="${empty vat}">
                                    <div class="error-message">Không tìm thấy thông tin VAT.</div>
                                </c:if>
                                <c:if test="${not empty vat}">
                                    <table class="table">
                                        <tr><th style="width: 30%;">ID VAT</th><td>${vat.vatID}</td></tr>
                                        <tr><th>Tỷ lệ VAT (%)</th><td><fmt:formatNumber value="${vat.vatRate}" pattern="#,##0.##" />%</td></tr>
                                        <tr><th>Mô tả</th><td>${vat.description}</td></tr>
                                        <tr><th>Ngày bắt đầu</th><td><fmt:formatDate value="${vat.startDate}" pattern="dd/MM/yyyy" /></td></tr>
                                        <tr><th>Ngày kết thúc</th><td><fmt:formatDate value="${vat.endDate}" pattern="dd/MM/yyyy" /></td></tr>
                                        <tr>
                                            <th>Trạng Thái</th>
                                            <td>
                                                <c:choose>
                                                    <c:when test="${vat.status == 1}">
                                                        <button class="status-active" disabled>Hoạt động</button>
                                                    </c:when>
                                                    <c:when test="${vat.status == 2}">
                                                        <button class="status-expired" disabled>Hết hiệu lực</button>
                                                    </c:when>
                                                    <c:otherwise>
                                                        <button class="status-inactive" disabled>Không hoạt động</button>
                                                    </c:otherwise>
                                                </c:choose>
                                            </td>
                                        </tr>
                                        <tr><th>Ngày Tạo</th><td><fmt:formatDate value="${vat.createDate}" pattern="dd/MM/yyyy" /></td></tr>
                                        <tr><th>Ngày Cập Nhật</th><td><fmt:formatDate value="${vat.updateDate}" pattern="dd/MM/yyyy" /></td></tr>
                                    </table>
                                    <div class="text-center">
                                        <a href="${pageContext.request.contextPath}/VATServlet?service=listVAT" class="btn btn-back me-2">Quay Lại</a>
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
        <script src="${pageContext.request.contextPath}/assets/js/scripts.js"></script>
    </body>
</html>