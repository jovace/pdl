package pdl.analizadorLexico;

import java.util.ArrayList;
import java.util.concurrent.SynchronousQueue;

public class Main {
	static analizadorLexico lex;
	
	public static void main(String[] args){
		lex= new analizadorLexico();
		lex.analizar("function suma(){i=i+1;} \t");
		
		System.out.println("RESULTADO: "+probarTodo());

	}
	
	private static boolean probarTodo() {
		boolean todoOk=true;
		
		ArrayList<String> listaPruebas = new ArrayList<>();
		listaPruebas.add("function suma(){i=i+1;}");
		listaPruebas.add("function int suma(){i=i+1;}");
		listaPruebas.add("function bool suma(){i=i+1;}");
		listaPruebas.add("function string suma(){i=i+1;}");
		listaPruebas.add("function string suma(int par1, bool par2, string par3){i=i+1;}");
		listaPruebas.add("for(var int i=0;i<5;i=i+1) {i=i+1;} \t");
		
		for(String test : listaPruebas) {
			lex= new analizadorLexico();
			if(!lex.analizar(test)) {
				System.out.println("Error en prueba: \n"+test+"\n \n \n");
				todoOk=false;
			}
		}
		
		return todoOk;
	}
}
