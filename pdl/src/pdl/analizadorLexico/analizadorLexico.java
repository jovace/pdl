package pdl.analizadorLexico;
//hola
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
		Token resultado=new Token(-1);
		
		if(estadoActual==0) {
//			if(Character.isDigit(c)) {//si es un numero
//				int num=c;
//				c=0;
//			}
//			if(Character.isLetter(c)) {//si es un numero
//				string palabra =c;
//				c=a;
			switch(c) {
			case '+':
				cadena+="+";
				estadoActual=1;
				break;
			case '-':
				cadena+="-";
				estadoActual=1;
				break;
			case '|':
				cadena+="|";
				estadoActual=1;
				break;
			case '/': //comentarios
				cadena+="/";
				estadoActual=2;
				break;			
//			case '0': //si es un numero
//				cadena==num;
//				estadoActual=5;
//				break;
//			case 'a': //si es un letra
//				cadena==palabra;
//				estadoActual=6;
//				break;
			
		}else if(estadoActual==1) {
			if(cadena.equals("+")&& c=='+') {
				resultado=new Token(1,5);
				cadena="";
				estadoActual=0;
			}else if(cadena.equals("-")&& c=='-') {
				resultado=new Token(1,6);
				cadena="";
				estadoActual=0;
			}else{
				resultado=new Token(false);
				cadena="";
				estadoActual=0;
			}
		}
		else if(estadoActual==2) { //cuando haya comentarios leeremos hasta detectar el siguiente asterisco barra
			if (c == '*') {//aqui ya sabemos que es comentario
					estadoActual=3;					
				}
			else {
				//tendriamos que ver que puede haber despues de una / si no es comentario
			}
			}
		else if(estadoActual==3){//haremos bucle para el comentario hasta que salga otro asterisco barra
			if (c !='*') {//es parte del comentario
			estadoActual=3;
		}
		else{
			estadoActual=4;//si sale un asterisco tenemos que ir a otro estado y vemos si hay barra y se acaba el comentario o si no hay barra y sigue
		}}
		else if(estadoActual==4) {
			if (c == '/') {
				estadoActual=0;
			}
			else {
				estadoActual=2;
			}
		}
		}
		else if(estadoActual==5) { //cuando venga un numero
			if(Character.isDigit(c)) {
			cadena = cadena*10 +c
				estadoActual=5;
			}
		}
			else if(estadoActual==6) { //cuando venga una letra
			if (Character.isLetter(c)) {
				cadena = cadena+c;
					estadoActual=6;}
			else {//tendriamos que ver que es c (necesitaria el automata)
				
			}
			}
		else if(true) { //...
			
		}
		
		return new Token(0);
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
