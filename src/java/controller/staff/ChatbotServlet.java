/*
* Copyright (C) 2025, Group 6.
 * ProjectCode/Short Name of Application: TravelAgentService 
 * Support Management and Provide Travel Service System 
 *
 * Record of change:
 * DATE        Version    AUTHOR            DESCRIPTION
 * 2025-07-17  1.0        Hà Thị Duyên      First implementation
*/
package controller.staff;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import com.google.gson.Gson;

@WebServlet("/ChatbotServlet")
public class ChatbotServlet extends HttpServlet {
    private static List<Message> chatMessages = new ArrayList<>();

    @Override
    public void init() throws ServletException {
        // Không clear danh sách khi khởi động, chỉ khởi tạo nếu rỗng
        if (chatMessages.isEmpty()) {
            chatMessages = new ArrayList<>();
        }
        super.init();
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        // Chỉ chuyển hướng đến manageChatbot.jsp khi yêu cầu GET trực tiếp
        if (request.getParameter("ajax") == null) {
            request.getRequestDispatcher("/view/staff/manageChatbot.jsp").forward(request, response);
        } else {
            // Trả về JSON cho yêu cầu AJAX
            response.setContentType("application/json");
            response.setCharacterEncoding("UTF-8");
            Gson gson = new Gson();
            response.getWriter().write(gson.toJson(chatMessages));
        }
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        String action = request.getParameter("action");
        if ("sendMessage".equals(action)) {
            String sender = request.getParameter("sender");
            String text = request.getParameter("text");
            if (sender != null && text != null && !text.trim().isEmpty()) {
                Message message = new Message(sender, text, new java.util.Date().toString());
                chatMessages.add(message);
                // Gửi phản hồi ngay lập tức (nếu cần)
                response.getWriter().flush();
            }
        } else if ("clearMessages".equals(action)) {
            chatMessages.clear();
        }
        // Trả về danh sách tin nhắn mới nhất dưới dạng JSON
        response.setContentType("application/json");
        response.setCharacterEncoding("UTF-8");
        Gson gson = new Gson();
        response.getWriter().write(gson.toJson(chatMessages));
    }

    // Lớp nội bộ để biểu diễn tin nhắn
    public static class Message {
        private String sender;
        private String text;
        private String timestamp;

        public Message(String sender, String text, String timestamp) {
            this.sender = sender;
            this.text = text;
            this.timestamp = timestamp;
        }

        public String getSender() {
            return sender;
        }

        public String getText() {
            return text;
        }

        public String getTimestamp() {
            return timestamp;
        }
    }
}