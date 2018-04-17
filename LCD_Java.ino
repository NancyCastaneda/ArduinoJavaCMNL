#include <LiquidCrystal_I2C.h>

LiquidCrystal_I2C lcd(0x27, 16, 2);

#define btnMsjAtras 2 //Pin asignado al boton que recorrera los mensajes para atras, se declara como define debido a que su valor sera constate
#define btnMsjSig 3   //Pin asignado al boton que recorrera los mensajes para adelante
String Mensaje = "";  //Variable que contendra el mensaje recibido de java
#define buzzer 8 //Pin asignado al buzzer
float temp = 0;  //Variable para la temperatura obtenida en el sensor
boolean music = false; //Variable boolean para la reproducción de una única vez la melodía de Mario bros

//A continuación se asignan los valores necesarios para la reproducción de melodias en Arduino, se declaran como define debido a que su valor sera constate
 
 #define NOTE_B0  31
 #define NOTE_C1  33
 #define NOTE_CS1 35
 #define NOTE_D1  37
 #define NOTE_DS1 39
 #define NOTE_E1  41
 #define NOTE_F1  44
 #define NOTE_FS1 46
 #define NOTE_G1  49
 #define NOTE_GS1 52
 #define NOTE_A1  55
 #define NOTE_AS1 58
 #define NOTE_B1  62
 #define NOTE_C2  65
 #define NOTE_CS2 69
 #define NOTE_D2  73
 #define NOTE_DS2 78
 #define NOTE_E2  82
 #define NOTE_F2  87
 #define NOTE_FS2 93
 #define NOTE_G2  98
 #define NOTE_GS2 104
 #define NOTE_A2  110
 #define NOTE_AS2 117
 #define NOTE_B2  123
 #define NOTE_C3  131
 #define NOTE_CS3 139
 #define NOTE_D3  147
 #define NOTE_DS3 156
 #define NOTE_E3  165
 #define NOTE_F3  175
 #define NOTE_FS3 185
 #define NOTE_G3  196
 #define NOTE_GS3 208
 #define NOTE_A3  220
 #define NOTE_AS3 233
 #define NOTE_B3  247
 #define NOTE_C4  262
 #define NOTE_CS4 277
 #define NOTE_D4  294
 #define NOTE_DS4 311
 #define NOTE_E4  330
 #define NOTE_F4  349
 #define NOTE_FS4 370
 #define NOTE_G4  392
 #define NOTE_GS4 415
 #define NOTE_A4  440
 #define NOTE_AS4 466
 #define NOTE_B4  494
 #define NOTE_C5  523
 #define NOTE_CS5 554
 #define NOTE_D5  587
 #define NOTE_DS5 622
 #define NOTE_E5  659
 #define NOTE_F5  698
 #define NOTE_FS5 740
 #define NOTE_G5  784
 #define NOTE_GS5 831
 #define NOTE_A5  880
 #define NOTE_AS5 932
 #define NOTE_B5  988
 #define NOTE_C6  1047
 #define NOTE_CS6 1109
 #define NOTE_D6  1175
 #define NOTE_DS6 1245
 #define NOTE_E6  1319
 #define NOTE_F6  1397
 #define NOTE_FS6 1480
 #define NOTE_G6  1568
 #define NOTE_GS6 1661
 #define NOTE_A6  1760
 #define NOTE_AS6 1865
 #define NOTE_B6  1976
 #define NOTE_C7  2093
 #define NOTE_CS7 2217
 #define NOTE_D7  2349
 #define NOTE_DS7 2489
 #define NOTE_E7  2637
 #define NOTE_F7  2794
 #define NOTE_FS7 2960
 #define NOTE_G7  3136
 #define NOTE_GS7 3322
 #define NOTE_A7  3520
 #define NOTE_AS7 3729
 #define NOTE_B7  3951
 #define NOTE_C8  4186
 #define NOTE_CS8 4435
 #define NOTE_D8  4699
 #define NOTE_DS8 4978

