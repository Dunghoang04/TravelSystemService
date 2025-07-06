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
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>JSP Page</title>
        
        
    </head>
    <body>
        <div id="layoutSidenav_nav">
                <nav class="sb-sidenav accordion sb-sidenav-dark" id="sidenavAccordion">
                    <div class="sb-sidenav-menu">
                        <div class="nav">
                            <div class="sb-sidenav-menu-heading">Bảng điều khiển</div>
                            <a class="nav-link" href="StatisticAgent">
                                <div class="sb-nav-link-icon"><i class="fas fa-tachometer-alt"></i></div>
                                Thống kê
                            </a>
                            <a class="nav-link" href="ListTour?service=list">
                                <div class="sb-nav-link-icon"><i class="fa-solid fa-gears"></i></div>
                                Quản lí danh sách chuyến đi
                            </a>

                            <a class="nav-link" href="ManagementAccommodation">
                                <div class="sb-nav-link-icon"><i class="fa-solid fa-hotel"></i></i></div>
                                Quản lí khách sạn
                            </a>
                            
                            <a class="nav-link" href="managerestaurant">
                                <div class="sb-nav-link-icon"><i class="fa-solid fa-utensils"></i></i></div>
                                Quản lí nhà hàng
                            </a>
                            <a class="nav-link" href="managementertainment">
                                <div class="sb-nav-link-icon"><i class="fa-solid fa-face-smile"></i></i></div>
                                Quản lí giải trí
                            </a>
                            <a class="nav-link" href="${pageContext.request.contextPath}/ManageTravelAgentProfile">
                                <div class="sb-nav-link-icon"><i class="fa-solid fa-user"></i></i></div>
                                Thông tin cá nhân
                            </a>


                        </div>
                    </div>

                </nav>
            </div>
    </body>
</html>
