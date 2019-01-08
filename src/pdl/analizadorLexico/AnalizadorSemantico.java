//package pdl.analizadorLexico;
//
//import java.util.ArrayList;
//import java.util.HashMap;
//import java.util.Map;
//
//public class AnalizadorSemantico {
//	private ArrayList<Integer> arbolSintactico;
//	private ArrayList<Token> listaTokens;
//	
//	
//	public AnalizadorSemantico (ArrayList<Integer> arbolSintactico, ArrayList<Token> listaTokens) {
//		this.arbolSintactico=arbolSintactico;
//		this.listaTokens=listaTokens;
//	}
//	
//	public void analizar() {
//		//Construir arbol semantico
//		Nodo J = new Nodo();
//		
//		Nodo nodoActual=J;
//		
//		for(Integer prod : this.arbolSintactico) {
//			if(prod==1) {
//				nodoActual.setProdN(1);
//				nodoActual.addHijo("var");
//				nodoActual.addHijo("T");
//				nodoActual.addHijo("id");
//				nodoActual.addHijo("I");
//				nodoActual.addNoTerm("T");
//				nodoActual.addNoTerm("I");
//			}else if(prod==2) {
//				nodoActual.setProdN(prod);
//			}else if(prod==3) {
//				nodoActual.setProdN(prod);
//			}else if(prod==4) {
//				nodoActual.setProdN(prod);
//			}else if(prod==5) {
//				nodoActual.setProdN(prod);
//				nodoActual.addHijo("E");
//				nodoActual.addNoTerm("E");
//			}else if(prod==6) {
//				nodoActual.setProdN(prod);
//				Nodo cte_int=new Nodo();
//				cte_int.setProp("token",null);
//				nodoActual.addHijo("cte_int");
//			}
//				
//				
//				
//				
//			
//			
//		}
//		
//		//Calcular propiedades recursivamente
//		J.calcularProps();
//		
//		//Recorrer arbol con recursividad
//		
//	}
//}
//
//
//
