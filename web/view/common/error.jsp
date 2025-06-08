<%-- 
    /*
 * Copyright (C) 2025, Group 6.
 * ProjectCode/Short Name of Application: TravelSystemService 
 * Support Management and Provide Travel Service System 
 *
 * Record of change:
 * DATE        Version    AUTHOR            DESCRIPTION
 * 2025-06-08  1.0        Group 6          Initial error page implementation
 */
--%>

<%@ page isErrorPage="true" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<!DOCTYPE html>
<html>
<head>
    <meta charset="UTF-8">
    <title>System Error</title>
    <style>
        body {
            font-family: Arial, sans-serif;
            background-color: #f8f8f8;
            text-align: center;
            padding: 60px;
        }
        .error-box {
            background-color: #fff;
            border: 1px solid #ccc;
            display: inline-block;
            padding: 40px;
            border-radius: 10px;
            box-shadow: 0 0 10px #aaa;
        }
        h1 {
            color: #d9534f;
        }
        p {
            margin-top: 20px;
            color: #555;
        }
        .details {
            margin-top: 30px;
            color: #999;
            font-size: 0.9em;
        }
    </style>
</head>
<body>
    <div class="error-box">
        <h1>Oops! Something went wrong.</h1>
        <p>We're sorry, but an unexpected error has occurred.</p>

        <div class="details">
            <p><strong>Error Message:</strong></p>
            <pre><%= exception != null ? exception.getMessage() : "Unknown error" %></pre>
        </div>
        
        <p>Please try again later or contact the system administrator.</p>
    </div>
</body>
</html>
