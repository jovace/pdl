package pdl.analizadorLexico;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class AnalizadorSemantico {
	private ArrayList<Integer> arbolSintactico;
	private ArrayList<Token> listaTokens;
	private TablaSimbolos tsMain = new TablaSimbolos("main",null);
	
	
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
		//TODO transformar todo a ifs
		int prodN = nodo.getProdN();
		if (prodN == 1) { // J -> D J
		} else if (prodN == 2) { // J -> F J
		} else if (prodN == 3) { // J -> S J
		} else if (prodN == 4) { // J -> $
			System.out.println("\n \n \nPrograma termino con estado 0");
			System.out.println(tsMain.toString());
		} else if (prodN == 5) { // D -> var T id I ;
			nodo.setProp("tipo", nodo.getHijo("T").getProp("tipo"));
			nodo.setProp("valor", nodo.getHijo("I").getProp("valor"));
			nodo.setProp("id", nodo.getHijo("id").getToken().getLexema());
			if (tsMain.existeSimbolo((String) nodo.getProp("id"), true)) {
				System.out.println("Error, variable ya definida en linea " + nodo.getHijo("id").getToken().getLinea());
			} else {
				// TODO Comprobacion tipo coincide, o sino conversion explicita
				tsMain.addSimbolo((String) nodo.getProp("id"), new Simbolo((String) nodo.getProp("tipo"),
						(String) nodo.getProp("id"), nodo.getProp("valor"), tsMain));
				System.out.println("Variable inicializada en tabla de simbolos " + tsMain.scope);
			}
		} else if (prodN == 6) { // T -> int
			nodo.setProp("tipo", "int");
		} else if (prodN == 7) { // T -> string
			nodo.setProp("tipo", "string");
		} else if (prodN == 8) { // T -> bool
			nodo.setProp("tipo", "bool");
		} else if (prodN == 9) { // I -> = E
			nodo.setProp("valor", nodo.getHijo("E").getProp("valor"));
			nodo.setProp("tipo", nodo.getHijo("E").getProp("tipo"));
		} else if (prodN == 10) { // I -> |= E
			
		} else if (prodN == 11) { // I ->lambda
		} else if (prodN == 12) { // E -> G EE
			Nodo EE = nodo.getHijo("EE");
			Nodo G = nodo.getHijo("G");
			switch (EE.getProdN()) {
			case 24: // EE -> lambda
				nodo.setProp("valor", G.getProp("valor"));
				nodo.setProp("tipo", G.getProp("tipo"));
				break;
			case 22: // EE -> + G EE
				// Comprobar que los componentes son del mismo tipo. TODO Posible conversion
				// implicita de int->string? int->boolean?
				if (!G.getProp("tipo").equals(EE.getProp("tipo"))) {
					System.out.println("Error, deben ser valores del mismo tipo. Encontrado G:"
							+ (String) G.getProp("tipo") + " EE:" + (String) EE.getProp("tipo"));
				}

				// Dependiendo del tipo tendremos una operacion u otra
				switch ((String) G.getProp("tipo")) {
				case "int":
					nodo.setProp("valor", (Integer) G.getProp("valor") + (Integer) EE.getProp("valor"));
					break;
				case "string":
					nodo.setProp("valor", (String) G.getProp("valor") + "" + (Integer) EE.getProp("valor"));
				case "bool":
					nodo.setProp("valor", (Boolean) G.getProp("valor") || (Boolean) EE.getProp("valor"));
				}
				nodo.setProp("tipo", G.getProp("tipo"));
				break;
			case 23:
				// Comprobar que los componentes son del mismo tipo.
				if (!G.getProp("tipo").equals(EE.getProp("tipo"))) {
					System.out.println("Error, deben ser valores del mismo tipo. Encontrado G:"
							+ (String) G.getProp("tipo") + " EE:" + (String) EE.getProp("tipo"));
				}

				nodo.setProp("valor", (Integer) G.getProp("valor") - (Integer) EE.getProp("valor"));
				nodo.setProp("tipo", G.getProp("tipo"));
				break;
			case 25:
				// Comprobar que los componentes son del mismo tipo. TODO Posible conversion
				// implicita de int->string? int->boolean?
				if (!G.getProp("tipo").equals(EE.getProp("tipo"))) {
					System.out.println("Error, deben ser valores del mismo tipo. Encontrado G:"
							+ (String) G.getProp("tipo") + " EE:" + (String) EE.getProp("tipo"));
				}

				// Dependiendo del tipo tendremos una operacion u otra
				switch ((String) G.getProp("tipo")) {
				case "int":
					nodo.setProp("valor", (Integer) G.getProp("valor") * (Integer) EE.getProp("valor"));
					break;
				case "bool":
					nodo.setProp("valor", (Boolean) G.getProp("valor") && (Boolean) EE.getProp("valor"));
				}
				nodo.setProp("tipo", G.getProp("tipo"));
				break;
			case 26:
				// Comprobar que los componentes son del mismo tipo.
				if (!G.getProp("tipo").equals(EE.getProp("tipo"))) {
					System.out.println("Error, deben ser valores del mismo tipo. Encontrado G:"
							+ (String) G.getProp("tipo") + " EE:" + (String) EE.getProp("tipo"));
				}

				nodo.setProp("valor", (Integer) G.getProp("valor") / (Integer) EE.getProp("valor"));
				nodo.setProp("tipo", G.getProp("tipo"));
				break;
			case 27:
				// Comprobar que los componentes son del mismo tipo.
				if (!G.getProp("tipo").equals(EE.getProp("tipo"))) {
					System.out.println("Error, deben ser valores del mismo tipo. Encontrado G:"
							+ (String) G.getProp("tipo") + " EE:" + (String) EE.getProp("tipo"));
				}

				nodo.setProp("valor", (Integer) G.getProp("valor") % (Integer) EE.getProp("valor"));
				nodo.setProp("tipo", G.getProp("tipo"));
				break;
			}
		} else if (prodN == 13) { // G -> ( X )
		} else if (prodN == 14) {
		} else if (prodN == 15) {
		} else if (prodN == 16) { // G -> cte_int
			nodo.setProp("valor", nodo.getHijo("cte_int").getToken().getNumero());
			nodo.setProp("tipo", "int");
		} else if (prodN == 17) { // G -> cte_cadena
			nodo.setProp("valor", nodo.getHijo("cte_cadena").getToken().getNumero());
			nodo.setProp("tipo", "string");
		} else if (prodN == 18) { // G -> cte_logica
			nodo.setProp("valor", Boolean.parseBoolean(nodo.getHijo("cte_logica").getToken().getLexema()));
			nodo.setProp("tipo", "bool");
		} else if (prodN == 19) { // GG -> lambda
			
		}else if (prodN == 20) { //
			
		}else if (prodN == 21) {
			
		}else if (prodN == 22) {
			// TODO Una vez pasado a ifs, renombrar de vuelta
			Nodo EE1 = nodo.getHijo("EE");
			Nodo G1 = nodo.getHijo("G");
			switch (EE1.getProdN()) {
			case 24: // EE -> lambda
				nodo.setProp("valor", G1.getProp("valor"));
				nodo.setProp("tipo", G1.getProp("tipo"));
				break;
			case 22: // EE -> + G EE
				// Comprobar que los componentes son del mismo tipo. TODO Posible conversion
				// implicita de int->string? int->boolean?
				if (!G1.getProp("tipo").equals(EE1.getProp("tipo"))) {
					System.out.println("Error, deben ser valores del mismo tipo. Encontrado G:"
							+ (String) G1.getProp("tipo") + " EE:" + (String) EE1.getProp("tipo"));
				}

				// Dependiendo del tipo tendremos una operacion u otra
				switch ((String) G1.getProp("tipo")) {
				case "int":
					nodo.setProp("valor", (Integer) G1.getProp("valor") + (Integer) EE1.getProp("valor"));
					break;
				case "string":
					nodo.setProp("valor", (String) G1.getProp("valor") + "" + (Integer) EE1.getProp("valor"));
				case "bool":
					nodo.setProp("valor", (Boolean) G1.getProp("valor") || (Boolean) EE1.getProp("valor"));
				}
				nodo.setProp("tipo", G1.getProp("tipo"));
				break;
			case 23:
				// Comprobar que los componentes son del mismo tipo.
				if (!G1.getProp("tipo").equals(EE1.getProp("tipo"))) {
					System.out.println("Error, deben ser valores del mismo tipo. Encontrado G:"
							+ (String) G1.getProp("tipo") + " EE:" + (String) EE1.getProp("tipo"));
				}

				nodo.setProp("valor", (Integer) G1.getProp("valor") - (Integer) EE1.getProp("valor"));
				nodo.setProp("tipo", G1.getProp("tipo"));
				break;
			case 25:
				// Comprobar que los componentes son del mismo tipo. TODO Posible conversion
				// implicita de int->string? int->boolean?
				if (!G1.getProp("tipo").equals(EE1.getProp("tipo"))) {
					System.out.println("Error, deben ser valores del mismo tipo. Encontrado G:"
							+ (String) G1.getProp("tipo") + " EE:" + (String) EE1.getProp("tipo"));
				}

				// Dependiendo del tipo tendremos una operacion u otra
				switch ((String) G1.getProp("tipo")) {
				case "int":
					nodo.setProp("valor", (Integer) G1.getProp("valor") * (Integer) EE1.getProp("valor"));
					break;
				case "bool":
					nodo.setProp("valor", (Boolean) G1.getProp("valor") && (Boolean) EE1.getProp("valor"));
				}
				nodo.setProp("tipo", G1.getProp("tipo"));
				break;
			case 26:
				// Comprobar que los componentes son del mismo tipo.
				if (!G1.getProp("tipo").equals(EE1.getProp("tipo"))) {
					System.out.println("Error, deben ser valores del mismo tipo. Encontrado G:"
							+ (String) G1.getProp("tipo") + " EE:" + (String) EE1.getProp("tipo"));
				}

				nodo.setProp("valor", (Integer) G1.getProp("valor") / (Integer) EE1.getProp("valor"));
				nodo.setProp("tipo", G1.getProp("tipo"));
				break;
			case 27:
				// Comprobar que los componentes son del mismo tipo.
				if (!G1.getProp("tipo").equals(EE1.getProp("tipo"))) {
					System.out.println("Error, deben ser valores del mismo tipo. Encontrado G:"
							+ (String) G1.getProp("tipo") + " EE:" + (String) EE1.getProp("tipo"));
				}

				nodo.setProp("valor", (Integer) G1.getProp("valor") % (Integer) EE1.getProp("valor"));
				nodo.setProp("tipo", G1.getProp("tipo"));
				break;
			}
		}else if(prodN==23) {
			Nodo EE = nodo.getHijo("EE");
			Nodo G = nodo.getHijo("G");
			switch (EE.getProdN()) {
			case 24: // EE -> lambda
				nodo.setProp("valor", G.getProp("valor"));
				nodo.setProp("tipo", G.getProp("tipo"));
				break;
			case 22: // EE -> + G EE
				// Comprobar que los componentes son del mismo tipo. TODO Posible conversion
				// implicita de int->string? int->boolean?
				if (!G.getProp("tipo").equals(EE.getProp("tipo"))) {
					System.out.println("Error, deben ser valores del mismo tipo. Encontrado G:"
							+ (String) G.getProp("tipo") + " EE:" + (String) EE.getProp("tipo"));
				}

				// Dependiendo del tipo tendremos una operacion u otra
				switch ((String) G.getProp("tipo")) {
				case "int":
					nodo.setProp("valor", (Integer) G.getProp("valor") + (Integer) EE.getProp("valor"));
					break;
				case "string":
					nodo.setProp("valor", (String) G.getProp("valor") + "" + (Integer) EE.getProp("valor"));
				case "bool":
					nodo.setProp("valor", (Boolean) G.getProp("valor") || (Boolean) EE.getProp("valor"));
				}
				nodo.setProp("tipo", G.getProp("tipo"));
				break;
			case 23:
				// Comprobar que los componentes son del mismo tipo.
				if (!G.getProp("tipo").equals(EE.getProp("tipo"))) {
					System.out.println("Error, deben ser valores del mismo tipo. Encontrado G:"
							+ (String) G.getProp("tipo") + " EE:" + (String) EE.getProp("tipo"));
				}

				nodo.setProp("valor", (Integer) G.getProp("valor") - (Integer) EE.getProp("valor"));
				nodo.setProp("tipo", G.getProp("tipo"));
				break;
			case 25:
				// Comprobar que los componentes son del mismo tipo. TODO Posible conversion
				// implicita de int->string? int->boolean?
				if (!G.getProp("tipo").equals(EE.getProp("tipo"))) {
					System.out.println("Error, deben ser valores del mismo tipo. Encontrado G:"
							+ (String) G.getProp("tipo") + " EE:" + (String) EE.getProp("tipo"));
				}

				// Dependiendo del tipo tendremos una operacion u otra
				switch ((String) G.getProp("tipo")) {
				case "int":
					nodo.setProp("valor", (Integer) G.getProp("valor") * (Integer) EE.getProp("valor"));
					break;
				case "bool":
					nodo.setProp("valor", (Boolean) G.getProp("valor") && (Boolean) EE.getProp("valor"));
				}
				nodo.setProp("tipo", G.getProp("tipo"));
				break;
			case 26:
				// Comprobar que los componentes son del mismo tipo.
				if (!G.getProp("tipo").equals(EE.getProp("tipo"))) {
					System.out.println("Error, deben ser valores del mismo tipo. Encontrado G:"
							+ (String) G.getProp("tipo") + " EE:" + (String) EE.getProp("tipo"));
				}

				nodo.setProp("valor", (Integer) G.getProp("valor") / (Integer) EE.getProp("valor"));
				nodo.setProp("tipo", G.getProp("tipo"));
				break;
			case 27:
				// Comprobar que los componentes son del mismo tipo.
				if (!G.getProp("tipo").equals(EE.getProp("tipo"))) {
					System.out.println("Error, deben ser valores del mismo tipo. Encontrado G:"
							+ (String) G.getProp("tipo") + " EE:" + (String) EE.getProp("tipo"));
				}

				nodo.setProp("valor", (Integer) G.getProp("valor") % (Integer) EE.getProp("valor"));
				nodo.setProp("tipo", G.getProp("tipo"));
				break;
			}
		}else if(prodN==25) {
			Nodo EE = nodo.getHijo("EE");
			Nodo G = nodo.getHijo("G");
			switch (EE.getProdN()) {
			case 24: // EE -> lambda
				nodo.setProp("valor", G.getProp("valor"));
				nodo.setProp("tipo", G.getProp("tipo"));
				break;
			case 22: // EE -> + G EE
				// Comprobar que los componentes son del mismo tipo. TODO Posible conversion
				// implicita de int->string? int->boolean?
				if (!G.getProp("tipo").equals(EE.getProp("tipo"))) {
					System.out.println("Error, deben ser valores del mismo tipo. Encontrado G:"
							+ (String) G.getProp("tipo") + " EE:" + (String) EE.getProp("tipo"));
				}

				// Dependiendo del tipo tendremos una operacion u otra
				switch ((String) G.getProp("tipo")) {
				case "int":
					nodo.setProp("valor", (Integer) G.getProp("valor") + (Integer) EE.getProp("valor"));
					break;
				case "string":
					nodo.setProp("valor", (String) G.getProp("valor") + "" + (Integer) EE.getProp("valor"));
				case "bool":
					nodo.setProp("valor", (Boolean) G.getProp("valor") || (Boolean) EE.getProp("valor"));
				}
				nodo.setProp("tipo", G.getProp("tipo"));
				break;
			case 23:
				// Comprobar que los componentes son del mismo tipo.
				if (!G.getProp("tipo").equals(EE.getProp("tipo"))) {
					System.out.println("Error, deben ser valores del mismo tipo. Encontrado G:"
							+ (String) G.getProp("tipo") + " EE:" + (String) EE.getProp("tipo"));
				}

				nodo.setProp("valor", (Integer) G.getProp("valor") - (Integer) EE.getProp("valor"));
				nodo.setProp("tipo", G.getProp("tipo"));
				break;
			case 25:
				// Comprobar que los componentes son del mismo tipo. TODO Posible conversion
				// implicita de int->string? int->boolean?
				if (!G.getProp("tipo").equals(EE.getProp("tipo"))) {
					System.out.println("Error, deben ser valores del mismo tipo. Encontrado G:"
							+ (String) G.getProp("tipo") + " EE:" + (String) EE.getProp("tipo"));
				}

				// Dependiendo del tipo tendremos una operacion u otra
				switch ((String) G.getProp("tipo")) {
				case "int":
					nodo.setProp("valor", (Integer) G.getProp("valor") * (Integer) EE.getProp("valor"));
					break;
				case "bool":
					nodo.setProp("valor", (Boolean) G.getProp("valor") && (Boolean) EE.getProp("valor"));
				}
				nodo.setProp("tipo", G.getProp("tipo"));
				break;
			case 26:
				// Comprobar que los componentes son del mismo tipo.
				if (!G.getProp("tipo").equals(EE.getProp("tipo"))) {
					System.out.println("Error, deben ser valores del mismo tipo. Encontrado G:"
							+ (String) G.getProp("tipo") + " EE:" + (String) EE.getProp("tipo"));
				}

				nodo.setProp("valor", (Integer) G.getProp("valor") / (Integer) EE.getProp("valor"));
				nodo.setProp("tipo", G.getProp("tipo"));
				break;
			case 27:
				// Comprobar que los componentes son del mismo tipo.
				if (!G.getProp("tipo").equals(EE.getProp("tipo"))) {
					System.out.println("Error, deben ser valores del mismo tipo. Encontrado G:"
							+ (String) G.getProp("tipo") + " EE:" + (String) EE.getProp("tipo"));
				}

				nodo.setProp("valor", (Integer) G.getProp("valor") % (Integer) EE.getProp("valor"));
				nodo.setProp("tipo", G.getProp("tipo"));
				break;
			}
		}else if(prodN==26) {
			Nodo EE = nodo.getHijo("EE");
			Nodo G = nodo.getHijo("G");
			switch (EE.getProdN()) {
			case 24: // EE -> lambda
				nodo.setProp("valor", G.getProp("valor"));
				nodo.setProp("tipo", G.getProp("tipo"));
				break;
			case 22: // EE -> + G EE
				// Comprobar que los componentes son del mismo tipo. TODO Posible conversion
				// implicita de int->string? int->boolean?
				if (!G.getProp("tipo").equals(EE.getProp("tipo"))) {
					System.out.println("Error, deben ser valores del mismo tipo. Encontrado G:"
							+ (String) G.getProp("tipo") + " EE:" + (String) EE.getProp("tipo"));
				}

				// Dependiendo del tipo tendremos una operacion u otra
				switch ((String) G.getProp("tipo")) {
				case "int":
					nodo.setProp("valor", (Integer) G.getProp("valor") + (Integer) EE.getProp("valor"));
					break;
				case "string":
					nodo.setProp("valor", (String) G.getProp("valor") + "" + (Integer) EE.getProp("valor"));
				case "bool":
					nodo.setProp("valor", (Boolean) G.getProp("valor") || (Boolean) EE.getProp("valor"));
				}
				nodo.setProp("tipo", G.getProp("tipo"));
				break;
			case 23:
				// Comprobar que los componentes son del mismo tipo.
				if (!G.getProp("tipo").equals(EE.getProp("tipo"))) {
					System.out.println("Error, deben ser valores del mismo tipo. Encontrado G:"
							+ (String) G.getProp("tipo") + " EE:" + (String) EE.getProp("tipo"));
				}

				nodo.setProp("valor", (Integer) G.getProp("valor") - (Integer) EE.getProp("valor"));
				nodo.setProp("tipo", G.getProp("tipo"));
				break;
			case 25:
				// Comprobar que los componentes son del mismo tipo. TODO Posible conversion
				// implicita de int->string? int->boolean?
				if (!G.getProp("tipo").equals(EE.getProp("tipo"))) {
					System.out.println("Error, deben ser valores del mismo tipo. Encontrado G:"
							+ (String) G.getProp("tipo") + " EE:" + (String) EE.getProp("tipo"));
				}

				// Dependiendo del tipo tendremos una operacion u otra
				switch ((String) G.getProp("tipo")) {
				case "int":
					nodo.setProp("valor", (Integer) G.getProp("valor") * (Integer) EE.getProp("valor"));
					break;
				case "bool":
					nodo.setProp("valor", (Boolean) G.getProp("valor") && (Boolean) EE.getProp("valor"));
				}
				nodo.setProp("tipo", G.getProp("tipo"));
				break;
			case 26:
				// Comprobar que los componentes son del mismo tipo.
				if (!G.getProp("tipo").equals(EE.getProp("tipo"))) {
					System.out.println("Error, deben ser valores del mismo tipo. Encontrado G:"
							+ (String) G.getProp("tipo") + " EE:" + (String) EE.getProp("tipo"));
				}

				nodo.setProp("valor", (Integer) G.getProp("valor") / (Integer) EE.getProp("valor"));
				nodo.setProp("tipo", G.getProp("tipo"));
				break;
			case 27:
				// Comprobar que los componentes son del mismo tipo.
				if (!G.getProp("tipo").equals(EE.getProp("tipo"))) {
					System.out.println("Error, deben ser valores del mismo tipo. Encontrado G:"
							+ (String) G.getProp("tipo") + " EE:" + (String) EE.getProp("tipo"));
				}

				nodo.setProp("valor", (Integer) G.getProp("valor") % (Integer) EE.getProp("valor"));
				nodo.setProp("tipo", G.getProp("tipo"));
				break;
			}
		}else if(prodN==27) {
			Nodo EE = nodo.getHijo("EE");
			Nodo G = nodo.getHijo("G");
			switch (EE.getProdN()) {
			case 24: // EE -> lambda
				nodo.setProp("valor", G.getProp("valor"));
				nodo.setProp("tipo", G.getProp("tipo"));
				break;
			case 22: // EE -> + G EE
				// Comprobar que los componentes son del mismo tipo. TODO Posible conversion
				// implicita de int->string? int->boolean?
				if (!G.getProp("tipo").equals(EE.getProp("tipo"))) {
					System.out.println("Error, deben ser valores del mismo tipo. Encontrado G:"
							+ (String) G.getProp("tipo") + " EE:" + (String) EE.getProp("tipo"));
				}

				// Dependiendo del tipo tendremos una operacion u otra
				switch ((String) G.getProp("tipo")) {
				case "int":
					nodo.setProp("valor", (Integer) G.getProp("valor") + (Integer) EE.getProp("valor"));
					break;
				case "string":
					nodo.setProp("valor", (String) G.getProp("valor") + "" + (Integer) EE.getProp("valor"));
				case "bool":
					nodo.setProp("valor", (Boolean) G.getProp("valor") || (Boolean) EE.getProp("valor"));
				}
				nodo.setProp("tipo", G.getProp("tipo"));
				break;
			case 23:
				// Comprobar que los componentes son del mismo tipo.
				if (!G.getProp("tipo").equals(EE.getProp("tipo"))) {
					System.out.println("Error, deben ser valores del mismo tipo. Encontrado G:"
							+ (String) G.getProp("tipo") + " EE:" + (String) EE.getProp("tipo"));
				}

				nodo.setProp("valor", (Integer) G.getProp("valor") - (Integer) EE.getProp("valor"));
				nodo.setProp("tipo", G.getProp("tipo"));
				break;
			case 25:
				// Comprobar que los componentes son del mismo tipo. TODO Posible conversion
				// implicita de int->string? int->boolean?
				if (!G.getProp("tipo").equals(EE.getProp("tipo"))) {
					System.out.println("Error, deben ser valores del mismo tipo. Encontrado G:"
							+ (String) G.getProp("tipo") + " EE:" + (String) EE.getProp("tipo"));
				}

				// Dependiendo del tipo tendremos una operacion u otra
				switch ((String) G.getProp("tipo")) {
				case "int":
					nodo.setProp("valor", (Integer) G.getProp("valor") * (Integer) EE.getProp("valor"));
					break;
				case "bool":
					nodo.setProp("valor", (Boolean) G.getProp("valor") && (Boolean) EE.getProp("valor"));
				}
				nodo.setProp("tipo", G.getProp("tipo"));
				break;
			case 26:
				// Comprobar que los componentes son del mismo tipo.
				if (!G.getProp("tipo").equals(EE.getProp("tipo"))) {
					System.out.println("Error, deben ser valores del mismo tipo. Encontrado G:"
							+ (String) G.getProp("tipo") + " EE:" + (String) EE.getProp("tipo"));
				}

				nodo.setProp("valor", (Integer) G.getProp("valor") / (Integer) EE.getProp("valor"));
				nodo.setProp("tipo", G.getProp("tipo"));
				break;
			case 27:
				// Comprobar que los componentes son del mismo tipo.
				if (!G.getProp("tipo").equals(EE.getProp("tipo"))) {
					System.out.println("Error, deben ser valores del mismo tipo. Encontrado G:"
							+ (String) G.getProp("tipo") + " EE:" + (String) EE.getProp("tipo"));
				}

				nodo.setProp("valor", (Integer) G.getProp("valor") % (Integer) EE.getProp("valor"));
				nodo.setProp("tipo", G.getProp("tipo"));
				break;
			}
		}else {
			//TODO prodN fuera de rango
		}
		return codigo;
	}
}



