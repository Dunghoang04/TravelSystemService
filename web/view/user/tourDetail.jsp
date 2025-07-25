<%-- 
 * Copyright (C) 2025, Group 6.
 * ProjectCode/Short Name of Application: TravelAgentService 
 * Support Management and Provide Travel Service System 
 *
 * Record of change:
 * DATE        Version    AUTHOR            DESCRIPTION
 * 2025-06-07  1.0        Quynh Mai          First implementation
 * 2025-07-15  1.1        Grok               Added edit feedback modal
 * 2025-07-16  1.2        Grok               Added pagination for feedback section
 * 2025-07-16  1.3        Grok               Added success popup for feedback update
 * 2025-07-16  1.4        Grok               Enhanced success popup debug
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
            .star-rating .fas, .star-rating .far {
                font-size: 16px;
                margin-right: 2px;
            }
            .star-rating {
                font-size: 24px;
                color: #ddd;
                cursor: pointer;
            }
            .star-rating input {
                display: none;
            }
            .star-rating label {
                margin-right: 5px;
            }
            .star-rating label:before {
                content: "\f005";
                font-family: "Font Awesome 5 Free";
                font-weight: 900;
            }
            .star-rating input:checked ~ label:before,
            .star-rating label:hover:before,
            .star-rating label:hover ~ label:before {
                color: #f50057;
            }
            .preview-image {
                max-width: 200px;
                max-height: 200px;
                margin-top: 10px;
                display: none;
            }
            .modal-dialog {
                margin: 1.75rem auto;
            }
            .modal-content {
                width: 100%;
            }
            .modal-body form {
                width: 90%;
                margin: 0 auto;
            }
            .modal-body .mb-3 {
                width: 100%;
            }
            .pagination {
                display: flex;
                justify-content: center;
                gap: 5px;
                margin-top: 20px;
            }
            .pagination a {
                padding: 5px 10px;
                text-decoration: none;
                color: #007bff;
                border: 1px solid #ddd;
                border-radius: 3px;
            }
            .pagination a.active {
                background-color: #007bff;
                color: white;
            }
            .pagination a:hover {
                background-color: #f0f0f0;
            }
            #successModal .modal-body {
                text-align: center;
                color: #28a745;
            }
        </style>
    </head>
    <body>
        <%@include file="../layout/header.jsp"%>

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
                                       <c:forEach var="accomodation" items="${requestScope.accomodationList}">
                                            <li><a href="DetailAccommodation?id=${accomodation.serviceID}&tourId=${requestScope.tourId}">${accomodation.name}</a></li>
                                            </c:forEach>
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
                                <i class="fas fa-money-bill-wave"></i> <strong>Giá trẻ em:</strong> <fmt:formatNumber value="${tour.childrenPrice}" type="currency" currencySymbol="đ" maxFractionDigits="0"/><br>
                                <i class="fas fa-users"></i> <strong>Đánh giá:</strong> ${rateOfTour}<br>

                                <a href="${pageContext.request.contextPath}/booktourservlet?tourID=${tour.tourID}" class="btn book-btn text-white w-100" style="background-color: #86B817; margin-top: 20px; margin-bottom: 20px;padding-top: 10px; padding-bottom: 10px">Đặt ngay</a>
                            </div>
                        </div>
                    </div>
                </div>

                <!-- Feedback Section -->
                <div class="mt-5">
                    <h3 class="section-title1">Đánh Giá Của Người Dùng</h3>

                    <c:set var="page" value="${param.page != null ? param.page : 1}"/>
                    <c:set var="pageSize" value="5"/>
                    <c:set var="totalFeedbacks" value="${feedbacks != null ? feedbacks.size() : 0}"/>
                    <c:set var="totalPages" value="${(totalFeedbacks + pageSize - 1) / pageSize}"/>
                    <c:set var="start" value="${(page - 1) * pageSize}"/>
                    <c:set var="end" value="${start + pageSize - 1 < totalFeedbacks ? start + pageSize - 1 : totalFeedbacks - 1}"/>

                    <c:if test="${totalFeedbacks > 0}">
                        <c:forEach var="fb" items="${feedbacks}" begin="${start}" end="${end}">
                            <div class="card mb-3 shadow-sm p-3">
                                <div class="d-flex align-items-start">
                                    <!-- Avatar + Date -->
                                    <div class="me-3 text-center" style="width: 80px;">
                                        <div class="text-muted" style="font-size: 12px;">
                                            <fmt:formatDate value="${fb.createDate}" pattern="dd/MM/yyyy"/>
                                        </div>
                                    </div>
                                    <!-- Main content -->
                                    <div class="flex-grow-1">
                                        <div class="d-flex justify-content-between align-items-center mb-1">
                                            <div>
                                                <strong>${fb.userName}</strong>
                                            </div>
                                            <!-- Rating with Star Icons -->
                                            <div class="star-rating text-warning">
                                                <c:set var="rate" value="${fb.rate}"/>
                                                <c:set var="fullStars" value="${Math.floor(rate)}"/>
                                                <c:set var="hasHalfStar" value="${rate % 1 >= 0.5}"/>
                                                <c:forEach begin="1" end="5" var="i">
                                                    <c:choose>
                                                        <c:when test="${i <= fullStars}">
                                                            <i class="fas fa-star"></i>
                                                        </c:when>
                                                        <c:when test="${i == fullStars + 1 && hasHalfStar}">
                                                            <i class="fas fa-star-half-alt"></i>
                                                        </c:when>
                                                        <c:otherwise>
                                                            <i class="far fa-star"></i>
                                                        </c:otherwise>
                                                    </c:choose>
                                                </c:forEach>
                                            </div>
                                        </div>
                                        <c:if test="${not empty fb.image}">
                                            <img src="${pageContext.request.contextPath}${fb.image}" width="150px" height="auto" alt="Feedback Image"/>
                                        </c:if>
                                        <p class="mb-1">${fb.content}</p>

                                        <!-- Edit and Delete buttons -->
                                        <c:if test="${user.userID == fb.userID}">
                                            <a class="btn btn-sm btn-outline-primary mt-2" data-bs-toggle="modal" data-bs-target="#editFeedbackModal-${fb.feedbackID}">
                                                <i class="fas fa-edit"></i> Chỉnh sửa
                                            </a>
                                            <a href="DeleteFeedback?feedbackID=${fb.feedbackID}&tourId=${requestScope.tourId}" class="btn btn-sm btn-outline-danger mt-2" onclick="return confirm('Bạn có chắc chắn muốn xóa đánh giá này?');">
                                                <i class="fas fa-trash"></i> Xóa
                                            </a>
                                        </c:if>
                                    </div>
                                </div>
                            </div>
                            <!-- Edit Feedback Modal -->
                            <div class="modal fade" id="editFeedbackModal-${fb.feedbackID}" tabindex="-1" aria-labelledby="editFeedbackModalLabel-${fb.feedbackID}" aria-hidden="true">
                                <div class="modal-dialog" style="width: 1100px;">
                                    <div class="modal-content">
                                        <div class="modal-header">
                                            <h5 class="modal-title" id="editFeedbackModalLabel-${fb.feedbackID}">Chỉnh sửa phản hồi</h5>
                                            <button type="button" class="btn-close" data-bs-dismiss="modal" aria-label="Close"></button>
                                        </div>
                                        <div class="modal-body">
                                            <form action="EditFeedback" method="post" enctype="multipart/form-data">
                                                <input type="hidden" name="feedbackID" value="${fb.feedbackID}">
                                                <input type="hidden" name="tourId" value="${requestScope.tourId}">
                                                <div class="mb-3">
                                                    <label class="form-label">Đánh giá (1-5)</label>
                                                    <div class="star-rating">
                                                        <input type="radio" id="star5-${fb.feedbackID}" name="rate" value="5" ${fb.rate == 5 ? 'checked' : ''} required><label for="star5-${fb.feedbackID}"></label>
                                                        <input type="radio" id="star4-${fb.feedbackID}" name="rate" value="4" ${fb.rate == 4 ? 'checked' : ''}><label for="star4-${fb.feedbackID}"></label>
                                                        <input type="radio" id="star3-${fb.feedbackID}" name="rate" value="3" ${fb.rate == 3 ? 'checked' : ''}><label for="star3-${fb.feedbackID}"></label>
                                                        <input type="radio" id="star2-${fb.feedbackID}" name="rate" value="2" ${fb.rate == 2 ? 'checked' : ''}><label for="star2-${fb.feedbackID}"></label>
                                                        <input type="radio" id="star1-${fb.feedbackID}" name="rate" value="1" ${fb.rate == 1 ? 'checked' : ''}><label for="star1-${fb.feedbackID}"></label>
                                                    </div>
                                                </div>
                                                <div class="mb-3">
                                                    <label for="image-${fb.feedbackID}" class="form-label">Hình ảnh</label>
                                                    <input type="file" class="form-control" id="image-${fb.feedbackID}" name="image" accept="image/*" onchange="previewImage(event, 'preview-${fb.feedbackID}')">
                                                    <img id="preview-${fb.feedbackID}" class="preview-image" src="${pageContext.request.contextPath}${not empty fb.image ? fb.image : ''}" alt="Preview Image" style="${not empty fb.image ? 'display: block;' : 'display: none;'}">
                                                </div>
                                                <div class="mb-3">
                                                    <label for="content-${fb.feedbackID}" class="form-label">Nội dung</label>
                                                    <textarea class="form-control" id="content-${fb.feedbackID}" name="content" rows="3" required>${fb.content}</textarea>
                                                </div>
                                                <button type="submit" class="btn btn-primary">Cập nhật phản hồi</button>
                                            </form>
                                        </div>
                                    </div>
                                </div>
                            </div>
                        </c:forEach>

                        <!-- Pagination -->
                        <c:if test="${totalPages > 1}">
                            <div class="pagination">
                                <c:if test="${page > 1}">
                                    <a href="?tourId=${param.tourId}&page=${page - 1}">Trước</a>
                                </c:if>
                                <c:forEach var="i" begin="1" end="${totalPages}">
                                    <a href="?tourId=${param.tourId}&page=${i}" class="${page == i ? 'active' : ''}">${i}</a>
                                </c:forEach>
                                <c:if test="${page < totalPages}">
                                    <a href="?tourId=${param.tourId}&page=${page + 1}">Sau</a>
                                </c:if>
                            </div>
                        </c:if>
                    </c:if>
                    <c:if test="${totalFeedbacks == 0}">
                        <div class="alert alert-info mt-3">Chưa có đánh giá nào cho tour này.</div>
                    </c:if>
                </div>
            </c:if>
            <c:if test="${empty tour}">
                <div class="alert alert-warning text-center">
                    Không tìm thấy tour!
                </div>
            </c:if>

            <!-- Success Modal -->
            <div class="modal fade" id="successModal" tabindex="-1" aria-labelledby="successModalLabel" aria-hidden="true">
                <div class="modal-dialog modal-dialog-centered">
                    <div class="modal-content">
                        <div class="modal-header">
                            <h5 class="modal-title" id="successModalLabel">Thành công</h5>
                            <button type="button" class="btn-close" data-bs-dismiss="modal" aria-label="Close"></button>
                        </div>
                        <div class="modal-body">
                            <i class="fas fa-check-circle" style="font-size: 2rem;"></i>
                            <p>Cập nhật phản hồi thành công!</p>
                        </div>
                    </div>
                </div>
            </div>
        </div>
        <%@include file="../layout/footer.jsp"%>
        <script src="https://code.jquery.com/jquery-3.4.1.min.js"></script>
        <script src="https://cdn.jsdelivr.net/npm/bootstrap@5.0.0/dist/js/bootstrap.bundle.min.js"></script>
        <script src="assets/lib/wow/wow.min.js"></script>
        <script src="assets/lib/easing/easing.min.js"></script>
        <script src="assets/lib/waypoints/waypoints.min.js"></script>
        <script src="assets/lib/owlcarousel/owl.carousel.min.js"></script>
        <script src="assets/lib/tempusdominus/js/moment.min.js"></script>
        <script src="assets/lib/tempusdominus/js/moment-timezone.min.js"></script>
        <script src="assets/lib/tempusdominus/js/tempusdominus-bootstrap-4.min.js"></script>
        <script src="assets/js/main.js"></script>
        <script>
                                                        function previewImage(event, previewId) {
                                                            const file = event.target.files[0];
                                                            const preview = document.getElementById(previewId);
                                                            if (file) {
                                                                const reader = new FileReader();
                                                                reader.onload = function (e) {
                                                                    preview.src = e.target.result;
                                                                    preview.style.display = 'block';
                                                                };
                                                                reader.readAsDataURL(file);
                                                            } else {
                                                                preview.style.display = 'none';
                                                            }
                                                        }

                                                        // Check for success parameter and show modal with debug
                                                        window.onload = function () {
                                                            const urlParams = new URLSearchParams(window.location.search);
                                                            console.log("URL Params:", urlParams.toString()); // Debug log
                                                            if (urlParams.get('success') === 'true') {
                                                                console.log("Success parameter detected, showing modal"); // Debug log
                                                                const successModal = new bootstrap.Modal(document.getElementById('successModal'));
                                                                successModal.show();
                                                                // Auto hide after 3 seconds
                                                                setTimeout(() => {
                                                                    successModal.hide();
                                                                    // Remove success parameter from URL
                                                                    const newUrl = window.location.pathname + window.location.search.replace('?success=true', '').replace('&success=true', '');
                                                                    window.history.replaceState({}, document.title, newUrl);
                                                                    console.log("Modal hidden, new URL:", newUrl); // Debug log
                                                                }, 3000);
                                                            } else {
                                                                console.log("No success parameter found"); // Debug log
                                                            }
                                                        };
        </script>
    </body>
</html>