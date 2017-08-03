/*

	Universidade Federal De Sao Carlos - Campus Sorocaba
	Compiladores - 01/2017
	Andre Domingues Vieira  -   511420
	Rafael Camara Pereira	-	380431


*/

package AST;

import java.util.ArrayList;

public class Expr {
	
	ArrayList<Term> tList;
	ArrayList<String> opList;
	
	public Expr(ArrayList<Term> tLst, ArrayList<String> op){
		
		tList = tLst;
		opList = op;
		
	}
	
	public ArrayList<Term> gettList() {
		return tList;
	}

	public void settList(ArrayList<Term> tList) {
		this.tList = tList;
	}

	public ArrayList<String> getOpList() {
		return opList;
	}

	public void setOpList(ArrayList<String> opList) {
		this.opList = opList;
	}

	
	public void genC(PW pw){
		
		tList.get(0).genC(pw);
		
		for(int i = 0; i < opList.size(); i++){
			
			pw.out.print(" " + opList.get(i) + " ");
			tList.get(i + 1).genC(pw);
			
		}
	}
}