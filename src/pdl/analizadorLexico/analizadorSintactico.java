package pdl.analizadorLexico;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;
import java.util.Stack;

public class analizadorSintactico {
	Stack<String> pila = new Stack<>();
	Map<String,Map<String,ArrayList<String>>> tablaTransicion = new HashMap<>();
	Set<String> terminales = new HashSet<>();
	Set<String> noTerminales = new HashSet<>();
	Map<String,String> tokenaTerminales = new HashMap<>();
	Map<Character,Token> terminalesAToken = new HashMap<>();
	
	public analizadorSintactico() {
		terminales.add("a");
		terminales.add("b");
		terminales.add("c");
		terminales.add("$");
		
		noTerminales.add("A");
		noTerminales.add("B");
		noTerminales.add("C");
		noTerminales.add("D");
		noTerminales.add("E");
		noTerminales.add("F");
		
		ArrayList<String> produccionAa = new ArrayList<>();
		Map<String, ArrayList<String>> filaA = new HashMap<>();
		produccionAa.add("a");
		produccionAa.add("D");
		filaA.put("a",produccionAa);
		tablaTransicion.put("A", filaA);
		
		ArrayList<String> produccionDa = new ArrayList<>();
		ArrayList<String> produccionDb = new ArrayList<>();
		Map<String, ArrayList<String>> filaD = new HashMap<>();
		produccionDa.add("A");
		filaD.put("a", produccionDa);
		produccionDb.add("B");
		filaD.put("b", produccionDb);
		tablaTransicion.put("D", filaD);
		
		ArrayList<String> produccionBb = new ArrayList<>();
		Map<String, ArrayList<String>> filaB = new HashMap<>();
		produccionBb.add("b");
		produccionBb.add("E");
		filaB.put("b", produccionBb);
		tablaTransicion.put("B", filaB);
		
		ArrayList<String> produccionEb = new ArrayList<>();
		ArrayList<String> produccionEc = new ArrayList<>();
		Map<String, ArrayList<String>> filaE = new HashMap<>();
		produccionEb.add("B");
		filaE.put("b",produccionEb);
		produccionEc.add("C");
		filaE.put("c", produccionEc);
		tablaTransicion.put("E", filaE);
		
		ArrayList<String> produccionCc = new ArrayList<>();
		Map<String, ArrayList<String>> filaC = new HashMap<>();
		produccionCc.add("c");
		produccionCc.add("F");
		filaC.put("c", produccionCc);
		tablaTransicion.put("C", filaC);
		
		ArrayList<String> produccionFc = new ArrayList<>();
		ArrayList<String> produccionF$ = new ArrayList<>();
		Map<String, ArrayList<String>> filaF = new HashMap<>();
		produccionFc.add("C");
		filaF.put("c", produccionFc);
		produccionF$.clear();
		filaF.put("$", produccionF$);
		tablaTransicion.put("F", filaF);
		
		
		ArrayList<String> tokens=new ArrayList<>();
		tokens.add("a");
		tokens.add("a");
		tokens.add("b");
		tokens.add("c");
		tokens.add("$");
		boolean bol=analizar(tokens);
		System.out.println(bol);
		//Construccion de tabla de transiciones
		
	}
	
	
	/*
	 * Recibimos lista de tokens de analizadorLexico.
	 * Lo transformamos en sus correspondientes simbolos terminales.
	 * Analizamos sintacticamente
	 */
	public boolean analizar(/*ArrayList<Token>*/ArrayList<String> listaTokens) {
		
		ArrayList<String> codigo=listaTokens;//convertirTokenaTerminales(listaTokens);
		pila.clear();
		pila.push("$");
		pila.push("A");
		int puntero=0;
		
		while(!pila.peek().equals("$")) {
			String X=pila.peek();
			String a=codigo.get(puntero);
			if(terminales.contains(X)) {
				if(X.equals(a)) {
					pila.pop();
					puntero++;
				}else {
					return false;
				}
			}else if(noTerminales.contains(X)) {
				pila.pop();
				
				ArrayList<String> produccion = tablaTransicion.get(X).get(a);
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
