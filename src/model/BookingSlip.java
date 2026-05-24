package model;

import java.io.Serializable;
import java.time.LocalDate;
import java.time.temporal.ChronoUnit;
import java.util.ArrayList;
import java.util.List;

public class BookingSlip implements Serializable {
    private int id;
    private LocalDate bookingDay;
    private double sellOff;
    private String note;
    private Client client;
    private User user;
    private List<BookedCourt> bookedCourts;

    public BookingSlip() {
        super();
        bookedCourts = new ArrayList<>();
    }

    public BookingSlip(LocalDate bookingDay, double sellOff, String note, Client client, User user) {
        super();
        this.bookingDay = bookingDay;
        this.sellOff = sellOff;
        this.note = note;
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

    public LocalDate getBookingDay() {
        return bookingDay;
    }

    public void setBookingDay(LocalDate bookingDay) {
        this.bookingDay = bookingDay;
    }

    public double getTotalAmount() {
        double total = 0;
        if (bookedCourts != null) {
            for (BookedCourt bc : bookedCourts) {
                double hours = bc.getDurationHours();
                int    sessions = bc.getSessions().size();
                double discount = (bc.getSellOff() > 0) ? bc.getSellOff() : this.getSelloff();
                double pricePerCourt = bc.getPrice() * hours * sessions * (1 - discount);
                total += pricePerCourt;
            }
        }
        return total;
    }

    public double getDeposit() {
        return getTotalAmount() * 0.10;
    }

    public String getNote() {
        return note;
    }

    public void setNote(String note) {
        this.note = note;
    }

    public double getDiscountRate() {
        if (bookedCourts == null || bookedCourts.isEmpty()) {
            return 0.0;
        }
        // Lấy 1 sân đầu để đại diện
        BookedCourt bc = bookedCourts.get(0);

        long months = ChronoUnit.MONTHS.between(bc.getStartDate(), bc.getEndDate().plusDays(1));
        if (months >= 3) {
            return 0.15;
        } else if (months >= 1) {
            return 0.10;
        } else {
            return 0.0;
        }
    }

    public double getSelloff() {
        return getDiscountRate();
    }

    public void setSelloff(double selloff) {
        this.sellOff = selloff;
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

    public void addBookedCourt(BookedCourt bc) {
        if (this.bookedCourts == null) {
            this.bookedCourts = new ArrayList<>();
        }
        this.bookedCourts.add(bc);
    }
}
