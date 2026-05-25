package view;

import dao.ClientDAO;
import model.BookedCourt;
import model.Client;
import model.BookingSlip;
import model.User;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

public class ClientInforFrm extends JFrame {
    private JPanel mainPanel;
    private JTextField txtName;
    private JButton btnSearch;
    private JButton btnAdd;
    private JTable tblResult;

    private User user;
    private List<BookedCourt> bookedCourts;
    private List<Client> currentClients;
    private ClientDAO clientDAO;

    public ClientInforFrm(User user, List<BookedCourt> bookedCourts) {
        this.user = user;
        this.bookedCourts = bookedCourts;
        this.clientDAO = new ClientDAO();

        setContentPane(mainPanel);
        setTitle("ClientInfoView");
        setSize(700, 450);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);

        String[] columns = {"Client ID", "Full Name", "Phone", "Email", "Note"};
        tblResult.setModel(new DefaultTableModel(new Object[0][5], columns) {
            @Override
            public boolean isCellEditable(int row, int column) {
                return false;
            }
        });
        tblResult.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);

        btnSearch.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String keyword = txtName.getText().trim();
                if (keyword.isEmpty()) {
                    JOptionPane.showMessageDialog(ClientInforFrm.this,
                            "Vui lòng nhập tên để tìm kiếm!", "Thông báo", JOptionPane.WARNING_MESSAGE);
                    return;
                }
                currentClients = clientDAO.searchByName(keyword);
                DefaultTableModel model = (DefaultTableModel) tblResult.getModel();
                model.setRowCount(0);
                if (currentClients.isEmpty()) {
                    JOptionPane.showMessageDialog(ClientInforFrm.this,
                            "Không tìm thấy khách hàng nào với tên \"" + keyword + "\".\nVui lòng dùng nút 'Add New' để thêm mới.",
                            "Không tìm thấy", JOptionPane.INFORMATION_MESSAGE);
                } else {
                    for (Client c : currentClients) {
                        model.addRow(new Object[]{
                                "KH" + String.format("%03d", c.getId()),
                                c.getName(),
                                c.getTel(),
                                c.getEmail(),
                                c.getNote()
                        });
                    }
                }
            }
        });

        btnAdd.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                setVisible(false);
                new AddClientFrm(ClientInforFrm.this, user, bookedCourts);
            }
        });

        tblResult.addMouseListener(new java.awt.event.MouseAdapter() {
            @Override
            public void mouseClicked(java.awt.event.MouseEvent e) {
                if (e.getClickCount() == 2) {
                    int row = tblResult.getSelectedRow();
                    if (row >= 0 && currentClients != null) {
                        proceedWithClient(currentClients.get(row));
                    }
                }
            }
        });

        setVisible(true);
    }

    public void proceedWithClient(Client client) {
        BookingSlip slip = new BookingSlip();
        slip.setUser(this.user);
        slip.setClient(client);
        slip.setBookingDay(LocalDate.now());
        for (BookedCourt bc : this.bookedCourts) {
            slip.addBookedCourt(bc);
        }
        new ConfirmFrm(slip);
        dispose();
    }

    public static void main(String[] args) {
        new ClientInforFrm(new User(), new ArrayList<>());
    }
}
