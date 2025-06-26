<%--
* Copyright (C) 2025, Group 6.
 * ProjectCode/Short Name of Application: TravelAgentService 
 * Support Management and Provide Travel Service System 
 *
 * Record of change:
 * DATE        Version    AUTHOR            DESCRIPTION
 * 2025-06-07  1.0        Hà Thị Duyên      First implementation
 * 2025-06-25  1.1        Hà Thị Duyên      Added password visibility toggle
--%>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>

<!DOCTYPE html>
<html>
<head>
    <meta charset="UTF-8">
    <title>Chỉnh sửa thông tin cá nhân</title>
    <!-- Load CSS từ CDN và file cục bộ -->
    <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.0/dist/css/bootstrap.min.css" rel="stylesheet">
    <link href="https://fonts.googleapis.com/css2?family=Poppins:wght@300;400;600&display=swap" rel="stylesheet">
    <link href="https://cdnjs.cloudflare.com/ajax/libs/font-awesome/5.10.0/css/all.min.css" rel="stylesheet">
    <link href="https://cdn.jsdelivr.net/npm/bootstrap-icons@1.4.1/font/bootstrap-icons.css" rel="stylesheet">
    <link href="${pageContext.request.contextPath}/assets/css/bootstrap.min.css" rel="stylesheet">
    <link href="${pageContext.request.contextPath}/assets/css/style.css" rel="stylesheet">

    <style>
        body {
            background: url('${pageContext.request.contextPath}/assets/img/img_10.jpg') no-repeat center center fixed;
            background-size: cover;
            margin: 0;
            min-height: 100vh;
            position: relative;
            display: flex;
            flex-direction: column;
        }
        .content-wrapper {
            flex: 1 0 auto;
            z-index: 1;
        }
        .profile-form {
            width: 100%;
            max-width: 800px;
            background-color: #ffffff;
            border-radius: 12px;
            padding: 30px;
            box-shadow: 0 4px 12px rgba(0, 0, 0, 0.1);
            margin: 120px auto 20px auto;
            position: relative;
            z-index: 1;
        }
        .profile-form h2 {
            font-family: 'Arial', sans-serif;
            font-weight: 600;
            color: #0066cc;
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
            color: #2d3748;
            height: 45px;
            border-radius: 8px;
            border: 2px solid #d1d5db;
            padding: 10px;
            font-family: 'Poppins', sans-serif;
        }
        .btn-update {
            height: 50px;
            font-size: 16px;
            font-weight: bold;
            border-radius: 8px;
            background-color: #007bff;
            color: #fff;
            border: none;
            width: 100%;
            margin-top: 10px;
            font-family: 'Poppins', sans-serif;
            transition: background-color 0.3s ease;
        }
        .btn-back {
            height: 50px;
            font-size: 16px;
            font-weight: bold;
            border-radius: 8px;
            background-color: #dc3545;
            color: #fff;
            border: none;
            width: 100%;
            margin-top: 10px;
            text-decoration: none;
            display: inline-block;
            text-align: center;
            line-height: 50px;
            font-family: 'Poppins', sans-serif;
            transition: background-color 0.3s ease;
        }
        footer {
            flex-shrink: 0;
            margin-top: 20px;
            background-color: transparent;
            color: #333;
            text-align: center;
            padding: 10px 0;
            font-family: 'Poppins', sans-serif;
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
        header {
            position: relative;
            z-index: 1000;
            width: 100%;
            top: 0;
            font-family: 'Poppins', sans-serif;
        }
        .row > div {
            padding: 0 15px;
        }
        select.form-control {
            height: 45px !important;
            padding: 10px;
        }
        .input-group {
            position: relative;
        }
        .input-group-text {
            background-color: #f8f9fa;
            border: 2px solid #d1d5db;
            border-left: none;
            border-radius: 0 8px 8px 0;
            cursor: pointer;
            height: 45px;
            display: flex;
            align-items: center;
        }
        .input-group .form-control {
            border-right: none;
            border-radius: 8px 0 0 8px;
        }
        .input-group-text .fas {
            color: #6b7280;
        }
        .input-group-text .fa-eye-slash {
            color: #6b7280;
        }
    </style>
</head>
<body>
    <!-- Include header -->
    <%@include file="../layout/header.jsp" %>

    <div class="content-wrapper">
        <div class="profile-form">
            <h2 class="text-center text-primary mb-4">Chỉnh sửa thông tin cá nhân</h2>
            <c:if test="${not empty error}">
                <div class="alert alert-danger">${error}</div>
            </c:if>
            <c:if test="${not empty success}">
                <div class="alert alert-success">${success}</div>
            </c:if>

            <c:if test="${not empty sessionScope.loginUser}">
                <form action="${pageContext.request.contextPath}/ProfileUser" method="post">
                    <input type="hidden" name="service" value="updateUser">
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
                                    <input type="password" class="form-control" name="password" id="passwordInput"
                                           value="${not empty requestScope.password ? requestScope.password : sessionScope.loginUser.password}" required>
                                    <span class="input-group-text">
                                        <i class="fas fa-eye" id="togglePassword"></i>
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
                                       value="${not empty param.dob ? param.dob : (sessionScope.loginUser.dob != null ? sessionScope.loginUser.dob : '')}" required>
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
                    <button type="submit" class="btn-update">Cập nhật thông tin</button>
                    <a href="${pageContext.request.contextPath}/ProfileUser?service=viewProfileUser" class="btn-back">Quay lại</a>
                </form>
            </c:if>
        </div>
    </div>

    <!-- Include footer -->
    <%@include file="../layout/footer.jsp" %>

    <!-- Load JavaScript -->
    <script src="https://code.jquery.com/jquery-3.4.1.min.js"></script>
    <script src="https://cdn.jsdelivr.net/npm/bootstrap@5.0.0/dist/js/bootstrap.bundle.min.js"></script>
    <script src="${pageContext.request.contextPath}/assets/lib/wow/wow.min.js"></script>
    <script src="${pageContext.request.contextPath}/assets/lib/easing/easing.min.js"></script>
    <script src="${pageContext.request.contextPath}/assets/lib/waypoints/waypoints.min.js"></script>
    <script src="${pageContext.request.contextPath}/assets/lib/owlcarousel/owl.carousel.min.js"></script>
    <script src="${pageContext.request.contextPath}/assets/lib/tempusdominus/js/moment.min.js"></script>
    <script src="${pageContext.request.contextPath}/assets/lib/tempusdominus/js/moment-timezone.min.js"></script>
    <script src="${pageContext.request.contextPath}/assets/lib/tempusdominus/js/tempusdominus-bootstrap-4.min.js"></script>
    <script src="${pageContext.request.contextPath}/assets/js/main.js"></script>

    <!-- JavaScript for password toggle -->
    <script>
        document.getElementById('togglePassword').addEventListener('click', function () {
            const passwordInput = document.getElementById('passwordInput');
            const icon = this;
            if (passwordInput.type === 'password') {
                passwordInput.type = 'text';
                icon.classList.remove('fa-eye');
                icon.classList.add('fa-eye-slash');
            } else {
                passwordInput.type = 'password';
                icon.classList.remove('fa-eye-slash');
                icon.classList.add('fa-eye');
            }
        });
    </script>
</body>
</html>