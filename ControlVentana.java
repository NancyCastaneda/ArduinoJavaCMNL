package datos;

//A continuación se muestran todas las importaciones necesarias
import conexion.ConexionJavaArduino;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.JOptionPane;
import vista.Ventana;

public class ControlVentana implements ActionListener{ //Clase que implementa ActionListener
    private Ventana o;                   //Objeto tipo Ventana (Vista)
    private final Mensajes control;      //Objeto tipo Mensajes 
    private final ConexionJavaArduino ob;//Objeto tipo ConexionJavaArduino
    
    public ControlVentana(Ventana o){    //Contructor sobrecargado
        ob = new ConexionJavaArduino();  //Objeto tipo ConexionJavaArduino
        this.o=o; //Asigna el valor de la conexión al obj tipo conexion de la clase
        ob.inicializarConexion(); //Inicia o establece la conexión Java-Arduino
        control=new Mensajes("Proceso",ob);//Se instancia la clase y
        control.start();                   //se llama al método start() para que arranque el hilo
    }

    @Override
    public void actionPerformed(ActionEvent e) { //Método que realiza las acciones programadas ante ese evento
        if (e.getSource()== o.getBtnEnviar())    //Si el componente accionado es el JButton btnEnviar
            agregarMensaje();                    //Llama al método encargado de enviar mensajes al método que agrega al ArrayList y mostrar en la tabla el mensaje agregado 
        else if (e.getSource()==o.getBtnLimpiar())//Si el componente accionado es el JButton btnLimpiar
            o.gettxtText().setText("");           //Limpia la caja de texto (JTextField)
    }    
    
    public void agregarMensaje(){ //Método encargado de enviar mensajes al método que agrega al ArrayList y mostrar en la tabla el mensaje agregado
        String msg = o.gettxtText().getText(); //Obtiene el contenido de la caja de texto (JTextField)
        control.agregarMensaje(msg);   //Envia o pasa el mensaje al método que agrega al ArrayList
        String [] arreglo = new String [2]; //Arreglo de tamaño 2 
        arreglo[0] = control.getMensajes().size() + ""; //Obtiene el tamaño del ArrayList y lo asigna en la posición 0
        arreglo[1] = msg;   //Asigna el valor obtenido en la caja de texto al arreglo en la posición 1
        o.getDtmMensaje().addRow(arreglo); //Agrega y muestra en la tabla o en el modelo de la tabla lel registro insertado anteriormente
        JOptionPane.showMessageDialog(null, "Mensaje Agregado con Éxito"); //Muestra en un dialogo el mensaje de que el mensaje fue agregado
    }     
   
}
