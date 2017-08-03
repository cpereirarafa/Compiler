/*

	Universidade Federal De Sao Carlos - Campus Sorocaba
	Compiladores - 01/2017
	Andre Domingues Vieira  -   511420
	Rafael Camara Pereira	-	380431


*/

import java.io.PrintWriter;
import java.util.*;
import AST.*;
import AST.Number;
import AuxComp.*;
import Lexer.*;

public class Compiler {
	
	private SymbolTable symbolTable;
    private Lexer lexer;
    private CompilerError error;
    private int insideLoop = 0;


	public Program compile(char[] input, PrintWriter outError, String arg) {

        symbolTable = new SymbolTable();
        error = new CompilerError( lexer, new PrintWriter(outError), arg);
        lexer = new Lexer(input, error);
        error.setLexer(lexer);
        
        lexer.nextToken();
        
        Program p = null;
        try {
          p = program();
        } catch ( Exception e ) {
              // the below statement prints the stack of called methods.
              // of course, it should be removed if the compiler were 
              // a production compiler.
            e.printStackTrace();
        }
        if ( error.wasAnErrorSignalled() )
          return null;
        else
          return p;
    }
    
	// 'program' Name ':' FuncDef {FuncDef} 'end'       
	private Program program() {
		
		ArrayList<FuncDef> f = new ArrayList<FuncDef>();
		Name na;
		
		if (lexer.token == Symbol.PROGRAM)
			lexer.nextToken();
		else
			error.signal("Expected program statement");
		na = name("program");
		
		if(symbolTable.get(na.getName()) != null)
			error.signal("Identifier already declared " + na.getName());
		else
			symbolTable.putInGlobal(na.getName(), na);
			
		if (lexer.token == Symbol.COLON)
			lexer.nextToken();
		else
			error.signal("Colon expected");
		
		while(lexer.token != Symbol.END)
			f.add(funcDef());
		
		if(symbolTable.getInGlobal("main") == null)
			error.signal("\'main\' function not declared");
		
		if (lexer.token == Symbol.END)
			lexer.nextToken();
		else
			error.signal("Expected end identifier");
		return new Program (f);
		
	}

	// Letter {Letter | Digit}
    private Name name(String type){
    	
    	String n;
    	
    	n  = lexer.getStringValue();
    	
    	if(lexer.token != Symbol.IDENT)
    		error.signal("Identifier expected before \'" + lexer.token + "\'");
    	

    	if(!(n.charAt(0) >= 'a' && n.charAt(0) <= 'z'))
    		error.signal("Invalid identifier \'" + n + "\'");
    	
    	lexer.nextToken();

    	return new Name(n, type);
    	
    }
    
    //'def' Name '(' [ArgsList] ')' ':' Type '{'Body'}'
    private FuncDef funcDef(){
    	
    	Name na;
    	ArgsList aLst = null;
    	Type type;
    	Body body; 
    	
    	if(lexer.token != Symbol.DEF)
    		error.signal("Function definition expected");
    	
    	lexer.nextToken();
    	
    	na = name(null);
    	
    	//SymbolTable
    	if(symbolTable.getInGlobal(na.getName()) != null)
    		error.signal("Function " + na.getName() + " already declared");
    	else
    		symbolTable.putInGlobal(na.getName(), na);
    	
    	if(lexer.token != Symbol.LEFTPAR)
    		error.signal("Expected \'(\' in function declaration");
    	
    	lexer.nextToken();
    	
    	if(lexer.token != Symbol.RIGHTPAR)
        	aLst = argsList();
    	
    	if(lexer.token != Symbol.RIGHTPAR)
    		error.signal("Expected \')\' at the end of function definition");
    	
    	lexer.nextToken();
    	
    	if (lexer.token == Symbol.COLON)
			lexer.nextToken();
		else
			error.signal("Colon expected");
		
    	type = type();
    	
    	if(na.getName().equals("main") && (type.getType() != Symbol.VOID))
    		error.signal("Invalid main function type");
    	
    	if(na.getName().equals("main") && (aLst != null))
    		error.signal("Main function can\'t have parameters");
    	
    	na.setType(type.getType().toString());
    	
    	if (lexer.token == Symbol.CURLYLEFTBRACE)
			lexer.nextToken();
		else
			error.signal("\'{\' expected");
    	
    	body = body();
    	
    	if (lexer.token == Symbol.CURLYRIGHTBRACE)
			lexer.nextToken();
		else
			error.signal("\'}\' expected");

    	symbolTable.removeLocalIdent();
    	
    	return new FuncDef(na, aLst, type, body);
    	
    }
    
