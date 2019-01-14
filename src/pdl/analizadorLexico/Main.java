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
//		listaPruebas.add("function suma(){var int i = 0;i=i+6-3; print(i);return;} suma(); \n");
//		listaPruebas.add("for(var int i=0;i<5;i++) {print(i);} \t");
//		listaPruebas.add("print(2<3); \n");
		listaPruebas.add("var bool i = true; var bool j = false;var bool x=(i&&j); \n");
//		listaPruebas.add("var bool i = true; var bool j = false;var bool x=(i||j); \n");
//		listaPruebas.add("function suma(){var int i = 0;i=i+6-3; print(i);return;} suma(); \n");
//      listaPruebas.add("for(var int i=0;i<5;i++) {print(i);} \t");
//      listaPruebas.add("print(2<3); \n");
//      listaPruebas.add("var bool i = true; var bool j = false;var bool x=(i&&j); \n");
//      listaPruebas.add("var bool i = true; var bool j = false;var bool x=(i||j); \n");
//      listaPruebas.add("var int i =0; i++; i--; print(i); \n");
//      listaPruebas.add("print(2<=1); \n");
//      listaPruebas.add("for(var int i=0; i<100; i++ ){print(i);} \n");
//      listaPruebas.add("for(var int i=1; i<10; i++){var int x=i; print(x);} \n");
//      listaPruebas.add("for(var int i=1; i<10; i++){var int x=i*i; print(x);} \n");
//      listaPruebas.add("for(var int i=1; i<10; i++){for(var int x=1; x<10; x++){var int a =x+i; print(a);}} \n");
//      listaPruebas.add("for(var int i=1; i<10; i++){var int x=i; print(i<5);} \n");
//      listaPruebas.add("var string a = \"holaaa pruebaaaaaa\"; print(a); \n");               
//      listaPruebas.add("for(var int i=0; i<10; i++){var string x=\"holaaa pruebaaaaaa\";print(x);} \n");               
//      listaPruebas.add("var int x=0; var int y=1; var int a=0; a=(x+3); print(a); \n");
//      listaPruebas.add("var int x=0; var int y=1; var int a=0; a=(x+1+y+3); print(a); \n");                           
//      listaPruebas.add("var int x=0; var int y=1; var int a=0; a=x+1+y+3*x; print(a); \n");
//      listaPruebas.add("var int y=0; var int y1=81; y=y1*3+y*4; print(y); \n");               
//      listaPruebas.add("var bool y = false; var bool y1 = true; print(y||y1);  \n");
//      listaPruebas.add("var bool y = true; var bool y1=false; y1|=y; print(y1);  \n");
//      listaPruebas.add("var bool y = false; var bool y1=false; y1|=y; print(y1);  \n");
//      listaPruebas.add("function suma(int i, int a){var int b = i+a;return b;} print(suma(2,3)); \n");
//      listaPruebas.add("function resta(int c, int d){var int x = c-d; return x;} print(resta(2,5)); \n");
//      listaPruebas.add("function int suma(int i, int a){var int b = i+a;return b;} function int resta(int c, int d){var int x = c-d; return x;} var int r=suma(2,4); var int m = resta(6,2); print (suma(r,m)); \n");

//		PRUEBAS QUE NO HA PASADO
//		PROBLEMAS CON LOS PARENTESIS
//		esta de abajo no se que falla

//		listaPruebas.add("function suma(int i, int a){var int b = i+a;return b;} function resta(int c, int d){var int x = c-d; return x;} var int r=suma(2,4); var int m = resta(6,2); print (suma(r,m)); \n");

//		ESTA DE ABAJO AL METER UNA FUNCION DENTRO DE OTRA PETA POR SI SOLAS SUMA Y RESTA VAN
//      listaPruebas.add("function suma(int i, int a){var int b = i+a;return b;} function resta(int c, int d){var int x = c-d; return x;} var int r=(suma(resta(2,5),resta(2,5))); print (r); \n");

//		SI CAMBIAS A ESTO VA a=(x+1+y+3);

//      listaPruebas.add("var int x=0; var int y=1; var int a=0; a=(x+1)+(y+3); print(a); \n");

		
		for(String test : listaPruebas) {
			lex= new analizadorLexico();
			
			if(test.equals("\n \n \n Cadenas con errores sintacticos \n \n")) {
				System.out.println(test);
			}else {
				System.out.print("Test: "+test);
				if(!lex.analizar(test)) {
					todoOk=false;
				}
			}
			System.out.println("------------------------------------------------\n \n \n");
		}
		
		return todoOk;
	}
}
