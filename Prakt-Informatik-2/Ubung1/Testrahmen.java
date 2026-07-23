package Ubung1;

public class Testrahmen {

	public static void main(String[] args) {
		Testrahmen test = new Testrahmen();
		test.test();
	}
	
	private void test() {
		System.out.println("progStart");
		
		Windows_User windowsUser= new Windows_User();
		windowsUser.kennung="123myname";
		
		Windows_User windowsUser2 = new Windows_User();
		
		System.out.println(User.anzahlBenutzer);
		System.out.println("progStart");
		
	}//METHODE Test

	/* private void testUnix() {
		Unix_User unixi= new Unix_User();
		
	} */
	
	public void testAufgabe3() {
		 User a1 = new User();
		 Windows_User a2 = new Windows_User();
		 
		 User b1 = new Windows_User();
		 Windows_User b2 = new User();
		 
		 User c1 = new User(); //dinamisch
		 Windows_User c2 = c1; 
		 
		 User d1 = new User();
		 Windows_User d2 = (Windows_User) d1; //laufzeitsfehler
		 
		 User e1 = new Windows_User(); //kein Comp. und LaufZ , dinamisch
		 Windows_User e2 = e1;			//Statisch , e2 hat Comp.fehler
		 
		 User f1 = new Windows_User();	// 
		 Windows_User f2 = (Windows_User) f1;  //lauft ganz normal
		 
		 Windows_User g2 = new Windows_User();  //Ganz normal
		 User g1 = g2;			//Funkt ganz normal
		 
		 Windows_User h2 = new User(); //Comp.fehler 
		 User h1 = h2;		//Nicht mehr relevant, 
		 
		 Windows_User i2 = new User();  //Comp.fehler auftreten
		 User i1 = (User) i2; //Nicht mehr relevant
		
		
	}//Test aufgabe 3
	
}
