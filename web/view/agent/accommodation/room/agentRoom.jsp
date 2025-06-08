<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<!DOCTYPE html>
<html>
    <head>
        <meta charset="utf-8" />
        <meta http-equiv="X-UA-Compatible" content="IE=edge" />
        <meta name="viewport" content="width=device-width, initial-scale=1, shrink-to-fit=no" />
        <title>Dashboard - Room List</title>
        <link href="https://cdn.jsdelivr.net/npm/simple-datatables@7.1.2/dist/style.min.css" rel="stylesheet" />
        <link href="./assets/css/styles2.css" rel="stylesheet" />
        <link rel="stylesheet" href="https://cdnjs.cloudflare.com/ajax/libs/font-awesome/6.7.2/css/all.min.css" />
        <link rel="stylesheet" href="https://cdn.jsdelivr.net/npm/bootstrap-select@1.14.0-beta3/dist/css/bootstrap-select.min.css">
        <link href="https://fonts.googleapis.com/css2?family=Ancizar+Serif&display=swap" rel="stylesheet">
    </head>
    <style>
        form {
            display: flex;
            flex-wrap: wrap;
            gap: 10px;
            justify-content: center;
            margin: 32px 0px;
        }
        form input, form select, form button {
            height: 35px;
            font-size: 13px;
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
            text-align: center;
            font-size: 13px;
        }
        table th {
            background-color: #f1f1f1;
        }
        .btn {
            text-decoration: none;
            padding: 5px 10px;
        }
        .box-action {
            display: flex;
            justify-content: center;
            gap: 10px;
        }
    </style>
    <body>
        <%@ include file="../../../layout/headerAdmin.jsp" %>

        <div id="layoutSidenav">
            <div id="layoutSidenav_content">
                <main>
                    <div class="container-fluid px-4">
                        <h1 class="mt-4" style="font-family: 'Ancizar Serif', serif;">Danh sách phòng</h1>

                        <form action="ManagementRoom" method="GET" class="d-flex align-items-center">
                            <input 
                                type="text" 
                                name="accommodationID"
                                value="${param.accommodationID}"
                                class="form-control me-2 shadow-sm" 
                                placeholder="Tìm theo mã nơi ở..." 
                                style="max-width: 350px; border-radius: 20px; padding: 20px" 
                                />
                            <button type="submit" class="btn btn-primary d-flex align-items-center px-3" style="border-radius: 20px;">
                                Tìm kiếm
                            </button>
                        </form>

                        <div style="display: flex; gap: 15px; margin-bottom: 15px;">
                            <a href="AddRoom?id=${requestScope.accommodationID}">
                                <button class="btn btn-primary">Thêm phòng</button>
                            </a>
                            <a href="ManagementAccommodation">
                                <button class="btn btn-primary">Trở lại</button>
                            </a>
                        </div>

                        <div class="col-xl-12 col-md-6">
                            <div class="card-body">
                                <table id="tableList" style="border: 1px solid">
                                    <thead>
                                        <tr>
                                            <th>Mã phòng</th>
                                            <th>Loại phòng</th>
                                            <th>Số lượng phòng</th>
                                            <th>Giá phòng</th>
                                            <th>Hành động</th>
                                        </tr>
                                    </thead>
                                    <tbody>
                                        <c:forEach items="${requestScope.rooms}" var="room">
                                            <tr>
                                                <td>${room.roomID}</td>
                                                <td>${room.roomTypes}</td>
                                                <td>${room.numberOfRooms}</td>
                                                <td><fmt:formatNumber value="${room.priceOfRoom}" type="currency" currencySymbol="₫"/></td>
                                                <td>
                                                    <div class="box-action">
                                                        <a href="updateroom?id=${room.roomID}" class="btn btn-warning btn-sm">Sửa</a>
                                                        <a href="deleteroom?id=${room.roomID}" class="btn btn-danger btn-sm" 
                                                           onclick="return confirm('Bạn có chắc chắn muốn xóa phòng này không?');">
                                                            Xóa
                                                        </a>
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

        <script src="https://cdn.jsdelivr.net/npm/bootstrap@5.2.3/dist/js/bootstrap.bundle.min.js"></script>
        <script src="./assets/js/scripts.js"></script>
        <script src="https://cdn.jsdelivr.net/npm/simple-datatables@7.1.2/dist/umd/simple-datatables.min.js"></script>
        <script src="./assets/js/datatables-simple-demo.js"></script>
    </body>
</html>