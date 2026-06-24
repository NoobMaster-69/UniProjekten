package de.thk.se.uebung.junit.test;

import de.thk.se.uebung.junit.gehalt.GehaltsBerechnung;
import de.thk.se.uebung.junit.gehalt.StundenException;
import de.thk.se.uebung.junit.mitarbeiter.Mitarbeiter;
import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.assertEquals;

public class GehaltsBerechnungTest {
    // Hier bitte die JUnit-Test implementieren.
    Mitarbeiter mita;
    GehaltsBerechnung gb;
    @Before
    public void setUp(){
        mita = new Mitarbeiter("Schmidt");
        gb = new GehaltsBerechnung(mita, 40);
    }

    @Test
    public void testGetNameMitarbeiter(){
        String expected = mita.getName();
        String actual = gb.getNameMitarbeiter();
        assertEquals(expected, actual);
    }
    @Test
    public void testUeberstunden_1(){
        mita.setStunden(41);
        boolean actual = gb.ueberstunden();
        assertEquals(true, actual);
    }
    @Test
    public void testUeberstunden_2(){
        mita.setStunden(40);
        boolean actual = gb.ueberstunden();
        assertEquals(false, actual);
    }
    @Test(expected = StundenException.class)
    public void testMonatsgehalt_1() throws StundenException {
        mita.setStunden(-1);
        double test = gb.monatsGehalt();
    }
    @Test
    public void testMonatsgehalt_2() throws StundenException {
        mita.setStundenlohn(25.7);
        mita.setStunden(39);
        assertEquals(1002.30, gb.monatsGehalt(), 0.001);
    }
    @Test
    public void testMonatsgehalt_3() throws StundenException {
        mita.setStundenlohn(28.3);
        mita.setStunden(54);
        assertEquals(1607.44, gb.monatsGehalt(), 0.001);
    }
}
