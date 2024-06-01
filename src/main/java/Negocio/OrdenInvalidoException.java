/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Negocio;

/**
 *
 * @author Lenovo
 */
public class OrdenInvalidoException extends Exception{
    public OrdenInvalidoException(){
        super("Orden del arbol debe ser al menos 3");
    }
    public OrdenInvalidoException(String message){
        super(message);
    }
}
