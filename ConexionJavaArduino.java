package conexion;

//A continuación se muestran todas las importaciones necesarias
import gnu.io.CommPortIdentifier;
import gnu.io.SerialPort;
import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.util.Enumeration;

public class ConexionJavaArduino { //Clase encargada de la conexión de Java a Arduino y viceversa
    
    SerialPort serialPort; //Objeto tipo SeriaPort para la comunicación serial 
    private final String PUERTO = "COM5"; //puerto con el que hara la conexión
    private static final int TIMEOUT = 2000; // 2 segundos.
    private static final int DATA_RATE = 9600; // Baudios. 
    private OutputStream output = null; //Objeto tipo OutputStream
    private BufferedReader input = null; //Objeto tipo BufferedReader
    
    public void inicializarConexion(){ //Método que inicia la conexión
        CommPortIdentifier puertoID = null; //Objeto tipo CommPortIdentifier
        Enumeration puertoEnum = CommPortIdentifier.getPortIdentifiers();
 
        while (puertoEnum.hasMoreElements()){ //Descubrir qué puertos de comunicación están disponibles y luego seleccionar un puerto para abrir.
            CommPortIdentifier actualPuertoID = (CommPortIdentifier) puertoEnum.nextElement();
            System.out.println(actualPuertoID.getName());
            if (PUERTO.equals(actualPuertoID.getName())){
                puertoID = actualPuertoID;
                break; //Rompe el ciclo
            }
        } 
        
        if (puertoID == null) //Si la variable puertoID = null (vacio)
            System.out.println("No se puede conectar al puerto");  //Notifica que no se puede conectar al puerto especificado          
        
        try{ //A continuación iIntenta conectar al puerto señalado
            serialPort = (SerialPort) puertoID.open(this.getClass().getName(), TIMEOUT);
            // Parámatros puerto serie. 
            serialPort.setSerialPortParams(DATA_RATE, SerialPort.DATABITS_8, SerialPort.STOPBITS_2, SerialPort.PARITY_NONE); 
            output = serialPort.getOutputStream();
            input= new BufferedReader(new InputStreamReader (serialPort.getInputStream()));            
        } 
        catch (Exception e){ //atrapa y trata la excepcion
            System.out.println("No se puede conectar al puerto " + e.getMessage()); //Notificar que no se pueden conectar al puerto especificado
        }
}
    
    public void enviarDatos(String data){ //Método encargado de enviar datos a Arduino
        try{        //Intenta
            output.write(data.getBytes()); //Envia por bytes el mensaje recibido
        }
        catch (Exception e){ //atrapa y trata la excepcion
            System.out.println("No se pueden enviar datos " + e.getMessage()); //Notificar que no se pueden enviar los datos
        }
    }
    
    public String recibirDatos(){ //Método encargado de recibir datos de Arduino
        try{ //Intenta
            return input.readLine(); //retornar lo que envio arduino
        }
        catch (Exception e){ //atrapa y trata la excepcion
            return ""; //retorna una cadena vacia
        }
    }
    
}
