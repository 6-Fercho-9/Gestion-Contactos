/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Negocio;

import java.io.Serializable;
import java.util.LinkedList;
import java.util.List;
import java.util.Queue;
import java.util.Stack;

/**
 *
 * @author Lenovo
 */
public class ArbolBinarioBusqueda<K extends Comparable<K>,V> implements IArbolBusqueda<K,V>,Serializable{
    protected NodoBinario<K,V> raiz;
    
    public ArbolBinarioBusqueda(){
    
    }
 /*--------------------------------------------------------------------------------------------------*/
    /*-----------------------------------Insertar clave valor---------------------------------------*/
    @Override
    public void insertarClaveValor(K clave, V valor) {//metodo iterativo
        if(this.esArbolVacio()){//si es arbol vacio solamente inserta
            NodoBinario<K,V> p=new NodoBinario<K,V>(clave,valor);
            this.raiz=p;
    
        }else{//si no es arbol vacio
            NodoBinario<K,V> ant=NodoBinario.nodoVacio();
            NodoBinario<K,V> act=raiz;
            do{//este do while es para moverme a donde debo insertar,pero el que enlazara es el anterior y no el actual
                if(clave.compareTo(act.getClave())<0){//si la clave a insertar es menor a donde estoy parado entonces avanza por la izquierda
                    ant=act;
                    act=act.getHijoIzquierdo();
                }else if(clave.compareTo(act.getClave())>0){//si la clave a insertar es mayor a donde estoy parado,entonces avanza por la derecha
                        ant=act;//recordando que el actual es el que controlara el ciclo,y generalmente llegara a nulo o sera nodo vacio,
        //el anterior sera el que ENLACE con el nuevo nodo a crear
                        act=act.getHijoDerecho();
                }else{//si son iguales,entonces actualizo el valor
                    act.setValor(valor);
                    return ;//para que acabe la ejecucion
                }
            }while(!NodoBinario.esNodoVacio(act));//como mencionaba mientras el actual no sea nodo vacio o el nodo sea distinto de nulo
            //una vez que ya me posicione donde debo insertar ,solamente me falta enlazarlos
        //entra a estos if cuando la clave no se encuentre en el arbol
        //si se encuentra la clave en el arbol entonces no ejecuta estas instrucciones
        //y como mencionaba cuando llega a estas instrucciones es que el actual es nodo vacio o nulo
        //Y el que pivotea serea el anterior=ant
            if(clave.compareTo(ant.getClave())<0){//si la clave es menor inserta por la izquierda
                NodoBinario<K,V> p=new NodoBinario<K,V>(clave,valor);
                ant.setHijoIzquierdo(p);//aca lo inserta o se enlaza
            }else if(clave.compareTo(ant.getClave())>0){//si es mayor inserta por la derecha
                NodoBinario<K,V> p=new NodoBinario<K,V>(clave,valor);
                ant.setHijoDerecho(p);
            }
    
        }
    }
    //metodo recursivo
    public void insertarClaveValorRecursivo(K clave,V valor){
        this.raiz=this.insertarClaveValorRecursivo(this.raiz,clave, valor);
    
    }
    private NodoBinario<K,V> insertarClaveValorRecursivo(NodoBinario<K,V> nodoActual,K clave,V valor){
        if(NodoBinario.esNodoVacio(nodoActual)){//si es arbol vacio,creo el nodo
            return new NodoBinario<K,V>(clave,valor);
        }
        //si la clave a insertar es menor al nodo actual,debo bajar por izquierda
        if(clave.compareTo(nodoActual.getClave())<0){
            NodoBinario<K,V>supuestoHijoIzquierdo=this.insertarClaveValorRecursivo(nodoActual.getHijoIzquierdo(), clave, valor);
            nodoActual.setHijoIzquierdo(supuestoHijoIzquierdo);
            return nodoActual;
        }
        //si la clave a insertar es mayor al nodo actual,debo bajar por derecha
        else if(clave.compareTo(nodoActual.getClave())>0){
            NodoBinario<K,V>supuestoHijoDerecho=this.insertarClaveValorRecursivo(nodoActual.getHijoDerecho(), clave, valor);
            nodoActual.setHijoDerecho(supuestoHijoDerecho);
            return nodoActual;
        }else{//si la clave es igual entonces actualizo el valor y retorno ese nodo
            nodoActual.setValor(valor);
            return nodoActual;
        }
    }
/*--------------------------------------------------------------------------------------------------*/
    
