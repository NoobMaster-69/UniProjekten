package de.thk.se.uebung.module.daten;

public class Artikel {
    String name;
    double preis;
    public Artikel(String name, double preis) {
        this.name = name;
        this.preis = preis;
    }
    public String getName() {
        return name;
    }
    public double getPreis() {
        return preis;
    }
    public void setName(String name) {
        this.name = name;
    }
    public void setPreis(double preis) {
        this.preis = preis;
    }
}
