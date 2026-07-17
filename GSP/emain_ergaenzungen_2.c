#include "simuc.h"

///////////////////////////////////////////////////////////////////////////////////////
// Bedingte Compilierung zur Bestimmung welches "Beispiel" compiliert werden soll  ///
//
// Es darf immer nur ein "define" aktive, d.h. nicht auskommentiert, sein.
//#define dynamische_speicherallokierung
//#define zeiger_auf_zeiger
//#define aufgabe_b3
//#define inline_assembler_feldmanipulation_a
//#define inline_assembler_feldmanipulation_b
#define inline_assembler_feldmanipulation_c

///////////////////////////////////////////////////////////////////////////////////////





#ifdef dynamische_speicherallokierung

#include <stdio.h>

void emain(void* arg)
{
    long int zahl, groesse;
    long int anfangadresse_des_reservierten_speicherbereiches;
    char string[100];

    short int* pi;
    short int i;

    INIT_BM_WITHOUT_REGISTER_UI;

    putstring("Bitte eine ganzahlige positive Zahl eingeben:\n");
    getstring(string);
    zahl=stringtoi(string);

    groesse = sizeof(short int);              // Zeile A
    /* Antwort:
     * sizeof erhaelt als Argument einen existierenden Datentyp und lierfert als Rueckgabewert
     * die Groesse diese Datentypes in Byte. Der Datentyp darf durchaus ein selbst definierter
     * Datentyp und durchaus auch ein zusammengesetzer Datentyp(z.B. eine Struktur) sein.
     * Wie ueblich muss der Datentyp aber an der Stelle seiner Nutzung bekannt sein.
    */

    pi = (short int*) calloc(zahl, groesse);  // Zeile B
    /* Antwort:
     * calloc "holt sich" (reserviert sich) zur Laufzeit, also zum Zeitpunkt seiner Ausfuehrung, einen
     * zusammenhaengenden Speicherbereich vom Betriebssystem und initialisiert diesen
     * Byte für Byte mit dem Wert 0.
     * Das Betriebssystem verwaletet den Speicher in Form einer sogenannte Heap (Halde).
     * Die Anzahl der reservierten Bytes ergibt sich aus dem Produkt zahl*groesse.
     * Als Rueckgebawert wird ein Zeiger auf den Anfang (die niedrigste Adresse)
     * des reservierten Speicherbereiches zurueck gegeben.
     * Wichtig zu bemerken ist, dass dieses Reservieren nicht unbedingt immer funktioniert.
     * Wenn z.B. kein Speicher auf der Heap mehr zur Verfügung steht (die Halde also leer ist),
     * so wir als Returnwert der Wert NULL zurueck gegeben.
     * Die obbige C-Code-Zeile sollte also sicherheitshalber wie folgt darunter ergaenzt werden
     * if (pi == NULL){
     *       putstring("Kein Speicher mehr frei. Was soll getan werden?:\n");
     *       ...
     * }
     *
    */

    anfangadresse_des_reservierten_speicherbereiches = (long int) pi;
    /* Bemerkung zum Umgang mit dem Debugger:
     * Den Inhalt des reservierten Speicherbereiches kann man sich im Qt-Debugger wie folgt anschauen:
     * 1.) Im Darstellungfenster der lokalen Variablen die Formatausgabe für die Variable anfangadresse_des_reservierten_speicherbereiches auf hexadezimal aendern
     *     CLM auf den Variablenwert -> Lokales Anzeigeformat aendern -> Hexadezimale Ganzzahl
     * 2.) Das Fenster fuer die Speicherdarstellung oeffnen und dort als Startadresse den Hex-Wert aus 1.) eingeben
     *     CLM in das Darstellungsfenster der lokalen Variablen -> Speicher Editor oeffnen -> Speicher Editor oeffnen -> Hex-Wert aus 1.) eingeben
    */

    for (i=0; i< zahl; i++){
        *(pi+i) = i;
    }

    putstring("vor free\n");
    for (i=0; i< zahl; i++){
        itostring(*(pi+i), string);
        putstring(string);
        putstring("\n");

    }

    free (pi);                  // Zeile C
    /* Antwort:
     * Gibt den durch calloc() reservierten Speicherbereich auf der Heap wieder frei.
     * Dies sollte unbedingt immer gemacht werden, denn ein sehr haeufiger Fehler ist der,
     * dass man sich zylisch (also in irgendwelchen Schleifen) mit calloc() immer wieder
     * Speicher reserviert, aber diesen nie mehr wieder frei gibt. Dies fuehrt dann zu einem
     * Speicherueberlauf.
     * Es ist also NICHT SO WIE IN JAVA wo ein Garbage-Collector immer mal wieder automatisch
     * nachschaut ob Speicher nicht mehr benoetigt wird und diesen dann frei gibt.
     *
     * Wichtig zu beachten ist, dass der uebergebene zeiger (hier pi) auf den Anfang
     * des reservierten Speicherbereiches zeigt. Falls dieser nicht mehr darauf zeigt
     * (z.B. weil Sie zwischen durch ein pi++ gemacht haben) so schlaegt die Freigabe
     * fehl.
     * Da free() keine Auskunft darueber gibt ob die Freigabe erfolgreich war oder nicht
     * ist also sehr darauf zu achten, dass der an free() uebergeben Zeiger korrekt ist,
     * denn ansonsten ist das free() im besten Fall wirkungslos oder gibt im schlimmsten Fall
     * Speicher frei der nicht frei gegeben werden soll.
     *
     * Im unten aufgefuehrten C-Code wird der Inhalt des reservierten Speichers nach Freigabe
     * ein weiteres mal ausgegeben und man sieht, dass die Ausgabe nach dem free()
     * nicht mehr die Werte wie vor dem free() ausgibt.
    */


    putstring("nach free\n");
    for (i=0; i< zahl; i++){
        itostring(*(pi+i), string);
        putstring(string);
        putstring("\n");

    }


    END;
}

