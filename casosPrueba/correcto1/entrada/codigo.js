/*Ejemplo 1: Juego con operaciones logicas y aritmeticas int*/
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
print(e);
print(e<3);
print(e<=3);
print(e==3);
print(e>=3);
print(e>3);
var int f=resta(d,c);
print(div(e,f));
print(mult(suma(a,c),resta(d,b)));