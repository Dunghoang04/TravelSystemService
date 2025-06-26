<<<<<<< OURS
<%-- 
 * Copyright (C) 2025, Group 6.
 * ProjectCode/Short Name of Application: TravelSystemService 
 * Support Management and Provide Travel Service System 
 *
 * Record of change:
 * DATE        Version    AUTHOR            DESCRIPTION
 * 2025-06-07  1.0        Group 6           First implementation
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html>
<html>
    <body>
        <nav class="sb-topnav navbar navbar-expand navbar-dark bg-dark">
            <!-- Navbar Brand-->
            <c:set var="homeLink" value="index.html"/>
            <c:if test="${not empty sessionScope.loginUser}">
                <c:choose>
                    <c:when test="${sessionScope.loginUser.roleID == 1}">
                        <c:set var="homeLink" value="StatisticalAdmin"/>
                    </c:when>
                    <c:when test="${sessionScope.loginUser.roleID == 2}">
                        <c:set var="homeLink" value=""/>
                    </c:when>
                    <c:when test="${sessionScope.loginUser.roleID == 4}">
                        <c:set var="homeLink" value=""/>
                    </c:when>
                    <c:otherwise>
                        <c:set var="homeLink" value="home"/>
                    </c:otherwise>
                </c:choose>
            </c:if>
            <a class="navbar-brand ps-3" href="${homeLink}">Trang chủ</a>
            <!-- Sidebar Toggle-->
            <button class="btn btn-link btn-sm order-1 order-lg-0 me-4 me-lg-0" id="sidebarToggle" href="#!"><i class="fas fa-bars"></i></button>
            <!-- Navbar-->
            <ul class="navbar-nav ms-auto ms-md-12 me-3 me-lg-4">
                <li class="nav-item dropdown">
                    <a class="nav-link dropdown-toggle" id="navbarDropdown" href="#" role="button" data-bs-toggle="dropdown" aria-expanded="false"><i class="fas fa-user fa-fw"></i></a>
                    <ul class="dropdown-menu dropdown-menu-end" aria-labelledby="navbarDropdown">
                        <c:set var="profileLink" value="#!"/>
                        <c:if test="${not empty sessionScope.loginUser}">
                            <c:choose>
                                <c:when test="${sessionScope.loginUser.roleID == 1}">
                                    <c:set var="profileLink" value="ManageAdminProfile?service=viewAdminProfile"/>
                                </c:when>
                                <c:when test="${sessionScope.loginUser.roleID == 2}">
                                    <c:set var="profileLink" value="ManageManagerProfile?service=viewProfile"/>
                                </c:when>                              
                                <c:when test="${sessionScope.loginUser.roleID == 4}">
                                    <c:set var="profileLink" value="ManageTravelAgentProfile?service=viewProfile"/>
                                </c:when>
                                <c:otherwise>
                                    <c:set var="profileLink" value="home"/>
                                </c:otherwise>
                            </c:choose>
                        </c:if>
                        <li><a class="dropdown-item" href="${profileLink}">Trang cá nhân</a></li>
                        <li><a class="dropdown-item" href="#!">Cài đặt</a></li>
                        <li><hr class="dropdown-divider" /></li>
                        <li><a class="dropdown-item" href="LoginLogout?service=logoutUser">Đăng xuất</a></li>
                    </ul>
                </li>
            </ul>
        </nav>
    </body>
</html>
=======
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
    
    <body>
        <nav class="sb-topnav navbar navbar-expand navbar-dark bg-dark">
            <!-- Navbar Brand-->
            <a class="navbar-brand ps-3" href="index.html">Trang chủ</a>
            <!-- Sidebar Toggle-->
            <button class="btn btn-link btn-sm order-1 order-lg-0 me-4 me-lg-0" id="sidebarToggle" href="#!"><i class="fas fa-bars"></i></button>
            <!-- Navbar Search-->
            
            <!-- Navbar-->
            <ul class="navbar-nav ms-auto ms-md-12 me-3 me-lg-4">
                <li class="nav-item dropdown">
                    <a class="nav-link dropdown-toggle" id="navbarDropdown" href="#" role="button" data-bs-toggle="dropdown" aria-expanded="false"><i class="fas fa-user fa-fw"></i></a>
                    <ul class="dropdown-menu dropdown-menu-end" aria-labelledby="navbarDropdown">
                        <li><a class="dropdown-item" href="ManageTravelAgentProfile?service=viewProfile">Trang cá nhân</a></li>
                        <li><a class="dropdown-item" href="#!">Cài đặt</a></li>
                        <li><hr class="dropdown-divider" /></li>
                        <li><a class="dropdown-item" href="LoginLogout?service=logoutUser">Đăng xuất</a></li>
                    </ul>
                </li>
            </ul>
        </nav>
    </body>
</html>
>>>>>>> THEIRS
