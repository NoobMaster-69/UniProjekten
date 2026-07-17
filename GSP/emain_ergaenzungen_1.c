//#include "simuc.h"

///////////////////////////////////////////////////////////////////////////////////////
// Bedingte Compilierung zur Bestimmung welches "Beispiel" compiliert werden soll  ///
//
// Es darf immer nur ein "define" aktive, d.h. nicht auskommentiert, sein.
//
//#define test_haendische_portmanipulation_zur_ansteuerung_des_bandmodells
#define rollosteuerung_moore
//#define rollosteuerung_mealy
//#define rollosteuerung_moore_mit_steuerungsfunktion
//#define rollosteuerung_mealy_mit_steuerungsfunktion

///////////////////////////////////////////////////////////////////////////////////////





#ifdef  test_haendische_portmanipulation_zur_ansteuerung_des_bandmodells
// Wenn dieses Programm gestartet wurde dann kann man die Eingabesignale fuer das Bandmodell
// also die Ausgabesignale die Mikrocontrollers von Hand setzen und dann sehen wie das Bandmodell
// darauf reagiert.
//
// Das Programm sorgt  nur fuer für die notwendige Initialisierung der Ports
// und dafuer, dass einmal je Schleifendurchlauf ein SYNC_SIM zur Synchronisation
// mit dem Bandmodell durchgefuehrt wird. Das "ms_wait(10);" muss sein damit das Programm auch andere
// Tasks (insbesondere die für die Simulation) eimal "dran laesst".
//
// Das Programm muss so genutzt werden, dass man
// 1.) einen breakpoint auf "SYNC_SIM" setzt und dann
// 2.) die Ausgabe-Portbits ueber das User_Interface waehrend der "Unterbrechungspause" veraendert.
//     ACHTUNG: Hierbei das RETURN nach der Aenderung nicht vergessen und dann
// 3.) auf RUN clickt so dass der naechste Schleifendurchlauf simuliert wird.
//
// Die Belegung der Portbits ist wie folgt:

// Bits die ueber ### PORT 1 ### vom Bandmodell kommen
// Diese koennen bei angeschlossenem Bandmodell nicht haendisch editiert werden
// da das Bandmodell diese Editierung direkt wieder ueberschreibt.
#define HPL_B		0
#define HPR_B		1
#define ESL_B		2
#define ESR_B		3
#define T1_B		4
#define T2_B		5
#define T3_B		6
#define T4_B		7

// Bits die ueber ### PORT 1 ### zum Bandmodell gehen
// Diese Ausgabe-Bits koennen haendisch manipuliert werden.
// So kann z.B. durch setzen der Bit 8 und 9 bewirkt werden, dass sich
// das Bandmodell link herum dreht.
#define M_An_B		8
#define M_Li_B		9
#define M_Re_B		10
#define LedRot_B	11
#define LedGruen_B	12

// An den  Bit 13, 14 und 15 ist nichts angeschlossen
// Diese Bits koennen, wenn von Ihrem Programm heraus darauf
// nicht geschrieben wird, immer editiert werden.



void emain(void* arg)
{
    short int in_1_register, out_1_register, dir_1_register;

    INIT_BM_WITH_REGISTER_UI;
    //INIT_REGISTER_UI_WITHOUT_BM


    io_out16 (DIR1, 0xFF00 );

    while(1) {

        SYNC_SIM;
        ms_wait(10);

        dir_1_register = io_in16(DIR1);
        in_1_register = io_in16(IN1);
        out_1_register = io_in16(OUT1);

    }


}
#endif // test_haendische_portmanipulation_zur_ansteuerung_des_bandmodells



#ifdef rollosteuerung_moore

/*
    Die im Skript angegebene Belegung erlaubt es die Rollo-Steuerung mit dem Bandmodellsimulator zu testen.
    Hierbei ist jedoch zu beachten, dass das Bit 8 an Port 1 (M_An) immer auf '1' gesetzt wird, damit der Motor laueft.
    Dies ist NICHT Gegenstand der eigentlichen Aufgabenstellung aus dem Skript. Deshalb wird dies
    in den folgenden Sourcen bei der Ausgabe „immer hart eincodiert mit gemacht“ und zwar mittels der
    zusaetzlichen Zeile:
                            output=output | (1<< BIT_POS_MOTOR_AN); // Nur fuer Bandmodell

    Zusaetzlich dazu ist zu Anfang des Programms die Simulation zu initialisieren. Dies geschieht mittels der
    zusaetzlichen Zeile:
                            INIT_BM_WITH_REGISTER_UI;	// Nur fuer Simulation

    Zusaetzlich dazu ist zu Anfang der while(1)-Schleife eine Daten-Synchronisation mit der Simuation durchzufuehren.
    Dies geschieht mittels der zusaetzlichen Zeile:

                            SYNC_SIM; // Nur fuer Simulation


    // Die vollstaendige Belegung bzgl. des Bandmodells ist wie folgt
        // Bits die ueber ### PORT 1 ### vom Bandmodell kommen
        #define HPL_B		0
        #define HPR_B		1
        #define ESL_B		2
        #define ESR_B		3
        #define T1_B		4
        #define T2_B		5
        #define T3_B		6
        #define T4_B		7

        // Bits die ueber ### PORT 1 ### zum Bandmodell gehen
        #define M_An_B		8
        #define M_Li_B		9
        #define M_Re_B		10
        #define LedRot_B	11
        #define LedGruen_B	12

        // Der Zaehlstand des Bandmodells (16 Bit) ist an ### PORT 0 ### angeschlossen
*/

