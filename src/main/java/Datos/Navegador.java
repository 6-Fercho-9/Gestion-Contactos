/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Datos;

import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JFileChooser;
import javax.swing.UIManager;
import javax.swing.UnsupportedLookAndFeelException;
import javax.swing.filechooser.FileNameExtensionFilter;
import presentacion.frame;

/**
 *
 * @author Lenovo
 */
public class Navegador {
    
    //metodo para abrir el archivo y obtener la ruta de donde eh abierto
    public String obtenerRutaDondeAbriElArchivo(){
        
        this.interfazWindows();//para que tenga un aspecto de windows
        //String Ruta = "";
        JFileChooser fileChooser=new JFileChooser();
        FileNameExtensionFilter filtro = new FileNameExtensionFilter("Archivos DAT", "dat");
        fileChooser.setFileFilter(filtro);
        int respuesta=fileChooser.showOpenDialog(null);//opendialog,para arbir un archivo
        
        return this.obtenerRutaDelFileChooser(respuesta, fileChooser);
        
    }
    //metodo para guardar la ruta y obtener donde lo guarde
    //tambien se puede guardar con solo el nombre y por defecto le agrega la extension ".dat"
    public String obtenerRutaDondeGuardeElArchivo(){
        this.interfazWindows();
        JFileChooser filechooser=new JFileChooser();
        int respuesta=filechooser.showSaveDialog(null);//para guardar un archivo
        String ruta=this.obtenerRutaDelFileChooser(respuesta, filechooser);
        return ruta==null?null:!ruta.endsWith(".dat")?ruta+=".dat":ruta;
    }
    //metodo que me retorna la ruta
    private String obtenerRutaDelFileChooser(int respuesta,JFileChooser fileChooser){
        if(respuesta==JFileChooser.APPROVE_OPTION){
            return fileChooser.getSelectedFile().getAbsolutePath();
        }else if(respuesta==JFileChooser.CANCEL_OPTION){
          return null;  
        }else{
            return null;
        }
    }
    //metodo para hacer el file chooser con una interfaz de windows y ya no la de java
    private void interfazWindows(){
        try {
            // TODO add your handling code here:
            //al agregar esto ya tiene la interfaz de windows
            
            UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
        } catch (ClassNotFoundException ex) {
            Logger.getLogger(frame.class.getName()).log(Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            Logger.getLogger(frame.class.getName()).log(Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            Logger.getLogger(frame.class.getName()).log(Level.SEVERE, null, ex);
        } catch (UnsupportedLookAndFeelException ex) {
            Logger.getLogger(frame.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
}
