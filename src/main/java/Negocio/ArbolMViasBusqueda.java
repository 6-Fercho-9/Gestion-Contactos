/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Negocio;

import java.io.Serializable;
import java.util.LinkedList;
import java.util.List;
import java.util.Queue;

/**
 *
 * @author Lenovo
 * proyecto
 */
public class ArbolMViasBusqueda<K extends Comparable<K>,V> implements IArbolBusqueda<K,V>,Serializable {
    protected NodoMVias<K,V> raiz;
    protected int orden;
    
    //constantes
    protected static final int ORDEN_MINIMO=3;
    protected static final int POSICION_INVALIDA=-1;
    
    //orden=M
    public ArbolMViasBusqueda(int orden) throws OrdenInvalidoException{
        if(orden<ArbolMViasBusqueda.ORDEN_MINIMO){
            throw new OrdenInvalidoException();
        }
        //si no
        this.orden=orden;
    
    }
    public ArbolMViasBusqueda(){
        this.orden=ArbolMViasBusqueda.ORDEN_MINIMO;
    
    }
    public boolean contiene(K clave){
        return this.buscarClave(clave)!=null;
    }
    public void insertarClaveValorIterativo(K clave, V valor)  {
      if(this.esArbolVacio()){
          this.raiz=new NodoMVias<>(this.orden,clave,valor);
      }else{
          //primero verifico si el elemento no esta ya insertado
          NodoMVias<K,V>  nodoAuxiliar=this.raiz;
          do{
            int posicionClaveAInsertar=buscarClaveEnNodo(clave,nodoAuxiliar);
            //si la posicion es invalida quiere decir que la clave a insertar esta en el nodoactual
            if(posicionClaveAInsertar!=ArbolMViasBusqueda.POSICION_INVALIDA){
                //si es asi entonces,solo actualiza el valor
                nodoAuxiliar.setValor(posicionClaveAInsertar, valor);
                nodoAuxiliar=NodoMVias.nodoVacio();//para que acabe el bucle
            }else{
                //si entra aca quiere decir que no encontro la clave en el nodo raiz
                //por lo tanto tengo que ver donde insertar,la insercion en el nodo es de manera ordenada
                //primero pregunto si es hoja,por que la insercion siempre es en hojas
                if(nodoAuxiliar.esHoja()){
                    //luego pregunto si no tiene las claves llenas
                    //si no tiene las claves llenas que lo inserte
                    if(!nodoAuxiliar.clavesLLenas()){
                        
                        //este metodo se encargar de insertar en el nodo de manera ordenada
                        this.insertarClaveEnNodo(clave, valor, nodoAuxiliar);
                        
                    }else{//si tiene las claves llenas
                        //creo un nuevo nodo con el dato
                        NodoMVias<K,V> nodoNuevo=new NodoMVias<>(this.orden,clave,valor);
                        //y solamente tengo que ver quien lo seteara a ese nodoNuevo
                        int posicionHijo=this.posicionhijoparaSetear(nodoAuxiliar, clave);
                        nodoAuxiliar.setHijo(posicionHijo, nodoNuevo);
                    }
                    nodoAuxiliar=NodoMVias.nodoVacio();
                }else{
                    //si no es hoja entonces tengo que ver por donde bajar
                    int posicionParabajar=this.posicionhijoparaSetear(nodoAuxiliar,clave);
                    if(nodoAuxiliar.esHijoVacio(posicionParabajar)){//si es hijo vacio en la posicion,debo crear y setearlo
                        //por que puede pasar de que no sea nodo hoja y pasar directamente al nodo,no seria lo correcto dado que agarraria un nulo y daria problemas
                        NodoMVias<K,V> nodoNuevo=new NodoMVias<>(this.orden,clave,valor);
                        //y solamente tengo que ver quien lo seteara a ese nodoNuevo
                        
                        nodoAuxiliar.setHijo(posicionParabajar, nodoNuevo);
                        nodoAuxiliar=NodoMVias.nodoVacio();//para finalizar el ciclo
                    }else{
                        nodoAuxiliar=nodoAuxiliar.getHijo(posicionParabajar);
                    }
                    
                }
            }
          }while(!NodoMVias.esNodoVacio(nodoAuxiliar));
      }
    
    }
    /*
    este metodo me dira a que hijo debo setear cuando el nodopadre tiene sus claves llenas
    si por ejeplo con  M=4,tiene 3 claves y 4 hijos 
    el nodo fuera [80,120,200]
    y quiero insertar la clave 50
    como las claves estan llenas,el 50 es menor que el 80,por lo tanto este metodo me tiene que retornar 0
    si fuese a insertar la clave 100
    este metodo debe retornarme la posicion 1 por que 100 es mayor a 80,pero menor a 120
    y si fuese 190,debe retornar la posicion 2 por que es menor a 200
    finalmente si insert    amos 500 debe retornarme 3 por que es mayor a 200 
    entonces
        
    */
    protected int posicionhijoparaSetear(NodoMVias<K,V> nodo,K clave){
        int i=0;
        for (i = 0; i < nodo.nroDeClavesNoVacias(); i++) {
            if(clave.compareTo(nodo.getClave(i))<0){
                return i;
            }
        }
        //si llega aca es por que la clave a insertar es superior a todas las del nodo
        //por lo tanto el padre debe setear al ultimo hijo
        return i;
    }
    protected void insertarClaveEnNodo(K clave,V valor,NodoMVias<K,V> nodo){
        
        this.insertarClaveEnNodo(clave, valor, nodo, 0);
    }
    //este metodo se encargar de insertar la clave y valor en el nodo de manera ordenada
    //se puede hacer iterativo puro
    private void insertarClaveEnNodo(K clave,V valor,NodoMVias<K,V> nodo,int i){
        if(nodo.esDatoVacio(i)){
            nodo.setClave(i, clave);
            nodo.setValor(i, valor);
        }else{
            
            if(clave.compareTo(nodo.getClave(i))>0){
                i++;
                insertarClaveEnNodo(clave,valor,nodo,i);
            }else{
                //para recorrer los elementos
                this.recorrerElementos(nodo, i);
                //finalmente solo lo inserto
                nodo.setClave(i, clave);
                nodo.setValor(i, valor);
            }
        }
    }
    
