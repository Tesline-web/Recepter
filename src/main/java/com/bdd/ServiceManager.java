package com.bdd;

import java.sql.*;

public class ServiceManager {

    public boolean addService(String name, Double value) {
        BddManager bdd = new BddManager();
        Connection connection = bdd.connection();
        String sql_request = "INSERT INTO services (name,amount) VALUES (?,?)";
        try {
            PreparedStatement pstmt = connection.prepareStatement(sql_request);
            pstmt.setString(1, name);
            pstmt.setDouble(2, value);
            return pstmt.execute();
        } catch (SQLException e) {
            System.err.println("Erreur de conversion du prix : " + e.getMessage());
            e.printStackTrace();  // Afficher l'erreur si le prix est invalide
            throw new RuntimeException(e);
        }

    }

    public ResultSet getServices() {
        BddManager bdd = new BddManager();
        Connection connection = bdd.connection();
        ResultSet rs = null;
        String sql_request = "SELECT * FROM services";
        try {
            Statement stmt = connection.createStatement();
            System.out.println(rs);
            return rs = stmt.executeQuery(sql_request);


        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public boolean deleteServiceById(int serviceId) {
        BddManager bdd = new BddManager();
        Connection connection = bdd.connection();
        String sql = "DELETE FROM services WHERE id = ?";

        try (PreparedStatement stmt = connection.prepareStatement(sql)) {
            stmt.setInt(1, serviceId);
            int rowsAffected = stmt.executeUpdate();
            return rowsAffected > 0;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }
}
