/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/GUIForms/JFrame.java to edit this template
 */
package presentacion;

import BotonesModificados.ComboBoxProperties;
import BotonesModificados.TextPrompt;
import Datos.Archivo;
import Datos.Navegador;
import Negocio.ArbolAVL;
import Negocio.ArbolB;
import Negocio.ArbolBinarioBusqueda;
import Negocio.ArbolMViasBusqueda;
import Negocio.Contacto;
//import Negocio.ErrorClaveExistente;
//import Negocio.ErrorClaveExistente;
//import Negocio.ErrorClaveExistente;
//import Negocio.ErrorClaveExistente;
import Negocio.IArbolBusqueda;
import Negocio.OrdenInvalidoException;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.ItemSelectable;
import java.awt.Point;
import java.awt.Toolkit;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.geom.Ellipse2D;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.net.URL;
import java.util.List;
import java.util.Random;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.imageio.ImageIO;
import javax.swing.ImageIcon;
import javax.swing.JComboBox;
import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JOptionPane;
import static javax.swing.JOptionPane.CANCEL_OPTION;
import static javax.swing.JOptionPane.ERROR_MESSAGE;
import static javax.swing.JOptionPane.PLAIN_MESSAGE;
import javax.swing.JPanel;
import javax.swing.JTable;
import javax.swing.RowFilter;
import javax.swing.UIManager;
import javax.swing.UnsupportedLookAndFeelException;
import javax.swing.filechooser.FileNameExtensionFilter;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableRowSorter;

/**
 *
 * @author Lenovo
 */

public class frame extends javax.swing.JFrame {

