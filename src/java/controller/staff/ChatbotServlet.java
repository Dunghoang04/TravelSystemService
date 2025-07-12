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
        // Làm mới danh sách tin nhắn khi servlet khởi động
        chatMessages.clear();
        super.init();
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        // Trả về danh sách tin nhắn dưới dạng JSON
        response.setContentType("application/json");
        response.setCharacterEncoding("UTF-8");
        Gson gson = new Gson();
        response.getWriter().write(gson.toJson(chatMessages));
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        String action = request.getParameter("action");
        if ("sendMessage".equals(action)) {
            String sender = request.getParameter("sender");
            String text = request.getParameter("text");
            if (sender != null && text != null) {
                Message message = new Message(sender, text, new java.util.Date().toString());
                chatMessages.add(message);
            }
        } else if ("clearMessages".equals(action)) {
            // Làm mới danh sách tin nhắn
            chatMessages.clear();
        }
        // Trả về danh sách tin nhắn mới nhất
        doGet(request, response);
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