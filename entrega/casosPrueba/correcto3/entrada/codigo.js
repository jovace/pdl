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

print("Hacer 5 divisiones");
divisiones(5);

print("Hacer 20 divisiones");
divisiones(20);

print("Hacer 10 divisiones");
divisiones(10);
