package model;

import java.io.Serializable;
import java.time.LocalDate;
import java.time.LocalTime;

public class BookingSession implements Serializable {
    private int id;
    private LocalDate sessionDate;
    private LocalTime startTime;
    private LocalTime endTime;
    private String status; // VD: "Đã hoàn thành", "Đã đặt", "Đã hủy"
    private BookedCourt bookedCourt;

    public BookingSession() {
        super();
    }

    public BookingSession(LocalDate sessionDate, LocalTime startTime, LocalTime endTime, String status, BookedCourt bookedCourt) {
        super();
        this.sessionDate = sessionDate;
        this.startTime = startTime;
        this.endTime = endTime;
        this.status = status;
        this.bookedCourt = bookedCourt;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public LocalDate getSessionDate() {
        return sessionDate;
    }

    public void setSessionDate(LocalDate sessionDate) {
        this.sessionDate = sessionDate;
    }

    public LocalTime getStartTime() {
        return startTime;
    }

    public void setStartTime(LocalTime startTime) {
        this.startTime = startTime;
    }

    public LocalTime getEndTime() {
        return endTime;
    }

    public void setEndTime(LocalTime endTime) {
        this.endTime = endTime;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public BookedCourt getBookedCourt() {
        return bookedCourt;
    }

    public void setBookedCourt(BookedCourt bookedCourt) {
        this.bookedCourt = bookedCourt;
    }
}
