import 'package:flutter/material.dart';
import 'package:medicine_buddy/core/Notifications/patient_notification_handler.dart';
import 'package:medicine_buddy/core/ThemeAndDesign/colors.dart';
import 'package:medicine_buddy/screens/Patient/p_add_medicine.dart';
import 'package:medicine_buddy/screens/Patient/p_generate_report.dart';
import 'package:medicine_buddy/screens/Patient/p_medicine_history.dart';
import 'package:medicine_buddy/screens/Patient/p_my_progress.dart';
import 'package:medicine_buddy/screens/Patient/p_show_progress.dart';

class PHomePage extends StatefulWidget {
  const PHomePage({super.key, required this.phoneNumber});

  final String phoneNumber;

  @override
  State<PHomePage> createState() => _PHomePageState();
}

class _PHomePageState extends State<PHomePage> {
  int _selectedPageIndex = 0;
  late String activePageTitle;

  @override
  void initState() {
    super.initState();
    PatientNotificationHandler notificationHandler =
        PatientNotificationHandler();
    notificationHandler.initNotifications(context);
  }

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
        activePage = const PMyProgress();
        activePageTitle = "My Progress";
      case 1:
        activePage = PAddMedicine(phoneNumber: widget.phoneNumber);
        activePageTitle = "Add Medicine";
        break;
      case 2:
        activePage = const PShowProgress();
        activePageTitle = "Show Progress";
        break;
      case 3:
        activePage = const PGenerateReport();
        activePageTitle = "Generate Report";
        break;
      case 4:
        activePage = const PMedicineHistory();
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
