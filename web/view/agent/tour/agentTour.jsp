<%--
 * Copyright (C) 2025, Group 6.
 * ProjectCode/Short Name of Application: TravelSystemService 
 * Support Management and Provide Travel Service System 
 *
 * Record of change:
 * DATE        Version    AUTHOR            DESCRIPTION
 * 2025-06-20  1.0        Quynh Mai         First implementation based on agent profile requirements
--%>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<!DOCTYPE html>
<html lang="en">
    <head>
        <meta charset="utf-8" />
        <meta http-equiv="X-UA-Compatible" content="IE=edge" />
        <meta name="viewport" content="width=device-width, initial-scale=1, shrink-to-fit=no" />
        <title>Quản lý Tour</title>
        <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.2.3/dist/css/bootstrap.min.css" rel="stylesheet" crossorigin="anonymous">
        <link href="${pageContext.request.contextPath}/assets/css/styles2.css" rel="stylesheet" />
        <link href="https://cdn.datatables.net/1.11.5/css/dataTables.bootstrap5.min.css" rel="stylesheet" />
        <link href="https://cdnjs.cloudflare.com/ajax/libs/font-awesome/6.0.0-beta3/css/all.min.css" rel="stylesheet">
        <link rel="stylesheet" href="https://cdn.jsdelivr.net/npm/sweetalert2@11/dist/sweetalert2.min.css">
        <style>
            html, body {
                height: 100%;
                margin: 0;
                padding: 0;
                background-color: #f4f6f8;
                color: #333;
                overflow-x: hidden;
            }
            .sidebar {
                position: fixed;
                top: 0;
                left: 0;
                width: 250px;
                height: 100vh;
                background-color: #f8f9fa;
                overflow-y: auto;
            }
            .content {
                margin-left: 250px;
                padding: 20px;
                flex: 1;
            }
            .container-centered {
                max-width: 1200px;
                margin: 0 auto;
            }
            h1 {
                color: #28A745;
                font-size: 24px;
                margin-bottom: 20px;
                text-align: center;
            }
            .table-responsive {
                margin-top: 20px;
                background-color: white;
                border-radius: 20px;
                padding: 40px;
            }
            .status-filter button {
                padding: 8px 16px;
                border: none;
                border-radius: 20px;
                color: white;
                cursor: pointer;
                transition: transform 0.3s, font-weight 0.3s;
            }
            .status-filter button.active {
                transform: scale(1.1);
                font-weight: bold;
            }
            .dataTables_paginate {
                margin-top: 10px;
            }
            .error-message {
                color: #dc3545;
                font-size: 16px;
                margin-bottom: 15px;
                text-align: center;
                background-color: #f8d7da;
                padding: 10px;
                border-radius: 5px;
            }
        </style>
    </head>
    <body>
        <%@include file="/view/layout/headerAdmin.jsp" %>
        <div id="layoutSidenav">
            <jsp:include page="/view/layout/sideNavOptionAgent.jsp" />
            <div id="layoutSidenav_content">
                <main>
                    <div class="container-centered mt-3 mb-3">
                        <h1>Quản lý Tour</h1>
                        <c:if test="${not empty requestScope.errorMessage}">
                            <div class="error-message">
                                <c:out value="${requestScope.errorMessage}" />
                            </div>
                        </c:if>
                        <c:if test="${not empty param.successMessage}">
                            <div class="alert alert-success" role="alert">
                                ${param.successMessage}
                            </div>
                        </c:if>
                        <form action="${pageContext.request.contextPath}/ListTour?service=list" method="get">
                            <input type="hidden" name="service" value="list">
                            <div class="status-filter">
                                <button style="background-color: #6c757d;" type="submit" name="status" value="all" class="all ${param.status == null || param.status == 'all' ? 'active' : ''}">Tất cả</button>
                                <button style="background-color: #007bff;" type="submit" name="status" value="2" class="pending ${param.status == '2' ? 'active' : ''}">Chờ duyệt</button>
                                <button style="background-color: #dc3545;" type="submit" name="status" value="3" class="rejected ${param.status == '3' ? 'active' : ''}">Bị từ chối</button>
                                <button style="background-color: #28a745;" type="submit" name="status" value="1" class="activeS ${param.status == '1' ? 'active' : ''}">Hoạt động</button>
                                <button style="background-color: #ffc107;" type="submit" name="status" value="0" class="inactive ${param.status == '0' ? 'active' : ''}">Không hoạt động</button>
                            </div>
                        </form>
                        <div class="table-responsive">
                            <div style="text-align: right; margin-bottom: 10px;">
                                <a style="color: white; background-color: #28A745; border: none; padding: 6px 20px;" href="${pageContext.request.contextPath}/AddTour?service=addStep1" class="btn btn-info btn-sm">Thêm Tour</a>
                            </div>
                            <table id="tourTable" class="table table-striped" style="width:100%">
                                <thead>
                                    <tr>
                                        <th>STT</th>
                                        <th>Tên Tour</th>
                                        <th>Điểm Đến</th>
                                        <th>Ngày Bắt Đầu</th>
                                        <th>Ngày Kết Thúc</th>
                                        <th>Trạng Thái</th>
                                        <th>Hành Động</th>
                                    </tr>
                                </thead>
                                <tbody>
                                    <c:forEach items="${requestScope.tours}" var="tour" varStatus="loop">
                                        <tr>
                                            <td></td>
                                            <td>${tour.tourName}</td>
                                            <td>${tour.endPlace}</td>
                                            <td><fmt:formatDate value="${tour.startDay}" pattern="dd/MM/yyyy" /></td>
                                            <td><fmt:formatDate value="${tour.endDay}" pattern="dd/MM/yyyy" /></td>
                                            <td>
                                                <c:choose>
                                                    <c:when test="${tour.status == 2}">Chờ duyệt</c:when>
                                                    <c:when test="${tour.status == 1}">Hoạt động</c:when>
                                                    <c:when test="${tour.status == 3}">Bị từ chối</c:when>
                                                    <c:when test="${tour.status == 0}">Không hoạt động</c:when>
                                                </c:choose>
                                            </td>
                                            <td style="text-align: center;"> 
                                                <a style="color: white; background-color: #86B817; border: none" href="${pageContext.request.contextPath}/ListTour?service=viewTourDetail&tourId=${tour.tourID}" class="btn btn-info btn-sm">Chi tiết</a>                                          
                                                <a style="color: white; background-color: #28A745; border: none" href="${pageContext.request.contextPath}/EditTour?tourId=${tour.tourID}" class="btn btn-success btn-sm ${tour.status != 1 ? 'disabled' : ''}">Sửa</a>
                                                <button type="button" class="btn btn-danger btn-sm changeStatusbtn ${tour.status != 1 ? 'btn-disabled' : ''}" data-tour-id="${tour.tourID}" data-name="${tour.tourName}" ${tour.status != 1 ? 'disabled' : ''}>Đổi trạng thái</button>
                                            </td>
                                        </tr>
                                    </c:forEach>
                                </tbody>
                            </table>
                        </div>
                    </div>
                </main>
                <footer class="bg-white p-3">
                    <div class="container">
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
        <script src="https://code.jquery.com/jquery-3.6.0.min.js" crossorigin="anonymous"></script>
        <script src="https://cdn.jsdelivr.net/npm/bootstrap@5.2.3/dist/js/bootstrap.bundle.min.js" crossorigin="anonymous"></script>
        <script src="https://cdn.datatables.net/1.11.5/js/jquery.dataTables.min.js"></script>
        <script src="https://cdn.datatables.net/1.11.5/js/dataTables.bootstrap5.min.js"></script>
        <script src="https://cdn.jsdelivr.net/npm/sweetalert2@11"></script>
        <script>
            $(document).ready(function () {
                var table = $('#tourTable').DataTable({
                    "language": {
                        "sProcessing": "Đang xử lý...",
                        "sLengthMenu": "Hiển thị _MENU_ mục",
                        "sZeroRecords": "Không tìm thấy dữ liệu",
                        "sInfo": "Hiển thị từ _START_ đến _END_ của _TOTAL_ mục",
                        "sInfoEmpty": "Hiển thị từ 0 đến 0 của 0 mục",
                        "sInfoFiltered": "(được lọc từ _MAX_ mục)",
                        "sInfoPostFix": "",
                        "sSearch": "Tìm kiếm:",
                        "sUrl": "",
                        "oPaginate": {
                            "sFirst": "Đầu",
                            "sPrevious": "Trước",
                            "sNext": "Tiếp",
                            "sLast": "Cuối"
                        },
                        "oAria": {
                            "sSortAscending": ": Sắp xếp tăng dần",
                            "sSortDescending": ": Sắp xếp giảm dần"
                        }
                    },
                    "pageLength": 5,
                    "lengthMenu": [5, 10, 20, 50],
                    "paging": true,
                    "lengthChange": true,
                    "searching": true,
                    "info": true,
                    "order": [[0, "desc"]],
                    "columnDefs": [
                        {"width": "20px", "targets": 0},
                        {"width": "200px", "targets": 1},
                        {"width": "80px", "targets": [2]},
                        {"width": "60px", "targets": [3, 4]},
                        {"width": "60px", "targets": [5]},
                        {"width": "200px", "targets": [6]}
                    ],
                    "drawCallback": function (settings) {
                        var api = this.api();
                        api.column(0, {page: 'current'}).nodes().each(function (cell, i) {
                            cell.innerHTML = i + 1 + (api.page.info().page * api.page.info().length);
                        });
                    }
                });

                // Check for lastPage parameter and navigate to last page
                const urlParams = new URLSearchParams(window.location.search);
                if (urlParams.get('lastPage') === 'true') {
                    table.page('last').draw('page');
                }

                // Check for success pop-up and clear it immediately after display
            <c:if test="${not empty sessionScope.showSuccessPopup}">
                Swal.fire({
                    title: '${sessionScope.showSuccessPopup == "add" ? "Thêm tour thành công!" : "Sửa tour thành công!"}',
                    text: '${sessionScope.successMessage}',
                    icon: 'success',
                    timer: 1500,
                    showConfirmButton: false
                });
                // Clear session attributes to prevent re-display
                <% session.removeAttribute("showSuccessPopup"); %>
                <% session.removeAttribute("successMessage"); %>
            </c:if>
               


                const changeStatusbtn = document.querySelectorAll(".changeStatusbtn");
                changeStatusbtn.forEach(btn => {
                    btn.addEventListener("click", function (e) {
                        e.preventDefault();
                        const tourId = this.dataset.tourId;
                        const name = this.dataset.name;
                        Swal.fire({
                            title: `Bạn có chắc chắn muốn thay đổi trạng thái tour ${name}?`,
                            text: "Hành động này không thể hoàn tác!",
                            icon: 'warning',
                            showCancelButton: true,
                            confirmButtonColor: '#d33',
                            cancelButtonColor: '#3085d6',
                            confirmButtonText: 'Thay đổi',
                            cancelButtonText: 'Hủy'
                        }).then((result) => {
                            if (result.isConfirmed) {
                                $.ajax({
                                    url: '${pageContext.request.contextPath}/ListTour',
                                    method: 'GET',
                                    data: {
                                        service: 'delete',
                                        tourId: tourId,
                                        status: 0 // Đặt trạng thái thành "Không hoạt động" (0)
                                    },
                                    success: function (response) {
                                        Swal.fire({
                                            title: 'Đã Đổi trạng thái!',
                                            text: 'Thay đổi trạng thái thành công.',
                                            icon: 'success',
                                            timer: 1500,
                                            showConfirmButton: false
                                        }).then(() => {
                                            // Chuyển hướng đến trang danh sách tour không hoạt động
                                            window.location.href = "${pageContext.request.contextPath}/ListTour?service=list&status=0";
                                        });
                                    },
                                    error: function (xhr, status, error) {
                                        Swal.fire({
                                            title: 'Lỗi!',
                                            text: 'Không thể thay đổi trạng thái. Vui lòng thử lại.',
                                            icon: 'error',
                                            confirmButtonText: 'OK'
                                        });
                                    }
                                });
                            }
                        });
                    });
                });

                if (!$.fn.DataTable.isDataTable('#tourTable')) {
                    console.error("DataTable failed to initialize on #tourTable");
                }
            });
            
            

        </script>
    </body>
</html>