    /**
     * Creates new form frame
     */
    ImagenFondo imagen=new ImagenFondo();
    IArbolBusqueda[] vectorDeArboles=new IArbolBusqueda[4];//vector de arboles para almacenar los cambios realizados
    IArbolBusqueda<String,Contacto> arbol;//arbol 
    String seleccionAnterior="Arbol Binario de Busqueda";//para una seleccion anterior(por defecto empieza aca)
     //vector de imagenes
    ImageIcon[] vectorDeImagenes=new ImageIcon[14];//vector de imagenes,para tener por defeto si no se quiere poner una foto
    DefaultTableModel defaultTable1;
    TableRowSorter<DefaultTableModel> sorter;//esto para tener un filtro como buscar
    int filaSeleccionada;//esto para capturar la filaseleccionada de un jtable
    Contacto contactoUniversal;
    String tokenUniversal;
    public frame() {
        
        this.setContentPane(imagen);//agrego un fondo al frame
        //estos 2 codigos es para que desaparesca la linea de maximizar y salir por defecto
        setUndecorated(true);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        
        initComponents();
        
       int anchoViewPort=(int) (Toolkit.getDefaultToolkit().getScreenSize().getWidth());//ancho viewport
       
        this.setLocation((int) ((anchoViewPort/2)*0.09),10);//ubicar el frame
        //lo demas son para poner un supuesto placeHolder a los textField
        TextPrompt buscarContact =new TextPrompt("Buscar Contacto",txtbuscar);
        TextPrompt nombreC =new TextPrompt("Escriba su Nombre",txtnombre);
        TextPrompt numeroC =new TextPrompt("Escriba su Numero",txtnumero);
        TextPrompt correoC =new TextPrompt("Escriba su correo",txtcorreo);
        
        //para tener la defaultatable del modelo table original
         defaultTable1=(DefaultTableModel) this.tableDark1.getModel();
         
         //le agrego un scrollpane modificado
         tableDark1.fixTable(jScrollPane1);
         //estas 3 lineas son para el filtro
         tableDark1.setAutoCreateRowSorter(true);//ni idea
         sorter=new TableRowSorter<>(defaultTable1);
         tableDark1.setRowSorter(sorter);
         
         //this.txtbuscarnumero.setVisible(f);//esto es para despues
         contactoUniversal=new Contacto();
         
        
        this.setSize(1200, 650);//darle tamaño al form
        
        //para cargar las imagenes al vector,y que se tenga una imagen en el label circular
        try {
            ///////////////////////////////////////////////////////////////////////////////
            cargarImagenes();
            this.labelImagen.setIcon(this.vectorDeImagenes[obtenerRandomEnIntervalo(0,this.vectorDeImagenes.length-1)]);//seteo la imagen por defecto tambien
        } catch (IOException ex) {
            System.out.println("Hubo errores al cargar las imagenes");
            Logger.getLogger(frame.class.getName()).log(Level.SEVERE, null, ex);
        }
        
        arbol=new ArbolBinarioBusqueda<>();
        instanciarArbolesEnELVector();
        //this.vectorDeArboles[0]=arbol;//por defecto le meto al vector sub 0 el arbol binario inicial
        //evento para el combobox
        
        this.sComboBox1.addItemListener(new ItemListener(){
            public void itemStateChanged(ItemEvent e){
                
                //el ItemEvent me proporciona o se dispara cuando se selecciona o des selecciona un item
                //entonces dado que cuando seleccionamos en el comboBox
                //se selecciona es cierto,pero tambien se deselecciona por que la lista de opciones desparece
                //entonces ocurre los 2 eventos,pero como solo me interesa cuando se selecciona
                
                
                //en general el evento o el que este seleccionado no nos sirve mucho
                //nos sirve su anterior,para eso
                //pero gracias al evento sabemos quien fue la nueva seleccion
                //el que nos sirve es el anterior para guardar los datos en el vector de arboles
                // anterior se actualiza con el nuevo valor que nos da el eevnto
                if(e.getStateChange()==ItemEvent.SELECTED){//esto hace eso(pero puede dar fallos)
                    String nuevaSeleccion=sComboBox1.getSelectedItem().toString();
                    //en base a la seleccion anterior se guarda el arbol que tenia antes de hacer el cambio
                    /*
                    ------------------------------------------------------------------------------------------
                    importante: 
                    Para esto el switch de la seleccion anterior:
                    
                    si por ejemplo estaba en el ABB
                        y selecciona el AVL entonces se guarda todos los cambios hechos en el abb
                    si esta en el AVL y se va al Arbol B entonces se guarda todos los cambios hechos en el arbol AVL
                    -----------------------------------------------------------------------------------------
                    */
                    switch(frame.this.seleccionAnterior){
                        case "Arbol Binario de Busqueda":frame.this.vectorDeArboles[0]=frame.this.arbol;break;
                        case "Arbol AVL":frame.this.vectorDeArboles[1]=frame.this.arbol;break;
                        case "Arbol Mvias":frame.this.vectorDeArboles[2]=frame.this.arbol;break;
                        case "Arbol B":frame.this.vectorDeArboles[3]=frame.this.arbol;break;
                        default://si no esta seleccionado ninguno entonces toma por defecto el arbol
                              frame.this.vectorDeArboles[0]=frame.this.arbol;  
                    }
                    frame.this.seleccionAnterior=nuevaSeleccion;//actualiza la nueva seleccion
                   //la logica es similar al nodo anterior y nodo actual
                   /*
                   ---------------------------------------------------------------------------
                   //para la nueva seleccion
                    dado que el arbol puede adoptar cualquier arbol
                   
                   con la nueva seleccion
                   decimos que si estamos en el arbol B y queremos ir al Arbol binario
                   entonces le digo al arbol que tiene que adoptar la forma de un arbol Binario
                   
                   ---------------------------------------------------------------------------
                   //
                   */
                   switch(nuevaSeleccion){
                       case "Arbol Binario de Busqueda":frame.this.arbol=frame.this.vectorDeArboles[0];break;
                        case "Arbol AVL":frame.this.arbol=frame.this.vectorDeArboles[1];break;
                        case "Arbol Mvias":frame.this.arbol=frame.this.vectorDeArboles[2];break;
                        case "Arbol B":frame.this.arbol=frame.this.vectorDeArboles[3];break;
                        default://si no esta seleccionado ninguno entonces toma por defecto el arbol
                          frame.this.arbol=frame.this.vectorDeArboles[0] ;  
                   }
                   //obviamente el cambio de arbol se hacer normal,pero debo tambien reiniciar los datos
                   //que tiene el jtable en todo caso
                   reiniciarDatosParaCambioDeArbol();
                   frame.this.btnAgregar.setText("Agregar");
                }
                
                
            }
        });
        this.sComboBox2.addItemListener(new ItemListener(){//evento para el combobox que actuara como menu
            public void itemStateChanged(ItemEvent e){
                
                if(e.getStateChange()==ItemEvent.SELECTED){//con esto me se cual esta seleccionado
                   String seleccion=e.getItem().toString();
                    switch(seleccion){
                        case "Size":
                            JOptionPane.showMessageDialog(rootPane, "La cantidad de nodos es: "+arbol.size(), "Arbol-Metodo-Size", PLAIN_MESSAGE);
                            frame.this.sComboBox2.setSelectedIndex(0);break;
                        case "Nivel":
                            JOptionPane.showMessageDialog(rootPane, "El nivel del Arbol es: "+arbol.nivel(), "Arbol-Metodo-Nivel", PLAIN_MESSAGE);
                            frame.this.sComboBox2.setSelectedIndex(0);break;
                        case "Altura":
                            JOptionPane.showMessageDialog(rootPane, "La Altura del Arbol es: "+arbol.altura(), "Arbol-Metodo-Altura", JOptionPane.PLAIN_MESSAGE);
                                    frame.this.sComboBox2.setSelectedIndex(0);
                                    break;
                        case "Orden Arbol B":
                             String input=JOptionPane.showInputDialog(rootPane, "Introduzca el orden: ", "Arbol-metodo-instanciarOrden", JOptionPane.QUESTION_MESSAGE);
                             if(input==null){
                                 JOptionPane.showMessageDialog(rootPane, "ERROR..CAMPOS VACIOS", "ERROR", JOptionPane.ERROR_MESSAGE);
                                 break;
                             }
                             if(!input.isEmpty()){
                                int orden=Integer.parseInt(input);
                                if(orden>2){
                                    try {
                                        frame.this.defaultTable1.setRowCount(0);
                                        frame.this.defaultTable1.setRowCount(0);
                                        frame.this.arbol.vaciar();
                                        frame.this.vectorDeArboles[3]=new ArbolB<>(orden);
                                    } catch (OrdenInvalidoException ex) {
                                        Logger.getLogger(frame.class.getName()).log(Level.SEVERE, null, ex);
                                    }
                                }else{
                                    JOptionPane.showMessageDialog(rootPane, "ERROR..ORDEN INVALIDO", "ERROR", JOptionPane.ERROR_MESSAGE);
                                }
                            }else{
                                JOptionPane.showMessageDialog(rootPane, "ERROR..CAMPOS VACIOS", "ERROR", JOptionPane.ERROR_MESSAGE);
                            }
                            frame.this.sComboBox2.setSelectedIndex(0);
                        break;
                        case "Orden Arbol MVias":
                            String input2=JOptionPane.showInputDialog(rootPane, "Introduzca el orden: ", "Arbol-metodo-instanciarOrden", JOptionPane.QUESTION_MESSAGE);
                            if(input2==null){
                                 //JOptionPane.showMessageDialog(rootPane, "Error..Campos vacios..");
                                 JOptionPane.showMessageDialog(rootPane, "ERROR..CAMPOS VACIOS", "ERROR", JOptionPane.ERROR_MESSAGE);
                                 break;
                            }
                            if(!input2.isEmpty()){
                                int orden=Integer.parseInt(input2);
                                if(orden>2){
                                    try {
                                        frame.this.defaultTable1.setRowCount(0);
                                        frame.this.defaultTable1.setRowCount(0);
                                        frame.this.arbol.vaciar();
                                        frame.this.vectorDeArboles[2]=new ArbolMViasBusqueda<>(orden);
                                    } catch (OrdenInvalidoException ex) {
                                        Logger.getLogger(frame.class.getName()).log(Level.SEVERE, null, ex);
                                    }
                                }else{
                                    JOptionPane.showMessageDialog(rootPane, "ERROR..ORDEN INVALIDO", "ERROR", JOptionPane.ERROR_MESSAGE);
                                }
                            }else{
                                JOptionPane.showMessageDialog(rootPane, "ERROR..CAMPOS VACIOS", "ERROR", JOptionPane.ERROR_MESSAGE);
                            }
                            frame.this.sComboBox2.setSelectedIndex(0);
                        break;
                            
                    }
                }
                
                
            }
        });
        
        ocultarClavePrimariaDelTable();//crea un nuevo campo no visible parael usuario
        this.cargarDatosPorDefectoDelArchivo();
        
    }
    //metodo para cargar al vector de arboles con datos por defecto
    private void cargarDatosPorDefectoDelArchivo(){
          File archivoFile=new File("SOLO ARCHIVOS.DAT\\datosPorDefecto2.dat");
            Archivo aryi=new Archivo();
            if(archivoFile.exists()){
                
                this.vectorDeArboles=aryi.AbrirArchivo(archivoFile.getAbsolutePath());
                this.arbol=this.vectorDeArboles[0];
                this.reiniciarDatosParaCambioDeArbol(this.vectorDeArboles[0]);
            }else{
                JOptionPane.showMessageDialog(rootPane,"No se pudo abrir el archivo");
            }
    }
    private void ocultarClavePrimariaDelTable(){
        this.defaultTable1.addColumn("Clave-Primaria");
        this.tableDark1.getTableHeader().getColumnModel().getColumn(3).setMaxWidth(0);
        this.tableDark1.getTableHeader().getColumnModel().getColumn(3).setMinWidth(0);
        this.tableDark1.getTableHeader().getColumnModel().getColumn(3).setPreferredWidth(0);
          this.tableDark1.getColumnModel().getColumn(3).setMaxWidth(0);
        this.tableDark1.getColumnModel().getColumn(3).setMinWidth(0);
        this.tableDark1.getColumnModel().getColumn(3).setPreferredWidth(0);
    }
    //metodo para reiniciar datos para el cambio de arbol
    //por defecto cuando reinicia,los datos del table cambian de lugar y esto es por que se usa el recorrido por niveles
    //esto de alguna manera si afecta eso
    private void reiniciarDatosParaCambioDeArbol(){
        //importante para reiniciar se debe ingresar al table el token
        //notese que aca hago uso de un recorrido,para recuperar los datos
        this.defaultTable1.setRowCount(0);//para eliminar todas las filas del jtable
        if(!arbol.esArbolVacio()){
            List<Contacto> listaValores=arbol.recorridoenPorNiveles();
            
                for (int i = 0; i < listaValores.size(); i++) {
                    //cargo con los datos del arbol
                    this.defaultTable1.addRow(new Object[]{listaValores.get(i).getNombre(),
                    listaValores.get(i).getNumero(),listaValores.get(i).getCorreo(),listaValores.get(i).getToken()});

                }
                //setearTextoVacio_labelAleatorio();
            
        }
        setearTextoVacio_labelAleatorio();
        
        
    }
    //mismo metodo solo que,recibe un arbol
    private void reiniciarDatosParaCambioDeArbol(IArbolBusqueda arbol){
        //importante para reiniciar se debe ingresar al table el token
        //notese que aca hago uso de un recorrido,para recuperar los datos
        this.defaultTable1.setRowCount(0);//para eliminar todas las filas del jtable
        if(!arbol.esArbolVacio()){
            List<Contacto> listaValores=arbol.recorridoenPorNiveles();
            
                for (int i = 0; i < listaValores.size(); i++) {
                    //cargo con los datos del arbol
                    this.defaultTable1.addRow(new Object[]{listaValores.get(i).getNombre(),
                    listaValores.get(i).getNumero(),listaValores.get(i).getCorreo(),listaValores.get(i).getToken()});

                }
                //setearTextoVacio_labelAleatorio();
            
        }
        setearTextoVacio_labelAleatorio();
        
        
    }
    private void setearTextoVacio(){
        txtnumero.setText("");
                txtnombre.setText("");
                txtcorreo.setText("");
    }
    private void setearTextoVacio_labelAleatorio(){
        txtnumero.setText("");
                txtnombre.setText("");
                txtcorreo.setText("");
                //reseteo la imagen para que obtenga otar aleatoria
                this.labelImagen.setIcon(this.vectorDeImagenes[obtenerRandomEnIntervalo(0,this.vectorDeImagenes.length-1)]);
    }
    //esto instancia un vector de arboles
    public void instanciarArbolesEnELVector(){
        vectorDeArboles[0]=new ArbolBinarioBusqueda<>();
        vectorDeArboles[1]=new ArbolAVL<>();
        vectorDeArboles[2]=new ArbolMViasBusqueda<>();//por defecto con M=3
        vectorDeArboles[3]=new ArbolB<>();//por defecto con M=3
    }
    //este metodo supuestamente era para cuando se clikee al table que sus datos de este se pasen a los textfield
    //pero se ve raro y ya no se usa--no se usa en el proyecto
    public void ejecutarEventoTable(){
        
        this.tableDark1.addMouseListener(new MouseAdapter(){
            public void mousePressed(MouseEvent Mouse_evt){
                //aunque estas no tengo ni idea por que se ejecuta
                /*JTable table=(JTable) Mouse_evt.getSource();
                Point point=Mouse_evt.getPoint();
                int row=table.rowAtPoint(point);*/
                if(Mouse_evt.getClickCount()==1){
                    //aca seteo a los txt correspondientes del talbe obtento su valor en la fila y columna deseada
                    frame.this.txtnombre.setText((String) frame.this.tableDark1.getValueAt(frame.this.tableDark1.getSelectedRow(), 0));
                    frame.this.txtnumero.setText((String) frame.this.tableDark1.getValueAt(frame.this.tableDark1.getSelectedRow(), 1));
                    frame.this.txtcorreo.setText((String) frame.this.tableDark1.getValueAt(frame.this.tableDark1.getSelectedRow(), 2));
                    
                }
            }
        });
        
        
    }
    
