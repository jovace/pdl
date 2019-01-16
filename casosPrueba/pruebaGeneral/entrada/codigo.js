
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

*/