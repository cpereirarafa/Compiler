/*

	Universidade Federal De Sao Carlos - Campus Sorocaba
	Compiladores - 01/2017
	Andre Domingues Vieira  -   511420
	Rafael Camara Pereira	-	380431


*/

package AST;

public class Name{
	
	private String name;
	private String type;
	
	public Name(String n, String ty){
		
		name = n;
		type = ty;
		
	}
	
	public String getType() {
			
		return type;
		
	}
	
	public void setType(String ty) {
		
		type = ty;
		
	}
	
	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}
	
	public void genC(PW pw){
		
		pw.out.print(name);
		
	}
}