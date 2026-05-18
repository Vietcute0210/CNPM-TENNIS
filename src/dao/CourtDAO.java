package dao;

import model.Court;
import model.CourtChain;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.List;

public class CourtDAO extends DAO {
    public CourtDAO() {
        super();
    }

    public List<Court> searchFreeCourt(java.util.Date startDate, java.util.Date endDate, String daysOfWeek, String timeSlot) {
        List<Court> list = new ArrayList<>();
        
        // Chuyển đổi Date sang LocalDate để tính toán số buổi
        LocalDate start = new java.sql.Date(startDate.getTime()).toLocalDate();
        LocalDate end = new java.sql.Date(endDate.getTime()).toLocalDate();
        
        // Tính tổng số buổi lý thuyết yêu cầu
        int totalRequestedSessions = calculateTotalSessions(start, end, daysOfWeek);
        
        // Query cơ bản: Lấy tất cả các sân
        // Trong thực tế, cần left join với bảng BookedCourt và BookingSession 
        // để lọc các sân bị trùng lịch. Để đơn giản cho chức năng demo, 
        // giả sử chúng ta tìm sân chưa bị đặt trùng toàn bộ khoảng thời gian.
        String sql = "SELECT c.*, cc.name AS chainName, cc.address AS chainAddress "
                   + "FROM tblCourt c "
                   + "JOIN tblCourtChain cc ON c.courtChainId = cc.id";
                   
        try {
            PreparedStatement ps = con.prepareStatement(sql);
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                CourtChain cc = new CourtChain(rs.getInt("courtChainId"), rs.getString("chainName"), rs.getString("chainAddress"));
                Court court = new Court();
                court.setId(rs.getInt("id"));
                court.setName(rs.getString("name"));
                court.setType(rs.getString("type"));
                court.setPrice(rs.getDouble("price"));
                court.setDescription(rs.getString("description"));
                court.setCourtChain(cc);
                
                // Demo logic: Check collision in tblBookingSession
                int bookedCount = getBookedSessionCount(court.getId(), startDate, endDate, daysOfWeek, timeSlot);
                int availableCount = totalRequestedSessions - bookedCount;
                
                if (availableCount > 0) {
                    court.setAvailableSessionsCount(availableCount);
                    list.add(court);
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return list;
    }
    
    private int getBookedSessionCount(int courtId, java.util.Date startDate, java.util.Date endDate, String daysOfWeek, String timeSlot) {
        int count = 0;
        String[] times = timeSlot.split(" - ");
        if(times.length != 2) return 0;
        String sql = "SELECT COUNT(*) FROM tblBookingSession s "
                   + "JOIN tblBookedCourt bc ON s.bookedCourtId = bc.id "
                   + "WHERE bc.courtId = ? AND s.sessionDate >= ? AND s.sessionDate <= ? "
                   + "AND s.startTime = ? AND s.endTime = ? AND s.status != 'Đã hủy'";
        try {
            PreparedStatement ps = con.prepareStatement(sql);
            ps.setInt(1, courtId);
            ps.setDate(2, new java.sql.Date(startDate.getTime()));
            ps.setDate(3, new java.sql.Date(endDate.getTime()));
            ps.setTime(4, java.sql.Time.valueOf(times[0].trim() + ":00"));
            ps.setTime(5, java.sql.Time.valueOf(times[1].trim() + ":00"));
            ResultSet rs = ps.executeQuery();
            if (rs.next()) {
                count = rs.getInt(1);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return count;
    }

    private int calculateTotalSessions(LocalDate start, LocalDate end, String daysOfWeek) {
        int count = 0;
        LocalDate current = start;
        while (!current.isAfter(end)) {
            String dayStr = getDayOfWeekString(current.getDayOfWeek().getValue());
            if (daysOfWeek.contains(dayStr)) {
                count++;
            }
            current = current.plusDays(1);
        }
        return count;
    }

    private String getDayOfWeekString(int javaDayOfWeek) {
        switch (javaDayOfWeek) {
            case 1: return "Thứ 2";
            case 2: return "Thứ 3";
            case 3: return "Thứ 4";
            case 4: return "Thứ 5";
            case 5: return "Thứ 6";
            case 6: return "Thứ 7";
            case 7: return "Chủ nhật";
            default: return "";
        }
    }
}
