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