/*
1.- Define la siguiente extructura:
struct str_Libro
{
    char titulo[200];
    char autor[200];
    int paginas;
    float precio;
};

Define una función incrementarPrecio, que recive como parámetros una estructura str_Libro, y un float incr, y devuelve una nueva str_Libro con el mismo contenido pero su precio incrementado incr.

¿Se pasa por valor o por referencia el parámetro de la estructura?

2.- Cambia la función anterior y conviértela para que se pase por referencia la estructura y no se requiera copiar.

*/
#include <stdio.h>

struct str_Libro
{
    char titulo[200];
    char autor[200];
    int paginas;
    float precio;
};

int main(void)
{
    return 0;
}
