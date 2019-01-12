package pdl.analizadorLexico;

import java.util.ArrayList;
import java.util.concurrent.SynchronousQueue;

import pdl.analizadorLexico.analizadorLexico;

public class Main {
	static analizadorLexico lex;
	
	public static void main(String[] args){
//		analizadorSintactico as = new analizadorSintactico();
//		as.getTabla();
//		lex= new analizadorLexico();
//		lex.analizar("var bool t = true; for(var int i=4;i<5;i++){print(i);t=false;} \n");

//		lex.analizar("var bool i = (false || false)&&true; \n");	REVISAR
		
		System.out.println("RESULTADO: "+probarTodo());
		
		
		
//		TablaSimbolos ts = new TablaSimbolos("main");
//		Simbolo s=new Simbolo("cte_int","1",null);
//		ts.addSimbolo("varS", s);
//		ts.addScope("suma");
//		System.out.println();
		

	}
	
	private static boolean probarTodo() {
		boolean todoOk=true;
		
		ArrayList<String> listaPruebas = new ArrayList<>();
		listaPruebas.add("function suma(){var int i = 0;i=i+1; print(i);return;} suma(); \n");
		listaPruebas.add("for(var int i=0;i<5;i=i+1) {print(i);} \t");
		listaPruebas.add("print(2<3); \t");
		listaPruebas.add("var bool i = true; var bool j = false;var bool x=(i&&j); \n");
		listaPruebas.add("var bool i = true; var bool j = false;var bool x=(i||j); \n");
		
		
//		listaPruebas.add("\n \n \n Cadenas con errores sintacticos \n \n");
//		listaPruebas.add("function suma(){i=i+1} \n");
//		listaPruebas.add("prompt(); \n");
		
		for(String test : listaPruebas) {
			lex= new analizadorLexico();
			
			if(test.equals("\n \n \n Cadenas con errores sintacticos \n \n")) {
				System.out.println(test);
			}else {
				System.out.print(test+"-> ");
				if(!lex.analizar(test)) {
					todoOk=false;
				}
			}
			System.out.println("------------------------------------------------\n \n \n");
		}
		
		return todoOk;
	}
}
