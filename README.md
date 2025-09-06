Overview
Vidiyakul is an Android video player app built with a clean architecture approach. It allows users to select and play videos, saving their playback position so they can resume where they left off.

Features
Browse and select from multiple videos

Play videos using ExoPlayer

Save playback timestamps in Room database

Resume playback from saved position using ViewModel

Handles no-internet scenarios gracefully

Modular UI with Jetpack Compose composables

Architecture
Data Layer: Handles database (Room), DAO, models, repositories, and network utilities

Presentation Layer: Contains UI, themes, ViewModels, and screens (Home, VideoPlayer, No Internet)

Uses ViewModel for state management and composable widgets for reusable UI components

How to Run
Clone the repo and open in Android Studio

Ensure required dependencies (ExoPlayer, Room, Jetpack Compose) are included

Build and run on an Android device or emulator
