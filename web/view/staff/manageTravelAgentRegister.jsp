<%-- 
    Document   : manageTravelAgentRegister
    Created on : Jun 18, 2025, 10:20:34 PM
    Author     : Nhat Anh
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
        .action-buttons {
            display: flex;
            gap: 5px; /* Khoảng cách giữa các nút */
            align-items: center;
        }
        .modal-content {
            border-radius: 10px;
            background-color: #0d47a1; /* Màu xanh dương đậm */
            color: white;
        }
        .modal-header {
            background-color: #0d47a1; /* Giữ màu xanh dương đậm */
            color: white;
            border-bottom: none;
        }
        .modal-body {
            padding: 20px;
            text-align: center;
        }
        .modal-footer {
            border-top: none;
            justify-content: center; /* Căn giữa nút */
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
        /* Style for the status filter bar */
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
            transition: background-color 0.3s;
        }
        .status-filter button:hover {
            opacity: 0.9;
        }
        .status-filter .all { background-color: #6c757d; }
        .status-filter .pending { background-color: #007bff; }
        .status-filter .rejected { background-color: #dc3545; }
        .status-filter .active { background-color: #28a745; }
        .status-filter .inactive { background-color: #ffc107; }
    </style>
</head>
<body>
    <%@include file="/view/layout/headerAdmin.jsp" %>
    <div id="layoutSidenav">
        <jsp:include page="/view/layout/sideNavOptionStaff.jsp"></jsp:include>  
        <div id="layoutSidenav_content">
            <main>
                <div class="container-centered mt-3 mb-3">
                    <h1>Quản lý Đăng ký Travel Agent</h1>
                    <!-- Status Filter Bar with Form -->
                    <form action="${pageContext.request.contextPath}/ManageTravelAgentRegister" method="get">
                        <input type="hidden" name="service" value="list">
                        <div class="status-filter">
                            <button type="submit" name="status" value="all" class="all">Tất cả</button>
                            <button type="submit" name="status" value="2" class="pending">Chờ duyệt</button>
                            <button type="submit" name="status" value="3" class="rejected">Bị từ chối</button>
                            <button type="submit" name="status" value="1" class="active">Hoạt động</button>
                            <button type="submit" name="status" value="0" class="inactive">Không hoạt động</button>
                        </div>
                    </form>
                    <div class="table-responsive">
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
                                    <th>Hành Động</th>
                                </tr>
                            </thead>
                            <tbody>
                                <c:forEach items="${travelAgents}" var="agent" varStatus="loop">
                                    <tr data-status="${agent.status}">
                                        <td>${loop.count}</td> <!-- Sử dụng loop.count để đánh số từ 1 -->
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
                                        <td class="action-buttons">
                                            <a href="${pageContext.request.contextPath}/ManageTravelAgentRegister?service=profile&travelAgentID=${agent.travelAgentID}" class="btn btn-info btn-sm">Xem chi tiết</a>
                                        </td>
                                        <td class="action-buttons">
                                            <c:if test="${agent.status == 2}">
                                                <form action="${pageContext.request.contextPath}/ManageTravelAgentRegister" method="post" style="display:inline;">
                                                    <input type="hidden" name="service" value="approve">
                                                    <input type="hidden" name="travelAgentID" value="${agent.travelAgentID}">
                                                    <input type="hidden" name="userID" value="${agent.userID}">
                                                    <button type="submit" class="btn btn-success btn-sm">Duyệt</button>
                                                </form>
                                                <form action="${pageContext.request.contextPath}/ManageTravelAgentRegister" method="post" style="display:inline;">
                                                    <input type="hidden" name="service" value="reject">
                                                    <input type="hidden" name="travelAgentID" value="${agent.travelAgentID}">
                                                    <input type="hidden" name="userID" value="${agent.userID}">
                                                    <button type="button" class="btn btn-danger btn-sm" data-bs-toggle="modal" data-bs-target="#rejectModal${agent.travelAgentID}">Từ chối</button>
                                                </form>
                                            </c:if>
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

    <!-- Modal Từ chối cho từng Travel Agent -->
    <c:forEach items="${travelAgents}" var="agent">
        <div class="modal fade" id="rejectModal${agent.travelAgentID}" tabindex="-1" aria-labelledby="rejectModalLabel${agent.travelAgentID}" aria-hidden="true">
            <div class="modal-dialog modal-dialog-centered"> <!-- Căn giữa màn hình -->
                <div class="modal-content">
                    <div class="modal-header">
                        <h5 class="modal-title" id="rejectModalLabel${agent.travelAgentID}">Xác nhận Từ chối</h5>
                        <button type="button" class="btn-close" data-bs-dismiss="modal" aria-label="Close"></button>
                    </div>
                    <div class="modal-body">
                        <p>Bạn có chắc chắn muốn từ chối Travel Agent này không?</p>
                        <form action="${pageContext.request.contextPath}/ManageTravelAgentRegister" method="post">
                            <input type="hidden" name="service" value="reject">
                            <input type="hidden" name="travelAgentID" value="${agent.travelAgentID}">
                            <input type="hidden" name="userID" value="${agent.userID}">
                            <div class="mb-3">
                                <label for="reason${agent.travelAgentID}" class="form-label" style="color: white;">Lý do từ chối:</label>
                                <textarea class="form-control" id="reason${agent.travelAgentID}" name="reason" rows="3" required style="background-color: #ffffff; color: #000000;"></textarea>
                            </div>
                            <button type="submit" class="btn btn-danger">Đồng ý</button>
                            <button type="button" class="btn btn-secondary" data-bs-dismiss="modal">Không</button>
                        </form>
                    </div>
                </div>
            </div>
        </div>
    </c:forEach>

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