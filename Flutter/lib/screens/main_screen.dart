import 'package:flutter/material.dart';
import 'package:medicine_buddy/core/LogInAndRegister/Buddy/b_login.dart';
import 'package:medicine_buddy/core/LogInAndRegister/Doctor/d_login.dart';
import 'package:medicine_buddy/core/LogInAndRegister/Patient/p_login.dart';

class MainPage extends StatelessWidget {
  const MainPage({super.key});
  final String title = "Medicine Buddy";

  @override
  Widget build(BuildContext context) {
    return Scaffold(
      appBar: AppBar(
        title: Text(title),
      ),
      body: Center(
        child: Column(
          mainAxisAlignment: MainAxisAlignment.center,
          children: [
            Text(
              "Welcome To Medicine Buddy!",
              style: Theme.of(context).textTheme.headlineSmall,
            ),
            const SizedBox(
              height: 30,
            ),
            SizedBox(
              width: 200,
              child: ElevatedButton(
                onPressed: () {
                  Navigator.of(context).push(MaterialPageRoute(
                    builder: (context) {
                      return const PLogin();
                    },
                  ));
                },
                child: const Text("Log in as a Patient"),
              ),
            ),
            const SizedBox(height: 10),
            SizedBox(
              width: 200,
              child: ElevatedButton(
                onPressed: () {
                  Navigator.of(context).push(MaterialPageRoute(
                    builder: (context) {
                      return const BLogin();
                    },
                  ));
                },
                child: const Text("Log in as a Buddy"),
              ),
            ),
            const SizedBox(height: 10),
            SizedBox(
              width: 200,
              child: ElevatedButton(
                onPressed: () {
                  Navigator.of(context).push(MaterialPageRoute(
                    builder: (context) {
                      return const DLogin();
                    },
                  ));
                },
                child: const Text("Log in as a Doctor"),
              ),
            )
          ],
        ),
      ),
    );
  }
}
