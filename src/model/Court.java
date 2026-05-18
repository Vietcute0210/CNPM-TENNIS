package model;

import java.io.Serializable;

public class Court implements Serializable {
    private int id;
    private String name;
    private String type;
    private double price;
    private String description;
    private CourtChain courtChain;
    
    // Thuộc tính bổ sung để chứa thông tin hiển thị (VD: Tổng số buổi)
    private int availableSessionsCount;

    public Court() {
        super();
    }

    public Court(int id, String name, String type, double price, String description, CourtChain courtChain) {
        super();
        this.id = id;
        this.name = name;
        this.type = type;
        this.price = price;
        this.description = description;
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

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
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
