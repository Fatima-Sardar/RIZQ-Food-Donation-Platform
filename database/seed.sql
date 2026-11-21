USE rizq_db;

-- Admin account
INSERT INTO users
(name, email, password, phone, role, active)
SELECT
'RIZQ Admin', 'admin@rizq.com', 'Admin123',
'03000000000', 'ADMIN', TRUE
WHERE NOT EXISTS (
    SELECT 1 FROM users WHERE email = 'admin@rizq.com'
);

-- Demo Donor
INSERT INTO users
(name, email, password, phone, role, active)
SELECT
'Demo Donor', 'demo.donor@rizq.com', 'Demo123',
'03001111111', 'DONOR', TRUE
WHERE NOT EXISTS (
    SELECT 1 FROM users WHERE email = 'demo.donor@rizq.com'
);

-- Demo Volunteer
INSERT INTO users
(name, email, password, phone, role, active)
SELECT
'Demo Volunteer', 'demo.volunteer@rizq.com', 'Demo123',
'03002222222', 'VOLUNTEER', TRUE
WHERE NOT EXISTS (
    SELECT 1 FROM users WHERE email = 'demo.volunteer@rizq.com'
);

-- Demo Welfare Organization
INSERT INTO users
(name, email, password, phone, role, organization_name, active)
SELECT
'Demo Welfare', 'demo.welfare@rizq.com', 'Demo123',
'03003333333', 'WELFARE_ORG', 'Demo Welfare Foundation', TRUE
WHERE NOT EXISTS (
    SELECT 1 FROM users WHERE email = 'demo.welfare@rizq.com'
);

-- Demo Donation
INSERT INTO donation_posts
(donor_id, title, description, quantity, expiry_at,
 pickup_address, status)
SELECT
u.id, 'Demo Fresh Food',
'Fresh food for needy families', 25,
'2026-12-31 18:00:00',
'Sukkur City Center', 'AVAILABLE'
FROM users u
WHERE u.email = 'demo.donor@rizq.com'
AND NOT EXISTS (
    SELECT 1 FROM donation_posts
    WHERE title = 'Demo Fresh Food'
    AND donor_id = u.id
);