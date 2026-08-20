package TodoListApp;

import java.io.Serializable;
import java.time.LocalDate;
import java.time.temporal.ChronoUnit;

public class Tache implements Serializable {

    private String intitule;
    private String categorie;
    private boolean terminee;
    private LocalDate deadline;
    private int rappelJoursAvant;

    public Tache(String intitule, String categorie, LocalDate deadline, int rappelJoursAvant) {
        this.intitule = intitule;
        this.categorie = categorie;
        this.terminee = false;
        this.deadline = deadline;
        this.rappelJoursAvant = rappelJoursAvant;
    }

    // Vérifie si un rappel doit être affiché aujourd'hui
    public boolean doitAfficherRappel() {
        LocalDate aujourdHui = LocalDate.now();
        LocalDate dateRappel = deadline.minusDays(rappelJoursAvant);
        return !terminee && aujourdHui.equals(dateRappel);

    }


    public void marquerTerminee() {
        this.terminee = true;
    }

    // Affichage avec jours restants / retard
    public void affichageTerminee() {
        LocalDate aujourdHui = LocalDate.now();
        long joursDiff = ChronoUnit.DAYS.between(aujourdHui, deadline);

        String etatDeadline;

        if (joursDiff > 0) {
            etatDeadline = " | Dans " + joursDiff + " jour(s)";
        } else if (joursDiff == 0) {
            etatDeadline = " | Deadline aujourd'hui";
        } else {
            etatDeadline = " |En retard de " + Math.abs(joursDiff) + " jour(s)";
        }

        System.out.println(
            "Tâche : " + intitule +
            " | Catégorie : " + categorie +
            " | Deadline : " + deadline +
            etatDeadline +
            " | Rappel : " + rappelJoursAvant + " jour(s) avant deadline" +
            (terminee ? " [DONE]" : " [NOT DONE]")
        );
    }
}

