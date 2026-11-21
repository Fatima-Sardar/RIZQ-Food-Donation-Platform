package rizq.dao;

import rizq.DB;
import java.sql.*;

public class UserDao {

    public static class AuthUser {
        public int id;
        public String name;
        public String email;
        public String phone;
        public String role;
        public String organizationName;

        public AuthUser(int id, String name, String email,
                        String phone, String role, String organizationName) {
            this.id = id;
            this.name = name;
            this.email = email;
            this.phone = phone;
            this.role = role;
            this.organizationName = organizationName;
        }
    }

    public int register(String name, String email, String password,
                        String phone, String role, String orgName)
                        throws SQLException {

        String sql = "INSERT INTO users(name,email,password,phone,role,organization_name) "
                   + "VALUES(?,?,?,?,?,?)";

        try (Connection c = DB.getConnection();
             PreparedStatement p = c.prepareStatement(
                 sql, Statement.RETURN_GENERATED_KEYS)) {

            p.setString(1, name);
            p.setString(2, email);
            p.setString(3, password);
            p.setString(4, phone);
            p.setString(5, role);
            p.setString(6, orgName);

            p.executeUpdate();

            try (ResultSet r = p.getGeneratedKeys()) {
                return r.next() ? r.getInt(1) : -1;
            }
        }
    }

    public AuthUser login(String email, String password)
            throws SQLException {

        String sql = "SELECT id,name,email,phone,role,organization_name "
                   + "FROM users WHERE email=? AND password=? AND active=1";

        try (Connection c = DB.getConnection();
             PreparedStatement p = c.prepareStatement(sql)) {

            p.setString(1, email);
            p.setString(2, password);

            try (ResultSet r = p.executeQuery()) {
                if (r.next()) {
                    return new AuthUser(
                        r.getInt("id"),
                        r.getString("name"),
                        r.getString("email"),
                        r.getString("phone"),
                        r.getString("role"),
                        r.getString("organization_name")
                    );
                }
            }
        }

        return null;
    }
}