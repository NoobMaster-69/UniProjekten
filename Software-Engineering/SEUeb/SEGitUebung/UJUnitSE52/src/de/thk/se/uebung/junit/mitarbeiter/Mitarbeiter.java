package de.thk.se.uebung.junit.mitarbeiter;

public class Mitarbeiter {
    private String name;
    private double stundenlohn;
    private int stunden;

    public Mitarbeiter(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }

    public double getStundenlohn() {
        return stundenlohn;
    }

    public int getStunden() {
        return stunden;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setStundenlohn(double stundenlohn) {
        this.stundenlohn = stundenlohn;
    }

    public void setStunden(int stunden) {
        this.stunden = stunden;
    }
}
