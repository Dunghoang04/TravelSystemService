<%--
 * Copyright (C) 2025, Group 6.
 * ProjectCode/Short Name of Application: TravelSystemService 
 * Support Management and Provide Travel Service System 
 *
 * Record of change:
 * DATE        Version    AUTHOR            DESCRIPTION
 * 2025-06-20  1.0        Quynh Mai         First implementation based on agent profile requirements
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
        <meta name="description" content="" />
        <meta name="author" content="" />
        <title>Profile Travel Agent</title>

        <script src="https://use.fontawesome.com/releases/v6.3.0/js/all.js" crossorigin="anonymous"></script>
        <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.2.3/dist/css/bootstrap.min.css" rel="stylesheet" crossorigin="anonymous">
        <link href="https://cdn.jsdelivr.net/npm/simple-datatables@7.1.2/dist/style.min.css" rel="stylesheet" />
        <link href="./assets/css/styles2.css" rel="stylesheet" />
        <script src="https://use.fontawesome.com/releases/v6.3.0/js/all.js" crossorigin="anonymous"></script>
    </head>
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

        label {
            font-weight: bold;
            font-size: 14px;
            margin-bottom: 0;
            color: #212529;
            width: 150px;
        }

        p {
            margin: 0;
            font-size: 14px;
        }

        .info-row {
            display: flex;
            align-items: center;
            margin-bottom: 10px;
        }

        .custom-table {
            width: 100%;
            background-color: white;
            border-radius: 15px;
            box-shadow: 0 0 5px rgba(0,0,0,0.05);
            padding: 20px 30px;
        }

        .image-container {
            background-color: white;
            border-radius: 15px;
            box-shadow: 0 0 5px rgba(0,0,0,0.05);
            padding: 20px;
            display: flex;
            justify-content: center;
            align-items: center;
            margin-top: 20px;
        }

        .image-preview {
            max-width: 200px;
            height: 200px;
            object-fit: contain;
            cursor: pointer;
            margin-bottom: 10px;
        }

        .action-button {
            display: inline-block;
            margin: 10px 5px;
            padding: 10px 20px;
            background-color: #28A745;
            color: white;
            text-align: center;
            border-radius: 10px;
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

        .action-button,
        .action-button.reject,
        .btn-success,
        .btn-danger,
        .btn-primary {
            border: none !important;
            outline: none !important;
            box-shadow: none !important;
        }

        .error-message {
            color: red;
            font-size: 16px;
            text-align: center;
            margin-bottom: 20px;
        }
    </style>
    <body>
        <%@include file="../layout/headerAdmin.jsp" %>
        <div id="layoutSidenav">
            <jsp:include page="../layout/sideNavOptionStaff.jsp"></jsp:include>  
                <div id="layoutSidenav_content">
                    <main>                        
                        <div class="container-fluid px-4 container-centered">
                            <h1>Thông tin đại lý</h1>
                            <c:if test="${not empty requestScope.errorMessage}">
                                <div class="error-message">${requestScope.errorMessage}</div>
                            </c:if>
                            <c:if test="${requestScope.agent.status == 3}">
                                <div style="color: red; font-size: 20px; text-align: center; margin-bottom: 20px">
                                    Lý do từ chối: ${requestScope.agent.reason}
                                </div>
                            </c:if>
                            <div class="row">
                                <div class="col-md-6">
                                    <div class="custom-table">
                                        <h4 class="text-center">Thông tin đại lý du lịch</h4>
                                        <div class="info-row">
                                            <label>Tên công ty:</label>
                                            <p>${requestScope.agent.travelAgentName}</p>
                                        </div>
                                        <div class="info-row">
                                            <label>Email:</label>
                                            <p>${requestScope.agent.travelAgentGmail}</p>
                                        </div>
                                        <div class="info-row">
                                            <label>Số HotLine:</label>
                                            <p>${requestScope.agent.hotLine}</p>
                                        </div>
                                        <div class="info-row">
                                            <label>Địa chỉ:</label>
                                            <p>${requestScope.agent.travelAgentAddress}</p>
                                        </div>
                                        <div class="info-row">
                                            <label>Ngày thành lập:</label>
                                            <p>
                                                <fmt:parseDate value="${requestScope.agent.establishmentDate}" pattern="yyyy-MM-dd" var="parsedDate" />
                                                <fmt:formatDate value="${parsedDate}" pattern="dd/MM/yyyy" />
                                            </p>
                                        </div>
                                        <div class="info-row">
                                            <label>Mã số thuế:</label>
                                            <p>${requestScope.agent.taxCode}</p>
                                        </div>
                                        <h4 class="text-center mt-3">Thông tin tài khoản</h4>
                                        <div class="info-row">
                                            <label>Email tài khoản:</label>
                                            <p>${requestScope.agent.gmail != null ? requestScope.agent.gmail : ''}</p>
                                        </div>
                                        <div class="info-row">
                                            <label>Mật khẩu:</label>
                                            <p>*******</p>
                                        </div>
                                    </div>
                                </div>
                                <div class="col-md-6">
                                    <div class="custom-table">
                                        <h4 class="text-center">Thông tin người đại diện</h4>
                                        <div class="info-row">
                                            <label>Họ:</label>
                                            <p>${requestScope.agent.firstName}</p>
                                        </div>
                                        <div class="info-row">
                                            <label>Tên:</label>
                                            <p>${requestScope.agent.lastName}</p>
                                        </div>
                                        <div class="info-row">
                                            <label>Số điện thoại:</label>
                                            <p>${requestScope.agent.phone}</p>
                                        </div>
                                        <div class="info-row">
                                            <label>Địa chỉ:</label>
                                            <p>${requestScope.agent.address}</p>
                                        </div>
                                        <div class="info-row">
                                            <label>Ngày sinh:</label>
                                            <p>
                                                <fmt:parseDate value="${requestScope.agent.dob}" pattern="yyyy-MM-dd" var="dobDate" />
                                                <fmt:formatDate value="${dobDate}" pattern="dd/MM/yyyy" />
                                            </p>
                                        </div>
                                        <div class="info-row">
                                            <label>Giới tính:</label>
                                            <p>${requestScope.agent.gender != null ? requestScope.agent.gender : ''}</p>
                                        </div>
                                        <div class="info-row">
                                            <label>Số căn cước công dân:</label>
                                            <p>${requestScope.agent.representativeIDCard}</p>
                                        </div>
                                        <div class="info-row">
                                            <label>Ngày cấp:</label>
                                            <p>
                                                <fmt:parseDate value="${requestScope.agent.dateOfIssue}" pattern="yyyy-MM-dd" var="issueDate" />
                                                <fmt:formatDate value="${issueDate}" pattern="dd/MM/yyyy" />
                                            </p>
                                        </div>
                                    </div>
                                </div>
                            </div>

                            <!-- Hình ảnh -->
                            <div class="image-container row">
                                <div class="col-4">
                                    <div>Giấy phép kinh doanh:</div>
                                    <img class="image-preview" src="${pageContext.request.contextPath}/${requestScope.agent.businessLicense}" alt="Business License" onclick="openModal(this.src)">
                                </div>
                                <div class="col-4">
                                    <div>CCCD mặt trước:</div>
                                    <img class="image-preview" src="${pageContext.request.contextPath}/${requestScope.agent.frontIDCard}" alt="Front ID Card" onclick="openModal(this.src)">
                                </div>
                                <div class="col-4">
                                    <div>CCCD mặt sau:</div>
                                    <img class="image-preview" src="${pageContext.request.contextPath}/${requestScope.agent.backIDCard}" alt="Back ID Card" onclick="openModal(this.src)">
                                </div>
                            </div>

                            <!-- Nút Duyệt, Từ chối và Quay lại -->
                            <div class="text-center mt-4">
                                <a href="javascript:history.back()" class="action-button" style="background-color: #0d6efd">Quay lại</a>
                                <c:if test="${requestScope.agent.status == 2}">
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
                                            Bạn có chắc chắn muốn duyệt đại lý <strong>${requestScope.agent.travelAgentName}</strong> không?
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
                                            <h5 class="modal-title" id="rejectModalLabel">Từ chối đại lý</h5>
                                            <button type="button" class="btn-close" data-bs-dismiss="modal" aria-label="Close"></button>
                                        </div>
                                        <div class="modal-body">
                                            <p>Vui lòng nhập lý do từ chối đại lý <strong>${requestScope.agent.travelAgentName}</strong>:</p>
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

            <!-- Modal phóng to ảnh -->
            <div id="imageModal" class="modal">
                <span class="close" onclick="closeModal()">×</span>
                <img class="modal-content" id="modalImage">
            </div>

            <script src="https://cdn.jsdelivr.net/npm/bootstrap@5.2.3/dist/js/bootstrap.bundle.min.js" crossorigin="anonymous"></script>
            <script src="./assets/js/scripts.js"></script>
            <script src="https://cdnjs.cloudflare.com/ajax/libs/Chart.js/2.8.0/Chart.min.js" crossorigin="anonymous"></script>
            <script src="./assets/demo/chart-area-demo.js"></script>
            <script src="./assets/demo/chart-bar-demo.js"></script>
            <script src="https://cdn.jsdelivr.net/npm/simple-datatables@7.1.2/dist/umd/simple-datatables.min.js" crossorigin="anonymous"></script>
            <script src="./assets/js/datatables-simple-demo.js"></script>
            <script src="https://code.jquery.com/jquery-3.6.0.min.js"></script>
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
                    var url = "${pageContext.request.contextPath}/ManageTravelAgentRegister";

                    if (action === "reject") {
                        var reason = $("#rejectReason").val().trim();

                        // Kiểm tra và hiển thị lỗi ngay dưới form
                        if (reason === "") {
                            $("#reasonError").show(); // hiện lỗi
                            return; // dừng lại
                        } else {
                            $("#reasonError").hide(); // ẩn lỗi nếu có nội dung
                        }
                    }

                    var data = {
                        service: action,
                        travelAgentID: "${requestScope.agent.travelAgentID}",
                        userID: "${requestScope.agent.userID}",
                        travelAgentName: "${requestScope.agent.travelAgentName}",
                        email: "${requestScope.agent.travelAgentGmail}"
                    };

                    if (action === "reject") {
                        data.reason = $("#rejectReason").val().trim();
                    }

                    $.ajax({
                        url: url,
                        type: "POST",
                        data: data,
                        success: function (response) {
                            window.location.href = "${pageContext.request.contextPath}/ManageTravelAgentRegister?service=confirm";
                        },
                        error: function (xhr, status, error) {
                            $("#reasonError").text("Có lỗi xảy ra. Vui lòng thử lại sau.").show();
                        }
                    });
                }
            </script>
        </body>
    </html>