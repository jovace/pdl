package pdl.analizadorLexico;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;
import java.util.Stack;

public class analizadorSintactico {
	Stack<String> pila = new Stack<>();
	Map<String, Map<String, ArrayList<String>>> tablaTransicion = new HashMap<>();
	Set<String> terminales = new HashSet<>();
	Set<String> noTerminales = new HashSet<>();
	Map<String, String> tokenaTerminales = new HashMap<>();

	public analizadorSintactico() {
		// Inicializar lista terminales, no terminales y tabla de transicion

		// iniz terminales
		terminales.add("var");//
		terminales.add("id");//
		terminales.add("int");//
		terminales.add("$");
		terminales.add("=");//
		terminales.add("==");//
		terminales.add("(");//
		terminales.add(")");//
		terminales.add("!");//
		terminales.add("bool");//
		terminales.add("string");//
		terminales.add("+");//
		terminales.add("++");
		terminales.add("-");//
		terminales.add("--");
		terminales.add(">");//
		terminales.add("<");//
		terminales.add("<=");//
		terminales.add(">=");//
		terminales.add("{");//
		terminales.add("}");//
		terminales.add("function");//
		terminales.add("return");//
		terminales.add(";");//
		terminales.add(",");//
		terminales.add("*");//
		terminales.add("|=");//
		terminales.add("/");//
		terminales.add("%");//
		terminales.add("&&");//
		terminales.add("||");//
		terminales.add("print");//
		terminales.add("prompt");//
		terminales.add("false");//
		terminales.add("true");//
		terminales.add("for");//
		terminales.add("char");//
		terminales.add("cte_int");
		terminales.add("cte_cadena");
		terminales.add("cte_logica");

		// iniz no terminales
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
		noTerminales.add("SS");
		noTerminales.add("M");

		// iniz tabla
		ArrayList<String> produccionAcierrap = new ArrayList<>();
		ArrayList<String> produccionAbool = new ArrayList<>();
		Map<String, ArrayList<String>> filaA = new HashMap<>();
		produccionAcierrap.add("38");
		filaA.put(")", produccionAcierrap);
		produccionAbool.add("37");
		produccionAbool.add("T");
		produccionAbool.add("id");
		produccionAbool.add("AA");
		filaA.put("bool", produccionAbool);
		filaA.put("int", produccionAbool);
		filaA.put("string", produccionAbool);
		tablaTransicion.put("A", filaA);

		ArrayList<String> produccionAAcierrap = new ArrayList<>();
		ArrayList<String> produccionAAcomaT = new ArrayList<>();
		Map<String, ArrayList<String>> filaAA = new HashMap<>();
		produccionAAcierrap.add("40");
		filaAA.put(")", produccionAAcierrap);
		produccionAAcomaT.add("39");
		produccionAAcomaT.add(",");
		produccionAAcomaT.add("T");
		produccionAAcomaT.add("id");
		produccionAAcomaT.add("AA");
		filaAA.put(",", produccionAAcomaT);
		tablaTransicion.put("AA", filaAA);

		ArrayList<String> produccionBabrep = new ArrayList<>();
		ArrayList<String> produccionBctelog = new ArrayList<>();
		ArrayList<String> produccionBid = new ArrayList<>();
		Map<String, ArrayList<String>> filaB = new HashMap<>();
		produccionBabrep.add("51");
		produccionBabrep.add("(");
		produccionBabrep.add("X");
		produccionBabrep.add(")");
		filaB.put("(", produccionBabrep);
		produccionBctelog.add("52");
		produccionBctelog.add("cte_logica");
		filaB.put("cte_logica", produccionBctelog);
		produccionBid.add("53");
		produccionBid.add("id");
		filaB.put("id", produccionBid);
		tablaTransicion.put("B", filaB);

		ArrayList<String> produccionCSC = new ArrayList<>();
		ArrayList<String> produccionCDC = new ArrayList<>();
		ArrayList<String> produccionClambda = new ArrayList<>();
		Map<String, ArrayList<String>> filaC = new HashMap<>();
		produccionCSC.add("42");
		produccionCSC.add("S");
		produccionCSC.add("C");
		filaC.put("for", produccionCSC);
		filaC.put("id", produccionCSC);
		filaC.put("print", produccionCSC);
		filaC.put("prompt", produccionCSC);
		filaC.put("return", produccionCSC);
		produccionCDC.add("41");
		produccionCDC.add("D");
		produccionCDC.add("C");
		filaC.put("var", produccionCDC);
		produccionClambda.add("43");
		filaC.put("}", produccionClambda);
		tablaTransicion.put("C", filaC);

		ArrayList<String> produccionD = new ArrayList<>();
		Map<String, ArrayList<String>> filaD = new HashMap<>();
		produccionD.add("4");
		produccionD.add("var");
		produccionD.add("T");
		produccionD.add("id");
		produccionD.add("I");
		produccionD.add(";");
		filaD.put("var", produccionD);
		tablaTransicion.put("D", filaD);

		ArrayList<String> produccionE = new ArrayList<>();
		Map<String, ArrayList<String>> filaE = new HashMap<>();
		produccionE.add("11");
		produccionE.add("G");
		produccionE.add("EE");
		filaE.put("!", produccionE);
		filaE.put("(", produccionE);
		filaE.put("cte_cadena", produccionE);
		filaE.put("cte_int", produccionE);
		filaE.put("cte_logica", produccionE);
		filaE.put("id", produccionE);
		tablaTransicion.put("E", filaE);

		ArrayList<String> produccionElambda = new ArrayList<>();
		ArrayList<String> produccionEmod = new ArrayList<>();
		ArrayList<String> produccionEmult = new ArrayList<>();
		ArrayList<String> produccionEdiv = new ArrayList<>();
		ArrayList<String> produccionEsum = new ArrayList<>();
		ArrayList<String> produccionEresta = new ArrayList<>();
		Map<String, ArrayList<String>> filaEE = new HashMap<>();
		produccionElambda.add("23");
		filaEE.put("$", produccionElambda);
		filaEE.put("&&", produccionElambda);
		filaEE.put(")", produccionElambda);
		filaEE.put(";", produccionElambda);
		filaEE.put("<", produccionElambda);
		filaEE.put("<=", produccionElambda);
		filaEE.put(">", produccionElambda);
		filaEE.put(">=", produccionElambda);
		filaEE.put("==", produccionElambda);
		filaEE.put("for", produccionElambda);
		filaEE.put("function", produccionElambda);
		filaEE.put("id", produccionElambda);
		filaEE.put("print", produccionElambda);
		filaEE.put("prompt", produccionElambda);
		filaEE.put("return", produccionElambda);
		filaEE.put("var", produccionElambda);
		filaEE.put("}", produccionElambda);
		produccionEmod.add("26");
		produccionEmod.add("%");
		produccionEmod.add("G");
		produccionEmod.add("EE");
		filaEE.put("%", produccionEmod);
		produccionEmult.add("24");
		produccionEmult.add("*");
		produccionEmult.add("G");
		produccionEmult.add("EE");
		filaEE.put("*", produccionEmult);
		produccionEdiv.add("25");
		produccionEdiv.add("/");
		produccionEdiv.add("G");
		produccionEdiv.add("EE");
		filaEE.put("/", produccionEdiv);
		produccionEsum.add("21");
		produccionEsum.add("+");
		produccionEsum.add("G");
		produccionEsum.add("EE");
		filaEE.put("+", produccionEsum);
		produccionEresta.add("22");
		produccionEresta.add("-");
		produccionEresta.add("G");
		produccionEresta.add("EE");
		filaEE.put("-", produccionEresta);
		tablaTransicion.put("EE", filaEE);

		ArrayList<String> produccionFfunction = new ArrayList<>();
		Map<String, ArrayList<String>> filaF = new HashMap<>();
		produccionFfunction.add("34");
		produccionFfunction.add("function");
		produccionFfunction.add("H");
		produccionFfunction.add("id");
		produccionFfunction.add("(");
		produccionFfunction.add("A");
		produccionFfunction.add(")");
		produccionFfunction.add("{");
		produccionFfunction.add("C");
		produccionFfunction.add("}");
		filaF.put("function", produccionFfunction);
		tablaTransicion.put("F", filaF);

		ArrayList<String> produccionGnoB = new ArrayList<>();
		ArrayList<String> produccionGpXp = new ArrayList<>();
		ArrayList<String> produccionGctecadena = new ArrayList<>();
		ArrayList<String> produccionGcteint = new ArrayList<>();
		ArrayList<String> produccionGctelogica = new ArrayList<>();
		ArrayList<String> produccionGidGG = new ArrayList<>();
		Map<String, ArrayList<String>> filaG = new HashMap<>();
		produccionGnoB.add("14");
		produccionGnoB.add("!");
		produccionGnoB.add("B");
		produccionGpXp.add("12");
		produccionGpXp.add("(");
		produccionGpXp.add("X");
		produccionGpXp.add(")");
		produccionGctecadena.add("16");
		produccionGctecadena.add("cte_cadena");
		produccionGcteint.add("15");
		produccionGcteint.add("cte_int");
		produccionGctelogica.add("17");
		produccionGctelogica.add("cte_logica");
		produccionGidGG.add("13");
		produccionGidGG.add("id");
		produccionGidGG.add("GG");
		filaG.put("!", produccionGnoB);
		filaG.put("(", produccionGpXp);
		filaG.put("cte_cadena", produccionGctecadena);
		filaG.put("cte_int", produccionGcteint);
		filaG.put("cte_logica", produccionGctelogica);
		filaG.put("id", produccionGidGG);
		tablaTransicion.put("G", filaG);

		ArrayList<String> produccionGGlambda = new ArrayList<>();
		ArrayList<String> produccionGGmasmas = new ArrayList<>();
		ArrayList<String> produccionGGmenosmenos = new ArrayList<>();
		Map<String, ArrayList<String>> filaGG = new HashMap<>();
		produccionGGlambda.add("18");
		produccionGGmasmas.add("20");
		produccionGGmasmas.add("++");
		produccionGGmenosmenos.add("19");
		produccionGGmenosmenos.add("--");
		filaGG.put("$", produccionGGlambda);
		filaGG.put("%", produccionGGlambda);
		filaGG.put("&&", produccionGGlambda);
		filaGG.put(")", produccionGGlambda);
		filaGG.put("*", produccionGGlambda);
		filaGG.put("+", produccionGGlambda);
		filaGG.put("-", produccionGGlambda);
		filaGG.put("//", produccionGGlambda);
		filaGG.put(";", produccionGGlambda);
		filaGG.put("<", produccionGGlambda);
		filaGG.put("<=", produccionGGlambda);
		filaGG.put(">=", produccionGGlambda);
		filaGG.put(">", produccionGGlambda);
		filaGG.put("==", produccionGGlambda);
		filaGG.put("for", produccionGGlambda);
		filaGG.put("function", produccionGGlambda);
		filaGG.put("id", produccionGGlambda);
		filaGG.put("print", produccionGGlambda);
		filaGG.put("prompt", produccionGGlambda);
		filaGG.put("return", produccionGGlambda);
		filaGG.put("var", produccionGGlambda);
		filaGG.put("||", produccionGGlambda);
		filaGG.put("}", produccionGGlambda);
		filaGG.put("++", produccionGGmasmas);
		filaGG.put("--", produccionGGmenosmenos);
		tablaTransicion.put("GG", filaGG);

		ArrayList<String> produccionHT = new ArrayList<>();
		ArrayList<String> produccionHlambda = new ArrayList<>();
		Map<String, ArrayList<String>> filaH = new HashMap<>();
		produccionHT.add("35");
		produccionHT.add("T");
		produccionHlambda.add("36");
		filaH.put("bool", produccionHT);
		filaH.put("int", produccionHT);
		filaH.put("string", produccionHT);
		filaH.put("id", produccionHlambda);
		tablaTransicion.put("H", filaH);

		ArrayList<String> produccionIlambda = new ArrayList<>();
		ArrayList<String> produccionIigualE = new ArrayList<>();
		ArrayList<String> produccionIoigualE = new ArrayList<>();
		Map<String, ArrayList<String>> filaI = new HashMap<>();
		produccionIlambda.add("10");
		produccionIigualE.add("8");
		produccionIigualE.add("=");
		produccionIigualE.add("E");
		produccionIoigualE.add("9");
		produccionIoigualE.add("|=");
		produccionIoigualE.add("E");
		filaI.put("$", produccionIlambda);
		filaI.put(";", produccionIlambda);
		filaI.put("for", produccionIlambda);
		filaI.put("function", produccionIlambda);
		filaI.put("id", produccionIlambda);
		filaI.put("print", produccionIlambda);
		filaI.put("prompt", produccionIlambda);
		filaI.put("return", produccionIlambda);
		filaI.put("var", produccionIlambda);
		filaI.put("}", produccionIlambda);
		filaI.put("=", produccionIigualE);
		filaI.put("|=", produccionIoigualE);
		tablaTransicion.put("I", filaI);

		ArrayList<String> produccionJdol = new ArrayList<>();
		ArrayList<String> produccionJSJ = new ArrayList<>();
		ArrayList<String> produccionJFJ = new ArrayList<>();
		ArrayList<String> produccionJDJ = new ArrayList<>();
		Map<String, ArrayList<String>> filaJ = new HashMap<>();
		produccionJdol.add("3");
		produccionJdol.add("$");
		produccionJSJ.add("2");
		produccionJSJ.add("S");
		produccionJSJ.add("J");
		produccionJFJ.add("1");
		produccionJFJ.add("F");
		produccionJFJ.add("J");
		produccionJDJ.add("0");
		produccionJDJ.add("D");
		produccionJDJ.add("J");
		filaJ.put("$", produccionJdol);
		filaJ.put("for", produccionJSJ);
		filaJ.put("id", produccionJSJ);
		filaJ.put("print", produccionJSJ);
		filaJ.put("prompt", produccionJSJ);
		filaJ.put("return", produccionJSJ);
		filaJ.put("function", produccionJFJ);
		filaJ.put("var", produccionJDJ);
		tablaTransicion.put("J", filaJ);

		ArrayList<String> produccionRlamba = new ArrayList<>();
		ArrayList<String> produccionRimplicaX = new ArrayList<>();
		Map<String, ArrayList<String>> filaR = new HashMap<>();
		produccionRlamba.add("50");
		produccionRimplicaX.add("49");
		produccionRimplicaX.add("X");
		filaR.put("!", produccionRimplicaX);
		filaR.put("(", produccionRimplicaX);
		filaR.put(";", produccionRlamba);
		filaR.put("cte_logica", produccionRimplicaX);
		filaR.put("cte_cadena", produccionRimplicaX);
		filaR.put("cte_int", produccionRimplicaX);
		filaR.put("id", produccionRimplicaX);
		tablaTransicion.put("R", filaR);

		Map<String, ArrayList<String>> filaS = new HashMap<>();
		ArrayList<String> produccionSforpDpuntocomaXpuntocomaetc = new ArrayList<>();
		ArrayList<String> produccionSidigualx = new ArrayList<>();
		ArrayList<String> produccionSprintpxp = new ArrayList<>();
		ArrayList<String> produccionSpromtpidp = new ArrayList<>();
		ArrayList<String> produccionSreturnR = new ArrayList<>();
		produccionSforpDpuntocomaXpuntocomaetc.add("48");
		produccionSforpDpuntocomaXpuntocomaetc.add("for");
		produccionSforpDpuntocomaXpuntocomaetc.add("(");
		produccionSforpDpuntocomaXpuntocomaetc.add("D");
		// produccionSforpDpuntocomaXpuntocomaetc.add(";");
		produccionSforpDpuntocomaXpuntocomaetc.add("X");
		produccionSforpDpuntocomaXpuntocomaetc.add(";");
		produccionSforpDpuntocomaXpuntocomaetc.add("SS");
		produccionSforpDpuntocomaXpuntocomaetc.add(")");
		produccionSforpDpuntocomaXpuntocomaetc.add("{");
		produccionSforpDpuntocomaXpuntocomaetc.add("C");
		produccionSforpDpuntocomaXpuntocomaetc.add("}");
		produccionSidigualx.add("44");
		produccionSidigualx.add("SS");
		//produccionSidigualx.add("id");
		//produccionSidigualx.add("=");
		//produccionSidigualx.add("E");
		produccionSidigualx.add(";");
		produccionSprintpxp.add("46");
		produccionSprintpxp.add("print");
		produccionSprintpxp.add("(");
		produccionSprintpxp.add("X");
		produccionSprintpxp.add(")");
		produccionSprintpxp.add(";");
		produccionSpromtpidp.add("47");
		produccionSpromtpidp.add("prompt");
		produccionSpromtpidp.add("(");
		produccionSpromtpidp.add("id");
		produccionSpromtpidp.add(")");
		produccionSpromtpidp.add(";");
		produccionSreturnR.add("45");
		produccionSreturnR.add("return");
		produccionSreturnR.add("R");
		produccionSreturnR.add(";");
		filaS.put("for", produccionSforpDpuntocomaXpuntocomaetc);
		filaS.put("id", produccionSidigualx);
		filaS.put("print", produccionSprintpxp);
		filaS.put("prompt", produccionSpromtpidp);
		filaS.put("return", produccionSreturnR);
		tablaTransicion.put("S", filaS);

		Map<String, ArrayList<String>> filaSS = new HashMap<>();
		ArrayList<String> produccionSSidModificador = new ArrayList<>();
		ArrayList<String> produccionSSprintpxp = new ArrayList<>();
		produccionSSidModificador.add("54");
		produccionSSidModificador.add("id");
		produccionSSidModificador.add("M");
		produccionSSprintpxp.add("55");
		produccionSSprintpxp.add("print");
		produccionSSprintpxp.add("(");
		produccionSSprintpxp.add("X");
		produccionSSprintpxp.add(")");
		filaSS.put("id", produccionSSidModificador);
		filaSS.put("print", produccionSSprintpxp);
		tablaTransicion.put("SS", filaSS);

		Map<String, ArrayList<String>> filaT = new HashMap<>();
		ArrayList<String> produccionTbool = new ArrayList<>();
		ArrayList<String> produccionTint = new ArrayList<>();
		ArrayList<String> produccionTstring = new ArrayList<>();
		produccionTint.add("5");
		produccionTint.add("int");
		produccionTstring.add("6");
		produccionTstring.add("string");
		produccionTbool.add("7");
		produccionTbool.add("bool");
		filaT.put("bool", produccionTbool);
		filaT.put("int", produccionTint);
		filaT.put("string", produccionTstring);
		tablaTransicion.put("T", filaT);
		
		Map<String, ArrayList<String>> filaM = new HashMap<>();
		ArrayList<String> produccionMmasmas = new ArrayList<>();
		ArrayList<String> produccionMmenmen = new ArrayList<>();
		ArrayList<String> produccionMigual = new ArrayList<>();
		produccionMmasmas.add("57");
		produccionMmasmas.add("++");
		produccionMmenmen.add("58");
		produccionMmenmen.add("--");
		produccionMigual.add("59");
		produccionMigual.add("I");
		filaM.put("++", produccionMmasmas);
		filaM.put("--", produccionMmenmen);
		filaM.put("=", produccionMigual);
		tablaTransicion.put("M", filaM);

		ArrayList<String> produccionX = new ArrayList<>();
		Map<String, ArrayList<String>> filaX = new HashMap<>();
		produccionX.add("27");
		produccionX.add("E");
		produccionX.add("XX");
		filaX.put("!", produccionX);
		filaX.put("(", produccionX);
		filaX.put("cte_cadena", produccionX);
		filaX.put("cte_int", produccionX);
		filaX.put("cte_logica", produccionX);
		filaX.put("id", produccionX);
		tablaTransicion.put("X", filaX);

		ArrayList<String> produccionXXyy = new ArrayList<>();
		ArrayList<String> produccionXXm = new ArrayList<>();
		ArrayList<String> produccionXXi = new ArrayList<>();
		ArrayList<String> produccionXXmi = new ArrayList<>();
		ArrayList<String> produccionXXM = new ArrayList<>();
		ArrayList<String> produccionXXMi = new ArrayList<>();
		ArrayList<String> produccionXXoo = new ArrayList<>();
		ArrayList<String> produccionXXlambda = new ArrayList<>();
		Map<String, ArrayList<String>> filaXX = new HashMap<>();
		produccionXXyy.add("35");
		produccionXXyy.add("&&");
		produccionXXyy.add("E");
		filaXX.put("&&", produccionXXyy);
		produccionXXoo.add("34");
		produccionXXoo.add("||");
		produccionXXoo.add("E");
		filaXX.put("||", produccionXXoo);
		produccionXXmi.add("33");
		produccionXXmi.add("<=");
		produccionXXmi.add("E");
		filaXX.put("<=", produccionXXmi);
		produccionXXMi.add("32");
		produccionXXMi.add(">=");
		produccionXXMi.add("E");
		filaXX.put(">=", produccionXXMi);
		produccionXXM.add("31");
		produccionXXM.add(">");
		produccionXXM.add("E");
		filaXX.put(">", produccionXXM);
		produccionXXm.add("30");
		produccionXXm.add("<");
		produccionXXm.add("E");
		filaXX.put("<", produccionXXm);
		produccionXXi.add("28");
		produccionXXi.add("==");
		//produccionXXm.add("=");
		produccionXXi.add("E");
		filaXX.put("==", produccionXXi);
		produccionXXlambda.add("56");
		filaXX.put(")", produccionXXlambda);
		filaXX.put(";", produccionXXlambda);
		tablaTransicion.put("XX", filaXX);

		tokenaTerminales.put("{0,0}", "function");
		tokenaTerminales.put("{0,1}", "var");
		tokenaTerminales.put("{0,2}", "int");
		tokenaTerminales.put("{0,3}", "string");
		tokenaTerminales.put("{0,4}", "bool");
		tokenaTerminales.put("{0,5}", "for");
		tokenaTerminales.put("{0,6}", "cte_logica");
		tokenaTerminales.put("{0,7}", "cte_logica");
		tokenaTerminales.put("{0,8}", "return");
		tokenaTerminales.put("{0,9}", "print");
		tokenaTerminales.put("{0,10}", "prompt");

		tokenaTerminales.put("{1,0}", "+");
		tokenaTerminales.put("{1,1}", "-");
		tokenaTerminales.put("{1,2}", "*");
		tokenaTerminales.put("{1,3}", "/");
		tokenaTerminales.put("{1,4}", "%");
		tokenaTerminales.put("{1,5}", "++");
		tokenaTerminales.put("{1,6}", "--");
		tokenaTerminales.put("{1,7}", "=");
		tokenaTerminales.put("{1,8}", "|=");

		tokenaTerminales.put("{2,0}", "&&");
		tokenaTerminales.put("{2,1}", "||");
		tokenaTerminales.put("{2,2}", "==");
		tokenaTerminales.put("{2,3}", "!");

		tokenaTerminales.put("{3,0}", "==");
		tokenaTerminales.put("{3,1}", "!=");
		tokenaTerminales.put("{3,2}", "<");
		tokenaTerminales.put("{3,3}", ">");
		tokenaTerminales.put("{3,4}", "<=");
		tokenaTerminales.put("{3,5}", ">=");

		tokenaTerminales.put("{4,0}", "(");
		tokenaTerminales.put("{4,1}", ")");
		tokenaTerminales.put("{4,2}", "{");
		tokenaTerminales.put("{4,3}", "}");
		tokenaTerminales.put("{4,4}", ";");
		tokenaTerminales.put("{4,5}", ",");

		tokenaTerminales.put("{9,-1}", "cte_cadena");

		tokenaTerminales.put("{10,-1}", "id");

		// ArrayList<String> tokens=new ArrayList<>();
		// tokens.add("var");
		// tokens.add("int");
		// tokens.add("id");
		// tokens.add("=");
		// tokens.add("id");
		// tokens.add("+");
		// tokens.add("id");
		// tokens.add(";");
		// tokens.add("$");
		// boolean bol=analizar(tokens);
		// System.out.println(bol);
		// Construccion de tabla de transiciones

	}

