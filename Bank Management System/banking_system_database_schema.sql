-- Create the Database
CREATE DATABASE IF NOT EXISTS banking_system;

-- Use the Database
USE banking_system;

-- Create the 'user' Table
CREATE TABLE IF NOT EXISTS user (
    full_name VARCHAR(255) NOT NULL,
    email VARCHAR(255) PRIMARY KEY,
    password VARCHAR(255) NOT NULL
);

-- Create the 'accounts' Table
CREATE TABLE IF NOT EXISTS accounts (
    account_number BIGINT PRIMARY KEY,
    full_name VARCHAR(255),
    email VARCHAR(255),
    balance DECIMAL(10, 2),
    security_pin CHAR(4)
);
