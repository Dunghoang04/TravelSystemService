<%-- 
 * Copyright (C) 2025, Group 6.
 * ProjectCode/Short Name of Application: TravelAgentService 
 * Support Management and Provide Travel Service System 
 *
 * Record of change:
 * DATE        Version    AUTHOR                   DESCRIPTION
 * 2025-06-21  1.0        Hoang Tuan Dung          First implementation
--%>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<!DOCTYPE html>
<html>
    <head>
        <meta charset="utf-8" />
        <meta http-equiv="X-UA-Compatible" content="IE=edge" />
        <meta name="viewport" content="width=device-width, initial-scale=1, shrink-to-fit=no" />
        <meta name="description" content="" />
        <meta name="author" content="" />
        <title>Thêm nhà hàng</title>
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
            border: 1px solid #ced4da !important; /* hoặc border: none nếu muốn ẩn hoàn toàn */
            box-shadow: none !important; /* xóa shadow xanh khi focus */
            outline: none !important;
            background-color: #fff !important; /* giữ nền trắng hoặc tuỳ chọn */
            text-align: left !important;
            transition: all 0.2s ease-in-out;
        }
        .bootstrap-select .dropdown-toggle:hover{
            border: 1px solid #ced4da !important; /* hoặc border: none nếu muốn ẩn hoàn toàn */
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
            color: gold !important; /* Hoặc dùng #ffc107 cho màu vàng Bootstrap */
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
                                    <!--Tạo ra ảnh xem trước, khung ảnh để người dùng tải lên , sau khi chọn ảnh js sẽ chèn src vô-->
                                    <div class="col-md-6">
                                        <div class="border p-3 mb-3 d-flex align-items-center justify-content-center" style="height: 300px;">
                                        <c:if test="${not empty requestScope.imageFileNameRestaurant and requestScope.imageFileNameRestaurant != ''}">
                                            <img id="previewImage" src="${pageContext.request.contextPath}/assets/img-restaurant/${requestScope.imageFileNameRestaurant}" 
                                                 alt="Ảnh nhà hàng" style="max-height: 100%; max-width: 100%; object-fit: contain;" />
                                        </c:if>
                                        <c:if test="${empty requestScope.imageFileNameRestaurant or requestScope.imageFileNameRestaurant == ''}">
                                            <img id="previewImage" src="" alt="Hãy chọn ảnh" style="max-height: 100%; max-width: 100%; object-fit: contain;" />
                                        </c:if>
                                    </div>
                                    <!--Miêu tả ảnh, tí js sẽ chèn vô-->
                                    <p id="imagePath"  class="input-group input-group-outline mb-2">${requestScope.imageFileNameRestaurant}</p>
                                    <c:if test="${not empty requestScope.errorSystem}">
                                        <div class="col-12 errorNoti" style="background-color: #F6E4E1; border: solid 1px red; text-align: center; color: red; padding: 10px; display: flex; align-items: center; justify-content: center; text-align: center; border-radius: 5px">
                                            <p>${requestScope.errorSystem}</p>
                                        </div>
                                    </c:if>
                                    <c:if test="${not empty requestScope.errorImage}">
                                        <div class="col-12 errorNoti" style="background-color: #F6E4E1; border: solid 1px red; text-align: center; color: red; padding: 5px 10px; display: flex; align-items: center; justify-content: center; text-align: center; border-radius: 5px">
                                            <p style="margin-bottom: 0px">${requestScope.errorImage}</p>
                                        </div>
                                    </c:if>
                                </div>
                                <div class="col-md-6">
                                    <!--Form để thêm sản phẩm lên-->
                                    <form action="addrestaurant" method="POST" enctype="multipart/form-data">
                                        <!--này là input file này là 1 kiểu input cho chọn ảnh, nhưng ban đầu ẩn đi, khi ấn nút dưới -> nó gọi input file và mở lên, khi chọn ảnh -> onchange đc gọi và nhảy vô hàm kia -->
                                        <input type="file" id="fileInput" name="image" class="d-none" accept="image/*" onchange="previewImage(event)"/>
                                        <button type="button" class="btn btn-primary" onclick="document.getElementById('fileInput').click();"
                                                style="justify-content: center; align-content: center; width: 100%; color: white; border: 1px solid #007bff; padding: 5px; border-radius: 5px; cursor: pointer;">
                                            Chọn hình nhà hàng<span style="color: red">*</span>
                                        </button>
                                        <div class="col-12 form-group">
                                            <label for="name"><b>Tên nhà hàng<span style="color: red">*</span></b></label>
                                            <input type="text" class="form-control" id="name" name="name" required oninvalid="this.setCustomValidity('Vui lòng nhập tên nhà hàng')"
                                                   oninput="setCustomValidity('')" value="${requestScope.name}">
                                        </div>
                                        <c:if test="${not empty requestScope.errorName}">
                                            <div class="col-12 errorNoti" style="background-color: #F6E4E1; border: solid 1px red; text-align: center; color: red; padding: 5px 10px; display: flex; align-items: center; justify-content: center; text-align: center; border-radius: 5px">
                                                <p style="margin-bottom: 0px">${requestScope.errorName}</p>
                                            </div>
                                        </c:if>
                                        <div class="col-12 form-group">
                                            <label for="name"><b>Loại nhà hàng<span style="color: red">*</span></b></label>
                                            <select name="type" id="type"  class="custom-select" required oninvalid="this.setCustomValidity('Vui lòng chọn loại nhà hàng')" oninput="setCustomValidity('')" >
                                                <option value="" disabled selected>Chọn loại nhà hàng</option>
                                                <option value="Cao cấp" ${requestScope.type=="Cao cấp"?'selected':''}>Cao cấp</option>
                                                <option value="Sang trọng" ${requestScope.type=="Sang trọng"?'selected':''}>Sang trọng</option>
                                                <option value="Trung bình" ${requestScope.type=="Trung bình"?'selected':''}>Trung bình</option>
                                            </select>
                                        </div>
                                        <c:if test="${not empty requestScope.errorType}">
                                            <div class="col-12 errorNoti" style="background-color: #F6E4E1; border: solid 1px red; text-align: center; color: red; padding: 5px 10px; display: flex; align-items: center; justify-content: center; text-align: center; border-radius: 5px">
                                                <p style="margin-bottom: 0px">${requestScope.errorType}</p>
                                            </div>
                                        </c:if>
                                        <div class="col-12 form-group">
                                            <label for="phone"><b>Chọn Tỉnh/Thành phố<span style="color: red">*</span></b></label>
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
                                            <input type="text" name="phone" class="form-control" required oninvalid="this.setCustomValidity('Vui lòng nhập số điện thoại')"
                                                   oninput="setCustomValidity('')" value="${requestScope.phone}"/>
                                        </div>
                                        <c:if test="${not empty requestScope.errorPhone}">
                                            <div class="col-12 errorNoti" style="background-color: #F6E4E1; border: solid 1px red; text-align: center; color: red; padding: 5px 10px; display: flex; align-items: center; justify-content: center; text-align: center; border-radius: 5px">
                                                <p style="margin-bottom: 0px">${requestScope.errorPhone}</p>
                                            </div>
                                        </c:if>
                                        <div class="col-12 form-group">
                                            <label for="rate"><b>Đánh giá (Thang điểm 10) <span style="color: red">*</span></b></label>
                                            <input type="text" name="rate" class="form-control"required oninvalid="this.setCustomValidity('Vui lòng nhập điểm đánh giá')"
                                                   oninput="setCustomValidity('')"value="${requestScope.rateStr}"/>
                                        </div>
                                        <c:if test="${not empty requestScope.errorRate}">
                                            <div class="col-12 errorNoti" style="background-color: #F6E4E1; border: solid 1px red; text-align: center; color: red; padding: 5px 10px; display: flex; align-items: center; justify-content: center; text-align: center; border-radius: 5px">
                                                <p style="margin-bottom: 0px">${requestScope.errorRate}</p>
                                            </div>
                                        </c:if>
                                        <div class="col-12 row form-group time">
                                            <div class="col-6 form-group">
                                                <label for="timeopen"><b>Thời gian mở cửa <span style="color: red">*</span></b></label>
                                                <input type="time" name="timeopen" class="form-control" value="${requestScope.timeOpenStr}" required oninvalid="this.setCustomValidity('Vui lòng nhập thời gian')" oninput="setCustomValidity('')"/>
                                            </div>
                                            <div class="col-6 form-group">
                                                <label for="timeclose"><b>Thời gian đóng cửa <span style="color: red">*</span></b></label>
                                                <input type="time" name="timeclose" class="form-control" value="${requestScope.timeCloseStr}" required oninvalid="this.setCustomValidity('Vui lòng nhập thời gian')" oninput="setCustomValidity('')"/>
                                            </div>
                                        </div>
                                        <c:if test="${not empty requestScope.errorTime}">
                                            <div class="col-12 errorNoti" style="background-color: #F6E4E1; border: solid 1px red; text-align: center; color: red; padding: 5px 10px; display: flex; align-items: center; justify-content: center; text-align: center; border-radius: 5px">
                                                <p style="margin-bottom: 0px">${requestScope.errorTime}</p>
                                            </div>
                                        </c:if>
                                        <div class="col-12 form-group">
                                            <label for="description"><b>Mô tả nhà hàng <span style="color: red">*</span></b></label>
                                            <textarea rows="4" id="description" name="description" class="form-control" required oninvalid="this.setCustomValidity('Vui lòng nhập mô tả')"
                                                      oninput="setCustomValidity('')">${requestScope.description}</textarea>
                                        </div>
                                        <c:if test="${not empty requestScope.errorDescription}">
                                            <div class="col-12 errorNoti" style="background-color: #F6E4E1; border: solid 1px red; text-align: center; color: red; padding: 5px 10px; display: flex; align-items: center; justify-content: center; text-align: center; border-radius: 5px">
                                                <p style="margin-bottom: 0px">${requestScope.errorDescription}</p>
                                            </div>
                                        </c:if>
                                        <br>
                                        <div class="d-flex justify-content-end">
                                            <button type="submit" class="btn btn-info me-2" name="action" value="insert">Thêm nhà hàng</button>
                                            <a href="managerestaurant"><button type="button" class="btn btn-danger caceladd" data-bs-dismiss="modal" aria-label="Close">Huỷ bỏ</button></a>
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
                                window.location.href = '${pageContext.request.contextPath}/managerestaurant';
                            });
                        };
                    </script>
                </c:if>
                <script>
                    const cacelAdd = document.querySelector(".caceladd");
                    cacelAdd.addEventListener("click", (e) => {
                        window.history.back();
                    });

                    fetch('https://provinces.open-api.vn/api/?depth=1')
                            .then(response => response.json())
                            .then(data => {
                                const provinceSelect = document.getElementById('province');
                                const selectedAddress = '${requestScope.address}'; // Get the address from the servlet
                                data.forEach(province => {
                                    let opt = document.createElement('option');
                                    opt.value = province.name;
                                    opt.textContent = province.name;
                                    if (selectedAddress && province.name === selectedAddress) {
                                        opt.selected = true; // Pre-select the province if it matches
                                    }
                                    provinceSelect.appendChild(opt);
                                });
                            });
                            
//                     fetch('https://vn-public-apis.fpo.vn/provinces/getAll?limit=-1')
//                    .then(res => res.json())
//                    .then(response => {
//                        const provinces = response.data.data; // <-- sửa chỗ này
//                        const sel = document.getElementById('province');
//                        const selectedProvince = '${requestScope.address}';
//
//                        provinces.forEach(p => {
//                            const o = document.createElement('option');
//                            o.value = p.name;
//                            o.text = p.name_with_type;
//                            if (p.name === selectedProvince)
//                                o.selected = true;
//                            sel.appendChild(o);
//                        });
//                    })
//                    .catch(console.error);       
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
        <script src="https://cdn.jsdelivr.net/npm/sweetalert2@11"></script>
        <!-- Bootstrap Select JS -->
    </body>
</html>
