package pdl.analizadorLexico;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.SynchronousQueue;

public class AnalizadorSemantico {
	private ArrayList<Integer> arbolSintactico;
	private ArrayList<Token> listaTokens;
	private TablaSimbolos tsMain = new TablaSimbolos("main",null);
	private TablaSimbolos tsActiva = tsMain;
	private boolean hayError=false;
	private ArrayList<Nodo> listaNodosPostorden;
	/*
	 *  tsActiva.addSimbolo((String) nodo.getProp("id"), new Simbolo("function"+((Integer)nodoN).toString(),(String)nodo.getProp("id"),(Integer)nodoN       ,tsActiva));

		tsActiva.addSimbolo((String) nodo.getProp("id"), new Simbolo((String) nodo.getProp("tipo"),         (String)nodo.getProp("id"),nodo.getProp("valor"),tsActiva));
	 */
	
	
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
		listaNodosPostorden = asin.getNodosPostorden();
		for(int i=0;i<listaNodosPostorden.size();i++) {
			String codigo = calcularCodigo(i,listaNodosPostorden);
			codigoFinal+=codigo;
			if(hayError) {
				System.out.println("Errores en tiempo de ejecucion. Tabla de simbolos no mostrada.");
				break;
			}
		}
			
		
		//asin.calcularCodigo();
		
