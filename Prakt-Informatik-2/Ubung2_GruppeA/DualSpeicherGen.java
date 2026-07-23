package Ubung2_GruppeA;

public class DualSpeicherGen<T> {

	//Attribut für 2 Elemente String

	private String str1="abc";
	private String str2="def";
	
	private Tv="Hallo";
	
	//Test nr.1 Aufgabe 10.2
	//CL<String> cL= new String<String>();
	cL.String
	
	//Alternativ:
	private T[] inhalt= null;
	
	
	
	//Alternativ3
	//Liste usw.
	
	//KONSTRUKTOR 
	DualSpeicherGen(){
		inhalt= new(T[]) Object[2];
		
	}
	
	
	public int storeObject(T t){
		
		if(inhalt[0]==null){
			inhalt[0]=t;
			
			return 0;  //erfolgreich Speicherung 
		}
		
		if(inhalt[1]==null) {
			inhalt[1]=t;
			return 0;
		}
		
		return -1;
		
	}//Methode StoreObject
	
	public T getObject(int position){
		T ret;
		
		if(position==0){
			ret = inhalt[0];
			inhalt[0]= null;
			return ret;
			
			
		}
		
		if(position==1){
			ret = inhalt[1];
			inhalt[1]=null;
			
			return ret;
		} 
		
		return null;
		
	}//Methode GetObject
	
	public void print() {
		System.out.println("print auf Bildschirm");
		System.out.println(inhalt[0]);
		System.out.println(inhalt[1]);
	}
	
	
}//CLASS 
