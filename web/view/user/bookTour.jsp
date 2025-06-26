<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<<<<<<< OURS
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
            .tour-description {
                white-space: pre-line;
            }
            .voucher-tile:hover {
                background-color: #f8f9fa;
            }
            .btn-qty {
                width: 2.5rem;
            }
            .form-control-qty {
                text-align: center;
            }
            
            body {
                padding-top: 100px;
            }
        </style>
    </head>
    <body class="bg-light">

        <%@include file="../layout/header.jsp" %>

        <div style="margin: 1rem 0 1rem 2rem;">
            <a href="javascript:history.back()" class="btn btn-success">
                <i class="fa fa-arrow-left"></i> Quay lại
            </a>
        </div>

        <form action="booktourservlet" method="post">
            <input type="hidden" name="tourID" value="${tour.tourID}" />
            <div class="container mt-5">
                <div class="row g-4">

                    <!-- LEFT COL -->
                    <div class="col-md-8">
                        <!-- Thông tin chuyến đi -->
                        <div class="card mb-4 shadow-sm">
                            <div class="card-body">
                                <h5 class="card-title">Thông tin chuyến đi</h5>
                                <p class="fw-bold">${tour.tourName}</p>
                                <p class="text-muted fw-bold">${tour.numberOfDay}N${tour.numberOfDay - 1}Đ</p>
                                <div class="row">
                                    <div class="col">
                                        <p class="fw-bold">Khởi hành: <fmt:formatDate value="${tour.startDay}" pattern="dd/MM/yyyy"/></p>
                                    </div>
                                    <div class="col">
                                        <p class="fw-bold">Kết thúc: <fmt:formatDate value="${tour.endDay}" pattern="dd/MM/yyyy"/></p>
                                    </div>
                                </div>
                                <div class="row">
                                    <div class="col">
                                        <p class="fw-bold">Khởi hành từ: ${requestScope.tour.startPlace}</p>
                                    </div>
                                    <div class="col">
                                        <p class="fw-bold">Kết thúc: ${requestScope.tour.endPlace}</p>
                                    </div>
                                </div>
                                <p class="mt-3 fw-bold">Số chỗ còn: ${requestScope.tour.quantity}</p>
                            </div>
                        </div>

                        <!-- Thông tin liên hệ -->
                        <div class="card mb-4 shadow-sm">
                            <div class="card-body">
                                <h5 class="card-title">Thông tin liên hệ</h5>
                                <div class="row g-3">

                                    <div class="col-md-6">
                                        <label class="form-label">Họ</label>
                                        <input type="text" name="firstName" value="${param.firstName}" class="form-control" />
                                        <c:if test="${not empty firstNameError}">
                                            <div class="text-danger small">${firstNameError}</div>
                                        </c:if>
                                    </div>

                                    <div class="col-md-6">
                                        <label class="form-label">Tên</label>
                                        <input type="text" name="lastName" value="${param.lastName}" class="form-control" />
                                        <c:if test="${not empty lastNameError}">
                                            <div class="text-danger small">${lastNameError}</div>
                                        </c:if>
                                    </div>

                                    <div class="col-md-6">
                                        <label class="form-label">Email</label>
                                        <input type="email" name="email" value="${param.email}" class="form-control" />
                                        <c:if test="${not empty errorGmail}">
                                            <div class="text-danger small">${errorGmail}</div>
                                        </c:if>
                                    </div>

                                    <div class="col-md-6">
                                        <label class="form-label">Số điện thoại</label>
                                        <input type="tel" name="phone" value="${param.phone}" class="form-control" />
                                        <c:if test="${not empty phoneError}">
                                            <div class="text-danger small">${phoneError}</div>
                                        </c:if>
                                    </div>

                                    <div class="col-md-6">
                                        <label class="form-label">Người lớn (≥12 tuổi)</label>
                                        <div class="input-group">
                                            <button type="button" class="btn btn-outline-secondary btn-qty" onclick="decreaseAdult(this)">−</button>
                                            <input type="number" name="adult" id="adultCount" min="1"
                                                   value="${empty param.adult ? 1 : param.adult}"
                                                   class="form-control form-control-qty" />
                                            <button type="button" class="btn btn-outline-secondary btn-qty" onclick="increase(this)">+</button>
                                        </div>
                                        <c:if test="${not empty numberAdultError}">
                                            <div class="text-danger small">${numberAdultError}</div>
                                        </c:if>
                                    </div>

                                    <div class="col-md-6">
                                        <label class="form-label">Trẻ em (<12 tuổi)</label>
                                        <div class="input-group">
                                            <button type="button" class="btn btn-outline-secondary btn-qty" onclick="decrease(this)">−</button>
                                            <input type="number" name="children" id="childrenCount" min="0"
                                                   value="${empty param.children ? 0 : param.children}"
                                                   class="form-control form-control-qty" />
                                            <button type="button" class="btn btn-outline-secondary btn-qty" onclick="increase(this)">+</button>
                                        </div>
                                        <c:if test="${not empty numberChildrenError}">
                                            <div class="text-danger small">${numberChildrenError}</div>
                                        </c:if>
                                    </div>

                                    <div class="col-12">
                                        <label class="form-label">Ghi chú</label>
                                        <textarea name="note" rows="4" class="form-control"
                                                  placeholder="Nhập ghi chú (nếu có)...">${param.note}</textarea>
                                    </div>

                                    <div class="col-12 form-check">
                                        <input type="hidden" name="isOther" value="0">
                                        <input class="form-check-input" type="checkbox" name="isOther" value="1" id="isOther"
                                               <c:if test="${param.isOther == '1'}">checked</c:if>>
                                               <label class="form-check-label" for="isOther">Tôi đặt phòng giúp cho người khác</label>
                                        </div>

                                    </div>
                                </div>
                            </div>
                                               
                                               <!-- Phương thức thanh toán -->
