<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ page import="java.sql.Connection, java.sql.DriverManager, java.sql.PreparedStatement, java.sql.ResultSet, java.sql.SQLException" %>
<!DOCTYPE html>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>チャットボット</title>
<style>
body {
	background-color: #000;
	font-family: Arial, sans-serif;
}

.chat-container {
	text-align: center;
}

#message {
	width: 70%;
	padding: 1%;
	border-radius: 30px;
	font-size: 20px; /* フォントサイズを統一 */
}

.button-46 {
	width: 70px;
	height: 70px;
	border: none;
	border-radius: 50%;
	box-shadow: 0 10px 10px rgb(0 0 0/ 20%);
	background-image: linear-gradient(0, #ddd, #fff);
	color: #333333;
	font-weight: 600;
	font-size: .9em;
}

.button-46:hover {
	transform: scale(.99);
	box-shadow: 0 5px 5px rgb(0 0 0/ 20%);
}

.chat-messages {
	color: #FFFFFF;
	font-size: 25px;
	max-height: 70vh;
	overflow-y: auto;
	padding: 10px;
	box-sizing: border-box;
}

/* メッセージのスタイル */
.user-message {
	text-align: right;
	color: #00FF00; /* 緑色 (ユーザー) */
	margin: 5px 0;
}

.bot-message {
	text-align: left;
	color: #00FFFF; /* 青色 (Jupyter) */
	margin: 5px 0;
	margin-bottom: 70px;
}

h1, h2 {
	color: #FFF;
}
h2{
margin-top: 15%}

.chat-input {
	position: fixed;
	bottom: 0;
	left: 50%;
	transform: translateX(-50%);
	width: 100%;
	background-color: #000;
	padding: 10px;
	box-sizing: border-box;
}

.main {
	margin: 0 150px 120px;
}

nav {
	margin: 27px auto 0;
	position: relative;
	width: 590px;
	height: 50px;
	background-color: #34495e;
	border-radius: 8px;
	font-size: 0;
}

nav a {
	line-height: 50px;
	height: 100%;
	font-size: 15px;
	display: inline-block;
	position: relative;
	z-index: 1;
	text-decoration: none;
	text-transform: uppercase;
	text-align: center;
	color: white;
	cursor: pointer;
}

nav .animation {
	position: absolute;
	height: 100%;
	top: 0;
	z-index: 0;
	transition: all .5s ease 0s;
	border-radius: 8px;
}

a:nth-child(1) {
	width: 100px;
}

a:nth-child(2) {
	width: 110px;
}

a:nth-child(3) {
	width: 100px;
}

a:nth-child(4) {
	width: 160px;
}

a:nth-child(5) {
	width: 120px;
}

nav .start-home, a:nth-child(1):hover ~.animation {
	width: 100px;
	left: 0;
	background-color: #1abc9c;
}

nav .start-about, a:nth-child(2):hover ~.animation {
	width: 110px;
	left: 100px;
	background-color: #e74c3c;
}

nav .start-blog, a:nth-child(3):hover ~.animation {
	width: 100px;
	left: 210px;
	background-color: #3498db;
}

nav .start-portefolio, a:nth-child(4):hover ~.animation {
	width: 160px;
	left: 310px;
	background-color: #9b59b6;
}

nav .start-contact, a:nth-child(5):hover ~.animation {
	width: 120px;
	left: 470px;
	background-color: #e67e22;
}

span {
	color: #2BD6B4;
}

h3 {
	color: white;
}
</style>
</head>
<body>
    <form action="HelloServlet" method="POST">
        <div class="chat-container">
            <div class="header">
                <h1>ChatBoton</h1>
                <nav>
                    <a href="#">empty</a> <a href="#">empty</a> <a href="#">empty</a> <a href="#">empty</a> <a href="#">empty</a>
                    <div class="animation start-home"></div>
                </nav>
                <h3>会話履歴:</h3>
            </div>

            <div class="chat-messages">
                <div class="main">
                    <%
                    // データベース接続情報
                    String url = "jdbc:mysql://localhost:3306/test"; // DBのURL
                    String user = "root"; // DBのユーザー名
                    String password = "root"; // DBのパスワード（実際は設定すべき）
                    try (
                        Connection conn = DriverManager.getConnection(url, user, password);
                        PreparedStatement pstmt = conn.prepareStatement("SELECT text, status FROM chat");
                        ResultSet rs = pstmt.executeQuery()
                    ) {
                        // チャット履歴を取得
                        while (rs.next()) {
                            String userType = rs.getString("status"); // ユーザー or ボット
                            String message = rs.getString("text");

                            if ("java".equals(userType)) {
                                out.println("<div class='user-message'>" + message + "</div>");
                            } else {
                                out.println("<div class='bot-message'>" + message + "</div>");
                            }
                        }
                    } catch (SQLException e) {
                        out.println("<p style='color:red;'>エラー: " + e.getMessage() + "</p>");
                    }
                    %>
                </div>
            </div>

            <div class="chat-input">
                <input type="text" id="message" name="message" placeholder="メッセージを入力..." />
                <button class="button-46" type="submit">
                    <span>送信</span>
                </button>
            </div>
        </div>
    </form>
</body>
</html>
