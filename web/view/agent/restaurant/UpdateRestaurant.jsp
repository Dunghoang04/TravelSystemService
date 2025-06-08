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
        <title>Cập nhập nhà hàng</title>
        <link href="https://cdn.jsdelivr.net/npm/simple-datatables@7.1.2/dist/style.min.css" rel="stylesheet" />
        <link href="./assets/css/styles2.css" rel="stylesheet" />
        <script src="https://use.fontawesome.com/releases/v6.3.0/js/all.js" crossorigin="anonymous"></script>
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
            white-space: nowrap; /* Tránh chữ trong button bị cắt */
        }

        html, body {
            height: 100%;
            margin: 0;
            padding: 0;

            overflow-x: hidden; /* Ngăn chặn tràn ngang */
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
            word-break: break-word; /* Để chữ tự xuống dòng nếu quá dài */
        }

        table th, table td {
            padding: 8px;
            border: 1px solid #ddd;
            text-align: left;
            font-size: 13px; /* Giảm chữ trong bảng */
        }

        table th {
            background-color: #f1f1f1;
        }
        #updaterestaurant{
            display: block;
        }
        .form-group{
            margin-bottom: 15px;
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
            color: gold !important; /* Hoặc dùng #ffc107 cho màu vàng Bootstrap */
        }

        .btn-check:checked + .btn, :not(.btn-check) + .btn:active, .btn:first-child:active, .btn.active, .btn.show {
            background-color: #fff;
            color: #FFCA2C!important;
        }
        .bootstrap-select .dropdown-toggle .filter-option {
            color: #FFCA2C!important;
        }
        .bootstrap-select>.dropdown-toggle {
            border: 1px solid #ccc;
            background: #fff;
        }
        .bootstrap-select .dropdown-menu li a {
            background: #fff;
            color: gold;
        }
        .bootstrap-select .dropdown-menu li a:hover {
            background: #ccc;
        }
        .errorNoti p{
            margin: 0px;
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
                                    <!--Tạo ra ảnh xem trước, khung ảnh để người dùng tải lên , sau khi chọn ảnh js sẽ chèn src vô-->
                                    <div class="col-md-6">
                                        <div class="border p-3 mb-3 d-flex align-items-center justify-content-center" style="height: 300px;">
                                            <img id="previewImage" src="${requestScope.updateRestaurant.getImage()}" alt="Hãy Chọn ảnh" style="max-height: 100%;
                                             max-width: 100%;
                                             object-fit: contain;"/>
                                    </div>
                                    <!--Miêu tả ảnh, tí js sẽ chèn vô-->
                                    <p id="imagePath" class="input-group input-group-outline mb-2"></p>
                                    <c:if test="${not empty requestScope.errorInput}">
                                        <div class="col-12 errorNoti" style="background-color: #F6E4E1; border: solid 1px red; text-align: center; color: red; padding: 10px; display: flex; align-items: center; justify-content: center; text-align: center; border-radius: 5px">
                                            <p style="margin-bottom: 0px">${requestScope.errorInput}</p>
                                        </div>
                                    </c:if>
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
                                    <!--Form để thêm sản phẩm lên-->
                                    <form action="updaterestaurant" id="updaterestaurant" method="POST" enctype="multipart/form-data">
                                        <!--này là input file này là 1 kiểu input cho chọn ảnh, nhưng ban đầu ẩn đi, khi ấn nút dưới -> nó gọi input file và mở lên, khi chọn ảnh -> onchange đc gọi và nhảy vô hàm kia -->
                                        <input type="file" id="fileInput" name="image" class="d-none" accept="image/*" onchange="previewImage(event)"/>
                                        <input type="hidden" name="existingImage" value="${requestScope.updateRestaurant.getImage()}"/>
                                        <button type="button" class="btn btn-danger" onclick="document.getElementById('fileInput').click();"
                                                style="justify-content: center;
                                                align-content: center;
                                                width: 100%;
                                                color: white;
                                                border: 1px solid #007bff;
                                                padding: 5px;
                                                border-radius: 5px;
                                                cursor: pointer;">
                                            Thay đổi hình ảnh nhà hàng
                                        </button>
                                        <div class="col-12 form-group">
                                            <label for="id"><b>Mã nhà hàng</b></label>
                                            <input type="text" class="form-control" id="id" name="serviceID" value="${requestScope.updateRestaurant.serviceID}" >
                                        </div>
                                        <div class="col-12 form-group">
                                            <label for="name"><b>Tên nhà hàng</b></label>
                                            <input type="text" class="form-control" id="name" name="name" value="${requestScope.updateRestaurant.name}" >
                                        </div>
                                        <div class="col-12 form-group star">
                                            <label for="name"><b>Loại nhà hàng</b></label>
                                            <select name="type" id="type" class="selectpicker " data-live-search="false" data-width="100%" required>
                                                <option value="5 sao" data-content='<i class="fa-solid fa-star"></i> <i class="fa-solid fa-star"></i> <i class="fa-solid fa-star"></i> <i class="fa-solid fa-star"></i> <i class="fa-solid fa-star"></i>' ${requestScope.updateRestaurant.type=='5 sao'?'selected':''}>5 sao</option>
                                                <option value="4 sao" data-content='<i class="fa-solid fa-star"></i> <i class="fa-solid fa-star"></i> <i class="fa-solid fa-star"></i> <i class="fa-solid fa-star"></i>' ${requestScope.updateRestaurant.type=='4 sao'?'selected':''}>4 sao</option>
                                                <option value="3 sao" data-content='<i class="fa-solid fa-star"></i> <i class="fa-solid fa-star"></i> <i class="fa-solid fa-star"></i>' ${requestScope.updateRestaurant.type=='3 sao'?'selected':''}>3 sao</option>
                                            </select>
                                        </div>

                                        <div class="col-12 form-group">
                                            <select id="province" name="address" class="form-control" >
                                                <option value="">Chọn Tỉnh/Thành phố</option>
                                            </select>
                                        </div>
                                        <div class="col-12 form-group">
                                            <label for="address"><b>Số điện thọai</b></label>
                                            <input type="text" name="phone" class="form-control" value="${requestScope.updateRestaurant.phone}"/>
                                        </div>
                                        <div class="col-12 row form-group time">
                                            <div class="col-6 form-group">
                                                <label for="timeopen"><b>Thời gian mở cửa</b></label>
                                                <input type="time" name="timeopen" class="form-control" value="${requestScope.updateRestaurant.getTimeOpen() != null ? requestScope.updateRestaurant.getTimeOpen().toString().substring(0, 5) : ''}" required/>
                                            </div>
                                            <div class="col-6 form-group">
                                                <label for="timeclose"><b>Thời gian đóng cửa</b></label>
                                                <input type="time" name="timeclose" class="form-control" value="${requestScope.updateRestaurant.getTimeClose() != null ? requestScope.updateRestaurant.getTimeClose().toString().substring(0, 5) : ''}" required/>
                                            </div>
                                        </div>
                                        <div class="col-12 form-group">
                                            <label for="rate"><b>Đánh giá nhà hàng</b></label>
                                            <input type="text" name="rate" class="form-control" value="${requestScope.updateRestaurant.rate}"/>
                                        </div>
                                        <div class="col-12 form-group">
                                            <label for="description"><b>Mô tả nhà hàng</b></label>
                                            <textarea id="description" name="description" rows="6" class="form-control" >${requestScope.updateRestaurant.description}</textarea>
                                        </div>
                                        <div class="col-12 form-group">
                                            <label for="status"><b>Trạng thái nhà hàng</b></label>
                                            <select name="status" id="status" class="form-select" >
                                                <option value="1" ${requestScope.updateRestaurant.status==1?'selected':''}>Đang hoạt động</option>
                                                <option value="0" ${requestScope.updateRestaurant.status==0?'selected':''}>Dừng hoạt động</option>
                                            </select>
                                        </div>
                                        <br>
                                        <div class="d-flex justify-content-end">
                                            <button type="submit" class="btn btn-info me-2" name="action" value="Cập nhập">Cập nhập</button>
                                            <a href="managerestaurant"><button type="button" class="btn btn-danger caceladd" data-bs-dismiss="modal" aria-label="Close">Huỷ bỏ</button></a>
                                        </div>

                                    </form>
                                </div>
                                <script>
                                    const cacelAdd = document.querySelector(".caceladd");
                                    cacelAdd.addEventListener("click", (e) => {
                                        window.history.back();
                                    });


                                    const selectedProvince = "${requestScope.updateRestaurant.address}"; // Lấy tỉnh đã lưu

                                    fetch('https://provinces.open-api.vn/api/?depth=1')
                                            .then(response => response.json())
                                            .then(data => {
                                                const provinceSelect = document.getElementById('province');
                                                data.forEach(province => {
                                                    let opt = document.createElement('option');
                                                    opt.value = province.name;
                                                    opt.textContent = province.name;
                                                    if (province.name === selectedProvince) {
                                                        opt.selected = true; // Gán selected nếu trùng
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
                            <div class="text-muted">Copyright &copy; Go Việt</div>
                            <div>
                                <a href="#">Điều khoản</a>
                                &middot;
                                <a href="#">Terms &amp; Conditions</a>
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