<div class="card mb-4 shadow-sm">
    <div class="card-body">
        <h5 class="card-title">Phương thức thanh toán</h5>

        <div class="form-check">
            <input class="form-check-input" type="radio" name="paymentMethodID" id="vietqr" value="1" checked>
            <label class="form-check-label" for="vietqr">
                Chuyển khoản bằng ví
            </label>
        </div>

        <div class="form-check mt-2">
            <input class="form-check-input" type="radio" name="paymentMethodID" id="vnpay" value="2">
            <label class="form-check-label" for="vnpay">
                <img src="assets/img/vnpay.png" alt="VNPay QR" style="height: 24px; margin-right: 5px;">
                VNPay QR
            </label>
        </div>
    </div>
</div>
                                               
                            <!-- Thông tin dịch vụ -->
                            <div class="card mb-4 shadow-sm">
                                <div class="card-body">
                                    <h5 class="card-title">Thông tin dịch vụ</h5>

                                    <h6 class="mt-3">Bao Gồm</h6>
                                    <p class="tour-description">${tour.tourInclude}</p>

                                <h6 class="mt-3">Không Bao Gồm</h6>
                                <p class="tour-description">${tour.tourNonInclude}</p>
                            </div>
                        </div>
                    </div>

                    <!-- RIGHT COL -->
                    <div class="col-md-4">
                        <div class="card sticky-top shadow-sm">
                            <div class="card-body">
                                <h5 class="card-title">Thông tin thanh toán</h5>

                                <c:if test="${not empty error}">
                                    <div class="alert alert-danger">${error}</div>
                                </c:if>
                                <c:if test="${param.success == 'true'}">
                                    <div class="alert alert-success">Đặt thành công</div>
                                </c:if>

                                <img src="${pageContext.request.contextPath}/${not empty tour.image ? tour.image : 'https://via.placeholder.com/600x400'}"
                                     alt="Tour" class="img-fluid rounded mb-3">

                                <p class="fw-semibold">${tour.tourName}</p>
                                <ul class="list-unstyled small text-muted">
                                    <li>Giá trẻ em: <fmt:formatNumber value="${tour.childrenPrice}" type="currency" currencySymbol="đ" maxFractionDigits="0"/></li>
                                    <li>Giá người lớn: <fmt:formatNumber value="${tour.adultPrice}" type="currency" currencySymbol="đ" maxFractionDigits="0"/></li>
                                    <li>Tổng tiền gốc: <span id="totalPrice">0 ₫</span></li>
                                </ul>
                                <hr>
                                <p id="vatPrice" class="small text-muted">Phí VAT (${vat.vatRate}%) : 0 ₫</p>

                                <div id="voucher-section" class="border border-dashed rounded p-3 mb-2 text-center voucher-tile" data-bs-toggle="modal" data-bs-target="#voucherModal">
                                    <i class="bi bi-plus-circle me-2"></i>Chọn mã giảm giá
                                </div>
                                
                                <div id="voucher-applied" class="d-none border rounded bg-light p-2 mb-2">
                                    <div class="d-flex justify-content-between align-items-center">
                                        <span><strong id="voucher-code">--</strong> – <small id="voucher-desc">--</small></span>
                                        <button type="button" class="btn btn-link text-danger p-0" onclick="removeVoucher()">Hủy</button>
                                    </div>
                                </div>
                                <h6>Giảm giá: <span id="discountAmount">0 ₫</span></h6>
                                <h5 class="text-success">Tổng cần thanh toán: <span id="discountedPrice">0 ₫</span></h5>

                                <input type="hidden" id="selectedVoucherId" name="voucherId" value="">
                                <input type="hidden" id="finalAmount" name="finalAmount" value="">

                                <button type="submit" class="btn btn-danger w-100">Thanh toán</button>
                            </div>
                        </div>
                    </div>

                </div>
            </div>
        </form>

        <!-- Voucher Modal -->
        <div class="modal fade" id="voucherModal" tabindex="-1">
            <div class="modal-dialog modal-dialog-centered">
                <div class="modal-content">
                    <div class="modal-header">
                        <h5 class="modal-title">Chọn mã giảm giá</h5>
                        <button type="button" class="btn-close" data-bs-dismiss="modal"></button>
                    </div>
                    <div class="modal-body">
                        <div class="mb-3 d-flex">
                            <input type="text" id="voucher-search" class="form-control me-2" placeholder="Tìm mã giảm giá...">
                            <button class="btn btn-success">Tìm</button>
                        </div>
                        <div class="list-group">
                            <c:forEach var="v" items="${voucherlist}">
                                <label class="list-group-item voucher-tile">
                                    <input type="radio" name="voucherRadio" value="${v.voucherId}"
                                           data-code="${v.voucherCode}"
                                           data-desc="${v.voucherName}"
                                           data-percent="${v.percentDiscount}"
                                           data-max-discount="${v.maxDiscountAmount}"
                                           data-min-apply="${v.minAmountApply}"
                                           class="form-check-input me-2">
                                    <strong>${v.voucherCode}</strong> – ${v.voucherName}
                                    <br><small><fmt:formatDate value="${v.endDate}" pattern="dd/MM/yyyy"/></small>
                                </label>
                            </c:forEach>
                        </div>
                    </div>
                    <div class="modal-footer">
                        <button type="button" class="btn btn-secondary" data-bs-dismiss="modal">Đóng</button>
                        <button type="button" class="btn btn-danger" onclick="applyVoucher()">Áp dụng</button>
                    </div>
                </div>
            </div>
        </div>
        <script src="https://code.jquery.com/jquery-3.6.0.min.js"></script>
        <script src="https://cdn.jsdelivr.net/npm/bootstrap@5.3.0/dist/js/bootstrap.bundle.min.js"></script>
        <script>
                            const adultPrice = ${empty tour.adultPrice ? 0 : tour.adultPrice};
                            const childrenPrice = ${empty tour.childrenPrice ? 0 : tour.childrenPrice};
                            const vatRate = ${empty vat.vatRate ? 0 : vat.vatRate};

                            function getTotal() {
                                const a = parseInt($('#adultCount').val()) || 0;
                                const c = parseInt($('#childrenCount').val()) || 0;
                                return a * adultPrice + c * childrenPrice;
                            }

                            function updateTotalPrice() {
                                const total = getTotal();
                                let discount = 0;
                                const radio = document.querySelector('input[name="voucherRadio"]:checked');
                                if (radio) {
                                    const pct = Number(radio.dataset.percent);
                                    const max = +radio.dataset.maxDiscount;
                                    const minApply = +radio.dataset.minApply;
                                    if (total >= minApply)
                                        discount = Math.min(total * pct / 100, max);
                                }
                                const after = total - discount;
                                const vatAmt = after * vatRate / 100;
                                const finalAmt = after + vatAmt;
                                const fmt = new Intl.NumberFormat('vi-VN', {style: 'currency', currency: 'VND', minimumFractionDigits: 0});
                                $('#totalPrice').text(fmt.format(total));
                                $('#discountAmount').text('- ' + fmt.format(discount));
                                $('#vatPrice').text(`Phí VAT (${vat.vatRate}%): ` + fmt.format(vatAmt));
                                $('#discountedPrice').text(fmt.format(finalAmt));
                                $('#finalAmount').val(finalAmt);
                            }

                            function applyVoucher() {
                                const radio = document.querySelector('input[name="voucherRadio"]:checked');
                                if (!radio)
                                    return;
                                $('#selectedVoucherId').val(radio.value);
                                $('#voucher-code').text(radio.dataset.code);
                                $('#voucher-desc').text(radio.dataset.desc);
                                $('#voucher-applied').removeClass('d-none');
                                $('#voucher-section').addClass('d-none');
                                const mod = bootstrap.Modal.getInstance(document.getElementById('voucherModal'));
                                mod.hide();
                                updateTotalPrice();
                            }

                            function removeVoucher() {
                                $('#voucher-applied').addClass('d-none');
                                $('#voucher-section').removeClass('d-none');
                                $('#discountAmount').text('0 ₫');
                                $('#selectedVoucherId').val('');
                                $('input[name="voucherRadio"]:checked').prop('checked', false);
                                updateTotalPrice();
                            }

                            function increase(btn) {
                                const inp = btn.parentElement.querySelector('input');
                                inp.stepUp();
                                updateTotalPrice();
                            }
                            function decrease(btn) {
                                const inp = btn.parentElement.querySelector('input');
                                inp.stepDown();
                                updateTotalPrice();
                            }
                            function decreaseAdult(btn) {
                                const inp = btn.parentElement.querySelector('input');
                                if (parseInt(inp.value) > 1)
                                    inp.stepDown();
                                updateTotalPrice();
                            }
                            $(document).ready(() => {
                                $('#adultCount,#childrenCount').on('input', updateTotalPrice);
                                updateTotalPrice();
                            });
        </script>
    </body>
    <footer> <%@include file="../layout/footer.jsp" %>
    </footer>
