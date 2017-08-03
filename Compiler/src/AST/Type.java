/*

	Universidade Federal De Sao Carlos - Campus Sorocaba
	Compiladores - 01/2017
	Andre Domingues Vieira  -   511420
	Rafael Camara Pereira	-	380431


*/

package AST;

import Lexer.*;

public class Type {
	
	private Symbol type;
	
	public Type (Symbol t){
		
		type = t;
		
	}

	public Symbol getType() {
		return type;
	}

	public void genC(PW pw){		
		if(type == Symbol.BOOLEAN)
			pw.out.print("int");
		else if(type == Symbol.STRING)
			pw.out.print("char");
		else
			pw.out.print(type.toString());
	}
}