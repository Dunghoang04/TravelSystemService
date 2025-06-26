<%-- 
 * Copyright (C) 2025, Group 6.
 * ProjectCode/Short Name of Application: TravelAgentService 
 * Support Management and Provide Travel Service System 
 *
 * Record of change:
 * DATE        Version    AUTHOR                   DESCRIPTION
 * 2025-06-07  1.0        Hoang Tuan Dung          First implementation
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<!DOCTYPE html>
<html>
    <head>
        <meta charset="utf-8" />
        <meta http-equiv="X-UA-Compatible" content="IE=edge" />
        <meta name="viewport" content="width=device-width, initial-scale=1, shrink-to-fit=no" />
        <meta name="description" content="" />
        <meta name="author" content="" />
        <title>Cập nhập thẻ khuyến mãi</title>
        <link href="https://cdn.jsdelivr.net/npm/simple-datatables@7.1.2/dist/style.min.css" rel="stylesheet" />
        <link href="./assets/css/styles2.css" rel="stylesheet" />
        <!--Thư viện chèn bootstrap-->
        <link rel="stylesheet" href="https://cdn.jsdelivr.net/npm/bootstrap-select@1.14.0-beta3/dist/css/bootstrap-select.min.css">
        <link rel="stylesheet" href="https://cdnjs.cloudflare.com/ajax/libs/font-awesome/6.7.2/css/all.min.css" integrity="sha512-Evv84Mr4kqVGRNSgIGL/F/aIDqQb7xQ2vcrdIwxfjThSH8CSR7PBEakCr51Ck+w+/U6swU2Im1vVX0SVk9ABhg==" crossorigin="anonymous" referrerpolicy="no-referrer" />
    </head>
    <style>


        form {
            display: flex;
            flex-wrap: wrap;
            gap: 10px;
            justify-content: center;
        }

        form input, form select, form button {
            height: 35px;
            font-size: 13px;
        }

        button {
            white-space: nowrap;
        }

        html, body {
            height: 100%;
            margin: 0;
            padding: 0;

            overflow-x: hidden;
        }

        .container-xxl {
            min-height: 100vh;
            display: flex;
            flex-direction: column;
        }

        .sidebar {
            height: 100vh;
            position: fixed;
            left: 0;
            top: 0;
            width: 250px;
            background: #f8f9fa;
            overflow-y: auto;
        }

        .content {
            margin-left: 250px;
            padding: 20px;
            width: calc(100% - 250px);
            flex: 1;
            overflow-x: auto;
        }

        table {
            width: 100%;
            border-collapse: collapse;
            word-break: break-word;
        }

        table th, table td {
            padding: 8px;
            border: 1px solid #ddd;
            text-align: left;
            font-size: 13px;
        }

        table th {
            background-color: #f1f1f1;
        }
        #addRestaurantForm{
            display: block;
        }
        .form-group{
            margin-bottom: 15px;
        }

        .time div{
            padding: 0px;
        }
        .time div:nth-child(2){
            padding-left: 10px;
        }
        .selectpicker:hover{
            background: #fff;
            border: 1px solid #CED4DA
        }
        .selectpicker{
            background: #fff;
            border: 1px solid #CED4DA
        }
        .bootstrap-select .dropdown-toggle {
            border: 1px solid #ced4da !important;
            box-shadow: none !important;
            outline: none !important;
            background-color: #fff !important;
            text-align: left !important;
            transition: all 0.2s ease-in-out;
        }
        .bootstrap-select .dropdown-toggle:hover{
            border: 1px solid #ced4da !important;
        }
        .bootstrap-select .dropdown-menu .active,
        .bootstrap-select .dropdown-menu .dropdown-item.active,
        .bootstrap-select .dropdown-menu .selected {
            background-color: transparent !important;
            color: #333 !important;

        }

        select data-content i{
            color: #FFCA2C;
        }

        .bootstrap-select .dropdown-toggle:focus, .bootstrap-select>select.mobile-device:focus+.dropdown-toggle {
            outline: 0!important;
            box-shadow: 0 0 0 0.25rem rgba(13, 110, 253, 0.25)!important;
            outline-offset: 0px!important;
        }
        .bootstrap-select .dropdown-menu i.fa-star,
        .bootstrap-select .dropdown-toggle i.fa-star {
            color: gold !important;
        }
        .errorNoti p{
            margin: 0px;
        }

        .custom-select {
            width: 100%;
            height: 41px;
            padding: 0.5rem 0.75rem;
            border: 1px solid #ccc;
            border-radius: 6px;
            background-color: #fff;
            font-size: 16px;
            box-shadow: none;
            transition: border-color 0.3s ease;
        }

        .custom-select:focus {
            outline: none;
            border-color: #66afe9;
            box-shadow: 0 0 3px rgba(102, 175, 233, 0.6);
        }


    </style>
    <body>
        <%@include file="../layout/headerAdmin.jsp" %>
        <div id="layoutSidenav">
            <%@include file="../layout/sideNavOptionStaff.jsp" %>
            <div id="layoutSidenav_content">
                <main>
                    <div class="card-body px-0 pb-2">
                        <div class="container mt-5">
                            <h2 class="mb-4">Cập nhập thẻ khuyến mãi</h2>
                            <c:if test="${not empty messErro}">
                                <div style="color:red">${messErro}</div>
                            </c:if>


                            <div class="row">

                                <div class="col-md-12">
                                    <form action="updatevoucher" method="POST" >
                                        <input type="hidden" name="voucherId" value="${voucher.voucherId}" />
                                        <div class="col-12 row time">
                                            <div class="col-6 ">
                                                <label class="form-label"><b>Mã thẻ</b> <span class="required" style="color: red">*</span></label>
                                                <input type="text" name="voucherCode" class="form-control"  value="${voucher.voucherCode}" readonly/>
                                            </div>
                                            <div class="col-6 ">
                                                <label class="form-label"><b>Tên thẻ</b> <span class="required" style="color:red">*</span></label>
                                                <input type="text" name="voucherName" class="form-control" placeholder="5-25 ký tự" value="${voucher.voucherName}" required/>
                                                <c:if test="${not empty voucherNameErro}">
                                                    <div style="color:red">${voucherNameErro}</div>
                                                </c:if>

                                            </div>
                                        </div>
                                        <div class="col-12 form-group">
                                            <label class="form-label"><b>Mô tả</b> <span class="required" style="color: red">*</span></label>
                                            <textarea name="description" class="form-control" placeholder="Trên 10 ký tự..." rows="3">${voucher.description}</textarea>
                                            <c:if test="${not empty description}">
                                                <div style="color:red">${description}</div>
                                            </c:if>

                                        </div>

                                        <div class="col-12 row form-group time">
                                            <div class="col-6 form-group">
                                                <label class="form-label"><b>Phần trăm khuyến mãi (%)</b> <span class="required" style="color:red">*</span></label>
                                                <input type="number" name="percentDiscount" class="form-control" placeholder="Nhập 1-100" value="${voucher.percentDiscount.intValue()}" step="0.1" required/>
                                                <c:if test="${not empty percenDiscountErro}">
                                                    <div style="color:red">${percenDiscountErro}</div>
                                                </c:if>
                                            </div>
                                            <div class="col-6 form-group">
                                                <label class="form-label"><b>Số lượng</b> <span class="required" style="color: red">*</span></label>
                                                <input type="number" name="quantity" class="form-control" placeholder="1-100" value="${voucher.quantity}" required/>
                                                <c:if test="${not empty quantityErro}">
                                                    <div style="color:red">${quantityErro}</div>
                                                </c:if>

                                            </div>
                                        </div>
                                        <div class="col-12 row form-group time">
                                            <div class="col-6 form-group">
                                                <label class="form-label"><b>Giảm tối đa</b> <span class="required" style="color:red">*</span></label>
                                                <input type="number" name="maxDiscountAmount" class="form-control" placeholder="Tối đa 100.000.000" value="${voucher.maxDiscountAmount.intValue()}" step="1" required/>
                                                <c:if test="${not empty maxDiscountAmountErro}">
                                                    <div style="color:red">${maxDiscountAmountErro}</div>
                                                </c:if>

                                            </div>
                                            <div class="col-6 form-group time">
                                                <label class="form-label"><b>Giá tối thiểu áp dụng</b> <span class="required"style="color:red">*</span></label>
                                                <input type="number" name="minAmountApply" class="form-control" placeholder="Từ 0 đến 1.000.000.000" value="${voucher.minAmountApply.intValue()}" step="1" required/>
                                                <c:if test="${not empty minAmountApplyErro}">
                                                    <div style="color:red">${minAmountApplyErro}</div>
                                                </c:if>


                                            </div>
                                        </div>

                                        <div class="col-12 row form-group time">
                                            <div class="col-6 form-group">
                                                <label class="form-label"><b>Ngày bắt đầu</b> <span class="required" style="color:red">*</span></label>
                                                <input type="date" name="startDate" class="form-control" value="${voucher.startDate}" required/>
                                                <c:if test="${not empty startDateErro}">
                                                    <div style="color:red">${startDateErro}</div>
                                                </c:if>

                                            </div>
                                            <div class="col-6 form-group">
                                                <label class="form-label"><b>Ngày kết thúc</b> <span class="required" style="color:red">*</span></label>
                                                <input type="date" name="endDate" class="form-control" value="${voucher.endDate}" required/>
                                                <c:if test="${not empty endDateErro}">
                                                    <div style="color:red">${endDateErro}</div>
                                                </c:if>

                                            </div>
                                        </div>

                                        <div class="col-12 row form-group time">
                                            <label class="form-label">Trạng thái <span class="required">*</span></label>
                                            <select name="status" class="form-control">
                                                <option value="1" ${voucher.status == 1 ? "selected" : ""}>Hoạt động</option>
                                                <option value="0" ${voucher.status == 0 ? "selected" : ""}>Không hoạt động</option>
                                            </select>
                                        </div>        
                                        <div class="d-flex justify-content-end">
                                            <button type="submit" class="btn btn-info me-2" name="action" value="insert">Cập nhập</button>
                                            <a href="${pageContext.request.contextPath}/listvoucher"><button type="button" class="btn btn-danger caceladd" 
                                                                                                             data-bs-dismiss="modal" aria-label="Close">Quay lại</button></a>
                                        </div>
                                    </form>
                                </div>
                            </div>
                        </div>
                    </div>
                </main>


                <footer class="py-4 bg-light mt-auto">
                    <%@include file="../layout/footerStaff.jsp" %>
                </footer>
            </div>
        </div>

        <script src="https://cdn.jsdelivr.net/npm/bootstrap@5.2.3/dist/js/bootstrap.bundle.min.js" crossorigin="anonymous"></script>
        <script src="./assets/js/scripts.js"></script>
        <script src="https://cdnjs.cloudflare.com/ajax/libs/Chart.js/2.8.0/Chart.min.js" crossorigin="anonymous"></script>
        <script src="./assets/demo/chart-area-demo.js"></script>
        <script src="./assets/demo/chart-bar-demo.js"></script>
        <script src="https://cdn.jsdelivr.net/npm/simple-datatables@7.1.2/dist/umd/simple-datatables.min.js" crossorigin="anonymous"></script>
        <script src="./assets/js/datatables-simple-demo.js"></script>
        <script src="https://code.jquery.com/jquery-3.6.0.min.js"></script>
        <script src="https://cdn.jsdelivr.net/npm/bootstrap-select@1.14.0-beta3/dist/js/bootstrap-select.min.js"></script>
        <script src="https://cdn.jsdelivr.net/npm/sweetalert2@11"></script>
    </body>
</html>
