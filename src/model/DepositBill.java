package model;

import java.io.Serializable;
import java.time.LocalDate;

public class DepositBill implements Serializable {
    private int id;
    private double deposit;
    private String paymentMethod;
    private LocalDate createdDate;
    private BookingSlip bookingSlip;

    public DepositBill() {
        super();
    }

    public DepositBill(double deposit, String paymentMethod, LocalDate createdDate, BookingSlip bookingSlip) {
        super();
        this.deposit = deposit;
        this.paymentMethod = paymentMethod;
        this.createdDate = createdDate;
        this.bookingSlip = bookingSlip;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public double getDeposit() {
        return deposit;
    }

    public void setDeposit(double deposit) {
        this.deposit = deposit;
    }

    public String getPaymentMethod() {
        return paymentMethod;
    }

    public void setPaymentMethod(String paymentMethod) {
        this.paymentMethod = paymentMethod;
    }

    public LocalDate getCreatedDate() {
        return createdDate;
    }

    public void setCreatedDate(LocalDate createdDate) {
        this.createdDate = createdDate;
    }

    public BookingSlip getBookingSlip() {
        return bookingSlip;
    }

    public void setBookingSlip(BookingSlip bookingSlip) {
        this.bookingSlip = bookingSlip;
    }
}
