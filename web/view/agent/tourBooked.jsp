<%-- 
    Document   : TourBooked
    Created on : Jun 30, 2025, 11:12:21 PM
    Author     : ad
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
        <title>Dashboard - SB Admin</title>
        <link href="https://cdn.jsdelivr.net/npm/simple-datatables@7.1.2/dist/style.min.css" rel="stylesheet" />
        <link href="${pageContext.request.contextPath}/assets/css/styles2.css" rel="stylesheet" />
        <script src="https://use.fontawesome.com/releases/v6.3.0/js/all.js" crossorigin="anonymous"></script>

        <!-- Template Stylesheet -->
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
                text-align: left;
                max-width: 425px;
                font-size: 13px;
            }
            .btn{
                text-decoration: none;
                padding: 5px 10px;
            }
            a, a:hover{
                color: #000;
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
                margin-left:5px;
                font-size: 13px;
            }
            .box-action{
                display: flex
                    ;
                justify-content: space-evenly;
            }

            table tr td .btn-status{
                display: flex
                    ;
                align-items: center;
                justify-content: center;
            }
            .detail .back a{
                border: solid 1px #000;
                padding: 10px 17px;
                color: #000;
                border-radius: 10px;
                font-size: 16px;
                text-decoration: none;
            }
            .detail .back a:hover{
                background: #000;
                color: #000;
            }


        </style>
    </head>
    <body>

        <%@ include file="../layout/headerAdmin.jsp" %>
        <div id="layoutSidenav">
            <%@ include file="../layout/sideNavOptionAgent.jsp" %>
            <div id="layoutSidenav_content">
                <main>
                    <div class="container-fluid px-4">
                        <h1 class="mt-4">Quản lí đặt chuyến đi</h1>
                        <div class="row" style="    align-items: center;
                             justify-content: space-between;">
                            <div class="col-md-6">
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
                                </div>
                            </div>
                            <div class="col-md-6">
                                <form action="ManageTourBooked" method="GET" class="d-flex align-items-center">
                                    <input 
                                        type="text" 
                                        name="searchName"
                                        value="${requestScope.searchName}"
                                        maxlength="100"
                                        class="form-control me-2 shadow-sm" 
                                        placeholder="Tìm theo tên khách hàng..." 
                                        style="max-width: 350px; border-radius: 20px; padding: 20px" 
                                        />
                                    <input type="hidden" name="statusType" value="${requestScope.statusType}" />
                                    <button type="submit" class="btn btn-primary d-flex align-items-center px-3" style="border-radius: 20px;">
                                        Tìm kiếm
                                    </button>
                                </form>
                            </div>
                        </div>
                        <c:if test="${not empty listBookDetail}">


                            <div class="col-xl-12 col-md-6">

                                <div class="card-body">

                                    <table style="border: 1px solid">
                                        <thead>
                                            <tr>

                                                <th>STT</th>
                                                <th>Tên chuyến đi</th>
                                                <th>Tên khách hàng</th>
                                                <th>Thời gian đặt hàng</th>
                                                <th>Mã giảm giá</th>
                                                <th>Tổng tiền</th>
                                                <th>Trạng thái</th>
                                                <th>Hoạt Động</th>
                                            </tr>
                                        </thead>

                                        <c:forEach items="${requestScope.listBookDetail}" var="booked" varStatus="status">
                                            <tr>
                                                <td>${requestScope.startIndex+status.index}</td>
                                                <td>${booked.tourName}</td>
                                                <td>${booked.firstName} ${booked.lastName}</td>
                                                <td>${booked.bookDate}</td>
                                                <td>${booked.code}</td>
                                                <td><fmt:formatNumber value="${booked.totalPrice}" type="number" groupingUsed="true"></fmt:formatNumber>VNĐ</td>
                                                    <td>
                                                        <div class="btn-status">
                                                        <c:if test="${booked.status == 1}">
                                                            <button type="button" class="btnStatus active">Đang hoạt động</button>
                                                        </c:if>
                                                        <c:if test="${booked.status == 0}">
                                                            <button type="button" class="btnStatus deactive">Dừng hoạt động</button>
                                                        </c:if>
                                                    </div>
                                                </td>
                                                <td class="box-button">
                                                    <div class="box-action" style="display: flex">
                                                        <button type="button" class="btn btn-primary detailbtn" 
                                                                data-href="DetailTourBooked?id=${booked.bookID}">
                                                            Chi tiết
                                                        </button>
                                                        <button type="button" class="btn btn-dark changeStatusbtn" 
                                                                data-href="ChangeStatusBooked?id=${booked.bookID}&page=${requestScope.currentPage}" >
                                                            Đổi trạng thái
                                                        </button>
                                                    </div>
                                                </td>

                                            </tr>

                                        </c:forEach>

                                    </table>
                                    <div class="pagination">
                                        <c:forEach begin="1" end="${totalPages}" var="i">
                                            <a href="listvoucher?page=${i}" class="${i == currentPage ? 'active' : ''}">${i}</a>
                                        </c:forEach>
                                    </div>  
                                </div>
                            </div>
                        </c:if>
                        <c:if test="${empty listBookDetail}">
                            <div class="alert" style="    color: white;
                                 text-align: center;
                                 background-color: #2b2323;
                                 border-radius: 0px;
                                 margin: 0px;
                                 padding: 5px;">Hiện chưa có đơn đặt chuyến đi nào</div>
                            <div class="back" style="margin-top: 14px;">
                                <a href="ManageTourBooked" style="color: #000;
                                   padding: 5px 10px;
                                   border-radius: 5px;
                                   border: solid 1px #002;
                                   text-decoration: none;">
                                    <i class="fa-solid fa-arrow-left"></i>
                                    <span class="backTo" >Trở lại</span>
                                </a>
                            </div>     
                        </c:if>
                    </div>
                </main>
                <c:if test="${not empty listBookDetail}">
                    <c:set var="nameParam" value="${param.searchName}"/>
                    <c:set var="typeParam" value="${param.statusType}"/>
                    <c:set var="queryParams" value="" />
                    <c:if test="${not empty param.searchName}">
                        <c:set var="queryParams" value="${queryParams}&searchName=${fn:escapeXml(param.searchName)}" />
                    </c:if>
                    <c:if test="${not empty param.statusType}">
                        <c:set var="queryParams" value="${queryParams}&statusType=${fn:escapeXml(param.statusType)}" />
                    </c:if>
                    <div class="pagination d-flex justify-content-center mt-3">
                        <nav aria-label="Page navigation">
                            <ul class="pagination">
                                <li class="page-item ${requestScope.currentPage==1?'disabled':''}">
                                    <a class="page-link btn-danger" aria-label="Previous"
                                       href="?page=${requestScope.currentPage-1}${queryParams}">
                                        <span aria-hidden="true">« Trang trước</span>
                                    </a>
                                </li>
                                <c:forEach begin="1" end="${requestScope.numberPage}" var="pageNum">
                                    <li class="page-item ${requestScope.currentPage==pageNum ? 'active':''}">
                                        <a class="page-link btn-danger" href="?page=${pageNum}${queryParams}">
                                            <span aria-hidden="true">${pageNum}</span>
                                        </a>
                                    </li>
                                </c:forEach>
                                <li class="page-item ${requestScope.currentPage==requestScope.numberPage?'disabled':''}">
                                    <a class="page-link btn-danger" aria-label="Next"
                                       href="?page=${requestScope.currentPage+1}${queryParams}">
                                        <span aria-hidden="true">Trang sau »</span>
                                    </a>
                                </li>
                            </ul>
                        </nav>
                    </div>
                </c:if>
                <%@include file="../layout/footerStaff.jsp" %>
            </div>
        </div>
        <script>
            // Handle detail button clicks
            const detailButtons = document.querySelectorAll(".detailbtn");
            detailButtons.forEach(button => {
                button.addEventListener("click", function (e) {
                    e.preventDefault();
                    const detailUrl = this.dataset.href;
                    window.location.href = detailUrl;
                });
            });

            const statusFilter = document.querySelector("#statusFilter"); // Use id for specificity
            const nameInput = document.querySelector('input[name="searchName"]');
            let url = new URL(window.location.href);
            statusFilter.addEventListener("change", (e) => {
                e.preventDefault();
                const keyword = statusFilter.value;
                // nếu có đảy lên url , còn k -> xóa
                if (keyword) {
                    url.searchParams.set("statusType", keyword);
                } else {
                    url.searchParams.delete("statusType");
                }
                //đồng thời cập nhập giá trị input
                const nameValue = nameInput.value.trim();
                if (nameValue) {
                    url.searchParams.set("searchName", nameValue);
                } else {
                    url.searchParams.delete("searchName");
                }
                window.location.href = url.href;
            });
            // Initialize dropdown value based on URL parameter
            const urlParams = new URLSearchParams(window.location.search);
            const currentStatus = urlParams.get("statusType");
            if (currentStatus) {
                statusFilter.value = currentStatus;
            } else {
                statusFilter.value = ""; // Default to "-Loại nhà hàng-" or "Tất cả"
            }
            
            const buttonBack=document.querySelector(".backTo");
            if(buttonBack){
                let href=new URL(window.location.href);
                buttonBack.addEventListener("click",()=>{
                    Console.log(window.location.href);
                });
            }
            
            const changeStatusbtn = document.querySelectorAll(".changeStatusbtn");
            changeStatusbtn.forEach(btn => {
                btn.addEventListener("click", function (e) {
                    e.preventDefault();
                    const href = this.dataset.href; // lấy URL từ data-href
                    const name = this.dataset.name; // lấy URL từ data-href
                    Swal.fire({
                        title: `Bạn có chắc chắn muốn thay đổi trạng thái nhà hàng ${name} ?`,
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
                                title: 'Đã Đổi trạng thái!',
                                text: 'Thay đổi trạng thái thành công.',
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
        </script>
        <script src="https://cdn.jsdelivr.net/npm/bootstrap@5.2.3/dist/js/bootstrap.bundle.min.js" crossorigin="anonymous"></script>
        <script src="./assets/js/scripts.js"></script>
        <script src="https://cdnjs.cloudflare.com/ajax/libs/Chart.js/2.8.0/Chart.min.js" crossorigin="anonymous"></script>
        <script src="./assets/demo/chart-area-demo.js"></script>
        <script src="./assets/demo/chart-bar-demo.js"></script>
        <script src="https://cdn.jsdelivr.net/npm/simple-datatables@7.1.2/dist/umd/simple-datatables.min.js" crossorigin="anonymous"></script>
        <script src="./assets/js/datatables-simple-demo.js"></script>
        <script src="https://cdn.jsdelivr.net/npm/sweetalert2@11"></script>
    </body>
</html>
