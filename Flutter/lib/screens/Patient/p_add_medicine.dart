import 'dart:convert';

import 'package:flutter/material.dart';
import 'package:flutter/services.dart';
import 'package:medicine_buddy/screens/Patient/p_add_hour.dart';
import 'package:http/http.dart' as http;
import 'package:medicine_buddy/screens/Patient/patient_home_screen.dart';

class PAddMedicine extends StatefulWidget {
  const PAddMedicine({super.key, required this.phoneNumber});

  final String phoneNumber;

  @override
  State<PAddMedicine> createState() => _PAddMedicineState();
}

class _PAddMedicineState extends State<PAddMedicine> {
  final _formKey = GlobalKey<FormState>();
  String medicineName = "";
  String? takingForm;
  String amount = "";
  List<String> hours = [];

  final List<DropdownMenuItem> takingFormItems = [
    const DropdownMenuItem(
      value: "Tablet/Capsule",
      child: Text("Tablet/Capsule"),
    ),
    const DropdownMenuItem(
      value: "Syrup",
      child: Text("Syrup"),
    ),
    const DropdownMenuItem(
      value: "Injection",
      child: Text("Injection"),
    ),
    const DropdownMenuItem(
      value: "Eye drop",
      child: Text("Eye drop"),
    )
  ];

  void submitMedicine() async {
    if (_formKey.currentState!.validate()) {
      _formKey.currentState!.save();
      if (hours.isEmpty) {
        ScaffoldMessenger.of(context).showSnackBar(
          const SnackBar(
            content: Text("Please select the hours"),
            duration: Duration(seconds: 2),
          ),
        );
        return;
      } else if (takingForm == null) {
        ScaffoldMessenger.of(context).showSnackBar(
          const SnackBar(
            content: Text("Please select the taking form"),
            duration: Duration(seconds: 2),
          ),
        );
        return;
      } else {
        Map<String, dynamic> data = {
          "patientId": widget.phoneNumber,
          "medicineName": medicineName,
          "takingForm": takingForm,
          "takingAmount": amount,
          "hours": hours
        };
        final response = await http.post(
            Uri.parse("http://192.168.56.1:8080/patientmedicines/addmedicine"),
            headers: {
              "Content-type": "application/json",
            },
            body: jsonEncode(data));
        if (!context.mounted) return;

        var result = jsonDecode(response.body);

        if (result["success"]) {
          ScaffoldMessenger.of(context).showSnackBar(
              const SnackBar(content: Text("Medicine successfully saved!")));
          Navigator.push(
            context,
            MaterialPageRoute(
              builder: (ctx) => PHomePage(
                phoneNumber: widget.phoneNumber,
              ),
            ),
          );
        } else {
          ScaffoldMessenger.of(context).showSnackBar(const SnackBar(
            content: Text('Medicines has not been saved successfully.'),
          ));
        }
      }
    }
  }

  @override
  Widget build(BuildContext context) {
    return Padding(
      padding: const EdgeInsets.all(12),
      child: SingleChildScrollView(
        child: Form(
          key: _formKey,
          child: Column(
            crossAxisAlignment: CrossAxisAlignment.start,
            children: [
              TextFormField(
                decoration: const InputDecoration(
                    prefixIcon: Icon(Icons.medical_information),
                    hintText: "Enter the name of medicine",
                    labelText: "Medicine Name"),
                autofocus: true,
                maxLength: 25,
                inputFormatters: <TextInputFormatter>[
                  FilteringTextInputFormatter.allow(RegExp('[a-zA-Z]'))
                ],
                validator: (value) {
                  if (value == null || value.isEmpty) {
                    return "Check your medicine name!";
                  }
                  return null;
                },
                onSaved: (newValue) {
                  medicineName = newValue!;
                },
              ),
              Padding(
                padding: const EdgeInsets.fromLTRB(12, 0, 0, 5),
                child: DropdownButton(
                  hint: const Text("Taking Form"),
                  items: takingFormItems,
                  onChanged: (value) {
                    setState(() {
                      takingForm = value;
                    });
                  },
                  value: takingForm,
                ),
              ),
              TextFormField(
                decoration: const InputDecoration(
                    prefixIcon: Icon(Icons.medical_information),
                    hintText: "Enter the taking amount",
                    labelText: "Taking Amount"),
                maxLength: 4,
                keyboardType: TextInputType.number,
                validator: (value) {
                  if (value == null || value.isEmpty || value[0] == "0") {
                    return "Check your amount!";
                  }
                  return null;
                },
                onSaved: (newValue) {
                  amount = newValue!;
                },
              ),
              const SizedBox(
                height: 12,
              ),
              Row(
                mainAxisAlignment: MainAxisAlignment.spaceEvenly,
                children: [
                  ElevatedButton(
                      onPressed: () async {
                        hours = [];
                        final hoursList = Navigator.of(context)
                            .push<List<String>>(MaterialPageRoute(
                          builder: (ctx) {
                            return const PAddHour();
                          },
                        ));
                        hoursList.then((List<String>? result) {
                          if (result != null) {
                            hours = result;
                          } else {
                            return;
                          }
                        });
                      },
                      child: const Text("Select Hours")),
                  ElevatedButton(
                      onPressed: submitMedicine,
                      child: const Text("Save Medicine"))
                ],
              )
            ],
          ),
        ),
      ),
    );
  }
}
