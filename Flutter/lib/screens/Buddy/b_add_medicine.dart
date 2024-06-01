import 'package:flutter/material.dart';

class BAddMedicine extends StatefulWidget {
  const BAddMedicine({super.key});

  @override
  State<BAddMedicine> createState() => _BAddMedicineState();
}

class _BAddMedicineState extends State<BAddMedicine> {
  @override
  Widget build(BuildContext context) {
    return Container(child: const Center(child: Text("Add Medicine")));
  }
}
