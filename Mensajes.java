package datos;

//A continuación se muestran todas las importaciones necesarias
import conexion.ConexionJavaArduino;
import java.util.ArrayList;
import java.util.Calendar;

public class Mensajes extends Thread{       //Clase que hereda de Thread
    private ConexionJavaArduino o;          //Objeto tipo ConexionJavaArduino
    ArrayList<String> mensajes;             //Objeto tipo ArrayList
    private boolean boleano = false;        //variable boolean para decisiones
    private boolean musica = false;         //variable boolean para decisiones
    
    public Mensajes(String msg, ConexionJavaArduino o){//Constructor sobrecargado
        super(msg);         //Hace referencia a un constructor de la clase padre
        mensajes = new ArrayList<String>(); //Intancia el obj tipo ArrayList
        mensajesPredeterminados();//Llama al método encargado de cargar o añadir los mensajes predeterminados al ArrayList
        this.o = o; //Asigna el valor de la conexión al obj tipo conexion de la clase
    }
    
    public void run() {  //Método para manejar o controlar hilos
        if(musica == false) //Si la variable boolean musica == false 
            try {           //Intenta
                Thread.sleep(16000); //Dormir el hilo (para sonar una melodia en Arduino en este tipo una unica vez)
                musica = true;     // variable boolean musica = true para que no vuelva a realizar esta acción
            } 
            catch (InterruptedException ex) { //atrapa y Trata la excepcion
                Thread.currentThread().interrupt(); //configura el indicador de interrupción del hilo, para que los manejadores de interrupción de nivel superior lo noten y puedan manejarlo de manera apropiada.
            }
        int i; //Variable tipo entera 
        if(boleano == false)  //Si la variable boolean == false (lo que significa que la manera de mostrar los mensajes sera ascendente)
            i = 0;            //asigna el valor de 0 a la variable entera
        else                  // sino (significa que la manera de mostrar los mensajes sera descendente)
            i = mensajes.size() - 1; //asigna el valor de -1 a la variable entera
        while (true) {    //Ciclo infinito 
            try {                              //Intenta  
                String res = o.recibirDatos(); //variable tipo String que obtiene (intenta) datos del arduino
                if (res.length() > 0) {        //Si la longitud de la variable String es mayor a 0 verifica:
                    if (res.equalsIgnoreCase("1")) {//Si el contenido de la variable String == 1
                        i = compara(i);             //la variable entera i = (llama al metodo compara(i) para verificar la misma variable)
                        i--; //Disminuye la variable entera en 1
                    } else if (res.equalsIgnoreCase("2")) {//Si el contenido de la variable String == 1
                        i = compara(i);             //la variable entera i = (llama al metodo compara(i) para verificar la misma variable)
                        i++; //Aumenta la variable entera en 1
                    }
                } else {    //Si la longitud de la variable String no es mayor a 0 verifica:
                    i = compara(i); //la variable entera i = (llama al metodo compara(i) para verificar la misma variable)
                    if(boleano == false) //Si la variable boolean == false (lo que significa que la manera de mostrar los mensajes sera ascendente)
                        i++; //Aumenta la variable entera en 1
                    else    //Sino (significa que la manera de mostrar los mensajes sera descendente)
                        i--; //Disminuye la variable entera en 1
                }                
                Thread.sleep(2000); //Dormir el hilo (para durante este tiempo mostrar el mensaje enviado a arduino en la lcd)
            }
            catch (InterruptedException ex) { //atrapa y Trata la excepcion
                Thread.currentThread().interrupt(); //configura el indicador de interrupción del hilo, para que los manejadores de interrupción de nivel superior lo noten y puedan manejarlo de manera apropiada.
            }
        }
    }
    
    public int compara(int i){ //Método encargado de comprar y retornar el valor de la variable entera para saber que acción tomar en arduino
        if (i <= 0) {   //Si el valor es menor o igual a 0
            i = 0;      //Asigna a la variable entera 0
            System.out.println(i + "    " + mensajes.get(i));
            o.enviarDatos("Tem"); //Envia a arduino "Temp" para que muestre la temperatura
            boleano = false; //Asifna a la variable boolean = false (lo que significa que la manera de mostrar los mensajes sera ascendente)
        } 
        else if (i == 1){  //Si el valor de la variable entera es == 1
                System.out.println(i + "    " + mensajes.get(i));
                o.enviarDatos(hora()); //Envia a arduino la hora actual del sistema
        }
        else if (i >= mensajes.size()-1) { //Si el valor de la variable entera es mayor o igual del tamaño del ArrayList-1 
            i = mensajes.size() - 1;    //Asigna a la variable entera el tamaño del ArrayList-1
            System.out.println(i + "    " + mensajes.get(i));
            o.enviarDatos(mensajes.get(i)); //Envia al arduino el mensaje del ArrrayList marcado en la posición de la variable entera
            boleano = true; //Variable boolean = true (significa que la manera de mostrar los mensajes sera descendente)
        } 
        else{ //Si la variable entera no tiene el valor de las antes mencionadas
            System.out.println(i + "    " + mensajes.get(i));
            o.enviarDatos(mensajes.get(i));  //Envia al arduino el mensaje del ArrrayList marcado en la posición de la variable entera
        
        }
        return i; //Retorna o regresa el valor de la variable entera
    }
    
    public void mensajesPredeterminados(){ //Método que agrega al ArrayList mensajes predeterminados
        mensajes.add("Tem");        //Agrega el String al ArrayList
        mensajes.add("Hra");        //Agrega el String al ArrayList
        mensajes.add("Uno");        //Agrega el String al ArrayList
        mensajes.add("Dos");        //Agrega el String al ArrayList
        mensajes.add("Tres");       //Agrega el String al ArrayList
        mensajes.add("Cuatro");     //Agrega el String al ArrayList
        mensajes.add("Cinco");      //Agrega el String al ArrayList
        mensajes.add("Seis");       //Agrega el String al ArrayList
        mensajes.add("Siete");      //Agrega el String al ArrayList
        mensajes.add("Ocho");       //Agrega el String al ArrayList
    }
    
    public String hora(){//Método encargado de obtener y retornar la hora actual
        Calendar calendario = Calendar.getInstance();   //Objeto tipo Calendar
        int hora = calendario.get(Calendar.HOUR_OF_DAY);//Obtiene la hora
        int minutos = calendario.get(Calendar.MINUTE);  //Obtiene los minutos
        return hora + ":" + minutos;//Retorna la concatenación de la hora y minutos 
    }
        
    public void agregarMensaje(String mensaje){//Método encargado de agregar nuevos mensajes enviados desde la interfaz al ArrayList
        mensajes.add(mensaje);  //Agrega el String al ArrayList
    }

    public ArrayList<String> getMensajes() {
        return mensajes;        //Método get que retorna un obj. tipo ArrayList
    }    
}
