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
            padding: 10px;
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

        .badge {
            font-size: 0.85rem;
            padding: 5px 10px;
            border-radius: 12px;
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
                            <a class="nav-link" href="admin">
                                <div class="sb-nav-link-icon"><i class="fas fa-tachometer-alt"></i></div>
                                Quản lí tài khoản
                            </a>

                            <a class="nav-link" href="tourmanagement">
                                <div class="sb-nav-link-icon"><i class="fas fa-tachometer-alt"></i></div>
                                Quản lí chuyến đi
                            </a>
                            <a class="nav-link" href="requestuploadtour">
                                <div class="sb-nav-link-icon"><i class="fas fa-tachometer-alt"></i></div>
                                Yêu cầu xét duyệt
                            </a>


                        </div>
                    </div>

                </nav>
            </div>
            <div id="layoutSidenav_content">
                <main>
                    <div class="container-fluid px-4">
                        <h1 class="mt-4">Quản lí thẻ khuyến mãi</h1>


                        <div class="mb-3">
                            <form action="findvoucherbyvouchercode" method="post" >
                                <input 
                                    type="text" 
                                    name="voucherCode" 
                                    class="form-control shadow-sm" 
                                    placeholder="Nhập mã thẻ.." 
                                    style="max-width: 300px; border-radius: 8px;" 
                                    />
                                <button type="submit" class="btn btn-primary d-flex align-items-center px-3" style="border-radius: 8px;">
                                    <i class="fas fa-search me-1"></i> Tìm kiếm
                                </button>
                            </form>
                        </div>


                        <div class="d-flex justify-content-between align-items-center mb-3 flex-wrap gap-2">
                            <form action="filtervoucherbystatus" method="post" >
                                <div class="d-flex align-items-center gap-2">
                                    <label class="form-label mb-0">Lọc theo trạng thái:</label>
                                    <select class="form-select" name="status" id="statusFilter" onchange="this.form.submit()" style="width: 180px;">
                                        <option value="2" ${param.status == '2' ? 'selected' : ''}>Tất cả</option>
                                        <option value="1" ${param.status == '1' ? 'selected' : ''}>Hoạt động</option>
                                        <option value="0" ${param.status == '0' ? 'selected' : ''}>Không hoạt động</option>
                                    </select>
                                </div>
                            </form>

                            <a href="addvoucher" class="btn btn-success d-flex align-items-center gap-1">
                                <i class="fas fa-user-plus"></i> Thêm thẻ khuyến mãi
                            </a>
                        </div>
                        <c:if test="${not empty statusError}">
                            <div class="alert alert-danger">${statusError}</div>
                        </c:if>
                        <div class="col-xl-12 col-md-6">

                            <div class="card-body">

                                <table style="border: 1px solid">
                                    <thead>
                                        <tr>
                                            <th>Id</th>
                                            <th>Mã</th>
                                            <th>Tên</th>

                                            <th>Khuyến mãi</th>
                                            <th>Tối đa</th>
                                            <th>Giá tối thiểu</th>
                                            <th>Ngày bắt đầu</th>
                                            <th>Ngày kết thúc</th>
                                            <th>Số lượng</th>
                                            <th>Trạng thái</th>
                                            <th>Hoạt Động</th>
                                        </tr>
                                    </thead>
                                    <c:forEach var="v" items="${requestScope.listVoucher}">
                                        <tr>
                                            <td>${v.voucherId}</td>
                                            <td>${v.voucherCode}</td>
                                            <td>${v.voucherName}</td>                                      
                                            <td>${v.percentDiscount}%</td>
                                            <td>${v.maxDiscountAmount}</td>
                                            <td>${v.minAmountApply}</td>
                                            <td>${v.startDate}</td>
                                            <td>${v.endDate}</td>
                                            <td>${v.quantity}</td>
                                            <td>${v.status}</td>
                                            <td >
                                                <a href="updatevoucher?voucherId=${v.voucherId}" class="btn btn-primary btn-sm">
                                                    Cập Nhật <i class="bi bi-pencil-square"></i>
                                                </a>

                                                <a href="detailvoucher?voucherId=${v.voucherId}" class="btn btn-primary btn-sm">
                                                    Chi Tiết <i class="bi bi-pencil-square"></i>
                                                </a>

                                                <form action="changestatusvoucher" method="post" style="display:inline;">
                                                    <input type="hidden" name="id" value="${v.voucherId}" />
                                                    <input type="hidden" name="status" value="${v.status}" />
                                                    <button type="submit" class="btn ${v.status == 1 ? 'btn-danger' : 'btn-success'} btn-sm">
                                                        ${v.status == 1 ? 'Ẩn' : 'Hiện'} 
                                                        <i class="bi ${v.status == 1 ? 'bi-toggle-on' : 'bi-toggle-off'}"></i>
                                                    </button>
                                                </form>

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
    </body>
    
</html>