    //Type NameArray {',' Type NameArray}
    private ArgsList argsList(){

    	ArrayList<Type> tyLst = new ArrayList<Type>();
    	ArrayList<NameArray> naLst = new ArrayList<NameArray>();

    	while(lexer.token == Symbol.INT || lexer.token == Symbol.FLOAT || lexer.token == Symbol.STRING || lexer.token == Symbol.BOOLEAN){
    		
    		tyLst.add(type());
    		naLst.add(nameArray(tyLst.get(tyLst.size() -1).getType().toString()));
    		
    		if(symbolTable.get(naLst.get(naLst.size() - 1).getName().getName()) != null)
    			error.signal("Identifier Already declared: "+ naLst.get(naLst.size() - 1).getName().getName());
    		symbolTable.putInLocal(naLst.get(naLst.size() - 1).getName().getName(), naLst.get(naLst.size() - 1).getName());
    		
    		if(lexer.token == Symbol.COMMA)
    			lexer.nextToken();
    		else if(lexer.token == Symbol.INT || lexer.token == Symbol.FLOAT || lexer.token == Symbol.STRING || lexer.token == Symbol.BOOLEAN)
    			error.show("Expected \',\' between arguments");
    			
    		
    	}
    	
    	return new ArgsList(tyLst, naLst);
    	
    }
    
  //Name['[' Number']']
    private NameArray nameArray(String ty){
    	
    	Name na;
    	Number nu = null;
    	
    	na = name(ty);
    	
		if(lexer.token == Symbol.LEFTSQBRACKET){
			lexer.nextToken();
			
			if ((lexer.token == Symbol.NUMBER) || (lexer.token == Symbol.MINUS)){
				
				nu = number();
				
				if(nu.getOp() == Symbol.MINUS.toString())
					error.signal("Invalid negative vector position");
				
				if(nu.getFrac() != null)
					error.signal("Vector position must use integer values");

			} else 
				error.signal("Vector position isn\'t defined.");

			if(lexer.token == Symbol.RIGHTSQBRACKET)
				lexer.nextToken();
		}
		
		return new NameArray(na, nu);
    	
    }
    
    // [Declaration] {Stmt}
    private Body body(){
    	
    	ArrayList<Stmt> stLst = new ArrayList<Stmt>();
    	Declaration decLst = null;
    	
    	if(lexer.token == Symbol.INT || lexer.token == Symbol.FLOAT || lexer.token == Symbol.STRING || lexer.token == Symbol.BOOLEAN){
    	
    		decLst = declaration();

    	}
    	
    	if (lexer.token == Symbol.CURLYRIGHTBRACE)
    		return new Body(decLst, stLst);
    		
    	while((lexer.token != Symbol.EOF) && (lexer.token != Symbol.CURLYRIGHTBRACE) ){
    		
    		stLst.add(stmt());
    		
    	}
    	
    	if (lexer.token == Symbol.EOF)
    		error.signal("Expected \'}\' at the end of the Function");
    	
    	return new Body(decLst, stLst);
    }
    
