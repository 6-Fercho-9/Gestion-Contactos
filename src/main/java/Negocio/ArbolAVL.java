/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Negocio;

import java.io.Serializable;
import java.util.List;
import java.util.Stack;

/**
 *
 * @author Lenovo
 */
public class ArbolAVL <K extends Comparable<K>,V>extends ArbolBinarioBusqueda<K,V> implements Serializable {
    //Constantes para ver si el arbol esta balanceado o no
    //el arbol esta balanceado cuando su diferencia en altura es -1,0,1
    private static final byte RANGO_SUPERIOR=1;
    private static final byte RANGO_INFERIOR=-1;
    public ArbolAVL(){
        super();
    }
    /*public ArbolAVL(List<K> listaClavesInOrden,List<K> listaClavesNoInOrden,
            List<V> listaValoresInOrden,List<V> listaValoresNoInOrden,boolean espreOrden){
           super(listaClavesInOrden,listaClavesNoInOrden,listaValoresInOrden,
                   listaValoresNoInOrden,espreOrden);
    }*/
    //sobreescribir el metodo ,en la clase padre es decir en la clase arbol binario,tiene su propie insertar
    //aca lo sobreescribo
    @Override
    public void insertarClaveValor(K clave,V valor){
        super.raiz=this.insertarClaveValor(super.raiz, clave, valor);
    
    }
    /*
    metodo para insertar recibe un nodo,la clave a insertar y el valor a insertar
    
    este metodo lo que hace es que cuando hace el get hijo izquierdo o derecho,cuando este sea vacio,
    retorna un nodo nuevo con los parametros dados
    y como la recursion lo guarda en una pila
    
    el camino por donde inserto se guardo en el stack
    entonces el arbol se armaria desde abajo hacia arriba,o basicamente al desapilar se armaria el arbol
    
    
    ahora como este arbol es AVL,se requiere balancear
    entonces al momento que se vaya reconstruyendo debo preguntar por la altura
    */
    private NodoBinario<K,V> insertarClaveValor(NodoBinario<K,V> nodoActual,K clave,V valor){
        if(NodoBinario.esNodoVacio(nodoActual)){//si es arbol vacio,retorno
            return new NodoBinario<K,V>(clave,valor);
        }
        //si no
        if(clave.compareTo(nodoActual.getClave())<0){
            NodoBinario<K,V> supuestoHijoIzquierdo=this.insertarClaveValor(nodoActual.getHijoIzquierdo(), clave, valor);
            nodoActual.setHijoIzquierdo(supuestoHijoIzquierdo);
            return this.balancear(nodoActual);
            
        }else if(clave.compareTo(nodoActual.getClave())>0){
            NodoBinario<K,V> supuestoHijoDerecho=this.insertarClaveValor(nodoActual.getHijoDerecho(), clave, valor);
            nodoActual.setHijoDerecho(supuestoHijoDerecho);
            return this.balancear(nodoActual);
            
        }else{//quiere decir que la clave a insertar es igual
            //solo actualizo el valor
            nodoActual.setValor(valor);
            return nodoActual;
        }
        
        
    }
    /*Metodo para balancer en todo caso hacer las rotaciones recalcar que 
    el nodo a retornar debe ser el nodo a rotar para cuando vuelva a la desapiladadel insertar se setee normal
    */
    public NodoBinario<K,V> balancear(NodoBinario<K,V> nodoActual){
        int alturaIzquierda=super.altura(nodoActual.getHijoIzquierdo());
        int alturaDerecha=super.altura(nodoActual.getHijoDerecho());
        int diferencia=alturaIzquierda-alturaDerecha;
        if(diferencia>=ArbolAVL.RANGO_INFERIOR&&diferencia<=ArbolAVL.RANGO_SUPERIOR){//si esta en el rango su diferecnia entonces esta balanceado
            return nodoActual;//retorno el nodo Actual
        }else if(alturaIzquierda>alturaDerecha){//quiere decir que no esta en el rango es decir no esta balanceado,por lo tanto requiero saber 
            //cual lado es el mas largo (aca pregunto si la altura izquierda es mayor que la altura derecha)
            //si la altura izquierda es mayor que la derecha,entonces debo verificar si es rotacion simple o doble
            NodoBinario<K,V> hijoIzq=nodoActual.getHijoIzquierdo();//aca bajo al hijo izquierdo
            //saco sus alturas de ese hijo
            int hijoalturaIzquierda=super.altura(hijoIzq.getHijoIzquierdo());
            int hijoalturaDerecha=super.altura(hijoIzq.getHijoDerecho());
            //comparo si la altura contraria(derecha) es mayor que la izquierda entonces es rotacion doble derecha
            if(hijoalturaDerecha>hijoalturaIzquierda){//si la contraria(en este caso la derecha es mayor que la izq)
                return rotacionDobleDerecha(nodoActual);//rota el nieto(dentro del metodo,el metodos sabe como hacer que el nieto rote)
            }else{//rotacion simple derecha--si no es rotacion simple derecha
                return rotacionSimpleDerecha(nodoActual);//rota el hijo,dentro del metodo(el metodo sabe como hacer que el hijo rote)
            }
            
        }else{//en caso de que no se cumpla ese if quiere decir que la altura derecha es mayor que la izquierda
            //estamos en la derecha,es como que del nodo actual la altura mas grande es por derecha
            NodoBinario<K,V> hijoDer=nodoActual.getHijoDerecho();//bajo al hijo derecho
            //saco sus alturas de ese hijo
            int hijoalturaIzquierda=super.altura(hijoDer.getHijoIzquierdo());
            int hijoalturaDerecha=super.altura(hijoDer.getHijoDerecho());
            //comparo si la altura contraria en este caso es la izquierda es mayor que la derecha
            if(hijoalturaIzquierda>hijoalturaDerecha){//si es asi entonces es rotacion doble izquierda
                return rotacionDobleIzquierda(nodoActual);//rota el nieto
            }else{
                return rotacionSimpleIzquierda(nodoActual);//rota el hijo,dentro del metodo
            }
        }
    }
    /*
    rotacion simple a la derecha a mi manera,dado que en el metodo balancear tenemos el nodoArotar que es el hijo izquierdo del nodoActual
    public NodoBinario<K,V> rotacionSimpleDerecha(NodoBinario<K,V> nodoPadre,NodoBinario<K,V> nodoArotar){
        NodoBinario<K,V> hijoDerechoNodoARotar=nodoArotar.getHijoDerecho();
        nodoPadre.setHijoIzquierdo(hijoDerechoNodoARotar);
        nodoArotar.setHijoDerecho(nodoPadre);
        return nodoArotar;
        
    }
    */
    /*
    en estas rotaciones simples el nodo que rota es el hijo
    es decir que el nodoArotar es el hijo del nodo padre
    de cajon nomas le mandamos el nodo padre,pero el que rota es el hijo
    pero en las rotaciones dobles el que rota es el nieto
    este codigo se puede simplificar mas borrando el hijoDerechoNodoArotar,pero lo dejo ahi por si me olvido despues
    
    
    */
    public NodoBinario<K,V> rotacionSimpleDerecha(NodoBinario<K,V> nodoPadre){
        NodoBinario<K,V> nodoArotar=nodoPadre.getHijoIzquierdo();
        NodoBinario<K,V> hijoDerechoNodoARotar=nodoArotar.getHijoDerecho();
        nodoPadre.setHijoIzquierdo(hijoDerechoNodoARotar);
        nodoArotar.setHijoDerecho(nodoPadre);
        return nodoArotar;
  
    }
    public NodoBinario<K,V> rotacionSimpleIzquierda(NodoBinario<K,V> nodoPadre){
        NodoBinario<K,V> nodoArotar=nodoPadre.getHijoDerecho();
        NodoBinario<K,V> hijoIzquierdoNodoARotar=nodoArotar.getHijoIzquierdo();
        nodoPadre.setHijoDerecho(hijoIzquierdoNodoARotar);
        nodoArotar.setHijoIzquierdo(nodoPadre);
        return nodoArotar;
    }
    /*
    Rotacion doble izquierda=RSD+RSI
    recalcar que rota el nieto
    */
    public NodoBinario<K,V> rotacionDobleIzquierda(NodoBinario<K,V> nodoPadre){
        NodoBinario<K,V> nodoAux=this.rotacionSimpleDerecha(nodoPadre.getHijoDerecho());
        nodoPadre.setHijoDerecho(nodoAux);
        return this.rotacionSimpleIzquierda(nodoPadre);
        
    }
    /*
    Rotacion doble Derecha=RSI+RSD
    */
    public NodoBinario<K,V> rotacionDobleDerecha(NodoBinario<K,V> nodoPadre){
        NodoBinario<K,V> nodoAux=this.rotacionSimpleIzquierda(nodoPadre.getHijoIzquierdo());//aca le mando del nodo padre su hijo izquierdo y 
        //dado que las rotaciones simples rotan los hijos del parametro enviado,rotaria el hijo del hijo izquierdo del padre que es su nieto
        //justo como lo vemos en pizarra 
        //ahora cuando hacemos la rotacion simpleizquierda el nodoPadre seguira apuntando al nodo que tenia como hijo anterior
        //por lo tanto debo actualizarlo,ahora solo falta hacer rotacion simpleDerecha y le mando el nodo padre
        //a su vez como lo actualizamos en la rotacion rota su hijo y su hijo es nada mas y nada menos que el nieto
        
        nodoPadre.setHijoIzquierdo(nodoAux);
        return this.rotacionSimpleDerecha(nodoPadre);
    }
    
