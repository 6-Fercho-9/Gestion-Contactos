/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Negocio;

import java.io.Serializable;

/**
 *
 * @author Lenovo
 */
//clase Nodo binario Generico con una clave valor
public class NodoBinario<K extends Comparable<K>,V> implements Serializable {
    private K clave;
    private V valor;
    private NodoBinario<K,V> hijoIzquierdo;
    private NodoBinario<K,V> hijoDerecho;
    public NodoBinario(){
        
    }
    public NodoBinario(K clave, V valor){
        this.clave=clave;
        this.valor=valor;       
    }
    //getters clave,valor
    public K getClave(){
        return this.clave;
    }
    public V getValor(){
        return this.valor;
    }
    //setters clave,valor
    public void setClave(K clave){
        this.clave=clave;
    }
    public void setValor(V valor){
        this.valor=valor;
    }
    //setters de los hijos
    public void setHijoIzquierdo(NodoBinario <K,V> p){
        this.hijoIzquierdo=p;
    }
    public void setHijoDerecho(NodoBinario<K,V> p){
        this.hijoDerecho=p;
    }
    //getters de los hijos,como el get enlace de la lista
    public NodoBinario<K,V> getHijoIzquierdo(){
        return this.hijoIzquierdo;
    }
    public NodoBinario<K,V> getHijoDerecho(){
        return this.hijoDerecho;
    }
    //metodo statico que solo arroja un resultado,llamado Nodo vacio,en pocas palabras es un metodo
    //que retorna un nulo
    /***
     * los metodos estaticos son de la clase no del objeto,
     * y como solo arrojan resultados
     * 
     * primero pongo el nombre de la clase no su generico y el metodo nodo vacio
     * y para utilizar el metodo debo poner el nombre de la clase. el nombre del metodo estatico
     * 
     * LO IMPORTANTE ACA ES QUE SOLAMENTE ES UN METODO QUE RETORNA UN NULO
     * @return 
     */
    public static NodoBinario nodoVacio(){
        return null;
    }
    /***
     * 
     * tambien un metodo estatico,es el metodo de saber si es un nodo vacio
     * @param nodo
     * @return 
     */
    public static boolean esNodoVacio(NodoBinario nodo){
        //comparo el nodo que mandan como parametro si es igual a un nulo
        return nodo==NodoBinario.nodoVacio();
    }   
    //este metodo si requiere una instancia
    /***
     * y para usarlo primero instancio un nodo y luego
     * nodo.esVacioHijoIzquierdo() me retornara un true si el nodo no tiene hijo izquierdo
     * me retornara un false cuando el nodo si tenga un hijo izquierdo
     * @return 
     */
    public boolean esVacioHijoIzquierdo(){
        return NodoBinario.esNodoVacio(this.getHijoIzquierdo());
    }
    public boolean esVacioHijoDerecho(){
        return NodoBinario.esNodoVacio(this.getHijoDerecho());
    }
    //requiere ser instanciado,pero aparte si el nodo no tiene ni hijo derecho,ni hijo izquierdo,entonces 
    //el nodo es hoja
    public boolean esHoja(){
        return this.esVacioHijoIzquierdo()&&this.esVacioHijoDerecho();
    }
    //este metodo retorna si el nodo tiene un solo hijo
    public boolean tieneunHijo(){
        return ((this.esVacioHijoIzquierdo()&&!this.esVacioHijoDerecho())||(!this.esVacioHijoIzquierdo()&&this.esVacioHijoDerecho()));
    }
    @Override
    public String toString(){
        return "["+this.getClave()+"|"+this.getValor()+"]";
    }
    
}
