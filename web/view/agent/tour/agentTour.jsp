<%-- 
 * Copyright (C) 2025, Group 6.
 * ProjectCode/Short Name of Application: TravelSystemService 
 * Support Management and Provide Travel Service System 
 *
 * Record of change:
 * DATE        Version    AUTHOR            DESCRIPTION
 * 2025-06-07  1.0        Quynh Mai          First implementation
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
    <meta name="description" content="" />
    <meta name="author" content="" />
    <title>Agent Tour Management</title>
    <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.2.3/dist/css/bootstrap.min.css" rel="stylesheet" crossorigin="anonymous" id="bootstrap-css">
    <link href="https://cdn.datatables.net/1.13.4/css/dataTables.bootstrap5.min.css" rel="stylesheet" />
    <link href="${pageContext.request.contextPath}/assets/css/styles2.css" rel="stylesheet" />
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
            padding: 20px;
            flex: 1;
        }
        .container-centered {
            max-width: 1300px;
            margin: 0 auto;
        }
        h1 {
            color: #28A745;
            font-size: 24px;
            margin-bottom: 20px;
            text-align: center;
        }
        .custom-table {
            background-color: white;
            border-radius: 15px;
            box-shadow: 0 0 5px rgba(0,0,0,0.05);
            padding: 20px;
        }
        .modal {
            display: none;
            position: fixed;
            z-index: 1000;
            left: 0;
            top: 0;
            width: 100%;
            height: 100%;
            overflow: auto;
            background-color: rgba(0,0,0,0.5);
            justify-content: center;
            align-items: center;
        }
        .modal-dialog {
            margin: 10% auto;
            width: 80%;
            max-width: 600px;
        }
        .modal-content {
            background-color: #fff;
            padding: 20px;
            border-radius: 10px;
        }
        .modal-header {
            border-bottom: 1px solid #ccc;
            padding-bottom: 10px;
        }
        .modal-title {
            margin: 0;
            color: #000;
        }
        .close {
            float: right;
            font-size: 20px;
            cursor: pointer;
        }
        .modal-body input, .modal-body select, .modal-body textarea {
            width: 100%;
            padding: 10px;
            margin-bottom: 10px;
            border: 1px solid #ccc;
            border-radius: 6px;
        }
        .image-preview {
            max-height: 150px;
            max-width: 100%;
            object-fit: contain;
            display: none;
            margin-bottom: 10px;
        }
        .button {
            width: auto;
            padding: 6px 12px;
            background: #28A745;
            border: none;
            border-radius: 6px;
            color: white;
            font-size: 14px;
            font-weight: bold;
            cursor: pointer;
            transition: 0.3s;
        }
        .button:hover {
            background: #218838;
        }
        .required {
            color: red;
            margin-left: 2px;
        }
        .container {
            margin-left: auto;
            margin-right: auto;
            padding-left: 15px;
            padding-right: 15px;
        }
        .small-input {
            width: 30%;
        }
        .large-input {
            width: 100%;
        }
        .add-tour-button {
            margin-left: auto;
            display: inline-block;
        }
    </style>
</head>
<body>
    <%@include file="/view/layout/headerAdmin.jsp" %>
    <div id="layoutSidenav">
        <jsp:include page="/view/layout/sideNavOptionAgent.jsp"></jsp:include>  
        <div id="layoutSidenav_content">
            <main>
                <div class="container mt-3 mb-3">
                    <h1>Quản lý Tours</h1>
                    <div class="custom-table bg-white p-3 rounded">
                        <div class="mb-3" style="text-align: right">
                            <button class="button add-tour-button" onclick="openInsertModal()">Thêm Tour Mới</button>
                        </div>
                        <table id="tourTable" class="table table-striped">
                            <thead>
                                <tr>
                                    <th>STT</th>
                                    <th>Tên Tour</th>
                                    <th>Nơi Bắt Đầu</th>
                                    <th>Điểm Đến</th>
                                    <th>Ngày Bắt Đầu</th>
                                    <th>Ngày Kết Thúc</th>
                                    <th>Giá Người Lớn</th>
                                    <th>Trạng Thái</th>
                                </tr>
                            </thead>
                            <tbody>
                                <c:forEach items="${requestScope.tours}" var="tour" varStatus="loop">
                                    <tr>
                                        <td></td> <!-- Số thứ tự sẽ được gán bằng JavaScript -->
                                        <td>${tour.tourName}</td>
                                        <td>${tour.startPlace}</td>
                                        <td>${tour.endPlace}</td>
                                        <td><fmt:formatDate value="${tour.startDay}" pattern="dd/MM/yyyy" /></td>
                                        <td><fmt:formatDate value="${tour.endDay}" pattern="dd/MM/yyyy" /></td>
                                        <td><fmt:formatNumber value="${tour.adultPrice}"/>đ</td>
                                        <td>
                                            <c:choose>
                                                <c:when test="${tour.status == 1}">Hoạt động</c:when>
                                                <c:otherwise>Không hoạt động</c:otherwise>
                                            </c:choose>
                                        </td>
                                        <td>
                                            <a href="${pageContext.request.contextPath}/ManageTour?service=viewTourDetail&tourId=${tour.tourID}" class="btn btn-sm" style="background-color: #86B817; color: white">Xem chi tiết</a>                                                 
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
    <script src="https://cdn.datatables.net/1.13.4/js/jquery.dataTables.min.js" crossorigin="anonymous"></script>
    <script src="https://cdn.datatables.net/1.13.4/js/dataTables.bootstrap5.min.js" crossorigin="anonymous"></script>
    <script src="${pageContext.request.contextPath}/assets/js/scripts.js"></script>
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
                "pageLength": 10,
                "lengthMenu": [5, 10, 20, 50],
                "order": [[0, "desc"]],
                "columnDefs": [
                    {"width": "30px", "targets": 0},
                    {"width": "200px", "targets": 1},
                    {"width": "80px", "targets": [2, 3]},
                    {"width": "60px", "targets": [4, 5, 6]},
                    {"width": "60px", "targets": [7]},                    
                    {"width": "150px", "targets": [ 8]}
                ],
                "drawCallback": function(settings) {
                    var api = this.api();
                    api.column(0, {page: 'current'}).nodes().each(function(cell, i) {
                        cell.innerHTML = i + 1;
                    });
                }
            });
        });

        function openInsertModal() {
            document.getElementById("insertModal").style.display = 'flex';
            document.getElementById("insertPreview").style.display = 'none';
            document.getElementById("insertImagePath").innerText = '';
        }

        function closeInsertModal() {
            document.getElementById("insertModal").style.display = 'none';
        }

        function previewImage(event, previewId, pathId) {
            const file = event.target.files[0];
            if (file) {
                const reader = new FileReader();
                reader.onload = function (e) {
                    document.getElementById(previewId).src = e.target.result;
                    document.getElementById(previewId).style.display = 'block';
                    document.getElementById(pathId).innerText = file.name;
                };
                reader.readAsDataURL(file);
            }
        }

        window.onclick = function (event) {
            if (event.target.className === "modal") {
                closeInsertModal();
            }
        };
    </script>
</body>
</html>