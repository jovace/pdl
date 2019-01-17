package pdl.jsplInterpreter;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class Nodo {
	private Token token = null;
	private String id;
	private HashMap<String,Nodo> hijos  = new HashMap<>();
	private ArrayList<Nodo> hijosL = new ArrayList<>();
	private Map<String,Object> props = new HashMap<>();
	private int prodN=-1;
	private Nodo padre;
	
	public Nodo(String id, int prodN, Nodo padre) {
		this.id=id;
		this.prodN=prodN;
		this.padre=padre;
	}
	
	public Nodo(String id, Nodo padre) {
		this.id=id;
		this.padre=padre;
	}
	
	public Nodo() {}

	public Token getToken() {
		return token;
	}

	public void setToken(Token token) {
		this.token = token;
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public HashMap<String, Nodo> getHijos() {
		return hijos;
	}

	public void setHijos(HashMap<String, Nodo> hijos) {
		this.hijos = hijos;
	}

	public Map<String, Object> getProps() {
		return props;
	}

	public void setProps(Map<String, Object> props) {
		this.props = props;
	}

	public int getProdN() {
		return prodN;
	}

	public void setProdN(int prodN) {
		this.prodN = prodN;
	}
	
	public void addHijo(String id,Nodo hijo) {
		this.hijos.put(id,hijo);
		this.hijosL.add(hijo);
	}
	
	public Nodo getHijo(String id) {
		return this.hijos.get(id);
	}
	
	public String getInfo() {
		String res="";
		res="Nodo: "+this.id+" . Hijos: ";
		for(Nodo n : this.hijos.values()) {
			res+=n.getId()+ " ";
		}
		return res;
	}
	
	public void printPostorder(int depth) {
		for(Nodo n : hijosL) {
			n.printPostorder(depth+1);
		}
		
		System.out.print(depth+":"+this.id+ " ");
	}
	
	public Nodo getPadre() {
		return this.padre;
	}
	
	public void setPadre(Nodo padre) {
		this.padre=padre;
	}

	public ArrayList<Nodo> getNodosPostOrden(ArrayList<Nodo> listaNodosPostorden) {
		for(Nodo n : hijosL) {
			n.getNodosPostOrden(listaNodosPostorden);
		}		
		listaNodosPostorden.add(this);
		
		return listaNodosPostorden;
	}
	
	public void setProp(String nombre, Object valor) {
		this.props.put(nombre, valor);
	}
	
	public Object getProp(String nombre) {
		return this.props.get(nombre);
	}
	
	public boolean hasProp(String nombre) {
		return this.props.containsKey(nombre);
	}
	
	public boolean removeProp(String nombre) {
		if(this.hasProp(nombre)) {
			this.props.remove(nombre);
			return true;
		}else {
			return false;
		}
	}
	
	public void setPropRecursive(String nombre, Object valor) {
		this.setProp(nombre, valor);
		
		for(Nodo nodo : hijos.values()) {
			nodo.setPropRecursive(nombre, valor);
		}
	}
	
	public void removePropRecursive(String nombre) {
		this.removeProp(nombre);
		
		for(Nodo nodo : hijos.values()) {
			nodo.removePropRecursive(nombre);
		}
	}
	
	public String toString() {
		String res="";
		
		res+="{id: "+id+"; hijos:[";
		for(int i=0;i<hijosL.size()-1;i++) {
			res+=hijosL.get(i).getId()+", ";
		}
		res+=hijosL.get(hijosL.size()-1).getId();
		res+="]";
				
		return res;
	}
	
}