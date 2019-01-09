package pdl.analizadorLexico;

public class Simbolo {
	private String lexema;
	private String tipo;
	private Object valor;
	private TablaSimbolos tabla;
	private TablaSimbolos tablaPadre;
	
	public Simbolo(String tipo, String lexema, Object valor, TablaSimbolos tablaPadre) {
		this.tipo=tipo;
		this.lexema=lexema;
		this.valor=valor;
		this.tablaPadre=tablaPadre;
		if(tipo.equals("function")) {
			this.tabla=new TablaSimbolos(lexema, tablaPadre);
		}else {
			tabla=null;
		}
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
}
