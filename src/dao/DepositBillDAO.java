package dao;

import model.DepositBill;
import java.sql.PreparedStatement;
import java.sql.Statement;
import java.sql.Date;

public class DepositBillDAO extends DAO {
    public DepositBillDAO() {
        super();
    }

    public boolean confirmPayment(DepositBill db) {
        String sql = "INSERT INTO tblDepositBill (createdDate, deposit, paymentMethod, tblBookingSlipID) VALUES (?, ?, ?, ?)";
        try {
            PreparedStatement ps = con.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);
            ps.setDate(1, Date.valueOf(db.getCreatedDate()));
            ps.setDouble(2, db.getDeposit());
            ps.setString(3, db.getPaymentMethod());
            ps.setInt(4, db.getBookingSlip().getId());
            
            ps.executeUpdate();
            return true;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return false;
    }
}
