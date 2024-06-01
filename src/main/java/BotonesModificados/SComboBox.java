/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package BotonesModificados;

/**
 *
 * @author Lenovo
 */
import java.awt.Color;
 import java.awt.Dimension;
 import javax.swing.BorderFactory;
 import javax.swing.JComboBox;
 /**
  * @web https://www.jc-mouse.net/
  * @author Mouse
  */
 public class SComboBox extends JComboBox{
  
     /** Constructor */
     //Constructor para arrastrar este nuevo componente al frame
     //valores para que tenga por defecto el nuevo combobox
     
     public SComboBox()
     {
         Dimension dimension = new Dimension(200,32);//crea una dimension por defecto
         setPreferredSize(dimension);//le da un tamaño por defecto que es esa dimension
         setSize(dimension);      //una dimension maxima definida
         setForeground(Color.WHITE);        //creo que aca cambia el tipo de letra
         setBorder(BorderFactory.createLineBorder(new Color(71, 71, 71), 2));//le da un border
         setUI(CustomUI.createUI(this));  //aca llama a la clase custom              
         setVisible(true);//lo hace visitble
     }
 
 }
