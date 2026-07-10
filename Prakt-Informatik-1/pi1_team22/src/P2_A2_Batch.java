// Hier Imports (z.B. Scanner)
import java.util.Scanner;

public class P2_A2_Batch {

    // Hier kommt der gemeinsame Scanner hin
    static Scanner scanner1= new Scanner(System.in);

    // Hier kommen die Hilfsmethoden für die Eingabe hin
    // z.B.:
    // - readLine(String prompt): liest eine komplette Textzeile ein
    // - readInt(String prompt): liest eine ganze Zahl ein (mit Fehlermeldung bei
    // falscher Eingabe)
    // - ggf. readChar(String prompt): liest ein einzelnes Zeichen ein (erster
    // Buchstabe, als Großbuchstabe)

    static String readLine(String prompt){  //Komplette Textzeile (STRING) einlesen
        System.out.println(prompt);
        return scanner1.nextLine();
    }

    static int readInt(String prompt){  //Ganzzahl (INTEGER) einlesen mit Fehlermeldung
        while(true){
            try{
                return Integer.parseInt(readLine(prompt).trim());
            }catch(Exception e){
                System.out.println("Bitte eine ganze Zahl eingeben.");
            }
        }//While Schleife ENDE
    }

    public static void main(String[] args) {

        // Hier kommt die Logik für die Verarbeitung der Datensätze hin

        System.out.println("Batch-Verarbeitung gestartet...");  //Start Meldung

        //Schritt 1: Anzahl der Modulen zum einlesen vonn 1 bis n
        int n;
        do{
            n=readInt("\n Wie viele Module möchten Sie eingeben? (1 bis n):");
            if(n<1 || n>10){
                System.out.println("\n Die Anzahl muss zwischen 1 und 10 liegen, bitte versuchen Sie noch einmal");
            }else{
                System.out.println("\n Sie haben sich entschieden für "+n+" Module einzugeben.");
            }
        }while(n<1 || n>10);

        //Laufende Variablen
        int anzahlBestanden=0;
        int summeEctsBestanden=0;
        int summeEctsGesamt=0;

        //Schritt 2: Datensätze einlesen
        for(int i=1; i<=n; i++){    //FOR SCHLEIFE für n Module
            System.out.println("\n Modul "+i+" von "+n+" wird eingelesen...");

            int note=leseNote(); //Hilfsmethode für Note einlesen
            int ects=leseEcts(); //Hilfsmethide für ECTS einlesen
            summeEctsGesamt += ects;    //ECTS summieren

        //Bestanden ab Note <=4
        if(note<=4){
            anzahlBestanden++;
            summeEctsBestanden += ects;
        }
    }

        //Schritt 3: Auswertung ausgeben
        auswertungAusgeben(anzahlBestanden, summeEctsBestanden, summeEctsGesamt, n);

    }//Main ENDE 

    // Hier kommen ggf. weitere Hilfsmethoden hin
    // z.B.:
    // - Einlesen eines Datensatzes
    static int leseNote(){
         //Note zwischen 1 und 5 einlesen mit Überprüfung
            int note;
            do{ //DO-WHILE SCHLEIFE für Note zwischen 1 und 5
                note=readInt("\n Note 1 bis 5 für das Modul eingeben: ");
                if(note<1 || note>5){       //NOTEN ÜBERPRÜFUNG zwischen 1 und 5
                    System.out.println("\n ACHTUNG: Die Note muss zwischen 1 und 5 anliegen, bitte versuchen Sie es erneut.");
                }
            }while(note<1 || note>5);

            return note;
        }

        //ETCS grösse als 0 (>=0)
    static int leseEcts(){
        int ects;
        do{         //Do-While Schleife für ECTS mit Überprüfung
            ects= readInt("ETCS grösser oder gleich als 0 eingeben:");
            if(ects<0){
                System.out.println("ECTS dürfen nicht negativ so wie kleiner als 0 sein, bitte versuchen Sie es noch mal");
            }
        }while(ects<0);

        return ects;
    }

    // - Ausgabe der Auswertung
    static void auswertungAusgeben(int bestanden, int ectsBestanden, int ectsGesamt, int n){
        System.out.println("\n --Auswertung--");
        System.out.println("Anzahl der bestandener Module: "+bestanden);
        System.out.println("Summe ECTS (bestanden): "+ectsBestanden);
        System.out.println("Summe ECTS (gesamt): "+ectsGesamt);

        double quote =(bestanden *100.0) /n;
        System.out.printf("\n Bestandsquote sind: %.2f %% \n",quote);

        if(ectsBestanden >= 30){
            System.out.println("Mindestens 30 ECTS wurden bestanden");
        }else{
            System.out.println("Weniger als 30 ECTS wurden bestanden");
        }
    }

}//CLASS ENDE
