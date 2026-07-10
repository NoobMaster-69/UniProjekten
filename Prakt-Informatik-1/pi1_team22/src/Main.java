import java.util.Scanner;

/**
 * The Main class contains the main method to interact with the user by reading
 * inputs from the console.
 * 
<<<<<<< HEAD
 * @author Walid Nhaila & Heberto Aguilar 
  @version version 1.0
  Groupe 22
=======
 * @author Heberto Aguilar und Walid Nhaila
 * Gruppe 22
>>>>>>> e10b90c6096f9f36deb79476ce47806d72284bd6
 */

public class Main {
    public static void main(String[] args) {

        // this is a comment line. The compiler will ignore it

        // the line beneath provides an object called scanner that can read inputs from
        // a keyboard
        Scanner scanner = new Scanner(System.in);

        System.out.println("Enter your name: "); // prints "Enter your name: " on the screen
        String name = scanner.nextLine(); // the scanner object reads keyboard inputs and saves them in
                                          // a variable called name

        //TEAM Nummer eingefügt
        System.out.println("Enter your Team number: ");
        int teamnr = scanner.nextInt();

        System.out.println("Hello, " + name + "! , \n Your Team number is: "+teamnr); // prints "Hello, " and the typed in name plus a !

    }
}
