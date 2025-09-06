# DnickSaklarPintar 🏠⚡

**Smart Home Switch Control System with IoT Integration**

DnickSaklarPintar adalah aplikasi mobile berbasis IoT yang memungkinkan anggota keluarga untuk mengontrol saklar listrik rumah secara remote melalui smartphone. Aplikasi ini dilengkapi dengan sistem keamanan fingerprint authentication dan terintegrasi dengan Firebase untuk real-time control.

## Tampilan Aplikasi
<img src="/DnickSaklar.gif" width="270" height="555"/>

## 🌟 Fitur Utama

### 🔌 Smart Switch Control
- **Remote ON/OFF Control**: Kontrol saklar listrik rumah dari manapun
- **Real-time Status**: Monitoring status saklar secara real-time
- **Multi-device Support**: Mendukung kontrol multiple saklar dalam satu rumah

### 🔐 Keamanan & Authentication  
- **Fingerprint Authentication**: Autentikasi biometrik untuk mencegah akses tidak sah
- **User Management**: Manajemen pengguna keluarga yang memiliki akses
- **Secure Access**: Hanya anggota keluarga yang terdaftar yang dapat mengontrol saklar

### ☁️ Cloud Integration
- **Firebase Integration**: Sinkronisasi real-time melalui Firebase
- **Cloud Database**: Penyimpanan data pengguna dan status device di cloud
- **Cross-platform Sync**: Sinkronisasi antar multiple device

### 🚪 Extensible Design
- **Modular Architecture**: Dapat dimodifikasi untuk kontrol perangkat lain
- **Gate Control**: Mudah diadaptasi untuk sistem buka/tutup pagar otomatis
- **IoT Ready**: Siap dikembangkan untuk perangkat IoT lainnya

## 🎯 Use Cases

1. **Smart Home Automation**: Kontrol pencahayaan dan perangkat listrik rumah
2. **Security Enhancement**: Simulasi kehadiran dengan kontrol lampu otomatis
3. **Energy Management**: Monitoring dan kontrol penggunaan listrik
4. **Gate Automation**: Dapat dimodifikasi untuk kontrol pagar otomatis
5. **Family Access Control**: Memberikan akses terkontrol kepada anggota keluarga

## 🛠️ Teknologi yang Digunakan

- **Frontend**: Android (Java/Kotlin)
- **Backend**: Firebase Realtime Database
- **Authentication**: Firebase Auth + Fingerprint Biometric
- **IoT Communication**: WiFi/Internet connectivity
- **Security**: Biometric authentication, encrypted communication

## 📱 Screenshots & Demo

*[Tambahkan screenshots aplikasi di sini]*

## 🚀 Getting Started

### Prerequisites
- Android device dengan fingerprint sensor
- Koneksi internet yang stabil
- Hardware IoT untuk saklar (relay module, microcontroller, dll)

### Installation

1. **Clone repository**
   ```bash
   git clone https://github.com/ridd7/DnickSaklarPintar.git
   cd DnickSaklarPintar
   ```

2. **Setup Firebase**
   - Buat project baru di Firebase Console
   - Download `google-services.json` 
   - Tempatkan file tersebut di direktori `app/`

3. **Configure Hardware**
   - Setup microcontroller (Arduino/ESP32) dengan relay module
   - Konfigurasikan koneksi WiFi dan Firebase
   - Upload firmware ke microcontroller

4. **Build & Install**
   ```bash
   ./gradlew assembleDebug
   adb install app/build/outputs/apk/debug/app-debug.apk
   ```

### Configuration

1. **Firebase Setup**
   ```json
   {
     "switches": {
       "switch1": {
         "name": "Living Room Light",
         "status": false,
         "location": "living_room"
       }
     },
     "users": {
       "userId1": {
         "name": "John Doe",
         "fingerprint_id": "fp_001",
         "permissions": ["switch1", "switch2"]
       }
     }
   }
   ```

2. **Hardware Configuration**
   - Konfigurasikan pin GPIO untuk relay control
   - Setup WiFi credentials
   - Konfigurasi Firebase connection string

## 🔧 Usage

### Adding New Users
1. Buka aplikasi dan login sebagai admin
2. Pilih "Add New User"
3. Daftarkan fingerprint pengguna baru
4. Tentukan permissions untuk setiap saklar

### Controlling Switches
1. Buka aplikasi di smartphone
2. Lakukan fingerprint authentication
3. Pilih saklar yang ingin dikontrol
4. Tap untuk ON/OFF switch
5. Status akan ter-update secara real-time

### Monitoring
- Lihat status real-time semua saklar
- History log aktivitas switching
- Monitor status koneksi IoT device

## 🏗️ Architecture

```
┌─────────────────┐    ┌─────────────────┐    ┌─────────────────┐
│   Mobile App    │    │    Firebase     │    │  IoT Hardware   │
│                 │    │                 │    │                 │
│ - Authentication│◄──►│ - Realtime DB   │◄──►│ - Microcontroller│
│ - UI Controls   │    │ - User Auth     │    │ - Relay Module  │
│ - Status Monitor│    │ - Cloud Sync    │    │ - WiFi Module   │
└─────────────────┘    └─────────────────┘    └─────────────────┘
```

## 🔒 Security Features

- **Biometric Authentication**: Fingerprint-based access control
- **Encrypted Communication**: Secure data transmission via Firebase
- **User Permission System**: Granular control over device access
- **Session Management**: Secure session handling and timeout
- **Audit Trail**: Logging semua aktivitas switching

## 🚪 Modification for Gate Control

Aplikasi ini dapat dengan mudah dimodifikasi untuk kontrol pagar otomatis:

```java
// Example modification for gate control
public class GateController extends BaseDeviceController {
    public void openGate() {
        sendCommand("GATE_OPEN");
        updateStatus("opening");
    }
    
    public void closeGate() {
        sendCommand("GATE_CLOSE"); 
        updateStatus("closing");
    }
}
```

## 🤝 Contributing

1. Fork repository ini
2. Buat feature branch (`git checkout -b feature/AmazingFeature`)
3. Commit perubahan (`git commit -m 'Add some AmazingFeature'`)
4. Push ke branch (`git push origin feature/AmazingFeature`)
5. Buka Pull Request

## 📝 TODO / Roadmap

- [ ] Voice control integration
- [ ] Scheduling system untuk auto ON/OFF
- [ ] Energy consumption monitoring
- [ ] Multiple home support
- [ ] iOS version
- [ ] Web dashboard
- [ ] Integration dengan Google Assistant/Alexa

## ⚠️ Troubleshooting

### Common Issues

**Q: Aplikasi tidak bisa connect ke Firebase**
- Pastikan `google-services.json` sudah ter-configure dengan benar
- Check internet connection
- Verify Firebase project settings

**Q: Fingerprint authentication gagal**
- Pastikan device support fingerprint sensor
- Check fingerprint permission di Android settings
- Re-register fingerprint jika perlu

**Q: IoT device tidak merespon**
- Check WiFi connection pada microcontroller
- Verify Firebase credentials di hardware
- Check power supply relay module