    //funcion para obtener un numero rando en un intervalo dado
    private static int obtenerRandomEnIntervalo(int a, int b) {
        // Crear un objeto Random
        Random random = new Random();
        // Generar un número aleatorio entre a y b (incluyendo ambos extremos)
        return random.nextInt(b - a + 1) + a;
        
    }
    //este void es para cargar las imagenes al vector de imagenes
    public void cargarImagenes() throws IOException{
        try {
            String cadenadeUsuarios = "user";
            for (int i = 0; i < this.vectorDeImagenes.length; i++) { // para cargar las imágenes
                String imagePath = "/images/" + cadenadeUsuarios + i + ".png";
                URL imageUrl = getClass().getResource(imagePath);

                if (imageUrl == null) {
                    System.out.println("No se encontró la imagen en la ruta: " + imagePath);
                    continue;
                }

                BufferedImage bufferedImage = ImageIO.read(imageUrl);
                if (bufferedImage != null) {
                    Image resizedImage = bufferedImage.getScaledInstance(labelImagen.getWidth(), labelImagen.getHeight(), Image.SCALE_SMOOTH);
                    this.vectorDeImagenes[i] = new ImageIcon(resizedImage); // carga las imágenes con un tamaño ideal
                } else {
                    System.out.println("No se pudo leer la imagen desde la URL: " + imageUrl);
                }
            }
        } catch (Exception e) {
            System.out.println("Error al abrir el archivo");
            e.printStackTrace();
        }
    }
    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        panelContacto = new javax.swing.JPanel();
        txtnombre = new javax.swing.JTextField();
        txtnumero = new javax.swing.JTextField();
        txtcorreo = new javax.swing.JTextField();
        labelNombre = new javax.swing.JLabel();
        labelNumero = new javax.swing.JLabel();
        labelCorreo = new javax.swing.JLabel();
        btnAgregar = new BotonesModificados.MyButton();
        btnEliminar = new BotonesModificados.MyButton();
        labelImagen = new BotonesModificados.CLabel();
        btnCancelar = new BotonesModificados.MyButton();
        panelBuscar = new javax.swing.JPanel();
        txtbuscar = new javax.swing.JTextField();
        sComboBox1 = new BotonesModificados.SComboBox();
        sComboBox2 = new BotonesModificados.SComboBox();
        btnGuardarArch = new BotonesModificados.MyButton();
        btnAbrirArch = new BotonesModificados.MyButton();
        panelSalida = new javax.swing.JPanel();
        btnMinimizar = new BotonesModificados.MyButton();
        btnexit = new BotonesModificados.MyButton();
        jPanel2 = new javax.swing.JPanel();
        btnPreOrden = new BotonesModificados.MyButton();
        btnInOrden = new BotonesModificados.MyButton();
        btnPostOrden = new BotonesModificados.MyButton();
        panelMostradorContactos = new javax.swing.JPanel();
        labelContactoGigante = new javax.swing.JLabel();
        jScrollPane1 = new javax.swing.JScrollPane();
        tableDark1 = new BotonesModificados.TableDark();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
        setSize(new java.awt.Dimension(800, 200));
        getContentPane().setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        panelContacto.setFont(new java.awt.Font("Roboto Lt", 0, 18)); // NOI18N
        panelContacto.setOpaque(false);

