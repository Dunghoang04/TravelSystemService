<%-- 
    Document   : adduser
    Created on : Feb 11, 2025, 12:57:17 AM
    Author     : Dell
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html lang="vi">
    <head>
        <meta charset="UTF-8">
        <title>Thêm người dùng</title>
        <style>
            * {
                box-sizing: border-box;
            }

            body {
                font-family: 'Segoe UI', sans-serif;
                background-color: #f4f6f9;
                margin: 0;
                padding: 0;
                display: flex;
                justify-content: center;
                align-items: center;
                height: 100vh;
            }

            .container {
                width: 800px;
                background-color: white;
                padding: 40px 50px;
                border-radius: 12px;
                box-shadow: 0 8px 20px rgba(0,0,0,0.15);
            }

            h2 {
                text-align: center;
                color: #333;
                margin-bottom: 30px;
            }

            form {
                display: grid;
                grid-template-columns: 1fr 1fr;
                gap: 20px 30px;
            }

            label {
                display: block;
                font-weight: 500;
                color: #333;
                margin-bottom: 6px;
            }

            input, select {
                width: 100%;
                padding: 10px;
                font-size: 14px;
                border: 1px solid #ccc;
                border-radius: 6px;
            }

            .full-width {
                grid-column: 1 / 3;
            }

            input[type="submit"] {
                background-color: #007BFF;
                color: white;
                font-weight: bold;
                border: none;
                cursor: pointer;
                transition: background-color 0.3s ease;
            }

            input[type="submit"]:hover {
                background-color: #0056b3;
            }

            
        </style>
    </head>
    <body>
        <div class="container">
            <h2>Thêm Người Dùng</h2>
            <form>
                <div>
                    <label for="gmail">Email</label>
                    <input type="email" id="gmail" name="gmail" required>
                 
                </div>

                <div>
                    <label for="password">Mật khẩu</label>
                    <input type="password" id="password" name="password" required>
                </div>

                <div>
                    <label for="firstName">Họ</label>
                    <input type="text" id="firstName" name="firstName" required>
                </div>

                <div>
                    <label for="lastName">Tên</label>
                    <input type="text" id="lastName" name="lastName" required>
                </div>

                <div>
                    <label for="dob">Ngày sinh</label>
                    <input type="date" id="dob" name="dob" required>
                </div>

                <div>
                    <label for="gender">Giới tính</label>
                    <select id="gender" name="gender" required>
                        <option value="">-- Chọn giới tính --</option>
                        <option value="Nam">Nam</option>
                        <option value="Nữ">Nữ</option>
                        <option value="Khác">Khác</option>
                    </select>
                </div>

                <div>
                    <label for="address">Địa chỉ</label>
                    <input type="text" id="address" name="address">
                </div>

                <div>
                    <label for="phone">Số điện thoại</label>
                    <input type="text" id="phone" name="phone">
                </div>

                <div>
                    <label for="status">Trạng thái</label>
                    <select id="status" name="status" required>
                        <option value="1">Hoạt động</option>
                        <option value="0">Tạm khóa</option>
                    </select>
                </div>

                <div>
                    <label for="role">Vai trò</label>
                    <select id="role" name="roleID" required>
                        <option value="">-- Chọn vai trò --</option>
                        <option value="1">Admin</option>
                        <option value="2">Staff</option>
                        <option value="3">Tourist</option>
                        <option value="4">Agent</option>
                    </select>
                </div>

                <div class="full-width">
                    <input type="submit" value="Thêm người dùng">
                </div>
            </form>
        </div>
    </body>
    
</html>