		//Recorrer arbol con recursividad
		
		
		System.out.println(codigoFinal);
		return true;
	}

	@SuppressWarnings("unchecked")
	private String calcularCodigo(int nodoN,ArrayList<Nodo> listaNodosPostorden) {
		Nodo nodo=listaNodosPostorden.get(nodoN);
		String codigo="";
		if(tsActiva.existeSimbolo("$$iniciaFor$$", true)&&nodo.getId().equals("C")){tsActiva.removeSimbolo("$$iniciaFor$$");return "";}
		//TODO transformar todo a ifs
		int prodN = nodo.getProdN();
		if (prodN == 1) { // J -> D J
		} else if (prodN == 2) { // J -> F J
		} else if (prodN == 3) { // J -> S J
		} else if (prodN == 4) { // J -> $
			if(!hayError) {
				System.out.println("\n \n \nPrograma termino con estado 0");
				System.out.println(tsMain.toString());
			}else {
				System.out.println("Errores en tiempo de ejecucion. Tabla de simbolos no mostrada.");
			}
		} else if (prodN == 5) { // D -> var T id I ;
			Nodo T = nodo.getHijo("T");
			Nodo I = nodo.getHijo("I");
			
			if(nodo.getPadre().getProdN()==49) {
				TablaSimbolos tsFor = new TablaSimbolos("tablaFor",tsActiva);
				tsActiva=tsFor;
				tsActiva.addSimbolo("$$iniciaFor$$", null);
			}
	
			
			if(!T.getProp("tipo").equals(I.getProp("tipo"))) {
				System.out.println("Error en linea " + nodo.getHijo("id").getToken().getLinea() +". Valor no es del tipo especificado.");
				hayError=true;
			}else {
				nodo.setProp("tipo", nodo.getHijo("T").getProp("tipo"));
				nodo.setProp("valor", nodo.getHijo("I").getProp("valor"));
				nodo.setProp("id", nodo.getHijo("id").getToken().getLexema());
				if (tsActiva.existeSimbolo((String) nodo.getProp("id"), true)) {
					System.out.println("Error en linea " + nodo.getHijo("id").getToken().getLinea() +". Variable "+nodo.getProp("id").toString()+" ya definida.");
					hayError=true;
				} else {
					tsActiva.addSimbolo((String) nodo.getProp("id"), new Simbolo(
							(String) nodo.getProp("tipo"),
							(String) nodo.getProp("id"), 
							nodo.getProp("valor"),
							tsActiva));
				}
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
			Nodo E = nodo.getHijo("E");
			
			if(!E.getProp("tipo").equals("bool")) {
				System.out.println("Error, se esperaba tipo bool. Encontrado " + (String) E.getProp("tipo")+ ".");
				hayError=true;
			}else {
				nodo.setProp("valor", E.getProp("valor"));
				nodo.setProp("tipo", E.getProp("tipo"));
				nodo.setProp("operacion", "|=");
			}
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
			case 30:
				if(G.getProp("tipo").equals("bool") && G.getProp("tipo").equals(EE.getProp("tipo"))){
					nodo.setProp("tipo", "bool");
					if(EE.getProp("operacion").equals("oo")) {
						Boolean res = new Boolean(((Boolean)G.getProp("valor"))||((Boolean)EE.getProp("valor")));
						nodo.setProp("valor",res);
					}else if(EE.getProp("operacion").equals("yy")) {
						Boolean res = new Boolean(((Boolean)G.getProp("valor"))&&((Boolean)EE.getProp("valor")));
						nodo.setProp("valor",res);
					}
				}else {
					System.out.println("Error, se esperaban valores de tipo bool.");
					hayError=true;
				}
			case 62:
				if(G.getProp("tipo").equals("bool") && G.getProp("tipo").equals(EE.getProp("tipo"))){
					nodo.setProp("tipo", "bool");
					if(EE.getProp("operacion").equals("oo")) {
						Boolean res = new Boolean(((Boolean)G.getProp("valor"))||((Boolean)EE.getProp("valor")));
						nodo.setProp("valor",res);
					}else if(EE.getProp("operacion").equals("yy")) {
						Boolean res = new Boolean(((Boolean)G.getProp("valor"))&&((Boolean)EE.getProp("valor")));
						nodo.setProp("valor",res);
					}
				}else {
					System.out.println("Error, se esperaban valores de tipo bool.");
					hayError=true;
				}
			}
		} else if (prodN == 13) { // G -> ( X )
			nodo.setProp("valor", nodo.getHijo("X").getProp("valor"));
			nodo.setProp("tipo", nodo.getHijo("X").getProp("tipo"));
		} else if (prodN == 14) { //G -> id GG
			Nodo GG = nodo.getHijo("GG");
			Nodo id = nodo.getHijo("id");
			
			
			if(!tsActiva.existeSimbolo(id.getToken().getLexema(), false)) {
				System.out.println("Error en linea "+id.getToken().getLinea()+". Variable o funcion "+id.getToken().getLexema()+" no definida.");
				hayError=true;
			}else{
				String tipo;
				Object valor;
				switch(GG.getProdN()) {
					case 19: //GG -> lambda
						tipo = tsActiva.getSimbolo(id.getToken().getLexema()).getTipo();
						valor = tsActiva.getSimbolo(id.getToken().getLexema()).getValor();
						nodo.setProp("tipo", tipo);
						nodo.setProp("valor", valor);
						break;
					case 20: //GG -> --
						tipo = tsActiva.getSimbolo(id.getToken().getLexema()).getTipo();
						valor = tsActiva.getSimbolo(id.getToken().getLexema()).getValor();
						if(tipo.equals("int")) {
							nodo.setProp("tipo", tipo);
							nodo.setProp("valor", ((Integer)valor)-1);
						}else {
							System.out.println("Warn en linea "+id.getToken().getLinea()+". Operador -- restringido a variables de tipo int.");
							nodo.setProp("tipo", tipo);
							nodo.setProp("valor", valor);
						}
						break;
					case 21: //GG -> ++
						tipo = tsActiva.getSimbolo(id.getToken().getLexema()).getTipo();
						valor = tsActiva.getSimbolo(id.getToken().getLexema()).getValor();
						if(tipo.equals("int")) {
							nodo.setProp("tipo", tipo);
							nodo.setProp("valor", ((Integer)valor)+1);
						}else {
							System.out.println("Warn en linea "+id.getToken().getLinea()+". Operador ++ restringido a variables de tipo int.");
							nodo.setProp("tipo", tipo);
							nodo.setProp("valor", valor);
						}
						break;
					case 61: //GG -> L
						Map<Integer,Nodo> args = (Map<Integer, Nodo>) GG.getProp("args");
						if(GG.hasProp("function") && (Boolean)GG.getProp("function")) {
							Nodo definicionFuncion=(Nodo) tsActiva.getSimbolo(id.getToken().getLexema()).getValor();
							Map<Integer,Nodo> argumentosDef = (Map<Integer, Nodo>) definicionFuncion.getProp("argsDef");
							if(argumentosDef.size()!=args.size()) {
								//ERROR, numero de argumentos no coincide
								hayError=true;
							}else{
								boolean tipoArgumentoError=false;
								for(int i=0;i<argumentosDef.size() && !tipoArgumentoError;i++) {
									if(!argumentosDef.get(i).getProp("tipo").equals(args.get(i).getProp("tipo"))) {
										tipoArgumentoError=true;
										//ERROR, el tipo del parametro i no coicide con la definicion
									}
								}
								if(!tipoArgumentoError) {
									//Argumentos correctos
									//Tipo de nodo G sera tipo de retorno de la funcion
									nodo.setProp("tipo",definicionFuncion.getProp("tipoRetorno"));
									
									//Inicializo una nueva tabla de simbolos para la ejecucion de la funcion
									TablaSimbolos tsFuncion=new TablaSimbolos(id.getToken().getLexema(),tsActiva);
									for(int i=0;i<argumentosDef.size();i++) {
										tsFuncion.addSimbolo(argumentosDef.get(i).getProp("id").toString(), new Simbolo(args.get(i).getProp("tipo").toString(),argumentosDef.get(i).getProp("id").toString(),args.get(i).getProp("valor"),tsFuncion));
									}
									if(tsFuncion.getTablaPadre()==tsActiva) {
										tsActiva=tsFuncion;
									}
									
									Arbol afunc = new Arbol(definicionFuncion.getHijo("C"));
									ArrayList<Nodo> listaNodosFuncPostorden = afunc.getNodosPostorden();
									for(int i=0;i<listaNodosFuncPostorden.size();i++) {
										String codigoFunc = calcularCodigo(i,listaNodosFuncPostorden);
										if(codigoFunc.equals("return")) {
											break;
										}
									}
									nodo.setProp("valor", tsActiva.getSimbolo("$$return$$").getValor());
									tsActiva.removeSimbolo("$$return$$");
								}else {
									//ERROR, algo petó
									hayError=true;
								}
							}
						}
						break;
				}
			}
		} else if (prodN == 15) { //G -> ! B
			Nodo B = nodo.getHijo("B");
			//Comprobar que el valor de B es de tipo Bool
			if(!B.getProp("tipo").equals("bool")) {
				System.out.println("Error, se espera un valor booleano. Se encontro "+B.getProp("tipo").toString());
			}
			if(B.getProp("valor").equals(true)) {
				nodo.setProp("valor", !((Boolean)B.getProp("valor")));
			}else if(B.getProp("valor").equals(false)) {
				nodo.setProp("valor", !((Boolean)B.getProp("valor")));
			}else {
				System.out.println("Error, se esperaba un valor booleano. Se encontro "+B.getProp("valor").toString());
			}
			nodo.setProp("tipo", B.getProp("tipo"));			
		} else if (prodN == 16) { // G -> cte_int
			nodo.setProp("valor", nodo.getHijo("cte_int").getToken().getNumero());
			nodo.setProp("tipo", "int");
		} else if (prodN == 17) { // G -> cte_cadena
			nodo.setProp("valor", nodo.getHijo("cte_cadena").getToken().getLexema());
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
		}else if(prodN==28) { //X-> E XX
			Nodo E = nodo.getHijo("E");
			Nodo XX = nodo.getHijo("XX");
			

			if (!XX.hasProp("valor")) {
				nodo.setProp("tipo", E.getProp("tipo"));
				nodo.setProp("valor", E.getProp("valor"));
			}else if(E.getProp("tipo").equals("int") && E.getProp("tipo").equals(XX.getProp("tipo"))) {
				nodo.setProp("tipo", "bool");
				if(XX.getProp("operacion").equals("menor")) {
					Boolean res = new Boolean((Integer)E.getProp("valor")<(Integer)XX.getProp("valor"));
					nodo.setProp("valor",res);
				}else if(XX.getProp("operacion").equals("mayor")) {
					Boolean res = new Boolean((Integer)E.getProp("valor")>(Integer)XX.getProp("valor"));
					nodo.setProp("valor",res);
				}else if(XX.getProp("operacion").equals("mayorIgual")) {
					Boolean res = new Boolean((Integer)E.getProp("valor")>=(Integer)XX.getProp("valor"));
					nodo.setProp("valor",res);

				}else if(XX.getProp("operacion").equals("menorIgual")) {
					Boolean res = new Boolean((Integer)E.getProp("valor")<=(Integer)XX.getProp("valor"));
					nodo.setProp("valor",res);
				}else if(XX.getProp("operacion").equals("igualigual")) {
					Boolean res = new Boolean(((Integer)E.getProp("valor")).equals((Integer)XX.getProp("valor")));
					nodo.setProp("valor",res);
				}
			}else {
				System.out.println("Error. Tipos no coinciden.");
				hayError=true;
			}
		}else if(prodN==29) {//XX-> == E
			Nodo E = nodo.getHijo("E");
			nodo.setProp("tipo", E.getProp("tipo"));
			nodo.setProp("valor", E.getProp("valor"));
			nodo.setProp("operacion", "igualigual");
		}else if(prodN==30) {//XX-> || E
			Nodo G = nodo.getHijo("G");
			nodo.setProp("tipo", G.getProp("tipo"));
			nodo.setProp("valor", G.getProp("valor"));
			nodo.setProp("operacion", "oo");
		}else if(prodN==31) {//XX-> < E
			Nodo E = nodo.getHijo("E");
			nodo.setProp("tipo", E.getProp("tipo"));
			nodo.setProp("valor", E.getProp("valor"));
			nodo.setProp("operacion", "menor");		
		}else if(prodN==32) {//XX-> > E
			Nodo E = nodo.getHijo("E");
			nodo.setProp("tipo", E.getProp("tipo"));
			nodo.setProp("valor", E.getProp("valor"));
			nodo.setProp("operacion", "mayor");				
		}else if(prodN==33) {//XX-> >= E
			Nodo E = nodo.getHijo("E");
			nodo.setProp("tipo", E.getProp("tipo"));
			nodo.setProp("valor", E.getProp("valor"));
			nodo.setProp("operacion", "mayorIgual");	
		}else if(prodN==34) {//XX-> <= E
			Nodo E = nodo.getHijo("E");
			nodo.setProp("tipo", E.getProp("tipo"));
			nodo.setProp("valor", E.getProp("valor"));
			nodo.setProp("operacion", "menorIgual");
		}else if (prodN == 35) { // F-> function,H, id,(,A,),{,C,}
			Nodo H = nodo.getHijo("H");
			Nodo A = nodo.getHijo("A");
			Nodo C = nodo.getHijo("C");
			
			
			nodo.setProp("argsDef",A.getProp("argsDef"));

		    if(!C.hasProp("tipoRetorno") && H.getProp("tipoRetorno").equals("void")){
		        nodo.setProp("tipoRetorno",H.getProp("tipoRetorno"));
		    }else if(H.getProp("tipoRetorno").equals(C.getProp("tipoRetorno"))) {
		    	nodo.setProp("tipoRetorno",H.getProp("tipoRetorno"));
		    }else{
		        System.out.println("Error. Tipo de retorno de la funcion ("
		                            + (String) H.getProp("tipoRetorno") + ") no coincide con tipo retornado (" + (String) C.getProp("tipoRetorno") +").");
		        hayError=true;
		    }
		    nodo.setProp("id", nodo.getHijo("id").getToken().getLexema());
		    
		    if(!tsActiva.existeSimbolo((String)nodo.getProp("id"), true)) {
		    	//Si funcion no existe en tsActual, la inicializamos
		    	tsActiva.addSimbolo((String) nodo.getProp("id"), new Simbolo("function",(String)nodo.getProp("id"),nodo,tsActiva));
		    }else {
		    	System.out.println("Error, funcion ya esta declara en este ambito.");
		    }
		    
		    if(tsActiva.getTablaPadre()!=null) {
		    	tsActiva=tsActiva.getTablaPadre();
		    }
		}else if (prodN == 36) { // H-> T
			Nodo T = nodo.getHijo("T");
			nodo.setProp("tipoRetorno",T.getProp("tipo"));
		}else if (prodN == 37) { // H-> void
			nodo.setProp("tipoRetorno", "void");
		}else if(prodN==38) {// A -> T id AA
			Nodo T = nodo.getHijo("T");
			Nodo id = nodo.getHijo("id");
			Nodo AA = nodo.getHijo("AA");
			
			nodo.setProp("tipo", T.getProp("tipo"));
			nodo.setProp("id", id.getToken().getLexema());
			
			Map<Integer,Nodo> argsDef=(Map<Integer, Nodo>) AA.getProp("argsDef");
			argsDef.put(argsDef.size(), nodo);
			nodo.setProp("argsDef", argsDef);
			
			String idFuncion=nodo.getPadre().getHijo("id").getToken().getLexema();
			TablaSimbolos tsDefFunc=new TablaSimbolos("$$"+idFuncion+"$$",tsActiva);
			for(int i=0;i<argsDef.size();i++) {
				tsDefFunc.addSimbolo(argsDef.get(i).getProp("id").toString(), new Simbolo(argsDef.get(i).getProp("tipo").toString(),argsDef.get(i).getProp("id").toString(),0,tsDefFunc));
			}
			if(tsDefFunc.getTablaPadre()==tsActiva) {
				tsActiva=tsDefFunc;
			}
		}else if(prodN==39) {// A -> lambda
			nodo.setProp("argsDef", new HashMap<Integer,Nodo>());
			
			String idFuncion=nodo.getPadre().getHijo("id").getToken().getLexema();
			TablaSimbolos tsDefFunc=new TablaSimbolos("$$"+idFuncion+"$$",tsActiva);
			if(tsDefFunc.getTablaPadre()==tsActiva) {
				tsActiva=tsDefFunc;
			}
		}else if(prodN==40) {// AA -> , T id AA
			Nodo T = nodo.getHijo("T");
			Nodo id = nodo.getHijo("id");
			Nodo AA = nodo.getHijo("AA");
			
			nodo.setProp("tipo", T.getProp("tipo"));
			nodo.setProp("id", id.getToken().getLexema());
			
			Map<Integer,Nodo> argsDef=(Map<Integer, Nodo>) AA.getProp("argsDef");
			argsDef.put(argsDef.size(), nodo);
			nodo.setProp("argsDef", argsDef);
		}else if(prodN==41) {// AA -> lambda
			nodo.setProp("argsDef", new HashMap<Integer,Nodo>());
		}else if(prodN==42) {// C -> D C
			if(tsActiva.existeSimbolo("$$iniciaFor$$", true)) {
				tsActiva.removeSimbolo("$$iniciaFor$$");
			}
		}else if (prodN == 43) { //C -> S C
			if(!tsActiva.existeSimbolo("$$iniciaFor$$", true)) {
			    Nodo C = nodo.getHijo("C");
			    Nodo S = nodo.getHijo("S");
			    
			    if(S.hasProp("tipoRetorno")) {
			    	nodo.setProp("tipoRetorno",S.getProp("tipoRetorno"));
			    } 
			}else {
				tsActiva.removeSimbolo("$$iniciaFor$$");
			}
		}else if(prodN==44) {// C -> D C
			if(tsActiva.existeSimbolo("$$iniciaFor$$", true)) {
				tsActiva.removeSimbolo("$$iniciaFor$$");
			}
		}else if (prodN == 45) { //S -> id M ;
		    Nodo id = nodo.getHijo("id");
		    Nodo M = nodo.getHijo("M");
		    
			if(!tsActiva.existeSimbolo(id.getToken().getLexema(), false)) {
				System.out.println("Error en linea "+id.getToken().getLinea()+". Variable "+id.getToken().getLexema()+" no definida.");
				hayError=true;
			}else{
				String tipo;
				Object valor=null;
				switch(M.getProdN()) {
					case 60: //M -> I
						tipo = (String)M.getProp("tipo");
						if(M.hasProp("operacion") && M.getProp("operacion").equals("|=")) {
							Simbolo s = tsActiva.getSimbolo(id.getToken().getLexema());
							if(!s.getTipo().equals("bool")) {
								System.out.println("Error, se esperaba tipo bool. Encontrado " + (String) s.getTipo()+ ".");
								hayError=true;
							}else {
								valor = ((Boolean)s.getValor())||((Boolean)M.getProp("valor"));
							}
						}else {
							valor = M.getProp("valor");
						}
						tsActiva.getSimbolo(id.getToken().getLexema()).setValor(valor);
						nodo.setProp("valor", valor);
						
						nodo.setProp("tipo", tipo);
						break;
					case 59: //M -> --
						tipo = tsActiva.getSimbolo(id.getToken().getLexema()).getTipo();
						valor = tsActiva.getSimbolo(id.getToken().getLexema()).getValor();
						if(tipo.equals("int")) {
							nodo.setProp("tipo", tipo);
							nodo.setProp("valor", ((Integer)valor)-1);
						}else {
							System.out.println("Warn en linea "+id.getToken().getLinea()+". Operador -- restringido a variables de tipo int.");
							nodo.setProp("tipo", tipo);
							nodo.setProp("valor", valor);
						}
						tsActiva.getSimbolo(id.getToken().getLexema()).setValor(nodo.getProp("valor"));
						break;
					case 58: //M -> ++
						tipo = tsActiva.getSimbolo(id.getToken().getLexema()).getTipo();
						valor = tsActiva.getSimbolo(id.getToken().getLexema()).getValor();
						if(tipo.equals("int")) {
							nodo.setProp("tipo", tipo);
							nodo.setProp("valor", ((Integer)valor)+1);
						}else {
							System.out.println("Warn en linea "+id.getToken().getLinea()+". Operador ++ restringido a variables de tipo int.");
							nodo.setProp("tipo", tipo);
							nodo.setProp("valor", valor);
						}
						tsActiva.getSimbolo(id.getToken().getLexema()).setValor(nodo.getProp("valor"));
						break;
					case 69: //M -> L
						Nodo GG=nodo.getHijo("M");
						Map<Integer,Nodo> args = (Map<Integer, Nodo>) GG.getProp("args");
						if(GG.hasProp("function") && (Boolean)GG.getProp("function")) {
							Nodo definicionFuncion=(Nodo) tsActiva.getSimbolo(id.getToken().getLexema()).getValor();
							Map<Integer,Nodo> argumentosDef = (Map<Integer, Nodo>) definicionFuncion.getProp("argsDef");
							if(argumentosDef.size()!=args.size()) {
								//ERROR, numero de argumentos no coincide
								hayError=true;
							}else{
								boolean tipoArgumentoError=false;
								for(int i=0;i<argumentosDef.size() && !tipoArgumentoError;i++) {
									if(!argumentosDef.get(i).getProp("tipo").equals(args.get(i).getProp("tipo"))) {
										tipoArgumentoError=true;
										//ERROR, el tipo del parametro i no coicide con la definicion
									}
								}
								if(!tipoArgumentoError) {
									//Argumentos correctos
									//Tipo de nodo G sera tipo de retorno de la funcion
									nodo.setProp("tipo",definicionFuncion.getProp("tipoRetorno"));
									
									//Inicializo una nueva tabla de simbolos para la ejecucion de la funcion
									TablaSimbolos tsFuncion=new TablaSimbolos(id.getToken().getLexema(),tsActiva);
									for(int i=0;i<argumentosDef.size();i++) {
										tsFuncion.addSimbolo(argumentosDef.get(i).getProp("id").toString(), new Simbolo(args.get(i).getProp("tipo").toString(),argumentosDef.get(i).getProp("id").toString(),args.get(i).getProp("valor"),tsFuncion));
									}
									if(tsFuncion.getTablaPadre()==tsActiva) {
										tsActiva=tsFuncion;
									}
									
									Arbol afunc = new Arbol(definicionFuncion.getHijo("C"));
									ArrayList<Nodo> listaNodosFuncPostorden = afunc.getNodosPostorden();
									for(int i=0;i<listaNodosFuncPostorden.size();i++) {
										String codigoFunc = calcularCodigo(i,listaNodosFuncPostorden);
										if(codigoFunc.equals("return")) {
											break;
										}
									}
									nodo.setProp("valor", tsActiva.getSimbolo("$$return$$").getValor());
									tsActiva.removeSimbolo("$$return$$");
								}else {
									//ERROR, algo petó
									hayError=true;
								}
							}
						}
						break;
					
				}
			}
		    
		}else if (prodN == 46) { //S -> return R ;
		    Nodo R = nodo.getHijo("R");
		    nodo.setProp("tipoRetorno",R.getProp("tipo"));
		    nodo.setProp("valor",R.getProp("valor"));
		    //TODO Como saco el retorno del scope que toca.
		    if(tsActiva.getTablaPadre()!=null) {
		    	TablaSimbolos tablaPadre = tsActiva.getTablaPadre();
		    	tablaPadre.addSimbolo("$$return$$", new Simbolo(nodo.getProp("tipoRetorno").toString(),"$$return$$",nodo.getProp("valor"),tablaPadre));
		    	tsActiva=tablaPadre;
		    	codigo="return";
		    }
		}else if (prodN == 47) { //S -> print ( X ) ;
		    Nodo X = nodo.getHijo("X");
		    nodo.setProp("tipo", X.getProp("tipo"));
		    nodo.setProp("valor", X.getProp("valor"));
		    System.out.println(nodo.getProp("valor").toString());
		}else if (prodN == 48) { //S -> prompt ( id ) ;
		    //TODO prompt
		}else if (prodN == 49) { //S -> for ( D X ; SS ) { C }
		    Nodo D = nodo.getHijo("D");
		    Nodo X = nodo.getHijo("X");
		    Nodo SS = nodo.getHijo("SS");
		    Nodo C = nodo.getHijo("C");
		    
		    Arbol afor = new Arbol(C);
	    	ArrayList<Nodo> listaNodosAFor = afor.getNodosPostorden();
	    	
	    	Arbol apostfor = new Arbol(SS);
	    	ArrayList<Nodo> listaNodosPostFor = apostfor.getNodosPostorden();
		    
		    while((Boolean) X.getProp("valor")) {
		    	TablaSimbolos tsInnerFor = new TablaSimbolos("innerFor",tsActiva);
		    	tsActiva=tsInnerFor;
		    	for(int i=0;i<listaNodosAFor.size();i++) {
		    		calcularCodigo(i,listaNodosAFor);
		    	}
		    	tsActiva=tsActiva.getTablaPadre();
		    	
		    	
		    	for(int i=0;i<listaNodosPostFor.size();i++) {
		    		calcularCodigo(i,listaNodosPostFor);
		    	}
		    	
		    	Arbol acondfor = new Arbol(X);
		    	ArrayList<Nodo> listaacondfor = acondfor.getNodosPostorden();
		    	for(int i=0;i<listaacondfor.size();i++) {
		    		calcularCodigo(i,listaacondfor);
		    	}
		    	
		    }
		    tsActiva=tsActiva.getTablaPadre();
		}else if (prodN == 50) { //R -> X
		    Nodo X = nodo.getHijo("X");
		    nodo.setProp("tipo",X.getProp("tipo"));
		    nodo.setProp("valor", X.getProp("valor"));
		}else if (prodN == 51) { //R -> lambda
			nodo.setProp("tipo", "void");
		}else if (prodN == 52) { //B -> ( X )
		    Nodo X = nodo.getHijo("X");
		    if (X.getProp("tipo").equals("bool")) {
		        nodo.setProp("tipo", X.getProp("tipo"));
		        nodo.setProp("valor", (Boolean)X.getProp("valor"));
		    }
		}else if (prodN == 53) { //B -> cte_logica
			nodo.setProp("valor", Boolean.parseBoolean(nodo.getHijo("cte_logica").getToken().getLexema()));
			nodo.setProp("tipo", "bool");
		}else if (prodN == 54) { //B -> id
			Nodo id = nodo.getHijo("id");
			if(!tsActiva.existeSimbolo(id.getToken().getLexema(), false)) {
				System.out.println("Error en linea "+id.getToken().getLinea()+". Variable "+id.getToken().getLexema()+" no definida.");
				hayError=true;
			}else{
				Simbolo var = tsActiva.getSimbolo(id.getToken().getLexema());
				if(!var.getTipo().equals("bool")) {
					System.out.println("Error en linea "+id.getToken().getLinea()+". Variable "+id.getToken().getLexema()+" no es de tipo bool.");
					hayError=true;
				}else {
					nodo.setProp("tipo",var.getTipo());
					nodo.setProp("valor",(Boolean)var.getValor());
				}
			}
		}else if (prodN==55){ //SS -> id M
			if(!tsActiva.existeSimbolo("$$iniciaFor$$", true)){
				Nodo M = nodo.getHijo("M");
				Nodo id = nodo.getHijo("id");
				if(!tsActiva.existeSimbolo(id.getToken().getLexema(), false)) {
					System.out.println("Error en linea "+id.getToken().getLinea()+". Variable "+id.getToken().getLexema()+" no definida.");
					hayError=true;
				}else{
					String tipo;
					Object valor=null;
					switch(M.getProdN()) {
						case 60: //M -> I
							tipo = (String)M.getProp("tipo");
							if(!tipo.equals("void")) {
								if(M.hasProp("operacion") && M.getProp("operacion").equals("|=")) {
									Simbolo s = tsActiva.getSimbolo(id.getToken().getLexema());
									if(!s.getTipo().equals("bool")) {
										System.out.println("Error, se esperaba tipo bool. Encontrado " + (String) s.getTipo()+ ".");
										hayError=true;
									}else {
										valor = ((Boolean)s.getValor())||((Boolean)M.getProp("valor"));
									}
								}else {
									valor = M.getProp("valor");
								}
								tsActiva.getSimbolo(id.getToken().getLexema()).setValor(valor);
								nodo.setProp("valor", valor);
							}
							nodo.setProp("tipo", tipo);
							break;
						case 59: //M -> --
							tipo = tsActiva.getSimbolo(id.getToken().getLexema()).getTipo();
							valor = tsActiva.getSimbolo(id.getToken().getLexema()).getValor();
							if(tipo.equals("int")) {
								nodo.setProp("tipo", tipo);
								nodo.setProp("valor", ((Integer)valor)-1);
							}else {
								System.out.println("Warn en linea "+id.getToken().getLinea()+". Operador -- restringido a variables de tipo int.");
								nodo.setProp("tipo", tipo);
								nodo.setProp("valor", valor);
							}
							tsActiva.getSimbolo(id.getToken().getLexema()).setValor(nodo.getProp("valor"));
							break;
						case 58: //M -> ++
							tipo = tsActiva.getSimbolo(id.getToken().getLexema()).getTipo();
							valor = tsActiva.getSimbolo(id.getToken().getLexema()).getValor();
							if(tipo.equals("int")) {
								nodo.setProp("tipo", tipo);
								nodo.setProp("valor", ((Integer)valor)+1);
							}else {
								System.out.println("Warn en linea "+id.getToken().getLinea()+". Operador ++ restringido a variables de tipo int.");
								nodo.setProp("tipo", tipo);
								nodo.setProp("valor", valor);
							}
							tsActiva.getSimbolo(id.getToken().getLexema()).setValor(nodo.getProp("valor"));
							break;
						case 69: // M -> L
							Nodo GG = nodo.getHijo("L");
							
							Map<Integer,Nodo> args = (Map<Integer, Nodo>) GG.getProp("args");
							if(GG.hasProp("function") && (Boolean)GG.getProp("function")) {
								Nodo definicionFuncion=(Nodo) tsActiva.getSimbolo(id.getToken().getLexema()).getValor();
								Map<Integer,Nodo> argumentosDef = (Map<Integer, Nodo>) definicionFuncion.getProp("argsDef");
								if(argumentosDef.size()!=args.size()) {
									//ERROR, numero de argumentos no coincide
									hayError=true;
								}else{
									boolean tipoArgumentoError=false;
									for(int i=0;i<argumentosDef.size() && !tipoArgumentoError;i++) {
										if(!argumentosDef.get(i).getProp("tipo").equals(args.get(i).getProp("tipo"))) {
											tipoArgumentoError=true;
											//ERROR, el tipo del parametro i no coicide con la definicion
										}
									}
									if(!tipoArgumentoError) {
										//Argumentos correctos
										//Tipo de nodo G sera tipo de retorno de la funcion
										nodo.setProp("tipo",definicionFuncion.getProp("tipoRetorno"));
										
										//Inicializo una nueva tabla de simbolos para la ejecucion de la funcion
										TablaSimbolos tsFuncion=new TablaSimbolos(id.getToken().getLexema(),tsActiva);
										for(int i=0;i<argumentosDef.size();i++) {
											tsFuncion.addSimbolo(argumentosDef.get(i).getProp("id").toString(), new Simbolo(args.get(i).getProp("tipo").toString(),argumentosDef.get(i).getProp("id").toString(),args.get(i).getProp("valor"),tsFuncion));
										}
										if(tsFuncion.getTablaPadre()==tsActiva) {
											tsActiva=tsFuncion;
										}
										
										Arbol afunc = new Arbol(definicionFuncion.getHijo("C"));
										ArrayList<Nodo> listaNodosFuncPostorden = afunc.getNodosPostorden();
										for(int i=0;i<listaNodosFuncPostorden.size();i++) {
											String codigoFunc = calcularCodigo(i,listaNodosFuncPostorden);
											if(codigoFunc.equals("return")) {
												break;
											}
										}
										nodo.setProp("valor", tsActiva.getSimbolo("$$return$$").getValor());
										tsActiva.removeSimbolo("$$return$$");
									}else {
										//ERROR, algo petó
										hayError=true;
									}
								}
							}
							break;
					}
				}
			}else { //Deshacer el comando en SS
				Nodo M = nodo.getHijo("M");
				Nodo id = nodo.getHijo("id");
				
//				tsActiva.removeSimbolo("$$iniciaFor$$");
//				Integer valor=(Integer) tsActiva.getSimbolo(id.getToken().getLexema()).getValor();
//				
//				if(M.getProdN()==58) { //M -> ++
//					tsActiva.getSimbolo(id.getToken().getLexema()).setValor(valor--);
//				}else if(M.getProdN()==59) { //M -> --
//					tsActiva.getSimbolo(id.getToken().getLexema()).setValor(valor++);
//				}
			}
		}else if (prodN == 56) { //S -> print ( X )
			if(!tsActiva.existeSimbolo("$$iniciaFor$$", true)) {
			    Nodo X = nodo.getHijo("X");
			    nodo.setProp("tipo", X.getProp("tipo"));
			    nodo.setProp("valor", X.getProp("valor"));
			    System.out.println(nodo.getProp("valor").toString());
			}else {
//				tsActiva.removeSimbolo("$$iniciaFor$$");
			}
		}else if(prodN==60){
			Nodo I = nodo.getHijo("I");
			if(I.getProdN()!=11) {
				if(I.hasProp("operacion")) {
					nodo.setProp("operacion", I.getProp("operacion"));
				}
				nodo.setProp("tipo",I.getProp("tipo"));
				nodo.setProp("valor", I.getProp("valor"));
			}else {
				nodo.setProp("tipo", "void");
			}
		}else if(prodN==61) {//GG-> L
			Nodo L = nodo.getHijo("L");
			nodo.setProp("args", L.getProp("args"));
			nodo.setProp("function", (Boolean)true);
		}else if(prodN==62) {//XX-> && E
			Nodo G = nodo.getHijo("G");
			nodo.setProp("tipo", G.getProp("tipo"));
			nodo.setProp("valor", G.getProp("valor"));
			nodo.setProp("operacion", "yy");
		}else if(prodN==64) {//L -> ( AR )
			Nodo AR = nodo.getHijo("AR");
			nodo.setProp("args", AR.getProp("args"));
		}else if(prodN==65) {//AR -> E RR
			Nodo E = nodo.getHijo("E");
			Nodo RR = nodo.getHijo("RR");
			Map<Integer,Nodo> args = (Map<Integer,Nodo>)RR.getProp("args");
			args.put(args.size(), E);
			nodo.setProp("args", args);
		}else if(prodN==66) {//AR -> lambda
			Map<Integer,Nodo> args = new HashMap<Integer,Nodo>();
			nodo.setProp("args", args);
		}else if(prodN==67) {//RR -> RRR
			nodo.setProp("args", nodo.getHijo("RRR").getProp("args"));
		}else if(prodN==68) {//RR -> lambda
			Map<Integer,Nodo> args = new HashMap<Integer,Nodo>();
			nodo.setProp("args", args);
		}else if(prodN==69) {//M-> L
			Nodo L = nodo.getHijo("L");
			nodo.setProp("args", L.getProp("args"));
			nodo.setProp("function", (Boolean)true);
		}else if(prodN==70) {//RRR -> E RR
			Nodo E = nodo.getHijo("E");
			Nodo RR = nodo.getHijo("RR");
			Map<Integer,Nodo> args = (Map<Integer,Nodo>)RR.getProp("args");
			args.put(args.size(), E);
			nodo.setProp("args", args);
		}else {
			//TODO prodN fuera de rango
		}
		return codigo;
	}
}



