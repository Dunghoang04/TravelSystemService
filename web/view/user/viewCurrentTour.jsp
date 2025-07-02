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
            background-color: #f5f5f5;
            margin: 0;
            padding-top: 110px; /* tạo khoảng cách từ header */
        }

        .container-fixed {
            max-width: 1200px;
            margin: 0 auto;
            min-height: calc(100vh - 300px); /* giúp đẩy footer xuống nếu ít content */
        }

        .sidebar-btn {
            width: 100%;
            margin-bottom: 10px;
            font-weight: bold;
            padding: 10px;
        }

        .btn-active {
            background-color: #8bc34a;
            color: white;
        }

        .tour-card {
            border: 1px solid #ddd;
            border-radius: 6px;
            padding: 20px;
            background-color: #fff;
        }

        .tour-card:not(:last-child) {
            margin-bottom: 15px;
        }

        .tour-status {
            font-weight: bold;
            padding: 5px 10px;
            border-radius: 5px;
        }

        .badge-green {
            background-color: #4caf50;
            color: white;
        }

        .badge-yellow {
            background-color: #ffc107;
            color: black;
        }

        .pagination .page-link {
            color: #000;
        }

        .pagination .active .page-link {
            background-color: #007bff;
            color: white;
        }
    </style>
</head>
<body>

    <%@ include file="../layout/header.jsp" %>

    <div class="container-fixed">
        <div class="row">
            <!-- Sidebar -->
            <div class="col-md-3">
                <a href="viewCurrentTour.jsp" class="btn sidebar-btn btn-active">Chuyến đi hiện tại</a>
                <a href="viewTourFinish.jsp" class="btn sidebar-btn btn-outline-warning">Chuyến đi đã hoàn thành</a>
            </div>

            <!-- Tour list -->
            <div class="col-md-9">
                <!-- Tour 1 -->
                <div class="tour-card d-flex justify-content-between align-items-center">
                    <div>
                        <h5><strong>Đà Lạt – Bình yên chọn</strong></h5>
                        <p>28 người</p>
                        <p>Ngày bắt đầu: 2025-07-10</p>
                        <p>Ngày kết thúc: 2025-07-12</p>
                    </div>
                    <div class="text-end">
                        <span class="tour-status badge-green">Đang diễn ra</span><br><br>
                        <button class="btn btn-outline-danger btn-sm mb-1">Huỷ</button><br>
                        <button class="btn btn-outline-success btn-sm">Xem chi tiết</button>
                    </div>
                </div>

                <!-- Tour 2 -->
                <div class="tour-card d-flex justify-content-between align-items-center">
                    <div>
                        <h5><strong>Nha Trang biển gọi</strong></h5>
                        <p>18 người</p>
                        <p>Ngày bắt đầu: 2025-08-01</p>
                        <p>Ngày kết thúc: 2025-08-05</p>
                    </div>
                    <div class="text-end">
                        <span class="tour-status badge-yellow">Chờ khởi hành</span><br><br>
                        <button class="btn btn-outline-danger btn-sm mb-1">Huỷ</button><br>
                        <button class="btn btn-outline-success btn-sm">Xem chi tiết</button>
                    </div>
                </div>

                <!-- Pagination -->
                <nav class="mt-4">
                    <ul class="pagination justify-content-center">
                        <li class="page-item active"><a class="page-link" href="#">1</a></li>
                        <li class="page-item"><a class="page-link" href="#">2</a></li>
                    </ul>
                </nav>
            </div>
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
