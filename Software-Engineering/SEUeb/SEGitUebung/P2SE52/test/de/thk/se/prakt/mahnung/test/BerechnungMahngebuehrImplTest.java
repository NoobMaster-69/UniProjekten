package de.thk.se.prakt.mahnung.test;



import de.thk.se.prakt.mahnung.programm.*;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.Parameterized;

import java.util.Arrays;
import java.util.Collection;

import static org.junit.Assert.*;

@RunWith(Parameterized.class)
public class BerechnungMahngebuehrImplTest {

    UnbezahlteRechnung testRechnung = null;
    BerechnungMahngebuehrImpl testMahnung = new BerechnungMahngebuehrImpl();
    private String name;
    private int rechnungsNr;
    private int rechJahr, jahr;
    private double betrag;
    private double expected;
    //(1).
    @Test
    public void testUnbezahlteRechnungName1() {
        testRechnung = new UnbezahlteRechnung("Jeff", 2001, 2021, 5.30);
        testMahnung.addRechnung(testRechnung);
        boolean actual = testMahnung.hatUnbezahlteRechnung("Jeff");
        assertTrue(actual);
        testMahnung.delRechnung(testRechnung);
    }

    //(2).
    @Test
    public void testUnbezahlteRechnungName2() {
        testRechnung = new UnbezahlteRechnung("Jeffe", 2001, 2021, 5.30);
        testMahnung.addRechnung(testRechnung);
        boolean actual = testMahnung.hatUnbezahlteRechnung("Jeff");
        assertFalse(actual);
        testMahnung.delRechnung(testRechnung);
    }
    //(3).
    @Test(expected = RechnungNotFoundException.class)
    public void testFindeRechnungNr1() throws RechnungNotFoundException {
        testRechnung = new UnbezahlteRechnung("Jeffe", 2001, 2021, 5.30);
        testMahnung.addRechnung(testRechnung);
        testMahnung.findeRechnung(68);
        testMahnung.delRechnung(testRechnung);
    }
    //(4).
    @Test
    public void testFindeRechnungNr2() throws RechnungNotFoundException {
        testRechnung = new UnbezahlteRechnung("Joff", 68, 2021, 5.30);
        testMahnung.addRechnung(testRechnung);
        assertEquals(68, testMahnung.findeRechnung(68).getRechnungsNummer());
        testMahnung.delRechnung(testRechnung);
    }

    //(5).
    @Test(expected = RechnungNotFoundException.class)
    public void testFindeRechnungNr3() throws RechnungNotFoundException {
        testMahnung.findeRechnung(68);
    }

    //(6).
    @Test(expected = FalschesJahrException.class)
    public void testMahngebuehr() throws FalschesJahrException, RechnungNotFoundException {
        testRechnung = new UnbezahlteRechnung("Jeff", 66, 2021, 5.30);
        testMahnung.addRechnung(testRechnung);
        testMahnung.berechneMahngebuehr(66,2020);
        testMahnung.delRechnung(testRechnung);
    }

    //(7.1 - 7.5).
    @Parameterized.Parameters
    public static Collection param(){
        return Arrays.asList(new Object[][]{
                {"Jeff",89, 2021, 478.45, 2021, 23.9225}, {"Jeff",57, 2020, 4783.33, 2021, 382.6664}, {"Jeff",96, 2018, 2999.99, 2020, 299.999},
                {"Jeff",1, 2010, 7296.67, 2014, 1021.5338}, {"Jeff",49, 1999, 12798.60, 2021, 12798.60} });
    }

    public BerechnungMahngebuehrImplTest(String Name, int rechnungsNr, int rechJahr, double betrag, int jahr, double expected){
        this.name = Name; this.rechnungsNr = rechnungsNr; this.rechJahr = rechJahr; this.betrag = betrag;this.jahr = jahr; this.expected = expected;
    }
    @Test
    public void testParam() throws RechnungNotFoundException, FalschesJahrException {
        testRechnung = new UnbezahlteRechnung(name, rechnungsNr, rechJahr, betrag);
        testMahnung.addRechnung(testRechnung);
        assertEquals(expected, testMahnung.berechneMahngebuehr(rechnungsNr, jahr), 0.0001);
        testMahnung.delRechnung(testRechnung);
    }
}