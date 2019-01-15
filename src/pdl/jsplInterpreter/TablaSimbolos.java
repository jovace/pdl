package pdl.jsplInterpreter;

import java.util.HashMap;
import java.util.Map;

public class TablaSimbolos {
	String scope;
	int numTabla;
	private Map<String, Simbolo> tabla;
	TablaSimbolos tablaPadre;
	int lastPosicion=0;
	
	public TablaSimbolos(String scope, TablaSimbolos tablaPadre, int numTabla) {
		this.tabla=new HashMap<String, Simbolo>();
		this.scope=scope;
		this.tablaPadre=tablaPadre;
		this.numTabla=numTabla;
	}
	
	public String getScope() {
		return this.scope;
	}
	
	public Simbolo getSimbolo(String id) {
		if(tabla.containsKey(id)) {
			return tabla.get(id);
		}else {
			return tablaPadre.getSimbolo(id);
		}
	}
	
	public void addSimbolo(String id, Simbolo simbolo) {
		if(simbolo==null) return;
		simbolo.setAtributo("Despl", Integer.toString(lastPosicion));
		this.tabla.put(id, simbolo);
		
		if(simbolo.getTipo().equals("int")) {
			lastPosicion+=2;
		}else if(simbolo.getTipo().equals("bool")) {
			lastPosicion++;
		}else if(simbolo.getTipo().equals("string")) {
			lastPosicion+=((String)simbolo.getValor()).length();
		}
		
	}
	
	public boolean removeSimbolo(String id) {
		if(this.tabla.containsKey(id)) {
			this.tabla.remove(id);
			return true;
		}else {
			return false;
		}
	}
	
	public TablaSimbolos addScope(String nombreScope) {
		this.tabla.put(nombreScope, new Simbolo("function",nombreScope,"",this));
		return this.tabla.get(nombreScope).getTabla();
	}
	
	public boolean existeSimbolo(String id, boolean restringirScope) {
		if(this.tabla.keySet().contains(id)) {
			return true;
		}else {
			if(this.tablaPadre==null || restringirScope) {
				return false;
			}else{
				return this.tablaPadre.existeSimbolo(id,restringirScope);
			}
		}
	}
	
	public TablaSimbolos getTablaPadre() {
		return this.tablaPadre;
	}
	
	public int getLastPosicion() {
		return this.lastPosicion;
	}
	
	public String toString() {
		String res="";
		
		res+="Scope: "+this.scope+"\n";
		if(tablaPadre!=null) {
			res+="Tabla padre: "+this.tablaPadre.getScope()+"\n";
		}else {
			res+="Tabla padre: null\n";
		}
		for(String id : tabla.keySet()) {
			Simbolo s = tabla.get(id);
			res+=id +" -> {"+s.getTipo()+", "+s.getLexema()+", "+s.getValor().toString()+"} \n";
		}
		res+="Total variables: "+this.tabla.size();
		
		return res;
	}
	
	public String toStringF() {
		String res="";
		
		res+=this.scope+" # "+this.numTabla+" : \n";
		for(String id : tabla.keySet()) {
			Simbolo s = tabla.get(id);
			res+="* LEXEMA : '"+id+"' \n";
			res+="ATRIBUTOS : \n";
			for(String nomAtrib : s.getAtributos().keySet()) {
				String valorAtrib = s.getAtributo(nomAtrib);
				res+="+ "+nomAtrib + " : "+valorAtrib+"\n";
			}
			res+="------------------- \n";
		}
		
		return res;
		
	}
}
