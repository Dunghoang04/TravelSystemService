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
                            <a class="nav-link" href="StatisticalAdmin">
                                <div class="sb-nav-link-icon"><i class="fas fa-tachometer-alt"></i></div>
                                Thống kê
                            </a>
                            <a class="nav-link" href="ManagementAccount">
                                <div class="sb-nav-link-icon"><i class="fa-solid fa-gears"></i></div>
                                Quản lý tài khoản
                            </a>

                            <a class="nav-link" href="RevenueManagementServlet">
                                <div class="sb-nav-link-icon"><i class="fa-solid fa-chart-line"></i></i></div>
                                Quản lý doanh thu 
                            </a>
                            
                            <a class="nav-link" href="VATServlet">
                                <div class="sb-nav-link-icon"><i class="fa-solid fa-calculator"></i></i></div>
                                Quản lý thuế
                            </a>                           
                            <a class="nav-link" href="">
                                <div class="sb-nav-link-icon"><i class="fa-solid fa-book"></i></i></div>
                                Quản lý điều khoản 
                            </a>                           
                        </div>
                    </div>

                </nav>
            </div>
    </body>
</html>
