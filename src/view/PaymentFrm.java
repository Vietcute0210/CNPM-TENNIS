package view;
import javax.swing.*;

public class PaymentFrm extends JFrame {
    private JPanel mainPanel;
    private JLabel lblDeposit;
    private JComboBox cmbPaymentMethod;
    private JButton btnCancel;
    private JButton btnPayment;

    public PaymentFrm() {
        setContentPane(mainPanel);
        setTitle("PaymentView");
        setSize(400, 200);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);

        if (lblDeposit != null) {
            lblDeposit.setText("1.555.500 VNĐ");
            lblDeposit.setForeground(java.awt.Color.RED);
            lblDeposit.setFont(new java.awt.Font("Arial", java.awt.Font.BOLD, 14));
        }

        if (cmbPaymentMethod != null) {
            cmbPaymentMethod.addItem("Chuyển khoản");
            cmbPaymentMethod.addItem("Tiền mặt");
            cmbPaymentMethod.addItem("Thẻ tín dụng");
        }

        setVisible(true);
    }

    public static void main(String[] args) {
        new PaymentFrm();
    }
}
