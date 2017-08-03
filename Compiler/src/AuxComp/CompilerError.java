/*

	Universidade Federal De Sao Carlos - Campus Sorocaba
	Compiladores - 01/2017
	Andre Domingues Vieira  -   511420
	Rafael Camara Pereira	-	380431


*/

package AuxComp;
import Lexer.*;
import java.io.*;

public class CompilerError {


    

	public CompilerError(Lexer lexer, PrintWriter out, String arg) {
          // output of an error is done in out
        this.lexer = lexer;
        this.out = out;
        this.fileName = arg;
        thereWasAnError = false;
    }
    
    public void setLexer( Lexer lexer ) {
        this.lexer = lexer;
    }
    
    public boolean wasAnErrorSignalled() {
        return thereWasAnError;
    }

    public void show( String strMessage ) {
        show( strMessage, false );
    }
    
    public void show( String strMessage, boolean goPreviousToken ) {
        // is goPreviousToken is true, the error is signalled at the line of the
        // previous token, not the last one.
        if ( goPreviousToken ) {
          out.print(fileName + " At line " + lexer.getLineNumberBeforeLastToken() + ", ");
          //out.println( lexer.getLineBeforeLastToken() );
        }
        else {
          out.print(fileName + " At line " + lexer.getLineNumber() + ", ");
          //out.println(lexer.getCurrentLine());
        }
        
        out.println( strMessage );
        out.println(lexer.getLineBeforeLastToken());
        out.flush();
        if ( out.checkError() )
          System.out.println("Error in signaling an error");
        thereWasAnError = true;
    }
       

    public void signal( String strMessage, boolean goPreviousToken ) {
    	out.println(fileName + ", At line " + lexer.getLineNumberBeforeLastToken() + ", " + strMessage);
    	out.println(lexer.getLineBeforeLastToken());

        if ( out.checkError() )
            System.out.println("Error in signaling an error");
        throw new RuntimeException(strMessage);
    }


    public void signal( String strMessage ) {
        // erro padrao
        out.println(fileName + ", At line " + lexer.getLineNumber() + ", " + strMessage);
        out.println(lexer.getCurrentLine());

        if ( out.checkError() )
            System.out.println("Error in signaling an error");
        throw new RuntimeException(strMessage);
    }

    
    private Lexer lexer;
    private PrintWriter out;
    private boolean thereWasAnError;
    private String fileName;
   }
    