//Underworld melody
int underworld_melody[] = { //Arreglo que contiene las notas necesarias para la reproducción de la melodia
  NOTE_C4, NOTE_C5, NOTE_A3, NOTE_A4,
  NOTE_AS3, NOTE_AS4, 0,
  0,
  NOTE_C4, NOTE_C5, NOTE_A3, NOTE_A4,
  NOTE_AS3, NOTE_AS4, 0,
  0,
  NOTE_F3, NOTE_F4, NOTE_D3, NOTE_D4,
  NOTE_DS3, NOTE_DS4, 0,
  0,
  NOTE_F3, NOTE_F4, NOTE_D3, NOTE_D4,
  NOTE_DS3, NOTE_DS4, 0,
  0, NOTE_DS4, NOTE_CS4, NOTE_D4,
  NOTE_CS4, NOTE_DS4,
  NOTE_DS4, NOTE_GS3,
  NOTE_G3, NOTE_CS4,
  NOTE_C4, NOTE_FS4, NOTE_F4, NOTE_E3, NOTE_AS4, NOTE_A4,
  NOTE_GS4, NOTE_DS4, NOTE_B3,
  NOTE_AS3, NOTE_A3, NOTE_GS3,
  0, 0, 0
};
//Underwolrd tempo
int underworld_tempo[] = { //Arreglo que contiene la duración de las notas
  12, 12, 12, 12,
  12, 12, 6,
  3,
  12, 12, 12, 12,
  12, 12, 6,
  3,
  12, 12, 12, 12,
  12, 12, 6,
  3,
  12, 12, 12, 12,
  12, 12, 6,
  6, 18, 18, 18,
  6, 6,
  6, 6,
  6, 6,
  18, 18, 18, 18, 18, 18,
  10, 10, 10,
  10, 10, 10,
  3, 3, 3
};

void setup() {
  pinMode(buzzer, OUTPUT);   //El Buzzer como una salida
  /*A continuación se preparan y/o definen las interrupciones y el tipo de interrupción a usar*/
  pinMode(btnMsjSig, INPUT_PULLUP);
  pinMode(btnMsjAtras, INPUT_PULLUP);
  attachInterrupt(digitalPinToInterrupt(btnMsjSig), interrupcionSig, RISING);
  attachInterrupt(digitalPinToInterrupt(btnMsjAtras), interrupcionAtr, RISING);
  
  lcd.init(); // Inicializar el LCD
  
  lcd.backlight();//Encender la luz de fondo.

  Serial.begin(9600);//Comienza la comunicación serial
}

void loop() { //función ciclica
  temperatura(); //Llama al método que obtiene la temperatura
  lcd.clear();   //Limpia el contenido de la LCD
  obtenMensajeJava(); //Llama al método que obtiene los mensajes de Java
  if (temp > 26.0)   //Si la temperatura es mayor a 26 
    alarma();        //Llama al método encargado de reproducir una alarma
  else { //Si la temperatura no es mayor a 26
    noTone(8); //El buzzer dejara de emitir sonidos
    if(music == false) //Si la variable boolean music == false 
      musica();        //Se llama al método encargado de reproducir melodía
    music = true;      //Asigna a la variable boolean music el valor de true debido a que solo se espera que la melodia la genere una sola vez
    if (Mensaje.equalsIgnoreCase("Tem")) { //Si el mensaje recibido de java == Temp
      lcd.clear();    //Limpia el contenido de la LCD
      lcd.print("Temperatura"); //Mostrará en la lcd "Temperatura"
      lcd.setCursor(0, 1);      //Mueve el cursor de posición
      lcd.print("C =");         //Imprime en la lcd "C ="
      temperatura();            //Llama al método que obtiene la temperatura
      delay(2000); //Se espera dos segundos
      Mensaje = ""; //Limpia la varialbe mensaje
    }
    else        //Si el mensaje recibido de java no es Temp
      imprimeMensaje(); //Llama al método que imprime el mensaje enviado por java
  }

}

void alarma(){
  for (int i = 0; i < 180; i++) { //Comenzará un cliclo para reproducir una alerta mientras i < 180 (que son los grados para recorrer una señal senoidal)
      float sinVal = (sin(i * (3.1412 / 180))); //Se pasan los grados a radianes
      int toneVal = 200 + (int(sinVal * 1000)); //Se genera la frecuencia
      tone(buzzer, toneVal); //Emite sonido en el buzzer de acuerdo a la frecuencia marcada
      delay(2); //Espera de 2 milisegundos
    }
}

