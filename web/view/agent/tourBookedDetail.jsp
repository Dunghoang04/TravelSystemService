<%-- 
 * Copyright (C) 2025, Group 6.
 * ProjectCode/Short Name of Application: TravelAgentService 
 * Support Management and Provide Travel Service System 
 *
 * Record of change:
 * DATE        Version    AUTHOR                   DESCRIPTION
 * 2025-06-21  1.0        Hoang Tuan Dung          First implementation
--%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
    <head>
       <meta charset="utf-8" />
        <meta http-equiv="X-UA-Compatible" content="IE=edge" />
        <meta name="viewport" content="width=device-width, initial-scale=1, shrink-to-fit=no" />
        <meta name="description" content="" />
        <meta name="author" content="" />
        <title>Dashboard - SB Admin</title>
        <link href="https://cdn.jsdelivr.net/npm/simple-datatables@7.1.2/dist/style.min.css" rel="stylesheet" />
        <link href="${pageContext.request.contextPath}/assets/css/styles2.css" rel="stylesheet" />
        <script src="https://use.fontawesome.com/releases/v6.3.0/js/all.js" crossorigin="anonymous"></script>
        
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
                width: 100%;
                height: auto;
                object-fit: contain;
                max-height: 500px; /* hoặc giới hạn chiều cao tùy ý */
                display: block;
                margin: 0 auto;

            }
            .box-desc {
                padding-left: 50px;
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
            main{
                color: #fff;
                z-index: 0;
                position: relative;
                background-repeat: no-repeat;
                background-size: cover;
                background-image: url(./assets/img/background/BackgroundTourBooked.png);
            }
            main::before {
                content: "";
                position: absolute;
                top: 0;
                left: 0;
                width: 100%;
                height: 100%;
                background: rgba(0, 0, 0, 0.5); /* điều chỉnh độ tối ở đây */
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
    </head>

    <body>
        <%@include file="../layout/headerAdmin.jsp" %>
        <div id="layoutSidenav">
                <jsp:include page="../layout/sideNavOptionAgent.jsp"></jsp:include>  
            <div id="layoutSidenav_content">
                <main>
                    <div class="card-body px-0 pb-2">
                        <div class="container mt-1">
                            <div class="row title">
                                <div class="detail-title">
                                    <h1 style="font-family: var(--font1)">Chuyến chi tiết chuyến đi được đặt</h1>
                                </div>
                            </div>
                            <div class="row detail">
                                <!--Tạo ra ảnh xem trước, khung ảnh để người dùng tải lên , sau khi chọn ảnh js sẽ chèn src vô-->
                                <div class="col-md-5 box-img">
                                    <div class="show-img">
                                        <img  src="${item.tourImage}">
                                    </div>
                                </div>

                                <div class="col-md-6 box-desc">
                                    <div class="col-12 name">
                                        <h3>Tên chuyến đi: ${item.tourName}</h3>
                                    </div>
                                    <div class="col-12 rate">
                                        <b class="mb-1">Tên khách hàng đặt: </b>
                                        <span>${item.firstName} ${item.lastName}</span>
                                    </div>
                                    <div class="col-12 address">
                                        <b>Ngày đặt: </b>
                                        <span>${item.bookDate}</span>
                                    </div>
                                    <div class="col-12 address">
                                        <b>Mã giảm giá: </b>
                                        <span>${item.code}</span>
                                    </div>
                                    <div class="col-12 phone">
                                        <b>Số điện thoại:  </b>
                                        <span>${item.phone}</span>
                                    </div>
                                    <div class="col-12 gmail">
                                        <b>Gmail:  </b>
                                        <span>${item.gmail}</span>
                                    </div>
                                    <div class="col-12 gmail">
                                        <b>Tổng giá tiền  </b>
                                        <span>${item.totalPrice}</span>
                                    </div>
                                    <div class="col-12 time">
                                        <div class="row mb-0">
                                            <div class="col-6 openTime mb-0">
                                                <b>Số lượng người lớn: </b><span>${item.numberAdult}</span>
                                            </div>
                                            <div class="col-6 close mb-0">
                                                <b>Số lượng trẻ em: </b><span>${item.numberChildren}</span>
                                            </div>
                                        </div>
                                    </div>
                                    <div class="col-12 desciption">
                                        <b>Ghi chú:  </b><span>${item.note}</span> 
                                    </div>
                                        <div class="col-12 back">
                                            <a href="ManageTourBooked?id=${param.tourId}">
                                                <i class="fa-solid fa-arrow-left"></i>
                                                <span class="backTo">Trở lại</span>
                                            </a>
                                        </div>
                                    
                                </div>

                            </div>
                        </div>
                    </div>
                </main>
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
            </div>
        </div>
        <script src="https://cdn.jsdelivr.net/npm/bootstrap@5.2.3/dist/js/bootstrap.bundle.min.js" crossorigin="anonymous"></script>
        <script src="./assets/js/scripts.js"></script>
        <script src="https://cdnjs.cloudflare.com/ajax/libs/Chart.js/2.8.0/Chart.min.js" crossorigin="anonymous"></script>
        <script src="./assets/demo/chart-area-demo.js"></script>
        <script src="./assets/demo/chart-bar-demo.js"></script>
        <script src="https://cdn.jsdelivr.net/npm/simple-datatables@7.1.2/dist/umd/simple-datatables.min.js" crossorigin="anonymous"></script>
        <script src="./assets/js/datatables-simple-demo.js"></script>
        <script src="https://code.jquery.com/jquery-3.6.0.min.js"></script>
    </body>
</html>
