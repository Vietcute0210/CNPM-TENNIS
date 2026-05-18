CREATE DATABASE IF NOT EXISTS db_tennis_booking;
USE db_tennis_booking;

CREATE TABLE tblUser (
    id INT AUTO_INCREMENT PRIMARY KEY,
    username VARCHAR(100) NOT NULL UNIQUE,
    password VARCHAR(100) NOT NULL,
    fullName VARCHAR(100),
    role VARCHAR(100)
);

CREATE TABLE tblClient (
    id INT AUTO_INCREMENT PRIMARY KEY,
    name VARCHAR(70) NOT NULL,
    address VARCHAR(100),
    tel VARCHAR(20),
    email VARCHAR(30),
    note VARCHAR(400)
);

CREATE TABLE tblCourtChain (
    id INT AUTO_INCREMENT PRIMARY KEY,
    name VARCHAR(70) NOT NULL,
    address VARCHAR(100),
    description VARCHAR(500)
);

CREATE TABLE tblCourt (
    id INT AUTO_INCREMENT PRIMARY KEY,
    name VARCHAR(70) NOT NULL,
    price FLOAT(10),
    description VARCHAR(500),
    status VARCHAR(50),
    tblCourtChainID INT,
    FOREIGN KEY (tblCourtChainID) REFERENCES tblCourtChain(id)
);

CREATE TABLE tblBookingSlip (
    id INT AUTO_INCREMENT PRIMARY KEY,
    bookingDay DATE,
    sellOff FLOAT(10),
    note VARCHAR(400),
    tblClientID INT,
    tblUserID INT,
    FOREIGN KEY (tblClientID) REFERENCES tblClient(id),
    FOREIGN KEY (tblUserID) REFERENCES tblUser(id)
);

CREATE TABLE tblBookedCourt (
    id INT AUTO_INCREMENT PRIMARY KEY,
    startDate DATE,
    endDate DATE,
    price FLOAT(10),
    sellOff FLOAT(10),
    daysOfWeek VARCHAR(40),
    timeSlot VARCHAR(40),
    tblCourtID INT,
    tblBookingSlipID INT,
    FOREIGN KEY (tblCourtID) REFERENCES tblCourt(id),
    FOREIGN KEY (tblBookingSlipID) REFERENCES tblBookingSlip(id)
);

CREATE TABLE tblBookingSession (
    id INT AUTO_INCREMENT PRIMARY KEY,
    date DATE,
    startTime TIME,
    endTime TIME,
    status VARCHAR(50),
    tblBookedCourtID INT,
    FOREIGN KEY (tblBookedCourtID) REFERENCES tblBookedCourt(id)
);

CREATE TABLE tblDepositBill (
    id INT AUTO_INCREMENT PRIMARY KEY,
    createdDate DATE,
    deposit FLOAT(10),
    paymentMethod VARCHAR(100),
    tblBookingSlipID INT,
    FOREIGN KEY (tblBookingSlipID) REFERENCES tblBookingSlip(id)
);
