package rizq;

import rizq.dao.*;
import rizq.ds.*;
import java.util.Scanner;

import org.jline.reader.LineReader;
import org.jline.reader.LineReaderBuilder;
import org.jline.terminal.Terminal;
import org.jline.terminal.TerminalBuilder;

public class Main {

    static Scanner sc = new Scanner(System.in);

    static Terminal terminal;
    static LineReader reader;

    static {
        try {
            terminal = TerminalBuilder.builder()
                    .system(true)
                    .build();

            reader = LineReaderBuilder.builder()
                    .terminal(terminal)
                    .build();

        } catch (Exception e) {
            System.out.println("Terminal initialization failed.");
        }
    }

    static UserDao users = new UserDao();
    static DonationDao donations = new DonationDao();
    static ActivityDao activityDao = new ActivityDao();
    // Data structures
    static DoublyLinkedList<String> activity =
            new DoublyLinkedList<>();

    static CircularLinkedList<String> rotation =
            new CircularLinkedList<>();

    static Stack<String> recent =
            new Stack<>();

    static Queue<Integer> tasks =
            new Queue<>();

    static String readInput() {
        if (reader != null) {
            return reader.readLine();
        }
        return sc.nextLine();
    }

    static String readPassword() {
        if (reader != null) {
            return reader.readLine("Password: ", '\u2022');
        }

        System.out.print("Password: ");
        return sc.nextLine();
    }

    public static void main(String[] args) {

        // Load available donations from database at startup
        try {
            donations.loadAvailableDonationsInto(rotation);
            System.out.println(
                    "Available donations loaded into circular rotation."
            );
        } catch (Exception e) {
            System.out.println(
                    "Could not load donation rotation: " + e.getMessage()
            );
        }

        while (true) {

            System.out.println("\n=== RIZQ FOOD DONATION PLATFORM ===");
            System.out.println("1 Register");
            System.out.println("2 Login");
            System.out.println("3 Exit");
            System.out.print("Choose: ");

            String choice = readInput();

            try {

                if (choice.equals("1")) {
                    register();

                } else if (choice.equals("2")) {
                    login();

                } else if (choice.equals("3")) {
                    System.out.println("Thank you for using RIZQ.");
                    break;

                } else {
                    System.out.println("Invalid choice.");
                }

            } catch (Exception e) {
                System.out.println("Error: " + e.getMessage());
            }
        }
    }

    // Registration
    static void register() throws Exception {

        System.out.println("\n1 Donor");
        System.out.println("2 Volunteer");
        System.out.println("3 Welfare Organization");
        System.out.print("Select account type: ");

        String type = readInput();

        String role;

        if (type.equals("1")) {
            role = "DONOR";
        } else if (type.equals("2")) {
            role = "VOLUNTEER";
        } else if (type.equals("3")) {
            role = "WELFARE_ORG";
        } else {
            System.out.println("Invalid role.");
            return;
        }

        System.out.print("Full name / Contact person: ");
        String name = readInput();

        System.out.print("Email: ");
        String email = readInput().trim();

        String password = readPassword();

        System.out.print("Phone: ");
        String phone = readInput();

        String org = null;

        if (role.equals("WELFARE_ORG")) {
            System.out.print("Organization name: ");
            org = readInput();
        }

        int id = users.register(
                name, email, password, phone, role, org
        );

        System.out.println("Registration successful. User ID: " + id);
    }

    // Login
    static void login() throws Exception {

        System.out.print("Email: ");
        String email = readInput().trim();

        String password = readPassword();

        UserDao.AuthUser user = users.login(email, password);

        if (user == null) {
            System.out.println("Invalid login or inactive account.");
            return;
        }

        System.out.println("\nWelcome, " + user.name);
        System.out.println("Role: " + user.role);

        switch (user.role) {

            case "DONOR":
                donorDashboard(user);
                break;

            case "VOLUNTEER":
                participantDashboard(user);
                break;

            case "WELFARE_ORG":
                welfareDashboard(user);
                break;

            case "ADMIN":
                adminDashboard(user);
                break;

            default:
                System.out.println("Unknown account role.");
        }
    }

    // Donor Dashboard
    static void donorDashboard(UserDao.AuthUser user)
            throws Exception {

        while (true) {

            System.out.println("\n=== DONOR DASHBOARD ===");
            System.out.println("1 Create Donation");
            System.out.println("2 View Available Donations");
            System.out.println("3 View Activity History");
            System.out.println("4 Next Donation in Rotation");
            System.out.println("5 Logout");

            String choice = readInput();

            if (choice.equals("1")) {

                System.out.print("Donation title: ");
                String title = readInput();

                System.out.print("Description: ");
                String desc = readInput();

                System.out.print("Quantity: ");
                int qty = Integer.parseInt(readInput());

                System.out.print("Expiry (YYYY-MM-DD HH:MM:SS): ");
                String exp = readInput();

                System.out.print("Pickup address: ");
                String addr = readInput();

                int donationId = donations.create(
                        user.id, title, desc, qty, exp, addr
                );

                String message = "Created donation ID: "
                        + donationId + " - " + title;

                recordActivity(user.id, message);

                // Add new donation to circular rotation
                rotation.add(
                        "Donation ID: " + donationId + " - " + title
                );

                System.out.println("Donation saved successfully.");
                System.out.println("Donation ID: " + donationId);

            } else if (choice.equals("2")) {

                donations.listAvailable();

            } else if (choice.equals("3")) {

              showActivityHistory(user);
            } else if (choice.equals("4")) {

                showNextRotation();

            } else if (choice.equals("5")) {

                break;

            } else {

                System.out.println("Invalid choice.");
            }
        }
    }

