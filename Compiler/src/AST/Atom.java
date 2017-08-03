/*

	Universidade Federal De Sao Carlos - Campus Sorocaba
	Compiladores - 01/2017
	Andre Domingues Vieira  -   511420
	Rafael Camara Pereira	-	380431


*/

package AST;

public class Atom {

	String type;
	Name name;
	Number number;
	StringType string;
	boolean trueAt;
	boolean falseAt;
	
	public Atom(Name na, Number nu, StringType st, boolean tr, boolean fa) {
		name = na;
		number = nu;
		string = st;
		trueAt = tr;
		falseAt = fa;
		if(tr != false || fa != false)
			type = "boolean";
		else if(st != null)
			type = "char";
		else if(nu != null)
			if(nu.getFrac() != null)
				type = "float";
			else
				type = "int";
		else
			type = "variable";
	}

	public String getType() {
		return type;
	}

	public Name getName() {
		return name;
	}

	public Number getNumber() {
		return number;
	}

	public StringType getString() {
		return string;
	}

	public boolean isTrueAt() {
		return trueAt;
	}

	public boolean isFalseAt() {
		return falseAt;
	}

	public void genC(PW pw){
		
		if(name != null){
			name.genC(pw);
		} else if(number != null)
			number.genC(pw);
		else if(string != null)
			string.genC(pw);
		else if(trueAt == true)
			pw.out.print("1");
		else
			pw.out.print("0");
	}
}