        txtnombre.setFont(new java.awt.Font("Roboto Lt", 1, 15)); // NOI18N
        txtnombre.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txtnombreActionPerformed(evt);
            }
        });

        txtnumero.setFont(new java.awt.Font("Roboto Lt", 1, 15)); // NOI18N
        txtnumero.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                txtnumeroKeyPressed(evt);
            }
            public void keyTyped(java.awt.event.KeyEvent evt) {
                txtnumeroKeyTyped(evt);
            }
        });

        txtcorreo.setFont(new java.awt.Font("Roboto Lt", 1, 15)); // NOI18N

        labelNombre.setFont(new java.awt.Font("Roboto Lt", 0, 18)); // NOI18N
        labelNombre.setForeground(new java.awt.Color(255, 255, 255));
        labelNombre.setText("Nombre");
        labelNombre.setCursor(new java.awt.Cursor(java.awt.Cursor.DEFAULT_CURSOR));

        labelNumero.setFont(new java.awt.Font("Roboto Lt", 0, 18)); // NOI18N
        labelNumero.setForeground(new java.awt.Color(255, 255, 255));
        labelNumero.setText("Numero");

        labelCorreo.setFont(new java.awt.Font("Roboto Lt", 0, 18)); // NOI18N
        labelCorreo.setForeground(new java.awt.Color(255, 255, 255));
        labelCorreo.setText("Correo");

        btnAgregar.setText("Agregar");
        btnAgregar.setFont(new java.awt.Font("Roboto Lt", 0, 14)); // NOI18N
        btnAgregar.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseReleased(java.awt.event.MouseEvent evt) {
                btnAgregarMouseReleased(evt);
            }
        });
        btnAgregar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnAgregarActionPerformed(evt);
            }
        });

        btnEliminar.setText("Eliminar");
        btnEliminar.setFont(new java.awt.Font("Roboto Lt", 0, 14)); // NOI18N
        btnEliminar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnEliminarActionPerformed(evt);
            }
        });

        labelImagen.setText("");
        labelImagen.setOpaque(false);
        labelImagen.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                labelImagenMouseClicked(evt);
            }
            public void mousePressed(java.awt.event.MouseEvent evt) {
                labelImagenMousePressed(evt);
            }
        });
        labelImagen.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyTyped(java.awt.event.KeyEvent evt) {
                labelImagenKeyTyped(evt);
            }
        });

        btnCancelar.setText("Cancelar");
        btnCancelar.setFont(new java.awt.Font("Roboto Lt", 0, 12)); // NOI18N
        btnCancelar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnCancelarActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout panelContactoLayout = new javax.swing.GroupLayout(panelContacto);
        panelContacto.setLayout(panelContactoLayout);
        panelContactoLayout.setHorizontalGroup(
            panelContactoLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(panelContactoLayout.createSequentialGroup()
                .addGroup(panelContactoLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(panelContactoLayout.createSequentialGroup()
                        .addGap(117, 117, 117)
                        .addComponent(labelImagen, javax.swing.GroupLayout.PREFERRED_SIZE, 186, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(panelContactoLayout.createSequentialGroup()
                        .addGap(23, 23, 23)
                        .addGroup(panelContactoLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                            .addGroup(panelContactoLayout.createSequentialGroup()
                                .addComponent(btnAgregar, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addGap(59, 59, 59)
                                .addComponent(btnCancelar, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addGap(46, 46, 46)
                                .addComponent(btnEliminar, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                            .addGroup(panelContactoLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                                .addGroup(panelContactoLayout.createSequentialGroup()
                                    .addComponent(labelCorreo)
                                    .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                    .addComponent(txtcorreo, javax.swing.GroupLayout.PREFERRED_SIZE, 292, javax.swing.GroupLayout.PREFERRED_SIZE))
                                .addGroup(panelContactoLayout.createSequentialGroup()
                                    .addComponent(labelNombre)
                                    .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                    .addComponent(txtnombre, javax.swing.GroupLayout.PREFERRED_SIZE, 292, javax.swing.GroupLayout.PREFERRED_SIZE))
                                .addGroup(panelContactoLayout.createSequentialGroup()
                                    .addComponent(labelNumero)
                                    .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                    .addComponent(txtnumero, javax.swing.GroupLayout.PREFERRED_SIZE, 292, javax.swing.GroupLayout.PREFERRED_SIZE))))))
                .addContainerGap(39, Short.MAX_VALUE))
        );
        panelContactoLayout.setVerticalGroup(
            panelContactoLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(panelContactoLayout.createSequentialGroup()
                .addGap(25, 25, 25)
                .addComponent(labelImagen, javax.swing.GroupLayout.PREFERRED_SIZE, 182, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(18, 18, 18)
                .addGroup(panelContactoLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(txtnombre, javax.swing.GroupLayout.PREFERRED_SIZE, 34, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(labelNombre))
                .addGap(18, 18, 18)
                .addGroup(panelContactoLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(labelNumero)
                    .addComponent(txtnumero, javax.swing.GroupLayout.PREFERRED_SIZE, 34, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(16, 16, 16)
                .addGroup(panelContactoLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(txtcorreo, javax.swing.GroupLayout.PREFERRED_SIZE, 34, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(labelCorreo))
                .addGap(27, 27, 27)
                .addGroup(panelContactoLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(btnAgregar, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(btnEliminar, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(btnCancelar, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(43, 43, 43))
        );

        getContentPane().add(panelContacto, new org.netbeans.lib.awtextra.AbsoluteConstraints(20, 180, 430, 460));

        panelBuscar.setOpaque(false);

        txtbuscar.setFont(new java.awt.Font("Roboto Lt", 1, 15)); // NOI18N
        txtbuscar.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyReleased(java.awt.event.KeyEvent evt) {
                txtbuscarKeyReleased(evt);
            }
        });

        sComboBox1.setBorder(new javax.swing.border.LineBorder(new java.awt.Color(255, 255, 255), 2, true));
        sComboBox1.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "Arbol Binario de Busqueda", "Arbol AVL", "Arbol Mvias", "Arbol B" }));
        sComboBox1.setFont(new java.awt.Font("Roboto Lt", 0, 12)); // NOI18N
        sComboBox1.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseReleased(java.awt.event.MouseEvent evt) {
                sComboBox1MouseReleased(evt);
            }
        });

        sComboBox2.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(255, 255, 255), 2));
        sComboBox2.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "Metodos", "Size", "Nivel", "Altura", "Orden Arbol B", "Orden Arbol MVias" }));
        sComboBox2.setFont(new java.awt.Font("Roboto Lt", 0, 12)); // NOI18N
        sComboBox2.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                sComboBox2ActionPerformed(evt);
            }
        });

        btnGuardarArch.setBackground(new java.awt.Color(255, 255, 255));
        btnGuardarArch.setForeground(new java.awt.Color(0, 0, 0));
        btnGuardarArch.setText("Guardar Archivo");
        btnGuardarArch.setFont(new java.awt.Font("Roboto Cn", 0, 14)); // NOI18N
        btnGuardarArch.setRadius(0);
        btnGuardarArch.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseEntered(java.awt.event.MouseEvent evt) {
                btnGuardarArchMouseEntered(evt);
            }
            public void mouseExited(java.awt.event.MouseEvent evt) {
                btnGuardarArchMouseExited(evt);
            }
            public void mousePressed(java.awt.event.MouseEvent evt) {
                btnGuardarArchMousePressed(evt);
            }
            public void mouseReleased(java.awt.event.MouseEvent evt) {
                btnGuardarArchMouseReleased(evt);
            }
        });
        btnGuardarArch.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnGuardarArchActionPerformed(evt);
            }
        });

        btnAbrirArch.setBackground(new java.awt.Color(255, 255, 255));
        btnAbrirArch.setForeground(new java.awt.Color(0, 0, 0));
        btnAbrirArch.setText("Abrir Archivo");
        btnAbrirArch.setFont(new java.awt.Font("Roboto Cn", 0, 14)); // NOI18N
        btnAbrirArch.setRadius(0);
        btnAbrirArch.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseEntered(java.awt.event.MouseEvent evt) {
                btnAbrirArchMouseEntered(evt);
            }
            public void mouseExited(java.awt.event.MouseEvent evt) {
                btnAbrirArchMouseExited(evt);
            }
            public void mousePressed(java.awt.event.MouseEvent evt) {
                btnAbrirArchMousePressed(evt);
            }
            public void mouseReleased(java.awt.event.MouseEvent evt) {
                btnAbrirArchMouseReleased(evt);
            }
        });
        btnAbrirArch.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnAbrirArchActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout panelBuscarLayout = new javax.swing.GroupLayout(panelBuscar);
        panelBuscar.setLayout(panelBuscarLayout);
        panelBuscarLayout.setHorizontalGroup(
            panelBuscarLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(panelBuscarLayout.createSequentialGroup()
                .addGap(18, 18, 18)
                .addGroup(panelBuscarLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addComponent(sComboBox2, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(sComboBox1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(txtbuscar, javax.swing.GroupLayout.PREFERRED_SIZE, 400, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 77, Short.MAX_VALUE)
                .addGroup(panelBuscarLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(btnGuardarArch, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(btnAbrirArch, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(56, 56, 56))
        );
        panelBuscarLayout.setVerticalGroup(
            panelBuscarLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(panelBuscarLayout.createSequentialGroup()
                .addGap(18, 18, 18)
                .addGroup(panelBuscarLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(txtbuscar, javax.swing.GroupLayout.PREFERRED_SIZE, 33, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(sComboBox1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(btnGuardarArch, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(18, 18, 18)
                .addGroup(panelBuscarLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(sComboBox2, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(btnAbrirArch, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addContainerGap(29, Short.MAX_VALUE))
        );

        getContentPane().add(panelBuscar, new org.netbeans.lib.awtextra.AbsoluteConstraints(210, 40, 880, 130));

        panelSalida.setOpaque(false);

        btnMinimizar.setBackground(new java.awt.Color(255, 255, 255));
        btnMinimizar.setForeground(new java.awt.Color(0, 0, 0));
        btnMinimizar.setText("_");
        btnMinimizar.setRadius(0);
        btnMinimizar.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseEntered(java.awt.event.MouseEvent evt) {
                btnMinimizarMouseEntered(evt);
            }
            public void mouseExited(java.awt.event.MouseEvent evt) {
                btnMinimizarMouseExited(evt);
            }
            public void mousePressed(java.awt.event.MouseEvent evt) {
                btnMinimizarMousePressed(evt);
            }
        });
        btnMinimizar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnMinimizarActionPerformed(evt);
            }
        });
        panelSalida.add(btnMinimizar);

        btnexit.setBackground(new java.awt.Color(255, 255, 255));
        btnexit.setForeground(new java.awt.Color(0, 0, 0));
        btnexit.setText("X");
        btnexit.setRadius(0);
        btnexit.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseEntered(java.awt.event.MouseEvent evt) {
                btnexitMouseEntered(evt);
            }
            public void mouseExited(java.awt.event.MouseEvent evt) {
                btnexitMouseExited(evt);
            }
        });
        btnexit.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnexitActionPerformed(evt);
            }
        });
        panelSalida.add(btnexit);

        getContentPane().add(panelSalida, new org.netbeans.lib.awtextra.AbsoluteConstraints(1100, 0, 100, 60));

        jPanel2.setOpaque(false);

        btnPreOrden.setText("PreOrden");
        btnPreOrden.setFont(new java.awt.Font("Roboto Lt", 0, 12)); // NOI18N
        btnPreOrden.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnPreOrdenActionPerformed(evt);
            }
        });

        btnInOrden.setText("InOrden");
        btnInOrden.setFont(new java.awt.Font("Roboto Lt", 0, 12)); // NOI18N
        btnInOrden.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnInOrdenActionPerformed(evt);
            }
        });

        btnPostOrden.setText("PostOrden");
        btnPostOrden.setFont(new java.awt.Font("Roboto Lt", 0, 12)); // NOI18N
        btnPostOrden.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnPostOrdenActionPerformed(evt);
            }
        });

        panelMostradorContactos.setCursor(new java.awt.Cursor(java.awt.Cursor.DEFAULT_CURSOR));
        panelMostradorContactos.setOpaque(false);
        panelMostradorContactos.setLayout(new java.awt.BorderLayout());

        labelContactoGigante.setFont(new java.awt.Font("Roboto Lt", 0, 30)); // NOI18N
        labelContactoGigante.setForeground(new java.awt.Color(255, 255, 255));
        labelContactoGigante.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        labelContactoGigante.setText("CONTACTOS");
        panelMostradorContactos.add(labelContactoGigante, java.awt.BorderLayout.CENTER);

        tableDark1.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {

            },
            new String [] {
                "Nombre", "Numero", "Correo"
            }
        ) {
            boolean[] canEdit = new boolean [] {
                false, false, false
            };

            public boolean isCellEditable(int rowIndex, int columnIndex) {
                return canEdit [columnIndex];
            }
        });
        tableDark1.setFont(new java.awt.Font("Roboto Lt", 0, 16)); // NOI18N
        tableDark1.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                tableDark1MouseClicked(evt);
            }
            public void mousePressed(java.awt.event.MouseEvent evt) {
                tableDark1MousePressed(evt);
            }
        });
        jScrollPane1.setViewportView(tableDark1);

        javax.swing.GroupLayout jPanel2Layout = new javax.swing.GroupLayout(jPanel2);
        jPanel2.setLayout(jPanel2Layout);
        jPanel2Layout.setHorizontalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addGap(41, 41, 41)
                .addComponent(btnPreOrden, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(154, 154, 154)
                .addComponent(btnInOrden, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 203, Short.MAX_VALUE)
                .addComponent(btnPostOrden, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(69, 69, 69))
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(panelMostradorContactos, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(jScrollPane1))
                .addContainerGap())
        );
        jPanel2Layout.setVerticalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel2Layout.createSequentialGroup()
                .addGap(14, 14, 14)
                .addComponent(panelMostradorContactos, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jScrollPane1, javax.swing.GroupLayout.DEFAULT_SIZE, 337, Short.MAX_VALUE)
                .addGap(18, 18, 18)
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(btnInOrden, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(btnPreOrden, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(btnPostOrden, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(20, 20, 20))
        );

        getContentPane().add(jPanel2, new org.netbeans.lib.awtextra.AbsoluteConstraints(490, 170, -1, -1));

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void txtnombreActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txtnombreActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_txtnombreActionPerformed
/////////////////////////////////////////////////////////////////////////////////////////////
//PARA AGREGAR LOS DATOS DEL CONTACTO AL JTABLE
/////////////////////////////////////////////////////////////////////////////////////////////
    private void btnAgregarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnAgregarActionPerformed
        // TODO add your handling code here:
        if(!txtnumero.getText().isEmpty()&&!txtnombre.getText().isEmpty()){
            if(puedeEntrarCorreo(txtcorreo.getText())){
                int numero=Integer.parseInt(txtnumero.getText());
                String nombre=txtnombre.getText();
                String correo=txtcorreo.getText();
                //cuando esta en actualizar el valor si o si esta en el arbol
                //el evento del mouse nos dara un token,ya asi podriamos modificar el nombre,numero y demas de un usuario ya existente
                if(this.btnAgregar.getText().equals("Actualizar")){
                    ImageIcon imagenContacto=(ImageIcon) this.labelImagen.getIcon();

                    Contacto contacto=new Contacto(nombre,numero,correo);
                    contacto.setFotoDePerfil(imagenContacto);
                    contacto.setToken(tokenUniversal);
                    try {
                        
                        this.arbol.insertarClaveValor(tokenUniversal, contacto);//no deberia insertar,si no masbien actualizar
                        //tambien se puede modificar el contacto universal como es pbjeto se deberia afecta

                    } catch (Exception ex) {
                        //Logger.getLogger(frame.class.getName()).log(Level.SEVERE, null, ex);
                        System.out.println("hubo error al actualizar");
                    }
                    //actualizo la tabla
                    this.updateContact(nombre, numero, correo,tokenUniversal, filaSeleccionada);//con esto actualizao el jtable
                    setearTextoVacio_labelAleatorio();
                    this.btnAgregar.setText("Agregar");
                    return;
                }
                //este if es si viene a agregar un contacto todos felices
                //pero cuando intenta agregar manualmente digamos inserta fernando 123 con un correo @
                //pero cuando queremos actualizar manualmente el correo o lafoto se insertara un nuevo elemento 
                //al jtable,en el arbol no,pero si en el jtable
                //se puede solucionar agregando un contador a la clase arbol
                //cada que se inserte y no actualize se incrementa el contador
                //cada eliminacion se decrementa ese contador,es como un contador de nodos
                if(this.btnAgregar.getText().equals("Agregar")){
                    ImageIcon imagenContacto=(ImageIcon) this.labelImagen.getIcon();

                    Contacto contacto=new Contacto(nombre,numero,correo);
                    contacto.setFotoDePerfil(imagenContacto);
                    contacto.generarToken();
                    //cada que se agregue al table,se debe ingresar el token,dependemos mucho de eso
                    //si el token no esta en el table,basicamente no sirve el proyecto
                    int cantidadNodosAntiguos=arbol.cantidadClaves();
                    try {
                        arbol.insertarClaveValor(contacto.getToken(), contacto);
                    } catch (Exception ex) {
                        System.out.println("hubo error al agregar");
                        Logger.getLogger(frame.class.getName()).log(Level.SEVERE, null, ex);
                    }
                    int cantidadNodosNuevos=arbol.cantidadClaves();
                    if(cantidadNodosNuevos>cantidadNodosAntiguos){
                        this.defaultTable1.addRow(new Object[]{contacto.getNombre(),contacto.getNumero(),contacto.getCorreo(),contacto.getToken()});
                    }else{//si quiero hacerlo manual es decir que ya haya un fernando en la tabla,y quiero actualizarlo manual poniendo sus mismo datos
                   
                        int posicionTabledadoToken=this.buscarFilaEnTabledadoToken(contacto.getToken());
                       this.updateContact(nombre, numero, correo,tokenUniversal, posicionTabledadoToken);//con esto actualizao el jtable
                    }
                    
                    setearTextoVacio_labelAleatorio();
                    this.btnAgregar.setText("Agregar");
                    return;
                }
                
            }else{
                JOptionPane.showMessageDialog(rootPane, "Formato de correo no valido..intentelo de nuevo", "Advertencia", JOptionPane.WARNING_MESSAGE);
            }
            
        }else{
            JOptionPane.showMessageDialog(rootPane, "Error.. Datos Vacios..", "ERROR", JOptionPane.ERROR_MESSAGE);
        }
        
    }//GEN-LAST:event_btnAgregarActionPerformed
    private void updateContact(String nombre,int numero,String correo,String token,int filaSeleccionada){//para actualizar el jtable cuando haga falta
        
            this.defaultTable1.setValueAt(nombre, filaSeleccionada, 0);
            this.defaultTable1.setValueAt(numero, filaSeleccionada, 1);
            this.defaultTable1.setValueAt(correo, filaSeleccionada, 2);
            this.defaultTable1.setValueAt(token, filaSeleccionada, 3);
        
        
    }
    private static boolean puedeEntrarCorreo(String correo){
        //si esta vacio entra nomas,por que puede que el contacto no tenga correo
        if(correo.isEmpty()){
            return true;//puede entrar
        }
        //pero si tiene gmail o la estructura de un gmail
        //entonces hay que verificar si es valido el correo
        if(tieneGmail(correo)){
            return correo.charAt(0)!='@';
        }
        //si no tiene gmail entonces no puede entrar
        return false;
    }
    private static boolean tieneGmail(String correo){
        return correo.contains("@gmail.com");//retorna true si tiene ese formato de correo,si no no lo acepta
    }
