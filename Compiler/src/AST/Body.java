/*

	Universidade Federal De Sao Carlos - Campus Sorocaba
	Compiladores - 01/2017
	Andre Domingues Vieira  -   511420
	Rafael Camara Pereira	-	380431


*/

package AST;

import java.util.ArrayList;

public class Body {

	Declaration dec;
	ArrayList<Stmt> stmtLst;
	
	public Body(Declaration d, ArrayList<Stmt> sL){
		
		dec = d;
		stmtLst = sL;
		
	}
	public Declaration getDec() {
		return dec;
	}

	public void setDec(Declaration dec) {
		this.dec = dec;
	}

	public ArrayList<Stmt> getStmtLst() {
		return stmtLst;
	}

	public void setStmtLst(ArrayList<Stmt> stmtLst) {
		this.stmtLst = stmtLst;
	}
	
	public void genC(PW pw){
		
		if(dec != null)
			dec.genC(pw);
		for(int i = 0; i < stmtLst.size(); i++)
			stmtLst.get(i).genC(pw);
		
	}	
}