/*

	Universidade Federal De Sao Carlos - Campus Sorocaba
	Compiladores - 01/2017
	Andre Domingues Vieira  -   511420
	Rafael Camara Pereira	-	380431


*/

package AST;

import Lexer.Symbol;

public class ExprStmt extends SimpleStmt{

	Name name;
	Atom atom;
	OrTest ortst;
	OrList orlist;
	
	public ExprStmt(Name na, Atom at, OrTest ot, OrList ol){
		
		name = na;
		atom = at;
		ortst = ot;
		orlist = ol;
	}
	
	public Name getName() {
		return name;
	}

	public void setName(Name name) {
		this.name = name;
	}
	
	public Atom getAtom() {
		return atom;
	}

	public void setAtom(Atom atom) {
		this.atom = atom;
	}

	public OrTest getOrtst() {
		return ortst;
	}

	public void setOrtst(OrTest ortst) {
		this.ortst = ortst;
	}

	public OrList getOrlist() {
		return orlist;
	}

	public void setOrlist(OrList orlst) {
		orlist = orlst;
	}

	public void genC(PW pw){
		
		pw.print("");
		
		if(name.getType() == Symbol.CHAR.toString()){
				
			pw.out.print("strcpy(");
			name.genC(pw);
			if(atom != null){
				pw.out.print("[");
				atom.genC(pw);
				pw.out.print("]");
			}
			pw.out.print(", \"");
			ortst.genC(pw);
			pw.out.println("\");");
			return;
			
		}

		if(ortst != null){
			
			name.genC(pw);
			
			if(atom != null){
				pw.out.print("[");
				atom.genC(pw);
				pw.out.print("]");
			}
			
			pw.out.print(" = ");
			ortst.genC(pw);
			
		} else {
			
			pw.out.print("memcpy(");
			name.genC(pw);
			if(atom != null){
				pw.out.print("[");
				atom.genC(pw);
				pw.out.print("]");
			}
			
			switch(name.getType()){
			
			case "boolean":
				pw.out.print(", (int[]) {");
				break;
			case "string":
				pw.out.print(", (char[]) {");
				break;
			default:
				pw.out.print(", (" + name.getType() + "[]) {");
				
			}
			
			orlist.genC(pw);
			pw.out.print("}, sizeof(");
			name.genC(pw);
			if(atom != null){
				pw.out.print("[");
				atom.genC(pw);
				pw.out.print("]");
			}
			pw.out.print("))");
				
		}
		
		pw.out.println(";");

	}	
}