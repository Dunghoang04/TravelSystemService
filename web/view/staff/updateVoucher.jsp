<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<fmt:setLocale value="en_US"/>
<!DOCTYPE html>
<html>
    <head>
        <title>Cập Nhật Voucher</title>
        <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.0/dist/css/bootstrap.min.css" rel="stylesheet" />
    </head>
    <body style="padding-bottom: 60px;">
        <div class="container mt-5">
            <h2 class="mb-4">Cập Nhật Thông Tin thẻ khuyến mãi</h2>
            <c:if test="${not empty errorMsg}">
                <div class="alert alert-danger">${errorMsg}</div>
            </c:if>

            <!-- Format lại số tránh hiển thị dạng 2.0E7 -->
            <fmt:formatNumber value="${voucher.maxDiscountAmount}" pattern="#0.##" var="formattedMaxDiscountAmount"/>
            <fmt:formatNumber value="${voucher.minAmountApply}" pattern="#0.##" var="formattedMinAmountApply"/>
            <fmt:formatNumber value="${voucher.percentDiscount}" pattern="#0.##" var="formattedPercentDiscount"/>

            <form action="updatevoucher" method="post">
                <input type="hidden" name="voucherId" value="${voucher.voucherId}"/>

                <div class="mb-3">
                    <label class="form-label">Mã thẻ</label>
                    <input type="text" name="voucherCode" class="form-control" value="${voucher.voucherCode}" readonly/>
                </div>

                <div class="mb-3">
                    <label class="form-label">Tên thẻ</label>
                    <input type="text" name="voucherName" class="form-control" value="${voucher.voucherName}" required />
                </div>

                <div class="mb-3">
                    <label class="form-label">Mô tả</label>
                    <textarea name="description" class="form-control">${voucher.description}</textarea>
                </div>
                <c:if test="${not empty voucherNameErro}">
                    <div class="alert alert-danger">${description}</div>
                </c:if>

                <div class="mb-3">
                    <label class="form-label">Phần trăm khuyến mãi (%)</label>
                    <input type="number" name="percentDiscount" class="form-control" value="${formattedPercentDiscount}" step="0.01" required />
                </div>
                <c:if test="${not empty percenDiscountErro}">
                    <div class="alert alert-danger">${percenDiscountErro}</div>
                </c:if>

                <div class="mb-3">
                    <label class="form-label">Giảm tối đa</label>
                    <input type="number" name="maxDiscountAmount" class="form-control" value="${formattedMaxDiscountAmount}" step="0.01" required />
                </div>
                <c:if test="${not empty maxDiscountAmountErro}">
                    <div class="alert alert-danger">${maxDiscountAmountErro}</div>
                </c:if>

                <div class="mb-3">
                    <label class="form-label">Giá tối thiểu áp dụng</label>
                    <input type="number" name="minAmountApply" class="form-control" value="${formattedMinAmountApply}" step="0.01" required />
                </div>
                <c:if test="${not empty minAmountApplyErro}">
                    <div class="alert alert-danger">${minAmountApplyErro}</div>
                </c:if>

                <div class="mb-3">
                    <label class="form-label">Ngày bắt đầu</label>
                    <input type="date" name="startDate" class="form-control" value="${voucher.startDate}" required />
                </div>
                <c:if test="${not empty startDateErro}">
                    <div class="alert alert-danger">${startDateErro}</div>
                </c:if>

                <div class="mb-3">
                    <label class="form-label">Ngày kết thúc</label>
                    <input type="date" name="endDate" class="form-control" value="${voucher.endDate}" required />
                </div>
                <c:if test="${not empty endDateErro}">
                    <div class="alert alert-danger">${endDateErro}</div>
                </c:if>

                <div class="mb-3">
                    <label class="form-label">Số lượng</label>
                    <input type="number" name="quantity" class="form-control" value="${voucher.quantity}" required />
                </div>
                <c:if test="${not empty quantityErro}">
                    <div class="alert alert-danger">${quantityErro}</div>
                </c:if>

                <div class="mb-3">
                    <label class="form-label">Trạng thái</label>
                    <select name="status" class="form-control">
                        <option value="1" ${voucher.status == 1 ? "selected" : ""}>Hoạt động</option>
                        <option value="0" ${voucher.status == 0 ? "selected" : ""}>Không hoạt động</option>
                    </select>
                </div>

                <div class="d-flex justify-content-center gap-3 mt-4 mb-5">
                    <button type="submit" class="btn btn-primary px-4">Cập Nhật</button>
                    <a href="listvoucher" class="btn btn-secondary px-4">Quay lại</a>
                </div>
            </form>
        </div>
    </body>
</html>
