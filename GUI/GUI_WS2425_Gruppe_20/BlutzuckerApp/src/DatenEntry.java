public class DatenEntry {
    private String name;
    private String vorname;
    private String Geburtsdatum;
    private String datum;
    private String uhrzeit;
    private String blutzuckerwerte;
    private double kohlenhydrate;
    private String Medikation;
    private String za;

    public DatenEntry(String name, String vorname, String Geburtsdatum, double kohlenhydrate, String blutzuckerwerte,  String Medikation, String za, String datum,String uhrzeit) {
        this.name = name;
        this.vorname = vorname;
        this.Geburtsdatum = Geburtsdatum;
        this.blutzuckerwerte = blutzuckerwerte;
        this.kohlenhydrate = kohlenhydrate;
        this.Medikation = Medikation;
        this.za = za;
        this.datum = datum;
        this.uhrzeit = uhrzeit;
    }
    public String getName() {
        return name;
    }
    public String getVorname() {
        return vorname;
    }
    public String getGeburtsdatum() {
        return Geburtsdatum;
    }
    public String getDatum() {
        return datum;
    }
    public String getUhrzeit() {
        return uhrzeit;
    }
    public String getBlutzuckerwerte() {
        return blutzuckerwerte;
    }
    public double getKohlenhydrate() {
        return kohlenhydrate;
    }
    public String getMedikation() {
        return Medikation;
    }
    public String getZa() {
        return za;
    }

    public void setName(String newName) {
        name = newName;
    }
    public void setVorname(String newVorname) {
        vorname = newVorname;
    }
    public void setGeburtsdatum(String newGeburtsdatum) {
        Geburtsdatum = newGeburtsdatum;
    }
    public void setDatum(String newDatum) {
        datum = newDatum;
    }
    public void setUhrzeit(String newUhrzeit) {
        uhrzeit = newUhrzeit;
    }
    public void setBlutzuckerwerte(String newBlutzuckerwerte) {
        blutzuckerwerte = newBlutzuckerwerte;
    }
    public void setKohlenhydrate(double newKohlenhydrate) {
        kohlenhydrate = newKohlenhydrate;
    }
    public void setMedikation(String newMedikation) {
        Medikation = newMedikation;
    }
    public void setZa(String newZa) {
        za = newZa;
    }

}