    //recorre elementos hasta una posicion i,dejando ese i libre para insertar o lo que sea
    private void recorrerElementos(NodoMVias<K,V> nodo,int i){
        for (int j = nodo.nroDeClavesNoVacias()-1; j >= i; j--) {
                    nodo.setClave(j+1, nodo.getClave(j));
                    nodo.setValor(j+1, nodo.getValor(j));
        }
    }
    /*
    metodo como indexOf que me retorna la posicion donde se encuentra la clave a buscar
    */
    protected int buscarClaveEnNodo(K clave,NodoMVias<K,V> nodo){
        int n=nodo.nroDeClavesNoVacias();
        for (int i = 0; i < n; i++) {
            if(clave.compareTo(nodo.getClave(i))==0){
                return i;
            }
        }
        //si no lo encontro
        return -1;
    }
    @Override
    public V eliminarClave(K clave)  {
        V valorAretornar=this.buscarClave(clave);
        if(valorAretornar==null){
            return null;
        }
        this.raiz=this.eliminarClave(this.raiz, clave);
    
        return valorAretornar;
        
    }
    //asumo que ya esta la clave a buscar
    private NodoMVias<K,V> eliminarClave(NodoMVias<K,V> nodoActual,K clave){
        //si lo encontro
        for (int i = 0; i < nodoActual.nroDeClavesNoVacias(); i++) {
            if(clave.compareTo(nodoActual.getClave(i))==0){
                if(nodoActual.esHoja()){
                    this.eliminarClaveDelNodo(clave, nodoActual);
                    
                    if(nodoActual.esDatoVacio(0)){//si al eliminar ya no hay claves en el nodo,retorna nulo
    
                        return null;
                    }
                    //si no,quiere decir que al eliminar aun hay claves en el nodo
                    return nodoActual;
                }else{//si no es hoja
                    
                    NodoMVias<K,V> nodoAEliminar;
                    //caso 2 si es nodo no hoja y tiene hijos mas adelante de su posicion
                    //le mando un i por que esta en estoy parado en esa posicion
                    if(this.hayHijosMasAdelanteDeLaPosicion(nodoActual, i)){
                        nodoAEliminar=this.obtenerSucesorInOrden(nodoActual, i);
                    }else{//si no es caso 3 que hay hijos atras de esa posicion
                        nodoAEliminar=this.obtenerAntecesorInOrden(nodoActual, i);//tambien llamado predecesor
                    }
                    K claveReemplazo=nodoAEliminar.getClave(0);
                    V valorReemplazo=nodoAEliminar.getValor(0);
                    nodoActual=this.eliminarClave(nodoActual, claveReemplazo);
                    //actualizar
                    nodoActual.setClave(i,claveReemplazo);
                    nodoActual.setValor(i, valorReemplazo);
                    return nodoActual;
                }
            }else{//si no lo encontre a la primera entonces pregunto si la clave a ekiminar es menor a la clave donde estoy parado
                //si es asi entonces lo llamo recursivamente
                if(clave.compareTo(nodoActual.getClave(i))<0){
                    NodoMVias<K,V> supuestohijo=this.eliminarClave(nodoActual.getHijo(i), clave);
                    nodoActual.setHijo(i, supuestohijo);
                    return nodoActual;
                }
                        
            }
            
        }
        
        //fin del for en el for verifico hasta la cantidad de hijos menos 1 (n-1),el hijo n lo verifico aca
        //puede pasar de que el hijo a eliminar este despues
        NodoMVias<K,V>supuestohijo=this.eliminarClave(nodoActual.getHijo(nodoActual.nroDeClavesNoVacias()), clave);
        nodoActual.setHijo(nodoActual.nroDeClavesNoVacias(), supuestohijo);
        return nodoActual;
    }
    
