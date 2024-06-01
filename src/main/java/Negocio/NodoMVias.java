/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Negocio;

import java.io.Serializable;
import java.util.LinkedList;
import java.util.List;

/**
 *
 * @author Lenovo
 */
public class NodoMVias <K,V> implements Serializable{
    private List<K> listaDeClaves;
    private List<V> listaDeValores;
    private List<NodoMVias<K,V>>listadeHijos;
    //retorna un nulo
    public static NodoMVias nodoVacio(){
        return null;
    }
    //retorna un nulo tipo Object
    public static Object datoVacio(){
        return null;
    }
    //para comparar si un nodo es nodo vacio o nodo nulo
    public static boolean esNodoVacio(NodoMVias nodo){
        return nodo==NodoMVias.nodoVacio();
    }
    
    //orden es como M
    //aca se instancia cada campo ,claves,valores,y hijos
    public NodoMVias(int orden){
        this.listaDeClaves=new LinkedList<>();
        this.listaDeValores=new LinkedList<>();
        this.listadeHijos=new LinkedList<>();
        /**
         * acordando que la lista de claves y valores son de m-1,aca es lo mismo
         * si m es 3 entonces las claves seran 2
         * y podra tener 3 hijos
         * en el for instancio 2 hijos
         * el ultimo lo agrego yo
         */
        for (int i = 0; i < orden-1; i++) {
            this.listaDeValores.add((V)NodoMVias.datoVacio());
            this.listaDeClaves.add((K)NodoMVias.datoVacio());
            this.listadeHijos.add(NodoMVias.nodoVacio());
        }
        this.listadeHijos.add(NodoMVias.nodoVacio());//aca lo agrego
    }
    public NodoMVias(int orden,K primerClave,V primerValor){
        this(orden);//esto hace referencia al constructor,siempre y cuando este declarado antes de usarlo
        //puedes llamar  a cualquier constructor de la clase siempre y cuando reciba los parametros adecuados
        this.listaDeClaves.set(0, primerClave);
        this.listaDeValores.set(0, primerValor);
        //this hace referencia ala instancia actual,a la que estes manejando
    }
    //suposicion que la posicion es valida
    public K getClave(int posicion){
        return this.listaDeClaves.get(posicion);
    }
    public V getValor(int posicion){
        return this.listaDeValores.get(posicion);
    }
    public void setClave(int posicion,K clave){
        //el metodo set,setea en una posicion dada un elemento ,el elemento puede ser de cualquier tipo
        this.listaDeClaves.set(posicion, clave);
    }
    public void setValor(int posicion,V valor){
        this.listaDeValores.set(posicion, valor);
    }
    //sethijo o set nodo
    public void setHijo(int posicion,NodoMVias<K,V> nodo){
    
        this.listadeHijos.set(posicion, nodo);
    }
    
    public NodoMVias<K,V> getHijo(int posicion){
        return this.listadeHijos.get(posicion);
    }
    public boolean esHijoVacio(int posicion){
        return NodoMVias.esNodoVacio(this.getHijo(posicion));
     
    }
    
    public boolean esDatoVacio(int posicion){
        return this.getValor(posicion)==NodoMVias.datoVacio();
    }
    //ojo aca puede dar lios
    public boolean esHoja(){
        for (int i = 0; i < this.listadeHijos.size(); i++) {
            if(!this.esHijoVacio(i)){
                return false;
            }
        }
        return true;
    }
    
    public int nroDeClavesNoVacias(){
        int cantidad=0;
        for (int i = 0; i < this.listaDeClaves.size(); i++) {
            if(!this.esDatoVacio(i)){
                cantidad++;
            }
        }
        return cantidad;
    }
    public int nroClavesVacias(){
        return this.listaDeClaves.size()-this.nroDeClavesNoVacias();
    }
    //retornar true si hay claves no vacias
    public boolean hayClavesNoVacias(){
        return this.nroDeClavesNoVacias()!=0;
        // return this.getClave(0)!=0 //significa que hay al menos una clave no vacia
    }
    
    public boolean clavesLLenas(){
        return this.nroDeClavesNoVacias()==this.listaDeClaves.size();
    }
    @Override
    public String toString(){
        String cadena="[";
        for (int i = 0; i < listaDeClaves.size(); i++) {
            cadena+=this.getClave(i)+"|"+this.getValor(i)+"] [";
        }
        return cadena;
            
    }
                
                
    

    
}
