import 'dart:async';

import 'package:firebase_messaging/firebase_messaging.dart';
import 'package:flutter/material.dart';
import 'package:medicine_buddy/screens/Patient/patient_notification_screen.dart';

Future<void> handleBackgroundMessage(RemoteMessage message) async {}

class PatientNotificationHandler {
  final firebaseMessaging = FirebaseMessaging.instance;

  void handleNotificationClick(BuildContext context, RemoteMessage message) {
    Future<DateTime?> takenTimeFuture = Navigator.of(context)
        .push<DateTime>(MaterialPageRoute(builder: (context) {
      return const PatientNotificationScreen();
    }));

    Future.delayed(const Duration(minutes: 1), () async {
      DateTime? takenTime = await takenTimeFuture;
      if (takenTime == null) {
        return;
      } else {
        print(message.sentTime!);
        Duration difference = takenTime.difference(message.sentTime!);
        int differenceInSeconds = difference.inSeconds.abs();

        if (differenceInSeconds < 60) {
          print('Difference is lower than 1 minute');
        } else {
          print('Difference is equal or greater than 1 minute');
        }
      }
    });
  }

  Future<void> initNotifications(BuildContext context) async {
    FirebaseMessaging.onBackgroundMessage(handleBackgroundMessage);

    FirebaseMessaging.onMessageOpenedApp.listen((RemoteMessage message) {
      handleNotificationClick(context, message);
      print("On Message OpenedApp fonksiyonu debug ${message.data}");
    });
  }
}