////////////////////////////////////////////////////////////////////////////////////////////

////////////////////////////////////////////////////////////////////////////////////////////
    /*
    Solo info:
    //evento cuando alguien clickea solamente
    //el evento mouse cliket es cuando clickea y suelta a la vez
    
    este void lo que hace es abrir para navegar en el archivo pero con un estilo de windows
    y selecciona la imagen y a su vez lo muestra en el label
    */
    private void labelImagenMousePressed(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_labelImagenMousePressed
        // TODO add your handling code here:
                //ni idea de donde salieron los bloque try catch
        //pero basicamente es para que tenga lainterfaz de windows
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
        
        String Ruta = "";
        JFileChooser jFileChooser = new JFileChooser();//creo un objeto filechooser
       // jFileChooser.setBackground(new Color(0,0,0));
        FileNameExtensionFilter filtrado = new FileNameExtensionFilter("JPG&PNG&JPEG", "JPG", "PNG","JPEG");//esto es paraque aparesca una seleccion de que tipo de imagenes aceptara
        jFileChooser.setFileFilter(filtrado);//lo agrego al filechooser
        
            int respuesta = jFileChooser.showOpenDialog(this);//al ejecutar esto se abre la ventana y hasta que no seleccionamos o le demos cancelar no hace nada
            //le mandamos el this por que le estamos diciendo donde queremos que se muestre esa ventana de dialogo
            
        if (respuesta == JFileChooser.APPROVE_OPTION) {//si se aprueba la respuesta
            /*Ruta = jFileChooser.getSelectedFile().getPath();//obteneemos la ruta,donde se encuentra
            
            Image mImagen = new ImageIcon(Ruta).getImage();//lo guardamos en una imagen
            ImageIcon mIcono = new ImageIcon(mImagen.getScaledInstance(this.labelImagen.getWidth(), this.labelImagen.getHeight(), Image.SCALE_SMOOTH));
            this.labelImagen.setIcon(mIcono); */
            Ruta = jFileChooser.getSelectedFile().getPath();//obteneemos la ruta,donde se encuentra
            BufferedImage image;
            try {
                image = ImageIO.read(new File(Ruta));
                Image resizedImage = image.getScaledInstance(labelImagen.getWidth(), labelImagen.getHeight(), Image.SCALE_SMOOTH);
                labelImagen.setIcon(new ImageIcon(resizedImage));
                
            } catch (IOException ex) {
                
                JOptionPane.showMessageDialog(rootPane, "Error..Archivo no valido..");
                //nueva Exception
                //Logger.getLogger(frame.class.getName()).log(Level.SEVERE, null, ex);
            }
            
            
        }else if(respuesta == JFileChooser.CANCEL_OPTION){
            this.labelImagen.setIcon(this.vectorDeImagenes[obtenerRandomEnIntervalo(0,13)]);
        }else{
            
            JOptionPane.showMessageDialog(rootPane, "Error..Archivo no valido..");
        }
        


    }//GEN-LAST:event_labelImagenMousePressed

    private void labelImagenMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_labelImagenMouseClicked
        // TODO add your handling code here:
    }//GEN-LAST:event_labelImagenMouseClicked
