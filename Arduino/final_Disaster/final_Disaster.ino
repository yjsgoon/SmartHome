#include <ESP8266.h>
#include <ESP8266Client.h>

// 001 -> normal
// 002 -> security

#include "Servo.h"
#include <SoftwareSerial.h>

#include "ESP8266.h"

#define SSID "DMap'S"
#define PASS "34693469"
#define DST_IP "192.168.0.78"

SoftwareSerial esp8266Serial = SoftwareSerial(10, 11);
ESP8266 wifi = ESP8266(esp8266Serial);

Servo myservo;  //servo 객체를 만들어준다 (최대8개 생성 가능)
#define TRIG_PIN 2
#define ECHO_PIN 3
#define LED_PIN 13
#define FIRE_PIN A0
#define VIBE_PIN A1

#define ARDUINO_ID 3
#define FIRE_INDEX 1
#define VIBE_INDEX 2
#define MICR_INDEX 3


int dataArr[4][101]; // 전체 데이터
int countMax[4]; // 전체 데이터 크기
int countInd[4]; // 데이터의 현재인덱스
int countAbo[4]; // 데이터가 이 횟수 이상일시 경보
int countArr[4]; // 현재 데이터 내 횟수
bool sensorFlag[3] = {true, true, true}; // 센서 상태를 위한 변수
unsigned long timer[3];

byte buffer[3];

int flag = 0;

void setup()
{
  Serial.begin(9600);
  myservo.attach(12);   //servo motor 연결핀 설정(디지털 12번핀)
  pinMode(TRIG_PIN, OUTPUT);
  pinMode(ECHO_PIN, INPUT);
  pinMode(LED_PIN, OUTPUT);
  pinMode(FIRE_PIN, INPUT);
  pinMode(VIBE_PIN, INPUT);
  countInd[FIRE_INDEX] = 0;
  countArr[FIRE_INDEX] = 0;
  countMax[FIRE_INDEX] = 5;
  countAbo[FIRE_INDEX] = 3;
  timer[FIRE_INDEX] = 0;

  countInd[VIBE_INDEX] = 0;
  countArr[VIBE_INDEX] = 0;
  countMax[VIBE_INDEX] = 100;
  countAbo[VIBE_INDEX] = 50;
  timer[VIBE_INDEX] = 0;

  countInd[MICR_INDEX] = 0;
  countArr[MICR_INDEX] = 0;
  countMax[MICR_INDEX] = 3;
  countAbo[MICR_INDEX] = 2;
  timer[MICR_INDEX] = 0;

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
  Serial.println(getStatus(wifi.connect(ESP8266_PROTOCOL_TCP, DST_IP, 9998)));
}

void microWave()
{
  int distance = 0;

  digitalWrite(TRIG_PIN, HIGH);
  delayMicroseconds(10);
  digitalWrite(TRIG_PIN, LOW);
  distance = pulseIn(ECHO_PIN, HIGH) / 58.2; /* 센치미터(cm) */

  /* 3cm 이내로 접근 시 LED를 켠다 */
  if (distance <= 5) {
    dataArr[MICR_INDEX][countInd[MICR_INDEX]] = 1;
  }
  else
  {
    dataArr[MICR_INDEX][countInd[MICR_INDEX]] = 0;
  }
  sendChecker(MICR_INDEX);
  countInd[MICR_INDEX]++;
  if (countInd[MICR_INDEX] == countMax[MICR_INDEX])
  {
    countInd[MICR_INDEX] = 0;
  }
}
void sendChecker(int sensor_index)
{
  unsigned long currentTime;
  int index, endIndex;

  if (wifi.available() > 0) {
    wifi.read(buffer, 3);
    wifi.flush();
    flag = buffer[2];
  }
  
  index = countInd[sensor_index];
  endIndex = (index + 1) % countMax[sensor_index];
  if (dataArr[sensor_index][index] == 1)
  {
    countArr[sensor_index]++;
  }
  if (dataArr[sensor_index][endIndex] == 1)
  {
    countArr[sensor_index]--;
  }
  currentTime = millis();

  if (timer[sensor_index] != 0 && currentTime - timer[sensor_index] < 30000) {
    return;
  }
  else
  {
    if(sensor_index == 3)
    {
      Serial.println(currentTime);
      Serial.println(timer[sensor_index]);
    }
  }
  if (countArr[sensor_index] >= countAbo[sensor_index] && sensorFlag[sensor_index] == true) {
    buffer[0] = ARDUINO_ID;
    buffer[1] = sensor_index;
    buffer[2] = 1;
    wifi.send(buffer);
    Serial.println("Disaster");
    sensorFlag[sensor_index] = false;
    timer[sensor_index] = (unsigned long)currentTime;
  }
  else {
    if (sensorFlag[sensor_index] == false) {
      buffer[0] = ARDUINO_ID;
      buffer[1] = sensor_index;
      buffer[2] = 0;
      wifi.send(buffer);
      Serial.println("Not Disaster");
      sensorFlag[sensor_index] = true;
    }
  }
}

void readSensor()
{
  int fire, vibe;
  fire = analogRead(FIRE_PIN);
  vibe = analogRead(VIBE_PIN);
  // 불났는지 체크
  if (fire <= 1000)
  {
    dataArr[FIRE_INDEX][countInd[FIRE_INDEX]] = 1;
  }
  else
  {
    dataArr[FIRE_INDEX][countInd[FIRE_INDEX]] = 0;
  }

  // 진동일어난지 체크
  if (vibe <= 1000)
  {
    dataArr[VIBE_INDEX][countInd[VIBE_INDEX]] = 1;
  }
  else
  {
    dataArr[VIBE_INDEX][countInd[VIBE_INDEX]] = 0;
  }
  // 초음파 체크
  /*
  */
  sendChecker(FIRE_INDEX);
  sendChecker(VIBE_INDEX);
  // 불부터 진까지 체크
  countInd[FIRE_INDEX]++;
  if (countInd[FIRE_INDEX] == countMax[FIRE_INDEX])
  {
    countInd[FIRE_INDEX] = 0;
  }
  countInd[VIBE_INDEX]++;
  if (countInd[VIBE_INDEX] == countMax[VIBE_INDEX])
  {
    countInd[VIBE_INDEX] = 0;
  }
}


void loop() {
  if (flag == 1) {
    int dg = 0;
    for (int i = 0; i < 10; i++) {
      myservo.write(dg);     //각도 0도로 움직임
      delay(300);
      microWave();
      readSensor();
      dg += 10;
    }
    for (int i = 0; i < 10; i++) {
      myservo.write(dg);     //각도 0도로 움직임
      delay(300);
      microWave();
      readSensor();
      dg -= 10;
    }
  }
  else
  {
    delay(300);
    readSensor();
  }

}

String getStatus(bool status) {
  if (status)
    return "OK";

  return "KO";
}

String getStatus(ESP8266CommandStatus status) {
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
