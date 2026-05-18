package view;
import javax.swing.*;

public class ReceptHomeFrm extends JFrame {

    private JPanel mainPanel;
    private JButton btnCancel;
    private JButton btnBooking;
    private JLabel lblAccount;

    public ReceptHomeFrm() {
        setContentPane(mainPanel);
        setTitle("ReceptHomeView");
        setSize(450, 300);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);
        setResizable(false);
        setVisible(true);
    }

    public static void main(String[] args) {
        new ReceptHomeFrm();
    }
}
