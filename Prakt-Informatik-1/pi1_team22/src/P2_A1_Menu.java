import java.util.Scanner;// Hier Imports (z.B. Scanner)

public class P2_A1_Menu {
    // Vaiablen für die Modulinformationen
    public static String modulName = " ";
    public static int etcs = 0;
    public static int note = 0;
    public static boolean modulenAngelegt = false;

    // Hier kommt der gemeinsame Scanner hin
    static Scanner scanner1 = new Scanner(System.in);
    // Hier kommen die Hilfsmethoden für die Eingabe hin
    // z.B.:
    // - readLine(String prompt): liest eine komplette Textzeile ein
    // - readInt(String prompt): liest eine ganze Zahl ein (mit Fehlermeldung bei
    // falscher Eingabe)
    // - readChar(String prompt): liest ein einzelnes Zeichen ein (erster Buchstabe,
    // als Großbuchstabe)

    ////// HILFEMETHODE/////
    static String readLine(String prompt) {
        System.out.println(prompt);
        return scanner1.nextLine();
    }

    static int readInt(String prompt) {
        while (true) {
            try {
                return Integer.parseInt(readLine(prompt).trim());
            } catch (Exception e) {
                System.out.println("Bitte eine ganze Zahl eingeben.");
            }
        }
    }

    static char readChar(String prompt) {
        String s = readLine(prompt).trim();

        // Gibt die ersten Buchstaben als Grossbuchstaben züruck, oder leer Zeile wenn
        // nichts eingegeben wurde
        return s.isEmpty() ? '\n' : Character.toUpperCase(s.charAt(0));
    }

    ///////////////////////////////////////////
    ////////////// MAIN ///////////////////////
    ///////////////////////////////////////////

    public static void main(String[] args) {

        // Hier kommt die Menülogik hin
        boolean runningBucle = true;

        // While-Schleife für das MENU
        while (runningBucle) {
            System.out.println();
            System.out.println("Willkomen zur MENU des Campus-Management-Systems ");
            System.out.println("Menü: ");
            System.out.println("(M) Modul anlegen / bearbeiten ");
            System.out.println("(A) Modul anzeigen ");
            System.out.println("(N) Note setzen (1 -> 5) ");
            System.out.println("(B) Bonusregel prüfen (ECTS %3 ==0)");
            System.out.println("(P) Fortschrittsbalken ausgeben ");
            System.out.println("(T) Zeitraster ausgeben ");
            System.out.println("(X) Beenden ");

            char auswahl1 = readChar(" \n Bitte gibt Ihre Wahl: ");

            switch (auswahl1) {
                case 'M':
                    modulenAngelegenBearbeiten();
                    break;
                case 'A':
                    modulAnzeigen();
                    break;
                case 'N':
                    noteSetzen();
                    break;
                case 'B':
                    bonusregelPruefen();
                    break;
                case 'P':
                    fortschrittsbalkenAusgeben();
                    break;
                case 'T':
                    zeitrasterAusgeben();
                    break;
                case 'X':
                    if (beendenBestaetigen()) {
                        System.out.println("Programm wird beendet, Bis dann!!! :)");
                        runningBucle = false;
                    } else {
                        System.out.println("Beenden abgebrochen, züruck zum Menü");
                    }
                    break;

                default:
                    System.out.println("Ungültige Eingabe. Bitte wählen Sie eine Option aus dem Menü");

            }// Switch ENDE

        } // While ENDE
    }// main ENDE

    // Hier kommen ggf. die Methoden für die einzelnen Menüfunktionen hin

    // - Modul anlegen / bearbeiten
    static void modulenAngelegenBearbeiten() {
        modulName = readLine("Modulname: ");

        do { // DO-While SCHLEIFE für ETCS <= 0
            etcs = readInt("Bitte geben Sie die ECTS-Punkte ein ( > 0 ): ");
            if (etcs <= 0) {
                System.out.println("ETCS müssen grösser als 0 sein, bitte geben Sie eine gültige Zahl ein. ");
            } else {
                modulenAngelegt = true;
            }
        } while (etcs <= 0);

        do { // DO-While SCHLEIFE für NOTE zwischen 1 und 5
            note = readInt("Note (1-5): ");
            if (note < 1 || note > 5) {
                System.out.println("Die Note muss zwischen 1 und 5 liegen, bitte geben Sie eine gültige Zahl ein. ");
            }
        } while (note < 1 || note > 5);

        modulenAngelegt = true;
        System.out.println("\n Modul wurde erfolgreich angelegt/gespeichert.");
    }// Modul anlegen / bearbeiten ENDE

