package view;
import javax.swing.*;

public class ClientInforFrm extends JFrame {
    private JPanel mainPanel;
    private JTextField txtName;
    private JButton btnSearch;
    private JButton btnAdd;
    private JTable tblResult;

    public ClientInforFrm() {
        setContentPane(mainPanel);
        setTitle("ClientInfoView");
        setSize(600, 400);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);
        
        String[] columns = {"Client ID", "Full Name", "Phone", "Email", "History"};
        Object[][] data = {
                {"KH001", "B", "123456789", "b@gmail.com", "2 lần"},
                {"KH002", "Bảo", "987654321", "bao@gmail.com", "5 lần"}
        };

        if (tblResult != null) {
            tblResult.setModel(new javax.swing.table.DefaultTableModel(data, columns));
        }

        setVisible(true);
    }

    public static void main(String[] args) {
        new ClientInforFrm();
    }
}