    public int contarHijosVaciosDelArbol(){
       // return contarHijosVaciosDelArbol(this.raiz);
       return 0;
    }
    /*private int contarHijosVaciosDelArbol(NodoMVias<K,V> nodoActual){
        int hijosvaciosIzq;
        if(NodoMVias.esNodoVacio(nodoActual)){
            return 1;
        }else if(nodoActual.esHoja()){
            return this.contarhijosVaciosDelNodo(nodoActual);
        }
        //si no
        int i=0;
        int hijosVaciosIzquierda;
        for ( i = 0; i < nodoActual.nroDeClavesNoVacias(); i++) {
            hijosVaciosIzquierda=this.contarHijosVaciosDelArbol(nodoActual.getHijo(i));
        }
        int hijosVaciosDerecha=this.contarHijosVaciosDelArbol(nodoActual.getHijo(i));
        return hijosVaciosDerecha+hijosVaciosIzquierda+this.contarhijosVaciosDelNodo(nodoActual);
    }*/
    private  int contarhijosVaciosDelNodo(NodoMVias<K,V> nodoActual){
        
        int i=0;
        int contador=0;
        for ( i = 0; i < nodoActual.nroDeClavesNoVacias(); i++) {
            if(nodoActual.esHijoVacio(i)){
                contador++;
            }
        }
        if(nodoActual.esHijoVacio(i)){
            contador++;
        }
        return contador;
    }
    //antecesor=predecesor
    //mejorar
    protected NodoMVias<K,V> obtenerAntecesorInOrden(NodoMVias<K,V> nodoActual,int i){
        
        if(nodoActual.esHijoVacio(i)){//entra aca cuando en esa posicion i no hay hijo
            return new NodoMVias<K,V>(this.orden,nodoActual.getClave(i-1),nodoActual.getValor(i-1));
        }
        //si no quiere decir que en esa posicion i,existe hijo
        nodoActual=nodoActual.getHijo(i);//bajo a la posicion
        NodoMVias<K,V> nodoAretornar=new NodoMVias<K,V>(this.orden);
        while(!NodoMVias.esNodoVacio(nodoActual)){
            int n=nodoActual.nroDeClavesNoVacias();
            nodoAretornar.setClave(0,nodoActual.getClave(n-1));
            nodoAretornar.setValor(0,nodoActual.getValor(n-1));
            nodoActual=nodoActual.getHijo(n);
        }
        return nodoAretornar;
    }
    /*
    obtiene el sucesor inorden de un nodo dado su posicion
    */
    private NodoMVias<K,V> obtenerSucesorInOrden(NodoMVias<K,V> nodoActual,int i){
        NodoMVias<K,V> nodoARetornar=null;
        //si la posicion esta en la ultima clave entonces 
        //entonces pregunto si tiene un hijo en la posicion i,si no tiene hijo en la posicion i que es la penultima
        //quiere decir que tiene un hijo en la ultima posicion(i+1)
        if(i+1==this.orden-1){//si el i es igual al orden-1 quiere decir que estoy en la ultima clave
            nodoActual=nodoActual.getHijo(i+1);//bajo al ultimo hijo
            nodoARetornar=new NodoMVias<>(this.orden,nodoActual.getClave(0),nodoActual.getValor(0));
            //puede que de problemas
        }else{
            //en el caso que entre aca es que la posicion i o la clave es menor a M o no es la ultima clave
             nodoARetornar=new NodoMVias<>(this.orden,nodoActual.getClave(i+1),nodoActual.getValor(i+1));
            nodoActual=nodoActual.getHijo(i+1);
        }
        ///para avanzar inOrden avanza a pura izquierda
        while(!NodoMVias.esNodoVacio(nodoActual)){
                nodoARetornar.setClave(0, nodoActual.getClave(0));
                nodoARetornar.setValor(0,nodoActual.getValor(0));
                nodoActual=nodoActual.getHijo(0);
        }
        
        return nodoARetornar;
        
        
        
    }
    