    /**
     * eliminar la clave pero de la manera del ing
     */
    @Override 
    public V eliminarClave(K clave){
        if(clave==null){
            throw new IllegalArgumentException("Clave vacia");
        }
        
        V valorAretornar=super.buscarClave(clave);
        if(valorAretornar!=null){
            this.raiz=this.eliminarClave(super.raiz, clave);
        }
        return valorAretornar;
    }
    private NodoBinario<K,V> eliminarClave(NodoBinario<K,V> nodoActual,K clave){
        if(clave.compareTo(nodoActual.getClave())<0){
            NodoBinario<K,V>supuestoHijoIzquierdo=this.eliminarClave(nodoActual.getHijoIzquierdo(), clave);
            nodoActual.setHijoIzquierdo(supuestoHijoIzquierdo);
            return this.balancear(nodoActual);
            
        }
        if(clave.compareTo(nodoActual.getClave())>0){
            NodoBinario<K,V> supuestoHijoDerecho=this.eliminarClave(nodoActual.getHijoDerecho(), clave);
            nodoActual.setHijoDerecho(supuestoHijoDerecho);
            return this.balancear(nodoActual);
           
        }
        //caso 1
        //si es hoja
        if(nodoActual.esHoja()){
            return NodoBinario.nodoVacio();
        }
        //caso 2
        //si tiene 1 hijo
        //en este caso si tiene solo hijo izquierdo
        if(nodoActual.esVacioHijoDerecho()&&!nodoActual.esVacioHijoIzquierdo()){
            return nodoActual.getHijoIzquierdo();
        }
        //si tiene solo hijo derecho
        if(!nodoActual.esVacioHijoDerecho()&&nodoActual.esVacioHijoIzquierdo()){
            return nodoActual.getHijoDerecho();
        }
        //si no quiere decir que es caso 3
        NodoBinario<K,V> nodoAEliminar=super.retornarUltimoIzquierda(nodoActual.getHijoDerecho());
        //le mando que busque a partir de la derecha del nodo actual
        NodoBinario<K,V> supuestonuevohijo=this.eliminarClave(nodoActual, nodoAEliminar.getClave());
        //actualizo los valores
        nodoActual.setClave(nodoAEliminar.getClave());
        nodoActual.setValor(nodoAEliminar.getValor());
        //antes le mandabamos nodoActual
        return this.balancear(supuestonuevohijo);
        
    }
    
