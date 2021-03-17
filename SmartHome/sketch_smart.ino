#include <SoftwareSerial.h>
#include <Servo.h>
#include <dht.h>
dht DHT;

// Arduino(RX, TX) - Bluetooth (TX, RX)
SoftwareSerial Bluetooth(53, 52);

//----------------------------------------- SERVO
Servo servo;
int angle = 0;

//----------------------------------------- LEDS

// The pins the LEDs are connected to
int LED_hol = 4;
int LED_baie = 5;
int LED_terasa = 6;
int LED_dormitor = 7;


// Variables holding the states of the LEDs
int isHolOn = 0;
int isTerasaOn = 0;
int isDormitorOn = 0;
int isBaieOn = 0;

//----------------------------------------- TEMP LM35

// The pin the sensor is connected to
int temp_pin = A0;

// Variable used for temeperature
float temperatura = 0.0;

//----------------------------------------- TEMP + UMIDITATE DHT11

// The pin the sensor is connected to
int dht_pin = 8;

// Variable used for reading the value from the sensor
float dht_reading = 0.0;

//----------------------------------------- UMIDITATE SOL

// The pin the sensor is connected to
int umid_sol_pin = A1;

// Variable used for soil humidity
float umiditate_sol = 0.0;

//----------------------------------------- SENZOR IR

// The pin the sensor is connected to
int IR_pin = 9;
// The pin the buzzer is connected to
int buzzer_pin = 10;

//----------------------------------------- DATA STRING

// Define empty data string used to read information via Bluetooth 
String dataIn = "";

//-----------------------------------------


void setup() 
{
  // Default baud rate of the Bluetooth module
  Bluetooth.begin(9600);
  
  // Declare the LEDs as outputs
  pinMode(LED_hol, OUTPUT);
  pinMode(LED_terasa, OUTPUT);
  pinMode(LED_dormitor, OUTPUT);
  pinMode(LED_baie, OUTPUT);

  // Set the IR sensor as input.
  pinMode(IR_pin, INPUT);
  // Set the buzzer as output.
  pinMode(buzzer_pin, OUTPUT);

  servo.attach(11);
  servo.write(angle);
}


void loop() 
{
  //-------------------------- BLUETOOTH IF -------------------------------- 
  
  // If the Bluetooth connection is available
  if (Bluetooth.available() > 0) 
  {
    // Read the string received from the Bluetooth in <dataIn>
    dataIn = Bluetooth.readString();
    delay(20);

    //----------------------------------------- LEDS
    
    if (dataIn.equals("HOL"))
    {
      isHolOn = 1;
    }
    else if (dataIn.equals("HOL_OFF"))
    {
      isHolOn = 0;
    }
    else if (dataIn.equals("TERASA"))
    {
      isTerasaOn = 1;
    }
    else if (dataIn.equals("TERASA_OFF"))
    {
      isTerasaOn = 0;
    }
    else if (dataIn.equals("DORMITOR"))
    {
      isDormitorOn = 1;
    }
    else if (dataIn.equals("DORMITOR_OFF"))
    {
      isDormitorOn = 0;
    }
    else if (dataIn.equals("BAIE"))
    {
      isBaieOn = 1;
    }
    else if (dataIn.equals("BAIE_OFF"))
    {
      isBaieOn = 0;
    }
    
    //----------------------------------------- TEMP EXT

    if (dataIn.equals("TEMP"))
    {
      /*
      float v_med = 0;
      float v_final = 0;
      for (int j = 0; j < 10; j++)
      {
        // Read the analog value. This voltage is stored as a 10bit number
        int reading = analogRead(temp_pin); 
        // Converting that reading to voltage
        float voltage = reading * 5.0;
        voltage /= 1024.0;
        v_med = v_med + voltage;
        delay(200);
      }
       
      // Divide it by 10 beacuse each degree rise results in a 10 millivolt increase
      v_final = v_med / 10;
      // Multiplied by 1000 to convert it to millivolt
      temperatura = v_final * 100 ;

      // Send the value of the temperature via Bluetooth
      Bluetooth.println(temperature);
      */

      // Read the analog value. This voltage is stored as a 10bit number
        int reading = analogRead(temp_pin); 
      // 5*temp/1024 is to convert the 10 bit number to a voltage reading.
      // This is multiplied by 1000 to convert it to millivolt.
      // We then divide it by 10 beacuse each degree rise results in a 10 millivolt increase.
        reading = (5.0* reading *1000.0 )/( 1024 * 10);
      // Send the value of the temperature via Bluetooth
      Bluetooth.println(reading);
    }

    //----------------------------------------- TEMP INT
    if (dataIn.equals("TEMP_INT"))
    {
      dht_reading = DHT.read11(dht_pin);
      // Send the value of the temperature via Bluetooth
      Bluetooth.println(DHT.temperature);
      delay(200);
    }

    //----------------------------------------- UMID AER

    if (dataIn.equals("AER"))
    {
      dht_reading = DHT.read11(dht_pin);
      // Send the value of the humidity via Bluetooth
      Bluetooth.println(DHT.humidity);
      delay(200);
    }

    //----------------------------------------- UMID SOL

    if (dataIn.equals("SOL"))
    {
      // Read the analog value
      int reading = analogRead(umid_sol_pin);
      // Transform the data read in percentage
      umiditate_sol = map(reading, 1023, 0, 0, 100); // convert data into procent
      // Send the value of the humidity via Bluetooth
      Bluetooth.println(umiditate_sol);
      delay(200);
    }

    //----------------------------------------- GEAM

    if (dataIn.equals("GEAM_CLOSE"))
    {
      for(angle = 0; angle < 90; angle++)  
      {                                  
        servo.write(angle);               
        delay(15);                   
      }
    }

    delay(2000);

    if (dataIn.equals("GEAM"))
    {
      for(angle = 90; angle > 0; angle--)  
      {                                  
        servo.write(angle);               
        delay(15);                   
      }
    }
  }
  

  //-------------------------- END BLUETOOTH IF -------------------------------- 


  
  //----------------------------------------- hol
  if (isHolOn == 1)
  {
    digitalWrite(LED_hol, HIGH);
  }
  else
  {
    digitalWrite(LED_hol, LOW);
  }
  //----------------------------------------- terasa
  if (isTerasaOn == 1)
  {
    digitalWrite(LED_terasa, HIGH);
  }
  else
  {
    digitalWrite(LED_terasa, LOW);
  }
  //----------------------------------------- dormitor
  if (isDormitorOn == 1)
  {
    digitalWrite(LED_dormitor, HIGH);
  }
  else
  {
    digitalWrite(LED_dormitor, LOW);
  }
  //----------------------------------------- baie
  if (isBaieOn == 1)
  {
    digitalWrite(LED_baie, HIGH);
  }
  else
  {
    digitalWrite(LED_baie, LOW);
  }
  //----------------------------------------- IR
  if (digitalRead(IR_pin) == LOW) 
  {
    digitalWrite(buzzer_pin, LOW);
  }
  if (digitalRead(IR_pin) == HIGH) 
  {
    digitalWrite(buzzer_pin, HIGH);
  }
  //----------------------------------------- end
  
  delay(20);
}
