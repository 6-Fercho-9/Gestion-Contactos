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
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.RenderingHints;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import javax.swing.JButton;

public class MyButton extends JButton {

    public boolean isOver() {
        return over;
    }

    public void setOver(boolean over) {
        this.over = over;
    }

    public Color getColor() {
        return color;
    }

    public void setColor(Color color) {
        this.color = color;
        setBackground(color);
    }

    //obtener color de encima
    public Color getColorOver() {
        return colorOver;
    }
//enviar color encima
    public void setColorOver(Color colorOver) {
        this.colorOver = colorOver;
    }
//obtener color cuando se hace click
    public Color getColorClick() {
        return colorClick;
    }
//enviar el color cuando se hace click
    public void setColorClick(Color colorClick) {
        this.colorClick = colorClick;
    }
//obtener el color del borde del botom
    public Color getBorderColor() {
        return borderColor;
    }
//enviar color de borde al botom
    public void setBorderColor(Color borderColor) {
        this.borderColor = borderColor;
    }

    public int getRadius() {
        return radius;
    }

    public void setRadius(int radius) {
        this.radius = radius;
    }
    public void setColorTexto(Color c){
        this.setForeground(c);
    }
    public MyButton() {
        //  Init Color
//por defecto el color del boton es negro
        
        setColor(Color.BLACK);//parece que es para la letra
        setForeground(new Color(255,255,255));//y las letras en negro
        colorOver = new Color(255, 255, 255);//color encima
        colorClick = new Color(255, 255, 255);//color click blanco
        //colorClick = new Color(152, 184, 144);
        //borderColor = new Color(30, 136, 56);
        borderColor = new Color(255, 255, 255);//borde de color blanco
        radius=20;
        setContentAreaFilled(false);
        //  Add event mouse
        addMouseListener(new MouseAdapter() {
            
            @Override
            
            //evento cuando el mouse entra al objeto boton
            public void mouseEntered(MouseEvent me) {
                setBackground(colorOver);//pintalo  blanco
                setForeground(new Color(0,0,0));//y las letras en negro
                over = true;//y habilita la bandera para decir que esta encima
            }

            @Override
            //evento cuando el mouse sale del objeto boton
            public void mouseExited(MouseEvent me) {
                setBackground(color);
                setForeground(new Color(255,255,255));//y las letras en negro
                over = false;

            }

            @Override
            //evento cuando el se presiona el mouse
            public void mousePressed(MouseEvent me) {
                setBackground(colorClick);
                setForeground(new Color(0,0,0));
            }

            @Override
            //evento cuando se suelta de hacer click(?
            public void mouseReleased(MouseEvent me) {
                 setBackground(Color.BLACK);
                 setForeground(new Color(255,255,255));
                /*if (over) {
                    setBackground(Color.GREEN);
                    //setForeground(new Color(0,0,0));
                } else {
                    //setBackground(color);
                    setBackground(Color.BLACK);
                }*/
            }
        });
    }

    private boolean over;
    private Color color;
    private Color colorOver;
    private Color colorClick;
    private Color borderColor;
    private int radius = 0;

    @Override
    protected void paintComponent(Graphics grphcs) {
        Graphics2D g2 = (Graphics2D) grphcs;
        g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
        //  Paint Border
        g2.setColor(borderColor);
        g2.fillRoundRect(0, 0, getWidth(), getHeight(), radius, radius);
        g2.setColor(getBackground());
        //  Border set 2 Pix
        g2.fillRoundRect(2, 2, getWidth() - 4, getHeight() - 4, radius, radius);
        super.paintComponent(grphcs);
    }
}