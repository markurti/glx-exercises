package com.logistics.repository;

import com.logistics.models.*;
import com.logistics.database.IDatabaseConnection;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class CarrierRepository implements IRepository<Carrier> {
    private final IDatabaseConnection dbConnection;

    public CarrierRepository(IDatabaseConnection dbConnection) {
        this.dbConnection = dbConnection;
    }

    @Override
    public Carrier save(Carrier carrier) {
        String sql = "INSERT INTO carriers (name, contact_name, contact_email, contact_phone, " +
                "rate_ground, rate_air, rate_ocean) VALUES (?,?,?,?,?,?,?) RETURNING id";

        try (Connection conn = dbConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setString(1, carrier.getName());
            stmt.setString(2, carrier.getContactInfo().getName());
            stmt.setString(3, carrier.getContactInfo().getEmail());
            stmt.setString(4, carrier.getContactInfo().getPhone());

            Rates rates = carrier.getRates() != null ? carrier.getRates() : new Rates(5.0f, 10.0f, 3.0f);
            stmt.setFloat(5, rates.getGround());
            stmt.setFloat(6, rates.getAir());
            stmt.setFloat(7, rates.getOcean());

            ResultSet rs = stmt.executeQuery();
            if (rs.next()) {
                carrier.setId(rs.getLong("id"));
            }
        } catch (SQLException e) {
            System.out.println("Error saving carrier: " + e.getMessage());
        }
        return carrier;
    }

    @Override
    public Carrier findById(Long id) {
        String sql = "SELECT * FROM carriers WHERE id = ?";

        try (Connection conn = dbConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setLong(1, id);
            ResultSet rs = stmt.executeQuery();

            if (rs.next()) {
                return mapResultSetToCarrier(rs);
            }
        } catch (SQLException e) {
            System.out.println("Error finding carrier with id " + id + ": " + e.getMessage());
        }
        return null;
    }

    @Override
    public List<Carrier> findAll() {
        List<Carrier> carriers = new ArrayList<>();
        String sql = "SELECT * FROM carriers";

        try (Connection conn = dbConnection.getConnection();
             Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery(sql)) {

            while (rs.next()) {
                carriers.add(mapResultSetToCarrier(rs));
            }
        } catch (SQLException e) {
            System.out.println("Error fetching carriers: " + e.getMessage());
        }
        return carriers;
    }

    @Override
    public void update(Carrier carrier) {
        String sql = "UPDATE carriers SET name = ?, contact_name = ?, contact_email = ?, " +
                "contact_phone = ?, rate_ground = ?, rate_air = ?, rate_ocean = ? WHERE id = ?";

        try (Connection conn = dbConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setString(1, carrier.getName());
            stmt.setString(2, carrier.getContactInfo().getName());
            stmt.setString(3, carrier.getContactInfo().getEmail());
            stmt.setString(4, carrier.getContactInfo().getPhone());

            Rates rates = carrier.getRates() != null ? carrier.getRates() : new Rates(5.0f, 10.0f, 3.0f);
            stmt.setFloat(5, rates.getGround());
            stmt.setFloat(6, rates.getAir());
            stmt.setFloat(7, rates.getOcean());
            stmt.setLong(8, carrier.getId());

            stmt.executeUpdate();
        } catch (SQLException e) {
            System.out.println("Error updating carrier: " + e.getMessage());
        }
    }

    @Override
    public void delete(Long id) {
        String sql = "DELETE FROM carriers WHERE id = ?";

        try (Connection conn = dbConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setLong(1, id);
            stmt.executeUpdate();
        } catch (SQLException e) {
            System.out.println("Error deleting carrier: " + e.getMessage());
        }
    }

    private Carrier mapResultSetToCarrier(ResultSet rs) throws SQLException {
        Contact contact = new Contact(
                rs.getString("contact_name"),
                rs.getString("contact_email"),
                rs.getString("contact_phone")
        );

        Carrier carrier = new Carrier(rs.getString("name"), contact);
        carrier.setId(rs.getLong("id"));
        carrier.setRates(new Rates(
                rs.getFloat("rate_ground"),
                rs.getFloat("rate_air"),
                rs.getFloat("rate_ocean")
        ));

        return carrier;
    }
}
