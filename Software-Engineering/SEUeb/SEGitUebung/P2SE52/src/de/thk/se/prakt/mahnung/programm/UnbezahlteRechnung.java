package de.thk.se.prakt.mahnung.programm;

public class UnbezahlteRechnung {
    private String kundenName;
    private int rechnungsNummer;
    private int rechnungsJahr;
    private double rechnungsBetrag;

    public UnbezahlteRechnung(String name, int rechnungsNr, int rechJahr, double betrag) {
        this.kundenName = name;
        this.rechnungsNummer = rechnungsNr;
        this.rechnungsJahr = rechJahr;
        this.rechnungsBetrag = betrag;
    }

    public String getKundenName() {
        return kundenName;
    }

    public void setKundenName(String kundenName) {
        this.kundenName = kundenName;
    }

    public int getRechnungsNummer() {
        return rechnungsNummer;
    }

    public void setRechnungsNummer(int rechnungsNummer) {
        this.rechnungsNummer = rechnungsNummer;
    }

    public int getRechnungsJahr() {
        return rechnungsJahr;
    }

    public void setRechnungsJahr(int rechnungsJahr) {
        this.rechnungsJahr = rechnungsJahr;
    }

    public double getRechnungsBetrag() {
        return rechnungsBetrag;
    }

    public void setRechnungsBetrag(double rechnungsBetrag) {
        this.rechnungsBetrag = rechnungsBetrag;
    }
}