    // Type IdList {';' Type IdList}';'
    private Declaration declaration(){
    	
    	ArrayList<Type> ty = new ArrayList<Type>();
    	ArrayList<IdList> id = new ArrayList<IdList>();
    	int i;
    	
    	if(lexer.token != Symbol.INT && lexer.token != Symbol.FLOAT && lexer.token != Symbol.STRING && lexer.token != Symbol.BOOLEAN && lexer.token != Symbol.VOID)
    		error.signal("Expected a declaration");
    	
    	while(lexer.token == Symbol.INT || lexer.token == Symbol.FLOAT || lexer.token == Symbol.STRING || lexer.token == Symbol.BOOLEAN || lexer.token == Symbol.VOID){
    		
    		ty.add(type());
    		id.add(idList(ty.get(ty.size() -1).getType().toString()));
    		
    		if(ty.get(ty.size() - 1).getType() == Symbol.CHAR){
    			
    			Number n;
    			
    			n = new Number("256", null, null);
    			
    			for(i = 0; i < id.get(ty.size() - 1).getName().size(); i++){	
    				if(id.get(ty.size() - 1).getName().get(i).getNumber() == null)
    					id.get(ty.size() - 1).getName().get(i).setNumber(n);
    			}
    			
    		}

    		for(i = 0; i < id.get(ty.size() - 1).getName().size(); i++){	
    			
    			if(symbolTable.get(id.get(ty.size() - 1).getName().get(i).getName().getName()) != null)
    				error.signal("Identifier Already declared: "+ id.get(ty.size() - 1).getName().get(i).getName().getName());
    			symbolTable.putInLocal(id.get(ty.size() - 1).getName().get(i).getName().getName(), id.get(ty.size() - 1).getName().get(i).getName());
			}
    		
    		if(lexer.token != Symbol.SEMICOLON)
    			error.signal("\';\' expected at the end of the declaration", true);
    		
    		lexer.nextToken();
    		
    	}
    	
    	return new Declaration(ty, id);
    	
    }
    
    // 'int' | 'float' | 'string' | 'boolean' | 'void'
    private Type type(){
    	
    	Symbol t;
    	
    	if(lexer.token != Symbol.INT && lexer.token != Symbol.FLOAT && lexer.token != Symbol.STRING && lexer.token != Symbol.BOOLEAN && lexer.token != Symbol.VOID)
    		error.signal("Expected a type identifier");
    	
    	if(lexer.token == Symbol.STRING)
    		t = Symbol.CHAR;
    	else
    		t = lexer.token;
    	
    	lexer.nextToken();
    	return new Type(t);
    	
    }
    
 // NameArray { ',' NameArray}
    private IdList idList(String ty){
    	
    	ArrayList<NameArray> name = new ArrayList<NameArray>();

    	do{
    		
    		if(name.size() != 0)
    			lexer.nextToken();
    		
    		name.add(nameArray(ty));    		
    		
    	} while(lexer.token == Symbol.COMMA);

    	return new IdList(name);
    	
    }
    
    
    // SimpleStmt | CompoundStmt
    private Stmt stmt(){

    	if(lexer.token == Symbol.IF || lexer.token == Symbol.WHILE || lexer.token == Symbol.FOR)
    		return compoundStmt();
    	
    	return simpleStmt();
    	
    }
    
    //ExprStmt | PrintStmt | BreakStmt | ReturnStmt | FuncStmt
    private SimpleStmt simpleStmt(){
    	
    	Name na;
    	
    	if (lexer.token == Symbol.PRINT)
    		return printStmt();
    	if(lexer.token == Symbol.BREAK)
    		return breakStmt();
    	
    	if(lexer.token == Symbol.RETURN)
    		return returnStmt();

    	na = name(null);

    	if(lexer.token == Symbol.LEFTPAR){

    		if(symbolTable.get(na.getName()) == null)
    			error.signal("Function " + na.getName() + " not declared");

    		na = (Name) symbolTable.get(na.getName());
    		return funcStmt(na);

    	}

    	if(symbolTable.get(na.getName()) == null)
			error.signal("Variable " + na.getName() + " not declared");

		na = (Name) symbolTable.get(na.getName());
    	return exprStmt(na);

    }
    
