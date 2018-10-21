package pdl.analizadorLexico;

public class Token{
	private boolean completo;
	private int type;
	private int indice;
	private boolean consumeCaracter;
	private String lexema;
	
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
		this.type=-1;
		this.indice=-1;
		this.consumeCaracter=consumeCaracter;
		this.lexema="";
	}

	//Constructor para crear token completo
	public Token(int type, int indice){
		//Devuelve el tipo y el indice en la tabla (si corresponde, sino -1) del token
		this.completo=true;
		this.type=type;
		this.indice=indice;
		this.consumeCaracter=true;
		this.lexema="";
	}
	
	public Token(int numero) {
		this.completo=true;
		this.type=11;
		this.indice=numero;
		this.consumeCaracter=true;
		this.lexema="";
	}
	
	public Token(String identificador, boolean esIdentificador) {
		if(esIdentificador) {
			this.completo=true;
			this.type=10;
			this.indice=-1;
			this.consumeCaracter=true;
			this.lexema=identificador;
		}else {
			this.completo=true;
			this.type=9;
			this.indice=-1;
			this.consumeCaracter=true;
			this.lexema=identificador;
		}
	}
	
	public int getType() {
		return this.type;
	}
	
	public boolean consumeCaracter(){
		return this.consumeCaracter;
	}
	
	public void setConsumeCaracter(boolean bool) {
		this.consumeCaracter=bool;
	}
	
	public int getNumero() {
		return this.indice;
	}
	
	public String getLexema() {
		return this.lexema;
	}
	
	public String toString() {
		return "{"+this.type+"; "+this.indice+"; "+this.lexema+"}";
	}
}