// Einige Defines zum leichteren Wiederfinden
#define BIT_POS_IST_OBEN	0
#define BIT_POS_IST_UNTEN	1
#define BIT_POS_NACH_OBEN	4
#define BIT_POS_NACH_UNTEN	5
#define BIT_POS_FAHRE_NACH_OBEN	9
#define BIT_POS_MOTOR_AN	8
#define BIT_POS_FAHRE_NACH_UNTEN	10

typedef enum {hoch, runter, steht} STATE;	// Datentyp für den Zustand das Automaten.
                                            // Vergleichbar mit "TYPE .... IS" in VHDL.
typedef unsigned short int USHORT;			// Eigener Datentyp zur Abkuerzung

void emain(void* arg)
{
    STATE cstate;		// Variable für den Zustand das Automaten.

    // Variablen für die Eingabesignale. Eine Variable fuer jedes Signal.
    USHORT		nach_oben;
    USHORT		nach_unten;
    USHORT		ist_oben;
    USHORT		ist_unten;

    // Variablen für die Ausgabesignale. Eine Variable fuer jedes Signal.
    USHORT		fahre_nach_oben;
    USHORT		fahre_nach_unten;

    USHORT		input, output, temp, outputdir_bitmuster, clear_bitmuster, inputdir_bitmuster;


    INIT_BM_WITH_REGISTER_UI;	// Nur fuer Simulation

    // Bitmuster zum Setzen und Loeschen der Bits 8, 9 und 10 einmaig berechnen
    outputdir_bitmuster = (1<<BIT_POS_FAHRE_NACH_UNTEN) | (1<< BIT_POS_FAHRE_NACH_OBEN) | (1<< BIT_POS_MOTOR_AN);
    clear_bitmuster = ~outputdir_bitmuster;
    inputdir_bitmuster = (1 << BIT_POS_IST_OBEN)|(1 << BIT_POS_IST_UNTEN)|(1 << BIT_POS_NACH_OBEN)|(1 << BIT_POS_NACH_UNTEN);


    // 1.)	Hardware konfigurieren
    temp = io_in16(DIR1);
    temp = temp | outputdir_bitmuster; // Ausgang: Bits 10 bis 8 auf "1"
    temp = temp & ~inputdir_bitmuster;    // Eingang: Bits 0, 1, 4, 5 auf "0"
    io_out16(DIR1, temp);

    // 2.)	 Definition des Startzustandes. Entspricht dem asynchronen Reset in VHDL.
    cstate = runter;

    // 3.) Unendliche Schleife. Ein Schleifendurchlauf entspricht einem Zyklus des Automaten
    while (1) {

        SYNC_SIM; // Nur fuer Simulation

        // 4.)	Einlesen der Eingabesignale einmal je Zyklus
        input = io_in16(IN1);

        // extrahieren von "ist_oben" (BIT_POS_IST_OBEN)
        ist_oben = (input >> BIT_POS_IST_OBEN) & 0x01;

        // extrahieren von "ist_unten" (BIT_POS_IST_UNTEN)
        ist_unten = (input >> BIT_POS_IST_UNTEN) & 0x01;

        // extrahieren von "nach_oben" (BIT_POS_NACH_OBEN)
        nach_oben = (input >> BIT_POS_NACH_OBEN) & 0x01;

        // extrahieren von "nach_unten" (BIT_POS_NACH_UNTEN)
        nach_unten = (input >> BIT_POS_NACH_UNTEN) & 0x01;

        // 5.)	switch-case-Konstrukt zur "Verwaltung" der Zustaende
        switch (cstate) {

            // 6.)	Ein CASE je Zustand
            case steht:

                // 7.) Was zu tun ist ausfuehren. Hier ....
                // 7a.)  .... Ausgabesignale bestimmen
                fahre_nach_unten=0; fahre_nach_oben=0;

                // 7b.)  Ausgabesignale ausgeben
                output=fahre_nach_unten<<BIT_POS_FAHRE_NACH_UNTEN;
                output=output | (fahre_nach_oben<< BIT_POS_FAHRE_NACH_OBEN);
                output=output | (1<< BIT_POS_MOTOR_AN);   // Nur fuer Bandmodell
                temp = io_in16(OUT1);
                temp = temp  & clear_bitmuster;
                temp = temp |  output;
                io_out16(OUT1,  temp);

                // 8.)    Eingabesignale auswerten und Zustandswechsel herbei fuehren
                //          Ein IF je Pfeil
                if (  (ist_unten == 0) && (nach_unten == 1) && (nach_oben == 0)) {
                    cstate = runter; // Wechsel in den Zustand "runter"
                }
                if (  (ist_oben == 0) && (nach_oben == 1) ){
                    cstate = hoch;  // Wechsel in den Zustand "hoch"
                }

                // Diese if-Anweisung kann entfallen, da sie cstate nicht veraendert.
                if ( !(    ((ist_unten == 0) && (nach_unten == 1) && (nach_oben == 0))
                        || ((ist_oben == 0) && (nach_oben == 1)) )      ) {
                    cstate = steht; // Bleibe im Zustand "steht"
                }
                break;

            case runter:

                // 7a.)  Ausgabesignale bestimmen
                fahre_nach_unten=1; fahre_nach_oben=0;

                // 7b.)  Ausgabesignale ausgeben
                output=fahre_nach_unten<<BIT_POS_FAHRE_NACH_UNTEN;
                output=output | (fahre_nach_oben<< BIT_POS_FAHRE_NACH_OBEN);
                output=output | (1<< BIT_POS_MOTOR_AN);   // Nur fuer Bandmodell
                temp = io_in16(OUT1);
                temp = temp  & clear_bitmuster;
                temp = temp |  output;
                io_out16(OUT1,  temp);

                // 8.)    Eingabesignale auswerten und Zustandswechsel herbei fuehren
                if (ist_unten == 1) {
                    cstate = steht; // Wechsel in den Zustand "steht"
                }

                // Diese if-Anweisung kann entfallen, da sie cstate nicht veraendert.
                if (ist_unten == 0) {
                    cstate = runter; // Bleibe im Zustand "runter"
                }

                break;
            case hoch:

                // 7a.)  Ausgabesignale bestimmen
                fahre_nach_unten=0; fahre_nach_oben=1;

                // 7b.)  Ausgabesignale ausgeben
                output=fahre_nach_unten<<BIT_POS_FAHRE_NACH_UNTEN;
                output=output | (fahre_nach_oben<< BIT_POS_FAHRE_NACH_OBEN);
                output=output | (1<< BIT_POS_MOTOR_AN);   // Nur fuer Bandmodell
                temp = io_in16(OUT1);
                temp = temp  & clear_bitmuster;
                temp = temp |  output;
                io_out16(OUT1,  temp);

                // 8.)    Eingabesignale auswerten und Zustandswechsel herbei fuehren
                if (ist_oben == 1){
                    cstate = steht; // Wechsel in den Zustand "steht"
                }

                // Diese if-Anweisung kann entfallen, da sie cstate nicht veraendert.
                if (ist_oben == 0){
                    cstate = hoch; // Bleibe im Zustand "hoch"
                }
                break;

            // 9.) nicht erlaubte Zustaende "abfangen"
            default:
                cstate = runter;
        } // end switch
    } // end while
} // end main
#endif // rollosteuerung_moore


