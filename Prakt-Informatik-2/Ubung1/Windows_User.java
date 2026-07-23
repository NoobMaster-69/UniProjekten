package Ubung1;

public class Windows_User extends User {
		
	private boolean []rechte = new boolean[100];
	int remainDaysValid;
	
	public int setPasswort (String oldPw, String newPw) {
		int ret= super.setPasswort(oldPw, newPw);
		remainDaysValid=90;
		
		return ret;
	}//Setter Passwort

	public int setPasswort (String oldPw, String newPw, int ablaufZeit) {
		int ret= super.setPasswort(oldPw, newPw);
		remainDaysValid= ablaufZeit;
		
		return ret;
	}//Setter Passwort mit Var "ablaufZeit"
	
	public int checkPasswort(String passwort) {
		int ret;
		if(remainDaysValid<=0) {
			return -1;
		}
		ret=super.checkPasswort(passwort);
		return ret;
	}//Getter Passwort
	
	
	
}//CLASS Windows User