    /*
    este metodo retorna true si dado un nodo y su posicion,existen hijos a partir de esa posicion+1
    */
    protected boolean hayHijosMasAdelanteDeLaPosicion(NodoMVias<K,V> nodo,int pos){
        int i=pos+1;
        for ( i = pos+1; i < nodo.nroDeClavesNoVacias(); i++) {
            if(!nodo.esHijoVacio(i)){
                return true;
            }
        }
        //me falto preguntar el ultimo hijo,en este caso i sera igual al ultimo hijo
        if(!nodo.esHijoVacio(i)){
            return true;
        }
        //si no
        return false;
    }
    /*metodo para eliminar el elemento del nodo
    solo entra aca en el caso 1 si es hoja
    */
    protected void eliminarClaveDelNodo(K clave,NodoMVias<K,V> nodo){
        int posicionAEliminar=this.buscarClaveEnNodo(clave, nodo);
        int n=nodo.nroDeClavesNoVacias();
        for (int i = posicionAEliminar+1; i < n; i++) {
            nodo.setClave(i-1, nodo.getClave(i));
            nodo.setValor(i-1, nodo.getValor(i));
        }
        //para limpiar ese campo
        nodo.setClave(n-1, (K)NodoMVias.datoVacio());
        nodo.setValor(n-1, (V)NodoMVias.datoVacio());
    }
    
