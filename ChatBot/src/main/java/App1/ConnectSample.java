package App1;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class ConnectSample {

	public static void main(String[] args) {
		//DB接続用定数
		String DATABASE_NAME = "データベース名";
		String PROPATIES = "?characterEncoding=UTF-8&serverTimezone=JST";
		String URL = "jdbc:mySQL://localhost/" + DATABASE_NAME+PROPATIES;
		//DB接続用・ユーザ定数
		String USER = "ユーザー名";
		String PASS = "";

		try {
			//MySQL に接続する
			Class.forName("com.mysql.cj.jdbc.Driver");
			//データベースに接続
			Connection conn = DriverManager.getConnection(URL, USER, PASS);

			// データベースに対する処理
			System.out.println("データベースに接続に成功");

		} catch (SQLException | ClassNotFoundException e) {
			e.printStackTrace();
		}

	}

}
