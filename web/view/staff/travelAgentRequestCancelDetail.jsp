<%--
 * Copyright (C) 2025, Group 6.
 * ProjectCode/Short Name of Application: TravelSystemService
 * Support Management and Provide Travel Service System
 *
 * Record of change:
 * DATE        Version    AUTHOR            DESCRIPTION
 * 2025-07-03  1.0        Huu Hung         First implementation
--%>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<!DOCTYPE html>
<html>
    <head>
        <meta charset="UTF-8">
        <title>Chi tiết yêu cầu huỷ chuyến đi</title>
        <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.0/dist/css/bootstrap.min.css" rel="stylesheet" />
        <link href="./assets/css/styles2.css" rel="stylesheet" />
        <link rel="stylesheet" href="https://cdnjs.cloudflare.com/ajax/libs/font-awesome/6.4.0/css/all.min.css"/>
        <style>
            .img-preview {
                max-width: 100%;
                border: 1px solid #ccc;
                border-radius: 8px;
                cursor: pointer;
            }
            .btn-status {
                min-width: 120px;
            }
        </style>
    </head>
    <body>
        <%@include file="../layout/headerAdmin.jsp" %>
        <div id="layoutSidenav">
            <%@include file="../layout/sideNavOptionStaff.jsp" %>
            <div id="layoutSidenav_content">
                <main class="container mt-4 mb-5">
                    <div class="d-flex align-items-center mb-4">
                        <a href="travelagentmanagerequestcancel" class="btn btn-secondary me-3">
                            <i class="fa fa-arrow-left"></i> Quay lại
                        </a>


                    </div>
                    <h3 class="mb-0">Chi tiết yêu cầu huỷ chuyến đi</h3>
                    <br>
                    <!-- Thông tin người dùng & yêu cầu -->
                    <div class="mb-2"><c:if test="${param.cancelSuccess == 'true'}">
                            <script>
                                Swal.fire({
                                    icon: 'success',
                                    title: 'Huỷ tour thành công!',
                                    text: 'Yêu cầu của bạn đã được gửi đến hệ thống.',
                                    showConfirmButton: false,
                                    timer: 3000
                                });
                            </script>
                        </c:if>

                        <c:if test="${param.cancelSuccess == 'false'}">
                            <script>
                                Swal.fire({
                                    icon: 'error',
                                    title: 'Huỷ tour thất bại!',
                                    text: 'Có lỗi xảy ra. Vui lòng thử lại.',
                                    showConfirmButton: false,
                                    timer: 3000
                                });
                            </script>
                        </c:if>
                        <strong>Email đại lý huỷ:</strong> ${agent.travelAgentGmail}
                    </div>
                    <div class="mb-2">
                        <strong>Mã đặt tour:</strong> ${dto.bookDetail.bookCode}
                    </div>
                    <div class="mb-2">
                        <strong>Tên tour:</strong> ${dto.tour.tourName}
                    </div>
                    <div class="mb-2">
                        <strong>Ngày khởi hành:</strong> <fmt:formatDate value="${dto.tour.startDay}" pattern="dd/MM/yyyy" />
                    </div>
                    <div class="mb-2">
                        <strong>Giá:</strong> <fmt:formatNumber value="${dto.bookDetail.totalPrice}" type="number" /> VNĐ
                    </div>
                    <div class="mb-2">
                        <strong>Giá cần hoàn trả:</strong>
                        <fmt:formatNumber value="${dto.bookDetail.totalPrice}" type="number" /> VNĐ
                    </div>
                    <div class="mb-2">
                        <strong>Trạng thái yêu cầu:</strong>
                        <c:choose>
                            <c:when test="${dto.requestCancel.status ==1}">
                                <span class="badge bg-warning text-dark">Chờ xử lý</span>
                            </c:when>
                            <c:when test="${dto.requestCancel.status ==2}">
                                <span class="badge bg-success">Đã duyệt</span>
                            </c:when>
                            <c:when test="${dto.requestCancel.status ==3}">
                                <span class="badge bg-danger">Đã từ chối</span>
                            </c:when>
                        </c:choose>
                    </div>
                    <div class="mb-2">
                        <strong>Lý do huỷ:</strong> ${dto.requestCancel.reason}
                    </div>
                    <c:if test="${not empty errorMessage}">
    <div class="alert alert-danger">${errorMessage}</div>
