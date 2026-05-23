package view;
import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.Date;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import dao.CourtDAO;
import model.Court;
import model.BookedCourt;
import model.User;

public class SearchCourtFrm extends JFrame {
    private JPanel mainPanel;
    private com.toedter.calendar.JDateChooser dtStartDate;
    private com.toedter.calendar.JDateChooser dtEndDate;
    private JComboBox<String> cmbTimeSlot;
    private JCheckBox chkT2, chkT3, chkT4, chkT5, chkT6, chkT7, chkCN;
    private JButton btnSearch;
    private JTable tblResult;
    private JButton btnNext;

    private CourtDAO courtDAO;
    private List<Court> currentCourts;
    private User user;

    public SearchCourtFrm(User user) {
        this.user = user;
        courtDAO = new CourtDAO();
        setContentPane(mainPanel);
        setTitle("SearchCourtView");
        setSize(800, 350);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);
        setResizable(false);

        // Khởi tạo bảng với tên cột ban đầu
        String[] columns = {"Court Name", "Status", "Price/h", "Total Sessions", "Description", "Select"};
        tblResult.setModel(new DefaultTableModel(new Object[0][6], columns) {
            @Override
            public Class<?> getColumnClass(int col) {
                return col == 5 ? Boolean.class : String.class;
            }
            @Override
            public boolean isCellEditable(int row, int col) {
                return col == 5;
            }
        });

        if (cmbTimeSlot != null) {
            cmbTimeSlot.addItem("07:00 - 09:00");
            cmbTimeSlot.addItem("09:00 - 11:00");
            cmbTimeSlot.addItem("11:00 - 13:00");
            cmbTimeSlot.addItem("13:00 - 15:00");
            cmbTimeSlot.addItem("15:00 - 17:00");
            cmbTimeSlot.addItem("17:00 - 19:00");
            cmbTimeSlot.addItem("19:00 - 21:00");
            cmbTimeSlot.addItem("21:00 - 23:00");
        }

        btnSearch.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                java.util.Date startDate = dtStartDate.getDate();
                java.util.Date endDate = dtEndDate.getDate();
                String timeSlot = (String) cmbTimeSlot.getSelectedItem();

                StringBuilder days = new StringBuilder();
                if(chkT2.isSelected()) days.append("Thứ 2, ");
                if(chkT3.isSelected()) days.append("Thứ 3, ");
                if(chkT4.isSelected()) days.append("Thứ 4, ");
                if(chkT5.isSelected()) days.append("Thứ 5, ");
                if(chkT6.isSelected()) days.append("Thứ 6, ");
                if(chkT7.isSelected()) days.append("Thứ 7, ");
                if(chkCN.isSelected()) days.append("Chủ nhật, ");

                String daysOfWeek = days.toString();

                if (startDate == null || endDate == null) {
                    JOptionPane.showMessageDialog(SearchCourtFrm.this, "Vui lòng chọn ngày bắt đầu và kết thúc!");
                    return;
                }
                if (daysOfWeek.isEmpty()) {
                    JOptionPane.showMessageDialog(SearchCourtFrm.this, "Vui lòng chọn ít nhất một ngày trong tuần!");
                    return;
                }

                currentCourts = courtDAO.searchFreeCourt(startDate, endDate, daysOfWeek, timeSlot);

                if (currentCourts.isEmpty()) {
                    JOptionPane.showMessageDialog(SearchCourtFrm.this,
                            "Không tìm thấy sân nào trống theo yêu cầu.\nVui lòng thử lại với ngày hoặc khung giờ khác.",
                            "Không tìm thấy", JOptionPane.INFORMATION_MESSAGE);
                }

                Object[][] newData = new Object[currentCourts.size()][6];
                for(int i=0; i<currentCourts.size(); i++) {
                    Court c = currentCourts.get(i);
                    newData[i][0] = c.getName();
                    newData[i][1] = c.getStatus();
                    newData[i][2] = String.format("%,.0fđ", c.getPrice());
                    newData[i][3] = String.valueOf(c.getAvailableSessionsCount());
                    newData[i][4] = c.getDescription();
                    newData[i][5] = false; // checkbox chưa chọn
                }
                String[] columns = {"Court Name", "Status", "Price/h", "Total Sessions", "Description", "Select"};
                tblResult.setModel(new javax.swing.table.DefaultTableModel(newData, columns) {
                    @Override
                    public Class<?> getColumnClass(int col) {
                        return col == 5 ? Boolean.class : String.class;
                    }
                    @Override
                    public boolean isCellEditable(int row, int col) {
                        return col == 5;
                    }
                });
            }
        });

        btnNext.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if (currentCourts == null || currentCourts.isEmpty()) {
                    JOptionPane.showMessageDialog(SearchCourtFrm.this, "Vui lòng tìm sân trước!");
                    return;
                }

                List<BookedCourt> selectedBookedCourts = new ArrayList<>();
                String daysOfWeek = buildDaysOfWeek();
                String timeSlot = (String) cmbTimeSlot.getSelectedItem();
                LocalDate startLocal = new Date(dtStartDate.getDate().getTime()).toLocalDate();
                LocalDate endLocal   = new Date(dtEndDate.getDate().getTime()).toLocalDate();

                for (int i = 0; i < tblResult.getRowCount(); i++) {
                    Boolean isChecked = (Boolean) tblResult.getValueAt(i, 5);
                    if (isChecked != null && isChecked) {
                        Court court = currentCourts.get(i);
                        BookedCourt bc = new BookedCourt();
                        bc.setCourt(court);
                        bc.setPrice(court.getPrice()); // Lấy giá từ sân
                        bc.setStartDate(startLocal);
                        bc.setEndDate(endLocal);
                        bc.setTimeSlot(timeSlot);
                        bc.setDaysOfWeek(daysOfWeek);
                        bc.generateSessions(); // Sinh sessions tự động
                        selectedBookedCourts.add(bc);
                    }
                }

                if (selectedBookedCourts.isEmpty()) {
                    JOptionPane.showMessageDialog(SearchCourtFrm.this, "Vui lòng tích chọn ít nhất một sân!");
                    return;
                }

                new ClientInforFrm(user, selectedBookedCourts);
                dispose();
            }
        });

        setVisible(true);
    }

    private String buildDaysOfWeek() {
        StringBuilder days = new StringBuilder();
        if(chkT2.isSelected()) days.append("Thứ 2, ");
        if(chkT3.isSelected()) days.append("Thứ 3, ");
        if(chkT4.isSelected()) days.append("Thứ 4, ");
        if(chkT5.isSelected()) days.append("Thứ 5, ");
        if(chkT6.isSelected()) days.append("Thứ 6, ");
        if(chkT7.isSelected()) days.append("Thứ 7, ");
        if(chkCN.isSelected()) days.append("Chủ nhật, ");
        return days.toString();
    }

    public static void main(String[] args) {
        new SearchCourtFrm(new User());
    }
}
