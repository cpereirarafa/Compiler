/*

	Universidade Federal De Sao Carlos - Campus Sorocaba
	Compiladores - 01/2017
	Andre Domingues Vieira  -   511420
	Rafael Camara Pereira	-	380431


*/

package AST;

import java.util.ArrayList;

public class Program {
	
	ArrayList<FuncDef> func;
	
    public Program(ArrayList<FuncDef> f) {
    	
    	func = f;
    	
    }
    
    public void genC(PW pw) {
    	
    	pw.println("#include <stdio.h>");
        pw.println("#include <string.h>");
        pw.println("#include <math.h>");
        for(int i = 0; i < func.size(); i++)
        	func.get(i).genC(pw);
        
    }       
}