void musica(){
  int size = sizeof(underworld_melody) / sizeof(int); //variable tipo size que contiene cantidad de bytes 
    for (int thisNote = 0; thisNote < size; thisNote++) { // Comienza ciclo ejecutado mientras la variable entera thisNote sea menor que size
      int noteDuration = 1000 / underworld_tempo[thisNote]; //para calcular la duración de la nota, tomar un segundo, dividido por el tipo de nota
 
      buzz(buzzer, underworld_melody[thisNote], noteDuration); //Emite sonido en el buzzer de acuerdo a la frecuencia marcada en el tiempo marcado
 
      int pauseBetweenNotes = noteDuration * 1.30; //para distinguir las notas, se establece un tiempo mínimo entre ellas
      delay(pauseBetweenNotes); //Se espera el tiempo marcado por pauseBetweenNotes
  
      buzz(buzzer, 0, noteDuration); //El buzzer dejara de emitir la melodía 
    }
}

void buzz(int targetPin, long frequency, long length) {
  long delayValue = 1000000 / frequency / 2;    // calcula el valor de demora entre transiciones
  //// 1 segundo vale microsegundos, dividido por la frecuencia, luego se divide a la mitad 
  //// ya que existen dos formas en cada ciclo
  long numCycles = frequency * length / 1000; //  calcula el número de ciclos para la frecuencia multiplicada por el tiempo adecuado, 
  ////que es realmente ciclos por segundo, por la cantidad de segundos para obtener el número total de ciclos para producir
  for (long i = 0; i < numCycles; i++) { // durante el tiempo calculado
    digitalWrite(targetPin, HIGH); //Emite en el buzzer
    delayMicroseconds(delayValue); // espera el valor de retraso calculado
    digitalWrite(targetPin, LOW); //Deja de emitir en el buzzer
    delayMicroseconds(delayValue); // espera el valor de retraso calculado
  } 
}

void imprimeMensaje() {  //Método que se encarga de imprimir en la lcd los mensajes recibidos desde java
  int caracteres = 0;    //se le asigna a la variable entera el valor de 0
  caracteres = Mensaje.length(); //se le asigna el tamaño del mensaje a la variable entera caracteres
  if (caracteres > 16) { //Si el valor de la variable entera caracteres es mayor a 16
    if (Mensaje != "") { //si el contenido del mensaje es distinto a vacio
      lcd.clear();      //limpia el contenido de la lcd
      lcd.print(Mensaje.substring(0, 16));//Imprime en la lcd del caracter 0 al 16 del contenido del mensaje
      lcd.setCursor(0, 1); //Mueve el cursor de la lcd
      lcd.print(Mensaje.substring(16, caracteres));//imprime en la lcd el resto de los caracteres del mensaje
    }
  }
  else { //Si el valor de la variable entera caracteres no es mayor a 16
    if (Mensaje != "") { //si el contenido del mensaje es distinto a vacio
      lcd.clear();       //limpia el contenido de la lcd
      lcd.print(Mensaje);//Imprime en la lcd el contenido del mensaje
    }
  }
  delay(2000); //Se espera dos segundos
  Mensaje = "";//Limpia el contenido de la variable mensaje
}

void obtenMensajeJava() { //Método que se encarga de obtener los mensajes enviados desde java
  Mensaje = "";       //Limpia el contenido de la variable mensaje
  while (Serial.available() > 0) {//Si la cantidad de bytes (caracteres) disponibles para leer desde el puerto serie es mayor a 0
    Mensaje = Mensaje + Decimal_to_ASCII(Serial.read()); //concatena en mensaje, mensaje + la conversión ascii de los datos de serie entrantes.
  }
}

void interrupcionSig() {//Método de interrupción encargado de enviar a java valores
  Serial.println("2");  //Envia a Java el valor 2
}

void interrupcionAtr() {//Método de interrupción encargado de enviar a java valores
  Serial.println("1");  //Envia a Java el valor 1
}


