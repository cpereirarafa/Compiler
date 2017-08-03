/*

	Universidade Federal De Sao Carlos - Campus Sorocaba
	Compiladores - 01/2017
	Andre Domingues Vieira  -   511420
	Rafael Camara Pereira	-	380431


*/

package AST;

import java.util.*;

public class PrintStmt extends SimpleStmt{

	ArrayList<OrTest> orList;
	
	public PrintStmt(ArrayList<OrTest> orLst){
		
		orList = orLst;
	}

	public ArrayList<OrTest> getOrList() {
		return orList;
	}

	public void setOrList(ArrayList<OrTest> orList) {
		this.orList = orList;
	}

	public void genC(PW pw){
		
		int i = 0;
		Expr exp;
		AtomExpr atExp;
		ArrayList<Expr> ident = new ArrayList<Expr>(); 
		
		pw.print("printf(\"");
		
		// 'print' OrTest {',' OrTest} ';'
		while(i < orList.size()){
			
			exp = orList.get(i).getAndtest().get(0).getNottest().get(0).getComp().geteLeft();
			atExp = orList.get(i).getAndtest().get(0).getNottest().get(0).getComp().geteLeft().gettList().get(0).getfList().get(0).getAtomExpr();

			if(atExp.getAtom().getString() != null){
				
				orList.get(i).genC(pw);
				
			} else if (atExp.getAtom().getName().getName() != null){
				
				ident.add(exp);
				switch(atExp.getAtom().getName().getType()){
				case "int":
					pw.out.print("%d");
					break;
				case "boolean":
					pw.out.print("%d");
					break;
				case "float":
					pw.out.print("%f");
					break;
				case "char":
					if(atExp.getDetails() != null)
						pw.out.print("%c");
					else
						pw.out.print("%s");
					break;		
				}
			} else if(atExp.getAtom().getNumber() != null){
				
				ident.add(exp);
				
				if(atExp.getAtom().getNumber().getFrac() != null){
					
					pw.out.print("%f");
					
				} else {
					
					pw.out.print("%d");
					
				}
				
			} else if(atExp.getAtom().isTrueAt()){
				
				pw.out.print("true");
				
			} else {
				
				pw.out.print("false");
				
			}
				i++;
		}

		if(ident.size() == 0)
			pw.out.println("\");");
		else {
			
			pw.out.print("\"");
			
			for(i = 0; i < ident.size(); i++){
				
				pw.out.print(", ");
				ident.get(i).genC(pw);
				
			}
			
			pw.out.println(");");
			
		}
	}
}