<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>チャットボット</title>
<style>
body {
    background-color: #000;
    font-family: Arial, sans-serif;  /* フォントを追加 */
}

.chat-container {
    text-align: center;
}

#message {
    width: 70%;
    padding: 1%;
    border-radius: 30px;
}

.button-46 {
    width: 70px;
    height: 70px;
    border: none;
    border-radius: 50%;
    box-shadow: 0 10px 10px rgb(0 0 0 / 20%);
    background-image: linear-gradient(0, #ddd, #fff);
    color: #333333;
    font-weight: 600;
    font-size: .9em;
}

.button-46:hover {
    transform: scale(.99);
    box-shadow: 0 5px 5px rgb(0 0 0 / 20%);
}

.button-46 span {
    width: 100%;
    height: 100%;
    border-radius: 50%;
    background-image: linear-gradient(0, #fff, #ddd);
    line-height: 64px;
}

.chat-messages {
    color: #FFFFFF;
    font-size: 15px;
    max-height: 70vh;  /* 画面の高さに合わせる */
    overflow-y: auto;  /* スクロール可能に */
    padding: 10px;
    box-sizing: border-box;
}

h1, h2 {
    color: #FFF;
}

.chat-input {
    position: fixed;
    bottom: 0;  /* 画面下に固定 */
    left: 50%;
    transform: translateX(-50%);
    width: 100%;
    background-color: #000;
    padding: 10px;
    box-sizing: border-box;
}

</style>
</head>
<body>
	<form action="HelloServlet" method="POST">
		<div class="chat-container">
			<div class="header">
				<h1>チャットボット</h1>
			</div>

			<div class="chat-messages">

				<h3>会話履歴:</h3>
				<%
				String chatHistory = (String) session.getAttribute("chatHistory");
				if (chatHistory != null) {
					out.print(chatHistory);
				}
				%>

			</div>

			<div class="chat-input">
				<h2>お手伝いできることはありますか？</h2>

				<input type="text" id="message" name="message"
					placeholder="メッセージを入力..." />
				<button class="button-46">
					<span>送信</span>
				</button>
			</div>
		</div>
	</form>
</body>
</html>
