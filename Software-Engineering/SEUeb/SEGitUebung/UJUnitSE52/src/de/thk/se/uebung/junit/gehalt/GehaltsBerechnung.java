package de.thk.se.uebung.junit.gehalt;

import de.thk.se.uebung.junit.mitarbeiter.Mitarbeiter;

public class GehaltsBerechnung {
    private Mitarbeiter dieserMitarbeiter;
    private int monatlicheArbeitszeit;
    private double gehalt;
    private double gehaltUeberstunden;
    private int ueberstunden;

    public GehaltsBerechnung(Mitarbeiter dieserMitarbeiter, int arbeitszeit) {
        this.dieserMitarbeiter = dieserMitarbeiter;
        this.monatlicheArbeitszeit = arbeitszeit;
    }

    public Mitarbeiter getDieserMitarbeiter() {
        return dieserMitarbeiter;
    }

    public void setDieserMitarbeiter(Mitarbeiter dieserMitarbeiter) {
        this.dieserMitarbeiter = dieserMitarbeiter;
    }

    public double monatsGehalt() throws StundenException {
        if (dieserMitarbeiter.getStunden() < 0) {
            throw new StundenException(dieserMitarbeiter.getName());
        }

        if (dieserMitarbeiter.getStunden() > monatlicheArbeitszeit){
            ueberstunden = dieserMitarbeiter.getStunden() - monatlicheArbeitszeit;
            gehaltUeberstunden = ueberstunden * (dieserMitarbeiter.getStundenlohn()*1.2);
            gehalt = gehaltUeberstunden + dieserMitarbeiter.getStundenlohn() * monatlicheArbeitszeit;
            return gehalt;
        } else {
            gehalt = dieserMitarbeiter.getStunden() * dieserMitarbeiter.getStundenlohn();
            return gehalt;
        }
    }

    public boolean ueberstunden(){
        if (dieserMitarbeiter.getStunden() > monatlicheArbeitszeit) {
            return true;
        } else {
            return false;
        }
    }

    public String getNameMitarbeiter() {
        return dieserMitarbeiter.getName();
    }
}
