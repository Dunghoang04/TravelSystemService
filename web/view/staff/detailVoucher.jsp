<%-- 
    Document   : DetailVoucher
    Created on : May 31, 2025, 1:19:48 PM
    Author     : Hung
--%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
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
        <link href="${pageContext.request.contextPath}/assets/css/styles2.css" rel="stylesheet" />
        <script src="https://use.fontawesome.com/releases/v6.3.0/js/all.js" crossorigin="anonymous"></script>
        <meta charset="UTF-8">
        <title>Chi tiết thẻ khuyến mãi</title>


    </head>
    <body class="sb-nav-fixed">
        <%@ include file="../layout/headerAdmin.jsp" %>

        <div id="layoutSidenav" style="margin-top: 5px">
            <%@include file="../layout/sideNavOptionStaff.jsp" %>
            <div id="layoutSidenav_content">
                <main>
                    <div class="container-fluid px-4">
                        <!-- Nút quay lại ở trên cùng bên trái -->
                        <div class="mb-3">
                            <a href="listvoucher" class="btn btn-secondary">
                                <i class="fas fa-arrow-left"></i> Quay lại danh sách
                            </a>
                        </div>

                        <div class="voucher-detail-card mb-4">
                            <h2>Chi tiết thẻ khuyến mãi</h2>
                            <div class="voucher-detail-item"><label>ID: </label><span>${voucher.voucherId}</span></div>
                            <div class="voucher-detail-item"><label>Mã Voucher:</label><span>${voucher.voucherCode}</span></div>
                            <div class="voucher-detail-item"><label>Tên:</label><span>${voucher.voucherName}</span></div>
                            <div class="voucher-detail-item"><label>Chi tiết:</label><span>${voucher.description}</span></div>
                            <div class="voucher-detail-item"><label>Phần trăm khuyến mãi:</label><span>${voucher.percentDiscount}%</span></div>
                            <div class="voucher-detail-item"><label>Giảm tối đa:</label>
                                <span><fmt:formatNumber value="${voucher.maxDiscountAmount}" type="number" groupingUsed="true"/></span>
                            </div>
                            <div class="voucher-detail-item"><label>Giá trị đơn tối thiểu áp dụng:</label>
                                <span><fmt:formatNumber value="${voucher.minAmountApply}" type="number" groupingUsed="true"/></span>
                            </div>
                            <div class="voucher-detail-item"><label>Ngày bắt đầu:</label><span>${voucher.startDate}</span></div>
                            <div class="voucher-detail-item"><label>Ngày kết thúc:</label><span>${voucher.endDate}</span></div>
                            <div class="voucher-detail-item"><label>Số lượng:</label><span>${voucher.quantity}</span></div>
                            <div class="voucher-detail-item"><label>Trạng thái:</label>
                                <span>
                                    <c:choose>
                                        <c:when test="${voucher.status == 1}">Hoạt động</c:when>
                                        <c:otherwise>Không hoạt động</c:otherwise>
                                    </c:choose>
                                </span>
                            </div>
                        </div>

                        <!-- BẢNG LỊCH SỬ CẬP NHẬT -->
                        <div class="card mb-4">
                            <div class="card-header"><i class="fas fa-history me-1"></i> Lịch sử cập nhật voucher</div>
                            <div class="card-body table-responsive">
                                <c:if test="${not empty listHistory}">
                                    <table class="table table-bordered">
                                        <thead class="table-light">
                                            <tr>
                                                <th>STT</th>
                                                <th>Trường thay đổi</th>
                                                <th>Giá trị cũ</th>
                                                <th>Giá trị mới</th>
                                                <th>Ngày cập nhật</th>
                                                <th>Người cập nhật</th>
                                            </tr>
                                        </thead>
                                        <tbody>
                                            <c:forEach var="h" items="${listHistory}" varStatus="loop">
                                                <tr>
                                                    <td>${(currentPage - 1) * 5 + loop.index + 1}</td>
                                                    <td>${h.fieldName}</td>
                                                    <td>${h.oldValue}</td>
                                                    <td>${h.newValue}</td>
                                                    <td><fmt:formatDate value="${h.updateDate}" pattern="dd/MM/yyyy HH:mm:ss"/></td>
                                                    <td>${h.updatedByName}</td>
                                                </tr>
                                            </c:forEach>
                                        </tbody>
                                    </table>

                                    <!-- PHÂN TRANG -->
                                    <nav>
                                        <ul class="pagination">
                                            <c:forEach begin="1" end="${totalPages}" var="i">
                                                <li class="page-item ${i == currentPage ? 'active' : ''}">
                                                    <a class="page-link" href="detailvoucher?voucherId=${voucherId}&page=${i}">${i}</a>
                                                </li>
                                            </c:forEach>
                                        </ul>
                                    </nav>
                                </c:if>
                                <c:if test="${empty listHistory}">
                                    <div class="alert alert-warning">Chưa có lịch sử cập nhật nào.</div>
                                </c:if>
                            </div>
                        </div>

                    </div>
                </main>
                <%@include file="../layout/footerStaff.jsp" %>
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

