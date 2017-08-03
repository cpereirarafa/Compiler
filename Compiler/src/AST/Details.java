/*

	Universidade Federal De Sao Carlos - Campus Sorocaba
	Compiladores - 01/2017
	Andre Domingues Vieira  -   511420
	Rafael Camara Pereira	-	380431


*/

package AST;

public class Details {

	Name name;
	Number number;
	OrList orList;
	
	public Details(Name na, Number nu, OrList orLst) {
		
		name = na;
		number = nu;
		orList = orLst;
	}

	public Name getName() {
		return name;
	}

	public void setName(Name na) {
		name = na;
	}

	public Number getNumber() {
		return number;
	}

	public void setNumber(Number nu) {
		number = nu;
	}

	public OrList getOrList() {
		return orList;
	}

	public void setOrList(OrList orLst) {
		orList = orLst;
	}

	public void genC(PW pw) {
		
		if(name != null || number != null){
			
			pw.out.print("[");
			if(name != null)
				name.genC(pw);
			else
				number.genC(pw);
			pw.out.print("]");
		} else {
			
			pw.out.print("(");
			if(orList != null)
				orList.genC(pw);
			pw.out.print(")");
			
		}
	}
}
