package com.bibliotheque.bibliotheque;

import java.sql.*;
import java.time.LocalDate;
import java.time.temporal.ChronoUnit;

public class EmpruntDAO {
    public void enregistrerEmprunt(int membreId, int livreId) {
        String sql = "INSERT INTO emprunts (membreId, livreId, dateEmprunt, dateRetourPrevue) VALUES (?, ?, ?, ?)";
        try (Connection conn = DBConnection.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
            LocalDate aujourdhui = LocalDate.now();
            pstmt.setInt(1, membreId);
            pstmt.setInt(2, livreId);
            pstmt.setDate(3, Date.valueOf(aujourdhui));
            pstmt.setDate(4, Date.valueOf(aujourdhui.plusDays(14))); // Retour prévu dans 14 jours
            pstmt.executeUpdate();
            System.out.println("Emprunt enregistré.");
        } catch (SQLException e) { e.printStackTrace(); }
    }

    public void retournerLivre(int idEmprunt) {
        String selectSql = "SELECT dateRetourPrevue FROM emprunts WHERE idEmprunt = ?";
        String updateSql = "UPDATE emprunts SET dateRetourEffective = ? WHERE idEmprunt = ?";

        try (Connection conn = DBConnection.getConnection()) {

            PreparedStatement pstmtSelect = conn.prepareStatement(selectSql);
            pstmtSelect.setInt(1, idEmprunt);
            ResultSet rs = pstmtSelect.executeQuery();

            if (rs.next()) {
                LocalDate datePrevue = rs.getDate("dateRetourPrevue").toLocalDate();
                LocalDate dateEffective = LocalDate.now();

                PreparedStatement pstmtUpdate = conn.prepareStatement(updateSql);
                pstmtUpdate.setDate(1, Date.valueOf(dateEffective));
                pstmtUpdate.setInt(2, idEmprunt);
                pstmtUpdate.executeUpdate();

                if (dateEffective.isAfter(datePrevue)) {
                    long jours = ChronoUnit.DAYS.between(datePrevue, dateEffective);
                    System.out.println("Retard de " + jours + " jours. Pénalité : " + (jours * 100) + " F CFA.");
                } else {
                    System.out.println("Livre retourné à temps.");
                }
            }
        } catch (SQLException e) { e.printStackTrace(); }
    }

    public void listerEmpruntsParMembre() {
        String sql = "SELECT m.nom, m.prenom, l.titre, e.dateEmprunt, e.dateRetourPrevue " +
                "FROM emprunts e " +
                "JOIN membres m ON e.membreId = m.id " +
                "JOIN livres l ON e.livreId = l.id " +
                "ORDER BY m.nom";
        try (Connection conn = DBConnection.getConnection();
             Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery(sql)) {
            System.out.println("\n--- LISTE DES EMPRUNTS EFFECTUÉS ---");
            while (rs.next()) {
                System.out.println("Membre: " + rs.getString("prenom") + " " + rs.getString("nom") +
                        " | Livre: " + rs.getString("titre") +
                        " | Emprunté le: " + rs.getDate("dateEmprunt"));
            }
        } catch (SQLException e) { e.printStackTrace(); }
    }
}
