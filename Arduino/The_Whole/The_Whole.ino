#include <Keypad.h>
#include <SoftwareSerial.h>

#include "ESP8266.h"

#define SSID "DMap'S"
#define PASS "34693469"
#define DST_IP "192.168.0.78"

SoftwareSerial esp8266Serial = SoftwareSerial(10, 11);
ESP8266 wifi = ESP8266(esp8266Serial);

int led[6] = {2, 3, 4, 5, 6, 7};

byte buffer[3];

void setup() {
  Serial.begin(9600);
  
  for(int i=0; i<6; i++) {
    pinMode(led[i], OUTPUT);
  }
  
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
   Serial.println(getStatus(wifi.connect(ESP8266_PROTOCOL_TCP, DST_IP, 9997)));
}

void loop() {
  if(wifi.available()) {
    wifi.read(buffer, 3);
    if(buffer[0] == 2) {
      switch(buffer[1]) {
        case 1 :
        if(buffer[2] == 1) {
          digitalWrite(led[0], HIGH);
        }
        else {
          digitalWrite(led[0], LOW);
        }
        break;
        case 2 :
        if(buffer[2] == 1) {
          digitalWrite(led[1], HIGH);
        }
        else {
          digitalWrite(led[1], LOW);
        }
        break;
        case 3 :
        if(buffer[2] == 1) {
          digitalWrite(led[2], HIGH);
        }
        else {
          digitalWrite(led[2], LOW);
        }
        break;
        case 4 :
        if(buffer[2] == 1) {
          digitalWrite(led[3], HIGH);
        }
        else {
          digitalWrite(led[3], LOW);
        }
        break;
        case 5 :
        if(buffer[2] == 1) {
          digitalWrite(led[4], HIGH);
        }
        else {
          digitalWrite(led[4], LOW);
        }
        break;
        case 6 :
        if(buffer[2] == 1) {
          digitalWrite(led[5], HIGH);
        }
        else {
          digitalWrite(led[5], LOW);
        }
        break;
        default :
        break;
      }
    }
  }
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
