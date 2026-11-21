package rizq.dao;

import rizq.DB;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

public class AdminDao {

    // View all registered users
    public void listUsers() throws Exception {

        String sql = "SELECT id, name, email, phone, role, "
        + "organization_name, active FROM users "
        + "WHERE role <> 'ADMIN' "
        + "ORDER BY id";

        try (Connection c = DB.getConnection();
             PreparedStatement p = c.prepareStatement(sql);
             ResultSet rs = p.executeQuery()) {

            while (rs.next()) {
                System.out.println("\nUser ID: " + rs.getInt("id"));
                System.out.println("Name: " + rs.getString("name"));
                System.out.println("Email: " + rs.getString("email"));
                System.out.println("Phone: " + rs.getString("phone"));
                System.out.println("Role: " + rs.getString("role"));
                System.out.println("Organization: "
                        + rs.getString("organization_name"));
                System.out.println("Active: " + rs.getBoolean("active"));
            }
        }
    }

    // Activate or deactivate a non-admin user
    public boolean setUserActive(int userId, boolean active)
            throws Exception {

        String sql = "UPDATE users SET active = ? "
                + "WHERE id = ? AND role <> 'ADMIN'";

        try (Connection c = DB.getConnection();
             PreparedStatement p = c.prepareStatement(sql)) {

            p.setBoolean(1, active);
            p.setInt(2, userId);

            return p.executeUpdate() > 0;
        }
    }

    // View all donations
    public void listAllDonations() throws Exception {

        String sql = "SELECT id, donor_id, title, quantity, "
                + "status, expiry_at FROM donation_posts "
                + "ORDER BY id DESC";

        try (Connection c = DB.getConnection();
             PreparedStatement p = c.prepareStatement(sql);
             ResultSet rs = p.executeQuery()) {

            while (rs.next()) {
                System.out.println("\nDonation ID: "
                        + rs.getInt("id"));
                System.out.println("Donor ID: "
                        + rs.getInt("donor_id"));
                System.out.println("Title: "
                        + rs.getString("title"));
                System.out.println("Quantity: "
                        + rs.getInt("quantity"));
                System.out.println("Status: "
                        + rs.getString("status"));
                System.out.println("Expiry: "
                        + rs.getString("expiry_at"));
            }
        }
    }

    // View all claims
    public void listClaims() throws Exception {

        String sql = "SELECT c.id, c.donation_id, c.claimant_id, "
                + "c.status, c.created_at FROM claims c "
                + "ORDER BY c.id DESC";

        try (Connection c = DB.getConnection();
             PreparedStatement p = c.prepareStatement(sql);
             ResultSet rs = p.executeQuery()) {

            while (rs.next()) {
                System.out.println("\nClaim ID: " + rs.getInt("id"));
                System.out.println("Donation ID: "
                        + rs.getInt("donation_id"));
                System.out.println("Claimant ID: "
                        + rs.getInt("claimant_id"));
                System.out.println("Status: "
                        + rs.getString("status"));
                System.out.println("Date: "
                        + rs.getString("created_at"));
            }
        }
    }

    // View all feedback
    public void listFeedback() throws Exception {

        String sql = "SELECT id, donation_id, user_id, "
                + "rating, comments, created_at "
                + "FROM feedback ORDER BY id DESC";

        try (Connection c = DB.getConnection();
             PreparedStatement p = c.prepareStatement(sql);
             ResultSet rs = p.executeQuery()) {

            while (rs.next()) {
                System.out.println("\nFeedback ID: "
                        + rs.getInt("id"));
                System.out.println("Donation ID: "
                        + rs.getInt("donation_id"));
                System.out.println("User ID: "
                        + rs.getInt("user_id"));
                System.out.println("Rating: "
                        + rs.getInt("rating"));
                System.out.println("Comments: "
                        + rs.getString("comments"));
                System.out.println("Date: "
                        + rs.getString("created_at"));
            }
        }
    }
}