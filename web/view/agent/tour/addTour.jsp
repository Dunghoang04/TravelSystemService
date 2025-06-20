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
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="utf-8" />
    <meta http-equiv="X-UA-Compatible" content="IE=edge" />
    <meta name="viewport" content="width=device-width, initial-scale=1, shrink-to-fit=no" />
    <meta name="description" content="" />
    <meta name="author" content="" />
    <title>Thêm Tour Mới</title>
    <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.2.3/dist/css/bootstrap.min.css" rel="stylesheet" crossorigin="anonymous">
    <link href="${pageContext.request.contextPath}/assets/css/styles2.css" rel="stylesheet" />
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
            max-width: 1300px;
            margin: 0 auto;
        }
        h1 {
            color: #28A745;
            font-size: 24px;
            margin-bottom: 20px;
            text-align: center;
        }
        .custom-form {
            background-color: white;
            border-radius: 15px;
            box-shadow: 0 0 5px rgba(0,0,0,0.05);
            padding: 20px;
        }
        .form-row {
            display: flex;
            gap: 20px;
            margin-bottom: 15px;
        }
        .form-group {
            flex: 1;
        }
        .form-group label {
            font-weight: bold;
        }
        .form-group input, .form-group select, .form-group textarea {
            width: 100%;
            padding: 8px;
            border: 1px solid #ccc;
            border-radius: 6px;
        }
        .image-preview {
            max-height: 200px;
            max-width: 100%;
            object-fit: contain;
            margin-right: 20px;
        }
        .button {
            padding: 8px 16px;
            background: #28A745;
            border: none;
            border-radius: 6px;
            color: white;
            font-weight: bold;
            cursor: pointer;
            transition: 0.3s;
        }
        .button:hover {
            background: #218838;
        }
        .back-button {
            margin-right: 10px;
            background: #6c757d;
        }
        .back-button:hover {
            background: #5a6268;
        }
        .required {
            color: red;
            margin-left: 2px;
        }
        .service-selects {
            margin-bottom: 15px;
        }
    </style>
