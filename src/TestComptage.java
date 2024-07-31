
import org.junit.*;
import org.junit.runner.*;
import static org.junit.Assert.*;
import org.junit.runners.MethodSorters;

import modele.entities.*;
import java.sql.Date;
import java.util.Arrays;

/**
 * Classe de test de la classe Comptage
 * Utilise JUnit et test les cas normaux, les cas limites et les cas d'erreurs
 * @author Groupe 4B2
 */
@FixMethodOrder(MethodSorters.JVM)
public class TestComptage {
    private Comptage comptage;

    @Before
    public void instancier() {
        int[] passages = new int[]{1,2,3,4,5,6,7,8,9,10,11,12,13,14,15,16,17,18,19,20,21,22,23,24};
        Quartier quartier = new Quartier(1, "Quartier 1", 100);
        Compteur compteur = new Compteur(1, "Compteur 1", "Sens 1", 1.0f, 1.0f, quartier);
        DateInfo dateInfo = new DateInfo(new Date(1609455600000L), 10, Jour.Lundi, Vacances.Nulle);
        this.comptage = new Comptage(passages, PresenceAnomalie.Nulle, compteur, dateInfo);
    }

    @Test
    public void testGetPassageNormal() {
        System.out.println("testGetPassage : cas normal");
        assertEquals(5, comptage.getPassage(4));
        System.out.println("Test réussi : " + comptage.getPassage(4));
    }

    @Test
    public void testGetPassageLimite() {
        System.out.println("testGetPassage : cas limite");
        assertEquals(1, comptage.getPassage(0));
        System.out.println("Test réussi : " + comptage.getPassage(0));
    }

    @Test
    public void testGetPassageErreur() {
        System.out.println("testGetPassage : cas d'erreurs");
        try{
            comptage.getPassage(25);
            fail("IllegalArgumentException expected");
        } catch (IllegalArgumentException e){
            System.out.println("Test réussi : " + e.getMessage());
        }
    }

    @Test
    public void testSetPassageNormal() {
        System.out.println("testSetPassage : cas normal");
        comptage.setPassage(4, 10);
        assertEquals(10, comptage.getPassage(4));
        System.out.println("Test réussi : " + comptage.getPassage(4));
    }

    @Test
    public void testSetPassageErreur() {
        System.out.println("testSetPassage : cas d'erreurs");
        try{
            comptage.setPassage(25, 10);
            fail("IllegalArgumentException expected");
        } catch (IllegalArgumentException e){
            System.out.println("Test réussi : " + e.getMessage());
        }
    }

    @Test
    public void testGetPassagesNormal() {
        System.out.println("testGetPassages : cas normal");
        int[] passages = new int[]{1,2,3,4,5,6,7,8,9,10,11,12,13,14,15,16,17,18,19,20,21,22,23,24};
        assertArrayEquals(passages, comptage.getPassages());
        System.out.println("Test réussi : " + Arrays.toString(comptage.getPassages()));
    }

    @Test
    public void testSetPassagesNormal() {
        System.out.println("testSetPassages : cas normal");
        int[] passages = new int[]{24,23,22,21,20,19,18,17,16,15,14,13,12,11,10,9,8,7,6,5,4,3,2,1};
        comptage.setPassages(passages);
        assertArrayEquals(passages, comptage.getPassages());
        System.out.println("Test réussi : " + Arrays.toString(comptage.getPassages()));
    }

    @Test
    public void testSetPassagesErreur() {
        System.out.println("testSetPassages : cas d'erreurs");
        try{
            int[] passages = new int[]{1,2,3,4,5,6,7,8,9,10,11,12,13,14};
            comptage.setPassages(passages);
            fail("IllegalArgumentException expected");
        } catch (IllegalArgumentException e){
            System.out.println("Test réussi : " + e.getMessage());
        }
    }

    @Test
    public void testGetAnomalieNormal() {
        System.out.println("testGetAnomalie : cas normal");
        assertEquals(PresenceAnomalie.Nulle, comptage.getAnomalie());
        System.out.println("Test réussi : " + comptage.getAnomalie());
    }

