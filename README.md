# Práctica 3: Arduino con LCD y Java versión 1.0
/******************************************************************************************************************************/

Fecha: 12/Abril/2018

/******************************************************************************************************************************/

Costo: Gratuito por fines educativos

Para la construcción de la práctica se hizo uso de software libre, tales como:

    Frintzing 0.9.3 (Para la construcción del diagrama).

    Arduino IDE 1.8.5 (Para el código).

Las evidencias se muestran en los mismos archivos añadidos a este repositorio.

/******************************************************************************************************************************/

Práctica 2: Arduino con LCD y Java.

En está práctica se establece comunicación serial de Java con Arduino, permitiendo demostrar que ambas tecnologías pueden funcionar a la par sin ningún problema, inclusive, independientemente del SO en el que se corran o ejecuten. La funcionalida que tiene la presente práctica es permitir desplegar en la pantalla lcd:

* Temperatura
* Hora
* Mensajes

Cabe mencionar que los mensajes se envian a arduino desde una interfaz creada en Java, además de permitir navegar entre los mensajes.

A continuación, se detallará el funcionamiento que se le dio a lo mencionado anteriormente:
Una vez cargado el programa al arduino y corrida la aplicación de escritorio de java, calculara la temperatura gracias al sensor de temperatura integrado, si la temperatura que detecta el sensor es mayor a 26°C de inmediato producira un sonido de alarma (lo cual indica que se registra una temperatura alta) y está no dejará de sonar hasta que el sensor de temperatura detecte una menor a los 26°C, cuando suceda lo anterior, reproducira la famosa melodía de Mario Bros al rededor de 10 segundos (lo cual solo sucederá una vez), al finalizar la melodía comenzará la comunicación serial entre java y arduino, java enviará una serie de mensajes para ser desplegados en la pantalla lcd, entre ellos, la hora actual del sistema, cuando java envie "Temp", arduino mostrará la temperatura que detecta el sensor, además, a través de la interfaz, el usuario podrá agregar la cantidad de mensajes que desee, los cuales serán mostrados, la manera de proyectar los mensajes es de fomra ascendente (es decir la manera en como se encuentran agregados en el ArrayList) una vez llegado al ultimo mensaje registrado, comenzará a mostrarlos de forma descendente, al llegar al primer mensaje, los mostrará de forma ascendente y así sucesivamente. A través de los botones se podrá navegar en los mensajes, es decir, si se esta mostrando el mensaje "Cuatro" que se encuetra en la posición 5 del ArrayList, se podrá retroceder para visualizar el de la posición 4, o 3, 0 2, etc., o adelantar para ver los mensajes seguidos de él. Pero si en algún momento se detecta una temperatura mayor a los 26°C, sonará la alarma.

/******************************************************************************************************************************/

Información de contacto:

    Autor: Nancy Castañeda

    Correo: nancycast96@gmail.com

/******************************************************************************************************************************/

Instrucciones: Para la construcción del circuito se añade una imagen llamada "LCDJava_bb.png" con el diagrama para su correcto armado, así como el código necesario para su funcionamiento, listo para cargar en el arduino UNO, el archivo se llama "LCD_Java.ino", de igual manera, se incluyen los .java (ConexionJavaArduino, ControlVentana, Ventana, Mensajes) necesarios para la creación de la aplicación de escritorio.

/******************************************************************************************************************************/

Materiales:

* 1 Arduino UNO

* 1 Protoboard

* N Cables puente para arduino

* Resistencias de 220 Ω

* 2 Push Button

* 1 Buzzer

* 1 Sensor de temperatura LM35

* 1 Pantalla LCD 16 x 2

* 1 Controlador LCD I2C
