package dao;

import model.BookingSlip;
import model.Client;
import model.User;
import org.junit.Before;
import org.junit.Test;
import static org.junit.Assert.*;

import java.time.LocalDate;
import java.time.LocalDateTime;

public class BookingSlipDAOTest {
    private BookingSlipDAO bookingSlipDAO;

    @Before
    public void setUp() {
        bookingSlipDAO = new BookingSlipDAO();
    }

    @Test
    public void testAddBookingSlip() {
        BookingSlip bs = new BookingSlip();
        bs.setBookingDay(LocalDate.now());
        bs.setSelloff(0);
        
        Client client = new Client();
        client.setId(1);
        bs.setClient(client);
        
        User user = new User();
        user.setId(1);
        bs.setUser(user);

        boolean result = bookingSlipDAO.addBookingSlip(bs);
        assertNotNull(bookingSlipDAO);
    }
}
