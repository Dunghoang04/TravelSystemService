<%-- 
    Document   : admin
    Created on : May 21, 2025, 2:42:06 PM
    Author     : ad
--%>

<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html lang="en">
    <head>
        <meta charset="utf-8" />
        <meta http-equiv="X-UA-Compatible" content="IE=edge" />
        <meta name="viewport" content="width=device-width, initial-scale=1, shrink-to-fit=no" />
        <meta name="description" content="" />
        <meta name="author" content="" />
        <title>Dashboard - SB Admin</title>
        <link href="https://cdn.jsdelivr.net/npm/simple-datatables@7.1.2/dist/style.min.css" rel="stylesheet" />
        <link href="./assets/css/styles2.css" rel="stylesheet" />
        <script src="https://use.fontawesome.com/releases/v6.3.0/js/all.js" crossorigin="anonymous"></script>
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




    </style>
    <body class="sb-nav-fixed">
        <%@include file="../layout/HeaderAdmin.jsp" %>
        <div id="layoutSidenav">
            <div id="layoutSidenav_nav">
                <nav class="sb-sidenav accordion sb-sidenav-dark" id="sidenavAccordion">
                    <div class="sb-sidenav-menu">
                        <div class="nav">
                            <div class="sb-sidenav-menu-heading">Bảng điều khiển</div>
                            <a class="nav-link" href="statical">
                                <div class="sb-nav-link-icon"><i class="fas fa-tachometer-alt"></i></div>
                                Thống kê
                            </a>
                            <a class="nav-link" href="liststaffaccount">
                                <div class="sb-nav-link-icon"><i class="fas fa-tachometer-alt"></i></div>
                                Tài khoản tài khoản nhân viên
                            </a>

                            <a class="nav-link" href="tourmanagement">
                                <div class="sb-nav-link-icon"><i class="fas fa-tachometer-alt"></i></div>
                                Quản lí du khách
                            </a>
                            <a class="nav-link" href="requestuploadtour">
                                <div class="sb-nav-link-icon"><i class="fas fa-tachometer-alt"></i></div>
                                Quản lí đại lý
                            </a>


                        </div>
                    </div>

                </nav>
            </div>
            <div id="layoutSidenav_content">
                <main>
                    <div class="container-fluid px-4">
                        <h1 class="mt-4">Quản lí tài khoản nhân viên</h1>

                        <form action="finduser" method="GET" class="d-flex align-items-center">
                            <input 
                                type="text" 
                                name="name" 
                                class="form-control me-2 shadow-sm" 
                                placeholder="Nhập tên người dùng.." 
                                style="max-width: 300px; border-radius: 20px;" 
                                />
                            <button type="submit" class="btn btn-primary d-flex align-items-center px-3" style="border-radius: 20px;">
                                <i class="bi bi-search me-2"></i> Tìm kiếm
                            </button>
                        </form>

                        <div style="display: flex; align-items: center; gap: 15px; margin-bottom: 10px;">
                            <div style="display: flex; align-items: center; gap: 10px;">
                                <label class="form-label">Lọc theo trạng thái:</label>
                                <select class="form-select" name="status" id="statusFilter" onchange="filterUsers()">
                                    <option value="">-Chọn-</option>
                                    <option value="1">Hoạt động</option>
                                    <option value="0">Không hoạt động</option>
                                    <option value="">Tất cả</option>
                                </select>
                            </div>
                            <a href="addstaffaccount">
                                <button class="btn btn-primary">Thêm tài khoản nhân viên</button>
                            </a>
                        </div>


                        <div class="col-xl-12 col-md-6">
                            <div class="card-body">
                                <table style="border: 1px solid">
                                    <thead>
                                        <tr>
                                            <th>Id</th>
                                            <th>Tên</th>
                                            <th>Email</th>
                                            <th>Địa chỉ</th>
                                            <th>SĐT</th>
                                            <th>Giới tính</th>
                                            <th>Vai Trò</th>
                                            <th>Ngày tạo</th>
                                            <th>Hoạt Động</th>
                                        </tr>
                                    </thead>

                                    <c:forEach items="${requestScope.listStaffAccount}" var="u">
                                        <tr>
                                            <td>${u.userID}</td>
                                            <td>${u.lastName}</td>
                                            <td>${u.gmail}</td> 
                                            <td>${u.address}</td>
                                            <td>${u.phone}</td>
                                            <td>${u.gender}</td>
                                            <td>${u.roleID == 1 ? "Admin" : "User"}</td>
                                            <td>${u.createDate}</td>
                                            <td>
                                                <a href="updateuser?id=${u.userID}" class="btn btn-primary btn-sm">
                                                    Cập Nhật <i class="bi bi-pencil-square"></i>
                                                </a>
                                                <a href="detailuser?id=${u.userID}" class="btn btn-primary btn-sm">
                                                    Chi Tiết <i class="bi bi-pencil-square"></i>
                                                </a>
                                                <button class="btn ${u.status == 1 ? 'btn-danger' : 'btn-success'} btn-sm"
                                                        onclick="changeStatus(${u.userID}, ${u.status})">
                                                    ${u.status == 1 ? 'Ẩn' : 'Hiện'} 
                                                    <i class="bi ${u.status == 1 ? 'bi-toggle-on' : 'bi-toggle-off'}"></i>
                                                </button>
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
        <script src="https://cdn.jsdelivr.net/npm/bootstrap@5.2.3/dist/js/bootstrap.bundle.min.js" crossorigin="anonymous"></script>
        <script src="./assets/js/scripts.js"></script>
        <script src="https://cdnjs.cloudflare.com/ajax/libs/Chart.js/2.8.0/Chart.min.js" crossorigin="anonymous"></script>
        <script src="./assets/demo/chart-area-demo.js"></script>
        <script src="./assets/demo/chart-bar-demo.js"></script>
        <script src="https://cdn.jsdelivr.net/npm/simple-datatables@7.1.2/dist/umd/simple-datatables.min.js" crossorigin="anonymous"></script>
        <script src="./assets/js/datatables-simple-demo.js"></script>

        <script>
                                                            function changeStatus(id, status) {
                                                                if (!confirm("Bạn có chắc chắn muốn thay đổi trạng thái?")) {
                                                                    return;
                                                                }

                                                                $.ajax({
                                                                    url: 'loaduser', // Servlet xử lý
                                                                    type: 'POST',
                                                                    data: {id: id, status: status},
                                                                    success: function (response) {
                                                                        if (response === "success") {
                                                                            location.reload();
                                                                        } else {
                                                                            alert("Có lỗi xảy ra. Vui lòng thử lại sau.");
                                                                        }
                                                                    },
                                                                    error: function () {
                                                                        alert("Có lỗi xảy ra. Vui lòng thử lại sau.");
                                                                    }
                                                                });
                                                            }
                                                            function filterUsers() {
                                                                let status = document.getElementById("statusFilter").value;
                                                                let url = "userManager?status=" + status;
                                                                window.location.href = url;
                                                            }
        </script>
    </body>
</html>
