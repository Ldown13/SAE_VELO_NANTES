import java.io.IOException;
import java.io.PrintWriter;
import java.sql.Date;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Arrays;

import modele.dao.*;
import modele.database.*;
import modele.entities.*;

/**
 * Scenario presentant le modele 
 * Après etre lancé, des updates sont effectués sur la base de données
 * Donc il faut relancer le script SQL pour retrouver la base de données initiale
 * Le scenario marche sans mais les modifications ne seront pas visibles
 * @author Groupe 4B2
 */
public class ScenarioMySQL {
    public static void main(String[] args) {
        try {
            // ---------------- Setup ---------------- //

            System.out.println("Connexion à la base de données");
            Database database = new Database("jdbc:mysql://localhost:3306/bd_velo_4b2");
            database.openReadConnection("read_4b2", "read_4b2");
            database.openWriteConnection("write_4b2", "write_4b2");

            QuartierDao quartierDao = new QuartierDao(database);
            DateInfoDao dateInfoDao = new DateInfoDao(database);
            CompteurDao compteurDao = new CompteurDao(database, quartierDao);
            ComptageDao comptageDao = new ComptageDao(database, compteurDao, dateInfoDao);

            System.out.println("Lecture des données");
            quartierDao.readAll();
            dateInfoDao.readAll();
            compteurDao.readAll();
            comptageDao.readAll();
            
            ArrayList<Quartier> lesQuartiers = quartierDao.getAll();
            ArrayList<DateInfo> lesDates = dateInfoDao.getAll();
            ArrayList<Compteur> lesCompteurs = compteurDao.getAll();
            ArrayList<Comptage> lesComptages = comptageDao.getAll();

            // ---------------- Ecriture dans le fichier ---------------- //

            System.out.println("Ecriture dans le fichier output.txt");
            PrintWriter file = new PrintWriter("output.txt");

            DateInfo debut = null;
            DateInfo fin = null;
            Date janvierDate = new Date(1609455600000L);
            DateInfo janvier = dateInfoDao.get(janvierDate);

            for(Quartier quartier : lesQuartiers) {
                file.println(quartier);
            }
            for(DateInfo date : lesDates) {
                file.println(date);
                if(debut == null || date.getDate().compareTo(debut.getDate()) < 0) {
                    debut = date;
                }
                if(fin == null || date.getDate().compareTo(fin.getDate()) > 0) {
                    fin = date;
                }
            }
            for(Compteur compteur : lesCompteurs) {
                file.println(compteur);
            }
            for(Comptage comptage : lesComptages) {
                file.println(comptage);
            }

            file.close();
            System.out.println("Fichier output.txt créé");
            System.out.println();

            // ---------------- Utilisation des methodes ---------------- //

            System.out.println("Total de passages pour le premier quartier toutes dates :");
            System.out.println(lesQuartiers.get(0).totalPassages(debut, fin));
            System.out.println();


            System.out.println("Total des passages par jour pour le premier quartier a partir du 1er janvier 2021 :");
            int[] total = lesQuartiers.get(0).totalPassagesPerDay(janvier, fin);
            System.out.println(Arrays.toString(total));
            System.out.println();

            System.out.println("Moyenne des passages pour le deuxieme compteur a partir du 1er janvier 2021 :");
            System.out.println(lesCompteurs.get(1).averagePassages(janvier, fin));
            System.out.println();

            System.out.println("Moyenne des passages par heure pour le deuxieme compteur toutes dates :");
            float[] average = lesCompteurs.get(1).averagePassagesPerHour(debut, fin);
            System.out.println(Arrays.toString(average));
            System.out.println();

            System.out.println("Total des passages le 1er janvier 2021");
            System.out.println(janvier.totalPassages());
            System.out.println();

            System.out.println("Moyenne du nombre de passages par heure le 1er janvier 2021");
            System.out.println(Arrays.toString(janvier.averagePassagesPerHour()));
            System.out.println();

            System.out.println("Modification du nom du quartier Centre Ville");
            System.out.println(lesQuartiers.get(0));
            lesQuartiers.get(0).setNomQuartier("Nouveau nom");
            System.out.println(lesQuartiers.get(0));
            quartierDao.update(lesQuartiers.get(0));
            System.out.println("Update dans la base de données effectué");
            System.out.println();

            System.out.println("Modification du sense du compteur 1");
            System.out.println(lesCompteurs.get(0));
            lesCompteurs.get(0).setSens("Nord");
            System.out.println(lesCompteurs.get(0));
            compteurDao.update(lesCompteurs.get(0));
            System.out.println("Update dans la base de données effectué");
            System.out.println();

            System.out.println("Modification du nombre de passages du comptage 1");
            System.out.println(lesComptages.get(0));
            lesComptages.get(0).setPassage(4, 999);
            System.out.println(lesComptages.get(0));
            comptageDao.update(lesComptages.get(0));
            System.out.println("Update dans la base de données effectué");
            System.out.println();

            System.out.println("Suppression du comptage 1 dans le compteur 2");
            ArrayList<Comptage> comptages = lesCompteurs.get(1).getLesComptages();
            System.out.println("size : " + lesCompteurs.get(1).getLesComptages().size());
            lesCompteurs.get(1).removeComptage(comptages.get(0));
            lesComptages.remove(comptages.get(0));
            System.out.println("size : " + lesCompteurs.get(1).getLesComptages().size());
            comptageDao.remove(comptages.get(0));
            System.out.println("Remove dans la base de données effectué");
            System.out.println();

            System.out.println("Creation d'un nouveau compteur");
            Compteur compteur = new Compteur(2000, "Nouveau compteur", "Sud", 0, 0, lesQuartiers.get(1));
            compteurDao.add(compteur);
            lesQuartiers.get(1).addCompteur(compteur);
            System.out.println(compteur);
            System.out.println("Insertion dans la base de données effectué");

            database.closeReadConnection();
            database.closeWriteConnection();

        } catch (SQLException exception) {
            System.out.println("Error with the database");
            System.out.println(exception.getMessage());
        } catch (IOException exception) {
            System.out.println("Error while writing to file");
            System.out.println(exception.getMessage());
        }
    }
}
