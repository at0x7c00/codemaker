package test;

public class Test2 {

	public static void main(String[] args) {
		String str = "a & b &(( c<d | e>f ) | g like \"google\") | h<i";
		char[] charArray = str.toCharArray();
		for(char c : charArray){
			System.out.println(c);
		}
	}
}
