
/*
var string cadena = "";

prompt(cadena);
print(cadena);

*/

var int a=1;
var int b=1;
var int c=0;
var int x=5;

function int suma(int a, int b){
	return a+b;
}

function int resta(int a, int b){
	return a-b;
}


for(var int j=0;j<x;j++){
	var bool d=false;
	c=suma(a,b);
	a=b;
	b=c;
}

print(b);

var int r=-1;
print(r);



var int z=0;
z=suma(suma(suma(suma(suma(suma(suma(suma(6,1),resta(0,1)),resta(0,1)),resta(0,1)),resta(0,1)),resta(0,1)),resta(0,1)),resta(0,1));
print(z);

/*
Codigo 1: Juego con operaciones basicas de enteros
var int a=1;
var int b=2;
var int c=3;
var int d=4;

function int suma(var int x, var int y){
	return x+y;
}

function int resta(var int x, var int y){
	return x-y;
}

function int mult(var int x, var int y){
	return x*y;
}

function int div(var int x, var int y){
	return x/y;
}

var int e=suma(a,b);
print(e);
print(e<3);
print(e<=3);
print(e==3);
print(e>=3);
print(e>3);
var int f=resta(d,c);
print(div(e,f));
print(mult(suma(a,c),resta(d,b)));


Codigo 2: Juego con booleans, strings
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
}
print("Introduzca una cadena:");
prompt(nombre);
print(saludar(nombre));

*/