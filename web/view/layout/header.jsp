<%-- 
 * Copyright (C) 2025, Group 6.
 * ProjectCode/Short Name of Application: TravelSystemService 
 * Support Management and Provide Travel Service System 
 *
 * Record of change:
 * DATE        Version    AUTHOR            DESCRIPTION
 * 2025-06-07  1.0        Group 6         First implementation
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>

<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <style>
            /* Đẩy menu sang phải nhẹ nhàng, không căn giữa */
            .navbar-collapse {
                display: flex !important;
                justify-content: flex-start !important;
                margin-left: -30px; /* Dịch menu sang trái */
            }

            .navbar-nav {
                display: flex;
                align-items: center;
                padding-left: 50px; /* Tạo khoảng cách giữa logo và menu */
            }


            /* Căn dropdown avatar & làm đẹp */
            .dropdown {
                position: relative;
                margin-left: 20px;
            }

            .dropdown-menu {
                left: 50%;
                transform: translateX(-50%);

                min-width: 160px;
            }

            .dropdown-toggle i {
                font-size: 20px;
                color: #28a745;
            }

            .btn.border {
                border-radius: 50%;
                width: 40px;
                height: 40px;
                display: flex;
                justify-content: center;
                align-items: center;
                padding: 0;
            }

        </style>

    </head>
    <body>
        <header class="navbar navbar-expand-lg navbar-light px-4 px-lg-5 py-3 py-lg-0">
            <a href="" class="navbar-brand p-0">
                <h1 style="font-style: italic" class="text-primary m-0"></i>Go<span style="color: red">Viet</span></h1>
                <!-- <img src="img/logo.png" alt="Logo"> -->
            </a>
            <button class="navbar-toggler" type="button" data-bs-toggle="collapse" data-bs-target="#navbarCollapse">
                <span class="fa fa-bars"></span>
            </button>
            <div class="navbar-nav ms-auto py-0">
                <a href="index.html" class="nav-item nav-link active">Trang chủ</a>
                <a href="service.html" class="nav-item nav-link">Dịch vụ</a>
                <a href="package.html" class="nav-item nav-link">Nổi bật</a>
                <a href="booking.html" class="nav-item nav-link">Đặt lịch</a>  

                <c:if test="${empty sessionScope.loginUser}">
                    <a href="${pageContext.request.contextPath}/RegisterTravelAgentServlet" class="nav-item nav-link">Đối tác</a>
                </c:if>

                <a href="contact.html" class="nav-item nav-link">Liên hệ</a>

                <c:choose>
                    <c:when test="${empty sessionScope.loginUser}">
                        <a href="${pageContext.request.contextPath}/GmailUser" class="nav-item nav-link">Đăng ký</a>
                        <a href="${pageContext.request.contextPath}/LoginLogout" class="nav-item nav-link">Đăng nhập</a>
                    </c:when>
                    <c:otherwise>
                        <div class="dropdown">
                            <a class="btn border dropdown-toggle" href="#" role="button" id="accountDropdown" data-bs-toggle="dropdown" aria-expanded="false">
                                <i class="fas fa-user text-primary"></i>
                            </a>
                            <ul class="dropdown-menu" aria-labelledby="accountDropdown">
                                <li><a class="dropdown-item" href="${pageContext.request.contextPath}/ProfileUser">Xem tài khoản</a></li>
                                <li><a class="dropdown-item" href="${pageContext.request.contextPath}/LoginLogout?service=logoutUser">Đăng xuất</a></li>
                            </ul>
                        </div>
                    </c:otherwise>
                </c:choose>
            </div>

        </header>
    </body>
</html>