    //basicamente elimina el nodo mediante su clave y retorna el valor eliminado
    //eliminar clave recursivo
    @Override
    public V eliminarClave(K clave) {
        V valorARetornar=this.buscarClave(clave);//primero busco esa clave y si existe entonces lo eliminara
        if(valorARetornar==null){
            return null;
        }
        //entonces si la clave existe en el arbol,entrara al metodo recursivo
       this.raiz=this.eliminarClave(raiz, clave);
    
        return valorARetornar;
    }
    //este metodo lo que hace es que cuando lo pilla al nodo a buscar,
    //debe retornar un valor,una vez que retorna ese valor sus supuestos hijos tomaran ese valor
    //y el actual es el que desapilara entonces cuando desapile ese actual simula un nodo anterior como en iterativo y con ese es
    //el que enlazaremos con los supuestos,con eso se "elimina",pero claro esto tiene un detalle y es que no 
    //retorna el valor del nodo a eliminar,solamente lo elimina
    //para eso el ingeniero llama al metodo buscar clave cosa que ese si retorna el valor
    //entonces solamente debe preocuparse por eliminar
    
    //este metodo como su nombre lo indica lo elimina el nodo el arbol y no retorna un valor en si
    private NodoBinario<K,V> eliminarClave(NodoBinario<K,V> nodoActual,K clave){
        if(!NodoBinario.esNodoVacio(nodoActual)){
            if(clave.compareTo(nodoActual.getClave())<0){
                NodoBinario<K,V> supuestoHijoIzquierdo=eliminarClave(nodoActual.getHijoIzquierdo(),clave);
                nodoActual.setHijoIzquierdo(supuestoHijoIzquierdo);//con esto es que lo reconstruye y elimina digamos
                return nodoActual;///este return sera el valor del supuesto hijo
            }else if(clave.compareTo(nodoActual.getClave())>0){
                NodoBinario<K,V> supuestoHijoDerecho=eliminarClave(nodoActual.getHijoDerecho(),clave);
                nodoActual.setHijoDerecho(supuestoHijoDerecho);
                return nodoActual;
            }else{
                //aca entra si encontro el nodo
                //caso 1
                if(nodoActual.esHoja()){
                    return NodoBinario.nodoVacio();
                }
                //caso 2
                
                     if(!nodoActual.esVacioHijoIzquierdo()&&nodoActual.esVacioHijoDerecho()){//si solo tiene 1 hijo izquierdo
                        return nodoActual.getHijoIzquierdo();
                     }
                     if(nodoActual.esVacioHijoIzquierdo()&&!nodoActual.esVacioHijoDerecho()){//si solo tiene derecha
                         return nodoActual.getHijoDerecho();
                     }
                 
                 //caso 3
                 //busca el ultimo nodo por izquierda,mandandole el nodo derecho del actual
                 //if(!nodoActual.esVacioHijoDerecho()&&!nodoActual.esVacioHijoIzquierdo()){
                     NodoBinario<K,V> nodoAEliminar=this.retornarUltimoIzquierda(nodoActual.getHijoDerecho());
                     this.eliminarClave(nodoActual, nodoAEliminar.getClave());
                     
                     //actualiza los valores
                     nodoActual.setClave(nodoAEliminar.getClave());
                     nodoActual.setValor(nodoAEliminar.getValor());
                     
                     return nodoActual;
                 //}
                 //return null;
            }
            
        }else{
            return null;
        }
        
        
    }
    
