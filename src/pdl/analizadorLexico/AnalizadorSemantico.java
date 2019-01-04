package pdl.analizadorLexico;

public class AnalizadorSemantico {
	TablaSimbolos scopeActual;
	int stackCount;
	//Map<int,accionSemantica> acciones
	String arbolSintactico;
	
	public AnalizadorSemantico(String arbolSintactico) {
		this.scopeActual=new TablaSimbolos("main");
		int stackCount=0;
		this.arbolSintactico=arbolSintactico;
	}
	
	public void analizar() {
		/*
		 * -Recorrer arbol
		 * -Ir construyendo arbol semantico por profundidad
		 */
	}

}