    // Name [ '[' Atom ']' ] '=' (OrTest | '[' OrList ']')';'
    private ExprStmt exprStmt(Name na){
    	
    	Atom at = null;
    	OrTest or = null;
    	OrList ol = null;
    	String type;
    	
		if(lexer.token == Symbol.LEFTSQBRACKET){
    						
    		lexer.nextToken();
    		at = atom();
    		
    		if(at.getString() != null || at.isTrueAt() != false || at.isFalseAt() != false)
    			error.signal("Vector attribution doesn\'t permit boolean or string index.");
    		
    		if(at.getNumber() != null){
    			
	    		if(at.getNumber().getOp() == Symbol.MINUS.toString())
					error.signal("Negative vector attribution not allowed");
				
	    		if(at.getNumber().getFrac() != null)
	    			error.signal("Can\'t access a vector\'s floating position.");
	    		
    		} //else if(at.getName())

    		
    		if(lexer.token == Symbol.RIGHTSQBRACKET)
    			lexer.nextToken();
    		else
    			error.signal("\']\'expected after number ");
    	}
    	
    	if(lexer.token == Symbol.ASSIGN)
    		lexer.nextToken();
    	else
    		error.signal("Assignment signal \'=\' expected.");
    	
    	if(lexer.token == Symbol.LEFTSQBRACKET){
    		lexer.nextToken();
    		ol = orList();
    		if(lexer.token == Symbol.RIGHTSQBRACKET)
    			lexer.nextToken();
    		else
    			error.signal("\']\'expected after expression ");

    	} else {
    		or = orTest();
    		type = or.getAndtest().get(0).getNottest().get(0).getComp().geteLeft().gettList().get(0).getfList().get(0).getAtomExpr().getType(); 
    		if(type.equals("variable")){
    			if(!na.getType().toString().equals(or.getAndtest().get(0).getNottest().get(0).getComp().geteLeft().gettList().get(0).getfList().get(0).getAtomExpr().getAtom().getName().getType().toString()))
    				error.signal("Incompatible tipes in atribution");
    		} else if(!na.getType().equals(type)){
    			if(!"function".equals(type)){
    				
    				error.signal("Incompatible tipes in atribution");
    				
    			}
    		}
    	}
    	
    	if(lexer.token != Symbol.SEMICOLON)
			error.signal("\';\' expected at the end of the definition ");
    	
		else
    		lexer.nextToken();
    	
    	return new ExprStmt(na, at, or, ol);
    	
    }

    // OrTest {',' OrTest}
    private OrList orList(){
    	
    	ArrayList<OrTest> orList = new ArrayList<OrTest>();
    	
    	orList.add(orTest());
    	
    	while(lexer.token == Symbol.COMMA){
    		lexer.nextToken();
    		orList.add(orTest());
    		
    	}
    	return new OrList(orList);

    }
    
    // AndTest {'or' AndTest}
    private OrTest orTest(){
    	
    	ArrayList<AndTest> at = new ArrayList<AndTest>();
    	
    	at.add(andTest());
    	
    	while(lexer.token == Symbol.OR){
    		
    		lexer.nextToken();
    		at.add(andTest());
    		
    	}
    	return new OrTest(at);

    }
    
    // NotTest {'and' NotTest}
    private AndTest andTest(){
    	
    	ArrayList<NotTest> nt = new ArrayList<NotTest>();
    	
    	nt.add(notTest());
    	
    	while(lexer.token == Symbol.AND){
    		lexer.nextToken();
    		nt.add(notTest());
    	}
    	
    	return new AndTest(nt);

    }
     
    // ['not'] Comparison
    private NotTest notTest(){
    	
    	boolean not = false;
    	Comparison comp;
    	
    	if(lexer.token == Symbol.NOT){
    		
    		lexer.nextToken();
    		not = true;
    		
    	}
    	
    	comp = comparison();
    	
    	if(comp.geteLeft().gettList().get(0).getfList().get(0).getAtomExpr().getType() == "variable"){
			if(comp.geteLeft().gettList().get(0).getfList().get(0).getAtomExpr().getAtom().getName().getType().toString().equals("char") && not == true)
				error.signal("You can\'t negate a string.");
		} else if(comp.geteLeft().gettList().get(0).getfList().get(0).getAtomExpr().getType().equals("char") && not == true)
			error.signal("You can\'t negate a string.");
    	
    	return new NotTest(not, comp);
    	
    }
    
    // Expr [CompOp Expr]
    private Comparison comparison(){
    	
//    	Type varEL = null, varER = null;
    	Expr eL, eR = null;
    	String cOp = null;
    	
    	eL = expr();
    	
    	if(lexer.token == Symbol.GT || lexer.token == Symbol.LT || lexer.token == Symbol.EQ || lexer.token == Symbol.GE || lexer.token == Symbol.LE || lexer.token == Symbol.NEQ){
    		
    		if(lexer.token == Symbol.NEQ)
    			cOp = "!=";
    		else
    			cOp = lexer.token.toString();
    		
    		lexer.nextToken();
    		eR = expr();
    		
    	}
    	
    	return new Comparison(eL, cOp, eR);
    	
    }
    
