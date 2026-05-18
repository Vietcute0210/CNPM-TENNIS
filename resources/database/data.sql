USE db_tennis_booking;

-- Mock data for tblUser
INSERT INTO tblUser (username, password, fullName, role) VALUES 
('A', 'A@123', 'Nhân viên lễ tân A', 'Receptionist'),
('admin', '123456', 'Quản trị viên', 'Admin'),
('manager1', 'manager123', 'Quản lý 1', 'Manager'),
('recept2', '123456', 'Nhân viên lễ tân 2', 'Receptionist'),
('recept3', '123456', 'Nhân viên lễ tân 3', 'Receptionist');

-- Mock data for tblClient
INSERT INTO tblClient (name, tel, email, address, note) VALUES 
('B', '123456789', 'B@gmail.com', 'Hà Nội', ''),
('Bảo', '987654321', 'bao@gmail.com', 'Hà Nội', 'VIP'),
('Cường', '0912345678', 'cuong@gmail.com', 'Hải Phòng', ''),
('Dũng', '0987654321', 'dung@gmail.com', 'Đà Nẵng', ''),
('Em', '0123456789', 'em@gmail.com', 'Hồ Chí Minh', ''),
('Phong', '0999888777', 'phong@gmail.com', 'Cần Thơ', '');

-- Mock data for tblCourtChain
INSERT INTO tblCourtChain (name, address) VALUES 
('Chuỗi sân Tennis Hà Nội', 'Cầu Giấy, Hà Nội'),
('Chuỗi sân Tennis HCM', 'Quận 1, Hồ Chí Minh'),
('Chuỗi sân Tennis Đà Nẵng', 'Hải Châu, Đà Nẵng'),
('Chuỗi sân Tennis VVIP', 'Tây Hồ, Hà Nội'),
('Chuỗi sân Phố Cổ', 'Hoàn Kiếm, Hà Nội');

-- Mock data for tblCourt
INSERT INTO tblCourt (name, type, price, description, courtChainId) VALUES 
('M01', 'Mini', 150000, 'Trong nhà', 1),
('M02', 'Ghép 2 sân mini', 300000, 'Trong nhà', 1),
('M03', 'Ghép 4 sân mini', 500000, 'Ngoài trời', 1),
('M04', 'Mini', 150000, 'Trong nhà', 1),
('M05', 'Tiêu chuẩn', 200000, 'Ngoài trời', 2),
('M06', 'Tiêu chuẩn', 200000, 'Trong nhà', 2),
('M07', 'VIP', 1000000, 'Trong nhà máy lạnh', 4);

-- Mock data for tblBookingSlip
INSERT INTO tblBookingSlip (bookingDate, totalAmount, selloff, clientId, userId) VALUES 
('2026-01-10 10:00:00', 300000, 0, 1, 1),
('2026-01-15 15:30:00', 600000, 10, 2, 1),
('2026-02-01 08:00:00', 1000000, 0, 3, 2),
('2026-02-14 09:15:00', 500000, 5, 4, 1),
('2026-02-20 14:00:00', 150000, 0, 5, 2);

-- Mock data for tblBookedCourt
INSERT INTO tblBookedCourt (startDate, endDate, daysOfWeek, timeSlot, price, bookingSlipId, courtId) VALUES 
('2026-01-11', '2026-01-11', 'Thứ 2', '08:00 - 10:00', 150000, 1, 1),
('2026-01-16', '2026-01-16', 'Thứ 6', '19:00 - 21:00', 300000, 2, 2),
('2026-02-02', '2026-02-03', 'Thứ 3, Thứ 4', '06:00 - 08:00', 500000, 3, 3),
('2026-02-15', '2026-02-15', 'Thứ 7', '18:00 - 20:00', 500000, 4, 3),
('2026-02-21', '2026-02-21', 'Chủ nhật', '09:00 - 10:00', 150000, 5, 4);

-- Mock data for tblBookingSession
INSERT INTO tblBookingSession (sessionDate, startTime, endTime, status, bookedCourtId) VALUES 
('2026-01-11', '08:00:00', '10:00:00', 'Đã hoàn thành', 1),
('2026-01-16', '19:00:00', '21:00:00', 'Đã hoàn thành', 2),
('2026-02-02', '06:00:00', '08:00:00', 'Đã hoàn thành', 3),
('2026-02-03', '06:00:00', '08:00:00', 'Đã hoàn thành', 3),
('2026-02-15', '18:00:00', '20:00:00', 'Đã hoàn thành', 4),
('2026-02-21', '09:00:00', '10:00:00', 'Đã hoàn thành', 5);

-- Mock data for tblDepositBill
INSERT INTO tblDepositBill (amount, paymentMethod, paymentDate, bookingSlipId, userId) VALUES 
(30000, 'Tiền mặt', '2026-01-10 10:05:00', 1, 1),
(60000, 'Chuyển khoản', '2026-01-15 15:35:00', 2, 1),
(100000, 'Thẻ tín dụng', '2026-02-01 08:05:00', 3, 2),
(50000, 'Tiền mặt', '2026-02-14 09:20:00', 4, 1),
(15000, 'Chuyển khoản', '2026-02-20 14:05:00', 5, 2);

-- Pre-fill a booked court for 31/03/2026 for Court M01 to test the scenario where 1 session is already booked out of 61
INSERT INTO tblBookingSlip (bookingDate, totalAmount, selloff, clientId, userId) VALUES ('2026-01-01 10:00:00', 300000, 0, 2, 1);
INSERT INTO tblBookedCourt (startDate, endDate, daysOfWeek, timeSlot, price, bookingSlipId, courtId) VALUES ('2026-03-31', '2026-03-31', 'Thứ 3', '19:00 - 21:00', 150000, 6, 1);
INSERT INTO tblBookingSession (sessionDate, startTime, endTime, status, bookedCourtId) VALUES ('2026-03-31', '19:00:00', '21:00:00', 'Đã đặt', 6);