/////////////////////////////////////////////////////////////////////////////////////////////
    //evento al clickear el boton que salga es decir que cierre el frame
    private void btnexitActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnexitActionPerformed
        // TODO add your handling code here:
         //setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
         System.exit(0);
         //dispose();//para salir de la ventana
    }//GEN-LAST:event_btnexitActionPerformed
/////////////////////////////////////////////////////////////////////////////////////////////
/////////////////////////////////////////////////////////////////////////////////////////////
    
    //evento cuando entra al objeto x o exit(boton)
    private void btnexitMouseEntered(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_btnexitMouseEntered
        // TODO add your handling code here:
        
        btnexit.setBackground(Color.RED);
        btnexit.setForeground(Color.WHITE);
    }//GEN-LAST:event_btnexitMouseEntered
/////////////////////////////////////////////////////////////////////////////////////////////
    //evento cuando salga del boton exit
    private void btnexitMouseExited(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_btnexitMouseExited
        // TODO add your handling code here:
        btnexit.setBackground(Color.WHITE);
        btnexit.setForeground(Color.BLACK);
    }//GEN-LAST:event_btnexitMouseExited
/////////////////////////////////////////////////////////////////////////////////////////////
///cuando entre al boton minimizar que cambie su color y fondo de letra
    private void btnMinimizarMouseEntered(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_btnMinimizarMouseEntered
        // TODO add your handling code here:
        btnMinimizar.setBackground(new Color(64, 207, 255));
        btnMinimizar.setForeground(Color.WHITE);
    }//GEN-LAST:event_btnMinimizarMouseEntered
/////////////////////////////////////////////////////////////////////////////////////////////
//cuando salga del boton minimizar que cambie su color y fondo de letra
    private void btnMinimizarMouseExited(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_btnMinimizarMouseExited
        // TODO add your handling code here:
        btnMinimizar.setBackground(Color.WHITE);
        btnMinimizar.setForeground(Color.BLACK);
    }//GEN-LAST:event_btnMinimizarMouseExited
/////////////////////////////////////////////////////////////////////////////////////////////
    //metodo para minimizar el frame pero sin la animacion que tiene
    private void btnMinimizarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnMinimizarActionPerformed
        // TODO add your handling code here:
        //boton para minimizar pantalla del frame
        this.setExtendedState(ICONIFIED);
    }//GEN-LAST:event_btnMinimizarActionPerformed
/////////////////////////////////////////////////////////////////////////////////////////////
    //evento MousePressed para cuando el mouse presione el boton y creo que tambien cuando lo suelta
    //minimizar que lo pinte de un color el fondo
    //y el texto del boton de otro color
    private void btnMinimizarMousePressed(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_btnMinimizarMousePressed
        // TODO add your handling code here:
        btnMinimizar.setBackground(new Color(64, 207, 255));
        btnMinimizar.setForeground(Color.WHITE);
    }//GEN-LAST:event_btnMinimizarMousePressed

    private void btnPreOrdenActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnPreOrdenActionPerformed
        // TODO add your handling code here:

        if(!this.arbol.esArbolVacio()){
            this.defaultTable1.setRowCount(0);
           List<Contacto> lista=arbol.recorridoenPreOrden();
            for (int i = 0; i < lista.size(); i++) {
                this.defaultTable1.addRow(new Object[]{lista.get(i).getNombre(),lista.get(i).getNumero(),lista.get(i).getCorreo(),lista.get(i).getToken()});
            }
            
        }
        this.setearTextoVacio_labelAleatorio();
        
    }//GEN-LAST:event_btnPreOrdenActionPerformed

    private void txtnumeroKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txtnumeroKeyPressed
        // TODO add your handling code here:
        
    }//GEN-LAST:event_txtnumeroKeyPressed

    private void labelImagenKeyTyped(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_labelImagenKeyTyped
        // TODO add your handling code here:
        
    }//GEN-LAST:event_labelImagenKeyTyped

    private void txtnumeroKeyTyped(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txtnumeroKeyTyped
        // TODO add your handling code here:
        int key=evt.getKeyChar();
        boolean num=key>=48&&key<=57;
        if(!num){
            evt.consume();
        }
    }//GEN-LAST:event_txtnumeroKeyTyped

    //evento para cuando uno teclea en el buscador
    private void txtbuscarKeyReleased(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txtbuscarKeyReleased
        // TODO add your handling code here:
        filtrar();
    }//GEN-LAST:event_txtbuscarKeyReleased

    private void tableDark1MousePressed(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_tableDark1MousePressed
        // TODO add your handling code here:
    }//GEN-LAST:event_tableDark1MousePressed

    //evento para pasar los datos a los text field 
    //aca asumo que el contacto estara en el arbol
    //y esto basicamente me carga al objeto contacto universal
    //es como acceder a una base de datos para buscar el dato que esta en el jtable
    //aca se usa el metodo buscar en el arbol
    
    //AJUSTAR!!!!!!!!!!!!!!!!
    private void tableDark1MouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_tableDark1MouseClicked
        // TODO add your handling code here:
        filaSeleccionada=this.tableDark1.rowAtPoint(evt.getPoint());//guardo la fila seleccionada
        String nombre=String.valueOf(tableDark1.getValueAt(filaSeleccionada, 0));
         int numero=(int) (tableDark1.getValueAt(filaSeleccionada, 1));
         String correo=String.valueOf(tableDark1.getValueAt(filaSeleccionada, 2));
         String token=String.valueOf(tableDark1.getValueAt(filaSeleccionada, 3));
      //   this.txtbuscar.setText(token);
         //este contacto universal me va permitir tener la foto de perfil y el id nuevamente,solo eso
       
        contactoUniversal=this.arbol.buscarClave(token);
        
        tokenUniversal=contactoUniversal.getToken();

