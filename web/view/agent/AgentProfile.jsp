<%-- 
    Document   : AgentProfile
    Created on : May 30, 2025, 8:46:46 AM
    Author     : Nhat Anh
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<!DOCTYPE html>
<html>
    <head>
        <meta charset="utf-8" />
        <meta http-equiv="X-UA-Compatible" content="IE=edge" />
        <meta name="viewport" content="width=device-width, initial-scale=1, shrink-to-fit=no" />
        <meta name="description" content="" />
        <meta name="author" content="" />
        <title>Agent Profile</title>
        <link href="https://cdn.jsdelivr.net/npm/simple-datatables@7.1.2/dist/style.min.css" rel="stylesheet" />
        <link href="./assets/css/styles2.css" rel="stylesheet" />
        <script src="https://use.fontawesome.com/releases/v6.3.0/js/all.js" crossorigin="anonymous"></script>



    </head>
    <style>

        html, body {
            height: 100%;
            margin: 0;
            padding: 0;
            background-color: #f4f6f8;
            color: #333;
            overflow-x: hidden;
        }




        .sidebar {
            position: fixed;
            top: 0;
            left: 0;
            width: 250px;
            height: 100vh;
            background-color: #f8f9fa;
            overflow-y: auto;
        }

        .content {
            margin-left: 250px;
            padding: 20px;
            flex: 1;
        }


        h2, h4 {
            color: #28A745;
            margin-bottom: 15px;
        }

        label {
            font-weight: bold;
            font-size: 14px;
            margin-bottom: 5px;
            display: block;
            color: #212529;
        }

        p {
            margin-top: 15px;
            font-size: 14px;
        }


        form {
            display: flex;
            flex-wrap: wrap;
            gap: 20px;
            justify-content: center;
        }

        .form-group {
            margin-bottom: 15px;
            width: 100%;
        }

        .input, select {
            width: 100%;
            padding: 10px;
            font-size: 14px;
            border-radius: 6px;
            border: 1px solid #ccc;
            transition: 0.3s ease;
            box-sizing: border-box;
        }



        input:focus, select:focus {
            border-color: #28A745;
            outline: none;
            background-color: #fff;
        }




        .button {
            width: 70%;
            padding: 12px;
            background: #28A745;
            border: none;
            border-radius: 6px;
            color: white;
            font-size: 18px;
            font-weight: bold;
            cursor: pointer;
            transition: 0.3s;
            margin-bottom: 20px;
        }

        button:hover {
            background: #28A745;
        }



        table {
            width: 70%;
            margin-bottom: 25px;
            background-color: white;
            border-radius: 15px;
            overflow: hidden;
            box-shadow: 0 0 5px rgba(0,0,0,0.05);
        }

        table th, table td {
            padding: 10px 30px;
            font-size: 14px;
            text-align: left;
        }

        table th {
            background-color: #E8E4E3;
        }


        .error, .success {
            text-align: center;
            font-size: 14px;
            margin-top: 10px;
        }

        .error {
            color: #842029;
        }

        .success {
            color: #0f5132;
        }






    </style>
    <body>
        <%@include file="../layout/HeaderAdmin.jsp" %>
        <div id="layoutSidenav">
            <jsp:include page="../layout/SideNavOptionAgent.jsp"></jsp:include>  
                <div id="layoutSidenav_content">
                    <main>                        
                        <div class="container-fluid px-4">

                            <form id="registerForm" action="${pageContext.request.contextPath}/ManageTravelAgentProfile" method="POST" >
                            <input type="hidden" name="service" value="updateProfile">

                            <table style="margin-top: 30px">
                                <thead>
                                    <tr>
                                        <th colspan="2" style="text-align:center; font-size:18px; color:#28A745;">Thông tin đại lý du lịch</th>
                                    </tr>
                                </thead>
                                <tbody>
                                    <tr>
                                        <td colspan="2">
                                            <div class="form-group">
                                                <label for="travelAgentName">Tên công ty:</label>
                                                <input class="input" type="text" id="travelAgentName" name="travelAgentName" value="${sessionScope.agent.travelAgentName}" placeholder="" required readonly="">
                                            </div>
                                        </td>
                                    </tr>
                                    <tr>
                                        <td>
                                            <div class="form-group">
                                                <label for="travelAgentEmail">Email:</label>
                                                <input class="input" type="email" id="travelAgentEmail" name="travelAgentEmail" value="${sessionScope.agent.travelAgentGmail}" placeholder="" required readonly="">
                                            </div>
                                        </td>
                                        <td>
                                            <div class="form-group">
                                                <label for="hotLine">Số HotLine:</label>
                                                <input class="input" type="tel" id="hotLine" name="hotLine" value="${sessionScope.agent.hotLine}" pattern="[0-9]{10}" required readonly="">
                                            </div>
                                        </td>
                                    </tr>                    
                                    <tr>
                                        <td colspan="2">
                                            <div class="form-group">
                                                <label for="address">Địa chỉ:</label>
                                                <input class="input" type="text" id="address" name="address" value="${sessionScope.agent.travelAgentAddress}" required readonly="">
                                            </div>
                                        </td>
                                    </tr>
                                    <tr>
                                        <td>
                                            <div class="form-group">
                                                <label for="establishmentDate">Ngày thành lập:</label>
                                                <input class="input" type="date" id="establishmentDate" name="establishmentDate" value="${sessionScope.agent.establishmentDate}" required readonly="">
                                            </div>
                                        </td>
                                        <td>
                                            <div class="form-group">
                                                <label for="taxCode">Mã số thuế:</label>
                                                <input class="input" type="text" id="taxCode" name="taxCode" placeholder="" value="${sessionScope.agent.taxCode}" required readonly="">
                                            </div>
                                        </td>
                                    </tr>
                                </tbody>
                            </table>

                            <table>
                                <thead>
                                    <tr>
                                        <th colspan="2" style="text-align:center; font-size:18px; color:#28A745;">Người đại diện</th>
                                    </tr>
                                </thead>
                                <tbody>
                                    <tr>
                                        <td>
                                            <div class="form-group">
                                                <label for="representativeFirstName">Họ:</label>
                                                <input class="input" type="text" id="representativeFirstName" name="representativeFirstName" value="${sessionScope.agent.firstName}" placeholder="" required readonly="">
                                            </div>
                                        </td>
                                        <td>
                                            <div class="form-group">
                                                <label for="representativeLastName">Tên:</label>
                                                <input class="input" type="text" id="representativeLastName" name="representativeLastName" value="${sessionScope.agent.lastName}" placeholder="" required readonly="">
                                            </div>
                                        </td>
                                    </tr>
                                    <tr>
                                        <td>
                                            <div class="form-group">
                                                <label for="email">Email:</label>
                                                <input class="input" type="email" id="email" name="email" value="${sessionScope.agent.gmail != null ? sessionScope.agent.gmail : ''}" readonly="">
                                            </div>
                                        </td>
                                        <td>
                                            <div class="form-group">
                                                <label for="representativePhone">Số điện thoại:</label>
                                                <input class="input" type="tel" id="representativePhone" name="representativePhone" value="${sessionScope.agent.phone}" pattern="[0-9]{10}" required readonly="">
                                            </div>
                                        </td>
                                    </tr>
                                    <tr>
                                        <td colspan="2">
                                            <div class="form-group">
                                                <label for="representativeAddress">Địa chỉ:</label>
                                                <input class="input" type="text" id="representativeAddress" name="representativeAddress" value="${sessionScope.agent.address}" required readonly="">
                                            </div>
                                        </td>
                                    </tr>
                                    <tr>
                                        <td>
                                            <div class="form-group">
                                                <label for="dob">Ngày sinh:</label>
                                                <input class="input" type="date" id="dob" name="dob" value="${sessionScope.agent.dob}" required readonly="">
                                            </div>
                                        </td>
                                        <td>
                                            <div class="form-group">
                                                <label>Giới tính:</label>
                                                <div style="display: flex;justify-content: space-around">
                                                    <div><input type="radio" id="male" name="gender" value="Nam" disabled ${sessionScope.agent.gender eq 'Nam' || sessionScope.agent.gender == null ? 'checked' : ''}>Nam</div>
                                                    <div><input type="radio" id="female" name="gender" value="Nữ" disabled ${sessionScope.agent.gender eq 'Nữ' ? 'checked':''}>Nữ</div>
                                                </div>
                                            </div>
                                        </td>
                                    </tr>
                                    <tr>
                                        <td>
                                            <div class="form-group">
                                                <label for="representativeIDCard">Số căn cước công dân:</label>
                                                <input class="input" type="idCard" id="representativeIDCard" name="representativeIDCard" value="${sessionScope.agent.representativeIDCard}" required readonly="">
                                            </div>
                                        </td>
                                        <td>
                                            <div class="form-group">
                                                <label for="dateOfIssue">Ngày cấp:</label>
                                                <input class="input" type="date" id="dateOfIssue" name="dateOfIssue" value="${sessionScope.agent.dateOfIssue}" required readonly="">
                                            </div>
                                        </td>
                                    </tr>
                                </tbody>
                            </table>

                            <table>
                                <thead>
                                    <tr>
                                        <th colspan="2" style="text-align:center; font-size:18px; color:#28A745;">Thông tin tài khoản</th>
                                    </tr>
                                </thead>
                                <tbody>
                                    <tr>
                                        <td>
                                            <div class="form-group">
                                                <label for="email">Email:</label>
                                                <input class="input" type="email" id="email" name="email" placeholder="" value="${sessionScope.agent.gmail != null ? sessionScope.agent.gmail : ''}" readonly>
                                            </div>
                                        </td>
                                        <td>
                                            <div class="form-group">
                                                <label for="password">Mật khẩu</label>
                                                <input class="input" type="password" id="password" name="password" placeholder="" value="${sessionScope.agent.password != null ? sessionScope.agent.password : ''}" readonly>
                                            </div>
                                        </td>
                                    </tr>
                                </tbody>
                            </table>
                            <button class="button" type="submit">Cập nhật hồ sơ</button>

                        </form>

                    </div>
            </div>
        </main>

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
