if(1>2) print("Correcto");

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