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
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<!DOCTYPE html>
<html>
    <head>
        <meta charset="utf-8">
        <title>Tourist - Travel Agency HTML Template</title>
        <meta content="width=device-width, initial-scale=1.0" name="viewport">
        <meta content="" name="keywords">
        <meta content="" name="description">

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

        <!-- Chatbot Styles -->
        <style>
            .tour-name {
                font-size: 1.25rem;
                font-weight: 600;
                color: #333;
                margin-bottom: 1rem;
                display: -webkit-box;
                -webkit-line-clamp: 2;
                -webkit-box-orient: vertical;
                overflow: hidden;
                text-overflow: ellipsis;
            }
            .price {
                font-weight: bold;
                color: #e74c3c;
            }
            .book-btn {
                padding: 10px 30px;
                background-color: #e74c3c;
                border: none;
            }
            .book-btn:hover {
                background-color: #c0392b;
            }
            .user-message {
                background-color: #e3f2fd;
                margin-left: 20%;
                text-align: right;
                padding: 10px;
                border-radius: 10px;
                margin-bottom: 10px;
            }
            .bot-message {
                background-color: #f1f1f1;
                margin-right: 20%;
                padding: 10px;
                border-radius: 10px;
                margin-bottom: 10px;
            }
            .staff-message {
                background-color: #d4edda;
                margin-right: 20%;
                padding: 10px;
                border-radius: 10px;
                margin-bottom: 10px;
            }
            .chatbot-icon {
                position: fixed;
                bottom: 20px;
                right: 20px;
                width: 60px;
                height: 60px;
                background-color: #007bff;
                border-radius: 50%;
                display: flex;
                align-items: center;
                justify-content: center;
                color: #fff;
                font-size: 24px;
                cursor: pointer;
                box-shadow: 0 0 10px rgba(0, 0, 0, 0.2);
                z-index: 1000;
            }
            .chatbot-container {
                position: fixed;
                bottom: 90px;
                right: 20px;
                width: 350px;
                height: 500px;
                background-color: #fff;
                border-radius: 10px;
                box-shadow: 0 0 10px rgba(0, 0, 0, 0.2);
                display: none;
                flex-direction: column;
                z-index: 1000;
            }
            .chatbot-header {
                background-color: #007bff;
                color: #fff;
                padding: 10px;
                border-top-left-radius: 10px;
                border-top-right-radius: 10px;
                text-align: center;
                font-weight: bold;
            }
            .chatbot-body {
                flex-grow: 1;
                overflow-y: auto;
                padding: 10px;
            }
            .chatbot-input {
                display: flex;
                padding: 10px;
                border-top: 1px solid #ddd;
            }
            .chatbot-input input {
                flex-grow: 1;
                padding: 10px;
                border: 1px solid #ddd;
                border-radius: 5px;
                margin-right: 10px;
            }
            .chatbot-input button {
                padding: 10px 20px;
                background-color: #007bff;
                color: #fff;
                border: none;
                border-radius: 5px;
                cursor: pointer;
            }
            .chatbot-input button:hover {
                background-color: #0056b3;
            }
        </style>
    </head>
    <body>
        
        <%@include file="../layout/header.jsp" %>
        <!-- Navbar & Hero Start -->
        <div class="container-fluid position-relative p-0">
            <div class="container-fluid bg-primary py-5 mb-5 hero-header">
                <div class="container py-5">
                    <div class="row justify-content-center py-5">
                        <div class="col-lg-10 pt-lg-5 mt-lg-5 text-center">
                            <h1 class="display-3 text-white mb-3 animated slideInDown">Tận hưởng chuyến đi với chúng tôi</h1>
                            <p class="fs-4 text-white mb-4 animated slideInDown">Cuộc sống như chiếc bánh xe, bạn muốn cân bằng thì phải luôn di chuyển.</p>
                            <div class="position-relative w-75 mx-auto animated slideInDown">
                                <form action="TourServlet" method="post">
                                    <input class="form-control border-0 rounded-pill w-100 py-3 ps-4 pe-5" type="text" name="destination" placeholder="Nhập tỉnh/thành phố muốn đến (VD: Lào Cai, Hà Nội)" value="${param.destination}">
                                    <button class="btn btn-primary rounded-pill py-2 px-4 position-absolute top-0 end-0 me-2" style="margin-top: 7px;" type="submit">Tìm kiếm</button>
                                </form>                        
                            </div>
                        </div>
                    </div>
                </div>
            </div>
        </div>
        <!-- Navbar & Hero End -->
        <!-- About Start -->
        <div class="container-xxl py-5">
            <div class="container">
                <div class="row g-5">
                    <div class="col-lg-6 wow fadeInUp" data-wow-delay="0.1s" style="min-height: 400px;">
                        <div class="position-relative h-100">
                            <img class="img-fluid position-absolute w-100 h-100" src="${pageContext.request.contextPath}/assets/img/15.jpg" style="object-fit: cover;">
                        </div>
                    </div>
                    <div class="col-lg-6 wow fadeInUp" data-wow-delay="0.3s">
                        <h6 class="section-title bg-white text-start text-primary pe-3">Chúng tôi</h6>
                        <h1 class="mb-4">Chào mừng <span class="text-primary">GoViet</span></h1>
                        <p class="mb-4">Cùng Du lịch Việt khám phá vẻ đẹp đất nước hình chữ S qua hàng trăm tour độc đáo từ các công ty lữ hành uy tín trên toàn quốc. Dù bạn muốn nghỉ dưỡng, khám phá thiên nhiên hay trải nghiệm văn hóa – chúng tôi luôn có tour phù hợp cho bạn.</p>
                        <p class="mb-4">Đặt tour nhanh chóng, so sánh dễ dàng, hỗ trợ tận tâm – đó là cam kết của chúng tôi dành cho bạn!</p>
                        <div class="row gy-2 gx-4 mb-4">
                            <div class="col-sm-6">
                                <p class="mb-0"><i class="fa fa-arrow-right text-primary me-2"></i>Hơn 50 đại lý liên kết</p>
                            </div>
                            <div class="col-sm-6">
                                <p class="mb-0"><i class="fa fa-arrow-right text-primary me-2"></i>Khách sạn chuẩn 5 sao</p>
                            </div>
                            <div class="col-sm-6">
                                <p class="mb-0"><i class="fa fa-arrow-right text-primary me-2"></i>Hơn 100 điểm đến hấp dẫn</p>
                            </div>
                            <div class="col-sm-6">
                                <p class="mb-0"><i class="fa fa-arrow-right text-primary me-2"></i>Sàn đặt lịch tiện lợi</p>
                            </div>
                            <div class="col-sm-6">
                                <p class="mb-0"><i class="fa new fa-arrow-right text-primary me-2"></i>Phương tiện hiện đại</p>
                            </div>
                            <div class="col-sm-6">
                                <p class="mb-0"><i class="fa fa-arrow-right text-primary me-2"></i>Dịch vụ 24/7</p>
                            </div>
                        </div>
                        <a class="btn btn-primary py-3 px-5 mt-2" href="">Xem thêm</a>
                    </div>
                </div>
            </div>
        </div>
        <!-- About End -->
        <!-- Service Start -->
        <div class="container-xxl py-5">
            <div class="container">
                <div class="text-center wow fadeInUp" data-wow-delay="0.1s">
                    <h6 class="section-title bg-white text-center text-primary px-3">Dịch vụ</h6>
                    <h1 class="mb-5">Dịch vụ chúng tôi</h1>
                </div>
                <div class="row g-4">
                    <div class="col-lg-3 col-sm-6 wow fadeInUp" data-wow-delay="0.1s">
                        <div class="service-item rounded pt-3">
                            <div class="p-4">
                                <i class="fa fa-3x fa-globe text-primary mb-4"></i>
                                <h5>WorldWide Tours</h5>
                                <p>Diam elitr kasd sed at elitr sed ipsum justo dolor sed clita amet diam</p>
                            </div>
                        </div>
                    </div>
                    <div class="col-lg-3 col-sm-6 wow fadeInUp" data-wow-delay="0.3s">
                        <div class="service-item rounded pt-3">
                            <div class="p-4">
                                <i class="fa fa-3x fa-hotel text-primary mb-4"></i>
                                <h5>Hotel Reservation</h5>
                                <p>Diam elitr kasd sed at elitr sed ipsum justo dolor sed clita amet diam</p>
                            </div>
                        </div>
                    </div>
                    <div class="col-lg-3 col-sm-6 wow fadeInUp" data-wow-delay="0.5s">
                        <div class="service-item rounded pt-3">
                            <div class="p-4">
                                <i class="fa fa-3x fa-user text-primary mb-4"></i>
                                <h5>Travel Guides</h5>
                                <p>Diam elitr kasd sed at elitr sed ipsum justo dolor sed clita amet diam</p>
                            </div>
                        </div>
                    </div>
                    <div class="col-lg-3 col-sm-6 wow fadeInUp" data-wow-delay="0.7s">
                        <div class="service-item rounded pt-3">
                            <div class="p-4">
                                <i class="fa fa-3x fa-cog text-primary mb-4"></i>
                                <h5>Event Management</h5>
                                <p>Diam elitr kasd sed at elitr sed ipsum justo dolor sed clita amet diam</p>
                            </div>
                        </div>
                    </div>
                    <div class="col-lg-3 col-sm-6 wow fadeInUp" data-wow-delay="0.1s">
                        <div class="service-item rounded pt-3">
                            <div class="p-4">
                                <i class="fa fa-3x fa-globe text-primary mb-4"></i>
                                <h5>WorldWide Tours</h5>
                                <p>Diam elitr kasd sed at elitr sed ipsum justo dolor sed clita amet diam</p>
                            </div>
                        </div>
                    </div>
                    <div class="col-lg-3 col-sm-6 wow fadeInUp" data-wow-delay="0.3s">
                        <div class="service-item rounded pt-3">
                            <div class="p-4">
                                <i class="fa fa-3x fa-hotel text-primary mb-4"></i>
                                <h5>Hotel Reservation</h5>
                                <p>Diam elitr kasd sed at elitr sed ipsum justo dolor sed clita amet diam</p>
                            </div>
                        </div>
                    </div>
                    <div class="col-lg-3 col-sm-6 wow fadeInUp" data-wow-delay="0.5s">
                        <div class="service-item rounded pt-3">
                            <div class="p-4">
                                <i class="fa fa-3x fa-user text-primary mb-4"></i>
                                <h5>Travel Guides</h5>
                                <p>Diam elitr kasd sed at elitr sed ipsum justo dolor sed clita amet diam</p>
                            </div>
                        </div>
                    </div>
                    <div class="col-lg-3 col-sm-6 wow fadeInUp" data-wow-delay="0.7s">
                        <div class="service-item rounded pt-3">
                            <div class="p-4">
                                <i class="fa fa-3x fa-cog text-primary mb-4"></i>
                                <h5>Event Management</h5>
                                <p>Diam elitr kasd sed at elitr sed ipsum justo dolor sed clita amet diam</p>
                            </div>
                        </div>
                    </div>
                </div>
            </div>
        </div>
        <!-- Service End -->
        <!-- Tour Start -->
        <div class="container-xxl py-5">
            <div class="container">
                <div class="text-center wow fadeInUp" data-wow-delay="0.1s">
                    <h6 class="section-title bg-white text-center text-primary px-3">Chuyến đi</h6>
                    <h1 class="mb-5">Gợi ý cho bạn</h1>
                </div>
                <div class="row">
                    <c:forEach var="tour" items="${topTour}">
                        <div class="col-md-4 mb-4">
                            <div class="card tour-card shadow-sm h-100">
                                <img style="height: 300px" src="${pageContext.request.contextPath}/${not empty tour.image ? tour.image : 'https://via.placeholder.com/300x200'}" class="card-img-top" alt="${tour.tourName}">
                                <div class="card-body">
                                    <h5 class="card-title tour-name">${tour.tourName}</h5>
                                    <p class="card-text">
                                        <i class="fas fa-clock"></i> <strong> Khởi hành:</strong> <fmt:formatDate value="${tour.startDay}" pattern="dd/MM/yyyy"/><br>
                                        <i class="fas fa-calendar-alt"></i> <strong>Thời gian:</strong> ${tour.numberOfDay}N${tour.numberOfDay - 1}Đ<br>
                                        <i class="fas fa-map-marker-alt"></i><strong> Khởi hành từ:</strong> ${tour.startPlace}<br>
                                        <i class="fas fa-users"></i> <strong>Số chỗ còn:</strong> ${tour.quantity}
                                    </p>
                                    <div class="d-flex justify-content-between align-items-center">
                                        <span class="price">Giá từ: <fmt:formatNumber value="${tour.adultPrice}" type="currency" currencySymbol="đ" maxFractionDigits="0"/></span>
                                        <a href="TourDetailServlet?tourId=${tour.tourID}" style="background-color: #86B817" class="btn book-btn text-white">Đặt ngay</a>
                                    </div>
                                </div>
                            </div>
                        </div>
                    </c:forEach>
                </div>
            </div>
        </div>
        <!-- Tour End -->
        <!-- Process Start -->
        <div class="container-xxl py-5">
            <div class="container">
                <div class="text-center pb-4 wow fadeInUp" data-wow-delay="0.1s">
                    <h6 class="section-title bg-white text-center text-primary px-3">Tiến trình</h6>
                    <h1 class="mb-5">3 bước dễ dàng</h1>
                </div>
                <div class="row gy-5 gx-4 justify-content-center">
                    <div class="col-lg-4 col-sm-6 text-center pt-4 wow fadeInUp" data-wow-delay="0.1s">
                        <div class="position-relative border border-primary pt-5 pb-4 px-4">
                            <div class="d-inline-flex align-items-center justify-content-center bg-primary rounded-circle position-absolute top-0 start-50 translate-middle shadow" style="width: 100px; height: 100px;">
                                <i class="fa fa-globe fa-3x text-white"></i>
                            </div>
                            <h5 class="mt-4">Chọn địa điểm</h5>
                            <hr class="w-25 mx-auto bg-primary mb-1">
                            <hr class="w-50 mx-auto bg-primary mt-0">
                            <p class="mb-0">Khám phá hàng trăm tour du lịch từ các công ty lữ hành uy tín trên khắp cả nước. Dễ dàng so sánh giá cả, lịch trình, và đánh giá thực tế để chọn điểm đến phù hợp với nhu cầu và sở thích của bạn.</p>
                        </div>
                    </div>
                    <div class="col-lg-4 col-sm-6 text-center pt-4 wow fadeInUp" data-wow-delay="0.3s">
                        <div class="position-relative border border-primary pt-5 pb-4 px-4">
                            <div class="d-inline-flex align-items-center justify-content-center bg-primary rounded-circle position-absolute top-0 start-50 translate-middle shadow" style="width: 100px; height: 100px;">
                                <i class="fa fa-dollar-sign fa-3x text-white"></i>
                            </div>
                            <h5 class="mt-4">Thanh toán trực tuyến</h5>
                            <hr class="w-25 mx-auto bg-primary mb-1">
                            <hr class="w-50 mx-auto bg-primary mt-0">
                            <p class="mb-0">Thực hiện thanh toán chỉ trong vài bước với các cổng thanh toán phổ biến như MoMo, ZaloPay, thẻ ngân hàng nội địa và quốc tế. Bảo mật thông tin tuyệt đối và xác nhận đơn hàng tức thì.</p>
                        </div>
                    </div>
                    <div class="col-lg-4 col-sm-6 text-center pt-4 wow fadeInUp" data-wow-delay="0.5s">
                        <div class="position-relative border border-primary pt-5 pb-4 px-4">
                            <div class="d-inline-flex align-items-center justify-content-center bg-primary rounded-circle position-absolute top-0 start-50 translate-middle shadow" style="width: 100px; height: 100px;">
                                <i class="fa fa-plane fa-3x text-white"></i>
                            </div>
                            <h5 class="mt-4">Xuất phát</h5>
                            <hr class="w-25 mx-auto bg-primary mb-1">
                            <hr class="w-50 mx-auto bg-primary mt-0">
                            <p class="mb-0">Bạn chỉ cần chuẩn bị hành lý, còn lại để chúng tôi và các đối tác lo! Nhận đầy đủ thông tin tour, hướng dẫn viên và các lưu ý cần thiết trước ngày khởi hành để có chuyến đi trọn vẹn và an tâm</p>
                        </div>
                    </div>
                </div>
            </div>
        </div>
        <!-- Process End -->
        <!-- Testimonial Start -->
        <div class="container-xxl py-5 wow fadeInUp" data-wow-delay="0.1s">
            <div class="container">
                <div class="text-center">
                    <h6 class="section-title bg-white text-center text-primary px-3">Đánh giá</h6>
                    <h1 class="mb-5">Đánh giá nổi bật!!!</h1>
                </div>
                <div class="owl-carousel testimonial-carousel position-relative">
                    <div class="testimonial-item bg-white text-center border p-4">
                        <img class="bg-white rounded-circle shadow p-1 mx-auto mb-3" src="${pageContext.request.contextPath}/assets/img/testimonial/testimonial1.png" style="width: 80px; height: 80px;">
                        <h5 class="mb-0">Nguyễn Xuân</h5>
                        <p>Thạch Thất, Hà Nội</p>
                        <p class="mb-0">Giao diện dễ dùng, tìm tour cực nhanh! Mình đặt tour Phú Quốc của một công ty địa phương mà trước giờ chưa biết tới. Nhờ sàn này mà tiết kiệm được cả thời gian lẫn chi phí.</p>
                    </div>
                    <div class="testimonial-item bg-white text-center border p-4">
                        <img class="bg-white rounded-circle shadow p-1 mx-auto mb-3" src="${pageContext.request.contextPath}/assets/img/testimonial/testimonial4.png" style="width: 80px; height: 80px;">
                        <h5 class="mb-0">Hà Quang</h5>
                        <p>Từ Sơn, Bắc Ninh</p>
                        <p class="mt-2 mb-0">Trang web rất tiện lợi, có thể so sánh tour giữa các công ty khác nhau. Mình thích nhất là phần đánh giá từ người dùng thật – giúp mình yên tâm hơn khi đặt tour.</p>
                    </div>
                    <div class="testimonial-item bg-white text-center border p-4">
                        <img class="bg-white rounded-circle shadow p-1 mx-auto mb-3" src="${pageContext.request.contextPath}/assets/img/testimonial/testimonial2.png" style="width: 80px; height: 80px;">
                        <h5 class="mb-0">Nguyễn Thu Hà</h5>
                        <p>Nam Định</p>
                        <p class="mt-2 mb-0">Mình lần đầu đặt tour du lịch qua một nền tảng như thế này, và thực sự rất hài lòng. Hỗ trợ nhiệt tình, tour rõ ràng, và giá cả hợp lý. Sẽ tiếp tục sử dụng trong tương lai!</p>
                    </div>
                    <div class="testimonial-item bg-white text-center border p-4">
                        <img class="bg-white rounded-circle shadow p-1 mx-auto mb-3" src="${pageContext.request.contextPath}/assets/img/testimonial/testimonial3.png" style="width: 80px; height: 80px;">
                        <h5 class="mb-0">Thu Hương</h5>
                        <p>Thái Bình</p>
                        <p class="mt-2 mb-0">Trang web tour này giúp mình khám phá ra nhiều tour lạ và độc đáo mà các trang lớn không có. Rất phù hợp cho những người muốn trải nghiệm mới mẻ!</p>
                    </div>
                </div>
            </div>
        </div>
        <!-- Testimonial End -->
        <!-- Chatbot Icon and Container -->
        <div class="chatbot-icon" onclick="toggleChatbox()">
            <i class="fas fa-comments"></i>
        </div>
        <div class="chatbot-container" id="chatbot-container">
            <div class="chatbot-header">Tư vấn du Lịch</div>
            <div class="chatbot-body" id="chatbot-body">
                <div class="bot-message">Chào bạn! tôi có thể giúp gì cho bạn!</div>
            </div>
            <div class="chatbot-input">
                <input type="text" id="user-input" placeholder="Nhập câu hỏi của bạn...">
                <button onclick="sendMessage()">Gửi</button>
            </div>
        </div>
        <!-- Footer Start -->
        <%@include file="../layout/footer.jsp" %>
        <!-- Footer End -->
        <!-- Back to Top -->
        <a href="#" class="btn btn-lg btn-primary btn-lg-square back-to-top"><i class="bi bi-arrow-up"></i></a>
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
        <!-- Chatbot Javascript -->
        <script>
            // Tải tin nhắn từ server khi trang được tải
            document.addEventListener('DOMContentLoaded', function () {
                loadMessages();
                // Cập nhật tin nhắn định kỳ (mỗi 2 giây)
                setInterval(loadMessages, 2000);
            });

            // Danh sách câu trả lời mặc định
            const defaultResponses = {
                'giá tour': 'Giá tour phụ thuộc vào điểm đến, thời gian và loại tour. Bạn có thể xem chi tiết giá trên trang danh sách tour hoặc nhập địa điểm cụ thể để tôi hỗ trợ!',
                'đặt tour': 'Để đặt tour, bạn chọn tour mong muốn, nhấn nút "Đặt ngay" và làm theo hướng dẫn thanh toán. Nếu cần hỗ trợ thêm, hãy cung cấp chi tiết tour bạn quan tâm!',
                'hủy tour': 'Việc hủy tour cần liên hệ trực tiếp với đại lý cung cấp tour. Bạn có thể tìm thông tin liên hệ trên trang chi tiết tour hoặc yêu cầu tôi chuyển câu hỏi này cho nhân viên hỗ trợ.',
                'khách sạn': 'Chúng tôi cung cấp các tour với khách sạn từ 3 đến 5 sao. Bạn muốn tìm tour có khách sạn ở khu vực nào? Vui lòng cung cấp thêm thông tin!',
                'liên hệ': 'Bạn có thể liên hệ qua hotline 1900 1234 hoặc email support@goviet.com. Nếu cần hỗ trợ ngay, tôi sẽ chuyển câu hỏi của bạn cho nhân viên!'
            };

            // Hàm gửi tin nhắn
            function sendMessage() {
                const userInput = document.getElementById('user-input').value.trim();
                if (!userInput) return;

                // Thêm tin nhắn của người dùng
                appendMessage(userInput, 'user-message');

                // Gửi tin nhắn người dùng đến server
                $.post('ChatbotServlet', {
                    action: 'sendMessage',
                    sender: 'user',
                    text: userInput
                }).done(function () {
                    // Sau khi gửi thành công, gửi phản hồi của bot
                    const botResponse = getBotResponse(userInput);
                    appendMessage(botResponse, 'bot-message');
                    $.post('ChatbotServlet', {
                        action: 'sendMessage',
                        sender: 'bot',
                        text: botResponse
                    });
                }).fail(function (xhr, status, error) {
                    console.error('Lỗi khi gửi tin nhắn:', error);
                });

                // Xóa input
                document.getElementById('user-input').value = '';
            }

            // Hàm hiển thị tin nhắn
            function appendMessage(text, className) {
                const chatBody = document.getElementById('chatbot-body');
                const messageDiv = document.createElement('div');
                messageDiv.className = className;
                messageDiv.textContent = text;
                chatBody.appendChild(messageDiv);
                chatBody.scrollTop = chatBody.scrollHeight;
            }

            // Hàm tải tin nhắn từ server
            function loadMessages() {
                $.get('ChatbotServlet', function (data) {
                    const chatBody = document.getElementById('chatbot-body');
                    // Giữ tin nhắn ban đầu nếu danh sách từ server rỗng
                    if (data.length === 0) {
                        const initialMessage = chatBody.querySelector('.bot-message');
                        if (!initialMessage) {
                            appendMessage('Chào bạn! Tôi là chatbot du lịch. Hỏi tôi bất kỳ điều gì về tour du lịch nhé!', 'bot-message');
                        }
                    } else {
                        // Xóa nội dung cũ và thêm tin nhắn từ server
                        chatBody.innerHTML = '';
                        data.forEach(message => {
                            appendMessage(message.text, message.sender === 'user' ? 'user-message' : message.sender === 'bot' ? 'bot-message' : 'staff-message');
                        });
                    }
                }).fail(function (xhr, status, error) {
                    console.error('Lỗi khi tải tin nhắn:', error);
                });
            }

            // Hàm lấy phản hồi của bot
            function getBotResponse(userInput) {
                const input = userInput.toLowerCase();
                for (const key in defaultResponses) {
                    if (input.includes(key)) {
                        return defaultResponses[key];
                    }
                }
                return 'Cảm ơn câu hỏi của bạn! Câu hỏi này hơi ngoài khả năng của tôi. Tôi sẽ chuyển nó cho nhân viên hỗ trợ, bạn vui lòng chờ phản hồi nhé!';
            }

            // Gửi tin nhắn khi nhấn Enter
            document.getElementById('user-input').addEventListener('keypress', function (e) {
                if (e.key === 'Enter') {
                    sendMessage();
                }
            });

            // Hàm toggle chatbox
            function toggleChatbox() {
                const chatContainer = document.getElementById('chatbot-container');
                if (chatContainer.style.display === 'none' || chatContainer.style.display === '') {
                    chatContainer.style.display = 'flex';
                    loadMessages(); // Tải lại tin nhắn khi mở chatbox
                } else {
                    chatContainer.style.display = 'none';
                }
            }
        </script>
    </body>
</html>