package model;

import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

public class BookingSlip implements Serializable {
    private int id;
    private LocalDateTime bookingDate;
    private double totalAmount;
    private double selloff;
    private Client client;
    private User user;
    private List<BookedCourt> bookedCourts;

    public BookingSlip() {
        super();
        bookedCourts = new ArrayList<>();
    }

    public BookingSlip(LocalDateTime bookingDate, double totalAmount, double selloff, Client client, User user) {
        super();
        this.bookingDate = bookingDate;
        this.totalAmount = totalAmount;
        this.selloff = selloff;
        this.client = client;
        this.user = user;
        this.bookedCourts = new ArrayList<>();
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public LocalDateTime getBookingDate() {
        return bookingDate;
    }

    public void setBookingDate(LocalDateTime bookingDate) {
        this.bookingDate = bookingDate;
    }

    public double getTotalAmount() {
        return totalAmount;
    }

    public void setTotalAmount(double totalAmount) {
        this.totalAmount = totalAmount;
    }

    public double getSelloff() {
        return selloff;
    }

    public void setSelloff(double selloff) {
        this.selloff = selloff;
    }

    public Client getClient() {
        return client;
    }

    public void setClient(Client client) {
        this.client = client;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public List<BookedCourt> getBookedCourts() {
        return bookedCourts;
    }

    public void setBookedCourts(List<BookedCourt> bookedCourts) {
        this.bookedCourts = bookedCourts;
    }
}
