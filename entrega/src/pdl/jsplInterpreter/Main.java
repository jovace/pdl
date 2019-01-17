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
		String pathCodigo="entrada/codigo.js";
		String pathTokens="salida/tokens.txt";
		String pathTS="salida/ts.txt";
		String pathParse="salida/parse.txt";
		String pathEntrada="entrada";
		String pathSalida="salida";
		
		Set<String> casosPruebaValidos=new HashSet<String>();
		casosPruebaValidos.add("correcto1");
		casosPruebaValidos.add("correcto2");
		casosPruebaValidos.add("correcto3");
		casosPruebaValidos.add("correcto4");
		casosPruebaValidos.add("correcto5");
		casosPruebaValidos.add("fallo1");
		casosPruebaValidos.add("fallo2");
		casosPruebaValidos.add("fallo3");
		casosPruebaValidos.add("fallo4");
		casosPruebaValidos.add("fallo5");
		
		System.out.println("Seleccione el caso de prueba:");
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
}