    public void insertarClaveValorIterativo(K clave,V valor){
        Stack<NodoBinario<K,V>> pila=new Stack<>();
        if(super.esArbolVacio()){
            super.raiz=new NodoBinario<>(clave,valor);
        }else{
            NodoBinario<K,V> nodoActual=super.raiz;
            NodoBinario<K,V> nodoAnterior=NodoBinario.nodoVacio();
            do{
                if(clave.compareTo(nodoActual.getClave())<0){
                    pila.push(nodoActual);
                    nodoAnterior=nodoActual;
                    nodoActual=nodoActual.getHijoIzquierdo();
                }else if(clave.compareTo(nodoActual.getClave())>0){
                    pila.push(nodoActual);
                    nodoAnterior=nodoActual;
                    nodoActual=nodoActual.getHijoDerecho();
                }else{//SI son iguales,entonces solo actualiza el valor
                    nodoActual.setValor(valor);
                    //break ;//para forzar que acabe
                    return ;//creo que con esto igual funcaba
                }
                
            }while(!NodoBinario.esNodoVacio(nodoActual));
            
            if(clave.compareTo(nodoAnterior.getClave())<0 ){
                NodoBinario<K,V> nodo=new NodoBinario<>(clave,valor);
                nodoAnterior.setHijoIzquierdo(nodo);
            }else{
                NodoBinario<K,V> nodo=new NodoBinario<>(clave,valor);
                nodoAnterior.setHijoDerecho(nodo);
            }
            
            //ahora que ya inserte debo balancearlo
            do{
                NodoBinario<K,V> nodoPop=pila.pop();
                /*
                haciendo un repaso de que cuando se llama al metodo balancear 
                si el nodo que le mando esta balanceado es decir esta en rango,entonces solamente retorna el mismo nodo
                pero si no esta balanceado retorna el hijo o el nieto dependiendo de la rotacion que haya hecho(el que guarda eso es el nodo a rotar)
                entonces recalco que el "nodo que le mando" a que balancee es el pop de la pila
                pregunto si al llamar al metodo balanceo
                si el nodoArotar es igual al nodoPop quiere decir que el nodo esta balanceado y no debo hacer nada
                sin embargo si son diferentes quiere decir que hubo algun cambio es decir se hizo alguna rotacion
                
                ahora para saber por donde voy a setear por que el balanceo no lo enlaza al de atras eso tengo que hacerlo yo
                y ese nodo que esta atras esta en la cima de la pila,entonces debo saber por donde setear
                si por izquierda o por derecha 
                
                por ultimo el otro caso es si al hacer pop la pila es vacia,y ese nodo pop al balancearlo tambien se efectuo una rotacion
                entonces solamente habria que decirle que la raiz apunte al nodo a rotar
          
                */
                NodoBinario<K,V> nodoArotar=this.balancear(nodoPop);
                if(!pila.isEmpty()){
                    if(nodoArotar!=nodoPop){
                        if(nodoArotar.getClave().compareTo(pila.peek().getClave())<0){
                            pila.peek().setHijoIzquierdo(nodoArotar);
                        }else{
                            pila.peek().setHijoDerecho(nodoArotar);
                        }
                    }
                    
                }else{
                    if(nodoArotar!=nodoPop){
                        super.raiz=nodoArotar;
                    }
                }
            }while(!pila.isEmpty());
        }
    
    }
    public V eliminarClaveIterativo(K clave){
        Stack<NodoBinario<K,V>> pila=new Stack<>();
        V valorAretornar = null;
     
        NodoBinario<K,V> nodoActual=super.raiz;
        NodoBinario<K,V> nodoAnterior=null;
        NodoBinario<K,V> nodoAEliminar;
        NodoBinario<K,V> copiaNodoActual=null;
        //do while para buscar la clave
        do{
            nodoAEliminar=null;
            do{
                //si lo encontre
                if(clave.compareTo(nodoActual.getClave())==0){
                    break;
                }
                    pila.push(nodoActual);
                if(clave.compareTo(nodoActual.getClave())<0){
                    nodoAnterior=nodoActual;
                    nodoActual=nodoActual.getHijoIzquierdo();
                }else{
                    nodoAnterior=nodoActual;
                    nodoActual=nodoActual.getHijoDerecho();
                }

            }while(!NodoBinario.esNodoVacio(nodoActual));

            //si no pille la clave retorna nulo
            if(NodoBinario.esNodoVacio(nodoActual)){
                return null;
            }
            //pero si lo pille
            //caso 1
            if(nodoActual.esHoja()){
                if(nodoAnterior==null){
                    super.raiz=null;
                }else{
                    if(nodoActual==nodoAnterior.getHijoIzquierdo()){
                        nodoAnterior.setHijoIzquierdo(NodoBinario.nodoVacio());
                    }else{
                        nodoAnterior.setHijoDerecho(NodoBinario.nodoVacio());
                    }
                    valorAretornar=nodoActual.getValor();
                }
            }
            //caso 2
            if(nodoActual.tieneunHijo()){
                //si el nodo anteior es nulo quiere decir que el nodo a eliminar es raiz
                if(nodoAnterior==null){
                    if(!nodoActual.esVacioHijoIzquierdo()){
                        super.raiz=nodoActual.getHijoIzquierdo();
                    }else{
                        super.raiz=nodoActual.getHijoDerecho();
                    }
                }else{
                    //si el nodoactual es hijo izquierdo del nodo anterior
                    //si es asi entonces seteare por izquierda
                    //ahora solo requiero saber a quien voy a setear
                    if(nodoActual==nodoAnterior.getHijoIzquierdo()){
                        //el a quien seteare,se responde con el hijo que tenga el nodo actual,o es hijo izquierdo o derecho pero no ambas
                        if(!nodoActual.esVacioHijoIzquierdo()){
                            nodoAnterior.setHijoIzquierdo(nodoActual.getHijoIzquierdo());
                        }else{
                            nodoAnterior.setHijoIzquierdo(nodoActual.getHijoDerecho());
                        }
                    //si no quiere decir que el nodo actual es hijo derecho del nodo anterior    
                    //si es asi entonces seteare por derecha
                    }else{
                        if(!nodoActual.esVacioHijoDerecho()){
                            nodoAnterior.setHijoDerecho(nodoActual.getHijoDerecho());
                        }else{
                            nodoAnterior.setHijoDerecho(nodoActual.getHijoIzquierdo());
                        }
                    }
                }
                valorAretornar=nodoActual.getValor();
            }
            //caso 3
            //si tiene 2 hijos
            if(!nodoActual.esVacioHijoIzquierdo()&&!nodoActual.esVacioHijoDerecho()){
                nodoAEliminar=super.retornarUltimoIzquierda(nodoActual.getHijoDerecho());
                copiaNodoActual=nodoActual;//guardo la referencia del nodo actual
                //y reinicio los valores del nodo anterior y actual
                nodoAnterior=nodoActual;
                //el nodo actual donde esta parado,que vaya uno a la derecha y apartir de ahi que se reinicie  con el dowhile
                nodoActual=nodoActual.getHijoDerecho();
                //por ultimo tambien debo actualizar la clave a eliminar si no actualizo esto
                //no lo encontraria nunca
                clave=nodoAEliminar.getClave();
                
            }
        }while(!NodoBinario.esNodoVacio(nodoAEliminar));
        /**
         * si entra al caso 3 el nodoactual se modifica buscando el nuevo nodo a eliminar
         * 
         * recalcar tambien que al eliminar si quisiera tener la clave valor del nodo que elimine
         * el nodo actual lo tiene
         */
        
        if(copiaNodoActual!=null){
            valorAretornar=copiaNodoActual.getValor();
            
            copiaNodoActual.setClave(nodoActual.getClave());
            copiaNodoActual.setValor(nodoActual.getValor());
        }
        //ahora solo falta balancearlo
        this.balancear(pila);
    
        return valorAretornar;
    }
    private void balancear(Stack<NodoBinario<K,V>> pila){
        if(!pila.isEmpty()){
            do{
                NodoBinario<K,V> nodoPop=pila.pop();
                NodoBinario<K,V> nodoArotar=this.balancear(nodoPop);
                if(!pila.isEmpty()){
                    if(nodoArotar!=nodoPop){
                        if(nodoArotar.getClave().compareTo(pila.peek().getClave())<0){
                            pila.peek().setHijoIzquierdo(nodoArotar);
                        }else{
                            pila.peek().setHijoDerecho(nodoArotar);
                        }
                    }
                }else{
                    if(nodoArotar!=nodoPop){
                        super.raiz=nodoArotar;
                    }
                }
            }while(!pila.isEmpty());
        }
        
    }
    @Override
    public V buscarClave(K clave){
        return super.buscarClave(clave);
    }
    @Override
            
