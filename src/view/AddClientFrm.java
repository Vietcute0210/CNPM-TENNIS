import javax.swing.*;

public class AddClientFrm extends JFrame {
    private JPanel mainPanel;
    private JTextField txtName;
    private JTextField txtAddress;
    private JTextField txtTel;
    private JTextField txtEmail;
    private JTextField txtNote;
    private JButton btnAdd;
    private JButton btnReset;

    public AddClientFrm() {
        setContentPane(mainPanel);
        setTitle("AddClientView");
        setSize(400, 350);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);

        setVisible(true);
    }

    public static void main(String[] args) {
        new AddClientFrm();
    }
}
