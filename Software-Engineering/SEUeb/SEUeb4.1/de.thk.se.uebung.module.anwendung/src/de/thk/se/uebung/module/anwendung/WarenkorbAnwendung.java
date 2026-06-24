package de.thk.se.uebung.module.anwendung;

import de.thk.se.uebung.module.daten.*;
import de.thk.se.uebung.module.impl.*;

import java.util.ArrayList;


public class WarenkorbAnwendung extends WarenkorbImpl {
    public static void main(String[] args) {
        WarenkorbAnwendung WarenkorbNew = new WarenkorbAnwendung();
        Artikel Apfel = new Artikel("Apfel", 0.20);
        Artikel Birne = new Artikel("Birne", 0.24);
        Artikel Drachenfrucht = new Artikel("Drachenfrucht", 0.99);
        Artikel Orange = new Artikel("Orange", 0.29);
        WarenkorbNew.addiereArtikel(Apfel);
        WarenkorbNew.addiereArtikel(Drachenfrucht);
        WarenkorbNew.addiereArtikel(Birne);
        WarenkorbNew.addiereArtikel(Orange);
        System.out.println(WarenkorbNew.berechnePreis());
        ArrayList<Artikel> artikelListe =WarenkorbNew.getArtikel();
        for(Artikel artikel : artikelListe){
            System.out.println(artikel.getName());
        }

    }
}