    protected void eliminarClaveDelNodov2(K clave,NodoMVias<K,V> nodo,int posicionAEliminar){
        int n=nodo.nroDeClavesNoVacias();
        for (int i = posicionAEliminar+1; i < n; i++) {
            nodo.setClave(i-1, nodo.getClave(i));
            nodo.setValor(i-1, nodo.getValor(i));
        }
        //para limpiar ese campo
        nodo.setClave(n-1, (K)NodoMVias.datoVacio());
        nodo.setValor(n-1, (V)NodoMVias.datoVacio());
    }
    public V buscarClaveIterativo(K clave) {
        if(clave==null){
            throw new IllegalArgumentException("Clave vacia");
        }
        if(!this.esArbolVacio()){
            NodoMVias<K,V> nodoActual=this.raiz;
            do{
                boolean cambieDeNodo=false;
                //navega por las claves
                int i=0;
                for ( i = 0; !NodoMVias.esNodoVacio(nodoActual)&&i < nodoActual.nroDeClavesNoVacias()&&!cambieDeNodo; i++) {
                    K claveActual=nodoActual.getClave(i);
                    if(clave.compareTo(claveActual)==0){
                        return nodoActual.getValor(i);
                    }
                    if(clave.compareTo(claveActual)<0){
                        cambieDeNodo=true;
                        nodoActual=nodoActual.getHijo(i);
                    }
                }
                //orden=M
                //M numero maximo de hijos
                //M-1 numero maximo de claves
                //este if es necesario por si la clave es mayor a todas las claves del nodo actual
                if(!cambieDeNodo){
                    nodoActual=nodoActual.getHijo(i);
                }
            }while(!NodoMVias.esNodoVacio(nodoActual));
        }return null;
    }

    @Override
    public int size() {
        Queue<NodoMVias<K,V>> cola=new LinkedList<>();
        cola.offer(this.raiz);
        int cantidadNodos=0;
        if(this.esArbolVacio()){
            return 0;
        }
        do{
            NodoMVias<K,V> nodoActual=cola.poll();//decolar
            cantidadNodos++;
            int i=0;
            //este for es para verificar si tiene hijos no vacios y el if de abajo igual
            for (i = 0; i < nodoActual.nroDeClavesNoVacias(); i++) {
                if(!nodoActual.esHijoVacio(i)){
                    cola.offer(nodoActual.getHijo(i));
                }
            }
            //para verificar si tiene un hijo al final
            if(!nodoActual.esHijoVacio(i)){
                cola.offer(nodoActual.getHijo(i));
            }
        }while(!cola.isEmpty());
        return cantidadNodos;
    }

    @Override
    public int altura() {
        return altura(this.raiz); 
    }
    private int altura(NodoMVias<K,V> nodoActual){
        if(!NodoMVias.esNodoVacio(nodoActual)){        
            int alturaMaxima=0;
            for (int i = 0; i < nodoActual.nroDeClavesNoVacias(); i++) {
                 int alturaHijoRelativa=this.altura(nodoActual.getHijo(i));
                 if(alturaHijoRelativa>alturaMaxima){
                     alturaMaxima=alturaHijoRelativa;
                 }
            }
            int restante=this.altura(nodoActual.getHijo(nodoActual.nroDeClavesNoVacias()));
            //si la altura por el lado izquierdo menor el ultimo hijo es mayor a ese ultimo hijo,restante
            if(alturaMaxima>restante){
                return alturaMaxima+1;//entonces retorname esa altura izquierda+1 de la raiz
            }
            //si no quiere decir que el resto tiene una altura mayor por lo tanto retorno ese restant+1 de la raiz
            return restante+1;    
        }
        return 0;
    }

    @Override
    public void vaciar() {
        this.raiz=NodoMVias.nodoVacio();
    }

    @Override
    public boolean esArbolVacio() {
       return NodoMVias.esNodoVacio(this.raiz);
    }

