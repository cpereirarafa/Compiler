/*

	Universidade Federal De Sao Carlos - Campus Sorocaba
	Compiladores - 01/2017
	Andre Domingues Vieira  -   511420
	Rafael Camara Pereira	-	380431


*/

package AST;

import java.util.ArrayList;

public class Factor {

	String signal;
	AtomExpr atomExpr;
	ArrayList<Factor> facLst;
	
	public Factor(String sig, AtomExpr at, ArrayList<Factor> fl) {
		
		signal = sig;
		atomExpr = at;
		facLst = fl;
		
	}

	public String getSignal() {
		return signal;
	}

	public void setSignal(String signal) {
		this.signal = signal;
	}

	public AtomExpr getAtomExpr() {
		return atomExpr;
	}

	public void setAtomExpr(AtomExpr atomExpr) {
		this.atomExpr = atomExpr;
	}

	public ArrayList<Factor> getFacLst() {
		return facLst;
	}

	public void setFacLst(ArrayList<Factor> facLst) {
		this.facLst = facLst;
	}
	
	public void genC(PW pw){
		
		int i;
		
		if(signal != null)
			pw.out.print(signal);
		
		if(facLst.size() == 0)
			atomExpr.genC(pw);
		
		for(i = 0; i < facLst.size(); i++){
			
			pw.out.print("pow(");
			
			atomExpr.genC(pw);
			
			pw.out.print(", ");
			facLst.get(i).genC(pw);
			
			pw.out.print(")");
			
		}
	}
	
}
