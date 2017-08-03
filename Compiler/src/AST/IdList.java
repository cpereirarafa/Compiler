/*

	Universidade Federal De Sao Carlos - Campus Sorocaba
	Compiladores - 01/2017
	Andre Domingues Vieira  -   511420
	Rafael Camara Pereira	-	380431


*/

package AST;

import java.util.ArrayList;

public class IdList {

	ArrayList<NameArray> name = new ArrayList<NameArray>();
	
	public IdList(ArrayList<NameArray> na){
		
		name = na;
		
	}
	
	public ArrayList<NameArray> getName() {
		return name;
	}
	public void setName(NameArray na, int i) {
		name.set(i, na);
	}
	
	public void genC(PW pw){
				
		for(int i = 0; i < name.size(); i++){
			
			if(i != 0)
				pw.out.print(", ");
			
			name.get(i).genC(pw);
			
		}			
	}
	
}
