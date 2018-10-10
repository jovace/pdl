package pdl.analizadorLexico;

public class Token{
	private boolean completo;
	private int type;
	private int indice;
	
	//entero type nos da informacion del token
	//si >0, es la tabla en la que se encuentra el token
	//si -1, es un token incompleto que consume simbolo (por ejemplo un identificador del que seguimos leyendo caracteres)
	//si -2, es un token incompleto que NO CONSUME SIMBOLO (por ejemplo una letra que va despues de un +)

	//Metodo para crear un token incompleto
	public Token(int tipo){
		this.completo=false;
		this.type=tipo;
		this.indice=-1;
	
	}

	//Constructor para crear token completo
	public Token(int type, int indice){
		//Devuelve el tipo y el indice en la tabla (si corresponde, sino -1) del token
		this.completo=true;
		this.type=type;
		this.indice=indice;
	}
	
	public int getType() {
		return this.type;
	}
}
