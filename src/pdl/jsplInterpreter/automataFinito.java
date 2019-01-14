package pdl.jsplInterpreter;

import java.util.ArrayList;
import java.util.Map;

public class automataFinito{
	private int estadoActual;
	ArrayList<Map<Character , Token>> transiciones  = new ArrayList<Map<Character,Token>>();


	public automataFinito(int estadoInicial, ArrayList<Map<Character , Token>> transiciones){
		this.estadoActual=estadoInicial;
		this.transiciones=transiciones;
	}

	public Token nextChar(char c){
		//Si llegamos a estado final devolvemos instancia de Token correspondiente
		//Si todavia no hemos llegado a un estado final, devolvemos una instancia de Token con flag completo=false

		/*
		Utiliza el map de transiciones con indice el estado actual. El mapa va a devolver el token que corresponda.
		*/
		
		return transiciones.get(estadoActual).get(new Character(c));
	}
}