/*        System.out.println("---------------------------------------------");
         System.out.println(contactoUniversal.getNombre());
         System.out.println(String.valueOf( contactoUniversal.getNumero()));
         System.out.println(contactoUniversal.getCorreo());
         System.out.println("---------------------------------------------");*/
        
//        System.out.println(contactoUniversal);
        this.txtnombre.setText(contactoUniversal.getNombre());
        this.txtnumero.setText(String.valueOf(contactoUniversal.getNumero()));
        this.txtcorreo.setText(contactoUniversal.getCorreo());
        this.labelImagen.setIcon(contactoUniversal.getFotoDePerfil());
        this.btnAgregar.setText("Actualizar");
        
        
    }//GEN-LAST:event_tableDark1MouseClicked

    private void sComboBox1MouseReleased(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_sComboBox1MouseReleased
        // TODO add your handling code here:
        
    }//GEN-LAST:event_sComboBox1MouseReleased

    //para cancelar la actualizacion,solamente reseteo todo
    private void btnCancelarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnCancelarActionPerformed
        // TODO add your handling code here:
        txtnumero.setText("");
        txtnombre.setText("");
        txtcorreo.setText("");
        //reseteo la imagen para que obtenga otar aleatoria
        this.labelImagen.setIcon(this.vectorDeImagenes[obtenerRandomEnIntervalo(0,this.vectorDeImagenes.length-1)]);
         this.btnAgregar.setText("Agregar");
         //this.txtnombre.setEnabled(true);
                
    }//GEN-LAST:event_btnCancelarActionPerformed

    private void btnInOrdenActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnInOrdenActionPerformed
        // TODO add your handling code here:
        
        if(!this.arbol.esArbolVacio()){
            this.defaultTable1.setRowCount(0);
           List<Contacto> lista=arbol.recorridoenInOrden();
            for (int i = 0; i < lista.size(); i++) {
                this.defaultTable1.addRow(new Object[]{lista.get(i).getNombre(),lista.get(i).getNumero(),lista.get(i).getCorreo(),lista.get(i).getToken()});
            }
            
        }
        //setearTextoVacio();
        this.setearTextoVacio_labelAleatorio();
    }//GEN-LAST:event_btnInOrdenActionPerformed

    private void btnPostOrdenActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnPostOrdenActionPerformed
        if(!this.arbol.esArbolVacio()){
            this.defaultTable1.setRowCount(0);
           List<Contacto> lista=arbol.recorridoenPostOrden();
            for (int i = 0; i < lista.size(); i++) {
                this.defaultTable1.addRow(new Object[]{lista.get(i).getNombre(),lista.get(i).getNumero(),lista.get(i).getCorreo(),lista.get(i).getToken()});
            }
            
        }
//        setearTextoVacio();
        this.setearTextoVacio_labelAleatorio();
    }//GEN-LAST:event_btnPostOrdenActionPerformed

    private void btnAgregarMouseReleased(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_btnAgregarMouseReleased
        // TODO add your handling code here:
        //this.txtnombre.setEnabled(true);
    }//GEN-LAST:event_btnAgregarMouseReleased

    private void btnEliminarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnEliminarActionPerformed
        // TODO add your handling code here:
        if(!this.arbol.esArbolVacio()){
            if(!txtnombre.getText().isEmpty()&&!txtnumero.getText().isEmpty()){//este if es por si queremos eliminar manualmente
                String nombre=txtnombre.getText();
                String numero=txtnumero.getText();
                String correo=txtcorreo.getText();
                //si el token lo selecciono deltable haria la busqueda 2 veces solo ese detalle
                
                String tokenConFilaTable=buscarTokenEnTable(nombre,numero,correo);//busca el token en el table 
                //si no esta retorna vacio,si esta retorna el token y la fila donde lo encontro,los 2 juntos
                //entonces hay que separar esos datos
                JOptionPane.showMessageDialog(rootPane, "token: "+tokenConFilaTable);
                if(!tokenConFilaTable.isEmpty()){//si no lo encuentra es vacio
                    try {
                        //si no es vacio quiere decir que existe un token asociado
                        int posicionGuion=tokenConFilaTable.indexOf("-");
                        String tokenAEliminar=tokenConFilaTable.substring(0, posicionGuion);
                        int filaAEliminar=Integer.parseInt(tokenConFilaTable.substring(posicionGuion+1,tokenConFilaTable.length()));
                        //JOptionPane.showMessageDialog(rootPane,"fila a eliminar: "+filaAEliminar+" token: "+tokenAEliminar);
                        Contacto contactoEliminado=this.arbol.eliminarClave(tokenAEliminar);
                        this.defaultTable1.removeRow(filaAEliminar);//elimina del jtable
                        this.btnAgregar.setText("Agregar");
                        setearTextoVacio_labelAleatorio();
                        JOptionPane.showMessageDialog(rootPane, "Se acaba de Eliminar al contacto "+contactoEliminado);
                    
                    } catch (Exception ex) {
                        System.out.println("Eliminar error");
                        System.out.println(ex.toString());
                        
                    }
                    
                }else{
                    JOptionPane.showMessageDialog(rootPane, "Valor no Encontrado");
                }
            }else{
                JOptionPane.showMessageDialog(rootPane, "No Hay nada que buscar..");
            }
        }
    }//GEN-LAST:event_btnEliminarActionPerformed

    private void sComboBox2ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_sComboBox2ActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_sComboBox2ActionPerformed

    private void btnGuardarArchMouseEntered(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_btnGuardarArchMouseEntered
        // TODO add your handling code here:
         btnGuardarArch.setBackground(Color.RED);
         //btnGuardarArch.setFont(new Font("Roboto",Font.BOLD,12));
        btnGuardarArch.setForeground(Color.WHITE);
    }//GEN-LAST:event_btnGuardarArchMouseEntered

    private void btnGuardarArchActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnGuardarArchActionPerformed
        // TODO add your handling code here:
        Navegador nav=new Navegador();
        Archivo aryi=new Archivo();
        String ruta=nav.obtenerRutaDondeGuardeElArchivo();
        if(ruta!=null){
            System.out.println(this.sComboBox1.getSelectedIndex());
            vectorDeArboles[this.sComboBox1.getSelectedIndex()]=this.arbol;//por si no me movi de la primer seleccion o ultima
            //dado que almacena en ram cuando yo selecciono un elemento del combobox,si por si acaso
            //seleccionase los 3 primero arboles y de ultima seleccione el B al guardar en archivo guardara todos menos el B
            //Por que su valor no lo guarde en el vector,se guardara cuando de la seleccion del arbol b me vaya a otro arbol
            //entonces esa parte es para asegurarme de tener todos los arboles cargados
            aryi.GuardarArchivo(ruta, vectorDeArboles);
           
            JOptionPane.showMessageDialog(rootPane, "Archivo Guardado Con Exito!", "Aviso", JOptionPane.INFORMATION_MESSAGE);
            //cuando guardo en un archivo tengo que reiniciar todo el arbol
            this.arbol.vaciar();
            //si solo reinicio el this.arbol no serviria mucho por que los demas cambios aun siguen en el vector
            //entonces tambien se debe reiniciar el vector
            this.vectorDeArboles[0].vaciar();
            this.vectorDeArboles[1].vaciar();
            this.vectorDeArboles[2].vaciar();
            this.vectorDeArboles[3].vaciar();
           this.arbol=vectorDeArboles[0];//por defecto empieza en el binario
           this.sComboBox1.setSelectedIndex(0);
           this.sComboBox2.setSelectedIndex(0);
           this.seleccionAnterior="Arbol Binario de Busqueda";
           //para reiniciar los datos de table
           this.defaultTable1.setRowCount(0);
        }else{
            JOptionPane.showMessageDialog(rootPane, "No se encontro La ruta..", "Advertencia", JOptionPane.WARNING_MESSAGE);
        }
        
    }//GEN-LAST:event_btnGuardarArchActionPerformed

    private void btnGuardarArchMouseExited(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_btnGuardarArchMouseExited
        // TODO add your handling code here:
        btnGuardarArch.setBackground(Color.WHITE);
        //btnGuardarArch.setFont(new Font("Roboto",Font.PLAIN,12));
        btnGuardarArch.setForeground(Color.BLACK);
    }//GEN-LAST:event_btnGuardarArchMouseExited

    //cuando presiona el boton de raton
    private void btnGuardarArchMousePressed(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_btnGuardarArchMousePressed
        // TODO add your handling code here:
        btnGuardarArch.setBackground(Color.red);
        btnGuardarArch.setForeground(Color.WHITE);
        
    }//GEN-LAST:event_btnGuardarArchMousePressed
    //cuando suelta el boton del raton
    private void btnGuardarArchMouseReleased(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_btnGuardarArchMouseReleased
        // TODO add your handling code here:
        btnGuardarArch.setBackground(Color.WHITE);
        //btnGuardarArch.setFont(new Font("Roboto",Font.PLAIN,12));
        btnGuardarArch.setForeground(Color.BLACK);
    }//GEN-LAST:event_btnGuardarArchMouseReleased

    private void btnAbrirArchMouseEntered(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_btnAbrirArchMouseEntered
        // TODO add your handling code here:
          btnAbrirArch.setBackground(Color.RED);
         //btnGuardarArch.setFont(new Font("Roboto",Font.BOLD,12));
        btnAbrirArch.setForeground(Color.WHITE);
        
    }//GEN-LAST:event_btnAbrirArchMouseEntered

    private void btnAbrirArchMouseExited(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_btnAbrirArchMouseExited
        // TODO add your handling code here:
         btnAbrirArch.setBackground(Color.WHITE);
        //btnGuardarArch.setFont(new Font("Roboto",Font.PLAIN,12));
        btnAbrirArch.setForeground(Color.BLACK);
    }//GEN-LAST:event_btnAbrirArchMouseExited

    private void btnAbrirArchMousePressed(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_btnAbrirArchMousePressed
        // TODO add your handling code here:
         btnAbrirArch.setBackground(Color.red);
        btnAbrirArch.setForeground(Color.WHITE);
    }//GEN-LAST:event_btnAbrirArchMousePressed

    private void btnAbrirArchMouseReleased(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_btnAbrirArchMouseReleased
        // TODO add your handling code here:
         btnAbrirArch.setBackground(Color.WHITE);
        
        btnAbrirArch.setForeground(Color.BLACK);
    }//GEN-LAST:event_btnAbrirArchMouseReleased

    private void btnAbrirArchActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnAbrirArchActionPerformed
        // TODO add your handling code here:
        Navegador nav1=new Navegador();
        Archivo aryi=new Archivo();
        String ruta=nav1.obtenerRutaDondeAbriElArchivo();
        if(ruta!=null){
           this.vectorDeArboles= aryi.AbrirArchivo(ruta);
         //  this.arbol=null;
           //System.out.println(vectorDeArboles[0].recorridoEnPorNiveles().size());
           JOptionPane.showMessageDialog(rootPane, "Archivo cargado con exito!");
           
           //reinicio todo
        //   System.out.println(vectorDeArboles[0].cantidadClaves());
           // System.out.println(vectorDeArboles.length);
           this.arbol=null;//por defecto empieza en el binario
           this.arbol=this.vectorDeArboles[0];
           this.seleccionAnterior="Arbol Binario de Busqueda";
          // this.seleccionAnterior=this.sComboBox1.getSelectedItem().toString();
           this.sComboBox1.setSelectedIndex(0);
           this.sComboBox2.setSelectedIndex(0);
           
           //para reiniciar los datos de table
           this.reiniciarDatosParaCambioDeArbol(this.vectorDeArboles[0]);
           
        }else{
            JOptionPane.showMessageDialog(rootPane, "No se encontro La ruta..", "Advertencia", JOptionPane.WARNING_MESSAGE);
        }
    }//GEN-LAST:event_btnAbrirArchActionPerformed

        
    private int buscarFilaEnTabledadoToken(String token){
        for (int i = 0; i < this.tableDark1.getRowCount(); i++) {
            if(this.tableDark1.getValueAt(i, 3).toString().equals(token)){
                return i;
            }
        }
        return -1;
    }
