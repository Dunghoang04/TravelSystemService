<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>

<!DOCTYPE html>
<html>
    <head>
        <meta charset="UTF-8">
        <title>Xác minh OTP - Du lịch Việt Nam</title>
        <link href="https://fonts.googleapis.com/css2?family=Inter:wght@400;600;700&display=swap" rel="stylesheet">
        <style>
            body {
                font-family: 'Inter', sans-serif;
                background-image: url('<%=request.getContextPath()%>/assets/img/img_9.webp') ; /* Hoặc màu xanh nhạt tươi */
                display: flex;
                justify-content: center;
                align-items: center;
                height: 100vh;
                margin: 0;
            }

            .otp-container {
                background-color: white;
                padding: 40px 30px;
                border-radius: 12px;
                box-shadow: 0 10px 25px rgba(0, 0, 0, 0.1);
                width: 400px;
                text-align: center;
            }

            .otp-container h2 {
                margin-bottom: 20px;
                color: #2c3e50;
            }

            .otp-inputs {
                display: flex;
                justify-content: space-between;
                margin-bottom: 20px;
            }

            .otp-inputs input {
                width: 48px;
                height: 55px;
                font-size: 24px;
                text-align: center;
                border: 1px solid #ccc;
                border-radius: 8px;
                outline: none;
                transition: border-color 0.2s;
            }

            .otp-inputs input:focus {
                border-color: #007bff;
            }

            .otp-container button {
                background-color: #007bff;
                color: white;
                border: none;
                padding: 12px 20px;
                font-size: 16px;
                border-radius: 8px;
                cursor: pointer;
                width: 100%;
            }

            .otp-container button:hover {
                background-color: #0056b3;
            }

            .message {
                margin-top: 15px;
                font-size: 14px;
                color: red;
            }

            #timer {
                margin-top: 15px;
                font-weight: bold;
                color: #007bff;
            }
        </style>
    </head>
    <body>
        <form class="otp-container" action="${pageContext.request.contextPath}/VerifyOtp" method="post" onsubmit="return combineOTP();">
            <h2>Nhập mã xác minh</h2>
            <div class="otp-inputs">
                <input type="text" maxlength="1" oninput="moveNext(this)">
                <input type="text" maxlength="1" oninput="moveNext(this)">
                <input type="text" maxlength="1" oninput="moveNext(this)">
                <input type="text" maxlength="1" oninput="moveNext(this)">
                <input type="text" maxlength="1" oninput="moveNext(this)">
                <input type="text" maxlength="1" oninput="moveNext(this)">
            </div>
            <input type="hidden" name="otp" id="otpHidden">
            <button type="submit">Xác minh</button>

            <!-- Đếm ngược thời gian -->
            <div id="timer">
                Thời gian còn lại: <span id="countdown">120</span> giây
            </div>

            <!-- Gửi lại mã nếu hết hạn  -->
            <div id="resend-link">
                <a href="${pageContext.request.contextPath}/ResendOtp">Gửi lại mã</a>
            </div>

            <c:if test="${not empty error}">
                <div class="message">${error}</div>
            </c:if>



        </form>


        <script>
            function combineOTP() {
                let inputs = document.querySelectorAll('.otp-inputs input');
                let otp = '';
                for (let input of inputs) {
                    otp += input.value;
                }
                document.getElementById('otpHidden').value = otp;
                return true; // không cần validate JS vì đã có validate ở Servlet
            }

            function moveNext(current) {
                if (current.value.length === 1) {
                    let next = current.nextElementSibling;
                    if (next)
                        next.focus();
                }
            }

            // (Tùy chọn) Hiển thị thời gian đếm ngược cho UX
            let countdown = 120;
            const interval = setInterval(() => {
                document.getElementById("countdown").textContent = countdown;
                countdown--;
                if (countdown < 0) {
                    clearInterval(interval);
                    document.getElementById("timer").textContent = "Mã đã hết hạn.";
                }
            }, 1000);
        </script>

    </body>
</html>
