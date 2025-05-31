<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
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
        <link rel="stylesheet" href="https://cdnjs.cloudflare.com/ajax/libs/font-awesome/6.7.2/css/all.min.css" integrity="sha512-Evv84Mr4kqVGRNSgIGL/F/aIDqQb7xQ2vcrdIwxfjThSH8CSR7PBEakCr51Ck+w+/U6swU2Im1vVX0SVk9ABhg==" crossorigin="anonymous" referrerpolicy="no-referrer" />
        <link rel="stylesheet" href="https://cdn.jsdelivr.net/npm/bootstrap-select@1.14.0-beta3/dist/css/bootstrap-select.min.css">
        <link rel="preconnect" href="https://fonts.googleapis.com">
<link rel="preconnect" href="https://fonts.gstatic.com" crossorigin>
<link href="https://fonts.googleapis.com/css2?family=Ancizar+Serif:ital,wght@0,300..900;1,300..900&family=Ephesis&display=swap" rel="stylesheet">
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
            text-align: center;
        }
        table tbody td{
            text-align: center;
        }
        .btn{
            text-decoration: none;
            padding: 5px 10px;
        }
        a, a:hover{
            color: #fff;
        }
        form{
            margin: 32px 0px;
        }

        .btnStatus {
            width: 111px;
            padding: 3px;
            /* height: 25px; */
            border-radius: 14px;
            background-color: green;
            border: none;
            color: #fff;
            display: flex
                ;
            align-items: center;
            justify-content: center;
            text-align: center;
        }

        .btnStatus.deactive{
            background-color: red;
        }
        .box-button button{
            margin-left: 10px;
        }
        .box-action{
            display: flex
                ;
            justify-content: space-evenly;
        }
        .box-action button{
            width: 30%;
        }
        table tr td .btn-status{
            display: flex
                ;
            align-items: center;
            justify-content: center;
        }

        .star i{
            color: #FFCA2C;
        }





    </style>
    <body>
        <%@include file="../../layout/HeaderAdmin.jsp" %>
        <div id="layoutSidenav">
            <jsp:include page="../../layout/SideNavOptionAgent.jsp"></jsp:include>  
                <div id="layoutSidenav_content">
                    <main>
                        <div class="container-fluid px-4">
                            <h1 class="mt-4" style="font-family: 'Ancizar Serif', serif;">Danh sách nhà hàng</h1>

                            <form action="findrestaurant"  method="GET" class="d-flex align-items-center">
                                <input 
                                    type="text" 
                                    name="name"
                                    value="${sessionScope.nameSearchRestaurant}"
                                class="form-control me-2 shadow-sm" 
                                placeholder="Tìm nhà hàng..." 
                                style="max-width: 350px;
                                border-radius: 20px;
                                padding: 20px" 
                                />
                            <button type="submit" class="btn btn-primary d-flex align-items-center px-3" style="border-radius: 20px;">
                                Tìm kiếm
                            </button>
                        </form>

                        <div style="display: flex;
                             align-items: center;
                             gap: 15px;
                             margin-bottom: 10px;">
                            <div style="display: flex;
                                 align-items: center;
                                 gap: 10px;">
                                <label class="form-label">Lọc theo loại:</label>
                                <select class="form-select" name="type" id="statusFilter">
                                    <option value="" >-Loại nhà hàng-</option>
                                    <option value="5 sao" >5 sao</option>
                                    <option value="4 sao" >4 sao</option>
                                    <option value="3 sao" >3 sao</option>
                                    <option value="" >Tất cả</option>
                                </select>
                            </div>
                            <a href="addrestaurant">
                                <button class="btn btn-primary">Thêm nhà hàng</button>
                            </a>
                        </div>
                        <script>
                            const statusFilter = document.querySelector("#statusFilter"); // Use id for specificity
                            let url = new URL(window.location.href);
                            statusFilter.addEventListener("change", (e) => {
                                e.preventDefault();
                                const keyword = statusFilter.value;
                                if (keyword) {
                                    url.searchParams.set("type", keyword);
                                } else {
                                    url.searchParams.delete("type");
                                }
                                window.location.href = url.href;
                            });
                            // Initialize dropdown value based on URL parameter
                            const urlParams = new URLSearchParams(window.location.search);
                            const currentStatus = urlParams.get("type");
                            if (currentStatus) {
                                statusFilter.value = currentStatus;
                            } else {
                                statusFilter.value = ""; // Default to "-Loại nhà hàng-" or "Tất cả"
                            }

                        </script>
                        <div class="col-xl-12 col-md-6">

                            <div class="card-body">
                                <table id="tableList" style="border: 1px solid">
                                    <thead>
                                        <tr>
                                            <th>Mã Dịch Vụ</th>
                                            <th>Tên nhà hàng</th>
                                            <!--<th>Địa Chỉ</th>-->
                                            <!--<th>Số điện thoại</th>-->
                                            <th>Ảnh</th>
                                            <th>Loại nhà hàng</th>
                                            <!--<th>Thời gian mở cửa</th>-->
                                            <!--<th>Thời gian đóng cửa</th>-->
                                            <!--<th>Mô tả</th>-->
                                            <th>Đánh giá(Thang 10)</th>
                                            <th>Trạng thái</th>
                                            <th>Hoạt động</th>
                                        </tr>
                                    </thead>
                                    <c:forEach items="${sessionScope.listRes}" var="res">
                                        <tr>
                                            <td>${res.getServiceID()}</td>
                                            <td>${res.getName()}</td>

                                            <!--<td>${res.getAddress()}</td>-->
                                            <!--<td>${res.getPhone()}</td>-->
                                            <td ><a href="${res.getImage()}" target="_blank" ><img style="width: 50px;
                                                                                                   height: 50px" src="${res.getImage()}"></a></td>
                                            <td>
                                                <c:if test="${res.getType()=='5 sao'}">
                                                    <div class="star">
                                                        <i class="fa-solid fa-star"></i>
                                                        <i class="fa-solid fa-star"></i>
                                                        <i class="fa-solid fa-star"></i>
                                                        <i class="fa-solid fa-star"></i>
                                                        <i class="fa-solid fa-star"></i>
                                                    </div>
                                                </c:if>
                                                <c:if test="${res.getType()=='4 sao'}">
                                                    <div class="star">
                                                        <i class="fa-solid fa-star"></i>
                                                        <i class="fa-solid fa-star"></i>
                                                        <i class="fa-solid fa-star"></i>
                                                        <i class="fa-solid fa-star"></i>
                                                    </div>
                                                </c:if>
                                                <c:if test="${res.getType()=='3 sao'}">
                                                    <div class="star">
                                                        <i class="fa-solid fa-star"></i>
                                                        <i class="fa-solid fa-star"></i>
                                                        <i class="fa-solid fa-star"></i>
                                                    </div>
                                                </c:if>
                                            </td>
                                            <!--<td>${res.getTimeOpen()}</td>-->
                                            <!--<td>${res.getTimeClose()}</td>-->
                                            <!--<td>${res.getDescription()}</td>-->
                                            <td>${res.getRate()}</td>
                                            <td>
                                                <div class="btn-status">
                                                    <c:if test="${res.getStatus()==1}">
                                                        <button type="button" class="btnStatus active">Đang hoạt động</button>
                                                    </c:if>
                                                    <c:if test="${res.getStatus()==0}">
                                                        <button type="button" class="btnStatus deactive">Dừng hoạt động</button>
                                                    </c:if>
                                                </div>

                                            </td>
                                            <td class="box-button">
                                                <div class="box-action" style="display: flex">
                                                    <button type="button" class="btn btn-warning updatebtn" 
                                                            data-href="updaterestaurant?id=${res.getServiceID()}">
                                                        Cập nhập
                                                    </button>
                                                    <button type="button" class="btn btn-primary detailbtn" 
                                                            data-href="detailrestaurant?id=${res.getServiceID()}" data-name="${res.getName()}">
                                                        Chi tiết
                                                    </button>
                                                    <button type="button" class="btn btn-danger deleteBtn" 
                                                            data-href="deleterestaurant?id=${res.getServiceID()}" data-name="${res.getName()}">
                                                        Xóa
                                                    </button>
                                                </div>
                                            </td>

                                        </tr>

                                    </c:forEach>
                                </table>

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
        <script>
            const btnDelete = document.querySelectorAll(".deleteBtn");
            btnDelete.forEach(btn => {
                btn.addEventListener("click", function (e) {
                    e.preventDefault();
                    const href = this.dataset.href; // lấy URL từ data-href
                    const name = this.dataset.name; // lấy URL từ data-href
                    Swal.fire({
                        title: `Bạn có chắc chắn muốn xóa nhà hàng ${name} ?`,
                        text: "Hành động này không thể hoàn tác!",
                        icon: 'warning',
                        showCancelButton: true,
                        confirmButtonColor: '#d33',
                        cancelButtonColor: '#3085d6',
                        confirmButtonText: 'Xóa',
                        cancelButtonText: 'Hủy'
                    }).then((result) => {
                        if (result.isConfirmed) {
                            Swal.fire({
                                title: 'Đã xóa!',
                                text: 'Nhà hàng đã được xóa thành công.',
                                icon: 'success',
                                timer: 1500,
                                showConfirmButton: false
                            }).then(() => {
                                window.location.href = href;
                            });
                        }
                    });
                });
            });
            const btnDetail = document.querySelectorAll(".detailbtn");
            btnDetail.forEach(btn => {
                btn.addEventListener("click", function (e) {
                    e.preventDefault();
                    const href = this.dataset.href;
                    window.location.href = href;
                });
            });

            const btnUpdate = document.querySelectorAll(".updatebtn");
            btnUpdate.forEach(btn => {
                btn.addEventListener("click", function (e) {
                    e.preventDefault();
                    const href = this.dataset.href;
                    window.location.href = href;
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
        <script src="https://cdn.jsdelivr.net/npm/sweetalert2@11"></script>
        <script src="https://cdn.jsdelivr.net/npm/bootstrap-select@1.14.0-beta3/dist/js/bootstrap-select.min.js"></script>
    </body>
</html>
