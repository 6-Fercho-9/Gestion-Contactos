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
 import java.awt.Component;
 import java.awt.Graphics;
 import java.awt.Rectangle;
 import javax.swing.BorderFactory;
 import javax.swing.DefaultListCellRenderer;
 import javax.swing.ImageIcon;
 import javax.swing.JButton;
 import javax.swing.JComponent;
 import javax.swing.JList;
 import javax.swing.ListCellRenderer;
 import javax.swing.plaf.ComboBoxUI;
 import javax.swing.plaf.basic.BasicArrowButton;
 import javax.swing.plaf.basic.BasicComboBoxUI;

//basicamente esta clase es la que le da estilos al combobox
//con este no podemos crear el componente,pero si podemos darle estilos
 public class CustomUI extends BasicComboBoxUI{
     
     //private ImageIcon espacio =  new ImageIcon(getClass().getResource("/org/bolivia/res/espacio.png"));
     //private Color Blaxk = new Color(0,0,0);
     private static final Color color=new Color(0,0,0);//establecemos un color por defecto
     //constructor por defecto obligatorio
     public static ComboBoxUI createUI(JComponent c) {
         return new CustomUI();
     }
     //crea la el boton del combobox que viene por defecto la flechta hacia abajo
     @Override 
     protected JButton createArrowButton() {        
         /*
         public BasicArrowButton(int direction,
                Color background,//fondo
                Color shadow,//sombra
                Color darkShadow,//sombra oscura(este es el que cambia el color de la flecha)
                Color highlight)//resaltado
                Creates a BasicArrowButton whose arrow is drawn in the specified direction and with the specified colors.
                Parameters:
                direction - the direction of the arrow; one of SwingConstants.NORTH, SwingConstants.SOUTH, SwingConstants.EAST or SwingConstants.WEST
                background - the background color of the button
                shadow - the color of the shadow
                darkShadow - the color of the dark shadow
                highlight - the color of the highlight
                
         */
         //aca se crea una flecha
         
         BasicArrowButton basicArrowButton = new BasicArrowButton(BasicArrowButton.SOUTH, //Direccion de la flecha
                 Color.WHITE, //Color de fondo de la flecha
                 Color.GREEN,//no se muy bien para que sea
                 CustomUI.color,//el color de la flecha
                 Color.BLUE //highlight no se muy bien para que sea
                 );         
         //se quita el efecto 3d del boton, sombra y darkShadow no se aplican 
         //a continuacion se le da un borde al boton de la flecha por que la flecha es un boton
         basicArrowButton.setBorder(BorderFactory.createLineBorder(CustomUI.color,2,true));
         basicArrowButton.setContentAreaFilled(false);        //ni idea
         return basicArrowButton;
     }        
 
     //Se puede usar un JButton para usar un icono personalizado en lugar del arrow
     /* 
  @Override 
  protected JButton createArrowButton() { 
  JButton button = new JButton(); 
  //se quita el efecto 3d del boton, sombra y darkShadow no se aplican 
  button.setText("");
  button.setBorder(BorderFactory.createLineBorder(red,2));
  button.setContentAreaFilled(false);
  button.setIcon( new ImageIcon(getClass().getResource("/org/bolivia/res/estrella.png")) );
  return button;
  } 
  */
     // pintamos de rojo la sección donde va el item seleccionado
     //en este caso pintamos del color que este seleccionado 
     //paintCurrentValueBackground=pintar valor actual fondo
     @Override
     public void paintCurrentValueBackground(Graphics g,
                                Rectangle bounds,
                                boolean hasFocus)
     {
         g.setColor( CustomUI.color);            
                g.fillRect(bounds.x, bounds.y, bounds.width, bounds.height);
     }
       
     //Pinta los items
     //aca creo que esta la logica para saber cual esta seleccionado
     @Override
        protected ListCellRenderer createRenderer()
     {
         return new DefaultListCellRenderer() {      
             
         @Override
         public Component getListCellRendererComponent(JList list,Object value,int index,
           boolean isSelected,boolean cellHasFocus) {
       
         super.getListCellRendererComponent(list,value,index,isSelected,cellHasFocus);
         //CustomUI.color
         list.setSelectionBackground(CustomUI.color);//creo que aca a la lista del combobox le da un color
         //este if es para pintar cual esta seleccionado de un color, y por el else pinta los no seleccionados
         if (isSelected)//y al que este seleccionado lo pinta de ese color 
         {
             setBackground( Color.white);//osea el fondo lo pinta negro por que asi esta definido
             setForeground(Color.BLACK);//y el texto lo pinta blanco
         }
         else//y si no esta seleccionado ni idea(es como un evento) el ya sabe cuales no estan seleccionados
         {
             setBackground( CustomUI.color );            //a los que no estan seleccionados le mando a pintar de un fondo de color cualquiera
             //setForeground( new Color(70,70,70));
             setForeground(Color.white);////le pinto un color cualquiera a su texto de los que no estan seleccionados
         }
         /*if (index!=-1) {          
           setIcon( espacio );          
         }*/
         return this;
       }
     };
     }
 }
