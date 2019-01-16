package pdl.jsplInterpreter;

import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

public final class ErrorHandler {
	
	
	public static void error(int fase, int cod_error, int linea, String infoExtra) {
		String errorS = fase+"x"+cod_error;
		String errorMessage = errores.get(errorS);
		String salida;
		salida=String.format(errorMessage, infoExtra, linea,errorS);
		System.err.println(salida);
		System.exit(1);
	}
	
	private static final Map<String, String> errores;
    static {
        Map<String, String> staticMap = new HashMap<String,String>();
        staticMap.put("0x0","Error generico.\nInformacion adicional: %s\nLinea %d. \nCodigo %s");
        staticMap.put("0x1","Error generico. No se puede leer el archivo de codigo.\nInformacion adicional: %s\nLinea %d. \nCodigo %s");
        staticMap.put("0x2","Error generico. No se puede abrir el archivo para escritura.\nInformacion adicional: %s\nLinea %d. \nCodigo %s");
        staticMap.put("0x3","Error generico. Caso de prueba elegido no esta disponible.\nInformacion adicional: %s\nLinea %d. \nCodigo %s");
        staticMap.put("1x0", "Error lexico.\nInformacion adicional: %s\nLinea %d.\nCodigo %s");
        staticMap.put("1x1", "Error lexico: caracter no reconocido.\nInformacion adicional: %s\nLinea %d.\nCodigo %s");
        staticMap.put("2x0", "Error sintactico: token inesperado.\nInformacion adicional: %s\nLinea %d.\nCodigo %s");
        staticMap.put("2x1", "Error sintactico: token desconocido.\nInformacion adicional: %s\nLinea %d.\nCodigo %s");
        staticMap.put("3x0", "Error semantico.\nInformacion adicional: %s\nLinea %d.\nCodigo %s"); //Error general semantico
        staticMap.put("3x1", "Error semantico: el valor recibido no es del tipo declarado.\nInformacion adicional: %s\nLinea %d.\nCodigo %s");
        staticMap.put("3x2", "Error semantico: ya existe un identificador con ese nombre.\nInformacion adicional: %s\nLinea %d.\nCodigo %s");
        staticMap.put("3x3", "Error semantico: el valor recibido no es del tipo esperado.\nInformacion adicional: %s\nLinea %d.\nCodigo %s");
        staticMap.put("3x4", "Error semantico: el tipo de ambos operadores debe ser el mismo.\nInformacion adicional: %s\nLinea %d.\nCodigo %s");
        staticMap.put("3x5", "Error semantico: no existe un identificador con ese nombre.\nInformacion adicional: %s\nLinea %d.\nCodigo %s");
        staticMap.put("3x6", "Error semantico: numero de argumentos no es el esperado.\nInformacion adicional: %s\nLinea %d.\nCodigo %s");
        staticMap.put("3x7", "Error semantico: el argumento recibido no es del tipo esperado.\nInformacion adicional: %s\nLinea %d.\nCodigo %s");
        errores = Collections.unmodifiableMap(staticMap);
    }
	
	
}
