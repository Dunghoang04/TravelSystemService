<%-- 
    Document   : admin
    Created on : May 21, 2025, 2:42:06 PM
    Author     : ad
--%>

<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html lang="en">
    <head>
        <meta charset="utf-8" />
        <meta http-equiv="X-UA-Compatible" content="IE=edge" />
        <meta name="viewport" content="width=device-width, initial-scale=1, shrink-to-fit=no" />
        <meta name="description" content="" />
        <meta name="author" content="" />
        <title>Dashboard - SB Admin</title>
        <link href="https://cdn.jsdelivr.net/npm/simple-datatables@7.1.2/dist/style.min.css" rel="stylesheet" />
        <link href="${pageContext.request.contextPath}/assets/css/styles2.css" rel="stylesheet" />
        <script src="https://use.fontawesome.com/releases/v6.3.0/js/all.js" crossorigin="anonymous"></script>
    </head>

    <body class="sb-nav-fixed">
        <%@include file="../layout/headerAdmin.jsp" %>
        <div id="layoutSidenav">
            <%@include file="../layout/sideNavOptionStaff.jsp" %>
            <div id="layoutSidenav_content">
                <main>
                    <div class="container-fluid px-4">
                        <h1 class="mt-4">Quản lí thẻ khuyến mãi</h1>


                        <div class="mb-3 " style="">
                            <form action="findvoucherbyvouchercode" method="post" >
                                <input 
                                    type="text" 
                                    name="voucherCode" 
                                    class="form-control shadow-sm" 
                                    placeholder="Nhập mã thẻ.." 
                                    style="max-width: 300px; border-radius: 8px;" 
                                    />
                                <button type="submit" class="btn btn-primary d-flex align-items-center px-3" style="border-radius: 8px;">
                                    <i class="fas fa-search me-1"></i> Tìm kiếm
                                </button>
                            </form>
                        </div>

                        <c:if test="${param.success == '4'}">
                            <div class="alert" style="color: red" role="alert">
                                Hãy nhập mã thẻ khuyến mãi để tìm kiếm!
                            </div>
                        </c:if>
                        
                        <div class="d-flex justify-content-between align-items-center mb-3 flex-wrap gap-2">
                            <form action="filtervoucherbystatus" method="post" >
                                <div class="d-flex align-items-center gap-2">
                                    <label class="form-label mb-0">Lọc theo trạng thái:</label>
                                    <select class="form-select" name="status" id="statusFilter" onchange="this.form.submit()" style="width: 180px;">
                                        <option value="2" ${param.status == '2' ? 'selected' : ''}>Tất cả</option>
                                        <option value="1" ${param.status == '1' ? 'selected' : ''}>Hoạt động</option>
                                        <option value="0" ${param.status == '0' ? 'selected' : ''}>Không hoạt động</option>
                                    </select>
                                </div>
                            </form>

                            <a href="addvoucher" class="btn btn-success d-flex align-items-center gap-1">
                                <i class="fas fa-user-plus"></i> Thêm thẻ khuyến mãi
                            </a>
                        </div>
                        <c:if test="${param.success == '1'}">
                            <div class="alert alert-success border border-success" role="alert">
                                Cập nhật voucher thành công!
                            </div>
                        </c:if>
                        <c:if test="${param.success == '2'}">
                            <div class="alert alert-success border border-success" role="alert">
                                Thêm voucher thành công!
                            </div>
                        </c:if>
                        <c:if test="${param.success == '3'}">
                            <div class="alert alert-success border border-success" role="alert">
                                Sửa trạng thái thành công!
                            </div>
                        </c:if>
                        <c:if test="${not empty listVoucher}">


                            <div class="col-xl-12 col-md-6">

                                <div class="card-body">

                                    <table style="border: 1px solid">
                                        <thead>
                                            <tr>
                                                <th>STT</th>
                                                <th>Mã</th>
                                                <th>Tên</th>
                                                <th>Khuyến mãi</th>
                                                <th>Ngày bắt đầu</th>
                                                <th>Ngày kết thúc</th>
                                                <th>Số lượng</th>
                                                <th>Trạng thái</th>
                                                <th>Hoạt Động</th>
                                            </tr>
                                        </thead>

                                        <c:forEach var="v" items="${requestScope.listVoucher}" varStatus="index">
                                            <tr>
                                                <td>${(currentPage - 1) * 10 + index.index + 1}</td>
                                                <td>${v.voucherCode}</td>
                                                <td>${v.voucherName}</td>                                      
                                                <td>${v.percentDiscount}%</td>
                                                <td>${v.startDate}</td>
                                                <td>${v.endDate}</td>
                                                <td>${v.quantity}</td>
                                                <td>${v.status == 1 ? "Hoạt động" : "Không hoạt động"}</td>
                                                <td >
                                                    <a href="updatevoucher?voucherId=${v.voucherId}" class="btn btn-primary btn-sm">
                                                        Cập Nhật <i class="bi bi-pencil-square"></i>
                                                    </a>

                                                    <a href="detailvoucher?voucherId=${v.voucherId}" class="btn btn-primary btn-sm">
                                                        Chi Tiết <i class="bi bi-pencil-square"></i>
                                                    </a>

                                                    <form action="changestatusvoucher" method="post" style="display:inline;"
                                                          onsubmit="return confirm('Bạn có chắc chắn muốn thay đổi trạng thái voucher này?')">
                                                        <input type="hidden" name="id" value="${v.voucherId}" />
                                                        <input type="hidden" name="status" value="${v.status}" />
                                                        <button type="submit" class="btn ${v.status == 1 ? 'btn-danger' : 'btn-success'} btn-sm">
                                                            ${v.status == 1 ? 'Ẩn' : 'Hiện'} 
                                                            <i class="bi ${v.status == 1 ? 'bi-toggle-on' : 'bi-toggle-off'}"></i>
                                                        </button>
                                                    </form>

                                                </td>
                                            </tr>
                                        </c:forEach>

                                    </table>
                                    <div class="pagination">
                                        <c:forEach begin="1" end="${totalPages}" var="i">
                                            <a href="listvoucher?page=${i}" class="${i == currentPage ? 'active' : ''}">${i}</a>
                                        </c:forEach>
                                    </div>  
                                </div>
                            </div>
                        </c:if>
                        <c:if test="${ empty listVoucher}">
                            <div class="alert" style="color: red">${error}</div>
                        </c:if>
                    </div>
                </main>
                <%@include file="../layout/footerStaff.jsp" %>
            </div>
        </div>
        <script src="https://cdn.jsdelivr.net/npm/bootstrap@5.2.3/dist/js/bootstrap.bundle.min.js" crossorigin="anonymous"></script>
        <script src="./assets/js/scripts.js"></script>
        <script src="https://cdnjs.cloudflare.com/ajax/libs/Chart.js/2.8.0/Chart.min.js" crossorigin="anonymous"></script>
        <script src="./assets/demo/chart-area-demo.js"></script>
        <script src="./assets/demo/chart-bar-demo.js"></script>
        <script src="https://cdn.jsdelivr.net/npm/simple-datatables@7.1.2/dist/umd/simple-datatables.min.js" crossorigin="anonymous"></script>
        <script src="./assets/js/datatables-simple-demo.js"></script>
    </body>

</html>
