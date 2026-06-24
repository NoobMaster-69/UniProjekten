package de.thk.se.uebung.module.daten;

import java.util.ArrayList;

public interface Warenkorb {
    ArrayList<Artikel> getArtikel();
    void addiereArtikel(Artikel neu);
    void enfterneArtikel(Artikel raus);
    double berechnePreis();
}