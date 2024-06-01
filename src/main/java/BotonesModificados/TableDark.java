/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package BotonesModificados;

import java.awt.Color;
import java.awt.Component;
import java.awt.Dimension;
import java.awt.Font;
import java.util.HashMap;
import java.util.Map;
import javax.swing.BorderFactory;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.border.EmptyBorder;
import javax.swing.table.DefaultTableCellRenderer;
import static javax.swing.text.StyleConstants.Bold;

public class TableDark extends JTable {

    public TableDark(){
        getTableHeader().setDefaultRenderer(new TableDarkHeader());//para el header
        getTableHeader().setPreferredSize(new Dimension(0,35));
        setDefaultRenderer(Object.class,new TableDarkCell());//le envio el modelo de la tabla darkCells para las celdas
        
        this.setRowHeight(30);//para ampliar el tamaño de las filas
    }
    private class TableDarkHeader extends DefaultTableCellRenderer {//clase para El header del Jtable
        @Override
        //el int i=fila,int il=columna
        public Component getTableCellRendererComponent(JTable jtable, Object o, boolean bln, boolean bln1, int i, int i1) {
            Component com = super.getTableCellRendererComponent(jtable, o, bln, bln1, i, i1);
            com.setBackground(new Color(30, 30, 30));//aca le cambio el color del fondo a un negro no tan profundo
            com.setForeground(new Color(200, 200, 200));//y le camio las letras de cada celda del header a un blanco
 //           com.setFont(com.getFont().deriveFont(Font.BOLD, 12));//le cambio la fuente
               com.setFont(new Font("Roboto Lt", Font.PLAIN, 17));//con esto le digo que fuente tendra por defecto
            
            return com;//retorno el componente header (creo)
        
        }
    }//para el header del jtable
    private class TableDarkCell extends DefaultTableCellRenderer{//para las celdas o debajo del header
       @Override
        //el int i=fila,int il=columna
        public Component getTableCellRendererComponent(JTable jtable, Object o, boolean bln, boolean bln1, int fila, int columna) {
            Component com = super.getTableCellRendererComponent(jtable, o, bln, bln1, fila, columna);
                if(isCellSelected(fila,columna)){//si esta seleccionado en una fila y columna
                    if(fila%2==0){//para las filaspares el color sera un negro mas oscuro
                        com.setBackground(new Color(30,103,153));
                    }else{//para las filas impares el color sera un negro mas claró
                        com.setBackground(new Color(29,86,127));
                    }
                }else{
                    if(fila%2==0){//para las filaspares el color sera un negro mas oscuro
                        com.setBackground(new Color(50,50,50));
                    }else{//para las filas impares el color sera un negro mas claró
                        com.setBackground(new Color(30,30,30));
                    }
                }
                com.setForeground(new Color(200,200,200));//a todo el componente en general le doi una letra(blanca)
                setBorder(new EmptyBorder(0,5,0,5));
                return com;//retorno el componente 
        }

    }        //para lo demas del jtable
    
    public void fixTable(JScrollPane scrollBar){//con esto se podria crear un scrolbar personalizado
        scrollBar.setVerticalScrollBar(new ScrollBarCustom());
        JPanel panel=new JPanel();
        panel.setBackground(new Color(30,30,30));//el scrollbar tiene una pequeña parte arriba como un tope,entonces
        //por defecto es del color del frame,asi que ahi lo cambiamos a un negro
        scrollBar.setCorner(JScrollPane.UPPER_RIGHT_CORNER, panel);//ni idea
        scrollBar.getViewport().setBackground(new Color(30,30,30));//con esto se cambia de color a esa parte fuera de las celdas
        scrollBar.setBorder(BorderFactory.createLineBorder(new Color(60,60,60),2));//para poner un color al borde
    }

}