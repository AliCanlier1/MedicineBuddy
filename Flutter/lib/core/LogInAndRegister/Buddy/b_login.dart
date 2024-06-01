import 'dart:convert';

import 'package:flutter/material.dart';
import 'package:medicine_buddy/core/LogInAndRegister/Buddy/b_register.dart';
import 'package:medicine_buddy/screens/Buddy/buddy_home_screen.dart';
import 'package:http/http.dart' as http;

class BLogin extends StatefulWidget {
  const BLogin({super.key});

  @override
  State<BLogin> createState() => _BLoginState();
}

class _BLoginState extends State<BLogin> {
  final String _title = "Buddy Login";
  final _formKey = GlobalKey<FormState>();
  String phoneNumber = "";
  String password = "";
  void _login(BuildContext context) async {
    if (_formKey.currentState!.validate()) {
      _formKey.currentState!.save();

      final url = Uri.parse("http://192.168.56.1:8080/buddys/get").replace(
          queryParameters: {"buddyId": phoneNumber, "password": password});
      final response = await http.get(url);

      if (!context.mounted) return;

      var data = jsonDecode(response.body);

      if (data["success"]) {
        Navigator.push(
            context,
            MaterialPageRoute(
              builder: (ctx) => BHomePage(
                phoneNumber: phoneNumber,
              ),
            ));
      } else {
        ScaffoldMessenger.of(context).showSnackBar(const SnackBar(
          content: Text('Invalid Credentials!'),
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
        child: Column(
          crossAxisAlignment: CrossAxisAlignment.start,
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
                    onSaved: (newValue) => phoneNumber = newValue!,
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
                      if (value == null || value.isEmpty || value.length != 6) {
                        return "Check your password!";
                      }
                      return null;
                    },
                    onSaved: (newValue) => password = newValue!,
                  ),
                  Row(
                    mainAxisAlignment: MainAxisAlignment.spaceBetween,
                    children: [
                      ElevatedButton(
                        onPressed: () {
                          Navigator.of(context).push(
                            MaterialPageRoute(
                              builder: (ctx) => const BRegister(),
                            ),
                          );
                        },
                        child: const Text("Register"),
                      ),
                      ElevatedButton(
                        onPressed: () {
                          _login(context);
                        },
                        child: const Text("Log In"),
                      )
                    ],
                  )
                ],
              ),
            ),
          ],
        ),
      ),
    );
  }
}