#ifdef rollosteuerung_mealy

/*
    ES GELTEN DIE BEI "rollosteuerung_moore" AN GLEICHER STELLE VORANGESTELLTEN KOMMENTARE
*/

// Einige Defines zum leichteren Wiederfinden
#define BIT_POS_IST_OBEN	0
#define BIT_POS_IST_UNTEN	1
#define BIT_POS_NACH_OBEN	4
#define BIT_POS_NACH_UNTEN	5
#define BIT_POS_FAHRE_NACH_OBEN	9
#define BIT_POS_MOTOR_AN	8
#define BIT_POS_FAHRE_NACH_UNTEN	10

typedef enum {hoch, runter, steht} STATE;	// Datentyp für den Zustand das Automaten.
                                            // Vergleichbar mit "TYPE .... IS" in VHDL.
typedef unsigned short int USHORT;			// Eigener Datentyp zur Abkuerzung

void emain(void* arg)
{
    STATE cstate;		// Variable für den Zustand das Automaten.

    // Variablen für die Eingabesignale. Eine Variable fuer jedes Signal.
    USHORT		nach_oben;
    USHORT		nach_unten;
    USHORT		ist_oben;
    USHORT		ist_unten;

    // Variablen für die Ausgabesignale. Eine Variable fuer jedes Signal.
    USHORT		fahre_nach_oben;
    USHORT		fahre_nach_unten;

    USHORT		input, output, temp, outputdir_bitmuster, clear_bitmuster, inputdir_bitmuster;


    INIT_BM_WITH_REGISTER_UI;	// Nur fuer Simulation

    // Bitmuster zum Setzen und Loeschen der Bits 8, 9 und 10 einmaig berechnen
    outputdir_bitmuster = (1<<BIT_POS_FAHRE_NACH_UNTEN) | (1<< BIT_POS_FAHRE_NACH_OBEN) | (1<< BIT_POS_MOTOR_AN);
    clear_bitmuster = ~outputdir_bitmuster;
    inputdir_bitmuster = (1 << BIT_POS_IST_OBEN)|(1 << BIT_POS_IST_UNTEN)|(1 << BIT_POS_NACH_OBEN)|(1 << BIT_POS_NACH_UNTEN);


    // 1.)	Hardware konfigurieren
    temp = io_in16(DIR1);
    temp = temp | outputdir_bitmuster; // Ausgang: Bits 10 bis 8 auf "1"
    temp = temp & ~inputdir_bitmuster;    // Eingang: Bits 0, 1, 4, 5 auf "0"
    io_out16(DIR1, temp);

    // 2.)	 Definition des Startzustandes. Entspricht dem asynchronen Reset in VHDL.
    cstate = runter;

    // Hier steht das, was an der Kante nach Start zu tun ist.
    fahre_nach_unten=1;  fahre_nach_oben=0;
    output=fahre_nach_unten<<BIT_POS_FAHRE_NACH_UNTEN; output=output | (fahre_nach_oben<< BIT_POS_FAHRE_NACH_OBEN);
    io_out16(OUT1, io_in16(OUT1) & 0x00FF); io_out16(OUT1, io_in16(OUT1) |  output);


    // 3.) Unendliche Schleife. Ein Schleifendurchlauf entspricht einem Zyklus des Automaten
    while (1) {

        SYNC_SIM; // Nur fuer Simulation

        // 4.)	Einlesen der Eingabesignale einmal je Zyklus
        input = io_in16(IN1);

        // extrahieren von "ist_oben" (BIT_POS_IST_OBEN)
        ist_oben = (input >> BIT_POS_IST_OBEN) & 0x01;

        // extrahieren von "ist_unten" (BIT_POS_IST_UNTEN)
        ist_unten = (input >> BIT_POS_IST_UNTEN) & 0x01;

        // extrahieren von "nach_oben" (BIT_POS_NACH_OBEN)
        nach_oben = (input >> BIT_POS_NACH_OBEN) & 0x01;

        // extrahieren von "nach_unten" (BIT_POS_NACH_UNTEN)
        nach_unten = (input >> BIT_POS_NACH_UNTEN) & 0x01;

            // 5.)	switch-case-Konstrukt zur "Verwaltung" der Zustaende
        switch (cstate) {

            // 6.)	Ein CASE je Zustand
            case steht:

                // 8.)    Eingabesignale auswerten und Zustandswechsel herbei fuehren
                //          Ein IF je Pfeil
                if (  (ist_unten == 0) && (nach_unten == 1) && (nach_oben == 0)) {

                    // 7a.)  Ausgabesignale bestimmen
                    fahre_nach_unten=1; fahre_nach_oben=0;
                    // 7b.)  Ausgabesignale ausgeben
                    output=fahre_nach_unten<<BIT_POS_FAHRE_NACH_UNTEN;
                    output=output | (fahre_nach_oben<< BIT_POS_FAHRE_NACH_OBEN);
                    output=output | (1<< BIT_POS_MOTOR_AN);   // Nur fuer Bandmodell
                    temp = io_in16(OUT1);
                    temp = temp  & clear_bitmuster;
                    temp = temp |  output;
                    io_out16(OUT1,  temp);

                    cstate = runter; // Wechsel in den Zustand "runter"
                }

                if (  (ist_oben == 0) && (nach_oben == 1) ){

                    // 7a.)  Ausgabesignale bestimmen
                    fahre_nach_unten=0; fahre_nach_oben=1;
                    // 7b.)  Ausgabesignale ausgeben
                    output=fahre_nach_unten<<BIT_POS_FAHRE_NACH_UNTEN;
                    output=output | (fahre_nach_oben<< BIT_POS_FAHRE_NACH_OBEN);
                    output=output | (1<< BIT_POS_MOTOR_AN);   // Nur fuer Bandmodell
                    temp = io_in16(OUT1);
                    temp = temp  & clear_bitmuster;
                    temp = temp |  output;
                    io_out16(OUT1,  temp);

                    cstate = hoch;  // Wechsel in den Zustand "hoch"
                }

                // Diese if-Anweisung kann entfallen, da sie cstate nicht veraendert
                // und die Ausgabesignale nicht neu ausgegeben werden muessen.
                if ( !(    ((ist_unten == 0) && (nach_unten == 1) && (nach_oben == 0))
                        || ((ist_oben == 0) && (nach_oben == 1)) )      ) {

                    // 7.) Was zu tun ist ausfuehren. Hier ....
                    // 7a.)  Ausgabesignale bestimmen
                    fahre_nach_unten=0; fahre_nach_oben=0;
                    // 7b.)  Ausgabesignale ausgeben
                    output=fahre_nach_unten<<BIT_POS_FAHRE_NACH_UNTEN;
                    output=output | (fahre_nach_oben<< BIT_POS_FAHRE_NACH_OBEN);
                    output=output | (1<< BIT_POS_MOTOR_AN);   // Nur fuer Bandmodell
                    temp = io_in16(OUT1);
                    temp = temp  & clear_bitmuster;
                    temp = temp |  output;
                    io_out16(OUT1,  temp);

                    cstate = steht; // Bleibe im Zustand "steht"
                }
                break;


            case runter:

                // 8.)    Eingabesignale auswerten und Zustandswechsel herbei fuehren
                if (ist_unten == 1) {

                    // 7.) Was zu tun ist ausfuehren. Hier ....
                    // 7a.)  Ausgabesignale bestimmen
                    fahre_nach_unten=0; fahre_nach_oben=0;
                    // 7b.)  Ausgabesignale ausgeben
                    output=fahre_nach_unten<<BIT_POS_FAHRE_NACH_UNTEN;
                    output=output | (fahre_nach_oben<< BIT_POS_FAHRE_NACH_OBEN);
                    output=output | (1<< BIT_POS_MOTOR_AN);   // Nur fuer Bandmodell
                    temp = io_in16(OUT1);
                    temp = temp  & clear_bitmuster;
                    temp = temp |  output;
                    io_out16(OUT1,  temp);

                    cstate = steht; // Wechsel in den Zustand "steht"
                }

                // Diese if-Anweisung kann entfallen, da sie cstate nicht veraendert
                // und die Ausgabesignale nicht neu ausgegeben werden muessen.
                if (ist_unten == 0) {

                    // 7a.)  Ausgabesignale bestimmen
                    fahre_nach_unten=1; fahre_nach_oben=0;
                    // 7b.)  Ausgabesignale ausgeben
                    output=fahre_nach_unten<<BIT_POS_FAHRE_NACH_UNTEN;
                    output=output | (fahre_nach_oben<< BIT_POS_FAHRE_NACH_OBEN);
                    output=output | (1<< BIT_POS_MOTOR_AN);   // Nur fuer Bandmodell
                    temp = io_in16(OUT1);
                    temp = temp  & clear_bitmuster;
                    temp = temp |  output;
                    io_out16(OUT1,  temp);

                    cstate = runter; // Bleibe im Zustand "runter"
                }

                break;

            case hoch:

                // 8.)    Eingabesignale auswerten und Zustandswechsel herbei fuehren
                if (ist_oben == 1){

                    // 7.) Was zu tun ist ausfuehren. Hier ....
                    // 7a.)  Ausgabesignale bestimmen
                    fahre_nach_unten=0; fahre_nach_oben=0;
                    // 7b.)  Ausgabesignale ausgeben
                    output=fahre_nach_unten<<BIT_POS_FAHRE_NACH_UNTEN;
                    output=output | (fahre_nach_oben<< BIT_POS_FAHRE_NACH_OBEN);
                    output=output | (1<< BIT_POS_MOTOR_AN);   // Nur fuer Bandmodell
                    temp = io_in16(OUT1);
                    temp = temp  & clear_bitmuster;
                    temp = temp |  output;
                    io_out16(OUT1,  temp);

                    cstate = steht; // Wechsel in den Zustand "steht"
                }

                // Diese if-Anweisung kann entfallen, da sie cstate nicht veraendert.
                // und die Ausgabesignale nicht neu ausgegeben werden muessen.
                if (ist_oben == 0){

                    // 7a.)  Ausgabesignale bestimmen
                    fahre_nach_unten=0; fahre_nach_oben=1;
                    // 7b.)  Ausgabesignale ausgeben
                    output=fahre_nach_unten<<BIT_POS_FAHRE_NACH_UNTEN;
                    output=output | (fahre_nach_oben<< BIT_POS_FAHRE_NACH_OBEN);
                    output=output | (1<< BIT_POS_MOTOR_AN);   // Nur fuer Bandmodell
                    temp = io_in16(OUT1);
                    temp = temp  & clear_bitmuster;
                    temp = temp |  output;
                    io_out16(OUT1,  temp);

                    cstate = hoch; // Bleibe im Zustand "hoch"
                }
                break;

            // 9.) nicht erlaubte Zustaende "abfangen"
            default:

                // 7a.)  Ausgabesignale bestimmen
                fahre_nach_unten=1; fahre_nach_oben=0;
                // 7b.)  Ausgabesignale ausgeben
                output=fahre_nach_unten<<BIT_POS_FAHRE_NACH_UNTEN;
                output=output | (fahre_nach_oben<< BIT_POS_FAHRE_NACH_OBEN);
                output=output | (1<< BIT_POS_MOTOR_AN);   // Nur fuer Bandmodell
                temp = io_in16(OUT1);
                temp = temp  & clear_bitmuster;
                temp = temp |  output;
                io_out16(OUT1,  temp);

                cstate = runter;
        } // end switch
    } // end while
} // end main
#endif // rollosteuerung_mealy


