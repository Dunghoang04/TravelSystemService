<%-- 
    Document   : SideNavOptionAgent
    Created on : May 26, 2025, 10:47:46 PM
    Author     : ad
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
                            <a class="nav-link" href="saticalagent">
                                <div class="sb-nav-link-icon"><i class="fas fa-tachometer-alt"></i></div>
                                Thống kê
                            </a>
                            <a class="nav-link" href="tourmanagementagent">
                                <div class="sb-nav-link-icon"><i class="fa-solid fa-gears"></i></div>
                                Quản lí danh sách chuyến đi
                            </a>

                            <a class="nav-link" href="managementhotel">
                                <div class="sb-nav-link-icon"><i class="fa-solid fa-hotel"></i></i></div>
                                Quản lí khách sạn
                            </a>
                            <a class="nav-link" href="managevehicle">
                                <div class="sb-nav-link-icon"><i class="fa-solid fa-car"></i></i></div>
                                Quản lí phương tiện
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
