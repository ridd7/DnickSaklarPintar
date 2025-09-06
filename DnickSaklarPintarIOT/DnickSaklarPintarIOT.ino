#include <WiFi.h>
#include <FirebaseESP32.h>
#include <ESP32Servo.h> // Atau library servo lain

#define DATABASE_URL "https://smart-switch-dnick-default-rtdb.asia-southeast1.firebasedatabase.app/" // Ganti dengan ID proyek Firebase Anda
#define API_KEY "AIzaSyARfwSkyvgyuqt-H6C_-oZjFJxerhEtM0U" // Atau gunakan Token ID dari Firebase Admin SDK

#define WIFI_SSID "DNICK20"
#define WIFI_PASSWORD "abidind20"

#define SERVO_PIN 13 // Pin GPIO tempat servo terhubung

FirebaseData fbdo;

FirebaseAuth auth;
FirebaseConfig config;
Servo myServo;

void setup() {
  Serial.begin(115200);

  WiFi.begin(WIFI_SSID, WIFI_PASSWORD);
  Serial.print("Connecting to Wi-Fi");
  while (WiFi.status() != WL_CONNECTED) {
    Serial.print(".");
    delay(300);
  }
  Serial.println("\nConnected to Wi-Fi");

  Firebase.begin(&config, &auth); // Ganti dengan token/kunci database jika pakai
  Firebase.reconnectWiFi(true);

  myServo.attach(SERVO_PIN);
  myServo.write(0); // Posisi awal OFF
}

void loop() {
  if (Firebase.ready() && Firebase.getString(fbdo, "/saklar_status")) {
    String statusStr = firebaseData.stringData();
    Serial.print("Saklar Status: ");
    Serial.println(statusStr);

    if (statusStr == "true") {
      myServo.write(90); // Atau sudut yang sesuai untuk ON
      Serial.println("Saklar ON");
    } else {
      myServo.write(0); // Atau sudut yang sesuai untuk OFF
      Serial.println("Saklar OFF");
    }
  }
  delay(100);
}