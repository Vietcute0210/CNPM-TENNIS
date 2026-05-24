package view;
import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import dao.UserDAO;
import model.User;

public class LoginFrm extends JFrame {

    private JPanel mainPanel;
    private JTextField txtUsername;
    private JPasswordField txtPassword;
    private JButton btnLogin;

    public LoginFrm() {
        setContentPane(mainPanel);
        setTitle("LoginView");
        setSize(400, 250);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);
        setResizable(false);

        btnLogin.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                User user = new User();
                user.setUsername(txtUsername.getText());
                user.setPassword(new String(txtPassword.getPassword()));
                
                UserDAO userDAO = new UserDAO();
                if (userDAO.checkLogin(user)) {
                    new ReceptHomeFrm(user);
                    dispose(); // Đóng form đăng nhập sau khi đăng nhập thành công
                } else {
                    JOptionPane.showMessageDialog(LoginFrm.this, "Tài khoản hoặc mật khẩu không đúng!");
                }
            }
        });


        setVisible(true);
    }

    public static void main(String[] args) {
        new LoginFrm();
    }
}
