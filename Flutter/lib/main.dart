import 'dart:io';

import 'package:firebase_core/firebase_core.dart';
import 'package:flutter/material.dart';
import 'package:medicine_buddy/core/ThemeAndDesign/theme.dart';
import 'package:medicine_buddy/screens/Patient/patient_home_screen.dart';

final navigatorKey = GlobalKey<NavigatorState>();

void main() async {
  WidgetsFlutterBinding.ensureInitialized();
  Platform.isAndroid
      ? await Firebase.initializeApp(
          options: const FirebaseOptions(
              apiKey: "Api Key",
              appId: "1:570562302897:android:60fafe9d2a3715a2c12390",
              messagingSenderId: "570562302897",
              projectId: "medicinebuddy-23bed"))
      : await Firebase.initializeApp();
  runApp(const MyApp());
}

class MyApp extends StatelessWidget {
  const MyApp({super.key});
  final String _title = "Medicine Buddy";

  @override
  Widget build(BuildContext context) {
    return MaterialApp(
      title: _title,
      theme: appTheme,
      home: const PHomePage(phoneNumber: "5555555555"),
      debugShowCheckedModeBanner: false,
    );
  }
}