#endif //dynamische_speicherallokierung


#ifdef zeiger_auf_zeiger

#include <stdio.h>

/* Antwort:
 * Wie der Name der Funktion schon sagt, reserviert diese Speicher fuer ein Feld (Array)
 * vom Typ short int. Die dafuer benoetigte Funktionalitaet(calloc, sizeof) wurde bereits
 * in der Aufgabe "Dynamische Speicherallokierung" erlaeutert.
 *
 * Neu bei dieser Funktion ist, dass der Zeiger auf den reservierten Speicherbereich
 * nicht als Rueckgabewert zuzueck gegeben wird sondern als Parameter.
 * Dies bedeutet, dass die Funktion reserviere_short_int_feld() den in emain()
 * uebergebenen Zeiger pi veraendern (also "verbiegen") muss. Damit dies moeglich ist,
 * muss pi per Call-By-Reference uebergeben werden. Es muss also die Adresse von pi uebergeben
 * werden. pi ist vom Typ Zeiger_auf_short_int(short int*). Folglich muss der Parameter pp
 * vom Typ Zeiger_auf_Zeiger_auf_short_int (short int**) sein.
 *
 * Haetten wir folgenden eigenen Datentyp  ZEIGER_AUF_SHORT_INT mit
 *
 *      typedef short int*  ZEIGER_AUF_SHORT_INT;
 *
 * definiert, so waere pp vom Datentyp ZEIGER_AUF_SHORT_INT*.
 *
 * Der Anschauung wegen ist dies in dem unten aufgefuehrten C-Code auch so geaendert worden.
 *
*/

typedef short int*  ZEIGER_AUF_SHORT_INT;

void reserviere_short_int_feld (long int anzahl_feldelemente, ZEIGER_AUF_SHORT_INT* pp  ) {

    long int groesse;
    groesse = sizeof(short int);

    short int* p;

    p = (short int*) calloc(anzahl_feldelemente, groesse  );

    *pp = p;

}


