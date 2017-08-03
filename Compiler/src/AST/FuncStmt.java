/*

	Universidade Federal De Sao Carlos - Campus Sorocaba
	Compiladores - 01/2017
	Andre Domingues Vieira  -   511420
	Rafael Camara Pereira	-	380431


*/

package AST;

public class FuncStmt extends SimpleStmt {

	Name name;
	OrList orList;

	public FuncStmt(Name na, OrList orLst) {
		
		name = na;
		orList = orLst;
		
	}

	public Name getName() {
		return name;
	}

	public void setName(Name na) {
		name = na;
	}

	public OrList getOrList() {
		return orList;
	}

	public void setOrList(OrList orLst) {
		orList = orLst;
	}

	public void genC(PW pw) {
		
		pw.print("");
		name.genC(pw);
		pw.out.print("(");
		
		if(orList != null)
			orList.genC(pw);
		
		pw.out.println(");");

	}
}
