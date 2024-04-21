-- Create the hotel_db database
CREATE DATABASE IF NOT EXISTS hotel_db;

-- Switch to the hotel_db database
USE hotel_db;

-- Table to store users
CREATE TABLE IF NOT EXISTS users (
    user_id INT AUTO_INCREMENT PRIMARY KEY,
    name VARCHAR(100) NOT NULL,
    contact_number VARCHAR(20),
    nid_number VARCHAR(20),
    permanent_address VARCHAR(255)
);

-- Table to store rooms
CREATE TABLE IF NOT EXISTS rooms (
    room_id INT AUTO_INCREMENT PRIMARY KEY,
    room_number INT,
    floor_number INT,
    category VARCHAR(1),
    room_type VARCHAR(10)
);

-- Table to store reservations
CREATE TABLE IF NOT EXISTS reservations (
    reservation_id INT AUTO_INCREMENT PRIMARY KEY,
    user_id INT,
    room_id INT,
    booking_date DATE,
    booking_duration INT,
    payment_status VARCHAR(50),
    bill_to_pay DECIMAL(10, 2),
    booking_time TIME,
    FOREIGN KEY (user_id) REFERENCES users(user_id),
    FOREIGN KEY (room_id) REFERENCES rooms(room_id)
);

-- Table to store payments
CREATE TABLE IF NOT EXISTS payments (
    payment_id INT AUTO_INCREMENT PRIMARY KEY,
    reservation_id INT,
    payment_status VARCHAR(50),
    bill_to_pay DECIMAL(10, 2),
    FOREIGN KEY (reservation_id) REFERENCES reservations(reservation_id)
);
