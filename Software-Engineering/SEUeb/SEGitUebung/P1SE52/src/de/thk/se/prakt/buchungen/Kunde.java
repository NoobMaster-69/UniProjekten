package de.thk.se.prakt.buchungen;


import java.util.ArrayList;

/**
 * Klasse Kunde
 * @author gruppe52
 * @version 1.0
 */
public class Kunde {
    /**
     * name des Kunden
     */
    private String name;
    /**
     * Eindeutige Kundennummer
     */
    private int kundenNr;
    /**
     * Buchungen des Kunden
     * @see Buchung
     */
    private ArrayList<Buchung> buchungen;

    public Kunde(String kName, int kNr) {
        this.name = kName;
        this.kundenNr = kNr;
        this.buchungen = new ArrayList<>();
    }

    /**
     *Zur berechnung der Kosten
     * @return gesamte Kosten der Buchungen
     * @see Buchung
     */
    public int gesamtKosten(){
        int sum = 0;
        for(Buchung b : buchungen){
            int td = b.getAnzahlTage();     //gebuchte Tage
            int dp = b.getZimmer().getTagespreis(); //Tagespreis des gebuchten Zimmers
            sum += td*dp;
        }
        return sum;
    }

    /**
     * getter für Kundennummer
     * @return Kundennummer
     */
    public int getKundenNr() {
        return kundenNr;
    }

    /**
     *setter für Kundennummer
     * @param kundenNr int, um die alte Kundennummer zu aendern
     */
    public void setKundenNr(int kundenNr) {
        this.kundenNr = kundenNr;
    }

    /**
     * getter für Name eines Kunden
     * @return name
     */
    public String getName() {
        return name;
    }

    /**
     *setter für Name eines Kunden
     * @param name String, um den alten Namen zu aendern
     */
    public void setName(String name) {
        this.name = name;
    }

    /**
     *fuegt eine Buchung zur Liste hinzu
     * @param b Buchung, die hinzuzufügende Buchung
     */
     public void addBuchung(Buchung b) {
            buchungen.add(b);
    }

}
