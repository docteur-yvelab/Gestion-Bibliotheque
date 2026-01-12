package com.bibliotheque.bibliotheque;

import java.sql.*;
import java.util.ArrayList;

public class MembreDAO {
    public void inscrireMembre(Membre membre) {

        String sql = "INSERT INTO membres (nom, prenom, email, adhesionDate) VALUES (?, ?, ?, ?)";
        try (Connection conn = DBConnection.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setString(1, membre.getNom());
            pstmt.setString(2, membre.getPrenom());
            pstmt.setString(3, membre.getEmail());
            pstmt.setDate(4, Date.valueOf(membre.getAdhesionDate()));
            pstmt.executeUpdate();
            System.out.println("Membre inscrit avec succès !");
        } catch (SQLException e) { e.printStackTrace(); }
    }

    public ArrayList<Membre> listerTousLesMembres() {
        ArrayList<Membre> liste = new ArrayList<>();
        String sql = "SELECT * FROM membres";
        try (Connection conn = DBConnection.getConnection();
             Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery(sql)) {
            while (rs.next()) {
                liste.add(new Membre(rs.getInt("id"), rs.getString("nom"),
                        rs.getString("prenom"), rs.getString("email"),
                        rs.getDate("adhesionDate").toLocalDate()));
            }
        } catch (SQLException e) { e.printStackTrace(); }
        return liste;
    }
}
