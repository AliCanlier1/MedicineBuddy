# MedicineBuddy - Medicine Reminder System
This project is a system developed to ensure that people take their medications on time. The application supports three types of users: patients, buddies, and doctors.

## What is MedicineBuddy?
### Features

- **Patients**: Patients are paired with a buddy to ensure they take their medications on time. If a patient misses their medication, a notification is sent to their buddy.
- **Buddies**: Buddies can add multiple patients to their care list. They receive alerts when any of their patients miss a medication dose, allowing them to follow up and ensure the patient takes their medication.
- **Doctors**: Doctors can monitor multiple patients, reviewing their medication adherence history. They can track whether patients are consistently taking their prescribed medications.

## Technologies Used

- **Mobile Application**: Developed using Flutter, ensuring a smooth and responsive user experience across both Android and iOS devices.
- **Backend**: Built with Spring Boot, providing a robust and scalable server-side application.
- **Database**: MySQL is used for reliable and efficient data storage and retrieval.
- **Backend as a Service (BaaS)**: Firebase is utilized for handling real-time notifications and authentication.

## Database Schema
<img src="https://github.com/AliCanlier1/MedicineBuddy/assets/114236364/7e974e6f-f6c0-4bc7-aed6-c45acf1a2f13" width="250" height="450">

## Screens
### Patient Screen
<img src="https://github.com/AliCanlier1/MedicineBuddy/assets/114236364/2ba49b2a-2624-482c-8821-ba22b0e98ef1" width="250" height="450"><br>
Patients can see if they have taken their daily and monthly medication, can add medicines and see the medicines they have taken in the past.

### Notification System
<img src="https://github.com/AliCanlier1/MedicineBuddy/assets/114236364/5027bc59-ad99-4568-b63c-647534f06f00" width="250" height="450">
<img src="https://github.com/AliCanlier1/MedicineBuddy/assets/114236364/7ea49d06-d196-4c24-b994-7282777cc1ad" width="250" height="450"><br>
Patients are notified according to the hours they add. If the patient does not receive the notification, the same notification goes to the patient's buddys.

### Buddy and Doctor Screen
<img src="https://github.com/AliCanlier1/MedicineBuddy/assets/114236364/a08ebe06-bda9-4a17-9e89-defba84a4cbf" width="250" height="450"><br>
Buddies and doctors can go to the patient screen to add patients, search and review the status of their current patients.

### Critical Notification System
<img src="https://github.com/AliCanlier1/MedicineBuddy/assets/114236364/fbfdfac4-3861-47a7-baab-35d9f612af57" width="250" height="450">
<img src="https://github.com/AliCanlier1/MedicineBuddy/assets/114236364/ea1fb9e5-b4c5-4f31-a0d3-2dcf7bf68ef4" width="250" height="450"><br>
If the medication the patient is not taking is critical for the patient, the app sends notifications to both the patient and the buddys in at certain time intervals.