#ifdef rollosteuerung_moore_mit_steuerungsfunktion

/*
    ES GELTEN DIE BEI "rollosteuerung_moore" AN GLEICHER STELLE VORANGESTELLTEN KOMMENTARE
*/

// Einige Defines zum leichteren Wiederfinden
#define BIT_POS_IST_OBEN	0
#define BIT_POS_IST_UNTEN	1
#define BIT_POS_NACH_OBEN	4
#define BIT_POS_NACH_UNTEN	5
#define BIT_POS_FAHRE_NACH_OBEN	9
#define BIT_POS_MOTOR_AN	8
#define BIT_POS_FAHRE_NACH_UNTEN	10

typedef enum {hoch, runter, steht} STATE;	// Datentyp für den Zustand das Automaten.
                                            // Vergleichbar mit "TYPE .... IS" in VHDL.
typedef unsigned short int USHORT;			// Eigener Datentyp zur Abkuerzung


void steuerungsfunktion    (	USHORT ist_oben, USHORT ist_unten,
                        USHORT nach_oben, USHORT nach_unten,
                        USHORT* p_fahre_nach_oben,
                        USHORT* p_fahre_nach_unten,
                        STATE* p_state	)
{

    // 5.)	switch-case-Konstrukt zur "Verwaltung" der Zustaende
    switch (*p_state) {

        // 6.)	Ein CASE je Zustand
        case steht:

            // 7a.)  .... Ausgabesignale bestimmen
            *p_fahre_nach_unten=0; *p_fahre_nach_oben=0;

            // 8.)    Eingabesignale auswerten und Zustandswechsel herbei fuehren
            //         Ein IF je Pfeil
            if (  (ist_unten == 0) && (nach_unten == 1) && (nach_oben == 0)) {
                *p_state = runter; // Wechsel in den Zustand "runter"
            }
            if (  (ist_oben == 0) && (nach_oben == 1) ){
                *p_state = hoch;  // Wechsel in den Zustand "hoch"
            }

            // Diese if-Anweisung kann entfallen, da sie cstate nicht veraendert.
            if ( !(    ((ist_unten == 0) && (nach_unten == 1) && (nach_oben == 0))
                    || ((ist_oben == 0) && (nach_oben == 1)) )      ) {
                *p_state = steht; // Bleibe im Zustand "steht"
            }
            break;

        case runter:

            // 7a.)  Ausgabesignale bestimmen
            *p_fahre_nach_unten=1; *p_fahre_nach_oben=0;


            // 8.)    Eingabesignale auswerten und Zustandswechsel herbei fuehren
            if (ist_unten == 1) {
                *p_state = steht; // Wechsel in den Zustand "steht"
            }

            // Diese if-Anweisung kann entfallen, da sie cstate nicht veraendert.
            if (ist_unten == 0) {
                *p_state = runter; // Bleibe im Zustand "runter"
            }

            break;

        case hoch:

            // 7a.)  Ausgabesignale bestimmen
            *p_fahre_nach_unten=0; *p_fahre_nach_oben=1;

            // 8.)    Eingabesignale auswerten und Zustandswechsel herbei fuehren
            if (ist_oben == 1){
                *p_state = steht; // Wechsel in den Zustand "steht"
            }

            // Diese if-Anweisung kann entfallen, da sie cstate nicht veraendert.
            if (ist_oben == 0){
                *p_state = hoch; // Bleibe im Zustand "hoch"
            }
            break;

        // 9.) nicht erlaubte Zustaende "abfangen"
        default:
            *p_state = runter;

    } // end switch
} // end steuerungsfunktion()




