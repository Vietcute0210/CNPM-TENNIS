package dao;

import model.DepositBill;
import java.sql.PreparedStatement;
import java.sql.Statement;
import java.sql.Timestamp;

public class DepositBillDAO extends DAO {
    public DepositBillDAO() {
        super();
    }

    public boolean confirmPayment(DepositBill db) {
        String sql = "INSERT INTO tblDepositBill (amount, paymentMethod, paymentDate, bookingSlipId, userId) VALUES (?, ?, ?, ?, ?)";
        try {
            PreparedStatement ps = con.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);
            ps.setDouble(1, db.getAmount());
            ps.setString(2, db.getPaymentMethod());
            ps.setTimestamp(3, Timestamp.valueOf(db.getPaymentDate()));
            ps.setInt(4, db.getBookingSlip().getId());
            ps.setInt(5, db.getUser().getId());
            
            ps.executeUpdate();
            return true;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return false;
    }
}
