/*

	Universidade Federal De Sao Carlos - Campus Sorocaba
	Compiladores - 01/2017
	Andre Domingues Vieira  -   511420
	Rafael Camara Pereira	-	380431


*/

package AST;

public class NameArray {

	Name name;
	Number number;
	
	public NameArray(Name na, Number nu) {

		name = na;
		number = nu;

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

	public void genC(PW pw) {
		
		name.genC(pw);
		
		if(number != null){
			pw.out.print("[");
			number.genC(pw);
			pw.out.print("]");
		}
	}
}
