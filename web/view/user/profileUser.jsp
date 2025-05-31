<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<!DOCTYPE html>
<html>
    <head>
        <meta charset="UTF-8">
        <title>Thông tin cá nhân</title>
        <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.0/dist/css/bootstrap.min.css" rel="stylesheet">
        <link href="https://fonts.googleapis.com/css2?family=Poppins:wght@300;400;600&display=swap" rel="stylesheet">
        <style>
            body {
                background: url('<%=request.getContextPath()%>/assets/img/img_10.jpg') ;
                background-size: cover;         /* ảnh sẽ co giãn để phủ hết màn hình */
                background-repeat: no-repeat;   /* không lặp ảnh */
                background-position: center;
                display: flex;
                justify-content: center;
                align-items: center;
                min-height: 100vh;
                margin: 0;
            }
            .profile-form {
                width: 100%;
                max-width: 900px;
                background-color: white;
                border-radius: 12px;
                padding: 40px;
                box-shadow: 0 10px 30px rgba(0, 0, 0, 0.08);
            }
            .form-label {
                font-weight: 500;
                color: #2d3748;
            }
            .form-control {
                height: 50px;
                border-radius: 10px;
                background-color: #f9fafb;
                border: 2px solid #d1d5db;
            }
            .form-control:focus {
                border-color: #3b82f6;
                box-shadow: 0 0 0 4px rgba(59, 130, 246, 0.15);
                background-color: #fff;
            }
            .btn-primary {
                height: 60px;
                font-size: 18px;
                font-weight: bold;
                border-radius: 10px;
                background-color: #3b82f6;
                border-color: #3b82f6;
                text-transform: uppercase;

            }
            .btn-primary:hover {
                background-color: #2563eb;
                border-color: #2563eb;

            }

            .btn1 {
                margin-top: 20px;
                background-color: #ff0000; /* Màu đỏ cho nền */
                color: #ffffff; /* Màu trắng cho chữ */
                padding: 10px 20px; /* Khoảng cách bên trong (trên/dưới 10px, trái/phải 20px) */
                text-decoration: none; /* Bỏ gạch chân mặc định của thẻ <a> */
                border-radius: 5px; /* Bo góc nút */
                display: inline-block; /* Đảm bảo nút hiển thị dạng khối */
                font-weight: bold; /* Chữ đậm */
                transition: background-color 0.3s ease; /* Hiệu ứng chuyển màu mượt mà khi hover */
            }

            .btn1:hover {
                background-color: #cc0000; /* Màu đỏ đậm hơn khi hover */
                color: #ffffff; /* Giữ chữ trắng khi hover */
            }
        </style>
    </head>
    <body>
        <div class="profile-form">
            <h2 class="text-center text-primary mb-4">Thông tin cá nhân</h2>
            <c:if test="${not empty error}">
                <div class="alert alert-danger">${error}</div>
            </c:if>
<<<<<<< OURS
            <c:if test="${not empty success}">
=======
            <c:if test="${not empty successMessage}">
>>>>>>> THEIRS
                <div class="alert alert-success">${success}</div>
            </c:if>

            <c:if test="${not empty sessionScope.loginUser}">
                <form action="${pageContext.request.contextPath}/ProfileUser" method="post">
                    <input type="hidden" name="service" value="updateUser">
                    <div class="row">
                        <div class="col-md-6 mb-3">
                            <label class="form-label">Họ:</label>
                            <input type="text" class="form-control" name="lastName" placeholder="VD: Nguyễn" value="${sessionScope.loginUser.lastName}">
                        </div>
                        <div class="col-md-6 mb-3">
                            <label class="form-label">Tên:</label>
                            <input type="text" class="form-control" name="firstName" placeholder="VD: Hoa" value="${sessionScope.loginUser.firstName}">
                        </div>
                        <div class="col-md-6 mb-3">
                            <label class="form-label">Mật khẩu:</label>
                            <input type="password" class="form-control" name="password" value="${sessionScope.loginUser.password}">
                        </div>

                        <div class="col-md-6 mb-3">
                            <label class="form-label">Giới tính:</label>
                            <select class="form-control" name="gender">
                                
                                <option value="">-- Chọn giới tính --</option>
                                <option value="Male" ${sessionScope.loginUser.gender.toString().equalsIgnoreCase("Male") ? 'selected' : ''}>Nam</option>
                                <option value="Female" ${sessionScope.loginUser.gender.toString().equalsIgnoreCase("Female") ? 'selected' : ''}>Nữ</option>
                                <option value="Other" ${sessionScope.loginUser.gender.toString().equalsIgnoreCase("Other") ? 'selected' : ''}>Khác</option>
                            </select>
                        </div>
                        <div class="col-md-6 mb-3">
                            <label class="form-label">Số điện thoại:</label>
                            <input type="text" class="form-control" name="phone" placeholder="Nhập số điện thoại" value="${sessionScope.loginUser.phone}">
                        </div>
                        <div class="col-md-6 mb-3">
                            <label class="form-label">Ngày sinh:</label>
                            <input type="date" class="form-control" name="dob" value="${sessionScope.loginUser.dob}">
                        </div>
                        <div class="col-md-6 mb-3">
                            <label class="form-label">Gmail:</label>
                            <input type="email" class="form-control" name="gmail" placeholder="Gmail:" value="${sessionScope.loginUser.gmail}" readonly="">
                        </div>
                        <div class="col-md-6 mb-3">
                            <label class="form-label">Địa chỉ:</label>
                            <input type="text" class="form-control" name="address" placeholder="Nhập địa chỉ của bạn" value="${sessionScope.loginUser.address}">
                        </div>

                    </div>
                    <button type="submit" class="btn btn-primary mt-3 w-100">Cập nhật thông tin</button>

                    <a href="${pageContext.request.contextPath}/home" class="btn1">Back to Home</a>
                </form>
            </c:if>
        </div>
    </body>
</html>
