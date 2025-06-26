<%--
* Copyright (C) 2025, Group 6.
 * ProjectCode/Short Name of Application: TravelAgentService 
 * Support Management and Provide Travel Service System 
 *
 * Record of change:
 * DATE        Version    AUTHOR            DESCRIPTION
 * 2025-06-24  1.0        Hà Thị Duyên      First implementationF
--%>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>

<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="utf-8" />
    <meta http-equiv="X-UA-Compatible" content="IE=edge" />
    <meta name="viewport" content="width=device-width, initial-scale=1, shrink-to-fit=no" />
    <meta name="description" content="Admin Profile for TravelSystemService" />
    <meta name="author" content="Group 6" />
    <title>Profile - Admin</title>
    <link href="https://cdn.jsdelivr.net/npm/simple-datatables@7.1.2/dist/style.min.css" rel="stylesheet" />
    <link href="${pageContext.request.contextPath}/assets/css/styles2.css" rel="stylesheet" />
    <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.0/dist/css/bootstrap.min.css" rel="stylesheet" />
    <script src="https://use.fontawesome.com/releases/v6.3.0/js/all.js" crossorigin="anonymous"></script>
    <style>
        .card {
            transition: transform 0.2s;
        }
        .card:hover {
            transform: translateY(-5px);
        }
        .content {
            margin-left: 10%;
        }
        .profile-card {
            background-color: #fff;
            border-radius: 8px;
            padding: 20px;
            margin-bottom: 20px;
        }
        .profile-title {
            font-size: 28px; /* Tăng từ 24px lên 28px */
            font-weight: bold;
            color: #007bff;
            margin-bottom: 20px;
            text-align: center;
        }
        .profile-info {
            font-size: 18px; /* Tăng từ 16px lên 18px */
            color: #333;
            margin-bottom: 10px;
        }
        .profile-info label {
            font-size: 18px; /* Tăng từ 16px lên 18px */
            font-weight: 700; /* Tăng từ 500 lên 700 để đậm hơn */
            color: #555;
            display: inline-block;
            width: 150px;
        }
        .profile-columns {
            display: flex;
            justify-content: space-between;
            align-items: stretch; /* Đảm bảo hai cột có chiều cao bằng nhau */
        }
        .profile-column {
            flex: 1;
            padding: 0 15px;
            display: flex;
            flex-direction: column;
            justify-content: space-between;
        }
        .btn-action {
            margin-top: 15px;
            padding: 10px 20px;
            border-radius: 6px;
            text-decoration: none;
            font-weight: 600;
            display: inline-block;
            transition: background-color 0.3s ease;
            font-size: 16px; /* Tăng kích thước chữ nút */
        }
        .btn-edit {
            background-color: #28a745;
            color: #fff;
        }
        
        .btn-back {
            background-color: #696969;
            color: #fff;
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
                    <h1 class="mt-4 profile-title">Thông tin Admin</h1>
                   
                    <c:if test="${not empty sessionScope.loginUser && sessionScope.loginUser.roleID == 1}">
                        <div class="profile-card shadow">
                            <div class="profile-columns">
                                <div class="profile-column">
                                    <div class="profile-info">
                                        <label>Họ:</label> ${sessionScope.loginUser.lastName}
                                    </div>
                                    <div class="profile-info">
                                        <label>Tên:</label> ${sessionScope.loginUser.firstName}
                                    </div>
                                    <div class="profile-info">
                                        <label>Mật khẩu:</label> ********
                                    </div>
                                    <div class="profile-info">
                                        <label>Giới tính:</label> 
                                        <c:choose>
                                            <c:when test="${sessionScope.loginUser.gender.toString().equalsIgnoreCase('Nam')}">Nam</c:when>
                                            <c:when test="${sessionScope.loginUser.gender.toString().equalsIgnoreCase('Nữ')}">Nữ</c:when>
                                            <c:otherwise>Khác</c:otherwise>
                                        </c:choose>
                                    </div>
                                    <div class="profile-info">
                                        <label>Số điện thoại:</label> ${sessionScope.loginUser.phone}
                                    </div>
                                    <div class="profile-info">
                                        <label>Địa chỉ:</label> ${sessionScope.loginUser.address}
                                    </div>
                                </div>
                                <div class="profile-column">
                                    <div class="profile-info">
                                        <label>Ngày sinh:</label><fmt:formatDate value="${sessionScope.loginUser.dob}" pattern="dd/MM/yyyy"/>
                                    </div>
                                    <div class="profile-info">
                                        <label>Gmail:</label> ${sessionScope.loginUser.gmail}
                                    </div>
                                    <div class="profile-info">
                                        <label>Ngày tạo:</label> ${sessionScope.loginUser.createDate}
                                    </div>
                                    <div class="profile-info">
                                        <label>Ngày cập nhật:</label> ${sessionScope.loginUser.updateDate}
                                    </div>
                                    <div class="profile-info">
                                        <label>Trạng thái:</label> 
                                        <c:choose>
                                            <c:when test="${sessionScope.loginUser.status == 1}">Đang hoạt động</c:when>
                                            <c:when test="${sessionScope.loginUser.status == 0}">Không hoạt động</c:when>
                                            <c:otherwise>Không xác định</c:otherwise>
                                        </c:choose>
                                    </div>
                                </div>
                            </div>
                            <a href="${pageContext.request.contextPath}/ManageAdminProfile?service=editAdminProfile" class="btn-action btn-edit">Chỉnh sửa</a>
                            <a href="${pageContext.request.contextPath}/StatisticalAdmin" class="btn-action btn-back">Quay lại dashboard</a>
                        </div>
                    </c:if>
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
    <script src="https://cdn.jsdelivr.net/npm/simple-datatables@7.1.2/dist/umd/simple-datatables.min.js" crossorigin="anonymous"></script>
    <script src="${pageContext.request.contextPath}/assets/js/scripts.js"></script>
</body>
</html>