void emain(void* arg)
{
    STATE cstate;		// Variable für den Zustand das Automaten.

    // Variablen für die Eingabesignale. Eine Variable fuer jedes Signal.
    USHORT		nach_oben;
    USHORT		nach_unten;
    USHORT		ist_oben;
    USHORT		ist_unten;

    // Variablen für die Ausgabesignale. Eine Variable fuer jedes Signal.
    USHORT		fahre_nach_oben;
    USHORT		fahre_nach_unten;

    USHORT		input, output, last_output;
    USHORT      temp, outputdir_bitmuster, clear_bitmuster, inputdir_bitmuster;


    INIT_BM_WITH_REGISTER_UI;	// Nur fuer Simulation

    // Bitmuster zum Setzen und Loeschen der Bits 8, 9 und 10 einmaig berechnen
    outputdir_bitmuster = (1<<BIT_POS_FAHRE_NACH_UNTEN) | (1<< BIT_POS_FAHRE_NACH_OBEN) | (1<< BIT_POS_MOTOR_AN);
    clear_bitmuster = ~outputdir_bitmuster;
    inputdir_bitmuster = (1 << BIT_POS_IST_OBEN)|(1 << BIT_POS_IST_UNTEN)|(1 << BIT_POS_NACH_OBEN)|(1 << BIT_POS_NACH_UNTEN);


    // 1.)	Hardware konfigurieren
    temp = io_in16(DIR1);
    temp = temp | outputdir_bitmuster; // Ausgang: Bits 10 bis 8 auf "1"
    temp = temp & ~inputdir_bitmuster;    // Eingang: Bits 0, 1, 4, 5 auf "0"
    io_out16(DIR1, temp);

    // 2.)	 Definition des Startzustandes. Entspricht dem asynchronen Reset in VHDL.
    cstate = runter;

    // 3.) Unendliche Schleife. Ein Schleifendurchlauf entspricht einem Zyklus des Automaten
    while (1) {

        SYNC_SIM; // Nur fuer Simulation

        // 4.)	Einlesen der Eingabesignale einmal je Zyklus
        input = io_in16(IN1);

        // extrahieren von "ist_oben" (BIT_POS_IST_OBEN)
        ist_oben = (input >> BIT_POS_IST_OBEN) & 0x01;

        // extrahieren von "ist_unten" (BIT_POS_IST_UNTEN)
        ist_unten = (input >> BIT_POS_IST_UNTEN) & 0x01;

        // extrahieren von "nach_oben" (BIT_POS_NACH_OBEN)
        nach_oben = (input >> BIT_POS_NACH_OBEN) & 0x01;

        // extrahieren von "nach_unten" (BIT_POS_NACH_UNTEN)
        nach_unten = (input >> BIT_POS_NACH_UNTEN) & 0x01;

        // Aufruf der Steuerungsfunktion
        steuerungsfunktion    (	ist_oben, ist_unten, nach_oben, nach_unten,
                            &fahre_nach_oben, &fahre_nach_unten, &cstate	);


        // 7b.) Ausgabesignale ausgeben
        output=fahre_nach_unten<<BIT_POS_FAHRE_NACH_UNTEN;
        output=output | (fahre_nach_oben<< BIT_POS_FAHRE_NACH_OBEN);
        output=output | (1<< BIT_POS_MOTOR_AN);   // Nur fuer Bandmodell



        // Nur wirklich ausgeben wenn notwendig
        // Optimierung mittels bedigter Portausgabe
        if (last_output != output) {
            temp = io_in16(OUT1);
            temp = temp  & clear_bitmuster;
            temp = temp |  output;

            io_out16(OUT1, temp);
            last_output = output;
        }
    } // end while
} // end main
#endif // rollosteuerung_moore_mit_steuerungsfunktion


