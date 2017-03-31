
/*
  Basic Code to run the OpenDrop V2.1, Research platfrom for digital microfluidics
  Object codes are defined in the OpenDrop.h library
  Written by Urs Gaudenz from GaudiLabs
  2016
 */

#include <SPI.h>
#include <Wire.h>
#include <Adafruit_GFX.h>
#include <Adafruit_SSD1306.h>

#include <OpenDrop.h>
#include "hardware_def.h"

// If using software SPI (the default case):
#define OLED_MOSI  2
#define OLED_CLK   3
#define OLED_DC    11
#define OLED_CS    12
#define OLED_RESET 13
Adafruit_SSD1306 display(OLED_MOSI, OLED_CLK, OLED_DC, OLED_RESET, OLED_CS);
 
#define BL_pin           9           // BL Pin
#define LE_pin           4           // LE Pin
#define CLK_pin         15           // CLK Pin
#define DI_pin          16           // Digital Out Pin

#define SW1_pin          8           // Buton 1 pin
#define SW2_pin         A5           // Buton 1 pin
#define SW3_pin          7           // Buton 1 pin
#define JOY_pin         A4           // Buton 1 pin
#define FEEDBACK_pin    A2           // Buton 1 pin


#define ESP_RESET_pin   A3           // Buton 1 pin
#define ESP_GPIO0_pin   10           // Buton 1 pin
#define ESP_RX_pin       1           // Buton 1 pin
#define ESP_TX_pin       0           // Buton 1 pin


#define LED_LIGHT_pin   5           // Digital  Pin for LEDs
#define SPK_pin         6           // Digital Pin for speaker

boolean sound_flag=false;           // Digital Out Pin 2
boolean light_flag=true;           // Digital Out Pin 2
int JOY_value;
int FEEDBACK_value;

#define BUILDPATH 0x0F
#define RUNPATH   0x0E
#define ADDDROP   0x0A
#define EOTrans    23    //23 (DEC) end of trans. block
#define FULL_ASCII 256  //used for byte subtraction

bool Fluxls[FluxlPad_width][FluxlPad_heigth];

Drop drop[] = {Drop(1),Drop(1),Drop(1), Drop(1), Drop(1)};        //constructor for drop array
#define MAX_NUM_DROPS 5
#define MAX_NUM_TRANS 8
#define MAX_SERIAL_BYTES 50
#define DROP_MOVE_DELAY 100 

int dropMoveMatrix[MAX_NUM_DROPS][MAX_NUM_TRANS];  //holds drop and movements
byte readData[MAX_SERIAL_BYTES];                    //used for input serial stream

// the setup function runs once when you press reset or power the board
void setup() {
  // initialize digital pin 13 as an output.


  pinMode(BL_pin, OUTPUT); //BL pin
  digitalWrite(BL_pin, HIGH);   // set BL 
  pinMode(LE_pin, OUTPUT); //LE pin
  digitalWrite(LE_pin, LOW);   // set LE 
  pinMode(CLK_pin, OUTPUT); //SCK pin
  digitalWrite(CLK_pin, LOW);   // set SCK 
  pinMode(DI_pin, OUTPUT); //DI pin
  digitalWrite(DI_pin, LOW);   // DI LE 

  pinMode(LED_LIGHT_pin, OUTPUT); //SCK pin
  
  pinMode(SW1_pin, INPUT); 
  digitalWrite(SW1_pin, HIGH);  
  pinMode(SW2_pin, INPUT); 
  digitalWrite(SW2_pin, HIGH);  
  pinMode(SW3_pin, INPUT); 
  digitalWrite(SW3_pin, HIGH);  
  pinMode(JOY_pin, INPUT); 

  Serial.begin(9600);

  // by default, we'll generate the high voltage from the 3.3v line internally! (neat!)
  display.begin(SSD1306_SWITCHCAPVCC);
  // init done
  
  // Show image buffer on the display hardware.
  // Since the buffer is intialized with an Adafruit splashscreen
  // internally, this will display the splashscreen.
  display.display();
  delay(2000);

  // Clear the buffer.
  display.clearDisplay();
  display.dim(false);

  display.setTextSize(1);
  display.setTextColor(WHITE);
  display.setCursor(5,20);
  display.println("Loading protocol...");
  display.display();
  delay(500);
  display.setCursor(5,29);
  display.println("Press Start");
  display.display();

  /*initialize drops here*/
//  drop[0].begin(0,0);
//  drop[1].begin(10,4);
//  drop[2].begin(15,6);
  
  update(0);
  
  tone(SPK_pin,2000,100);
  delay(100);
  tone(SPK_pin,2000,100);
  delay(200);
  while(digitalRead(SW2_pin) == HIGH);

  if (light_flag)   digitalWrite(LED_LIGHT_pin, HIGH);   // set SCK 

  zeroFillDataStream();
  zeroFillDropMatrix();
  Serial.setTimeout(4000);
}
  
