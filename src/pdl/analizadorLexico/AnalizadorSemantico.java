package pdl.analizadorLexico;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class AnalizadorSemantico {
	private ArrayList<Integer> arbolSintactico;
	private ArrayList<Token> listaTokens;
	
	
	public AnalizadorSemantico (ArrayList<Integer> arbolSintactico, ArrayList<Token> listaTokens) {
		this.arbolSintactico=arbolSintactico;
		this.listaTokens=listaTokens;
	}
	
	public void analizar() {
		//Construir arbol semantico
		Nodo J = new Nodo();
		
		Nodo nodoActual=J;
		
		for(Integer prod : this.arbolSintactico) {
			if(prod==1) {
				nodoActual.setProdN(1);
				nodoActual.addHijo("var");
				nodoActual.addHijo("T");
				nodoActual.addHijo("id");
				nodoActual.addHijo("I");
				nodoActual.addNoTerm("T");
				nodoActual.addNoTerm("I");
			}else if(prod==2) {
				nodoActual.setProdN(prod);
				nodoActual.addHijo("int");
			}
		}
		
		//Calcular propiedades recursivamente
		J.calcularProps();
		
		//Recorrer arbol con recursividad
		
	}
}

class Nodo{
	Map<String,Object> props;
	Map<String,Nodo> hijos;
	ArrayList<Nodo> noTerm;
	int prodN;
	
	public Nodo() {
		this.hijos=new HashMap<String,Nodo>();
		this.props=new HashMap<String,Object>();
		this.noTerm=new ArrayList<Nodo>();		
		this.prodN=-1;
	}
	
	public void addHijo(String nombre) {
		this.hijos.put(nombre, new Nodo());
	}
	
	public void setProdN(int prodN) {
		this.prodN=prodN;
	}
	
	public void addNoTerm(String nombre) {
		this.noTerm.add(this.hijos.get(nombre));
	}
	
	public Object getProp(String nombre) {
		if(this.props.containsKey(nombre)) {
			return this.props.get(nombre);
		}else {
			return null;
		}
	}
	
	private void setProp(String nombre, Object valor) {
		this.props.put(nombre, valor);
	}
	
	public void calcularProps() {
		if(this.prodN==1) {
			setProp("id", ((Token) this.hijos.get("id").getProp("token")).getLexema());
			setProp("tipo", (String) this.hijos.get("T").getProp("tipo"));
			setProp("valor", (Integer) this.hijos.get("I").getProp("valor"));
		}else if(this.prodN==2) {
			setProp("tipo", "double");
		}else if(this.prodN==3) {
			setProp("tipo","int");
		}else if(prodN==4) {
			setProp("valor",0);
		}else if(prodN==5) {
			setProp("valor",(Integer) this.hijos.get("E").getProp("valor"));
		}else if(prodN==6) {
			setProp("valor",((Token) this.hijos.get("cte_int").getProp("token")).getNumero());
		}else if(prodN==7) {
			setProp("valor",((Token) this.hijos.get("cte_int").getProp("token")).getNumero());
		}
	}
}

