# Study Planner - Mobile Development Midterm Project

A comprehensive Android application designed to help students manage their study schedule, tasks, and academic performance effectively.

## 📱 Overview

Study Planner is a mobile application that provides students with tools to:
- Organize study subjects and tasks
- Track deadlines with calendar integration
- Monitor study hours and performance
- Receive timely notifications for upcoming tasks
- View analytical reports on study habits

## ✨ Features

### 🔐 User Authentication
- Secure user registration and login
- Session management for persistent login state
- Profile management

### 📚 Subject Management
- Add, edit, and delete study subjects
- Organize tasks by subject
- Track study hours per subject

### ✅ Task Management
- Create tasks with title, description, and due dates
- Mark tasks as complete/incomplete
- View upcoming and completed tasks
- Associate tasks with subjects

### 📅 Calendar View
- Visual calendar displaying all tasks
- Date-based task filtering
- Quick navigation between dates

### 📊 Dashboard
- Overview of upcoming tasks
- Quick statistics and insights
- Easy access to recent activities

### 📈 Reports & Analytics
- Study hours visualization with charts
- Subject performance metrics
- Custom report generation
- Data-driven insights

### 🔔 Notifications
- Task deadline reminders
- Customizable notification settings

### 👤 Profile Management
- View and edit user information
- Manage app preferences and settings

## 🛠️ Technical Stack

### Architecture
- **Pattern**: MVVM (Model-View-ViewModel)
- **Language**: Java
- **Min SDK**: 26 (Android 8.0)
- **Target SDK**: 34

### Core Technologies
- **AndroidX Libraries**: Modern Android components
- **Material Design Components**: UI framework
- **Room Database**: Local data persistence
- **LiveData & ViewModel**: Lifecycle-aware components
- **Navigation Component**: Fragment navigation
- **MPAndroidChart**: Data visualization

### Key Libraries
```gradle
// AndroidX
implementation 'androidx.appcompat:appcompat:1.6.1'
implementation 'androidx.constraintlayout:constraintlayout:2.1.4'

// Material Design
implementation 'com.google.android.material:material:1.11.0'

// Navigation
implementation 'androidx.navigation:navigation-fragment:2.7.7'
implementation 'androidx.navigation:navigation-ui:2.7.7'

// Room Database
implementation 'androidx.room:room-runtime:2.6.1'
annotationProcessor 'androidx.room:room-compiler:2.6.1'

// Lifecycle
implementation 'androidx.lifecycle:lifecycle-viewmodel:2.7.0'
implementation 'androidx.lifecycle:lifecycle-livedata:2.7.0'

// Charts
implementation 'com.github.PhilJay:MPAndroidChart:v3.1.0'
```

## 🏗️ Project Structure

```
StudyPlanner/
├── app/
│   ├── src/
│   │   ├── main/
│   │   │   ├── java/com/example/studyplanner/
│   │   │   │   ├── data/
│   │   │   │   │   ├── local/         # Room Database & DAOs
│   │   │   │   │   ├── model/         # Entity classes
│   │   │   │   │   └── relation/      # Database relations
│   │   │   │   ├── repository/        # Data repository
│   │   │   │   ├── ui/
│   │   │   │   │   ├── auth/          # Authentication screens
│   │   │   │   │   └── main/          # Main app screens
│   │   │   │   ├── util/              # Utility classes
│   │   │   │   ├── MainActivity.java
│   │   │   │   └── NotificationReceiver.java
│   │   │   ├── res/                   # Resources
│   │   │   └── AndroidManifest.xml
│   │   └── androidTest/               # Instrumented tests
│   └── build.gradle.kts
├── docs/                              # Documentation & images
├── PROJECT_REPORT.md                  # Comprehensive project report
├── CONTRIBUTING.md                    # Contribution guidelines
└── README.md                          # This file
```

## 🚀 Getting Started

### Prerequisites
- Android Studio Arctic Fox or later
- JDK 11 or higher
- Android SDK with API level 26 or higher

### Installation

1. **Clone the repository**
   ```bash
   git clone https://github.com/bahungTDTU/StudyPlanner.git
   cd StudyPlanner
   ```

2. **Open in Android Studio**
   - Launch Android Studio
   - Select "Open an Existing Project"
   - Navigate to the cloned repository and select the `StudyPlanner` folder

3. **Sync Project**
   - Android Studio will automatically sync Gradle
   - Wait for the sync to complete

4. **Run the Application**
   - Connect an Android device or start an emulator
   - Click the "Run" button (▶️) or press Shift+F10
   - Select your device/emulator

### Building APK

```bash
# Debug APK
./gradlew assembleDebug

# Release APK (unsigned)
./gradlew assembleRelease
```

APK location: `app/build/outputs/apk/`

## 📖 Documentation

- **[Project Report](PROJECT_REPORT.md)**: Comprehensive documentation including use cases, screenshots, and technical details
- **[Contributing Guide](CONTRIBUTING.md)**: Guidelines for team members contributing to the project
- **[Images Guide](docs/images/README.md)**: Instructions for capturing and organizing project screenshots

## 👥 Team

See [PROJECT_REPORT.md](PROJECT_REPORT.md) for detailed team member information and contributions.

## 📸 Screenshots

Screenshots are available in the [PROJECT_REPORT.md](PROJECT_REPORT.md) file, including:
- Authentication screens
- Dashboard overview
- Task management interface
- Calendar view
- Subject management
- Reports and analytics
- Profile management

## 🎯 Use Cases

The application supports the following main use cases:
1. User Authentication (Register/Login)
2. Subject Management (Add/Edit/Delete)
3. Task Management (Create/Complete/Delete)
4. Calendar Viewing (Date-based task organization)
5. Dashboard Overview (Quick insights)
6. Reports & Analytics (Study patterns and performance)
7. Profile Management (User settings)
8. Notifications (Task reminders)

For detailed use case diagrams and explanations, see [PROJECT_REPORT.md](PROJECT_REPORT.md).

## 🔧 Configuration

### Database
The app uses Room database with the following entities:
- **User**: User account information
- **Subject**: Study subjects
- **Task**: Academic tasks and assignments

### Notifications
- Notification channel: "study_planner_channel"
- Requires POST_NOTIFICATIONS permission (Android 13+)
- Uses AlarmManager for scheduling

## 🧪 Testing

### Run Tests
```bash
# Unit Tests
./gradlew test

# Instrumented Tests
./gradlew connectedAndroidTest
```

## 📝 License

This project is created as part of a Mobile Development course midterm project.

## 🤝 Contributing

Please read [CONTRIBUTING.md](CONTRIBUTING.md) for details on our code of conduct and the process for submitting pull requests.

## 📞 Support

For questions or issues:
1. Check the [PROJECT_REPORT.md](PROJECT_REPORT.md)
2. Review closed issues in the repository
3. Open a new issue with detailed information

## 🙏 Acknowledgments

- Android Developers documentation
- Material Design guidelines
- MPAndroidChart library
- AndroidX Jetpack components

---

**Project Status**: Active Development  
**Course**: Mobile Development  
**Academic Term**: Midterm Project  
**Last Updated**: 2024
