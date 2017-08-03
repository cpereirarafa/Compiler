/*

	Universidade Federal De Sao Carlos - Campus Sorocaba
	Compiladores - 01/2017
	Andre Domingues Vieira  -   511420
	Rafael Camara Pereira	-	380431


*/

package AST;

public class BreakStmt extends SimpleStmt{
	
	public void genC(PW pw){
		
		pw.println("break;");
		
	}
}