package App1;

import java.io.IOException;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.nio.charset.StandardCharsets;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * Servlet implementation class mainpage
 */
@WebServlet("/mainpage")
public class mainpage extends HttpServlet {
    private static final long serialVersionUID = 1L;

    /**
     * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
     */
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String error = "";
        String msg = request.getParameter("message");

        if (msg == null || msg.isEmpty()) {
            response.getWriter().println("Message is missing");
            return;  // メッセージが無ければ処理を終了
        }

        // DB接続部分（PreparedStatementを使用してSQLインジェクション対策）
        DBAcs dba = new DBAcs();
        try {
            String sql = "INSERT INTO メッセージ (message_column) VALUES (?)";
            dba.executeUpdateWithPreparedStatement(sql, msg); // ここでmsgを挿入

            System.out.println("Message inserted: " + msg);

            // 外部サーバーへのPOSTリクエスト
            String jsonInputString = "{\"message\": \"" + msg + "\"}"; // 正しいJSON形式に修正

            URL url = new URL("http://127.0.0.1:5000");
            HttpURLConnection conn = (HttpURLConnection) url.openConnection();
            conn.setRequestMethod("POST");
            conn.setRequestProperty("Content-Type", "application/json; utf-8");
            conn.setDoOutput(true);

            // JSONデータの送信a
            try (OutputStream os = conn.getOutputStream()) {
                byte[] input = jsonInputString.getBytes(StandardCharsets.UTF_8);
                os.write(input, 0, input.length);
            }

            // レスポンスの確認
            int responseCode = conn.getResponseCode();
            if (responseCode == HttpURLConnection.HTTP_OK) {
                System.out.println("Data sent successfully.");
            } else {
                System.out.println("Failed to send data. Response code: " + responseCode);
            }

            conn.disconnect();
        } catch (Exception e) {
            e.printStackTrace();  // エラー内容を出力
            response.getWriter().println("An error occurred: " + e.getMessage());
        }
    }
}
