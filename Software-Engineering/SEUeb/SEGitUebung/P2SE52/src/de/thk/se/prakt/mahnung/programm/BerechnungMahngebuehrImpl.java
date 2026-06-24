package de.thk.se.prakt.mahnung.programm;

import java.util.ArrayList;

public class BerechnungMahngebuehrImpl implements BerechnungMahngebuehr {
    private ArrayList<UnbezahlteRechnung> UnbezahlteRechnungen = new ArrayList<>();
    public boolean hatUnbezahlteRechnung(String name) {
        for(int i = 0; i < UnbezahlteRechnungen.size(); i++){
            if(UnbezahlteRechnungen.get(i).getKundenName().equals(name)){
               return true;
            }
        }
        return false;
    }

    public UnbezahlteRechnung findeRechnung(int rechnungNr) throws RechnungNotFoundException {
        for(int i = 0; i < UnbezahlteRechnungen.size(); i++){
            if(UnbezahlteRechnungen.get(i).getRechnungsNummer() == rechnungNr){
                return UnbezahlteRechnungen.get(i);
            }
        }
        throw new RechnungNotFoundException("Rechnung mit der Nr: " + rechnungNr + " nicht gefunden.");
    }

    public void addRechnung(UnbezahlteRechnung rechnung) {
        UnbezahlteRechnungen.add(rechnung);
    }

    public void delRechnung(UnbezahlteRechnung rechnung) {
        UnbezahlteRechnungen.remove(rechnung);
    }

    public double berechneMahngebuehr(int rechnungsNummer, int jahr) throws RechnungNotFoundException, FalschesJahrException {
        double Mahngebuehr = 0;
        try{
          findeRechnung(rechnungsNummer);
        }catch(RechnungNotFoundException e){
            System.out.println("Rechnung nicht gefunden.");
        }
        if(findeRechnung(rechnungsNummer).getRechnungsJahr() == jahr){
            Mahngebuehr = findeRechnung(rechnungsNummer).getRechnungsBetrag() * 0.05;
            return Mahngebuehr;
        }else if(findeRechnung(rechnungsNummer).getRechnungsJahr() == jahr-1){
            Mahngebuehr = findeRechnung(rechnungsNummer).getRechnungsBetrag() * 0.08;
            return Mahngebuehr;
        }else if(findeRechnung(rechnungsNummer).getRechnungsJahr() == jahr-2){
            Mahngebuehr = findeRechnung(rechnungsNummer).getRechnungsBetrag() * 0.1;
            return Mahngebuehr;
        }else if(findeRechnung(rechnungsNummer).getRechnungsJahr() == jahr-3 || findeRechnung(rechnungsNummer).getRechnungsJahr() == jahr-4){
            Mahngebuehr = findeRechnung(rechnungsNummer).getRechnungsBetrag() * 0.14;
            return Mahngebuehr;
        }else if(findeRechnung(rechnungsNummer).getRechnungsJahr() < jahr-4) {
            Mahngebuehr = findeRechnung(rechnungsNummer).getRechnungsBetrag();
            return Mahngebuehr;
        }else{
            throw new FalschesJahrException("Dieses Jahr kann nicht verarbeitet werden.");
        }
    }

}
