package pdl.jsplInterpreter;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.nio.file.Paths;

public class FileManager {
	private BufferedReader readerCodigo;
	private BufferedWriter writerTokens, writerTS, writerParse;
	
	public FileManager(String pathEntrada, String pathSalida, String pathCodigo, String pathTokens, String pathTS, String pathParse, String casoPrueba) {
		casoPrueba="casosPrueba/"+casoPrueba+"/";
		try {
			readerCodigo = new BufferedReader(new FileReader(casoPrueba+pathCodigo));
		}catch(IOException ex) {
			ErrorHandler.error(0, 1, 0, "Ruta de trabajo: "+Paths.get(".").toAbsolutePath().normalize().toString()+". Ruta al archivo: "+casoPrueba+pathCodigo+".");
		}
		
		try {
			writerTokens = new BufferedWriter(new FileWriter(casoPrueba+pathTokens,false));
		}catch(IOException ex) {
			ErrorHandler.error(0, 2, 0, "Ruta de trabajo: "+Paths.get(".").toAbsolutePath().normalize().toString()+". Ruta al archivo: "+casoPrueba+pathTokens+".");
		}
		
		try {
			writerTS = new BufferedWriter(new FileWriter(casoPrueba+pathTS,false));
		}catch(IOException ex) {
			ErrorHandler.error(0, 2, 0, "Ruta de trabajo: "+Paths.get(".").toAbsolutePath().normalize().toString()+". Ruta al archivo: "+casoPrueba+pathTS+".");
		}
		
		try {
			writerParse = new BufferedWriter(new FileWriter(casoPrueba+pathParse,false));
		}catch(IOException ex) {
			ErrorHandler.error(0, 2, 0, "Ruta de trabajo: "+Paths.get(".").toAbsolutePath().normalize().toString()+". Ruta al archivo: "+casoPrueba+pathParse+".");
		}
	}

	public BufferedReader getReaderCodigo() {
		return readerCodigo;
	}

	public void setReaderCodigo(BufferedReader readerCodigo) {
		this.readerCodigo = readerCodigo;
	}

	public BufferedWriter getWriterTokens() {
		return writerTokens;
	}

	public void setWriterTokens(BufferedWriter writerTokens) {
		this.writerTokens = writerTokens;
	}

	public BufferedWriter getWriterTS() {
		return writerTS;
	}

	public void setWriterTS(BufferedWriter writerTS) {
		this.writerTS = writerTS;
	}

	public BufferedWriter getWriterParse() {
		return writerParse;
	}

	public void setWriterParse(BufferedWriter writerParse) {
		this.writerParse = writerParse;
	}
	
	

}