    // Term {('+' | '-') Term}
    private Expr expr(){
    	
    	ArrayList<String> oper = new ArrayList<String>();
    	ArrayList<Term> tLst = new ArrayList<Term>();
    	
    	tLst.add(term());
    	
    	while(lexer.token == Symbol.PLUS || lexer.token == Symbol.MINUS){
    		
    		oper.add(lexer.token.toString());
    		lexer.nextToken();
    		tLst.add(term());
    		
    	}
    	
    	return new Expr(tLst, oper);
    	
    }
    
    // Factor {('*' | '/') Factor}
    private Term term(){
    	
    	ArrayList<String> oper = new ArrayList<String>();
    	ArrayList<Factor> fLst = new ArrayList<Factor>();
    	
    	fLst.add(factor());
    	
    	while(lexer.token == Symbol.MULT || lexer.token == Symbol.DIV){
    		
    		oper.add(lexer.token.toString());
    		lexer.nextToken();
    		fLst.add(factor());
    		
    	}
    	
    	return new Term(fLst, oper);
    	
    }
    
    // ['+' | '-'] AtomExpr {^ Factor}
    private Factor factor(){
    	
    	String signal = null;
    	AtomExpr atExp;
    	ArrayList<Factor> facLst = new ArrayList<Factor>();
    	
    	if(lexer.token == Symbol.PLUS || lexer.token == Symbol.MINUS){
    		
    		signal = lexer.token.toString();
    		lexer.nextToken();
    		
    	}
    	
    	atExp = atomExpr();
    	
    	while(lexer.token == Symbol.POT){

    		lexer.nextToken();
    		facLst.add(factor());
    		
    	}

    	return new Factor(signal, atExp, facLst);
    	
    }

    // Atom [Details]
    private AtomExpr atomExpr(){
    	
    	Atom at;
    	Details det = null;
    	
    	at = atom();
    	
    	if(lexer.token == Symbol.LEFTPAR || lexer.token == Symbol.LEFTSQBRACKET)
    		det = details();
    	
    	return new AtomExpr(at, det);
    	
    }
    
    // Name | Number | String | 'True' | 'False'
    private Atom atom(){
    	
    	Name na = null;
    	Number nu = null;
    	StringType st = null;
    	boolean tr = false;
    	boolean fs = false;
    	
    	if (lexer.token == Symbol.TRUE){
    		lexer.nextToken();
    		return new Atom(na, nu, st, true, fs);
    	}
    	
    	if (lexer.token == Symbol.FALSE){
    		lexer.nextToken();
    		return new Atom(na, nu, st, tr, true);
    	}
    	
    	if (lexer.token == Symbol.STRING)
    		st = string();
    	else if (lexer.token == Symbol.NUMBER)
    		nu = number();
    	else {
    		
    		na = name(null);
    		
    		if(symbolTable.get(na.getName()) == null)
        		error.signal("Variable " + na.getName() + " not declared");
        	
    		na = (Name) symbolTable.get(na.getName());
    		
    	}

    	return new Atom(na, nu, st, tr, fs);
    	
    }
    
    // '[' (Number | Name) ']' | '('[OrList]')'
    private Details details(){
    	
    	Name na = null;
    	Number nu = null;
    	OrList orLst = null;
    	
    	if(lexer.token == Symbol.LEFTSQBRACKET){
			lexer.nextToken();
			
			if (lexer.token == Symbol.NUMBER){
				
				nu = number();
				
				if(nu.getOp() == Symbol.MINUS.toString())
					error.signal("Negative vector search not allowed");
				
				if(nu.getFrac() != null)
					error.signal("Vector position must use integer values");
							
			} else {
					
				na = name(null);
					
				if(symbolTable.get(na.getName()) == null)
			    	error.signal("Variable " + na.getName() + " not declared");
					
				na = (Name) symbolTable.get(na.getName());
										
			}
			if(lexer.token == Symbol.RIGHTSQBRACKET)
				lexer.nextToken();
		} else if(lexer.token == Symbol.LEFTPAR){
			
			lexer.nextToken();
			
			if(lexer.token != Symbol.RIGHTPAR)
				orLst = orList();
			else 
				orLst = new OrList(new ArrayList<OrTest>());
			
			if(lexer.token != Symbol.RIGHTPAR)
				error.signal("Expected ')' at the end of Details");
			
			lexer.nextToken();
			
		}
    	
    	return new Details(na, nu, orLst);
    	
    }
    
