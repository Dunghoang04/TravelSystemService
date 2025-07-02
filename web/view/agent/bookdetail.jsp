<%--
* Copyright (C) 2025, Group 6.
* ProjectCode/Short Name of Application: TravelAgentService 
* Support Management and Provide Travel Service System 
*
* Record of change:
* DATE        Version    AUTHOR            DESCRIPTION
* 2025-06-25  1.1  Nguyễn Vũ Quỳnh Mai      Redesigned UI for better aesthetics
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
        <%@ include file="../layout/headerAdmin.jsp" %>
        <div id="layoutSidenav">
            <%@ include file="../layout/sideNavOptionAgent.jsp" %>
            <div id="layoutSidenav_content">
                <main>
                    <div class="container-fluid px-4">
                        <h1 class="mt-4">Quản lí đặt chuyến đi</h1>

                       
                        
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

                        </div>
                        
                       
                        
                        <c:if test="${not empty listBook}">


                            <div class="col-xl-12 col-md-6">

                                <div class="card-body">

                                    <table style="border: 1px solid">
                                        <thead>
                                            <tr>
                                                
                                                <th>Mã đơn đặt</th>
                                                <th>Tên chuyến đi</th>
                                                <th>Tên khách hàng</th>
                                                <th>SĐT</th>
                                                <th>Email</th>
                                                <th>Ngày đặt</th>
                                                <th>Số lượng người</th>
                                                
                                                <th>Hoạt Động</th>
                                            </tr>
                                        </thead>

                                        <c:forEach var="v" items="${requestScope.listBook}" varStatus="index">
                                            <tr>
                                              
                                                <td>${v.bookID}</td>
                                                <td>${v.tourName}</td>                                      
                                                <td>${v.fullName}%</td>
                                                <td>${v.phone}%</td>
                                                <td>${v.gmail}%</td>
                                                <td>${v.bookDate}</td>
                                                <td>${v.numberAdult} + ${v.numberChildren}</td>
                                                <td>${v.quantity}</td>
                                                
                                                <td >
                                                    <a href="updatevoucher?voucherId=${v.voucherId}" class="btn btn-primary btn-sm">
                                                        Xác nhận đã thanh toán <i class="bi bi-pencil-square"></i>
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
                        <c:if test="${ empty listBook}">
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
