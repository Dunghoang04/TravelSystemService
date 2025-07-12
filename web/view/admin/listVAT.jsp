<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html lang="en">
    <head>
        <meta charset="utf-8" />
        <meta http-equiv="X-UA-Compatible" content="IE=edge" />
        <meta name="viewport" content="width=device-width, initial-scale=1, shrink-to-fit=no" />
        <meta name="description" content="VAT Management for TravelSystemService" />
        <meta name="author" content="Group 6" />
        <title>Quản Lý VAT - Admin</title>
        <link href="https://cdn.datatables.net/1.13.4/css/dataTables.bootstrap5.min.css" rel="stylesheet" />
        <link href="${pageContext.request.contextPath}/assets/css/styles2.css" rel="stylesheet" />
        <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.0/dist/css/bootstrap.min.css" rel="stylesheet" />
        <script src="https://use.fontawesome.com/releases/v6.3.0/js/all.js" crossorigin="anonymous"></script>
        <style>
            .card {
                transition: transform 0.2s;
            }
            .card:hover {
                transform: translateY(-5px);
            }
            .content {
                margin: 0 auto;
                width: 1200px;
            }
            .table-responsive {
                overflow-x: auto;
            }
            .action-btn {
                margin-right: 5px;
                padding: 4px 8px;
                font-size: 0.875rem;
            }
            .filter-section {
                margin-bottom: 20px;
                display: flex;
                flex-direction: column;
                gap: 15px;
                align-items: flex-start;
            }
            .custom-select {
                padding: 5px;
                border-radius: 4px;
                width: 200px;
            }
            .table th, .table td {
                text-align: center;
                vertical-align: middle;
                padding: 8px;
            }
            .table td:nth-child(3) {
                max-width: 200px;
                word-wrap: break-word;
                overflow-wrap: break-word;
            }
            .status-active {
                background-color: #28a745;
                color: white;
                padding: 4px 12px;
                border-radius: 20px;
                border: none;
                cursor: default;
                display: inline-block;
                vertical-align: middle;
            }
            .status-inactive {
                background-color: #dc3545;
                color: white;
                padding: 4px 12px;
                border-radius: 20px;
                border: none;
                cursor: default;
                display: inline-block;
                vertical-align: middle;
            }
            .status-expired {
                background-color: #6c757d;
                color: white;
                padding: 4px 12px;
                border-radius: 20px;
                border: none;
                cursor: default;
                display: inline-block;
                vertical-align: middle;
            }
            .btn-detail {
                background-color: #007bff;
                color: white;
                border: none;
                padding: 4px 12px;
                border-radius: 20px;
            }
            .btn-status {
                background-color: #6c757d;
                color: white;
                border: none;
                padding: 4px 12px;
                border-radius: 20px;
            }
            .btn-disabled {
                background-color: #adb5bd;
                color: #fff;
                cursor: not-allowed;
                opacity: 0.65;
            }
            .card-header {
                display: flex;
                justify-content: space-between;
                align-items: center;
            }
            .error-message {
                color: #dc3545;
                font-size: 1rem;
                font-weight: bold;
                margin-bottom: 1rem;
            }
            .success-message {
                color: #28a745;
                font-size: 1rem;
                font-weight: bold;
                margin-bottom: 1rem;
            }
        </style>
    </head>
    <body class="sb-nav-fixed">
        <%@include file="../layout/headerAdmin.jsp" %>
        <div id="layoutSidenav">
            <%@include file="../layout/sideNavOptionAdmin.jsp" %>
            <div id="layoutSidenav_content">
                <main class="content">
                    <div class="container-fluid px-4">
                        <h1 class="mt-4">Danh Sách VAT</h1>
                        <div class="card mb-4">
                            <div class="card-header">
                                <div><i class="fas fa-table me-1"></i> Danh Sách VAT</div>
                                <div>
                                    <a href="${pageContext.request.contextPath}/ManageVAT" class="btn btn-success btn-sm action-btn">
                                        <i class="fas fa-plus me-1"></i> Thêm VAT
                                    </a>
                                </div>
                            </div>
                            <div class="card-body">
                                <c:if test="${not empty successMessage}">
                                    <div class="success-message">${successMessage}</div>
                                </c:if>
                                <c:if test="${not empty errorMessage}">
                                    <div class="error-message">${errorMessage}</div>
                                </c:if>
                                <div class="filter-section">
                                    <div>
                                        <label for="statusFilter">Lọc theo Trạng Thái:</label>
                                        <select id="statusFilter" class="custom-select">
                                            <option value="">Tất cả</option>
                                            <option value="Hoạt động">Hoạt động</option>
                                            <option value="Không hoạt động">Không hoạt động</option>
                                            <option value="Hết hiệu lực">Hết hiệu lực</option>
                                        </select>
                                    </div>
                                </div>
                                <div class="table-responsive">
                                    <table id="vatTable" class="table table-bordered table-hover">
                                        <thead>
                                            <tr>
                                                <th>STT</th>
                                                <th>Tỷ lệ VAT (%)</th>
                                                <th>Mô tả</th>
                                                <th>Ngày bắt đầu</th>
                                                <th>Ngày kết thúc</th>
                                                <th>Trạng Thái</th>
                                                <th>Hành động</th>
                                            </tr>
                                        </thead>
                                        <tbody>
                                            <c:forEach var="vat" items="${vatList}">
                                                <tr>
                                                    <td></td>
                                                    <td><fmt:formatNumber value="${vat.vatRate}" pattern="0.0" />%</td>
                                                    <td>${vat.description}</td>
                                                    <td><fmt:formatDate value="${vat.startDate}" pattern="dd/MM/yyyy" /></td>
                                                    <td><fmt:formatDate value="${vat.endDate}" pattern="dd/MM/yyyy" /></td>
                                                    <td>
                                                        <c:choose>
                                                            <c:when test="${vat.status == 1 && vat.endDate.after(today)}">
                                                                <button class="status-active" disabled>Hoạt động</button>
                                                            </c:when>
                                                            <c:when test="${vat.status == 2 || (!vat.endDate.after(today) && vat.status == 1)}">
                                                                <button class="status-expired" disabled>Hết hiệu lực</button>
                                                            </c:when>
                                                            <c:otherwise>
                                                                <button class="status-inactive" disabled>Không hoạt động</button>
                                                            </c:otherwise>
                                                        </c:choose>
                                                    </td>
                                                    <td>
                                                        <a href="${pageContext.request.contextPath}/ManageVAT?service=viewVAT&vatID=${vat.vatID}" class="btn btn-detail action-btn">Chi tiết</a>
                                                        <c:choose>
                                                            <c:when test="${!vat.endDate.after(today) || vat.status == 2}">
                                                                <button type="button" class="btn btn-status action-btn btn-disabled" disabled>Đổi trạng thái</button>
                                                            </c:when>
                                                            <c:otherwise>
                                                                <button type="button" class="btn btn-status action-btn changeStatusBtn" 
                                                                        data-href="${pageContext.request.contextPath}/VATServlet?service=changeVATStatus&vatID=${vat.vatID}" 
                                                                        data-id="${vat.vatID}">Đổi trạng thái</button>
                                                            </c:otherwise>
                                                        </c:choose>
                                                    </td>
                                                </tr>
                                            </c:forEach>
                                        </tbody>
                                    </table>
                                </div>
                            </div>
                        </div>
                    </div>
                </main>
                <footer class="bg-light py-4 mt-auto">
                    <div class="container-fluid px-4">
                        <div class="text-muted text-center">Copyright © TravelSystemService 2025</div>
                    </div>
                </footer>
            </div>
        </div>
        <script src="https://cdn.jsdelivr.net/npm/bootstrap@5.3.0/dist/js/bootstrap.bundle.min.js" crossorigin="anonymous"></script>
        <script src="https://code.jquery.com/jquery-3.6.0.min.js" crossorigin="anonymous"></script>
        <script src="https://cdn.datatables.net/1.13.4/js/jquery.dataTables.min.js" crossorigin="anonymous"></script>
        <script src="https://cdn.datatables.net/1.13.4/js/dataTables.bootstrap5.min.js" crossorigin="anonymous"></script>
        <script src="${pageContext.request.contextPath}/assets/js/scripts.js"></script>
        <script src="https://cdn.jsdelivr.net/npm/sweetalert2@11"></script>
        <script>
            $(document).ready(function () {
                const table = $('#vatTable').DataTable({
                    language: {
                        search: "Tìm kiếm...",
                        lengthMenu: "Hiển thị _MENU_ mục",
                        zeroRecords: "Không tìm thấy dữ liệu",
                        info: "Hiển thị từ _START_ đến _END_ của _TOTAL_ mục",
                        infoEmpty: "Hiển thị từ 0 đến 0 của 0 mục",
                        infoFiltered: "(lọc từ _MAX_ mục)",
                        paginate: {previous: "Trước", next: "Tiếp"}
                    },
                    pageLength: 10,
                    lengthMenu: [5, 10, 20, 50],
                    responsive: true,
                    drawCallback: function (settings) {
                        var api = this.api();
                        var pageInfo = api.page.info();
                        api.column(0, {page: 'current'}).nodes().each(function (cell, i) {
                            cell.innerHTML = pageInfo.start + i + 1;
                        });
                    }
                });

                $('#statusFilter').on('change', function () {
                    const status = $(this).val().trim();
                    const statusColumn = table.column(5);
                    if (statusColumn) {
                        if (status === 'Hết hiệu lực') {
                            statusColumn.search('Hết hiệu lực').draw();
                        } else if (status) {
                            statusColumn.search('^' + status + '$', true, false).draw();
                        } else {
                            statusColumn.search('').draw();
                        }
                    }
                });

                $('#vatTable_filter input').on('keyup', function () {
                    table.search(this.value).draw();
                });

                $('#vatTable').on('click', '.changeStatusBtn', function (e) {
                    e.preventDefault();
                    const href = $(this).data('href');
                    const vatID = $(this).data('id');
                    Swal.fire({
                        title: `Bạn có chắc chắn muốn thay đổi trạng thái VAT ${vatID}?`,
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
                                url: href,
                                type: 'POST',
                                success: function (response) {
                                    Swal.fire({
                                        title: 'Thành công!',
                                        text: 'Trạng thái VAT đã được thay đổi.',
                                        icon: 'success',
                                        timer: 1500,
                                        showConfirmButton: false
                                    }).then(() => {
                                        window.location.href = '${pageContext.request.contextPath}/VATServlet?service=listVAT';
                                    });
                                },
                                error: function (xhr) {
                                    Swal.fire({
                                        title: 'Lỗi!',
                                        text: xhr.responseText || 'Không thể thay đổi trạng thái VAT.',
                                        icon: 'error',
                                        confirmButtonText: 'OK'
                                    });
                                }
                            });
                        }
                    });
                });

            <c:if test="${not empty successMessage}">
                Swal.fire({
                    title: 'Thành công!',
                    text: '${successMessage}',
                    icon: 'success',
                    timer: 2000,
                    showConfirmButton: false
                });
            </c:if>
            <c:if test="${not empty errorMessage}">
                Swal.fire({
                    title: 'Lỗi!',
                    text: '${errorMessage}',
                    icon: 'error',
                    confirmButtonText: 'OK'
                });
            </c:if>
            });
        </script>
    </body>
</html>