package view;

import model.BookedCourt;
import model.BookingSession;
import model.User;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.List;

public class BookingSessionView extends JFrame {
    private JPanel mainPanel;
    private JLabel lblTitle;
    private JTable tblSessions;
    private JButton btnContinue;

    private List<BookedCourt> bookedCourts;
    private User user;

    public BookingSessionView(List<BookedCourt> bookedCourts, User user) {
        this.bookedCourts = bookedCourts;
        this.user = user;

        setContentPane(mainPanel);
        setTitle("BookingSessionView");
        setSize(700, 450);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);
        setResizable(false);

        // Tiêu đề hiển thị tất cả sân đã chọn
        if (lblTitle != null) {
            StringBuilder courtNames = new StringBuilder();
            for (BookedCourt bc : bookedCourts) {
                if (courtNames.length() > 0) courtNames.append(", ");
                courtNames.append(bc.getCourt().getName());
            }
            lblTitle.setText("Các buổi đặt sân cho: " + courtNames.toString());
        }

        // Gom tất cả sessions từ tất cả sân vào 1 bảng
        String[] columns = {"STT", "Court", "Date", "startTime", "endTime", "status"};
        DefaultTableModel model = new DefaultTableModel(columns, 0) {
            @Override
            public boolean isCellEditable(int row, int column) {
                return false;
            }
        };

        int stt = 1;
        for (BookedCourt bc : bookedCourts) {
            List<BookingSession> sessions = bc.getSessions();
            for (BookingSession s : sessions) {
                model.addRow(new Object[]{
                        stt++,
                        bc.getCourt().getName(),
                        s.getDate().toString(),
                        s.getStartTime().toString(),
                        s.getEndTime().toString(),
                        s.getStatus()
                });
            }
        }

        tblSessions.setModel(model);

        btnContinue.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                // Truyền danh sách bookedCourts sang ClientInforFrm
                new ClientInforFrm(user, bookedCourts);
                dispose();
            }
        });

        setVisible(true);
    }

    public static void main(String[] args) {
        new BookingSessionView(new java.util.ArrayList<>(), new User());
    }
}
