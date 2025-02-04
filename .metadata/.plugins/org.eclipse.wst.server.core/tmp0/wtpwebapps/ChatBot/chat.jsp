<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
    <title>ChatBot</title>
</head>
<body>
    <h2>ChatBot</h2>
    <form action="ChatServlet" method="post">
        <input type="text" name="userMessage" required>
        <input type="submit" value="送信">
    </form>
    <div>
        <h3>会話履歴:</h3>
        <%
            String chatHistory = (String) session.getAttribute("chatHistory");
            if (chatHistory != null) {
                out.print(chatHistory);
            }
        %>
    </div>
</body>
</html>
