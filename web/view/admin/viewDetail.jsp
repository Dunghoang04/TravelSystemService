<%--
* Copyright (C) 2025, Group 6.
* ProjectCode/Short Name of Application: TravelAgentService 
* Support Management and Provide Travel Service System 
*
* Record of change:
* DATE        Version    AUTHOR            DESCRIPTION
* 2025-06-25  1.1        Hà Thị Duyên      Redesigned UI for better aesthetics
--%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>

<!DOCTYPE html>
<html lang="en">
    <head>
        <meta charset="utf-8" />
        <meta http-equiv="X-UA-Compatible" content="IE=edge" />
        <meta name="viewport" content="width=device-width, initial-scale=1, shrink-to-fit=no" />
        <meta name="description" content="User Account Detail for TravelSystemService" />
        <meta name="author" content="Group 6" />
        <title>Chi Tiết Tài Khoản - Admin</title>
        <!-- Bootstrap CSS -->
        <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.0/dist/css/bootstrap.min.css" rel="stylesheet" />
        <!-- Font Awesome -->
        <link href="https://cdnjs.cloudflare.com/ajax/libs/font-awesome/6.4.0/css/all.min.css" rel="stylesheet" />
        <!-- Google Fonts -->
        <link href="${pageContext.request.contextPath}/assets/css/styles2.css" rel="stylesheet" />

        <link href="https://fonts.googleapis.com/css2?family=Roboto:wght@300;400;700&display=swap" rel="stylesheet" />
        <!-- Custom Styles -->
        <style>
            body {
                font-family: 'Roboto', sans-serif;
                background: linear-gradient(135deg, #f5f7fa 0%, #c3cfe2 100%);
                min-height: 100vh;
                display: flex;
                flex-direction: column;
            }
            .container-fluid {
                max-width: 1200px;
                margin: 0 auto;
            }
            .card {
                border: none;
                border-radius: 15px;
                box-shadow: 0 4px 20px rgba(0, 0, 0, 0.1);
                overflow: hidden;
                transition: transform 0.3s ease;
                margin-bottom: 30px;
            }
            .card:hover {
                transform: translateY(-5px);
            }
            .card-header {
                background: linear-gradient(to right, #007bff, #00d4ff);
                color: white;
                font-weight: 700;
                padding: 15px 20px;
                border-radius: 15px 15px 0 0;
                display: flex;
                align-items: center;
                gap: 10px;
            }
            .detail-section {
                padding: 20px;
                margin-bottom: 20px;
                border-radius: 10px;
                background-color: #fff;
                box-shadow: 0 2px 10px rgba(0, 0, 0, 0.05);
            }
            .detail-section h4 {
                color: #007bff;
                font-weight: 700;
                margin-bottom: 20px;
                border-bottom: 2px solid #007bff;
                padding-bottom: 10px;
            }
            .info-label {
                font-weight: 600;
                color: #333;
                width: 150px;
                display: inline-block;
            }
            .info-value {
                color: #555;
            }
            .image-preview {
                max-width: 100%;
                height: auto;
                border-radius: 8px;
                border: 1px solid #ddd;
                margin-top: 10px;
            }
            .btn-back {
                background: #555;
                color: white;
                font-weight: 600;
                border: none;
                padding: 10px 20px;
                border-radius: 8px;
                transition: background 0.3s ease;
            }
            .btn-back:hover {
                background: #555;
            }
            footer {
                background: #fff;
                padding: 20px 0;
                text-align: center;
                color: #555;
                border-top: 1px solid #ddd;
                margin-top: auto;
            }
            @media (max-width: 768px) {
                .info-label {
                    width: 100%;
                    display: block;
                    margin-bottom: 5px;
                }
                .info-value {
                    display: block;
                }
            }
        </style>
    </head>
    <body>
        <%@include file="../layout/headerAdmin.jsp" %>
        <div id="layoutSidenav">
            <%@include file="../layout/sideNavOptionAdmin.jsp" %>
            <div id="layoutSidenav_content">
                <main>
                    <div class="container-fluid px-4 py-5">
                        <h1 class="mb-4 text-center" style="color: #007bff;">Chi Tiết Tài Khoản</h1>
                        <div class="card">
                            <div class="card-header">
                                <i class="fas fa-user fa-lg"></i>
                                Thông Tin Người Dùng
                            </div>
                            <div class="card-body">
                                <!-- General Information -->
                                <div class="detail-section">
                                    <h4>Thông Tin Chung</h4>
                                    <div class="row g-3">
                                        <div class="col-md-6">
                                            <span class="info-label">Email:</span>
                                            <span class="info-value">${user.gmail}</span>
                                        </div>
                                        <div class="col-md-6">
                                            <span class="info-label">Vai Trò:</span>
                                            <span class="info-value">
                                                <c:choose>
                                                    <c:when test="${user.roleID == 1}">Quản trị viên</c:when>
                                                    <c:when test="${user.roleID == 2}">Nhân viên</c:when>
                                                    <c:when test="${user.roleID == 3}">Khách du lịch</c:when>
                                                    <c:when test="${user.roleID == 4}">Đại lý</c:when>
                                                    <c:otherwise>Không xác định</c:otherwise>
                                                </c:choose>
                                            </span>
                                        </div>
                                        <div class="col-md-6">
                                            <span class="info-label">Họ:</span>
                                            <span class="info-value">${user.lastName}</span>
                                        </div>
                                        <div class="col-md-6">
                                            <span class="info-label">Tên:</span>
                                            <span class="info-value">${user.firstName}</span>
                                        </div>
                                        <div class="col-md-6">
                                            <span class="info-label">Ngày Sinh:</span>
                                            <span class="info-value"><fmt:formatDate value="${user.dob}" pattern="dd/MM/yyyy"/></span>
                                        </div>
                                        <div class="col-md-6">
                                            <span class="info-label">Giới Tính:</span>
                                            <span class="info-value">${user.gender}</span>
                                        </div>
                                        <div class="col-md-6">
                                            <span class="info-label">Địa Chỉ:</span>
                                            <span class="info-value">${user.address}</span>
                                        </div>
                                        <div class="col-md-6">
                                            <span class="info-label">Số Điện Thoại:</span>
                                            <span class="info-value">${user.phone}</span>
                                        </div>
                                        <div class="col-md-6">
                                            <span class="info-label">Ngày Tạo:</span>
                                            <span class="info-value"><fmt:formatDate value="${user.createDate}" pattern="dd/MM/yyyy"/></span>
                                        </div>
                                        <div class="col-md-6">
                                            <span class="info-label">Ngày Cập Nhật:</span>
                                            <span class="info-value"><fmt:formatDate value="${user.updateDate}" pattern="dd/MM/yyyy"/></span>
                                        </div>
                                        <div class="col-md-6">
                                            <span class="info-label">Trạng Thái:</span>
                                            <span class="info-value">
                                                <c:choose>
                                                    <c:when test="${user.status == 1}">Hoạt động</c:when>
                                                    <c:when test="${user.status == 0}">Không hoạt động</c:when>
                                                    <c:when test="${user.status == 2 && user.roleID == 4}">Đang chờ duyệt</c:when>
                                                    <c:when test="${user.status == 3 && user.roleID == 4}">Từ chối</c:when>
                                                    <c:otherwise>Không xác định</c:otherwise>
                                                </c:choose>
                                            </span>
                                        </div>
                                    </div>
                                </div>

                                <!-- Staff Information (if roleID == 2) -->
                                <c:if test="${user.roleID == 2}">
                                    <div class="detail-section">
                                        <h4>Thông Tin Nhân Viên</h4>
                                        <div class="row g-3">
                                            <div class="col-md-6">
                                                <span class="info-label">Mã Nhân Viên:</span>
                                                <span class="info-value">${staff.employeeCode}</span>
                                            </div>
                                            <div class="col-md-6">
                                                <span class="info-label">Ngày Tuyển Dụng:</span>
                                                <span class="info-value"><fmt:formatDate value="${staff.hireDate}" pattern="dd/MM/yyyy"/></span>
                                            </div>
                                            <div class="col-md-6">
                                                <span class="info-label">Trạng Thái Công Việc:</span>
                                                <span class="info-value">${staff.workStatus}</span>
                                            </div>
                                        </div>
                                    </div>
                                </c:if>

                                <!-- Travel Agent Information (if roleID == 4) -->
                                <c:if test="${user.roleID == 4}">
                                    <div class="detail-section">
                                        <h4>Thông Tin Đại Lý</h4>
                                        <div class="row g-3">
                                            <div class="col-md-6">
                                                <span class="info-label">Tên Đại Lý:</span>
                                                <span class="info-value">${travelAgent.travelAgentName}</span>
                                            </div>
                                            <div class="col-md-6">
                                                <span class="info-label">Địa Chỉ Đại Lý:</span>
                                                <span class="info-value">${travelAgent.travelAgentAddress}</span>
                                            </div>
                                            <div class="col-md-6">
                                                <span class="info-label">Email Đại Lý:</span>
                                                <span class="info-value">${travelAgent.travelAgentGmail}</span>
                                            </div>
                                            <div class="col-md-6">
                                                <span class="info-label">Hotline:</span>
                                                <span class="info-value">${travelAgent.hotLine}</span>
                                            </div>
                                            <div class="col-md-6">
                                                <span class="info-label">Mã Số Thuế:</span>
                                                <span class="info-value">${travelAgent.taxCode}</span>
                                            </div>
                                            <div class="col-md-6">
                                                <span class="info-label">Ngày Thành Lập:</span>
                                                <span class="info-value"><fmt:formatDate value="${travelAgent.establishmentDate}" pattern="dd/MM/yyyy"/></span>
                                            </div>
                                            <div class="col-md-6">
                                                <span class="info-label">CMND/CCCD Đại Diện:</span>
                                                <span class="info-value">${travelAgent.representativeIDCard}</span>
                                            </div>
                                            <div class="col-md-6">
                                                <span class="info-label">Ngày Cấp CMND/CCCD:</span>
                                                <span class="info-value"><fmt:formatDate value="${travelAgent.dateOfIssue}" pattern="dd/MM/yyyy"/></span>
                                            </div>
                                            <div class="col-md-4">
                                                <span class="info-label">Giấy Phép Kinh Doanh:</span>
                                                <c:if test="${not empty travelAgent.businessLicense}">
                                                    <img src="${travelAgent.businessLicense}" alt="Giấy phép kinh doanh" class="image-preview" />
                                                </c:if>
                                            </div>
                                            <div class="col-md-4">
                                                <span class="info-label">CMND/CCCD Mặt Trước:</span>
                                                <c:if test="${not empty travelAgent.frontIDCard}">
                                                    <img src="${travelAgent.frontIDCard}" alt="Mặt trước CMND/CCCD" class="image-preview" />
                                                </c:if>
                                            </div>
                                            <div class="col-md-4">
                                                <span class="info-label">CMND/CCCD Mặt Sau:</span>
                                                <c:if test="${not empty travelAgent.backIDCard}">
                                                    <img src="${travelAgent.backIDCard}" alt="Mặt sau CMND/CCCD" class="image-preview" />
                                                </c:if>
                                            </div>
                                        </div>
                                    </div>
                                </c:if>

                                <!-- Back Button -->
                                <div class="text-center mt-4">
                                    <a href="${pageContext.request.contextPath}/ManagementAccount" class="btn btn-back">Quay Lại</a>
                                </div>
                            </div>
                        </div>
                    </div>
                </main>
                <footer>
                    <div class="container-fluid px-4">
                        <div class="text-muted text-center">Copyright © TravelSystemService 2025</div>
                    </div>
                </footer>
            </div>
        </div>
        <!-- JavaScript -->
        <script src="https://cdn.jsdelivr.net/npm/bootstrap@5.3.0/dist/js/bootstrap.bundle.min.js"></script>
        <script src="https://code.jquery.com/jquery-3.6.0.min.js"></script>
        <script src="${pageContext.request.contextPath}/assets/js/scripts.js"></script>
    </body>
</html>