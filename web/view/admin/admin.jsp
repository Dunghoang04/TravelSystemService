<%--
* Copyright (C) 2025, Group 6.
 * ProjectCode/Short Name of Application: TravelAgentService 
 * Support Management and Provide Travel Service System 
 *
 * Record of change:
 * DATE        Version    AUTHOR            DESCRIPTION
 * 2025-06-24  1.0        Hà Thị Duyên      First implementationF
--%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html lang="en">
    <head>
        <meta charset="utf-8" />
        <meta http-equiv="X-UA-Compatible" content="IE=edge" />
        <meta name="viewport" content="width=device-width, initial-scale=1, shrink-to-fit=no" />
        <meta name="description" content="Admin Dashboard for TravelSystemService" />
        <meta name="author" content="Group 6" />
        <title>Dashboard - Admin</title>
        <link href="https://cdn.jsdelivr.net/npm/simple-datatables@7.1.2/dist/style.min.css" rel="stylesheet" />
        <link href="${pageContext.request.contextPath}/assets/css/styles2.css" rel="stylesheet" />
        <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.0/dist/css/bootstrap.min.css" rel="stylesheet" />
        <script src="https://use.fontawesome.com/releases/v6.3.0/js/all.js" crossorigin="anonymous"></script>
        <style>
            .card {
                transition: transform 0.2s;
            }
            .card:hover {
                transform: translateY(-5px);
            }
            .chart-container {
                max-width: 600px;
                margin: auto;
            }
            .content{
                margin-left:10%;

            }
        </style>
    </head>
    <body class="sb-nav-fixed">
        <%@include file="../layout/headerAdmin.jsp" %>
        <div id="layoutSidenav">
            <%@include file="../layout/sideNavOptionAdmin.jsp" %>
            <div id="layoutSidenav_content">
                <main class="content">
                    <div class="container-fluid px-4">
                        <h1 class="mt-4">Bảng Điều Khiển Quản Trị</h1>
                        <div class="row mt-4">
                            <!-- Total Accounts -->
                            <div class="col-xl-4 col-md-6 mb-4">
                                <div class="card border-left-primary shadow h-100 py-2">
                                    <div class="card-body">
                                        <div class="row no-gutters align-items-center">
                                            <div class="col mr-2">
                                                <div class="text-xs font-weight-bold text-primary text-uppercase mb-1">Tổng Số Tài Khoản</div>
                                                <div class="h5 mb-0 font-weight-bold text-gray-800">  
                                                    <c:out value="${totalUser != null ? totalUser : '0'}" />
                                                </div>
                                            </div>
                                            <div class="col-auto">
                                                <i class="fas fa-users fa-2x text-gray-300"></i>
                                            </div>
                                        </div>
                                    </div>
                                </div>
                            </div>
                            <!-- Total Travel Agents -->
                            <div class="col-xl-4 col-md-6 mb-4">
                                <div class="card border-left-success shadow h-100 py-2">
                                    <div class="card-body">
                                        <div class="row no-gutters align-items-center">
                                            <div class="col mr-2">
                                                <div class="text-xs font-weight-bold text-success text-uppercase mb-1">Số Đại Lý Du Lịch</div>
                                                <div class="h5 mb-0 font-weight-bold text-gray-800">
                                                    <c:out value="${totalTravelAgents != null ? totalTravelAgents : '0'}" />
                                                </div>
                                            </div>
                                            <div class="col-auto">
                                                <i class="fas fa-briefcase fa-2x text-gray-300"></i>
                                            </div>
                                        </div>
                                    </div>
                                </div>
                            </div>
                            <!-- Total Staff -->
                            <div class="col-xl-4 col-md-6 mb-4">
                                <div class="card border-left-info shadow h-100 py-2">
                                    <div class="card-body">
                                        <div class="row no-gutters align-items-center">
                                            <div class="col mr-2">
                                                <div class="text-xs font-weight-bold text-info text-uppercase mb-1">Số Nhân Viên</div>
                                                <div class="h5 mb-0 font-weight-bold text-gray-800">
                                                    <c:out value="${totalStaff != null ? totalStaff : '0'}" />
                                                </div>
                                            </div>
                                            <div class="col-auto">
                                                <i class="fas fa-user-tie fa-2x text-gray-300"></i>
                                            </div>
                                        </div>
                                    </div>
                                </div>
                            </div>
                            <!-- Total Tourists -->
                            <div class="col-xl-4 col-md-6 mb-4">
                                <div class="card border-left-warning shadow h-100 py-2">
                                    <div class="card-body">
                                        <div class="row no-gutters align-items-center">
                                            <div class="col mr-2">
                                                <div class="text-xs font-weight-bold text-warning text-uppercase mb-1">Số Khách Du Lịch</div>
                                                <div class="h5 mb-0 font-weight-bold text-gray-800">
                                                    <c:out value="${totalTourists != null ? totalTourists : '0'}" />
                                                </div>
                                            </div>
                                            <div class="col-auto">
                                                <i class="fas fa-globe fa-2x text-gray-300"></i>
                                            </div>
                                        </div>
                                    </div>
                                </div>
                            </div>
                            <!-- Total Revenue -->
                            <div class="col-xl-4 col-md-6 mb-4">
                                <div class="card border-left-danger shadow h-100 py-2">
                                    <div class="card-body">
                                        <div class="row no-gutters align-items-center">
                                            <div class="col mr-2">
                                                <div class="text-xs font-weight-bold text-danger text-uppercase mb-1">Tổng Doanh Thu</div>
                                                <div class="h5 mb-0 font-weight-bold text-gray-800">
                                                    <c:out value="${totalRevenue != null ? totalRevenue : ''}" />
                                                </div>
                                            </div>
                                            <div class="col-auto">
                                                <i class="fas fa-dollar-sign fa-2x text-gray-300"></i>
                                            </div>
                                        </div>
                                    </div>
                                </div>
                            </div>
                            <!-- Total Tours -->
                            <div class="col-xl-4 col-md-6 mb-4">
                                <div class="card border-left-secondary shadow h-100 py-2">
                                    <div class="card-body">
                                        <div class="row no-gutters align-items-center">
                                            <div class="col mr-2">
                                                <div class="text-xs font-weight-bold text-secondary text-uppercase mb-1">Tổng Số Tour</div>
                                                <div class="h5 mb-0 font-weight-bold text-gray-800">
                                                    <c:out value="${totalTours != null ? totalTours : ''}" />
                                                </div>
                                            </div>
                                            <div class="col-auto">
                                                <i class="fas fa-map fa-2x text-gray-300"></i>
                                            </div>
                                        </div>
                                    </div>
                                </div>
                            </div>
                        </div>
                        <!-- Chart -->
                        <div class="card mb-4">
                            <div class="card-header">
                                <i class="fas fa-chart-pie me-1"></i>
                                Phân Bố Tài Khoản
                            </div>
                            <div class="card-body">
                                <div class="chart-container">
                                    <canvas id="accountDistributionChart"></canvas>
                                </div>
                            </div>
                        </div>
                    </div>
                </main>
                <footer class="bg-light py-4 mt-auto">
                    <div class="container-fluid px-4">
                        <div class="text-muted text-center">Copyright © TravelSystemService 2025</div>
                    </div>
                </footer>
            </div>
        </div>
        <script src="https://cdn.jsdelivr.net/npm/bootstrap@5.3.0/dist/js/bootstrap.bundle.min.js" crossorigin="anonymous"></script>
        <script src="https://cdn.jsdelivr.net/npm/simple-datatables@7.1.2/dist/umd/simple-datatables.min.js" crossorigin="anonymous"></script>
        <script src="${pageContext.request.contextPath}/assets/js/scripts.js"></script>
        <script src="https://cdnjs.cloudflare.com/ajax/libs/Chart.js/2.8.0/Chart.min.js" crossorigin="anonymous"></script>
        <script>
            // Chart.js for Account Distribution
            var ctx = document.getElementById('accountDistributionChart').getContext('2d');
            var accountChart = new Chart(ctx, {
                type: 'pie',
                data: {
                    labels: ['Đại Lý Du Lịch', 'Nhân Viên', 'Khách Du Lịch'],
                    datasets: [{
                            data: [
            ${totalTravelAgents != null ? totalTravelAgents : 0},
            ${totalStaff != null ? totalStaff : 0},
            ${totalTourists != null ? totalTourists : 0}
                            ],
                            backgroundColor: ['#28a745', '#17a2b8', '#ffc107'],
                            borderColor: ['#ffffff', '#ffffff', '#ffffff'],
                            borderWidth: 1
                        }]
                },
                options: {
                    responsive: true,
                    plugins: {
                        legend: {position: 'top'},
                        title: {display: true, text: 'Phân Bố Tài Khoản Theo Loại'}
                    }
                }
            });
        </script>
    </body>
</html>