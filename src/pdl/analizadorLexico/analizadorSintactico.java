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
	//tipo,indice -> cadena
	//8,9        -> sumasuma 
	//Map<Character,Token> terminalesAToken = new HashMap<>();
	
	public analizadorSintactico() {
		//Inicializar lista terminales, no terminales y tabla de transicion
		
		//iniz terminales
		terminales.add("var");
		terminales.add("id");
		terminales.add("int");
		terminales.add("$");
		terminales.add("=");
		terminales.add("(");
		terminales.add(")");
		terminales.add("!");
		terminales.add("bool");
		terminales.add("+");
		terminales.add("++");
		terminales.add("-");
		terminales.add("--");
		terminales.add(">");
		terminales.add("<");
		terminales.add("<=");
		terminales.add(">=");
		terminales.add("{");
		terminales.add("}");
		terminales.add("function");
		terminales.add("return");
		terminales.add(";");
		terminales.add(",");
		terminales.add("*");
		terminales.add("|=");
		terminales.add("/");
		terminales.add("%");
		terminales.add("&&");
		terminales.add("||");
		terminales.add("print");
		terminales.add("prompt");
		terminales.add("false");
		terminales.add("true");
		terminales.add("for");
		terminales.add("char");
		terminales.add("cte_int");
		terminales.add("cte_cadena");
		terminales.add("cte_logica");
		
		//iniz no terminales
		noTerminales.add("J");
		noTerminales.add("D");
		noTerminales.add("T");
		noTerminales.add("I");
		noTerminales.add("E");
		noTerminales.add("G");
		noTerminales.add("GG");
		noTerminales.add("EE");
		noTerminales.add("X");
		noTerminales.add("XX");
		noTerminales.add("F");
		noTerminales.add("H");
		noTerminales.add("A");
		noTerminales.add("AA");
		noTerminales.add("C");
		noTerminales.add("S");
		noTerminales.add("R");
		noTerminales.add("B");
		
		//iniz tabla
		ArrayList<String> produccionAcierrap = new ArrayList<>();
		ArrayList<String> produccionAbool = new ArrayList<>();
		ArrayList<String> produccionAint = new ArrayList<>();
		ArrayList<String> produccionAstring = new ArrayList<>();
		Map<String, ArrayList<String>> filaA = new HashMap<>();
		produccionAcierrap.add("38");
		filaA.put(")",produccionAcierrap);
		produccionAbool.add("37");
		produccionAbool.add("T");
		produccionAbool.add("id");
		produccionAbool.add("AA");
		filaA.put("bool", produccionAbool);
		filaA.put("int", produccionAbool);
		filaA.put("string", produccionAbool);
		
		
		tablaTransicion.put("A", filaA);
		
		ArrayList<String> produccionDa = new ArrayList<>();
		ArrayList<String> produccionDb = new ArrayList<>();
		Map<String, ArrayList<String>> filaD = new HashMap<>();
		//produccionAa.add("A");
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


	private ArrayList<String> convertirTokenaTerminales(ArrayList<Token> listaTokens) {
		ArrayList<String> codigo=new ArrayList<>();
		
		for(Token token : listaTokens) {
			codigo.add(tokenaTerminales.get(token.tokenTipo()));
		}
		
		return codigo;
	}
}