    @Override
    public int nivel() {
        return this.nivel(this.raiz);
    }
    private int nivel(NodoMVias<K,V>nodoActual){
        if(NodoMVias.esNodoVacio(nodoActual)){
            return -1;
        }
        int nivelMaximo=-1;
        for (int i = 0; i < nodoActual.nroDeClavesNoVacias(); i++) {
            int nivelHijoRelativo=this.nivel(nodoActual.getHijo(i));
            if(nivelHijoRelativo>nivelMaximo){
                nivelMaximo=nivelHijoRelativo;
            }
        }
        int restante=this.nivel(nodoActual.getHijo(nodoActual.nroDeClavesNoVacias()));
        //si la altura por el lado izquierdo menor el ultimo hijo es mayor a ese ultimo hijo,restante
        if(nivelMaximo>restante){
            return nivelMaximo+1;//entonces retorname esa altura izquierda+1 de la raiz
        }
        //si no quiere decir que el resto tiene una altura mayor por lo tanto retorno ese restant+1 de la raiz
        return restante+1;
        
    }

    @Override
    public List<K> recorridoEnInOrden() {
        List<K> lista=new LinkedList<>();
        return recorridoEnInOrden(this.raiz,lista);
    }
    public List<K> recorridoEnInOrden(NodoMVias<K,V> nodoActual,List<K> lista){
        if(!NodoMVias.esNodoVacio(nodoActual)){
            for (int i = 0; i < nodoActual.nroDeClavesNoVacias(); i++) {
                this.recorridoEnInOrden(nodoActual.getHijo(i),lista);
                lista.add(nodoActual.getClave(i));
            }
            this.recorridoEnInOrden(nodoActual.getHijo(nodoActual.nroDeClavesNoVacias()),lista);
        }
        
        return lista;
    }

    @Override
    public List<K> recorridoEnPreOrden() {
        List<K> lista=new LinkedList<>();
        return recorridoEnPreOrden(this.raiz,lista);
    }
    
    private List<K> recorridoEnPreOrden(NodoMVias<K,V> nodoActual,List<K> lista){
        //este for recorre toda la izquierda,todos los hijos menos el ultimo hijo
        if(!NodoMVias.esNodoVacio(nodoActual)){
            for (int i = 0; i < nodoActual.nroDeClavesNoVacias(); i++) {
                lista.add(nodoActual.getClave(i));
                this.recorridoEnPreOrden(nodoActual.getHijo(i),lista);
            
            }
            //acordate que El mvias tiene M-1 claves y M hijos,entonces,eso recorre las m-1 claves,pero faltaria 
            //procesar el hijo en la posicion M
            //para eso es este de aca
            this.recorridoEnPreOrden(nodoActual.getHijo(nodoActual.nroDeClavesNoVacias()),lista);
        }
        
        
        return lista;
        
    }
    @Override
    public List<K> recorridoEnPostOrden() {
        List<K> lista=new LinkedList<>();
        return recorridoEnPostOrden(this.raiz,lista);
    }
    private List<K> recorridoEnPostOrden(NodoMVias<K,V> nodoActual,List<K> lista){
        if(!NodoMVias.esNodoVacio(nodoActual)){
            //primero recorro es postorden todos el hijo 0
            this.recorridoEnPostOrden(nodoActual.getHijo(0), lista);
            //despues recorro en postOrden todos los hijos restantes
            for (int i = 0; i < nodoActual.nroDeClavesNoVacias(); i++) {
                this.recorridoEnPostOrden(nodoActual.getHijo(i+1), lista);
                lista.add(nodoActual.getClave(i));
            }
        }
        return lista;
    }

