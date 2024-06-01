import 'package:flutter/material.dart';
import 'package:flutter/services.dart';

class PAddHour extends StatefulWidget {
  const PAddHour({super.key});

  @override
  State<PAddHour> createState() => _PAddHourState();
}

class _PAddHourState extends State<PAddHour> {
  String? hourFrequency;
  String? selectedTime;
  List<String> selectedHours = [];
  TextEditingController? _controller;
  List<Widget> customHourButtons = [];

  final List<DropdownMenuItem> hoursDropdownButtonItems = [
    const DropdownMenuItem(
      value: "3 times per day",
      child: Text("3 times per day"),
    ),
    const DropdownMenuItem(
      value: "2 times per day",
      child: Text("2 times per day"),
    ),
    const DropdownMenuItem(
      value: "1 time per day",
      child: Text("1 time per day"),
    ),
    const DropdownMenuItem(
      value: "Other",
      child: Text("Other"),
    ),
  ];

  Widget generateRoutineHoursButton(BuildContext context) {
    return ElevatedButton(
        onPressed: () async {
          final TimeOfDay? selectedHour = await showTimePicker(
            context: context,
            initialTime: const TimeOfDay(hour: 9, minute: 0),
            cancelText: "Cancel",
            confirmText: "Select",
            initialEntryMode: TimePickerEntryMode.dial,
            builder: (BuildContext context, Widget? child) {
              return MediaQuery(
                data: MediaQuery.of(context)
                    .copyWith(alwaysUse24HourFormat: true),
                child: child!,
              );
            },
          );
          if (selectedHour == null) {
            return;
          } else {
            setState(() {
              selectedHours = [];
              selectedTime = null;
              final String hour = selectedHour.hour.toString().padLeft(2, "0");
              final String minute =
                  selectedHour.minute.toString().padLeft(2, "0");
              selectedTime = "$hour:$minute";
            });
          }
        },
        child: const Text("Select Hour"));
  }

  Widget generateRoutineHours(
      BuildContext context, String hourFrequency, String time) {
    String hour = time.substring(0, 2);
    String minute = time.substring(3);
    switch (hourFrequency) {
      case "3 times per day":
        selectedHours = [
          "$hour:$minute:00",
          "${((int.parse(hour) + 6) % 24).toString().length == 1 ? "0${((int.parse(hour) + 6) % 24)}" : ((int.parse(hour) + 6) % 24)}:$minute:00",
          "${((int.parse(hour) + 12) % 24).toString().length == 1 ? "0${((int.parse(hour) + 12) % 24)}" : ((int.parse(hour) + 12) % 24)}:$minute:00"
        ];
        return Row(
          mainAxisAlignment: MainAxisAlignment.spaceEvenly,
          children: [
            ElevatedButton(onPressed: () {}, child: Text("$hour:$minute")),
            ElevatedButton(
                onPressed: () {},
                child: Text(
                    "${((int.parse(hour) + 6) % 24).toString().length == 1 ? "0${((int.parse(hour) + 6) % 24)}" : ((int.parse(hour) + 6) % 24)}:$minute")),
            ElevatedButton(
                onPressed: () {},
                child: Text(
                    "${((int.parse(hour) + 12) % 24).toString().length == 1 ? "0${((int.parse(hour) + 12) % 24)}" : ((int.parse(hour) + 12) % 24)}:$minute")),
          ],
        );
      case "2 times per day":
        selectedHours = [
          "$hour:$minute:00",
          "${((int.parse(hour) + 8) % 24).toString().length == 1 ? "0${((int.parse(hour) + 8) % 24)}" : ((int.parse(hour) + 8) % 24)}:$minute:00"
        ];
        return Row(
          mainAxisAlignment: MainAxisAlignment.spaceEvenly,
          children: [
            ElevatedButton(onPressed: () {}, child: Text("$hour:$minute")),
            ElevatedButton(
                onPressed: () {},
                child: Text(
                    "${((int.parse(hour) + 8) % 24).toString().length == 1 ? "0${((int.parse(hour) + 8) % 24)}" : ((int.parse(hour) + 8) % 24)}:$minute")),
          ],
        );
      case "1 time per day":
        selectedHours = ["$hour:$minute:00"];
        return Row(
          mainAxisAlignment: MainAxisAlignment.spaceEvenly,
          children: [
            ElevatedButton(onPressed: () {}, child: Text("$hour:$minute")),
          ],
        );
      default:
        return const Text("Please select a hour.");
    }
  }

