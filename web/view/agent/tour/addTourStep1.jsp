
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
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html>
<html lang="vi">
<head>
    <meta charset="utf-8" />
    <meta http-equiv="X-UA-Compatible" content="IE=edge" />
    <meta name="viewport" content="width=device-width, initial-scale=1, shrink-to-fit=no" />
    <title>Thêm Tour - Bước 1</title>
    <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.2.3/dist/css/bootstrap.min.css" rel="stylesheet">
    <link href="${pageContext.request.contextPath}/assets/css/styles2.css" rel="stylesheet" />
    <link href="https://cdnjs.cloudflare.com/ajax/libs/font-awesome/6.0.0-beta3/css/all.min.css" rel="stylesheet">
    <style>
        html, body {
            background-color: #f4f6f8;
            color: #333;
        }

        h1 {
            color: #28A745;
            font-size: 24px;
            margin-bottom: 20px;
            text-align: center;
        }

        .form-container {
            max-width: 1000px;
            margin: 20px auto;
            background-color: white;
            border-radius: 20px;
            padding: 40px;
        }

        .error-message {
            color: #dc3545;
            font-size: 15px;
            margin-top: 8px;
            border-radius: 5px;
        }

        .required {
            color: red;
        }
    </style>
</head>
<body>
<%@include file="/view/layout/headerAdmin.jsp" %>
<div id="layoutSidenav">
    <jsp:include page="/view/layout/sideNavOptionAgent.jsp" />
    <div id="layoutSidenav_content">
        <main>
            <br><br>
            <h1>Thêm Tour - Bước 1: Thông tin cơ bản</h1>
            <div class="form-container">
                <form id="addTourForm" action="${pageContext.request.contextPath}/AddTour?service=addStep1" method="post">
                    <div class="row g-3">
                        <div class="col-md-6">
                            <label for="tourCategoryID" class="form-label">Loại tour <span class="required">*</span></label>
                            <select name="tourCategoryID" id="tourCategoryID" class="form-control">
                                <option value="">Chọn loại tour</option>
                                <c:forEach var="category" items="${tourCategories}">
                                    <option value="${category.tourCategoryID}" <c:if test="${param.tourCategoryID eq category.tourCategoryID}">selected</c:if>>
                                        ${category.tourCategoryName}
                                    </option>
                                </c:forEach>
                            </select>
                            <c:if test="${sessionScope.validationErrors.tourCategoryID != null}">
                                <div class="error-message">${sessionScope.validationErrors.tourCategoryID}</div>
                            </c:if>
                        </div>

                        <div class="col-md-6">
                            <label for="startDay" class="form-label">Ngày đi <span class="required">*</span></label>
                            <input type="date" name="startDay" id="startDay" value="${param.startDay}" class="form-control">
                            <c:if test="${sessionScope.validationErrors.startDay != null}">
                                <div class="error-message">${sessionScope.validationErrors.startDay}</div>
                            </c:if>
                        </div>

                        <div class="col-md-6">
                            <label for="endDay" class="form-label">Ngày về <span class="required">*</span></label>
                            <input type="date" name="endDay" id="endDay" value="${param.endDay}" class="form-control">
                            <c:if test="${sessionScope.validationErrors.endDay != null}">
                                <div class="error-message">${sessionScope.validationErrors.endDay}</div>
                            </c:if>
                        </div>

                        <div class="col-md-6">
                            <label for="startPlace" class="form-label">Điểm đi <span class="required">*</span></label>
                            <select name="startPlace" id="startPlace" class="form-control">
                                <option value="">Chọn điểm đi</option>
                            </select>
                            <c:if test="${sessionScope.validationErrors.startPlace != null}">
                                <div class="error-message">${sessionScope.validationErrors.startPlace}</div>
                            </c:if>
                        </div>

                        <div class="col-md-6">
                            <label for="endPlace" class="form-label">Điểm đến <span class="required">*</span></label>
                            <select name="endPlace" id="endPlace" class="form-control">
                                <option value="">Chọn điểm đến</option>
                                <c:forEach var="address" items="${addresses}">
                                    <option value="${address}" <c:if test="${param.endPlace eq address}">selected</c:if>>${address}</option>
                                </c:forEach>
                            </select>
                            <c:if test="${sessionScope.validationErrors.endPlace != null}">
                                <div class="error-message">${sessionScope.validationErrors.endPlace}</div>
                            </c:if>
                        </div>
                    </div>

                    <div class="text-center mt-4">
                        <button type="submit" class="btn btn-primary px-4">Tiếp tục</button>
                    </div>
                </form>
            </div>
        </main>

        <footer class="bg-white p-3 mt-4">
            <div class="container">
                <div class="d-flex align-items-center justify-content-between small">
                    <div class="text-muted">Copyright © Go Việt</div>
                    <div>
                        <a href="#">Điều khoản</a> · <a href="#">Terms & Conditions</a>
                    </div>
                </div>
            </div>
        </footer>
    </div>
</div>

<script src="https://cdn.jsdelivr.net/npm/bootstrap@5.2.3/dist/js/bootstrap.bundle.min.js"></script>
<script>
    document.addEventListener('DOMContentLoaded', function () {
        fetch('https://provinces.open-api.vn/api/?depth=1')
            .then(response => response.json())
            .then(data => {
                const select = document.getElementById('startPlace');
                const selectedProvince = '${param.startPlace}';
                data.forEach(p => {
                    const option = document.createElement('option');
                    option.value = p.name;
                    option.textContent = p.name;
                    if (selectedProvince === p.name) option.selected = true;
                    select.appendChild(option);
                });
            })
            .catch(err => {
                console.error("Lỗi lấy dữ liệu tỉnh:", err);
                const select = document.getElementById('startPlace');
                const errorOption = document.createElement('option');
                errorOption.textContent = 'Lỗi tải danh sách tỉnh/thành';
                select.appendChild(errorOption);
            });
    });
</script>
</body>
</html>
