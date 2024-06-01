import 'dart:convert';

import 'package:firebase_messaging/firebase_messaging.dart';
import 'package:flutter/material.dart';
import 'package:flutter/services.dart';
import 'package:http/http.dart' as http;
import 'package:medicine_buddy/core/LogInAndRegister/Patient/p_login.dart';

class PRegister extends StatefulWidget {
  const PRegister({super.key});

  @override
  State<PRegister> createState() => _PRegisterState();
}

class _PRegisterState extends State<PRegister> {
  final String _title = "Register as a patient";
  final _formKey = GlobalKey<FormState>();
  String phoneNumber = "";
  String name = "";
  String surname = "";
  String password = "";
  int bornDate = 0;
  String? key;

  void setUpPushNotifications() async {
    final fcm = FirebaseMessaging.instance;

    await fcm.requestPermission();

    key = await fcm.getToken();
  }

  @override
  void initState() {
    super.initState();
    setUpPushNotifications();
  }

  void _login(BuildContext context) async {
    if (_formKey.currentState!.validate()) {
      _formKey.currentState!.save();

      Map<String, dynamic> data = {
        "id": phoneNumber,
        "password": password,
        "key": key,
        "name": name,
        "surname": surname,
        "dateOfBirth": bornDate
      };

      final response =
          await http.post(Uri.parse("http://192.168.56.1:8080/patients/add"),
              headers: {
                "Content-type": "application/json",
              },
              body: jsonEncode(data));

      if (!context.mounted) return;

      var result = jsonDecode(response.body);

      if (result["success"]) {
        ScaffoldMessenger.of(context).showSnackBar(
            SnackBar(content: Text("Successfully registered $name")));
        Navigator.push(
          context,
          MaterialPageRoute(
            builder: (ctx) => const PLogin(),
          ),
        );
      } else {
        ScaffoldMessenger.of(context).showSnackBar(const SnackBar(
          content: Text('You have already an account!'),
        ));
      }
    }
  }

  @override
  Widget build(BuildContext context) {
    return Scaffold(
      appBar: AppBar(title: Text(_title)),
      body: Padding(
        padding: const EdgeInsets.all(10),
        child: SingleChildScrollView(
          child: Column(
            children: [
              Form(
                key: _formKey,
                child: Column(
                  children: [
                    TextFormField(
                      decoration: const InputDecoration(
                        prefixIcon: Icon(Icons.phone),
                        hintText: "Enter your phone number",
                        labelText: "Phone Number",
                      ),
                      autofocus: true,
                      keyboardType: TextInputType.phone,
                      maxLength: 10,
                      validator: (value) {
                        if (value == null ||
                            value.isEmpty ||
                            value[0] != '5' ||
                            value.length != 10) {
                          return "Check your phone number!";
                        }
                        return null;
                      },
                      onSaved: (value) => phoneNumber = value!,
                    ),
                    TextFormField(
                      decoration: const InputDecoration(
                        prefixIcon: Icon(Icons.account_circle_rounded),
                        hintText: "Enter your name",
                        labelText: "Name",
                      ),
                      inputFormatters: <TextInputFormatter>[
                        FilteringTextInputFormatter.allow(RegExp('[a-zA-Z]'))
                      ],
                      keyboardType: TextInputType.name,
                      maxLength: 30,
                      validator: (value) {
                        if (value == null || value.isEmpty) {
                          return "Check your name!";
                        }
                        return null;
                      },
                      onSaved: (value) => name = value!,
                    ),
                    TextFormField(
                      decoration: const InputDecoration(
                        prefixIcon: Icon(Icons.account_circle_rounded),
                        hintText: "Enter your surname",
                        labelText: "Surname",
                      ),
                      inputFormatters: <TextInputFormatter>[
                        FilteringTextInputFormatter.allow(RegExp('[a-zA-Z]'))
                      ],
                      keyboardType: TextInputType.name,
                      maxLength: 30,
                      validator: (value) {
                        if (value == null || value.isEmpty) {
                          return "Check your surname!";
                        }
                        return null;
                      },
                      onSaved: (value) => surname = value!,
                    ),
                    TextFormField(
                      obscureText: true,
                      decoration: const InputDecoration(
                        prefixIcon: Icon(Icons.password_rounded),
                        hintText: "Enter your password",
                        labelText: "Password",
                      ),
                      keyboardType: TextInputType.visiblePassword,
                      maxLength: 6,
                      validator: (value) {
                        if (value == null ||
                            value.isEmpty ||
                            value.length != 6) {
                          return "Check your password!";
                        }
                        return null;
                      },
                      onSaved: (value) => password = value!,
                    ),
                    TextFormField(
                      decoration: const InputDecoration(
                        prefixIcon: Icon(Icons.password_rounded),
                        hintText: "Enter your born year",
                        labelText: "Born Year",
                      ),
                      maxLength: 4,
                      keyboardType: TextInputType.datetime,
                      validator: (value) {
                        if (value == null ||
                            value.isEmpty ||
                            value.length != 4 ||
                            int.tryParse(value)! < 1950) {
                          return "Check your born date!";
                        }
                        return null;
                      },
                      onSaved: ((value) => bornDate = int.parse(value!)),
                    ),
                    Row(
                      mainAxisAlignment: MainAxisAlignment.end,
                      children: [
                        ElevatedButton(
                          onPressed: () => _login(context),
                          child: const Text("Register"),
                        )
                      ],
                    )
                  ],
                ),
              )
            ],
          ),
        ),
      ),
    );
  }
}