    @Test
    public void testSetAnomalieNormal() {
        System.out.println("testSetAnomalie : cas normal");
        comptage.setAnomalie(PresenceAnomalie.Forte);
        assertEquals(PresenceAnomalie.Forte, comptage.getAnomalie());
        System.out.println("Test réussi : " + comptage.getAnomalie());
    }

    @Test
    public void testSetAnomalieErreur() {
        System.out.println("testSetAnomalie : cas d'erreurs");
        try{
            comptage.setAnomalie(null);
            fail("IllegalArgumentException expected");
        } catch (IllegalArgumentException e){
            System.out.println("Test réussi : " + e.getMessage());
        }
    }

    @Test
    public void testGetLeCompteurNormal() {
        System.out.println("testGetLeCompteur : cas normal");
        Compteur compteur = comptage.getLeCompteur();
        assertEquals("Compteur 1", compteur.getNomCompteur());
        System.out.println("Test réussi : " + compteur.getNomCompteur());
    }

    @Test
    public void testSetLeCompteurNormal() {
        System.out.println("testSetLeCompteur : cas normal");
        Quartier quartier = new Quartier(2, "Quartier 2", 200);
        Compteur compteur = new Compteur(2, "Compteur 2", "Sens 2", 2.0f, 2.0f, quartier);
        comptage.setLeCompteur(compteur);
        Compteur newCompteur = comptage.getLeCompteur();
        assertEquals("Compteur 2", newCompteur.getNomCompteur());
        System.out.println("Test réussi : " + newCompteur.getNomCompteur());
    }

    @Test
    public void testSetLeCompteurErreur() {
        System.out.println("testSetLeCompteur : cas d'erreurs");
        try{
            comptage.setLeCompteur(null);
            fail("IllegalArgumentException expected");
        } catch (IllegalArgumentException e){
            System.out.println("Test réussi : " + e.getMessage());
        }
    }

    @Test
    public void testGetLaDateNormal() {
        System.out.println("testGetLaDate : cas normal");
        DateInfo laDate = comptage.getLaDate();
        assertEquals(new Date(1609455600000L), laDate.getDate());
        System.out.println("Test réussi : " + laDate.getDate());
    }

    @Test
    public void testSetLaDateNormal() {
        System.out.println("testSetLaDate : cas normal");
        DateInfo dateInfo = new DateInfo(new Date(1612047600000L), 20, Jour.Mardi, Vacances.Noel);
        comptage.setLaDate(dateInfo);
        assertEquals(new Date(1612047600000L), comptage.getLaDate().getDate());
        System.out.println("Test réussi : " + comptage.getLaDate().getDate());
    }

    @Test
    public void testSetLaDateErreur() {
        System.out.println("testSetLaDate : cas d'erreurs");
        try{
            comptage.setLaDate(null);
            fail("IllegalArgumentException expected");
        } catch (IllegalArgumentException e){
            System.out.println("Test réussi : " + e.getMessage());
        }
    }

    @Test
    public void testToStringNormal() {
        System.out.println("testToString : cas normal");
        String expected = "Comptage(laDate : 2021-01-01, leCompteur : 1, anomalie : Nulle, passages : [1, 2, 3, 4, 5, 6, 7, 8, 9, 10, 11, 12, 13, 14, 15, 16, 17, 18, 19, 20, 21, 22, 23, 24])";
        assertEquals(expected, comptage.toString());
        System.out.println("Test réussi : " + comptage.toString());
    }

    @Test
    public void testTotalPassagesNormal() {
        System.out.println("testTotalPassages : cas normal");
        assertEquals(300, comptage.totalPassages());
        System.out.println("Test réussi : " + comptage.totalPassages());
    }

    @Test
    public void testAveragePassagesNormal() {
        System.out.println("testAveragePassages : cas normal");
        assertEquals(12.5f, comptage.averagePassages(), 0.001);
        System.out.println("Test réussi : " + comptage.averagePassages());
    }

    public static void main (String args[]) {
        JUnitCore.main("TestComptage");
    }
}
