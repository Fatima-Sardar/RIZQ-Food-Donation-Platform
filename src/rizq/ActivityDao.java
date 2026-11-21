package rizq.dao;

import rizq.DB;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

public class ActivityDao {

    // Save activity in MySQL
    public void logActivity(int userId, String message)
            throws Exception {

        String sql = "INSERT INTO activity_logs "
                + "(user_id, activity_message) VALUES (?, ?)";

        try (Connection c = DB.getConnection();
             PreparedStatement p = c.prepareStatement(sql)) {

            p.setInt(1, userId);
            p.setString(2, message);
            p.executeUpdate();
        }
    }

    // Display activity history from MySQL
    public void showActivityHistory(int userId)
            throws Exception {

        String sql = "SELECT activity_message, created_at "
                + "FROM activity_logs "
                + "WHERE user_id = ? "
                + "ORDER BY id DESC";

        boolean found = false;
        String latestActivity = null;

        try (Connection c = DB.getConnection();
             PreparedStatement p = c.prepareStatement(sql)) {

            p.setInt(1, userId);

            try (ResultSet rs = p.executeQuery()) {

                System.out.println("\n=== ACTIVITY HISTORY ===");
                System.out.println("All Activities:");

                while (rs.next()) {

                    String message = rs.getString("activity_message");
                    String date = rs.getString("created_at");

                    if (!found) {
                        latestActivity = message;
                    }

                    found = true;

                    System.out.println(
                            message + " | " + date
                    );
                }
            }
        }

        System.out.println("\nMost Recent Activity:");

        System.out.println(found
                ? latestActivity
                : "No recent activity.");
    }
}