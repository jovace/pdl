package pdl.jsplInterpreter;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.nio.file.Files;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Scanner;
import java.util.Set;
import java.util.concurrent.SynchronousQueue;

import pdl.jsplInterpreter.AnalizadorLexico;

public class Main {
	static AnalizadorLexico lex;
	
	public static void main(String[] args){
//		AnalizadorSintactico asem = new AnalizadorSintactico(null);
//		asem.getProducciones();
//		System.exit(0);
		
		String pathCodigo="entrada/codigo.js";
		String pathTokens="salida/tokens.txt";
		String pathTS="salida/ts.txt";
		String pathParse="salida/parse.txt";
		String pathEntrada="entrada";
		String pathSalida="salida";
		
		Set<String> casosPruebaValidos=new HashSet<String>();
		casosPruebaValidos.add("pruebaGeneral");
		casosPruebaValidos.add("correcto1");
		casosPruebaValidos.add("correcto2");
		casosPruebaValidos.add("correcto3");
		casosPruebaValidos.add("correcto4");
		casosPruebaValidos.add("fallo1");
		
		System.out.println("Seleccione el caso de prueba:");
		System.out.println("-> pruebaGeneral");
		System.out.println("-> correcto1");
		System.out.println("-> correcto2");
		System.out.println("-> correcto3");
		System.out.println("-> correcto4");
		System.out.println("-> correcto5");
		System.out.println("-> fallo1");
		System.out.println("-> fallo2");
		System.out.println("-> fallo3");
		System.out.println("-> fallo4");
		System.out.println("-> fallo5");
		Scanner in = new Scanner(System.in);
		String casoPrueba = in.nextLine();
		if(casoPrueba.equals("")) {
			casoPrueba="pruebaGeneral";
		}else if (!casosPruebaValidos.contains(casoPrueba)) {
			ErrorHandler.error(0, 3, 0, casoPrueba);
		}
		
		FileManager fileManager = new FileManager(pathEntrada, pathSalida, pathCodigo, pathTokens, pathTS, pathParse,casoPrueba);
		lex=new AnalizadorLexico(fileManager);
		lex.analizar(getCodigo(fileManager));
	}
	
	public static String getCodigo(FileManager fm) {
		BufferedReader br = fm.getReaderCodigo();
		
		String codigo="";
		String nuevaLinea;
		try {
			while ((nuevaLinea = br.readLine()) != null) {
			    codigo+=nuevaLinea+"\n";
			 }
		} catch (IOException e) {
			ErrorHandler.error(0, 0, 0, "Error durante lectura de codigo.");
		} 
		
		return codigo;
	}
	
//	private static boolean probarTodo() {
//		boolean todoOk=true;
//		
//		ArrayList<String> listaPruebas = new ArrayList<>();
//		listaPruebas.add("function suma(){var int i = 0;i=i+6-3; print(i);return;} suma(); \n");
//		listaPruebas.add("for(var int i=0;i<5;i++) {print(i);} \t");
//		listaPruebas.add("print(2<3); \n");
//		listaPruebas.add("var bool i = true; var bool j = false;var bool x=(i&&j); \n");
//		listaPruebas.add("var bool i = true; var bool j = false;var bool x=(i||j); \n");
//		listaPruebas.add("function suma(){var int i = 0;i=i+6-3; print(i);return;} suma(); \n");
//		listaPruebas.add("for(var int i=0;i<5;i++) {print(i);} \t");
//		listaPruebas.add("print(2<3); \n");
//		listaPruebas.add("var bool i = true; var bool j = false;var bool x=(i&&j); \n");
//		listaPruebas.add("var bool i = true; var bool j = false;var bool x=(i||j); \n");
//		listaPruebas.add("var int i =0; i++; i--; print(i); \n");
//		listaPruebas.add("print(2<=1); \n");
//		listaPruebas.add("for(var int i=0; i<100; i++ ){print(i);} \n");
//		listaPruebas.add("for(var int i=1; i<10; i++){var int x=i; print(x);} \n");
//		listaPruebas.add("for(var int i=1; i<10; i++){var int x=i*i; print(x);} \n");
//		listaPruebas.add("for(var int i=1; i<10; i++){for(var int x=1; x<10; x++){var int a =x+i; print(a);}} \n");
//		listaPruebas.add("for(var int i=1; i<10; i++){var int x=i; print(i<5);} \n");
//		listaPruebas.add("var string a = \"holaaa pruebaaaaaa\"; print(a); \n");               
//		listaPruebas.add("for(var int i=0; i<10; i++){var string x=\"holaaa pruebaaaaaa\";print(x);} \n");               
//		listaPruebas.add("var int x=0; var int y=1; var int a=0; a=(x+3); print(a); \n");
//		listaPruebas.add("var int x=0; var int y=1; var int a=0; a=(x+1+y+3); print(a); \n");                           
//		listaPruebas.add("var int x=0; var int y=1; var int a=0; a=x+1+y+3*x; print(a); \n");
//		listaPruebas.add("var int y=0; var int y1=81; y=y1*3+y*4; print(y); \n");               
//		listaPruebas.add("var bool y = false; var bool y1 = true; print(y||y1);  \n");
//		listaPruebas.add("var bool y = true; var bool y1=false; y1|=y; print(y1);  \n");
//		listaPruebas.add("var bool y = false; var bool y1=false; y1|=y; print(y1);  \n");
//		listaPruebas.add("function suma(int i, int a){var int b = i+a;return b;} print(suma(2,3)); \n");
//		listaPruebas.add("function resta(int c, int d){var int x = c-d; return x;} print(resta(2,5)); \n");
//		listaPruebas.add("function int suma(int i, int a){var int b = i+a;return b;} function int resta(int c, int d){var int x = c-d; return x;} var int r=suma(2,4); var int m = resta(6,2); print (suma(r,m)); \n");
//		listaPruebas.add("var int x=0; var int y=1; var int a=0; a=(x+1)+(y+3); print(a); \n");		
//
//		
//		for(String test : listaPruebas) {
//			lex= new AnalizadorLexico();
//			
//			if(test.equals("\n \n \n Cadenas con errores sintacticos \n \n")) {
//				System.out.println(test);
//			}else {
//				System.out.print("Test: "+test);
//				if(!lex.analizar(test)) {
//					todoOk=false;
//				}
//			}
//			System.out.println("------------------------------------------------\n \n \n");
//		}
//		
//		return todoOk;
//	}
}
