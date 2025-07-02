<%-- 
 * Copyright (C) 2025, Group 6.
 * ProjectCode/Short Name of Application: TravelAgentService 
 * Support Management and Provide Travel Service System 
 *
 * Record of change:
 * DATE        Version    AUTHOR                   DESCRIPTION
 * 2025-06-07  1.0        Hoang Tuan Dung          First implementation
--%>

<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<!DOCTYPE html>
<html>
    <head>
        <meta charset="utf-8" />
        <meta http-equiv="X-UA-Compatible" content="IE=edge" />
        <meta name="viewport" content="width=device-width, initial-scale=1, shrink-to-fit=no" />
        <title>Chi tiết giải trí</title>
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

        <link rel="preconnect" href="https://fonts.googleapis.com">
        <link rel="preconnect" href="https://fonts.gstatic.com" crossorigin>
        <link href="https://fonts.googleapis.com/css2?family=Ancizar+Serif:ital,wght@0,300..900;1,300..900&family=Ephesis&display=swap" rel="stylesheet">
        <link href="https://cdnjs.cloudflare.com/ajax/libs/font-awesome/5.10.0/css/all.min.css" rel="stylesheet">
        <link href="https://cdn.jsdelivr.net/npm/bootstrap-icons@1.4.1/font/bootstrap-icons.css" rel="stylesheet">
        <script src="https://use.fontawesome.com/releases/v6.3.0/js/all.js" crossorigin="anonymous"></script>
        

        <link href="https://cdn.jsdelivr.net/npm/simple-datatables@7.1.2/dist/style.min.css" rel="stylesheet" />
        <link href="./assets/css/styles2.css" rel="stylesheet" />
        <script src="https://use.fontawesome.com/releases/v6.3.0/js/all.js" crossorigin="anonymous"></script>
    </head>
    <style>
        form {
            display: flex;
            flex-wrap: wrap;
            gap: 10px;
            justify-content: center;
        }

        form input, form select, form button {
            height: 35px;
            font-size: 13px;
        }

        button {
            white-space: nowrap; /* Tránh chữ trong button bị cắt */
        }

        html, body {
            height: 100%;
            margin: 0;
            padding: 0;

            overflow-x: hidden; /* Ngăn chặn tràn ngang */
        }

        .container-xxl {
            min-height: 100vh;
            display: flex;
            flex-direction: column;
        }

        .sidebar {
            height: 100vh;
            position: fixed;
            left: 0;
            top: 0;
            width: 250px;
            background: #f8f9fa;
            overflow-y: auto;
        }

        .content {
            margin-left: 250px;
            padding: 20px;
            width: calc(100% - 250px);
            flex: 1;
            overflow-x: auto;
        }
        .detail{
            padding: 0px 30px;
        }
        .detail .show-img img{
            width: 90%;
            height: auto;
            object-fit: contain;
            max-height: 500px; /* hoặc giới hạn chiều cao tùy ý */
            display: block;
            margin: 0 auto;

        }
        .detail-title{
            margin-bottom: 40px;
            margin-top: 20px;
        }
        .detail .box-desc div{
            margin-bottom: 30px;
        }
        .detail .box-desc  .rate .star {
            color: #FFCA2C;
        }
        main {
            color: #fff;
            position: relative;
            z-index: 0;
            background-image: url(./assets/img/background/background-entertainment2.png);


            background-size: cover;
            background-repeat: no-repeat;
        }

        main::before {
            content: "";
            position: absolute;
            top: 0;
            left: 0;
            width: 100%;
            height: 100%;
            background: rgba(0, 0, 0, 0.7); /* điều chỉnh độ tối ở đây */


            z-index: -1;
        }
        footer{
            margin-top: 0px;
        }
        .detail .back a{
            border: solid 1px #fff;
            padding: 10px 17px;
            color: #fff;
            border-radius: 10px;
            font-size: 16px;
            text-decoration: none;
        }
        .detail .back a:hover{
            background: #fff;
            color: #000;
        }
        .box-desc .name{
            position: relative;
        }
        .box-desc .name::before{
            content: "";
            top: 47px;
            left: 1px;
            color: cyan;
            width: 51px;
            height: 1px;
            background: #fff;
            position: absolute;
        }
        main{
            height: 100%;
        }
        body span,h3,h1{
            color: #FFFFFF!important;
        }
        .detail .back a:hover i,
        .detail .back a:hover span {
            color: #000 !important;
        }

        /* Đẩy menu sang phải nhẹ nhàng, không căn giữa */
            .navbar-collapse {
                display: flex !important;
                justify-content: flex-start !important;
                margin-left: -30px; /* Dịch menu sang trái */
            }

            .navbar-nav {
                display: flex;
                align-items: center;
                padding-left: 50px; /* Tạo khoảng cách giữa logo và menu */
            }


            /* Căn dropdown avatar & làm đẹp */
            .dropdown {
                position: relative;
                margin-left: 20px;
            }

            .dropdown-menu {
                left: 50%;
                transform: translateX(-50%);

                min-width: 160px;
            }

            .dropdown-toggle i {
                font-size: 20px;
                color: #28a745;
            }

            .btn.border {
                border-radius: 50%;
                width: 40px;
                height: 40px;
                display: flex;
                justify-content: center;
                align-items: center;
                padding: 0;
            }

    </style>
