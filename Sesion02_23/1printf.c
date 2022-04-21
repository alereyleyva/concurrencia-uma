/*
 En este ejercicio vamos a jugar con la salida estandar
 https://www.cplusplus.com/reference/cstdio/printf/?kw=printf

int printf ( const char * format, ... );
Format:
%[flags][width][.precision][length]specifier


1.- Vamos a imprimir tres variables:
 array char cadena[11]="HOla mundo";
 int a = 65;
 float b = 65.0;
2.- Vamos a justificar el entero a la izquierda, con el flags - y vamos a forzar a imprimir con width 5 ¿qué ocurre?.
3.- Vamos a añadir al formato del float una precision de 2 ¿qué ocurre?.
4.- Vamos a cambiar el formato del entero para pintar un caracter (busca su specifier en el enlace) ¿qué letra sale? ASCII code
https://www.cplusplus.com/doc/tutorial/variables/
*/
// ¿Qué letra sale al pintar en caracter? :one: B :two: C :three: Ninguna :four: A

#include <stdio.h>
int main(int argc, char const *argv[])
{
   char cadena[11] = "HOla mundo";
   int a = 65;
   float b = 65.0;
   printf("un entero %-5c,esto es una cadena %s  y un float %.2f\n", a, cadena, b);
   return 0;
}
