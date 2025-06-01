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

        .time div {
            padding: 0px;
        }

        .time {
            display: flex;
            flex-wrap: nowrap;
            align-items: stretch; /* Ensure items stretch to the same height */
            gap: 15px; /* Increased gap for better spacing */
            margin: 0; /* Remove any default margins */
        }

 

        .time div:nth-child(2) {
            padding-left: 10px;
        }

        .selectpicker:hover {
            background: #fff;
            border: 1px solid #CED4DA;
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
    </style>
    <body>
        <%@include file="../../layout/HeaderAdmin.jsp" %>
        <div id="layoutSidenav">
            <jsp:include page="../../layout/SideNavOptionAgent.jsp"></jsp:include>  
                <div id="layoutSidenav_content">
                    <main>
                        <div class="card-body px-0 pb-2">
                            <div class="container mt-5">
                                <div class="row">
                                    <!-- Image preview section -->
                                    <div class="col-md-6">
                                        <div class="border p-3 mb-3 d-flex align-items-center justify-content-center" style="height: 300px;">
                                            <img id="previewImage" src="${requestScope.entertainmentUpdate.image}" alt="Hãy Chọn ảnh" style="max-height: 100%; max-width: 100%; object-fit: contain;" />
                                    </div>
                                    <p id="imagePath" class="input-group input-group-outline mb-2">${requestScope.entertainmentUpdate.image != null ? requestScope.entertainmentUpdate.image : ''}</p>
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
                                    <!-- Form to update entertainment service -->
                                    <form action="updateentertainment" id="updateentertainment" method="POST" enctype="multipart/form-data">
                                        <input type="file" id="fileInput" name="image" class="d-none" accept="image/*" onchange="previewImage(event)"/>
                                        <input type="hidden" name="existingImage" value="${requestScope.entertainmentUpdate.image}"/>
                                        <button type="button" class="btn btn-danger" onclick="document.getElementById('fileInput').click();"
                                                style="justify-content: center; align-content: center; width: 100%; color: white; border: 1px solid #007bff; padding: 5px; border-radius: 5px; cursor: pointer;">
                                            Thay đổi hình ảnh dịch vụ giải trí
                                        </button>

                                        <div class="col-12 form-group">
                                            <label for="serviceID"><b>Mã dịch vụ giải trí</b></label>
                                            <input type="text" class="form-control" id="serviceID" name="serviceID" value="${requestScope.entertainmentUpdate.serviceID}" readonly>
                                        </div>

                                        <div class="col-12 form-group">
                                            <label for="name"><b>Tên dịch vụ giải trí</b></label>
                                            <input type="text" class="form-control" id="name" name="name" value="${requestScope.entertainmentUpdate.name}" required>
                                        </div>

                                        <div class="col-12 form-group">
                                            <label for="type"><b>Loại hình giải trí</b></label>
                                            <select name="type" id="type" class="selectpicker" data-live-search="false" data-width="100%" required>
                                                <option value="Rạp chiếu phim" ${requestScope.entertainmentUpdate.type == 'Rạp chiếu phim' ? 'selected' : ''}>Rạp chiếu phim</option>
                                                <option value="Khu vui chơi" ${requestScope.entertainmentUpdate.type == 'Khu vui chơi' ? 'selected' : ''}>Khu vui chơi</option>
                                                <option value="Công viên nước" ${requestScope.entertainmentUpdate.type == 'Công viên nước' ? 'selected' : ''}>Công viên nước</option>
                                                <option value="Nhạc sống" ${requestScope.entertainmentUpdate.type == 'Nhạc sống' ? 'selected' : ''}>Nhạc sống</option>
                                            </select>
                                        </div>

                                        <div class="col-12 form-group">
                                            <label for="province"><b>Tỉnh/Thành phố</b></label>
                                            <select id="province" name="address" class="form-control" required>
                                                <option value="">Chọn Tỉnh/Thành phố</option>
                                            </select>
                                        </div>

                                        <div class="col-12 form-group">
                                            <label for="phone"><b>Số điện thoại</b></label>
                                            <input type="text" name="phone" class="form-control" value="${requestScope.entertainmentUpdate.phone}" required/>
                                        </div>

                                        <div class="col-12 form-group">
                                            <label for="rate"><b>Đánh giá (Thang điểm 10)</b></label>
                                            <input type="text" name="rate" class="form-control" value="${requestScope.entertainmentUpdate.rate}" required/>
                                        </div>

                                        <div class="col-12 row form-group time">
                                            <div class="col-6 form-group">
                                                <label for="timeopen"><b>Thời gian mở cửa</b></label>
                                                <input type="time" name="timeopen" class="form-control" value="${requestScope.entertainmentUpdate.timeOpen != null ? requestScope.entertainmentUpdate.timeOpen.toString().substring(0, 5) : ''}" required/>
                                            </div>
                                            <div class="col-6 form-group">
                                                <label for="timeclose"><b>Thời gian đóng cửa</b></label>
                                                <input type="time" name="timeclose" class="form-control" value="${requestScope.entertainmentUpdate.timeClose != null ? requestScope.entertainmentUpdate.timeClose.toString().substring(0, 5) : ''}" required/>
                                            </div>
                                        </div>

                                        <div class="col-12 form-group">
                                            <label for="dayOfWeekOpen"><b>Ngày mở cửa trong tuần</b></label>
                                            <select name="dayOfWeekOpen" class="form-select" required>
                                                <option value="Thứ 2" ${requestScope.entertainmentUpdate.dayOfWeekOpen == 'Thứ 2' ? 'selected' : ''}>Thứ 2</option>
                                                <option value="Thứ 3" ${requestScope.entertainmentUpdate.dayOfWeekOpen == 'Thứ 3' ? 'selected' : ''}>Thứ 3</option>
                                                <option value="Thứ 4" ${requestScope.entertainmentUpdate.dayOfWeekOpen == 'Thứ 4' ? 'selected' : ''}>Thứ 4</option>
                                                <option value="Thứ 5" ${requestScope.entertainmentUpdate.dayOfWeekOpen == 'Thứ 5' ? 'selected' : ''}>Thứ 5</option>
                                                <option value="Thứ 6" ${requestScope.entertainmentUpdate.dayOfWeekOpen == 'Thứ 6' ? 'selected' : ''}>Thứ 6</option>
                                                <option value="Thứ 7" ${requestScope.entertainmentUpdate.dayOfWeekOpen == 'Thứ 7' ? 'selected' : ''}>Thứ 7</option>
                                                <option value="Chủ nhật" ${requestScope.entertainmentUpdate.dayOfWeekOpen == 'Chủ nhật' ? 'selected' : ''}>Chủ nhật</option>
                                            </select>
                                        </div>

                                        <div class="col-12 form-group">
                                            <label for="ticketPrice"><b>Giá vé</b></label>
                                            <input type="text" name="ticketPrice" class="form-control" value="${requestScope.entertainmentUpdate.ticketPrice}" placeholder="Nhập giá vé nếu có" />
                                        </div>

                                        <div class="col-12 form-group">
                                            <label for="description"><b>Mô tả dịch vụ giải trí</b></label>
                                            <textarea rows="4" id="description" name="description" class="form-control">${requestScope.entertainmentUpdate.description}</textarea>
                                        </div>

                                        <div class="col-12 form-group">
                                            <label for="status"><b>Trạng thái dịch vụ</b></label>
                                            <select name="status" id="status" class="form-select" required>
                                                <option value="1" ${requestScope.entertainmentUpdate.status == 1 ? 'selected' : ''}>Đang hoạt động</option>
                                                <option value="0" ${requestScope.entertainmentUpdate.status == 0 ? 'selected' : ''}>Dừng hoạt động</option>
                                            </select>
                                        </div>

                                        <br>
                                        <div class="d-flex justify-content-end">
                                            <button type="submit" class="btn btn-info me-2" name="action" value="update">Cập nhập</button>
                                            <button type="button" class="btn btn-danger caceladd" data-bs-dismiss="modal" aria-label="Close">Huỷ bỏ</button>
                                        </div>
                                    </form>
                                </div>
                                <script>
                                    const cacelAdd = document.querySelector(".caceladd");
                                    cacelAdd.addEventListener("click", (e) => {
                                        window.history.back();
                                    });

                                    const selectedProvince = "${requestScope.entertainmentUpdate.address}";
                                    fetch('https://provinces.open-api.vn/api/?depth=1')
                                            .then(response => response.json())
                                            .then(data => {
                                                const provinceSelect = document.getElementById('province');
                                                data.forEach(province => {
                                                    let opt = document.createElement('option');
                                                    opt.value = province.name;
                                                    opt.textContent = province.name;
                                                    if (province.name === selectedProvince) {
                                                        opt.selected = true;
                                                    }
                                                    provinceSelect.appendChild(opt);
                                                });
                                            });

                                    $(document).ready(function () {
                                        $('.selectpicker').selectpicker();
                                    });
                                </script>
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
        <script src="https://code.jquery.com/jquery-3.6.0.min.js"></script>
        <script src="https://cdn.jsdelivr.net/npm/bootstrap-select@1.14.0-beta3/dist/js/bootstrap-select.min.js"></script>
    </body>
</html>