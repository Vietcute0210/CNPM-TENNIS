package model;

import java.io.Serializable;
import java.time.LocalDateTime;

public class DepositBill implements Serializable {
    private int id;
    private double amount;
    private String paymentMethod;
    private LocalDateTime paymentDate;
    private BookingSlip bookingSlip;
    private User user;

    public DepositBill() {
        super();
    }

    public DepositBill(double amount, String paymentMethod, LocalDateTime paymentDate, BookingSlip bookingSlip, User user) {
        super();
        this.amount = amount;
        this.paymentMethod = paymentMethod;
        this.paymentDate = paymentDate;
        this.bookingSlip = bookingSlip;
        this.user = user;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public double getAmount() {
        return amount;
    }

    public void setAmount(double amount) {
        this.amount = amount;
    }

    public String getPaymentMethod() {
        return paymentMethod;
    }

    public void setPaymentMethod(String paymentMethod) {
        this.paymentMethod = paymentMethod;
    }

    public LocalDateTime getPaymentDate() {
        return paymentDate;
    }

    public void setPaymentDate(LocalDateTime paymentDate) {
        this.paymentDate = paymentDate;
    }

    public BookingSlip getBookingSlip() {
        return bookingSlip;
    }

    public void setBookingSlip(BookingSlip bookingSlip) {
        this.bookingSlip = bookingSlip;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }
}
