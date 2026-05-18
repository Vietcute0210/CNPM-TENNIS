package view;

import dao.ClientDAO;
import model.BookedCourt;
import model.Client;
import model.User;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.List;

public class AddClientFrm extends JFrame {
    private JPanel mainPanel;
    private JTextField txtName;
    private JTextField txtAddress;
    private JTextField txtTel;
    private JTextField txtEmail;
    private JTextField txtNote;
    private JButton btnAdd;
    private JButton btnReset; // Nút Reset trong UI, ta sẽ dùng làm nút Back

    private ClientInforFrm parentFrm;
    private User user;
    private List<BookedCourt> bookedCourts;
    private boolean addedSuccessfully = false;

    /**
     * Constructor khi mở từ ClientInforFrm (exception 16: KH chưa có trong hệ thống)
     */
    public AddClientFrm(ClientInforFrm parent, User user, List<BookedCourt> bookedCourts) {
        this.parentFrm = parent;
        this.user = user;
        this.bookedCourts = bookedCourts;

        setContentPane(mainPanel);
        setTitle("AddClientView");
        setSize(450, 350);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setLocationRelativeTo(null);

        // Đổi tên nút Reset thành Back theo yêu cầu 1
        if (btnReset != null) {
            btnReset.setText("Back");
        }

        btnAdd.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String name = txtName.getText().trim();
                String tel  = txtTel.getText().trim();

                if (name.isEmpty() || tel.isEmpty()) {
                    JOptionPane.showMessageDialog(AddClientFrm.this,
                            "Tên và số điện thoại là bắt buộc (*)!", "Lỗi", JOptionPane.ERROR_MESSAGE);
                    return;
                }

                ClientDAO dao = new ClientDAO();
                
                // Nghiệp vụ 2: kiểm tra trùng lặp số điện thoại
                if (dao.checkDuplicatePhone(tel)) {
                    JOptionPane.showMessageDialog(AddClientFrm.this,
                            "Số điện thoại này đã tồn tại trên hệ thống!\nVui lòng nhập số khác hoặc quay lại tìm kiếm.",
                            "Lỗi trùng lặp", JOptionPane.ERROR_MESSAGE);
                    return;
                }

                Client client = new Client();
                client.setName(name);
                client.setTel(tel);
                client.setEmail(txtEmail.getText().trim());
                client.setAddress(txtAddress.getText().trim());
                client.setNote(txtNote.getText().trim());

                if (dao.addClient(client)) {
                    addedSuccessfully = true; // Đánh dấu thêm thành công để không hiện lại màn chọn
                    JOptionPane.showMessageDialog(AddClientFrm.this,
                            "Thêm khách hàng \"" + name + "\" thành công!", "Thành công", JOptionPane.INFORMATION_MESSAGE);
                    
                    // Nghiệp vụ 3: Đóng màn thêm KH và chuyển sang màn xác nhận confirm luôn
                    dispose();
                    if (parentFrm != null) {
                        parentFrm.proceedWithClient(client);
                    }
                } else {
                    JOptionPane.showMessageDialog(AddClientFrm.this,
                            "Lỗi khi lưu thông tin khách hàng!", "Lỗi", JOptionPane.ERROR_MESSAGE);
                }
            }
        });

        // Nghiệp vụ 1: Nút Back để đóng màn thêm KH và hiện lại màn chọn KH
        if (btnReset != null) {
            btnReset.addActionListener(new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent e) {
                    dispose(); // Đóng form hiện tại, kích hoạt sự kiện windowClosed
                }
            });
        }

        // Khi đóng AddClientFrm mà KHÔNG thêm thành công -> hiện lại ClientInforFrm
        addWindowListener(new java.awt.event.WindowAdapter() {
            @Override
            public void windowClosed(java.awt.event.WindowEvent e) {
                if (!addedSuccessfully && parentFrm != null) {
                    parentFrm.setVisible(true);
                }
            }
        });

        setVisible(true);
    }

    // Constructor độc lập để test
    public AddClientFrm() {
        setContentPane(mainPanel);
        setTitle("AddClientView");
        setSize(450, 350);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);
        setVisible(true);
    }

    public static void main(String[] args) {
        new AddClientFrm();
    }
}
