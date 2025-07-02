<%--
* Copyright (C) 2025, Group 6.
 * ProjectCode/Short Name of Application: TravelAgentService 
 * Support Management and Provide Travel Service System 
 *
 * Record of change:
 * DATE        Version    AUTHOR            DESCRIPTION
 * 2025-06-07  1.0        Hà Thị Duyên      First implementationF
--%>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>

<!DOCTYPE html>
<html>
    <head>
        <meta charset="UTF-8">
        <title>Thông tin cá nhân</title>
        <!-- Load CSS từ CDN và file cục bộ -->
        <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.0/dist/css/bootstrap.min.css" rel="stylesheet">
        <link href="https://fonts.googleapis.com/css2?family=Poppins:wght@300;400;600&display=swap" rel="stylesheet">
        <link href="https://cdnjs.cloudflare.com/ajax/libs/font-awesome/5.10.0/css/all.min.css" rel="stylesheet">
        <link href="https://cdn.jsdelivr.net/npm/bootstrap-icons@1.4.1/font/bootstrap-icons.css" rel="stylesheet">
        <link href="${pageContext.request.contextPath}/assets/css/bootstrap.min.css" rel="stylesheet">
        <link href="${pageContext.request.contextPath}/assets/css/style.css" rel="stylesheet">

        <style>
            .profile-info {
                font-size: 16px;
                color: #333;
                margin-bottom: 10px;
                word-wrap: break-word; /* Cho phép ngắt từ dài */
                overflow-wrap: break-word;
            }

            .profile-container {
                width: 100%;
                max-width: 800px;
                background-color: #ffffff;
                border-radius: 8px;
                padding: 30px;
                box-shadow: 0 4px 12px rgba(0, 0, 0, 0.1);
                margin: 120px auto 20px auto;
                position: relative;
                z-index: 1;
                overflow: auto; /* Xử lý nội dung tràn */
            }

            body {
                background: url('${pageContext.request.contextPath}/assets/img/img_10.jpg') no-repeat center center fixed;
                background-size: cover;
                margin: 0;
                min-height: 100vh;
                position: relative;
                display: flex;
                flex-direction: column;
                overflow-x: hidden; /* Ngăn tràn ngang */
            }
            .content-wrapper {
                flex: 1 0 auto;
                z-index: 1;
            }

            .profile-title {
                font-size: 24px;
                font-weight: 600;
                color: #007bff;
                margin-bottom: 20px;
                text-align: center;
            }

            .profile-info label {
                font-weight: 500;
                color: #555;
                display: inline-block;
                width: 150px;
            }
            .btn-back, .btn-edit {
                margin-top: 15px;
                padding: 10px 20px;
                border-radius: 6px;
                text-decoration: none;
                font-weight: 600;
                display: inline-block;
                transition: background-color 0.3s ease;
            }
            .btn-back {
                background-color: #dc3545;
                color: #fff;
            }
            .btn-back:hover {
                background-color: #c82333;
            }
            .btn-edit {
                background-color: #28a745;
                color: #fff;
            }
            .btn-edit:hover {
                background-color: #218838;
            }
            .alert {
                border-radius: 6px;
                margin-bottom: 20px;
            }
            footer {
                flex-shrink: 0;
                margin-top: 20px;
                background-color: transparent;
                color: #333;
                text-align: center;
                padding: 10px 0;
            }
            
        </style>
    </head>
    <body>
        <!-- Include header -->
        <%@include file="../layout/header.jsp" %>

        <div class="content-wrapper">
            <div class="profile-container">
                <h2 class="profile-title">Thông tin cá nhân</h2>
                <c:if test="${not empty error}">
                    <div class="alert alert-danger">${error}</div>
                </c:if>
                <c:if test="${not empty success}">
                    <div class="alert alert-success">${success}</div>
                </c:if>

                <c:if test="${not empty sessionScope.loginUser}">
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
                        <label>Ngày sinh:</label> <fmt:formatDate value="${sessionScope.loginUser.dob}" pattern="dd/MM/yyyy"/>
                    </div>
                    <div class="profile-info">
                        <label>Gmail:</label> ${sessionScope.loginUser.gmail}
                    </div>
                    <div class="profile-info">
                        <label>Địa chỉ:</label> ${sessionScope.loginUser.address}
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
                    <a href="${pageContext.request.contextPath}/ProfileUser?service=editProfileUser" class="btn-edit">Chỉnh sửa</a>
                    <a href="${pageContext.request.contextPath}/home" class="btn-back">Quay lại trang chủ</a>
                </c:if>
            </div>
        </div>

        <!-- Include footer -->
        <%@include file="../layout/footer.jsp" %>

        <!-- Load JavaScript -->
        <script src="https://code.jquery.com/jquery-3.4.1.min.js"></script>
        <script src="https://cdn.jsdelivr.net/npm/bootstrap@5.0.0/dist/js/bootstrap.bundle.min.js"></script>
        <script src="${pageContext.request.contextPath}/assets/lib/wow/wow.min.js"></script>
        <script src="${pageContext.request.contextPath}/assets/lib/easing/easing.min.js"></script>
        <script src="${pageContext.request.contextPath}/assets/lib/waypoints/waypoints.min.js"></script>
        <script src="${pageContext.request.contextPath}/assets/lib/owlcarousel/owl.carousel.min.js"></script>
        <script src="${pageContext.request.contextPath}/assets/lib/tempusdominus/js/moment.min.js"></script>
        <script src="${pageContext.request.contextPath}/assets/lib/tempusdominus/js/moment-timezone.min.js"></script>
        <script src="${pageContext.request.contextPath}/assets/lib/tempusdominus/js/tempusdominus-bootstrap-4.min.js"></script>
        <script src="${pageContext.request.contextPath}/assets/js/main.js"></script>
    </body>
</html>