package com.logistics.repository;

import com.logistics.models.Warehouse;
import com.logistics.models.Location;
import com.logistics.database.IDatabaseConnection;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class WarehouseRepository implements IRepository<Warehouse> {
    private final IDatabaseConnection dbConnection;

    public WarehouseRepository(IDatabaseConnection dbConnection) {
        this.dbConnection = dbConnection;
    }

    @Override
    public Warehouse save(Warehouse warehouse) {
        String sql = "INSERT INTO warehouses (latitude, longitude, capacity) VALUES (?,?,?) RETURNING id";

        try (Connection conn = dbConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setFloat(1, warehouse.getLocation().getLatitude());
            stmt.setFloat(2, warehouse.getLocation().getLongitude());
            stmt.setInt(3, warehouse.getCapacity());

            ResultSet rs = stmt.executeQuery();
            if (rs.next()) {
                warehouse.setId(rs.getLong("id"));
            }
        } catch (SQLException e) {
            System.out.println("Error saving warehouse: " + e.getMessage());
        }
        return warehouse;
    }

    @Override
    public Warehouse findById(Long id) {
        String sql = "SELECT * FROM warehouses WHERE id = ?";

        try (Connection conn = dbConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setLong(1, id);
            ResultSet rs = stmt.executeQuery();

            if (rs.next()) {
                return mapResultSetToWarehouse(rs);
            }
        } catch (SQLException e) {
            System.out.println("Error finding warehouse with id " + id + ": " + e.getMessage());
        }
        return null;
    }

    @Override
    public List<Warehouse> findAll() {
        List<Warehouse> warehouses = new ArrayList<>();
        String sql = "SELECT * FROM warehouses";

        try (Connection conn = dbConnection.getConnection();
             Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery(sql)) {

            while (rs.next()) {
                warehouses.add(mapResultSetToWarehouse(rs));
            }
        } catch (SQLException e) {
            System.out.println("Error fetching warehouses: " + e.getMessage());
        }
        return warehouses;
    }

    @Override
    public void update(Warehouse warehouse) {
        String sql = "UPDATE warehouses SET capacity = ? WHERE id = ?";

        try (Connection conn = dbConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setInt(1, warehouse.getCapacity());
            stmt.setLong(2, warehouse.getId());

            stmt.executeUpdate();
        } catch (SQLException e) {
            System.out.println("Error updating warehouse: " + e.getMessage());
        }
    }

    @Override
    public void delete(Long id) {
        String sql = "DELETE FROM warehouses WHERE id = ?";

        try (Connection conn = dbConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setLong(1, id);
            stmt.executeUpdate();
        } catch (SQLException e) {
            System.out.println("Error deleting warehouse: " + e.getMessage());
        }
    }

    private Warehouse mapResultSetToWarehouse(ResultSet rs) throws SQLException {
        Location location = new Location(rs.getFloat("latitude"), rs.getFloat("longitude"));
        Warehouse warehouse = new Warehouse(location, rs.getInt("capacity"));
        warehouse.setId(rs.getLong("id"));
        return warehouse;
    }
}
