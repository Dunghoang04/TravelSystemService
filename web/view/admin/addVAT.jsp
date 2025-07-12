<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html lang="en">
    <head>
        <meta charset="utf-8" />
        <meta http-equiv="X-UA-Compatible" content="IE=edge" />
        <meta name="viewport" content="width=device-width, initial-scale=1, shrink-to-fit=no" />
        <meta name="description" content="Add VAT for TravelSystemService" />
        <meta name="author" content="Group 6" />
        <title>Add VAT - Admin</title>
        <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.0/dist/css/bootstrap.min.css" rel="stylesheet" />
        <link href="${pageContext.request.contextPath}/assets/css/styles2.css" rel="stylesheet" />
        <script src="https://use.fontawesome.com/releases/v6.3.0/js/all.js" crossorigin="anonymous"></script>
        <style>
            .content {
                margin: 0 auto !important;
                width: 1200px !important;
            }
            .card {
                transition: transform 0.2s !important;
            }
            .card:hover {
                transform: translateY(-5px) !important;
            }
            .form-label {
                font-weight: 500 !important;
            }
            .error-message {
                color: #dc3545 !important;
                font-size: 0.875rem !important;
                margin-top: 0.25rem !important;
            }
            .success-message {
                color: #28a745 !important;
                font-size: 1rem !important;
                font-weight: bold !important;
                margin-bottom: 1rem !important;
            }
            .form-control.is-invalid {
                border-color: #dc3545 !important;
            }
            .form-section {
                margin-bottom: 2rem !important;
            }
            .btn-submit {
                background-color: #28a745 !important;
                border-color: #28a745 !important;
            }
            .btn-submit:hover {
                background-color: #218838 !important;
                border-color: #1e7e34 !important;
            }
            .btn-back {
                background-color: #6c757d !important;
                border-color: #6c757d !important;
            }
            .btn-back:hover {
                background-color: #5a6268 !important;
                border-color: #545b62 !important;
            }
            .card-body form {
                display: block;
                padding: 0;
                margin: 0;
            }
        </style>
    </head>
    <body class="sb-nav-fixed">
        <%@include file="../layout/headerAdmin.jsp" %>
        <div id="layoutSidenav">
            <%@include file="../layout/sideNavOptionAdmin.jsp" %>
            <div id="layoutSidenav_content">
                <main class="content">
                    <div class="container-fluid px-4">
                        <h1 class="mt-4">Thêm VAT</h1>
                        <div class="card mb-4">
                            <div class="card-header">
                                <i class="fas fa-plus me-1"></i>
                                Thêm VAT Mới
                            </div>
                            <div class="card-body">
                                <!-- Hiển thị thông báo thành công (dùng request scope) -->
                                <c:if test="${not empty successMessage}">
                                    <div class="success-message">${successMessage}</div>
                                </c:if>
                                <!-- Hiển thị thông báo lỗi -->
                                <c:if test="${not empty generalError}">
                                    <div class="error-message">${generalError}</div>
                                </c:if>

                                <form action="${pageContext.request.contextPath}/ManageVAT?service=addVAT" method="post">
                                    <div class="form-section">
                                        <div class="row mb-3">
                                            <div class="col-md-6">
                                                <label for="vatRate" class="form-label">Tỷ lệ VAT (%)</label>
                                                <input type="number" class="form-control ${not empty vatRateError ? 'is-invalid' : ''}" 
                                                       id="vatRate" name="vatRate" value="${vatRate}" placeholder="VD: 8" required />
                                                <c:if test="${not empty vatRateError}">
                                                    <div class="error-message">${vatRateError}</div>
                                                </c:if>
                                            </div>
                                            <div class="col-md-6">
                                                <label for="description" class="form-label">Mô tả</label>
                                                <input type="text" class="form-control ${not empty descriptionError ? 'is-invalid' : ''}" 
                                                       id="description" name="description" value="${description}" 
                                                       placeholder="VD: VAT áp dụng từ 30/5 đến 1/7" required />
                                                <c:if test="${not empty descriptionError}">
                                                    <div class="error-message">${descriptionError}</div>
                                                </c:if>
                                            </div>
                                        </div>
                                        <div class="row mb-3">
                                            <div class="col-md-6">
                                                <label for="startDate" class="form-label">Ngày bắt đầu</label>
                                                <input type="date" class="form-control ${not empty startDateError ? 'is-invalid' : ''}" 
                                                       id="startDate" name="startDate" value="${startDate}" required />
                                                <c:if test="${not empty startDateError}">
                                                    <div class="error-message">${startDateError}</div>
                                                </c:if>
                                            </div>
                                            <div class="col-md-6">
                                                <label for="endDate" class="form-label">Ngày kết thúc</label>
                                                <input type="date" class="form-control ${not empty endDateError ? 'is-invalid' : ''}" 
                                                       id="endDate" name="endDate" value="${endDate}" required />
                                                <c:if test="${not empty endDateError}">
                                                    <div class="error-message">${endDateError}</div>
                                                </c:if>
                                            </div>
                                        </div>
                                        <div class="row mb-3">
                                            <div class="col-md-6">
                                                <label for="status" class="form-label">Trạng thái</label>
                                                <select class="form-control ${not empty statusError ? 'is-invalid' : ''}" id="status" name="status" required>
                                                    <option value="">Chọn trạng thái</option>
                                                    <option value="1" ${status == '1' ? 'selected' : ''}>Kích hoạt</option>
                                                    <option value="0" ${status == '0' ? 'selected' : ''}>Không kích hoạt</option>
                                                    <option value="2" ${status == '2' ? 'selected' : ''}>Hết hiệu lực</option>
                                                </select>
                                                <c:if test="${not empty statusError}">
                                                    <div class="error-message">${statusError}</div>
                                                </c:if>
                                            </div>
                                        </div>
                                    </div>
                                    <div class="text-center">
                                        <a href="${pageContext.request.contextPath}/VATServlet?service=listVAT" class="btn btn-back me-2">Quay Lại</a>
                                        <button type="submit" class="btn btn-submit">Thêm VAT</button>
                                    </div>
                                </form>
                            </div>
                        </div>
                    </div>
                </main>
                <footer class="bg-light py-4 mt-auto">
                    <div class="container-fluid px-4">
                        <div class="text-muted text-center">Copyright © TravelSystemService 2025</div>
                    </div>
                </footer>
            </div>
        </div>
        <script src="https://cdn.jsdelivr.net/npm/bootstrap@5.3.0/dist/js/bootstrap.bundle.min.js" crossorigin="anonymous"></script>
        <script src="https://cdn.jsdelivr.net/npm/sweetalert2@11"></script>
        <script src="${pageContext.request.contextPath}/assets/js/scripts.js"></script>
        <script>
            <c:if test="${not empty successMessage}">
                Swal.fire({
                    title: 'Thành công!',
                    text: '${successMessage}',
                    icon: 'success',
                    timer: 2000,
                    showConfirmButton: false
                }).then(() => {
                    window.location.href = '${pageContext.request.contextPath}/VATServlet?service=listVAT';
                });
            </c:if>
            <c:if test="${not empty generalError}">
                Swal.fire({
                    title: 'Lỗi!',
                    text: '${generalError}',
                    icon: 'error',
                    confirmButtonText: 'OK'
                });
            </c:if>
        </script>
    </body>
</html>