<%--
 * Copyright (C) 2025, Group 6.
 * ProjectCode/Short Name of Application: TravelSystemService
 * Support Management and Provide Travel Service System
 *
 * Record of change:
 * DATE        Version    AUTHOR                    DESCRIPTION
 * 2025-07-01  1.0        Nguyen Huu Hung           First implementation
--%>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<!DOCTYPE html>
<html>
    <head>
        <meta charset="UTF-8">
        <title>Quản lí yêu cầu huỷ chuyến đi của du khách</title>
        <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.0/dist/css/bootstrap.min.css" rel="stylesheet" />
        <link href="./assets/css/styles2.css" rel="stylesheet" />
        <link rel="stylesheet" href="https://cdnjs.cloudflare.com/ajax/libs/font-awesome/6.4.0/css/all.min.css"/>
    </head>
    <body>
        <%@include file="../layout/headerAdmin.jsp" %>
        <div id="layoutSidenav">
            <%@include file="../layout/sideNavOptionStaff.jsp" %>
            <div id="layoutSidenav_content">
                <main class="container mt-5">
                    <h2 class="mb-4">Quản lý yêu cầu huỷ chuyến đi của du khách</h2>

                    <!-- Tìm kiếm + lọc -->
                    <div class="d-flex align-items-center mb-4 flex-wrap">
                        <!-- Tìm kiếm -->
                        <form action="requestcancelmanage" method="get" class="d-flex align-items-center me-auto mb-2">
                            <div class="d-flex align-items-center gap-2">
                                <input type="text" name="keyword" class="form-control me-2" placeholder="Nhập email"
                                       value="${param.keyword}" />
                                <button type="submit" class="btn btn-primary">
                                    <i class="fa fa-search"></i> Tìm kiếm
                                </button>
                            </div>
                        </form>

                        <!-- Lọc trạng thái -->
                        <form action="requestcancelmanage" method="get" class="d-flex align-items-center mb-2">
                            <label for="status" class="me-2 mb-0 fw-bold">Lọc theo trạng thái:</label>
                            <select name="status" id="status" class="form-select" style="width: 200px;" onchange="this.form.submit()">
                                <option value="">Tất cả</option>
                                <option value="PENDING" ${param.status == 'PENDING' ? 'selected' : ''}>Chờ xử lý</option>
                                <option value="FINISHED" ${param.status == 'FINISHED' ? 'selected' : ''}>Đã duyệt</option>
                                <option value="REJECTED" ${param.status == 'REJECTED' ? 'selected' : ''}>Đã từ chối</option>
                            </select>
                        </form>
                    </div>

                    <!-- Bảng dữ liệu -->
                    <table class="table table-bordered table-hover">
                        <thead class="table-light">
                            <tr>
                                <th>STT</th>
                                <th>Email</th>
                                <th>Lí do huỷ</th>
                                <th>Ngày yêu cầu</th>
                                <th>Ngày khởi hành</th>
                                <th>Số tiền hoàn trả</th>
                                <th>Trạng thái</th>
                                <th>Hành động</th>
                            </tr>
                        </thead>
                        <tbody>
                            <c:forEach var="r" items="${listRequest}" varStatus="loop">
                                <c:set var="offset" value="${(currentPage - 1) * 10}" />
                                <tr>
                                    <td>${loop.index + 1 + offset}</td>
                                    <td>${r.bookDetail.gmail}</td>
                                    <td>${r.requestCancel.reason}</td>
                                    <td><fmt:formatDate value="${r.requestCancel.requestDate}" pattern="dd/MM/yyyy" /></td>
                                    <td><fmt:formatDate value="${r.tour.startDay}" pattern="dd/MM/yyyy" /></td>
                                    <td>
                                        <c:set var="now" value="${r.requestCancel.requestDate}" />
                                        <c:set var="bookingDate" value="${r.bookDetail.bookDate}" />
                                        <c:set var="startDate" value="${r.tour.startDay}" />
                                        <c:set var="totalPrice" value="${r.bookDetail.totalPrice}" />
                                        <c:set var="daysToStart" value="${(startDate.time - now.time) / (1000 * 60 * 60 * 24)}" />
                                        <c:set var="hoursSinceBooking" value="${(now.time - bookingDate.time) / (1000 * 60 * 60)}" />

                                        <c:choose>
                                            <c:when test="${hoursSinceBooking <= 24}">
                                                <fmt:formatNumber value="${totalPrice.longValue()}"/> VNĐ
                                            </c:when>
                                            <c:when test="${daysToStart >= 7}">
                                                <fmt:formatNumber value="${(totalPrice * 0.8).longValue()}"/> VNĐ
                                            </c:when>
                                            <c:when test="${daysToStart >= 3 && daysToStart < 7}">
                                                <fmt:formatNumber value="${(totalPrice * 0.5).longValue()}"/> VNĐ
                                            </c:when>
                                            <c:otherwise>
                                                0 VNĐ
                                            </c:otherwise>
                                        </c:choose>
                                    </td>
                                    <td>

                                        <c:choose>
                                            <c:when test="${r.requestCancel.status ==1}">
                                                <span class="badge bg-warning text-dark">Chờ xử lý</span>
                                            </c:when>
                                            <c:when test="${r.requestCancel.status ==2}">
                                                <span class="badge bg-success">Đã duyệt</span>
                                            </c:when>
                                            <c:when test="${r.requestCancel.status ==3}">
                                                <span class="badge bg-danger">Đã từ chối</span>
                                            </c:when>
                                        </c:choose>
                                    </td>
                                    <td class="text-center">
                                        <a href="requestcanceldetail?id=${r.requestCancel.requestCancelID}" class="btn btn-sm btn-outline-primary">
                                            <i class="fa fa-eye"></i> Xem chi tiết
                                        </a>
                                    </td>
                                </tr>
                            </c:forEach>

                            <c:if test="${empty listRequest}">
                                <tr>
                                    <td colspan="7" class="text-center">Không có yêu cầu nào</td>
                                </tr>
                            </c:if>
                        </tbody>
                    </table>
                    

                    <!-- Phân trang -->
                    <c:if test="${not empty totalPages && totalPages > 1}">
                        <nav aria-label="Page navigation">
                            <ul class="pagination justify-content-center">
                                <c:forEach begin="1" end="${totalPages}" var="i">
                                    <li class="page-item ${i == currentPage ? 'active' : ''}">
                                        <a class="page-link"
                                           href="requestcancelmanage?page=${i}&keyword=${param.keyword}&status=${param.status}">
                                            ${i}
                                        </a>
                                    </li>
                                </c:forEach>
                            </ul>
                        </nav>
                    </c:if>
                </main>
                <footer class="py-4 bg-light mt-auto">
                    <%@include file="../layout/footerStaff.jsp" %>
                </footer>
            </div>
        </div>
        <script src="https://cdn.jsdelivr.net/npm/bootstrap@5.3.0/dist/js/bootstrap.bundle.min.js"></script>
    </body>
</html>