</style>
<body>

    <c:if test="${empty param.tourId}">
        <jsp:include page="../../layout/headerAdmin.jsp" />
    </c:if>
    <c:if test="${not empty param.tourId}">
       <header class="navbar navbar-expand-lg navbar-light px-4 px-lg-5 py-3 py-lg-0" style="background-color: #fff; color: #000!important">
            <a href="home" class="navbar-brand p-0">
                <h1 style="font-style: italic;color: #86B817!important;    font-weight: 800;" class="text-primary m-0"></i>Go<span style="color: red!important">Viet</span></h1>
                <!-- <img src="img/logo.png" alt="Logo"> -->
            </a>
            <button class="navbar-toggler" type="button" data-bs-toggle="collapse" data-bs-target="#navbarCollapse">
                <span class="fa fa-bars"></span>
            </button>
            <div class="navbar-nav ms-auto py-0">
                <a href="home" class="nav-item nav-link active">Trang chủ</a>
                <a href="service.html" class="nav-item nav-link">Dịch vụ</a>
                <a href="package.html" class="nav-item nav-link">Nổi bật</a>
                <a href="booking.html" class="nav-item nav-link">Đặt lịch</a>  

                <c:if test="${empty sessionScope.loginUser}">
                    <a href="${pageContext.request.contextPath}/RegisterTravelAgentServlet" class="nav-item nav-link">Đối tác</a>
                </c:if>

                <a href="contact.html" class="nav-item nav-link">Liên hệ</a>

                <c:choose>
                    <c:when test="${empty sessionScope.loginUser}">
                        <a href="${pageContext.request.contextPath}/GmailUser" class="nav-item nav-link">Đăng ký</a>
                        <a href="${pageContext.request.contextPath}/LoginLogout" class="nav-item nav-link">Đăng nhập</a>
                    </c:when>
                    <c:otherwise>
                        <div class="dropdown">
                            <a class="btn border dropdown-toggle" href="#" role="button" id="accountDropdown" data-bs-toggle="dropdown" aria-expanded="false">
                                <i class="fas fa-user text-primary"></i>
                            </a>
                            <ul class="dropdown-menu" aria-labelledby="accountDropdown">
                                <li><a class="dropdown-item" href="${pageContext.request.contextPath}/ProfileUser">Xem tài khoản</a></li>
                                <li><a class="dropdown-item" href="${pageContext.request.contextPath}/LoginLogout?service=logoutUser">Đăng xuất</a></li>
                            </ul>
                        </div>
                    </c:otherwise>
                </c:choose>
            </div>

        </header>
    </c:if>
    <div id="layoutSidenav">
        <c:if test="${empty param.tourId}">
            <jsp:include page="../../layout/sideNavOptionAgent.jsp"></jsp:include>
        </c:if>
        <div id="layoutSidenav_content">
            <main>
                <div class="card-body px-0 pb-2">
                    <div class="container mt-2">
                        <div class="row title">
                            <div class="detail-title">
                                <h1 style="font-family: var(--font1)">Chi tiết địa điểm giải trí</h1>
                            </div>
                        </div>
                        <div class="row detail">
                            <div class="col-md-6 box-img">
                                <div class="show-img">
                                    <!-- Display entertainment image -->
                                    <img src="${requestScope.entertainmentDetail.getImage()}">


                                </div>
                            </div>
                            <div class="col-md-6 box-desc">
                                <div class="col-12 name">
                                    <h3>Tên dịch vụ giải trí: <span style="font-family: 'Ephesis', cursive; font-size: 40px">${requestScope.entertainmentDetail.getName()}</span></h3>
                                </div>
                                <div class="col-12 rate">
                                    <b>Loại hình : <span>${requestScope.entertainmentDetail.getType()}</span></b>


                                </div>
                                <div class="col-12 address">
                                    <b>Vị trí: </b><span>${requestScope.entertainmentDetail.getAddress()}</span>
                                </div>
                                <div class="col-12 phone">
                                    <b>Số điện thoại: </b><span>${requestScope.entertainmentDetail.getPhone()}</span>
                                </div>
                                <div class="col-12 time">
                                    <div class="row mb-0">
                                        <div class="col-6 openTime mb-0">
                                            <b>Thời gian mở cửa: </b><span>${requestScope.entertainmentDetail.getTimeOpen()}</span>
                                        </div>
                                        <div class="col-6 close mb-0">
                                            <b>Thời gian đóng cửa: </b><span>${requestScope.entertainmentDetail.getTimeClose()}</span>
                                        </div>
                                    </div>
                                </div>
                                <div class="col-12 dayOfWeek">
                                    <b>Ngày hoạt động: </b><span>${requestScope.entertainmentDetail.getDayOfWeekOpen()}</span>
                                </div>
                                <div class="col-12 price">
                                    <b>Giá vé: </b><span><fmt:formatNumber value="${requestScope.entertainmentDetail.getTicketPrice()}" type="number" groupingUsed="true"></fmt:formatNumber>VNĐ</span>
                                    </div>
                                    <div class="col-12 rate">
                                        <b>Đánh giá trung bình: </b><span>(<fmt:formatNumber value="${requestScope.entertainmentDetail.getRate()}" pattern="#"></fmt:formatNumber>/10)</span>
                                    </div>
                                    <div class="col-12 desciption">
                                        <b>Mô tả: </b><span>${requestScope.entertainmentDetail.getDescription()}</span>
                                </div>
                                <div class="col-12 back">
                                    <c:if test="${empty param.tourId}">
                                        <a href="${pageContext.request.contextPath}/managementertainment">
                                            <i class="fa-solid fa-arrow-left"></i>
                                            <span>Trở lại</span>
                                        </a>
                                    </c:if>
                                    <c:if test="${not empty param.tourId}">
                                        <a href="TourDetailServlet?tourId=${param.tourId}">
                                            <i class="fa-solid fa-arrow-left"></i>
                                            <span>Trở lại</span>
                                        </a>
                                    </c:if>


                                </div>
                            </div>
                        </div>
                    </div>
                </div>
            </main>
            <c:if test="${empty param.tourId}">
                <footer class="py-4 bg-light">
                    <div class="container-fluid px-4">
                        <div class="d-flex align-items-center justify-content-between small">
                            <div class="text-muted">Copyright © Go Việt</div>
                            <div>
                                <a href="#">Điều khoản</a> ·
                                <a href="#">Terms & Conditions</a>
                            </div>
                        </div>
                    </div>
                </footer>
            </c:if>
            <c:if test="${not empty param.tourId}">
                <%@include file="../../layout/footer.jsp" %>
            </c:if>                 

        </div>
    </div>

    <!-- Scripts -->
    <script src="https://cdn.jsdelivr.net/npm/bootstrap@5.2.3/dist/js/bootstrap.bundle.min.js"></script>
    <script src="./assets/js/scripts.js"></script>
    <script src="https://cdn.jsdelivr.net/npm/simple-datatables@7.1.2/dist/umd/simple-datatables.min.js"></script>
</body>
</html>