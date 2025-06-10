<%-- 
 * Copyright (C) 2025, Group 6.
 * ProjectCode/Short Name of Application: TravelAgentService 
 * Support Management and Provide Travel Service System 
 *
 * Record of change:
 * DATE        Version    AUTHOR            DESCRIPTION
 *  2025-06-07  1.0       NguyenVanVang     First implementation
--%>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<!DOCTYPE html>
<html>
    <head>
        <meta charset="utf-8" />
        <title>Cập Nhật Phòng</title>
        <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.2.3/dist/css/bootstrap.min.css" rel="stylesheet" />
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
            .form-group {
                margin-bottom: 15px;
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
            .content {
                padding: 20px;
                width: 100%;
                flex: 1;
                overflow-x: auto;
            }
        </style>
    </head>
    <body>
        <div id="layoutSidenav">
            <div id="layoutSidenav_content">
                <main>
                    <div class="container mt-5">
                        <h4 class="mb-4">Cập Nhật Thông Tin Phòng</h4>

                        <!-- Hiển thị lỗi nếu có -->
                        <c:if test="${not empty error}">
                            <div class="alert alert-danger">${error}</div>
                        </c:if>

                        <!-- Form cập nhật phòng -->
                        <form action="updateroom" method="POST">

                            <div class="col-12 form-group">
                                <label for="roomID"><b>Mã phòng</b></label>
                                <input type="text" class="form-control" id="roomID" name="roomID"
                                       value="${room.roomID}" readonly required>
                            </div>

                            <div class="col-12 form-group">
                                <label for="roomTypes"><b>Loại phòng</b></label>
                                <select name="roomTypes" class="form-control" id="roomTypes" required>
                                    <option value="">-- Chọn loại phòng --</option>
                                    <option value="Phòng đơn" ${room.roomTypes == 'Phòng đơn' ? 'selected' : ''}>Phòng đơn</option>
                                    <option value="Phòng đôi" ${room.roomTypes == 'Phòng đôi' ? 'selected' : ''}>Phòng đôi</option>
                                    <option value="Phòng gia đình" ${room.roomTypes == 'Phòng gia đình' ? 'selected' : ''}>Phòng gia đình</option>
                                    <option value="Phòng tập thể" ${room.roomTypes == 'Phòng tập thể' ? 'selected' : ''}>Phòng tập thể</option>
                                    <option value="Phòng VIP" ${room.roomTypes == 'Phòng VIP' ? 'selected' : ''}>Phòng VIP</option>
                                </select>
                            </div>

                            <div class="col-12 form-group">
                                <label for="numberOfRooms"><b>Số lượng phòng</b></label>
                                <input type="number" name="numberOfRooms" class="form-control" id="numberOfRooms"
                                       value="${room.numberOfRooms}" required min="1" />
                            </div>

                            <div class="col-12 form-group">
                                <label for="priceOfRoom"><b>Giá phòng (VNĐ)</b></label>
                                <input type="number" name="priceOfRoom" class="form-control" id="priceOfRoom"
                                       value="${room.priceOfRoom}" required min="0" step="1000" />
                            </div>

                            <div class="d-flex justify-content-end mt-3">
                                <button type="submit" class="btn btn-success me-2">Cập nhật phòng</button>
                                <button type="button" class="btn btn-danger cancelBtn">Huỷ bỏ</button>
                            </div>
                        </form>
                    </div>
                </main>
            </div>
        </div>

        <script>
            document.querySelector(".cancelBtn").addEventListener("click", function () {
                window.history.back();
            });
        </script>

        <script src="https://cdn.jsdelivr.net/npm/bootstrap@5.2.3/dist/js/bootstrap.bundle.min.js"></script>
    </body>
</html>
