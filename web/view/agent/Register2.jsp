<%-- 
    Document   : register
    Created on : May 23, 2025, 9:07:47 PM
    Author     : Nhat Anh
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


        <style>
            body {
                background: url('${pageContext.request.contextPath}/assets/img/re3.jpg');
                background-size: cover;       /* ảnh nền sẽ bao phủ toàn bộ khung hình */
                background-repeat: no-repeat; /* không lặp lại ảnh nền */
                background-position: center;  /* căn giữa ảnh nền */
                display: flex;
                justify-content: center;
                align-items: center;
                height: 140vh;
                margin: 0;
            }

            .register-container {
                background: white;
                display: flex;
                align-items: center;
                padding: 30px;
                border-radius: 12px;
                box-shadow: 0 4px 20px rgba(0, 0, 0, 0.15);
                width: 60%;
                text-align: center;
            }

            h2 {
                color: #28A745;
                font-size: 28px;
                margin-bottom: 20px;
            }

            .form-group {
                margin-bottom: 15px;
                text-align: left;
            }

            label {
                display: block;
                font-size: 14px;
                color: black;
                font-weight: bold;
                margin-bottom: 5px;
            }

            .input {
                width: 100%;
                padding: 10px;
                border: 1px solid #d6c9c9;
                border-radius: 6px;
                box-sizing: border-box;
                font-size: 16px;
                background-color: #f9f8f7;
                transition: all 0.3s ease-in-out;
            }

            input:focus {
                border-color: #b89f9a;
                background-color: #fff;
                outline: none;
            }

            button {
                width: 100%;
                padding: 12px;
                background: #28A745;
                border: none;
                border-radius: 6px;
                color: white;
                font-size: 18px;
                font-weight: bold;
                cursor: pointer;
                transition: 0.3s;
            }

            button:hover {
                background: #28A745;
            }

            p {
                text-align: center;
                margin-top: 15px;
                font-size: 14px;
                color: black;
            }

            a {
                color: #00539B;
                text-decoration: none;
                font-weight: bold;
            }

            a:hover {
                text-decoration: underline;
            }

            .error {
                color: red;
                font-size: 14px;
                margin-top: 5px;
                text-align: center;
            }


            .success {
                color: green;
                font-size: 14px;
                text-align: center;
                margin-top: 5px;
            }

            .image-section img{
                width: 80%;
            }



        </style>
    </head>
    <body>
        <div class="register-container">
            <div class="image-section">
                <h2>Đăng kí làm đại lý du lịch</h2>
                <br><!-- comment -->
                <br><!-- comment -->

                <img src="${pageContext.request.contextPath}/assets/img/doitac.png" alt="Đối tác">
            </div>
            <div >

                <c:if test="${not empty errorMessage}">
                    <div class="error">${errorMessage}</div>
                </c:if>
                <c:if test="${not empty successMessage}">
                    <div class="success">${successMessage}</div>
                </c:if>
                <form id="registerForm" action="${pageContext.request.contextPath}/RegisterTravelAgentServlet" method="POST" onsubmit="return validateForm(event)">
                    <input type="hidden" name="service" value="addAgent">

                    <table >
                        <thead>
                        <h4>Thông tin đại lý du lịch</h4>
                        </thead>
                        <tbody>
                            <tr >
                                <td colspan="2"><div class="form-group">
                                        <label for="travelAgentName">Tên công ty:</label>
                                        <input class="input" type="text" id="travelAgentName" name="travelAgentName" value="${param.travelAgentName}" placeholder=""  required>
                                    </div></td>


                            </tr>
                            <tr>
                                <td><div class="form-group">
                                        <label for="travelAgentEmail">Email:</label>
                                        <input class="input" type="email" id="travelAgentEmail" name="travelAgentEmail" value="${param.travelAgentEmail}" placeholder=""  required>
                                    </div></td>
                                <td><div class="form-group">
                                        <label for="hotLine">Số HotLine:</label>
                                        <input class="input" type="tel" id="hotLine" name="hotLine" value="${param.hotLine}" pattern="[0-9]{10}"  required>
                                    </div></td>
                            </tr>                    
                            <tr >
                                <td colspan="2">
                                    <div class="form-group">
                                        <label for="address">Địa chỉ:</label>
                                        <input class="input" type="text" id="address" name="address" value="${param.address}"  required>
                                    </div>
                                </td>
                            </tr>
                            <tr>
                                <td><div class="form-group">
                                        <label for="establishmentDate">Ngày thành lập:</label>
                                        <input class="input" type="date" id="establishmentDate" name="establishmentDate" value="${param.establishmentDate}" required>
                                    </div></td>
                                <td><div class="form-group">
                                        <label for="taxCode">Mã số thuế:</label>
                                        <input class="input" type="text" id="taxCode" name="taxCode" placeholder="" value="${param.taxCode}" required>
                                    </div></td>

                            </tr>
                        </tbody>
                    </table>

                    <table>
                        <thead>
                        <h4>Người đại diện</h4>
                        </thead>
                        <tbody>
                            <tr>
                                <td><div class="form-group">
                                        <label for="representativeFirstName">Họ:</label>
                                        <input class="input" type="text" id="representativeFirstName" name="representativeFirstName" value="${param.representativeFirstName}" placeholder=""  required>
                                    </div></td>
                                <td><div class="form-group">
                                        <label for="representativeLastName">Tên:</label>
                                        <input class="input" type="text" id="representativeLastName" name="representativeLastName" value="${param.representativeLastName}" placeholder=""  required>
                                    </div></td>
                            </tr>
                            <tr><td><div class="form-group">
                                        <label for="email">Email:</label>
                                        <input class="input" type="email" id="email" name="email" placeholder=""  value="${sessionScope.email != null ? sessionScope.email : ''}" readonly>
                                    </div>
                                    </div></td>
                                <td><div class="form-group">
                                        <label for="representativePhone">Số điện thoại:</label>
                                        <input class="input" type="tel" id="representativePhone" name="representativePhone" value="${param.representativePhone}" pattern="[0-9]{10}"  required>
                                    </div></td>
                            </tr>
                            <tr >
                                <td colspan="2">
                                    <div class="form-group">
                                        <label for="representativeAddress">Địa chỉ:</label>
                                        <input class="input" type="text" id="representativeAddress" name="representativeAddress" value="${param.representativeAddress}" required>
                                    </div>
                                </td>
                            </tr>
                            <tr>
                                <td><div class="form-group">
                                        <label for="dob">Ngày sinh:</label>
                                        <input class="input" type="date" id="dob" name="dob" value="${param.dob}" required>
                                    </div></td>
                                    <td ><div class="form-group" >
                                        <label>Giới tính:</label>
                                        <input type="radio" id="male" name="gender" value="Nam" ${param.gender eq 'Nam' || param.gender == null ? 'checked' : ''} >Nam
                                        <input type="radio" id="female" name="gender" value="Nữ" ${param.gender eq 'Nữ' ? 'checked':''}>Nữ
                                    </div>
                                </td>
                            </tr>

                            <tr>

                                <td ><div class="form-group">
                                        <label for="representativeIDCard">Số căn cước công dân:</label>
                                        <input class="input" type="idCard" id="representativeIDCard" name="representativeIDCard" value="${param.representativeIDCard}" required></div>
                                </td>
                                <td><div class="form-group">
                                        <label for="dateOfIssue">Ngày cấp:</label>
                                        <input class="input" type="date" id="dateOfIssue" name="dateOfIssue" value="${param.dateOfIssue}" required>
                                    </div></td>
                            </tr>

                            <tr >                        
                                <td colspan="2">
                                    <input type="checkbox" name="terms" required> 
                                    Tôi đồng ý tất cả các điều khoản trong 
                                    <a href="${pageContext.request.contextPath}/view/common/term.jsp">điều khoản dịch vụ</a>

                                </td>
                            </tr>
                            <tr>
                                <td></td>
                                <td>
                                    <button type="submit">Đăng ký</button>
                                </td>

                            </tr>
                        </tbody>
                    </table>


                </form>

            </div>
        </div>
    </body>

   
</html>