    // Activity History
   static void recordActivity(int userId, String message)
        throws Exception {

    activity.add(message);
    recent.push(message);

    activityDao.logActivity(userId, message);
    }

    static void showActivityHistory(UserDao.AuthUser user)
        throws Exception {

    activityDao.showActivityHistory(user.id);
    }

    // Circular Linked List rotation
    static void showNextRotation() {

        String next = rotation.next();

        if (next == null) {
            System.out.println("No donations in rotation.");
        } else {
            System.out.println("\nNext Donation in Rotation:");
            System.out.println(next);
        }
    }

    // Process donation requests using Queue
    static boolean processClaimRequest(
            int donationId, int userId) throws Exception {

        tasks.enqueue(donationId);

        while (!tasks.isEmpty()) {

            int nextDonationId = tasks.dequeue();

            boolean result = donations.claimDonation(
                    nextDonationId, userId
            );

            if (nextDonationId == donationId) {
                return result;
            }
        }

        return false;
    }

    // Volunteer Dashboard
    static void participantDashboard(UserDao.AuthUser user)
            throws Exception {

        while (true) {

            System.out.println("\n=== VOLUNTEER DASHBOARD ===");
            System.out.println("1 View Available Donations");
            System.out.println("2 Claim Donation");
            System.out.println("3 View My Accepted Donations");
            System.out.println("4 Logout");

            String choice = readInput();

            if (choice.equals("1")) {

                donations.listAvailable();

            } else if (choice.equals("2")) {

                donations.listAvailable();

                System.out.print("Enter Donation ID to claim: ");
                int donationId = Integer.parseInt(readInput());

                boolean result = processClaimRequest(
                        donationId, user.id
                );

                if (result) {
                          recordActivity(
                           user.id,
                         "Volunteer claimed donation: " + donationId
                );

                    System.out.println("Donation claimed successfully!");
                } else {
                    System.out.println(
                            "Donation unavailable or already claimed."
                    );
                }

            } else if (choice.equals("3")) {

                donations.listMyClaims(user.id);

            } else if (choice.equals("4")) {

                break;

            } else {

                System.out.println("Invalid choice.");
            }
        }
    }

    // Welfare Organization Dashboard
    static void welfareDashboard(UserDao.AuthUser user)
            throws Exception {

        while (true) {

            System.out.println("\n=== WELFARE ORGANIZATION DASHBOARD ===");
            System.out.println("1 View Available Donations");
            System.out.println("2 Request Donation");
            System.out.println("3 View My Requests");
            System.out.println("4 Submit Feedback");
            System.out.println("5 Logout");

            String choice = readInput();

            if (choice.equals("1")) {

                donations.listAvailable();

            } else if (choice.equals("2")) {

                donations.listAvailable();

                System.out.print("Enter Donation ID: ");
                int id = Integer.parseInt(readInput());

                boolean result = processClaimRequest(id, user.id);

                if (result) {
                    recordActivity(
                      user.id,
                   "Welfare organization requested donation: " + id
                );

                    System.out.println("Donation requested successfully!");
                } else {
                    System.out.println(
                            "Donation unavailable or already claimed."
                    );
                }

            } else if (choice.equals("3")) {

                donations.listMyClaims(user.id);

            } else if (choice.equals("4")) {

                System.out.print("Enter Donation ID: ");
                int id = Integer.parseInt(readInput());

                System.out.print("Rating (1-5): ");
                int rating = Integer.parseInt(readInput());

                if (rating < 1 || rating > 5) {
                    System.out.println("Invalid rating.");
                    continue;
                }

                System.out.print("Comments: ");
                String comments = readInput();

                boolean result = donations.submitFeedback(
                        id, user.id, rating, comments
                );

                System.out.println(result
                        ? "Feedback submitted successfully!"
                        : "You must claim this donation first.");

            } else if (choice.equals("5")) {

                break;

            } else {

                System.out.println("Invalid choice.");
            }
        }
    }

    // Admin Dashboard
    static void adminDashboard(UserDao.AuthUser user)
            throws Exception {

        AdminDao admin = new AdminDao();

        while (true) {

            System.out.println("\n=== ADMIN DASHBOARD ===");
            System.out.println("1 View All Donations");
            System.out.println("2 View All Users");
            System.out.println("3 Activate/Deactivate User");
            System.out.println("4 View All Claims");
            System.out.println("5 View All Feedback");
            System.out.println("6 Logout");
            System.out.print("Choose: ");

            String choice = readInput();

            if (choice.equals("1")) {

                admin.listAllDonations();

            } else if (choice.equals("2")) {

                admin.listUsers();

            } else if (choice.equals("3")) {

                System.out.print("Enter User ID: ");
                int id = Integer.parseInt(readInput());

                System.out.println("1 Activate");
                System.out.println("2 Deactivate");
                System.out.print("Choose: ");

                String action = readInput();

                if (action.equals("1")) {

                    System.out.println(
                            admin.setUserActive(id, true)
                                    ? "User activated."
                                    : "User not found or cannot be changed."
                    );

                } else if (action.equals("2")) {

                    System.out.println(
                            admin.setUserActive(id, false)
                                    ? "User deactivated."
                                    : "User not found or cannot be changed."
                    );

                } else {

                    System.out.println("Invalid choice.");
                }

            } else if (choice.equals("4")) {

                admin.listClaims();

            } else if (choice.equals("5")) {

                admin.listFeedback();

            } else if (choice.equals("6")) {

                break;

            } else {

                System.out.println("Invalid choice.");
            }
        }
    }
}
