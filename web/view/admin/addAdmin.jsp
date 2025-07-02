<%--
* Copyright (C) 2025, Group 6.
 * ProjectCode/Short Name of Application: TravelAgentService 
 * Support Management and Provide Travel Service System 
 *
 * Record of change:
 * DATE        Version    AUTHOR            DESCRIPTION
 * 2025-06-24  1.0        Hà Thị Duyên      First implementation
--%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html lang="en">
    <head>
        <meta charset="utf-8" />
        <meta http-equiv="X-UA-Compatible" content="IE=edge" />
        <meta name="viewport" content="width=device-width, initial-scale=1, shrink-to-fit=no" />
        <meta name="description" content="Add Admin for TravelSystemService" />
        <meta name="author" content="Group 6" />
        <title>Add Admin - TravelSystemService</title>
        <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.0/dist/css/bootstrap.min.css" rel="stylesheet" />
        <link href="${pageContext.request.contextPath}/assets/css/styles2.css" rel="stylesheet" />
        <script src="https://use.fontawesome.com/releases/v6.3.0/js/all.js" crossorigin="anonymous"></script>
        <style>
            .content {
                margin: 0 auto;
                width: 1200px;
            }
            .card {
                transition: transform 0.2s;
            }
            .card:hover {
                transform: translateY(-5px);
            }
            .form-label {
                font-weight: 500;
            }
            .error-message {
                color: #dc3545;
                font-size: 0.875rem;
                margin-top: 0.25rem;
            }
            .success-message {
                color: #28a745;
                font-size: 1rem;
                font-weight: bold;
                margin-bottom: 1rem;
            }
            .form-control.is-invalid {
                border-color: #dc3545;
            }
            .form-section {
                margin-bottom: 2rem;
            }
            .btn-submit {
                background-color: #28a745;
                border-color: #28a745;
            }
            .btn-submit:hover {
                background-color: #218838;
                border-color: #1e7e34;
            }
            .btn-back {
                background-color: #6c757d;
                border-color: #6c757d;
            }
            .btn-back:hover {
                background-color: #5a6268;
                border-color: #545b62;
            }
            form {
                display: block !important;
                flex-wrap: unset !important;
                gap: 0 !important;
                justify-content: unset !important;
                padding: 0 !important;
            }
            .input-group-text {
                border: 2px solid #d1d5db !important;
                border-left: none !important;
                border-radius: 0 8px 8px 0 !important;
                background-color: #f8f9fa !important;
                cursor: pointer !important;
            }
            .input-group .form-control {
                border-right: none !important;
                border-radius: 8px 0 0 8px !important;
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
                        <h1 class="mt-4">Thêm Quản Trị Viên</h1>
                        <div class="card mb-4">
                            <div class="card-header">
                                <i class="fas fa-user-plus me-1"></i>
                                Thêm Quản Trị Viên Mới
                            </div>
                            <div class="card-body">
                                <c:if test="${not empty generalError}">
                                    <div class="error-message">${generalError}</div>
                                </c:if>
                                <form action="${pageContext.request.contextPath}/AddAdmin" method="post">
                                    <input type="hidden" name="service" value="addAdmin" />
                                    <div class="form-section">
                                        <div class="row mb-3">
                                            <div class="col-md-6">
                                                <label for="lastName" class="form-label">Họ</label>
                                                <input type="text" class="form-control ${not empty lastNameError ? 'is-invalid' : ''}" id="lastName" name="lastName" value="${requestScope.lastName}" placeholder="Nhập họ" />
                                                <c:if test="${not empty lastNameError}">
                                                    <div class="error-message">${lastNameError}</div>
                                                </c:if>
                                            </div>
                                            <div class="col-md-6">
                                                <label for="firstName" class="form-label">Tên</label>
                                                <input type="text" class="form-control ${not empty firstNameError ? 'is-invalid' : ''}" id="firstName" name="firstName" value="${requestScope.firstName}" placeholder="Nhập tên" />
                                                <c:if test="${not empty firstNameError}">
                                                    <div class="error-message">${firstNameError}</div>
                                                </c:if>
                                            </div>
                                        </div>
                                        <div class="row mb-3">
                                            <div class="col-md-6">
                                                <label for="password" class="form-label">Mật Khẩu</label>
                                                <div class="input-group">
                                                    <input type="password" class="form-control ${not empty passwordError ? 'is-invalid' : ''}" id="password" name="password" value="${requestScope.password}" placeholder="Nhập mật khẩu" />
                                                    <span class="input-group-text" onclick="togglePassword('password')" style="cursor: pointer;">
                                                        <i class="fas fa-eye"></i>
                                                    </span>
                                                </div>
                                                <c:if test="${not empty passwordError}">
                                                    <div class="error-message">${passwordError}</div>
                                                </c:if>
                                            </div>
                                            <div class="col-md-6">
                                                <label for="repassword" class="form-label">Nhập Lại Mật Khẩu</label>
                                                <div class="input-group">
                                                    <input type="password" class="form-control ${not empty repasswordError ? 'is-invalid' : ''}" id="repassword" name="repassword" value="${requestScope.repassword}" placeholder="Nhập lại mật khẩu" />
                                                    <span class="input-group-text" onclick="togglePassword('repassword')" style="cursor: pointer;">
                                                        <i class="fas fa-eye"></i>
                                                    </span>
                                                </div>
                                                <c:if test="${not empty repasswordError}">
                                                    <div class="error-message">${repasswordError}</div>
                                                </c:if>
                                            </div>
                                        </div>
                                        <div class="row mb-3">
                                            <div class="col-md-6">
                                                <label for="phone" class="form-label">Số Điện Thoại</label>
                                                <input type="text" class="form-control ${not empty phoneError ? 'is-invalid' : ''}" id="phone" name="phone" value="${requestScope.phone}" placeholder="VD: 0901234567" />
                                                <c:if test="${not empty phoneError}">
                                                    <div class="error-message">${phoneError}</div>
                                                </c:if>
                                            </div>
                                            <div class="col-md-6">
                                                <label for="dob" class="form-label">Ngày Sinh</label>
                                                <input type="date" class="form-control ${not empty dobError ? 'is-invalid' : ''}" id="dob" name="dob" value="${requestScope.dob}" placeholder="Chọn ngày sinh" />
                                                <c:if test="${not empty dobError}">
                                                    <div class="error-message">${dobError}</div>
                                                </c:if>
                                            </div>
                                        </div>
                                        <div class="row mb-3">
                                            <div class="col-md-6">
                                                <label for="gender" class="form-label">Giới Tính</label>
                                                <select class="form-control ${not empty genderError ? 'is-invalid' : ''}" id="gender" name="gender">
                                                    <option value="">Chọn giới tính</option>
                                                    <option value="Nam" ${gender == 'Nam' ? 'selected' : ''}>Nam</option>
                                                    <option value="Nữ" ${gender == 'Nữ' ? 'selected' : ''}>Nữ</option>
                                                    <option value="Nữ" ${gender == 'Khác' ? 'selected' : ''}>Khác</option>
                                                </select>
                                                <c:if test="${not empty genderError}">
                                                    <div class="error-message">${genderError}</div>
                                                </c:if>
                                            </div>
                                            <div class="col-md-6">
                                                <label for="gmail" class="form-label">Gmail</label>
                                                <input type="email" class="form-control ${not empty gmailError ? 'is-invalid' : ''}" id="gmail" name="gmail" value="${requestScope.gmail}" placeholder="VD: example@gmail.com" />
                                                <c:if test="${not empty gmailError}">
                                                    <div class="error-message">${gmailError}</div>
                                                </c:if>
                                            </div>
                                        </div>
                                        <div class="mb-3">
                                            <label for="address" class="form-label">Địa Chỉ</label>
                                            <textarea class="form-control ${not empty addressError ? 'is-invalid' : ''}" id="address" name="address" rows="2" placeholder="Nhập địa chỉ">${requestScope.address}</textarea>
                                            <c:if test="${not empty addressError}">
                                                <div class="error-message">${addressError}</div>
                                            </c:if>
                                        </div>
                                    </div>
                                    <div class="text-end">
                                        <a href="${pageContext.request.contextPath}/ManagementAccount?service=listAccount" class="btn btn-back me-2">Quay Lại</a>
                                        <button type="submit" class="btn btn-submit">Thêm Quản Trị Viên</button>
                                    </div>
                                </form>
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
        <script src="https://cdn.jsdelivr.net/npm/sweetalert2@11"></script>
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
                window.location.href = '${pageContext.request.contextPath}/ManagementAccount?service=listAccount';
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