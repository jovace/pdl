package pdl.jsplInterpreter;

import java.util.HashMap;
import java.util.Map;

public class TablaSimbolos {
	String scope;
	private Map<String, Simbolo> tabla;
	TablaSimbolos tablaPadre;
	
	public TablaSimbolos(String scope, TablaSimbolos tablaPadre) {
		this.tabla=new HashMap<String, Simbolo>();
		this.scope=scope;
		this.tablaPadre=tablaPadre;
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
		this.tabla.put(id, simbolo);
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
}