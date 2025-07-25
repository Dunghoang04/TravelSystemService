<%--
 * Copyright (C) 2025, Group 6.
 * ProjectCode/Short Name of Application: TravelSystemService
 * Support Management and Provide Travel Service System
 *
 * Record of change:
 * DATE        Version    AUTHOR            DESCRIPTION
 * 2025-07-18  1.0        Hoang Tuan Dung        First implementation
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
                text-align: left;
                max-width: 425px;
                font-size: 13px;
            }
            .btn {
                text-decoration: none;
                padding: 5px 10px;
            }
            a, a:hover {
                color: #000;
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


            .btn{
                margin-left: 5px;
                font-size: 13px;
            }
            .box-action {
                display: flex;
                justify-content: space-evenly;
            }

            table tr td .btn-status {
                display: flex;
                align-items: center;
                justify-content: center;
            }
            .detail .back a {
                border: solid 1px #000;
                padding: 10px 17px;
                color: #000;
                border-radius: 10px;
                font-size: 16px;
                text-decoration: none;
            }
            .detail .back a:hover {
                background: #000;
                color: #fff;
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
                        <div class="row" style="align-items: center; justify-content: space-between;">
                            <div class="col-md-6">
                                <div style="display: flex; align-items: center; gap: 15px; margin-bottom: 10px; justify-content: space-between;">
                                    <div style="display: flex; align-items: center; gap: 10px;">
                                        <label class="form-label">Lọc theo trạng thái:</label>
                                        <select class="form-select" name="statusType" id="statusFilter">
                                            <option value="">-Trạng thái-</option>
                                            <option value="1" ${requestScope.statusType == "1" ? 'selected' : ''}>Đã thanh toán</option>
                                            <option value="2" ${requestScope.statusType == "2" ? 'selected' : ''}>Đại lí yêu cầu hủy</option>
                                            <option value="3" ${requestScope.statusType == "3" ? 'selected' : ''}>Hủy bởi đại lí</option>
                                            <option value="4" ${requestScope.statusType == "4" ? 'selected' : ''}>Hoàn thành chuyến đi</option>
                                            <option value="5" ${requestScope.statusType == "5" ? 'selected' : ''}>Đã yêu cầu hoàn tiền</option>
                                            <option value="6" ${requestScope.statusType == "6" ? 'selected' : ''}>Hủy bởi du khách</option>
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
                                                <td>${requestScope.startIndex + status.index}</td>
                                                <td>${booked.tourName}</td>
                                                <td>${booked.firstName} ${booked.lastName}</td>
                                                <td>${booked.bookDate}</td>
                                                <td>${booked.code}</td>
                                                <td><fmt:formatNumber value="${booked.totalPrice}" type="number" groupingUsed="true"/>VNĐ</td>
                                                <td>
                                                    <div class="btn-status">
                                                        <c:if test="${booked.status == 1}">
                                                            <button type="button" class="btn btn-success">Đã thanh toán</button>
                                                        </c:if>
                                                        <c:if test="${booked.status == 2}">
                                                            <button type="button" class="btn btn-warning">Đại lí yêu cầu hủy</button>
                                                        </c:if>
                                                        <c:if test="${booked.status == 3}">
                                                            <button type="button" class="btn btn-danger">Hủy bởi đại lí</button>
                                                        </c:if>  
                                                        <c:if test="${booked.status == 4}">
                                                            <button type="button" class="btn btn-info">Hoàn thành chuyến đi</button>
                                                        </c:if>  
                                                        <c:if test="${booked.status == 5}">
                                                            <button type="button" class="btn btn-secondary">Đã yêu cầu hoàn tiền</button>
                                                        </c:if> 
                                                        <c:if test="${booked.status == 6}">
                                                            <button type="button" class="btn btn-danger">Hủy bởi du khách</button>
                                                        </c:if> 
                                                            <c:if test="${booked.status == 7}">
                                                            <button type="button" class="btn btn-danger">Hủy bởi du khách</button>
                                                        </c:if> 
                                                    </div>
                                                </td>
                                                <td class="box-button">
                                                    <div class="box-action" style="display: flex">
                                                        <button type="button" class="btn btn-primary detailbtn" 
                                                                data-href="DetailTourBooked?id=${booked.bookID}">
                                                            Chi tiết
                                                        </button>
                                                        <c:if test="${booked.status==1}">
                                                            <button type="button" class="btn btn-danger btn-status" 
                                                                    data-bs-toggle="modal" 
                                                                    data-bs-target="#cancelTourModal" 
                                                                    data-book-id="${booked.bookID}" 
                                                                    data-user-id="${booked.userID}">
                                                                <i class="fa fa-times"></i> Hủy chuyến đi
                                                            </button>
                                                        </c:if>

                                                    </div>
                                                </td>
                                            </tr>
                                        </c:forEach>
                                    </table>
                                    <div class="modal fade" id="cancelTourModal" tabindex="-1" aria-hidden="true">
                                        <div class="modal-dialog">
                                            <form action="ChangeStatusBooked" method="post" class="modal-content" onsubmit="return validateReason()">
                                                <div class="modal-header">
                                                    <h5 class="modal-title">Hủy chuyến đi</h5>
                                                    <button type="button" class="btn-close" data-bs-dismiss="modal" aria-label="Close"></button>
                                                </div>
                                                <div class="modal-body">
                                                    <input type="hidden" name="bookID" id="bookId">
                                                    <input type="hidden" name="userID" id="userId">
                                                    <input type="hidden" name="page" value="${requestScope.currentPage}">
                                                    <div class="mb-3">
                                                        <label class="form-label">Lý do hủy:</label>
                                                        <textarea class="form-control" name="reason" id="cancelReason" required minlength="10"
                                                                  placeholder="Nhập lý do hủy (ít nhất 10 ký tự)" rows="3"></textarea>
                                                    </div>
                                                </div>
                                                <div class="modal-footer">
                                                    <button type="submit" class="btn btn-danger">Xác nhận hủy</button>
                                                    <button type="button" class="btn btn-secondary" data-bs-dismiss="modal">Đóng</button>
                                                </div>
                                            </form>
                                        </div>
                                    </div>
                                </div>
                            </div>
                            <div class="pagination d-flex justify-content-center mt-3">
                                <nav aria-label="Page navigation">
                                    <ul class="pagination">
                                        <li class="page-item ${requestScope.currentPage == 1 ? 'disabled' : ''}">
                                            <a class="page-link btn-danger" aria-label="Previous"
                                               href="ManageTourBooked?page=${requestScope.currentPage - 1}${queryParams}">
                                                <span aria-hidden="true">« Trang trước</span>
                                            </a>
                                        </li>
                                        <c:forEach begin="1" end="${requestScope.numberPage}" var="pageNum">
                                            <li class="page-item ${requestScope.currentPage == pageNum ? 'active' : ''}">
                                                <a class="page-link btn-danger" href="ManageTourBooked?page=${pageNum}${queryParams}">
                                                    <span aria-hidden="true">${pageNum}</span>
                                                </a>
                                            </li>
                                        </c:forEach>
                                        <li class="page-item ${requestScope.currentPage == requestScope.numberPage ? 'disabled' : ''}">
                                            <a class="page-link btn-danger" aria-label="Next"
                                               href="ManageTourBooked?page=${requestScope.currentPage + 1}${queryParams}">
                                                <span aria-hidden="true">Trang sau »</span>
                                            </a>
                                        </li>
                                    </ul>
                                </nav>
                            </div>
                        </c:if>
                        <c:if test="${empty listBookDetail}">
                            <div class="alert" style="color: white; text-align: center; background-color: #2b2323; border-radius: 0px; margin: 0px; padding: 5px;">
                                Hiện chưa có đơn đặt chuyến đi nào
                            </div>
                            <div class="back" style="margin-top: 14px;">
                                <a href="ManageTourBooked" style="color: #000; padding: 5px 10px; border-radius: 5px; border: solid 1px #002; text-decoration: none;">
                                    <i class="fa-solid fa-arrow-left"></i>
                                    <span class="backTo">Trở lại</span>
                                </a>
                            </div>     
                        </c:if>
                    </div>
                </main>
                <%@include file="../layout/footerStaff.jsp" %>
            </div>
        </div>
        <script src="https://cdn.jsdelivr.net/npm/sweetalert2@11"></script>

        <c:if test="${requestScope.cancel=='true'}">
            <script>
                                                Swal.fire({
                                                    icon: 'success',
                                                    title: 'Hủy chuyến đi thành công',
                                                    text: 'Yêu cầu hủy đã được ghi nhận.',
                                                    showConfirmButton: false,
                                                    timer: 2000
                                                }).then(() => {
                                                    let url = new URL(window.location.href);
                                                    url.searchParams.delete("cancel");
                                                    window.location.assign(url.href); // Sử dụng assign để chuyển hướng
                                                });
            </script>
        </c:if>
        <c:if test="${requestScope.cancel=='false'}">
            <script>
                Swal.fire({
                    icon: 'error',
                    title: 'Hủy chuyến đi thất bại',
                    text: 'Đã xảy ra lỗi. Vui lòng thử lại.',
                    confirmButtonText: 'OK'
                }).then(() => {
                    let url = new URL(window.location.href);
                    url.searchParams.delete("cancel");
                    window.location.assign(url.href);
                });
            </script>
        </c:if>

        <script>
            document.addEventListener("DOMContentLoaded", function () {
                // Khởi tạo URL để quản lý query params
                const url = new URL(window.location.href);

                // Handle status filter
                const statusFilter = document.querySelector("#statusFilter");
                const nameInput = document.querySelector('input[name="searchName"]');
                if (statusFilter) {
                    statusFilter.addEventListener("change", (e) => {
                        e.preventDefault();
                        const keyword = statusFilter.value;
                        if (keyword) {
                            url.searchParams.set("statusType", keyword);
                        } else {
                            url.searchParams.delete("statusType");
                        }
                        const nameValue = nameInput ? nameInput.value.trim() : "";
                        if (nameValue) {
                            url.searchParams.set("searchName", nameValue);
                        } else {
                            url.searchParams.delete("searchName");
                        }
                        window.location.assign(url.href);
                    });

                    // Khởi tạo giá trị dropdown filter
                    const currentStatus = url.searchParams.get("statusType");
                    statusFilter.value = currentStatus || "";
                } else {
                    console.warn("Status filter not found");
                }

                // Gán bookID và userID vào form khi nhấn nút "Hủy chuyến đi"
                document.querySelectorAll('.btn-status').forEach(button => {
                    button.addEventListener('click', function () {
                        const bookIdInput = document.getElementById('bookId');
                        const userIdInput = document.getElementById('userId');
                        if (bookIdInput && userIdInput) {
                            bookIdInput.value = this.dataset.bookId || "";
                            userIdInput.value = this.dataset.userId || "";
                        } else {
                            console.error("Book ID or User ID input not found");
                        }
                    });
                });

                // Kiểm tra lý do trước khi submit form hủy
                const cancelForm = document.querySelector('#cancelTourModal .modal-content');
                if (cancelForm) {
                    cancelForm.addEventListener('submit', function (e) {
                        const reason = document.getElementById('cancelReason').value.trim();
                        if (reason.length < 10) {
                            e.preventDefault();
                            Swal.fire({
                                icon: 'warning',
                                title: 'Lý do không hợp lệ',
                                text: 'Lý do hủy phải có ít nhất 10 ký tự.',
                                confirmButtonText: 'OK'
                            });
                        }
                    });
                } else {
                    console.warn("Cancel form not found");
                }

                // Handle detail button clicks
                const detailButtons = document.querySelectorAll(".detailbtn");
                console.log("Detail buttons found:", detailButtons.length);
                detailButtons.forEach(button => {
                    button.addEventListener("click", function (e) {
                        e.preventDefault();
                        const detailUrl = this.dataset.href;
                        if (detailUrl) {
                            console.log("Navigating to:", detailUrl);
                            window.location.assign(detailUrl);
                        } else {
                            console.error("Invalid detail URL");
                            Swal.fire({
                                icon: 'error',
                                title: 'Lỗi',
                                text: 'Không tìm thấy URL chi tiết.',
                                confirmButtonText: 'OK'
                            });
                        }
                    });
                });

                // Debug back button
                const buttonBack = document.querySelector(".backTo");
                if (buttonBack) {
                    buttonBack.addEventListener("click", () => {
                        console.log("Back button clicked, current URL:", window.location.href);
                    });
                }
            });
        </script>

        <script src="https://cdn.jsdelivr.net/npm/bootstrap@5.2.3/dist/js/bootstrap.bundle.min.js" crossorigin="anonymous"></script>
        <script src="./assets/js/scripts.js"></script>
        <script src="https://cdnjs.cloudflare.com/ajax/libs/Chart.js/2.8.0/Chart.min.js" crossorigin="anonymous"></script>
        <script src="./assets/demo/chart-area-demo.js"></script>
        <script src="./assets/demo/chart-bar-demo.js"></script>
        <script src="https://cdn.jsdelivr.net/npm/simple-datatables@7.1.2/dist/umd/simple-datatables.min.js" crossorigin="anonymous"></script>
        <script src="./assets/js/datatables-simple-demo.js"></script>

    </body>
</html>