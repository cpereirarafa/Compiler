/*

	Universidade Federal De Sao Carlos - Campus Sorocaba
	Compiladores - 01/2017
	Andre Domingues Vieira  -   511420
	Rafael Camara Pereira	-	380431


*/

package AST;

import java.util.*;

public class ForStmt extends CompoundStmt{

	Name variable;
	Number from, to;
	ArrayList<Stmt> stmt;
	
	public ForStmt(Name var, Number f, Number t, ArrayList<Stmt> s){
		
		variable = var;
		from = f;
		to = t;
		stmt = s;
		
	}

	public Name getVariable() {
		return variable;
	}

	public void setVariable(Name variable) {
		this.variable = variable;
	}

	public Number getFrom() {
		return from;
	}

	public void setFrom(Number from) {
		this.from = from;
	}

	public Number getTo() {
		return to;
	}

	public void setTo(Number to) {
		this.to = to;
	}

	public ArrayList<Stmt> getStmt() {
		return stmt;
	}

	public void setStmt(ArrayList<Stmt> stmt) {
		this.stmt = stmt;
	}
	
	public void genC(PW pw){
		
		pw.print("for(");
		variable.genC(pw);
		pw.out.print(" = ");
		from.genC(pw);
		pw.out.print("; ");
		variable.genC(pw);
		if(Integer.valueOf(from.getInteg()) > Integer.valueOf(to.getInteg()))
			pw.out.print(" > ");
		else
			pw.out.print(" < ");
		to.genC(pw);
		pw.out.print("; ");
		variable.genC(pw);
		if(Integer.valueOf(from.getInteg()) > Integer.valueOf(to.getInteg()))
			pw.out.print("--");
		else
			pw.out.print("++");
		pw.out.println("){");
		pw.add();
		for(int i = 0; i < stmt.size(); i++)
			stmt.get(i).genC(pw);
		pw.sub();
		pw.println("}");
		
	}
}