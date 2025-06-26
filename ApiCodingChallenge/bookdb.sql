CREATE DATABASE bookdb;
USE bookdb;


CREATE TABLE book (
    id BIGINT AUTO_INCREMENT PRIMARY KEY,
    title VARCHAR(255),
    author VARCHAR(255),
    isbn VARCHAR(20) UNIQUE,
    publication_year INT
);


INSERT INTO book (title, author, isbn, publication_year) VALUES
('The Alchemist', 'Paulo Coelho', '1234567890', 1988),
('1984', 'George Orwell', '9876543210', 1949);


CREATE TABLE user (
    id BIGINT AUTO_INCREMENT PRIMARY KEY,
    username VARCHAR(100) UNIQUE,
    password VARCHAR(100)
);
select * from user;

INSERT INTO user (username, password) VALUES
('admin', '{noop}admin123'),
('user', '{noop}user123');
