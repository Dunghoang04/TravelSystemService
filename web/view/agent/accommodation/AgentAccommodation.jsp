
<%-- 
 * Copyright (C) 2025, Group 6.
 * ProjectCode/Short Name of Application: TravelAgentService 
 * Support Management and Provide Travel Service System 
 *
 * Record of change:
 * DATE        Version    AUTHOR            DESCRIPTION
 *  2025-06-07  1.0       NguyenVanVang     First implementation
--%>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<!DOCTYPE html>
<html>
    <head>
        <meta charset="utf-8" />
        <meta http-equiv="X-UA-Compatible" content="IE=edge" />
        <meta name="viewport" content="width=device-width, initial-scale=1, shrink-to-fit=no" />
        <meta name="description" content="" />
        <meta name="author" content="" />
        <title>Quản lí nơi ở</title>
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
        <%@include file="../../layout/HeaderAdmin.jsp" %>
        <div id="layoutSidenav">
            <jsp:include page="../../layout/SideNavOptionAgent.jsp"></jsp:include>  
                <div id="layoutSidenav_content">
                    <main>
                        <div class="container-fluid px-4">
                            <h1 class="mt-4" style="font-family: 'Ancizar Serif', serif;">Danh sách nơi ở</h1>

                            <form action="managementaccommodation" method="GET" class="d-flex align-items-center">
                                <input 
                                    type="text" 
                                    name="name"
                                    value="${requestScope.searchName}"
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
                            <a href="managementroom">
                                <button class="btn btn-primary">Quản lí phòng</button>
                            </a>
                        </div>

                        <script>
                            const statusFilter = document.querySelector("#statusFilter");
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
                            const urlParams = new URLSearchParams(window.location.search);
                            const currentStatus = urlParams.get("type");
                            if (currentStatus) {
                                statusFilter.value = currentStatus;
                            } else {
                                statusFilter.value = "";
                            }
                        </script>
                        <div class="col-xl-12 col-md-6">
                            <div class="card-body">
                                <table id="tableList" style="border: 1px solid">
                                    <thead>
                                        <tr>
                                            <th>STT</th>
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
                                                <td></td>
                                                <td>${acc.getName()}</td>

                                                <td>
                                                    <a href="${acc.getImage()}" target="_blank">
                                                        <img style="width: 50px; height: 50px" src="${acc.getImage()}">
                                                    </a>
                                                </td>
                                                <td>${acc.getType()}</td>
                                                <td>${acc.getRate()}</td>
                                                <td>
                                                    <div class="btn-status">
                                                        <c:if test="${acc.getStatus() == 1}">
                                                            <button type="button" class="btnStatus active">Đang hoạt động</button>
                                                        </c:if>
                                                        <c:if test="${acc.getStatus() == 0}">
                                                            <button type="button" class="btnStatus deactive">Dừng hoạt động</button>
                                                        </c:if>
                                                    </div>
                                                </td>
                                                <td class="box-button">
                                                    <div class="box-action">

                                                        <button type="button" class="btn btn-primary addBtn" 
                                                                data-href="addroom?id=${acc.getServiceID()}" data-name="${acc.getName()}">
                                                            Tạo phòng
                                                        </button>

                                                        <button type="button" class="btn btn-primary detailbtn" 
                                                                data-href="detailaccommodation?id=${acc.getServiceID()}" data-name="${acc.getName()}">
                                                            Chi tiết
                                                        </button>
                                                        <button type="button" class="btn btn-warning updatebtn" 
                                                                data-href="updateaccommodation?id=${acc.getServiceID()}">
                                                            Cập nhật
                                                        </button>

                                                        <button type="button" class="btn btn-warning deleteBtn" 
                                                                data-href="changestatus?id=${acc.getServiceID()}" data-name="${acc.getName()}">
                                                            Đổi trạng thái
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

                <c:set var="nameParam" value="${param.name}" />
                <c:set var="typeParam" value="${param.type}" />

                <div class="pagination d-flex justify-content-center mt-3">
                    <nav aria-label="Page navigation">
                        <ul class="pagination">
                            <!-- Previous page -->
                            <li class="page-item ${requestScope.currentPage == 1 ? 'disabled' : ''}">
                                <a class="page-link btn-danger"
                                   aria-label="Previous"
                                   href="?page=${requestScope.currentPage - 1}&name=${fn:escapeXml(nameParam)}&type=${fn:escapeXml(typeParam)}">
                                    <span aria-hidden="true">&laquo; Trang trước</span>
                                </a>
                            </li>

                            <!-- Page numbers -->
                            <c:forEach begin="1" end="${requestScope.numberPage}" var="pageNum">
                                <li class="page-item ${requestScope.currentPage == pageNum ? 'active' : ''}" style="background-color: cyan">
                                    <a class="page-link btn-danger"
                                       href="?page=${pageNum}&name=${fn:escapeXml(nameParam)}&type=${fn:escapeXml(typeParam)}">${pageNum}</a>
                                </li>
                            </c:forEach>

                            <!-- Next page -->
                            <li class="page-item ${requestScope.currentPage == requestScope.numberPage ? 'disabled' : ''}">
                                <a class="page-link btn-danger"
                                   aria-label="Next"
                                   href="?page=${requestScope.currentPage + 1}&name=${fn:escapeXml(nameParam)}&type=${fn:escapeXml(typeParam)}">
                                    <span aria-hidden="true">Trang sau &raquo;</span>
                                </a>
                            </li>
                        </ul>
                    </nav>
                </div>

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
                        title: `Bạn có chắc chắn muốn thay đổi trạng thái ${name} ?`,
                        text: "Hành động này không thể hoàn tác!",
                        icon: 'warning',
                        showCancelButton: true,
                        confirmButtonColor: '#d33',
                        cancelButtonColor: '#3085d6',
                        confirmButtonText: 'Thay đổi',
                        cancelButtonText: 'Hủy'
                    }).then((result) => {
                        if (result.isConfirmed) {
                            Swal.fire({
                                title: 'Đã thay đổi!',
                                text: 'Trạng thái dịch vụ giải trí cập nhập thành công.',
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



            // Tự động đánh số STT cho cột đầu tiên
            document.addEventListener("DOMContentLoaded", function () {
                const table = document.querySelector("#tableList");
                const rows = table.querySelectorAll("tbody tr");
                rows.forEach((row, index) => {
                    const sttCell = row.querySelector("td:first-child");
                    sttCell.textContent = index + 1; // Đánh số bắt đầu từ 1
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