    public V eliminarClave4(K clave){//eliminar iterativo
        //NodoBinario<K,V> nodoPivote=this.raiz;
        NodoBinario<K,V> nodoActual=this.raiz;
        NodoBinario<K,V> nodoAnterior=NodoBinario.nodoVacio();
        V valorARetornar=null;
        NodoBinario<K,V> nodoAEliminar;
         NodoBinario<K,V>nodoTemporal=NodoBinario.nodoVacio();
      //este ciclo es para encontrar el nodo a eliminar
      do{
          nodoAEliminar=NodoBinario.nodoVacio();
        do{//este do while es para buscar
            if(clave.compareTo(nodoActual.getClave())==0){
                break;
            }else if(clave.compareTo(nodoActual.getClave())>0){
                nodoAnterior=nodoActual;
                nodoActual=nodoActual.getHijoDerecho();
            }else{
                nodoAnterior=nodoActual;
                nodoActual=nodoActual.getHijoIzquierdo();
            }
        }while(!NodoBinario.esNodoVacio(nodoActual));
        
        if(NodoBinario.esNodoVacio(nodoActual)){//aca entrara solamente si no lo encontre
            //valorARetornar=null;
            return null;
        }
        //primer caso
        //si el nodo a eliminar es hoja
        if(nodoActual.esHoja()){
            if(nodoAnterior==null){//aca solamente entra cuando el nodo a eliminar es hoja y es el nodo raiz
                this.raiz=NodoBinario.nodoVacio();
            }else{//si no quiere decir que el nodo a eliminar es diferente de la raiz
                if(nodoActual==nodoAnterior.getHijoIzquierdo()){//si el nodo actual es hijo izquierdo del anterior
                    //estos if responden a la pregunta de por donde voy a setear
                    nodoAnterior.setHijoIzquierdo(NodoBinario.nodoVacio());
                }else if(nodoActual==nodoAnterior.getHijoDerecho()){//si el nodo actual es hijo derecho del anterior
                    nodoAnterior.setHijoDerecho(NodoBinario.nodoVacio());
                }
            }
            valorARetornar= nodoActual.getValor();
        }
        //segundo caso
        //si el nodo a eliminar tiene 1 solo hijo
        //si solo tiene 1 hijo
        //modificar se necesita hacer la condicion del saber si es hoja
        if(nodoActual.tieneunHijo()){
            if(nodoAnterior==null){//si suponiendo elimino la raiz,esta no tiene un antecesor
                if(!nodoActual.esVacioHijoDerecho()){//si tiene hijo derecho
                    this.raiz=nodoActual.getHijoDerecho();//entonces la raiz es igual a ese nodo
                }else{//si no seguramente tiene hijo izquierdo
                    this.raiz=nodoActual.getHijoIzquierdo();
                }
            }else{
                if(nodoActual==nodoAnterior.getHijoIzquierdo()){//si el nodo Actual es hijo izquierdo del nodo anterior
                    if(!nodoActual.esVacioHijoDerecho()){//si tiene hijo derecho
                        nodoAnterior.setHijoIzquierdo(nodoActual.getHijoDerecho());
                    }else{//si tiene hijo izquierdo
                        nodoAnterior.setHijoIzquierdo(nodoActual.getHijoIzquierdo());
                    }
                }else{//si el nodo Actual es hijo derecho del anterior
                    if(!nodoActual.esVacioHijoDerecho()){//si tiene hijo derecho,setealo a ese hijo dado que es el unico
                        nodoAnterior.setHijoDerecho(nodoActual.getHijoDerecho());
                    }else{//caso constrario seguramente es el hijo izquierdo
                        nodoAnterior.setHijoDerecho(nodoActual.getHijoIzquierdo());
                    }
                }
            }
            valorARetornar= nodoActual.getValor();
        }
        //pero este if es para el caso 3,si el nodo a elimina tiene hijo izquierdo y derecho
        //entonces es caso 3
        if(!nodoActual.esVacioHijoDerecho()&&!nodoActual.esVacioHijoIzquierdo()){
            nodoAEliminar=retornarUltimoIzquierda(nodoActual.getHijoDerecho());//avanzare por izquierda mandandole la derecha del nodo actual
            nodoTemporal=nodoActual;//obtengo el nodo temporal para tener el nodo actual,dado que despues cambiara su valor
            
            //actualizo los indices para que a partir de estos busque
            //tambien la clave original que viene como parametro,no afectara puesto a que no hay referencias,una vez
            //termine el metodo no se perdera la referencia inicial o original
            nodoAnterior=nodoActual;
            nodoActual=nodoActual.getHijoDerecho();
            clave=nodoAEliminar.getClave();
        }
       }while(!NodoBinario.esNodoVacio(nodoAEliminar));
      
      /***
       * hago notar que
       * si tengo un nodo y hago cambios en este,afectara al arbol en si
       * como se ve arriba,afecto a la clave que pase como parametro,pero cuando FINALIZE
       * el metodo en su totalidad,esa clave volvera a su valor original
       * por ende puedo hacer que esa clave cambie su valor en el metodo y no me preocupo por su referencia original
       * ya que despues la volvera a obtener
       * 
       * ahora en este if,¿como yo se que entro al caso 3?,la respuesta a eso esta en si
       * existio el nodo temporal,si existio el nodo temporal su valor es distinto de null
       * pero si el nodoTemporal no existio entonces su valor es nulo
       * el nodo temporal no existe cuando es caso 1 y 2,
       * 
       * cuando es caso 1 o 2 solamente retorna el "valorARetornar"
       * 
       * como se ve en el if,antes de actualizar
       * guardo el valor del nodo actual que es el nodo temporal
       * y a ese nodo temporal le actualizp sus valores
       * una vez lo actualizo al ser referencias,automatico tambien el arbol se actualizara
       * finalmente retorno el valor que guarde antes de actualizar
       * 
       */
        if(nodoTemporal!=null){//si el nodo temporal es distinto de null quiere decir que pase por el caso 3
            //antes de actualizar el valor,guardo su valor que debo devolver
            V valorARetornar2=nodoTemporal.getValor();
            //entonces actualizo los valores
            nodoTemporal.setClave(clave);
            nodoTemporal.setValor(valorARetornar);
            //valorARetornar=valorARetornar2;
    
            return valorARetornar2;
        }
    
        return valorARetornar;//retorna lo que tenia antes
    }
/*--------------------------------------------------------------------------------------------------*/
    /*---------------------------Buscar clave------------------------------------------------------*/
    @Override
    public V buscarClave(K clave) {//recursivo
        return buscarClave(this.raiz,clave);
    }
    private V buscarClave(NodoBinario<K,V> nodoActual,K clave){
        
        if(!NodoBinario.esNodoVacio(nodoActual)){
            if(clave.compareTo(nodoActual.getClave())==0){
                return nodoActual.getValor();
            }
            //si no lo encontre
            //si la clave a buscar es menor al nodo actual
            if(clave.compareTo(nodoActual.getClave())<0){
                return buscarClave(nodoActual.getHijoIzquierdo(),clave);
            }
            //si no quiere decir que es mayor
            //y debe buscar por la derecha
            //no es necesario preguntar
            if(clave.compareTo(nodoActual.getClave())>0){
                return buscarClave(nodoActual.getHijoDerecho(),clave);
            }
            
        }
        //Si no
        return null;
    }
    //iterativo
    public V buscarClaveIterativo(K clave){
        if(clave==null){
           return (V) new ErrorClaveExistente("Error..Clave nula");
       }else{
           NodoBinario<K,V> nodoAct=this.raiz;
           do{
               if(clave.compareTo(nodoAct.getClave())==0){//si a la primera lo encontre,entonces retorna el valor
                   return nodoAct.getValor();
               }else if(clave.compareTo(nodoAct.getClave())>0){//si la clave a buscar es mayor al nodo donde estoy parado
                   //entonces que avanze por la derecha
                   nodoAct=nodoAct.getHijoDerecho();
               }else{
                   //caso contrario que sea menor que avanze por izquierda
                   nodoAct=nodoAct.getHijoIzquierdo();
               }
           }while(!NodoBinario.esNodoVacio(nodoAct));//en caso de que el nodo sea vacio enotnces quiere decir que no encontro el nodo
           //por lo tanto es nulo
           return null;//si no lo pilla,retorna null
       }
    }
/*--------------------------------------------------------------------------------------------------*/
    @Override
    public int size() {
        if(this.esArbolVacio()){
            return 0;
        }
        return this.recorridoEnPorNiveles().size();//contar cuantos nodos tiene un arbol
    }
/*--------------------------------------------------------------------------------------------------*/
/*------------------------------------altura------------------------------------------*/    
    @Override
    public int altura() {//recursivo
        return altura(this.raiz);
    }
    protected int altura(NodoBinario<K,V> nodoActual){
        if(!NodoBinario.esNodoVacio(nodoActual)){
            int alturaIzquierda=altura(nodoActual.getHijoIzquierdo());//ya hay un proceso que cuenta por izquierda
            int alturaDerecha=altura(nodoActual.getHijoDerecho());//ya hay un proceso que cuenta por derecha
            if(alturaIzquierda>alturaDerecha){
                return alturaIzquierda+1;
            }
            //si no quiere decir que la altura derecha es mayor que la izquierda
            return alturaDerecha+1;
        }
        return 0;//inicializo
    }
//iterativo
    public int alturaIterativo(){
        Stack<NodoBinario<K,V>> pilaDeNodos=new Stack<>();
        this.cargarNodosaLaPilaParaPostOrden(raiz, pilaDeNodos);
        int alturaMayor=pilaDeNodos.size();
        int alturaRelativa=pilaDeNodos.size();
        do{
            NodoBinario<K,V> nodoActual=pilaDeNodos.pop();
            if(!pilaDeNodos.isEmpty()){
                if(nodoActual==pilaDeNodos.peek().getHijoIzquierdo()){//si el nodo actual es hijoizquierdo del nodocima de la pila,entonces cargar por derecha
                    this.cargarNodosaLaPilaParaPostOrden(pilaDeNodos.peek().getHijoDerecho(), pilaDeNodos);
                    alturaRelativa=pilaDeNodos.size();
                    if(alturaRelativa>alturaMayor){
                        alturaMayor=alturaRelativa;
                    }
                }
            }
        }while(!pilaDeNodos.isEmpty());
        return alturaMayor;
    }
    