 // ['+' | '-'] Digit{Digit} ['.' Digit{Digit}]
    private Number number(){
    	
    	String sign = null;
    	String digIP = null;
    	String digFP = null;
    	
    	if(lexer.token == Symbol.PLUS || lexer.token == Symbol.MINUS){
    		
    		sign = lexer.token.toString();
    		lexer.nextToken();
    		
    	}
    	
    	if(lexer.token != Symbol.NUMBER)
    		error.signal("Number expected");
    	digIP = String.valueOf(lexer.getNumberValue());
    	lexer.nextToken();
    	
    	if(lexer.token == Symbol.DOT){
    		
    		lexer.nextToken();
    		if(lexer.token != Symbol.NUMBER)
        		error.signal("Number expected");
    		digFP = String.valueOf(lexer.getNumberValue());
    		lexer.nextToken();
    		
    	}
    	
    	return new Number(digIP, digFP, sign);
    	
    }
    
    // ''' . '''
    private StringType string(){
    	
    	String str;
    	
    	if(lexer.token != Symbol.STRING)
    		error.signal("String expected");
    	
    	str = lexer.getStringValue();
    	lexer.nextToken();
    	
    	return new StringType(str);
    	
    }
    
    // 'print' OrTest {',' OrTest} ';'
    private PrintStmt printStmt(){
    	
    	ArrayList<OrTest> orLst = new ArrayList<OrTest>();
    	
    	if(lexer.token != Symbol.PRINT)
    		error.signal("Print statement expected");
    	lexer.nextToken();
    	orLst.add(orTest());
    	
    	while(lexer.token == Symbol.COMMA){
    		
    		lexer.nextToken();
    		orLst.add(orTest());
    		
    	}
    	
    	if(lexer.token != Symbol.SEMICOLON)
    		error.signal("\';\' expected at the end of print statement");
    	lexer.nextToken();
    	
    	return new PrintStmt(orLst);
    		
    }
    
    // 'break' ';'
    private BreakStmt breakStmt(){
    	
    	if(insideLoop == 0)
    		error.signal("Break statement must be on for or while loop");
    	
    	if(lexer.token != Symbol.BREAK)
    		error.signal("Break expected");
    	lexer.nextToken();
    	
    	if(lexer.token != Symbol.SEMICOLON)
    		error.signal("\';\' expected at the end of break statement");
    	lexer.nextToken();
    	return new BreakStmt();
    	
    }

    // 'return' [OrTest] ';'
    private ReturnStmt returnStmt(){
    	
    	OrTest orTst = null;
    	
    	if(lexer.token != Symbol.RETURN)
    		error.signal("Return statement expected");
    	lexer.nextToken();
    	
    	if(lexer.token != Symbol.SEMICOLON)
    		orTst = orTest();
    	
    	if(lexer.token != Symbol.SEMICOLON)
    		error.signal("\';\' expected at the end of return statement");
    	
    	lexer.nextToken();
    	return new ReturnStmt(orTst);
    	
    }
    
    // Name'(' [OrList] ');'
    private FuncStmt funcStmt(Name na){
    	
    	OrList orLst = null;
    	
    	if(lexer.token == Symbol.LEFTPAR){
			
			lexer.nextToken();
			
			if(lexer.token != Symbol.RIGHTPAR)
				orLst = orList();
			
		}
    	
    	if(lexer.token != Symbol.RIGHTPAR)
			error.signal("Expected ')' at the end of FuncStmt definition");
		
		lexer.nextToken();
		
		if(lexer.token != Symbol.SEMICOLON)
    		error.signal("\';\' expected at the end of FuncStmt");
		
		lexer.nextToken();
		
    	return new FuncStmt(na, orLst);
    	
    }
    
