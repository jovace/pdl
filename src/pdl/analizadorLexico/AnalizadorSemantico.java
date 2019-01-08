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
		case 1:
			break;
		case 2:
			break;
		case 3:
			break;
		case 4:
			break;
		case 5: //D -> var T id I ;
			nodo.setProp("tipo", nodo.getHijo("T").getProp("tipo"));
			nodo.setProp("valor", nodo.getHijo("I").getProp("valor"));
			System.out.println("init variable tipo: "+nodo.getProp("tipo") + " y valor: "+nodo.getProp("valor"));
			break;
		case 6: //T -> int
			nodo.setProp("tipo", "int");
			break;
		case 7: //T -> string
			nodo.setProp("tipo", "string");
			break;
		case 8: //T -> bool
			nodo.setProp("tipo", "bool");
			break;
		case 9: //I -> = E 
			nodo.setProp("valor", nodo.getHijo("E").getProp("valor"));
			break;
		case 10: //I -> |= E
			break;
		case 11: //I ->lambda
			break;
		case 12: //E -> G EE
			nodo.setProp("valor", nodo.getHijo("G").getProp("valor"));
			break;
		case 13: //G -> ( X )
			break;
		case 14:
			break;
		case 15:
			break;
		case 16: //G -> cte_int
			nodo.setProp("valor", nodo.getHijo("cte_int").getToken().getNumero());
			break;
		default:
			break;
		}
		return codigo;
	}
}



