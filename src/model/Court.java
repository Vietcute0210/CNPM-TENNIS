package model;

import java.io.Serializable;

public class Court implements Serializable {
    private int id;
    private String name;
    private double price;
    private String description;
    private String status;
    private CourtChain courtChain;

    private int availableSessionsCount;

    public Court() {
        super();
    }

    public Court(int id, String name, double price, String description, String status, CourtChain courtChain) {
        super();
        this.id = id;
        this.name = name;
        this.price = price;
        this.description = description;
        this.status = status;
        this.courtChain = courtChain;
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

    public double getPrice() {
        return price;
    }

    public void setPrice(double price) {
        this.price = price;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public CourtChain getCourtChain() {
        return courtChain;
    }

    public void setCourtChain(CourtChain courtChain) {
        this.courtChain = courtChain;
    }

    public int getAvailableSessionsCount() {
        return availableSessionsCount;
    }

    public void setAvailableSessionsCount(int availableSessionsCount) {
        this.availableSessionsCount = availableSessionsCount;
    }
}