    //IfStmt | WhileStmt | ForStmt
    private CompoundStmt compoundStmt(){
    	
    	if(lexer.token == Symbol.IF)
    		return ifStmt();
    	if(lexer.token == Symbol.WHILE)
    		return whileStmt();
    	if(lexer.token == Symbol.FOR)
    		return forStmt();
    	
    	error.signal("Compound statement expected");
    	return null;
    	
    }
    
    // 'if' OrTest '{' {Stmt} '}' ['else' '{' {Stmt} '}']
    private IfStmt ifStmt(){
    	
    	OrTest ot;
    	ArrayList<Stmt> iStmt = new ArrayList<Stmt>();
    	ArrayList<Stmt> eStmt = new ArrayList<Stmt>();
    	
    	if(lexer.token != Symbol.IF)
    		error.signal("if Expected");
    	lexer.nextToken();
    	ot = orTest();
    	
    	if(lexer.token != Symbol.CURLYLEFTBRACE)
    		error.signal("Open brace expected for if statement", true);
    	
    	lexer.nextToken();
    	
    	while(lexer.token != Symbol.CURLYRIGHTBRACE)
    		iStmt.add(stmt());
    	lexer.nextToken();
    	
    	if(lexer.token == Symbol.ELSE){
    		
    		lexer.nextToken();
    		
    		if(lexer.token != Symbol.CURLYLEFTBRACE)
        		error.signal("Open brace expected for if statement");
        	lexer.nextToken();
        	
        	while(lexer.token != Symbol.CURLYRIGHTBRACE)
        		eStmt.add(stmt());
        	lexer.nextToken();
    	}
    	
    	return new IfStmt(ot, iStmt, eStmt);
    	
    }
    
    // 'while' OrTst '{' {Stmt} '}'
    private WhileStmt whileStmt(){
    	
    	insideLoop++;
    	OrTest ot;
    	ArrayList<Stmt> wStmt = new ArrayList<Stmt>();
    	
    	if(lexer.token != Symbol.WHILE)
    		error.signal("while Expected");
    	lexer.nextToken();
    	ot = orTest();
    	
    	if(lexer.token != Symbol.CURLYLEFTBRACE)
    		error.signal("Open brace expected for while statement");
    	lexer.nextToken();
    	
    	while(lexer.token != Symbol.CURLYRIGHTBRACE)
    		wStmt.add(stmt());
    	lexer.nextToken();
    	
    	insideLoop--;
    	return new WhileStmt(ot, wStmt);
    	
    }
    
 // 'for' Name 'inrange' '(' Number ',' Number ')' '{' {Stmt} '}'
    private ForStmt forStmt(){
    	
    	insideLoop++;
    	Name var;
    	Number from, to;
    	ArrayList<Stmt> fStmt = new ArrayList<Stmt>();
    	
    	if(lexer.token != Symbol.FOR)
    		error.signal("for Expected");
    	lexer.nextToken();
    	var = name(null);
    	
    	if(symbolTable.get(var.getName()) == null)
    		error.signal("Variable " + var.getName() + " not declared");
    	
    	var = (Name) symbolTable.get(var.getName());
    	
    	if(lexer.token != Symbol.INRANGE)
    		error.signal("inrange Expected");
    	lexer.nextToken();
    	
    	if(lexer.token != Symbol.LEFTPAR)
    		error.signal("\'(\' expected for range definition");
    	lexer.nextToken();
    	
    	from = number();
    	
    	if(from.getFrac() != null)
    		error.signal("Range definition must use integer values");
    		
    	if(lexer.token != Symbol.COMMA)
    		error.signal("\',\' expected between range definitions");
    	lexer.nextToken();
    	
    	to = number();
    	if(to.getFrac() != null)
    		error.signal("Range definition must use integer values");
    	
    	if(lexer.token != Symbol.RIGHTPAR)
    		error.signal("\')\' expected for range definition");
    	lexer.nextToken();
    	
    	if(lexer.token != Symbol.CURLYLEFTBRACE)
    		error.signal("Open brace expected for while statement");
    	lexer.nextToken();
    	
    	while(lexer.token != Symbol.CURLYRIGHTBRACE)
    		fStmt.add(stmt());
    	lexer.nextToken();
    	
    	insideLoop--;
    	return new ForStmt(var, from, to, fStmt);
    	
    }    
}