    @Override
    public List<K> recorridoEnPorNiveles() {
        Queue<NodoMVias<K,V>> cola=new LinkedList<>();
        List<K> lista=new LinkedList<>();
        cola.offer(this.raiz);
        
        do{
            NodoMVias<K,V> nodoActual=cola.poll();//decolar
            //y agrego todos los elementos del nodoactual que decole a la lista
            for (int i = 0; i < nodoActual.nroDeClavesNoVacias(); i++) {
                lista.add(nodoActual.getClave(i));
            }
            
            int i=0;
            //este for es para verificar si tiene hijos no vacios y el if de abajo igual
            for (i = 0; i < nodoActual.nroDeClavesNoVacias(); i++) {
                if(!nodoActual.esHijoVacio(i)){
                    cola.offer(nodoActual.getHijo(i));
                }
            }
            //para verificar si tiene un hijo al final
            if(!nodoActual.esHijoVacio(i)){
                cola.offer(nodoActual.getHijo(i));
            }
        }while(!cola.isEmpty());
        return lista;
    }
/*
    @Override
    public List<K> preOrden() {
        return null;
    }

    @Override
    public List<K> inOrden() {
        throw new UnsupportedOperationException("Not supported yet."); // Generated from nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/GeneratedMethodBody
    }

    @Override
    public List<K> postOrden() {
        throw new UnsupportedOperationException("Not supported yet."); // Generated from nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/GeneratedMethodBody
    }*/
    
    @Override
    //metodo de insercion recursiva
    public void insertarClaveValor(K claveAInsertar,V valorAInsertar){
        this.raiz=this.insertarClaveValor(this.raiz,claveAInsertar, valorAInsertar);
    }
    private NodoMVias<K,V> insertarClaveValor(NodoMVias<K,V> nodoActual,K claveAInsertar,V valorAInsertar){
        if(NodoMVias.esNodoVacio(nodoActual)){
            return new NodoMVias<K,V>(this.orden,claveAInsertar,valorAInsertar);
        }
            int posicionClaveAInsertar=this.buscarClaveEnNodo(claveAInsertar, nodoActual);
            if(posicionClaveAInsertar!=ArbolMViasBusqueda.POSICION_INVALIDA){//si es distinto a una posicion invalida(-1),quiere decir que la clave esta en el nodo,por lo tanto solo debo actualizar el valor
                nodoActual.setValor(posicionClaveAInsertar, valorAInsertar);//actualizo el valor
                return nodoActual;
            }
        //si no
        if(nodoActual.esHoja()){
            
            if(!nodoActual.clavesLLenas()){
                //aca inserta de manera ordenada
                this.insertarClaveEnNodo(claveAInsertar, valorAInsertar, nodoActual);
                return nodoActual;
            }/*else{
                int posicionPorDondeBajar=this.posicionhijoparaSetear(nodoActual, claveAInsertar);//busco por donde bajar
                NodoMVias<K,V> supuestoHijo=this.insertarClaveValorRecursivo(nodoActual.getHijo(posicionPorDondeBajar), claveAInsertar, valorAInsertar);
                nodoActual.setHijo(posicionPorDondeBajar, supuestoHijo);
                return nodoActual;
            }*/
        }
        //si las claves estan llenas entonces busca por donde bajar,o si no es hoja
        int posicionPorDondeBajar=this.posicionhijoparaSetear(nodoActual, claveAInsertar);//busco por donde bajar
        NodoMVias<K,V> supuestoHijo=this.insertarClaveValor(nodoActual.getHijo(posicionPorDondeBajar), claveAInsertar, valorAInsertar);
        nodoActual.setHijo(posicionPorDondeBajar, supuestoHijo);
        return nodoActual;
        
    }
    
    //metodo para recorrer los arboles y que retorne el valor
    @Override
    public List<V> recorridoenPreOrden() {
        List<V> lista=new LinkedList<>();
        return recorridoenPreOrden(this.raiz,lista);
    }
    
    private List<V> recorridoenPreOrden(NodoMVias<K,V> nodoActual,List<V> lista){
        //este for recorre toda la izquierda,todos los hijos menos el ultimo hijo
        if(!NodoMVias.esNodoVacio(nodoActual)){
            for (int i = 0; i < nodoActual.nroDeClavesNoVacias(); i++) {
                lista.add(nodoActual.getValor(i));
                this.recorridoenPreOrden(nodoActual.getHijo(i),lista);
            
            }
            //acordate que El mvias tiene M-1 claves y M hijos,entonces,eso recorre las m-1 claves,pero faltaria 
            //procesar el hijo en la posicion M
            //para eso es este de aca
            this.recorridoenPreOrden(nodoActual.getHijo(nodoActual.nroDeClavesNoVacias()),lista);
        }
        
        
        return lista;
        
    }

