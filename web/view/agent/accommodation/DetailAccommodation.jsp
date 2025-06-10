<%-- 
 * Copyright (C) 2025, Group 6.
 * ProjectCode/Short Name of Application: TravelAgentService 
 * Support Management and Provide Travel Service System 
 *
 * Record of change:
 * DATE        Version    AUTHOR            DESCRIPTION
 *  2025-06-07  1.0       NguyenVanVang     First implementation
--%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@page import="model.Room" %>

<!DOCTYPE html>
<html>
    <head>
        <meta charset="utf-8" />
        <meta http-equiv="X-UA-Compatible" content="IE=edge" />
        <meta name="viewport" content="width=device-width, initial-scale=1, shrink-to-fit=no" />
        <title>Chi tiết phòng</title>
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
            white-space: nowrap;
        }

        html, body {
            height: 100%;
            margin: 0;
            padding: 0;
            overflow-x: hidden;
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
            max-height: 500px;
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
             height: 100%;
            color: #fff;
            position: relative;
            z-index: 0;
            background-image: url(./assets/img/background/hotelB.jpg);
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
            background: rgba(0, 0, 0, 0.4);
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
    </style>
<body>
    <%@include file="../../layout/HeaderAdmin.jsp" %>
    <div id="layoutSidenav">
        <jsp:include page="../../layout/SideNavOptionAgent.jsp"></jsp:include>  
            <div id="layoutSidenav_content">
                <main>
                    <div class="card-body px-0 pb-2">
                        <div class="container mt-2">
                            <div class="row title">
                                <div class="detail-title">
                                    <h1 style="font-family: var(--font1)">Chi tiết phòng</h1>
                                </div>
                            </div>
                            <div class="row detail">
                                <div class="col-md-6 box-img">
                                    <div class="show-img">
                                        <img src="${requestScope.acc.image}">
                                    </div>
                                </div>

                            <div class="col-md-6 box-desc">
                                    <div class="col-12 rate">
                                        <h3>Loại hình: 
                                            <span>${requestScope.acc.type}</span>
                                        </h3>
                                    </div>

                                    <div class="col-12 address">
                                        <b>Vị trí: </b><span>${requestScope.acc.address}</span>
                                    </div>

                                    <div class="col-12 phone">
                                        <b>Số điện thoại: </b><span>${requestScope.acc.phone}</span>
                                    </div>

                                    <div class="col-12 time">
                                        <div class="row mb-0">
                                            <div class="col-6 openTime mb-0">
                                                <b>Thời gian mở cửa: </b><span>${requestScope.acc.checkInTime}</span>
                                            </div>
                                            <div class="col-6 close mb-0">
                                                <b>Thời gian đóng cửa: </b><span>${requestScope.acc.checkOutTime}</span>
                                            </div>
                                        </div>
                                    </div>

                                    <div class="col-12 rate">
                                        <b>Đánh giá trung bình: </b>
                                        <span>(<fmt:formatNumber value="${requestScope.acc.rate}" pattern="#"/>/10)</span> 
                                    </div>

                                    <div class="col-12 desciption">
                                        <b>Mô tả: </b>
                                        <span>${requestScope.acc.description}</span> 
                                    </div>

                                    <div class="col-12">
                                        <b>Số lượng phòng: </b><span>${requestScope.room.numberOfRooms}</span>
                                    </div>

                                    <div class="col-12">
                                        <b>Giá phòng: </b>
                                        <fmt:formatNumber value="${requestScope.room.priceOfRoom}" type="currency" currencySymbol="₫" />
                                    </div>

                                    <div class="col-12 back">
                                        <a href="managementaccommodation">
                                            <i class="fa-solid fa-arrow-left"></i>
                                            <span>Trở lại</span>
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
                            <div class="text-muted">Copyright &copy; Go Việt</div>
                            <div>
                                <a href="#">Điều khoản</a> &middot;
                                <a href="#">Terms &amp; Conditions</a>
                            </div>
                        </div>
                    </div>
                </footer>
            </div>
    </div>

    <!-- Scripts -->
    <script src="https://cdn.jsdelivr.net/npm/bootstrap@5.2.3/dist/js/bootstrap.bundle.min.js"></script>
    <script src="./assets/js/scripts.js"></script>
    <script src="https://cdn.jsdelivr.net/npm/simple-datatables@7.1.2/dist/umd/simple-datatables.min.js"></script>
</body>
</html>
