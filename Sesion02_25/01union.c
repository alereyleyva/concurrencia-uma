/*
Vamos a crear una tienda con 100 artículos máximo, vamos a tener libros y revistas.

struct str_Libro
{
    char titulo[200];
    char autor[200];
    int paginas;
    float precio;
};

enum Meses { ENE, FEB, MAR, ABR, MAY, JUN, JUL, AGO, SEP, OCT, NOV, DIC };

struct str_Revisa_Mensual
{
    char titulo[200];
    enum Meses mes;
    float precio;
};
1.- Genera una nueva estructura str_item que contenga un struct str_Libro, un str_Revisa_Mensual y un char que almacene 'r' si es revisa, 'l' si es libro y 'v' si no hay nada.
2.-usa sizeof(struct str_item) para conocer cuantos bytes necesitamos.
3.-genera un menu interactivo para introducir y ver mostrar todos los ítem que hay en la tienda.
-----------------------

4.-Vamos a usar ahora la union para reducir tamaño. Se va a generar una nueva str_union_item, con un char y una variable item que es la una union de libro y revista.
union u_Item{
        struct str_Libro libro;
        struct str_Revisa_Mensual revista;
} item;

*/



#include<stdio.h>
struct str_Libro
{
    char titulo[200];
    char autor[200];
    int paginas;
    float precio;
};
enum Meses { ENE, FEB, MAR, ABR, MAY, JUN, JUL, AGO, SEP, OCT, NOV, DIC };
struct str_Revisa_Mensual
{
    char titulo[200];
    enum Meses mes;
    float precio;
};
// struct str_item{
//     struct str_Libro libro;
//     struct str_Revisa_Mensual revista;
//     char tipo;
// };

union u_Item{
        struct str_Libro libro;
        struct str_Revisa_Mensual revista;
};

struct str_item{
    union u_Item item;
    char tipo;
};


const int MAX = 100;
int main(int argc, char const *argv[])
{
    struct str_item list[MAX];

    printf("-----\nTAM: %i bytes\n------\n",sizeof(struct str_item)*MAX);

    
    return 0;
}