#ifdef rollosteuerung_mealy_mit_steuerungsfunktion

/*
    ES GELTEN DIE BEI "rollosteuerung_moore" AN GLEICHER STELLE VORANGESTELLTEN KOMMENTARE
*/

// Einige Defines zum leichteren Wiederfinden
#define BIT_POS_IST_OBEN	0
#define BIT_POS_IST_UNTEN	1
#define BIT_POS_NACH_OBEN	4
#define BIT_POS_NACH_UNTEN	5
#define BIT_POS_FAHRE_NACH_OBEN	9
#define BIT_POS_MOTOR_AN	8
#define BIT_POS_FAHRE_NACH_UNTEN	10

typedef enum {hoch, runter, steht} STATE;	// Datentyp für den Zustand das Automaten.
                                            // Vergleichbar mit "TYPE .... IS" in VHDL.
typedef unsigned short int USHORT;			// Eigener Datentyp zur Abkuerzung


void steuerungsfunktion    (	USHORT ist_oben, USHORT ist_unten, USHORT nach_oben,
                        USHORT nach_unten,
                        USHORT* p_fahre_nach_oben, USHORT* p_fahre_nach_unten,
                        STATE* p_state	)
{

    // 5.)	switch-case-Konstrukt zur "Verwaltung" der Zustaende
    switch (*p_state) {

        // 6.)	Ein CASE je Zustand
        case steht:

            // 8.)    Eingabesignale auswerten und Zustandswechsel herbei fuehren
            //         Ein IF je Pfeil
            if (  (ist_unten == 0) && (nach_unten == 1) && (nach_oben == 0)) {

                // 7a.)  Ausgabesignale bestimmen
                *p_fahre_nach_unten=1; *p_fahre_nach_oben=0;

                *p_state = runter; // Wechsel in den Zustand "runter"
            }

            if (  (ist_oben == 0) && (nach_oben == 1) ){
                // 7a.)  Ausgabesignale bestimmen
                *p_fahre_nach_unten=0; *p_fahre_nach_oben=1;

                *p_state = hoch;  // Wechsel in den Zustand "hoch"
            }

            // Diese if-Anweisung kann entfallen, da sie den Zustand nicht veraendert
            // und die Ausgabesignale nicht neu bestimmt werden muessen.
            if ( !(    ((ist_unten == 0) && (nach_unten == 1) && (nach_oben == 0))
                    || ((ist_oben == 0) && (nach_oben == 1)) )      ) {
                // 7a.)  Ausgabesignale bestimmen
                *p_fahre_nach_unten=0; *p_fahre_nach_oben=0;

                *p_state = steht; // Bleibe im Zustand "steht"
            }
            break;


        case runter:

            // 8.)    Eingabesignale auswerten und Zustandswechsel herbei fuehren
            if (ist_unten == 1) {
                // 7a.)  Ausgabesignale bestimmen
                *p_fahre_nach_unten=0; *p_fahre_nach_oben=0;

                *p_state = steht; // Wechsel in den Zustand "steht"
            }

            // Diese if-Anweisung kann entfallen, da sie cstate nicht veraendert
            // und die Ausgabesignale nicht neu bestimmt werden muessen.
            if (ist_unten == 0) {
                // 7a.)  Ausgabesignale bestimmen
                *p_fahre_nach_unten=1; *p_fahre_nach_oben=0;

                *p_state = runter; // Bleibe im Zustand "runter"
            }

            break;

        case hoch:

            // 8.)    Eingabesignale auswerten und Zustandswechsel herbei fuehren
            if (ist_oben == 1){
                // 7a.)  Ausgabesignale bestimmen
                *p_fahre_nach_unten=0; *p_fahre_nach_oben=0;

                *p_state = steht; // Wechsel in den Zustand "steht"
            }

            // Diese if-Anweisung kann entfallen, da sie cstate nicht veraendert
            // und die Ausgabesignale nicht neu bestimmt werden muessen.
            if (ist_oben == 0){
                // 7a.)  Ausgabesignale bestimmen
                *p_fahre_nach_unten=0; *p_fahre_nach_oben=1;

                *p_state = hoch; // Bleibe im Zustand "hoch"
            }
            break;

        // 9.) nicht erlaubte Zustaende "abfangen"
        default:
            // 7a.)  Ausgabesignale bestimmen
            *p_fahre_nach_unten=1; *p_fahre_nach_oben=0;
            *p_state = runter;

    } // end switch
} // end steuerungsfunktion()




