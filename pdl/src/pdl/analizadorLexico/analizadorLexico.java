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
					cadena+="'";
					estadoActual=1;
					break;
				case '"':
					cadena+="\"";
					estadoActual=11;
					break;
				case '/':
					cadena+="/";
					estadoActual=17;
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
				case '|':
					cadena+="|";
					estadoActual=6;
					break;
				case '&':
					cadena+="&";
					estadoActual=4;
					break;
				case '\t':
					cadena="";
					estadoActual=0;
					break;
				case '\n':
					cadena="";
					estadoActual=0;
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
			
		}else if(estadoActual==3){
			
		}else if(estadoActual==4){
			switch(c){
				case '&':
					resultado=new Token(2,0);
					cadena="";
					estadoActual=0;
					break;
			}
		}else if(estadoActual==5){
			
		}else if(estadoActual==6){
			
		}else if(true) {
			
		}
		
		return resultado;
	}

	
}
/*
PRUEBA PERSONAL

//Arraylist que contiene los mapas de transicion para todos los estados
		ArrayList<HashMap<Character, Token>> transiciones = new ArrayList<HashMap<Character, Token>>();
		
		//Un token de cada tipo e indice
		Token sumaAO = new Token(1,0);
		Token restaAO = new Token(1,1);
		Token multAO = new Token(1,2);
		Token divAO = new Token(1,3);
		Token modAO = new Token(1,4);
		Token suma2AO = new Token(1,5);
		Token resta2AO = new Token(1,6);
		
		Token
		//...
		
		
		//Creas el Map de cada estado, que asigna un token generado a cada caracter de entrada
		//En este primer caso, pasamos del estado 0 al 1 con cualquiera de los siguientes simbolos
		//Como es un token incompleto, lo inicializamos solo con el estado al que pasamos
		HashMap<Character,Token> estado0 = new HashMap<Character,Token>();
		estado0.put(new Character('+'), new Token(1));
		estado0.put(new Character('-'), new Token(1));
		//...
		
		//Añadimos el mapa de rtansiciones del estado 0 en la posicion 0 del array
		transiciones.add(estado0);
		
		
		HashMap<Character,Token> estado1 = new HashMap<Character,Token>();
		estado1.put(new Character('+'), suma2AO);
		estado1.put(new Character('-'), resta2AO);
		estado1.put(new Character(' '),  sumaAO);
		estado1.put(new Character(' '),  sumaAO);
*/
