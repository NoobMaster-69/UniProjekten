package Ubung2_GruppeA;

public class DualSpeicherStr {

	//Attribut für 2 Elemente String

	private String str1="abc";
	private String str2="def";
	
	//Alternativ:
	private String[] inhalt= null;
	
	//Alternativ3
	//Liste usw.
	
	//KONSTRUKTOR 
	DualSpeicherStr(){
		inhalt= new String[2];
	}
	
	
	public int storeObject(String t){
		
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
	
	public String getObject(int position){
		String ret;
		
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