void emain(void* arg)
{
    STATE cstate;		// Variable für den Zustand das Automaten.

    // Variablen für die Eingabesignale. Eine Variable fuer jedes Signal.
    USHORT		nach_oben;
    USHORT		nach_unten;
    USHORT		ist_oben;
    USHORT		ist_unten;

    // Variablen für die Ausgabesignale. Eine Variable fuer jedes Signal.
    USHORT		fahre_nach_oben;
    USHORT		fahre_nach_unten;

    USHORT		input, output, last_output;
    USHORT      temp, outputdir_bitmuster, clear_bitmuster, inputdir_bitmuster;


    INIT_BM_WITH_REGISTER_UI;	// Nur fuer Simulation

    // Bitmuster zum Setzen und Loeschen der Bits 8, 9 und 10 einmaig berechnen
    outputdir_bitmuster = (1<<BIT_POS_FAHRE_NACH_UNTEN) | (1<< BIT_POS_FAHRE_NACH_OBEN) | (1<< BIT_POS_MOTOR_AN);
    clear_bitmuster = ~outputdir_bitmuster;
    inputdir_bitmuster = (1 << BIT_POS_IST_OBEN)|(1 << BIT_POS_IST_UNTEN)|(1 << BIT_POS_NACH_OBEN)|(1 << BIT_POS_NACH_UNTEN);


    // 1.)	Hardware konfigurieren
    temp = io_in16(DIR1);
    temp = temp | outputdir_bitmuster; // Ausgang: Bits 10 bis 8 auf "1"
    temp = temp & ~inputdir_bitmuster;    // Eingang: Bits 0, 1, 4, 5 auf "0"
    io_out16(DIR1, temp);

    // 2.)	 Definition des Startzustandes. Entspricht dem asynchronen Reset in VHDL.
    cstate = runter;

    // Hier steht das, was an der Kante nach Start zu tun ist.
    fahre_nach_unten=1;  fahre_nach_oben=0;
    output=fahre_nach_unten<<BIT_POS_FAHRE_NACH_UNTEN; output=output | (fahre_nach_oben<< BIT_POS_FAHRE_NACH_OBEN);
    io_out16(OUT1, io_in16(OUT1) & 0x00FF); io_out16(OUT1, io_in16(OUT1) |  output);


    // 3.) Unendliche Schleife. Ein Schleifendurchlauf entspricht einem Zyklus des Automaten
    while (1) {

        SYNC_SIM; // Nur fuer Simulation

        // 4.)	Einlesen der Eingabesignale einmal je Zyklus
        input = io_in16(IN1);

        // extrahieren von "ist_oben" (BIT_POS_IST_OBEN)
        ist_oben = (input >> BIT_POS_IST_OBEN) & 0x01;

        // extrahieren von "ist_unten" (BIT_POS_IST_UNTEN)
        ist_unten = (input >> BIT_POS_IST_UNTEN) & 0x01;

        // extrahieren von "nach_oben" (BIT_POS_NACH_OBEN)
        nach_oben = (input >> BIT_POS_NACH_OBEN) & 0x01;

        // extrahieren von "nach_unten" (BIT_POS_NACH_UNTEN)
        nach_unten = (input >> BIT_POS_NACH_UNTEN) & 0x01;

        // Aufruf der Steuerungsfunktion
        steuerungsfunktion    (	ist_oben, ist_unten, nach_oben, nach_unten,
                            &fahre_nach_oben, &fahre_nach_unten, &cstate	);


        // 7b.) Ausgabesignale ausgeben
        output=fahre_nach_unten<<BIT_POS_FAHRE_NACH_UNTEN;
        output=output | (fahre_nach_oben<< BIT_POS_FAHRE_NACH_OBEN);
        output=output | (1<< BIT_POS_MOTOR_AN);   // Nur fuer Bandmodell



        // Nur wirklich ausgeben wenn notwendig
        // Optimierung mittels bedigter Portausgabe
        if (last_output != output) {
            temp = io_in16(OUT1);
            temp = temp  & clear_bitmuster;
            temp = temp |  output;

            io_out16(OUT1, temp);
            last_output = output;
        }
    } // end while
} // end main
#endif // rollosteuerung_mealy_mit_steuerungsfunktion

