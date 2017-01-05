package test;


public class TestClass {

	public static void main(String[] args)throws Exception {
		byte d1 = 2;  
		
		
		System.out.println(d1 & 0xf0);
		
		
	/*	File file = new File("D:\\TestClass.class");
		File file2 = new File("D:\\TestClass2.txt");
		FileInputStream fis = new FileInputStream(file);
		FileOutputStream fos = new FileOutputStream(file2);
		System.out.println(fis.read()==0xCA);
		System.out.println(fis.read()==0xFE);
		System.out.println(fis.read()==0xBA);
		System.out.println(fis.read()==0xBE);
		Byte  b = Byte.valueOf((byte)0xCA);
		System.out.println(b.byteValue());
		
		fos.write(0xCA);
		fos.write(0xFE);
		fos.write(0xBA);
		fos.write(0xBE);
		System.out.println(new String(new byte[]{(byte)0xD6,(byte)0xD0},"GBK"));
		
		fos.close();
		fis.close();*/
	}
}
