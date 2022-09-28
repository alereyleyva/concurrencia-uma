/*
 * Planta.c
 *
 *  Created on: 9 abr. 2021
 *      Author: RZP
 */

#include "Planta.h"
#include <string.h>
#include <stdio.h>
#include <stdlib.h>

/**
 * crea una lista de habitaciones vacia
 */
void crear(ListaHab *lh)
{
    *lh = NULL;
}

/**
 * En esta función se añade a la lista, la habitación con número nh,
 * cliente "nombre" y fecha de salida "fs".
 * Si ya existe una habitación numerada con nh en la lista se actualizan sus campos
 * con los nuevos valores ("nombre" y "fs").
 * Si no existe ninguna habitación con ese número, se inserta un nuevo nodo de manera que
 * la lista siempre esté ordenada con respecto a los números de las habitaciones (de
 * menor a mayor)
 */
void nuevoCliente(ListaHab *lh, unsigned nh, char *nombre, unsigned fs)
{
    ListaHab nuevoCliente = malloc(sizeof(struct Nodo));
    nuevoCliente->numHab = nh;
    strcpy(nuevoCliente->nombre, nombre);
    nuevoCliente->fechaSalida = fs;

    if (*lh == NULL)
    {
        nuevoCliente->sig = NULL;
        *lh = nuevoCliente;
    }
    else if (nh <= (*lh)->numHab)
    {
        if (nh == (*lh)->numHab)
        {
            strcpy((*lh)->nombre, nuevoCliente->nombre);
            (*lh)->fechaSalida = nuevoCliente->fechaSalida;
        }
        else
        {
            nuevoCliente->sig = *lh;
            *lh = nuevoCliente;
        }
    }
    else
    {
        ListaHab anterior = *lh;
        ListaHab puntero = (*lh)->sig;
        while (puntero != NULL && nh > puntero->numHab)
        {
            anterior = puntero;
            puntero = puntero->sig;
        }

        if (puntero != NULL && nh == puntero->numHab)
        {
            strcpy(puntero->nombre, nuevoCliente->nombre);
            puntero->fechaSalida = nuevoCliente->fechaSalida;
        }
        else
        {
            nuevoCliente->sig = puntero;
            anterior->sig = nuevoCliente;
        }
    }
}

/**
 * Escribe por la pantalla la información de cada una de las habitaciones
 * almacenadas en la lista.
 * El formato de salida debe ser:
 * \t habitacion "nh" ocupada por "nombre" con fecha de salida "fs"
 */
void imprimir(ListaHab lh)
{
    ListaHab aux = lh;
    while (aux != NULL)
    {
        printf("\t habitacion %u ocupada por %s con fecha de salida %u \n", aux->numHab, aux->nombre, aux->fechaSalida);
        aux = aux->sig;
    }
}

/**
 * Borra todos los nodos de la lista y la deja vacía
 */
void borrar(ListaHab *lh)
{
    ListaHab aux;
    while (*lh != NULL)
    {
        aux = *lh;
        *lh = (*lh)->sig;
        free(aux);
    }
}

/**
 * Borra todos las habitaciones cuya fecha de salida es fs.
 */

void borrarFechaSalida(ListaHab *lh, unsigned fs)
{

    while (*lh != NULL && (*lh)->fechaSalida == fs)
    {
        ListaHab aux = *lh;
        *lh = (*lh)->sig;
        free(aux);
    }

    if (*lh != NULL)
    {
        ListaHab anterior = *lh;
        ListaHab puntero = (*lh)->sig;
        while (puntero != NULL)
        {
            if (puntero->fechaSalida == fs)
            {
                anterior->sig = puntero->sig;
                free(puntero);
                puntero = anterior->sig;
            }
            else
            {
                anterior = puntero;
                puntero = puntero->sig;
            }
        }
    }
}
