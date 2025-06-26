<%-- 
    Document   : paymentSuccess
    Created on : Jun 26, 2025, 6:46:35 PM
    Author     : Hung
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
    <head>
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

        <style>
            body {
                padding-top: 110px; /* tránh trùng với header */
                background-color: #f8f9fa;
            }

            .container-box {
                max-width: 900px;
                margin: 0 auto;
                padding: 40px;
                background-color: white;
                border-radius: 12px;
                box-shadow: 0 0 10px rgba(0,0,0,0.1);
                min-height: calc(100vh - 300px);
            }

            .title-success {
                font-size: 22px;
                font-weight: bold;
                color: #28a745;
                text-align: center;
                margin-bottom: 30px;
            }

            .row .label {
                font-weight: bold;
            }

            .btn-home {
                display: block;
                margin: 40px auto 0;
            }
        </style>
    </head>
    <body>

        <%@ include file="../layout/header.jsp" %>

        <div class="container" style="max-width: 1140px; margin: 100px auto; padding: 40px; background-color: #fff; border-radius: 20px; box-shadow: 0 0 20px rgba(0,0,0,0.1);">
            <h3 class="text-center text-success mb-5">Bạn đã thanh toán thành công đơn hàng</h3>
            <p><strong>Tên chuyến du lịch:</strong> ${tour.tourName}</p>
            <div class="row">
                <div class="col-md-6">
                    
                    <p><strong>Họ:</strong> ${bookDetail.lastName}</p>
                    <p><strong>Ngày bắt đầu:</strong> ${tour.startDay}</p>
                    <p><strong>Số lượng người lớn:</strong> ${bookDetail.numberAdult}</p>
                    <p><strong>Gmail:</strong> ${bookDetail.gmail}</p>
                    <p><strong>Số điện thoại:</strong> ${bookDetail.phone}</p>
                    <p><strong>Tổng tiền:</strong> ${bookDetail.totalPrice.longValue()} VNĐ</p>
                </div>

                <div class="col-md-6">
                    <p><strong>Tên:</strong> ${bookDetail.firstName}</p>
                    <p><strong>Ngày kết thúc:</strong> ${tour.endDay}</p>
                    <p><strong>Số lượng trẻ em:</strong> ${bookDetail.numberChildren}</p>
                </div>
            </div>

            <div class="text-center mt-5">
                <a href="home" class="btn btn-outline-success px-5 py-2">Quay lại trang chủ</a>
            </div>
        </div>


        <%@ include file="../layout/footer.jsp" %>
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
        <script src="https://cdn.jsdelivr.net/npm/bootstrap@5.0.2/dist/js/bootstrap.bundle.min.js"></script>
    </body>
</html>

