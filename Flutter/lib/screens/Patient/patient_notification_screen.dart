import 'package:flutter/material.dart';

class PatientNotificationScreen extends StatelessWidget {
  const PatientNotificationScreen({super.key});
  static const route = "/patient-notification-screen";

  @override
  Widget build(BuildContext context) {
    return Scaffold(
        appBar: AppBar(
          title: const Text("Keep taking your medicines!"),
        ),
        body: Column(
          mainAxisAlignment: MainAxisAlignment.center,
          crossAxisAlignment: CrossAxisAlignment.center,
          children: [
            ElevatedButton(
                onPressed: () {
                  Navigator.of(context).pop(DateTime.now());
                },
                child: const Text("Continue"))
          ],
        ));
  }
}
