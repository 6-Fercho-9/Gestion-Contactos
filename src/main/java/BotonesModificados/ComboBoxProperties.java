/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package BotonesModificados;

import java.awt.Color;
import java.awt.Component;
import java.awt.Graphics;
import java.awt.Rectangle;
import javax.swing.DefaultListCellRenderer;
import javax.swing.JButton;
import javax.swing.JComponent;
import javax.swing.JList;
import javax.swing.ListCellRenderer;
import javax.swing.plaf.ComboBoxUI;
import javax.swing.plaf.basic.BasicComboBoxUI;

/**
 *
 * @author Lenovo
 */
public class ComboBoxProperties extends BasicComboBoxUI{
    public static ComboBoxUI createUI(JComponent com){
        return new ComboBoxProperties();
    }
    
    //metodo para darle borde la parte inicial del combo menos a ese boton
    @Override
    public void paintCurrentValueBackground(Graphics g,Rectangle bounds,boolean hasFocus){
        g.setColor(Color.BLACK);
        g.fillRect(bounds.x, bounds.y, bounds.width, bounds.height);
    }
    
    @Override
    protected ListCellRenderer createRenderer(){
        
        return new DefaultListCellRenderer(){
                @Override
                public Component getListCellRendererComponent(JList<?> list,Object value,int index,boolean isSelected
                        ,boolean cellHasFocus){
                    super.getListCellRendererComponent(list,value,index,isSelected,cellHasFocus);
                    if(index>0){
                        list.setSelectionBackground(Color.white);//cambiar la seleccion seleccionada a blanco
                        list.setSelectionForeground(Color.BLACK);//cambiar la letra a negro
                    }
                    return this;
                }
        };
    }
    
}
