<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<!DOCTYPE html>
<html lang="vi">
    <head>
        <meta charset="utf-8">
        <title>Tourist - Travel Agency HTML Template</title>
        <meta content="width=device-width, initial-scale=1.0" name="viewport">
        <meta content="" name="keywords">
        <meta content="" name="description">

        <!-- Favicon -->
        <link href="img/favicon.ico" rel="icon">

        <!-- Google Web Fonts -->
        <link rel="preconnect" href="https://fonts.googleapis.com">
        <link rel="preconnect" href="https://fonts.gstatic.com" crossorigin>
        <link href="https://fonts.googleapis.com/css2?family=Heebo:wght@400;500;600&family=Nunito:wght@600;700;800&display=swap" rel="stylesheet">

        <!-- Icon Font Stylesheet -->
        <link href="https://cdnjs.cloudflare.com/ajax/libs/font-awesome/5.10.0/css/all.min.css" rel="stylesheet">
        <link href="https://cdn.jsdelivr.net/npm/bootstrap-icons@1.4.1/font/bootstrap-icons.css" rel="stylesheet">

        <!-- Libraries Stylesheet -->
        <link href="assets/lib/animate/animate.min.css" rel="stylesheet">
        <link href="assets/lib/owlcarousel/assets/owl.carousel.min.css" rel="stylesheet">
        <link href="assets/lib/tempusdominus/css/tempusdominus-bootstrap-4.min.css" rel="stylesheet" />

        <!-- Customized Bootstrap Stylesheet -->
        <link href="assets/css/bootstrap.min.css" rel="stylesheet">

        <!-- Template Stylesheet -->
        <link href="assets/css/style.css" rel="stylesheet">
        <style>
            body {
                font-family: "Segoe UI", Roboto, sans-serif;
                background-color: #f8f9fa;
                padding-top: 100px; /* tránh header đè lên nội dung */
            }
            .balance-box {
                background-color: #fff3cd;
                border: 1px solid #ffeeba;
                padding: 20px;
                border-radius: 6px;
                margin-bottom: 30px;
            }

            .balance-box h5 {
                font-size: 1.25rem;
                font-weight: 600;
                font-family: "Segoe UI", Roboto, sans-serif;
            }

            .card-header h5 {
                font-weight: bold;
            }
            .table th, .table td {
                vertical-align: middle;
            }
        </style>
    </head>
    <body>
        <%@include file="../layout/header.jsp" %>

        <div class="container">
            <!-- Số dư hiện tại -->
            <div class="balance-box text-center shadow-sm">
                <h5 class="mb-2 text-dark">Số dư hiện tại:</h5>
                <h3 class="text-success fw-bold">
                    <fmt:formatNumber value="${wallet.balance}" type="currency" currencySymbol="₫" maxFractionDigits="0"/>
                </h3>
            </div>

            <!-- Form nạp tiền -->
            <div class="card mb-5 shadow-sm">
                <div class="card-header bg-success text-white">
                    <h5 class="mb-0">Nạp tiền vào ví</h5>
                </div>
                <div class="card-body">
                    <form action="amounttopay" method="post">
                        <div class="mb-3">
                            <label for="amount" class="form-label fw-semibold">Nhập số tiền muốn nạp (₫):</label>
                            <input type="number" class="form-control" id="amount" name="amount" min="10000" max="100000000"  placeholder="Nhập số tiền" required>
                        </div>
                        <button type="submit" class="btn btn-success">
                            <i class="fas fa-wallet me-1"></i> Tiếp tục nạp tiền
                        </button>
                    </form>
                    
                </div>
            </div>

            <!-- Lịch sử giao dịch -->
            <div class="card shadow-sm">
                <div class="card-header bg-primary text-white">
                    <h5 class="mb-0">Lịch sử giao dịch</h5>
                </div>
                <div class="card-body p-0">
                    <c:if test="${empty transactionHistoryList}">
                        <div class="p-4 text-center text-muted">Không có giao dịch nào.</div>
                    </c:if>
                    <c:if test="${not empty transactionHistoryList}">
                        <div class="table-responsive">
                            <table class="table table-striped mb-0">
                                <thead class="table-light">
                                    <tr>
                                        <th>Ngày giao dịch</th>
                                        <th>Mô tả</th>
                                        <th>Loại</th>
                                        <th>Số tiền</th>
                                    </tr>
                                </thead>
                                <tbody>
                                    <c:forEach var="t" items="${transactionHistoryList}">
                                        <tr>
                                            <td><fmt:formatDate value="${t.transactionDate}" pattern="dd/MM/yyyy"/></td>
                                            <td><c:out value="${t.description}"/></td>
                                            <td>
                                                <c:choose>
                                                    <c:when test="${t.transactionType eq 'RECHARGE'}">
                                                        <span class="text-success fw-bold">Nạp</span>
                                                    </c:when>
                                                    <c:when test="${t.transactionType eq 'PURCHASE'}">
                                                        <span class="text-danger fw-bold">Thanh toán</span>
                                                    </c:when>
                                                    <c:when test="${t.transactionType eq 'REFUND'}">
                                                        <span class="text-warning fw-bold">Hoàn</span>
                                                    </c:when>
                                                    <c:when test="${t.transactionType eq 'ADJUSTMENT'}">
                                                        <span class="text-warning fw-bold">Thay đổi từ hệ thống</span>
                                                    </c:when>
                                                    <c:otherwise>
                                                        <c:out value="${t.transactionType}"/>
                                                    </c:otherwise>
                                                </c:choose>
                                            </td>
                                            <td>
                                                <fmt:formatNumber value="${t.amount}" type="currency" currencySymbol="₫" maxFractionDigits="0"/>
                                            </td>
                                        </tr>
                                    </c:forEach>
                                </tbody>
                            </table>
                        </div>
                        <c:if test="${totalPages > 1}">
                            <nav class="mt-4 mb-5">
                                <ul class="pagination justify-content-center">
                                    <c:forEach var="i" begin="1" end="${totalPages}">
                                        <li class="page-item ${i == currentPage ? 'active' : ''}">
                                            <a class="page-link" href="${pageContext.request.contextPath}/amounttopay?page=${i}">${i}</a>

                                        </li>
                                    </c:forEach>
                                </ul>
                            </nav>
                        </c:if>

                    </c:if>
                </div>
            </div>
        </div>

        <%@include file="../layout/footer.jsp" %>
        <script src="https://cdn.jsdelivr.net/npm/bootstrap@5.3.0/dist/js/bootstrap.bundle.min.js"></script>
    </body>
</html>

