<%-- 
    Document   : AgentRestaurant
    Created on : May 26, 2025, 9:15:04 PM
    Author     : ad
--%>

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
    <body>
        <%@include file="../layout/HeaderAdmin.jsp" %>
        <div id="layoutSidenav">
            <jsp:include page="../layout/SideNavOptionAgent.jsp"></jsp:include>  
            <div id="layoutSidenav_content">
                <main>
                    <div class="container-fluid px-4">
                        <h1 class="mt-4">Danh sách nhà hàng</h1>

                        <form action="finduser" " method="GET" class="d-flex align-items-center">
                            <input 
                                type="text" 
                                name="name" 
                                class="form-control me-2 shadow-sm" 
                                placeholder="Tìm nhà hàng..." 
                                style="max-width: 300px; border-radius: 20px;" 
                                />
                            <button type="submit" class="btn btn-primary d-flex align-items-center px-3" style="border-radius: 20px;">
                                 Tìm kiếm
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
                            <a href="adduser">
                        <button class="btn btn-primary">Thêm nhà hàng</button>
                    </a>
                        </div>
                        
                        <div class="col-xl-12 col-md-6">

                            <div class="card-body">

                                <table style="border: 1px solid">
                                    <thead>
                                        <tr>
                                            <th>Id</th>
                                            <th>Tên</th>
                                            <th>Tài Khoản</th>
                                            <th>Email</th>
                                            <th>SĐT</th>
                                            <th>Giới tính</th>
                                            <th>Vai Trò</th>
                                            <th>Ngày tạo</th>
                                            <th>Ngày sửa</th>
                                            <th>Hoạt Động</th>
                                        </tr>
                                    </thead>
                                    <tr>
                                        <td>ID</td>
                                        <td>Name</td>
                                        <td>Account</td>
                                        <td>Email</td>
                                        <td>Phone</td>
                                        <td>Gender</td>
                                        <td>Role</td>
                                        <td>Create at</td>
                                        <td>Update At</td>
                                        <td >
                                            <a href="updateuser?id=${u.id}" class="btn btn-primary btn-sm">
                                                Cập Nhật <i class="bi bi-pencil-square"></i>
                                            </a>

                                            <a href="detailuser?id=${u.id}" class="btn btn-primary btn-sm">
                                                Chi Tiết <i class="bi bi-pencil-square"></i>
                                            </a>

                                            <button class="btn ${u.status == 1 ? 'btn-danger' : 'btn-success'} btn-sm"
                                                    onclick="changeStatus(${u.id}, ${u.status})">
                                                ${u.status == 1 ? 'Ẩn' : 'Hiện'} 
                                                <i class="bi ${u.status == 1 ? 'bi-toggle-on' : 'bi-toggle-off'}"></i>
                                            </button>

                                        </td>
                                    </tr>

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
    </body>
</html>
