/*

	Universidade Federal De Sao Carlos - Campus Sorocaba
	Compiladores - 01/2017
	Andre Domingues Vieira  -   511420
	Rafael Camara Pereira	-	380431


*/

package AST;

import java.util.ArrayList;

public class OrTest {

	ArrayList<AndTest> andtest;

	public OrTest(ArrayList<AndTest> at) {
		
		andtest = at;
		
	}

	public ArrayList<AndTest> getAndtest() {
		return andtest;
	}

	public void setAndtest(ArrayList<AndTest> andtest) {
		this.andtest = andtest;
	}
	
	public void genC(PW pw){
		
		int i;
		
		for(i = 0; i < andtest.size() - 1; i++){
			
			andtest.get(i).genC(pw);
			pw.out.print(" || ");
			
		}
		
		andtest.get(i).genC(pw);
		
	}
}
