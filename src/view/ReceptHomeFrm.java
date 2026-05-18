package view;
import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import model.User;

public class ReceptHomeFrm extends JFrame {

    private JPanel mainPanel;
    private JButton btnCancel;
    private JButton btnBooking;
    private JLabel lblAccount;

    private User user;

    public ReceptHomeFrm(User user) {
        this.user = user;
        setContentPane(mainPanel);
        setTitle("ReceptHomeView");
        setSize(450, 300);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);
        setResizable(false);
        
        if (lblAccount != null && user != null) {
            lblAccount.setText("Xin chào: " + user.getFullName());
        }

        btnBooking.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                new SearchCourtFrm(user);
                dispose();
            }
        });

        btnCancel.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                new LoginFrm();
                dispose();
            }
        });

        setVisible(true);
    }

    public static void main(String[] args) {
        new ReceptHomeFrm(new User());
    }
}
