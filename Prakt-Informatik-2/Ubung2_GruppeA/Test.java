package Ubung2_GruppeA;

public class Test {

	public static void main(String[] args) {
		Test test= new Test();
		test.test01();

	}// MAIN
	
	private void test01() {
		DualSpeicherStr d= new DualSpeicherStr();
		d.storeObject("hallo");
		d.storeObject("hallo2");
		d.print();
	}
	
	private void test02() {
		DualSpeicherGen d= new DualSpeicherGen();
		d.storeObject("hallo");
		d.storeObject("hallo2");
		d.print();
	}
	
	

}//CLASS
