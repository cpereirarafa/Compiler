/*

	Universidade Federal De Sao Carlos - Campus Sorocaba
	Compiladores - 01/2017
	Andre Domingues Vieira  -   511420
	Rafael Camara Pereira	-	380431


*/

package Lexer;

public enum Symbol {
	AND  			("and"),
	ASSIGN			("="),
	BOOLEAN			("boolean"),
	BREAK			("break"),
	CHAR			("char"),
	DEF				("def"),
    COLON			(":"),
    COMMA			(","),
    CURLYLEFTBRACE	("{"),
    CURLYRIGHTBRACE	("}"),
    DIV				("/"),
    DOT				("."),
    ELIF			("elif"),
    ELSE			("else"),
    END				("end"),
    EOF				("eof"),
    EQ				("=="),
    FALSE			("False"),
    FLOAT			("float"),
    FOR				("for"),
    GE				(">="),
    GT				(">"),
    IDENT			("ident"),
	IF				("if"),
	IN				("in"),
    INRANGE			("inrange"),
    INT				("int"),
    LE				("<="),
    LEFTPAR			("("),
    LEFTSQBRACKET	("["),
    LT				("<"),
    MINUS			("-"),
    MULT			("*"),
    NEQ				("<>"),
    NOT				("not"),
    NOTIN			("notin"),
    NUMBER			("number"),
    OR   			("or"),
    PLUS			("+"),
    POT				("^"),
    PRINT			("print"),
	PROGRAM			("program"),
	RETURN			("return"),
	RIGHTPAR		(")"),
    RIGHTSQBRACKET	("]"),
    SEMICOLON		(";"),
    STRING			("string"),
    TRUE			("True"),
    VOID			("void"),
    WHILE			("while");
    
	Symbol(String name) {
		this.name = name;
    }

    public String toString() {
        return name;
    }

    private String name;

}