</c:if>


                    <!-- Hành động -->
                    <form action="travelagentrequestcanceldetail" method="post" class="d-flex gap-2">
                        <input type="hidden" name="id" value="${dto.requestCancel.requestCancelID}" />
                        <c:if test="${dto.requestCancel.status == 1}">
                            <div class="d-flex gap-2">
                                <!-- Nút duyệt hiện modal nhập số tiền -->
                                <button type="button" class="btn btn-success btn-status" data-bs-toggle="modal" data-bs-target="#approveModal">
                                    <i class="fa fa-check"></i> Duyệt & Hoàn tiền
                                </button>

                                <!-- Nút từ chối hiện modal nhập lý do -->
                                <button type="button" class="btn btn-danger btn-status" data-bs-toggle="modal" data-bs-target="#rejectModal">
                                    <i class="fa fa-times"></i> Từ chối
                                </button>
                            </div>
                        </c:if>

                    </form>
                </main>


                <!-- Modal duyệt -->
                <div class="modal fade" id="approveModal" tabindex="-1" aria-hidden="true">
                    <div class="modal-dialog">
                        <form action="travelagentrequestcanceldetail" method="post" class="modal-content">
                            <input type="hidden" name="action" value="approve">
                            <input type="hidden" name="requestCancelID" value="${dto.requestCancel.requestCancelID}">
                            <input type="hidden" name="userID" value="${dto.requestCancel.userID}">
                            <input type="hidden" name="refundAmount" value="${dto.bookDetail.totalPrice}">
                            <div class="modal-body">
                                <p><strong>Số tiền hoàn:</strong><fmt:formatNumber value="${dto.bookDetail.totalPrice}" type="number" /> VNĐ</p>
                                <p>Xác nhận hoàn tiền vào ví của người dùng?</p>
                            </div>
                            <div class="modal-footer">
                                <button type="submit" class="btn btn-success">Xác nhận</button>
                                <button type="button" class="btn btn-secondary" data-bs-dismiss="modal">Hủy</button>
                            </div>
                        </form>
                    </div>
                </div>

                <!-- Modal nhập lý do từ chối -->
                <div class="modal fade" id="rejectModal" tabindex="-1" aria-hidden="true">
                    <div class="modal-dialog">
                        <form action="travelagentrequestcanceldetail" method="post" class="modal-content">
                            <input type="hidden" name="action" value="reject">
                            <input type="hidden" name="requestCancelID" value="${dto.requestCancel.requestCancelID}">

                            <div class="modal-body">
                                <label for="rejectReason" class="form-label">Nhập lý do từ chối:</label>
                                <textarea class="form-control" name="rejectReason" id="rejectReason" required rows="3"></textarea>
                            </div>

                            <div class="modal-footer">
                                <button type="submit" class="btn btn-danger">Xác nhận từ chối</button>
                                <button type="button" class="btn btn-secondary" data-bs-dismiss="modal">Huỷ</button>
                            </div>
                        </form>
                    </div>
                </div>

                <footer class="py-4 bg-light mt-auto">
                    <%@include file="../layout/footerStaff.jsp" %>
                </footer>
            </div>
        </div>
        <script src="https://cdn.jsdelivr.net/npm/bootstrap@5.3.0/dist/js/bootstrap.bundle.min.js"></script>
        <script src="https://cdn.jsdelivr.net/npm/sweetalert2@11"></script>
        <c:if test="${param.approve == 'true'}">
                        <script>
                            Swal.fire({
                                icon: 'success',
                                title: 'Chấp nhận thành công',
                                text: 'Yêu cầu của bạn đã được gửi đến hệ thống.',
                                showConfirmButton: false,
                                timer: 3000
                            });
                        </script>
                    </c:if>

                    <c:if test="${param.approve == 'false'}">
                        <script>
                            Swal.fire({
                                icon: 'error',
                                title: 'Duyệt thất bại',
                                text: 'Có lỗi xảy ra. Vui lòng thử lại.',
                                showConfirmButton: false,
                                timer: 3000
                            });
                        </script>
                    </c:if>
                    <c:if test="${param.reject == 'true'}">
                        <script>
                            Swal.fire({
                                icon: 'success',
                                title: 'Chấp nhận thành công',
                                text: 'Yêu cầu của bạn đã được gửi đến hệ thống.',
                                showConfirmButton: false,
                                timer: 3000
                            });
                        </script>
                    </c:if>

                    <c:if test="${param.reject == 'false'}">
                        <script>
                            Swal.fire({
                                icon: 'error',
                                title: 'Duyệt thất bại',
                                text: 'Có lỗi xảy ra. Vui lòng thử lại.',
                                showConfirmButton: false,
                                timer: 3000
                            });
                        </script>
                    </c:if>
    </body>
</html>
