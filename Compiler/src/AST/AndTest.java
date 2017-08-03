/*

	Universidade Federal De Sao Carlos - Campus Sorocaba
	Compiladores - 01/2017
	Andre Domingues Vieira  -   511420
	Rafael Camara Pereira	-	380431


*/

package AST;

import java.util.ArrayList;

public class AndTest {
	
	ArrayList<NotTest> nottest;

	public AndTest(ArrayList<NotTest> nt) {
		
		nottest = nt;
		
	}

	public ArrayList<NotTest> getNottest() {
		return nottest;
	}

	public void setNottest(ArrayList<NotTest> nottest) {
		this.nottest = nottest;
	}
	
	public void genC(PW pw){
		
		int i;
		
		for(i = 0; i < nottest.size() - 1; i++){
			
			nottest.get(i).genC(pw);
			pw.out.print(" && ");
			
		}
		
		nottest.get(i).genC(pw);
		
	}
}
