/*

	Universidade Federal De Sao Carlos - Campus Sorocaba
	Compiladores - 01/2017
	Andre Domingues Vieira  -   511420
	Rafael Camara Pereira	-	380431


*/

package AST;

public class Comparison {
	
	public Expr geteLeft() {
		return eLeft;
	}

	public void seteLeft(Expr eLeft) {
		this.eLeft = eLeft;
	}

	public Expr geteRight() {
		return eRight;
	}

	public void seteRight(Expr eRight) {
		this.eRight = eRight;
	}

	public String getOper() {
		return oper;
	}

	public void setOper(String oper) {
		this.oper = oper;
	}

	Expr eLeft, eRight;
	String oper;
	
	public Comparison(Expr eLft, String op, Expr eRgt){
		
		eLeft = eLft;
		oper = op;
		eRight = eRgt;
		
	}
	
	public void genC(PW pw){
		
		eLeft.genC(pw);
		if(eRight != null){
			
			pw.out.print(" " + oper + " ");
			eRight.genC(pw);
			
		}
		
	}
}