<%-- 
 * Copyright (C) 2025, Group 6.
 * ProjectCode/Short Name of Application: TravelSystemService 
 * Support Management and Provide Travel Service System 
 *
 * Record of change:
 * DATE        Version    AUTHOR            DESCRIPTION
 * 2025-06-14  1.0        Quynh Mai          First implementation
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
    <title>Quản lý Đăng ký Travel Agent</title>
    <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.2.3/dist/css/bootstrap.min.css" rel="stylesheet" crossorigin="anonymous">
    <link href="${pageContext.request.contextPath}/assets/css/styles2.css" rel="stylesheet" />
    <link href="https://cdn.datatables.net/1.11.5/css/dataTables.bootstrap5.min.css" rel="stylesheet" />
    <link href="https://cdnjs.cloudflare.com/ajax/libs/font-awesome/6.0.0-beta3/css/all.min.css" rel="stylesheet">
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
        }
        .modal-content {
            border-radius: 10px;
            background-color: #ffffff;
            color: #333;
        }
        .modal-header {
            background-color: #ffffff;
            color: #333;
            border-bottom: 1px solid #dee2e6;
        }
        .modal-body {
            padding: 20px;
            text-align: center;
        }
        .modal-footer {
            border-top: none;
            justify-content: center;
            padding: 10px;
        }
        .modal-footer .btn {
            padding: 8px 16px;
            margin: 0 5px;
        }
        .image-preview {
            max-height: 200px;
            max-width: 100%;
            object-fit: contain;
            margin-bottom: 10px;
        }
        .status-filter {
            margin-bottom: 20px;
            display: flex;
            gap: 10px;
            flex-wrap: wrap;
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
    </style>
</head>
<body>
    <%@include file="/view/layout/headerAdmin.jsp" %>
    <div id="layoutSidenav">
        <jsp:include page="/view/layout/sideNavOptionStaff.jsp"></jsp:include>  
        <div id="layoutSidenav_content">
            <main>
                <div class="container-centered mt-3 mb-3">
                    <h1>Quản lý Đăng ký đại lý</h1>
                    <form action="${pageContext.request.contextPath}/ManageTravelAgentRegister" method="get">
                        <input type="hidden" name="service" value="list">
                        <div class="status-filter">
                            <button style="background-color: #6c757d;" type="submit" name="status" value="all" class="all ${param.status == null || param.status == 'all' ? 'active' : ''}">Tất cả</button>
                            <button style="background-color: #007bff;" type="submit" name="status" value="2" class="pending ${param.status == '2' ? 'active' : ''}">Chờ duyệt</button>
                            <button style="background-color: #dc3545;" type="submit" name="status" value="3" class="rejected ${param.status == '3' ? 'active' : ''}">Bị từ chối</button>
                            <button style="background-color: #28a745;" type="submit" name="status" value="1" class="activeS ${param.status == '1' ? 'active' : ''}">Hoạt động</button>
                            <button style="background-color: #ffc107;" type="submit" name="status" value="0" class="inactive ${param.status == '0' ? 'active' : ''}">Không hoạt động</button>
                        </div>
                    </form>
                        <div class="table-responsive" style="background-color: white; border-radius: 20px; padding: 40px; margin-top: -20px">
                        <table id="agentTable" class="table table-striped" style="width:100%">
                            <thead>
                                <tr>
                                    <th>STT</th>
                                    <th>Tên Travel Agent</th>
                                    <th>Email</th>
                                    <th>Hotline</th>
                                    <th>Ngày Thành Lập</th>
                                    <th>Trạng Thái</th>
                                    <th>Xem chi tiết</th>
                                </tr>
                            </thead>
                            <tbody>
                                <c:forEach items="${travelAgents}" var="agent" varStatus="loop">
                                    <tr data-status="${agent.status}">
                                        <td>${loop.count}</td>
                                        <td>${agent.travelAgentName}</td>
                                        <td>${agent.travelAgentGmail}</td>
                                        <td>${agent.hotLine}</td>
                                        <td><fmt:formatDate value="${agent.establishmentDate}" pattern="dd/MM/yyyy" /></td>
                                        <td>
                                            <c:choose>
                                                <c:when test="${agent.status == 2}">Đang chờ duyệt</c:when>
                                                <c:when test="${agent.status == 1}">Hoạt động</c:when>
                                                <c:when test="${agent.status == 3}">Bị từ chối</c:when>
                                                <c:when test="${agent.status == 0}">Không hoạt động</c:when>
                                            </c:choose>
                                        </td>
                                        <td class="action-buttons" style="text-align: center;  ">
                                            <a style="background-color: #0d6efd; color: white; padding: 5px 10px; border-radius: 10px" href="${pageContext.request.contextPath}/ManageTravelAgentRegister?service=profile&travelAgentID=${agent.travelAgentID}" class="btn btn-info btn-sm">Xem chi tiết</a>
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
    <script>
        $(document).ready(function() {
            $('#agentTable').DataTable({
                "pageLength": 5,
                "lengthMenu": [5, 10, 25, 50],
                "language": {
                    "lengthMenu": "Hiển thị _MENU_ mục mỗi trang",
                    "zeroRecords": "Không tìm thấy dữ liệu",
                    "info": "Hiển thị trang _PAGE_ của _PAGES_",
                    "infoEmpty": "Không có dữ liệu",
                    "infoFiltered": "(lọc từ _MAX_ tổng số mục)",
                    "search": "Tìm kiếm:",
                    "paginate": {
                        "first": "Đầu",
                        "last": "Cuối",
                        "next": "Tiếp",
                        "previous": "Trước"
                    }
                }
            });
        });

    </script>
</body>
</html>