    //metodo para cargar la pila en inOrden,metodo amigo
    private void meterAlaPilaInOrden(Stack<NodoBinario<K,V>> pila,NodoBinario<K,V> nodoActual){
        while(!NodoBinario.esNodoVacio(nodoActual)){
            pila.push(nodoActual);
            nodoActual=nodoActual.getHijoIzquierdo();
        }
    }
    
    //metodo para cargar a la pilaPostOrden,metodo amigo
    //este metodo cargar todos los hijos izquierdos y los derechos,basicamente carga los nodos necesarios para PostOrden
    private void cargarNodosaLaPilaParaPostOrden(NodoBinario<K,V> nodo,Stack<NodoBinario<K,V>> pila){
        while(!NodoBinario.esNodoVacio(nodo)){
            pila.push(nodo);
            if(!nodo.esVacioHijoIzquierdo()){//si tiene hijo izquierdo,entonces lo meto a la pila
                nodo=nodo.getHijoIzquierdo();
            }else{//si en algun caso el hijo derecho tambien fuese vacio,entonces el while no lo dejara entrar
                nodo=nodo.getHijoDerecho();
            }
        }
        
    }
    //metodo que me retorna el ultimo a la izquierda o sucesor inOrden
    protected NodoBinario<K,V> retornarUltimoIzquierda(NodoBinario<K,V> nodo){
     /* 
        //de esta forma tambien funciona
        NodoBinario<K,V> nodoFinal=nodo;
        while(!NodoBinario.esNodoVacio(nodoFinal.getHijoIzquierdo())){
            nodoFinal=nodoFinal.getHijoIzquierdo();
        }
        return nodoFinal;
        */
        NodoBinario<K,V> nodoFinal=nodo;
        while(!NodoBinario.esNodoVacio(nodo)){
            nodoFinal=nodo;
            nodo=nodo.getHijoIzquierdo();
        }
        return nodoFinal;
    }
    @Override
    public void vaciar() {
        this.raiz=NodoBinario.nodoVacio();
    }