void loop() {

    static int activeDrops = 0;
    static int test = 0;
    
    //read bytes
    while (Serial.available() > 0) {
      /*configuration to read serial data*/
      Serial.readBytes(readData, Serial.available());
      delay(1000); 
    }
    
    if (readData[0] == 0) {
      /*do nothing, cont to poll*/
    }
    else if (readData[0] == BUILDPATH) {
      /*fill matrix with movements*/
      fillDropMoveMatrix();
      //checkMatrix();
      zeroFillDataStream();
    }
    else if (readData[0] == RUNPATH) {
      /*move drops, aftr matrix filled*/
      moveTheDrops(activeDrops);
      blinkLED();
      zeroFillDataStream();
      zeroFillDropMatrix ();
    }
    else if(readData[0] == ADDDROP) {
      addDropsToGrid(&activeDrops);
      zeroFillDataStream();
    }
}

void addDropsToGrid (int* activeDrops) {
  
  int ptr = 1, dropNum = 0, xPos = 0, yPos = 0;

  while (readData[ptr] != EOTrans) {
    if (readData[ptr] == 'd') {
      ptr++;
      dropNum = readData[ptr];
      ptr++;
  
      xPos = (int) readData[ptr];
      ptr++;
      yPos = (int) readData[ptr];
      ptr++; 

      drop[dropNum].begin(xPos, yPos);
      (*activeDrops)++;
//      blinkLED();
    }
  }

  update(*activeDrops);
  blinkLED();
}

void checkMatrix() {

  int blinkCount = 1;
  
  for (int i = 0; i < MAX_NUM_DROPS; i++) {
    for (int j = 0; j < MAX_NUM_TRANS; j++) {
      if (dropMoveMatrix[i][j] != 3 && dropMoveMatrix[i][j] != -3) {
          blinkCount = 3;
      }  
    } 
  }

  /*one is success, three is error*/
  if (blinkCount == 3) {
    digitalWrite(LED_LIGHT_pin,LOW);
  }else {
    /*high is good*/
    digitalWrite(LED_LIGHT_pin,HIGH);
  }
  
}
 

/*fill the data structure with drop movements*/
void fillDropMoveMatrix () {

  int dropNum = -1, ptr = 1, trans = 0;

  while (readData[ptr] != EOTrans) {
    if (readData[ptr] == 'd') {
      ptr++;
      dropNum = readData[ptr];
      ptr++;
      /*start filling in the row*/  
      trans = findNextMovement(dropNum);
      while (readData[ptr] != 'd' && readData[ptr] != EOTrans) {      
        if (readData[ptr] > 239) {
          dropMoveMatrix[dropNum][trans] = readData[ptr] - FULL_ASCII;  
        }
        else {
          dropMoveMatrix[dropNum][trans] = readData[ptr];
        }
        trans++;
        ptr++; 
      }
    }
  }
  /*notify usr the data has been read*/
  blinkLED();
}

int findNextMovement (int dropNum) {

  //in the matrix, with given dropnum
  //find both 0 1 are 0 and then return the value 
  int trans, found = 0;  
  for (trans = 0; trans < MAX_NUM_TRANS && found == 0; trans += 2) {
    if (dropMoveMatrix[dropNum][trans] == 0 && dropMoveMatrix[dropNum][trans + 1] == 0) {
        found = 1;
      }
  }
  return trans - 2;
}
  
int moveDrop(int dropNum, int trans, int movement, int activeDrops) {
  /*vertical movement*/
  while (movement != 0) {
    /*vertical movement*/
    if (trans) {
      if (movement > 0) {
          movement--;
          drop[dropNum].move_down();
        }  
      else {
        movement++;
        drop[dropNum].move_up();
      }
    } 
    /*horizontal movement*/
    else {
      if (movement > 0) {
          movement--;
          drop[dropNum].move_right();
        }  
      else {
          movement++;
          drop[dropNum].move_left();
      }
    }
    update(activeDrops);
    delay(DROP_MOVE_DELAY); 
  }

}

/*trace through matrix to move drops*/
void moveTheDrops(int activeDrops) {

  int finish = 0, trans = 0, transVal = 0, doneRow = 0;
  for (int dropNum = 0; dropNum < MAX_NUM_DROPS; dropNum++) {
    for (int trans = 0; trans < MAX_NUM_TRANS; trans++) {
      /*if a value, move the drop that many times*/
      if (dropMoveMatrix[dropNum][trans] != 0) {
          moveDrop(dropNum, trans % 2, dropMoveMatrix[dropNum][trans], activeDrops);
      }    
    }
  }
  
/*notify the user the data has been read*/
  blinkLED();
}

