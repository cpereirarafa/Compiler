/*

	Universidade Federal De Sao Carlos - Campus Sorocaba
	Compiladores - 01/2017
	Andre Domingues Vieira  -   511420
	Rafael Camara Pereira	-	380431


*/

package AST;

public class ReturnStmt extends SimpleStmt {

	OrTest orTest;

	public ReturnStmt(OrTest orTst) {

		orTest = orTst;
		
	}

	public OrTest getOrTest() {
		return orTest;
	}

	public void setOrTest(OrTest orTst) {
		orTest = orTst;
	}

	public void genC(PW pw) {

		pw.print("return");
		
		if(orTest != null){
			
			pw.out.print(" ");
			orTest.genC(pw);
			
		}
		
		pw.out.println(";");
		
	}
}
