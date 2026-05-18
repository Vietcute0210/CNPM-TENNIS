package dao;

import model.Client;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

public class ClientDAO extends DAO {
    public ClientDAO() {
        super();
    }

    public List<Client> searchByName(String name) {
        List<Client> list = new ArrayList<>();
        // Truy vấn tìm kiếm gần đúng bằng LIKE
        // Left join với bảng BookingSlip để đếm số lần đặt sân (lịch sử đặt sân)
        String sql = "SELECT c.*, COUNT(bs.id) AS bookingCount "
                   + "FROM tblClient c "
                   + "LEFT JOIN tblBookingSlip bs ON c.id = bs.clientId "
                   + "WHERE c.name LIKE ? "
                   + "GROUP BY c.id";
        try {
            PreparedStatement ps = con.prepareStatement(sql);
            ps.setString(1, "%" + name + "%");
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                Client client = new Client();
                client.setId(rs.getInt("id"));
                client.setName(rs.getString("name"));
                client.setTel(rs.getString("tel"));
                client.setEmail(rs.getString("email"));
                client.setAddress(rs.getString("address"));
                client.setNote(rs.getString("note"));
                client.setBookingCount(rs.getInt("bookingCount"));
                list.add(client);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return list;
    }

    public boolean addClient(Client client) {
        String sql = "INSERT INTO tblClient (name, tel, email, address, note) VALUES (?, ?, ?, ?, ?)";
        try {
            PreparedStatement ps = con.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);
            ps.setString(1, client.getName());
            ps.setString(2, client.getTel());
            ps.setString(3, client.getEmail());
            ps.setString(4, client.getAddress());
            ps.setString(5, client.getNote());
            ps.executeUpdate();

            ResultSet generatedKeys = ps.getGeneratedKeys();
            if (generatedKeys.next()) {
                client.setId(generatedKeys.getInt(1));
                return true;
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return false;
    }
}
