package Ubung1;

public class User {
	
	public String kennung;
	private String passwort;
	
	public static int anzahlBenutzer = 0;  //Klassenbezogenes Attribut, Momentan vorhandenen Benutzerkonten
	
	private final String StdKenn = "StdKenn";
	private final String Stdpw = "ganzgeheim";
	
	
	public User() {  // KONSTRUKTOR 1 
		anzahlBenutzer ++;
		
		this.kennung = StdKenn;
		this.passwort= Stdpw; //Kein guter Stiel / Sicherheitslücke
	}//Public User ENDE

	public User(String kennung) { //KONSTRUKTOR 2
		new User();
		this.kennung = kennung;
		this.passwort= Stdpw; //Kein guter Stiel
	}

	public User(String kennung, String passwort) { //KONSTRUKTOR 3 mit Kennung und Passwort
		new User();
		this.kennung = kennung;
		this.passwort = passwort;
	}
	
	public int checkPasswort(String passwort) {
		
		if(passwort.equals(this.passwort) ) {
			return 0; //falls es stimmt.
		} else {
		return -1; //falls es nicht Stimmt.
		} //IF ELSE
	}//Methode checkPasswort
	

	public int check2Passwort(String passwort) {
		
		if(passwort.equals(this.passwort) ) {
			return 0; //falls es stimmt.
		}else { 
			return -1; //falls es nicht Stimmt.
		} //IF ELSE
	}//Methode checkPasswort
	

	public int setPasswort(String altPw,String neuPw) {
		// checkpasswort..passwort.Alternative;
		
		if(altPw.equalsIgnoreCase(this.passwort)) {
			this.passwort = neuPw;
		return 0; //falls es Stimmt
		} else {
		return -1; //falls es Fehlschlag
		}
	}//Ende Methode setPasswort
	
}//CLASS User