void blinkLED() {

  for (int i = 0; i < 3; i++) {
      digitalWrite(LED_LIGHT_pin, HIGH);
      delay(100);
      digitalWrite(LED_LIGHT_pin, LOW);
      delay(100);   
  }
  digitalWrite(LED_LIGHT_pin,HIGH);
}

/*debug function*/
//void printData() {
//  char data[5];
//  for (int i = 0; i < MAX_NUM_DROPS; i++) {
//    for (int j = 0; j < MAX_NUM_TRANS; j++) {
//      //Serial.print("hello");
//      Serial.print(dropMoveMatrix[i][j]);
//      Serial.print('\n');
//      delay(100);
//    }
//  }
//}
 
void zeroFillDropMatrix () {
  
  for (int i = 0; i < MAX_NUM_DROPS; i++) {
    for (int j = 0; j < MAX_NUM_TRANS; j++) {
      dropMoveMatrix[i][j] = 0;
    } 
  } 
}
void zeroFillDataStream () {
  
  for (int i = 0; i < MAX_SERIAL_BYTES; i++) {
    readData[i] = 0;
  } 
}

void update(int activeDrops)
{
  
const byte pad_lookup_x [64] PROGMEM = {
0,0,0,0,1,1,1,1,
2,2,2,2,3,3,3,3,
4,4,4,4,5,5,5,5,
6,7,7,6,6,7,7,6,
6,7,7,6,7,6,7,6,
5,5,5,5,4,4,4,4,
3,3,3,3,2,2,2,2,
1,1,1,1,0,0,0,0
};

const byte pad_lookup_y [64] PROGMEM = {
3,2,1,0,3,2,0,1,
3,1,0,2,3,0,1,2,
3,0,1,2,0,1,2,3,
0,1,2,2,1,0,3,3,
4,4,7,6,5,5,6,7,
4,5,6,7,5,6,7,4,
5,6,7,4,5,7,6,4,
6,7,5,4,7,6,5,4
};

clear_Fluxels();                     //clear Fluxel Array

//Fill Fluxel Array
for (int i = 0; i < activeDrops; i++) 
{
  Fluxls[drop[i].position_x()][drop[i].position_y()]=true;
};

// clear Display
  display.clearDisplay();
// write coordinates
 display.setCursor(18,4);
  display.print("X: ");
  display.print(drop[0].position_x());
  display.print(" Y: ");
  display.print(drop[0].position_y());

// draw Grid
  for (int x = 1; x <FluxlPad_width ; x++) 
    for (int y = 1; y <FluxlPad_heigth ; y++) 
  display.drawPixel(x*6+16,y*6+16,WHITE);
  
  
// draw Fluxels
      for (int x = 0; x <FluxlPad_width ; x++) 
    for (int y = 0; y <FluxlPad_heigth ; y++) 
   if (Fluxls[x][y]) {
           display.fillRect(x*6+17, y*6+17, 5,5, 1);
    }
     
// draw Rect
     display.drawRect(16, 16, 97,48, 1);

// update display
    display.display();
   
   

// Fill chip

    digitalWrite(LE_pin, LOW);   // set LE 
    digitalWrite(CLK_pin, LOW);   // set SCK 
   
    for (int i = 0; i <64 ; i++) 

{
   digitalWrite(DI_pin,Fluxls[pad_lookup_x[i]][pad_lookup_y[i]]);

   digitalWrite(CLK_pin, HIGH);   // set LE
   digitalWrite(CLK_pin, LOW);   // set LE
 
  //Serial.println(Fluxls[pad_lookup_x[i]][pad_lookup_y[i]]);
   };
   
     for (int i = 0; i <64 ; i++) 
{
   digitalWrite(DI_pin,Fluxls[15-pad_lookup_x[i]][7-pad_lookup_y[i]]);

   digitalWrite(CLK_pin, HIGH);   // set LE
   digitalWrite(CLK_pin, LOW);   // set LE
 
  //Serial.println(Fluxls[pad_lookup_x[i]][pad_lookup_y[i]]);
   };
   
   
  
    digitalWrite(LE_pin, HIGH);   // set LE 
    digitalWrite(LE_pin, LOW);   // set LE 
    
FEEDBACK_value=analogRead(FEEDBACK_pin);

    
 if(sound_flag)   tone(SPK_pin,FEEDBACK_value*2,50);  
  
};


void clear_Fluxels()
{
    for (int x = 0; x <FluxlPad_width ; x++) 
    for (int y = 0; y <FluxlPad_heigth ; y++) 
  Fluxls[x][y]=false;

};


