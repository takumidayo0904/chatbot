package App1;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.Statement;

// DBアクセスクラス
public class DBAcs {
    private Connection con = null;
    private Statement stmt = null;
    private ResultSet rs = null;
    
    // コンストラクタ
    public DBAcs() {
        System.out.println("ログイン処理実行");
        try {
            // JDBCドライバのロード
            Class.forName("com.mysql.cj.jdbc.Driver");
            
            // DBに接続
            con = DriverManager.getConnection("jdbc:mysql://localhost:3306/test", "root", "root");
            stmt = con.createStatement();
            
            System.out.println("ログイン成功");
        } catch (Exception e) {
            System.out.println("ログイン失敗");
            e.printStackTrace();
        }
    }

    // SELECT文実行メソッド
    public ResultSet selectExe(String query) {
        System.out.println("SELECT文実行");
        System.out.println("SQL文: " + query);
        try {
            rs = stmt.executeQuery(query);
            System.out.println("SELECT文成功");
        } catch (Exception ex) {
            System.out.println("SELECT文失敗");
            this.closeDB();
            ex.printStackTrace();
        }
        return rs;
    }

    // INSERT、UPDATE、DELETE文実行メソッド
    public int updateExe(String sql) {
        System.out.println("INSERT、UPDATE、DELETE文実行");
        System.out.println("SQL文: " + sql);
        int cnt = 0;
        try {
            stmt = con.createStatement();
            cnt = stmt.executeUpdate(sql);
            System.out.println("INSERT、UPDATE、DELETE文成功");
        } catch (Exception ex) {
            System.out.println("INSERT、UPDATE、DELETE文失敗");
            this.closeDB();
            ex.printStackTrace();
        }
        return cnt;
    }

    // DB切断メソッド
    public void closeDB() {
        try {
            System.out.println("DB切断処理");
            if (rs != null) {
                rs.close();
                rs = null;
            }
            if (stmt != null) {
                stmt.close();
                stmt = null;
            }
            if (con != null) {
                con.close();
                con = null;
            }
            System.out.println("DB切断成功");
        } catch (Exception ex) {
            System.out.println("DB切断失敗");
            ex.printStackTrace();
        }
    }
}
