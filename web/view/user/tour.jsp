<%-- 
    Document   : tour.jsp
    Created on : Jun 5, 2025, 9:48:22 AM
    Author     : Nhat Anh
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ page import="dao.TourDAO, dao.TourSessionDAO, java.util.Vector" %>
<jsp:useBean id="tourDAO" class="dao.TourDAO" scope="page"/>
<jsp:useBean id="sessionDAO" class="dao.TourSessionDAO" scope="page"/>
<!DOCTYPE html>
<html>
    <head>
        <meta charset="UTF-8">
        <meta name="viewport" content="width=device-width, initial-scale=1.0">
        <title>Danh Sách Tour Du Lịch</title>
        <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.0/dist/css/bootstrap.min.css" rel="stylesheet">
        <style>
            .tour-card {
                transition: transform 0.3s;
            }
            .tour-card:hover {
                transform: scale(1.05);
            }

            .price {
                font-weight: bold;
                color: #e74c3c;
            }
            .book-btn {
                background-color: #e74c3c;
                border: none;
            }
            .book-btn:hover {
                background-color: #c0392b;
            }
        </style>
    </head>
    <body>
        <!-- Header -->
        <%@include file="../layout/Header.jsp" %>

        <!-- Main Content -->
        <div class="container-fluid pt-5">
            <div class="row px-xl-5">


                <!-- Filter Sidebar -->
                <div class="col-lg-3 col-md-4">
                    <div class="filter-section mb-4">
                        <h5 class="filter-label">Tour</h5>
                        <!-- Category Filter -->
                        <form id="filterForm" action="ProductServlet" method="get">
                            <div class="mb-3">
                                <h6>Categories</h6>
                                <c:forEach var="category" items="${requestScope.categories}">
                                    <div class="form-check">
                                        <input class="form-check-input" type="checkbox" name="category" value="${category.categoryID}"
                                               <c:forEach var="selectedCat" items="${paramValues.category}">
                                                   <c:if test="${selectedCat == category.categoryID.toString()}">checked</c:if>
                                               </c:forEach>>
                                        <label class="form-check-label">${category.categoryName}</label>
                                    </div>
                                </c:forEach>
                            </div>
                            <!-- Brand Filter -->
                            <div class="mb-3">
                                <h6>Brands</h6>
                                <c:forEach var="brand" items="${requestScope.brands}">
                                    <div class="form-check">
                                        <input class="form-check-input" type="checkbox" name="brand" value="${brand}"
                                               <c:forEach var="selectedBrand" items="${paramValues.brand}">
                                                   <c:if test="${selectedBrand == brand}">checked</c:if>
                                               </c:forEach>>
                                        <label class="form-check-label">${brand}</label>
                                    </div>
                                </c:forEach>
                            </div>
                            <!-- Size Filter -->
                            <div class="mb-3">
                                <h6>Sizes</h6>
                                <c:forEach var="size" items="${requestScope.sizes}">
                                    <div class="form-check">
                                        <input class="form-check-input" type="checkbox" name="size" value="${size}"
                                               <c:forEach var="selectedSize" items="${paramValues.size}">
                                                   <c:if test="${selectedSize == size}">checked</c:if>
                                               </c:forEach>>
                                        <label class="form-check-label">${size}</label>
                                    </div>
                                </c:forEach>
                            </div>
                            <!-- Price Filter with Min and Max -->
                            <div class="mb-3">
                                <h6>Price Range</h6>
                                <div class="d-flex align-items-center">                           
                                    <input type="number" class="form-control price-input" name="minPrice" placeholder="Min" min="0" value="${param.minPrice}">
                                    <span class="mx-2">-</span>
                                    <input type="number" class="form-control price-input" name="maxPrice" placeholder="Max" min="0" value="${param.maxPrice}">
                                </div>
                            </div>
                            <button class="btn btn-primary mt-3 w-100" type="submit">
                                <i class="fas fa-search"></i> Search
                            </button>
                        </form>
                    </div>
                </div>


                <!-- Tour List -->
                <div class="col-lg-9 col-md-8">
                    <div class="row pb-3">
                        <div class="col-12 pb-1">
                            <div class="d-flex justify-content-between align-items-center mb-4">
                                <h2>
                                    <c:choose>
                                        <c:when test="${not empty param.query}">Search Results for "${param.query}"</c:when>
                                        <c:when test="${not empty requestScope.selectedBrand}">Products by ${requestScope.selectedBrand}</c:when>
                                        <c:when test="${not empty requestScope.selectedCategory}">
                                            <c:forEach var="cat" items="${requestScope.categories}">
                                                <c:if test="${cat.categoryID == requestScope.selectedCategory}">${cat.categoryName}</c:if>
                                            </c:forEach>
                                        </c:when>
                                        <c:when test="${not empty requestScope.selectedSize}">Products in Size "${requestScope.selectedSize}"</c:when>
                                        <c:otherwise>All Products</c:otherwise>
                                    </c:choose>
                                </h2>                            
                            </div>
                        </div>

                        <!-- Dynamic Product List from Database -->
                        <c:forEach var="product" items="${requestScope.products}">
                            <div class="col-lg-4 col-md-6 col-sm-12 pb-1">
                                <div class="card product-card border-0 mb-4">
                                    <div class="card-header product-img position-relative overflow-hidden bg-transparent border p-0">
                                        <img class="img-fluid w-100 product-img" style="height: 300px" src="${product.image}" alt="${product.productName}">
                                    </div>
                                    <div class="card-body border-left border-right text-center p-0 pt-4 pb-3">
                                        <h6 class="text-truncate mb-3" style="color: #9C8679">${product.productName}</h6>
                                        <p style="color: black">${product.supplierName} - ${product.size}</p>
                                        <div class="d-flex justify-content-center">
                                            <h6 style="color: red">$${product.price}</h6>
                                        </div>
                                    </div>
                                    <div class="card-footer d-flex justify-content-between bg-light border">
                                        <a href="ProductDetailServlet?pid=${product.productID}" class="btn btn-sm text-dark p-0">
                                            <i class="fas fa-eye text-primary mr-1"></i>View Detail
                                        </a>
                                        <c:choose>
                                            <c:when test="${sessionScope.name == null}">
                                                <a href="LoginLogoutServlet?service=loginUser" class="btn btn-sm text-dark p-0"><i class="fas fa-shopping-cart text-primary mr-1"></i>Add To Cart</a>
                                            </c:when>
                                            <c:otherwise>
                                                <c:if test="${product.quantityInStock == 0}">
                                                    Out of stock
                                                </c:if>
                                                <c:if test="${product.quantityInStock > 0}">
                                                    <a href="CartServlet?service=add&pID=${product.productID}" class="btn btn-sm text-dark p-0">
                                                        <i class="fas fa-shopping-cart text-primary mr-1"></i>Add To Cart
                                                    </a></c:if>
                                            </c:otherwise>                                        
                                        </c:choose>
                                    </div>
                                </div>
                            </div>
                        </c:forEach>

                        <!-- No Products Found -->
                        <c:if test="${empty requestScope.tours}">
                            <div class="col-12 text-center">
                                <p>Không có tour nào phù hợp với yêu cầu của bạn.</p>
                            </div>
                        </c:if>


                    </div>
                </div>
            </div>
        </div>

        <!-- Footer -->
        <%@include file="../layout/Footer.jsp" %>
        <div class="container my-5">
            <h1 class="text-center mb-4">Danh Sách Tour Du Lịch</h1>
            <div class="row">
                <c:set var="tours" value="${tourDAO.getAllTours()}"/>
                <c:set var="sessions" value="${sessionDAO.getAllTourSessions()}"/>
                <c:forEach var="tour" items="${tours}">
                    <c:set var="earliestSession" value="${null}"/>
                    <c:forEach var="session" items="${sessions}">
                        <c:if test="${session.tourId == tour.tourID}">
                            <c:if test="${earliestSession == null || session.startDay < earliestSession.startDay}">
                                <c:set var="earliestSession" value="${session}"/>
                            </c:if>
                        </c:if>
                    </c:forEach>
                    <c:if test="${earliestSession != null}">
                        <c:set var="originalPrice" value="${earliestSession.adultPrice * 1.1}"/>
                        <div class="col-md-4 mb-4">
                            <div class="card tour-card shadow-sm">
                                <img src="${not empty tour.image ? tour.image : 'https://via.placeholder.com/300x200'}" class="card-img-top" alt="${tour.tourName}">
                                <div class="card-body">
                                    <h5 class="card-title">${tour.tourName}</h5>
                                    <p class="card-text">
                                        <strong>Khởi hành:</strong> <fmt:formatDate value="${earliestSession.startDay}" pattern="dd/MM/yyyy"/><br>
                                        <strong>Thời gian:</strong> ${tour.numberOfDay}N${tour.numberOfDay - 1}Đ<br>
                                        <strong>Khởi hành từ:</strong> ${tour.startPlace}
                                    </p>
                                    <p>
                                        <span class="price-old">Giá từ: <fmt:formatNumber value="${originalPrice}" type="currency" currencySymbol="đ" maxFractionDigits="0"/></span><br>
                                        <span class="price-new"><fmt:formatNumber value="${earliestSession.adultPrice}" type="currency" currencySymbol="đ" maxFractionDigits="0"/></span>
                                    </p>
                                    <a href="tourDetail.jsp?tourId=${tour.tourID}" class="btn book-btn text-white" style="background-color: green;">Đặt ngay</a>
                                </div>
                            </div>
                        </div>
                    </c:if>
                </c:forEach>
            </div>
        </div>
        <script src="https://code.jquery.com/jquery-3.4.1.min.js"></script>
        <script src="https://cdn.jsdelivr.net/npm/bootstrap@5.0.0/dist/js/bootstrap.bundle.min.js"></script>
        <script src="assets/lib/wow/wow.min.js"></script>
        <script src="assets/lib/easing/easing.min.js"></script>
        <script src="assets/lib/waypoints/waypoints.min.js"></script>
        <script src="assets/lib/owlcarousel/owl.carousel.min.js"></script>
        <script src="assets/lib/tempusdominus/js/moment.min.js"></script>
        <script src="assets/lib/tempusdominus/js/moment-timezone.min.js"></script>
        <script src="assets/lib/tempusdominus/js/tempusdominus-bootstrap-4.min.js"></script>

        <!-- Template Javascript -->
        <script src="assets/js/main.js"></script>
    </body>
</html>
