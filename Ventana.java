package vista;

//A continuación se muestran todas las importaciones necesarias
import datos.ControlVentana;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.table.DefaultTableModel;


public class Ventana {
    
    JFrame ventana;             //Objeto tipo JFrame
    JLabel lblTabla;            //Objeto tipo JLabel
    JTextField txtMensaje;      //Objeto tipo JTextField
    JButton btnEnviar;          //Objeto tipo JButton
    JButton btnLimpiar;         //Objeto tipo JButton
    JTable tablaMensajes;       //Objeto tipo JTable
    DefaultTableModel dtm;      //Objeto tipo DefaultTableModel  
    JScrollPane scrollTabla;    //Objeto tipo JScrollPane
    ControlVentana o;           //Objeto tipo ControlVentana

    public Ventana(){                   //Contructor vacio
        ventana = new JFrame();         //Instancia del obj tipo JFrame
        lblTabla = new JLabel();        //Instancia del obj tipo JLabel
        txtMensaje = new JTextField();  //Instancia del obj tipo JTextField
        btnEnviar = new JButton();      //Instancia del obj tipo JButton
        btnLimpiar = new JButton();     //Instancia del obj tipo JButton
        tablaMensajes = new JTable();   //Instancia del obj tipo JTable
        dtm = new DefaultTableModel();  //Instancia del obj tipo DefaultTableModel
        scrollTabla = new JScrollPane();//Instancia del obj tipo JScrollPane
        o = new ControlVentana(this);   //Instancia del obj tipo ControlVentana
    }
    
    public void preparaVentana(){     //Método encargado de asignar caracteristicas de tamaño, titulo, etc. al frame
        ventana.setSize(700, 300);    //Asigna tamaño al frame
        ventana.setTitle("Mensaje de Java a Arduino"); // Asigna un titulo que será visible en el frame
        ventana.setLayout(null);                       // Layout nulo para permitir acomodar los componentes con setBounds
        ventana.setLocationRelativeTo(null);           // Posiciona en el centro de la pantalla el componente JFrame
        ventana.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE); // Asigna la opción de poder de la aplicación con el boton rojo "x"
        ventana.setVisible(true);  //Hace visible el frama        
        llamaComponentes();        //Llama al método que se encarga de inicializar cada componente del frame
    }
    
    
    private void llamaComponentes() { //Método que se encarga de llamar métodos que inicializan componentes del frame
        txts();     //Llama al método que se encarga de la construcción de la caja de texto (JTextBox)
        label();    //Llama al método que se encarga de la construcción del Label (JLabel)
        botones();  //Llama al método que se encarga de la construcción del botón (JButton
        tabla();    //Llama al método que se encarga de la construcción de la tabla (JTtable)
        agregaComponente();    //Llama al método encargado de agregar los componentes al JFrame 
        agregaOyente();        //Llama al método encargado de agregar los oyentes de acción a los componentes 
        
    }
    
    public void txts(){     //Método que se encarga de la construcción de la caja de texto
        txtMensaje.setBounds(100, 20, 500, 30); //Acomoda el componente en el frame (x,y,ancho,alto)
    }
    
    public void label(){    //Método que se encarga de la construcción del Label
        lblTabla.setText("Mensajes agregados: ");  //Muestra o coloca el texto señalado en el componente
        lblTabla.setBounds(100, 120, 150, 30);     //Acomoda el componente en el frame (x,y,ancho,alto)
    }
    
     public void botones(){ //Método que se encarga de la construcción del botón 
        btnEnviar.setBounds(500, 60, 100, 30);     //Acomoda el componente en el frame (x,y,ancho,alto)
        btnEnviar.setText("Enviar");               //Muestra o coloca el texto señalado en el componente
        btnLimpiar.setBounds(350, 60, 100, 30);    //Acomoda el componente en el frame (x,y,ancho,alto)
        btnLimpiar.setText("Limpíar");             //Muestra o coloca el texto señalado en el componente
     }
     
     public void tabla(){   //Método que se encarga de la construcción de la tabla
        tablaMensajes.setBounds(100, 150, 500, 100);//Acomoda el componente en el frame (x,y,ancho,alto)
        dtm.addColumn("N°");                        //Agrega Columa "N°" en el componente DefaultTableModel
        dtm.addColumn("Mensaje");                   //Agrega Columa "Mensaje" en el componente DefaultTableModel
        tablaMensajes.setModel(dtm);
        scrollTabla.setBounds(100, 150, 500, 100);  //Acomoda el componente en el frame (x,y,ancho,alto)
        scrollTabla.setViewportView(tablaMensajes);
    }
     
     public void agregaComponente(){    //Método encargado de agregar los componentes al JFrame
        ventana.add(txtMensaje);        //Agrega el JTextField al JFrame
        ventana.add(lblTabla);          //Agrega el JLabel al JFrame
        ventana.add(btnEnviar);         //Agrega el JButton al JFrame       
        ventana.add(btnLimpiar);        //Agrega el JButton al JFrame
        ventana.add(scrollTabla);       //Agrega el JTextField al JFrame
    }
    
    public void agregaOyente(){          //Método encargado de agregar los oyentes de acción a los componentes
        btnEnviar.addActionListener(o);  //Agrega el oyente de acción al JButton
        btnLimpiar.addActionListener(o); //Agrega el oyente de acción al JButton
    }
    
    public JButton getBtnEnviar(){
        return btnEnviar;          //Método get que retorna un obj. tipo JButton
    }
    
    public JButton getBtnLimpiar(){
        return btnLimpiar;         //Método get que retorna un obj. tipo JButton
    }
    
    public JTextField gettxtText(){
        return txtMensaje;         //Método get que retorna un obj. tipo JTextField
    }
    
    public DefaultTableModel getDtmMensaje() {
        return dtm;                //Método get que retorna un obj. tipo DefaultTableModel
    }
    
    public static void main(String[] args) {    
        Ventana o = new Ventana();   //Creación de objeto tipo Ventana
        
        o.preparaVentana();          //Comienza la construcción de la ventana
    }
 
}