char Decimal_to_ASCII(int entrada) { //Método encargado de convertir a formato ascii los datos entrantes y retornar el valor
  char salida = ' ';
  switch (entrada) { //Se evalua en cada caso la varriable entera recibida
    case 32:
      salida = ' ';
      break;
    case 33:
      salida = '!';
      break;
    case 34:
      salida = '"';
      break;
    case 35:
      salida = '#';
      break;
    case 36:
      salida = '$';
      break;
    case 37:
      salida = '%';
      break;
    case 38:
      salida = '&';
      break;
    case 39:
      salida = ' ';
      break;
    case 40:
      salida = '(';
      break;
    case 41:
      salida = ')';
      break;
    case 42:
      salida = '*';
      break;
    case 43:
      salida = '+';
      break;
    case 44:
      salida = ',';
      break;
    case 45:
      salida = '-';
      break;
    case 46:
      salida = '.';
      break;
    case 47:
      salida = '/';
      break;
    case 48:
      salida = '0';
      break;
    case 49:
      salida = '1';
      break;
    case 50:
      salida = '2';
      break;
    case 51:
      salida = '3';
      break;
    case 52:
      salida = '4';
      break;
    case 53:
      salida = '5';
      break;
    case 54:
      salida = '6';
      break;
    case 55:
      salida = '7';
      break;
    case 56:
      salida = '8';
      break;
    case 57:
      salida = '9';
      break;
    case 58:
      salida = ':';
      break;
    case 59:
      salida = ';';
      break;
    case 60:
      salida = '<';
      break;
    case 61:
      salida = '=';
      break;
    case 62:
      salida = '>';
      break;
    case 63:
      salida = '?';
      break;
    case 64:
      salida = '@';
      break;
    case 65:
      salida = 'A';
      break;
    case 66:
      salida = 'B';
      break;
    case 67:
      salida = 'C';
      break;
    case 68:
      salida = 'D';
      break;
    case 69:
      salida = 'E';
      break;
    case 70:
      salida = 'F';
      break;
    case 71:
      salida = 'G';
      break;
    case 72:
      salida = 'H';
      break;
    case 73:
      salida = 'I';
      break;
    case 74:
      salida = 'J';
      break;
    case 75:
      salida = 'K';
      break;
    case 76:
      salida = 'L';
      break;
    case 77:
      salida = 'M';
      break;
    case 78:
      salida = 'N';
      break;
    case 79:
      salida = 'O';
      break;
    case 80:
      salida = 'P';
      break;
    case 81:
      salida = 'Q';
      break;
    case 82:
      salida = 'R';
      break;
    case 83:
      salida = 'S';
      break;
    case 84:
      salida = 'T';
      break;
    case 85:
      salida = 'U';
      break;
    case 86:
      salida = 'V';
      break;
    case 87:
      salida = 'W';
      break;
    case 88:
      salida = 'X';
      break;
    case 89:
      salida = 'Y';
      break;
    case 90:
      salida = 'Z';
      break;
    case 91:
      salida = '[';
      break;
    case 92:
      salida = ' ';
      break;
    case 93:
      salida = ']';
      break;
    case 94:
      salida = '^';
      break;
    case 95:
      salida = '_';
      break;
    case 96:
      salida = '`';
      break;
    case 97:
      salida = 'a';
      break;
    case 98:
      salida = 'b';
      break;
    case 99:
      salida = 'c';
      break;
    case 100:
      salida = 'd';
      break;
    case 101:
      salida = 'e';
      break;
    case 102:
      salida = 'f';
      break;
    case 103:
      salida = 'g';
      break;
    case 104:
      salida = 'h';
      break;
    case 105:
      salida = 'i';
      break;
    case 106:
      salida = 'j';
      break;
    case 107:
      salida = 'k';
      break;
    case 108:
      salida = 'l';
      break;
    case 109:
      salida = 'm';
      break;
    case 110:
      salida = 'n';
      break;
    case 111:
      salida = 'o';
      break;
    case 112:
      salida = 'p';
      break;
    case 113:
      salida = 'q';
      break;
    case 114:
      salida = 'r';
      break;
    case 115:
      salida = 's';
      break;
    case 116:
      salida = 't';
      break;
    case 117:
      salida = 'u';
      break;
    case 118:
      salida = 'v';
      break;
    case 119:
      salida = 'w';
      break;
    case 120:
      salida = 'x';
      break;
    case 121:
      salida = 'y';
      break;
    case 122:
      salida = 'z';
      break;
    case 123:
      salida = '{';
      break;
    case 124:
      salida = '|';
      break;
    case 125:
      salida = '}';
      break;
    case 126:
      salida = '~';
      break;
  }
  return salida;  //Retorna el caracter especificado de acuerdo al valor entrante
}

float centi(){  //Método encargado de obtener la temperatura del sensor
  int dato;     //Variable entera dato
  float c;      //Variable flotante c
  dato = analogRead(A0); //Obtiene el valor del sensor y se lo asigna a dato
  c = (500.0 * dato) / 1023; //convierte el dato a grados celsius y se lo asigna a c
  return (c); //Retorna la variable flotante c
}


void temperatura() { //Método encargado de imprimir en lcd la temperatura
  temp = centi();   //asigna a temp el valor obtenido (temperatura) del método centi()
  lcd.setCursor(4, 1); //Mueve y acomoda el cursor en la lcd
  lcd.print(temp);    //Imprime en la lcd el valor de la temperatura
}
