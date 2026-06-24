package de.thk.se.prakt.hotelmain;

import de.thk.se.prakt.buchungen.Buchung;
import de.thk.se.prakt.buchungen.Kunde;
import de.thk.se.prakt.hotel.Zimmer;

import java.util.ArrayList;

public class HotelMain {

    public static void main(String[] args) {

        Zimmer zimmer1 = new Zimmer(66, 134);
        Zimmer zimmer2 = new Zimmer(115, 33);
        Zimmer zimmer3 = new Zimmer(345, 66);
        Zimmer zimmer4 = new Zimmer(234, 456);
        Zimmer zimmer5 = new Zimmer(99, 334);

        Kunde kunde1 = new Kunde("Peter", 334);
        Kunde kunde2 = new Kunde("Ernst", 445);
        Kunde kunde3 = new Kunde("Michael", 67);


        Buchung buchung1 = new Buchung(3, kunde1, zimmer2);
        Buchung buchung2 = new Buchung(9, kunde1, zimmer1);
        Buchung buchung3 = new Buchung(5, kunde3, zimmer4);
        Buchung buchung4 = new Buchung(33, kunde2, zimmer5);


        kunde1.addBuchung(buchung1);
        kunde1.addBuchung(buchung2);
        kunde3.addBuchung(buchung3);
        kunde2.addBuchung(buchung4);

        System.out.println("Peter bezahlt "+kunde1.gesamtKosten()+"€.");
        System.out.println("Ernst bezahlt "+kunde2.gesamtKosten()+"€.");
        System.out.println("Michael bezahlt "+kunde3.gesamtKosten()+"€.");
    }
}
