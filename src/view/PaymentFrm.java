package view;
import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import model.BookingSlip;
import model.DepositBill;
import dao.BookingSlipDAO;
import dao.DepositBillDAO;

public class PaymentFrm extends JFrame {
    private JPanel mainPanel;
    private JLabel lblDeposit;
    private JComboBox cmbPaymentMethod;
    private JButton btnCancel;
    private JButton btnPayment;
    
    private BookingSlip bookingSlip;

    public PaymentFrm(BookingSlip slip) {
        this.bookingSlip = slip;
        setContentPane(mainPanel);
        setTitle("PaymentView");
        setSize(400, 200);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);

        if (lblDeposit != null) {
            double amount = (slip != null) ? slip.getDeposit() : 0;
            lblDeposit.setText(String.format("%,.0f VNĐ", amount));
            lblDeposit.setForeground(java.awt.Color.RED);
            lblDeposit.setFont(new java.awt.Font("Arial", java.awt.Font.BOLD, 14));
        }

        if (cmbPaymentMethod != null) {
            cmbPaymentMethod.addItem("Chuyển khoản");
            cmbPaymentMethod.addItem("Tiền mặt");
            cmbPaymentMethod.addItem("Thẻ tín dụng");
        }

         btnPayment.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if (bookingSlip != null) {
                    BookingSlipDAO slipDao = new BookingSlipDAO();
                    if (slipDao.addBookingSlip(bookingSlip)) {
                        
                        // Sau khi lưu BookingSlip thành công -> Tạo và lưu DepositBill
                        DepositBill db = new DepositBill();
                        db.setCreatedDate(java.time.LocalDate.now());
                        db.setDeposit(bookingSlip.getDeposit());
                        db.setPaymentMethod((String) cmbPaymentMethod.getSelectedItem());
                        db.setBookingSlip(bookingSlip);

                        DepositBillDAO depositDao = new DepositBillDAO();
                        if (depositDao.confirmPayment(db)) {
                            JOptionPane.showMessageDialog(PaymentFrm.this, "Thanh toán cọc và Đặt sân thành công!");
                            if (bookingSlip.getUser() != null) {
                                new ReceptHomeFrm(bookingSlip.getUser());
                            }
                            dispose();
                        } else {
                            JOptionPane.showMessageDialog(PaymentFrm.this, "Lưu hóa đơn đặt cọc thất bại!");
                        }
                    } else {
                        JOptionPane.showMessageDialog(PaymentFrm.this, "Lỗi khi lưu phiếu đặt sân!");
                    }
                }
            }
        });

        btnCancel.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                new ConfirmFrm(bookingSlip);
                dispose();
            }
        });

        setVisible(true);
    }

    public static void main(String[] args) {
        new PaymentFrm(null);
    }
}
