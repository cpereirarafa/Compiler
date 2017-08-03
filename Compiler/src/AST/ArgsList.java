/*

	Universidade Federal De Sao Carlos - Campus Sorocaba
	Compiladores - 01/2017
	Andre Domingues Vieira  -   511420
	Rafael Camara Pereira	-	380431


*/

package AST;

import java.util.ArrayList;

public class ArgsList {

	ArrayList<Type> typeLst;
	ArrayList<NameArray> nameLst;
	
	public ArgsList(ArrayList<Type> tyLst, ArrayList<NameArray> naLst) {
		typeLst = tyLst;
		nameLst = naLst;
	}

	public Type getType(int i) {
		return typeLst.get(i);
	}
	
	public ArrayList<Type> getTypeLst() {
		return typeLst;
	}

	public void setTypeLst(ArrayList<Type> tyLst) {
		typeLst = tyLst;
	}

	public ArrayList<NameArray> getNameLst() {
		return nameLst;
	}

	public void setNameLst(ArrayList<NameArray> naLst) {
		nameLst = naLst;
	}

	public void genC(PW pw) {
		
		for(int i = 0; i < typeLst.size(); i++){
			
			if(i != 0)
				pw.out.print(", ");
			
			switch(typeLst.get(i).getType().toString()){
			case "char":
				typeLst.get(i).genC(pw);
				pw.out.print(" *");
				break;
			default:
				typeLst.get(i).genC(pw);
				pw.out.print(" ");
				break;
			}
			nameLst.get(i).genC(pw);
			
		}
		
	}

}