=======

<!DOCTYPE html>
<html lang="vi">
    <head>
        <meta charset="UTF-8">
        <title>Đặt chuyến du lịch</title>
        <script src="https://cdn.tailwindcss.com"></script>
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

        </style>

    </head>
    <body class="bg-gray-100 font-sans">
        <%@include file="../layout/header.jsp" %>   

        <div class="absolute top-24 left-6 z-50">
            <a href="javascript:history.back()" 
               class="inline-flex items-center px-4 py-2 text-sm font-medium text-white bg-green-600 rounded-full shadow-md hover:bg-green-700 transition duration-200">
                <i class="fas fa-arrow-left mr-2"></i> Quay lại
            </a>
        </div>
        <c:if test="${not empty error}">
            <div style="color:red">${error}</div>
        </c:if>
        <form action="booktourservlet" method="post">
            <div class="max-w-7xl mx-auto pt-28 p-6 grid grid-cols-1 md:grid-cols-3 gap-6">

                <div class="md:col-span-2 space-y-6">

                    <!-- Thông tin đặt phòng -->
                    <div class="bg-white p-6 rounded shadow">
                        <h2 class="text-xl font-semibold mb-4">Thông tin chuyến đi</h2>
                        <p class="font-bold">${requestScope.tour.tourName}</p>
                        <p class="text-sm text-gray-600 font-bold">${tour.numberOfDay}N${tour.numberOfDay - 1}Đ</p>
                        <div class=" grid grid-cols-2 ">
                            <div>
                                <p class="font-bold">Khởi hành: <fmt:formatDate value="${tour.startDay}" pattern="dd/MM/yyyy"/></p>

                            </div>
                            <div>
                                <p class="font-bold">Kết thúc: <fmt:formatDate value="${tour.endDay}" pattern="dd/MM/yyyy"/></p>

                            </div>
                        </div>
                        <div class=" grid grid-cols-2 ">
                            <div>
                                <p class="font-bold">Khởi hành từ: ${requestScope.tour.startPlace}</p>

                            </div>
                            <div>
                                <p class="font-bold">Kết thúc: ${requestScope.tour.endPlace}</p>

                            </div>
                        </div>

                        <div class="mt-4">
                            <p class="font-bold">Số chỗ còn: ${requestScope.tour.quantity}</p>
                        </div>
                    </div>

                    <!-- Thông tin người liên hệ -->
                    <div class="bg-white p-6 rounded shadow">
                        <h2 class="text-xl font-semibold mb-4">Thông tin liên hệ</h2>

                        <div class="grid grid-cols-1 md:grid-cols-2 gap-4">
                            <div>
                                <label class="block font-medium">Họ</label>
                                <input type="text" class="mt-1 block w-full px-4 py-2 rounded-xl border border-gray-300 shadow-sm focus:ring-2 focus:ring-green-400 focus:border-green-400 transition duration-200"  />
                            </div>
                            <div>
                                <label class="block font-medium">Tên</label>
                                <input type="text" class="mt-1 block w-full px-4 py-2 rounded-xl border border-gray-300 shadow-sm focus:ring-2 focus:ring-green-400 focus:border-green-400 transition duration-200"  />
                            </div>
                            <div>
                                <label class="block font-medium">Email</label>
                                <input type="email" class="mt-1 block w-full px-4 py-2 rounded-xl border border-gray-300 shadow-sm focus:ring-2 focus:ring-green-400 focus:border-green-400 transition duration-200"  />
                            </div>
                            <div>
                                <label class="block font-medium">Số điện thoại</label>
                                <input type="tel" class="mt-1 block w-full px-4 py-2 rounded-xl border border-gray-300 shadow-sm focus:ring-2 focus:ring-green-400 focus:border-green-400 transition duration-200"  />
                            </div>
                            <div class="mt-4">
                                <label class="block font-medium mb-1">Số lượng trẻ em (dưới 12 tuổi):</label>
                                <div class="flex items-center max-w-[180px] border border-gray-300 rounded overflow-hidden shadow-sm">
                                    <button type="button" onclick="decrease(this)" class="w-10 h-10 text-xl bg-gray-100 hover:bg-gray-200">−</button>
                                    <input type="number" name="children" id="childrenCount" min="1" value="1"
                                           class="w-full h-10 text-center outline-none border-x border-gray-300" />
                                    <button type="button" onclick="increase(this)" class="w-10 h-10 text-xl bg-gray-100 hover:bg-gray-200">+</button>
                                </div>
                            </div>

                            <!-- Số lượng người lớn -->
                            <div class="mt-4">
                                <label class="block font-medium mb-1">Số lượng người lớn (trên 12 tuổi):</label>
                                <div class="flex items-center max-w-[180px] border border-gray-300 rounded overflow-hidden shadow-sm">
                                    <button type="button" onclick="decrease(this)" class="w-10 h-10 text-xl bg-gray-100 hover:bg-gray-200">−</button>
                                    <input type="number" name="adult" id="adultCount" min="1" value="1"
                                           class="w-full h-10 text-center outline-none border-x border-gray-300" />
                                    <button type="button" onclick="increase(this)" class="w-10 h-10 text-xl bg-gray-100 hover:bg-gray-200">+</button>
                                </div>
                            </div>


                        </div>
                        <div>
                            <label class="block font-medium">Ghi chú</label>
                            <textarea rows="4" placeholder="Nhập ghi chú (nếu có)..."
                                      class="mt-1 block w-full px-4 py-2 rounded-xl border border-gray-300 shadow-sm resize-none focus:ring-2 focus:ring-green-400 focus:border-green-400 transition duration-200"></textarea>
                        </div>

                        <div class="mt-4">
                            <label class="inline-flex items-center">
                                <input type="checkbox" class="mr-2"> Tôi đặt phòng giúp cho người khác
                            </label>
                        </div>

                    </div>
                    <!-- Phương thức thanh toán -->
                    <div class="bg-white p-6 rounded shadow mt-6">
                        <h2 class="text-xl font-semibold mb-4">Phương thức thanh toán</h2>

                        <div class="space-y-4">
                            <!-- Chuyển khoản VietQR -->
                            <label class="flex items-center space-x-3 cursor-pointer border rounded p-3 hover:bg-gray-50 transition">
                                <input type="radio" name="paymentMethod" value="vietqr" class="form-radio accent-green-600">
                                <div>
                                    <p class="font-medium">Chuyển khoản (VietQR miễn phí)</p>
                                </div>
                            </label>

                            <!-- Thanh toán MoMo -->
                            <label class="flex items-center space-x-3 cursor-pointer border rounded p-3 hover:bg-gray-50 transition">
                                <input type="radio" name="paymentMethod" value="momo" class="form-radio accent-pink-500">
                                <div class="flex items-center space-x-2">
                                    <img src="https://upload.wikimedia.org/wikipedia/vi/f/fe/MoMo_Logo.png" alt="MoMo" class="w-6 h-6">
                                    <p class="font-medium">Ví MoMo</p>
                                </div>
                            </label>

                            <!-- Thanh toán VNPAY -->
                            <label class="flex items-center space-x-3 cursor-pointer border rounded p-3 hover:bg-gray-50 transition">
                                <input type="radio" name="paymentMethod" value="vnpay" class="form-radio accent-blue-600">
                                <div class="flex items-center space-x-2">
                                    <img src="assets/img/vnpay.png" alt="VNPAY" class="w-6 h-6">
                                    <p class="font-medium">VNPAY QR</p>
                                </div>
                            </label>
                        </div>
                    </div>

                </div>

                <div class="bg-white p-4 rounded shadow space-y-3 self-start">
                    <h2 class="text-xl font-semibold">Thông tin thanh toán</h2>
                    <img src="${pageContext.request.contextPath}/${not empty tour.image ? tour.image : 'https://via.placeholder.com/600x400'}" alt="Phòng" class="rounded w-full mb-3">
                    <p class="font-semibold text-lg">${tour.tourName}</p>
                    <ul class="text-sm text-gray-700 list-disc list-inside">
                        <li>Giá tiền người trẻ em: <fmt:formatNumber value="${tour.childrenPrice}" type="currency" currencySymbol="đ" maxFractionDigits="0"/></li>
                        <li>Giá tiền người lớn: <fmt:formatNumber value="${tour.adultPrice}" type="currency" currencySymbol="đ" maxFractionDigits="0"/></li>
                        <li>Tổng tiền: <span id="totalPrice">0đ</span></li>
                    </ul>

                    <hr>

                    <!-- VOUCHER CHỌN -->
                    <div id="voucher-section" class="bg-gray-50 p-4 rounded border border-dashed border-gray-300 cursor-pointer hover:bg-gray-100" onclick="openVoucherModal()">
                        <div class="flex items-center justify-center text-gray-500">
                            <span class="text-2xl font-bold">+</span>
                            <span class="ml-2">Chọn mã giảm giá</span>
                        </div>
                    </div>

                    <!-- VOUCHER ĐÃ ÁP DỤNG -->
                    <div id="voucher-applied" class="hidden mt-3 p-4 bg-green-50 border border-green-300 rounded">
                        <div class="flex justify-between items-center">
                            <div><strong id="voucher-code">--</strong> - <span id="voucher-desc">--</span></div>
                            <button type="button" onclick="removeVoucher()" class="text-red-500 hover:underline">Hủy</button>
                        </div>
                    </div>

                    <!-- MODAL CHỌN VOUCHER -->
                    <div id="voucher-modal" class="fixed inset-0 bg-black bg-opacity-30 flex items-center justify-center hidden z-50">
                        <div class="bg-white p-6 rounded shadow-md w-full max-w-md relative">
                            <h2 class="text-lg font-semibold mb-4">Chọn mã giảm giá</h2>
                            <div class="flex gap-2 mb-4">
                                <input type="text" class="w-full px-4 py-2 border border-gray-300 rounded-lg focus:outline-none focus:ring-2 focus:ring-green-500 transition duration-200" placeholder="Tìm mã giảm giá..." id="voucher-search">
                                <button type="button" class="px-4 py-2 bg-green-500 text-white rounded-lg hover:bg-green-600 transition duration-200">Tìm</button>
                            </div>

                            <c:if test="${not empty voucherlist}">
                                <div style="max-height: 300px; overflow-y: auto;">
                                    <c:forEach var="v" items="${voucherlist}">
                                        <label class="flex items-start gap-3 border rounded p-3 cursor-pointer hover:bg-gray-50">
                                            <input type="radio" name="voucherId" value="${v.voucherCode}" 
                                                   data-id="${v.voucherId}"
                                                   data-desc="${v.voucherName}"
                                                   data-percent="${v.percentDiscount}" 
                                                   data-max-discount="${v.maxDiscountAmount}" 
                                                   data-min-apply="${v.minAmountApply}" 
                                                   class="mt-1">

                                            <div>
                                                <strong>${v.voucherCode}</strong> – ${v.voucherName}<br>
                                                <small><fmt:formatDate value="${v.endDate}" pattern="dd/MM/yyyy"/></small>
                                            </div>
                                        </label>
                                    </c:forEach>
                                </div>
                            </c:if>

                            <div class="mt-4 flex justify-end gap-2">
                                <button type="button" onclick="closeVoucherModal()" class="text-gray-600 hover:underline">Đóng</button>
                                <button type="button" onclick="applyVoucher()" class="bg-red-500 text-white px-4 py-2 rounded hover:bg-red-600">Áp dụng</button>
                            </div>
                        </div>
                    </div>
                    <h3>Giảm giá:</h3> <span id="discountAmount">0 ₫</span><br/>




                    <input type="hidden" id="selectedVoucherId" name="voucherId" value="">
                    <hr>
                    <p class="text-sm text-gray-500">Đã bao gồm thuế và phí</p>
                    <h3 class="text-lg font-medium text-green-600">Tổng giá <span id="discountedPrice">0đ</span></h3>
                    <button type="submit" class="w-full bg-red-500 text-white py-2 rounded hover:bg-red-600 mt-2">Thanh toán</button>
                </div>
            </div>
        </form>

        <script>
            function openVoucherModal() {
                document.getElementById("voucher-modal").classList.remove("hidden");
            }

            function closeVoucherModal() {
                document.getElementById("voucher-modal").classList.add("hidden");
            }

            const adultPrice = ${tour.adultPrice != null ? tour.adultPrice : 0};
            const childrenPrice = ${tour.childrenPrice != null ? tour.childrenPrice : 0};

            function getTotal() {
                const adultCount = parseInt(document.getElementById('adultCount').value) || 0;
                const childrenCount = parseInt(document.getElementById('childrenCount').value) || 0;
                return (adultCount * adultPrice) + (childrenCount * childrenPrice);
            }

            function updateTotalPrice() {
                const total = getTotal();
                const formatter = new Intl.NumberFormat('vi-VN', {style: 'currency', currency: 'VND', minimumFractionDigits: 0});
                document.getElementById('totalPrice').innerText = formatter.format(total);

                const selectedVoucherId = document.getElementById('selectedVoucherId').value;
                if (selectedVoucherId) {
                    applyVoucher(true);
                } else {
                    document.getElementById('discountedPrice').innerText = formatter.format(total);
                }
            }

            function increase(btn) {
                const input = btn.parentElement.querySelector('input');
                input.value = parseInt(input.value) + 1;
                updateTotalPrice();
            }

            function decrease(btn) {
                const input = btn.parentElement.querySelector('input');
                const current = parseInt(input.value);
                if (current > 1) {
                    input.value = current - 1;
                    updateTotalPrice();
                }
            }

            function applyVoucher(fromRecalculate = false) {
                const voucherRadio = document.querySelector('input[name="voucherId"]:checked');
                if (!voucherRadio)
                    return;

                const voucherId = voucherRadio.dataset.id;
                const code = voucherRadio.value;
                const desc = voucherRadio.dataset.desc;
                const percent = Number(voucherRadio.dataset.percent);
                const maxDiscount = Number(voucherRadio.dataset.maxDiscount);
                const minApply = Number(voucherRadio.dataset.minApply);

                const total = getTotal();
                let discount = 0;
                if (total >= minApply) {
                    discount = Math.min((total * percent) / 100, maxDiscount);
                }

                const discountedPrice = total - discount;
                const formatter = new Intl.NumberFormat('vi-VN', {style: 'currency', currency: 'VND', minimumFractionDigits: 0});

                // Cập nhật giá
                document.getElementById('totalPrice').innerText = formatter.format(total);
                document.getElementById('discountAmount').innerText = '- ' + formatter.format(discount);
                document.getElementById('discountedPrice').innerText = formatter.format(discountedPrice);
                document.getElementById('selectedVoucherId').value = voucherId;

                // Cập nhật thẻ voucher áp dụng
                document.getElementById('voucher-code').innerText = code;
                document.getElementById('voucher-desc').innerText = desc;

                // Hiện thẻ voucher áp dụng
                document.getElementById('voucher-applied').classList.remove('hidden');
                document.getElementById('voucher-section').classList.add('hidden');
                closeVoucherModal();
            }



            function removeVoucher() {
                // 1. Ẩn phần voucher đã áp dụng
                document.getElementById("voucher-applied").classList.add("hidden");

                // 2. Hiện lại nút "Chọn mã giảm giá"
                document.getElementById("voucher-section").classList.remove("hidden");

                // 3. Reset giá trị giảm giá về 0
                document.getElementById("discountAmount").innerText = "0 ₫";

                // 4. Reset mã voucher được chọn
                document.getElementById("selectedVoucherId").value = "";

                // 5. Tính lại tổng giá sau giảm (về giá gốc)
                updateTotalPrice(); // Đảm bảo có hàm này để cập nhật lại tổng
            }

            document.addEventListener("DOMContentLoaded", function () {
                document.getElementById('adultCount').addEventListener('input', updateTotalPrice);
                document.getElementById('childrenCount').addEventListener('input', updateTotalPrice);
                updateTotalPrice();
            });
        </script>


>>>>>>> THEIRS
</html>
