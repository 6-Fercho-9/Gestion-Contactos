/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Negocio;

import java.io.Serializable;
import java.util.UUID;
import javax.swing.ImageIcon;

/**
 *
 * @author Lenovo
 */
public class Contacto implements Serializable {
    
    
    private String nombre;
    private int numero;
    private String correo;
   // private int id;//supuesta LLave Primaria
    private ImageIcon fotoDePerfil;
    private String token;
    
    public Contacto(String nombre)
    {
        this.nombre=nombre;
        this.correo="";
    }
            
    public Contacto(){
        
    }
    public Contacto(Contacto c){
        this.nombre=c.nombre;
        this.numero=c.numero;
        this.correo=c.correo;
        this.fotoDePerfil=c.fotoDePerfil;
    }
    public Contacto(String nombre,int numero,String correo){
        this.nombre=nombre;
        this.numero=numero;
        this.correo=correo;
    }
    public Contacto(String nombre,int numero,String correo,ImageIcon fotoDePerfil,int id){
        this.numero=numero;
        this.nombre=nombre;
        this.correo=correo;
        this.fotoDePerfil=fotoDePerfil;
    }
    public Contacto(String nombre,int numero,String correo,int id){
        this.numero=numero;
        this.nombre=nombre;
        this.correo=correo;
        this.fotoDePerfil=fotoDePerfil;
    }
    /*public String getClave() {
        return clave;
    }

    public void setClave(String clave) {
        this.clave = clave;
    }*/


    public ImageIcon getFotoDePerfil() {
        return fotoDePerfil;
    }

    public void setFotoDePerfil(ImageIcon fotoDePerfil) {
        this.fotoDePerfil = fotoDePerfil;
    }

    public int getNumero() {
        return numero;
    }
    //genera token con el nombre y numero
    //el token es el id,es unico e inadmisible
    
    public void generarToken(){
        this.token=this.getNombre()+this.getNumero();
    }
    public String getToken(){
        return this.token;
    }
    public void setToken(String token){
        this.token=token;
    }
    public void setNumero(int numero) {
        this.numero = numero;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getCorreo() {
        return correo;
    }

    public void setCorreo(String correo) {
        this.correo = correo;
    }
    @Override
    public String toString(){
        return "Nombre: "+this.getNombre()+"\n"
                +"Numero: "+this.getNumero()+"\n"
                +"Correo: "+this.getCorreo()+"\n"
                +"Token: "+this.getToken();
    }
 
    
}
