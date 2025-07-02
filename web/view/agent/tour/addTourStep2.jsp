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
<%@taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<!DOCTYPE html>
<html lang="en">
    <head>
        <meta charset="utf-8" />
        <meta http-equiv="X-UA-Compatible" content="IE=edge" />
        <meta name="viewport" content="width=device-width, initial-scale=1, shrink-to-fit=no" />
        <title>Thêm Tour - Bước 2</title>
        <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.2.3/dist/css/bootstrap.min.css" rel="stylesheet" crossorigin="anonymous">
        <link href="${pageContext.request.contextPath}/assets/css/styles2.css" rel="stylesheet" />
        <link href="https://cdnjs.cloudflare.com/ajax/libs/font-awesome/6.0.0-beta3/css/all.min.css" rel="stylesheet">
        <style>
            html, body {
                height: 100%;
                margin: 0;
                padding: 0;
                background-color: #f4f6f8;
                color: #333;
                overflow-x: hidden;
            }
            h1 {
                color: #28A745;
                font-size: 24px;
                margin-bottom: 20px;
                text-align: center;
            }
            .form-container {
                max-width: 1200px;
                margin: 0 auto;
                background-color: white;
                border-radius: 20px;
                padding: 40px;
                margin-top: 20px;
            }
            .error-message {
                color: #dc3545;
                font-size: 14px;
                margin-top: 5px;                
                padding: 8px;
                border-radius: 5px;
            }
            .required {
                color: red;
            }
            .image-preview {
                width: 500px;
                height: 300px;
                object-fit: cover;
                border-radius: 8px;
                border: 1px solid #ccc;
            }
            .btn-upload {
                margin-top: 10px;
            }
            .flex-container {
                display: flex;
                flex-wrap: wrap;
                gap: 20px;
            }
            .image-column {
                flex: 0 0 250px;
                display: flex;
                flex-direction: column;
                align-items: center;
            }
            .form-column {
                flex: 1;
                display: flex;
                flex-direction: column;
                gap: 20px;
            }
            .price-row {
                display: flex;
                gap: 20px;
            }
            .price-col {
                flex: 1;
            }
            .service-row {
                display: flex;
                gap: 20px;
                width: 100%;
            }
            .service-col {
                flex: 1;
            }
            .service-select {
                height: 100px; /* Khoảng 4 dòng */
            }
            
            .clear-btn {
                margin-top: 10px;
                padding: 5px 10px;
                font-size: 14px;
            }
        </style>
    </head>
    <body>
        <%@include file="/view/layout/headerAdmin.jsp" %>
        <div id="layoutSidenav">
            <jsp:include page="/view/layout/sideNavOptionAgent.jsp" />
            <div id="layoutSidenav_content">
                <main>
                    <div class="form-container">
                        <h1>Thêm Tour - Bước 2: Chi tiết tour</h1>

                        <form action="${pageContext.request.contextPath}/AddTour?service=addStep2" method="post" enctype="multipart/form-data">
                            <div class="flex-container">
                                <!-- Cột hình ảnh -->
                                <div class="image-column">
                                    <c:choose>
                                        <c:when test="${not empty sessionScope.imagePath}">
                                            <img id="previewImage" class="image-preview" src="${sessionScope.imagePath}" alt="Ảnh xem trước">
                                        </c:when>
                                        <c:otherwise>
                                            <img id="previewImage" class="image-preview" src="${pageContext.request.contextPath}/assets/img/placeholder.jpg" alt="Ảnh xem trước">
                                        </c:otherwise>
                                    </c:choose>
                                </div>

                                <!-- Cột form -->
                                <div class="form-column">
                                    <!-- Nút chọn ảnh -->
                                    <div>
                                        <input type="file" id="fileInput" name="image" class="d-none" accept="image/*" onchange="previewImage(this, 'previewImage')">
                                        <button type="button" class="btn btn-primary btn-upload col-md-12" onclick="document.getElementById('fileInput').click();">
                                            Chọn ảnh cho tour <span class="required">*</span>
                                        </button>
                                        <c:if test="${not empty requestScope.validationErrors_image}">
                                            <div class="error-message">${requestScope.validationErrors_image}</div>
                                        </c:if>
                                    </div>

                                    <!-- Tên tour -->
                                    <div>
                                        <label for="tourName" class="form-label">Tên tour <span class="required">*</span></label>
                                        <textarea name="tourName" id="tourName" class="form-control" rows="2" placeholder="Nhập tên tour">${sessionScope.tourName}</textarea>
                                        <c:if test="${not empty requestScope.validationErrors_tourName}">
                                            <div class="error-message">${requestScope.validationErrors_tourName}</div>
                                        </c:if>
                                    </div>

                                    <!-- Số lượng, Giá người lớn, Giá trẻ em -->
                                    <div class="price-row">
                                        <div class="price-col">
                                            <label for="quantity" class="form-label">Số lượng <span class="required">*</span></label>
                                            <input type="text" class="form-control" name="quantity" value="${sessionScope.quantity}" placeholder="Số lượng">
                                            <c:if test="${not empty requestScope.validationErrors_quantity}">
                                                <div class="error-message">${requestScope.validationErrors_quantity}</div>
                                            </c:if>
                                        </div>
                                        <div class="price-col">
                                            <label for="adultPrice" class="form-label">Giá người lớn <span class="required">*</span></label>
                                            <input type="text" class="form-control" name="adultPrice" value="${sessionScope.adultPrice}" placeholder="Giá người lớn">
                                            <c:if test="${not empty requestScope.validationErrors_adultPrice}">
                                                <div class="error-message">${requestScope.validationErrors_adultPrice}</div>
                                            </c:if>
                                        </div>
                                        <div class="price-col">
                                            <label for="childrenPrice" class="form-label">Giá trẻ em <span class="required">*</span></label>
                                            <input type="text" class="form-control" name="childrenPrice" value="${sessionScope.childrenPrice}" placeholder="Giá trẻ em">
                                            <c:if test="${not empty requestScope.validationErrors_childrenPrice}">
                                                <div class="error-message">${requestScope.validationErrors_childrenPrice}</div>
                                            </c:if>
                                        </div>
                                    </div>
                                </div>
                            </div>

                            <!-- Chọn dịch vụ -->
                            <div class="service-row mt-4">
                                <div class="service-col">
                                    <label for="restaurantIds" class="form-label">Nhà hàng</label>
                                    <select name="restaurantIds" id="restaurantIds" multiple class="form-control service-select">
                                        <c:forEach var="restaurant" items="${restaurants}">
                                            <option value="${restaurant.serviceID}" ${sessionScope.restaurantIds != null && fn:contains(fn:join(sessionScope.restaurantIds, ','), restaurant.serviceID.toString()) ? 'selected' : ''}>${restaurant.name}</option>
                                        </c:forEach>
                                    </select>
                                    <button type="button" class="btn btn-warning clear-btn" onclick="clearSelection('restaurantIds')">Xóa lựa chọn</button>
                                </div>
                                <div class="service-col">
                                    <label for="accommodationIds" class="form-label">Khách sạn</label>
                                    <select name="accommodationIds" id="accommodationIds" multiple class="form-control service-select">
                                        <c:forEach var="accommodation" items="${accommodations}">
                                            <option value="${accommodation.serviceID}" ${sessionScope.accommodationIds != null && fn:contains(fn:join(sessionScope.accommodationIds, ','), accommodation.serviceID.toString()) ? 'selected' : ''}>${accommodation.name}</option>
                                        </c:forEach>
                                    </select>
                                    <button type="button" class="btn btn-warning clear-btn" onclick="clearSelection('accommodationIds')">Xóa lựa chọn</button>
                                </div>
                                <div class="service-col">
                                    <label for="entertainmentIds" class="form-label">Giải trí</label>
                                    <select name="entertainmentIds" id="entertainmentIds" multiple class="form-control service-select">
                                        <c:forEach var="entertainment" items="${entertainments}">
                                            <option value="${entertainment.serviceID}" ${sessionScope.entertainmentIds != null && fn:contains(fn:join(sessionScope.entertainmentIds, ','), entertainment.serviceID.toString()) ? 'selected' : ''}>${entertainment.name}</option>
                                        </c:forEach>
                                    </select>
                                    <button type="button" class="btn btn-warning clear-btn" onclick="clearSelection('entertainmentIds')">Xóa lựa chọn</button>
                                </div>
                                
                            </div>
                            <div class="row mt-4" style="width: 100%; text-align: center"> <c:if test="${not empty requestScope.validationErrors_services}">
                                                <div class="error-message">${requestScope.validationErrors_services}</div>
                                            </c:if></div>
                            <!-- Các thông tin chi tiết khác -->
                            <div class="row mt-4" style="width: 100%">
                                <div class="col-12" >
                                    <label for="tourIntroduce" class="form-label">Giới thiệu tour <span class="required">*</span></label>
                                    <textarea name="tourIntroduce" id="tourIntroduce" class="form-control" rows="4" placeholder="Giới thiệu">${sessionScope.tourIntroduce}</textarea>
                                    <c:if test="${not empty requestScope.validationErrors_tourIntroduce}">
                                        <div class="error-message">${requestScope.validationErrors_tourIntroduce}</div>
                                    </c:if>
                                </div>
                                <div class="col-12" >
                                    <label for="tourSchedule" class="form-label">Lịch trình tour <span class="required">*</span></label>
                                    <textarea name="tourSchedule" id="tourSchedule" class="form-control" rows="6" placeholder="Lịch trình">${sessionScope.tourSchedule}</textarea>
                                    <c:if test="${not empty requestScope.validationErrors_tourSchedule}">
                                        <div class="error-message">${requestScope.validationErrors_tourSchedule}</div>
                                    </c:if>
                                </div>
                                <div class="col-12" >
                                    <label for="tourInclude" class="form-label">Bao gồm <span class="required">*</span></label>
                                    <textarea name="tourInclude" id="tourInclude" class="form-control" rows="4" placeholder="Bao gồm trong tour">${sessionScope.tourInclude}</textarea>
                                    <c:if test="${not empty requestScope.validationErrors_tourInclude}">
                                        <div class="error-message">${requestScope.validationErrors_tourInclude}</div>
                                    </c:if>
                                </div>
                                <div class="col-12" >
                                    <label for="tourNonInclude" class="form-label">Không bao gồm <span class="required">*</span></label>
                                    <textarea name="tourNonInclude" id="tourNonInclude" class="form-control" rows="4" placeholder="Không bao gồm trong tour">${sessionScope.tourNonInclude}</textarea>
                                    <c:if test="${not empty requestScope.validationErrors_tourNonInclude}">
                                        <div class="error-message">${requestScope.validationErrors_tourNonInclude}</div>
                                    </c:if>
                                </div>
                            </div>

                            <!-- Nút submit -->
                            <div class="row mt-4" style="width: 100%">
                                <div class="col-12 text-center" >
                                    <button type="submit" class="btn btn-success" style="padding:10px 40px; height: 50px">Hoàn tất</button>
                                </div>
                            </div>
                        </form>
                    </div>
                </main>
                <footer class="bg-white p-3">
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
        <script src="https://code.jquery.com/jquery-3.6.0.min.js" crossorigin="anonymous"></script>
        <script src="https://cdn.jsdelivr.net/npm/bootstrap@5.2.3/dist/js/bootstrap.bundle.min.js" crossorigin="anonymous"></script>
        <script>
            function previewImage(input, previewId) {
                const preview = document.getElementById(previewId);
                const file = input.files[0];
                if (file) {
                    const reader = new FileReader();
                    reader.onload = function (e) {
                        preview.src = e.target.result;
                        preview.style.display = 'block';
                        console.log('Ảnh đã load: ' + e.target.result); // Debug
                    };
                    reader.onerror = function () {
                        alert('Lỗi khi đọc file ảnh, vui lòng chọn file khác!');
                        input.value = ''; // Reset input
                    };
                    reader.readAsDataURL(file);
                } else {
                    preview.style.display = 'none'; // Ẩn preview nếu không có file
                }
            }
            
            function clearSelection(selectId) {
                const select = document.getElementById(selectId);
                for (let option of select.options) {
                    option.selected = false;
                }
            }

            document.addEventListener('DOMContentLoaded', function () {
                const fileInput = document.getElementById('fileInput');
                if (fileInput) {
                    fileInput.addEventListener('change', function () {
                        previewImage(this, 'previewImage');
                    });
                }
            });

            window.onload = function () {
                const previewImage = document.getElementById('previewImage');
                if (previewImage && previewImage.src && previewImage.src !== '${pageContext.request.contextPath}/assets/img/placeholder.jpg') {
                    previewImage.style.display = 'block';
                }
            };
        </script>
    </body>
</html>