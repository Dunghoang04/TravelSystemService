<%--
* Copyright (C) 2025, Group 6.
 * ProjectCode/Short Name of Application: TravelAgentService 
 * Support Management and Provide Travel Service System 
 *
 * Record of change:
 * DATE        Version    AUTHOR            DESCRIPTION
 * 2025-07-02  1.0        Hà Thị Duyên      First implementation for staff profile
--%>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>

<!DOCTYPE html>
<html lang="vi">
<head>
    <meta charset="utf-8" />
    <meta http-equiv="X-UA-Compatible" content="IE=edge" />
    <meta name="viewport" content="width=device-width, initial-scale=1, shrink-to-fit=no" />
    <meta name="description" content="Staff Profile for TravelSystemService" />
    <meta name="author" content="Group 6" />
    <title>Hồ sơ - Nhân viên</title>
    <link href="https://cdn.jsdelivr.net/npm/simple-datatables@7.1.2/dist/style.min.css" rel="stylesheet" />
    <link href="${pageContext.request.contextPath}/assets/css/styles2.css" rel="stylesheet" />
    <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.0/dist/css/bootstrap.min.css" rel="stylesheet" />
    <link href="https://cdn.jsdelivr.net/npm/sweetalert2@11/dist/sweetalert2.min.css" rel="stylesheet" />
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
            font-size: 28px;
            font-weight: bold;
            color: #007bff;
            margin-bottom: 20px;
            text-align: center;
        }
        .profile-info {
            font-size: 18px;
            color: #333;
            margin-bottom: 10px;
        }
        .profile-info label {
            font-size: 18px;
            font-weight: 700;
            color: #555;
            display: inline-block;
            width: 150px;
        }
        .profile-columns {
            display: flex;
            justify-content: space-between;
            align-items: stretch;
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
            font-size: 16px;
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
        <%@include file="../layout/sideNavOptionStaff.jsp" %>
        <div id="layoutSidenav_content">
            <main class="content">
                <div class="container-fluid px-4">
                    <h1 class="mt-4 profile-title">Thông tin Nhân viên</h1>
                    
                    <c:if test="${not empty error}">
                        <div class="alert alert-danger">${error}</div>
                    </c:if>

                    <c:if test="${not empty staff && sessionScope.loginUser.roleID != 1}">
                        <div class="profile-card shadow">
                            <div class="profile-columns">
                                <div class="profile-column">
                                    <div class="profile-info">
                                        <label>Mã nhân viên:</label> ${staff.employeeCode}
                                    </div>
                                    <div class="profile-info">
                                        <label>Họ:</label> ${staff.lastName}
                                    </div>
                                    <div class="profile-info">
                                        <label>Tên:</label> ${staff.firstName}
                                    </div>
                                    <div class="profile-info">
                                        <label>Mật khẩu:</label> ********
                                    </div>
                                    <div class="profile-info">
                                        <label>Giới tính:</label> 
                                        <c:choose>
                                            <c:when test="${staff.gender.toString().equalsIgnoreCase('Nam')}">Nam</c:when>
                                            <c:when test="${staff.gender.toString().equalsIgnoreCase('Nữ')}">Nữ</c:when>
                                            <c:otherwise>Khác</c:otherwise>
                                        </c:choose>
                                    </div>
                                    <div class="profile-info">
                                        <label>Số điện thoại:</label> ${staff.phone}
                                    </div>
                                </div>
                                <div class="profile-column">
                                    <div class="profile-info">
                                        <label>Ngày sinh:</label> <fmt:formatDate value="${staff.dob}" pattern="dd/MM/yyyy"/>
                                    </div>
                                    <div class="profile-info">
                                        <label>Gmail:</label> ${staff.gmail}
                                    </div>
                                    <div class="profile-info">
                                        <label>Địa chỉ:</label> ${staff.address}
                                    </div>
                                    <div class="profile-info">
                                        <label>Ngày vào làm:</label> <fmt:formatDate value="${staff.hireDate}" pattern="dd/MM/yyyy"/>
                                    </div>
                                    <div class="profile-info">
                                        <label>Trạng thái làm việc:</label> ${staff.workStatus}
                                    </div>
                                    <div class="profile-info">
                                        <label>Ngày cập nhật:</label> <fmt:formatDate value="${staff.updateDate}" pattern="dd/MM/yyyy"/>
                                    </div>
                                </div>
                            </div>
                            <a href="${pageContext.request.contextPath}/ManageStaffProfile?service=editStaffProfile" class="btn-action btn-edit">Chỉnh sửa</a>
                            <a href="${pageContext.request.contextPath}/dashboard" class="btn-action btn-back">Quay lại dashboard</a>
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
    <script src="https://cdn.jsdelivr.net/npm/sweetalert2@11/dist/sweetalert2.all.min.js"></script>
    <script src="${pageContext.request.contextPath}/assets/js/scripts.js"></script>
    <script>
        <c:if test="${not empty successMessage}">
            Swal.fire({
                title: 'Thành công!',
                text: '${successMessage}',
                icon: 'success',
                timer: 1500,
                showConfirmButton: false
            });
        </c:if>
    </script>
</body>
</html>