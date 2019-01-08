package pdl.analizadorLexico;

import java.util.ArrayList;

public class Arbol {
	private Nodo raiz;
	
	public Arbol (Nodo raiz) {
		this.raiz=raiz;
	}
	
	public void printPostorden() {
		this.raiz.printPostorder(0);
	}
}
