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
		terminales.add('a');
		terminales.add('b');
		terminales.add('c');
		terminales.add('$');
		
		noTerminales.add('A');
		noTerminales.add('B');
		noTerminales.add('C');
		noTerminales.add('D');
		noTerminales.add('E');
		noTerminales.add('F');
		
		ArrayList<Character> produccionAa = new ArrayList<>();
		Map<Character, ArrayList<Character>> filaA = new HashMap<>();
		produccionAa.add('a');
		produccionAa.add('D');
		filaA.put('a',produccionAa);
		tablaTransicion.put('A', filaA);
		
		ArrayList<Character> produccionDa = new ArrayList<>();
		ArrayList<Character> produccionDb = new ArrayList<>();
		Map<Character, ArrayList<Character>> filaD = new HashMap<>();
		produccionDa.add('A');
		filaD.put('a', produccionDa);
		produccionDb.add('B');
		filaD.put('b', produccionDb);
		tablaTransicion.put('D', filaD);
		
		ArrayList<Character> produccionBb = new ArrayList<>();
		Map<Character, ArrayList<Character>> filaB = new HashMap<>();
		produccionBb.add('b');
		produccionBb.add('E');
		filaB.put('b', produccionBb);
		tablaTransicion.put('B', filaB);
		
		ArrayList<Character> produccionEb = new ArrayList<>();
		ArrayList<Character> produccionEc = new ArrayList<>();
		Map<Character, ArrayList<Character>> filaE = new HashMap<>();
		produccionEb.add('B');
		filaE.put('b',produccionEb);
		produccionEc.add('C');
		filaE.put('c', produccionEc);
		tablaTransicion.put('E', filaE);
		
		ArrayList<Character> produccionCc = new ArrayList<>();
		Map<Character, ArrayList<Character>> filaC = new HashMap<>();
		produccionCc.add('c');
		produccionCc.add('F');
		filaC.put('c', produccionCc);
		tablaTransicion.put('C', filaC);
		
		ArrayList<Character> produccionFc = new ArrayList<>();
		ArrayList<Character> produccionF$ = new ArrayList<>();
		Map<Character, ArrayList<Character>> filaF = new HashMap<>();
		produccionFc.add('C');
		filaF.put('c', produccionFc);
		produccionF$.clear();
		filaF.put('$', produccionF$);
		tablaTransicion.put('F', filaF);
		
		
		
		boolean bol=analizar("aabc$");
		System.out.println(bol);
		//Construccion de tabla de transiciones
		
	}
	
	
	/*
	 * Recibimos lista de tokens de analizadorLexico.
	 * Lo transformamos en sus correspondientes simbolos terminales.
	 * Analizamos sintacticamente
	 */
	public boolean analizar(/*ArrayList<Token>*/String listaTokens) {
		
		String codigo=listaTokens;//convertirTokenaTerminales(listaTokens);
		pila.clear();
		pila.push('$');
		pila.push('A');
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
