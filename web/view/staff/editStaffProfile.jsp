<%--
* Copyright (C) 2025, Group 6.
 * ProjectCode/Short Name of Application: TravelAgentService 
 * Support Management and Provide Travel Service System 
 *
 * Record of change:
 * DATE        Version    AUTHOR            DESCRIPTION
 * 2025-07-02  1.0        Hà Thị Duyên      First implementation for staff profile editing
 * 2025-07-02  1.1        Hà Thị Duyên      Changed address field to textarea with 3 rows
--%>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>

<!DOCTYPE html>
<html lang="vi">
    <head>
        <meta charset="utf-8" />
        <meta http-equiv="X-UA-Compatible" content="IE=edge" />
        <meta name="viewport" content="width=device-width, initial-scale=1, shrink-to-fit=no" />
        <meta name="description" content="Edit Staff Profile for TravelSystemService" />
        <meta name="author" content="Group 6" />
        <title>Chỉnh sửa Hồ sơ - Nhân viên</title>
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
            .readonly-field {
                background-color: #e9ecef;
                pointer-events: none;
            }
            .address-field .form-control {
                width: 100%;
                height: auto;
                resize: vertical;
            }
        </style>
    </head>
    <body class="sb-nav-fixed">
        <%@include file="../layout/headerAdmin.jsp" %>
        <div id="layoutSidenav">
            <%@include file="../layout/sideNavOptionStaff.jsp" %>
            <div id="layoutSidenav_content">
                <div class="container-fluid px-4">
                    <h1 class="mt-4 profile-title">Chỉnh sửa thông tin Nhân viên</h1>
                    <c:if test="${not empty error}">
                        <div class="alert alert-danger">${error}</div>
                    </c:if>
                    <c:if test="${not empty success}">
                        <div class="alert alert-success">${success}</div>
                    </c:if>
                    <c:if test="${not empty staff && sessionScope.loginUser.roleID != 1}">
                        <div class="profile-form shadow">
                            <form action="${pageContext.request.contextPath}/ManageStaffProfile" method="post">
                                <input type="hidden" name="service" value="updateStaff">
                                <div class="row">
                                    <div class="col-md-6">
                                        <div class="form-group">
                                            <label class="form-label">Mã nhân viên:</label>
                                            <input type="text" class="form-control readonly-field" name="employeeCode" 
                                                   value="${staff.employeeCode}" readonly>
                                        </div>
                                        <div class="form-group">
                                            <label class="form-label">Họ:<span class="required">*</span></label>
                                            <input type="text" class="form-control" name="lastName" 
                                                   value="${not empty requestScope.lastName ? requestScope.lastName : staff.lastName}" required>
                                            <c:if test="${not empty requestScope.lastNameError}">
                                                <span class="field-error">${requestScope.lastNameError}</span>
                                            </c:if>
                                        </div>
                                        <div class="form-group">
                                            <label class="form-label">Mật khẩu:<span class="required">*</span></label>
                                            <div class="input-group">
                                                <input type="password" class="form-control" id="password" name="password" 
                                                       value="${not empty requestScope.password ? requestScope.password : ''}">
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
                                                   value="${not empty requestScope.phone ? requestScope.phone : staff.phone}" required>
                                            <c:if test="${not empty requestScope.phoneError}">
                                                <span class="field-error">${requestScope.phoneError}</span>
                                            </c:if>
                                        </div>
                                        <div class="form-group">
                                            <label class="form-label">Trạng thái làm việc:</label>
                                            <input type="text" class="form-control readonly-field" name="workStatus" 
                                                   value="${staff.workStatus}" readonly>
                                        </div>
                                    </div>
                                    <div class="col-md-6">
                                        <div class="form-group">
                                            <label class="form-label">Ngày vào làm:</label>
                                            <input type="text" class="form-control readonly-field" name="hireDate" 
                                                   value="<fmt:formatDate value="${staff.hireDate}" pattern="dd/MM/yyyy"/>" readonly>
                                        </div>
                                        <div class="form-group">
                                            <label class="form-label">Tên:<span class="required">*</span></label>
                                            <input type="text" class="form-control" name="firstName" 
                                                   value="${not empty requestScope.firstName ? requestScope.firstName : staff.firstName}" required>
                                            <c:if test="${not empty requestScope.firstNameError}">
                                                <span class="field-error">${requestScope.firstNameError}</span>
                                            </c:if>
                                        </div>
                                        <div class="form-group">
                                            <label class="form-label">Giới tính:<span class="required">*</span></label>
                                            <select class="form-control" name="gender" required>
                                                <option value="Nam" ${param.gender == 'Nam' || (empty param.gender && staff.gender == 'Nam') ? 'selected' : ''}>Nam</option>
                                                <option value="Nữ" ${param.gender == 'Nữ' || (empty param.gender && staff.gender == 'Nữ') ? 'selected' : ''}>Nữ</option>
                                                <option value="Khác" ${param.gender == 'Khác' || (empty param.gender && staff.gender == 'Khác') ? 'selected' : ''}>Khác</option>
                                            </select>
                                            <c:if test="${not empty requestScope.genderError}">
                                                <span class="field-error">${requestScope.genderError}</span>
                                            </c:if>
                                        </div>
                                        <div class="form-group">
                                            <label class="form-label">Ngày sinh:<span class="required">*</span></label>
                                            <input type="date" class="form-control" name="dob" 
                                                   value="${not empty requestScope.dob ? requestScope.dob : (staff.dob != null ? staff.dob : '')}" required>
                                            <c:if test="${not empty requestScope.dobError}">
                                                <span class="field-error">${requestScope.dobError}</span>
                                            </c:if>
                                        </div>
                                        <div class="form-group">
                                            <label class="form-label">Gmail:</label>
                                            <input type="email" class="form-control" name="gmail" 
                                                   value="${not empty requestScope.gmail ? requestScope.gmail : staff.gmail}">
                                            <c:if test="${not empty requestScope.gmailError}">
                                                <span class="field-error">${requestScope.gmailError}</span>
                                            </c:if>
                                        </div>

                                    </div>
                                </div>
                                <div class="row">
                                    <div class="col-md-12 form-group address-field">
                                        <label class="form-label">Địa chỉ:<span class="required">*</span></label>
                                        <textarea class="form-control" name="address" rows="3" required>${not empty requestScope.address ? requestScope.address : staff.address}</textarea>
                                        <c:if test="${not empty requestScope.addressError}">
                                            <span class="field-error">${requestScope.addressError}</span>
                                        </c:if>
                                    </div>
                                </div>
                                <div class="update-back">
                                    <a href="${pageContext.request.contextPath}/ManageStaffProfile?service=viewStaffProfile" class="btn-back">Quay lại</a>
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