package view;
import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.List;
import dao.CourtDAO;
import model.Court;
import model.BookedCourt;
import model.BookingSlip;
import view.GeneratedSessionsFrm;
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

    public SearchCourtFrm() {
        courtDAO = new CourtDAO();
        setContentPane(mainPanel);
        setTitle("SearchCourtView");
        setSize(800, 350);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);
        setResizable(false);

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
                
                if(startDate != null && endDate != null && !daysOfWeek.isEmpty()) {
                    currentCourts = courtDAO.searchFreeCourt(startDate, endDate, daysOfWeek, timeSlot);
                    Object[][] newData = new Object[currentCourts.size()][6];
                    for(int i=0; i<currentCourts.size(); i++) {
                        Court c = currentCourts.get(i);
                        newData[i][0] = c.getName();
                        newData[i][1] = c.getType();
                        newData[i][2] = String.format("%,.0fđ", c.getPrice());
                        newData[i][3] = String.valueOf(c.getAvailableSessionsCount());
                        newData[i][4] = c.getDescription();
                        newData[i][5] = false;
                    }
                    String[] columns = {"Court ID", "Type", "Price/h", "Total Sessions", "Description", "Select"};
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
            }
        });

        btnNext.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                // Find selected court
                int selectedRow = -1;
                for (int i = 0; i < tblResult.getRowCount(); i++) {
                    Boolean isChecked = (Boolean) tblResult.getValueAt(i, 5);
                    if (isChecked != null && isChecked) {
                        selectedRow = i;
                        break;
                    }
                }
                
                if (selectedRow != -1 && currentCourts != null) {
                    Court selectedCourt = currentCourts.get(selectedRow);
                    
                    BookedCourt bookedCourt = new BookedCourt();
                    bookedCourt.setCourt(selectedCourt);
                    bookedCourt.setStartDate(new java.sql.Date(dtStartDate.getDate().getTime()).toLocalDate());
                    bookedCourt.setEndDate(new java.sql.Date(dtEndDate.getDate().getTime()).toLocalDate());
                    bookedCourt.setTimeSlot((String) cmbTimeSlot.getSelectedItem());
                    
                    StringBuilder days = new StringBuilder();
                    if(chkT2.isSelected()) days.append("Thứ 2, ");
                    if(chkT3.isSelected()) days.append("Thứ 3, ");
                    if(chkT4.isSelected()) days.append("Thứ 4, ");
                    if(chkT5.isSelected()) days.append("Thứ 5, ");
                    if(chkT6.isSelected()) days.append("Thứ 6, ");
                    if(chkT7.isSelected()) days.append("Thứ 7, ");
                    if(chkCN.isSelected()) days.append("Chủ nhật, ");
                    bookedCourt.setDaysOfWeek(days.toString());
                    
                    // Generate sessions automatically
                    bookedCourt.generateSessions();
                    
                    // Show generated sessions dialog
                    GeneratedSessionsFrm sessionsFrm = new GeneratedSessionsFrm(SearchCourtFrm.this, bookedCourt);
                    sessionsFrm.setVisible(true);
                    dispose(); // close SearchCourtFrm
                } else {
                    JOptionPane.showMessageDialog(SearchCourtFrm.this, "Vui lòng chọn một sân!");
                }
            }
        });

        setVisible(true);
    }

    public static void main(String[] args) {
        new SearchCourtFrm();
    }
}
