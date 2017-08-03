/*

	Universidade Federal De Sao Carlos - Campus Sorocaba
	Compiladores - 01/2017
	Andre Domingues Vieira  -   511420
	Rafael Camara Pereira	-	380431


*/

package AST;

import java.util.ArrayList;

public class OrList {
	
	ArrayList<OrTest> orlist;

	public OrList(ArrayList<OrTest> olist) {
		
		orlist = olist;
		
	}
	
	public ArrayList<OrTest> getOrlist() {
		return orlist;
	}

	public void setOrlist(ArrayList<OrTest> olist) {
		orlist = olist;
	}

	public void genC(PW pw){
		
		int i;
		
		for(i = 0; i < orlist.size() - 1; i++){
			
			orlist.get(i).genC(pw);
			pw.out.print(", ");
			
		}
		if(orlist.size() > 0)
			orlist.get(i).genC(pw);
		
	}
	
}
