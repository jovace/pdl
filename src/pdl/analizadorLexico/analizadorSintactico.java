package pdl.analizadorLexico;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;
import java.util.Stack;

public class analizadorSintactico {
	Stack<Character> pila = new Stack<>();
	Map<Character,Map<Character,ArrayList<Character>>> tablaTransicion = new HashMap<>();
	Set<Character> terminales = new HashSet<>();
	Set<Character> noTerminales = new HashSet<>();
	Map<String,Character> tokenaTerminales = new HashMap<>();
	Map<Character,Token> terminalesAToken = new HashMap<>();
	
	public analizadorSintactico() {
		
		//Construccion de tabla de transiciones
	}
	
	
	/*
	 * Recibimos lista de tokens de analizadorLexico.
	 * Lo transformamos en sus correspondientes simbolos terminales.
	 * Analizamos sintacticamente
	 */
	public boolean analizar(ArrayList<Token> listaTokens) {
		
		String codigo=convertirTokenaTerminales(listaTokens);
		
		pila.push('$');
		pila.push('S');
		int puntero=0;
		
		while(pila.peek()!='$') {
			Character X=pila.peek();
			Character a=codigo.charAt(puntero);
			if(terminales.contains(X)) {
				if(X.equals(a)) {
					pila.pop();
					puntero++;
				}else {
					return false;
				}
			}else if(noTerminales.contains(X)) {
				pila.pop();
				
				ArrayList<Character> produccion = tablaTransicion.get(X).get(a);
				for(int i=produccion.size()-1;i>=0;i--) {					
					pila.push(produccion.get(i));
				}
			}else {
				return false;
			}
		}
		
		return true;
	}


	private String convertirTokenaTerminales(ArrayList<Token> listaTokens) {
		String codigo="";
		
		for(Token token : listaTokens) {
			codigo+=tokenaTerminales.get(token.tokenTipo());
		}
		
		return null;
	}
}
