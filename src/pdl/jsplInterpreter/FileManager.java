package pdl.jsplInterpreter;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;

public class FileManager {
	private BufferedReader readerCodigo;
	private BufferedWriter writerTokens, writerTS, writerParse;
	
	public FileManager(String pathEntrada, String pathSalida, String pathCodigo, String pathTokens, String pathTS, String pathParse) {
		try {
			readerCodigo = new BufferedReader(new FileReader(pathCodigo));
			writerTokens = new BufferedWriter(new FileWriter(pathTokens,false));
//			writerTS = new BufferedWriter(new FileWriter(pathTS,false));
			writerParse = new BufferedWriter(new FileWriter(pathParse,false));
		}catch(IOException ex) {
			System.err.println("No se ha podido abrir el archivo indicado.");
			ex.printStackTrace();
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
