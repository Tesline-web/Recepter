package com.bdd;

import java.sql.*;

public class ClientManager {

    public boolean addClients(String brand, String siret, String mail, String adress) {
        BddManager manager = new BddManager();
        Connection connection = manager.connection();
        String sql_request = "INSERT INTO clients (brand,siret,adress,mail) VALUES (?,?,?,?)";
        try {
            PreparedStatement pstmt = connection.prepareStatement(sql_request);
            pstmt.setString(1, brand);
            pstmt.setString(2, siret);
            pstmt.setString(3, adress);
            pstmt.setString(4, mail);
            return pstmt.execute();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public ResultSet getClients() {
        BddManager manager = new BddManager();
        Connection connection = manager.connection();
        String sql_request = "SELECT * FROM clients";
        try {
            Statement stmt = connection.createStatement();
            return stmt.executeQuery(sql_request);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public boolean deleteClientById(int clientId) {
                BddManager manager = new BddManager();
                Connection connection = manager.connection();
                String sql = "DELETE FROM clients WHERE id = ?";
        try (PreparedStatement pstmt = connection.prepareStatement(sql)) {
                    pstmt.setInt(1, clientId);
                    int rowsAffected = pstmt.executeUpdate();
                    return rowsAffected > 0;
                } catch (SQLException e) {
                    e.printStackTrace();
                    return false;
                }
            }
        }
