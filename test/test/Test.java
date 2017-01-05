package test;


public class Test {

	public static void main(String[] args) {
		//long maxno = 1000000000000000000l;
		long maxno = 901100093l;
		int threadnum = 50;
		long increment = maxno / threadnum;
		long no = 100000000;
		while(no<maxno){
			Queryor query = new Queryor(no, no+increment);
			no += increment;
			new Thread(query).start();
		}
	}
}
