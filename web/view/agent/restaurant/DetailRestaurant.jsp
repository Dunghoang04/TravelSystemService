<%-- 
    Document   : DetailRestaurant
    Created on : May 30, 2025, 7:09:02 PM
    Author     : ad
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
        <title>Chi tiết nhà hàng</title>
        <link href="https://cdn.jsdelivr.net/npm/simple-datatables@7.1.2/dist/style.min.css" rel="stylesheet" />
        <link href="./assets/css/styles2.css" rel="stylesheet" />
        <script src="https://use.fontawesome.com/releases/v6.3.0/js/all.js" crossorigin="anonymous"></script>
        <link rel="preconnect" href="https://fonts.googleapis.com">
        <link rel="preconnect" href="https://fonts.gstatic.com" crossorigin>
        <link href="https://fonts.googleapis.com/css2?family=Ancizar+Serif:ital,wght@0,300..900;1,300..900&family=Ephesis&display=swap" rel="stylesheet">
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
            height: 100%;
            color: #fff;
            background-repeat: no-repeat;
            background-size: cover;
            background-image: url(./assets/img/background/background_restaurant);
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
                            <div class="container mt-1">
                                <div class="row title">
                                    <div class="detail-title">
                                        <h1 style="font-family: var(--font1)">Chi tiết nhà hàng</h1>
                                    </div>
                                </div>
                                <div class="row detail">
                                    <!--Tạo ra ảnh xem trước, khung ảnh để người dùng tải lên , sau khi chọn ảnh js sẽ chèn src vô-->
                                    <div class="col-md-5 box-img">
                                        <div class="show-img">
                                            <img  src="${requestScope.restaurantDetail.getImage()}">
                                    </div>
                                </div>

                                <div class="col-md-6 box-desc">
                                    <div class="col-12 name">
                                        <h3>Nhà hàng <span style="font-family: 'Ephesis', cursive;font-size: 40px">${requestScope.restaurantDetail.getName()}</span></h3>
                                    </div>
                                    <div class="col-12 rate">
                                        <b class="mb-1">Loại nhà hàng</b>
                                        <c:if test="${requestScope.restaurantDetail.getType()=='5 sao'}">
                                            <div class="star">
                                                <i class="fa-solid fa-star"></i>
                                                <i class="fa-solid fa-star"></i>
                                                <i class="fa-solid fa-star"></i>
                                                <i class="fa-solid fa-star"></i>
                                                <i class="fa-solid fa-star"></i>
                                            </div>
                                        </c:if>
                                        <c:if test="${requestScope.restaurantDetail.getType()=='4 sao'}">
                                            <div class="star">
                                                <i class="fa-solid fa-star"></i>
                                                <i class="fa-solid fa-star"></i>
                                                <i class="fa-solid fa-star"></i>
                                                <i class="fa-solid fa-star"></i>
                                            </div>
                                        </c:if>
                                        <c:if test="${requestScope.restaurantDetail.getType()=='3 sao'}">
                                            <div class="star">
                                                <i class="fa-solid fa-star"></i>
                                                <i class="fa-solid fa-star"></i>
                                                <i class="fa-solid fa-star"></i>
                                            </div>
                                        </c:if>

                                    </div>
                                    <div class="col-12 address">
                                        <b>Vị trí: </b>
                                        <span>${requestScope.restaurantDetail.getAddress()}</span>
                                    </div>
                                    <div class="col-12 phone">
                                        <b>Số điện thoại:  </b>
                                        <span>${requestScope.restaurantDetail.getPhone()}</span>
                                    </div>
                                    <div class="col-12 time">
                                        <div class="row mb-0">
                                            <div class="col-6 openTime mb-0">
                                                <b>Thời gian mở cửa: </b><span>${requestScope.restaurantDetail.getTimeOpen()}</span>
                                            </div>
                                            <div class="col-6 close mb-0">
                                                <b>Thời gian đóng cửa: </b><span>${requestScope.restaurantDetail.getTimeClose()}</span>
                                            </div>
                                        </div>
                                    </div>
                                    <div class="col-12 rate">
                                        <b>Đánh giá trung bình: </b><span>(${requestScope.restaurantDetail.getRate()}/10)</span> 
                                    </div>
                                    <div class="col-12 desciption">
                                        <b>Mô tả: </b><span>${requestScope.restaurantDetail.getDescription()}</span> 
                                    </div>
                                    <div class="col-12 back">
                                        <a href="managerestaurant">
                                            <i class="fa-solid fa-arrow-left"></i>
                                            <span>Trở lại</span>
                                        </a>
                                    </div>
                                </div>

                            </div>
                        </div>
                    </div>
                </main>
                <footer class="py-4 bg-light ">
                    <div class="container-fluid px-4">
                        <div class="d-flex align-items-center justify-content-between small">
                            <div class="text-muted">Copyright &copy; Go Việt</div>
                            <div>
                                <a href="#">Điều khoản</a>
                                &middot;
                                <a href="#">Terms &amp; Conditions</a>
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