void emain(void* arg)
{
    long int zahl;
    char string[100];

    ZEIGER_AUF_SHORT_INT pi;
    short int i;

    long int anfangadresse_des_reservierten_speicherbereiches;

    INIT_BM_WITHOUT_REGISTER_UI;

    putstring("Bitte eine ganzahlige positive Zahl eingeben:\n");
    getstring(string);
    zahl=stringtoi(string);

    reserviere_short_int_feld (zahl, &pi  );


    anfangadresse_des_reservierten_speicherbereiches = (long int) pi;
    /* Bemerkung zum Umgang mit dem Debugger:
     * Den Inhalt des reservierten Speicherbereiches kann man sich im Qt-Debugger wie folgt anschauen:
     * 1.) Im Darstellungfenster der lokalen Variablen die Formatausgabe für die Variable anfangadresse_des_reservierten_speicherbereiches auf hexadezimal aendern
     *     CLM auf den Variablenwert -> Lokales Anzeigeformat aendern -> Hexadezimale Ganzzahl
     * 2.) Das Fenster fuer die Speicherdarstellung oeffnen und dort als Startadresse den Hex-Wert aus 1.) eingeben
     *     CLM in das Darstellungsfenster der lokalen Variablen -> Speicher Editor oeffnen -> Speicher Editor oeffnen -> Hex-Wert aus 1.) eingeben
    */


    for (i=0; i< zahl; i++){
        *(pi+i) = i;
    }


    putstring("vor free\n");
    for (i=0; i< zahl; i++){
        itostring(*(pi+i), string);
        putstring(string);
        putstring("\n");

    }


    free (pi);


    END;
}

#endif //zeiger_auf_zeiger

#ifdef aufgabe_b3
/*
B3: Digitales I/O mittels Treiberfunktionen
Erstellen Sie unter Verwendung der in der Vorlesung vorgestellten Treiberfunktionen
die folgenden Programme:
a) Ein Programm, das alle 8 Bits (Bit 7 ... Bit 0) von Port D auf '1' setzt.
b) Ein Programm, das alle 8 Bits von Port C einliest und der Variablen puffer
(Typ BYTE) zuweist.
c) Erweitern Sie das Programm aus Unterpunkt b) so, dass die Variable
puffer den Wert erhält, der in den Bits 5 bis 2 steht und diesen Wert dann
an den Bits 3 bis 0 von Port D ausgibt.
*/

#include "simuc.h"
#include "io_treiber.h"

#define BIT_POS     2
#define MASK        0x0F

#define dscDIOInputByte         InputByte
#define dscDIOOutputByte        OutputByte
#define dscDIOInitAndSetConfig  Init
#define dscDIOFree              Free

void emain(void* arg)
{
    BYTE puffer;
    DSCB BHandle=NULL;

    INIT_REGISTER_UI_WITHOUT_BM;     // Nur fuer Simulation

    dscDIOInitAndSetConfig(&BHandle,0x9A);

    while (1) {
        dscDIOInputByte (BHandle,PC,&puffer);
        puffer = (puffer >> BIT_POS) & MASK;

        dscDIOOutputByte (BHandle,PD,puffer);
    } // end while

    dscDIOFree(BHandle);
} // end main



#endif //aufgabe_b3



#ifdef inline_assembler_feldmanipulation_a

