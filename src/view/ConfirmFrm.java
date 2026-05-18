package view;
import javax.swing.*;

public class ConfirmFrm extends JFrame {
    private JPanel mainPanel;
    private JTextArea taBookingSlip;
    private JButton btnCancel;
    private JButton btnConfirm;

    public ConfirmFrm() {
        setContentPane(mainPanel);
        setTitle("ConfirmView");
        setSize(700, 450);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);

        if (taBookingSlip != null) {
            String tempText = "===== BOOKING CONFIRMATION =====\n" +
                              "Creator : A\n" +
                              "Client  : B - Hà Nội - 123456789 - B@gmail.com\n\n" +
                              "Booked Courts list:\n" +
                              "No. Type    Price/h   Description  StartDate   EndDate     Days    TimeSlot      Promo 15%    Deposit 10%   Total\n" +
                              "1   Mini    150k      Trong nhà    01/03/2026  30/09/2026  T3,T5   19:00-21:00   2.745k       1.556k        15.555k (vnd)\n\n" +
                              "Promotion (15%) : 2.745.000 vnd\n" +
                              "Deposit (10%)   : 1.555.500 vnd\n" +
                              "Total           : 18.300.000 vnd\n" +
                              "Final Amount    : 15.555.000 vnd\n\n" +
                              "Note: Nếu ngừng thuê giữa chừng, báo trước 2 tuần.\n" +
                              "Cancellation Policy: >7 days=10% | >5 days=20% | >3 days=50% | <3 days=100%\n" +
                              "==============================";
            taBookingSlip.setText(tempText);
            taBookingSlip.setFont(new java.awt.Font("Monospaced", java.awt.Font.PLAIN, 12));
        }

        setVisible(true);
    }

    public static void main(String[] args) {
        new ConfirmFrm();
    }
}
