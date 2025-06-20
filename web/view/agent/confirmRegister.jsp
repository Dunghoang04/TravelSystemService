<%-- 
 * Copyright (C) 2025, Group 6.
 * ProjectCode/Short Name of Application: TravelSystemService 
 * Support Management and Provide Travel Service System 
 *
 * Record of change:
 * DATE        Version    AUTHOR            DESCRIPTION
 * 2025-06-07  1.0        Quynh Mai          First implementation
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>Xác nhận Đăng ký</title>
        <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.2.3/dist/css/bootstrap.min.css" rel="stylesheet" crossorigin="anonymous">
        <!-- Favicon -->
        <link href="${pageContext.request.contextPath}/img/favicon.ico" rel="icon">

        <!-- Google Web Fonts -->
        <link rel="preconnect" href="https://fonts.googleapis.com">
        <link rel="preconnect" href="https://fonts.gstatic.com" crossorigin>
        <link href="https://fonts.googleapis.com/css2?family=Heebo:wght@400;500;600&family=Nunito:wght@600;700;800&display=swap" rel="stylesheet">

        <!-- Icon Font Stylesheet -->
        <link href="https://cdnjs.cloudflare.com/ajax/libs/font-awesome/5.10.0/css/all.min.css" rel="stylesheet">
        <link href="https://cdn.jsdelivr.net/npm/bootstrap-icons@1.4.1/font/bootstrap-icons.css" rel="stylesheet">

        <!-- Libraries Stylesheet -->
        <link href="${pageContext.request.contextPath}/assets/lib/animate/animate.min.css" rel="stylesheet">
        <link href="${pageContext.request.contextPath}/assets/lib/owlcarousel/assets/owl.carousel.min.css" rel="stylesheet">

        <!-- Template Stylesheet -->
        <link href="${pageContext.request.contextPath}/assets/css/style.css" rel="stylesheet">
        <style>
            body {
                background-size: cover;
                background-repeat: no-repeat;
                background-position: center;
                margin: 0;
                min-height: 100vh;
                display: flex;
                flex-direction: column;
            }

            .main-content {
                flex: 1;
                display: flex;
                justify-content: center;
                align-items: center;
                padding-top: 200px;
                padding-bottom: 50px;
            }
            .confirmation-container {
                text-align: center;
                padding: 30px;
                border-radius: 2px;
                box-shadow: 2px 2px 4px 8px rgba(0, 0, 0, 0.1);
                background-color: white;
                width: 50%;
                height: 300px; /* Fixed height */
                display: flex;
                flex-direction: column;
                justify-content: space-between; 
            }
            .content {
                flex-grow: 1;
                display: flex;
                flex-direction: column;
                justify-content: center;
            }
            .loading {
                margin-top: 20px;
            }
            .success {
                color: green;
                font-size: 18px;
                margin-bottom: 20px;
                display: none;
            }
            .error {
                color: red;
                font-size: 18px;
                margin-bottom: 20px;
                display: none;
            }
            .btn-primary {
                width: 50%;
                padding: 15px;
            }
            #buttonContainer {
                display: none;
                margin-bottom: 20px; 
            }
            
            .text-primary{
                color: #86B817 !important;
            }
            
            .btn-link{
                text-decoration: none !important;
            }
        </style>
    </head>
    <body>
        <%@include file="../layout/header.jsp" %>
        <div class="main-content">
            <div class="confirmation-container">
                <div class="content">
                    <div id="loading" class="loading">
                        <p>Đang gửi email xác nhận, vui lòng đợi...</p>
                        <div class="spinner-border text-primary" role="status">
                            <span class="visually-hidden">Loading...</span>
                        </div>
                    </div>
                    <div id="successMessage" class="success">${successMessage}</div>
                    <div id="errorMessage" class="error"></div>
                </div>
                <div id="buttonContainer" class="text-center">
                    <a href="${pageContext.request.contextPath}/home" class="btn btn-primary w-50">Quay về trang Home</a>
                </div>
            </div>
        </div>
        <!-- Footer Start -->
        <%@include file="../layout/footer.jsp" %>
        <!-- Footer End -->
        <script src="https://code.jquery.com/jquery-3.4.1.min.js"></script>
        <script src="https://cdn.jsdelivr.net/npm/bootstrap@5.2.3/dist/js/bootstrap.bundle.min.js" crossorigin="anonymous"></script>
        <script>
            document.addEventListener('DOMContentLoaded', function () {
                const loading = document.getElementById('loading');
                const successMessage = document.getElementById('successMessage');
                const errorMessage = document.getElementById('errorMessage');
                const buttonContainer = document.getElementById('buttonContainer');

                // Show loading initially
                loading.style.display = 'block';
                successMessage.style.display = 'none';
                errorMessage.style.display = 'none';
                buttonContainer.style.display = 'none';

                // Make AJAX call to send email
                $.ajax({
                    url: '${pageContext.request.contextPath}/RegisterTravelAgentServlet',
                    type: 'GET',
                    data: { service: 'sendConfirmationEmail' },
                    success: function(response) {
                        loading.style.display = 'none';
                        if (response.status === 'success') {
                            successMessage.style.display = 'block';
                            buttonContainer.style.display = 'block';
                        } else {
                            errorMessage.textContent = response.message || 'Không thể gửi email xác nhận. Vui lòng thử lại sau.';
                            errorMessage.style.display = 'block';
                            buttonContainer.style.display = 'block'; // Still show home button to allow user to exit
                        }
                    },
                    error: function(xhr, status, error) {
                        loading.style.display = 'none';
                        errorMessage.textContent = 'Lỗi khi gửi email xác nhận: ' + error;
                        errorMessage.style.display = 'block';
                        buttonContainer.style.display = 'block'; // Still show home button to allow user to exit
                    }
                });
            });
        </script>
        <script src="https://cdn.jsdelivr.net/npm/bootstrap@5.0.0/dist/js/bootstrap.bundle.min.js"></script>
        <script src="${pageContext.request.contextPath}/assets/lib/wow/wow.min.js"></script>
        <script src="${pageContext.request.contextPath}/assets/lib/easing/easing.min.js"></script>
        <script src="${pageContext.request.contextPath}/assets/lib/waypoints/waypoints.min.js"></script>
        <script src="${pageContext.request.contextPath}/assets/lib/owlcarousel/owl.carousel.min.js"></script>
        <script src="${pageContext.request.contextPath}/assets/lib/tempusdominus/js/moment.min.js"></script>
        <script src="${pageContext.request.contextPath}/assets/lib/tempusdominus/js/moment-timezone.min.js"></script>

        <!-- Template Javascript -->
        <script src="${pageContext.request.contextPath}/assets/js/main.js"></script>
    </body>
</html>