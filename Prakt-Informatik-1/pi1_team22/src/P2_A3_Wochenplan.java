// Hier Imports (z.B. Scanner)
import java.util.Scanner;

public class P2_A3_Wochenplan {

    // Hier kommt der gemeinsame Scanner hin
    static final Scanner SC= new Scanner(System.in);

    // Hier kommen die Hilfsmethoden für die Eingabe hin
    // z.B.:
    // - readLine(String prompt): liest eine komplette Textzeile ein
    // - readInt(String prompt): liest eine ganze Zahl ein (mit Fehlermeldung bei
    // falscher Eingabe)
    // - ggf. readChar(String prompt): liest ein einzelnes Zeichen ein (erster
    // Buchstabe, als Großbuchstabe)
    static String readLine(String prompt){
        System.out.print(prompt);
        return SC.nextLine();
    }

    static int readInt(String prompt){
        while(true){
            try{
            return Integer.parseInt(readLine(prompt).trim());
        }catch(Exception e){
            System.out.println("Bitte geben Sie eine ganze Zahl ein.");
        }
    }
    }


    public static void main(String[] args) {
        // Hier kommt die Logik für die Erstellung und Ausgabe des Wochenplans hin

             int wochen;

        while (true) { // wiederholung 
            wochen = readInt("Anzahl der Wochen (mindestens 4): ");
            if (wochen >= 4) { // bis  eine gültige Zahl (>= 4)
                break;
            } else {
                System.out.println("Fehler: Die Anzahl der Wochen muss mindestens 4 sein.");
            }
        }

        //Ausgabe des Wochenplans mithilfe der Hilfsmethoden
        wochenplanAusgeben(wochen);

    }//main ENDE    


    // Hier kommen ggf. weitere Hilfsmethoden hin
    // z.B.:
    // - Ausgabe des Wochenplans
    static void wochenplanAusgeben(int wochen){
        for (int week_n = 1; week_n <= wochen; week_n++) { // Schleife , die läuft über alle Wochen
            
            System.out.print("Woche " + week_n + ":");     //  die aktuelle Woche

            for (int day = 1; day <= 5; day++) {
                char termin;                               // Buchstabe für den Termin an diesem Tag

                if ((day == 2 || day == 5) && (week_n % 3 == 0)) {  // Dienstag und Freitag :  P (Praktikum) nur jede dritte Woche (also Woche 3, 6, 9, …)
                    termin = 'P';
                } else if (day == 3 && (week_n % 2 == 0)) { // Mittwoch :U ( Übung) nur in geraden Wochen
                    termin = 'U';

                } else if (day == 4) { // Donnerstag : V (Vorlesung) immer 
                    termin = 'V';
                } else {                  // alle anderen Fälle:

                    termin = '-'; // '-' kein Termin
                }
                System.out.print(termin + " ");

            }
            System.out.println();   // Zeilenumbruch nach einer Woche
        }
    }//Hilfmethode ENDE
}//CLASS ENDE
