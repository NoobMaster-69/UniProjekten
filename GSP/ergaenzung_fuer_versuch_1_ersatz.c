

#define ergaenzung_fuer_versuch_1_ersatz



#ifdef ergaenzung_fuer_versuch_1_ersatz

// Zum Testen des Programms gehen Sie wie folgt vor:
//
// 1. Einen Break-Point an die erste Befehlszeile unterhalb von „SYNC_SIM“ setzen.
//
// 2. Das Simulationstool User_Interface.exe starten. (Bem.: Gemaess der in der Vorlage verwendeten
//    Anweisung „INIT_REGISTER_UI_WITHOUT_BM“ wird das Bandmodell nicht angeschlossen
//    Es darf auch nicht angeschlossen werden weil es dann haendisch zu editierende Bits direkt
//    wieder ueberschrieben wuerde.
//
// 3. Das Debuggen starten.
//
// 4. Im User_interface das Freeze deaktivieren und das Programm bis zum Break-Point laufen lassen.
//
// 5. Simulation eines Tastendrucks T1 (T2) unter Nutzung des User_Interface indem Sie manuell das
//     Bit4 (Bit5) des IN-Registers des GPIO_PORT_1 setzen.
//
// 6. Durch F5-Tastendruck den Debugger weiter laufen lassen und im User_Interface die Bits 9 bis 12
//    (L1 bis L4) des GPIO_PORT_1 beobachten.
//
// 7. Abhängig vom aktivierten Taster müssen sich diese Bits gemaess Aufgabenstellung korrekt aendern.
//
// ACHTUNG: Hinsichtlich der Verwendung von Freeze ist folgendes zu beachten:
//          Falls Sie ein schon laufendes Programm mit der Freeze-Funktionalität des User-Interfaces
//          unterbrechen, ist zu beruecksichtigen, dass das System nach dem Deaktivieren des
//          Freeze bis zu 5s brauchen kann um „aufzutauen“.

void emain(void* arg)
{
    unsigned short int T1, T2;
    unsigned short int temp;

    // Mit den folgenden beiden Defines kann bestimmt werden welche Simulations-Tools verwendet werden.
    // Diese MUESSEN dann auch gestartet werden.
    // ######### IN DIESEM VERSUCH MUESSEN SIE OHNE DAS BANDMODELL ARBEITEN,             ##############
    // ######### da dieses sonst die Eingabe ueber das User-Interface "ueberschreibt".   ##############
    //INIT_BM_WITH_REGISTER_UI;		// Es werden beide Simulations-Tools (Bandmodell.exe und User_Interface.exe) verwendet
    INIT_REGISTER_UI_WITHOUT_BM		// Es wird nur das User-Interface zur Registerdarstellung (User_Interface.exe) verwendet.



    // Initialisierung der Richtungen fuer die benoetigten Input-Bits des Port 1
    temp = (1 << 5) | (1 << 4);
    temp = ~temp;
    io_out16 (DIR1, ( io_in16(DIR1) & temp) );

    // Initialisierung der Richtungen fuer die benoetigten Ouput-Bits des Port 1
    temp = (1 << 12) | (1 << 11) | (1 << 10) | (1 << 9);
    io_out (DIR1, ( io_in(DIR1) |  temp) );


    while(1) {

        SYNC_SIM;

        // Einlesen des Wertes von T1 (T2), der an Bit 4(5) angeschlossen ist.
        // Gegenueber der bisherigen ausfuehrlichen Art und Weise ´geschieht dies hier etwas kuerzer
        temp = io_in16(IN1);
        T1 = (temp >> 4) & 0x01;
        T2 = (temp >> 5) & 0x01;


        // Abhaengig von den vier moeglichen Kombinationen von T1 und T2 etwas anderes machen
        // Das was gemacht wird muessen Sie entsprechend der Aufgabenstellung von A7 anpassen.
        // Hierbei ist es ratsam die in der Loesung der Aufgabe A5 (Manipulation von Bitgruppen)
        // vorgestellte Schablone zu verwenden.

        if(T2 == 0 && T1 == 0) {
            // Beispielhaft alle 4 Ausgabebits (9 bis 12) auf 0 setzen
            temp = (1 << 12) | (1 << 11) | (1 << 10) | (1 << 9);
            temp = ~temp;
            io_out16 (OUT1, ( io_in16(OUT1) & temp) );
        }

        if(T2 == 0 && T1 == 1) {
            // Beispielhaft die Ausgabebits 9 und 10 auf 1 setzen und dabei die Bits 11 und 12 unveraendert lassen
            temp = (1 << 9)| (1 << 10) ;
            io_out16 (OUT1, ( io_in16(OUT1) | temp) );

        }

        if(T2 == 1 && T1 == 0) {
            // Beispielhaft die Ausgabebits 11 und 12 auf 1 setzen und dabei die Bits 9 und 10 unveraendert lassen
            temp = (1 << 11)| (1 << 12) ;
            io_out16 (OUT1, ( io_in16(OUT1) | temp) );

        }


        if(T2 == 1 && T1 == 1) {
            // Beispielhaft die Ausgabebits 9 und 11 auf 1 setzen und dabei die Bits 10 und 12 unveraendert lassen
            temp = (1 << 9)| (1 << 11) ;
            io_out16 (OUT1, ( io_in16(OUT1) | temp) );
        }

    }
}

#endif // ergaenzung_fuer_versuch_1_ersatz


