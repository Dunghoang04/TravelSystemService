<%--
* Copyright (C) 2025, Group 6.
 * ProjectCode/Short Name of Application: TravelAgentService 
 * Support Management and Provide Travel Service System 
 *
 * Record of change:
 * DATE        Version    AUTHOR            DESCRIPTION
 * 2025-06-24  1.0        Hà Thị Duyên      First implementationF
--%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html lang="en">
    <head>
        <meta charset="utf-8" />
        <meta http-equiv="X-UA-Compatible" content="IE=edge" />
        <meta name="viewport" content="width=device-width, initial-scale=1, shrink-to-fit=no" />
        <meta name="description" content="User Account List for TravelSystemService" />
        <meta name="author" content="Group 6" />
        <title>User Accounts - Admin</title>
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
            .status-active {
                background-color: #28a745;
                color: white;
                padding: 4px 12px;
                border-radius: 20px;
                border: none;
                cursor: default;
            }
            .status-inactive, .status-rejected {
                background-color: #dc3545;
                color: white;
                padding: 4px 12px;
                border-radius: 20px;
                border: none;
                cursor: default;
            }
            .status-pending {
                background-color: #ffc107;
                color: black;
                padding: 4px 12px;
                border-radius: 20px;
                border: none;
                cursor: default;
            }
            .btn-update {
                background-color: #ffc107;
                color: black;
                border: none;
                padding: 4px 12px;
                border-radius: 20px;
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
            .card-header {
                display: flex;
                justify-content: space-between;
                align-items: center;
            }
        </style>
    </head>
    <body class="sb-nav-fixed">
        <%@include file="../layout/headerAdmin.jsp" %>
        <div id="layoutSidenav">
            <%@include file="../layout/sideNavOptionAdmin.jsp" %>
            <div id="layoutSidenav_content">
                <main class="content" style="width:1200px">
                    <div class="container-fluid px-4">
                        <h1 class="mt-4">Danh Sách Tài Khoản</h1>
                        <div class="card mb-4">
                            <div class="card-header">
                                <div>
                                    <i class="fas fa-table me-1"></i>
                                    Danh Sách Người Dùng
                                </div>
                                <div>
                                    
                                    <a href="${pageContext.request.contextPath}/AddStaff" class="btn btn-success btn-sm action-btn">
                                        <i class="fas fa-user-plus me-1"></i> Thêm Nhân Viên
                                    </a>
                                    <a href="${pageContext.request.contextPath}/AddAdmin" class="btn btn-success btn-sm action-btn">
                                        <i class="fas fa-user-plus me-1"></i> Thêm Quản trị viên
                                    </a>
                                </div>
                            </div>
                            <div class="card-body">
                                <div class="filter-section">
                                    <div>
                                        <label for="roleFilter">Lọc theo Vai Trò:</label>
                                        <select id="roleFilter" class="custom-select">
                                            <option value="">Tất cả</option>
                                            <option value="Admin">Quản trị viên</option>
                                            <option value="Staff">Nhân viên</option>
                                            <option value="Tourist">Khách du lịch</option>
                                            <option value="Agent">Đại lý</option>
                                        </select>
                                    </div>
                                    <div>
                                        <label for="statusFilter">Lọc theo Trạng Thái:</label>
                                        <select id="statusFilter" class="custom-select">
                                            <option value="">Tất cả</option>
                                            <option value="Hoạt động">Hoạt động</option>
                                            <option value="Không hoạt động">Dừng hoạt động</option>
                                            <option value="Đang chờ duyệt">Đang chờ duyệt</option>
                                            <option value="Từ chối">Từ chối</option>
                                        </select>
                                    </div>
                                </div>
                                <div class="table-responsive">
                                    <table id="userTable" class="table table-bordered table-hover">
                                        <thead>
                                            <tr>
                                                <th>STT</th>
                                                <th>Email</th>
                                                <th>Họ</th>
                                                <th>Tên</th>
                                                <th>Vai Trò</th>
                                                <th>Trạng Thái</th>
                                                <th>Chi tiết</th>
                                                <th>Đổi trạng thái</th>
                                            </tr>
                                        </thead>
                                        <tbody>
                                            <c:forEach var="user" items="${userList}">
                                                <tr>
                                                    <td></td>
                                                    <td>${user.gmail}</td>
                                                    <td>${user.firstName}</td>
                                                    <td>${user.lastName}</td>
                                                    <td><c:choose>
                                                            <c:when test="${user.roleID == 1}">Quản trị viên</c:when>
                                                            <c:when test="${user.roleID == 2}">Nhân viên</c:when>
                                                            <c:when test="${user.roleID == 3}">Khách du lịch</c:when>
                                                            <c:when test="${user.roleID == 4}">Đại lý</c:when>
                                                            <c:otherwise>Không xác định</c:otherwise>
                                                        </c:choose>
                                                    </td>
                                                    <td><c:choose>
                                                            <c:when test="${user.status == 1}"><button class="status-active" disabled>Hoạt động</button></c:when>
                                                            <c:when test="${user.status == 0}"><button class="status-inactive" disabled>Dừng hoạt động</button></c:when>
                                                            <c:when test="${user.status == 2 && user.roleID == 4}"><button class="status-pending" disabled>Đang chờ duyệt</button></c:when>
                                                            <c:when test="${user.status == 3 && user.roleID == 4}"><button class="status-rejected" disabled>Từ chối</button></c:when>
                                                            <c:otherwise>Không xác định</c:otherwise>
                                                        </c:choose>
                                                    </td>
                                                    <td>
                                                        <a href="${pageContext.request.contextPath}/ViewDetailAccount?userID=${user.userID}" class="btn btn-detail action-btn">Chi tiết</a>
                                                    </td>
                                                    <td>
                                                        <button type="button" class="btn btn-status action-btn changeStatusBtn" 
                                                                data-href="${pageContext.request.contextPath}/ChangeStatusServlet?userID=${user.userID}" 
                                                                <c:if test="${user.roleID == 1 || (user.roleID == 4 && (user.status == 2 || user.status == 3))}">disabled</c:if>>Đổi trạng thái</button>
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
                const table = $('#userTable').DataTable({
                    language: {
                        search: "Tìm kiếm...",
                        lengthMenu: "Hiển thị _MENU_ mục",
                        zeroRecords: "Không tìm thấy dữ liệu",
                        info: "Hiển thị từ _START_ đến _END_ của _TOTAL_ mục",
                        infoEmpty: "Hiển thị từ 0 đến 0 của 0 mục",
                        infoFiltered: "(lọc từ _MAX_ mục)",
                        paginate: {
                            previous: "Trước",
                            next: "Tiếp"
                        }
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
                        console.log("Table redrawn, visible rows:", api.rows({page: 'current'}).data().length);
                    },
                    initComplete: function () {
                        console.log("DataTable initialized, total rows:", this.api().rows().data().length);
                        this.api().column(5).data().each(function (value, index) {
                            console.log("Row", index, "Trạng Thái:", value);
                        });
                    }
                });

                // Ánh xạ giá trị dropdown sang giá trị hiển thị trong bảng
                const roleMapping = {
                    'Admin': 'Quản trị viên',
                    'Staff': 'Nhân viên',
                    'Tourist': 'Khách du lịch',
                    'Agent': 'Đại lý'
                };

                // Update status filter options based on role selection
                function updateStatusFilter(role) {
                    const $statusFilter = $('#statusFilter');
                    // Reset all options
                    $statusFilter.find('option').show();
                    if (role === 'Agent') {
                        // Keep all status options for Agent (including Đang chờ duyệt, Từ chối)
                    } else {
                        // Hide Agent-specific statuses for other roles
                        $statusFilter.find('option[value="Đang chờ duyệt"]').hide();
                        $statusFilter.find('option[value="Từ chối"]').hide();
                        if ($statusFilter.val() === 'Đang chờ duyệt' || $statusFilter.val() === 'Từ chối') {
                            $statusFilter.val('');
                            table.column(5).search('').draw();
                        }
                    }
                }

                // Role filter change handler
                $('#roleFilter').on('change', function () {
                    const role = $(this).val().trim();
                    console.log("Role filter changed to:", role);
                    updateStatusFilter(role);
                    const roleColumn = table.column(4);
                    if (roleColumn) {
                        if (role) {
                            const displayRole = roleMapping[role] || '';
                            roleColumn.search('^' + displayRole + '$', true, false).draw();
                            console.log("Filtering column 4 with:", displayRole);
                        } else {
                            roleColumn.search('').draw();
                            console.log("Role filter cleared");
                        }
                    } else {
                        console.error("Role column (4) not found!");
                    }
                });

                // Status filter change handler
                $('#statusFilter').on('change', function () {
                    const status = $(this).val().trim();
                    console.log("Status filter changed to:", status);
                    const statusColumn = table.column(5);
                    if (statusColumn) {
                        if (status) {
                            statusColumn.search('^' + status + '$', true, false).draw();
                            console.log("Filtering column 5 with:", status);
                        } else {
                            statusColumn.search('').draw();
                            console.log("Status filter cleared");
                        }
                    } else {
                        console.error("Status column (5) not found!");
                    }
                });

                // Bind default search input
                $('#userTable_filter input').on('keyup', function () {
                    table.search(this.value).draw();
                    console.log("Global search input changed to:", this.value);
                });

                // Change status functionality using event delegation
                $('#userTable').on('click', '.changeStatusBtn', function (e) {
                    e.preventDefault();
                    const href = $(this).data('href'); // Get the URL from data-href
                    const name = $(this).data('name') || $(this).closest('tr').find('td:nth-child(2)').text(); // Fallback to email if data-name not set
                    Swal.fire({
                        title: `Bạn có chắc chắn muốn thay đổi trạng thái tài khoản ${name}?`,
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
                                title: 'Thành công!',
                                text: 'Trạng thái đã được thay đổi.',
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
    </body>
</html>