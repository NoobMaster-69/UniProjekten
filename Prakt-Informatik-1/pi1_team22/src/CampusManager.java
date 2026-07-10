import java.util.Scanner;

public class CampusManager {

    static final Scanner SC = new Scanner(System.in);

    // Liest eine ganze Zeile ein (String)
    static String readLine(String prompt) {
        System.out.print(prompt);
        return SC.nextLine();
    }

    // Liest eine ganze Zahl ein (int), inkl. Fehlerbehandlung
    static int readInt(String prompt) {
        while (true) {
            try {
                return Integer.parseInt(readLine(prompt).trim());
            } catch (Exception e) {
                System.out.println("Bitte eine ganze Zahl eingeben.");
            }
        }
    }

    // Liest erstes Zeichen (char), als Großbuchstabe, bei leerer Eingabe '\n'
    static char readChar(String prompt) {
        String s = readLine(prompt).trim();
        return s.isEmpty() ? '\n' : Character.toUpperCase(s.charAt(0));
    }

    public static void main(String[] args) {

        // Hier kommt die Logik für Aufgabe 3 (CampusManager 2.0) hin:

        // Parallele Arrays für die Module anlegen, z.B.:
        String[] modulName = new String[20];
        int[] modulEcts = new int[20];
        int[] modulNote = new int[20];
        int anzahlModule = 0; // Anzahl aktuell gespeicherter Module
        boolean runningBucle = true;

        // While-Schleife für das MENU
        while (runningBucle) {
            System.out.println();
            System.out.println("Willkomen zur MENU des Campus-Management-Systems ");
            System.out.println("Menü: ");
            System.out.println("(1) Modul hinzufügen ");
            System.out.println("(2) Alle Module anzeigen ");
            System.out.println("(3)  Modul löschen ");
            System.out.println("(4)  Statistik anzeigen ");
            System.out.println("(5)  Notenänderung (optional)");
            System.out.println("(6) Sortieren (optional) ");
            System.out.println("(0) Beenden ");

            int auswahl = readInt(" \n Bitte gibt Ihre Wahl: ");

            switch (auswahl) {
                case 1:
                    anzahlModule = modulHinzufuegen(modulName, modulEcts, modulNote, anzahlModule);
                    break;
                case 2:
                    moduleAnzeigen(modulName, modulEcts, modulNote, anzahlModule);
                    break;
                case 3:
                    anzahlModule = modulLoeschen(modulName, modulEcts, modulNote, anzahlModule);
                    break;
                case 4:
                    statistik(modulName, modulEcts, modulNote, anzahlModule);
                    break;
                case 5:
                    noteAendern(modulName, modulNote, anzahlModule);
                    break;
                case 6:
                    sortieren(modulName, modulEcts, modulNote, anzahlModule);
                    break;
                case 0:
                    runningBucle = false;
                    System.out.println("Programm wird beendet. Auf Wiedersehen!");
                    break;
                default:
                    System.out.println("Ungültige Eingabe. Bitte wählen Sie eine gültige Option.");

            }// Switch ENDE

        } // While ENDE
    }

    // Hier können später eigene METHODEN für die eigentliche Logik hinzukommen
    // z.B.:

            // - modulHinzufuegen()
    static String modulename() {
        String name;
        do {
            name = readLine("Geben Sie den Namen des Moduls ein: ");
            if (!name.trim().isEmpty())
                break;
            System.out.println("Der Modulname darf nicht leer sein. Bitte versuchen Sie es erneut.");
        } while (true);
        return name.trim();
    }

    static int ectspunkte() {
        int ects;
        do {
            ects = readInt("Geben Sie die ECTS-Punkte des Moduls ein ( etcs-punkte > 0 ): ");
            if (ects > 0) {
                break;
            } else {
                System.out.println("ECTS-Punkte müssen größer als 0 sein. Bitte versuchen Sie es erneut.");
            }
        } while (true);
        return ects;
    }

    static int noten() {
        int note;
        do {
            note = readInt("Geben Sie die Note des Moduls ein (1-5): ");
            if (note >= 1 && note <= 5) {
                break;
            } else {
                System.out.println("Die Note muss zwischen 1 und 5 liegen. Bitte versuchen Sie es erneut.");
            }
        } while (true);
        return note;
    }

