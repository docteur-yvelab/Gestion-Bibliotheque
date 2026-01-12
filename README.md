# Système de Gestion de Bibliothèque (Java/JDBC)

Ce projet est une application Java permettant de gérer les livres, les membres et les emprunts d'une bibliothèque. Il utilise une base de données MySQL via phpMyAdmin .
##  Fonctionnalités
- **Gestion des Livres** : Ajout, recherche par titre/catégorie et affichage du catalogue.
- **Gestion des Membres** : Inscription et consultation de la liste des membres.
- **Gestion des Emprunts** : Enregistrement des prêts de livres.
- **Gestion des Retards** : Calcul automatique des pénalités (100 F CFA par jour de retard).

##  Technologies utilisées
- **Langage** : Java (JDK 24)
- **Base de données** : MySQL (via phpMyAdmin)
- **Connecteur** : JDBC (mysql-connector-j)
- **Outil de build** : Maven (ou ajout manuel du .jar)

##  Prérequis & Installation

### 1. Base de données
1. Démarrez les modules **Apache** et **MySQL** via le panneau de contrôle XAMPP.
2. Allez sur `http://localhost/phpmyadmin`.
3. Créez une base de données nommée `gestion_bibliotheque`.
4. Importez le script SQL suivant pour créer les tables :

```sql
CREATE TABLE livres (
    id INT PRIMARY KEY AUTO_INCREMENT,
    titre VARCHAR(255),
    auteur VARCHAR(255),
    categorie VARCHAR(100),
    nombreExemplaires INT
);

CREATE TABLE membres (
    id INT PRIMARY KEY AUTO_INCREMENT,
    nom VARCHAR(100),
    prenom VARCHAR(100),
    email VARCHAR(100),
    adhesionDate DATE
);

CREATE TABLE emprunts (
    idEmprunt INT PRIMARY KEY AUTO_INCREMENT,
    membreId INT,
    livreId INT,
    dateEmprunt DATE,
    dateRetourPrevue DATE,
    dateRetourEffective DATE,
    FOREIGN KEY (membreId) REFERENCES membres(id),
    FOREIGN KEY (livreId) REFERENCES livres(id)
);
