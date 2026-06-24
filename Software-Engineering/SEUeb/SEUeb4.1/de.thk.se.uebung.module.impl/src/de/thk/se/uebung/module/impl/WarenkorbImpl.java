package de.thk.se.uebung.module.impl;

import de.thk.se.uebung.module.daten.Artikel;
import de.thk.se.uebung.module.daten.Warenkorb;

import java.util.ArrayList;

public class WarenkorbImpl implements Warenkorb {
    private ArrayList<Artikel> alleartikel = new ArrayList<Artikel>();

    public ArrayList<Artikel> getArtikel() {
        return alleartikel;
    }

    public void addiereArtikel(Artikel neu) {
        alleartikel.add(neu);
    }

    public void enfterneArtikel(Artikel raus) {
        for (int i = 0; i < alleartikel.size(); i++) {
            if (alleartikel.get(i).equals(raus)) {
                alleartikel.remove(i);
            }
        }
    }

    public double berechnePreis() {
        double preis = 0;
        for (int i = 0; i < alleartikel.size(); i++) {
            preis += alleartikel.get(i).getPreis();
        }
        return preis;
    }
    public void Print(){
        System.out.println("Hallo");
    }

}
