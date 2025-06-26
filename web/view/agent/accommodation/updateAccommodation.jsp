<%-- 
 * Copyright (C) 2025, Group 6.
 * ProjectCode/Short Name of Application: TravelAgentService 
 * Support Management and Provide Travel Service System 
 *
 * Record of change:
 * DATE        Version    AUTHOR                   DESCRIPTION
 * 2025-06-24  1.0        Nguyen Van Vang         First implementation
--%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
    <head>
        <meta charset="utf-8" />
        <meta http-equiv="X-UA-Compatible" content="IE=edge" />
        <meta name="viewport" content="width=device-width, initial-scale=1, shrink-to-fit=no" />
        <meta name="description" content="" />
        <meta name="author" content="" />
        <title>Dashboard - SB Admin</title>
        <link href="https://cdn.jsdelivr.net/npm/simple-datatables@7.1.2/dist/style.min.css" rel="stylesheet" />
        <link href="./assets/css/styles2.css" rel="stylesheet" />
        <link rel="stylesheet" href="https://cdn.jsdelivr.net/npm/bootstrap-select@1.14.0-beta3/dist/css/bootstrap-select.min.css">
        <link rel="stylesheet" href="https://cdnjs.cloudflare.com/ajax/libs/font-awesome/6.7.2/css/all.min.css" integrity="sha512-Evv84Mr4kqVGRNSgIGL/F/aIDqQb7xQ2vcrdIwxfjThSH8CSR7PBEakCr51Ck+w+/U6swU2Im1vVX0SVk9ABhg==" crossorigin="anonymous" referrerpolicy="no-referrer" />
    </head>
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

        .selectpicker {
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

        .error-message {
            color: red;
            margin-bottom: 5px;
            font-size: 20px;
        }
    </style>
    <body>
        <%@include file="../../layout/headerAdmin.jsp" %>
        <div id="layoutSidenav">
            <jsp:include page="../../layout/sideNavOptionAgent.jsp"></jsp:include>  
            <div id="layoutSidenav_content">
                <main>
                    <div class="card-body px-0 pb-2">
                        <div class="container mt-5">
                            <div class="row">
                                <div class="col-md-6">
                                    <div class="border p-3 mb-3 d-flex align-items-center justify-content-center" style="height: 300px;">
                                        <img id="previewImage" src="${requestScope.accommodationUpdate.image}" alt="Hãy chọn ảnh" style="max-height: 100%; max-width: 100%; object-fit: contain;"/>
                                    </div>
                                    <p id="imagePath" class="input-group input-group-outline mb-2"></p>
                                </div>
                                <script>
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
                                <div class="col-md-6">
                                    <form action="UpdateAccommodation" method="POST" enctype="multipart/form-data">
                                        <input type="file" id="fileInput" name="image" class="d-none" accept="image/*" onchange="previewImage(event)"/>
                                        <input type="hidden" name="serviceID" value="${requestScope.accommodationUpdate.serviceID}"/>
                                        <input type="hidden" name="existingImage" value="${requestScope.accommodationUpdate.image}"/>
                                        <button type="button" class="btn btn-success" onclick="document.getElementById('fileInput').click();"
                                                style="justify-content: center; align-content: center; width: 100%; color: white; border: 1px solid #007bff; padding: 5px; border-radius: 5px; cursor: pointer;">
                                            Thay đổi hình ảnh dịch vụ nơi ở <span style="color:red; font-size:20px" >*</span>
                                        </button>

                                        <div class="col-12 form-group">
                                            <label for="name"><b>Tên dịch vụ nơi ở <span style="color:red; font-size:20px" >*</span></b></label>
                                            <input type="text" class="form-control" id="name" name="name" value="<c:choose><c:when test="${not empty requestScope['input_name']}">${requestScope['input_name']}</c:when><c:otherwise>${requestScope.accommodationUpdate.name}</c:otherwise></c:choose>" required>
                                            <div class="error-message">${error_name}</div>
                                        </div>

                                        <div class="col-12 form-group">
                                            <label for="type"><b>Loại hình nơi ở <span style="color:red; font-size:20px" >*</span></b></label>
                                            <select name="type" id="type" class="selectpicker" data-live-search="false" data-width="100%" required>
                                                <option value="Khách sạn" ${requestScope['input_type'] == 'Khách sạn' || (empty requestScope['input_type'] && requestScope.accommodationUpdate.type == 'Khách sạn') ? 'selected' : ''}>Khách sạn</option>
                                                <option value="Nhà nghỉ" ${requestScope['input_type'] == 'Nhà nghỉ' || (empty requestScope['input_type'] && requestScope.accommodationUpdate.type == 'Nhà nghỉ') ? 'selected' : ''}>Nhà nghỉ</option>
                                                <option value="Villa" ${requestScope['input_type'] == 'Villa' || (empty requestScope['input_type'] && requestScope.accommodationUpdate.type == 'Villa') ? 'selected' : ''}>Villa</option>
                                                <option value="Homestay" ${requestScope['input_type'] == 'Homestay' || (empty requestScope['input_type'] && requestScope.accommodationUpdate.type == 'Homestay') ? 'selected' : ''}>Homestay</option>
                                            </select>
                                            <div class="error-message">${error_type}</div>
                                        </div>

                                        <div class="col-12 form-group">
                                            <label for="address"><b>Tỉnh/Thành phố <span style="color:red; font-size:20px" >*</span></b></label>
                                            <select id="address" name="address" class="form-control" required>
                                                <option value="">Chọn Tỉnh/Thành phố</option>
                                                <c:if test="${not empty requestScope['input_address']}">
                                                    <option value="${requestScope['input_address']}" selected>${requestScope['input_address']}</option>
                                                </c:if>
                                                <c:if test="${empty requestScope['input_address'] and not empty requestScope.accommodationUpdate.address}">
                                                    <option value="${requestScope.accommodationUpdate.address}" selected>${requestScope.accommodationUpdate.address}</option>
                                                </c:if>
                                            </select>
                                            <div class="error-message">${error_address}</div>
                                        </div>

                                        <div class="col-12 form-group">
                                            <label for="phone"><b>Số điện thoại <span style="color:red; font-size:20px" >*</span></b></label>
                                            <input type="text" name="phone" class="form-control" value="<c:choose><c:when test="${not empty requestScope['input_phone']}">${requestScope['input_phone']}</c:when><c:otherwise>${requestScope.accommodationUpdate.phone}</c:otherwise></c:choose>" required/>
                                            <div class="error-message">${error_phone}</div>
                                        </div>

                                        <div class="col-12 form-group">
                                            <label for="rate"><b>Đánh giá (Thang điểm 10) <span style="color:red; font-size:20px" >*</span></b></label>
                                            <input type="number" name="rate" class="form-control" value="<c:choose><c:when test="${not empty requestScope['input_rate']}">${requestScope['input_rate']}</c:when><c:otherwise>${requestScope.accommodationUpdate.rate != null ? requestScope.accommodationUpdate.rate : ''}</c:otherwise></c:choose>" required/>
                                            <div class="error-message">${error_rate}</div>
                                        </div>

                                        <div class="col-12 row form-group time">
                                            <div class="col-6 form-group">
                                                <label for="checkInTime"><b>Thời gian nhận phòng <span style="color:red; font-size:20px" >*</span></b></label>
                                                <input type="time" name="checkInTime" class="form-control" value="<c:choose><c:when test="${not empty requestScope['input_checkInTime']}">${requestScope['input_checkInTime']}</c:when><c:otherwise>${requestScope.accommodationUpdate.checkInTime != null ? requestScope.accommodationUpdate.checkInTime.toString().substring(0, 5) : ''}</c:otherwise></c:choose>" required/>
                                                <div class="error-message">${error_checkInTime}</div>
                                            </div>
                                            <div class="col-6 form-group">
                                                <label for="checkOutTime"><b>Thời gian trả phòng <span style="color:red; font-size:20px" >*</span></b></label>
                                                <input type="time" name="checkOutTime" class="form-control" value="<c:choose><c:when test="${not empty requestScope['input_checkOutTime']}">${requestScope['input_checkOutTime']}</c:when><c:otherwise>${requestScope.accommodationUpdate.checkOutTime != null ? requestScope.accommodationUpdate.checkOutTime.toString().substring(0, 5) : ''}</c:otherwise></c:choose>" required/>
                                                <div class="error-message">${error_checkOutTime}</div>
                                            </div>
                                        </div>

                                        <div class="col-12 form-group">
                                            <label for="description"><b>Mô tả dịch vụ nơi ở <span style="color:red; font-size:20px" >*</span></b></label>
                                            <textarea rows="4" id="description" name="description" class="form-control"><c:choose><c:when test="${not empty requestScope['input_description']}">${requestScope['input_description']}</c:when><c:otherwise>${requestScope.accommodationUpdate.description}</c:otherwise></c:choose></textarea>
                                            <div class="error-message">${error_description}</div>
                                        </div>

                                        <div class="col-12 form-group">
                                            <label for="status"><b>Trạng thái dịch vụ <span style="color:red; font-size:20px" >*</span></b></label>
                                            <select name="status" id="status" class="form-select" required>
                                                <option value="1" ${requestScope['input_status'] == '1' || (empty requestScope['input_status'] && requestScope.accommodationUpdate.status == 1) ? 'selected' : ''}>Đang hoạt động</option>
                                                <option value="0" ${requestScope['input_status'] == '0' || (empty requestScope['input_status'] && requestScope.accommodationUpdate.status == 0) ? 'selected' : ''}>Dừng hoạt động</option>
                                            </select>
                                            <div class="error-message">${error_status}</div>
                                        </div>

                                        <br>
                                        <div class="d-flex justify-content-end">
                                            <button type="submit" class="btn btn-info me-2" name="action" value="update">Cập nhật</button>
                                            <a href="ManagementAccommodation"><button type="button" class="btn btn-danger caceladd" data-bs-dismiss="modal" aria-label="Close">Huỷ bỏ</button></a>
                                        </div>
                                    </form>
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
        <c:if test="${not empty success}">
            <script src="https://cdn.jsdelivr.net/npm/sweetalert2@11"></script>
            <script>
                window.onload = function () {
                    Swal.fire({
                        title: 'Thành công!',
                        text: '${success}',
                        icon: 'success',
                        showConfirmButton: false,
                        timer: 1500
                    }).then(() => {
                        window.location.href = 'ManagementAccommodation';
                    });
                };
            </script>
        </c:if>
        <script>
            const cacelAdd = document.querySelector(".caceladd");
            cacelAdd.addEventListener("click", (e) => {
                window.history.back();
            });

            const selectedProvince = "<%= request.getAttribute("address") %>";

            fetch('https://provinces.open-api.vn/api/?depth=1')
                    .then(response => response.json())
                    .then(data => {
                        const provinceSelect = document.getElementById('province');
                        data.forEach(province => {
                            let opt = document.createElement('option');
                            opt.value = province.name;
                            opt.textContent = province.name;

                            if (opt.value === selectedProvince) {
                                opt.selected = true;
                            }
                            provinceSelect.appendChild(opt);
                        });
                    });

            $(document).ready(function () {
                $('.selectpicker').selectpicker();
            });
        </script>
        <script src="https://cdn.jsdelivr.net/npm/bootstrap@5.2.3/dist/js/bootstrap.bundle.min.js" crossorigin="anonymous"></script>
        <script src="./assets/js/scripts.js"></script>
        <script src="https://cdnjs.cloudflare.com/ajax/libs/Chart.js/2.8.0/Chart.min.js" crossorigin="anonymous"></script>
        <script src="https://cdn.jsdelivr.net/npm/simple-datatables@7.1.2/dist/umd/simple-datatables.min.js" crossorigin="anonymous"></script>
        <script src="https://code.jquery.com/jquery-3.6.0.min.js"></script>
        <script src="https://cdn.jsdelivr.net/npm/bootstrap-select@1.14.0-beta3/dist/js/bootstrap-select.min.js"></script>
    </body>
</html>