    static int modulHinzufuegen(String[] namen, int[] ects, int[] noten, int anzahlModule) {
        if (anzahlModule >= namen.length) {
            System.out.println("Maximale Anzahl an Modulen erreicht.");
            return anzahlModule;
        }

        String name = modulename();
        int ectsPunkte = ectspunkte();
        int note = noten();

        namen[anzahlModule] = name;
        ects[anzahlModule] = ectsPunkte;
        noten[anzahlModule] = note;

        System.out.println("Modul erfolgreich hinzugefügt.");
        return anzahlModule + 1;
    }

            // - moduleAnzeigen()
    static void moduleAnzeigen(String[] namen, int[] ects, int[] noten, int anzahlModule) {
        if (anzahlModule == 0) {
            System.out.println("Es wurden noch keine Module angelegt.");
            return;
        }
        System.out.println("MODULINFORMATIONEN:");
        for (int i = 0; i < anzahlModule; i++) {
            System.out.println("Modul " + (i + 1) + ":");
            System.out.println("Name: " + namen[i]);
            System.out.println("ECTS: " + ects[i]);
            System.out.println("Note: " + noten[i]);
            System.out.println();
        }
    }

            // - modulLoeschen()
    static int modulLoeschen(String[] namen, int[] ects, int[] noten, int anzahlModule) {
        if (anzahlModule == 0) {
            System.out.println("Es sind keine Module vorhanden, die gelöscht werden können.");
            return anzahlModule;
        }
        int nr = readInt("Welche Modulnummer soll gelöscht werden (1-" + anzahlModule + ")? ");
        if (nr < 1 || nr > anzahlModule) {
            System.out.println("Ungültige Modulnummer.");
            return anzahlModule;
        }
        
        int index = nr - 1;
        // keine alten Daten hängen bleiben
        for (int i = index; i < anzahlModule - 1; i++) {
            namen[i] = namen[i + 1];
            ects[i] = ects[i + 1];
            noten[i] = noten[i + 1];
        }

        namen[anzahlModule - 1] = null;
        ects[anzahlModule - 1] = 0;
        noten[anzahlModule - 1] = 0;

        System.out.println("Modul erfolgreich gelöscht.");
        return anzahlModule - 1;
    }

            // - statistik()
    static void statistik(String[] namen, int[] ects, int[] noten, int anzahlModule) {
        if (anzahlModule == 0) {
            System.out.println("Es sind keine Module vorhanden, um eine Statistik zu erstellen.");
            return;
        }

        int sumEcts = 0;
        double sumNoten = 0;
        int summeGewichteteNote=0;


        int BestandeneModule = 0;
        int BesteNote=5;
        int SchlechteNote=0;

        for (int i = 0; i < anzahlModule; i++) {
            sumEcts += ects[i];
            summeGewichteteNote += noten[i] * ects[i];
            sumNoten += (double)(noten[i] * ects[i]) / sumEcts;
            //sumNoten += noten[i];

            if (noten[i] <= 4 ){
                BestandeneModule++;
            }
            if (noten[i]< BesteNote){
                BesteNote=noten[i];
            }
            if (noten[i]> SchlechteNote){
                SchlechteNote=noten[i];
            }
        }

        //double durchschnittNote = sumNoten / anzahlModule;
        double durchschnittNote;
        if(sumEcts==0){
            durchschnittNote = 0.0;     //FALLS alle Ects 0 wären, Division durch 0 vermeiden
        } else {
            durchschnittNote = (double) summeGewichteteNote / sumEcts;
        }

        System.out.println("STATISTIK:");
        System.out.println("Gesamt-ECTS: " + sumEcts);
        System.out.println("Bestandene Module: " + BestandeneModule);
        System.out.println("Beste Note: " + BesteNote);
        System.out.println("Schlechteste Note: " + SchlechteNote);
        System.out.printf("Durchschnittsnote: %.2f%n", durchschnittNote);
    }


