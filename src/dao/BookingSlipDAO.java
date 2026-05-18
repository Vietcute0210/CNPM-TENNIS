package dao;

import model.BookedCourt;
import model.BookingSession;
import model.BookingSlip;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;
import java.sql.Timestamp;
import java.sql.Date;
import java.sql.Time;

public class BookingSlipDAO extends DAO {
    public BookingSlipDAO() {
        super();
    }

    public boolean addBookingSlip(BookingSlip bs) {
        boolean result = false;
        try {
            con.setAutoCommit(false); // Bắt đầu transaction
            
            // 1. Lưu BookingSlip
            String sqlSlip = "INSERT INTO tblBookingSlip (bookingDate, totalAmount, selloff, clientId, userId) VALUES (?, ?, ?, ?, ?)";
            PreparedStatement psSlip = con.prepareStatement(sqlSlip, Statement.RETURN_GENERATED_KEYS);
            psSlip.setTimestamp(1, Timestamp.valueOf(bs.getBookingDate()));
            psSlip.setDouble(2, bs.getTotalAmount());
            psSlip.setDouble(3, bs.getSelloff());
            psSlip.setInt(4, bs.getClient().getId());
            psSlip.setInt(5, bs.getUser().getId());
            psSlip.executeUpdate();
            
            ResultSet rsSlip = psSlip.getGeneratedKeys();
            if (rsSlip.next()) {
                bs.setId(rsSlip.getInt(1));
                
                // 2. Lưu các BookedCourt
                for (BookedCourt bc : bs.getBookedCourts()) {
                    String sqlCourt = "INSERT INTO tblBookedCourt (startDate, endDate, daysOfWeek, timeSlot, price, bookingSlipId, courtId) VALUES (?, ?, ?, ?, ?, ?, ?)";
                    PreparedStatement psCourt = con.prepareStatement(sqlCourt, Statement.RETURN_GENERATED_KEYS);
                    psCourt.setDate(1, Date.valueOf(bc.getStartDate()));
                    psCourt.setDate(2, Date.valueOf(bc.getEndDate()));
                    psCourt.setString(3, bc.getDaysOfWeek());
                    psCourt.setString(4, bc.getTimeSlot());
                    psCourt.setDouble(5, bc.getPrice());
                    psCourt.setInt(6, bs.getId());
                    psCourt.setInt(7, bc.getCourt().getId());
                    psCourt.executeUpdate();
                    
                    ResultSet rsCourt = psCourt.getGeneratedKeys();
                    if (rsCourt.next()) {
                        bc.setId(rsCourt.getInt(1));
                        
                        // 3. Lưu các BookingSession
                        for (BookingSession session : bc.getSessions()) {
                            String sqlSession = "INSERT INTO tblBookingSession (sessionDate, startTime, endTime, status, bookedCourtId) VALUES (?, ?, ?, ?, ?)";
                            PreparedStatement psSession = con.prepareStatement(sqlSession);
                            psSession.setDate(1, Date.valueOf(session.getSessionDate()));
                            psSession.setTime(2, Time.valueOf(session.getStartTime()));
                            psSession.setTime(3, Time.valueOf(session.getEndTime()));
                            psSession.setString(4, session.getStatus());
                            psSession.setInt(5, bc.getId());
                            psSession.executeUpdate();
                        }
                    }
                }
            }
            con.commit(); // Hoàn thành transaction
            result = true;
        } catch (Exception e) {
            try {
                con.rollback(); // Rollback nếu có lỗi
            } catch (Exception ex) {
                ex.printStackTrace();
            }
            e.printStackTrace();
        } finally {
            try {
                con.setAutoCommit(true);
            } catch (Exception ex) {
                ex.printStackTrace();
            }
        }
        return result;
    }
}
