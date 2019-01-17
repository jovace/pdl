package pdl.jsplInterpreter;

public class Token{
	private boolean completo;
	private String type;
	private boolean consumeCaracter;
	private String lexema;
	private int linea,caracter;
	
	//entero type nos da informacion del token
	//si >0, es la tabla en la que se encuentra el token
	//si -1, es un token incompleto que consume simbolo (por ejemplo un identificador del que seguimos leyendo caracteres)
	//si -2, es un token incompleto que NO CONSUME SIMBOLO (por ejemplo una letra que va despues de un +)
	//si 9, es una cadena de caracteres
	//si 10, es un identificador
	//si 11, es un numero entero
	
	//Metodo para crear un token incompleto
	public Token(boolean consumeCaracter){
		this.completo=false;
		this.type="";
		this.consumeCaracter=consumeCaracter;
		this.lexema="";
	}

	//Constructor para crear token completo
	public Token(String type){
		this.completo=true;
		this.type=type;
		this.consumeCaracter=true;
		this.lexema="";
	}
	
	public Token(int numero) {
		this.completo=true;
		this.type="cte_int";
		this.consumeCaracter=true;
		this.lexema=String.valueOf(numero);
	}
	
	public Token(String identificador, boolean esIdentificador) {
		if(esIdentificador) {
			if(identificador.equals("true") || identificador.equals("false")) {
				this.type="cte_logica";
			}else {
				this.type="id";
			}
			this.lexema=identificador;
			this.completo=true;
			this.consumeCaracter=true;
		}else {
			this.completo=true;
			this.type="cte_cadena";
			this.consumeCaracter=true;
			this.lexema=identificador;
		}
	}
	
	public boolean consumeCaracter(){
		return this.consumeCaracter;
	}
	
	public void setConsumeCaracter(boolean bool) {
		this.consumeCaracter=bool;
	}
	
	public boolean getConsumeCaracter() {
		return this.consumeCaracter;
	}
	
	public int getNumero() {
		return Integer.valueOf(this.lexema);
	}
	
	public String getLexema() {
		return this.lexema;
	}
	
	public String toString() {
		return "<"+this.type+", "+this.lexema+">";
	}
	
	public String tokenTipo() {
		return "{"+this.type+"}";
	}
	
	public String getTipo() {
		return this.type;
	}
	
	public boolean getCompleto() {return this.completo;}
	
	public void setPosicion(int linea, int caracter) {
		this.linea=linea;
		this.caracter=caracter;
	}
	
	public int getLinea() {
		return this.linea;
	}
	
	public int getCaracter() {
		return this.caracter;
	}
	
	public String getPosicion() {
		return "@"+this.getLinea()+";"+this.getCaracter();
	}
}
