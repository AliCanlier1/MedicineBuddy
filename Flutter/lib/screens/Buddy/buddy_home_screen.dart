import 'package:flutter/material.dart';
import 'package:medicine_buddy/core/ThemeAndDesign/colors.dart';
import 'package:medicine_buddy/screens/Buddy/b_add_medicine.dart';
import 'package:medicine_buddy/screens/Buddy/b_generate_report.dart';
import 'package:medicine_buddy/screens/Buddy/b_medicine_history.dart';
import 'package:medicine_buddy/screens/Buddy/b_my_progress.dart';
import 'package:medicine_buddy/screens/Buddy/b_show_progress.dart';

class BHomePage extends StatefulWidget {
  const BHomePage({super.key, required this.phoneNumber});

  final String phoneNumber;

  @override
  State<BHomePage> createState() => _BHomePageState();
}

class _BHomePageState extends State<BHomePage> {
  int _selectedPageIndex = 0;
  late String activePageTitle;

  void _selectPage(int index) {
    setState(() {
      _selectedPageIndex = index;
    });
  }

  @override
  Widget build(BuildContext context) {
    late Widget activePage;
    switch (_selectedPageIndex) {
      case 0:
        activePage = const BMyProgress();
        activePageTitle = "My Progress";
      case 1:
        activePage = const BAddMedicine();
        activePageTitle = "Add Medicine";
        break;
      case 2:
        activePage = const BShowProgress();
        activePageTitle = "Show Progress";
        break;
      case 3:
        activePage = const BGenerateReport();
        activePageTitle = "Generate Report";
        break;
      case 4:
        activePage = const BMedicineHistory();
        activePageTitle = "Medicine History";
        break;
      default:
    }

    return Scaffold(
      appBar: AppBar(
        title: Text(activePageTitle),
        automaticallyImplyLeading: false,
      ),
      body: activePage,
      bottomNavigationBar: NavigationBar(
        destinations: const [
          NavigationDestination(
              selectedIcon: Icon(Icons.account_box_sharp),
              icon: Icon(Icons.account_box_outlined),
              label: "My Progress"),
          NavigationDestination(
              selectedIcon: Icon(Icons.add_box_sharp),
              icon: Icon(Icons.add_box_outlined),
              label: "Add Medicine"),
          NavigationDestination(
              selectedIcon: Icon(Icons.view_list_sharp),
              icon: Icon(Icons.view_list_outlined),
              label: "Show Progress"),
          NavigationDestination(
              selectedIcon: Icon(Icons.report_sharp),
              icon: Icon(Icons.report_outlined),
              label: "Generate Report"),
          NavigationDestination(
              icon: Icon(Icons.history_outlined), label: "Medicine History"),
        ],
        onDestinationSelected: _selectPage,
        indicatorColor: AppColors.navigationBarIndicatorColor,
        selectedIndex: _selectedPageIndex,
      ),
    );
  }
}
