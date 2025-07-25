
<%--
 * Copyright (C) 2025, Group 6.
 * ProjectCode/Short Name of Application: TravelSystemService
 * Support Management and Provide Travel Service System
 *
 * Record of change:
 * DATE        Version    AUTHOR            DESCRIPTION
 * 2025-6-24  1.0        Huu Hung         First implementation
--%>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>

<!DOCTYPE html>
<html lang="vi">
    <head>
        <meta charset="UTF-8">
        <title>Đơn hàng của tôi</title>
        <link rel="stylesheet" href="css/bootstrap.min.css">
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
                margin-top: 150px;
                min-height: 100vh;
                display: flex;
                flex-direction: column;
            }

            main {
                flex: 1;
            }

            .sidebar {
                background: #fff;
                border-right: 1px solid #ddd;
                padding: 20px;
            }

            .sidebar a {
                display: block;
                padding: 10px 15px;
                margin-bottom: 10px;
                color: #333;
                text-decoration: none;
                border-radius: 6px;
            }

            .sidebar a.active, .sidebar a:hover {
                background-color: #f50057;
                color: white;
            }

            .booking-card {
                background: #fff;
                padding: 20px;
                border: 1px solid #eee;
                border-radius: 10px;
                margin-bottom: 20px;
            }

            .booking-actions {
                margin-top: 10px;
            }

            .no-booking {
                text-align: center;
                padding: 60px 30px;
                color: #888;
            }

            .no-booking img {
                width: 100px;
                margin-bottom: 15px;
            }
            .badge {
                font-size: 0.9rem;
                padding: 6px 10px;
                border-radius: 8px;
            }
        </style>
    </head>
    <body>
        <%@include file="../layout/header.jsp" %>

        <main class="container my-4">
            <div class="row">
                <!-- Sidebar -->
                <div class="col-md-3 sidebar">
                    <a href="currentbooking" class="active">Chuyến đi sắp tới</a>
                    <a href="finishedbooking" class="">Chuyến đi đã hoàn thành</a>
                </div>


                <div class="col-md-9">
                    <h4>Đơn hàng của tôi</h4>

                    <c:choose>
                        <c:when test="${empty bookingList}">
                            <div class="no-booking">
                                <h5>Đơn hàng của bạn đang rỗng</h5>
                                <p>Mọi đơn hàng mà bạn đặt sẽ được hiển thị tại đây. Hiện bạn chưa có bất kỳ đơn hàng nào!</p>
                            </div>
                        </c:when>
                        <c:otherwise>
                            <c:set var="lastTourId" value="0" />
                            <c:forEach var="entry" items="${bookingList}">
                                <c:set var="booking" value="${entry.key}" />
                                <c:set var="tourName" value="${entry.value}" />

                                <div class="booking-card">
                                    <h5>${tourName}</h5>
                                    <p>
                                        Số người: ${booking.numberAdult} người lớn, ${booking.numberChildren} trẻ em<br>
                                        Ngày đặt: <fmt:formatDate value="${booking.bookDate}" pattern="dd/MM/yyyy"/><br>
                                        Tổng tiền: <fmt:formatNumber value="${booking.totalPrice}" type="number" groupingUsed="true"/>VNĐ <br>
                                        Trạng thái: 
                                        <span class="badge
                                              <c:choose>
                                                  <c:when test="${booking.status == 1}">bg-success</c:when>
                                                  <c:when test="${booking.status == 2}">bg-danger</c:when>
                                                  <c:when test="${booking.status == 3}">bg-danger</c:when>
                                                  <c:when test="${booking.status == 4}">bg-primary</c:when>
                                                  <c:when test="${booking.status == 5}">bg-warning text-dark</c:when>
                                                  <c:when test="${booking.status == 6}">bg-info text-dark</c:when>
                                                  <c:when test="${booking.status == 7}">bg-secondary</c:when>
                                                  <c:otherwise>bg-dark</c:otherwise>
                                              </c:choose>
                                              ">
                                            <c:choose>
                                                <c:when test="${booking.status == 1}">Đã thanh toán</c:when>
                                                <c:when test="${booking.status == 2}">Bị hủy bởi người dùng</c:when>
                                                <c:when test="${booking.status == 3}">Bị hủy bởi đại lý</c:when>
                                                <c:when test="${booking.status == 4}">Đã hoàn thành chuyến đi</c:when>
                                                <c:when test="${booking.status == 5}">Đã yêu cầu hoàn tiền</c:when>
                                                <c:when test="${booking.status == 6}">Đã hoàn tiền</c:when>
                                                <c:when test="${booking.status == 7}">Chờ thanh toán</c:when>
                                                <c:otherwise>Không rõ</c:otherwise>
                                            </c:choose>
                                        </span>
                                    </p>

                                    <div class="booking-actions">
                                        <a href="bookingdetail?bookID=${booking.bookID}" class="btn btn-outline-primary btn-sm">Xem chi tiết</a>

                                        <c:choose>
                                            <c:when test="${booking.status == 1 || booking.status == 7}">
                                                <button class="btn btn-outline-danger btn-sm" data-bs-toggle="modal" data-bs-target="#cancelModal${booking.bookID}">
                                                    Huỷ
                                                </button>
                                            </c:when>
                                            <c:when test="${booking.status == 4}">
                                                <a href="feedback?bookID=${booking.bookID}" class="btn btn-outline-success btn-sm">Phản hồi</a>
                                            </c:when>
                                            <c:otherwise>
                                                <span class="badge bg-secondary">Trạng thái khác</span>
                                            </c:otherwise>
                                        </c:choose>
                                    </div>
                                </div>
                                <script src="https://cdn.jsdelivr.net/npm/sweetalert2@11"></script>
                                <!-- Modal Huỷ Tour -->
                                <div class="modal fade" id="cancelModal${booking.bookID}" tabindex="-1" aria-labelledby="cancelModalLabel${booking.bookID}" aria-hidden="true">
                                    <div class="modal-dialog modal-lg modal-dialog-centered modal-dialog-scrollable">
                                        <div class="modal-content">
                                            <form action="cancelbooking" method="post" onsubmit="return validateCancelForm(this)">
                                                <input type="hidden" name="bookID" value="${booking.bookID}" />
                                                <div class="modal-header bg-danger text-white">
                                                    <h5 class="modal-title" id="cancelModalLabel${booking.bookID}">Yêu cầu huỷ đơn hàng</h5>
                                                    <button type="button" class="btn-close btn-close-white" data-bs-dismiss="modal" aria-label="Đóng"></button>
                                                </div>
                                                <div class="modal-body">
                                                    <div class="row">
                                                        <div class="col-md-6 border-end pe-3">
                                                            <h6>Điều khoản huỷ chuyến đi đặt trước</h6>
                                                            <ul class="small text-muted">
                                                                <li>Yêu cầu huỷ trong 24 giờ kể từ khi đặt sẽ được hoàn 100%</li>
                                                                <li>Yêu cầu huỷ trước 7 ngày sẽ được hoàn tiền 80%.</li>
                                                                <li>Huỷ từ 3 - 6 ngày: hoàn 50% giá trị tour.</li>
                                                                <li>Huỷ dưới 3 ngày: <strong>sẽ không được huỷ</strong>.</li>
                                                                <li>Tiền sẽ được hoàn lại vào ví sau 1-3 ngày làm việc.</li>
                                                            </ul>
                                                            <h6>Điều khoản huỷ chuyến đi liền</h6>
                                                            <ul class="small text-muted">
                                                                <li>Sẽ không chấp nhận huỷ với bất cứ lí do nào.</li>
                                                                <li>Yêu cầu chỉ đồng ý khi đại lí huỷ</li>
                                                            </ul>
                                                        </div>
                                                        <div class="col-md-6">
                                                            <div class="mb-3">
                                                                <label for="reason${booking.bookID}" class="form-label">Lý do huỷ tour <span class="text-danger">*</span></label>
                                                                <textarea class="form-control" id="reason${booking.bookID}" name="reason" rows="3" required></textarea>

                                                            </div>
                                                            
                                                        </div>
                                                    </div>
                                                </div>
                                                <div class="modal-footer justify-content-between">
                                                    <button type="button" class="btn btn-secondary" data-bs-dismiss="modal">Đóng</button>
                                                    <button type="submit" class="btn btn-danger">Xác nhận huỷ</button>
                                                </div>
                                            </form>
                                        </div>
                                    </div>
                                </div>
                            </c:forEach>

                        </c:otherwise>
                    </c:choose>


                    <!-- Phân trang -->
                    <nav>
                        <ul class="pagination justify-content-center">
                            <c:forEach begin="1" end="${totalPages}" var="i">
                                <li class="page-item ${i == currentPage ? 'active' : ''}">
                                    <a class="page-link" href="?page=${i}">${i}</a>
                                </li>
                            </c:forEach>
                        </ul>
                    </nav>
                </div>
            </div>

            <c:if test="${param.cancelSuccess == 'true'}">
                <script>
                    Swal.fire({
                        icon: 'success',
                        title: 'Huỷ tour thành công!',
                        text: 'Yêu cầu của bạn đã được gửi đến hệ thống.',
                        showConfirmButton: false,
                        timer: 3000
                    });
                </script>
            </c:if>

            <c:if test="${param.cancelSuccess == 'false'}">
                <script>
                    Swal.fire({
                        icon: 'error',
                        title: 'Huỷ tour thất bại!',
                        text: 'Có lỗi xảy ra. Vui lòng thử lại.',
                        showConfirmButton: false,
                        timer: 3000
                    });
                </script>
            </c:if>
        </main>



        <%@include file="../layout/footer.jsp" %>
        <script src="https://cdn.jsdelivr.net/npm/bootstrap@5.3.0/dist/js/bootstrap.bundle.min.js"></script>
        <script>
                    function validateCancelForm(form) {
                        const reasonField = form.querySelector("textarea[name='reason']");
                        const errorId = "reason-error-msg";
                        let errorMsg = form.querySelector("#" + errorId);

                        // Xoá lỗi cũ nếu có
                        if (errorMsg) {
                            errorMsg.remove();
                        }

                        const reason = reasonField.value.trim();
                        const letterCount = reason.replace(/[^a-zA-Z]/g, "").length;

                        if (letterCount < 10) {
                            // Tạo dòng báo lỗi
                            errorMsg = document.createElement("div");
                            errorMsg.id = errorId;
                            errorMsg.className = "text-danger small mt-1";
                            errorMsg.innerText = "Lý do huỷ chuyến đi phải chứa ít nhất 10 chữ cái.";

                            // Hiển thị lỗi ngay dưới ô nhập
                            reasonField.parentNode.appendChild(errorMsg);

                            // Focus lại ô nhập
                            reasonField.focus();
                            return false; // Ngăn submit
                        }

                        return true; // Cho phép submit
                    }
        </script>

    </body>
</html>
