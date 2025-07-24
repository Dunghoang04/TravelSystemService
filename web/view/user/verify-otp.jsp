<%--
* Copyright (C) 2025, Group 6.
 * ProjectCode/Short Name of Application: TravelAgentService 
 * Support Management and Provide Travel Service System 
 *
 * Record of change:
 * DATE        Version    AUTHOR            DESCRIPTION
 * 2025-06-07  1.0        Hà Thị Duyên      First implementationF
--%>
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
                background-image: url('<%=request.getContextPath()%>/assets/img/img_9.webp');
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

            .otp-container button:disabled {
                background-color: #ccc;
                cursor: not-allowed;
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
                <input type="number" maxlength="1" oninput="moveNext(this)" <c:if test="${sessionScope.otp == null}">disabled</c:if>>
                <input type="number" maxlength="1" oninput="moveNext(this)" <c:if test="${sessionScope.otp == null}">disabled</c:if>>
                <input type="number" maxlength="1" oninput="moveNext(this)" <c:if test="${sessionScope.otp == null}">disabled</c:if>>
                <input type="number" maxlength="1" oninput="moveNext(this)" <c:if test="${sessionScope.otp == null}">disabled</c:if>>
                <input type="number" maxlength="1" oninput="moveNext(this)" <c:if test="${sessionScope.otp == null}">disabled</c:if>>
                <input type="number" maxlength="1" oninput="moveNext(this)" <c:if test="${sessionScope.otp == null}">disabled</c:if>>
            </div>
            <input type="hidden" name="otp" id="otpHidden">
            <button type="submit" <c:if test="${sessionScope.otp == null}">disabled</c:if>>Xác minh</button>

            <!-- Đếm ngược thời gian -->
            <div id="timer">
                <c:choose>
                    <c:when test="${sessionScope.otp != null}">
                        Thời gian còn lại: <span id="countdown">120</span> giây
                    </c:when>
                    <c:otherwise>
                        Mã OTP không còn hợp lệ.
                    </c:otherwise>
                </c:choose>
            </div>

            <!-- Gửi lại mã nếu hết hạn -->
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
                return true;
            }

            function moveNext(current) {
                if (current.value.length === 1) {
                    let next = current.nextElementSibling;
                    if (next) next.focus();
                }
            }

            // Khởi tạo đồng hồ đếm ngược nếu OTP còn hợp lệ
            <c:if test="${sessionScope.otp != null}">
                let countdown = 120;
                const interval = setInterval(() => {
                    document.getElementById("countdown").textContent = countdown;
                    countdown--;
                    if (countdown < 0) {
                        clearInterval(interval);
                        document.getElementById("timer").textContent = "Mã đã hết hạn.";
                        // Vô hiệu hóa các trường nhập và nút
                        document.querySelectorAll('.otp-inputs input').forEach(input => input.disabled = true);
                        document.querySelector('button[type="submit"]').disabled = true;
                    }
                }, 1000);
            </c:if>
        </script>
    </body>
</html>