</head>
<body>
    <%@include file="/view/layout/headerAdmin.jsp" %>
    <div id="layoutSidenav">
        <jsp:include page="/view/layout/sideNavOptionAgent.jsp"></jsp:include>  
        <div id="layoutSidenav_content">
            <main>
                <div class="container-centered mt-3 mb-3">
                    <h1>Thêm Tour Mới</h1>
                    <div class="custom-form">
                        <div class="row">
                            <div class="col-md-4">
                                <img id="imagePreview" class="image-preview" style="display: none;" alt="Tour Image">
                                <input type="file" name="image" accept="image/*" onchange="previewImage(event)" class="form-control mt-2" required>
                            </div>
                            <div class="col-md-8">
                                <form action="${pageContext.request.contextPath}/ManageTour" method="POST" enctype="multipart/form-data">
                                    <input type="hidden" name="service" value="add">
                                    <div class="form-group">
                                        <label for="tourCategoryID">Loại Tour:<span class="required">*</span></label>
                                        <select name="tourCategoryID" class="form-control" required>
                                            <option value="">Chọn loại tour</option>
                                            <c:forEach items="${tourCategories}" var="category">
                                                <option value="${category.tourCategoryID}">${category.tourCategoryName}</option>
                                            </c:forEach>
                                        </select>
                                    </div>
                                    <div class="form-group">
                                        <label for="tourName">Tên Tour:<span class="required">*</span></label>
                                        <input type="text" name="tourName" class="form-control" value="" required>
                                    </div>
                                    <div class="form-row">
                                        <div class="form-group">
                                            <label for="numberOfDay">Số Ngày:<span class="required">*</span></label>
                                            <input type="number" name="numberOfDay" class="form-control" value="" required>
                                        </div>
                                        <div class="form-group">
                                            <label for="quantity">Số Lượng:<span class="required">*</span></label>
                                            <input type="number" name="quantity" class="form-control" value="" required>
                                        </div>
                                    </div>
                                    <div class="form-row">
                                        <div class="form-group">
                                            <label for="startPlace">Nơi Bắt Đầu:<span class="required">*</span></label>
                                            <input type="text" name="startPlace" class="form-control" value="" required>
                                        </div>
                                        <div class="form-group">
                                            <label for="endPlace">Điểm Đến:<span class="required">*</span></label>
                                            <input type="text" name="endPlace" class="form-control" value="" required>
                                        </div>
                                    </div>
                                    <div class="form-row">
                                        <div class="form-group">
                                            <label for="startDay">Ngày Bắt Đầu:<span class="required">*</span></label>
                                            <input type="date" name="startDay" class="form-control" value="" required>
                                        </div>
                                        <div class="form-group">
                                            <label for="endDay">Ngày Kết Thúc:<span class="required">*</span></label>
                                            <input type="date" name="endDay" class="form-control" value="" required>
                                        </div>
                                    </div>
                                    <div class="form-row">
                                        <div class="form-group">
                                            <label for="adultPrice">Giá Người Lớn:<span class="required">*</span></label>
                                            <input type="number" step="0.01" name="adultPrice" class="form-control" value="" required>
                                        </div>
                                        <div class="form-group">
                                            <label for="childrenPrice">Giá Trẻ Em:<span class="required">*</span></label>
                                            <input type="number" step="0.01" name="childrenPrice" class="form-control" value="" required>
                                        </div>
                                    </div>
                                    <div class="service-selects">
                                        <div class="form-group">
                                            <label for="restaurant">Chọn Nhà Hàng:</label>
                                            <select name="restaurant" class="form-control">
                                                <option value="0">Không chọn</option>
                                                <c:forEach items="${restaurants}" var="restaurant">
                                                    <option value="${restaurant.serviceID}">${restaurant.name}</option>
                                                </c:forEach>
                                            </select>
                                        </div>
                                        <div class="form-group">
                                            <label for="accommodation">Chọn Nơi Ở:</label>
                                            <select name="accommodation" class="form-control">
                                                <option value="0">Không chọn</option>
                                                <c:forEach items="${accommodations}" var="accommodation">
                                                    <option value="${accommodation.serviceId}">${accommodation.name}</option>
                                                </c:forEach>
                                            </select>
                                        </div>
                                        <div class="form-group">
                                            <label for="entertainment">Chọn Giải Trí:</label>
                                            <select name="entertainment" class="form-control">
                                                <option value="0">Không chọn</option>
                                                <c:forEach items="${entertainments}" var="entertainment">
                                                    <option value="${entertainment.serviceID}">${entertainment.name}</option>
                                                </c:forEach>
                                            </select>
                                        </div>
                                    </div>
                                    <div class="form-group">
                                        <label for="tourIntroduce">Giới Thiệu Tour:</label>
                                        <textarea name="tourIntroduce" class="form-control"></textarea>
                                    </div>
                                    <div class="form-group">
                                        <label for="tourSchedule">Lịch Trình Tour:</label>
                                        <textarea name="tourSchedule" class="form-control"></textarea>
                                    </div>
                                    <div class="form-group">
                                        <label for="tourInclude">Bao Gồm:</label>
                                        <textarea name="tourInclude" class="form-control"></textarea>
                                    </div>
                                    <div class="form-group">
                                        <label for="tourNonInclude">Không Bao Gồm:</label>
                                        <textarea name="tourNonInclude" class="form-control"></textarea>
                                    </div>
                                    <div class="form-group">
                                        <button type="submit" class="button">Lưu</button>
                                        <a href="${pageContext.request.contextPath}/ManageTour?service=list" class="button back-button">Quay lại</a>
                                    </div>
                                </form>
                            </div>
                        </div>
                    </div>
                </div>
            </main>
            <footer class="bg-white p-3">
                <div class="container">
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
    <script src="https://code.jquery.com/jquery-3.6.0.min.js" crossorigin="anonymous"></script>
    <script src="https://cdn.jsdelivr.net/npm/bootstrap@5.2.3/dist/js/bootstrap.bundle.min.js" crossorigin="anonymous"></script>
    <script>
        function previewImage(event) {
            const file = event.target.files[0];
            if (file) {
                const reader = new FileReader();
                reader.onload = function(e) {
                    document.getElementById('imagePreview').src = e.target.result;
                    document.getElementById('imagePreview').style.display = 'block';
                };
                reader.readAsDataURL(file);
            }
        }
    </script>
</body>
</html>