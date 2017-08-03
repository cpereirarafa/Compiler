/*

	Universidade Federal De Sao Carlos - Campus Sorocaba
	Compiladores - 01/2017
	Andre Domingues Vieira  -   511420
	Rafael Camara Pereira	-	380431


*/

package AST;

import java.util.*;

public class WhileStmt extends CompoundStmt{

	OrTest orTst;
	ArrayList<Stmt> stmt;
	
	public WhileStmt(OrTest ot, ArrayList<Stmt> s){
		
		orTst = ot;
		stmt = s;
		
	}

	public OrTest getOrTst() {
		return orTst;
	}

	public void setOrTst(OrTest orTst) {
		this.orTst = orTst;
	}

	public ArrayList<Stmt> getStmt() {
		return stmt;
	}

	public void setStmt(ArrayList<Stmt> stmt) {
		this.stmt = stmt;
	}
	
	public void genC(PW pw){
		
		pw.print("while(");
		orTst.genC(pw);
		pw.out.println("){");
		pw.add();
		for(int i = 0; i < stmt.size(); i++)
			stmt.get(i).genC(pw);
		pw.sub();
		pw.println("}");
		
	}
}