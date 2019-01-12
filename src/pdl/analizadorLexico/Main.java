package pdl.analizadorLexico;

import java.util.ArrayList;
import java.util.concurrent.SynchronousQueue;

import pdl.analizadorLexico.analizadorLexico;

public class Main {
	static analizadorLexico lex;
	
	public static void main(String[] args){
//		analizadorSintactico as = new analizadorSintactico();
//		as.getTabla();
		lex= new analizadorLexico();
		lex.analizar("function int suma(int a){return a;} print(suma(4)); \n");

//		lex.analizar("var bool i = (false || false)&&true; \n");	REVISAR
		
//		System.out.println("RESULTADO: "+probarTodo());
		
		
		
//		TablaSimbolos ts = new TablaSimbolos("main");
//		Simbolo s=new Simbolo("cte_int","1",null);
//		ts.addSimbolo("varS", s);
//		ts.addScope("suma");
//		System.out.println();
		

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
		listaPruebas.add("var bool x=(i||j); \n"); //No entra en nuestras asignaciones
		listaPruebas.add("var bool x|=n; \n");
		listaPruebas.add("var bool x|=(n&&i); \n");
		listaPruebas.add("var bool x|=!j; \n");
		listaPruebas.add("var bool x|=(n<i); \n");
		listaPruebas.add("var bool x|=(n>i); \n");
		listaPruebas.add("var bool x|=(n<=i); \n");
		listaPruebas.add("var bool x|=(n>=i); \n");
		listaPruebas.add("var int x=0; \n x++; \n");
		listaPruebas.add("for(var int i=0;i<10;i=i+1){i=i+1;} \n");
		listaPruebas.add("suma(); \n");
		listaPruebas.add("suma(1); \n");
		listaPruebas.add("suma(1,2,3,4); \n");
		listaPruebas.add("suma(true); \n");
		listaPruebas.add("suma(\"jorge\"); \n");
		listaPruebas.add("suma(\"jorge\",2,false); \n");
		listaPruebas.add("suma(\"jorge\",resta(2)); \n");
		listaPruebas.add("suma(id++); \n");
		listaPruebas.add("suma((id+2),!true); \n");
		
		
		listaPruebas.add("\n \n \n Cadenas con errores sintacticos \n \n");
		listaPruebas.add("function suma(){i=i+1} \n");
		listaPruebas.add("prompt(); \n");
		
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
		}
		
		return todoOk;
	}
}
