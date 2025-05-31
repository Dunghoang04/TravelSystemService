<%-- 
    Document   : admin.jsp
    Created on : Feb 21, 2025, 8:19:14 PM
    Author     : Dell
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page import="java.util.ArrayList"%>

<%@ page import="model.Users"%>
<!DOCTYPE html>

<html lang="en">

    <head>
        <meta charset="utf-8">
        <title>Trang Admin</title>
        <meta content="width=device-width, initial-scale=1.0" name="viewport">
        <meta content="" name="keywords">
        <meta content="" name="description">

        <!-- Favicon -->
        <link href="${pageContext.request.contextPath}/assets/img/favicon.ico" rel="icon">

        <!-- Google Web Fonts -->
        <link rel="preconnect" href="https://fonts.googleapis.com">
        <link rel="preconnect" href="https://fonts.gstatic.com" crossorigin>
        <link href="https://fonts.googleapis.com/css2?family=Heebo:wght@400;500;600;700&display=swap" rel="stylesheet">

        <!-- Icon Font Stylesheet -->
        <link href="https://cdnjs.cloudflare.com/ajax/libs/font-awesome/5.10.0/css/all.min.css" rel="stylesheet">
        <link href="https://cdn.jsdelivr.net/npm/bootstrap-icons@1.4.1/font/bootstrap-icons.css" rel="stylesheet">

        <!-- Libraries Stylesheet -->
        <link href="${pageContext.request.contextPath}/assets/lib/owlcarousel/assets/owl.carousel.min.css" rel="stylesheet">
        <link href="${pageContext.request.contextPath}/assets/lib/tempusdominus/css/tempusdominus-bootstrap-4.min.css" rel="stylesheet" />

        <!-- Customized Bootstrap Stylesheet -->
        <link href="${pageContext.request.contextPath}/assets/css/bootstrap.min.css" rel="stylesheet">

        <!-- Template Stylesheet -->
        <link href="${pageContext.request.contextPath}/assets/css/style.css" rel="stylesheet">

        <style>
            /* Đảm bảo body luôn chiếm hết chiều cao của viewport */
            html, body {
                height: 100%;
                margin: 0;
            }

            /* Container chính để sử dụng flexbox */
            .container-xxl {
                display: flex;
                flex-direction: column;
                min-height: 100%;
            }

            /* Phần nội dung chính chiếm không gian còn lại */
            .content {
                flex: 1;
            }

            table {
                width: 100%;
                border-collapse: collapse;
                margin: 20px 0;
                font-size: 18px;
                text-align: left;
            }
            th, td {
                padding: 10px;
                border: 1px solid #ddd;
            }
            th {
                background-color: #f4f4f4;
            }
            /* Cải thiện button phân trang */
            .pagination {
                list-style-type: none;
                padding: 0;
                margin: 20px 0;
                text-align: center;
            }

            .pagination li {
                display: inline;
                margin-right: 5px;
            }

            .pagination li a {
                padding: 10px 15px;
                background-color: #007bff;
                color: white;
                text-decoration: none;
                border-radius: 5px;
                transition: background-color 0.3s ease;
            }

            .pagination li a:hover {
                background-color: #0056b3; /* Màu nền khi hover qua các link phân trang */
            }

            .pagination .active a {
                background-color: #0056b3;
            }

            .pagination .disabled a {
                background-color: #ccc;
                cursor: not-allowed;
            }

            /* Căn chỉnh footer */
            .footer {

                color: #333; /* Màu chữ tối */
                padding-top: 200px;
                padding-bottom: 0;
                margin-top: auto; /* Đảm bảo footer luôn nằm ở dưới */
                margin-bottom: 0;
                text-align: center;
            }
            .nav-item {
                white-space: nowrap;
            } /*thêm dòng này*/

            .btn-primary {
                background-color: #007bff;
                color: white;
                border-radius: 20px;
                padding: 8px 15px;
                border: none;
                cursor: pointer;
            }

            .form-select {
                width: 150px;
                border-radius: 8px;
                padding: 5px;
            }




        </style>


    </head>

    <body>
        <div class="container-xxl position-relative bg-white d-flex p-0">
            <div class="sidebar pe-4 pb-3">
                <nav class="navbar bg-light navbar-light">
                    <a href="home" class="navbar-brand mx-4 mb-3">
                        <h3 class="text-primary">Trang Chủ</h3>
                    </a>



                    <div class="navbar-nav w-100">
                        <a href="admin" class="nav-item nav-link active"><i class="fa fa-tachometer-alt me-2"></i>Bảng Điều Khiển</a>
                        <a href="user" class="nav-item nav-link"><i class="fa fa-laptop me-2"></i>Quản Lí Người Dùng</a>
                        <a href="listcategories" class="nav-item nav-link"><i class="fa fa-th me-2"></i>Quản Lí Danh Mục</a>

                        <div class="nav-item dropdown">
                            <a href="#" class="nav-link dropdown-toggle" data-bs-toggle="dropdown"><i class="far fa-file-alt me-2"></i>Sản Phẩm</a>
                            <div class="dropdown-menu bg-transparent border-0">
                                <a href="listproduct" class="dropdown-item">Quản Lí Sản Phẩm</a>
                                <a href="listvariant" class="dropdown-item">Quản Lí Biến Thể</a>
                                <a href="attributes" class="dropdown-item">Quản Lí Thuộc Tính</a>
                                <a href="img" class="dropdown-item">Quản Lí Ảnh</a>
                            </div>
                        </div>
                        <a href="listorder" class="nav-item nav-link"><i class="fa fa-table me-2"></i>Quản Lí Đơn Hàng</a>


                    </div>
                </nav>
            </div>

            <!-- Sidebar End -->


            <!-- Content Start -->
            <div class="content">

                <br/>

                <!-- Navbar End -->

                <div class="container">
                    <div class="text-left mt-3">
                        <a href="user" class="btn btn-primary">Quay lại</a>
                    </div>
                    <br><!-- comment -->
                    <div class="card p-4">
                        <div class="text-center mb-3">

                            <img class="rounded-circle border" width="120" height="120"  src="${empty user.avatar ? 'avatar.png' : user.avatar}"  alt="Avatar" class="avatar">
                            <h4 class="mt-2">${user.fullname}</h4>
                        </div>
                        <table class="table table-bordered">
                            <tr>
                                <td class="info-label">ID:</td>
                                <td>${user.id}</td>
                            </tr>
                            <tr>
                                <td class="info-label">Tài khoản:</td>
                                <td>${user.account}</td>
                            </tr>
                            <tr>
                                <td class="info-label">Mật khẩu:</td>
                                <td>${user.password}</td>
                            </tr>
                            <tr>
                                <td class="info-label">Địa chỉ:</td>
                                <td>${user.address}</td>
                            </tr>
                            <tr>
                                <td class="info-label">Vai trò:</td>
                                <td>${user.role}</td>
                            </tr>
                            <tr>
                                <td class="info-label">Email:</td>
                                <td>${user.email}</td>
                            </tr>
                            <tr>
                                <td class="info-label">Số điện thoại:</td>
                                <td>${user.phone}</td>
                            </tr>
                            <tr>
                                <td class="info-label">Giới tính:</td>
                                <td>${user.gender}</td>
                            </tr>
                            <tr>
                                <td class="info-label">Trạng thái:</td>
                                <td>

                                    <c:choose>
                                        <c:when test="${user.status == 1}">Hoạt động</c:when>
                                        <c:otherwise>Không hoạt động</c:otherwise>
                                    </c:choose>
                                </td>
                            </tr>
                            <tr>
                                <td class="info-label">Ngày tạo:</td>
                                <td>${user.createdAt}</td>
                            </tr>
                        </table>

                    </div>
                </div>





                <br/>


                <br/>




                <c:if test="${not empty list}">
                    <table style="border: 1px solid">
                        <tr>
                            <th>Id</th>
                            <th>Id người dùng</th>
                            <th>Cột cập nhật</th>
                            <th>Dữ liệu cũ</th>
                            <th>Dữ liệu mới</th>
                            <th>Cập nhật ngày</th> 

                        </tr>
                        <c:forEach var="u" items="${list}">
                            <tr>
                                <td>${u.id}</td>
                                <td>${u.user_id}</td>
                                <td>${u.field_name}</td>
                                <td>${u.old_value}</td>
                                <td>${u.new_value}</td>
                                <td>${u.update_at}</td>


                            </tr>
                        </c:forEach>
                    </table>
                </c:if>





                <!-- Footer Start -->
                <div class="footer">
                    <div class="container-fluid pt-5 px-4 bg-light text-dark mt-3">
                        <div class="row">
                            <!-- Liên kết bản quyền -->
                            <div class="col-12 col-sm-6 text-center text-sm-start mb-4 mb-sm-0">
                                <p class="text-dark text-decoration-none">&copy;Được tài trợ bởi SE1903</p>
                            </div>

                            <!-- Liên kết MXH -->
                            <div class="col-md-6 mb-3 mb-md-0">

                                <div class="d-flex justify-content-center justify-content-sm-end">
                                    <a href="#" class="text-primary me-3"><i class="fab fa-facebook"></i></a>
                                    <a href="#" class="text-danger me-3"><i class="fab fa-instagram"></i></a>
                                </div>
                            </div>
                        </div>

                        <!-- Thông tin thiết kế và phân phối -->
                        <div class="row mt-3">
                            <div class="col-12 text-center">
                                <h5 class="mb-0">Nhóm DSH</h5>
                                <h5 class="mb-0">se1903@fpt.edu.vn</h5>
                                <h5 class="mb-0">Hân hạnh được phục vụ quý khách</h5>
                            </div>
                        </div>
                    </div>

                    <!-- Footer End -->




                    <!-- JavaScript Libraries -->
                    <script src="https://code.jquery.com/jquery-3.4.1.min.js"></script>
                    <script src="https://cdn.jsdelivr.net/npm/bootstrap@5.0.0/dist/js/bootstrap.bundle.min.js"></script>
                    <script src="${pageContext.request.contextPath}/assets/lib/chart/chart.min.js"></script>
                    <script src="${pageContext.request.contextPath}/assets/lib/easing/easing.min.js"></script>
                    <script src="${pageContext.request.contextPath}/assets/lib/waypoints/waypoints.min.js"></script>
                    <script src="${pageContext.request.contextPath}/assets/lib/owlcarousel/owl.carousel.min.js"></script>
                    <script src="${pageContext.request.contextPath}/assets/lib/tempusdominus/js/moment.min.js"></script>
                    <script src="${pageContext.request.contextPath}/assets/lib/tempusdominus/js/moment-timezone.min.js"></script>
                    <script src="${pageContext.request.contextPath}/assets/lib/tempusdominus/js/tempusdominus-bootstrap-4.min.js"></script>

                    <!-- Template Javascript -->
                    <script src="${pageContext.request.contextPath}/assets/js/main.js"></script>


                    </body>

                    </html>