	/*
	 * Recibimos lista de tokens de analizadorLexico. Lo transformamos en sus
	 * correspondientes simbolos terminales. Analizamos sintacticamente
	 */
	public boolean analizar(ArrayList<Token> listaTokens) {
		ArrayList<Integer> arbol = new ArrayList<>();
		ArrayList<String> codigo = convertirTokenaTerminales(listaTokens);
		pila.clear();
		pila.push("$");
		pila.push("J");
		int puntero = 0;
		
		Nodo nodoActual= new Nodo("ROOT",-1, null);
		nodoActual.addHijo("J",new Nodo("J",-1, nodoActual));
		Arbol asem = new Arbol(nodoActual);

		while (!pila.peek().equals("$")) {
			String X = pila.peek();
			String a = codigo.get(puntero);
			if (terminales.contains(X)) {
				if (X.equals(a)) {
					Nodo hijo;
					while(nodoActual.getHijo(X)==null) {
						nodoActual=nodoActual.getPadre();
					}
					hijo=nodoActual.getHijo(X);
					hijo.setToken(listaTokens.get(puntero));
					
					pila.pop();
					puntero++;
				} else {
					printError("No se puede consumir caracter", X, a, pila, codigo, puntero, arbol, listaTokens);
					return false;
				}
			} else if (noTerminales.contains(X)) {
				pila.pop();

				ArrayList<String> produccion = tablaTransicion.get(X).get(a);
				if (produccion == null) {
					printError("No existe dicha entrada en tabla sintactica", X, a, pila, codigo, puntero, arbol, listaTokens);
					return false;
				}
				
				arbol.add(Integer.parseInt(produccion.get(0)) + 1);
				for (int i = produccion.size() - 1; i > 0; i--) {
					pila.push(produccion.get(i));
				}
				
				Nodo hijo;
				while(nodoActual.getHijo(X)==null) {
					nodoActual=nodoActual.getPadre();
				}
				hijo=nodoActual.getHijo(X);
				
				hijo.setProdN(Integer.parseInt(produccion.get(0))+1);
				for(int i=1;i<produccion.size();i++) {
					hijo.addHijo(produccion.get(i), new Nodo(produccion.get(i), hijo));
				}
				if(produccion.size()>1) {
					nodoActual=hijo;
				}
				
			} else {
				printError("Simbolo no reconocido", X, a, pila, codigo, puntero, arbol, listaTokens);
				return false;
			}
		}
		AnalizadorSemantico as = new AnalizadorSemantico();
		return as.analizar(asem);
	}

