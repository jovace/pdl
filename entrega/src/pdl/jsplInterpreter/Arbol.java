package pdl.jsplInterpreter;

import java.util.ArrayList;
import java.util.PriorityQueue;
import java.util.Queue;
import java.util.concurrent.PriorityBlockingQueue;

public class Arbol {
	private Nodo raiz;
	
	
	public Arbol (Nodo raiz) {
		this.raiz=raiz;
	}
	
	public void printPostorden() {
		this.raiz.printPostorder(0);
	}

//	public Nodo nextNodo() {
//		nodoActual=raiz;
//		
//		while(!nodoActual.getHijos().values().isEmpty()) {
//			
//		}
//		pila.add(nodoActual);
//		
//		return null;
//	}
	
	public ArrayList<Nodo> getNodosPostorden(){
		ArrayList<Nodo> listaNodosPostorden = new ArrayList<Nodo>();
		
		return this.raiz.getNodosPostOrden(listaNodosPostorden);
	}
}
