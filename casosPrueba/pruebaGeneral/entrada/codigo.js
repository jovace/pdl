
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



//Arreglar esta parte. scope inner for no reconoce lo de arriba. maybe tablaPadre==null??
/*
var int elevado = 2;
var int i = 0;
function int dosElevadoN(int n){
	for(var int i=0;i<n-1;i++){
		elevado=elevado*2;
	}
	return elevado;
}
print(dosElevadoN(10));
*/