	private void printError(String lugar, String X, String a, Stack<String> pila2, ArrayList<String> codigo,
			int puntero, ArrayList<Integer> arbol, ArrayList<Token> listaToken) {
		System.err.println(lugar);
		System.err.println("Cima de pila: " + X.toString());
		System.err.println("Token a leer: " + a.toString());
		System.err.println("Pila: " + pila.toString());
		System.err.println("Archivo leido: \n" + codigo.subList(0, puntero));
		System.err.println("Arbol: \n" + arbol.toString());
		System.err.println("Lista tokens: " + codigo.toString());
		System.out.println("Error "+listaToken.get(puntero).getPosicion()+"\n \n");
	}

	private String getParseArbol(ArrayList<Integer> arbol) {
		String resultado = "Desc ";
		for (Integer entero : arbol) {
			resultado = resultado + entero + " ";
		}
		return resultado;
	}

	private ArrayList<String> convertirTokenaTerminales(ArrayList<Token> listaTokens) {
		ArrayList<String> codigo = new ArrayList<>();
		
		for(Token token : listaTokens) {
			codigo.add(token.getTipo());
		}
		codigo.add("$");
		return codigo;
	}
	
	public void getProducciones() {
		Map<Integer, ArrayList<String>> producciones = new HashMap<>();
		
		for(String fila : this.tablaTransicion.keySet()) {
			for(String columna : this.tablaTransicion.get(fila).keySet()) {
				ArrayList<String> prod = this.tablaTransicion.get(fila).get(columna);
				ArrayList<String> prod2 = new ArrayList<>();
				prod2.add((Integer.parseInt(prod.get(0))+1)+"");
				prod2.add(fila);
				prod2.add("->");
				prod2.addAll(prod);
				producciones.put(Integer.parseInt(prod.get(0)), prod2);
			}
		}
		
		for(Integer i : producciones.keySet()) {
			System.out.println(producciones.get(i));
		}
	}
}