    // - Modul anzeigen
    static void modulAnzeigen() {
        if (!modulenAngelegt) {
            System.out.println(" \n Es wurden noch kein Modul angelegt.");
        } else {
            System.out.println("MODULINFORMATIONEN:");
            System.out.println("Name: " + modulName);
            System.out.println("ETCS: " + etcs);
            System.out.println("Note: " + note);
        }
    }// Modul Anzeigen ENDE

    // - Note setzen
    static void noteSetzen() {
        if (!modulenAngelegt) {
            System.out.println("\n Bitte zuerst ein Modul anlegen (M) aus dem Menü. ");
            return;
        } else {
            int neueNote;
            do { // DO-While SCHLEIFE für NOTE zwischen 1 und 5
                neueNote = readInt("Neue Note (1-5): ");
                if (neueNote < 1 || neueNote > 5) {
                    System.out
                            .println("Die Note muss zwischen 1 und 5 liegen, bitte geben Sie eine gültige Zahl ein. ");
                }
            } while (neueNote < 1 || neueNote > 5);
            note = neueNote;
            System.out.println("\n Note wurde erfolgreich gesetzt.");
        }
    }// Module Note setzen ENDE

    // - Bonusregel prüfen
    static void bonusregelPruefen() {
        if (!modulenAngelegt) {
            System.out.println("\n Bitte zuerst ein Modul anlegen (M) aus dem Menü. ");
            return;
        }
        if (etcs % 3 == 0) {
            System.out.println("\n Bonus möglich (ECTS-Zahl ist durch 3 teilbar).");
        } else {
            System.out.println("\n Kein Bonus (ECTS-Zahl ist nicht durch 3 teilbar).");
        }

    }// Bonusregel prüfen ENDE

    // - Fortschrittsbalken ausgeben
    static void fortschrittsbalkenAusgeben() {
        if (!modulenAngelegt) {
            System.out.println("\n Bitte zuerst ein Modul anlegen (M) aus dem Menü.");
            return;
        }

        int ziel;
        do { // DO-While SCHLEIFE für Ziel-ETCS <=0
            ziel = readInt("\n Ziel-ETCS (grösser als 0): ");
            if (ziel <= 0) {
                System.out.println("\n Ziel-ETCS müssen grösser als 0 sein, bitte geben Sie eine gültige Zahl ein.");
            }
        } while (ziel <= 0);

        int erreicht = etcs;
        if (erreicht > ziel) {
            erreicht = ziel; // Balken sollen nicht länge als Ziel sein
        } // ENDE erreicht>ziel

        // Zeichen # für erreichte ETCS ausgeben
        for (int i = 0; i < erreicht; i++) {
            System.out.print("#");
        }

        // Zeichen _ für verbleibenden ETCS ausgeben
        for (int i = erreicht; i < ziel; i++) {
            System.out.print("_");
        }
        System.out.println(); // Neue Zeile nach dem Balken
    }

    // - Zeitraster ausgeben zwischen 9:00 und 16:55 in 5-Minuten-Schritten
    static void zeitrasterAusgeben() {
        System.out.println("\n Zeitraster wird ausgegeben....");

        for (int stunde = 9; stunde <= 16; stunde++) { // For Schleife für Stunden von 9 bis 16
            for (int minute = 0; minute < 60; minute += 5) {
                if (stunde == 16 && minute > 55) {
                    break;
                    // Schleife beenden wenn 16:55 Uhr erreicht ist.
                }
                // Formatierte Ausgabe der Zeit im Format HH:MM (hours : minutes)
                System.out.printf("\n %02d:%02d", stunde, minute);

                // Bei bestimmten Zeiten spezielle Nachrichten ausgeben (11:15)
                if (stunde == 11 && minute == 15) {
                    System.out.println("\n CampusManager: Datenabgleich läuft... Bitte warten Sie in ruhe ...");
                }
                System.out.println();

            }
        } // For Schleife ENDE
    }// Zeitraster ausgeben ENDE

    // - Beenden bestätigen
    static boolean beendenBestaetigen() {
        char antwort = readChar("\n Möchten Sie das Programm beenden? (Ja/Nein): ");
        return (antwort == 'J' ? true : false); // Gibt true züruck wenn Ja, sonst false
    }

}// Class ENDE