    @Override
    public List<V> recorridoenInOrden() {
        List<V> lista=new LinkedList<>();
        return recorridoenInOrden(this.raiz,lista);
    }
    public List<V> recorridoenInOrden(NodoMVias<K,V> nodoActual,List<V> lista){
        if(!NodoMVias.esNodoVacio(nodoActual)){
            for (int i = 0; i < nodoActual.nroDeClavesNoVacias(); i++) {
                this.recorridoenInOrden(nodoActual.getHijo(i),lista);
                lista.add(nodoActual.getValor(i));
            }
            this.recorridoenInOrden(nodoActual.getHijo(nodoActual.nroDeClavesNoVacias()),lista);
        }
        
        return lista;
    }

    @Override
    public List<V> recorridoenPostOrden() {
        List<V> lista=new LinkedList<>();
        return recorridoenPostOrden(this.raiz,lista);
    }
    private List<V> recorridoenPostOrden(NodoMVias<K,V> nodoActual,List<V> lista){
        if(!NodoMVias.esNodoVacio(nodoActual)){
            //primero recorro es postorden todos el hijo 0
            this.recorridoenPostOrden(nodoActual.getHijo(0), lista);
            //despues recorro en postOrden todos los hijos restantes
            for (int i = 0; i < nodoActual.nroDeClavesNoVacias(); i++) {
                this.recorridoenPostOrden(nodoActual.getHijo(i+1), lista);
                lista.add(nodoActual.getValor(i));
            }
        }
        return lista;
    }

    @Override
    public List<V> recorridoenPorNiveles() {
        Queue<NodoMVias<K,V>> cola=new LinkedList<>();
        List<V> lista=new LinkedList<>();
        cola.offer(this.raiz);
        
        do{
            NodoMVias<K,V> nodoActual=cola.poll();//decolar
            //y agrego todos los elementos del nodoactual que decole a la lista
            for (int i = 0; i < nodoActual.nroDeClavesNoVacias(); i++) {
                lista.add(nodoActual.getValor(i));
            }
            
            int i=0;
            //este for es para verificar si tiene hijos no vacios y el if de abajo igual
            for (i = 0; i < nodoActual.nroDeClavesNoVacias(); i++) {
                if(!nodoActual.esHijoVacio(i)){
                    cola.offer(nodoActual.getHijo(i));
                }
            }
            //para verificar si tiene un hijo al final
            if(!nodoActual.esHijoVacio(i)){
                cola.offer(nodoActual.getHijo(i));
            }
        }while(!cola.isEmpty());
        return lista;
    }
    @Override
    public V buscarClave(K clave){
        return buscarClave(this.raiz,clave);
    }
    private V buscarClave(NodoMVias<K,V> nodoActual,K clave){
        if(NodoMVias.esNodoVacio(nodoActual)){
            return null;
        }
        for (int i = 0; i < nodoActual.nroDeClavesNoVacias(); i++) {
            if(clave.compareTo(nodoActual.getClave(i))==0){
                return nodoActual.getValor(i);
            }
            if(clave.compareTo(nodoActual.getClave(i))<0){
                return buscarClave(nodoActual.getHijo(i),clave);
            }
        }
        //por si la clave a buscar es mayor a todos
        return buscarClave(nodoActual.getHijo(nodoActual.nroDeClavesNoVacias()),clave);
    }

    @Override
    public int cantidadClaves() {
        return this.esArbolVacio()?0:this.recorridoEnPorNiveles().size();
    }
    
}
