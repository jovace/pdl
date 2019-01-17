print("PRUEBA 1");
var int a=1;
var int b=2;
var int c=3;
var int d=4;

function int suma(int x, int y){
	return x+y;
}

function int resta(int x, int y){
	return x-y;
}

function int mult(int x, int y){
	return x*y;
}

function int div(int x, int y){
	return x/y;
}

var int e=suma(a,b);
print("Resultado esperado es 3");
print(e);
print("Resultado esperado es false");
print(e<3);
print("Resultado esperado es true");
print(e<=3);
print("Resultado esperado es true");
print(e==3);
print("Resultado esperado es true");
print(e>=3);
print("Resultado esperado es false");
print(e>3);
var int f=resta(d,c);
print("Resultado esperado es 3");
print(div(e,f));
print("Resultado esperado es 8");
print(mult(suma(a,c),resta(d,b)));


print("PRUEBA 2");

var bool varTrue = true;
var bool varFalse = false;
var bool oIgual=false;

print("Deberia resultar true");
oIgual|=varTrue;
print(oIgual);

print("Deberia resultar true");
oIgual|=varFalse;
print(oIgual);

function bool oo(bool x, bool y){
	return x||y;
}

function bool yy(bool x, bool y){
	return x&&y;
}

print("Debe resultar, en orden, false true true true");
print(oo(varFalse, varFalse));
print(oo(varTrue, varFalse));
print(oo(varFalse, varTrue));
print(oo(varTrue, varTrue));

print("Debe resultar, en orden, false false false true");
print(yy(varFalse, varFalse));
print(yy(varTrue, varFalse));
print(yy(varFalse, varTrue));
print(yy(varTrue, varTrue));

print("Debe resultar, en orden, false true false");
print(!varTrue);
print(!varFalse);
print(oo(varFalse, !varTrue));

var string nombre="";
function string saludar(string name){
	var string saludo="Hola "+name;
	return saludo;
}
print("Introduzca una cadena:");
prompt(nombre);
print(saludar(nombre));


print("PRUEBA 3");


var int j=0;
var int i=0;
print("Numeros del 0 al 9:");
for(var int i=0;i<10;i++){
	print(i);
}

print("Tablas de multiplicar:");
for(var int i=0;i<10;i++){
	print("Tabla del");
	print(i);
	print("--------------");
	j=0;
	for(var int j=0;j<10;j++){
		print(i*j);
	}
	print("--------------");
}

print("For invertido:");
for(var int i=10;i>0;i--){
	print(i);
}


function bucle(int x, int z){
	i=0;
	for(var int i=0;i<x;i=i+z){
		print(i);
	}
	return ;
}

bucle(20,4);

print("");

function divisiones(int x){
	for(var int i=0;i<100;i=i+(100/x)){
		print(i);
	}
	return ;
}

print("PRUEBA 4");
print("Hacer 5 divisiones");
divisiones(5);

print("Hacer 20 divisiones");
divisiones(20);

print("Hacer 10 divisiones");
divisiones(10);

if(1>2) print("Correcto");

a=1;
b=1;
c=0;
x=5;

function int suma(int a, int b){
	return a+b;
}

function int resta(int a, int b){
	return a-b;
}


for(var int j=0;j<x;j++){
	c=suma(a,b);
	a=b;
	b=c;
}

print("Deberia imprimir 13");
print(b);

print("Tiene que imprimir -1");
var int r=-1;
print(r);


print("Deberia imprimir ((((((((6+1)+(0-1))+(0-1))+(0-1))+(0-1))+(0-1))+(0-1))+(0-1))=0");
var int z=0;
z=suma(suma(suma(suma(suma(suma(suma(suma(6,1),resta(0,1)),resta(0,1)),resta(0,1)),resta(0,1)),resta(0,1)),resta(0,1)),resta(0,1));
print(z);


function int sumaMasUno(int a, int b){
	return suma(a+b,1);
}
print("Deberia imprimir 5");
print(sumaMasUno(3,1));


print("PRUEBA 5");
function int fibonacci(int x){
	var bool valorFijo=false;
	var int a=1;
	var int b=1;
	if(x<=2) valorFijo=true;
	if(!valorFijo) a=fibonacci(x-1);
	if(!valorFijo) b=fibonacci(x-2);
	var int resultado=0;
	resultado=a+b;
	if(valorFijo) resultado=1;
	return resultado;
}
print("Termino numero 3 de serie de Fibonacci");
print(fibonacci(3));
print("Termino numero 7 de serie de Fibonacci");
print(fibonacci(7));
print("Termino numero 25 de serie de Fibonacci");
print(fibonacci(25));


function int collatz(int x){
	print(x);
	var int retorno=0;
	if((x%2)==0) retorno=collatz(x/2);
	if((!(x==1))&&((x%2)==1)) retorno=collatz((3*x)+1);	
	return retorno;
}

print("Serie de Collatz para 4:");
print(collatz(4));

print("Serie de Collatz para 5:");
print(collatz(5));

print("Serie de Collatz para 15:");
print(collatz(15));