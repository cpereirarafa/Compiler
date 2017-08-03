/*

	Universidade Federal De Sao Carlos - Campus Sorocaba
	Compiladores - 01/2017
	Andre Domingues Vieira  -   511420
	Rafael Camara Pereira	-	380431


*/

package AST;

public class StringType {
	
	private String str;
	
	public StringType(String st){
		
		str = st;
		
	}
	
	public String getStr() {
		return str;
	}

	public void setStr(String str) {
		this.str = str;
	}
	
	public void genC(PW pw){

		pw.out.print(str);
		
	}
}