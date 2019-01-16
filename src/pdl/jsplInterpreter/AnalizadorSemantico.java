package pdl.jsplInterpreter;

import java.io.BufferedWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.Scanner;
import java.util.concurrent.SynchronousQueue;

public class AnalizadorSemantico {
	private FileManager fileManager;
	
	private ArrayList<Integer> arbolSintactico;
	private ArrayList<Token> listaTokens;
	private TablaSimbolos tsMain = new TablaSimbolos("main",null,0);
	private TablaSimbolos tsActiva = tsMain;
	private Map<Integer, TablaSimbolos> tablasSimbolos=new HashMap<>();
	private boolean hayError=false;
	private ArrayList<Nodo> listaNodosPostorden;
	private int tablaN=1;
	
	
	
	public AnalizadorSemantico (ArrayList<Integer> arbolSintactico, ArrayList<Token> listaTokens) {
		this.arbolSintactico=arbolSintactico;
		this.listaTokens=listaTokens;
		this.tablasSimbolos.put(0,tsMain);
	}
	
	public AnalizadorSemantico(FileManager fileManager) {
		this.fileManager=fileManager;
	}
	
	public boolean analizar(Arbol asin) {
		this.tablasSimbolos.put(0,tsMain);
		String codigoFinal="";	 
		
		listaNodosPostorden = asin.getNodosPostorden();
		for(int i=0;i<listaNodosPostorden.size();i++) {
			String codigo = calcularCodigo(i,listaNodosPostorden);
			codigoFinal+=codigo;
			//TODO Eliminar cuando implementados todos los errores
			if(hayError) {
				System.out.println("Errores en tiempo de ejecucion. Tabla de simbolos no mostrada.");
				break;
			}
		}
		
		return true;
	}

