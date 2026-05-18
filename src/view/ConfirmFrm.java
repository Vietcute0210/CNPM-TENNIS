package view;
import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import model.BookedCourt;
import model.BookingSlip;

public class ConfirmFrm extends JFrame {
    private JPanel mainPanel;
    private JTextArea taBookingSlip;
    private JButton btnCancel;
    private JButton btnConfirm;

    private BookingSlip bookingSlip;

    public ConfirmFrm(BookingSlip slip) {
        this.bookingSlip = slip;
        setContentPane(mainPanel);
        setTitle("ConfirmView");
        setSize(750, 500);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);

        if (taBookingSlip != null && slip != null) {
            StringBuilder sb = new StringBuilder();
            sb.append("===== BOOKING CONFIRMATION =====\n");
            sb.append(String.format("%-15s: %s\n", "Người tạo phiếu", slip.getUser() != null ? slip.getUser().getUsername() : "N/A"));
            sb.append(String.format("%-15s: %s - %s - %s - %s\n",
                    "Khách hàng",
                    slip.getClient() != null ? slip.getClient().getName() : "N/A",
                    slip.getClient() != null ? slip.getClient().getAddress() : "",
                    slip.getClient() != null ? slip.getClient().getTel() : "",
                    slip.getClient() != null ? slip.getClient().getEmail() : ""));
            sb.append("\nDanh sách sân đặt:\n");
            sb.append(String.format("%-3s %-15s %-12s %-12s %-12s %-10s %-10s\n",
                    "TT", "Sân", "Start Date", "End Date", "TimeSlot", "Buổi", "Giá/h"));
            sb.append("-".repeat(80)).append("\n");

            int stt = 1;
            int totalSessions = 0;
            for (BookedCourt bc : slip.getBookedCourts()) {
                int sessions = bc.getSessions().size();
                totalSessions += sessions;
                sb.append(String.format("%-3d %-15s %-12s %-12s %-12s %-10d %,.0fđ\n",
                        stt++,
                        bc.getCourt().getName(),
                        bc.getStartDate(),
                        bc.getEndDate(),
                        bc.getTimeSlot(),
                        sessions,
                        bc.getPrice()));
            }

            sb.append("-".repeat(80)).append("\n");
            sb.append(String.format("Tổng số buổi    : %d buổi\n", totalSessions));

            // Tính toán tài chính đúng:
            // grossPerCourt = price/h * hours * sessions (chưa có discount sân hay phiếu)
            double gross = 0;
            for (BookedCourt bc : slip.getBookedCourts()) {
                gross += bc.getPrice() * bc.getDurationHours() * bc.getSessions().size();
            }
            double slipDiscount = gross * slip.getSelloff();      // khuyến mại tổng phiếu
            double afterDiscount = gross - slipDiscount;           // thành tiền
            double deposit = slip.getDeposit();                    // cọc 10%

            sb.append(String.format("Tổng tiền gốc   : %,.0f đ\n", gross));
            sb.append(String.format("Khuyến mại (%.0f%%): %,.0f đ\n", slip.getSelloff() * 100, slipDiscount));
            sb.append(String.format("Thành tiền      : %,.0f đ\n", afterDiscount));
            sb.append(String.format("Cọc (10%%)       : %,.0f đ\n", deposit));
            sb.append("\nGhi chú: Nếu ngừng thuê giữa chừng, báo trước 2 tuần.\n");
            sb.append("================================");

            taBookingSlip.setText(sb.toString());
            taBookingSlip.setEditable(false);
            taBookingSlip.setFont(new java.awt.Font("Monospaced", java.awt.Font.PLAIN, 12));
        }

        btnConfirm.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                new PaymentFrm(bookingSlip);
                dispose();
            }
        });

        // Cancel -> quay về màn hình chính (exception 20)
        btnCancel.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                int choice = JOptionPane.showConfirmDialog(ConfirmFrm.this,
                        "Bạn có chắc muốn hủy đặt sân không?", "Xác nhận hủy",
                        JOptionPane.YES_NO_OPTION, JOptionPane.WARNING_MESSAGE);
                if (choice == JOptionPane.YES_OPTION) {
                    if (bookingSlip != null && bookingSlip.getUser() != null) {
                        new ReceptHomeFrm(bookingSlip.getUser());
                    }
                    dispose();
                }
            }
        });

        setVisible(true);
    }

    public static void main(String[] args) {
        new ConfirmFrm(null);
    }
}
