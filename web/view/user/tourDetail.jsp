<%-- 
 * Copyright (C) 2025, Group 6.
 * ProjectCode/Short Name of Application: TravelAgentService 
 * Support Management and Provide Travel Service System 
 *
 * Record of change:
 * DATE        Version    AUTHOR            DESCRIPTION
 * 2025-06-07  1.0        Quynh Mai          First implementation
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
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

            .tour-description {
                white-space: pre-line;
            }
        </style>
    </head>
    <body>
        <%@include file="../layout/header.jsp" %>      

        <div class="container my-5">
            <c:if test="${not empty tour}">
                <h1 class="text-center mb-4">${tour.tourName}</h1>
                <div class="row">
                    <div class="col-md-8">
                        <img style="height: 500px; width: 850px" src="${pageContext.request.contextPath}/${not empty tour.image ? tour.image : 'https://via.placeholder.com/600x400'}" class="img-fluid tour-image mb-4" alt="${tour.tourName}">

                        <!-- Tour Introduction -->
                        <div class="mb-4">
                            <h3 class="section-title1">Giới Thiệu Tour</h3>
                            <p class="tour-description">${tour.tourIntroduce}</p>
                        </div>

                        <!-- Services -->
                        <div class="mb-4">
                            <h3 class="section-title1">Dịch Vụ</h3>
                            <div class="row">
                                <div class="col-md-4">
                                    <h4>Nhà hàng</h4>
                                    <ul>
                                        <c:forEach var="restaurant" items="${requestScope.restaurantList}">
                                            <li><a href="detailrestaurant?id=${restaurant.serviceId}&tourId=${requestScope.tourId}">${restaurant.name}</a></li>
                                            </c:forEach>
                                    </ul>
                                </div>
                                <div class="col-md-4">
                                    <h4>Dịch vụ giải trí</h4>
                                    <ul>
                                        <c:forEach var="entertainment" items="${requestScope.entertainmentList}">
                                            <li><a href="detailentertainment?id=${entertainment.serviceId}&tourId=${requestScope.tourId}">${entertainment.name}</a></li>
                                            </c:forEach>
                                    </ul>
                                </div>
                                <div class="col-md-4">
                                    <h4>Khách sạn</h4>
                                    <ul>
                                        <!-- Thêm logic cho khách sạn nếu có -->
                                    </ul>
                                </div>
                            </div>
                           
                        </div>

                        <!-- Tour Schedule -->
                        <div class="mb-4">
                            <h3 class="section-title1">Lịch Trình</h3>
                            <p class="tour-description">${tour.tourSchedule}</p>
                        </div>



                        <!-- Inclusions -->
                        <div class="mb-4">
                            <h3 class="section-title1">Bao Gồm</h3>
                            <p class="tour-description">${tour.tourInclude}</p>
                        </div>

                        <!-- Exclusions -->
                        <div class="mb-4">
                            <h3 class="section-title1">Không Bao Gồm</h3>
                            <p class="tour-description">${tour.tourNonInclude}</p>
                        </div>
                    </div>

                    <div class="col-md-4">
                        <div class="card shadow-sm">
                            <div class="card-body">
                                <h5 class="card-title">Thông Tin Đặt Tour</h5>
                                <i class="fas fa-clock"></i> <strong> Khởi hành:</strong> <fmt:formatDate value="${tour.startDay}" pattern="dd/MM/yyyy"/><br>
                                <i class="fas fa-flag-checkered"></i> <strong>Kết thúc:</strong> <fmt:formatDate value="${tour.endDay}" pattern="dd/MM/yyyy"/><br>
                                <i class="fas fa-calendar-alt"></i> <strong>Thời gian:</strong> ${tour.numberOfDay}N${tour.numberOfDay - 1}Đ<br>
                                <i class="fas fa-map-marker-alt"></i><strong> Khởi hành từ:</strong> ${tour.startPlace}<br>
                                <i class="fas fa-map-pin"></i> <strong>Điểm đến:</strong> ${tour.endPlace}<br>                                
                                <i class="fas fa-users"></i> <strong>Số chỗ còn:</strong> ${tour.quantity}<br>
                                <i class="fas fa-money-bill-wave"></i> <strong>Giá người lớn:</strong> <fmt:formatNumber value="${tour.adultPrice}" type="currency" currencySymbol="đ" maxFractionDigits="0"/><br>
                                <i class="fas fa-money-bill-wave"></i> <strong>Giá trẻ em:</strong> <fmt:formatNumber value="${tour.childrenPrice}" type="currency" currencySymbol="đ" maxFractionDigits="0"/>
                                <a href="${pageContext.request.contextPath}/booktourservlet?tourID=${tour.tourID}" class="btn book-btn text-white w-100" style="background-color: #86B817; margin-top: 20px; margin-bottom: 20px;padding-top: 10px; padding-bottom: 10px">Đặt ngay</a>
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
        <%@include file="../layout/footer.jsp" %>
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