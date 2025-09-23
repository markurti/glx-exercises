package com.logistics.repository;

import com.logistics.models.*;
import com.logistics.database.IDatabaseConnection;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class ShipmentRepository implements IRepository<Shipment> {
    private final IDatabaseConnection dbConnection;

    public ShipmentRepository(IDatabaseConnection dbConnection) {
        this.dbConnection = dbConnection;
    }

    @Override
    public Shipment save(Shipment shipment) {
        String sql = "INSERT INTO shipments (sender_street, sender_city, sender_country, " +
                "receiver_street, receiver_city, receiver_country, weight, status_code, " +
                "status_description, carrier_id, warehouse_id) VALUES (?,?,?,?,?,?,?,?,?,?,?) RETURNING id";

        try (Connection conn = dbConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setString(1, shipment.getSender().getStreet());
            stmt.setString(2, shipment.getSender().getCity());
            stmt.setString(3, shipment.getSender().getCountry());
            stmt.setString(4, shipment.getReceiver().getStreet());
            stmt.setString(5, shipment.getReceiver().getCity());
            stmt.setString(6, shipment.getReceiver().getCountry());
            stmt.setFloat(7, shipment.getWeight());
            stmt.setInt(8, shipment.getStatus().getCode());
            stmt.setString(9, shipment.getStatus().getDescription());
            stmt.setObject(10, shipment.getCarrier() != null ? shipment.getCarrier().getId() : null);
            stmt.setObject(11, shipment.getWarehouse() != null ? shipment.getWarehouse().getId() : null);

            ResultSet rs = stmt.executeQuery();
            if (rs.next()) {
                shipment.setId(rs.getLong("id"));
            }
        } catch (SQLException e) {
            System.out.println("Error saving shipment: " + e.getMessage());
        }
        return shipment;
    }

    @Override
    public Shipment findById(Long id) {
        String sql = "SELECT * FROM shipments WHERE id = ?";

        try (Connection conn = dbConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setLong(1, id);
            ResultSet rs = stmt.executeQuery();

            if (rs.next()) {
                return mapResultSetToShipment(rs);
            }
        } catch (SQLException e) {
            System.out.println("Error finding shipment with id " + id + ": " + e.getMessage());
        }
        return null;
    }

    @Override
    public List<Shipment> findAll() {
        List<Shipment> shipments = new ArrayList<>();
        String sql = "SELECT * FROM shipments";

        try (Connection conn = dbConnection.getConnection();
             Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery(sql)) {

            while (rs.next()) {
                shipments.add(mapResultSetToShipment(rs));
            }
        } catch (SQLException e) {
            System.out.println("Error fetching shipments: " + e.getMessage());
        }
        return shipments;
    }

    @Override
    public void update(Shipment shipment) {
        String sql = "UPDATE shipments SET weight = ?, status_code = ?, status_description = ?, " +
                "carrier_id = ?, warehouse_id = ? WHERE id = ?";

        try (Connection conn = dbConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setFloat(1, shipment.getWeight());
            stmt.setInt(2, shipment.getStatus().getCode());
            stmt.setString(3, shipment.getStatus().getDescription());
            stmt.setObject(4, shipment.getCarrier() != null ? shipment.getCarrier().getId() : null);
            stmt.setObject(5, shipment.getWarehouse() != null ? shipment.getWarehouse().getId() : null);
            stmt.setLong(6, shipment.getId());

            stmt.executeUpdate();
        } catch (SQLException e) {
            System.out.println("Error updating shipment: " + e.getMessage());
        }
    }

    @Override
    public void delete(Long id) {
        String sql = "DELETE FROM shipments WHERE id = ?";

        try (Connection conn = dbConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setLong(1, id);
            stmt.executeUpdate();
        } catch (SQLException e) {
            System.out.println("Error deleting shipment: " + e.getMessage());
        }
    }

    private Shipment mapResultSetToShipment(ResultSet rs) throws SQLException {
        Address sender = new Address(
                rs.getString("sender_street"),
                rs.getString("sender_city"),
                rs.getString("sender_country")
        );

        Address receiver = new Address(
                rs.getString("receiver_street"),
                rs.getString("receiver_city"),
                rs.getString("receiver_country")
        );

        Shipment shipment = new Shipment(sender, receiver, rs.getFloat("weight"));
        shipment.setId(rs.getLong("id"));
        shipment.setStatus(new Status(rs.getInt("status_code"), rs.getString("status_description")));

        return shipment;
    }
}
