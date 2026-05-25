USE db_tennis_booking;

-- tblUser
INSERT INTO tblUser (username, password, fullName, role) VALUES 
('A', 'A@123', 'Nhân viên lễ tân A', 'Lễ tân'),
('admin', '123456', 'Quản trị viên', 'Admin'),
('manager1', 'manager123', 'Quản lý 1', 'Quản lý');

-- tblClient
INSERT INTO tblClient (name, address, tel, email, note) VALUES 
('B', 'Hà Nội', '123456789', 'B@gmail.com', ''),
('Bảo', 'Hà Nội', '987654321', 'bao@gmail.com', 'VIP'),
('Cường', 'Hải Phòng', '0912345678', 'cuong@gmail.com', '');

-- tblCourtChain
INSERT INTO tblCourtChain (name, address, description) VALUES 
('Chuỗi sân Thăng Long', 'Số 10 Hoàng Quốc Việt, Hà Nội', 'Hệ thống tennis tiêu chuẩn'),
('Chuỗi sân HCM', 'Quận 1, Hồ Chí Minh', '');

-- tblCourt
INSERT INTO tblCourt (name, price, description, status, tblCourtChainID) VALUES 
('Sân Mini 01', 150000, 'Sân mini tiêu chuẩn trong nhà', 'Đang hoạt động', 1),
('Sân Ghép 2', 300000, '2 sân mini ghép lại trong nhà', 'Đang hoạt động', 1),
('Sân Ghép 4', 500000, '4 sân mini ghép lại ngoài trời', 'Đang hoạt động', 1),
('Sân Mini 02', 150000, 'Sân mini tiêu chuẩn ngoài trời', 'Đang hoạt động', 1);

-- tblBookingSlip
INSERT INTO tblBookingSlip (bookingDay, sellOff, note, tblClientID, tblUserID) VALUES 
('2026-03-01', 0.0, '', 1, 1),
('2026-05-11', 0.15, 'Khách đặt quý', 1, 1);

-- tblBookedCourt
INSERT INTO tblBookedCourt (startDate, endDate, price, sellOff, daysOfWeek, timeSlot, tblCourtID, tblBookingSlipID) VALUES 
('2026-03-02', '2026-03-30', 300000, 0.0, 'Thứ 2, Thứ 4', '07:00 - 09:00', 2, 1),
('2026-06-01', '2026-09-30', 150000, 0.15, 'Thứ 3, Thứ 5', '19:00 - 21:00', 1, 2);

-- tblBookingSession
INSERT INTO tblBookingSession (date, startTime, endTime, status, tblBookedCourtID) VALUES 
('2026-03-02', '07:00:00', '09:00:00', 'Đã check-in', 1),
('2026-03-04', '07:00:00', '09:00:00', 'Đã check-out', 1),
('2026-06-02', '19:00:00', '21:00:00', 'Đã đặt', 2),
('2026-06-04', '19:00:00', '21:00:00', 'Đã đặt', 2);

-- tblDepositBill
INSERT INTO tblDepositBill (createdDate, deposit, paymentMethod, tblBookingSlipID) VALUES 
('2026-03-01', 540000, 'Tiền mặt', 1),
('2026-05-11', 892500, 'Chuyển khoản', 2);
