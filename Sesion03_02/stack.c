#include "stack.h"
Stack stackCreate() {
    printf("stackCreated invoked\n") ;
    Stack s ;
    s = malloc(sizeof(struct stack)) ;
    s->size = 0 ;
    s->head = NULL ;
    return s;
}
int stackPush(Stack s, int v) {
     assert(s!=NULL);
    // Implementación de stackPush aquí, metemos por la cabeza
    int result = 0;
    StackNode ptr = malloc(sizeof(struct stackNode));
    if ((ptr!=NULL)){
        ptr->siguiente=s->head;
        ptr->value=v;
        s->head=ptr;

        s->size++;
        
        printf("stackPush invoked\n") ;
        result = 1;
    }
    return result ;
}
int stackPop(Stack s, int * v){
     assert(s!=NULL);
     int result = 0;
    // Implementación de stackPop aquí, sacamos por la cabeza
    if((s->head!=NULL)){
        StackNode ptr=s->head;
        s->head = s->head->siguiente;
        *v = ptr->value;
        free(ptr);
        s->size--;
        result = 1;
    }
    return result;
}
int stackSize(Stack s) {
    assert(s!=NULL); // Si esto falla, para la ejecución, lo dan como precondición.
    return s->size;
}