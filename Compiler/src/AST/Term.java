/*

	Universidade Federal De Sao Carlos - Campus Sorocaba
	Compiladores - 01/2017
	Andre Domingues Vieira  -   511420
	Rafael Camara Pereira	-	380431


*/

package AST;

import java.util.*;

public class Term {

	ArrayList<Factor> fList;
	ArrayList<String> opList;
	
	public Term(ArrayList<Factor> fLst, ArrayList<String> op){
		
		fList = fLst;
		opList = op;
		
	}


	public ArrayList<Factor> getfList() {
		return fList;
	}

	public void setfList(ArrayList<Factor> fList) {
		this.fList = fList;
	}

	public ArrayList<String> getOpList() {
		return opList;
	}

	public void setOpList(ArrayList<String> opList) {
		this.opList = opList;
	}
		
	public void genC(PW pw){
		
		fList.get(0).genC(pw);
		for(int i = 0; i < opList.size(); i++){
			
			pw.out.print(" " + opList.get(i) + " ");
			fList.get(i + 1).genC(pw);
			
		}	
	}
}