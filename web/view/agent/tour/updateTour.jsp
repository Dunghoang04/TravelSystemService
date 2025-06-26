<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<%@taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<!DOCTYPE html>
<html lang="vi">
    <head>
        <meta charset="utf-8" />
        <meta http-equiv="X-UA-Compatible" content="IE=edge" />
        <meta name="viewport" content="width=device-width, initial-scale=1, shrink-to-fit=no" />
        <title>Chỉnh Sửa Tour</title>
        <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.2.3/dist/css/bootstrap.min.css" rel="stylesheet" crossorigin="anonymous">
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
                max-width: 1200px;
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
            .tour-type-row, .price-row {
                display: flex;
                gap: 20px;
            }
            .tour-type-col, .price-col {
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
                height: 100px;
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
                    <h1>Chỉnh Sửa Tour</h1>
                    <div class="form-container">
                        <form action="${pageContext.request.contextPath}/EditTour" method="post" enctype="multipart/form-data">
                            <input type="hidden" name="tourId" value="${tour.tourID}">
                            <input type="hidden" name="endPlace" value="${tour.endPlace}">
                            <div class="flex-container">
                                <!-- Cột hình ảnh -->
                                <div class="image-column">
                                    <c:choose>
                                        <c:when test="${not empty tour.image}">
                                            <img id="previewImage" class="image-preview" src="${tour.image}" alt="Ảnh xem trước">
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
                                            Chọn ảnh cho tour
                                        </button>
                                        <c:if test="${not empty sessionScope.validationErrors.image}">
                                            <div class="error-message">${sessionScope.validationErrors.image}</div>
                                        </c:if>
                                    </div>

                                    <!-- Tên tour -->
                                    <div>
                                        <label for="tourName" class="form-label">Tên tour <span class="required">*</span></label>
                                        <textarea name="tourName" id="tourName" class="form-control" rows="2" placeholder="Nhập tên tour">${sessionScope.tourName != null ? sessionScope.tourName : tour.tourName}</textarea>
                                        <c:if test="${not empty sessionScope.validationErrors.tourName}">
                                            <div class="error-message">${sessionScope.validationErrors.tourName}</div>
                                        </c:if>
                                    </div>

                                    <!-- Loại tour và Số lượng -->
                                    <div class="tour-type-row">
                                        <div class="tour-type-col">
                                            <label for="tourCategoryID" class="form-label">Loại tour <span class="required">*</span></label>
                                            <select name="tourCategoryID" id="tourCategoryID" class="form-control">
                                                <option value="">Chọn loại tour</option>
                                                <c:forEach var="category" items="${tourCategories}">
                                                    <option value="${category.tourCategoryID}" ${tour.tourCategoryID eq category.tourCategoryID ? 'selected' : ''}>${category.tourCategoryName}</option>
                                                </c:forEach>
                                            </select>
                                            <c:if test="${not empty sessionScope.validationErrors.tourCategoryID}">
                                                <div class="error-message">${sessionScope.validationErrors.tourCategoryID}</div>
                                            </c:if>
                                        </div>
                                        <div class="tour-type-col">
                                            <label for="quantity" class="form-label">Số lượng <span class="required">*</span></label>
                                            <input type="text" class="form-control" name="quantity" value="${sessionScope.quantity != null ? sessionScope.quantity : tour.quantity}" placeholder="Số lượng">
                                            <c:if test="${not empty sessionScope.validationErrors.quantity}">
                                                <div class="error-message">${sessionScope.validationErrors.quantity}</div>
                                            </c:if>
                                        </div>
                                    </div>

                                    <!-- Giá người lớn và Giá trẻ em -->
                                    <div class="price-row">
                                        <div class="price-col">
                                            <label for="adultPrice" class="form-label">Giá người lớn (VNĐ) <span class="required">*</span></label>
                                            <input type="text" class="form-control price-input" name="adultPrice" id="adultPrice" 
                                                   value="<fmt:formatNumber value='${sessionScope.adultPrice != null ? sessionScope.adultPrice : tour.adultPrice}' type='number' pattern='#,###' />" 
                                                   placeholder="Nhập giá người lớn" oninput="formatCurrency(this)">
                                            <c:if test="${not empty sessionScope.validationErrors.adultPrice}">
                                                <div class="error-message">${sessionScope.validationErrors.adultPrice}</div>
                                            </c:if>
                                        </div>
                                        <div class="price-col">
                                            <label for="childrenPrice" class="form-label">Giá trẻ em (VNĐ) <span class="required">*</span></label>
                                            <input type="text" class="form-control price-input" name="childrenPrice" id="childrenPrice" 
                                                   value="<fmt:formatNumber value='${sessionScope.childrenPrice != null ? sessionScope.childrenPrice : tour.childrenPrice}' type='number' pattern='#,###' />" 
                                                   placeholder="Nhập giá trẻ em" oninput="formatCurrency(this)">
                                            <c:if test="${not empty sessionScope.validationErrors.childrenPrice}">
                                                <div class="error-message">${sessionScope.validationErrors.childrenPrice}</div>
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
                                        <c:choose>
                                            <c:when test="${empty restaurants}">
                                                <option disabled>Không có nhà hàng</option>
                                            </c:when>
                                            <c:otherwise>
                                                <c:forEach var="restaurant" items="${restaurants}">
                                                    <option value="${restaurant.serviceID}" 
                                                            <c:forEach var="detail" items="${tourServiceDetails}">
                                                                <c:if test="${detail.serviceID == restaurant.serviceID && detail.status == 1}">selected</c:if>
                                                            </c:forEach>>${restaurant.name}</option>
                                                </c:forEach>
                                            </c:otherwise>
                                        </c:choose>
                                    </select>
                                    <button type="button" class="btn btn-warning clear-btn" onclick="clearSelection('restaurantIds')">Xóa lựa chọn</button>
                                </div>
                                <div class="service-col">
                                    <label for="accommodationIds" class="form-label">Khách sạn</label>
                                    <select name="accommodationIds" id="accommodationIds" multiple class="form-control service-select">
                                        <c:choose>
                                            <c:when test="${empty accommodations}">
                                                <option disabled>Không có khách sạn</option>
                                            </c:when>
                                            <c:otherwise>
                                                <c:forEach var="accommodation" items="${accommodations}">
                                                    <option value="${accommodation.serviceID}" 
                                                            <c:forEach var="detail" items="${tourServiceDetails}">
                                                                <c:if test="${detail.serviceID == accommodation.serviceID && detail.status == 1}">selected</c:if>
                                                            </c:forEach>>${accommodation.name}</option>
                                                </c:forEach>
                                            </c:otherwise>
                                        </c:choose>
                                    </select>
                                    <button type="button" class="btn btn-warning clear-btn" onclick="clearSelection('accommodationIds')">Xóa lựa chọn</button>
                                </div>
                                <div class="service-col">
                                    <label for="entertainmentIds" class="form-label">Giải trí</label>
                                    <select name="entertainmentIds" id="entertainmentIds" multiple class="form-control service-select">
                                        <c:choose>
                                            <c:when test="${empty entertainments}">
                                                <option disabled>Không có giải trí</option>
                                            </c:when>
                                            <c:otherwise>
                                                <c:forEach var="entertainment" items="${entertainments}">
                                                    <option value="${entertainment.serviceID}" 
                                                            <c:forEach var="detail" items="${tourServiceDetails}">
                                                                <c:if test="${detail.serviceID == entertainment.serviceID && detail.status == 1}">selected</c:if>
                                                            </c:forEach>>${entertainment.name}</option>
                                                </c:forEach>
                                            </c:otherwise>
                                        </c:choose>
                                    </select>
                                    <button type="button" class="btn btn-warning clear-btn" onclick="clearSelection('entertainmentIds')">Xóa lựa chọn</button>
                                </div>
                            </div>

                            <!-- Các thông tin chi tiết khác -->
                            <div class="row mt-4" style="width: 100%">
                                <div class="col-12">
                                    <label for="tourIntroduce" class="form-label">Giới thiệu tour <span class="required">*</span></label>
                                    <textarea name="tourIntroduce" id="tourIntroduce" class="form-control" rows="4" placeholder="Giới thiệu">${sessionScope.tourIntroduce != null ? sessionScope.tourIntroduce : tour.tourIntroduce}</textarea>
                                    <c:if test="${not empty sessionScope.validationErrors.tourIntroduce}">
                                        <div class="error-message">${sessionScope.validationErrors.tourIntroduce}</div>
                                    </c:if>
                                </div>
                                <div class="col-12">
                                    <label for="tourSchedule" class="form-label">Lịch trình tour <span class="required">*</span></label>
                                    <textarea name="tourSchedule" id="tourSchedule" class="form-control" rows="6" placeholder="Lịch trình">${sessionScope.tourSchedule != null ? sessionScope.tourSchedule : tour.tourSchedule}</textarea>
                                    <c:if test="${not empty sessionScope.validationErrors.tourSchedule}">
                                        <div class="error-message">${sessionScope.validationErrors.tourSchedule}</div>
                                    </c:if>
                                </div>
                                <div class="col-12">
                                    <label for="tourInclude" class="form-label">Bao gồm <span class="required">*</span></label>
                                    <textarea name="tourInclude" id="tourInclude" class="form-control" rows="4" placeholder="Bao gồm trong tour">${sessionScope.tourInclude != null ? sessionScope.tourInclude : tour.tourInclude}</textarea>
                                    <c:if test="${not empty sessionScope.validationErrors.tourInclude}">
                                        <div class="error-message">${sessionScope.validationErrors.tourInclude}</div>
                                    </c:if>
                                </div>
                                <div class="col-12">
                                    <label for="tourNonInclude" class="form-label">Không bao gồm <span class="required">*</span></label>
                                    <textarea name="tourNonInclude" id="tourNonInclude" class="form-control" rows="4" placeholder="Không bao gồm trong tour">${sessionScope.tourNonInclude != null ? sessionScope.tourNonInclude : tour.tourNonInclude}</textarea>
                                    <c:if test="${not empty sessionScope.validationErrors.tourNonInclude}">
                                        <div class="error-message">${sessionScope.validationErrors.tourNonInclude}</div>
                                    </c:if>
                                </div>

                                <!-- Điểm khởi hành, Ngày đi, Ngày về -->
                                <div class="price-row mt-4">
                                    <div class="price-col">
                                        <label for="startPlace" class="form-label">Điểm đi <span class="required">*</span></label>
                                        <select name="startPlace" id="startPlace" class="form-control">
                                            <option value="">Chọn tỉnh thành</option>
                                        </select>
                                        <c:if test="${not empty sessionScope.validationErrors.startPlace}">
                                            <div class="error-message">${sessionScope.validationErrors.startPlace}</div>
                                        </c:if>
                                    </div>
                                    <div class="price-col">
                                        <label for="startDay" class="form-label">Ngày đi <span class="required">*</span></label>
                                        <input type="date" class="form-control" name="startDay" value="${sessionScope.startDay != null ? sessionScope.startDay : tour.startDay}" placeholder="Chọn ngày đi">
                                        <c:if test="${not empty sessionScope.validationErrors.startDay}">
                                            <div class="error-message">${sessionScope.validationErrors.startDay}</div>
                                        </c:if>
                                    </div>
                                    <div class="price-col">
                                        <label for="endDay" class="form-label">Ngày về <span class="required">*</span></label>
                                        <input type="date" class="form-control" name="endDay" value="${sessionScope.endDay != null ? sessionScope.endDay : tour.endDay}" placeholder="Chọn ngày về">
                                        <c:if test="${not empty sessionScope.validationErrors.endDay}">
                                            <div class="error-message">${sessionScope.validationErrors.endDay}</div>
                                        </c:if>
                                    </div>
                                </div>
                            </div>

                            <!-- Nút submit -->
                            <div class="row mt-4" style="width: 100%">
                                <div class="col-12 text-center">
                                    <a href="${pageContext.request.contextPath}/ListTour?service=list" class="btn btn-secondary" style="padding:10px 40px; height: 50px; margin-left: 20px">Quay lại</a>
                                    <button type="submit" class="btn btn-success" style="padding:10px 40px; height: 50px">Cập Nhật</button>                                    
                                </div>
                            </div>
                        </form>
                        <c:if test="${not empty requestScope.successMessage}">
                            <div class="alert alert-success mt-3">${requestScope.successMessage}</div>
                        </c:if>
                        <c:if test="${not empty requestScope.errorMessage}">
                            <div class="alert alert-danger mt-3">${requestScope.errorMessage}</div>
                        </c:if>
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
                                                };
                                                reader.onerror = function () {
                                                    alert('Lỗi khi đọc file ảnh, vui lòng chọn file khác!');
                                                    input.value = '';
                                                };
                                                reader.readAsDataURL(file);
                                            } else {
                                                preview.style.display = 'none';
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

                                            // Gọi API để lấy danh sách tỉnh thành
                                            fetch('https://provinces.open-api.vn/api/?depth=1')
                                                    .then(response => response.json())
                                                    .then(data => {
                                                        const select = document.getElementById('startPlace');
                                                        const selectedProvince = '${sessionScope.startPlace != null ? sessionScope.startPlace : tour.startPlace}';
                                                        data.forEach(p => {
                                                            const option = document.createElement('option');
                                                            option.value = p.name;
                                                            option.textContent = p.name;
                                                            if (selectedProvince === p.name)
                                                                option.selected = true;
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

                                        function formatCurrency(input) {
                                            // Loại bỏ tất cả các ký tự không phải số
                                            let value = input.value.replace(/[^0-9]/g, '');
                                            // Định dạng số với dấu phẩy làm phân cách hàng nghìn
                                            let formattedValue = value.replace(/\B(?=(\d{3})+(?!\d))/g, ',');
                                            input.value = formattedValue;
                                        }

                                        // Xử lý khi mất focus để đảm bảo giá trị hợp lệ
                                        document.querySelectorAll('.price-input').forEach(input => {
                                            input.addEventListener('blur', function () {
                                                if (this.value === '' || this.value === '0') {
                                                    this.value = '0';
                                                } else {
                                                    let unformatted = this.value.replace(/[^0-9]/g, '');
                                                    if (unformatted === '')
                                                        unformatted = '0';
                                                    let formattedValue = unformatted.replace(/\B(?=(\d{3})+(?!\d))/g, ',');
                                                    input.value = formattedValue;
                                                }
                                            });
                                        });

                                        // Xử lý khi submit form để gửi giá trị không định dạng
                                        document.querySelector('form').addEventListener('submit', function (e) {
                                            document.querySelectorAll('.price-input').forEach(input => {
                                                input.value = input.value.replace(/[^0-9]/g, '');
                                            });
                                        });

                                        // Định dạng lại giá trị khi trang load
                                        window.onload = function () {
                                            document.querySelectorAll('.price-input').forEach(input => {
                                                let value = input.value.replace(/[^0-9]/g, '');
                                                if (value) {
                                                    let formattedValue = value.replace(/\B(?=(\d{3})+(?!\d))/g, ',');
                                                    input.value = formattedValue;
                                                }
                                            });
                                        };
                                        // Định dạng lại giá trị khi trang load
                                        window.onload = function () {
                                            document.querySelectorAll('.price-input').forEach(input => {
                                                let value = input.value.replace(/[^0-9]/g, '');
                                                if (value) {
                                                    input.value = new Intl.NumberFormat('vi-VN').format(value);
                                                }
                                            });
                                        };

                                        window.onload = function () {
                                            const previewImage = document.getElementById('previewImage');
                                            if (previewImage && previewImage.src && previewImage.src !== '${pageContext.request.contextPath}/assets/img/placeholder.jpg') {
                                                previewImage.style.display = 'block';
                                            }
                                        };
        </script>
    </body>
</html>