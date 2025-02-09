package App1;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

@WebServlet("/ChatServlet")
public class ChatServlet extends HttpServlet {
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
    	request.setCharacterEncoding("UTF-8");  // ここを追加
        response.setContentType("text/html; charset=UTF-8");
        String userMessage = request.getParameter("userMessage");
        String botResponse = generateResponse(userMessage);
        
        HttpSession session = request.getSession();
        String chatHistory = (String) session.getAttribute("chatHistory");
        if (chatHistory == null) {
            chatHistory = "";
        }
        
        chatHistory += "ユーザー: " + userMessage + "<br>Bot: " + botResponse + "<br>";
        session.setAttribute("chatHistory", chatHistory);
        
        response.sendRedirect("chat.jsp");
    }

    private String generateResponse(String userMessage) {
        if (userMessage.contains("こんにちは")) {
            return "こんにちは！元気ですか？";
        } else if (userMessage.contains("名前")) {
            return "私はシンプルなチャットボットです。";
        } else {
            return "すみません、よくわかりません。";
        }
    }
}
