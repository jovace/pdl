package pdl.analizadorLexico;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class Nodo {
	private Token token = null;
	private String id;
	private HashMap<String,Nodo> hijos  = new HashMap<>();
	private ArrayList<Nodo> hijosL = new ArrayList<>();
	private Map<String,Object> props = new HashMap<>();
	private int prodN;
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
	
	
}


/*
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
	
	public void setProp(String nombre, Object valor) {
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
*/