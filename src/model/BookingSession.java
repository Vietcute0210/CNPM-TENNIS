package model;

import java.io.Serializable;
import java.time.LocalDate;
import java.time.LocalTime;

public class BookingSession implements Serializable {
    private int id;
    private LocalDate date;
    private LocalTime startTime;
    private LocalTime endTime;
    private String status; // VD: "Đã hoàn thành", "Đã đặt", "Đã hủy"
    private BookedCourt bookedCourt;

    public BookingSession() {
        super();
    }

    public BookingSession(LocalDate date, LocalTime startTime, LocalTime endTime, String status, BookedCourt bookedCourt) {
        super();
        this.date = date;
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

    public LocalDate getDate() {
        return date;
    }

    public void setDate(LocalDate date) {
        this.date = date;
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
