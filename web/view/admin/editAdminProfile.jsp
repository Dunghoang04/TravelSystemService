<%--
* Copyright (C) 2025, Group 6.
 * ProjectCode/Short Name of Application: TravelAgentService 
 * Support Management and Provide Travel Service System 
 *
 * Record of change:
 * DATE        Version    AUTHOR            DESCRIPTION
 * 2025-06-24  1.0        Hà Thị Duyên      First implementation
--%>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>

<!DOCTYPE html>
<html lang="en">
    <head>
        <meta charset="utf-8" />
        <meta http-equiv="X-UA-Compatible" content="IE=edge" />
        <meta name="viewport" content="width=device-width, initial-scale=1, shrink-to-fit=no" />
        <meta name="description" content="Edit Admin Profile for TravelSystemService" />
        <meta name="author" content="Group 6" />
        <title>Edit Profile - Admin</title>
        <link href="https://cdn.jsdelivr.net/npm/simple-datatables@7.1.2/dist/style.min.css" rel="stylesheet" />
        <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.0/dist/css/bootstrap.min.css" rel="stylesheet" />
        <link href="https://cdn.jsdelivr.net/npm/sweetalert2@11/dist/sweetalert2.min.css" rel="stylesheet" />
        <link href="${pageContext.request.contextPath}/assets/css/styles2.css" rel="stylesheet" />
        <script src="https://use.fontawesome.com/releases/v6.3.0/js/all.js" crossorigin="anonymous"></script>
        <style>
            .card {
                transition: transform 0.2s;
            }
            .card:hover {
                transform: translateY(-5px);
            }
            .content {
                margin-left: 20%;
            }
            .profile-form {
                font-size: 20px;
                background-color: #fff;
                border-radius: 8px;
                margin-bottom: 20px;
                padding: 20px;
                width: 80%;
                margin-left: 10%;
            }
            .profile-title {
                font-size: 24px;
                font-weight: bold;
                color: #007bff;
                margin-bottom: 20px;
                text-align: center;
            }
            .form-group {
                margin-bottom: 20px;
            }
            .form-label {
                font-weight: 500;
                color: #2d3748;
                display: block;
                margin-bottom: 5px;
            }
            .form-control {
                height: 45px;
                border-radius: 8px;
                border: 2px solid #d1d5db;
                padding: 10px;
            }
            .update-back {
                display: flex;
                margin-left: 26%;
            }
            .btn-update {
                height: 50px;
                font-size: 16px;
                font-weight: bold;
                border-radius: 8px;
                background-color: #007bff;
                color: #fff;
                border: none;
                margin-top: 10px;
                transition: background-color 0.3s ease;
                width: 30%;
                margin-left: 35px;
            }
            .btn-back {
                height: 50px;
                font-size: 16px;
                font-weight: bold;
                border-radius: 8px;
                background-color: #696969;
                color: #fff;
                border: none;
                margin-top: 10px;
                text-decoration: none;
                display: inline-block;
                text-align: center;
                line-height: 50px;
                transition: background-color 0.3s ease;
                width: 30%;
            }
            .required {
                color: red;
                margin-left: 2px;
            }
            .field-error {
                color: red;
                font-size: 14px;
                margin-top: 5px;
                display: block;
            }
            form {
                display: block !important;
                flex-wrap: unset !important;
                gap: 0 !important;
                justify-content: unset !important;
                padding: 0 !important;
            }
            .input-group-text {
                border: 2px solid #d1d5db;
                border-left: none;
                border-radius: 0 8px 8px 0;
                background-color: #f8f9fa;
            }
            .input-group .form-control {
                border-right: none;
                border-radius: 8px 0 0 8px;
            }
          

        </style>
    </head>
    <body class="sb-nav-fixed">
        <%@include file="../layout/headerAdmin.jsp" %>
        <div id="layoutSidenav">
            <%@include file="../layout/sideNavOptionAdmin.jsp" %>
            <div id="layoutSidenav_content">
                <div class="container-fluid px-4">
                    <h1 class="mt-4 profile-title">Chỉnh sửa thông tin Admin</h1>
                    <c:if test="${not empty error}">
                        <div class="alert alert-danger">${error}</div>
                    </c:if>
                    <c:if test="${not empty success}">
                        <div class="alert alert-success">${success}</div>
                    </c:if>
                    <c:if test="${not empty sessionScope.loginUser && sessionScope.loginUser.roleID == 1}">
                        <div class="profile-form shadow">
                            <form action="${pageContext.request.contextPath}/ManageAdminProfile" method="post">                                
                                <input type="hidden" name="service" value="updateAdmin">
                                <div class="row">
                                    <div class="col-md-6">
                                        <div class="form-group">
                                            <label class="form-label">Họ:<span class="required">*</span></label>
                                            <input type="text" class="form-control" name="lastName" 
                                                   value="${not empty requestScope.lastName ? requestScope.lastName : sessionScope.loginUser.lastName}" required>
                                            <c:if test="${not empty requestScope.lastNameError}">
                                                <span class="field-error">${requestScope.lastNameError}</span>
                                            </c:if>
                                        </div>
                                        <div class="form-group">
                                            <label class="form-label">Mật khẩu:<span class="required">*</span></label>
                                            <div class="input-group">
                                                <input type="password" class="form-control" id="password" name="password" 
                                                       value="${not empty requestScope.password ? requestScope.password : sessionScope.loginUser.password}" required>
                                                <span class="input-group-text" onclick="togglePassword('password')" style="cursor: pointer;">
                                                    <i class="fas fa-eye"></i>
                                                </span>
                                            </div>
                                            <c:if test="${not empty requestScope.passwordError}">
                                                <span class="field-error">${requestScope.passwordError}</span>
                                            </c:if>
                                        </div>
                                        <div class="form-group">
                                            <label class="form-label">Số điện thoại:<span class="required">*</span></label>
                                            <input type="text" class="form-control" name="phone" 
                                                   value="${not empty requestScope.phone ? requestScope.phone : sessionScope.loginUser.phone}" required>
                                            <c:if test="${not empty requestScope.phoneError}">
                                                <span class="field-error">${requestScope.phoneError}</span>
                                            </c:if>
                                        </div>
                                        <div class="form-group">
                                            <label class="form-label">Gmail:</label>
                                            <input type="email" class="form-control" name="gmail" 
                                                   value="${sessionScope.loginUser.gmail}" readonly>
                                        </div>
                                    </div>
                                    <div class="col-md-6">
                                        <div class="form-group">
                                            <label class="form-label">Tên:<span class="required">*</span></label>
                                            <input type="text" class="form-control" name="firstName" 
                                                   value="${not empty requestScope.firstName ? requestScope.firstName : sessionScope.loginUser.firstName}" required>
                                            <c:if test="${not empty requestScope.firstNameError}">
                                                <span class="field-error">${requestScope.firstNameError}</span>
                                            </c:if>
                                        </div>
                                        <div class="form-group">
                                            <label class="form-label">Giới tính:<span class="required">*</span></label>
                                            <select class="form-control" name="gender" required>
                                                <option value="Nam" ${param.gender == 'Nam' || (empty param.gender && sessionScope.loginUser.gender == 'Nam') ? 'selected' : ''}>Nam</option>
                                                <option value="Nữ" ${param.gender == 'Nữ' || (empty param.gender && sessionScope.loginUser.gender == 'Nữ') ? 'selected' : ''}>Nữ</option>
                                                <option value="Khác" ${param.gender == 'Khác' || (empty param.gender && sessionScope.loginUser.gender == 'Khác') ? 'selected' : ''}>Khác</option>
                                            </select>
                                            <c:if test="${not empty requestScope.genderError}">
                                                <span class="field-error">${requestScope.genderError}</span>
                                            </c:if>
                                        </div>
                                        <div class="form-group">
                                            <label class="form-label">Ngày sinh:<span class="required">*</span></label>
                                            <input type="date" class="form-control" name="dob" 
                                                   value="${not empty requestScope.dob ? requestScope.dob : (sessionScope.loginUser.dob != null ? sessionScope.loginUser.dob : '')}" required>
                                            <c:if test="${not empty requestScope.dobError}">
                                                <span class="field-error">${requestScope.dobError}</span>
                                            </c:if>
                                        </div>
                                        <div class="form-group">
                                            <label class="form-label">Địa chỉ:<span class="required">*</span></label>
                                            <input type="text" class="form-control" name="address" 
                                                   value="${not empty requestScope.address ? requestScope.address : sessionScope.loginUser.address}" required>
                                            <c:if test="${not empty requestScope.addressError}">
                                                <span class="field-error">${requestScope.addressError}</span>
                                            </c:if>
                                        </div>
                                    </div>
                                </div>
                                <div class="update-back">
                                    <a href="${pageContext.request.contextPath}/ManageAdminProfile?service=viewAdminProfile" class="btn-back">Quay lại</a>
                                    <button type="submit" class="btn-update">Cập nhật thông tin</button>
                                </div>
                            </form>
                        </div>
                    </c:if>
                </div>

                <footer class="bg-light py-4 mt-auto">
                    <div class="container-fluid px-4">
                        <div class="text-muted text-center">Copyright © TravelSystemService 2025</div>
                    </div>
                </footer>
            </div>
        </div>
        <script src="https://cdn.jsdelivr.net/npm/bootstrap@5.3.0/dist/js/bootstrap.bundle.min.js" crossorigin="anonymous"></script>
        <script src="https://cdn.jsdelivr.net/npm/simple-datatables@7.1.2/dist/umd/simple-datatables.min.js" crossorigin="anonymous"></script>
        <script src="https://cdn.jsdelivr.net/npm/sweetalert2@11/dist/sweetalert2.all.min.js"></script>
        <script src="${pageContext.request.contextPath}/assets/js/scripts.js"></script>
        <script>
            <c:if test="${not empty successMessage}">
                                                    Swal.fire({
                                                        title: 'Thành công!',
                                                        text: '${successMessage}',
                                                        icon: 'success',
                                                        timer: 1500,
                                                        showConfirmButton: false
                                                    }).then(() => {
                                                        window.location.href = '${pageContext.request.contextPath}/ManageAdminProfile?service=viewAdminProfile';
                                                    });
            </c:if>
        </script>
        <script>
            function togglePassword(fieldId) {
                var passwordInput = document.getElementById(fieldId);
                var icon = passwordInput.nextElementSibling.querySelector("i");
                if (passwordInput.type === "password") {
                    passwordInput.type = "text";
                    icon.classList.remove("fa-eye");
                    icon.classList.add("fa-eye-slash");
                } else {
                    passwordInput.type = "password";
                    icon.classList.remove("fa-eye-slash");
                    icon.classList.add("fa-eye");
                }
            }
        </script>
    </body>
</html>