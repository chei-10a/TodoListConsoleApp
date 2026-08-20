import java.io.*;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Scanner;

public class Main {

    static ArrayList<Tache> taches = new ArrayList<>();
    static Scanner userInput = new Scanner(System.in);

    public static void main(String[] args) throws IOException, ClassNotFoundException {

        chargerTaches();

        boolean continuer = true;

        while (continuer) {

            System.out.println("\n=== MENU ===");
            verifierRappels();
            System.out.println("1 - Ajouter une tâche");
            System.out.println("2 - Supprimer une tâche");
            System.out.println("3 - Afficher les tâches");
            System.out.println("4 - Terminer une tâche");
            System.out.println("0 - Quitter");
            System.out.print("Choix : ");

            int x = userInput.nextInt();
            userInput.nextLine();

            switch (x) {
                case 1 : ajouterTache();
                case 2 : supprimerTache();
                case 3 : afficherTaches();
                case 4 : terminerTache();
                case 0 : {
                    continuer = false;
                    System.out.println("Au revoir");
                }
                default : System.out.println("Choix invalide");
            }
        }
    }

    // ================= MÉTHODES =================

    static void ajouterTache() throws IOException {
        System.out.print("Intitulé : ");
        String intitule = userInput.nextLine();

        System.out.print("Catégorie : ");
        String categorie = userInput.nextLine();

        System.out.print("Deadline (JJ/MM/AAAA) : ");
        String dateStr = userInput.nextLine();
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy");
        LocalDate deadline = LocalDate.parse(dateStr, formatter);

        System.out.print("Rappel combien de jours avant ? (0 = aucun) : ");
        int rappel = userInput.nextInt();
        userInput.nextLine();

        taches.add(new Tache(intitule, categorie, deadline, rappel));
        sauvegarderTaches();

        System.out.println("Tâche ajoutée");
    }

    static void afficherTaches() {
        if (taches.isEmpty()) {
            System.out.println("Aucune tâche.");
            return;
        }

        for (int i = 0; i < taches.size(); i++) {
            System.out.print(i + " - ");
            taches.get(i).affichageTerminee();
        }
    }

    static void terminerTache() throws IOException {
        afficherTaches();

        System.out.print("Numéro de la tâche : ");
        int index = userInput.nextInt();
        userInput.nextLine();

        if (index >= 0 && index < taches.size()) {
            taches.get(index).marquerTerminee();
            sauvegarderTaches();
            System.out.println("Tâche terminée");
        } else {
            System.out.println("Numéro invalide");
        }
    }

    static void supprimerTache() throws IOException {
        afficherTaches();

        System.out.print("Numéro de la tâche : ");
        int index = userInput.nextInt();
        userInput.nextLine();

        if (index >= 0 && index < taches.size()) {
            taches.remove(index);
            sauvegarderTaches();
            System.out.println("Tâche supprimée");
        } else {
            System.out.println("Numéro invalide");
        }
    }

    static void sauvegarderTaches() throws IOException {
        ObjectOutputStream out = new ObjectOutputStream(new FileOutputStream("GestionDeTaches.ser"));
        out.writeObject(taches);
        out.close();
    }

    static void chargerTaches() {
        try {
            ObjectInputStream in = new ObjectInputStream(new FileInputStream("GestionDeTaches.ser"));
            taches = (ArrayList<Tache>) in.readObject();
            in.close();
        } catch (Exception e) {
            System.out.println("Aucune sauvegarde trouvée.");
        }
    }

    static void verifierRappels() {
        System.out.println("\nRappels du jour");

        boolean rappelTrouve = false;
        for (Tache t : taches) {
            if (t.doitAfficherRappel()) {
                t.affichageTerminee();
                rappelTrouve = true;
            }
        }

        if (!rappelTrouve) {
            System.out.println("Aucun rappel aujourd'hui.");
        }
    }
}
