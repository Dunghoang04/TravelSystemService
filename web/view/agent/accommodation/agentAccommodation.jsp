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
            text-align: center;
        }

        table tbody td {
            text-align: center;
        }

        .btn {
            text-decoration: none;
            padding: 5px 10px;
        }

        a, a:hover {
            color: #fff;
        }

        form {
            margin: 32px 0px;
        }

        .btnStatus {
            width: 111px;
            padding: 3px;
            border-radius: 14px;
            background-color: green;
            border: none;
            color: #fff;
            display: flex;
            align-items: center;
            justify-content: center;
            text-align: center;
        }

        .btnStatus.deactive {
            background-color: red;
        }

        .box-button button {
            margin-left: 10px;
        }

        .box-action {
            display: flex;
            justify-content: space-evenly;
        }

        .box-action button {
            width: 30%;
        }

        table tr td .btn-status {
            display: flex;
            align-items: center;
            justify-content: center;
        }
    </style>
    <body>
        <%@include file="../../layout/headerAdmin.jsp" %>
        <div id="layoutSidenav">
            <jsp:include page="../../layout/sideNavOptionAgent.jsp"></jsp:include>  
            <div id="layoutSidenav_content">
                <main>
                    <div class="container-fluid px-4">
                        <h1 class="mt-4" style="font-family: 'Ancizar Serif', serif;">Danh sách nơi ở</h1>

                        <form action="ManagementAccommodation" method="GET" class="d-flex align-items-center">
                            <input 
                                type="text" 
                                name="name"
                                value="${requestScope.name}"
                                class="form-control me-2 shadow-sm" 
                                placeholder="Tìm nơi ở..." 
                                style="max-width: 350px; border-radius: 20px; padding: 20px" 
                                />
                            <button type="submit" class="btn btn-primary d-flex align-items-center px-3" style="border-radius: 20px;">
                                Tìm kiếm
                            </button>
                        </form>

                        <div style="display: flex; align-items: center; gap: 15px; margin-bottom: 10px;">
                            <a href="AddAccommodation">
                                <button class="btn btn-primary">Thêm nơi ở</button>
                            </a>
                            <a href="AgentRoom">
                                <button class="btn btn-primary">Quản lý phòng</button>
                            </a>
                        </div>

                        <div class="col-xl-12 col-md-6">
                            <div class="card-body">
                                <table id="tableList" style="border: 1px solid">
                                    <thead>
                                        <tr>
                                            <th>Mã Dịch Vụ</th>
                                            <th>Tên nơi ở</th>
                                            <th>Ảnh</th>
                                            <th>Loại nơi ở</th>
                                            <th>Đánh giá (Thang 10)</th>
                                            <th>Trạng thái</th>
                                            <th>Hoạt động</th>
                                        </tr>
                                    </thead>
                                    <tbody>
                                        <c:forEach items="${sessionScope.listAcc}" var="acc">
                                            <tr>
                                                <td>${acc.serviceID}</td>
                                                <td>${acc.name}</td>
                                                <td>
                                                    <a href="${acc.image}" target="_blank">
                                                        <img style="width: 50px; height: 50px" src="${acc.image}">
                                                    </a>
                                                </td>
                                                <td>${acc.type}</td>
                                                <td>${acc.rate}</td>
                                                <td>
                                                    <div class="btn-status">
                                                        <c:if test="${acc.status == 1}">
                                                            <button type="button" class="btnStatus active">Đang hoạt động</button>
                                                        </c:if>
                                                        <c:if test="${acc.status == 0}">
                                                            <button type="button" class="btnStatus deactive">Dừng hoạt động</button>
                                                        </c:if>
                                                    </div>
                                                </td>
                                                <td class="box-button">
                                                    <div class="box-action">
                                                        <button type="button" class="btn btn-warning updatebtn" 
                                                                data-href="UpdateAccommodation?id=${acc.serviceID}">
                                                            Cập nhật
                                                        </button>
                                                        <button type="button" class="btn btn-primary detailbtn" 
                                                                data-href="DetailAccommodation?id=${acc.serviceID}" data-name="${acc.name}">
                                                            Chi tiết
                                                        </button>
                                                      
                                                        <button type="button" class="btn btn-primary addBtn" 
                                                                data-href="AddRoom?id=${acc.serviceID}" data-name="${acc.name}">
                                                            Tạo phòng
                                                        </button>
                                                    </div>
                                                </td>
                                            </tr>
                                        </c:forEach>
                                    </tbody>
                                </table>
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
        <script>
            const btnDelete = document.querySelectorAll(".deleteBtn");
            btnDelete.forEach(btn => {
                btn.addEventListener("click", function (e) {
                    e.preventDefault();
                    const href = this.dataset.href;
                    const name = this.dataset.name;
                    Swal.fire({
                        title: `Bạn có chắc chắn muốn xóa nơi ở ${name} ?`,
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
                                text: 'Nơi ở đã được xóa thành công.',
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

            const addBtn = document.querySelectorAll(".addBtn");
            addBtn.forEach(btn => {
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
        <script src="https://cdn.jsdelivr.net/npm/simple-datatables@7.1.2/dist/umd/simple-datatables.min.js" crossorigin="anonymous"></script>
        <script src="https://cdn.jsdelivr.net/npm/sweetalert2@11"></script>
        <script src="https://cdn.jsdelivr.net/npm/bootstrap-select@1.14.0-beta3/dist/js/bootstrap-select.min.js"></script>
    </body>
</html>