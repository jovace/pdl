var int a=1;
var int b=1;
var int c=0;
var int x=5;

function int suma(int a, int b){
	return a+b;
}


	for(var int j=0;j<x;j++){
		c=suma(a,b);
		a=b;
		b=c;		
	}

print(b);
