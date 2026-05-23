package model;

import java.io.Serializable;

public class Client implements Serializable {
    private int id;
    private String name;
    private String tel;
    private String email;
    private String address;
    private String note;

    private int bookingCount;

    public Client() {
        super();
    }

    public Client(int id, String name, String tel, String email, String address, String note) {
        super();
        this.id = id;
        this.name = name;
        this.tel = tel;
        this.email = email;
        this.address = address;
        this.note = note;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getTel() {
        return tel;
    }

    public void setTel(String tel) {
        this.tel = tel;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getNote() {
        return note;
    }

    public void setNote(String note) {
        this.note = note;
    }

    public int getBookingCount() {
        return bookingCount;
    }

    public void setBookingCount(int bookingCount) {
        this.bookingCount = bookingCount;
    }
}
