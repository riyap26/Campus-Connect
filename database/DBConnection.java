import java.awt.event.ActionEvent;
import java.sql.*;

public class DBConnection {

    public static Connection conn;

    public static Connection getConnection() {
        try {
            Class.forName("com.mysql.jdbc.Driver");
            conn = DriverManager.getConnection(
                    "jdbc:mysql://localhost:3306/phase2",
                    "root",
                    "12345678");
            return conn;
        } catch (Exception e) {
            System.out.println("Error: " + e.getMessage());
            return null;
        }
    }

    public void closeConnection() throws SQLException, Exception {
        conn.close();
    }
    public static ResultSet executeSelect(String query) throws SQLException {
        try {
            Connection conn = getConnection();
            if (conn != null) {
                Statement stmt = conn.createStatement();
                ResultSet rs = stmt.executeQuery(query);
                return(rs);
            }
            return null;
        } catch (Exception e) {
            System.out.println("Error: " + e.getMessage());
            return null;
        }
    }
    public static void executeInsert(String query) throws SQLException {
        try {
            Connection conn = getConnection();
            if (conn != null) {
                Statement stmt = conn.createStatement();
                stmt.executeUpdate(query);
            }
        } catch (Exception e) {
            System.out.println("Error: " + e.getMessage());
        }
    }

    public static void logInUser(ActionEvent event, String username, String password) throws SQLException {
        try {
            ResultSet resultSet = executeSelect("SELECT  . . . . . . ");
            // isBeforeFirst() used to determine whether the cursor
            // is at the default position of the resultSet (ret: boolean)
            if (!resultSet.isBeforeFirst()) {
                System.out.println("User not found in the database!");
            } else {
                while (resultSet.next()) {
                    String retrievedPassword = resultSet.getString("password");
                    String retrievedUsername = resultSet.getString("username");
                    if (retrievedPassword.equals(password)) {
                        // Do something good
                    } else {
                        System.out.println("Passwords did not match!");
                    }
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}
