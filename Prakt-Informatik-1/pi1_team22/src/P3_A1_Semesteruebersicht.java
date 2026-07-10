import java.util.Scanner;

public class P3_A1_Semesteruebersicht {
    //Gemeinsamer SCANNER 
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

/////////////////////////////////////////////////////////////////////////////////////////////////////////
                                    /////////////////MAIN ///////////////////
    public static void main(String[] args) {
        int anzahl;
        do{
            anzahl= readInt(" Wie viele Module von 1 -> 10 möchten Sie eingeben?");
        }while(anzahl<1 || anzahl>10);

        // Hier kommt die Logik für Aufgabe 1 hin:

        // Parallele Arrays anlegen, z.B.:
        // String[] modulName;
        // int[] modulEcts;
        // int[] modulNote;
        // int anzahlModule;

        //Variablen, um die parallele Arrays ANLEGEN
        String[] modulName = new String[anzahl];
        int[] modulEcts = new int[anzahl];
        int[] modulNote = new int[anzahl];
        int anzahlModule = anzahl;  //erfasst die aktuelle Module


        // Teil 1 – Module erfassen
        // ------------------------------------
            //Datei EINLESEN          
        for(int i=0; i<anzahl; i++){     //For Schleife für die Array Eingeben
            System.out.println("\n Modul"+(i+1)+ ": ");  

            modulName[i] = readLine("Name des Modules: "); //Eingeben für jede Modul Name

                //Ects EINGEBEN
            int ects;
            do{
                ects= readInt("ECTS bitte eingeben (>=0): ");
            }while(ects<0);
            modulEcts[i] = ects;  //ARRAY Speichern

                //Note EINGEBEN
            int note;
            do{ 
                note= readInt("Note bitte eingeben (von 1 *SEHR GUT* bis 5 *TOTAL SCHLECHT*): ");
            }while(note<1 || note>5);
            modulNote[i] = note;  //ARRAY Speichern        
        }//For Schleife ENDE


        // Teil 2 – Auswertung durch Methoden
        // ------------------------------------


        int gesamtEct= gesamtEcts(modulEcts, anzahlModule);
        double durchschnitt= durchschnittnoteGewichtet(modulNote, modulEcts, anzahlModule);
        int bestandendeNote= anzahlBestanden(modulNote, anzahlModule);

        // Teil 3 – Ausgabe
        // ------------------------------------

        System.out.println ("\n Hier der Semesterübersicht ->  \n");
        System.out.println(" Modul \t ECTs \t Note...");

        for(int i=0; i<anzahlModule; i++){
            System.out.printf("%s \t %d \t %d%n", modulName[i], modulEcts[i], modulNote[i]);
        }
    
        System.out.println("\n Gesamt ECTS: " + gesamtEct);
        System.out.printf("Durschnittnote(gewichtet): %.2f ", durchschnitt);
        System.out.println("Anzahl bestandener Module: "+bestandendeNote);
    
    }//Main  ENDE 

    // Hier können später eigene Methoden für die Auswertungen hinzukommen
    // z.B.:

    // - gesamtEcts(...)
    static int gesamtEcts(int[] ects, int anzahl){
        int summe=0;
        for(int i=0;i<anzahl; i++){
            summe+= ects[i];
        }

        return summe;
    }//Methode ENDE


    // - durchschnittsnoteGewichtet(...)
    static double durchschnittnoteGewichtet(int[] noten, int[] ects, int anzahl){

        /* Berechnung der Note= (Summe (note[i] * ects[i]) ) / Summe ects */
        int summeGewichteteNote=0;
        int summeEcts=0;

        for(int i =0;i<anzahl; i++){
            summeGewichteteNote += noten[i] * ects[i];
            summeEcts += ects[i];
        }

        if(summeEcts==0){
            return 0.0;     //FALLS alle Ects 0 wären, Division durch 0 vermeiden
        }

        return (double) summeGewichteteNote / summeEcts;
    }//Methode ENDE


    // - anzahlBestanden(...)
    static int anzahlBestanden(int[] noten, int anzahl){
        int count=0;
        for(int i=0; i<anzahl; i++){  
            //Note zwischen 1 bis 4 -> bestanden
            if(noten[i] <=4){
                count++;
            }
        }

        return count;
    }//Methode ENDE



}//Class ENDE 
