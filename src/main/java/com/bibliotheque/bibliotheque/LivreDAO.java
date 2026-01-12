package com.bibliotheque.bibliotheque;

import java.sql.*;
import java.util.ArrayList;

public class LivreDAO {
    public void ajouterLivre(Livre livre) {
        String sql = "INSERT INTO livres (titre, auteur, categorie, nombreExemplaires) VALUES (?, ?, ?, ?)";
        try (Connection conn = DBConnection.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setString(1, livre.getTitre());
            pstmt.setString(2, livre.getAuteur());
            pstmt.setString(3, livre.getCategorie());
            pstmt.setInt(4, livre.getNombreExemplaires());
            pstmt.executeUpdate();
            System.out.println("Livre ajouté avec succès !");
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public ArrayList<Livre> rechercherParTitre(String titre) {
        ArrayList<Livre> liste = new ArrayList<>();
        String sql = "SELECT * FROM livres WHERE titre LIKE ?";
        try (Connection conn = DBConnection.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setString(1, "%" + titre + "%");
            ResultSet rs = pstmt.executeQuery();
            while (rs.next()) {
                liste.add(new Livre(rs.getInt("id"), rs.getString("titre"),
                        rs.getString("auteur"), rs.getString("categorie"),
                        rs.getInt("nombreExemplaires")));
            }
        } catch (SQLException e) { e.printStackTrace(); }
        return liste;
    }

    public ArrayList<Livre> listerTousLesLivres() {
        ArrayList<Livre> liste = new ArrayList<>();
        String sql = "SELECT * FROM livres";
        try (Connection conn = DBConnection.getConnection();
             Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery(sql)) {
            while (rs.next()) {
                liste.add(new Livre(rs.getInt("id"), rs.getString("titre"),
                        rs.getString("auteur"), rs.getString("categorie"),
                        rs.getInt("nombreExemplaires")));
            }
        } catch (SQLException e) { e.printStackTrace(); }
        return liste;
    }

}