            // - noteAendern() (optional)
    static void noteAendern(String[] namen, int[] noten, int anzahlModule) {
        if (anzahlModule == 0) {
            System.out.println("Es sind keine Module vorhanden, deren Note geändert werden kann.");
            return;
        }
        int nr = readInt("Für welches Modul soll die Note geändert werden (1-" + anzahlModule + ")? ");
        if (nr < 1 || nr > anzahlModule) {
            System.out.println("Ungültige Modulnummer.");
            return;
        }
        int index = nr - 1;
        int neueNote;
        do {
            neueNote = readInt("Neue Note für " + namen[index] + " (1-5): ");
            if (neueNote < 1 || neueNote > 5) {
                System.out.println("Die Note muss zwischen 1 und 5 liegen. Bitte versuchen Sie es erneut.");
            }
        } while (neueNote < 1 || neueNote > 5);
        noten[index] = neueNote;
        System.out.println("Note erfolgreich geändert.");
    }

    /// sortieren() (optional)
    ///
    ///
    static void sortiereNachNote(String[] namen, int[] ects, int[] noten, int anzahlModule) {
        boolean vertauscht;
        do {
            vertauscht = false;
            for (int i = 0; i < anzahlModule - 1; i++) {
                if (noten[i] > noten[i + 1]) {
                    int tmp = noten[i];
                    int tmpEcts = ects[i];
                    String tmpName = namen[i];
                    noten[i] = noten[i + 1];
                    ects[i] = ects[i + 1];
                    namen[i] = namen[i + 1];
                    noten[i + 1] = tmp;
                    ects[i + 1] = tmpEcts;
                    namen[i + 1] = tmpName;
                    vertauscht = true;
                }
            }
        } while (vertauscht);

    }

    static void sortiereNachEcts(String[] namen, int[] ects, int[] noten, int anzahlModule) {
        boolean vertauscht;
        do {
            vertauscht = false;
            for (int i = 0; i < anzahlModule - 1; i++) {
                if (ects[i] > ects[i + 1]) {
                    int tmp = noten[i];
                    int tmpEcts = ects[i];
                    String tmpName = namen[i];
                    noten[i] = noten[i + 1];
                    ects[i] = ects[i + 1];
                    namen[i] = namen[i + 1];
                    noten[i + 1] = tmp;
                    ects[i + 1] = tmpEcts;
                    namen[i + 1] = tmpName;
                    vertauscht = true;
                }
            }
        } while (vertauscht);

    }

    static void sortiereNachName(String[] namen, int[] ects, int[] noten, int anzahlModule) {
        boolean vertauscht;
        do {
            vertauscht = false;
            for (int i = 0; i < anzahlModule - 1; i++) {
                if (namen[i].compareTo(namen[i + 1]) > 0) {
                    int tmp = noten[i];
                    int tmpEcts = ects[i];
                    String tmpName = namen[i];
                    noten[i] = noten[i + 1];
                    ects[i] = ects[i + 1];
                    namen[i] = namen[i + 1];
                    noten[i + 1] = tmp;
                    ects[i + 1] = tmpEcts;
                    namen[i + 1] = tmpName;
                    vertauscht = true;
                }
            }
        } while (vertauscht);
    }

    static void sortieren(String[] namen, int[] ects, int[] noten, int anzahlModule) {
        if (anzahlModule == 1 || anzahlModule == 0) {
            System.out.println(" Es sind nicht genügend Module zum Sortieren vorhanden.");
            return;
        }

        switch (readChar("Sortieren nach Name (N), ECTS (E) oder Note (O)? ")) {
            case 'N':
                sortiereNachName(namen, ects, noten, anzahlModule);
                System.out.println("Module wurden nach Name sortiert.");
                break;
            case 'E':
                sortiereNachEcts(namen, ects, noten, anzahlModule);
                System.out.println("Module wurden nach ECTS sortiert.");
                break;
            case 'O':
                sortiereNachNote(namen, ects, noten, anzahlModule);
                System.out.println("Module wurden nach Note sortiert.");
                break;
            default:
                System.out.println("Ungültige Auswahl. Bitte wählen Sie N, E oder O.");
        }

    }
}//Class CampusManager ENDE
