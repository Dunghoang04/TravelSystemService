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
<!DOCTYPE html>
<html>
    <head>
        <meta charset="utf-8" />
        <meta http-equiv="X-UA-Compatible" content="IE=edge" />
        <meta name="viewport" content="width=device-width, initial-scale=1, shrink-to-fit=no" />
        <meta name="description" content="" />
        <meta name="author" content="" />
        <title>Edit Representative Profile</title>
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
            padding-top: 20px;
            border-right: 1px solid #dee2e6;
        }

        .sidebar .nav-link {
            color: #333;
            padding: 10px 15px;
            font-size: 16px;
        }

        .sidebar .nav-link:hover {
            background-color: #e9ecef;
            color: #28A745;
        }

        .sidebar .nav-link.active {
            background-color: #28A745;
            color: white;
        }

        .content {
            margin-left: 100px;
            padding: 20px;
            flex: 1;
        }

        .container-centered {
            max-width: 1200px;
            margin: 0 auto;
        }

        h4 {
            color: #28A745;
            font-size: 24px;
            margin-bottom: 20px;
            text-align: center;
        }

        .form-label {
            font-weight: bold;
            font-size: 14px;
            color: #212529;
        }

        .image-preview {
            max-width: 150px;
            max-height: 150px;
            object-fit: contain;
            margin-top: 10px;
            cursor: pointer;
        }

        .error {
            color: #842029;
            font-size: 14px;
            margin-top: 5px;
            text-align: left;
        }

        .required {
            color: red;
            margin-left: 2px;
        }

        .custom-form {
            background-color: white;
            border-radius: 15px;
            box-shadow: 0 0 5px rgba(0,0,0,0.05);
            padding: 20px;
        }
        
        .btn1 {
            width: 95%;
            padding: 12px;
            background: #28A745;
            border: none;
            border-radius: 6px;
            color: white;
            font-size: 18px;
            font-weight: bold;
            cursor: pointer;
            transition: 0.3s;
            margin-bottom: 20px;
            height: 50px;
        }
    </style>
    <body>
        <%@include file="../layout/headerAdmin.jsp" %>
        <div id="layoutSidenav">
            <jsp:include page="../layout/sideNavOptionAgent.jsp"></jsp:include>  
            <div id="layoutSidenav_content">
                <main class="content">
                    <div class="container-fluid container-centered">
                        <div class="custom-form">
                            <c:if test="${not empty requestScope.successRe}">
                                <div class="success text-center">${requestScope.successRe}</div>
                            </c:if>
                            <form id="editRepresentativeForm" action="${pageContext.request.contextPath}/ManageTravelAgentProfile" method="POST" enctype="multipart/form-data" class="row g-3">
                                <input type="hidden" name="service" value="saveRepresentative">
                                <h4>Thông tin người đại diện</h4>
                                <div class="mb-4">
                                    <div class="row g-3">
                                        <div class="col-md-4">
                                            <div class="mb-3">
                                                <label for="firstName" class="form-label">Họ:<span class="required">*</span></label>
                                                <input type="text" class="form-control" id="firstName" name="firstName" value="${not empty requestScope.agent.firstName ? requestScope.agent.firstName : sessionScope.agent.firstName}" required>
                                                <c:if test="${not empty requestScope.fieldErrors['firstName']}">
                                                    <div class="error">${requestScope.fieldErrors['firstName']}</div>
                                                </c:if>
                                            </div>
                                        </div>
                                        <div class="col-md-4">
                                            <div class="mb-3">
                                                <label for="lastName" class="form-label">Tên:<span class="required">*</span></label>
                                                <input type="text" class="form-control" id="lastName" name="lastName" value="${not empty requestScope.agent.lastName ? requestScope.agent.lastName : sessionScope.agent.lastName}" required>
                                                <c:if test="${not empty requestScope.fieldErrors['lastName']}">
                                                    <div class="error">${requestScope.fieldErrors['lastName']}</div>
                                                </c:if>
                                            </div>
                                        </div>
                                        <div class="col-md-4">
                                            <div class="mb-3">
                                                <label for="phone" class="form-label">Số điện thoại:<span class="required">*</span></label>
                                                <input type="text" class="form-control" id="phone" name="phone" value="${not empty requestScope.agent.phone ? requestScope.agent.phone : sessionScope.agent.phone}"required>
                                                <c:if test="${not empty requestScope.fieldErrors['phone']}">
                                                    <div class="error">${requestScope.fieldErrors['phone']}</div>
                                                </c:if>
                                            </div>
                                        </div>
                                    </div>
                                    <div class="row g-3">
                                        <div class="col-md-8">
                                            <div class="mb-3">
                                                <label for="address" class="form-label">Địa chỉ:<span class="required">*</span></label>
                                                <input type="text" class="form-control" id="address" name="address" value="${not empty requestScope.agent.address ? requestScope.agent.address : sessionScope.agent.address}" required>
                                                <c:if test="${not empty requestScope.fieldErrors['address']}">
                                                    <div class="error">${requestScope.fieldErrors['address']}</div>
                                                </c:if>
                                            </div>
                                        </div>
                                        <div class="col-md-4">
                                            <div class="mb-3">
                                                <label for="dob" class="form-label">Ngày sinh:<span class="required">*</span></label>
                                                <input type="date" class="form-control" id="dob" name="dob" value="${not empty requestScope.agent.dob ? requestScope.agent.dob : sessionScope.agent.dob}" required>
                                                <c:if test="${not empty requestScope.fieldErrors['dob']}">
                                                    <div class="error">${requestScope.fieldErrors['dob']}</div>
                                                </c:if>
                                            </div>
                                        </div>
                                    </div>
                                    <div class="row g-3">
                                        <div class="col-md-4">
                                            <div class="mb-3">
                                                <label class="form-label">Giới tính:<span class="required">*</span></label>
                                                <select class="form-control" id="gender" name="gender" required>
                                                    <option value="Nam" ${requestScope.agent.gender == 'Nam' || (empty requestScope.agent.gender && sessionScope.agent.gender == 'Nam') ? 'selected' : ''}>Nam</option>
                                                    <option value="Nữ" ${requestScope.agent.gender == 'Nữ' || (empty requestScope.agent.gender && sessionScope.agent.gender == 'Nữ') ? 'selected' : ''}>Nữ</option>
                                                </select>
                                                <c:if test="${not empty requestScope.fieldErrors['gender']}">
                                                    <div class="error">${requestScope.fieldErrors['gender']}</div>
                                                </c:if>
                                            </div>
                                        </div>
                                        <div class="col-md-4">
                                            <div class="mb-3">
                                                <label for="representativeIDCard" class="form-label">CCCD:<span class="required">*</span></label>
                                                <input type="text" class="form-control" id="representativeIDCard" name="representativeIDCard" value="${not empty requestScope.agent.representativeIDCard ? requestScope.agent.representativeIDCard : sessionScope.agent.representativeIDCard}" required>
                                                <c:if test="${not empty requestScope.fieldErrors['representativeIDCard']}">
                                                    <div class="error">${requestScope.fieldErrors['representativeIDCard']}</div>
                                                </c:if>
                                            </div>
                                        </div>
                                        <div class="col-md-4">
                                            <div class="mb-3">
                                                <label for="dateOfIssue" class="form-label">Ngày cấp:<span class="required">*</span></label>
                                                <input type="date" class="form-control" id="dateOfIssue" name="dateOfIssue" value="${not empty requestScope.agent.dateOfIssue ? requestScope.agent.dateOfIssue : sessionScope.agent.dateOfIssue}" required>
                                                <c:if test="${not empty requestScope.fieldErrors['dateOfIssue']}">
                                                    <div class="error">${requestScope.fieldErrors['dateOfIssue']}</div>
                                                </c:if>
                                            </div>
                                        </div>
                                    </div>
                                    <div class="row g-3">
                                        <div class="col-md-6">
                                            <div class="mb-3">
                                                <label for="frontIDCard" class="form-label">CCCD mặt trước:<span class="required">*</span></label>
                                                <input type="file" class="form-control" id="frontIDCard" name="frontIDCard" accept="image/*">
                                                <c:if test="${not empty sessionScope.agent.frontIDCard}">
                                                    <img class="image-preview" src="${pageContext.request.contextPath}/${sessionScope.agent.frontIDCard}" alt="Front ID Card">
                                                </c:if>
                                                <c:if test="${not empty requestScope.fieldErrors['frontIDCard']}">
                                                    <div class="error">${requestScope.fieldErrors['frontIDCard']}</div>
                                                </c:if>
                                            </div>
                                        </div>
                                        <div class="col-md-6">
                                            <div class="mb-3">
                                                <label for="backIDCard" class="form-label">CCCD mặt sau:<span class="required">*</span></label>
                                                <input type="file" class="form-control" id="backIDCard" name="backIDCard" accept="image/*">
                                                <c:if test="${not empty sessionScope.agent.backIDCard}">
                                                    <img class="image-preview" src="${pageContext.request.contextPath}/${sessionScope.agent.backIDCard}" alt="Back ID Card">
                                                </c:if>
                                                <c:if test="${not empty requestScope.fieldErrors['backIDCard']}">
                                                    <div class="error">${requestScope.fieldErrors['backIDCard']}</div>
                                                </c:if>
                                            </div>
                                        </div>
                                    </div>
                                </div>
                                <div class="row g-3">
                                    <div class="col-md-6">
                                        <a href="${pageContext.request.contextPath}/ManageTravelAgentProfile?service=viewProfile" class="btn btn1 btn-success w-100">Quay lại</a>
                                    </div>
                                    <div class="col-md-6">
                                        <button type="submit" class="btn btn1 btn-success w-100">Lưu thông tin</button>
                                    </div>
                                </div>
                            </form>
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
        <script src="https://cdn.jsdelivr.net/npm/bootstrap@5.2.3/dist/js/bootstrap.bundle.min.js" crossorigin="anonymous"></script>
        <script src="./assets/js/scripts.js"></script>
        <script src="https://cdnjs.cloudflare.com/ajax/libs/Chart.js/2.8.0/Chart.min.js" crossorigin="anonymous"></script>
        <script src="./assets/demo/chart-area-demo.js"></script>
        <script src="./assets/demo/chart-bar-demo.js"></script>
        <script src="https://cdn.jsdelivr.net/npm/simple-datatables@7.1.2/dist/umd/simple-datatables.min.js" crossorigin="anonymous"></script>
        <script src="./assets/js/datatables-simple-demo.js"></script>
    </body>
</html>