/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package BotonesModificados;

import java.awt.Color;
import java.awt.Dimension;
import javax.swing.JLabel;
import static javax.swing.SwingConstants.CENTER;
import javax.swing.border.AbstractBorder;
/**
 *autor @lenovo
 */
//esta clase permite crear el label modificado
//para arrastrarlo directo al frame
public class CLabel extends JLabel {

   
   private int lineBorder=3; //border o grosor de borde
   private Color lineColor= Color.WHITE;//color del borde
   private AbstractBorder circleBorder = new CircleBorder(lineColor,lineBorder);       

    /** Constructor */
   //constructor para cuando arrastremos tenga dimensiones por default
     public CLabel()
     {
        Dimension d = new Dimension(100,100);//
        setSize(d);
        setPreferredSize(d);       
        setText("CLabel");
        setOpaque(true);//cuando esto es false creo que es transparente
        setHorizontalAlignment(CENTER);       
        setVisible(true);       
        setBorder(circleBorder); 
     }

    //Color de borde
    public Color getLineColor() {
        return lineColor;
    }

    public void setLineColor(Color color) {
        circleBorder = new CircleBorder(color, lineBorder);
        lineColor = color;
        setBorder(circleBorder); 
    }

    //Grosor de borde
    public int getLineBorder() {
        return lineBorder;        
    }

    public void setLineBorder(int lineBorder) {
        circleBorder = new CircleBorder(lineColor, lineBorder);
        this.lineBorder = lineBorder;        
        setBorder(circleBorder); 
    }
    //metodo para setear un borde y un color 
    public void setLineBorderColor(int lineBorder,Color color){
        circleBorder = new CircleBorder(color,lineBorder);
        this.lineBorder=lineBorder;
        this.lineColor=color;
        setBorder(circleBorder);
    }
}