//busca el token en el table y tambien me devuelve la fila donde la encontro,solo que estara concatenado al final
    private String buscarTokenEnTable(String nombre,String numero,String correo){
        
        for (int i = 0; i < this.tableDark1.getRowCount(); i++) {
            String nombreTable= tableDark1.getValueAt(i, 0).toString();
            String numeroTable=tableDark1.getValueAt(i, 1).toString();
            String correoTable=tableDark1.getValueAt(i,2).toString();
            if(nombreTable.equals(nombre)
               &&numeroTable.equals(numero)
               &&correoTable.equals(correo)){
                return tableDark1.getValueAt(i,3).toString()+"-"+i;
            }
        }
        return "";
    }
    private void filtrar(){
        try{
            sorter.setRowFilter(RowFilter.regexFilter(txtbuscar.getText()));
            //tambien puede recibir otro parametro para buscar en una columna especifica
        }catch(Exception e){
            System.out.println("ocurrio un error");
        }
    }
    
    /**
     * @param args the command line arguments
     */
    public static void main(String args[]) {
        /* Set the Nimbus look and feel */
        //<editor-fold defaultstate="collapsed" desc=" Look and feel setting code (optional) ">
        /* If Nimbus (introduced in Java SE 6) is not available, stay with the default look and feel.
         * For details see http://download.oracle.com/javase/tutorial/uiswing/lookandfeel/plaf.html 
         */
        try {
            for (javax.swing.UIManager.LookAndFeelInfo info : javax.swing.UIManager.getInstalledLookAndFeels()) {
                if ("Nimbus".equals(info.getName())) {
                    javax.swing.UIManager.setLookAndFeel(info.getClassName());
                    break;
                }
            }
        } catch (ClassNotFoundException ex) {
            java.util.logging.Logger.getLogger(frame.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(frame.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(frame.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(frame.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>

        /* Create and display the form */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                new frame().setVisible(true);
            }
        });
    }

    
    // Variables declaration - do not modify//GEN-BEGIN:variables
    private BotonesModificados.MyButton btnAbrirArch;
    private BotonesModificados.MyButton btnAgregar;
    private BotonesModificados.MyButton btnCancelar;
    private BotonesModificados.MyButton btnEliminar;
    private BotonesModificados.MyButton btnGuardarArch;
    private BotonesModificados.MyButton btnInOrden;
    private BotonesModificados.MyButton btnMinimizar;
    private BotonesModificados.MyButton btnPostOrden;
    private BotonesModificados.MyButton btnPreOrden;
    private BotonesModificados.MyButton btnexit;
    private javax.swing.JPanel jPanel2;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JLabel labelContactoGigante;
    private javax.swing.JLabel labelCorreo;
    private BotonesModificados.CLabel labelImagen;
    private javax.swing.JLabel labelNombre;
    private javax.swing.JLabel labelNumero;
    private javax.swing.JPanel panelBuscar;
    private javax.swing.JPanel panelContacto;
    private javax.swing.JPanel panelMostradorContactos;
    private javax.swing.JPanel panelSalida;
    private BotonesModificados.SComboBox sComboBox1;
    private BotonesModificados.SComboBox sComboBox2;
    private BotonesModificados.TableDark tableDark1;
    private javax.swing.JTextField txtbuscar;
    private javax.swing.JTextField txtcorreo;
    private javax.swing.JTextField txtnombre;
    private javax.swing.JTextField txtnumero;
    // End of variables declaration//GEN-END:variables
    //esta clase espara poner la imagen en el frame
    class ImagenFondo extends JPanel{
        private Image imagen;
        public void paint(Graphics g){
            imagen=new ImageIcon(getClass().getResource("/images/espacio3.jpg")).getImage();
            g.drawImage(imagen, 0, 0, getWidth(), getHeight(), this);
            setOpaque(false);
            super.paint(g);
        }
        
    }
}
