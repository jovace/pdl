package pdl.analizadorLexico;

import java.util.ArrayList;
import java.util.concurrent.SynchronousQueue;

import pdl.analizadorLexico.analizadorLexico;

public class Main {
	static analizadorLexico lex;
	
	public static void main(String[] args){
//		lex= new analizadorLexico();
//		lex.analizar("for(var int i=0;!(i<j);i=i+1) {i=i+1;} \t");
		
		System.out.println("RESULTADO: "+probarTodo());

	}
	
	private static boolean probarTodo() {
		boolean todoOk=true;
		
		ArrayList<String> listaPruebas = new ArrayList<>();
		listaPruebas.add("function suma(){i=i+1; } \n");
		listaPruebas.add("function int suma(){i=i+1; } \n");
		listaPruebas.add("function int suma(){i=i+1;return;} \n");
		listaPruebas.add("function int suma(){i=i+1;return i;} \n");
		listaPruebas.add("function int suma(){i=i+1;return i+1;} \n");
		listaPruebas.add("function int suma(){i=i+1;return i--;} \n");
		listaPruebas.add("function bool suma(){i=i+1;} \n");
		listaPruebas.add("function string suma(){i=i+1;} \n");
		listaPruebas.add("function string suma(int par1, bool par2, string par3){i=i+1;} \n");
		listaPruebas.add("for(var int i=0;i<5;i=i+1) {i=i+1;} \t");
		listaPruebas.add("for(var int i=0;!(i<j);i=i+1) {i=i+1;} \t");
		listaPruebas.add("for(var int i=0;(i<j);i=i+1) {i=i+1;j=i+k;} \t");
		listaPruebas.add("for(var int i=0;true;i=i+1) {i=i+1;j=i+k;} \t");
		listaPruebas.add("print(i); \t");
		listaPruebas.add("prompt(i); \t");
		listaPruebas.add("var bool x=(i&&j); \n");
		//listaPruebas.add("var bool x=(i||j); \n"); //No entra en nuestras asignaciones
		listaPruebas.add("var bool x|=n; \n");
		listaPruebas.add("var bool x|=(n&&i); \n");
		listaPruebas.add("var bool x|=!j; \n");
		listaPruebas.add("var bool x|=(n<i); \n");
		listaPruebas.add("var bool x|=(n>i); \n");
		listaPruebas.add("var bool x|=(n<=i); \n");
		listaPruebas.add("var bool x|=(n>=i); \n");
		listaPruebas.add("var int x=0; \n x++; \n");
		listaPruebas.add("for(var int i=0;i<10;i=i+1){i=i+1;} \n");
		
		listaPruebas.add("\n \n \n Cadenas con errores sintacticos \n \n");
		listaPruebas.add("function suma(){i=i+1} \n");
		listaPruebas.add("prompt(); \n");
		
		for(String test : listaPruebas) {
			lex= new analizadorLexico();
			
			if(test.equals("\n \n \n Cadenas con errores sintacticos \n \n")) {
				System.out.println(test);
			}else {
				System.out.print(test+": ");
				if(!lex.analizar(test)) {
					System.out.println("Error en prueba: \n"+test+"\n \n \n");
					todoOk=false;
				}
			}
		}
		
		return todoOk;
	}
}