package model;

import org.junit.Test;
import static org.junit.Assert.*;

import java.time.LocalDate;
import java.util.List;

public class BookedCourtTest {

    @Test
    public void testGenerateSessions_Standard() {
        BookedCourt bc = new BookedCourt();
        bc.setStartDate(LocalDate.of(2026, 3, 1));
        bc.setEndDate(LocalDate.of(2026, 3, 15));
        bc.setDaysOfWeek("Thứ 3, Thứ 5");
        bc.setTimeSlot("19:00 - 21:00");
        
        bc.generateSessions();
        List<BookingSession> sessions = bc.getSessions();
        
        // 2026-03-01 is Sunday
        // 03-03 (Tue), 03-05 (Thu), 03-10 (Tue), 03-12 (Thu) -> 4 sessions
        assertEquals(4, sessions.size());
        assertEquals(LocalDate.of(2026, 3, 3), sessions.get(0).getDate());
        assertEquals("19:00", sessions.get(0).getStartTime().toString());
        assertEquals("21:00", sessions.get(0).getEndTime().toString());
        assertEquals("Đã đặt", sessions.get(0).getStatus());
    }
    
    @Test
    public void testGenerateSessions_NoMatches() {
        BookedCourt bc = new BookedCourt();
        bc.setStartDate(LocalDate.of(2026, 3, 1));
        bc.setEndDate(LocalDate.of(2026, 3, 2)); // Sun to Mon
        bc.setDaysOfWeek("Thứ 3, Thứ 5"); // Tue, Thu
        bc.setTimeSlot("19:00 - 21:00");
        
        bc.generateSessions();
        List<BookingSession> sessions = bc.getSessions();
        
        assertEquals(0, sessions.size());
    }
}