int emain(void* arg) {
    unsigned char		feld[5];
    unsigned char*		p_feld;
    unsigned short int	i;
    char				string[200];

    INIT_BM_WITHOUT_REGISTER_UI;

    for (i=0; i<5; i++) {
        feld[i]=0xff;
    }


    p_feld=&feld[0];


    // ACHTUNG:
    // Der MinGW- und der GCC-Compilier arbeiten hinsicht des asm() eigentlich
    // gemaess der ATT-Syntax. Möchte man dass diese gemaess der Intel-Syntax
    // arbeiten so ist in der Qt-Projektdatei (*.pro) folgendes einzufuegen:
    //   QMAKE_CFLAGS_RELEASE += -masm=intel
    //   QMAKE_CFLAGS_DEBUG += -masm=intel
    // Hierzu sollte man wie folgt vorgehen:
    //  1.) Qt schliessen
    //  2.) Die Datei Applikation_und_Simulation.pro mit einem Texteditor oeffnen
    //      Die obigen beiden Zeilen wie folgt in den Bereich zwischern ##Neu Anfang und
    //      ## Neu Ende  integrieren
    //
    //      ## Neu Anfang
    //      QT      += core
    //      QT      -= gui
    //      TARGET = Applikation_und_Simulation
    //      CONFIG   += console
    //      Config   -= app_bundle
    //      TEMPLATE = app
    //
    //      QMAKE_CFLAGS_RELEASE += -masm=intel
    //      QMAKE_CFLAGS_DEBUG += -masm=intel
    //      ## Neu Ende
    //
    // Im Normalbetrieb sprint der Qt-Debugger nicht in den asm-Teil hinein sondern
    // bearbeitet diesen quasie als einen C-Befehl. Man kann jedoch in den asm-Teil
    // "hinein debuggen:
    //      1.) Break-Point auf die C-Code zeile mit dem asm (  setzen.
    //      2.) Dem Debugger starten und bis zu dem Brakpoint laufen lassen.
    //      3.) Im Debugger-Fenster (also dem Fenster in dem mann den quadratischen
    //          roten "Stop-Degub-Button" sieht auf den quadratischen schwarzen
    //          Button (der wie das Icon eines Konsolenbildschirms aussieht) Clicken.
    //      4.) Die Dastellung wechselt jetzt in der Art, dass der Assembler-Code
    //          anstallt des C-Codes dagestellt wird.
    //      5.) Jetzt kann man mit "Einzelschritt" (F10) jede Assemblerzeile
    //          einzeln ausfuehren.
    //      6.) Zu beachten ist, dass in diesem Fenster der Assembler-Code gemaess
    //          der ATT-Syntax dargestellt werden. Bei der ATT-Syntax sind die
    //          Angaben von Quelle und Ziel vertauscht, d.h. statt mov eax, ebx
    //          sieht man dann move ebx, eax.
    //  Die Imhalte der Allegmeinen Register kann man sich anschauen indem man
    //  in dem Fenster in dem die Haltepunkte aufgelistet sind oben rechts aus
    //  "Ansichten" clickt und dann "Register" auswählt.
    asm (

                    "mov	ebx, %[p_feld];"
                    "mov eax, 5;"
                    "mov cl, 5;"
        "weiter:	mov [ebx + eax], cl;"
                    "add cl, -1;"
                    "add eax, -1;"
                    "jnz weiter;"

        : [p_feld]    "+r"    (p_feld)

        : // Kein Input

        : "ebx", "eax", "cl"

    );


    for (i=0; i<5; i++) {
        sprintf(string,"feld[%d]=%d\n", i, feld[i]);
        putstring(string);    // Wenn putstring genutzt wird, dann muss auch
                                // (wegen der Ausgabe) das Bandmodell gestartet werden
    }

    END;

}

#endif //inline_assembler_feldmanipulation_a


#ifdef inline_assembler_feldmanipulation_b

