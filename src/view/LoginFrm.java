package view;
import javax.swing.*;

public class LoginFrm extends JFrame {

    private JPanel mainPanel;
    private JLabel Username;
    private JTextField txtUsername;
    private JPasswordField txtPassword;
    private JButton btnLogin;
    private JButton btnCancel;

    public LoginFrm() {
        setContentPane(mainPanel);
        setTitle("LoginView");
        setSize(400, 250);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);
        setResizable(false);
        setVisible(true);
    }

    public static void main(String[] args) {
        new LoginFrm();
    }
}