    @Override
    public boolean esArbolVacio() {
        return NodoBinario.esNodoVacio(raiz);
    }
/*--------------------------------------------------------------------------------------------------*/
/*--------------------------------NIvel------------------------------------------------*/    
    @Override
    public int nivel() {//recursivo
        return nivel(this.raiz);
    }
    private int nivel(NodoBinario<K,V> nodoActual){
        if(!NodoBinario.esNodoVacio(nodoActual)){
            int nivelIzquierda=this.nivel(nodoActual.getHijoIzquierdo());
            int nivelDerecha=this.nivel(nodoActual.getHijoDerecho());
            if(nivelIzquierda>nivelDerecha){
                return nivelIzquierda+1;
            }
            //si no
            return nivelDerecha+1;
            
        }
        //si no
        return -1;
    }
    //metodos recursivos
    //PreOrden recursivo
    @Override
    public List<K> recorridoEnPreOrden(){
        List<K> lista=new LinkedList<>();
        return recorridoEnPreOrden(this.raiz,lista);
    }
    private List<K> recorridoEnPreOrden(NodoBinario<K,V> nodo,List<K> lista){
        if(!NodoBinario.esNodoVacio(nodo)){
            lista.add(nodo.getClave());
            recorridoEnPreOrden(nodo.getHijoIzquierdo(),lista);
            recorridoEnPreOrden(nodo.getHijoDerecho(),lista);
        }return lista;
    }
    @Override
    //inOrden recursivo
    public List<K> recorridoEnInOrden(){
        List<K> lista=new LinkedList<>();
        return recorridoEnInOrden(this.raiz,lista);
    }
    private List<K> recorridoEnInOrden(NodoBinario<K,V> nodo,List<K> lista){
        if(!NodoBinario.esNodoVacio(nodo)){
            recorridoEnInOrden(nodo.getHijoIzquierdo(),lista);
            lista.add(nodo.getClave());
            recorridoEnInOrden(nodo.getHijoDerecho(),lista);
        }return lista;
    }
    //post orden recursivo
    @Override
    public List<K> recorridoEnPostOrden(){
        List<K> lista=new LinkedList<>();
        return recorridoEnPostOrden(this.raiz,lista);
    }
    
