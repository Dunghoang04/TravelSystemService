<%-- 
 * Copyright (C) 2025, Group 6.
 * ProjectCode/Short Name of Application: TravelAgentService 
 * Support Management and Provide Travel Service System 
 *
 * Record of change:
 * DATE        Version    AUTHOR                   DESCRIPTION
 * 2025-06-07  1.0        Hoang Tuan Dung          First implementation
--%>

<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<!DOCTYPE html>
<html>
    <head>
        <meta charset="utf-8" />
        <meta http-equiv="X-UA-Compatible" content="IE=edge" />
        <meta name="viewport" content="width=device-width, initial-scale=1, shrink-to-fit=no" />
        <meta name="description" content="" />
        <meta name="author" content="" />
        <title>Cập nhập giải trí</title>
        <link href="https://cdn.jsdelivr.net/npm/simple-datatables@7.1.2/dist/style.min.css" rel="stylesheet" />
        <link href="./assets/css/styles2.css" rel="stylesheet" />
        <link rel="stylesheet" href="https://cdn.jsdelivr.net/npm/bootstrap-select@1.14.0-beta3/dist/css/bootstrap-select.min.css">
        <link rel="stylesheet" href="https://cdnjs.cloudflare.com/ajax/libs/font-awesome/6.7.2/css/all.min.css" integrity="sha512-Evv84Mr4kqVGRNSgIGL/F/aIDqQb7xQ2vcrdIwxfjThSH8CSR7PBEakCr51Ck+w+/U6swU2Im1vVX0SVk9ABhg==" crossorigin="anonymous" referrerpolicy="no-referrer" />
        <style>
            form {
                display: flex;
                flex-wrap: wrap;
                gap: 10px;
                justify-content: center;
            }

            form input, form select, form button {
                height: 35px;
                font-size: 13px;
            }

            button {
                white-space: nowrap;
            }

            html, body {
                height: 100%;
                margin: 0;
                padding: 0;
                overflow-x: hidden;
            }

            .container-xxl {
                min-height: 100vh;
                display: flex;
                flex-direction: column;
            }

            .sidebar {
                height: 100vh;
                position: fixed;
                left: 0;
                top: 0;
                width: 250px;
                background: #f8f9fa;
                overflow-y: auto;
            }

            .content {
                margin-left: 250px;
                padding: 20px;
                width: calc(100% - 250px);
                flex: 1;
                overflow-x: auto;
            }

            table {
                width: 100%;
                border-collapse: collapse;
                word-break: break-word;
            }

            table th, table td {
                padding: 8px;
                border: 1px solid #ddd;
                text-align: left;
                font-size: 13px;
            }

            table th {
                background-color: #f1f1f1;
            }

            #updateentertainment {
                display: block;
            }

            .form-group {
                margin-bottom: 15px;
            }

            .time div {
                padding: 0px;
            }

            .time {
                display: flex;
                flex-wrap: nowrap;
                align-items: stretch;
                gap: 15px;
                margin: 0;
            }

            .time div:nth-child(2) {
                padding-left: 10px;
            }

            .selectpicker:hover, .selectpicker {
                background: #fff;
                border: 1px solid #CED4DA;
            }

            .bootstrap-select .dropdown-toggle {
                border: 1px solid #ced4da !important;
                box-shadow: none !important;
                outline: none !important;
                background-color: #fff !important;
                text-align: left !important;
                transition: all 0.2s ease-in-out;
            }

            .bootstrap-select .dropdown-toggle:hover {
                border: 1px solid #ced4da !important;
            }

            .bootstrap-select .dropdown-menu .active,
            .bootstrap-select .dropdown-menu .dropdown-item.active,
            .bootstrap-select .dropdown-menu .selected {
                background-color: transparent !important;
                color: #333 !important;
            }

            select data-content i {
                color: #FFCA2C;
            }

            .bootstrap-select .dropdown-toggle:focus, .bootstrap-select>select.mobile-device:focus+.dropdown-toggle {
                outline: 0 !important;
                box-shadow: 0 0 0 0.25rem rgba(13, 110, 253, 0.25) !important;
                outline-offset: 0px !important;
            }

            .bootstrap-select .dropdown-menu i.fa-star,
            .bootstrap-select .dropdown-toggle i.fa-star {
                color: gold !important;
            }

            .errorNoti p {
                margin: 0px;
            }
        </style>
    </head>

    <body>
        <%@include file="../../layout/headerAdmin.jsp" %>
        <div id="layoutSidenav">
            <jsp:include page="../../layout/sideNavOptionAgent.jsp"></jsp:include>  
                <div id="layoutSidenav_content">
                    <main>
                        <div class="card-body px-0 pb-2">
                            <div class="container mt-5">
                                <div class="row">
                                    <!-- Image preview section -->
                                    <div class="col-md-6">
                                        <div class="border p-3 mb-3 d-flex align-items-center justify-content-center" style="height: 300px;overflow: hidden">
                                            <a href="${requestScope.image}" target="_self">
                                            <img id="previewImage" src="${requestScope.image}" alt="Hãy Chọn ảnh"
                                                 style="max-height: 100%; max-width: 100%; object-fit: contain;" />
                                        </a>
                                    </div>
                                    <p id="imagePath" class="input-group input-group-outline mb-2"></p>
                                    <c:if test="${not empty requestScope.errorSystem}">
                                        <div class="col-12 errorNoti" style="background-color: #F6E4E1; border: solid 1px red; text-align: center; color: red; padding: 10px; display: flex; align-items: center; justify-content: center; text-align: center; border-radius: 5px">
                                            <p style="margin-bottom: 0px">${requestScope.errorSystem}</p>
                                        </div>
                                    </c:if>
                                    <c:if test="${not empty requestScope.errorImage}">
                                        <div class="col-12 errorNoti" style="background-color: #F6E4E1; border: solid 1px red; text-align: center; color: red; padding: 5px 10px; display: flex; align-items: center; justify-content: center; text-align: center; border-radius: 5px">
                                            <p style="margin-bottom: 0px">${requestScope.errorImage}</p>
                                        </div>
                                    </c:if>
                                </div>

                                <div class="col-md-6">
                                    <!-- Form to update entertainment service -->
                                    <form action="updateentertainment" id="updateentertainment" method="POST" enctype="multipart/form-data">
                                        <input type="file" id="fileInput" name="image" class="d-none" accept="image/*" onchange="previewImage(event)" 
                                               oninvalid="this.setCustomValidity('Vui lòng chọn ảnh')" oninput="setCustomValidity('')" />
                                        <input type="hidden" name="existingImage" value="${requestScope.image}"/>
                                        <button type="button" class="btn btn-primary" onclick="document.getElementById('fileInput').click();"
                                                style="justify-content: center; align-content: center; width: 100%; color: white; border: 1px solid #007bff; padding: 5px; border-radius: 5px; cursor: pointer;">
                                            Thay đổi hình ảnh dịch vụ giải trí<span style="color: red">*</span>
                                        </button>

                                        <div class="col-12 form-group">
                                            <label for="name"><b>Tên dịch vụ giải trí <span style="color: red">*</span></b></label>
                                            <input type="text" class="form-control" id="name" name="name" value="${requestScope.name}" required 
                                                   maxlength="100" oninvalid="this.setCustomValidity('Vui lòng nhập tên')" oninput="setCustomValidity('')" />
                                        </div>
                                        <c:if test="${not empty requestScope.errorName}">
                                            <div class="col-12 errorNoti" style="background-color: #F6E4E1; border: solid 1px red; text-align: center; color: red; padding: 5px 10px; display: flex; align-items: center; justify-content: center; text-align: center; border-radius: 5px">
                                                <p style="margin-bottom: 0px">${requestScope.errorName}</p>
                                            </div>
                                        </c:if>

                                        <div class="col-12 form-group">
                                            <label for="type"><b>Loại hình giải trí <span style="color: red">*</span></b></label>
                                            <select name="type" id="type" class="selectpicker" data-live-search="false" data-width="100%" required 
                                                    oninvalid="this.setCustomValidity('Vui lòng chọn loại hình giải trí')" oninput="setCustomValidity('')">
                                                <option value="Giải trí" ${requestScope.type == 'Giải trí' ? 'selected' : ''}>Giải trí</option>
                                                <option value="Tham quan" ${requestScope.type == 'Tham quan' ? 'selected' : ''}>Tham quan</option>
                                            </select>
                                        </div>
                                        <c:if test="${not empty requestScope.errorType}">
                                            <div class="col-12 errorNoti" style="background-color: #F6E4E1; border: solid 1px red; text-align: center; color: red; padding: 5px 10px; display: flex; align-items: center; justify-content: center; text-align: center; border-radius: 5px">
                                                <p style="margin-bottom: 0px">${requestScope.errorType}</p>
                                            </div>
                                        </c:if>

                                        <div class="col-12 form-group">
                                            <label for="province"><b>Tỉnh/Thành phố <span style="color: red">*</span></b></label>
                                            <select id="province" name="address" class="form-control" required 
                                                    oninvalid="this.setCustomValidity('Vui lòng chọn tỉnh/thành phố')" oninput="setCustomValidity('')">
                                                <option value="">Chọn Tỉnh/Thành phố</option>
                                            </select>
                                        </div>
                                        <c:if test="${not empty requestScope.errorAddress}">
                                            <div class="col-12 errorNoti" style="background-color: #F6E4E1; border: solid 1px red; text-align: center; color: red; padding: 5px 10px; display: flex; align-items: center; justify-content: center; text-align: center; border-radius: 5px">
                                                <p style="margin-bottom: 0px">${requestScope.errorAddress}</p>
                                            </div>
                                        </c:if>

                                        <div class="col-12 form-group">
                                            <label for="phone"><b>Số điện thoại <span style="color: red">*</span></b></label>
                                            <input type="text" name="phone" class="form-control" value="${requestScope.phone}" required 
                                                   maxlength="10" pattern="0[3|5|7|8|9][0-9]{8}" 
                                                   oninvalid="this.setCustomValidity('Vui lòng nhập số điện thoại hợp lệ (10 số, bắt đầu 03, 05, 07, 08, 09)')" 
                                                   oninput="this.value = this.value.trim(); setCustomValidity('')" />
                                        </div>
                                        <c:if test="${not empty requestScope.errorPhone}">
                                            <div class="col-12 errorNoti" style="background-color: #F6E4E1; border: solid 1px red; text-align: center; color: red; padding: 5px 10px; display: flex; align-items: center; justify-content: center; text-align: center; border-radius: 5px">
                                                <p style="margin-bottom: 0px">${requestScope.errorPhone}</p>
                                            </div>
                                        </c:if>

                                        <div class="col-12 form-group">
                                            <label for="rate"><b>Đánh giá (Thang điểm 10) <span style="color: red">*</span></b></label>
                                            <input type="number" name="rate" class="form-control" value="${requestScope.rate}" required 
                                                   step="0.1" min="0" max="10" 
                                                   oninvalid="this.setCustomValidity('Vui lòng nhập điểm từ 0-10')" oninput="setCustomValidity('')" />
                                        </div>
                                        <c:if test="${not empty requestScope.errorRate}">
                                            <div class="col-12 errorNoti" style="background-color: #F6E4E1; border: solid 1px red; text-align: center; color: red; padding: 5px 10px; display: flex; align-items: center; justify-content: center; text-align: center; border-radius: 5px">
                                                <p style="margin-bottom: 0px">${requestScope.errorRate}</p>
                                            </div>
                                        </c:if>

                                        <div class="col-12 row form-group time">
                                            <div class="col-6 form-group">
                                                <label for="timeopen"><b>Thời gian mở cửa <span style="color: red">*</span></b></label>
                                                <input type="time" name="timeopen" class="form-control" value="${requestScope.timeOpen}" required 
                                                       oninvalid="this.setCustomValidity('Vui lòng nhập thời gian')" oninput="setCustomValidity('')" />
                                            </div>
                                            <div class="col-6 form-group">
                                                <label for="timeclose"><b>Thời gian đóng cửa <span style="color: red">*</span></b></label>
                                                <input type="time" name="timeclose" class="form-control" value="${requestScope.timeClose}" required 
                                                       oninvalid="this.setCustomValidity('Vui lòng nhập thời gian')" oninput="setCustomValidity('')" />
                                            </div>
                                        </div>
                                        <c:if test="${not empty requestScope.errorTime}">
                                            <div class="col-12 errorNoti" style="background-color: #F6E4E1; border: solid 1px red; text-align: center; color: red; padding: 5px 10px; display: flex; align-items: center; justify-content: center; text-align: center; border-radius: 5px">
                                                <p style="margin-bottom: 0px">${requestScope.errorTime}</p>
                                            </div>
                                        </c:if>

                                        <div class="col-12 form-group">
                                            <label for="dayOfWeekOpen"><b>Ngày mở cửa trong tuần (Ctrl để chọn nhiều) <span style="color: red">*</span></b></label>
                                            <select style="height: 100px" name="dayOfWeekOpen" multiple class="form-select" required 
                                                    oninvalid="this.setCustomValidity('Vui lòng chọn (có thể chọn nhiều)')" oninput="setCustomValidity('')">
                                                <option value="Thứ 2" ${requestScope.dayOfWeekOpen.contains("Thứ 2") ? 'selected' : ''}>Thứ 2</option>
                                                <option value="Thứ 3" ${requestScope.dayOfWeekOpen.contains("Thứ 3") ? 'selected' : ''}>Thứ 3</option>
                                                <option value="Thứ 4" ${requestScope.dayOfWeekOpen.contains("Thứ 4") ? 'selected' : ''}>Thứ 4</option>
                                                <option value="Thứ 5" ${requestScope.dayOfWeekOpen.contains("Thứ 5") ? 'selected' : ''}>Thứ 5</option>
                                                <option value="Thứ 6" ${requestScope.dayOfWeekOpen.contains("Thứ 6") ? 'selected' : ''}>Thứ 6</option>
                                                <option value="Thứ 7" ${requestScope.dayOfWeekOpen.contains("Thứ 7") ? 'selected' : ''}>Thứ 7</option>
                                                <option value="Chủ nhật" ${requestScope.dayOfWeekOpen.contains("Chủ nhật") ? 'selected' : ''}>Chủ nhật</option>
                                            </select>
                                        </div>
                                        <c:if test="${not empty requestScope.errorDayOfWeek}">
                                            <div class="col-12 errorNoti" style="background-color: #F6E4E1; border: solid 1px red; text-align: center; color: red; padding: 5px 10px; display: flex; align-items: center; justify-content: center; text-align: center; border-radius: 5px">
                                                <p style="margin-bottom: 0px">${requestScope.errorDayOfWeek}</p>
                                            </div>
                                        </c:if>

                                        <div class="col-12 form-group">
                                            <label for="ticketPrice"><b>Giá vé <span style="color: red">*</span></b></label>
                                            <input type="text" name="ticketPrice" class="form-control" value="${requestScope.ticketPrice}" placeholder="Nhập giá vé nếu có" 
                                                   required oninvalid="this.setCustomValidity('Vui lòng nhập giá vé')" oninput="setCustomValidity('')" />
                                        </div>
                                        <c:if test="${not empty requestScope.errorTicketPrice}">
                                            <div class="col-12 errorNoti" style="background-color: #F6E4E1; border: solid 1px red; text-align: center; color: red; padding: 5px 10px; display: flex; align-items: center; justify-content: center; text-align: center; border-radius: 5px">
                                                <p style="margin-bottom: 0px">${requestScope.errorTicketPrice}</p>
                                            </div>
                                        </c:if>

                                        <div class="col-12 form-group">
                                            <label for="description"><b>Mô tả dịch vụ giải trí <span style="color: red">*</span></b></label>
                                            <textarea rows="4" id="description" name="description" class="form-control" required 
                                                      oninvalid="this.setCustomValidity('Vui lòng nhập mô tả')" oninput="setCustomValidity('')">${requestScope.description}</textarea>
                                        </div>
                                        <c:if test="${not empty requestScope.errorDescription}">
                                            <div class="col-12 errorNoti" style="background-color: #F6E4E1; border: solid 1px red; text-align: center; color: red; padding: 5px 10px; display: flex; align-items: center; justify-content: center; text-align: center; border-radius: 5px">
                                                <p style="margin-bottom: 0px">${requestScope.errorDescription}</p>
                                            </div>
                                        </c:if>

                                        <input type="hidden" name="page" value="${requestScope.page}">
                                        <input type="hidden" name="status" value="${requestScope.status}">
                                        <input type="hidden" name="serviceId" value="${requestScope.serviceId}">

                                        <div class="d-flex justify-content-end">
                                            <button type="submit" class="btn btn-info me-2" name="action" value="update">Cập nhập</button>
                                            <a href="managementertainment"><button type="button" class="btn btn-danger caceladd" data-bs-dismiss="modal" aria-label="Close">Huỷ bỏ</button></a>
                                        </div>
                                    </form>
                                </div>
                            </div>
                        </div>
                </main>
                <c:if test="${not empty success}">
                    <script>
                        window.onload = function () {
                            Swal.fire({
                                title: 'Cập nhập thành công!',
                                text: '${success}',
                                icon: 'success',
                                showConfirmButton: false,
                                timer: 1500
                            }).then(() => {
                                window.location.href = 'managementertainment?page=${requestScope.page}';
                            });
                        };
                    </script>
                </c:if>
                <c:if test="${not empty requestScope.serviceUsed}">
                    <script>
                        window.onload = function () {
                            Swal.fire({
                                icon: "error",
                                title: "Dịch vụ giải trí trong trạng thái đã sử dụng",
                                text: "Không thể cập nhập",
                            }).then(() => {
                                window.location.href = 'managementertainment?page=${requestScope.page}';
                            });
                        };
                    </script>
                </c:if>
                <footer class="py-4 bg-light mt-auto">
                    <div class="container-fluid px-4">
                        <div class="d-flex align-items-center justify-content-between small">
                            <div class="text-muted">Copyright © Go Việt</div>
                            <div>
                                <a href="#">Điều khoản</a>
                                &middot;
                                <a href="#">Terms & Conditions</a>
                            </div>
                        </div>
                    </div>
                </footer>
            </div>
        </div>
        <script>
            // Handle cancel button click
            const cancelButton = document.querySelector(".caceladd");
            cancelButton.addEventListener("click", (e) => {
                window.history.back();
            });

            // Fetch province data
            const selectedProvinceName = "${requestScope.address}";
            fetch('https://provinces.open-api.vn/api/?depth=1')
                    .then(response => response.json())
                    .then(provincesData => {
                        const provinceSelectElement = document.getElementById('province');
                        provincesData.forEach(provinceItem => {
                            let optionElement = document.createElement('option');
                            optionElement.value = provinceItem.name;
                            optionElement.textContent = provinceItem.name;
                            if (provinceItem.name === selectedProvinceName) {
                                optionElement.selected = true;
                            }
                            provinceSelectElement.appendChild(optionElement);
                        });
                    });

            // Initialize Bootstrap Select
            $(document).ready(function () {
                $('.selectpicker').selectpicker();
            });

            // Handle image preview
            function previewImage(event) {
                const file = event.target.files[0];
                if (file) {
                    const reader = new FileReader();
                    reader.onload = function (e) {
                        document.getElementById('previewImage').src = e.target.result;
                        document.getElementById('imagePath').innerText = file.name;
                    };
                    reader.readAsDataURL(file);
                }
            }
        </script>
        <script src="https://cdn.jsdelivr.net/npm/bootstrap@5.2.3/dist/js/bootstrap.bundle.min.js" crossorigin="anonymous"></script>
        <script src="./assets/js/scripts.js"></script>
        <script src="https://cdnjs.cloudflare.com/ajax/libs/Chart.js/2.8.0/Chart.min.js" crossorigin="anonymous"></script>
        <script src="./assets/demo/chart-area-demo.js"></script>
        <script src="./assets/demo/chart-bar-demo.js"></script>
        <script src="https://cdn.jsdelivr.net/npm/simple-datatables@7.1.2/dist/umd/simple-datatables.min.js" crossorigin="anonymous"></script>
        <script src="./assets/js/datatables-simple-demo.js"></script>
        <script src="https://code.jquery.com/jquery-3.6.0.min.js"></script>
        <script src="https://cdn.jsdelivr.net/npm/bootstrap-select@1.14.0-beta3/dist/js/bootstrap-select.min.js"></script>
        <script src="https://cdn.jsdelivr.net/npm/sweetalert2@11"></script>
    </body>
</html>