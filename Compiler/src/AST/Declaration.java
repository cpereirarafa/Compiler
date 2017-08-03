/*

	Universidade Federal De Sao Carlos - Campus Sorocaba
	Compiladores - 01/2017
	Andre Domingues Vieira  -   511420
	Rafael Camara Pereira	-	380431


*/

package AST;

import java.util.*;

public class Declaration {

	ArrayList<Type> type;
	ArrayList<IdList> idLst;
	
	public Declaration(ArrayList<Type> t, ArrayList<IdList> i){
		
		type = t;
		idLst = i;
		
	}
	
	public ArrayList<Type> getType() {
		return type;
	}

	public void setType(ArrayList<Type> type) {
		this.type = type;
	}
	
	public ArrayList<IdList> getIdLst() {
		return idLst;
	}

	public void setIdLst(ArrayList<IdList> idLst) {
		this.idLst = idLst;
	}

	public void genC(PW pw){
		
		int i;
		
		for(i = 0; i < type.size(); i++){
			
			pw.print("");
			type.get(i).genC(pw);
			pw.out.print(" ");
			idLst.get(i).genC(pw);
			pw.out.println(";");
			
		}	
	}	
}