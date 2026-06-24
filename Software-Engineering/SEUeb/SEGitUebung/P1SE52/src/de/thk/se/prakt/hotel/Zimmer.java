package de.thk.se.prakt.hotel;


public class Zimmer {
    private int tagespreis;
    private int zimmerNr;

    public Zimmer(int tp, int zNr) {
        this.tagespreis = tp;
        this.zimmerNr = zNr;
    }

    public int getTagespreis() {
        return tagespreis;
    }
    public void setTagespreis(int tagespreis) {
        this.tagespreis = tagespreis;
    }
    public int getZimmerNr() {
        return zimmerNr;
    }
    public void setZimmerNr(int zimmerNr) {
        this.zimmerNr = zimmerNr;
    }

}
