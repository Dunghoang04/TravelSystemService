<%-- 
    Document   : bookingDetail
    Created on : Jul 9, 2025, 2:46:51 PM
    Author     : Hung
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>

<!DOCTYPE html>
<html lang="vi">
<head>
    <meta charset="UTF-8">
    <title>Chi tiết đơn hàng</title>
    <link href="assets/css/bootstrap.min.css" rel="stylesheet">
    <link href="assets/css/style.css" rel="stylesheet">
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
            margin-top: 150px;
            min-height: 100vh;
            display: flex;
            flex-direction: column;
        }

        main {
            flex: 1;
        }

        .section-title {
            font-weight: 700;
            font-size: 22px;
            color: #333;
            margin-bottom: 15px;
            border-left: 5px solid #f50057;
            padding-left: 10px;
        }

        .detail-card {
            background: #fff;
            padding: 20px;
            border: 1px solid #eee;
            border-radius: 10px;
            margin-bottom: 30px;
        }

        .detail-item {
            margin-bottom: 10px;
        }

        .detail-label {
            font-weight: 600;
            color: #555;
        }
    </style>
</head>
<body>
    <%@ include file="../layout/header.jsp" %>

    <main class="container my-4">
        <div class="row">
            <div class="col-md-12">
                <h3 class="mb-4">Chi tiết đơn hàng</h3>

                <!-- Thông tin Tour -->
                <div class="detail-card">
                    <div class="section-title">Thông tin đơn đặt</div>
                    <div class="detail-item"><span class="detail-label">Tên tour:</span> ${tour.tourName}</div>
                    <div class="detail-item"><span class="detail-label">Địa điểm bắt đầu:</span> ${tour.startPlace}</div>
                    <div class="detail-item"><span class="detail-label">Địa điểm kết thúc:</span> ${tour.endPlace}</div>
                    <div class="detail-item"><span class="detail-label">Ngày khởi hành:</span> <fmt:formatDate value="${tour.startDay}" pattern="dd/MM/yyyy" /></div>
                    <div class="detail-item"><span class="detail-label">Tổng tiền:</span><fmt:formatNumber value="${booking.totalPrice}" type="number" groupingUsed="true"/> VNĐ</div>
                    <div class="detail-item"><span class="detail-label">Trạng thái:</span> 
                        <c:choose>
                            <c:when test="${booking.status == 1}">Đã thanh toán</c:when>
                            <c:when test="${booking.status == 2}">Bị hủy bởi người dùng</c:when>
                            <c:when test="${booking.status == 3}">Bị hủy bởi đại lý</c:when>
                            <c:when test="${booking.status == 4}">Đã hoàn thành chuyến đi</c:when>
                            <c:when test="${booking.status == 5}">Đã yêu cầu hoàn tiền</c:when>
                            <c:when test="${booking.status == 6}">Đã hoàn tiền</c:when>
                            <c:when test="${booking.status == 7}">Chờ thanh toán</c:when>
                            <c:otherwise>Không xác định</c:otherwise>
                        </c:choose>
                    </div>
                </div>
                    
              

                <!-- Thông tin đại lý -->
                <div class="detail-card">
                    <div class="section-title">Thông tin đại lý chịu trách nhiệm</div>
                    <div class="detail-item"><span class="detail-label">Tên đại lý:</span> ${travelAgent.travelAgentName}</div>
                    <div class="detail-item"><span class="detail-label">Email:</span> ${travelAgent.travelAgentGmail}</div>
                    <div class="detail-item"><span class="detail-label">Số điện thoại:</span> ${travelAgent.phone}</div>
                    <div class="detail-item"><span class="detail-label">Địa chỉ:</span> ${travelAgent.travelAgentAddress}</div>
                </div>

                <a href="javascript:history.back()" class="btn btn-outline-secondary">Quay lại</a>
            </div>
        </div>
    </main>

    <%@ include file="../layout/footer.jsp" %>
</body>
</html>

