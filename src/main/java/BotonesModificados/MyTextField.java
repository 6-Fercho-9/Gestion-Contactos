/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package BotonesModificados;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.RenderingHints;
import javax.swing.JTextField;
import javax.swing.border.EmptyBorder;

/**
 *
 * @author Lenovo
 */
public class MyTextField extends JTextField{
    
    private int radius;
    private Color borderColor;

    public MyTextField() {
        this(10, Color.BLACK);
    }

    public MyTextField(int columns, Color borderColor) {
        super(columns);//esto se ejecuta ccuando se pasa parametros al crear el objeto
        this.radius = 20; // Radio del borde redondeado
        this.borderColor = borderColor;
        setOpaque(false); // Hace que el fondo del JTextField sea transparente para que se pueda ver el borde personalizado
        setBorder(new EmptyBorder(5, 10, 5, 10)); // Añade un margen interno al JTextField para que el texto no esté pegado al borde
    }

    @Override
    protected void paintComponent(Graphics g) {
        if (!isOpaque() && getBorder() instanceof EmptyBorder) {
            Graphics2D g2 = (Graphics2D) g.create();
            g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
            g2.setColor(getBackground()); // Color de fondo del JTextField
            g2.fillRoundRect(0, 0, getWidth(), getHeight(), radius, radius); // Dibuja el fondo redondeado
            g2.setColor(borderColor); // Color del borde del JTextField
            g2.drawRoundRect(0, 0, getWidth() - 1, getHeight() - 1, radius, radius); // Dibuja el borde redondeado
            g2.dispose();
        }
        super.paintComponent(g);
    }

    public void setBorderColor(Color borderColor) {
        this.borderColor = borderColor;
        repaint(); // Vuelve a dibujar el JTextField para reflejar el cambio en el color del borde
    }
}
