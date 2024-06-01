/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Datos;

import Negocio.IArbolBusqueda;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author Lenovo
 */
public class Archivo {
    public void GuardarArchivo(String ruta,IArbolBusqueda[] vectorDeArboles){
        try {
            ObjectOutputStream flujo=new ObjectOutputStream(new FileOutputStream(ruta));
           flujo.writeObject(vectorDeArboles);
           flujo.close();
        } catch (IOException ex) {
            System.out.println("Error::Archivo:GuardarArchivo...");
            System.out.println(ex.toString());
            //Logger.getLogger(Archivo.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    public IArbolBusqueda[] AbrirArchivo(String ruta){
        try {
            ObjectInputStream flujo=new ObjectInputStream(new FileInputStream(ruta));
            IArbolBusqueda[] vArbol=(IArbolBusqueda[])flujo.readObject();
           
            flujo.close();
            return vArbol;
        } catch (Exception ex) {
            System.out.println("ERROR::Archivo:AbrirArchivo");
            System.out.println(ex.toString());
          //  Logger.getLogger(Archivo.class.getName()).log(Level.SEVERE, null, ex);
        }
        return null;
    }
}
