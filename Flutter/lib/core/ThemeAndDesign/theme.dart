import 'package:flutter/material.dart';
import 'package:medicine_buddy/core/ThemeAndDesign/colors.dart';

get appTheme => ThemeData(
      appBarTheme: AppBarTheme(
        backgroundColor: AppColors.appBarColor,
        centerTitle: true,
      ),
      elevatedButtonTheme: ElevatedButtonThemeData(
        style: ButtonStyle(
          backgroundColor:
              MaterialStatePropertyAll<Color>(AppColors.elevatedButtonColor),
          padding: const MaterialStatePropertyAll<EdgeInsetsGeometry>(
            EdgeInsets.symmetric(horizontal: 40.0, vertical: 15.0),
          ),
          shape: MaterialStatePropertyAll<OutlinedBorder>(
            RoundedRectangleBorder(
              borderRadius: BorderRadius.circular(20.0),
            ),
          ),
        ),
      ),
      inputDecorationTheme: const InputDecorationTheme(
        border: OutlineInputBorder(
          borderRadius: BorderRadius.all(
            Radius.circular(30),
          ),
        ),
      ),
      textButtonTheme: const TextButtonThemeData(
        style: ButtonStyle(
          backgroundColor: MaterialStatePropertyAll<Color>(Colors.amberAccent),
        ),
      ),
    );