    public int size(){
        return super.size();
    }
    @Override
    public int altura(){//altura del arbol
        return super.altura();
    }
    @Override
    public void vaciar(){
        super.vaciar();
    }
    @Override
    public boolean esArbolVacio(){
        return super.esArbolVacio();
    }
    @Override
    
    public int nivel(){
        return super.nivel();
    }
    
    //los recorridos seran en base a la Clave
    //y son funciones,que me retornara una lista de claves en su respectivo orden
    @Override
    public List<K> recorridoEnInOrden(){
        return super.recorridoEnInOrden();
    }
    @Override
    public List<K> recorridoEnPreOrden(){
        return super.recorridoEnPreOrden();
    }
    @Override
    public List<K> recorridoEnPostOrden(){
        return super.recorridoEnPostOrden();
    }
    @Override
    public List<K> recorridoEnPorNiveles(){
        return super.recorridoEnPorNiveles();
    }
    //recorridos para que retorne valores
    @Override
    public List<V> recorridoenPreOrden() {
        return super.recorridoenPreOrden();
    }

    @Override
    public List<V> recorridoenInOrden() {
        return super.recorridoenInOrden();
    }

    @Override
    public List<V> recorridoenPostOrden() {
        return super.recorridoenPostOrden();
    }

    @Override
    public List<V> recorridoenPorNiveles() {
        return super.recorridoenPorNiveles();
    }
    @Override
    public int cantidadClaves(){
        return super.cantidadClaves();
    }
}
