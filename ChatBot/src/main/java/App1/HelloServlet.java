package App1;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.nio.charset.StandardCharsets;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

/**
 * Jupyter Labと通信を行うサーブレット
 */
@WebServlet("/HelloServlet")
public class HelloServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;

	// Jupyter Lab のエンドポイントURL
	private final String JUPYTER_LAB_URL = "http://127.0.0.1:5000/process_data";

	/**
	 * POSTリクエストを処理
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		doGet(request, response);
	}

	/**
	 * GETリクエストを処理
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		request.setCharacterEncoding("UTF-8");

		// クライアントから送信されたメッセージを取得
		String msg = request.getParameter("message");
		if (msg == null || msg.isEmpty()) {
			response.setCharacterEncoding("UTF-8");
			response.getWriter().println("Message parameter is missing.");
			return;
		}

		// データベース操作用のオブジェクト
		DBAcs dba = new DBAcs();
		try {
			// Jupyter Lab にメッセージを送信し、レスポンスを取得
			String jupyterResponse = sendToJupyterLab(msg);
			jupyterResponse = decodeUnicode(jupyterResponse);

			// データベースにユーザーのメッセージを保存
			String sql = "INSERT INTO chat (text, status) VALUES ('" + msg + "','java');";
			dba.updateExe(sql);

			// セッションに会話履歴を保存
			HttpSession session = request.getSession();
			String chatHistory = (String) session.getAttribute("chatHistory");
			if (chatHistory == null) {
				chatHistory = "";
			}
			msg = decodeUnicode(msg);

			// ユーザーのメッセージ (右寄せ)
			chatHistory += "<div class='user-message'>" + msg + ":ユーザー</div>";

			// Jupyter のレスポンス (左寄せ)
			chatHistory += "<div class='bot-message'>Bot: " + jupyterResponse + "</div>";

			session.setAttribute("chatHistory", chatHistory);

			// index.jsp にリダイレクト
			response.sendRedirect("index.jsp");

		} catch (Exception e) {
			e.printStackTrace();
			response.getWriter().println("An error occurred: " + e.getMessage());
		}
	}

	/**
	 * Jupyter Lab にメッセージを送信するメソッド
	 */
	private String sendToJupyterLab(String data) {
		try {
			URL url = new URL(JUPYTER_LAB_URL);
			HttpURLConnection connection = (HttpURLConnection) url.openConnection();
			connection.setRequestMethod("POST");
			connection.setRequestProperty("Content-Type", "application/json; utf-8");
			connection.setDoOutput(true);

			// JSONデータを送信
			String jsonInputString = "{\"message\": \"" + data + "\"}";
			try (OutputStream os = connection.getOutputStream()) {
				byte[] input = jsonInputString.getBytes(StandardCharsets.UTF_8);
				os.write(input, 0, input.length);
			}

			// Jupyter Lab からのレスポンスを受け取る
			int responseCode = connection.getResponseCode();
			if (responseCode == HttpURLConnection.HTTP_OK) {
				try (BufferedReader br = new BufferedReader(
						new InputStreamReader(connection.getInputStream(), StandardCharsets.UTF_8))) {
					StringBuilder response = new StringBuilder();
					String line;
					while ((line = br.readLine()) != null) {
						response.append(line);
					}
					return response.toString();
				}
			} else {
				return "Error: Received HTTP response code " + responseCode;
			}

		} catch (IOException e) {
			e.printStackTrace();
			return "Error while communicating with Jupyter Lab: " + e.getMessage();
		}
	}

	/**
	 * Unicodeエスケープされた文字列をデコードするメソッド
	 */
	private String decodeUnicode(String response) {
		StringBuilder decodedString = new StringBuilder();
		int length = response.length();
		for (int i = 0; i < length; i++) {
			char ch = response.charAt(i);
			if (ch == '\\' && i + 5 < length && response.charAt(i + 1) == 'u') {
				String hexCode = response.substring(i + 2, i + 6);
				try {
					int codePoint = Integer.parseInt(hexCode, 16);
					decodedString.append((char) codePoint);
					i += 5;
				} catch (NumberFormatException e) {
					decodedString.append(ch);
				}
			} else {
				decodedString.append(ch);
			}
		}
		return decodedString.toString();
	}
}
