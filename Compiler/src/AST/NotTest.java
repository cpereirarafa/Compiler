/*

	Universidade Federal De Sao Carlos - Campus Sorocaba
	Compiladores - 01/2017
	Andre Domingues Vieira  -   511420
	Rafael Camara Pereira	-	380431


*/

package AST;

public class NotTest {

	boolean not;
	Comparison comp;
	
	public NotTest(boolean n, Comparison c) {
		not = n;
		comp = c;
	}

	public boolean isNot() {
		return not;
	}

	public void setNot(boolean not) {
		this.not = not;
	}

	public Comparison getComp() {
		return comp;
	}

	public void setComp(Comparison comp) {
		this.comp = comp;
	}
	
	public void genC(PW pw){
		if(not)
			pw.out.print("!");

		comp.genC(pw);		
	}
}