  Widget generateCustomHoursTextField(BuildContext context) {
    return Column(
      children: [
        TextFormField(
          controller: _controller,
          autofocus: true,
          decoration: const InputDecoration(
              labelText: "Hours Frequency",
              hintText: "Enter mediciens frequency"),
          inputFormatters: <TextInputFormatter>[
            FilteringTextInputFormatter.allow(RegExp('[1-5]'))
          ],
          keyboardType: TextInputType.number,
          onFieldSubmitted: (value) {
            if (value == "") {
              return;
            }
            selectedHours = [];
            generateCustomHours(context, int.parse(value));
            setState(() {});
          },
          onTap: () {
            setState(() {
              customHourButtons = [];
            });
          },
        )
      ],
    );
  }

  List<Widget> generateCustomHours(BuildContext context, int noOfHour) {
    for (int i = 0; i < noOfHour; i++) {
      selectedHours.add("notSelected");
      customHourButtons.add(ElevatedButton(
          onPressed: () async {
            final TimeOfDay? selectedHour = await showTimePicker(
              context: context,
              initialTime: const TimeOfDay(hour: 9, minute: 0),
              cancelText: "Cancel",
              confirmText: "Select",
              initialEntryMode: TimePickerEntryMode.dial,
              builder: (BuildContext context, Widget? child) {
                return MediaQuery(
                  data: MediaQuery.of(context)
                      .copyWith(alwaysUse24HourFormat: true),
                  child: child!,
                );
              },
            );
            if (selectedHour == null) {
              return;
            } else {
              setState(() {
                final String hour =
                    selectedHour.hour.toString().padLeft(2, "0");
                final String minute =
                    selectedHour.minute.toString().padLeft(2, "0");
                String selectedTime = "$hour:$minute:00";
                selectedHours[i] = selectedTime;
              });
            }
          },
          child: const Text("Select Hour")));
    }
    customHourButtons.add(submitButton(context));
    return customHourButtons;
  }

  Widget submitButton(BuildContext context) {
    return ElevatedButton(
      onPressed: () {
        if (hourFrequency != "Other") {
          Navigator.of(context).pop(selectedHours);
        } else {
          if (selectedHours.contains("notSelected")) {
            List<int> notSelectedIndexList = [];
            for (int i = 1; i < selectedHours.length + 1; i++) {
              if (selectedHours[i - 1] == "notSelected") {
                notSelectedIndexList.add(i);
              }
            }
            ScaffoldMessenger.of(context).showSnackBar(SnackBar(
              content: Text(
                  "Please select the ${notSelectedIndexList.toString().replaceAll(RegExp(r"[\[\]]"), "")}. hours."),
              duration: const Duration(seconds: 2),
            ));
          } else {
            Navigator.of(context).pop(selectedHours);
          }
        }
      },
      child: const Text("Save"),
    );
  }

  @override
  Widget build(BuildContext context) {
    return Scaffold(
      appBar: AppBar(title: const Text("Add Hour")),
      body: SingleChildScrollView(
        padding: const EdgeInsets.all(12),
        child: Column(
          children: [
            Row(
              children: [
                Expanded(
                  child: Center(
                    child: DropdownButton(
                      autofocus: true,
                      items: hoursDropdownButtonItems,
                      onChanged: (value) {
                        setState(() {
                          selectedTime = null;
                          hourFrequency = value;
                        });
                      },
                      value: hourFrequency,
                      hint: const Text("Select frequency"),
                    ),
                  ),
                ),
              ],
            ),
            const SizedBox(
              height: 10,
            ),
            if (hourFrequency != null && hourFrequency != "Other") ...[
              generateRoutineHoursButton(context),
              if (selectedTime != null) ...[
                generateRoutineHours(context, hourFrequency!, selectedTime!),
                submitButton(context)
              ]
            ] else if (hourFrequency == "Other") ...[
              generateCustomHoursTextField(context),
              ...customHourButtons,
            ]
          ],
        ),
      ),
    );
  }
}
