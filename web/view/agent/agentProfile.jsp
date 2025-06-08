<%-- 
 * Copyright (C) 2025, Group 6.
 * ProjectCode/Short Name of Application: TravelSystemService 
 * Support Management and Provide Travel Service System 
 *
 * Record of change:
 * DATE        Version    AUTHOR            DESCRIPTION
 * 2025-06-07  1.0        Quynh Mai          First implementation
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
        <title>Agent Profile</title>
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
        }

        h2, h4 {
            color: #28A745;
            margin-bottom: 15px;
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
            padding: 10px 30px;
        }

        .image-container {
            background-color: white;
            min-width: 300px;
            border-radius: 15px;
            box-shadow: 0 0 5px rgba(0,0,0,0.05);
            padding: 20px;
            display: flex;
            justify-content: center;
            align-items: center;
            flex-direction: column;
        }

        .image-preview {
            max-width: 100%;
            height: 150px; /* Chiều cao cố định cho ảnh */
            object-fit: contain;
            cursor: pointer;
            margin-bottom: 10px;
        }

        .error, .success {
            text-align: center;
            font-size: 14px;
            margin-top: 10px;
        }

        .error {
            color: #842029;
        }

        .success {
            color: #0f5132;
        }

        /* Popup ảnh */
        .modal {
            display: none;
            position: fixed;
            z-index: 1000;
            left: 0;
            top: 0;
            width: 100%;
            height: 100%;
            overflow: auto;
            background-color: rgba(0,0,0,0.8);
            justify-content: center;
            align-items: center;
        }

        .modal-content {
            max-width: 90%;
            max-height: 90%;
            object-fit: contain;
        }

        .close {
            position: absolute;
            top: 20px;
            right: 35px;
            color: white;
            font-size: 40px;
            font-weight: bold;
            cursor: pointer;
        }

        .update-button {
            display: block;
            margin: 20px auto;
            padding: 10px;
            background-color: #28A745;
            color: white;
            text-align: center;
            border-radius: 5px;
            text-decoration: none;
            font-weight: bold;
        }

        .update-button:hover {
            background-color: #218838;
            color: white;
        }
    </style>
    <body>
        <%@include file="../layout/headerAdmin.jsp" %>
        <div id="layoutSidenav">
            <jsp:include page="../layout/sideNavOptionAgent.jsp"></jsp:include>  
                <div id="layoutSidenav_content">
                    <main>                        
                        <div class="container-fluid px-4 container-centered">
                            <br><!-- comment --><br>

                            <!-- Thông tin đại lý và giấy phép kinh doanh -->
                            <div class="row mb-4 justify-content-center">
                                <div class="col-md-6 d-flex">
                                    <div class="custom-table">
                                        <h4 class="text-center">Thông tin đại lý du lịch</h4>
                                        <div class="info-row">
                                            <label>Tên công ty:</label>
                                            <p>${sessionScope.agent.travelAgentName}</p>
                                    </div>
                                    <div class="info-row">
                                        <label>Email:</label>
                                        <p>${sessionScope.agent.travelAgentGmail}</p>
                                    </div>
                                    <div class="info-row">
                                        <label>Số HotLine:</label>
                                        <p>${sessionScope.agent.hotLine}</p>
                                    </div>
                                    <div class="info-row">
                                        <label>Địa chỉ:</label>
                                        <p>${sessionScope.agent.travelAgentAddress}</p>
                                    </div>
                                    <div class="info-row">
                                        <label>Ngày thành lập:</label>
                                        <p>
                                            <fmt:parseDate value="${sessionScope.agent.establishmentDate}" pattern="yyyy-MM-dd" var="parsedDate" />
                                            <fmt:formatDate value="${parsedDate}" pattern="dd/MM/yyyy" />
                                        </p>
                                    </div>
                                    <div class="info-row">
                                        <label>Mã số thuế:</label>
                                        <p>${sessionScope.agent.taxCode}</p>
                                    </div>
                                </div>
                            </div>
                            <div class="col-md-4 d-flex">
                                <div class="image-container">
                                    <label class="text-center">Giấy phép kinh doanh:</label>
                                    <img class="image-preview" src="${pageContext.request.contextPath}/${sessionScope.agent.businessLicense}" alt="Business License" onclick="openModal(this.src)">
                                </div>
                            </div>
                        </div>

                        <!-- Người đại diện và CCCD -->
                        <div class="row mb-4 justify-content-center">
                            <div class="col-md-6 d-flex">
                                <div class="custom-table">
                                    <h4 class="text-center">Người đại diện</h4>
                                    <div class="info-row">
                                        <label>Họ:</label>
                                        <p>${sessionScope.agent.firstName}</p>
                                    </div>
                                    <div class="info-row">
                                        <label>Tên:</label>
                                        <p>${sessionScope.agent.lastName}</p>
                                    </div>
                                    <div class="info-row">
                                        <label>Email:</label>
                                        <p>${sessionScope.agent.gmail != null ? sessionScope.agent.gmail : ''}</p>
                                    </div>
                                    <div class="info-row">
                                        <label>Số điện thoại:</label>
                                        <p>${sessionScope.agent.phone}</p>
                                    </div>
                                    <div class="info-row">
                                        <label>Địa chỉ:</label>
                                        <p>${sessionScope.agent.address}</p>
                                    </div>
                                    <div class="info-row">
                                        <label>Ngày sinh:</label>
                                        <p>
                                            <fmt:parseDate value="${sessionScope.agent.dob}" pattern="yyyy-MM-dd" var="dobDate" />
                                            <fmt:formatDate value="${dobDate}" pattern="dd/MM/yyyy" />
                                    </div>
                                    <div class="info-row">
                                        <label>Giới tính:</label>
                                        <p>${sessionScope.agent.gender != null ? sessionScope.agent.gender : ''}</p>
                                    </div>
                                    <div class="info-row">
                                        <label>Số căn cước công dân:</label>
                                        <p>${sessionScope.agent.representativeIDCard}</p>
                                    </div>
                                    <div class="info-row">
                                        <label>Ngày cấp:</label>
                                        <p>
                                            <fmt:parseDate value="${sessionScope.agent.dateOfIssue}" pattern="yyyy-MM-dd" var="issueDate" />
                                            <fmt:formatDate value="${issueDate}" pattern="dd/MM/yyyy" />
                                        </p>
                                    </div>
                                </div>
                            </div>
                            <div class="col-md-4 d-flex">
                                <div class="image-container">
                                    <label class="text-center">CCCD mặt trước:</label>
                                    <img class="image-preview" src="${pageContext.request.contextPath}/${sessionScope.agent.frontIDCard}" alt="Con" onclick="openModal(this.src)">
                                    <label class="text-center mt-3">CCCD mặt sau:</label>
                                    <img class="image-preview" src="${pageContext.request.contextPath}/${sessionScope.agent.backIDCard}" alt="Back ID Card" onclick="openModal(this.src)">
                                </div>
                            </div>
                        </div>

                        <!-- Thông tin tài khoản -->
                        <div class="row mb-8">
                            <div class="col-md-1"></div>
                            <div class="col-md-9">
                                <div class="custom-table">
                                    <h4 class="text-center">Thông tin tài khoản</h4>
                                    <div class="info-row">
                                        <label>Email:</label>
                                        <p>${sessionScope.agent.gmail != null ? sessionScope.agent.gmail : ''}</p>
                                    </div>
                                    <div class="info-row">
                                        <label>Mật khẩu:</label>
                                        <p>******</p>
                                    </div>
                                </div>
                            </div>
                        </div>

                        <div class="row mb-8">
                            <div class="col-md-1"></div>
                            <div class="col-md-9">
                                <!-- Nút Update thông tin -->
                                <a href="${pageContext.request.contextPath}/ManageTravelAgentProfile?service=updateProfile" class="update-button">Cập nhật thông tin</a>
                            </div>
                        </div>



                        <!-- Modal phóng to ảnh -->
                        <div id="imageModal" class="modal">
                            <span class="close" onclick="closeModal()">×</span>
                            <img class="modal-content" id="modalImage">
                        </div>

                    </div>
                </main>
            </div>
        </div>
        <script src="https://cdn.jsdelivr.net/npm/bootstrap@5.2.3/dist/js/bootstrap.bundle.min.js" crossorigin="anonymous"></script>
        <script src="./assets/js/scripts.js"></script>
        <script src="https://cdnjs.cloudflare.com/ajax/libs/Chart.js/2.8.0/Chart.min.js" crossorigin="anonymous"></script>
        <script src="./assets/demo/chart-area-demo.js"></script>
        <script src="./assets/demo/chart-bar-demo.js"></script>
        <script src="https://cdn.jsdelivr.net/npm/simple-datatables@7.1.2/dist/umd/simple-datatables.min.js" crossorigin="anonymous"></script>
        <script src="./assets/js/datatables-simple-demo.js"></script>
        <script>
                                function openModal(src) {
                                    var modal = document.getElementById("imageModal");
                                    var modalImg = document.getElementById("modalImage");
                                    modal.style.display = "flex";
                                    modalImg.src = src;
                                }

                                function closeModal() {
                                    var modal = document.getElementById("imageModal");
                                    modal.style.display = "none";
                                }

                                window.onclick = function (event) {
                                    var modal = document.getElementById("imageModal");
                                    if (event.target == modal) {
                                        modal.style.display = "none";
                                    }
                                }
        </script>
    </body>
</html>