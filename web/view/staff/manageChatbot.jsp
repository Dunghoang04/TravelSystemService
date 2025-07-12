<%--
 * Copyright (C) 2025, Group 6.
 * ProjectCode/Short Name of Application: TravelSystemService 
 * Support Management and Provide Travel Service System 
 *
 * Record of change:
 * DATE        Version    AUTHOR            DESCRIPTION
 * 2025-06-07  1.0        Group 6           First implementation
 * 2025-07-04  1.1        Grok              Added chatbot management for staff
 * 2025-07-04  1.2        Grok              Changed to sessionStorage for chat reset
 * 2025-07-05  1.3        Grok              Reverted to localStorage with session-based reset
 * 2025-07-05  1.4        Grok              Switched to server-side storage with ChatbotServlet
 * 2025-07-04  1.5        Grok              Updated UI to match admin layout
 * 2025-07-04  1.6        Grok              Fixed layout display issue
 * 2025-07-04  1.7        Grok              Aligned with TravelAgentService layout for inheritance
--%>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html>
<html lang="vi">
<head>
    <meta charset="utf-8" />
    <meta http-equiv="X-UA-Compatible" content="IE=edge" />
    <meta name="viewport" content="width=device-width, initial-scale=1, shrink-to-fit=no" />
    <meta name="description" content="Chatbot Management for TravelSystemService" />
    <meta name="author" content="Group 6" />
    <title>Quản lý Chatbot - Nhân viên</title>
    <!-- Bootstrap CSS -->
    <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.0/dist/css/bootstrap.min.css" rel="stylesheet" />
    <!-- Font Awesome -->
    <link href="https://use.fontawesome.com/releases/v6.3.0/css/all.css" rel="stylesheet" />
    <!-- SimpleBar CSS -->
    <link href="https://cdn.jsdelivr.net/npm/simplebar@5.3.0/dist/simplebar.min.css" rel="stylesheet" />
    <!-- SweetAlert2 CSS -->
    <link href="https://cdn.jsdelivr.net/npm/sweetalert2@11/dist/sweetalert2.min.css" rel="stylesheet" />
    <link href="${pageContext.request.contextPath}/assets/css/styles2.css" rel="stylesheet" />
    <style>
        body {
            font-family: -apple-system, BlinkMacSystemFont, "Segoe UI", Roboto, "Helvetica Neue", Arial, sans-serif;
        }
        .profile-title {
            font-size: 24px;
            font-weight: bold;
            color: #007bff;
            margin-bottom: 20px;
            text-align: center;
        }
        .card {
            border: none;
            border-radius: 8px;
            box-shadow: 0 2px 10px rgba(0, 0, 0, 0.1);
            transition: transform 0.2s;
            width: 80%;
            margin-left: 10%;
        }
        .card:hover {
            transform: translateY(-5px);
        }
        .card-header {
            background-color: #007bff;
            color: #fff;
            border-bottom: none;
            border-top-left-radius: 8px;
            border-top-right-radius: 8px;
            padding: 15px;
        }
        .chatbot-messages {
            max-height: 400px;
            overflow-y: auto;
            padding: 15px;
            background-color: #fff;
            border-radius: 5px;
            border: 1px solid #ddd;
        }
        .user-message {
            background-color: #e3f2fd;
            margin-left: 20%;
            text-align: right;
            padding: 10px;
            border-radius: 10px;
            margin-bottom: 10px;
            max-width: 70%;
        }
        .bot-message {
            background-color: #f1f1f1;
            margin-right: 20%;
            padding: 10px;
            border-radius: 10px;
            margin-bottom: 10px;
            max-width: 70%;
        }
        .staff-message {
            background-color: #d4edda;
            margin-right: 20%;
            padding: 10px;
            border-radius: 10px;
            margin-bottom: 10px;
            max-width: 70%;
        }
        .chatbot-input {
            display: flex;
            margin-top: 15px;
        }
        .chatbot-input input {
            flex-grow: 1;
            padding: 10px;
            border: 1px solid #ddd;
            border-radius: 5px;
            margin-right: 10px;
        }
        .chatbot-input button {
            padding: 10px 20px;
            background-color: #28a745;
            color: #fff;
            border: none;
            border-radius: 5px;
            cursor: pointer;
            transition: background-color 0.3s ease;
        }
        .chatbot-input button:hover {
            background-color: #218838;
        }


    </style>
</head>
<body class="sb-nav-fixed">
    <!-- Include Header -->
    <%@include file="../layout/headerAdmin.jsp" %>
    <div id="layoutSidenav">
        <!-- Include Sidebar -->
        <%@include file="../layout/sideNavOptionStaff.jsp" %>
        <div id="layoutSidenav_content">
            <main>
                <div class="container-fluid px-4">
                    <h1 class="mt-4 profile-title">Quản lý Chatbot</h1>
                    <div class="card mb-4">
                        <div class="card-header">Danh sách tin nhắn</div>
                        <div class="card-body">
                            <div class="chatbot-messages" id="chatbot-messages"></div>
                            <div class="chatbot-input">
                                <input type="text" id="staff-input" placeholder="Nhập phản hồi của bạn...">
                                <button onclick="sendStaffResponse()">Gửi</button>
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
    <!-- JavaScript Libraries -->
    <script src="https://code.jquery.com/jquery-3.4.1.min.js"></script>
    <script src="https://cdn.jsdelivr.net/npm/bootstrap@5.3.0/dist/js/bootstrap.bundle.min.js"></script>
    <script src="https://cdn.jsdelivr.net/npm/simplebar@5.3.0/dist/simplebar.min.js"></script>
    <script src="https://cdn.jsdelivr.net/npm/sweetalert2@11/dist/sweetalert2.all.min.js"></script>
    <script src="${pageContext.request.contextPath}/assets/js/scripts.js"></script>
    <script>
        // Load messages from server when the page loads
        document.addEventListener('DOMContentLoaded', function () {
            loadMessages();
            // Periodically update messages (every 2 seconds)
            setInterval(loadMessages, 2000);
        });

        // Function to append messages
        function appendMessage(text, className) {
            const chatBody = document.getElementById('chatbot-messages');
            const messageDiv = document.createElement('div');
            messageDiv.className = className;
            messageDiv.textContent = text;
            chatBody.appendChild(messageDiv);
            chatBody.scrollTop = chatBody.scrollHeight;
        }

        // Function to load messages from server
        function loadMessages() {
            $.get('ChatbotServlet', function (data) {
                const chatBody = document.getElementById('chatbot-messages');
                chatBody.innerHTML = ''; // Clear previous content
                if (data.length === 0) {
                    appendMessage('Không có tin nhắn nào.', 'bot-message');
                } else {
                    data.forEach(message => {
                        appendMessage(message.text, message.sender === 'user' ? 'user-message' : message.sender === 'bot' ? 'bot-message' : 'staff-message');
                    });
                }
            }).fail(function (xhr, status, error) {
                console.error('Lỗi khi tải tin nhắn:', error);
                const chatBody = document.getElementById('chatbot-messages');
                chatBody.innerHTML = '<div class="bot-message">Không thể tải tin nhắn. Vui lòng thử lại sau.</div>';
            });
        }
        

        // Send message on Enter key press
        document.getElementById('staff-input').addEventListener('keypress', function (e) {
            if (e.key === 'Enter') {
                sendStaffResponse();
            }
        });
    </script>
</body>
</html>