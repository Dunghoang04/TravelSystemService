<%-- 
    /*
 * Copyright (C) 2025, Group 6.
 * ProjectCode/Short Name of Application: TravelSystemService 
 * Support Management and Provide Travel Service System 
 *
 * Record of change:
 * DATE        Version    AUTHOR            DESCRIPTION
 * 2025-06-07  1.0        Group 6          First implementation
 */

--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
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
    </head>
    <style>
        .tour-name {
            font-size: 1.25rem;
            font-weight: 600;
            color: #333;
            margin-bottom: 1rem;
            display: -webkit-box;
            -webkit-line-clamp: 2; /* Giới hạn 2 dòng */
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
    </style>
    <body>

        <%@include file="../layout/header.jsp" %>


        <!-- Navbar & Hero Start -->
        <div class="container-fluid position-relative p-0">
            <div class="container-fluid bg-primary py-5 mb-5 hero-header">
                <div class="container py-5">
                    <div class="row justify-content-center py-5">
                        <div class="col-lg-10 pt-lg-5 mt-lg-5 text-center">
                            <h1 class="display-3 text-white mb-3 animated slideInDown">Tận hưởng chuyến đi với chúng tôi</h1>
                            <p class="fs-4 text-white mb-4 animated slideInDown">Cuộc sống như chiếc bánh xe, ban muốn cân bằng thi phải luôn di chuyển.</p>
                            <div class="position-relative w-75 mx-auto animated slideInDown">
                                <input class="form-control border-0 rounded-pill w-100 py-3 ps-4 pe-5" type="text" placeholder="Đà Lạt, Sapa,...">
                                
                                <button type="button" class="btn btn-primary rounded-pill py-2 px-4 position-absolute top-0 end-0 me-2" style="margin-top: 7px;">Tìm kiếm</button>
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
                            <!--<img class="img-fluid position-absolute w-100 h-100" src="../../assets/img/jpeg-3.jpg"  style="object-fit: cover;">-->
                            <img class="img-fluid position-absolute w-100 h-100" src="${pageContext.request.contextPath}/assets/img/15.jpg"style="object-fit: cover;">
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
                                <p class="mb-0"><i class="fa fa-arrow-right text-primary me-2"></i>Phương tiện hiện đại</p>
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


        <!-- Destination Start -->
        <div class="container-xxl py-5 destination">
            <div class="container">
                <div class="text-center wow fadeInUp" data-wow-delay="0.1s">
                    <h6 class="section-title bg-white text-center text-primary px-3">Điểm đến</h6>
                    <h1 class="mb-5">Điểm đến phổ biến</h1>
                </div>
                <div class="row g-3">
                    <div class="col-lg-7 col-md-6">
                        <div class="row g-3">
                            <div class="col-lg-12 col-md-12 wow zoomIn" data-wow-delay="0.1s">
                                <a class="position-relative d-block overflow-hidden" href="">
                                    <img class="img-fluid" src="${pageContext.request.contextPath}/assets/img/10.jpg" alt="">
                                    <div class="bg-white text-danger fw-bold position-absolute top-0 start-0 m-3 py-1 px-2">30% OFF</div>
                                    <div class="bg-white text-primary fw-bold position-absolute bottom-0 end-0 m-3 py-1 px-2">Sapa</div>
                                </a>
                            </div>
                            <div class="col-lg-6 col-md-12 wow zoomIn" data-wow-delay="0.3s">
                                <a class="position-relative d-block overflow-hidden" href="">
                                    <img class="img-fluid" src="${pageContext.request.contextPath}/assets/img/bana.webp" alt="">
                                    <div class="bg-white text-danger fw-bold position-absolute top-0 start-0 m-3 py-1 px-2">25% OFF</div>
                                    <div class="bg-white text-primary fw-bold position-absolute bottom-0 end-0 m-3 py-1 px-2">Bà Nà Hills</div>
                                </a>
                            </div>
                            <div class="col-lg-6 col-md-12 wow zoomIn" data-wow-delay="0.5s">
                                <a class="position-relative d-block overflow-hidden" href="">
                                    <img class="img-fluid" src="${pageContext.request.contextPath}/assets/img/halong.webp" alt="">
                                    <div class="bg-white text-danger fw-bold position-absolute top-0 start-0 m-3 py-1 px-2">35% OFF</div>
                                    <div class="bg-white text-primary fw-bold position-absolute bottom-0 end-0 m-3 py-1 px-2">Hạ Long</div>
                                </a>
                            </div>
                        </div>
                    </div>
                    <div class="col-lg-5 col-md-6 wow zoomIn" data-wow-delay="0.7s" style="min-height: 350px;">
                        <a class="position-relative d-block h-100 overflow-hidden" href="">
                            <img class="img-fluid position-absolute w-100 h-100" src="${pageContext.request.contextPath}/assets/img/phuquoc.webp" alt="" style="object-fit: cover;">
                            <div class="bg-white text-danger fw-bold position-absolute top-0 start-0 m-3 py-1 px-2">20% OFF</div>
                            <div class="bg-white text-primary fw-bold position-absolute bottom-0 end-0 m-3 py-1 px-2">Phú Quốc</div>
                        </a>
                    </div>
                </div>
            </div>
        </div>
        <!-- Destination Start -->


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


        <!-- Booking Start -->
        <div class="container-xxl py-5 wow fadeInUp" data-wow-delay="0.1s">
            <div class="container">
                <div class="booking p-5">
                    <div class="row g-5 align-items-center">
                        <div class="col-md-6 text-white">
                            <h6 class="text-white text-uppercase">Booking</h6>
                            <h1 class="text-white mb-4">Online Booking</h1>
                            <p class="mb-4">Tempor erat elitr rebum at clita. Diam dolor diam ipsum sit. Aliqu diam amet diam et eos. Clita erat ipsum et lorem et sit.</p>
                            <p class="mb-4">Tempor erat elitr rebum at clita. Diam dolor diam ipsum sit. Aliqu diam amet diam et eos. Clita erat ipsum et lorem et sit, sed stet lorem sit clita duo justo magna dolore erat amet</p>
                            <a class="btn btn-outline-light py-3 px-5 mt-2" href="">Read More</a>
                        </div>
                        <div class="col-md-6">
                            <h1 class="text-white mb-4">Book A Tour</h1>
                            <form>
                                <div class="row g-3">
                                    <div class="col-md-6">
                                        <div class="form-floating">
                                            <input type="text" class="form-control bg-transparent" id="name" placeholder="Your Name">
                                            <label for="name">Your Name</label>
                                        </div>
                                    </div>
                                    <div class="col-md-6">
                                        <div class="form-floating">
                                            <input type="email" class="form-control bg-transparent" id="email" placeholder="Your Email">
                                            <label for="email">Your Email</label>
                                        </div>
                                    </div>
                                    <div class="col-md-6">
                                        <div class="form-floating date" id="date3" data-target-input="nearest">
                                            <input type="text" class="form-control bg-transparent datetimepicker-input" id="datetime" placeholder="Date & Time" data-target="#date3" data-toggle="datetimepicker" />
                                            <label for="datetime">Date & Time</label>
                                        </div>
                                    </div>
                                    <div class="col-md-6">
                                        <div class="form-floating">
                                            <select class="form-select bg-transparent" id="select1">
                                                <option value="1">Destination 1</option>
                                                <option value="2">Destination 2</option>
                                                <option value="3">Destination 3</option>
                                            </select>
                                            <label for="select1">Destination</label>
                                        </div>
                                    </div>
                                    <div class="col-12">
                                        <div class="form-floating">
                                            <textarea class="form-control bg-transparent" placeholder="Special Request" id="message" style="height: 100px"></textarea>
                                            <label for="message">Special Request</label>
                                        </div>
                                    </div>
                                    <div class="col-12">
                                        <button class="btn btn-outline-light w-100 py-3" type="submit">Book Now</button>
                                    </div>
                                </div>
                            </form>
                        </div>
                    </div>
                </div>
            </div>
        </div>
        <!-- Booking Start -->


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
        <!-- Process Start -->

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


        <!-- Footer Start -->
        <%@include file="../layout/footer.jsp" %>
        <!-- Footer End -->


        <!-- Back to Top -->
        <a href="#" class="btn btn-lg btn-primary btn-lg-square back-to-top"><i class="bi bi-arrow-up"></i></a>



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
