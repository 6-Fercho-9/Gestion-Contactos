/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Negocio;

/**
 *
 * @author Lenovo
 */
//creo una clase error,y heredo de la clase ya definida Exception
public class ErrorClaveExistente extends Exception {
    //constructor por defecto solamente hago un super para llamar al constructor
    //por defecto de la clase padre
    public ErrorClaveExistente(){
        super("Error clave existente");
    }
    /***
     * constructor parametrizado con un string de argumentos
     * es decir un mensaje que me muestre cuando de error
     * @param args 
     */
    public ErrorClaveExistente(String args){
        super(args);
    }
}
