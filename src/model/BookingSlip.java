package model;

import java.io.Serializable;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

public class BookingSlip implements Serializable {
    private int id;
    private LocalDate bookingDay;
    private double selloff;
    private String note;
    private Client client;
    private User user;
    private List<BookedCourt> bookedCourts;

    public BookingSlip() {
        super();
        bookedCourts = new ArrayList<>();
    }

    public BookingSlip(LocalDate bookingDay, double selloff, String note, Client client, User user) {
        super();
        this.bookingDay = bookingDay;
        this.selloff = selloff;
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

    /**
     * Tính tổng tiền động:
     * Mỗi BookedCourt = price/h * số_giờ_mỗi_buổi * số_buổi * (1 - sellOff_sân)
     * Tổng cuối = sum * (1 - selloff_phiếu)
     */
    public double getTotalAmount() {
        double total = 0;
        if (bookedCourts != null) {
            for (BookedCourt bc : bookedCourts) {
                double hours = bc.getDurationHours();           // số giờ/buổi
                int    sessions = bc.getSessions().size();       // số buổi
                // Nếu sân đã được gán sellOff (ví dụ khi load từ DB), ta dùng chính nó.
                // Nếu chưa gán (ví dụ lúc đang tạo mới), ta lấy theo khuyến mại chung của phiếu.
                double discount = (bc.getSellOff() > 0) ? bc.getSellOff() : this.getSelloff();
                double pricePerCourt = bc.getPrice() * hours * sessions * (1 - discount);
                total += pricePerCourt;
            }
        }
        return total;
    }

    /**
     * Tính tiền đặt cọc (mặc định là 10% của tổng tiền sau khi đã giảm giá)
     */
    public double getDeposit() {
        return getTotalAmount() * 0.10;
    }

    public String getNote() {
        return note;
    }

    public void setNote(String note) {
        this.note = note;
    }

    /**
     * Tính tỷ lệ khuyến mại (selloff) tự động dựa trên thời gian thuê của các sân:
     * - Thuê từ 3 tháng trở lên (Theo Quý): giảm 15% (0.15)
     * - Thuê từ 1 tháng trở lên (Theo Tháng): giảm 10% (0.10)
     * - Thuê lẻ tẻ dưới 1 tháng: giảm 0% (0.00)
     */
    public double getDiscountRate() {
        if (bookedCourts == null || bookedCourts.isEmpty()) {
            return 0.0;
        }
        // Lấy sân đầu tiên để đại diện (do các sân được đặt cùng khoảng thời gian trong luồng)
        BookedCourt bc = bookedCourts.get(0);
        if (bc.getStartDate() == null || bc.getEndDate() == null) {
            return 0.0;
        }
        
        // Tính số tháng thuê dương lịch bao phủ
        long months = java.time.temporal.ChronoUnit.MONTHS.between(bc.getStartDate(), bc.getEndDate().plusDays(1));
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

    public void addBookedCourt(BookedCourt bc) {
        if (this.bookedCourts == null) {
            this.bookedCourts = new ArrayList<>();
        }
        this.bookedCourts.add(bc);
    }
}
