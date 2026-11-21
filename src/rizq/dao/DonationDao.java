package rizq.dao;

import rizq.DB;
import rizq.ds.SinglyLinkedList;
import rizq.ds.BinarySearchTree;
import rizq.ds.CircularLinkedList;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class DonationDao {

    private SinglyLinkedList<Integer> availableDonationIds =
            new SinglyLinkedList<>();

    private BinarySearchTree donationIndex =
            new BinarySearchTree();

    // Create donation
    public int create(
            int donorId,
            String title,
            String description,
            int quantity,
            String expiry,
            String pickupAddress) throws Exception {

        String sql = "INSERT INTO donation_posts "
                + "(donor_id, title, description, quantity, "
                + "expiry_at, pickup_address, status) "
                + "VALUES (?, ?, ?, ?, ?, ?, 'AVAILABLE')";

        try (Connection c = DB.getConnection();
             PreparedStatement p = c.prepareStatement(
                     sql, PreparedStatement.RETURN_GENERATED_KEYS)) {

            p.setInt(1, donorId);
            p.setString(2, title);
            p.setString(3, description);
            p.setInt(4, quantity);
            p.setString(5, expiry);
            p.setString(6, pickupAddress);

            p.executeUpdate();

            try (ResultSet rs = p.getGeneratedKeys()) {
                if (rs.next()) {
                    int id = rs.getInt(1);
                    donationIndex.insert(id);
                    return id;
                }
            }
        }

        throw new SQLException("Donation ID could not be generated.");
    }

    // Load available donations into data structures
    public void listAvailable() throws Exception {

        availableDonationIds = new SinglyLinkedList<>();
        donationIndex = new BinarySearchTree();

        String sql = "SELECT id, title, description, quantity, "
                + "expiry_at, pickup_address "
                + "FROM donation_posts "
                + "WHERE status = 'AVAILABLE' "
                + "AND expiry_at > NOW() ORDER BY id DESC";

        try (Connection c = DB.getConnection();
             PreparedStatement p = c.prepareStatement(sql);
             ResultSet rs = p.executeQuery()) {

            boolean found = false;

            while (rs.next()) {
                found = true;

                int id = rs.getInt("id");

                availableDonationIds.add(id);
                donationIndex.insert(id);

                System.out.println("\nDonation ID: " + id);
                System.out.println("Title: " + rs.getString("title"));
                System.out.println("Description: " + rs.getString("description"));
                System.out.println("Quantity: " + rs.getInt("quantity"));
                System.out.println("Expiry: " + rs.getString("expiry_at"));
                System.out.println("Pickup: " + rs.getString("pickup_address"));
            }

            if (!found) {
                System.out.println("No available donations.");
            }
        }
    }

    // Load all available donations into Circular Linked List
    public void loadAvailableDonationsInto(
            CircularLinkedList<String> rotation) throws Exception {

        String sql = "SELECT id, title FROM donation_posts "
                + "WHERE status = 'AVAILABLE' "
                + "AND expiry_at > NOW() ORDER BY id ASC";

        try (Connection c = DB.getConnection();
             PreparedStatement p = c.prepareStatement(sql);
             ResultSet rs = p.executeQuery()) {

            while (rs.next()) {
                int id = rs.getInt("id");
                String title = rs.getString("title");

                rotation.add("Donation ID: " + id + " - " + title);
            }
        }
    }

    // Check donation ID through Binary Search Tree
    public boolean isDonationIndexed(int donationId) {
        return donationIndex.search(donationId);
    }

    // Claim donation
    public boolean claimDonation(
            int donationId, int claimantId) throws Exception {

        if (!isDonationIndexed(donationId)) {
            listAvailable();

            if (!isDonationIndexed(donationId)) {
                return false;
            }
        }

        String updateSql = "UPDATE donation_posts "
                + "SET status = 'ACCEPTED' "
                + "WHERE id = ? AND status = 'AVAILABLE' "
                + "AND expiry_at > NOW()";

        String insertSql = "INSERT INTO claims "
                + "(donation_id, claimant_id, status) "
                + "VALUES (?, ?, 'ACCEPTED')";

        try (Connection c = DB.getConnection()) {

            c.setAutoCommit(false);

            try {
                int updated;

                try (PreparedStatement p = c.prepareStatement(updateSql)) {
                    p.setInt(1, donationId);
                    updated = p.executeUpdate();
                }

                if (updated == 0) {
                    c.rollback();
                    return false;
                }

                try (PreparedStatement p = c.prepareStatement(insertSql)) {
                    p.setInt(1, donationId);
                    p.setInt(2, claimantId);
                    p.executeUpdate();
                }

                c.commit();
                return true;

            } catch (SQLException e) {
                c.rollback();
                throw e;

            } finally {
                c.setAutoCommit(true);
            }
        }
    }

    // Display user's claims
    public void listMyClaims(int claimantId) throws Exception {

        String sql = "SELECT c.id AS claim_id, "
                + "d.id AS donation_id, d.title, "
                + "d.quantity, c.status "
                + "FROM claims c "
                + "JOIN donation_posts d "
                + "ON c.donation_id = d.id "
                + "WHERE c.claimant_id = ? "
                + "ORDER BY c.id DESC";

        try (Connection c = DB.getConnection();
             PreparedStatement p = c.prepareStatement(sql)) {

            p.setInt(1, claimantId);

            try (ResultSet rs = p.executeQuery()) {

                boolean found = false;

                while (rs.next()) {
                    found = true;

                    System.out.println("\nClaim ID: " + rs.getInt("claim_id"));
                    System.out.println("Donation ID: " + rs.getInt("donation_id"));
                    System.out.println("Title: " + rs.getString("title"));
                    System.out.println("Quantity: " + rs.getInt("quantity"));
                    System.out.println("Status: " + rs.getString("status"));
                }

                if (!found) {
                    System.out.println("No claims found.");
                }
            }
        }
    }

    // Submit feedback
    public boolean submitFeedback(
            int donationId,
            int userId,
            int rating,
            String comments) throws Exception {

        String sql = "INSERT INTO feedback "
                + "(donation_id, user_id, rating, comments) "
                + "SELECT ?, ?, ?, ? WHERE EXISTS "
                + "(SELECT 1 FROM claims "
                + "WHERE donation_id = ? AND claimant_id = ?)";

        try (Connection c = DB.getConnection();
             PreparedStatement p = c.prepareStatement(sql)) {

            p.setInt(1, donationId);
            p.setInt(2, userId);
            p.setInt(3, rating);
            p.setString(4, comments);
            p.setInt(5, donationId);
            p.setInt(6, userId);

            return p.executeUpdate() > 0;
        }
    }
}