int emain(void* arg) {
    unsigned char		feld[5];
    unsigned char*		p_feld;
    unsigned short int	i;
    char				string[200];

    INIT_BM_WITHOUT_REGISTER_UI;

    for (i=0; i<5; i++) {
        feld[i]=0xff;
    }


    p_feld=&feld[0];


    // ACHTUNG:
    // Der MinGW- und der GCC-Compilier arbeiten hinsicht des asm() eigentlich
    // gemaess der ATT-Syntax. Möchte man dass diese gemaess der Intel-Syntax
    // arbeiten so ist in der Qt-Projektdatei (*.pro) folgendes einzufuegen:
    //   QMAKE_CFLAGS_RELEASE += -masm=intel
    //   QMAKE_CFLAGS_DEBUG += -masm=intel
    // Hierzu sollte man wie folgt vorgehen:
    //  1.) Qt schliessen
    //  2.) Die Datei Applikation_und_Simulation.pro mit einem Texteditor oeffnen
    //      Die obigen beiden Zeilen wie folgt in den Bereich zwischern ##Neu Anfang und
    //      ## Neu Ende  integrieren
    //
    //      ## Neu Anfang
    //      QT      += core
    //      QT      -= gui
    //      TARGET = Applikation_und_Simulation
    //      CONFIG   += console
    //      Config   -= app_bundle
    //      TEMPLATE = app
    //
    //      QMAKE_CFLAGS_RELEASE += -masm=intel
    //      QMAKE_CFLAGS_DEBUG += -masm=intel
    //      ## Neu Ende
    //
    // Im Normalbetrieb sprint der Qt-Debugger nicht in den asm-Teil hinein sondern
    // bearbeitet diesen quasie als einen C-Befehl. Man kann jedoch in den asm-Teil
    // "hinein debuggen:
    //      1.) Break-Point auf die C-Code zeile mit dem asm (  setzen.
    //      2.) Dem Debugger starten und bis zu dem Brakpoint laufen lassen.
    //      3.) Im Debugger-Fenster (also dem Fenster in dem mann den quadratischen
    //          roten "Stop-Degub-Button" sieht auf den quadratischen schwarzen
    //          Button (der wie das Icon eines Konsolenbildschirms aussieht) Clicken.
    //      4.) Die Dastellung wechselt jetzt in der Art, dass der Assembler-Code
    //          anstallt des C-Codes dagestellt wird.
    //      5.) Jetzt kann man mit "Einzelschritt" (F10) jede Assemblerzeile
    //          einzeln ausfuehren.
    //      6.) Zu beachten ist, dass in diesem Fenster der Assembler-Code gemaess
    //          der ATT-Syntax dargestellt werden. Bei der ATT-Syntax sind die
    //          Angaben von Quelle und Ziel vertauscht, d.h. statt mov eax, ebx
    //          sieht man dann move ebx, eax.
    //  Die Imhalte der Allegmeinen Register kann man sich anschauen indem man
    //  in dem Fenster in dem die Haltepunkte aufgelistet sind oben rechts aus
    //  "Ansichten" clickt und dann "Register" auswählt.
    asm (

                    "mov	ebx, %[p_feld];"
                    "mov eax, 5;"
                    "mov cl, 5;"
        "weiter:    add cl, -1;"
                    "add eax, -1;"
                    "mov [ebx + eax], cl;"
                    "jnz weiter;"

        : [p_feld]    "+r"    (p_feld)

        : // Kein Input

        : "ebx", "eax", "cl"

    );

    for (i=0; i<5; i++) {
        sprintf(string,"feld[%d]=%d\n", i, feld[i]);
        putstring(string);    // Wenn putstring genutzt wird, dann muss auch
                                // (wegen der Ausgabe) das Bandmodell gestartet werden
    }

    END;
}

#endif //inline_assembler_feldmanipulation_b


#ifdef inline_assembler_feldmanipulation_c

int emain(void* arg) {
    unsigned long int 	feld[600];
    unsigned long int*	p_feld;
    unsigned short int	i;
    char				string[200];

    INIT_BM_WITHOUT_REGISTER_UI;


    for (i=0; i<20; i++) {
        feld[i]=0xff;
    }

    p_feld=&feld[0];


    // ACHTUNG:
    // Der MinGW- und der GCC-Compilier arbeiten hinsicht des asm() eigentlich
    // gemaess der ATT-Syntax. Möchte man dass diese gemaess der Intel-Syntax
    // arbeiten so ist in der Qt-Projektdatei (*.pro) folgendes einzufuegen:
    //   QMAKE_CFLAGS_RELEASE += -masm=intel
    //   QMAKE_CFLAGS_DEBUG += -masm=intel
    // Hierzu sollte man wie folgt vorgehen:
    //  1.) Qt schliessen
    //  2.) Die Datei Applikation_und_Simulation.pro mit einem Texteditor oeffnen
    //      Die obigen beiden Zeilen wie folgt in den Bereich zwischern ##Neu Anfang und
    //      ## Neu Ende  integrieren
    //
    //      ## Neu Anfang
    //      QT      += core
    //      QT      -= gui
    //      TARGET = Applikation_und_Simulation
    //      CONFIG   += console
    //      Config   -= app_bundle
    //      TEMPLATE = app
    //
    //      QMAKE_CFLAGS_RELEASE += -masm=intel
    //      QMAKE_CFLAGS_DEBUG += -masm=intel
    //      ## Neu Ende
    asm (

                    "mov ebx, %[p_feld];"
                    "mov eax, 600;"
                    "mov ecx, 600;"
        "weiter:    add ecx, -1;"
                    "add eax, -1;"
                    "mov [ebx + eax*4], ecx;"
                    "jnz weiter;"

        : [p_feld]    "+r"    (p_feld)

        : // Kein Input

        : "eax", "ebx", "ecx"

    );


    for (i=0; i<600; i++) {
        sprintf(string,"feld[%d]=%d\n", i, feld[i]);
        putstring(string);
    }
    END;
}

#endif //inline_assembler_feldmanipulation_c

