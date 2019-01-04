package pdl.analizadorLexico;

import java.util.HashMap;
import java.util.Map;

public class TablaSimbolos {
	String scope;
	private Map<String, Simbolo> tabla;
	
	public TablaSimbolos(String scope) {
		this.tabla=new HashMap<String, Simbolo>();
		this.scope=scope;
	}
	
	public Simbolo getSimbolo(String id) {
		if(tabla.containsKey(id)) {
			return tabla.get(id);
		}else {
			return null;
		}
	}
	
	public void addSimbolo(String id, Simbolo simbolo) {
		this.tabla.put(id, simbolo);
	}
	
	public TablaSimbolos addScope(String nombreScope) {
		this.tabla.put(nombreScope, new Simbolo("function",nombreScope,this));
		return this.tabla.get(nombreScope).getTabla();
	}
}
