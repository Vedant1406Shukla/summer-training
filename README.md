# VCCS Calling Application using JavaFX and Socket Programming

## Overview

The **VCCS (Voice Communication Control System)** Calling Application is a JavaFX-based client-server communication system developed using Java Socket Programming. It simulates the core functionality of a voice communication system including call initiation, call acceptance, call termination, ringtone notification, call duration tracking, and session authentication.

The application uses a SIP-inspired signaling mechanism and follows a client-server architecture.

---

## Features

### User Interface
- JavaFX-based Graphical User Interface
- CALL button
- ACCEPT button
- END button
- Status display panel
- Call duration timer
- Custom application logo (`icon.jpg`)
- Dynamic button color changes based on call state

### Networking
- TCP Socket Communication
- Client-Server Architecture
- Multi-threaded communication
- Loopback communication support (`127.0.0.1`)
- Real-time signaling between endpoints

### Signaling Protocol
The application implements simplified SIP-style signaling messages:

| Message | Purpose |
|----------|----------|
| ROOM:<ID> | Authentication Request |
| AUTH_OK | Authentication Successful |
| AUTH_FAIL | Authentication Failed |
| INVITE | Call Initiation |
| 200 OK | Call Accepted |
| BYE | Call Termination |

### Notifications
- Incoming call ringtone (`bell.wav`)
- Visual status updates
- Button state indicators

### Authentication
- Room ID based authentication
- Session validation before call establishment
- Unauthorized connection rejection

---

## Project Structure

### Sender Project

```text
VCCS_Source
│
├── SenderUI.java
├── icon.jpg
└── bell.wav
```

### Receiver Project

```text
VCCS_Destination
│
├── ReceiverUI.java
├── icon.jpg
└── bell.wav
```

---

## System Architecture

```text
+----------------+
|   SenderUI     |
| (Client Side)  |
+--------+-------+
         |
         |
         | TCP Socket
         |
         v
+--------+-------+
|  ReceiverUI    |
| (Server Side)  |
+----------------+
```

---

## Communication Flow

### Authentication

```text
Sender                           Receiver

ROOM:VCCS123 ------------------->

                         Verify Room ID

AUTH_OK <-------------------------
```

### Call Establishment

```text
Sender                           Receiver

CALL
  |
INVITE ------------------------->

                         Bell Rings

                         ACCEPT

200 OK <-------------------------

Timer Starts              Timer Starts
```

### Call Termination

```text
Sender                           Receiver

END
 |
BYE ---------------------------->

Timer Stops              Timer Stops

Status: Call Ended
```

---

## Call State Indicators

### Sender Side

| State | Button Color |
|---------|-------------|
| Idle | Default |
| Calling | Orange |
| Connected | Green |
| Ended | Default |

### Receiver Side

| State | Button Color |
|---------|-------------|
| Waiting | Default |
| Connected | Green |
| Ended | Default |

---

## Prerequisites

### Software Requirements

- Java JDK 8 or above
- JavaFX SDK
- BlueJ IDE or Eclipse IDE
- Windows/Linux/MacOS

### Required Files

Place the following files in both project directories:

```text
icon.jpg
bell.wav
```

---

## Running the Application

### Step 1

Start ReceiverUI first.

```bash
java ReceiverUI
```

Receiver waits for incoming connections.

---

### Step 2

Start SenderUI.

```bash
java SenderUI
```

Sender establishes connection with receiver.

---

### Step 3

Enter Room ID.

Example:

```text
VCCS123
```

Press:

```text
CONNECT
```

---

### Step 4

After successful authentication:

```text
Status : Authenticated
```

CALL button becomes available.

---

### Step 5

Press:

```text
CALL
```

Receiver receives:

```text
Incoming Call...
```

Ringtone starts playing.

---

### Step 6

Receiver presses:

```text
ACCEPT
```

Sender receives:

```text
200 OK
```

Both timers start.

---

### Step 7

Either endpoint can terminate the call using:

```text
END
```

Call timer stops and status changes to:

```text
Status : Call Ended
```

---

## Networking Concepts Demonstrated

- TCP Socket Programming
- Client-Server Communication
- Session Authentication
- Handshaking Mechanism
- Multi-threading
- Real-Time Signaling
- SIP-inspired Call Setup
- Call Session Management

---

## Future Enhancements

- RTP/RTCP Voice Streaming
- UDP Audio Communication
- Video Calling Support
- Multiple Concurrent Sessions
- Dynamic Room Creation
- User Registration System
- Call Logs
- Chat Messaging
- Encryption and Security

---

## Educational Objectives

This project demonstrates:

- JavaFX GUI Development
- Socket Programming
- Network Protocol Design
- Event Driven Programming
- Multithreading
- Session Establishment and Termination
- Client-Server Architecture

---

## Authors

Developed as a Computer Networks / Socket Programming Project using JavaFX and TCP Socket Communication.

---
<img width="318" height="519" alt="Screenshot 2026-06-11 000332" src="https://github.com/user-attachments/assets/6c4b52cd-40ad-4a8f-ba03-ada21441f8bc" />
<br>
<img width="1919" height="1017" alt="Screenshot 2026-06-11 001855" src="https://github.com/user-attachments/assets/a82297ab-73bb-4bbe-8775-47f361f9eaad" />



## License

This project is intended for educational and academic purposes.
