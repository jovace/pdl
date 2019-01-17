PRACTICA PROCESADORES DE LENGUAJES
CONVOCATORIA ENERO 2019

Autores:
-> Alfonso Ventosa Bonilla         02588399W        x15m027		alfonso.ventosab@alumnos.upm.es
-> Fernando Magdalena Laorden      71735296K        x15m018		fernando.magdalena.laorden@alumnos.upm.es
-> Jorge Vázquez Acevedo           02308940Q        y16m023		jorge.vazquez.acevedo@alumnos.upm.es

Timestamp del sellado mediante HASH:
18/01/2019 00:12:00

Sumas de verificacion:
SHA256: 	6165681d8661925cae99efbd06905fcd613821cb56964de1dad782b39320da24
CRC32:		8fc83de4
MD5:		02526debc1c8d33386ea241175cee325

Ficheros y directorios:
-> jsplInterpreter.jar
	Ejecutable con el programa.
	Ejecutar con "java -jar ./jsplInterpreter.jar"
	Requiere directorio casosPrueba en el mismo nivel para funcionar correctamente, y JRE 1.8.

-> /casosPrueba
	/correcto1
	/correcto2
	/correcto3
	/correcto4
	/correcto5
	/fallo1
	/fallo2
	/fallo3
	/fallo4
	/fallo5


	Arbol de directorios con los casos de prueba para el programa. Dentro de cada uno de los casos de prueba se encontraria:

	/entrada
		codigo.js 		-> Archivo con el codigo de entrada para el caso de prueba correspondiente.

	En el caso de casos de prueba correctos:

	/salida
		tokens.txt		-> Archivo de tokens en el formato especificado.
		parse.txt		-> Archivo parse con el formato especificado.
		ts.txt			-> Archivo de volcado de la tabla de simbolos con el formato especificado.
		arbol.html		-> Fichero con el arbol sintactico generado a partir del parse.

	En el caso de casos de prueba con errores, dichos archivos se pueden encontrar vacios si no se llego a ejecutar la fase correspondiente del programa. Además, podremos encontrar:

	/salida
		error.txt		-> Copia del error que apareceria por consola al ejecutar el caso de prueba.

-> /src
	Directorio con el codigo fuente del proyecto

-> /archivosEntrega
	pdlgramatica.ll1		-> Archivo con la gramatica en el formato especificado

-> autores.txt
	Fichero con los datos personales de los autores de la practica.

-> memoria.pdf
	Fichero PDF con la memoria impresa entregada en formato fisico.

-> entrega.zip
	Fichero comprimido con todos los archivos mencionados anteriormente.