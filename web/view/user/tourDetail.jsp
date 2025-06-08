<%-- 
    Document   : tourDetail.jsp
    Created on : Jun 5, 2025, 9:48:31 AM
    Author     : Nhat Anh
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ page import="dao.TourDAO, dao.TourSessionDAO, dao.TourServiceDetailDAO, java.util.Vector, model.Tour" %>
<jsp:useBean id="tourDAO" class="dao.TourDAO" scope="page"/>
<jsp:useBean id="sessionDAO" class="dao.TourSessionDAO" scope="page"/>
<jsp:useBean id="serviceDetailDAO" class="dao.TourServiceDetailDAO" scope="page"/>
<!DOCTYPE html>
<html lang="vi">
    <head>
        <!-- Favicon -->
        <link href="img/favicon.ico" rel="icon">

        <!-- Google Web Fonts -->
        <link rel="preconnect" href="https://fonts.googleapis.com">
        <link rel="preconnect" href="https://fonts.gstatic.com" crossorigin>
        <link href="https://fonts.googleapis.com/css2?family=Heebo:wght@400;500;600&family=Nunito:wght@600;700;800&display=swap" rel="stylesheet">

        <!-- Icon Font Stylesheet -->
        <link href="https://cdnjs.cloudflare.com/ajax/libs/font-awesome/5.10.0/css/all.min.css" rel="stylesheet">
        <link href="https://cdn.jsdelivr.net/npm/bootstrap-icons@1.4.1/font/bootstrap-icons.css" rel="stylesheet">

        <!-- Libraries Stylesheet -->
        <link href="assets/lib/animate/animate.min.css" rel="stylesheet">
        <link href="assets/lib/owlcarousel/assets/owl.carousel.min.css" rel="stylesheet">
        <link href="assets/lib/tempusdominus/css/tempusdominus-bootstrap-4.min.css" rel="stylesheet" />

        <!-- Customized Bootstrap Stylesheet -->
        <link href="assets/css/bootstrap.min.css" rel="stylesheet">

        <!-- Template Stylesheet -->
        <link href="assets/css/style.css" rel="stylesheet">
        
        <style>
            .tour-image {
                object-fit: cover;
            }
            .section-title1 {
                border-bottom: 2px solid #e74c3c;
                padding-bottom: 10px;
            }
            .book-btn {
                background-color: #e74c3c;
                border: none;
            }
            .book-btn:hover {
                background-color: #c0392b;
            }
            
            body {
                padding-top: 80px; /* Khoảng cách để tránh header cố định */
            }
        </style>
    </head>
    <body>
        <%@include file="../layout/Header.jsp" %>
        <c:set var="tourId" value="${param.tourId}"/>
        <c:set var="tour" value="${tourDAO.searchTour(tourId)}"/>
        <c:set var="sessions" value="${sessionDAO.getAllTourSessions()}"/>
        <c:set var="services" value="${serviceDetailDAO.getAllTourServiceDetails()}"/>
        <c:set var="earliestSession" value="${null}"/>
        <c:forEach var="session" items="${sessions}">
            <c:if test="${session.tourId == tourId}">
                <c:if test="${earliestSession == null || session.startDay < earliestSession.startDay}">
                    <c:set var="earliestSession" value="${session}"/>
                </c:if>
            </c:if>
        </c:forEach>
        <%
            // Retrieve the tour object from pageContext
            Object tourObj = pageContext.getAttribute("tour");
            String scheduleFormatted = "";
            String includeFormatted = "";
            String nonIncludeFormatted = "";
            if (tourObj != null && tourObj instanceof model.Tour) {
                model.Tour tour = (model.Tour) tourObj;
                scheduleFormatted = (tour.getTourSchedule() != null) ? tour.getTourSchedule().replace("\n", "<br>") : "";
                includeFormatted = (tour.getTourInclude() != null) ? tour.getTourInclude().replace("\n", "<br>") : "";
                nonIncludeFormatted = (tour.getTourNonInclude() != null) ? tour.getTourNonInclude().replace("\n", "<br>") : "";         
            }
            pageContext.setAttribute("scheduleFormatted", scheduleFormatted);
            pageContext.setAttribute("includeFormatted", includeFormatted);
            pageContext.setAttribute("nonIncludeFormatted", nonIncludeFormatted);
        %>
        <div class="container my-5">
            <c:if test="${not empty tour}">
                <h1 class="text-center mb-4">${tour.tourName}</h1>
                <div class="row">
                    <div class="col-md-8">
                        <img style="height: 500px; width: 850px" src="${pageContext.request.contextPath}/assets/img/${not empty tour.image ? tour.image : 'https://via.placeholder.com/600x400'}" class="img-fluid tour-image mb-4" alt="${tour.tourName}">

                        <!-- Tour Introduction -->
                        <div class="mb-4">
                            <h3 class="section-title1">Giới Thiệu Tour</h3>
                            <p>${tour.tourIntroduce}</p>
                        </div>
                        
                        <!-- Services -->
                        <div class="mb-4">
                            <h3 class="section-title1">Dịch Vụ</h3>
                            <ul>
                                <c:forEach var="service" items="${services}">
                                    <c:if test="${service.tourID == tourId}">
                                        <li>${service.serviceName}</li>
                                        </c:if>
                                    </c:forEach>
                            </ul>
                        </div>

                        <!-- Tour Schedule -->
                        <div class="mb-4">
                            <h3 class="section-title1">Lịch Trình</h3>
                            <p>${scheduleFormatted}</p>
                        </div>

                        

                        <!-- Inclusions -->
                        <div class="mb-4">
                            <h3 class="section-title1">Bao Gồm</h3>
                            <p>${includeFormatted}</p>
                        </div>

                        <!-- Exclusions -->
                        <div class="mb-4">
                            <h3 class="section-title1">Không Bao Gồm</h3>
                            <p>${nonIncludeFormatted}</p>
                        </div>
                    </div>

                        <div class="col-md-4">
                        <div class="card shadow-sm">
                            <div class="card-body">
                                <h5 class="card-title">Thông Tin Đặt Tour</h5>
                                <p><strong>Khởi hành:</strong> <fmt:formatDate value="${earliestSession.startDay}" pattern="dd/MM/yyyy"/></p>
                                <p><strong>Thời gian:</strong> ${tour.numberOfDay}N${tour.numberOfDay - 1}Đ</p>
                                <p><strong>Khởi hành từ:</strong> ${tour.startPlace}</p>
                                <p><strong>Giá người lớn:</strong> <fmt:formatNumber value="${earliestSession.adultPrice}" type="currency" currencySymbol="đ" maxFractionDigits="0"/></p>
                                <p><strong>Giá trẻ em:</strong> <fmt:formatNumber value="${earliestSession.childrenPrice}" type="currency" currencySymbol="đ" maxFractionDigits="0"/></p>
                                <a href="#" class="btn book-btn text-white w-100" style="background-color:green;">Đặt ngay</a>
                            </div>
                        </div>
                    </div>
                </div>
            </c:if>
            <c:if test="${empty tour}">
                <div class="alert alert-warning text-center">
                    Không tìm thấy tour!
                </div>
            </c:if>
        </div>
        <%@include file="../layout/Footer.jsp" %>
        <script src="https://code.jquery.com/jquery-3.4.1.min.js"></script>
        <script src="https://cdn.jsdelivr.net/npm/bootstrap@5.0.0/dist/js/bootstrap.bundle.min.js"></script>
        <script src="assets/lib/wow/wow.min.js"></script>
        <script src="assets/lib/easing/easing.min.js"></script>
        <script src="assets/lib/waypoints/waypoints.min.js"></script>
        <script src="assets/lib/owlcarousel/owl.carousel.min.js"></script>
        <script src="assets/lib/tempusdominus/js/moment.min.js"></script>
        <script src="assets/lib/tempusdominus/js/moment-timezone.min.js"></script>
        <script src="assets/lib/tempusdominus/js/tempusdominus-bootstrap-4.min.js"></script>

        <!-- Template Javascript -->
        <script src="assets/js/main.js"></script>
    </body>
</html>