package pdl.analizadorLexico;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class AnalizadorSemantico {
	private ArrayList<Integer> arbolSintactico;
	private ArrayList<Token> listaTokens;
	private TablaSimbolos tsMain = new TablaSimbolos("main");
	
	
	public AnalizadorSemantico (ArrayList<Integer> arbolSintactico, ArrayList<Token> listaTokens) {
		this.arbolSintactico=arbolSintactico;
		this.listaTokens=listaTokens;
	}
	
	public AnalizadorSemantico() {
		
	}
	
	public boolean analizar(Arbol asin) {
		String codigoFinal="";
		//Construir arbol semantico	-> DONE	
		
		//Calcular propiedades recursivamente
		ArrayList<Nodo> listaNodosPostorden = asin.getNodosPostorden();
		for(Nodo nodo : listaNodosPostorden) {
			String codigo = calcularCodigo(nodo);
			codigoFinal+=codigo;
		}
			
		
		//asin.calcularCodigo();
		
		//Recorrer arbol con recursividad
		
		
		System.out.println(codigoFinal);
		return true;
	}

	private String calcularCodigo(Nodo nodo) {
		String codigo="";
		switch(nodo.getProdN()) {
		case 0:
			codigo="print(1);";
			break;
		case 2:
			break;
		default:
			break;
		}
		return codigo;
	}
}