    private List<K> recorridoEnPostOrden(NodoBinario<K,V> nodo,List<K> lista){
        if(!NodoBinario.esNodoVacio(nodo)){
            recorridoEnPostOrden(nodo.getHijoIzquierdo(),lista);
            recorridoEnPostOrden(nodo.getHijoDerecho(),lista);
            lista.add(nodo.getClave());
        }return lista;
    }
    
    @Override
    public List<K> recorridoEnPorNiveles() {
        List<K> lista=new LinkedList<>();
        Queue<NodoBinario<K,V>> cola=new LinkedList<>();
        cola.offer(raiz);
        
        do{
            NodoBinario<K,V> nodoActual=cola.poll();
            lista.add(nodoActual.getClave());
            if(!nodoActual.esVacioHijoIzquierdo()){//si tiene hijo izquierdo
                cola.offer(nodoActual.getHijoIzquierdo());
            }
            if(!nodoActual.esVacioHijoDerecho()){//si tiene hijo derecho
                cola.offer(nodoActual.getHijoDerecho());
            }
        }while(!cola.isEmpty());
        return lista;
    }
    //luego agregare los recorridos iterativos
    //metodos recursivos
    //PreOrden recursivo
    @Override
    public List<V> recorridoenPreOrden(){
        List<V> lista=new LinkedList<>();
        return recorridoenPreOrden(this.raiz,lista);
    }
    private List<V> recorridoenPreOrden(NodoBinario<K,V> nodo,List<V> lista){
        if(!NodoBinario.esNodoVacio(nodo)){
            lista.add(nodo.getValor());
            recorridoenPreOrden(nodo.getHijoIzquierdo(),lista);
            recorridoenPreOrden(nodo.getHijoDerecho(),lista);
        }return lista;
    }
    @Override
    //inOrden recursivo
    public List<V> recorridoenInOrden(){
        List<V> lista=new LinkedList<>();
        return recorridoenInOrden(this.raiz,lista);
    }
    private List<V> recorridoenInOrden(NodoBinario<K,V> nodo,List<V> lista){
        if(!NodoBinario.esNodoVacio(nodo)){
            recorridoenInOrden(nodo.getHijoIzquierdo(),lista);
            lista.add(nodo.getValor());
            recorridoenInOrden(nodo.getHijoDerecho(),lista);
        }return lista;
    }
    //post orden recursivo
    @Override
    public List<V> recorridoenPostOrden(){
        List<V> lista=new LinkedList<>();
        return recorridoenPostOrden(this.raiz,lista);
    }
    
    private List<V> recorridoenPostOrden(NodoBinario<K,V> nodo,List<V> lista){
        if(!NodoBinario.esNodoVacio(nodo)){
            recorridoenPostOrden(nodo.getHijoIzquierdo(),lista);
            recorridoenPostOrden(nodo.getHijoDerecho(),lista);
            lista.add(nodo.getValor());
        }return lista;
    }
    
    @Override
    public List<V> recorridoenPorNiveles() {
        List<V> lista=new LinkedList<>();
        Queue<NodoBinario<K,V>> cola=new LinkedList<>();
        cola.offer(raiz);
        do{
            NodoBinario<K,V> nodoActual=cola.poll();
            lista.add(nodoActual.getValor());
            if(!nodoActual.esVacioHijoIzquierdo()){//si tiene hijo izquierdo
                cola.offer(nodoActual.getHijoIzquierdo());
            }
            if(!nodoActual.esVacioHijoDerecho()){//si tiene hijo derecho
                cola.offer(nodoActual.getHijoDerecho());
            }
        }while(!cola.isEmpty());
        return lista;
    }

    @Override
    public int cantidadClaves() {
       if(this.esArbolVacio()){
           return 0;
       }
       return this.recorridoEnPorNiveles().size();
    }
    
}
