CREATE DATABASE IF NOT EXISTS db_tennis_booking;
USE db_tennis_booking;

CREATE TABLE tblUser (
    id INT AUTO_INCREMENT PRIMARY KEY,
    username VARCHAR(50) NOT NULL UNIQUE,
    password VARCHAR(50) NOT NULL,
    fullName VARCHAR(100),
    role VARCHAR(50)
);

CREATE TABLE tblClient (
    id INT AUTO_INCREMENT PRIMARY KEY,
    name VARCHAR(100) NOT NULL,
    tel VARCHAR(20),
    email VARCHAR(100),
    address VARCHAR(255),
    note TEXT
);

CREATE TABLE tblCourtChain (
    id INT AUTO_INCREMENT PRIMARY KEY,
    name VARCHAR(100) NOT NULL,
    address VARCHAR(255)
);

CREATE TABLE tblCourt (
    id INT AUTO_INCREMENT PRIMARY KEY,
    name VARCHAR(50) NOT NULL,
    type VARCHAR(50),
    price DOUBLE,
    description TEXT,
    courtChainId INT,
    FOREIGN KEY (courtChainId) REFERENCES tblCourtChain(id)
);

CREATE TABLE tblBookingSlip (
    id INT AUTO_INCREMENT PRIMARY KEY,
    bookingDate DATETIME,
    totalAmount DOUBLE,
    selloff DOUBLE,
    clientId INT,
    userId INT,
    FOREIGN KEY (clientId) REFERENCES tblClient(id),
    FOREIGN KEY (userId) REFERENCES tblUser(id)
);

CREATE TABLE tblBookedCourt (
    id INT AUTO_INCREMENT PRIMARY KEY,
    startDate DATE,
    endDate DATE,
    daysOfWeek VARCHAR(50),
    timeSlot VARCHAR(50),
    price DOUBLE,
    bookingSlipId INT,
    courtId INT,
    FOREIGN KEY (bookingSlipId) REFERENCES tblBookingSlip(id),
    FOREIGN KEY (courtId) REFERENCES tblCourt(id)
);

CREATE TABLE tblBookingSession (
    id INT AUTO_INCREMENT PRIMARY KEY,
    sessionDate DATE,
    startTime TIME,
    endTime TIME,
    status VARCHAR(50),
    bookedCourtId INT,
    FOREIGN KEY (bookedCourtId) REFERENCES tblBookedCourt(id)
);

CREATE TABLE tblDepositBill (
    id INT AUTO_INCREMENT PRIMARY KEY,
    amount DOUBLE,
    paymentMethod VARCHAR(50),
    paymentDate DATETIME,
    bookingSlipId INT,
    userId INT,
    FOREIGN KEY (bookingSlipId) REFERENCES tblBookingSlip(id),
    FOREIGN KEY (userId) REFERENCES tblUser(id)
);
