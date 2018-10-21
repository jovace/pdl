package pdl.analizadorLexico;

import java.util.ArrayList;
import java.util.HashMap;

public class analizadorLexico{
	static int estadoActual=0;
	static String cadena="";

	public static void main(String[] args){
		//En esta variable (o como sea que lo hagamos) tendremos los caracteres de codigo de entrada
		String codigo="codigo fuente de prueba";
		ArrayList<Token> listaTokens = new ArrayList<Token>();

		int i=0;
		while(i<codigo.length()) {
			char c=codigo.charAt(i);

			Token resultado=nextChar(c);
			if(resultado.getType()>-1) {
				//Tenemos un token completo, lo añadimos a la lista de tokens
				listaTokens.add(resultado);
				i++;
			}else if(resultado.getType()==-1 && resultado.consumeCaracter()) {
				//Tenemos un token incompleto que consume caracter
				i++;
			}else if(resultado.getType()==-1 && !resultado.consumeCaracter()){
				//Tenemos un token incompleto que NO CONSUME CARACTER
				//No incrementamos i, para que asi se vuelva a enviar
			}
		}

		//listaTokens contiene la lista de tokens completa, lo que hay que devolver. Se puede llamar a su metodo .toString para 
		//el fichero de salida que hay que generar.

	}

	private static Token nextChar(char c) {
		//Token(0,1)
		//Salvo que se genere un token diferente (un token completo o incompleto que no consume)
		//devolveremos un Token incompleto que consume caracter
		Token resultado=new Token(true);


		//Dependiendo del estado en el que se encuentre el automata
		if(estadoActual==0) {
			switch(c) {
			case '\t':
				cadena="";
				estadoActual=0;
				break;
			case '\n':
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
				cadena+="\"";
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
				resultado=new Token(1,0);
				cadena="";
				estadoActual=0;
				break;
			case "-":
				resultado=new Token(1,1);
				cadena="";
				estadoActual=0;
				break;
			case "*":
				resultado=new Token(1,2);
				cadena="";
				estadoActual=0;
				break;
			case "%":
				resultado=new Token(1,4);
				cadena="";
				estadoActual=0;
				break;
			case "{":
				resultado=new Token(4,0);
				cadena="";
				estadoActual=0;
				break;
			case "}":
				resultado=new Token(4,1);
				cadena="";
				estadoActual=0;
				break;
			case "(":
				resultado=new Token(4,2);
				cadena="";
				estadoActual=0;
				break;
			case ")":
				resultado=new Token(4,3);
				cadena="";
				estadoActual=0;
				break;
			case ";":
				resultado=new Token(4,4);
				cadena="";
				estadoActual=0;
				break;							
			}
		}else if(estadoActual==2){
			if(Character.isDigit(c)) {
				cadena+=c;
				estadoActual=2;
				break;
			}else if(Character.isAlphabetic(c)) {
				cadena+=c;
				estadoActual=2;
				break;
			}
			else {
				resultado=new Token(2,0);//no se que token seria
				cadena="";
				estadoActual=0;
				break;

			}

			//TODO
		}else if(estadoActual==3){
			//TODO
		}else if(estadoActual==4){
			switch(c){
			case '&':
				resultado=new Token(2,0);
				cadena="";
				estadoActual=0;
				break;
			}
		}else if(estadoActual==5){
			//TODO
		}else if(estadoActual==6){
			switch(c) {
		case '|':
			resultado=new Token(2,1);
			cadena="";
			estadoActual=0;
			break;
		case '=':
			resultado=new Token(1,8);
			cadena="";
			estadoActual=0;
			break;
		}
		//TODO
	}else if(estadoActual==7) {
		//TODO
	}else if(estadoActual==8) {
		//TODO
	}else if(estadoActual==9) {

		if(Character.isDigit(c)) {
			cadena+=c;
			estadoActual=9;
			break;
		}
		else {
			resultado=new Token(2,0);//no se que token seria
			cadena="";
			estadoActual=0;
			break;

		}
		//TODO
	}else if(estadoActual==10) {
		//TODO
	}else if(estadoActual==11) {
		//TODO
	}else if(estadoActual==12) {
		//TODO
	}else if(estadoActual==13) {
		//TODO
	}else if(estadoActual==14) {

		switch(cadena) {//hago switch cadena para ver que venia 
	case '=':
		switch(c) {//hago switch c para ver si lo que viene es un = o no
	case '=':
		resultado=new Token(3,0);
		cadena="";
		estadoActual=0;
		break;
		default:
			resultado=new Token(1,7);
			cadena="";
			estadoActual=0;
			break;
			
		}
	case '!':
		switch(c) {
		case '=':
			resultado=new Token(3,2);
			cadena="";
			estadoActual=0;
			break;
			default:
				resultado=new Token(1,3);
				cadena="";
				estadoActual=0;
				break;}
	case '<':
		switch(c) {
		case '=':
			resultado=new Token(3,4);
			cadena="";
			estadoActual=0;
			break;
			default:
				resultado=new Token(3,2);
				cadena="";
				estadoActual=0;
				break;}
	case '>':
		switch(c) {
		case '=':
			resultado=new Token(3,5);
			cadena="";
			estadoActual=0;
			break;
			default:
				resultado=new Token(3,3);
				cadena="";
				estadoActual=0;
				break;
				}}
		//TODO
	}else if(estadoActual==15) {
		//TODO
	}else if(estadoActual==16) {
		//TODO
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
				resultado=new Token(1,3);
				cadena="";
				estadoActual=0;
				break;
				
		}
		//TODO
	}else if(estadoActual==18) {
		//TODO
	}else if(estadoActual==19) {
		//TODO
	}else if(estadoActual==20) {
		//TODO
	}else if(estadoActual==21) {
		//TODO
	}else if(estadoActual==22) {
		//TODO
	}else if(estadoActual==23) {
		//TODO
	}

	return resultado;
}


}