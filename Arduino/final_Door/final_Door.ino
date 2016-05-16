#include <Keypad.h>
#include <Password.h>
#include <SoftwareSerial.h>
#include "ESP8266.h"

#define SSID "DMap'S"
#define PASS "34693469"
#define DST_IP "192.168.0.78"

SoftwareSerial esp8266Serial = SoftwareSerial(10, 11);
ESP8266 wifi = ESP8266(esp8266Serial);
byte buffer[3];

const byte numRows= 4;
const byte numCols= 4;

char keymap[numRows][numCols] =
{
{'1', '2', '3', 'A'},
{'4', '5', '6', 'B'}, 
{'7', '8', '9', 'C'},
{'*', '0', '#', 'D'}
};

Password openPW = Password("0000");
Password securityPW = Password("1111");
char newPW[8] = "0000";
char newSPW[8] = "1111";
unsigned long timer;

byte rowPins[numRows] = {9,8,7,6}; 
byte colPins[numCols]= {5,4,3,2}; 

Keypad myKeypad= Keypad(makeKeymap(keymap), rowPins, colPins, numRows, numCols);

// Tracing Sensor
int tracingInput = A0;
int tracingOutput = 12;
bool tracingFlag = true;
bool flag = false;	//security mode On : true, OFF :false 
char keyMode = '\0';

void setup()
{
  Serial.begin(9600);
  pinMode(tracingInput, INPUT);
  pinMode(tracingOutput, OUTPUT);

  timer = 0;

   // ESP8266
  esp8266Serial.begin(9600);
  wifi.begin();
  wifi.setTimeout(1000);

  /****************************************/
  /******       Basic commands       ******/
  /****************************************/
  // test
  Serial.print("test: ");
  Serial.println(getStatus(wifi.test()));

  // restart
  Serial.print("restart: ");
  Serial.println(getStatus(wifi.restart()));

  // getVersion
  char version[16] = {};
  Serial.print("getVersion: ");
  Serial.print(getStatus(wifi.getVersion(version, 16)));
  Serial.print(" : ");
  Serial.println(version);


  /****************************************/
  /******        WiFi commands       ******/
  /****************************************/
  // joinAP
  Serial.print("joinAP: ");
  wifi.setMode(ESP8266_WIFI_STATION);
  Serial.println(getStatus(wifi.joinAP(SSID, PASS)));


  /****************************************/
  /******       TCP/IP commands      ******/
  /****************************************/
  // connect
   Serial.print("connect: ");
   Serial.println(getStatus(wifi.connect(ESP8266_PROTOCOL_TCP, DST_IP, 9996)));
}


String getStatus(bool status)
{
    if (status)
        return "OK";

    return "KO";
}

String getStatus(ESP8266CommandStatus status)
{
    switch (status) {
    case ESP8266_COMMAND_INVALID:
        return "INVALID";
        break;

    case ESP8266_COMMAND_TIMEOUT:
        return "TIMEOUT";
        break;

    case ESP8266_COMMAND_OK:
        return "OK";
        break;

    case ESP8266_COMMAND_NO_CHANGE:
        return "NO CHANGE";
        break;

    case ESP8266_COMMAND_ERROR:
        return "ERROR";
        break;

    case ESP8266_COMMAND_NO_LINK:
        return "NO LINK";
        break;

    case ESP8266_COMMAND_TOO_LONG:
        return "TOO LONG";
        break;

    case ESP8266_COMMAND_FAIL:
        return "FAIL";
        break;

    default:
        return "UNKNOWN COMMAND STATUS";
        break;
    }
}

bool checkPW(Password &tmpPW)
{
    char tmp = myKeypad.getKey();

    while(tmp!='*'&&tmp!=NO_KEY)
    {
        tmpPW.append(tmp);
	tmp = myKeypad.getKey();
    }    

    if(tmp =='*')
    {
        keyMode = '\0';
        if(tmpPW.evaluate())
        {
            tmpPW.reset();
            return true;
        }
        else
        {
            tmpPW.reset();
            Serial.println("Fail!!");
        }
    }
    return false;
}

void openDoor()
{
    if(checkPW(openPW))
    {
        buffer[0] = 1;
        buffer[1] = 1;
        buffer[2] = 1;
        wifi.send(buffer);
        Serial.println("Open the door!!");
    }
    else  
    { 
        buffer[0] = 1;
        buffer[1] = 1;
        buffer[2] = 0;
        wifi.send(buffer);
    }
}

void securityOff()
{
   if(checkPW(securityPW))
    {
        flag = false;
	buffer[0] = 0;
	buffer[1] = 0;
	buffer[2] = 0;
	wifi.send(buffer);
	Serial.println("Security Mode Off!!");
    }
}


void tracing()
{
  unsigned long currentTime = millis();
  
  if(currentTime - timer < 30000 && timer != 0) {
    return;
  }
int temp = analogRead(tracingInput);
  if( temp <= 600 && tracingFlag == true) {
    Serial.println(temp);
    buffer[0] = 1;
    buffer[1] = 2;
    buffer[2] = 1;
    wifi.send(buffer);
    tracingFlag = false;
    Serial.println("Tracing On");
    timer = currentTime;
  }
  else {
    if(tracingFlag == false) {
      buffer[0] = 1;
      buffer[1] = 2;
      buffer[2] = 0;
      wifi.send(buffer);
      Serial.println("Tracing Off");
      tracingFlag = true;
    }
  }
}

void changePW(Password &tmpPW,char* tmpChar)
{
    int length = 0;
    if(checkPW(tmpPW))
    {
        Serial.println("Press New PW!!");
        char tmp = myKeypad.getKey();
        while(tmp!='#')
        {
            if(tmp!=NO_KEY)
	    {
		tmpChar[length++] = tmp;
            }
            tmp = myKeypad.getKey();           
        }
        tmpChar[length] = '\0';

       	tmpPW.set(tmpChar);

	Serial.println("Change PW!");
     
    }
}
void securityOn()
{// 데이터 보내야되 서버로  여기에다가 보안모드 온!
     flag = true;
     buffer[0] = 0;
     buffer[1] = 0;
     buffer[2] = 1;
     wifi.send(buffer);
     Serial.println("Security Mode On!!");
     keyMode = '\0';
}
void loop()
{
  // 초기 문 비밀번호 -> 0000
  // * -> 문 비밀번호 누르기, # -> 비밀번호 바꾸기,
  // 비밀번호 누르기 : * -> 비밀번호 입력(끝 : *)	

  if(flag)
  {
     tracing(); 
  }
switch(keyMode)
  {
    case '*':
      openDoor();
      break;
    case '#':
      changePW(openPW,newPW);
      break; 
    case 'A':
      securityOn();
      break;
    case 'B':
      securityOff();
      break;
    case 'C':
      changePW(securityPW,newSPW);
      break;
    default:
      char tmpChar = myKeypad.getKey();
      if(tmpChar != NO_KEY)
      { 
        keyMode = tmpChar;
        Serial.println(keyMode);
      }
      break; 
  }
}

