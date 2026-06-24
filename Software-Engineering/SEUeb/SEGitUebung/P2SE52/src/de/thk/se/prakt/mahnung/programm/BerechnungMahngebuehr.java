package de.thk.se.prakt.mahnung.programm;

import java.util.ArrayList;

public interface BerechnungMahngebuehr {
    public boolean hatUnbezahlteRechnung (String name);
    public UnbezahlteRechnung findeRechnung(int rechnungNr) throws RechnungNotFoundException;
    public void addRechnung(UnbezahlteRechnung neueRechnung);
    public void delRechnung (UnbezahlteRechnung entfernteRechnung);
    public double berechneMahngebuehr (int rechnungsNr, int jahr) throws RechnungNotFoundException, FalschesJahrException;
}
