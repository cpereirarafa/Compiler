/*

	Universidade Federal De Sao Carlos - Campus Sorocaba
	Compiladores - 01/2017
	Andre Domingues Vieira  -   511420
	Rafael Camara Pereira	-	380431


*/

package AST;

import java.io.*;


public class PW {
   
   int currentIndent = 0;
   /* there is a Java and a Green mode. 
      indent in Java mode:
      3 6 9 12 15 ...
      indent in Green mode:
      3 6 9 12 15 ...
   */
   static public final int green = 0, java = 1;
   int mode = green; 
   public int step = 3;
   public PrintWriter out;

   static final private String space = "                                                                                                        ";

   public void add() {
      currentIndent += step;
   }
   
   public void sub() {
      currentIndent -= step;
   }
   
   public void set( PrintWriter out ) {
      this.out = out;
      currentIndent = 0;
   }
   
   public void set( int indent ) {
      currentIndent = indent;
   }
   
   public void print( String s ) {
      out.print( space.substring(0, currentIndent) );
      out.print(s);
   }
   
   public void println( String s ) {
      out.print( space.substring(0, currentIndent) );
      out.println(s);
   }
   
}
      
       
