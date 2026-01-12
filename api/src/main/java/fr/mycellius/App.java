package fr.mycellius;
public class App {
    public static void main(String[] args) {
        int nombreDePages = 0;
        double tempsMoyenLecture = 3.5;
        boolean wikiActif = true;
        String nomProjet = "Mycellius";
        System.out.println("Mycellius démarre !");
        System.out.println("Bienvenue dans la séance 2 (bases Java).");
        System.out.println("Nom du projet : " + nomProjet);
        System.out.println("Nombre de pages : " + nombreDePages);
        System.out.println("Temps moyen de lecture : " + tempsMoyenLecture + " minutes");
        System.out.println("Wiki actif ? " + wikiActif);

        System.out.println("--- Mise à jour des statistiques ---");
        nombreDePages = nombreDePages + 5;
        tempsMoyenLecture = tempsMoyenLecture + 0.2;
        System.out.println("Nouveau nombre de pages : " + nombreDePages);
        System.out.println("Nouveau temps moyen de lecture : " + tempsMoyenLecture + " minutes");

        if (nombreDePages == 0) {
            System.out.println("Le Wiki est vide pour l'instant.");
        } else {
            System.out.println("Le wiki contient déjà des pages");
        }

        System.out.println("--- Simulation de chargement ---");
        for (int i = 1; i <= 3; i++) {
            System.out.println("Chargement... étape " + i);
        }

        System.out.println("--- Démo POO avec encapsulation ---");
        Person bob = new Person("Bob", 30);
        bob.sayHello();
        System.out.println("Nom (via getter) : " + bob.getName());
    }
}
