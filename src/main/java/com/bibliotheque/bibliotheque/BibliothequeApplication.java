package com.bibliotheque.bibliotheque;

// import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Scanner;

@SpringBootApplication
public class BibliothequeApplication {

	public static void main(String[] args) {
		LivreDAO livreDAO = new LivreDAO();
		MembreDAO membreDAO = new MembreDAO();
		EmpruntDAO empruntDAO = new EmpruntDAO();

		Scanner scanner = new Scanner(System.in);
		int choix = 0;

		System.out.println(" ========================================  SYSTÈME DE GESTION DE BIBLIOTHÈQUE  ======================================== ");

		do {
			System.out.println("\n--- MENU PRINCIPAL ---");
			System.out.println("1. AJOUTER UN LIVRE");
			System.out.println("2. RECHERCHER UN LIVRE (Titre/Catégorie)");
			System.out.println("3. INSCRIRE UN MEMBRE");
			System.out.println("4. ENREGISTRER UN EMPRUNT ");
			System.out.println("5. GERER LE RETOUR D'UN LIVRE (Pénalités)");
			System.out.println("6. CATALOGUE DES LIVRES ");
			System.out.println("7. LISTE DES MEMBRES INSCRITS ");
			System.out.println("8. LISTE DES EMPRUNTS PAR MEMBRES ");
			System.out.println("9. Quitter");
			System.out.print("Veuillez choisir une option : ");

			try {
				choix = Integer.parseInt(scanner.nextLine());
			} catch (NumberFormatException e) {
				System.out.println("Erreur : Veuillez entrer un nombre valide.");
				continue;
			}

			switch (choix) {
				case 1:
					System.out.print("Entrez le titre : ");
					String titre = scanner.nextLine();
					System.out.print("Entrez l'auteur : ");
					String auteur = scanner.nextLine();
					System.out.print("Entrez la catégorie : ");
					String cat = scanner.nextLine();
					System.out.print("Nombre d'exemplaires : ");
					int nb = Integer.parseInt(scanner.nextLine());
					Livre nouveauLivre = new Livre(0, titre, auteur, cat, nb);
					livreDAO.ajouterLivre(nouveauLivre);
					break;

				case 2:
					System.out.print("Entrez le titre ou la catégorie à rechercher : ");
					String recherche = scanner.nextLine();
					ArrayList<Livre> resultats = livreDAO.rechercherParTitre(recherche);
					if (resultats.isEmpty()) {
						System.out.println("Aucun livre trouvé.");
					} else {
						for (Livre l : resultats) {
							// System.out.println("le livre " + l.getTitre() + " est " + l.getAuteur() + "a été trouver !");
							l.afficherDetails();
						}
					}
					break;

				case 3:
					System.out.print("Nom du membre : ");
					String nom = scanner.nextLine();
					System.out.print("Prénom du membre : ");
					String prenom = scanner.nextLine();
					System.out.print("Email : ");
					String email = scanner.nextLine();

					Membre nouveauMembre = new Membre(0, nom, prenom, email, LocalDate.now());
					membreDAO.inscrireMembre(nouveauMembre);
					break;

				case 4:
					System.out.print("ID du membre : ");
					int idM = Integer.parseInt(scanner.nextLine());
					System.out.print("ID du livre : ");
					int idL = Integer.parseInt(scanner.nextLine());
					empruntDAO.enregistrerEmprunt(idM, idL);
					break;

				case 5:
					System.out.print("Entrez l'ID de l'emprunt à clôturer : ");
					int idEmprunt = Integer.parseInt(scanner.nextLine());
					empruntDAO.retournerLivre(idEmprunt);
					break;

				case 6:
					System.out.println("\n--- CATALOGUE DES LIVRES ---");
					for (Livre l : livreDAO.listerTousLesLivres()) {
						l.afficherDetails();
					}
					break;

				case 7:
					System.out.println("\n--- LISTE DES MEMBRES INSCRITS ---");
					for (Membre m : membreDAO.listerTousLesMembres()) {
						m.afficherDetails();
					}
					break;

				case 8:
					empruntDAO.listerEmpruntsParMembre();
					break;

				case 9:
					System.out.println("Fermeture de l'application. Au revoir !");
					break;

				default:
					System.out.println("Option invalide, veuillez recommencer.");
			}
		} while (choix != 9);

		scanner.close();
	}
}