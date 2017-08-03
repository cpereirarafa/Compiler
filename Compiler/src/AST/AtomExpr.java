/*

	Universidade Federal De Sao Carlos - Campus Sorocaba
	Compiladores - 01/2017
	Andre Domingues Vieira  -   511420
	Rafael Camara Pereira	-	380431


*/

package AST;

public class AtomExpr {

	String type = null;
	Atom atom;
	Details details;
	
	public AtomExpr(Atom at, Details det) {
		atom = at;
		details = det;
		
		if(details != null)
			if(details.getOrList() != null)
				type = "function";
			else
				type = "vector";
		
	}

	public String getType() {
		if(type != null)
			return type;
		return atom.getType();
	}
	
	public Atom getAtom() {
		return atom;
	}

	public Details getDetails() {
		return details;
	}

	public void genC(PW pw) {

		atom.genC(pw);
		
		if(details != null)
			details.genC(pw);
		
	}
}
