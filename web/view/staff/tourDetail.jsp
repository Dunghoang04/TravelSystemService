<%--
 * Copyright (C) 2025, Group 6.
 * ProjectCode/Short Name of Application: TravelSystemService 
 * Support Management and Provide Travel Service System 
 *
 * Record of change:
 * DATE        Version    AUTHOR            DESCRIPTION
 * 2025-06-14  1.0        Quynh Mai          First implementation
--%>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>
<!DOCTYPE html>
<html>
    <head>
        <meta charset="utf-8" />
        <meta http-equiv="X-UA-Compatible" content="IE=edge" />
        <meta name="viewport" content="width=device-width, initial-scale=1, shrink-to-fit=no" />
        <title>Thông tin Tour</title>
        <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.2.3/dist/css/bootstrap.min.css" rel="stylesheet" crossorigin="anonymous">
        <link href="${pageContext.request.contextPath}/assets/css/styles2.css" rel="stylesheet" />
        <script src="https://use.fontawesome.com/releases/v6.3.0/js/all.js" crossorigin="anonymous"></script>
        <style>
            html, body {
                height: 100%;
                margin: 0;
                padding: 0;
                background-color: #f4f6f8;
                color: #333;
                overflow-x: hidden;
            }
            .sidebar {
                position: fixed;
                top: 0;
                left: 0;
                width: 250px;
                height: 100vh;
                background-color: #f8f9fa;
                overflow-y: auto;
            }
            .content {
                margin-left: 250px;
                padding: 20px;
                flex: 1;
            }
            .container-centered {
                max-width: 1200px;
                margin: 0 auto;
            }
            h1 {
                color: #28A745;
                font-size: 24px;
                margin-bottom: 20px;
                text-align: center;
                margin-top: 50px;
            }
            .tour-image {
                max-width: 100%;
                height: auto;
                object-fit: cover;
                margin-bottom: 20px;
            }
            .info-row {
                display: flex;
                align-items: flex-start;
                margin-bottom: 10px;
            }
            .label {
                font-weight: bold;
                font-size: 14px;
                margin-bottom: 0;
                color: #212529;
                width: 150px;
            }
            .value {
                margin: 0;
                font-size: 14px;
                word-wrap: break-word;
                overflow-wrap: break-word;
            }
            .action-button {
                display: inline-block;
                margin: 10px 5px;
                padding: 10px 20px;
                background-color: #28A745;
                color: white;
                text-align: center;
                border-radius: 5px;
                text-decoration: none;
                font-weight: bold;
            }
            .action-button.reject {
                background-color: #dc3545;
            }
            .action-button:hover {
                background-color: #218838;
                color: white;
            }
            .action-button.reject:hover {
                background-color: #c82333;
                color: white;
            }
            .modal-content {
                border-radius: 10px;
            }
            .modal-header {
                background-color: #f8f9fa;
                border-bottom: 1px solid #dee2e6;
            }
            .modal-footer {
                border-top: 1px solid #dee2e6;
            }
            #rejectReason {
                resize: vertical;
                min-height: 100px;
            }
            .action-button, .btn-success, .btn-danger, .btn-primary {
                border: none !important;
                outline: none !important;
                box-shadow: none !important;
            }
            .tour-description {
                white-space: pre-line;
            }
            .error-message {
                color: red;
                font-size: 16px;
                text-align: center;
                margin-bottom: 20px;
            }
        </style>
    </head>
    <body>
        <%@include file="../layout/headerAdmin.jsp" %>
        <div id="layoutSidenav">
            <jsp:include page="../layout/sideNavOptionStaff.jsp" />
            <div id="layoutSidenav_content">
                <main>
                    <div class="container-fluid px-4 container-centered">
                        <h1>Thông tin Tour</h1>
                        <c:if test="${not empty requestScope.errorMessage}">
                            <div class="error-message">${requestScope.errorMessage}</div>
                        </c:if>
                        <c:if test="${requestScope.tour.status == 3}">
                            <div style="color: red; font-size: 20px; text-align: center; margin-bottom: 20px">
                                Lý do từ chối: ${requestScope.tour.reason}
                            </div>
                        </c:if>
                        <div class="row">
                            <div class="col-md-6">
                                <img src="${pageContext.request.contextPath}/${requestScope.tour.image}" alt="Tour Image" class="img-fluid tour-image">
                                <div class="info-row">
                                    <label class="label">Tên Tour:</label>
                                    <p class="value">${requestScope.tour.tourName}</p>
                                </div>
                                <div class="info-row">
                                    <label class="label">Loại Tour:</label>
                                    <p class="value">${requestScope.categoryName}</p>
                                </div>
                                <div class="info-row">
                                    <label class="label">Đại lý:</label>
                                    <p class="value">${requestScope.travelAgentName}</p>
                                </div>
                                <div class="info-row">
                                    <label class="label">Số ngày:</label>
                                    <p class="value">${requestScope.tour.numberOfDay}</p>
                                </div>
                                <div class="info-row">
                                    <label class="label">Nơi khởi hành:</label>
                                    <p class="value">${requestScope.tour.startPlace}</p>
                                </div>
                                <div class="info-row">
                                    <label class="label">Đích đến:</label>
                                    <p class="value">${requestScope.tour.endPlace}</p>
                                </div>
                                <div class="info-row">
                                    <label class="label">Số lượng:</label>
                                    <p class="value">${requestScope.tour.quantity}</p>
                                </div>
                                <div class="info-row">
                                    <label class="label">Đánh giá:</label>
                                    <p class="value">${requestScope.tour.rate != null ? requestScope.tour.rate : '0.0'}</p>
                                </div>
                                <div class="info-row">
                                    <label class="label">Trạng thái:</label>
                                    <p class="value">
                                        <c:choose>
                                            <c:when test="${requestScope.tour.status == 2}">Chờ duyệt</c:when>
                                            <c:when test="${requestScope.tour.status == 1}">Hoạt động</c:when>
                                            <c:when test="${requestScope.tour.status == 3}">Bị từ chối</c:when>
                                            <c:when test="${requestScope.tour.status == 0}">Không hoạt động</c:when>
                                        </c:choose>
                                    </p>
                                </div>
                                <div class="info-row">
                                    <label class="label">Ngày bắt đầu:</label>
                                    <p class="value"><fmt:formatDate value="${requestScope.tour.startDay}" pattern="dd/MM/yyyy" /></p>
                                </div>
                                <div class="info-row">
                                    <label class="label">Ngày kết thúc:</label>
                                    <p class="value"><fmt:formatDate value="${requestScope.tour.endDay}" pattern="dd/MM/yyyy" /></p>
                                </div>
                                <div class="info-row">
                                    <label class="label">Giá người lớn:</label>
                                    <p class="value"><fmt:formatNumber value="${requestScope.tour.adultPrice}" type="currency" currencySymbol="VND " /></p>
                                </div>
                                <div class="info-row">
                                    <label class="label">Giá trẻ em:</label>
                                    <p class="value"><fmt:formatNumber value="${requestScope.tour.childrenPrice}" type="currency" currencySymbol="VND " /></p>
                                </div>
                                <div class="info-row">
                                    <label class="label">Bao gồm:</label>
                                    <p class="value tour-description" id="tourInclude">${requestScope.tour.tourInclude}</p>
                                </div>
                                <div class="info-row">
                                    <label class="label">Không bao gồm:</label>
                                    <p class="value tour-description" id="tourNonInclude">${requestScope.tour.tourNonInclude}</p>
                                </div>
                            </div>
                            <div class="col-md-6">
                                <div class="info-row">
                                    <label class="label">Giới thiệu:</label>
                                    <p class="value tour-description">${requestScope.tour.tourIntroduce}</p>
                                </div>
                                <div class="info-row">
                                    <label class="label">Lịch trình:</label>
                                    <p class="value tour-description" id="tourSchedule">${requestScope.tour.tourSchedule}</p>
                                </div>
                            </div>
                        </div>
                        <div class="text-center mt-4">
                            <a href="javascript:history.back()" class="action-button" style="background-color: #0d6efd">Quay lại</a>
                            <c:if test="${requestScope.tour.status == 2}">
                                <button type="button" class="action-button" data-bs-toggle="modal" data-bs-target="#approveModal">Duyệt</button>
                                <button type="button" class="action-button reject" data-bs-toggle="modal" data-bs-target="#rejectModal">Từ chối</button>
                            </c:if>
                        </div>
                        <!-- Approve Modal -->
                        <div class="modal fade" id="approveModal" tabindex="-1" aria-labelledby="approveModalLabel" aria-hidden="true">
                            <div class="modal-dialog">
                                <div class="modal-content">
                                    <div class="modal-header">
                                        <h5 class="modal-title" id="approveModalLabel">Xác nhận duyệt</h5>
                                        <button type="button" class="btn-close" data-bs-dismiss="modal" aria-label="Close"></button>
                                    </div>
                                    <div class="modal-body">
                                        Bạn có chắc chắn muốn duyệt tour <strong>${requestScope.tour.tourName}</strong> không?
                                    </div>
                                    <div class="modal-footer">
                                        <button type="button" class="btn btn-secondary" data-bs-dismiss="modal">Hủy</button>
                                        <button type="button" class="btn btn-success" onclick="submitAction('approve')">Duyệt</button>
                                    </div>
                                </div>
                            </div>
                        </div>
                        <!-- Reject Modal -->
                        <div class="modal fade" id="rejectModal" tabindex="-1" aria-labelledby="rejectModalLabel" aria-hidden="true">
                            <div class="modal-dialog">
                                <div class="modal-content">
                                    <div class="modal-header">
                                        <h5 class="modal-title" id="rejectModalLabel">Từ chối Tour</h5>
                                        <button type="button" class="btn-close" data-bs-dismiss="modal" aria-label="Close"></button>
                                    </div>
                                    <div class="modal-body">
                                        <p>Vui lòng nhập lý do từ chối tour <strong>${requestScope.tour.tourName}</strong>:</p>
                                        <textarea class="form-control" id="rejectReason" required></textarea>
                                        <small id="reasonError" class="text-danger" style="display:none;">Vui lòng nhập lý do từ chối.</small>
                                    </div>
                                    <div class="modal-footer">
                                        <button type="button" class="btn btn-secondary" data-bs-dismiss="modal">Hủy</button>
                                        <button type="button" class="btn btn-danger" onclick="submitAction('reject')">Từ chối</button>
                                    </div>
                                </div>
                            </div>
                        </div>
                    </div>
                </main>
                <footer class="py-4 bg-light mt-auto">
                    <div class="container-fluid px-4">
                        <div class="d-flex align-items-center justify-content-between small">
                            <div class="text-muted">Copyright © Go Việt</div>
                            <div>
                                <a href="#">Điều khoản</a>
                                ·
                                <a href="#">Terms & Conditions</a>
                            </div>
                        </div>
                    </div>
                </footer>
            </div>
        </div>
        <div id="imageModal" class="modal">
            <span class="close" onclick="closeModal()">×</span>
            <img class="modal-content" id="modalImage">
        </div>
        <script src="https://code.jquery.com/jquery-3.6.0.min.js"></script>
        <script src="https://cdn.jsdelivr.net/npm/bootstrap@5.2.3/dist/js/bootstrap.bundle.min.js" crossorigin="anonymous"></script>
        <script>
            function openModal(src) {
                var modal = document.getElementById("imageModal");
                var modalImg = document.getElementById("modalImage");
                modal.style.display = "flex";
                modalImg.src = src;
            }

            function closeModal() {
                document.getElementById("imageModal").style.display = "none";
            }

            window.onclick = function (event) {
                var modal = document.getElementById("imageModal");
                if (event.target == modal) {
                    modal.style.display = "none";
                }
            }

            function submitAction(action) {
                var url = "${pageContext.request.contextPath}/ManageTourCreate";
                if (action === "reject") {
                    var reason = $("#rejectReason").val().trim();
                    if (reason === "") {
                        $("#reasonError").show();
                        return;
                    } else {
                        $("#reasonError").hide();
                    }
                }
                var data = {
                    service: action,
                    tourID: "${requestScope.tour.tourID}",
                    tourName: "${requestScope.tour.tourName}"
                };
                if (action === "reject") {
                    data.reason = $("#rejectReason").val().trim();
                }
                $.ajax({
                    url: url,
                    type: "POST",
                    data: data,
                    success: function (response) {
                        window.location.href = "${pageContext.request.contextPath}/ManageTourCreate?service=confirm";
                    },
                    error: function (xhr, status, error) {
                        $("#reasonError").text("Có lỗi xảy ra. Vui lòng thử lại sau.").show();
                    }
                });
            }
        </script>
    </body>
</html>