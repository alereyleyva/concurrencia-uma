#include <stdlib.h>
#include <stdio.h>
#include "gestion_memoria.h"

int MAX = 1000;

/*
Función privada recomendada:
Recibe una lista y compacta elementos que son consecutivos, devolviendo la lista compactada.
*/
void compactar(T_Manejador *manejador_ptr)
{
    ;
}

/* Crea la estructura utilizada para gestionar la memoria disponible. Inicialmente, s�lo un nodo desde 0 a MAX
Por recordar:
typedef struct T_Nodo* T_Manejador;

struct T_Nodo {
    unsigned inicio;
    unsigned fin;
    T_Manejador sig;
};

En el main se define la lista como T_Manejador manej; (un puntero a una structura T_Nodo).
¿Porqué se pasa un puntero a T_Manejador? ¿Que pasa si pasamos T_Manejador y no un puntero a T_Manejador y cambiamos su valor (su valor es una zona de memporia)?
*/
void crear(T_Manejador *manejador)
{
    *manejador = malloc(sizeof(struct T_Nodo));
    (*manejador)->sig = NULL;
    (*manejador)->inicio = 0;
    (*manejador)->fin = MAX - 1;
}

/* Destruye la estructura utilizada (libera todos los nodos de la lista. El par�metro manejador debe terminar apuntando a NULL

Consejo: Para saber si te estas dejando memoria por ahí, en el main crea un bucle infinito que crea y destruye, si la memoria no se mantiene constante, está mal.

*/
void destruir(T_Manejador *manejador)
{
    T_Manejador aux;
    while (*manejador != NULL)
    {
        aux = *manejador;
        *manejador = (*manejador)->sig;
        free(aux);
    }
}

/* Devuelve en �dir� la dirección de memoria �simulada� (unsigned) donde comienza el trozo de memoria continua de tamaño �tam� solicitada.
Si la operación se pudo llevar a cabo, es decir, existe un trozo con capacidad suficiente, devolvera TRUE (1) en �ok�; FALSE (0) en otro caso.
 */
void obtener(T_Manejador *manejador, unsigned tam, unsigned *dir, unsigned *ok)
{
    if (*manejador == NULL)
    {
        *ok = 0;
    }
    else
    {

        unsigned tamNodo = (*manejador)->fin - (*manejador)->inicio + 1;
        if (tamNodo == tam)
        {
            *dir = (*manejador)->inicio;
            T_Manejador aux = *manejador;
            *manejador = (*manejador)->sig;
            free(aux);
            *ok = 1;
        }
        else if (tamNodo > tam)
        {
            *dir = (*manejador)->inicio;
            (*manejador)->inicio = (*manejador)->inicio + tam;
            *ok = 1;
        }
        else
        {
            T_Manejador *aux2 = ;
            obtener(&(*manejador)->sig, tam, dir, ok);
        }
    }
}

/* Muestra el estado actual de la memoria, bloques de memoria libre */
void mostrar(T_Manejador manejador)
{
    while (manejador != NULL)
    {
        printf("Inicio: %u, Fin: %u -> Tam: %u \n", manejador->inicio, manejador->fin, manejador->fin - manejador->inicio + 1);
        manejador = manejador->sig;
    }
}

/* Devuelve el trozo de memoria continua de tamaño �tam� y que
 * comienza en �dir�.
 * Se puede suponer que se trata de un trozo obtenido previamente.
 */
void devolver(T_Manejador *manejador, unsigned tam, unsigned dir)
{
    // 0 - 300 && 500 - 599 && 700 - 999 dir = 650 + (tam - 1)
    T_Manejador nodo = malloc(sizeof(struct T_Nodo));
    nodo->inicio = dir;
    nodo->fin = dir + tam - 1;
    T_Manejador aux;
    if (*manejador == NULL)
    {
        nodo->sig = NULL;
        *manejador = nodo;
    }
    else if (dir < (*manejador)->inicio)
    {
        nodo->sig = *manejador;
        *manejador = nodo;
    }
    else
    {
        aux = *manejador;
        while ((aux->sig) != NULL && (dir > (aux->sig)->inicio))
        {
            aux = aux->sig;
        }
        nodo->sig = aux->sig;
        aux->sig = nodo;
    }
}
