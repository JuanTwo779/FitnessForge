# Fitness Forge: Android Fitness App

## Overview
**Fitness Forge** is a Java-based mobile application designed to help users track their fitness schedules and research best practices for workouts and nutrition. It integrates Firebase for authentication and data storage, external APIs for fitness-related information, and Room for local storage, enabling offline functionality.

## Features
- **User Authentication**: Secure login and registration using Firebase Authentication.
- **Fitness Schedule Tracking**: Users can log workouts, set fitness goals, and track progress.
- **Offline Support**: Utilises Room for local storage, ensuring users can access their schedule without an internet connection.
- **Workout & Nutrition Information**: Fetches workout routines and nutritional guidance from API Ninjas.
- **Multithreading Optimisation**: Ensures smooth performance by handling API calls and database operations asynchronously.

## Technologies Used
- **Java** (Android Development)
- **Firebase Authentication** (User Authentication)
- **Firebase Realtime Database** (Cloud Data Storage)
- **Room Database** (Local Storage & Offline Support)
- **API Ninjas** (External API for Workout & Nutrition Data)
- **Multithreading** (Efficient Background Processing)

## Usage
- **Sign up/Login**: Register with Firebase Authentication.
- **Create & Manage Workouts**: Add fitness routines, set schedules, and track progress.
- **Fetch Workout & Nutrition Info**: Retrieve best practices and recommendations via API Ninjas.

## Multithreading
Android apps run on a single UI thread, meaning any long-running operations (like API calls, database access, or heavy computations) can freeze the app if not offloaded to background threads.

Reasons for implementing multithreading in FitnessForge:
- Fetching Data from API Ninjas: API calls are network operations, which must be run asynchronously to prevent UI freezes.
- Reading/Writing to Firebase – Firebase operations are asynchronous because they rely on an external database. Waiting for responses synchronously would block the UI.
- Processing Large Data: When the API or Firebase returns large JSON responses, multithreading would ensure smooth parsing and display.
