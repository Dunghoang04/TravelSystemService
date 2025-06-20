<%-- 
    Document   : confirm
    Created on : Jun 20, 2025
    Author     : [Your Name]
    Description: Merged confirmation page for travel agent and tour actions
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<!DOCTYPE html>
<html>
<head>
    <meta charset="utf-8" />
    <meta name="viewport" content="width=device-width, initial-scale=1" />
    <title>Xác nhận Hành động</title>
    <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.2.3/dist/css/bootstrap.min.css" rel="stylesheet" crossorigin="anonymous">
    <link href="${pageContext.request.contextPath}/assets/css/styles2.css" rel="stylesheet" />
    <style>
        html, body {
            height: 100%;
            margin: 0;
            padding: 0;
            background-color: #f4f6f8;
            color: #333;
        }
        .confirmation-wrapper {
            min-height: 100vh;
            display: flex;
            justify-content: center;
            align-items: center;
            padding: 30px 15px;
        }
        .confirmation-container {
            max-width: 600px;
            width: 100%;
            background-color: #fff;
            padding: 30px;
            border-radius: 15px;
            box-shadow: 0 0 10px rgba(0, 0, 0, 0.05);
            text-align: center;
            word-wrap: break-word;
            overflow-wrap: break-word;
        }
        .confirmation-container p {
            font-size: 16px;
        }
        .success {
            color: green;
            font-weight: 500;
            margin-top: 10px;
        }
        .error {
            color: red;
            font-weight: 500;
            margin-top: 10px;
        }
        .spinner-border {
            margin-top: 15px;
        }
        .button-container {
            margin-top: 25px;
            display: none;
        }
    </style>
</head>
<body>
    <%@include file="../layout/headerAdmin.jsp" %>
    <div id="layoutSidenav">
        <jsp:include page="../layout/sideNavOptionStaff.jsp" />
        <div id="layoutSidenav_content">
            <main>
                <div class="confirmation-wrapper">
                    <div class="confirmation-container">
                        <div id="loading">
                            <p>Đang xử lý hành động, vui lòng đợi...</p>
                            <div class="spinner-border text-success" role="status">
                                <span class="visually-hidden">Loading...</span>
                            </div>
                        </div>
                        <div id="successMessage" class="success" style="display: none;">
                            <c:choose>
                                <c:when test="${sessionScope.actionStatus == 'approve'}">
                                    <c:choose>
                                        <c:when test="${not empty sessionScope.travelAgentName}">
                                            <p>Đại lý <strong>${sessionScope.travelAgentName}</strong> đã được duyệt thành công.</p>
                                        </c:when>
                                        <c:when test="${not empty sessionScope.tourName}">
                                            <p>Tour <strong>${sessionScope.tourName}</strong> đã được duyệt thành công.</p>
                                        </c:when>
                                    </c:choose>
                                    <p>Email xác nhận đã được gửi.</p>
                                </c:when>
                                <c:when test="${sessionScope.actionStatus == 'reject'}">
                                    <c:choose>
                                        <c:when test="${not empty sessionScope.travelAgentName}">
                                            <p>Đại lý <strong>${sessionScope.travelAgentName}</strong> đã bị từ chối.</p>
                                        </c:when>
                                        <c:when test="${not empty sessionScope.tourName}">
                                            <p>Tour <strong>${sessionScope.tourName}</strong> đã bị từ chối.</p>
                                        </c:when>
                                    </c:choose>
                                    <p>Email xác nhận đã được gửi.</p>
                                </c:when>
                                <c:otherwise>
                                    <p class="error">Đã có lỗi xảy ra: <strong>${sessionScope.errorMessage}</strong></p>
                                </c:otherwise>
                            </c:choose>
                        </div>
                        <div id="errorMessage" class="error" style="display: none;"></div>
                        <div id="buttonContainer" class="button-container">
                            <c:choose>
                                <c:when test="${not empty sessionScope.travelAgentName}">
                                    <a href="${pageContext.request.contextPath}/ManageTravelAgentRegister?service=list" class="btn btn-primary">Quay lại danh sách</a>
                                </c:when>
                                <c:otherwise>
                                    <a href="${pageContext.request.contextPath}/ManageTourCreate?service=list" class="btn btn-primary">Quay lại danh sách</a>
                                </c:otherwise>
                            </c:choose>
                        </div>
                    </div>
                </div>
            </main>
        </div>
    </div>

    <script src="https://code.jquery.com/jquery-3.6.0.min.js"></script>
    <script src="https://cdn.jsdelivr.net/npm/bootstrap@5.2.3/dist/js/bootstrap.bundle.min.js" crossorigin="anonymous"></script>
    <script>
        $(document).ready(function() {
            const loading = $('#loading');
            const successMessage = $('#successMessage');
            const errorMessageDiv = $('#errorMessage');
            const buttonContainer = $('#buttonContainer');

            // Determine the servlet URL based on context (travel agent or tour)
            var servletUrl = "${not empty sessionScope.travelAgentName ? '/ManageTravelAgentRegister' : '/ManageTourCreate'}";

            $.ajax({
                url: '${pageContext.request.contextPath}' + servletUrl,
                type: 'GET',
                data: {service: 'sendConfirmationEmail'},
                success: function(response) {
                    setTimeout(() => {
                        loading.hide();
                        if (response.status === 'sent') {
                            successMessage.show();
                            buttonContainer.show();
                            $.post('${pageContext.request.contextPath}' + servletUrl, {service: 'clearSession'});
                        } else {
                            errorMessageDiv.text(response.message || 'Lỗi không xác định khi xử lý.');
                            errorMessageDiv.show();
                            buttonContainer.show();
                        }
                    }, 1000);
                },
                error: function(xhr, status, error) {
                    setTimeout(() => {
                        loading.hide();
                        errorMessageDiv.text('Lỗi khi xử lý hành động: ' + error);
                        errorMessageDiv.show();
                        buttonContainer.show();
                    }, 1000);
                }
            });
        });
    </script>
</body>
</html>