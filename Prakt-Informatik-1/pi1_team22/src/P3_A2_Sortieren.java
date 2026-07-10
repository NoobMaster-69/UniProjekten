import java.util.Scanner;

public class P3_A2_Sortieren {
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

        // Hier kommt die Logik für Aufgabe 2 hin:

        // Beispiel-Arrays zum Testen anlegen (oder von Aufgabe 1 übernehmen)
        // z.B.:

        String[] modulName = { "MA1", "PH1", "DR", "PI1", "PI2", "PP", "PH1", "GE1", "PH2", "MA2" };
        int[] modulEcts = { 9, 5, 5, 5, 5, 3, 4, 5, 6, 9 };
        int[] modulNote = { 1, 2, 3, 4, 5, 1, 2, 3, 4, 5 };

        // Aufgabe 2.1 – BubbleSort testen

        int[] BeispielArray = { 5, 2, 9, 1, 3 };
        System.out.println("Unsortiertes Beispiel-Array:");

        for (int i : BeispielArray) {
            System.out.print(i + " ");
        }

        System.out.println();

        bubbleSortInt(BeispielArray, BeispielArray.length);

        System.out.println("Sortiertes Beispiel-Array:");
        for (int i : BeispielArray) {
            System.out.print(i + " ");
        }
        System.out.println();

        // ------------------------------------

        // Aufgabe 2.2 – Module nach Note sortieren
        sortiereNachNote(modulName, modulEcts, modulNote, modulName.length);
        System.out.println("\nModule und Ects nach der Sortierung nach Note:");
        for (int i = 0; i < modulName.length; i++) {
            System.out.println(modulName[i] + " - ECTS: " + modulEcts[i] + " - Note: " + modulNote[i]);
        }
        // ------------------------------------

        // Aufgabe 2.3 – Weitere Sortierungen (optional)

        // 2-3-1 Nach ECTS absteigend sortieren
        System.out.println("\nModule und Ects nach der Sortierung nach ECTS Absteigend:");
        sortiereNachEcts(modulName, modulEcts, modulNote, modulName.length);

        for (int i = 0; i < modulName.length; i++) {
            System.out.println(modulName[i] + " - ECTS: " + modulEcts[i] + " - Note: " + modulNote[i]);
        }

        // 2-3-2 Nach Name sortieren (aufsteigend)
        // ------------------------------------

    }

    // Hier können später die Sortiermethoden eingefügt werden
    // z.B.:
    static void bubbleSortInt(int[] a, int n) {
        boolean vertauscht;
        do {
            vertauscht = false;
            for (int i = 0; i < n - 1; i++) {
                if (a[i] > a[i + 1]) {
                    int tmp = a[i];
                    a[i] = a[i + 1];
                    a[i + 1] = tmp;
                    vertauscht = true;
                }
            }
        } while (vertauscht);
    }

    // - sortiereNachNote(...)
    static void sortiereNachNote(String[] namen, int[] ects, int[] noten, int anzahl) {
        boolean vertauscht;
        do {
            vertauscht = false;
            for (int i = 0; i < anzahl - 1; i++) {
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

    // - sortiereNachEcts(...)
    static void sortiereNachEcts(String[] namen, int[] ects, int[] noten, int anzahl) {
        boolean vertauscht;
        do {
            vertauscht = false;
            for (int i = 0; i < anzahl - 1; i++) {
                if (ects[i] < ects[i + 1]) {

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

    // - sortiereNachName(...)
    static void sortiereNachName(String[] namen, int[] ects, int[] noten, int anzahl) {
        boolean vertauscht;
        do {
            vertauscht = false;
            for (int i = 0; i < anzahl - 1; i++) {
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

}// Class Sortieren ENDE