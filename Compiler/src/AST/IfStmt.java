/*

	Universidade Federal De Sao Carlos - Campus Sorocaba
	Compiladores - 01/2017
	Andre Domingues Vieira  -   511420
	Rafael Camara Pereira	-	380431


*/

package AST;

import java.util.ArrayList;

public class IfStmt extends CompoundStmt{

	OrTest orTst;
	ArrayList<Stmt> ifStmt, elStmt;
	
	public IfStmt(OrTest ot, ArrayList<Stmt> ifS, ArrayList<Stmt> elS){
		
		orTst = ot;
		ifStmt = ifS;
		elStmt = elS;
		
	}
	
	public OrTest getOrTst() {
		return orTst;
	}

	public void setOrTst(OrTest orTst) {
		this.orTst = orTst;
	}

	public ArrayList<Stmt> getIfStmt() {
		return ifStmt;
	}

	public void setIfStmt(ArrayList<Stmt> ifStmt) {
		this.ifStmt = ifStmt;
	}

	public ArrayList<Stmt> getElStmt() {
		return elStmt;
	}

	public void setElStmt(ArrayList<Stmt> elStmt) {
		this.elStmt = elStmt;
	}

	public void genC(PW pw){
		
		pw.print("if(");
		orTst.genC(pw);
		pw.out.println("){");
		pw.add();
		for(int i = 0; i < ifStmt.size(); i++)
			ifStmt.get(i).genC(pw);
		if(elStmt.size() != 0){
			pw.sub();
			pw.println("} else {");
			pw.add();
			for(int j = 0; j < elStmt.size(); j++)
				elStmt.get(j).genC(pw);
		}
		pw.sub();
		pw.println("}");
		
	}
}