package de.thk.se.prakt.buchungen;

import de.thk.se.prakt.hotel.Zimmer;

public class Buchung {
    private int anzahlTage;
    private Kunde kunde;
    private Zimmer zimmer;

    public Buchung(int anzahlT, Kunde k, Zimmer z) {
        this.anzahlTage = anzahlT;
        this.kunde = k;
        this.zimmer = z;
    }

    public int getAnzahlTage() {
        return anzahlTage;
    }
    public void setAnzahlTage(int anzahlTage) {
        this.anzahlTage = anzahlTage;
    }
    public Zimmer getZimmer() {
        return zimmer;
    }
    public void setZimmer(Zimmer zimmer) {
        this.zimmer = zimmer;
    }
    public Kunde getKunde() {
        return kunde;
    }
    public void setKunde(Kunde kunde) {
        this.kunde = kunde;
    }

}
