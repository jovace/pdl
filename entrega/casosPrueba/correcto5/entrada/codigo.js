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