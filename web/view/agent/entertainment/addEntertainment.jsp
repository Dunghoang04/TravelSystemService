<%-- 
 * Copyright (C) 2025, Group 6.
 * ProjectCode/Short Name of Application: TravelAgentService 
 * Support Management and Provide Travel Service System 
 *
 * Record of change:
 * DATE        Version    AUTHOR                   DESCRIPTION
 * 2025-06-07  1.0        Hoang Tuan Dung          First implementation
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html>
<html>
    <head>
        <meta charset="utf-8" />
        <meta http-equiv="X-UA-Compatible" content="IE=edge" />
        <meta name="viewport" content="width=device-width, initial-scale=1, shrink-to-fit=no" />
        <meta name="description" content="" />
        <meta name="author" content="" />
        <title>Thêm giải trí</title>
        <link href="https://cdn.jsdelivr.net/npm/simple-datatables@7.1.2/dist/style.min.css" rel="stylesheet" />
        <link href="./assets/css/styles2.css" rel="stylesheet" />
        <!--Thư viện chèn bootstrap-->
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
        #addRestaurantForm{
            display: block;
        }
        .form-group{
            margin-bottom: 15px;
        }

        .time div{
            padding: 0px;
        }
        .time div:nth-child(2){
            padding-left: 10px;
        }
        .selectpicker:hover{
            background: #fff;
            border: 1px solid #CED4DA
        }
        .selectpicker{
            background: #fff;
            border: 1px solid #CED4DA
        }
        .bootstrap-select .dropdown-toggle {
            border: 1px solid #ced4da !important;
            box-shadow: none !important;
            outline: none !important;
            background-color: #fff !important;
            text-align: left !important;
            transition: all 0.2s ease-in-out;
        }
        .bootstrap-select .dropdown-toggle:hover{
            border: 1px solid #ced4da !important;
        }
        .bootstrap-select .dropdown-menu .active,
        .bootstrap-select .dropdown-menu .dropdown-item.active,
        .bootstrap-select .dropdown-menu .selected {
            background-color: transparent !important;
            color: #333 !important;

        }

        select data-content i{
            color: #FFCA2C;
        }

        .bootstrap-select .dropdown-toggle:focus, .bootstrap-select>select.mobile-device:focus+.dropdown-toggle {
            outline: 0!important;
            box-shadow: 0 0 0 0.25rem rgba(13, 110, 253, 0.25)!important;
            outline-offset: 0px!important;
        }
        .bootstrap-select .dropdown-menu i.fa-star,
        .bootstrap-select .dropdown-toggle i.fa-star {
            color: gold !important;
        }
        .errorNoti p{
            margin: 0px;
        }

        .custom-select {
            width: 100%;
            height: 41px;
            padding: 0.5rem 0.75rem;
            border: 1px solid #ccc;
            border-radius: 6px;
            background-color: #fff;
            font-size: 16px;
            box-shadow: none;
            transition: border-color 0.3s ease;
        }

        .custom-select:focus {
            outline: none;
            border-color: #66afe9;
            box-shadow: 0 0 3px rgba(102, 175, 233, 0.6);
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
                                        <c:if test="${not empty requestScope.imageFileName and requestScope.imageFileName != ''}">
                                            <img id="previewImage" src="/assets/img-entertainment/${requestScope.imageFileName}" 
                                                 alt="Ảnh nhà hàng" style="max-height: 100%; max-width: 100%; object-fit: contain;" />
                                        </c:if>
                                        <c:if test="${empty requestScope.imageFileName or requestScope.imageFileName == ''}">
                                            <img id="previewImage" src="" alt="Hãy chọn ảnh" style="max-height: 100%; max-width: 100%; object-fit: contain;" />
                                        </c:if>
                                    </div>
                                    <p id="imagePath" class="input-group input-group-outline mb-2"></p>
                                    <c:if test="${not empty requestScope.errorInput}">
                                        <div class="col-12 errorNoti" style="background-color: #F6E4E1; border: solid 1px red; text-align: center; color: red; padding: 10px;
                                             display: flex; align-items: center; justify-content: center; text-align: center; border-radius: 5px">
                                            <p>${requestScope.errorInput}</p>
                                        </div>
                                    </c:if>
                                </div>
                                <div class="col-md-6">
                                    <form action="${pageContext.request.contextPath}/addentertainment" method="POST" enctype="multipart/form-data">
                                        <input type="file" id="fileInput" name="image" class="d-none" accept="image/*" onchange="previewImage(event)" 
                                               oninvalid="this.setCustomValidity('Vui lòng chọn ảnh nhà hàng')" oninput="setCustomValidity('')" />
                                        <button type="button" class="btn btn-primary" onclick="document.getElementById('fileInput').click();"
                                                style="justify-content: center; align-content: center; width: 100%; color: white; border: 1px solid #007bff; padding: 5px; border-radius: 5px; cursor: pointer;">
                                            Chọn hình dịch vụ giải trí<span style="color: red"> *</span>
                                        </button>
                                        <div class="col-12 form-group">
                                            <label for="name"><b>Tên dịch vụ giải trí<span style="color: red">*</span></b></label>
                                            <input type="text" class="form-control" id="name" name="name" value="${requestScope.name}" required 
                                                   maxlength="100" oninvalid="this.setCustomValidity('Vui lòng nhập tên nhà hàng')" oninput="setCustomValidity('')" />
                                        </div>
                                        <div class="col-12 form-group">
                                            <label for="type"><b>Loại hình giải trí <span style="color: red"> *</span></b></label>
                                            <select name="type" id="type" class="custom-select" required oninvalid="this.setCustomValidity('Vui lòng chọn loại giải trí')" oninput="setCustomValidity('')">
                                                <option value="" disabled selected>Chọn loại hình giải trí</option>
                                                <option value="Rạp chiếu phim" ${requestScope.type=='Rạp chiếu phim'?'selected':''}>Rạp chiếu phim</option>
                                                <option value="Khu vui chơi" ${requestScope.type=='Khu vui chơi'?'selected':''}>Khu vui chơi</option>
                                                <option value="Công viên nước" ${requestScope.type=='Công viên nước'?'selected':''}>Công viên nước</option>
                                                <option value="Nhạc sống" ${requestScope.type=='Nhạc sống'?'selected':''}>Nhạc sống</option>
                                            </select>
                                        </div>
                                        <div class="col-12 form-group">
                                            <label for="phone"><b>Chọn Tỉnh/Thành phố<span style="color: red">*</span></b></label>
                                            <select id="province" name="address" class="form-control" required 
                                                    oninvalid="this.setCustomValidity('Vui lòng chọn tỉnh/thành phố')" oninput="setCustomValidity('')">
                                                <option value="">Chọn Tỉnh/Thành phố</option>
                                            </select>
                                        </div>
                                        <div class="col-12 form-group">
                                            <label for="phone"><b>Số điện thoại<span style="color: red">*</span></b></label>
                                            <input type="text" name="phone" class="form-control" required maxlength="10" 
                                                   pattern="0[3|5|7|8|9][0-9]{9}" oninvalid="this.setCustomValidity('Vui lòng nhập số điện thoại hợp lệ (10 số, bắt đầu 0)')" 
                                                   oninput="setCustomValidity('')" value="${requestScope.phone}" />
                                        </div>
                                        <div class="col-12 form-group">
                                            <label for="rate"><b>Đánh giá (Thang điểm 10)<span style="color: red">*</span></b></label>
                                            <input type="number" name="rate" class="form-control" required step="0.1" min="0" max="10" 
                                                   oninvalid="this.setCustomValidity('Vui lòng nhập điểm từ 0-10')" oninput="setCustomValidity('')" 
                                                   value="${requestScope.rateStr}" />
                                        </div>
                                        <div class="col-12 row form-group time">
                                            <div class="col-6 form-group">
                                                <label for="timeopen"><b>Thời gian mở cửa<span style="color: red">*</span></b></label>
                                                <input type="time" name="timeopen" class="form-control" value="${requestScope.timeOpenStr}" required 
                                                       oninvalid="this.setCustomValidity('Vui lòng nhập thời gian')" oninput="setCustomValidity('')" />
                                            </div>
                                            <div class="col-6 form-group">
                                                <label for="timeclose"><b>Thời gian đóng cửa<span style="color: red">*</span></b></label>
                                                <input type="time" name="timeclose" class="form-control" value="${requestScope.timeCloseStr}" required 
                                                       oninvalid="this.setCustomValidity('Vui lòng nhập thời gian')" oninput="setCustomValidity('')" />
                                            </div>
                                        </div>
                                        <div class="col-12 form-group">
                                            <label for="dayOfWeekOpen"><b>Ngày mở cửa trong tuần (Vui lòng ấn giữ phím Ctrl để chọn nhiều)<span style="color: red">*</span></b></label>
                                            <select style="height: 100px" name="dayOfWeekOpen" class="form-select" multiple required 
                                                    oninvalid="this.setCustomValidity('Vui lòng chọn (có thể chọn nhiều)')" oninput="setCustomValidity('')">
                                                <option value="Thứ 2" ${requestScope.dayOfWeekAll.contains("Thứ 2") ? 'selected' : ''}>Thứ 2</option>
                                                <option value="Thứ 3" ${requestScope.dayOfWeekAll.contains("Thứ 3") ? 'selected' : ''}>Thứ 3</option>
                                                <option value="Thứ 4" ${requestScope.dayOfWeekAll.contains("Thứ 4") ? 'selected' : ''}>Thứ 4</option>
                                                <option value="Thứ 5" ${requestScope.dayOfWeekAll.contains("Thứ 5") ? 'selected' : ''}>Thứ 5</option>
                                                <option value="Thứ 6" ${requestScope.dayOfWeekAll.contains("Thứ 6") ? 'selected' : ''}>Thứ 6</option>
                                                <option value="Thứ 7" ${requestScope.dayOfWeekAll.contains("Thứ 7") ? 'selected' : ''}>Thứ 7</option>
                                                <option value="Chủ nhật" ${requestScope.dayOfWeekAll.contains("Chủ nhật") ? 'selected' : ''}>Chủ nhật</option>
                                            </select>
                                        </div>
                                        <div class="col-12 form-group">
                                            <label for="ticketPrice"><b>Giá vé<span style="color: red">*</span></b></label>
                                            <input type="text" name="ticketPrice" class="form-control" placeholder="Nhập giá vé nếu có(chỉ chấp nhận số)" required 
                                                   min="0" oninvalid="this.setCustomValidity('Vui lòng nhập giá vé')" oninput="setCustomValidity('')" 
                                                   value="${requestScope.ticketPriceStr}" />
                                        </div>
                                        <div class="col-12 form-group">
                                            <label for="description"><b>Mô tả dịch vụ giải trí<span style="color: red">*</span></b></label>
                                            <textarea rows="4" id="description" name="description" class="form-control" required 
                                                      oninvalid="this.setCustomValidity('Vui lòng nhập mô tả')" oninput="setCustomValidity('')">${requestScope.description}</textarea>
                                        </div>
                                        <br>
                                        <div class="d-flex justify-content-end">
                                            <button type="submit" class="btn btn-info me-2" name="action" value="insert">Thêm dịch vụ giải trí</button>
                                            <a href="${pageContext.request.contextPath}/managementertainment"><button type="button" class="btn btn-danger caceladd" 
                                                                                                                      data-bs-dismiss="modal" aria-label="Close">Huỷ bỏ</button></a>
                                        </div>
                                    </form>
                                </div>
                            </div>
                        </div>
                    </div>
                </main>

                <c:if test="${not empty success}">
                    <script>
                        window.onload = function () {
                            Swal.fire({
                                title: 'Thành công!',
                                text: '${success}',
                                icon: 'success',
                                showConfirmButton: false,
                                timer: 1500
                            }).then(() => {
                                window.location.href = '${pageContext.request.contextPath}/managementertainment';
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
                                ·
                                <a href="#">Terms & Conditions</a>
                            </div>
                        </div>
                    </div>
                </footer>
            </div>
        </div>
        <script>
            /*
             * JavaScript functions for image preview, cancel, and province data fetch.
             */
            // Preview image function
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

            // Cancel button event listener
            const cancelAddButton = document.querySelector(".caceladd");
            cancelAddButton.addEventListener("click", (e) => {
                window.history.back();
            });

            // Fetch province data
            fetch('https://provinces.open-api.vn/api/?depth=1')
                    .then(response => response.json())
                    .then(provincesData => {
                        const provinceSelectElement = document.getElementById('province');
                        const selectedProvince = '${requestScope.address}';
                        provincesData.forEach(provinceItem => {
                            let optionElement = document.createElement('option');
                            optionElement.value = provinceItem.name;
                            optionElement.textContent = provinceItem.name;
                            if (selectedProvince && provinceItem.name === selectedProvince) {
                                optionElement.selected = true;
                            }
                            provinceSelectElement.appendChild(optionElement);
                        });
                    });
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
