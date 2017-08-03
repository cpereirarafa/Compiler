/*

	Universidade Federal De Sao Carlos - Campus Sorocaba
	Compiladores - 01/2017
	Andre Domingues Vieira  -   511420
	Rafael Camara Pereira	-	380431


*/

package AST;

public class FuncDef {

	private Name name;
	private ArgsList args;
	private Type type;
	private Body body;
	
	
	
	public FuncDef(Name na, ArgsList arg, Type ty, Body b) {
		
		name = na;
		args = arg;
		type = ty;
		body = b;
	}



	public Name getName() {
		return name;
	}



	public void setName(Name na) {
		name = na;
	}



	public ArgsList getArgs() {
		return args;
	}



	public void setArgs(ArgsList arg) {
		args = arg;
	}



	public Type getType() {
		return type;
	}



	public void setType(Type ty) {
		type = ty;
	}



	public Body getBody() {
		return body;
	}



	public void setBody(Body b) {
		body = b;
	}

	public void genC(PW pw){
	
		pw.println("");
		type.genC(pw);
		pw.out.print(" ");
		name.genC(pw);
		pw.out.print("(");
		if(args != null)
			args.genC(pw);
		pw.out.println("){");
		pw.add();
		body.genC(pw);
		pw.sub();
		pw.out.println("}");
		
	}	
}
