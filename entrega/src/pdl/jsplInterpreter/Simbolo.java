package pdl.jsplInterpreter;

import java.util.HashMap;
import java.util.Map;

public class Simbolo {
	private String lexema;
	private String tipo;
	private Object valor;
	private TablaSimbolos tabla;
	private TablaSimbolos tablaPadre;
	private String nodoFuncion;
	private Map<String,String> atributos;
	
	@SuppressWarnings("unchecked")
	public Simbolo(String tipo, String lexema, Object valor, TablaSimbolos tablaPadre) {
		this.tipo=tipo;
		this.lexema=lexema;
		this.valor=valor;
		this.tablaPadre=tablaPadre;
		this.atributos=new HashMap<>();
		if(tipo.equals("function")) {
			this.tabla=new TablaSimbolos(lexema, tablaPadre,-1);
			Nodo F = (Nodo)valor;
			atributos.put("TipoRetorno", (String)F.getProp("tipoRetorno"));
			atributos.put("EtiqFuncion", (String)F.getProp("id"));
			Map<Integer,Nodo> args = (Map<Integer,Nodo>)F.getProp("argsDef");
			this.atributos.put("NumParam", Integer.toString(args.size()));
			for(int i = args.size()-1;i>=0;i--) {
				Nodo argN = args.get(i);
				atributos.put("TipoParam"+Integer.toString(args.size()-i), (String)argN.getProp("tipo"));
				atributos.put("IdParam"+Integer.toString(args.size()-i), (String)argN.getProp("id"));
			}
		}else {
			tabla=null;
		}
		this.atributos.put("Tipo", "'"+this.tipo+"'");
	}
	
	public Simbolo(String tipo, String lexema, Object valor, TablaSimbolos tablaPadre, String nodoFuncion) {
		this.tipo=tipo;
		this.lexema=lexema;
		this.valor=valor;
		this.tablaPadre=tablaPadre;
		this.atributos=new HashMap<>();
		if(tipo.equals("function")) {
			this.tabla=new TablaSimbolos(lexema, tablaPadre,-1);
		}else {
			tabla=null;
		}
		this.nodoFuncion=nodoFuncion;
		this.atributos.put("Tipo", "'"+this.tipo+"'");
	}
	
	public String getTipo() {
		return this.tipo;
	}
	
	public String getLexema() {
		return this.lexema;
	}
	
	public TablaSimbolos getTabla() {
		return this.tabla;
	}
	
	
	public TablaSimbolos getTablaPadre() {
		return this.tablaPadre;
	}
	
	public Object getValor() {
		return this.valor;
	}
	
	public void setValor(Object valor) {
		this.valor=valor;
	}

	public String getNodoFuncion() {
		return nodoFuncion;
	}

	public void setNodoFuncion(String nodoFuncion) {
		this.nodoFuncion = nodoFuncion;
	}
	
	public Map<String, String> getAtributos(){
		return this.atributos;
	}
	
	public String getAtributo(String nombre) {
		if(this.atributos.containsKey(nombre)) {
			return this.atributos.get(nombre);
		}else {
			return "'-'";
		}
	}
	
	public void setAtributo(String nombre, String valor) {
		this.atributos.put(nombre, valor);
	}
}
