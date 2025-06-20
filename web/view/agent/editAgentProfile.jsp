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
        <title>Edit Agent Profile</title>
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

        /* Modal Styles */
        .modal-content {
            border-radius: 10px;
        }

        .modal-img {
            max-width: 100%;
            height: auto;
            display: block;
            margin: 0 auto;
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
                            <c:if test="${not empty requestScope.successAgent}">
                                <div class="success text-center">${requestScope.successAgent}</div>
                            </c:if>
                            <form id="editAgentForm" action="${pageContext.request.contextPath}/ManageTravelAgentProfile" method="POST" enctype="multipart/form-data" class="row g-3">
                                <input type="hidden" name="service" value="saveAgent">
                                <h4>Thông tin đại lý du lịch</h4>
                                <div class="mb-4">
                                    <div class="row g-3">
                                        <div class="col-md-8">
                                            <div class="mb-3">
                                                <label for="travelAgentName" class="form-label">Tên công ty:<span class="required">*</span></label>
                                                <input type="text" class="form-control" id="travelAgentName" name="travelAgentName" value="${not empty requestScope.agent.travelAgentName ? requestScope.agent.travelAgentName : sessionScope.agent.travelAgentName}" required>
                                                <c:if test="${not empty requestScope.fieldErrors.travelAgentName}">
                                                    <div class="error">${requestScope.fieldErrors.travelAgentName}</div>
                                                </c:if>
                                            </div>
                                        </div>
                                        <div class="col-md-4">
                                            <div class="mb-3">
                                                <label for="travelAgentEmail" class="form-label">Email:<span class="required">*</span></label>
                                                <input type="text" class="form-control" id="travelAgentEmail" name="travelAgentEmail" value="${not empty requestScope.agent.travelAgentGmail ? requestScope.agent.travelAgentGmail : sessionScope.agent.travelAgentGmail}" required>
                                                <c:if test="${not empty requestScope.fieldErrors.travelAgentEmail}">
                                                    <div class="error">${requestScope.fieldErrors.travelAgentEmail}</div>
                                                </c:if>
                                            </div>
                                        </div>
                                    </div>
                                    <div class="row g-3">
                                        <div class="col-md-4">
                                            <div class="mb-3">
                                                <label for="hotLine" class="form-label">Số HotLine:<span class="required">*</span></label>
                                                <input type="tel" class="form-control" id="hotLine" name="hotLine" value="${not empty requestScope.agent.hotLine ? requestScope.agent.hotLine : sessionScope.agent.hotLine}">
                                                <c:if test="${not empty requestScope.fieldErrors.hotLine}">
                                                    <div class="error">${requestScope.fieldErrors.hotLine}</div>
                                                </c:if>
                                            </div>
                                        </div>
                                        <div class="col-md-8">
                                            <div class="mb-3">
                                                <label for="travelAgentAddress" class="form-label">Địa chỉ:<span class="required">*</span></label>
                                                <input type="text" class="form-control" id="travelAgentAddress" name="travelAgentAddress" value="${not empty requestScope.agent.travelAgentAddress ? requestScope.agent.travelAgentAddress : sessionScope.agent.travelAgentAddress}" required>
                                                <c:if test="${not empty requestScope.fieldErrors.travelAgentAddress}">
                                                    <div class="error">${requestScope.fieldErrors.travelAgentAddress}</div>
                                                </c:if>
                                            </div>
                                        </div>
                                    </div>
                                    <div class="row g-3">
                                        <div class="col-md-4">
                                            <div class="mb-3">
                                                <label for="businessLicense" class="form-label">Giấy phép kinh doanh:<span class="required">*</span></label>
                                                <input type="file" class="form-control" id="businessLicense" name="businessLicense" accept="image/*">
                                                <c:if test="${not empty sessionScope.agent.businessLicense}">
                                                    <img class="image-preview" src="${pageContext.request.contextPath}/${sessionScope.agent.businessLicense}" alt="Business License" data-bs-toggle="modal" data-bs-target="#imageModal" data-img-src="${pageContext.request.contextPath}/${sessionScope.agent.businessLicense}">
                                                </c:if>
                                                <c:if test="${not empty requestScope.fieldErrors.businessLicense}">
                                                    <div class="error">${requestScope.fieldErrors.businessLicense}</div>
                                                </c:if>
                                            </div>
                                        </div>
                                        <div class="col-md-4">
                                            <div class="mb-3">
                                                <label for="establishmentDate" class="form-label">Ngày thành lập:<span class="required">*</span></label>
                                                <input type="date" class="form-control" id="establishmentDate" name="establishmentDate" value="${not empty requestScope.agent.establishmentDate ? requestScope.agent.establishmentDate : sessionScope.agent.establishmentDate}" required>
                                                <c:if test="${not empty requestScope.fieldErrors.establishmentDate}">
                                                    <div class="error">${requestScope.fieldErrors.establishmentDate}</div>
                                                </c:if>
                                            </div>
                                        </div>
                                        <div class="col-md-4">
                                            <div class="mb-3">
                                                <label for="taxCode" class="form-label">Mã số thuế:<span class="required">*</span></label>
                                                <input type="text" class="form-control" id="taxCode" name="taxCode" value="${not empty requestScope.agent.taxCode ? requestScope.agent.taxCode : sessionScope.agent.taxCode}" required>
                                                <c:if test="${not empty requestScope.fieldErrors.taxCode}">
                                                    <div class="error">${requestScope.fieldErrors.taxCode}</div>
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

                    <!-- Image Preview Modal -->
                    <div class="modal fade" id="imageModal" tabindex="-1" aria-labelledby="imageModalLabel" aria-hidden="true">
                        <div class="modal-dialog modal-lg">
                            <div class="modal-content">
                                <div class="modal-header">
                                    <h5 class="modal-title" id="imageModalLabel">Image Preview</h5>
                                    <button type="button" class="btn-close" data-bs-dismiss="modal" aria-label="Close"></button>
                                </div>
                                <div class="modal-body">
                                    <img id="modalImage" class="modal-img" src="" alt="Image Preview">
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
        <script src="https://cdn.jsdelivr.net/npm/bootstrap@5.2.3/dist/js/bootstrap.bundle.min.js" crossorigin="anonymous"></script>
        <script src="./assets/js/scripts.js"></script>
        <script src="https://cdnjs.cloudflare.com/ajax/libs/Chart.js/2.8.0/Chart.min.js" crossorigin="anonymous"></script>
        <script src="./assets/demo/chart-area-demo.js"></script>
        <script src="./assets/demo/chart-bar-demo.js"></script>
        <script src="https://cdn.jsdelivr.net/npm/simple-datatables@7.1.2/dist/umd/simple-datatables.min.js" crossorigin="anonymous"></script>
        <script src="./assets/js/datatables-simple-demo.js"></script>
        <script>
            // JavaScript for Image Preview Modal
            document.addEventListener('DOMContentLoaded', function () {
                var imagePreviews = document.querySelectorAll('.image-preview');
                var modalImage = document.getElementById('modalImage');

                imagePreviews.forEach(function (img) {
                    img.addEventListener('click', function () {
                        var imgSrc = this.getAttribute('data-img-src');
                        modalImage.setAttribute('src', imgSrc);
                    });
                });
            });
        </script>
    </body>
</html>