	@SuppressWarnings("unchecked")
	private String calcularCodigo(int nodoN,ArrayList<Nodo> listaNodosPostorden) {
		Nodo nodo=listaNodosPostorden.get(nodoN);
		String codigo="";
		if(tsActiva.existeSimbolo("$$iniciaFor$$", true)&&nodo.getId().equals("C")){tsActiva.removeSimbolo("$$iniciaFor$$");return "";}
		if(nodo.hasProp("$$initFor$$")) {
			nodo.removeProp("$$initFor$$");
			return "";
		}
		if(nodo.hasProp("$$initFunc$$")) {
			nodo.removeProp("$$initFunc$$");
			return "";
		}
		
		
		
		int prodN = nodo.getProdN();
		if (prodN == 1) { // J -> D J
		} else if (prodN == 2) { // J -> F J
		} else if (prodN == 3) { // J -> S J
		} else if (prodN == 4) { // J -> $
			if(!hayError) {
				System.out.println("\n \n \nPrograma termino correctamente");
				
				BufferedWriter bw = fileManager.getWriterTS();				
				try {
					for(TablaSimbolos ts : this.tablasSimbolos.values()) {
						bw.write(ts.toStringF() + "\n\n\n");
					}
					bw.flush();
				}catch(IOException ex) {
					ErrorHandler.error(0, 0, 0, "Error durante escritura del archivo de tabla de simbolos.");
				}
			}else {//TODO eliminar una vez todos errores implementados
				System.out.println("Errores en tiempo de ejecucion. Tabla de simbolos no mostrada.");
			}
		} else if (prodN == 5) { // D -> var T id I ;
			Nodo T = nodo.getHijo("T");
			Nodo I = nodo.getHijo("I");
			
			if(nodo.getPadre().getProdN()==49) {
				TablaSimbolos tsFor = new TablaSimbolos("tablaFor",tsActiva,tablaN);
				this.tablasSimbolos.put(tablaN, tsFor);
				tablaN++;
				tsActiva=tsFor;
				tsActiva.addSimbolo("$$iniciaFor$$", null);
			}
	
			
			if(!T.getProp("tipo").equals(I.getProp("tipo"))) {
				ErrorHandler.error(3, 1, nodo.getHijo("id").getToken().getLinea(), "Tipo del valor recibido: "+I.getProp("tipo")+". Tipo declarado: "+T.getProp("tipo")+".");
			}else {
				nodo.setProp("tipo", nodo.getHijo("T").getProp("tipo"));
				nodo.setProp("valor", nodo.getHijo("I").getProp("valor"));
				nodo.setProp("id", nodo.getHijo("id").getToken().getLexema());
				if (tsActiva.existeSimbolo((String) nodo.getProp("id"), true)) {
					System.out.println("Error en linea " + nodo.getHijo("id").getToken().getLinea() +". Variable "+nodo.getProp("id").toString()+" ya definida.");
					ErrorHandler.error(3, 2, nodo.getHijo("id").getToken().getLinea(), "Identificador: "+nodo.getProp("id").toString()+".");
					hayError=true;
				} else {
					tsActiva.addSimbolo((String) nodo.getProp("id"), new Simbolo((String) nodo.getProp("tipo"),(String) nodo.getProp("id"),nodo.getProp("valor"),tsActiva));
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
			Nodo op=nodo.getHijo("|=");
			
			if(!E.getProp("tipo").equals("bool")) {
				ErrorHandler.error(3, 3, op.getToken().getLinea(), "Tipo del valor recibido: "+E.getProp("tipo")+". Tipo esperado: bool.");
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
				// Comprobar que los componentes son del mismo tipo. 
				if (!G.getProp("tipo").equals(EE.getProp("tipo"))) {
					//TODO Linea hardcodeada
					ErrorHandler.error(3, 4, 0, "Tipos del valores recibidos: "+G.getProp("tipo")+" y "+EE.getProp("tipo")+".");
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
					//TODO Linea hardcodeada
					ErrorHandler.error(3, 4, 0, "Tipos del valores recibidos: "+G.getProp("tipo")+" y "+EE.getProp("tipo")+".");
				}

				nodo.setProp("valor", (Integer) G.getProp("valor") - (Integer) EE.getProp("valor"));
				nodo.setProp("tipo", G.getProp("tipo"));
				break;
			case 25:
				// Comprobar que los componentes son del mismo tipo. TODO Posible conversion
				// implicita de int->string? int->boolean?
				if (!G.getProp("tipo").equals(EE.getProp("tipo"))) {
					//TODO Linea hardcodeada
					ErrorHandler.error(3, 4, 0, "Tipos del valores recibidos: "+G.getProp("tipo")+" y "+EE.getProp("tipo")+".");
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
					//TODO Linea hardcodeada
					ErrorHandler.error(3, 4, 0, "Tipos del valores recibidos: "+G.getProp("tipo")+" y "+EE.getProp("tipo")+".");
				}
				
				if((Integer)EE.getProp("valor")==0) {
					//TODO Error dividir cero
				}

				nodo.setProp("valor", (Integer) G.getProp("valor") / (Integer) EE.getProp("valor"));
				nodo.setProp("tipo", G.getProp("tipo"));
				break;
			case 27:
				// Comprobar que los componentes son del mismo tipo.
				if (!G.getProp("tipo").equals(EE.getProp("tipo"))) {
					//TODO Linea hardcodeada
					ErrorHandler.error(3, 4, 0, "Tipos del valores recibidos: "+G.getProp("tipo")+" y "+EE.getProp("tipo")+".");
				}
				if((Integer) EE.getProp("valor")==0) {
					//TODO Error dividir por cero
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
					//TODO linea hardcodeada
					ErrorHandler.error(3, 3, 0, "Tipos del valores recibidos: "+G.getProp("tipo")+" y "+EE.getProp("tipo")+". Esperado: bool.");
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
					//TODO linea hardcodeada
					ErrorHandler.error(3, 3, 0, "Tipos del valores recibidos: "+G.getProp("tipo")+" y "+EE.getProp("tipo")+". Esperado: bool.");
				}
			}
		} else if (prodN == 13) { // G -> ( X )
			nodo.setProp("valor", nodo.getHijo("X").getProp("valor"));
			nodo.setProp("tipo", nodo.getHijo("X").getProp("tipo"));
		} else if (prodN == 14) { //G -> id GG
			Nodo GG = nodo.getHijo("GG");
			Nodo id = nodo.getHijo("id");
			
			
			if(!tsActiva.existeSimbolo(id.getToken().getLexema(), false)) {
				ErrorHandler.error(3, 5, id.getToken().getLinea(), "Identificador: "+id.getToken().getLexema()+".");
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
							ErrorHandler.error(3, 3, id.getToken().getLinea(), "Tipo del valor recibido: "+tipo+". Esperado: int.");
						}
						break;
					case 21: //GG -> ++
						tipo = tsActiva.getSimbolo(id.getToken().getLexema()).getTipo();
						valor = tsActiva.getSimbolo(id.getToken().getLexema()).getValor();
						if(tipo.equals("int")) {
							nodo.setProp("tipo", tipo);
							nodo.setProp("valor", ((Integer)valor)+1);
						}else {
							ErrorHandler.error(3, 3, id.getToken().getLinea(), "Tipo del valor recibido: "+tipo+". Esperado: int.");
						}
						break;
					case 61: //GG -> L
						Map<Integer,Nodo> args = (Map<Integer, Nodo>) GG.getProp("args");
						if(GG.hasProp("function") && (Boolean)GG.getProp("function")) {
							Nodo definicionFuncion=(Nodo) tsActiva.getSimbolo(id.getToken().getLexema()).getValor();
							Map<Integer,Nodo> argumentosDef = (Map<Integer, Nodo>) definicionFuncion.getProp("argsDef");
							if(argumentosDef.size()!=args.size()) {
								ErrorHandler.error(3, 6, id.getToken().getLinea(), "Numero de argumentos recibido: "+args.size()+". Esperado: "+argumentosDef.size()+".");
							}else{
								boolean tipoArgumentoError=false;
								for(int i=0;i<argumentosDef.size() && !tipoArgumentoError;i++) {
									if(!argumentosDef.get(i).getProp("tipo").equals(args.get(i).getProp("tipo"))) {
										ErrorHandler.error(3, 7, id.getToken().getLinea(), "Tipo del argumento recibido: "+args.get(i).getProp("tipo")+". "
												+ "Esperado: "+argumentosDef.get(i).getProp("tipo")+". Nombre del argumento: "+argumentosDef.get(i).getProp("id").toString()+".");
									}
								}
								if(!tipoArgumentoError) {
									//Argumentos correctos
									//Tipo de nodo G sera tipo de retorno de la funcion
									nodo.setProp("tipo",definicionFuncion.getProp("tipoRetorno"));
									
									//Inicializo una nueva tabla de simbolos para la ejecucion de la funcion
									TablaSimbolos tsFuncion=new TablaSimbolos(id.getToken().getLexema(),tsActiva,tablaN);
									//this.tablasSimbolos.put(tablaN, tsFuncion);
									//tablaN++;
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
									ErrorHandler.error(3, 0, id.getToken().getLinea(),"");
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
				ErrorHandler.error(3, 3, 0, "Tipo del valor recibido: "+B.getProp("tipo").toString()+". Esperado: bool.");
			}
			if(B.getProp("valor").equals(true)) {
				nodo.setProp("valor", !((Boolean)B.getProp("valor")));
			}else if(B.getProp("valor").equals(false)) {
				nodo.setProp("valor", !((Boolean)B.getProp("valor")));
			}else {
				ErrorHandler.error(3, 3, 0, "Tipo del valor recibido: "+B.getProp("valor").toString()+". Esperado: bool.");
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
		}else if (prodN == 22) {
			Nodo EE1 = nodo.getHijo("EE");
			Nodo G1 = nodo.getHijo("G");
			switch (EE1.getProdN()) {
			case 24: // EE -> lambda
				nodo.setProp("valor", G1.getProp("valor"));
				nodo.setProp("tipo", G1.getProp("tipo"));
				break;
			case 22: // EE -> + G EE
				// Comprobar que los componentes son del mismo tipo.
				if (!G1.getProp("tipo").equals(EE1.getProp("tipo"))) {
					//TODO Linea hardcodeada
					ErrorHandler.error(3, 4, 0, "Tipos del valores recibidos: "+G1.getProp("tipo")+" y "+EE1.getProp("tipo")+".");
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
					//TODO Linea hardcodeada
					ErrorHandler.error(3, 4, 0, "Tipos del valores recibidos: "+G1.getProp("tipo")+" y "+EE1.getProp("tipo")+".");
				}

				nodo.setProp("valor", (Integer) G1.getProp("valor") - (Integer) EE1.getProp("valor"));
				nodo.setProp("tipo", G1.getProp("tipo"));
				break;
			case 25:
				// Comprobar que los componentes son del mismo tipo. TODO Posible conversion
				// implicita de int->string? int->boolean?
				if (!G1.getProp("tipo").equals(EE1.getProp("tipo"))) {
					//TODO Linea hardcodeada
					ErrorHandler.error(3, 4, 0, "Tipos del valores recibidos: "+G1.getProp("tipo")+" y "+EE1.getProp("tipo")+".");
				}

				// Dependiendo del tipo tendremos una operacion u otra
				switch ((String) G1.getProp("tipo")) {
				case "int":
					nodo.setProp("valor", (Integer) G1.getProp("valor") * (Integer) EE1.getProp("valor"));
					break;
				case "bool":
					nodo.setProp("valor", (Boolean) G1.getProp("valor") && (Boolean) EE1.getProp("valor"));
					break;
				default:
					//TODO Tipo esperado int o boolean
				}
				nodo.setProp("tipo", G1.getProp("tipo"));
				break;
			case 26:
				// Comprobar que los componentes son del mismo tipo.
				if (!G1.getProp("tipo").equals(EE1.getProp("tipo"))) {
					//TODO Linea hardcodeada
					ErrorHandler.error(3, 4, 0, "Tipos del valores recibidos: "+G1.getProp("tipo")+" y "+EE1.getProp("tipo")+".");
				}

				nodo.setProp("valor", (Integer) G1.getProp("valor") / (Integer) EE1.getProp("valor"));
				nodo.setProp("tipo", G1.getProp("tipo"));
				break;
			case 27:
				// Comprobar que los componentes son del mismo tipo.
				if (!G1.getProp("tipo").equals(EE1.getProp("tipo"))) {
					//TODO Linea hardcodeada
					ErrorHandler.error(3, 4, 0, "Tipos del valores recibidos: "+G1.getProp("tipo")+" y "+EE1.getProp("tipo")+".");
				}
				if((Integer) EE1.getProp("valor")==0) {
					//TODO Error dividir por cero
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
				if (!G.getProp("tipo").equals(EE.getProp("tipo"))) {
					//TODO Linea hardcodeada
					ErrorHandler.error(3, 4, 0, "Tipos del valores recibidos: "+G.getProp("tipo")+" y "+EE.getProp("tipo")+".");
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
				//TODO comprobar que son tipo int
				if (!G.getProp("tipo").equals(EE.getProp("tipo"))) {
					//TODO Linea hardcodeada
					ErrorHandler.error(3, 4, 0, "Tipos del valores recibidos: "+G.getProp("tipo")+" y "+EE.getProp("tipo")+".");
				}

				nodo.setProp("valor", (Integer) G.getProp("valor") - (Integer) EE.getProp("valor"));
				nodo.setProp("tipo", G.getProp("tipo"));
				break;
			case 25:
				// Comprobar que los componentes son del mismo tipo. 
				if (!G.getProp("tipo").equals(EE.getProp("tipo"))) {
					//TODO Linea hardcodeada
					ErrorHandler.error(3, 4, 0, "Tipos del valores recibidos: "+G.getProp("tipo")+" y "+EE.getProp("tipo")+".");
				}

				// Dependiendo del tipo tendremos una operacion u otra
				switch ((String) G.getProp("tipo")) {
				case "int":
					nodo.setProp("valor", (Integer) G.getProp("valor") * (Integer) EE.getProp("valor"));
					break;
				case "bool":
					nodo.setProp("valor", (Boolean) G.getProp("valor") && (Boolean) EE.getProp("valor"));
					break;
				default:
					//TODO Error esperado bool o int
				}
				nodo.setProp("tipo", G.getProp("tipo"));
				break;
			case 26:
				// Comprobar que los componentes son del mismo tipo.
				if (!G.getProp("tipo").equals(EE.getProp("tipo"))) {
					//TODO Linea hardcodeada
					ErrorHandler.error(3, 4, 0, "Tipos del valores recibidos: "+G.getProp("tipo")+" y "+EE.getProp("tipo")+".");
				}
				if((Integer) EE.getProp("valor")==0) {
					//TODO Error dividir por cero
				}

				nodo.setProp("valor", (Integer) G.getProp("valor") / (Integer) EE.getProp("valor"));
				nodo.setProp("tipo", G.getProp("tipo"));
				break;
			case 27:
				// Comprobar que los componentes son del mismo tipo.
				if (!G.getProp("tipo").equals(EE.getProp("tipo"))) {
					//TODO Linea hardcodeada
					ErrorHandler.error(3, 4, 0, "Tipos del valores recibidos: "+G.getProp("tipo")+" y "+EE.getProp("tipo")+".");
				}
				if((Integer) EE.getProp("valor")==0) {
					//TODO Error dividir por cero
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
				if (!G.getProp("tipo").equals(EE.getProp("tipo"))) {
					//TODO Linea hardcodeada
					ErrorHandler.error(3, 4, 0, "Tipos del valores recibidos: "+G.getProp("tipo")+" y "+EE.getProp("tipo")+".");
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
				//TODO comprobar que son tipo int
				if (!G.getProp("tipo").equals(EE.getProp("tipo"))) {
					//TODO Linea hardcodeada
					ErrorHandler.error(3, 4, 0, "Tipos del valores recibidos: "+G.getProp("tipo")+" y "+EE.getProp("tipo")+".");
				}

				nodo.setProp("valor", (Integer) G.getProp("valor") - (Integer) EE.getProp("valor"));
				nodo.setProp("tipo", G.getProp("tipo"));
				break;
			case 25:
				// Comprobar que los componentes son del mismo tipo. 
				if (!G.getProp("tipo").equals(EE.getProp("tipo"))) {
					//TODO Linea hardcodeada
					ErrorHandler.error(3, 4, 0, "Tipos del valores recibidos: "+G.getProp("tipo")+" y "+EE.getProp("tipo")+".");
				}

				// Dependiendo del tipo tendremos una operacion u otra
				switch ((String) G.getProp("tipo")) {
				case "int":
					nodo.setProp("valor", (Integer) G.getProp("valor") * (Integer) EE.getProp("valor"));
					break;
				case "bool":
					nodo.setProp("valor", (Boolean) G.getProp("valor") && (Boolean) EE.getProp("valor"));
					break;
				default:
					//TODO Error esperado bool o int
				}
				nodo.setProp("tipo", G.getProp("tipo"));
				break;
			case 26:
				// Comprobar que los componentes son del mismo tipo.
				if (!G.getProp("tipo").equals(EE.getProp("tipo"))) {
					//TODO Linea hardcodeada
					ErrorHandler.error(3, 4, 0, "Tipos del valores recibidos: "+G.getProp("tipo")+" y "+EE.getProp("tipo")+".");
				}
				if((Integer) EE.getProp("valor")==0) {
					//TODO Error dividir por cero
				}

				nodo.setProp("valor", (Integer) G.getProp("valor") / (Integer) EE.getProp("valor"));
				nodo.setProp("tipo", G.getProp("tipo"));
				break;
			case 27:
				// Comprobar que los componentes son del mismo tipo.
				if (!G.getProp("tipo").equals(EE.getProp("tipo"))) {
					//TODO Linea hardcodeada
					ErrorHandler.error(3, 4, 0, "Tipos del valores recibidos: "+G.getProp("tipo")+" y "+EE.getProp("tipo")+".");
				}
				if((Integer) EE.getProp("valor")==0) {
					//TODO Error dividir por cero
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
				if (!G.getProp("tipo").equals(EE.getProp("tipo"))) {
					//TODO Linea hardcodeada
					ErrorHandler.error(3, 4, 0, "Tipos del valores recibidos: "+G.getProp("tipo")+" y "+EE.getProp("tipo")+".");
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
				//TODO comprobar que son tipo int
				if (!G.getProp("tipo").equals(EE.getProp("tipo"))) {
					//TODO Linea hardcodeada
					ErrorHandler.error(3, 4, 0, "Tipos del valores recibidos: "+G.getProp("tipo")+" y "+EE.getProp("tipo")+".");
				}

				nodo.setProp("valor", (Integer) G.getProp("valor") - (Integer) EE.getProp("valor"));
				nodo.setProp("tipo", G.getProp("tipo"));
				break;
			case 25:
				// Comprobar que los componentes son del mismo tipo. 
				if (!G.getProp("tipo").equals(EE.getProp("tipo"))) {
					//TODO Linea hardcodeada
					ErrorHandler.error(3, 4, 0, "Tipos del valores recibidos: "+G.getProp("tipo")+" y "+EE.getProp("tipo")+".");
				}

				// Dependiendo del tipo tendremos una operacion u otra
				switch ((String) G.getProp("tipo")) {
				case "int":
					nodo.setProp("valor", (Integer) G.getProp("valor") * (Integer) EE.getProp("valor"));
					break;
				case "bool":
					nodo.setProp("valor", (Boolean) G.getProp("valor") && (Boolean) EE.getProp("valor"));
					break;
				default:
					//TODO Error esperado bool o int
				}
				nodo.setProp("tipo", G.getProp("tipo"));
				break;
			case 26:
				// Comprobar que los componentes son del mismo tipo.
				if (!G.getProp("tipo").equals(EE.getProp("tipo"))) {
					//TODO Linea hardcodeada
					ErrorHandler.error(3, 4, 0, "Tipos del valores recibidos: "+G.getProp("tipo")+" y "+EE.getProp("tipo")+".");
				}
				if((Integer) EE.getProp("valor")==0) {
					//TODO Error dividir por cero
				}

				nodo.setProp("valor", (Integer) G.getProp("valor") / (Integer) EE.getProp("valor"));
				nodo.setProp("tipo", G.getProp("tipo"));
				break;
			case 27:
				// Comprobar que los componentes son del mismo tipo.
				if (!G.getProp("tipo").equals(EE.getProp("tipo"))) {
					//TODO Linea hardcodeada
					ErrorHandler.error(3, 4, 0, "Tipos del valores recibidos: "+G.getProp("tipo")+" y "+EE.getProp("tipo")+".");
				}
				if((Integer) EE.getProp("valor")==0) {
					//TODO Error dividir por cero
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
				if (!G.getProp("tipo").equals(EE.getProp("tipo"))) {
					//TODO Linea hardcodeada
					ErrorHandler.error(3, 4, 0, "Tipos del valores recibidos: "+G.getProp("tipo")+" y "+EE.getProp("tipo")+".");
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
				//TODO comprobar que son tipo int
				if (!G.getProp("tipo").equals(EE.getProp("tipo"))) {
					//TODO Linea hardcodeada
					ErrorHandler.error(3, 4, 0, "Tipos del valores recibidos: "+G.getProp("tipo")+" y "+EE.getProp("tipo")+".");
				}

				nodo.setProp("valor", (Integer) G.getProp("valor") - (Integer) EE.getProp("valor"));
				nodo.setProp("tipo", G.getProp("tipo"));
				break;
			case 25:
				// Comprobar que los componentes son del mismo tipo. 
				if (!G.getProp("tipo").equals(EE.getProp("tipo"))) {
					//TODO Linea hardcodeada
					ErrorHandler.error(3, 4, 0, "Tipos del valores recibidos: "+G.getProp("tipo")+" y "+EE.getProp("tipo")+".");
				}

				// Dependiendo del tipo tendremos una operacion u otra
				switch ((String) G.getProp("tipo")) {
				case "int":
					nodo.setProp("valor", (Integer) G.getProp("valor") * (Integer) EE.getProp("valor"));
					break;
				case "bool":
					nodo.setProp("valor", (Boolean) G.getProp("valor") && (Boolean) EE.getProp("valor"));
					break;
				default:
					//TODO Error esperado bool o int
				}
				nodo.setProp("tipo", G.getProp("tipo"));
				break;
			case 26:
				// Comprobar que los componentes son del mismo tipo.
				if (!G.getProp("tipo").equals(EE.getProp("tipo"))) {
					//TODO Linea hardcodeada
					ErrorHandler.error(3, 4, 0, "Tipos del valores recibidos: "+G.getProp("tipo")+" y "+EE.getProp("tipo")+".");
				}
				if((Integer) EE.getProp("valor")==0) {
					//TODO Error dividir por cero
				}

				nodo.setProp("valor", (Integer) G.getProp("valor") / (Integer) EE.getProp("valor"));
				nodo.setProp("tipo", G.getProp("tipo"));
				break;
			case 27:
				// Comprobar que los componentes son del mismo tipo.
				if (!G.getProp("tipo").equals(EE.getProp("tipo"))) {
					//TODO Linea hardcodeada
					ErrorHandler.error(3, 4, 0, "Tipos del valores recibidos: "+G.getProp("tipo")+" y "+EE.getProp("tipo")+".");
				}
				if((Integer) EE.getProp("valor")==0) {
					//TODO Error dividir por cero
				}

				nodo.setProp("valor", (Integer) G.getProp("valor") % (Integer) EE.getProp("valor"));
				nodo.setProp("tipo", G.getProp("tipo"));
				break;
			}
		}else if(prodN==28) { //X-> E XX
			Nodo E = nodo.getHijo("E");
			Nodo XX = nodo.getHijo("XX");
			
			if(nodo.getPadre().getProdN()==49) {
				Nodo SS = nodo.getPadre().getHijo("SS");
				Nodo C = nodo.getPadre().getHijo("C");
				
				C.setPropRecursive("$$initFor$$", null);
				SS.setPropRecursive("$$initFor$$", null);
			}
			

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
				ErrorHandler.error(3, 4, 0, "Tipos del valores recibidos: "+E.getProp("tipo")+" y "+XX.getProp("tipo")+".");
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

//		    if(!C.hasProp("tipoRetorno") && H.getProp("tipoRetorno").equals("void")){
//		        nodo.setProp("tipoRetorno",H.getProp("tipoRetorno"));
//		    }else if(H.getProp("tipoRetorno").equals(C.getProp("tipoRetorno"))) {
		    nodo.setProp("tipoRetorno",H.getProp("tipoRetorno"));
//		    }else{
//		        System.out.println("Error. Tipo de retorno de la funcion ("
//		                            + (String) H.getProp("tipoRetorno") + ") no coincide con tipo retornado (" + (String) C.getProp("tipoRetorno") +").");
//		        hayError=true;
//		    }
		    nodo.setProp("id", nodo.getHijo("id").getToken().getLexema());
		    
		    if(tsActiva.getTablaPadre()!=null) {
		    	tsActiva=tsActiva.getTablaPadre();
		    }
		    
		    if(!tsActiva.existeSimbolo((String)nodo.getProp("id"), true)) {
		    	//Si funcion no existe en tsActual, la inicializamos
		    	tsActiva.addSimbolo((String) nodo.getProp("id"), new Simbolo("function",(String)nodo.getProp("id"),nodo,tsActiva));
		    }else {
		    	ErrorHandler.error(3, 2, nodo.getHijo("id").getToken().getLinea(), "Identificador: "+nodo.getHijo("id").getToken().getLexema()+".");
		    }
		    
		    
		}else if (prodN == 36) { // H-> T
			Nodo T = nodo.getHijo("T");
			nodo.setProp("tipoRetorno",T.getProp("tipo"));
			
			Nodo A = nodo.getPadre().getHijo("A");
			Nodo C = nodo.getPadre().getHijo("C");
			
			C.setPropRecursive("$$initFunc$$", null);
		}else if (prodN == 37) { // H-> void
			nodo.setProp("tipoRetorno", "void");
			
			Nodo C = nodo.getPadre().getHijo("C");
			C.setPropRecursive("$$initFunc$$", null);
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
			TablaSimbolos tsDefFunc=new TablaSimbolos("FUNC "+idFuncion,tsActiva,tablaN);
			this.tablasSimbolos.put(tablaN, tsDefFunc);
			tablaN++;
			for(int i=0;i<argsDef.size();i++) {
				tsDefFunc.addSimbolo(argsDef.get(i).getProp("id").toString(), new Simbolo(argsDef.get(i).getProp("tipo").toString(),argsDef.get(i).getProp("id").toString(),0,tsDefFunc));
			}
			if(tsDefFunc.getTablaPadre()==tsActiva) {
				tsActiva=tsDefFunc;
			}
		}else if(prodN==39) {// A -> lambda
			nodo.setProp("argsDef", new HashMap<Integer,Nodo>());
			
			String idFuncion=nodo.getPadre().getHijo("id").getToken().getLexema();
			TablaSimbolos tsDefFunc=new TablaSimbolos("FUNC "+idFuncion,tsActiva,tablaN);
			this.tablasSimbolos.put(tablaN, tsDefFunc);
			tablaN++;
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
				ErrorHandler.error(3, 5, id.getToken().getLinea(), "Identificador: "+id.getToken().getLexema()+".");
			}else{
				String tipo;
				Object valor=null;
				switch(M.getProdN()) {
					case 60: //M -> I
						tipo = (String)M.getProp("tipo");
						if(M.hasProp("operacion") && M.getProp("operacion").equals("|=")) {
							Simbolo s = tsActiva.getSimbolo(id.getToken().getLexema());
							if(!s.getTipo().equals("bool")) {
								ErrorHandler.error(3, 3, id.getToken().getLinea(), "Tipo del valor recibido: "+s.getTipo()+". Esperado: bool.");
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
							ErrorHandler.error(3, 3, id.getToken().getLinea(), "Tipo del valor recibido: "+tipo+". Esperado: int.");
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
							ErrorHandler.error(3, 3, id.getToken().getLinea(), "Tipo del valor recibido: "+tipo+". Esperado: int.");
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
								ErrorHandler.error(3, 6, id.getToken().getLinea(), "Numero de argumentos recibido: "+args.size()+". Esperado: "+argumentosDef.size()+".");
							}else{
								boolean tipoArgumentoError=false;
								for(int i=0;i<argumentosDef.size() && !tipoArgumentoError;i++) {
									if(!argumentosDef.get(i).getProp("tipo").equals(args.get(i).getProp("tipo"))) {
										ErrorHandler.error(3, 7, id.getToken().getLinea(), "Tipo del argumento recibido: "+args.get(i).getProp("tipo")+". "
												+ "Esperado: "+argumentosDef.get(i).getProp("tipo")+". Nombre del argumento: "+argumentosDef.get(i).getProp("id").toString()+".");
									}
								}
								if(!tipoArgumentoError) {
									//Argumentos correctos
									//Tipo de nodo G sera tipo de retorno de la funcion
									nodo.setProp("tipo",definicionFuncion.getProp("tipoRetorno"));
									
									//Inicializo una nueva tabla de simbolos para la ejecucion de la funcion
									TablaSimbolos tsFuncion=new TablaSimbolos(id.getToken().getLexema(),tsActiva,tablaN);
									//this.tablasSimbolos.put(tablaN, tsFuncion);
									//tablaN++;
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
									ErrorHandler.error(3, 0, id.getToken().getLinea(),"");
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

		    if(tsActiva.getTablaPadre()!=null) {
		    	TablaSimbolos tablaPadre = tsActiva.getTablaPadre();
		    	if(tablaPadre.getScope().equals("tablaFor")||tablaPadre.getScope().equals("innerFor"))tablaPadre=tablaPadre.getTablaPadre();
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
			Nodo id = nodo.getHijo("id");
			
			if(!tsActiva.existeSimbolo(id.getToken().getLexema(), false)) {
				ErrorHandler.error(3, 5, id.getToken().getLinea(), "Identificador: "+id.getToken().getLexema()+".");
			}else{
				Simbolo s = tsActiva.getSimbolo(id.getToken().getLexema());
				if(!s.getTipo().equals("string")) {
					System.err.println("Error, variable "+s.getLexema()+" es del tipo "+s.getTipo()+". Se esperaba tipo string.");
				}else {
					Scanner sc = new Scanner(System.in);
					String cadena = sc.nextLine();
					s.setValor(cadena);
				}
			}
		}else if (prodN == 49) { //S -> for ( D X ; SS ) { C }
		    Nodo D = nodo.getHijo("D");
		    Nodo X = nodo.getHijo("X");
		    Nodo SS = nodo.getHijo("SS");
		    Nodo C = nodo.getHijo("C");
		    
		    Arbol afor = new Arbol(C);
	    	ArrayList<Nodo> listaNodosAFor = afor.getNodosPostorden();
	    	
	    	Arbol apostfor = new Arbol(SS);
	    	ArrayList<Nodo> listaNodosPostFor = apostfor.getNodosPostorden();
		    
	    	int tablaNInnerFor = tablaN;
			tablaN++;
		    while((Boolean) X.getProp("valor")) {
		    	TablaSimbolos tsInnerFor = new TablaSimbolos("innerFor",tsActiva,tablaNInnerFor);
		    	this.tablasSimbolos.put(tablaNInnerFor, tsInnerFor);
		    	tsActiva=tsInnerFor;
		    	for(int i=0;i<listaNodosAFor.size();i++) {
		    		calcularCodigo(i,listaNodosAFor);
		    	}
		    	tsActiva=tsInnerFor.getTablaPadre();//?
		    	
		    	
		    	for(int i=0;i<listaNodosPostFor.size();i++) {
		    		calcularCodigo(i,listaNodosPostFor);
		    	}
		    	
		    	Arbol acondfor = new Arbol(X);
		    	ArrayList<Nodo> listaacondfor = acondfor.getNodosPostorden();
		    	for(int i=0;i<listaacondfor.size();i++) {
		    		calcularCodigo(i,listaacondfor);
		    	}
		    	SS.removePropRecursive("$$initFor$$");
		    	C.removePropRecursive("$$initFor$$");
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
				ErrorHandler.error(3, 5, id.getToken().getLinea(), "Identificador: "+id.getToken().getLexema()+".");
			}else{
				Simbolo var = tsActiva.getSimbolo(id.getToken().getLexema());
				if(!var.getTipo().equals("bool")) {
					ErrorHandler.error(3, 3, id.getToken().getLinea(), "Tipo del valor recibido: "+var.getTipo()+". Esperado: bool.");
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
					ErrorHandler.error(3, 5, id.getToken().getLinea(), "Identificador: "+id.getToken().getLexema()+".");
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
										ErrorHandler.error(3, 3, id.getToken().getLinea(), "Tipo del valor recibido: "+s.getTipo()+". Esperado: bool.");
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
								ErrorHandler.error(3, 3, id.getToken().getLinea(), "Tipo del valor recibido: "+tipo+". Esperado: int.");
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
								ErrorHandler.error(3, 3, id.getToken().getLinea(), "Tipo del valor recibido: "+tipo+". Esperado: int.");
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
									ErrorHandler.error(3, 6, id.getToken().getLinea(), "Numero de argumentos recibido: "+args.size()+". Esperado: "+argumentosDef.size()+".");
								}else{
									boolean tipoArgumentoError=false;
									for(int i=0;i<argumentosDef.size() && !tipoArgumentoError;i++) {
										if(!argumentosDef.get(i).getProp("tipo").equals(args.get(i).getProp("tipo"))) {
											ErrorHandler.error(3, 7, id.getToken().getLinea(), "Tipo del argumento recibido: "+args.get(i).getProp("tipo")+". "
													+ "Esperado: "+argumentosDef.get(i).getProp("tipo")+". Nombre del argumento: "+argumentosDef.get(i).getProp("id").toString()+".");
										}
									}
									if(!tipoArgumentoError) {
										//Argumentos correctos
										//Tipo de nodo G sera tipo de retorno de la funcion
										nodo.setProp("tipo",definicionFuncion.getProp("tipoRetorno"));
										
										//Inicializo una nueva tabla de simbolos para la ejecucion de la funcion
										TablaSimbolos tsFuncion=new TablaSimbolos(id.getToken().getLexema(),tsActiva,tablaN);
										//this.tablasSimbolos.put(tablaN, tsFuncion);
										//tablaN++;
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
			}
		}else if (prodN == 56) { //S -> print ( X )
			if(!tsActiva.existeSimbolo("$$iniciaFor$$", true)) {
			    Nodo X = nodo.getHijo("X");
			    nodo.setProp("tipo", X.getProp("tipo"));
			    nodo.setProp("valor", X.getProp("valor"));
			    System.out.println(nodo.getProp("valor").toString());
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
		}else if(prodN==-1){
			//Recibimos un terminal. No hacer nada.
			//TODO maybe ir actualizando linea actual con este caracter, para cuando no tenga otra opcion coger la linea actual de aqui
		}
		return codigo;
	}
}



