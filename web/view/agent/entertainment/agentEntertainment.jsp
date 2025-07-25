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
        <title>Quản lý giải trí</title>
        <link href="https://cdn.jsdelivr.net/npm/simple-datatables@7.1.2/dist/style.min.css" rel="stylesheet" />
        <link href="./assets/css/styles2.css" rel="stylesheet" />
        <link rel="stylesheet" href="https://cdnjs.cloudflare.com/ajax/libs/font-awesome/6.7.2/css/all.min.css" integrity="sha512-Evv84Mr4kqVGRNSgIGL/F/aIdqQb7xQ2vcrdIwxfjThSH8CSR7PBEakCr51Ck+w+/U6swU2Im1vVX0SVk9ABhg==" crossorigin="anonymous" referrerpolicy="no-referrer" />
        <link rel="stylesheet" href="https://cdn.jsdelivr.net/npm/bootstrap-select@1.14.0-beta3/dist/css/bootstrap-select.min.css">
        <link rel="preconnect" href="https://fonts.googleapis.com">
        <link rel="preconnect" href="https://fonts.gstatic.com" crossorigin>
        <script src="https://use.fontawesome.com/releases/v6.3.0/js/all.js" crossorigin="anonymous"></script>
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
        .box-button{
            box-sizing: content-box;
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
            width: 100%;
        }

        table tr td .btn-status {
            display: flex;
            align-items: center;
            justify-content: center;
        }
        .btn{
            padding: 5px 6px;
        }
        .pagination{
            margin-top: 30px;
        }
        .pagination ul li a{
            background-color: #fff;
            color: #000;
        }
    </style>
    <body>
        <%@include file="../../layout/headerAdmin.jsp" %>
        <div id="layoutSidenav">
            <jsp:include page="../../layout/sideNavOptionAgent.jsp"></jsp:include>  
                <div id="layoutSidenav_content">
                    <main>
                        <div class="container-fluid px-4">
                            <h1 class="mt-4" style="font-family: 'Ancizar Serif', serif;">Danh sách giải trí</h1>

                            <form action="${pageContext.request.contextPath}/managementertainment" method="GET" class="d-flex align-items-center">
                            <input 
                                type="text" 
                                name="searchName"
                                value="${requestScope.searchName}"
                                maxlength="100"
                                class="form-control me-2 shadow-sm" 
                                placeholder="Tìm giải trí theo tên..." 
                                style="max-width: 350px; border-radius: 20px; padding: 20px" 
                                />
                            <input type="hidden" name="statusType" value="${requestScope.statusType}" />
                            <button type="submit" class="btn btn-primary d-flex align-items-center px-3" style="border-radius: 20px;">
                                Tìm kiếm
                            </button>
                        </form>

                        <div style="display: flex; align-items: center; gap: 15px; margin-bottom: 10px; justify-content: space-between;">
                            <div style="display: flex; align-items: center; gap: 10px;">
                                <label class="form-label">Lọc theo trạng thái:</label>
                                <select class="form-select" name="statusType" id="statusFilter">
                                    <option value="">-Trạng thái-</option>
                                    <option value="1" ${requestScope.statusType == "1" ? 'selected' : ''}>Đang hoạt động</option>
                                    <option value="0" ${requestScope.statusType == "0" ? 'selected' : ''}>Dừng hoạt động</option>
                                    <option value="">Tất cả</option>
                                </select>
                            </div>
                            <a href="${pageContext.request.contextPath}/addentertainment">
                                <button style="padding: 5px 68px" class="btn btn-info"><i class="fa-solid fa-user-plus"></i>Thêm giải trí</button>
                            </a>
                        </div>
                        <c:if test="${not empty entertainmentList}">

                            <div class="col-xl-12 col-md-6">
                                <div class="card-body">
                                    <table id="tableList" style="border: 1px solid">
                                        <thead>
                                            <tr>
                                                <th>STT</th>
                                                <th>Tên giải trí</th>
                                                <th>Ảnh</th>
                                                <th>Loại giải trí</th>
                                                <th>Đánh giá (Thang 10)</th>
                                                <th>Giá tiền</th>
                                                <th>Trạng thái</th>
                                                <th>Hoạt động</th>
                                            </tr>
                                        </thead>
                                        <tbody>
                                            <c:forEach items="${requestScope.entertainmentList}" var="ent" varStatus="status">
                                                <tr>
                                                    <td>${requestScope.startIndex + status.index}</td>
                                                    <td>${ent.getName()}</td>
                                                    <td>
                                                        <a href="${ent.getImage()}" target="_self">
                                                            <img style="width: 140px; height: 80px" src="${ent.getImage()}">
                                                        </a>
                                                    </td>
                                                    <td>${ent.getType()}</td>
                                                    <td>${ent.getRate()}</td>
                                                    <td><fmt:formatNumber value="${ent.getTicketPrice()}" type="number" groupingUsed="true"></fmt:formatNumber>VNĐ</td>
                                                        <td>
                                                            <div class="btn-status">
                                                            <c:if test="${ent.getStatus() == 1}">
                                                                <button type="button" class="btnStatus active">Đang hoạt động</button>
                                                            </c:if>
                                                            <c:if test="${ent.getStatus() == 0}">
                                                                <button type="button" class="btnStatus deactive">Dừng hoạt động</button>
                                                            </c:if>
                                                        </div>
                                                    </td>
                                                    <td class="box-button">
                                                        <div class="box-action">
                                                            <button type="button" class="btn btn-warning updatebtn" 
                                                                    data-href="updateentertainment?id=${ent.getServiceId()}&page=${requestScope.currentPage}" data-name="${ent.getName()}">
                                                                Cập nhập
                                                            </button>
                                                            <button type="button" class="btn btn-primary detailbtn" 
                                                                    data-href="detailentertainment?id=${ent.getServiceId()}" data-name="${ent.getName()}">
                                                                Chi tiết
                                                            </button>
                                                            <button type="button" class="btn btn-dark changeStatusBtn" 
                                                                    data-href="ChangeStatus?id=${ent.getServiceId()}&page=${requestScope.currentPage}" 
                                                                    data-name="${ent.getName()}">
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
                        </c:if>
                        <c:if test="${ empty entertainmentList}">
                            <div class="alert" style="color: white;
                                 text-align: center;
                                 background-color: #2b2323;
                                 border-radius: 0px;
                                 margin: 0px;
                                 padding: 5px;">${error}</div>
                        </c:if>
                    </div>
                </main>
                <c:set var="nameParam" value="${param.searchName}" />
                <c:set var="typeParam" value="${param.statusType}" />
                <c:set var="queryParams" value="" />
                <c:if test="${not empty param.searchName}">
                    <c:set var="queryParams" value="${queryParams}&searchName=${fn:escapeXml(param.searchName)}" />
                </c:if>
                <c:if test="${not empty param.statusType}">
                    <c:set var="queryParams" value="${queryParams}&statusType=${fn:escapeXml(param.statusType)}" />
                </c:if>
                <c:if test="${not empty entertainmentList}">
                    <div class="pagination d-flex justify-content-center mt-3">
                        <nav aria-label="Page navigation">
                            <ul class="pagination">
                                <!-- Previous page -->
                                <li class="page-item ${requestScope.currentPage == 1 ? 'disabled' : ''}">
                                    <a class="page-link btn-danger" aria-label="Previous"
                                       href="?page=${requestScope.currentPage - 1}${queryParams}">
                                        <span aria-hidden="true">« Trang trước</span>
                                    </a>
                                </li>

                                <!-- Page numbers -->
                                <c:forEach begin="1" end="${requestScope.numberPage}" var="pageNum">
                                    <li class="page-item ${requestScope.currentPage == pageNum ? 'active' : ''}" >
                                        <a class="page-link btn-danger" href="?page=${pageNum}${queryParams}">${pageNum}</a>
                                    </li>
                                </c:forEach>

                                <!-- Next page -->
                                <li class="page-item ${requestScope.currentPage == requestScope.numberPage ? 'disabled' : ''}">
                                    <a class="page-link btn-danger" aria-label="Next"
                                       href="?page=${requestScope.currentPage + 1}${queryParams}">
                                        <span aria-hidden="true">Trang sau »</span>
                                    </a>
                                </li>
                            </ul>
                        </nav>
                    </div>
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
             * JavaScript functions for managing entertainment list interactions.
             */

            // Handle change status button clicks
            const changeStatusButtons = document.querySelectorAll(".changeStatusBtn");
            changeStatusButtons.forEach(button => {
                button.addEventListener("click", function (e) {
                    e.preventDefault();
                    const statusUrl = this.dataset.href;
                    const entertainmentName = this.dataset.name;
                    Swal.fire({
                        title: `Bạn có chắc chắn muốn thay đổi trạng thái ${entertainmentName} ?`,
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
                                window.location.href = statusUrl;
                            });
                        }
                    });
                });
            });

            // Handle detail button clicks
            const detailButtons = document.querySelectorAll(".detailbtn");
            detailButtons.forEach(button => {
                button.addEventListener("click", function (e) {
                    e.preventDefault();
                    const detailUrl = this.dataset.href;
                    window.location.href = detailUrl;
                });
            });

            // Handle update button clicks
            const updateButtons = document.querySelectorAll(".updatebtn");
            updateButtons.forEach(button => {
                button.addEventListener("click", function (e) {
                    e.preventDefault();
                    const updateUrl = this.dataset.href;
                    window.location.href = updateUrl;
                });
            });

            // Handle status filter change
            const statusFilterDropdown = document.querySelector("#statusFilter");
            const searchNameInput = document.querySelector('input[name="searchName"]');
            let currentUrl = new URL(window.location.href);
            statusFilterDropdown.addEventListener("change", (e) => {
                e.preventDefault();
                const filterKeyword = statusFilterDropdown.value;
                if (filterKeyword) {
                    currentUrl.searchParams.delete("page");
                    currentUrl.searchParams.set("statusType", filterKeyword);
                } else {
                    currentUrl.searchParams.delete("statusType");
                }
                const searchNameValue = searchNameInput.value.trim();
                if (searchNameValue) {
                    currentUrl.searchParams.set("searchName", searchNameValue);
                } else {
                    currentUrl.searchParams.delete("searchName");
                }
                window.location.href = currentUrl.href;
            });

            // Set initial status filter value
            const urlParams = new URLSearchParams(window.location.search);
            const currentStatus = urlParams.get("statusType");
            if (currentStatus) {
                statusFilterDropdown.value = currentStatus;
            } else {
                statusFilterDropdown.value = "";
            }

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

