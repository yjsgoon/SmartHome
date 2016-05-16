#define NOISY_PIN A0
#define HUMI_PIN A2
#define TEMP_PIN A3
#define GAS_PIN A4 // complete
#define LUX_PIN A5

#include <SoftwareSerial.h>

#include "ESP8266.h"

#define SSID "DMap'S"
#define PASS "34693469"
#define DST_IP "192.168.0.78"



SoftwareSerial esp8266Serial = SoftwareSerial(10, 11);
ESP8266 wifi = ESP8266(esp8266Serial);

byte buffer[3];

#define ARDUINO_ID 4
#define GAS_INDEX 1
#define HUMI_INDEX 2
#define TEMP_INDEX 3
#define NOISY_INDEX 4
#define LUX_INDEX 5


int dataArr[8][51]; // 전체 데이터
int countMax[8]; // 전체 데이터 크기
int countInd[8]; // 데이터의 현재인덱스
int countAbo[8]; // 데이터가 이 횟수 이상일시 경보
int countArr[8]; // 현재 데이터 내 횟수
int state[8];
unsigned long timer[8];


void setup() {
  Serial.begin(9600);
  pinMode(NOISY_PIN, INPUT);
  pinMode(HUMI_PIN, INPUT);
  pinMode(TEMP_PIN, INPUT);
  pinMode(GAS_PIN, INPUT);
  pinMode(LUX_PIN, INPUT); 
  countInd[LUX_INDEX] = 0;
  countArr[LUX_INDEX] = 0;
  countMax[LUX_INDEX] = 10;
  countAbo[LUX_INDEX] = 5;
  timer[LUX_INDEX] = 0;
  
  countInd[GAS_INDEX] = 0;
  countArr[GAS_INDEX] = 0;
  countMax[GAS_INDEX] = 10;
  countAbo[GAS_INDEX] = 5;
  timer[GAS_INDEX] = 0;

  countInd[TEMP_INDEX] = 0;
  countArr[TEMP_INDEX] = 0;
  countMax[TEMP_INDEX] = 10;
  countAbo[TEMP_INDEX] = 5;
  timer[TEMP_INDEX] = 0;
   
  countInd[HUMI_INDEX] = 0;
  countArr[HUMI_INDEX] = 0;
  countMax[HUMI_INDEX] = 10;
  countAbo[HUMI_INDEX] = 999; // humi는 안댐
  timer[HUMI_INDEX] = 0;

  countInd[NOISY_INDEX] = 0;
  countArr[NOISY_INDEX] = 0;
  countMax[NOISY_INDEX] = 50;
  countAbo[NOISY_INDEX] = 10;
  timer[NOISY_INDEX] = 0;

  for(int i = 1; i <= 5; i++)
  {
    state[i] = 0;
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
   Serial.println(getStatus(wifi.connect(ESP8266_PROTOCOL_TCP, DST_IP, 9999)));
}

void sendChecker(int sensor_index)
{
  unsigned long currentTime;
  int index, endIndex;
  index = countInd[sensor_index];
  endIndex = (index + 1) % countMax[sensor_index];
  if(dataArr[sensor_index][index] == 1)
  {
    countArr[sensor_index]++;
  }
  if(dataArr[sensor_index][endIndex] == 1)
  {
    countArr[sensor_index]--;
  }
  currentTime = millis();
  // 데이터를 보낸지 30초 미만일시 리턴
  if(currentTime - timer[sensor_index] < 30000 && timer[sensor_index] != 0)
  {
    return;
  }
  if(countArr[sensor_index] >= countAbo[sensor_index])
  {
    if(state[sensor_index] == 0)
    {
      state[sensor_index] = 1;
      buffer[0] = ARDUINO_ID;
      buffer[1] = sensor_index;
      buffer[2] = 1;
      Serial.print(sensor_index);
      Serial.println(" disaster");
      timer[sensor_index] = currentTime;
      wifi.send(buffer);
    }
  }
  else
  {
    if(state[sensor_index] == 1)
    {
      state[sensor_index] = 0;
      buffer[0] = ARDUINO_ID;
      buffer[1] = sensor_index;
      buffer[2] = 0;
      Serial.println("not disaster");
      wifi.send(buffer);
    }
  }
}

void readSensor()
{
  int noisy, humi, temp, gas, lux;

  noisy = digitalRead(NOISY_PIN); // 소음없으면 0 소음있으면 1
  humi   = digitalRead(HUMI_PIN); // 안댐
  temp = digitalRead(TEMP_PIN); // 사람손이닿으면 0 안닿으면 1 
  gas   = analogRead(GAS_PIN); // 평소 80~100 가스있으면 갚올라감
  lux = digitalRead(LUX_PIN); // 빛있으면 0 빛 없으면 1

  if(noisy == 1) 
  {
    dataArr[NOISY_INDEX][countInd[NOISY_INDEX]] = 1;
  }
  else dataArr[NOISY_INDEX][countInd[NOISY_INDEX]] = 0;
  if(humi == 1) dataArr[HUMI_INDEX][countInd[HUMI_INDEX]] = 1;
  else dataArr[HUMI_INDEX][countInd[HUMI_INDEX]] = 0;
  if(temp == 0) dataArr[TEMP_INDEX][countInd[TEMP_INDEX]] = 1;
  else dataArr[TEMP_INDEX][countInd[TEMP_INDEX]] = 0;
   if(gas >= 300) dataArr[GAS_INDEX][countInd[GAS_INDEX]] = 1;
  else dataArr[GAS_INDEX][countInd[GAS_INDEX]] = 0;
   if(lux == 1) dataArr[LUX_INDEX][countInd[LUX_INDEX]] = 1;
  else dataArr[LUX_INDEX][countInd[LUX_INDEX]] = 0;
  
  for(int i = 1; i <= 5; i++)
  {
    sendChecker(i);
  }
  
  // 불부터 진까지 체크
  for(int i = 1; i <= 5; i++)
  {
    countInd[i]++;
    if(countInd[i] == countMax[i])
    {
      countInd[i] = 0;
    }
  }
}


void loop() {
  readSensor();
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
