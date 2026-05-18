package view;

import model.BookedCourt;
import model.BookingSession;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.List;

public class GeneratedSessionsFrm extends JDialog {
    private JTable tblSessions;
    private JButton btnContinue;
    private BookedCourt bookedCourt;

    public GeneratedSessionsFrm(Frame owner, BookedCourt bookedCourt) {
        super(owner, "Danh sách buổi đặt sân được sinh tự động", true);
        this.bookedCourt = bookedCourt;
        
        setSize(600, 400);
        setLocationRelativeTo(owner);
        setLayout(new BorderLayout());

        JLabel lblTitle = new JLabel("Các buổi đặt sân cho: " + bookedCourt.getCourt().getName());
        lblTitle.setFont(new Font("Arial", Font.BOLD, 14));
        lblTitle.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));
        add(lblTitle, BorderLayout.NORTH);

        tblSessions = new JTable();
        String[] columns = {"STT", "Ngày", "Giờ bắt đầu", "Giờ kết thúc", "Trạng thái"};
        
        List<BookingSession> sessions = bookedCourt.getSessions();
        Object[][] data = new Object[sessions.size()][5];
        for (int i = 0; i < sessions.size(); i++) {
            BookingSession s = sessions.get(i);
            data[i][0] = i + 1;
            data[i][1] = s.getSessionDate().toString();
            data[i][2] = s.getStartTime().toString();
            data[i][3] = s.getEndTime().toString();
            data[i][4] = s.getStatus();
        }
        
        tblSessions.setModel(new DefaultTableModel(data, columns) {
            @Override
            public boolean isCellEditable(int row, int column) {
                return false;
            }
        });
        
        add(new JScrollPane(tblSessions), BorderLayout.CENTER);

        JPanel pnlBottom = new JPanel();
        btnContinue = new JButton("Tiếp tục (Chọn Khách Hàng)");
        btnContinue.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                dispose();
                // Normally we would pass BookingSlip/BookedCourt to ClientInforFrm
                try {
                    // Try to instantiate ClientInforFrm
                    Class<?> clazz = Class.forName("view.ClientInforFrm");
                    JFrame frm = (JFrame) clazz.getDeclaredConstructor().newInstance();
                    frm.setVisible(true);
                } catch (Exception ex) {
                    try {
                        // Fallback if no package view
                        Class<?> clazz = Class.forName("ClientInforFrm");
                        JFrame frm = (JFrame) clazz.getDeclaredConstructor().newInstance();
                        frm.setVisible(true);
                    } catch (Exception exc) {
                        exc.printStackTrace();
                    }
                }
            }
        });
        pnlBottom.add(btnContinue);
        add(pnlBottom, BorderLayout.SOUTH);
    }
}
