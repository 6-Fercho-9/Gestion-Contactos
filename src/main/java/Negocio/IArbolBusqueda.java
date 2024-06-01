/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Interface.java to edit this template
 */
package Negocio;

import java.util.List;

/**
 *
 * @author Lenovo
 * 
 * creo una interfaz solamente para declarar los metodos
 * una firma de los metodos que tendran todas las clases arbol
 * ya ellos veran como lo implementan
 * la implementacion dependera de cada arbol
 */
public interface IArbolBusqueda<K extends Comparable<K>,V> {
    public void insertarClaveValor(K clave,V valor) throws ErrorClaveExistente;
    public V eliminarClave( K clave) throws ErrorClaveExistente;
    public V buscarClave(K clave);
    int size();//contar los nodos
    int altura();//altura del arbol
    void vaciar();
    boolean esArbolVacio();
    int nivel();
    
    //los recorridos seran en base a la Clave
    //y son funciones,que me retornara una lista de claves en su respectivo orden
    List<K> recorridoEnInOrden();
    List<K> recorridoEnPreOrden();
    List<K> recorridoEnPostOrden();
    List<K> recorridoEnPorNiveles();
    
    //recorridos para obtener los valores
    List<V> recorridoenPreOrden();
    List<V> recorridoenInOrden();
    List<V> recorridoenPostOrden();
    List<V> recorridoenPorNiveles();
    //obtener cantidad de nodos
    int cantidadClaves();
  
}
