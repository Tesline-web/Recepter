package com.bdd;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

public class FactureManager {
    public void addFacture(int num_facture,boolean status,double amount,int id_client){
        BddManager bm = new BddManager();
        Connection connection =bm.connection();
        String sql_request = "INSERT INTO facture (num_facture,status,amount,id_client)" + " Values(?,?,?,?)";
        try {
            PreparedStatement pstmt = connection.prepareStatement(sql_request);
            pstmt.setInt(1,num_facture);
            pstmt.setBoolean(2,status);
            pstmt.setDouble(3,amount);
            pstmt.setInt(4,id_client);
            pstmt.execute();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }
    public void getFacture(){

    }
}
