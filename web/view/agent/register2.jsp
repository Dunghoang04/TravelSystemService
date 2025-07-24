
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
        <title>Register for Travel Agent</title>
        <meta charset="utf-8">
        <meta content="width=device-width, initial-scale=1.0" name="viewport">
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
                background: url('${pageContext.request.contextPath}/assets/img/re3.jpg');
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
            .register-container {
                background: white;
                padding: 30px;
                border-radius: 12px;
                box-shadow: 0 4px 20px rgba(0, 0, 0, 0.15);
                width: 80%;
                max-width: 900px;
                margin: 0 auto;
            }
            h4 {
                color: #28A745;
                font-size: 28px;
                margin-bottom: 20px;
                text-align: center;
            }
            .image-preview {
                margin-top: 10px;
                width: 100%;
                height: 150px;
                object-fit: contain;
                cursor: pointer;
                display: none;
            }
            .error {
                color: red;
                font-size: 14px;
                margin-top: 5px;
                text-align: left;
            }
            .success {
                color: green;
                font-size: 14px;
                text-align: center;
                margin-top: 5px;
            }
            .required {
                color: red;
                margin-left: 2px;
            }
            .modal-content {
                background: rgba(0, 0, 0, 0.9);
            }
            .modal-body img {
                max-width: 100%;
                max-height: 80vh;
                margin: auto;
                display: block;
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
        <!-- Header -->
        <%@include file="../layout/header.jsp" %>
        <div class="main-content">
            <div class="register-container">
                <c:if test="${not empty errorMessage}">
                    <div class="error">${errorMessage}</div>
                </c:if>            
                <form id="registerForm" action="${pageContext.request.contextPath}/RegisterTravelAgentServlet" method="POST" enctype="multipart/form-data" onsubmit="return validateForm(event)">
                    <input type="hidden" name="service" value="addAgent">

                    <!-- Thông tin đại lý du lịch -->
                    <div class="mb-4">
                        <h4>Thông tin đại lý du lịch</h4>
                        <div class="row g-3">
                            <div class="col-md-8">
                                <div class="mb-3">
                                    <label for="travelAgentName" class="form-label fw-bold">Tên công ty:<span class="required">*</span></label>
                                    <input type="text" class="form-control" id="travelAgentName" name="travelAgentName" value="${sessionScope.travelAgentName}" placeholder="Nhập tên công ty" required>
                                    <c:if test="${not empty validationErrors['travelAgentName']}">
                                        <div class="error">${validationErrors['travelAgentName']}</div>
                                    </c:if>
                                </div>
                            </div>
                            <div class="col-md-4">
                                <div class="mb-3">
                                    <label for="travelAgentGmail" class="form-label fw-bold">Email:<span class="required">*</span></label>
                                    <input type="text" class="form-control" id="travelAgentGmail" name="travelAgentGmail" value="${sessionScope.travelAgentGmail}" placeholder="Nhập gmail của công ty" required>
                                    <c:if test="${not empty validationErrors['travelAgentGmail']}">
                                        <div class="error">${validationErrors['travelAgentGmail']}</div>
                                    </c:if>
                                </div>
                            </div>
                        </div>
                        <div class="row g-3">
                            <div class="col-md-4">
                                <div class="mb-3">
                                    <label for="hotLine" class="form-label fw-bold">Số HotLine:<span class="required">*</span></label>
                                    <input type="text" class="form-control" id="hotLine" name="hotLine" value="${sessionScope.hotline}" placeholder="Nhập số hot line" required>
                                    <c:if test="${not empty validationErrors['hotLine']}">
                                        <div class="error">${validationErrors['hotLine']}</div>
                                    </c:if>
                                </div>
                            </div>
                            <div class="col-md-8">
                                <div class="mb-3">
                                    <label for="address" class="form-label fw-bold">Địa chỉ:<span class="required">*</span></label>
                                    <input type="text" class="form-control" id="address" name="address" value="${sessionScope.travelAgentAddress}" placeholder="Nhập địa chỉ công ty" required>
                                    <c:if test="${not empty validationErrors['travelAgentAddress']}">
                                        <div class="error">${validationErrors['travelAgentAddress']}</div>
                                    </c:if>
                                </div>
                            </div>
                        </div>
                        <div class="row g-3">
                            <div class="col-md-4">
                                <div class="mb-3">
                                    <label for="establishmentDate" class="form-label fw-bold">Ngày thành lập:<span class="required">*</span></label>
                                    <input type="date" class="form-control" id="establishmentDate" name="establishmentDate" value="${sessionScope.establishmentDate}" required>
                                    <c:if test="${not empty validationErrors['establishmentDate']}">
                                        <div class="error">${validationErrors['establishmentDate']}</div>
                                    </c:if>
                                </div>
                            </div>
                            <div class="col-md-4">
                                <div class="mb-3">
                                    <label for="taxCode" class="form-label fw-bold">Mã số thuế:<span class="required">*</span></label>
                                    <input type="text" class="form-control" id="taxCode" name="taxCode" value="${sessionScope.taxCode}" placeholder="Nhập mã số thuế" required>
                                    <c:if test="${not empty validationErrors['taxCode']}">
                                        <div class="error">${validationErrors['taxCode']}</div>
                                    </c:if>
                                </div>
                            </div>                        
                        </div>
                    </div>

                    <!-- Người đại diện -->
                    <div class="mb-4">
                        <h4>Người đại diện</h4>
                        <div class="row g-3">
                            <div class="col-md-4">
                                <div class="mb-3">
                                    <label for="representativeFirstName" class="form-label fw-bold">Họ:<span class="required">*</span></label>
                                    <input type="text" class="form-control" id="representativeFirstName" name="representativeFirstName" value="${sessionScope.firstName}" placeholder="Nhập họ" required>
                                    <c:if test="${not empty validationErrors['firstName']}">
                                        <div class="error">${validationErrors['firstName']}</div>
                                    </c:if>
                                </div>
                            </div>
                            <div class="col-md-4">
                                <div class="mb-3">
                                    <label for="representativeLastName" class="form-label fw-bold">Tên:<span class="required">*</span></label>
                                    <input type="text" class="form-control" id="representativeLastName" name="representativeLastName" value="${sessionScope.lastName}" placeholder="Nhập tên" required>
                                    <c:if test="${not empty validationErrors['lastName']}">
                                        <div class="error">${validationErrors['lastName']}</div>
                                    </c:if>
                                </div>
                            </div>
                            <div class="col-md-4">
                                <div class="mb-3">
                                    <label for="representativePhone" class="form-label fw-bold">Số điện thoại:<span class="required">*</span></label>
                                    <input type="text" class="form-control" id="representativePhone" name="representativePhone" value="${sessionScope.phone}" placeholder="Nhập số điện thoại" required>
                                    <c:if test="${not empty validationErrors['phone']}">
                                        <div class="error">${validationErrors['phone']}</div>
                                    </c:if>
                                </div>
                            </div>
                        </div>
                        <div class="row g-3">
                            <div class="col-md-8">
                                <div class="mb-3">
                                    <label for="representativeAddress" class="form-label fw-bold">Địa chỉ:<span class="required">*</span></label>
                                    <input type="text" class="form-control" id="representativeAddress" name="representativeAddress" value="${sessionScope.address}" placeholder="Nhập địa chỉ" required>
                                    <c:if test="${not empty validationErrors['address']}">
                                        <div class="error">${validationErrors['address']}</div>
                                    </c:if>
                                </div>
                            </div>
                            <div class="col-md-4">
                                <div class="mb-3">
                                    <label for="dob" class="form-label fw-bold">Ngày sinh:<span class="required">*</span></label>
                                    <input type="date" class="form-control" id="dob" name="dob" value="${sessionScope.dob}" required>
                                    <c:if test="${not empty validationErrors['dob']}">
                                        <div class="error">${validationErrors['dob']}</div>
                                    </c:if>
                                </div>
                            </div>
                        </div>
                        <div class="row g-3">
                            <div class="col-md-4">
                                <div class="mb-3">
                                    <label class="form-label fw-bold">Giới tính:<span class="required">*</span></label>
                                    <div class="d-flex gap-3">
                                        <div class="form-check">
                                            <input type="radio" class="form-check-input" id="male" name="gender" value="Nam" ${sessionScope.gender eq 'Nam' || sessionScope.gender == null ? 'checked' : ''}>
                                            <label class="form-check-label" for="male">Nam</label>
                                        </div>
                                        <div class="form-check">
                                            <input type="radio" class="form-check-input" id="female" name="gender" value="Nữ" ${sessionScope.gender eq 'Nữ' ? 'checked':''}>
                                            <label class="form-check-label" for="female">Nữ</label>
                                        </div>
                                        <c:if test="${not empty validationErrors['gender']}">
                                            <div class="error">${validationErrors['gender']}</div>
                                        </c:if>
                                    </div>
                                </div>
                            </div>
                            <div class="col-md-4">
                                <div class="mb-3">
                                    <label for="representativeIDCard" class="form-label fw-bold">Số căn cước công dân:<span class="required">*</span></label>
                                    <input type="text" class="form-control" id="representativeIDCard" name="representativeIDCard" value="${sessionScope.representativeIDCard}" placeholder="Nhập số CCCD" required>
                                    <c:if test="${not empty validationErrors['representativeIDCard']}">
                                        <div class="error">${validationErrors['representativeIDCard']}</div>
                                    </c:if>
                                </div>
                            </div>
                            <div class="col-md-4">
                                <div class="mb-3">
                                    <label for="dateOfIssue" class="form-label fw-bold">Ngày cấp:<span class="required">*</span></label>
                                    <input type="date" class="form-control" id="dateOfIssue" name="dateOfIssue" value="${sessionScope.dateOfIssue}"  required>
                                    <c:if test="${not empty validationErrors['dateOfIssue']}">
                                        <div class="error">${validationErrors['dateOfIssue']}</div>
                                    </c:if>
                                </div>
                            </div>                

                        </div> 
                        <!-- Tài liệu xác thực --> 
                        <div class="mb-4">
                            <h4>Tài liệu xác thực</h4>
                            <div class="row g-3">
                                <div class="col-md-4">
                                    <div class="mb-3">
                                        <label for="businessLicense" class="form-label fw-bold">Giấy phép kinh doanh:<span class="required">*</span></label>
                                        <input type="file" class="form-control" id="businessLicense" name="businessLicense" accept="image/*" onchange="previewImage(this, 'businessLicensePreview')">
                                        <c:if test="${not empty sessionScope.uploadedFiles['businessLicensePath']}">
                                            <img id="businessLicensePreview" class="image-preview" src="${pageContext.request.contextPath}/${sessionScope.uploadedFiles['businessLicensePath']}" alt="Business License Preview" onclick="showImageModal('businessLicensePreview')">
                                        </c:if>
                                        <c:if test="${empty sessionScope.uploadedFiles['businessLicensePath']}">
                                            <img id="businessLicensePreview" class="image-preview" src="#" alt="Business License Preview" style="display: none;" onclick="showImageModal('businessLicensePreview')">
                                        </c:if>
                                        <c:if test="${not empty validationErrors['businessLicense']}">
                                            <div class="error">${validationErrors['businessLicense']}</div>
                                        </c:if>
                                    </div>
                                </div>
                                <div class="col-4">
                                    <div class="mb-3">
                                        <label for="frontIDCard" class="form-label fw-bold">CCCD mặt trước:<span class="required">*</span></label>
                                        <input type="file" class="form-control" id="frontIDCard" name="frontIDCard" accept="image/*" onchange="previewImage(this, 'frontIDCardPreview')">
                                        <c:if test="${not empty sessionScope.uploadedFiles['frontIDCardPath']}">
                                            <img id="frontIDCardPreview" class="image-preview" src="${pageContext.request.contextPath}/${sessionScope.uploadedFiles['frontIDCardPath']}" alt="Front ID Card Preview" onclick="showImageModal('frontIDCardPreview')">
                                        </c:if>
                                        <c:if test="${empty sessionScope.uploadedFiles['frontIDCardPath']}">
                                            <img id="frontIDCardPreview" class="image-preview" src="#" alt="Front ID Card Preview" style="display: none;" onclick="showImageModal('frontIDCardPreview')">
                                        </c:if>
                                        <c:if test="${not empty validationErrors['frontIDCard']}">
                                            <div class="error">${validationErrors['frontIDCard']}</div>
                                        </c:if>
                                    </div>
                                </div>
                                <div class="col-4">
                                    <div class="mb-3">
                                        <label for="backIDCard" class="form-label fw-bold">CCCD mặt sau:<span class="required">*</span></label>
                                        <input type="file" class="form-control" id="backIDCard" name="backIDCard" accept="image/*" onchange="previewImage(this, 'backIDCardPreview')">
                                        <c:if test="${not empty sessionScope.uploadedFiles['backIDCardPath']}">
                                            <img id="backIDCardPreview" class="image-preview" src="${pageContext.request.contextPath}/${sessionScope.uploadedFiles['backIDCardPath']}" alt="Back ID Card Preview" onclick="showImageModal('backIDCardPreview')">
                                        </c:if>
                                        <c:if test="${empty sessionScope.uploadedFiles['backIDCardPath']}">
                                            <img id="backIDCardPreview" class="image-preview" src="#" alt="Back ID Card Preview" style="display: none;" onclick="showImageModal('backIDCardPreview')">
                                        </c:if>
                                        <c:if test="${not empty validationErrors['backIDCard']}">
                                            <div class="error">${validationErrors['backIDCard']}</div>
                                        </c:if>
                                    </div>
                                </div>
                            </div>
                        </div>

                        <!-- Điều khoản -->
                        <div class="mb-4">                        
                            <div class="row g-3">
                                <div class="col-12">
                                    <div class="mb-3 form-check">
                                        <input type="checkbox" class="form-check-input" name="terms" id="terms" required>
                                        <label class="form-check-label" for="terms">
                                            Tôi đồng ý với tất cả các điều khoản có trong 
                                            <a href="${pageContext.request.contextPath}/view/common/term.jsp">điều khoản dịch vụ đối với đại lý du lịch</a>
                                        </label>
                                    </div>
                                </div>
                            </div>   
                            <c:if test="${empty successMessage}">                                 
                                <div class="row g-3">
                                    <div class="col-md-4"></div>
                                    <div class="col-md-4"></div>
                                    <div class="col-md-4">
                                        <button type="submit" class="btn btn-success w-100">Đăng ký</button>
                                    </div>
                                </div>
                            </c:if>
                        </div>
                </form>
            </div>

        </div>
    </div>
    <%@include file="../layout/footer.jsp" %>


    <!-- Modal for image preview -->
    <div class="modal fade" id="imageModal" tabindex="-1" aria-labelledby="imageModalLabel" aria-hidden="true">
        <div class="modal-dialog modal-dialog-centered">
            <div class="modal-content">
                <div class="modal-body">
                    <img id="modalImage" src="" alt="Enlarged Image">
                </div>
            </div>
        </div>
    </div>

    <script>
        // Hàm preview ảnh
        function previewImage(input, previewId) {
            const preview = document.getElementById(previewId);
            const file = input.files[0];
            if (file) {
                const reader = new FileReader();
                reader.onload = function (e) {
                    preview.src = e.target.result;
                    preview.style.display = 'block';
                };
                reader.readAsDataURL(file);
            }
        }

        // Hàm hiển thị modal khi nhấp vào ảnh
        function showImageModal(previewId) {
            const preview = document.getElementById(previewId);
            const modalImage = document.getElementById('modalImage');
            modalImage.src = preview.src;
            new bootstrap.Modal(document.getElementById('imageModal')).show();
        }

        // Khôi phục ảnh từ sessionScope khi tải trang
        window.onload = function () {
            const businessLicensePreview = document.getElementById('businessLicensePreview');
            const frontIDCardPreview = document.getElementById('frontIDCardPreview');
            const backIDCardPreview = document.getElementById('backIDCardPreview');

            if (businessLicensePreview && businessLicensePreview.src && businessLicensePreview.src !== '#') {
                businessLicensePreview.style.display = 'block';
            }
            if (frontIDCardPreview && frontIDCardPreview.src && frontIDCardPreview.src !== '#') {
                frontIDCardPreview.style.display = 'block';
            }
            if (backIDCardPreview && backIDCardPreview.src && backIDCardPreview.src !== '#') {
                backIDCardPreview.style.display = 'block';
            }
        };

        // Gắn sự kiện onchange
        document.addEventListener('DOMContentLoaded', function () {
            document.getElementById('businessLicense').addEventListener('change', function () {
                previewImage(this, 'businessLicensePreview');
            });
            document.getElementById('frontIDCard').addEventListener('change', function () {
                previewImage(this, 'frontIDCardPreview');
            });
            document.getElementById('backIDCard').addEventListener('change', function () {
                previewImage(this, 'backIDCardPreview');
            });
        });

        function validateForm(event) {
            const businessLicense = document.getElementById('businessLicense').files[0];
            const frontIDCard = document.getElementById('frontIDCard').files[0];
            const backIDCard = document.getElementById('backIDCard').files[0];
            const terms = document.getElementById('terms').checked;

            const hasBusinessLicense = '${sessionScope.uploadedFiles["businessLicensePath"]}' !== '';
            const hasFrontIDCard = '${sessionScope.uploadedFiles["frontIDCardPath"]}' !== '';
            const hasBackIDCard = '${sessionScope.uploadedFiles["backIDCardPath"]}' !== '';

            if (!businessLicense && !hasBusinessLicense) {
                alert('Vui lòng chọn file hình ảnh cho giấy phép kinh doanh!');
                event.preventDefault();
                return false;
            }
            if (!frontIDCard && !hasFrontIDCard) {
                alert('Vui lòng chọn file hình ảnh cho CCCD mặt trước!');
                event.preventDefault();
                return false;
            }
            if (!backIDCard && !hasBackIDCard) {
                alert('Vui lòng chọn file hình ảnh cho CCCD mặt sau!');
                event.preventDefault();
                return false;
            }
            if (!terms) {
                alert('Vui lòng đồng ý với điều khoản dịch vụ!');
                event.preventDefault();
                return false;
            }
            return true;
        }
    </script>
    <script src="https://code.jquery.com/jquery-3.4.1.min.js"></script>
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

