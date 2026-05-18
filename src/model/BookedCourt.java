package model;

import java.io.Serializable;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.List;

public class BookedCourt implements Serializable {
    private int id;
    private LocalDate startDate;
    private LocalDate endDate;
    private String daysOfWeek; // VD: "Thứ 3, Thứ 5"
    private String timeSlot; // VD: "19:00 - 21:00"
    private double price;
    private double sellOff;
    private Court court;
    private BookingSlip bookingSlip;
    private List<BookingSession> sessions;

    public BookedCourt() {
        super();
        sessions = new ArrayList<>();
    }

    public BookedCourt(LocalDate startDate, LocalDate endDate, String daysOfWeek, String timeSlot, double price, double sellOff, Court court, BookingSlip bookingSlip) {
        super();
        this.startDate = startDate;
        this.endDate = endDate;
        this.daysOfWeek = daysOfWeek;
        this.timeSlot = timeSlot;
        this.price = price;
        this.sellOff = sellOff;
        this.court = court;
        this.bookingSlip = bookingSlip;
        this.sessions = new ArrayList<>();
    }
    
    /**
     * Hàm xử lý nghiệp vụ tự động sinh danh sách các phiên đặt sân dựa vào 
     * khoảng thời gian (startDate, endDate), các ngày trong tuần (daysOfWeek)
     * và khung giờ (timeSlot).
     */
    public void generateSessions() {
        sessions.clear();
        if (startDate == null || endDate == null || daysOfWeek == null || timeSlot == null) {
            return;
        }
        
        String[] times = timeSlot.split(" - ");
        if (times.length != 2) return;
        
        LocalTime startTime = LocalTime.parse(times[0].trim());
        LocalTime endTime = LocalTime.parse(times[1].trim());
        
        LocalDate current = startDate;
        while (!current.isAfter(endDate)) {
            String dayOfWeekStr = getDayOfWeekString(current.getDayOfWeek().getValue());
            if (daysOfWeek.contains(dayOfWeekStr)) {
                BookingSession session = new BookingSession(current, startTime, endTime, "Đã đặt", this);
                sessions.add(session);
            }
            current = current.plusDays(1);
        }
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

    /**
     * Tính số giờ của mỗi buổi từ timeSlot. VD: "19:00 - 21:00" -> 2.0
     */
    public double getDurationHours() {
        if (timeSlot == null) return 0;
        String[] parts = timeSlot.split(" - ");
        if (parts.length != 2) return 0;
        try {
            LocalTime start = LocalTime.parse(parts[0].trim());
            LocalTime end   = LocalTime.parse(parts[1].trim());
            return java.time.Duration.between(start, end).toMinutes() / 60.0;
        } catch (Exception e) {
            return 0;
        }
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public LocalDate getStartDate() {
        return startDate;
    }

    public void setStartDate(LocalDate startDate) {
        this.startDate = startDate;
    }

    public LocalDate getEndDate() {
        return endDate;
    }

    public void setEndDate(LocalDate endDate) {
        this.endDate = endDate;
    }

    public String getDaysOfWeek() {
        return daysOfWeek;
    }

    public void setDaysOfWeek(String daysOfWeek) {
        this.daysOfWeek = daysOfWeek;
    }

    public String getTimeSlot() {
        return timeSlot;
    }

    public void setTimeSlot(String timeSlot) {
        this.timeSlot = timeSlot;
    }

    public double getPrice() {
        return price;
    }

    public void setPrice(double price) {
        this.price = price;
    }

    public double getSellOff() {
        return sellOff;
    }

    public void setSellOff(double sellOff) {
        this.sellOff = sellOff;
    }

    public Court getCourt() {
        return court;
    }

    public void setCourt(Court court) {
        this.court = court;
    }

    public BookingSlip getBookingSlip() {
        return bookingSlip;
    }

    public void setBookingSlip(BookingSlip bookingSlip) {
        this.bookingSlip = bookingSlip;
    }

    public List<BookingSession> getSessions() {
        return sessions;
    }

    public void setSessions(List<BookingSession> sessions) {
        this.sessions = sessions;
    }
}
