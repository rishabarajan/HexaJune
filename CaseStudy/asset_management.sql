
CREATE DATABASE IF NOT EXISTS asset_management;
USE asset_management;

CREATE TABLE employee (
    employee_id INT AUTO_INCREMENT PRIMARY KEY,
    employee_name VARCHAR(100) NOT NULL,
    user_name VARCHAR(50) UNIQUE NOT NULL,
    password VARCHAR(100) NOT NULL,
    email VARCHAR(100) UNIQUE NOT NULL,
    contact_number VARCHAR(20),
    address VARCHAR(200),
    role VARCHAR(50) NOT NULL DEFAULT 'Employee',
    join_date DATE,
    gender VARCHAR(10),
    INDEX idx_username (user_name)
);


CREATE TABLE asset (
    asset_id INT AUTO_INCREMENT PRIMARY KEY,
    asset_name VARCHAR(100) NOT NULL,
    asset_category VARCHAR(50) NOT NULL,
    asset_model VARCHAR(100),
    manufacturing_date VARCHAR(20),
    expiry_date VARCHAR(20), 
    asset_value DECIMAL(10,2),
    status VARCHAR(20) DEFAULT 'Available',
    description TEXT,
    image_url VARCHAR(255),
    INDEX idx_category (asset_category)
);


CREATE TABLE asset_request (
    request_id INT AUTO_INCREMENT PRIMARY KEY,
    employee_id INT,
    asset_id INT,
    request_date VARCHAR(20), -- Changed to VARCHAR to match entity
    status VARCHAR(20) DEFAULT 'Pending',
    request_type VARCHAR(50),
    description TEXT,
    issue_type VARCHAR(50),
    FOREIGN KEY (employee_id) REFERENCES employee(employee_id) ON DELETE CASCADE,
    FOREIGN KEY (asset_id) REFERENCES asset(asset_id) ON DELETE CASCADE,
    INDEX idx_status (status)
);


INSERT INTO employee (employee_name, user_name, password, email, contact_number, address, role, join_date, gender) VALUES
('John Smith', 'johnsmith', 'password123', 'john.smith@hexaware.com', '9876543210', '123 Main St, Bangalore', 'Employee', '2020-01-15', 'Male'),
('Sarah Johnson', 'sarahj', 'sarahpass', 'sarah.j@hexaware.com', '8765432109', '456 Oak Ave, Mumbai', 'Employee', '2020-03-22', 'Female'),
('Michael Brown', 'michaelb', 'mikepass', 'michael.b@hexaware.com', '7654321098', '789 Pine Rd, Delhi', 'Manager', '2019-11-05', 'Male'),
('Emily Davis', 'emilyd', 'emilypass', 'emily.d@hexaware.com', '6543210987', '321 Elm St, Hyderabad', 'Admin', '2021-02-18', 'Female'),
('Robert Wilson', 'robertw', 'robertpass', 'robert.w@hexaware.com', '5432109876', '654 Maple Dr, Pune', 'Employee', '2021-05-30', 'Male'),
('Jennifer Lee', 'jenniferl', 'jennpass', 'jennifer.l@hexaware.com', '4321098765', '987 Cedar Ln, Chennai', 'Employee', '2020-08-12', 'Female'),
('David Miller', 'davidm', 'davidpass', 'david.m@hexaware.com', '3210987654', '159 Birch Blvd, Kolkata', 'Employee', '2021-01-25', 'Male'),
('Lisa Taylor', 'lisat', 'lisapass', 'lisa.t@hexaware.com', '2109876543', '753 Spruce Way, Gurgaon', 'Manager', '2019-07-14', 'Female'),
('James Anderson', 'jamesa', 'jamespass', 'james.a@hexaware.com', '1098765432', '456 Willow Ct, Noida', 'Employee', '2020-10-08', 'Male'),
('Patricia Thomas', 'patriciat', 'patpass', 'patricia.t@hexaware.com', '9876543211', '852 Palm Ave, Bangalore', 'Admin', '2021-03-17', 'Female');


INSERT INTO asset (asset_name, asset_category, asset_model, manufacturing_date, expiry_date, asset_value, status, description) VALUES
('Dell Latitude 5420', 'Laptop', 'Latitude 5420', '2021-01-15', '2024-01-15', 1200.00, 'Available', '14-inch business laptop with 16GB RAM'),
('HP EliteBook 840', 'Laptop', 'EliteBook 840 G7', '2021-03-10', '2024-03-10', 1350.00, 'Assigned', '14-inch corporate laptop with fingerprint reader'),
('Lenovo ThinkPad X1', 'Laptop', 'ThinkPad X1 Carbon', '2021-02-20', '2024-02-20', 1500.00, 'Available', 'Ultra-light business laptop with 512GB SSD'),
('MacBook Pro 13"', 'Laptop', 'MacBook Pro M1', '2021-04-05', '2024-04-05', 1800.00, 'Under Maintenance', 'Apple laptop with M1 chip, 8-core CPU'),
('Dell OptiPlex 7080', 'Desktop', 'OptiPlex 7080', '2021-01-30', '2024-01-30', 900.00, 'Available', 'Compact desktop with Intel Core i7'),
('HP ProDesk 600', 'Desktop', 'ProDesk 600 G5', '2021-03-15', '2024-03-15', 850.00, 'Assigned', 'Small form factor business desktop'),
('Apple iMac 24"', 'Desktop', 'iMac M1', '2021-05-20', '2024-05-20', 2000.00, 'Available', 'All-in-one desktop with 4.5K display'),
('Logitech MX Master 3', 'Mouse', 'MX Master 3', '2021-02-10', '2023-02-10', 99.99, 'Available', 'Advanced wireless mouse for productivity'),
('Dell Wireless Keyboard', 'Keyboard', 'KB522', '2021-01-25', '2023-01-25', 49.99, 'Assigned', 'Full-size wireless keyboard'),
('Samsung 27" Monitor', 'Monitor', 'S27A600', '2021-03-01', '2024-03-01', 299.99, 'Available', 'QHD 2560x1440 IPS monitor');


INSERT INTO asset_request (employee_id, asset_id, request_date, status, request_type, description, issue_type) VALUES
(1, 1, '2023-01-10', 'Approved', 'Borrow', 'Need for new project development', NULL),
(2, 2, '2023-01-12', 'Approved', 'Borrow', 'Replacement for faulty laptop', NULL),
(3, 6, '2023-01-15', 'Approved', 'Borrow', 'New workstation required', NULL),
(4, 9, '2023-01-18', 'Approved', 'Borrow', 'Additional keyboard needed', NULL),
(5, 3, '2023-01-20', 'Pending', 'Borrow', 'Need laptop for client demo', NULL),
(6, 7, '2023-01-22', 'Pending', 'Borrow', 'Require iMac for design work', NULL),
(1, 1, '2023-02-25', 'Pending', 'Service', 'Keyboard keys not working properly', 'Repair'),
(2, 2, '2023-02-28', 'Pending', 'Service', 'Battery draining too fast', 'Malfunction'),
(4, 9, '2023-03-01', 'Pending', 'Return', 'No longer needed', NULL),
(3, 6, '2023-03-05', 'Pending', 'Service', 'System crashes frequently', 'Malfunction');

select * from asset_request;
