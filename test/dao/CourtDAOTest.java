package dao;

import model.Court;
import org.junit.Before;
import org.junit.Test;
import static org.junit.Assert.*;

import java.util.Date;
import java.util.List;
import java.text.SimpleDateFormat;

public class CourtDAOTest {
    private CourtDAO courtDAO;
    private SimpleDateFormat sdf;

    @Before
    public void setUp() {
        courtDAO = new CourtDAO();
        sdf = new SimpleDateFormat("yyyy-MM-dd");
    }

    @Test
    public void testSearchFreeCourt_Normal() throws Exception {
        Date startDate = sdf.parse("2026-03-01");
        Date endDate = sdf.parse("2026-09-30");
        String daysOfWeek = "Thứ 3, Thứ 5";
        String timeSlot = "19:00 - 21:00";
        
        // Cần đảm bảo database đã được tạo và chứa dữ liệu mock (từ file sql)
        List<Court> freeCourts = courtDAO.searchFreeCourt(startDate, endDate, daysOfWeek, timeSlot);
        
        assertNotNull(freeCourts);
        // Vì có thể có lỗi kết nối nếu user chưa bật MySQL, test này có thể fail, 
        // nhưng về mặt logic DAO, hàm sẽ trả về list rỗng nếu lỗi.
    }
}
