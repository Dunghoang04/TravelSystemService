<%-- 
 * Copyright (C) 2025, Group 6.
 * ProjectCode/Short Name of Application: TravelSystemService 
 * Support Management and Provide Travel Service System 
 *
 * Record of change:
 * DATE        Version    AUTHOR            DESCRIPTION
 * 2025-06-14  1.0        Quynh Mai          First implementation
 * 2025-06-14  1.1        [Your Name]        Added pagination support
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<!DOCTYPE html>
<html>
    <head>
        <meta charset="UTF-8">
        <meta name="viewport" content="width=device-width, initial-scale=1.0">
        <title>Danh Sách Tour Du Lịch</title>
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
            body {
                padding-top: 80px;
                background-color: #f8f9fa;
            }
            .tour-container {
                padding: 20px;
            }
            .tour-card {
                border: 1px solid #ddd;
                border-radius: 8px;
                overflow: hidden;
                background-color: #fff;
                margin-bottom: 20px;
                box-shadow: 0 2px 4px rgba(0, 0, 0, 0.1);
                display: flex;
                align-items: stretch;
                min-height: 300px;
            }
            .tour-image {
                width: 41.67%;
                height: 300px;
                object-fit: cover;
            }
            .tour-details {
                width: 58.33%;
                padding: 15px;
                display: flex;
                flex-direction: column;
                justify-content: space-between;
            }
            .tour-title {
                font-size: 21px;
                font-weight: 600;
                margin-bottom: 10px;
                color: #2c3e50;
            }
            .tour-info {
                font-size: 16px;
                color: #7f8c8d;
                margin-bottom: 5px;
            }
            .tour-price {
                font-size: 22px;
                font-weight: bold;
                color: #e74c3c;
                margin-top: 10px;
            }
            .tour-actions {
                margin-top: 15px;
                display: flex;
                justify-content: space-between;
                align-items: flex-end;
                padding-bottom: 10px;
            }
            .detail-btn {
                background-color: #86B817;
                color: #fff;
                border: none;
                padding: 10px 20px;
                border-radius: 5px;
                text-decoration: none;
                font-size: 16px;
                display: inline-block;
                transition: background-color 0.3s;
            }
            .detail-btn:hover {
                background-color: #6a9212;
                color: #fff;
            }
            .filter-section {
                margin-top: 40px;
                background-color: #fff;
                padding: 20px;
                border-radius: 8px;
                box-shadow: 0 2px 4px rgba(0, 0, 0, 0.1);
                position: sticky;
                top: 100px; /* Adjust this value based on your header height to avoid overlap */
                z-index: 100; /* Ensure it stays above other content */
                max-height: calc(100vh - 120px); /* Limit height to viewport minus some padding */

            }
            .filter-header {
                display: flex;
                justify-content: space-between;
                align-items: center;
                padding-bottom: 10px;
                border-bottom: 1px solid #ddd;
                margin-bottom: 15px;
            }
            .filter-header h5 {
                font-size: 18px;
                font-weight: 600;
                color: #2c3e50;
                margin: 0;
            }
            .close-filter {
                color: #007bff;
                text-decoration: none;
                font-size: 14px;
            }
            .btn-group {
                display: flex;
                gap: 10px;
                margin-bottom: 10px;
            }
            .btn-option {
                background-color: #fff;
                border: 1px solid #ddd;
                padding: 8px 15px;
                border-radius: 5px;
                cursor: pointer;
                display: flex;
                align-items: center;
                font-size: 14px;
                color: #333;
                width: 48%;
                transition: background-color 0.3s;
            }
            .btn-option input[type="radio"] {
                margin-right: 5px;
            }
            .btn-option.active {
                background-color: #007bff;
                color: #fff;
                border-color: #007bff;
            }
            .form-control {
                border-radius: 5px;
                padding: 8px;
                border: 1px solid #ddd;
                width: 100%;
            }
            .btn-apply {
                background-color: #007bff;
                color: #fff;
                border: none;
                padding: 10px;
                border-radius: 5px;
                width: 100%;
                font-size: 16px;
                margin-top: 10px;
            }
            /* Added Pagination Styles */
            .pagination {
                text-align: center;
                margin-top: 20px;
            }
            .pagination a, .pagination span {
                margin: 0 5px;
                padding: 8px 12px;
                text-decoration: none;
                border: 1px solid #ddd;
                border-radius: 5px;
                max-height: 40px;
                color: #007bff;
            }
            .pagination a:hover {
                background-color: #007bff;
                color: #fff;
            }
            .pagination span {
                background-color: #007bff;
                color: #fff;
                border-color: #007bff;
            }
        </style>
    </head>
    <body>
        <!-- Header -->
        <%@include file="../layout/header.jsp" %>

        <!-- Main Content -->
        <div class="container-fluid tour-container">
            <div class="row px-xl-5">
                <!-- Filter Sidebar -->
                <div class="col-md-3">
                    <div class="filter-section mb-4">
                        <div class="filter-header">
                            <h5>BỘ LỌC TÌM KIẾM</h5>
                            <a href="TourServlet" class="close-filter">Xóa</a>
                        </div>
                        <form id="searchForm" action="TourServlet" method="post">
                            <div class="mb-3">
                                <h6>Ngân sách</h6>
                                <div class="btn-group" role="group">
                                    <label class="btn-option ${budget == 'under5' ? 'active' : ''}">
                                        <input type="radio" name="budget" value="under5" ${budget == 'under5' ? 'checked' : ''}> Dưới 5 triệu
                                    </label>
                                    <label class="btn-option ${budget == '5-10' ? 'active' : ''}">
                                        <input type="radio" name="budget" value="5-10" ${budget == '5-10' ? 'checked' : ''}> Từ 5 - 10 triệu
                                    </label>
                                </div>
                                <div class="btn-group" role="group">
                                    <label class="btn-option ${budget == '10-20' ? 'active' : ''}">
                                        <input type="radio" name="budget" value="10-20" ${budget == '10-20' ? 'checked' : ''}> Từ 10 - 20 triệu
                                    </label>
                                    <label class="btn-option ${budget == 'over20' ? 'active' : ''}">
                                        <input type="radio" name="budget" value="over20" ${budget == 'over20' ? 'checked' : ''}> Trên 20 triệu
                                    </label>
                                </div>
                            </div>
                            <div class="mb-3">
                                <h6>Điểm khởi hành</h6>
                                <select class="form-control" name="departure">
                                    <option value="" ${empty departure ? 'selected' : ''}>Tất cả</option>
                                    <c:forEach var="startPlace" items="${startPlaces}">
                                        <option value="${startPlace}" ${departure == startPlace ? 'selected' : ''}>
                                            ${startPlace}
                                        </option>
                                    </c:forEach>
                                </select>
                            </div>
                            <div class="mb-3">
                                <h6>Điểm đến</h6>
                                <select class="form-control" name="destination">
                                    <option value="" ${empty param.destination ? 'selected' : ''}>Tất cả</option>
                                    <c:forEach var="endPlace" items="${requestScope.endPlaces}">
                                        <c:set var="isSelected" value="${not empty param.destination and endPlace.toLowerCase().contains(param.destination.toLowerCase()) ? 'selected' : ''}"/>
                                        <option value="${endPlace}" ${isSelected}>
                                            ${endPlace}
                                        </option>
                                    </c:forEach>
                                </select>
                            </div>
                            <div class="mb-3">
                                <h6>Ngày đi</h6>
                                <input type="date" class="form-control" name="departureDate" value="${not empty departureDate ? departureDate : defaultDepartureDate}" required>
                                <p style="color: red">${error}</p>
                            </div>
                            <div class="mb-3">
                                <h6>Loại tour</h6>
                                <select class="form-control" name="tourCategory">
                                    <option value="" ${empty tourCategory ? 'selected' : ''}>Tất cả</option>
                                    <c:forEach var="category" items="${categories}">
                                        <option value="${category.tourCategoryID}" ${tourCategory == category.tourCategoryID ? 'selected' : ''}>
                                            ${category.tourCategoryName}
                                        </option>
                                    </c:forEach>
                                </select>
                            </div>
                            <button type="submit" class="btn-apply">Áp dụng</button>
                        </form>

                    </div>
                </div>

                <div class="col-md-1"></div>
                <!-- Tour List -->
                <div class="col-md-8 row">
                    <div class="col-12 pb-1">
                        <div class="d-flex justify-content-between align-items-center mb-4">
                            <h2>Tất cả các Tour</h2>
                        </div>
                        <h5>Có ${totalTours} tour</h5>
                    </div>

                    <!-- Dynamic Tour List from Database -->
                    <c:forEach var="tour" items="${tours}">
                        <div class="col-12">
                            <div class="tour-card">
                                <img class="tour-image" src="${not empty tour.image ? tour.image : 'https://via.placeholder.com/300x200'}" alt="${tour.tourName}">
                                <div class="tour-details">
                                    <h5 class="tour-title">${tour.tourName}</h5>
                                    <p class="tour-info">
                                        <i class="fas fa-clock"></i> <strong> Khởi hành:</strong> <fmt:formatDate value="${tour.startDay}" pattern="dd/MM/yyyy"/><br>
                                        <i class="fas fa-calendar-alt"></i> <strong>Thời gian:</strong> ${tour.numberOfDay}N${tour.numberOfDay - 1}Đ<br>
                                        <i class="fas fa-map-marker-alt"></i> <strong> Khởi hành từ:</strong> ${tour.startPlace}<br>
                                        <i class="fas fa-users"></i> <strong>Số chỗ còn:</strong> ${tour.quantity}
                                    </p>
                                    <div class="tour-actions">
                                        <span class="tour-price">Giá từ: <fmt:formatNumber value="${tour.adultPrice}" type="currency" currencySymbol="đ" maxFractionDigits="0"/></span>
                                        <a href="TourDetailServlet?tourId=${tour.tourID}" class="detail-btn">Xem chi tiết</a>
                                    </div>
                                </div>
                            </div>
                        </div>
                    </c:forEach>

                    <!-- No Tours Found -->
                    <c:if test="${empty tours}">
                        <div class="col-12 text-center">
                            <p>Không có tour nào phù hợp với yêu cầu của bạn.</p>
                        </div>
                    </c:if>

                    <!-- Pagination -->
                    <div style="justify-content: right" class="col-12 pagination">
                        <c:if test="${totalPages > 1}">
                            <!-- Previous Link -->
                            <c:if test="${currentPage > 1}">
                                <a href="TourServlet?page=${currentPage - 1}&service=${param.service}&destination=${destination}&budget=${budget}&departure=${departure}&departureDate=${departureDate}&tourCategory=${tourCategory}">Trang trước</a>
                            </c:if>
                            <!-- Page Numbers -->
                            <c:forEach begin="1" end="${totalPages}" var="i">
                                <c:choose>
                                    <c:when test="${i == currentPage}">
                                        <span>${i}</span>
                                    </c:when>
                                    <c:otherwise>
                                        <a href="TourServlet?page=${i}&service=${param.service}&destination=${destination}&budget=${budget}&departure=${departure}&departureDate=${departureDate}&tourCategory=${tourCategory}">${i}</a>
                                    </c:otherwise>
                                </c:choose>
                            </c:forEach>
                            <!-- Next Link -->
                            <c:if test="${currentPage < totalPages}">
                                <a href="TourServlet?page=${currentPage + 1}&service=${param.service}&destination=${destination}&budget=${budget}&departure=${departure}&departureDate=${departureDate}&tourCategory=${tourCategory}">Trang sau</a>
                            </c:if>
                        </c:if>
                    </div>
                </div>
            </div>
        </div>

        <!-- Footer -->
        <%@include file="../layout/footer.jsp" %>

        <!-- JavaScript Libraries -->
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
        <script>
            // JavaScript to handle budget button styling with proper toggle
            let lastSelectedBudget = null;
            document.querySelectorAll('.btn-option input[type="radio"]').forEach(radio => {
                radio.addEventListener('change', function () {
                    const groupName = this.name;
                    // Reset all budget buttons to white and remove active class
                    document.querySelectorAll(`.btn-option input[name="${groupName}"]`).forEach(input => {
                        const label = input.parentElement;
                        label.classList.remove('active');
                        label.style.backgroundColor = '#fff'; // Reset to white
                    });
                    // Apply blue background only to the newly selected button
                    if (this.checked) {
                        this.parentElement.classList.add('active');
                        this.parentElement.style.backgroundColor = '#007bff';
                        if (lastSelectedBudget) {
                            lastSelectedBudget.classList.remove('active');
                            lastSelectedBudget.style.backgroundColor = '#fff'; // Explicitly reset previous
                        }
                        lastSelectedBudget = this.parentElement;
                    }
                });
                // Initialize based on current selection
                if (radio.checked) {
                    radio.parentElement.classList.add('active');
                    radio.parentElement.style.backgroundColor = '#007bff';
                    lastSelectedBudget = radio.parentElement;
                }
            });
        </script>
    </body>
</html>