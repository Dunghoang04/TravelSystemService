<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html lang="vi">
<head>
    <meta charset="utf-8">
        <title>Tourist - Travel Agency HTML Template</title>
        <meta content="width=device-width, initial-scale=1.0" name="viewport">
        <meta content="" name="keywords">
        <meta content="" name="description">

<!--         Favicon -->
        <link href="img/favicon.ico" rel="icon">

<!--         Google Web Fonts -->
        <link rel="preconnect" href="https://fonts.googleapis.com">
        <link rel="preconnect" href="https://fonts.gstatic.com" crossorigin>
        <link href="https://fonts.googleapis.com/css2?family=Heebo:wght@400;500;600&family=Nunito:wght@600;700;800&display=swap" rel="stylesheet">

<!--         Icon Font Stylesheet -->
        <link href="https://cdnjs.cloudflare.com/ajax/libs/font-awesome/5.10.0/css/all.min.css" rel="stylesheet">
        <link href="https://cdn.jsdelivr.net/npm/bootstrap-icons@1.4.1/font/bootstrap-icons.css" rel="stylesheet">

<!--         Libraries Stylesheet -->
        <link href="assets/lib/animate/animate.min.css" rel="stylesheet">
        <link href="assets/lib/owlcarousel/assets/owl.carousel.min.css" rel="stylesheet">
        <link href="assets/lib/tempusdominus/css/tempusdominus-bootstrap-4.min.css" rel="stylesheet" />

<!--         Customized Bootstrap Stylesheet -->
        <link href="assets/css/bootstrap.min.css" rel="stylesheet">

<!--         Template Stylesheet -->
        <link href="assets/css/style.css" rel="stylesheet">
    <style>
        body {
            background-color: #f9fafb; /* nền sáng */
            font-family: sans-serif;
            color: #111;
            padding-top: 140px;
            text-align: center;
            line-height: 1.6;
        }

        .qr-box {
            background: #fff;
            padding: 30px;
            border-radius: 10px;
            display: inline-block;
            max-width: 100%;
            box-shadow: 0 0 10px rgba(0, 0, 0, 0.1);
        }

        .info {
            font-size: 16px;
            color: #111827;
        }

        .highlight {
            padding: 6px 12px;
            border-radius: 6px;
            font-weight: bold;
            color: #dc2626; /* đỏ đậm chữ */
            background-color: transparent !important; /* không nền */
            border: 1px dashed #dc2626;
        }

        .countdown #timer {
            color: red !important;
            font-size: 18px;
            font-weight: bold;
        }

        .countdown {
            margin-top: 20px;
            background-color: #fde68a;
            color: #000;
            padding: 10px;
            display: inline-block;
            border-radius: 5px;
            font-weight: bold;
        }

        a.back-link {
            display: block;
            margin-top: 30px;
            color: #2563eb;
            text-decoration: none;
            font-size: 16px;
        }
    </style>
</head>
<body>
    <%@include file="../layout/header.jsp"%>

    <div>
        <h2 style="font-size: 28px; font-weight: bold; color: #f59e0b; margin-bottom: 10px;">Nạp tiền vào ví</h2>
        <p style="font-size: 16px; color: #374151; margin-bottom: 30px;">
            Quét mã QR dưới đây bằng app ngân hàng của bạn để chuyển tiền:
        </p>

        <div class="qr-box">
            <img src="https://img.vietqr.io/image/MB-309090499999-compact.png?amount=${amount}&addInfo=${code}" alt="QR code chuyển khoản" width="240" style="margin-bottom: 20px;">
            <div class="info">
                Ngân hàng: <strong style="color: #1d4ed8;">Ngân hàng MB</strong><br/>
                Số tài khoản: <strong style="color: #1d4ed8;">309090499999</strong><br/>
                Chủ tài khoản: <strong style="color: #1d4ed8;">Nguyễn Hữu Hưng</strong><br/>
                Số tiền: <strong style="color: #1d4ed8;">${amount} VNĐ</strong><br/>
                Nội dung chuyển tiền: <span class="highlight">${code}</span>
            </div>
            <div class="countdown">
                Mã donate hết hạn sau <span id="timer"></span>
            </div>
        </div>

        <a href="amounttopay" class="back-link">← Quay lại</a>
    </div>

    

<script>
    document.addEventListener("DOMContentLoaded", function () {
        let timeLeft = 30 * 60; // ✅ luôn là số 1800
        const timerEl = document.getElementById("timer");

        function updateTimer() {
            if (!timerEl) return;

            if (timeLeft <= 0) {
                clearInterval(countdown);
                alert("Mã donate đã hết hạn. Quay về trang trước đó.");
                window.location.href = "amounttopay";
                return;
            }

            const minutes = String(Math.floor(timeLeft / 60)).padStart(2, '0');
            const seconds = String(timeLeft % 60).padStart(2, '0');
            
            timerEl.innerHTML = minutes+":"+seconds;
            console.log("timerEl =", timerEl);
            console.log("minutes:", minutes, "seconds:", seconds);
            timeLeft--;
        }

        const countdown = setInterval(updateTimer, 1000);
        setTimeout(updateTimer, 50);
    });
</script>
    <footer>
         <%@include file="../layout/footer.jsp" %>
    </footer>
</body>
</html>

