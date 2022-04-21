#include <stdlib.h>
#include <stdio.h>
#include "circular.h"

void Crear(LProc *lista)
{
    *lista = NULL;
}

void AnyadirProceso(LProc *lista, int idproc)
{
    LProc newProcess = malloc(sizeof(struct circular));
    newProcess->pid = idproc;
    if ((*lista) == NULL)
    {
        *lista = newProcess;
        newProcess->sig = *lista;
    }
    else
    {
        LProc aux = *lista;
        while ((aux->sig) != (*lista))
        {
            aux = aux->sig;
        }

        aux->sig = newProcess;
        newProcess->sig = *lista;
    }
}

void MostrarLista(LProc lista)
{
    printf("\nLista circular\n");
    if (lista == NULL)
    {
        printf("Lista vacía");
    }
    else
    {
        LProc aux = lista;
        do
        {
            printf("Proceso %i\n", aux->pid);
            aux = aux->sig;
        } while (aux != lista);
    }
}

void EjecutarProceso(LProc *lista)
{
    if ((*lista) != NULL)
    {

        if (((*lista)->sig) == (*lista))
        {
            free(*lista);
            *lista = NULL;
        }
        else
        {
            LProc ptr = (*lista)->sig;
            LProc aux = *lista;
            while ((aux->sig) != (*lista))
            {
                aux = aux->sig;
            }
            aux->sig = ptr;
            free(*lista);
            *lista = ptr;
        }
    }
}