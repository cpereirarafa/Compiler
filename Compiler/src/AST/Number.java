/*

	Universidade Federal De Sao Carlos - Campus Sorocaba
	Compiladores - 01/2017
	Andre Domingues Vieira  -   511420
	Rafael Camara Pereira	-	380431


*/

package AST;

public class Number {
	
	private String integ, frac, op;
	
	public Number(String i, String f, String o){
		
		integ = i;
		frac = f;
		op = o;
		
	}
	
	public String getOp() {
		return op;
	}

	public void setOp(String op) {
		this.op = op;
	}

	public String getInteg() {
		return integ;
	}

	public void setInteg(String integ) {
		this.integ = integ;
	}

	public String getFrac() {
		return frac;
	}

	public void setFrac(String frac) {
		this.frac = frac;
	}
	
	public void genC(PW pw){
		
		if(op == "-")
			pw.out.print(op);
		pw.out.print(integ);
		if(frac != null)
			pw.out.print("." + frac);
	}
}