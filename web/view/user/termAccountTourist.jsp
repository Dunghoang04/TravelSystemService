<%--
* Copyright (C) 2025, Group 6.
 * ProjectCode/Short Name of Application: TravelAgentService 
 * Support Management and Provide Travel Service System 
 *
 * Record of change:
 * DATE        Version    AUTHOR            DESCRIPTION
 * 2025-06-07  1.0        Hà Thị Duyên      First implementationF
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<!DOCTYPE html>
<html>
    <head>
        <title>Điều khoản Dịch vụ</title>
        <link rel="stylesheet" href="css/style.css" />
        <style>
            body {
                font-family: Arial, sans-serif;
                background-color: #f8f9fa;
                color: #333;
                line-height: 1.6;
                padding: 20px;
            }

            .container {
                max-width: 900px;
                margin: auto;
                background: white;
                padding: 30px;
                box-shadow: 0 0 10px rgba(0,0,0,0.1);
                border-radius: 8px;
            }

            h1 {
                color: #007bff;
                border-bottom: 2px solid #ddd;
                padding-bottom: 10px;
            }

            h2 {
                color: #0056b3;
                margin-top: 25px;
            }
            p{
                margin-top: 15px;
                margin-left: 85%;
                font-size: 20px;
                
            }
            a{
                color: red;
                text-decoration: none;
            }

        </style>
    </head>
    <body>
        <div class="container">
            <h1>Điều khoản Dịch vụ</h1>
            <h2>1. Chấp nhận Điều khoản</h2>
            <ul>
                <li>Bằng việc đăng ký tài khoản và sử dụng nền tảng, bạn đồng ý tuân thủ tất cả các điều khoản và điều kiện dưới đây...</li>
            </ul>
            
            <h2>2. Tài khoản người dùng</h2>
            <ul>
                <li>Bạn phải cung cấp thông tin chính xác, đầy đủ và cập nhật khi đăng ký.</li>
                <li>Bạn chịu trách nhiệm bảo mật thông tin đăng nhập và mọi hoạt động xảy ra dưới tài khoản của mình.</li>
            </ul>

            <h2>3. Quy định với người mua tour</h2>
            <ul>
                <li>Người mua có thể tìm kiếm và đặt tour, và có nghĩa vụ thanh toán đúng hạn...</li>
                <li>Khi đặt tour, bạn cần đọc kỹ nội dung tour, điều kiện hoàn/hủy, và các quy định riêng của nhà cung cấp.</li>

            </ul>


            <h2>4. Nội dung bị cấm</h2>
            <ul>
                <li>Đăng thông tin giả mạo, xuyên tạc, vi phạm thuần phong mỹ tục hoặc pháp luật Việt Nam.</li>
                <li>Sử dụng bot, script, hoặc hành vi gian lận để thao túng dữ liệu.</li>
            </ul>

            <h2>5. Quyền của sàn giao dịch</h2>
            <ul>
                <li>Có quyền tạm khóa tài khoản vi phạm, sửa đổi điều khoản và thông báo trước cho người dùng.</li>
            </ul>

            <h2>6. Chính sách bảo mật</h2>
             <ul>
                <li>Thông tin cá nhân của bạn được bảo vệ theo Chính sách bảo mật của sàn. Chúng tôi cam kết không tiết lộ trái phép thông tin cho bên thứ ba nếu không có sự đồng ý của bạn, trừ khi được yêu cầu bởi pháp luật.</li>
            </ul>

            <h2>7. Giới hạn trách nhiệm</h2>
             <ul>
                <li>Sàn chỉ là nền tảng trung gian. Mọi giao dịch và thỏa thuận giữa người mua và nhà cung cấp tour được thực hiện trực tiếp giữa hai bên.</li>
                <li>Sàn không chịu trách nhiệm nếu nhà cung cấp không thực hiện đúng cam kết, hoặc người mua vi phạm điều khoản đặt tour.</li>
            </ul>

            <h2>8. Luật áp dụng</h2>
            <ul>
                <li>Các điều khoản này được điều chỉnh bởi pháp luật Việt Nam. Mọi tranh chấp phát sinh sẽ được giải quyết tại tòa án có thẩm quyền tại Việt Nam.</li>
            </ul>

            <h2>9. Liên hệ</h2>
             <ul>
                <li>Mọi thắc mắc, vui lòng liên hệ: <strong>goViet1901@gmail.com</strong></li>
            </ul>
            

            <p><a href="${pageContext.request.contextPath}/GmailUser">Quay lại</a><p>

        </div>
    </body>
</html>
