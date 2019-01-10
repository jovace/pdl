package pdl.analizadorLexico;

import java.util.ArrayList;

public class analizadorLexico{
	int estadoActual=0;
	int lineaActual=1;
	int caracterActual=1;
	int caracterInicioToken=0;
	String cadena="";
	
	public boolean analizar(String codigo) {
		ArrayList<Token> listaTokens = new ArrayList<Token>();

		int i=0;
		while(i<codigo.length()) {
			char c=codigo.charAt(i);

			Token resultado=nextChar(c);
			if(resultado.getCompleto()) {
				//Tenemos un token completo, lo anadimos a la lista de tokens
				listaTokens.add(resultado);
				resultado.setPosicion(lineaActual, caracterInicioToken);
			}
			if(resultado.consumeCaracter()) {
				i++;
				caracterActual++;
			}
		}

		//listaTokens contiene la lista de tokens completa, lo que hay que devolver. Se puede llamar a su metodo .toString para 
		//el fichero de salida que hay que generar.
		
		
		analizadorSintactico a = new analizadorSintactico();
		return a.analizar(listaTokens);
	}

	private Token nextChar(char c) {
		//Token(0,1)
		//Salvo que se genere un token diferente (un token completo o incompleto que no consume)
		//devolveremos un Token incompleto que consume caracter
		Token resultado=new Token(true);


		//Dependiendo del estado en el que se encuentre el automata
		if(estadoActual==0) {
			caracterInicioToken=caracterActual;
			switch(c) {
			case '\t':
				caracterActual++;
				cadena="";
				estadoActual=0;
				break;
			case '\n':
				lineaActual++;
				caracterActual=1;
				cadena="";
				estadoActual=0;
				break;
			case ' ':
				caracterActual++;
				cadena="";
				estadoActual=0;
				break;
			case '+':
				cadena+="+";
				estadoActual=1;
				break;
			case '-':
				cadena+="-";
				estadoActual=1;
				break;
			case '%':
				cadena+="%";
				estadoActual=1;
				break;
			case '*':
				cadena+="*";
				estadoActual=1;
				break;
			case '{':
				cadena+="{";
				estadoActual=1;
				break;
			case '}':
				cadena+="}";
				estadoActual=1;
				break;
			case '(':
				cadena+="(";
				estadoActual=1;
				break;
			case ')':
				cadena+=")";
				estadoActual=1;
				break;
			case ';':
				cadena+=";";
				estadoActual=1;
				break;
			case ',':
				cadena+=",";
				estadoActual=1;
				break;
			case '&':
				cadena+="&";
				estadoActual=4;
				break;
			case '|':
				cadena+="|";
				estadoActual=6;
				break;
			case '"':
				cadena+="";
				estadoActual=11;
				break;				
			case '=':
				cadena+="=";
				estadoActual=14;
				break;
			case '!':
				cadena+="!";
				estadoActual=14;
				break;
			case '<':
				cadena+="<";
				estadoActual=14;
				break;
			case '>':
				cadena+=">";
				estadoActual=14;
				break;
			case '/':
				cadena+="/";
				estadoActual=17;
				break;				
			default:
				if(Character.isDigit(c)) {
					cadena+=c;
					estadoActual=9;
					break;
				}else if(Character.isAlphabetic(c)) {
					cadena+=c;
					estadoActual=2;
					break;
				}
			}	
		}else if(estadoActual==1) {
			switch(cadena) {
			case "+":
				switch(c) {
					case '+':
						resultado=new Token("++");
						cadena="";
						estadoActual=0;
						break;
					default:
						resultado=new Token("+");
						cadena="";
						estadoActual=0;
						break;
				}
				break;
			case "-":
				switch(c) {
				case '-':
					resultado=new Token("--");
					cadena="";
					estadoActual=0;
					break;
				default:
					resultado=new Token("-");
					cadena="";
					estadoActual=0;
					break;
			}
				break;
			case "*":
				resultado=new Token("*");
				cadena="";
				estadoActual=0;
				break;
			case "%":
				resultado=new Token("%");
				cadena="";
				estadoActual=0;
				break;
			case "{":
				resultado=new Token("{");
				cadena="";
				estadoActual=0;
				break;
			case "}":
				resultado=new Token("}");
				cadena="";
				estadoActual=0;
				break;
			case "(":
				resultado=new Token("(");
				cadena="";
				estadoActual=0;
				break;
			case ")":
				resultado=new Token(")");
				cadena="";
				estadoActual=0;
				break;
			case ";":
				resultado=new Token(";");
				cadena="";
				estadoActual=0;
				break;
			case ",":
				resultado=new Token(",");
				cadena="";
				estadoActual=0;
				break;
			default:
				resultado=new Token(false);
				cadena="";
				estadoActual=0;
				break;
			}

			resultado.setConsumeCaracter(c=='+' || c=='-');
		}else if(estadoActual==2){
			if(Character.isDigit(c)) {
				cadena+=c;
				estadoActual=2;
			}else if(Character.isAlphabetic(c)) {
				cadena+=c;
				estadoActual=2;
			}else{
				
				String comp=cadena.toLowerCase();
				if(comp.equals("function")) {
					resultado=new Token("function");
				}else if(comp.equals("var")) {
					resultado=new Token("var");
				}else if(comp.equals("int")) {
					resultado=new Token("int");
				}else if(comp.equals("string")) {
					resultado=new Token("string");
				}else if(comp.equals("bool")) {
					resultado=new Token("bool");
				}else if(comp.equals("for")) {
					resultado=new Token("for");
				}else if(comp.equals("true")) {
					resultado=new Token(comp,true);
				}else if(comp.equals("false")) {
					resultado=new Token(comp,true);
				}else if(comp.equals("return")) {
					resultado=new Token("return");
				}else if(comp.equals("print")) {
					resultado=new Token("print");
				}else if(comp.equals("prompt")) {
					resultado=new Token("prompt");
				}else if(comp.equals("char")){
					resultado=new Token("char");
				}else {
					resultado=new Token(cadena, true);
				}
				
				resultado.setConsumeCaracter(false);
				cadena="";
				estadoActual=0;
			}
		}else if(estadoActual==3){
			//Quitar cuando estemos en revision final. Nunca llegamos a este estado.
		}else if(estadoActual==4){
			switch(c){
				case '&':
					resultado=new Token("&&");
					cadena="";
					estadoActual=0;
					break;
				}
		}else if(estadoActual==5){
			//Quitar cuando estemos en revision final. Nunca llegamos a este estado.
		}else if(estadoActual==6){
			switch(c) {
				case '|':
					resultado=new Token("||");
					cadena="";
					estadoActual=0;
					break;
				case '=':
					resultado=new Token("|=");
					cadena="";
					estadoActual=0;
					break;
				}
	}else if(estadoActual==7) {
		//Quitar cuando estemos en revision final. Nunca llegamos a este estado.
	}else if(estadoActual==8) {
		//Quitar cuando estemos en revision final. Nunca llegamos a este estado.
	}else if(estadoActual==9) {
		if(Character.isDigit(c)) {
			cadena+=c;
			estadoActual=9;
		}else {
			resultado=new Token(Integer.parseInt(cadena));
			resultado.setConsumeCaracter(false);
			cadena="";
			estadoActual=0;
		}
	}else if(estadoActual==10) {
		//Quitar cuando estemos en revision final. Nunca llegamos a este estado.
	}else if(estadoActual==11) {
		switch(c) {
			case '\\':
				estadoActual=12;
			case '"':
				resultado=new Token(cadena, false);
				cadena="";
				estadoActual=0;
				break;
			case '\n':
				//ERROR
				break;
			default:
				cadena+=c;
				estadoActual=11;
				break;
		}
	}else if(estadoActual==12) {
		if(c=='\n') {
			//error
		}else {
			cadena+=c;
			estadoActual=11;
		}
	}else if(estadoActual==13) {
		//Quitar cuando estemos en revision final. Nunca llegamos a este estado.
	}else if(estadoActual==14) {
		//hago switch cadena para ver que venia
		if(cadena.equals("=")) {
			//hago switch c para ver si lo que viene es un = o no
			switch(c) {
				case '=':
					resultado=new Token("==");
					cadena="";
					estadoActual=0;
					break;
				default:
					resultado=new Token("=");
					resultado.setConsumeCaracter(false);
					cadena="";
					estadoActual=0;
					break;			
			}
		}else if(cadena.equals("!")){
			resultado=new Token("!");
			resultado.setConsumeCaracter(false);
			cadena="";
			estadoActual=0;
		}else if(cadena.equals("<")){
			switch(c) {
				case '=':
					resultado=new Token("<=");
					cadena="";
					estadoActual=0;
					break;
				default:
					resultado=new Token("<");
					resultado.setConsumeCaracter(false);
					cadena="";
					estadoActual=0;
					break;
			}
		}else if(cadena.equals(">")) {
			switch(c) {
				case '=':
					resultado=new Token(">=");
					cadena="";
					estadoActual=0;
					break;
				default:
					resultado=new Token(">");
					resultado.setConsumeCaracter(false);
					cadena="";
					estadoActual=0;
					break;
			}
		}
	}else if(estadoActual==15) {
		//Quitar cuando estemos en revision final. Nunca llegamos a este estado.
	}else if(estadoActual==16) {
		//Quitar cuando estemos en revision final. Nunca llegamos a este estado.
	}else if(estadoActual==17) {
		switch(c) {
			case '*':
				cadena+="*";
				estadoActual=21;
				break;
			case '/':
				cadena+="/";
				estadoActual=19;
				break;
			default:
				resultado=new Token("/");
				resultado.setConsumeCaracter(false);
				cadena="";
				estadoActual=0;
				break;
		}
	}else if(estadoActual==18) {
		//Quitar cuando estemos en revision final. Nunca llegamos a este estado.
	}else if(estadoActual==19) {
		//El comentario no genera token, por lo que simplemente reseteamos todo y si te he visto no me acuerdo
		if(c=='\n') {
			estadoActual=0;
			cadena="";
		}else {
			estadoActual=19;
			cadena+=c;
		}
	}else if(estadoActual==20) {
		//Quitar cuando estemos en revision final. Nunca llegamos a este estado.
	}else if(estadoActual==21) {
		if(c=='*') {
			estadoActual=22;
			cadena+=c;
		}else {
			estadoActual=21;
			cadena+=c;
		}
	}else if(estadoActual==22) {
		if(c=='\\') {
			estadoActual=0;
			cadena="";
		}else{
			estadoActual=21;
			cadena+=c;
		}
	}else if(estadoActual==23) {
		//Quitar cuando estemos en revision final. Nunca llegamos a este estado.
	}

	return resultado;
}


}