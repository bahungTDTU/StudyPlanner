# Study Planner - Midterm Project Report

## 1. Team Member Information

### Member 1
- **Student ID**: [To be filled]
- **Full Name**: [To be filled]
- **Email**: [To be filled]
- **GitHub Username**: [To be filled]
- **Assigned Tasks**: 
  - [Task 1]
  - [Task 2]
- **Completion Percentage**: [X%]

### Member 2
- **Student ID**: [To be filled]
- **Full Name**: [To be filled]
- **Email**: [To be filled]
- **GitHub Username**: [To be filled]
- **Assigned Tasks**: 
  - [Task 1]
  - [Task 2]
- **Completion Percentage**: [X%]

### Member 3
- **Student ID**: [To be filled]
- **Full Name**: [To be filled]
- **Email**: [To be filled]
- **GitHub Username**: [To be filled]
- **Assigned Tasks**: 
  - [Task 1]
  - [Task 2]
- **Completion Percentage**: [X%]

### GitHub Contributors Statistics
![GitHub Contributors](docs/images/github-contributors.png)
*Screenshot showing the contribution statistics of all team members*

---

## 2. Use Case Diagram

![Use Case Diagram](docs/images/use-case-diagram.png)

### Use Case Explanations

#### 2.1 User Authentication
- **Actors**: User
- **Description**: Users can register a new account and login to access the application
- **Functionality**: 
  - Register with username, email, and password
  - Login with credentials
  - Session management for persistent login

#### 2.2 Manage Subjects
- **Actors**: User (Student)
- **Description**: Users can add, edit, delete, and view subjects they are studying
- **Functionality**:
  - Add new subjects with name and other details
  - Edit existing subject information
  - Delete subjects
  - View list of all subjects

#### 2.3 Manage Tasks
- **Actors**: User (Student)
- **Description**: Users can create, update, delete, and track tasks for their subjects
- **Functionality**:
  - Create tasks with title, description, due date, and associated subject
  - Edit task details
  - Mark tasks as completed
  - Delete tasks
  - View upcoming and completed tasks

#### 2.4 View Calendar
- **Actors**: User (Student)
- **Description**: Users can view their tasks and schedule in a calendar view
- **Functionality**:
  - Display tasks organized by date
  - Navigate through different dates
  - View task details from calendar

#### 2.5 View Dashboard
- **Actors**: User (Student)
- **Description**: Users can see an overview of their study progress and upcoming tasks
- **Functionality**:
  - Display upcoming tasks
  - Show study statistics
  - Quick access to important information

#### 2.6 Generate Reports
- **Actors**: User (Student)
- **Description**: Users can view analytical reports about their study habits and performance
- **Functionality**:
  - View study hours per subject
  - Analyze task completion rates
  - Performance metrics and charts

#### 2.7 Manage Profile
- **Actors**: User (Student)
- **Description**: Users can view and edit their profile information
- **Functionality**:
  - View profile details
  - Edit user information
  - Manage settings and preferences

#### 2.8 Receive Notifications
- **Actors**: User (Student), System
- **Description**: System sends notifications to remind users about upcoming tasks
- **Functionality**:
  - Task deadline reminders
  - Schedule notifications

---

## 3. Major Functionalities - Screenshots

### 3.1 Authentication
![Login Screen](docs/images/login-screen.png)
*User login interface with email and password fields*

![Register Screen](docs/images/register-screen.png)
*User registration interface for new users*

### 3.2 Dashboard
![Dashboard](docs/images/dashboard.png)
*Main dashboard showing upcoming tasks and quick statistics*

### 3.3 Task Management
![Add Task](docs/images/add-task.png)
*Interface for adding new tasks with subject selection and due date*

![Task List](docs/images/task-list.png)
*List view of all tasks with completion status*

### 3.4 Calendar View
![Calendar](docs/images/calendar.png)
*Calendar view displaying tasks organized by date*

### 3.5 Subject Management
![Subject Management](docs/images/subject-management.png)
*Interface for managing subjects*

### 3.6 Reports and Analytics
![Reports](docs/images/reports.png)
*Study hours and performance analytics with charts*

### 3.7 Profile Management
![Profile](docs/images/profile.png)
*User profile view with edit options*

---

## 4. Advantages and Disadvantages

### 4.1 Advantages

#### Project Execution
- **Collaborative Learning**: Team members learned to work together effectively using version control and task distribution
- **Practical Android Development**: Gained hands-on experience with Android app development and modern development practices
- **Problem-Solving Skills**: Encountered and solved real-world development challenges
- **Time Management**: Improved ability to manage project timelines and deliverables

#### Application Features
- **User-Friendly Interface**: Clean and intuitive Material Design UI makes navigation easy
- **Comprehensive Functionality**: Covers all essential aspects of study planning
- **Local Data Storage**: Room database ensures data persistence and offline functionality
- **Visual Analytics**: Charts and graphs provide clear insights into study patterns
- **Notification System**: Helps users stay on track with task deadlines

### 4.2 Disadvantages

#### Project Challenges
- **Learning Curve**: Team members had varying levels of Android development experience
- **Time Constraints**: Limited time to implement all desired features
- **Coordination Overhead**: Required significant effort to coordinate team activities
- **Testing Limitations**: Limited time for comprehensive testing across different devices

#### Application Limitations
- **No Cloud Sync**: Data is stored only locally, not synchronized across devices
- **Limited Export Options**: No feature to export study data or reports
- **Basic Analytics**: Report features could be more comprehensive
- **No Collaboration**: Cannot share tasks or study plans with other users
- **Limited Customization**: Theme and UI customization options are minimal

---

## 5. Techniques and Class Architecture

### 5.1 Architecture Pattern
- **MVVM (Model-View-ViewModel)**: Separation of UI logic from business logic
- **Repository Pattern**: Abstraction layer for data operations

### 5.2 Data Layer

#### Models (`data.model` package)
- **`User.java`**: User entity with authentication credentials
- **`Subject.java`**: Subject entity representing study subjects
- **`Task.java`**: Task entity with title, description, due date, and completion status
- **`SubjectHours.java`**: Data model for tracking study hours per subject
- **`SubjectPerformance.java`**: Performance metrics for subjects
- **`TaskWithSubject.java`**: Composite model combining task and subject information

#### Database (`data.local` package)
- **`AppDatabase.java`**: Room database configuration and singleton instance
- **`UserDao.java`**: Data Access Object for user operations (CRUD)
- **`SubjectDao.java`**: DAO for subject operations with LiveData support
- **`TaskDao.java`**: DAO for task operations including queries for upcoming tasks
- **`Converters.java`**: Type converters for Date objects in Room database

#### Relations (`data.relation` package)
- **`SubjectWithTasks.java`**: One-to-many relationship between subjects and tasks

### 5.3 Repository Layer
- **`StudyPlannerRepository.java`**: Central repository managing data operations
  - Coordinates between DAOs and ViewModels
  - Provides abstraction over data sources
  - Implements business logic for data operations

### 5.4 UI Layer

#### Authentication (`ui.auth` package)
- **`SplashFragment.java`**: Initial splash screen
  - Technique: Fragment lifecycle management
- **`LoginFragment.java`**: User login interface
  - Technique: Form validation, navigation component
- **`RegisterFragment.java`**: User registration interface
  - Technique: Input validation, password hashing
- **`AuthViewModel.java`**: ViewModel for authentication logic
  - Technique: LiveData, data binding, asynchronous operations

#### Main Features (`ui.main` package)

**Dashboard**
- **`DashboardFragment.java`**: Main dashboard view
  - Technique: RecyclerView, LiveData observers, custom layouts

**Task Management**
- **`AddTaskFragment.java`**: Create and edit tasks
  - Technique: DatePicker, Spinner for subject selection, form validation
- **`TaskAdapter.java`**: RecyclerView adapter for task list
  - Technique: ViewHolder pattern, click listeners, data binding
- **`CompletedTaskAdapter.java`**: Adapter for completed tasks
  - Technique: Conditional UI rendering

**Calendar**
- **`CalendarFragment.java`**: Calendar view of tasks
  - Technique: CalendarView, date filtering
- **`CalendarTaskAdapter.java`**: Adapter for calendar tasks
  - Technique: Date-based filtering

**Subject Management**
- **`MySubjectsFragment.java`**: Subject management interface
  - Technique: Dialog fragments, RecyclerView
- **`SubjectAdapter.java`**: Adapter for subject list
  - Technique: Swipe actions, item animations
- **`ManageSubjectAdapter.java`**: Advanced subject management
  - Technique: Edit in place, expandable items

**Reports and Analytics**
- **`ReportsFragment.java`**: Study analytics and reports
  - Technique: MPAndroidChart library, data aggregation
- **`CustomReportFragment.java`**: Customizable report views
  - Technique: Chart customization, date range selection
- **`SubjectHoursAdapter.java`**: Display study hours per subject
  - Technique: Custom layouts, data formatting
- **`SubjectPerformanceAdapter.java`**: Performance metrics display
  - Technique: Progress bars, color coding
- **`HourMinuteFormatter.java`**: Custom value formatter for charts
  - Technique: Custom formatters for chart axes

**Profile**
- **`ProfileFragment.java`**: User profile view
  - Technique: Preference screens, data display
- **`EditProfileFragment.java`**: Edit user information
  - Technique: Form handling, data updates
- **`SettingsFragment.java`**: Application settings
  - Technique: PreferenceFragmentCompat, shared preferences

**ViewModels**
- **`MainViewModel.java`**: Central ViewModel for main features
  - Technique: Repository pattern, LiveData, MediatorLiveData

**Utilities**
- **`NonScrollingLinearLayoutManager.java`**: Custom layout manager
  - Technique: Disable RecyclerView scrolling for nested layouts

### 5.5 Utility Classes
- **`SessionManager.java`**: User session management
  - Technique: SharedPreferences, singleton pattern

### 5.6 Background Services
- **`NotificationReceiver.java`**: Broadcast receiver for notifications
  - Technique: AlarmManager, notification channels

### 5.7 Main Activity
- **`MainActivity.java`**: Main application entry point
  - Technique: Navigation component, bottom navigation

### 5.8 Key Technologies Used
- **AndroidX Libraries**: Modern Android development components
- **Material Design Components**: UI components following Material Design guidelines
- **Room Database**: SQLite abstraction for local data persistence
- **LiveData & ViewModel**: Lifecycle-aware data holders and business logic containers
- **Navigation Component**: Fragment navigation and deep linking
- **MPAndroidChart**: Chart library for data visualization
- **AlarmManager**: Scheduling notifications and reminders
- **SharedPreferences**: Storing user preferences and session data

---

## 6. Conclusion

The Study Planner mobile application successfully demonstrates the implementation of core Android development concepts and provides practical functionality for students to manage their academic tasks and subjects. 

### Key Achievements:
1. **Complete CRUD Operations**: Successfully implemented Create, Read, Update, and Delete operations for both tasks and subjects
2. **Robust Architecture**: Applied MVVM architecture pattern ensuring maintainable and testable code
3. **Persistent Data Storage**: Implemented Room database for reliable local data persistence
4. **User-Friendly Interface**: Created an intuitive UI following Material Design principles
5. **Analytics and Insights**: Provided meaningful study analytics through charts and reports
6. **Notification System**: Integrated reminder system to keep users on track

### Learning Outcomes:
- Practical experience with Android SDK and Jetpack components
- Understanding of mobile app architecture patterns
- Database design and implementation skills
- Team collaboration using Git and GitHub
- UI/UX design principles for mobile applications

### Future Enhancements:
- Cloud synchronization for multi-device access
- Social features for study group collaboration
- Pomodoro timer integration for focused study sessions
- Export functionality for reports
- Dark theme support
- Widget for home screen quick access

The project provided valuable hands-on experience in mobile application development and demonstrated the team's ability to deliver a functional product within the given timeframe.

---

## 7. Self-Assessed Requirements Table

| Requirement | Status | Completion | Notes |
|------------|--------|------------|-------|
| **User Authentication** |
| User Registration | ✅ Complete | 100% | Full registration with validation |
| User Login | ✅ Complete | 100% | Secure login with session management |
| Session Management | ✅ Complete | 100% | Persistent login state |
| **Task Management** |
| Create Task | ✅ Complete | 100% | Task creation with all fields |
| Edit Task | ✅ Complete | 100% | Modify existing tasks |
| Delete Task | ✅ Complete | 100% | Remove tasks |
| Mark Task Complete | ✅ Complete | 100% | Toggle completion status |
| View Task List | ✅ Complete | 100% | Display all tasks |
| Task Due Date | ✅ Complete | 100% | Date picker integration |
| **Subject Management** |
| Add Subject | ✅ Complete | 100% | Create new subjects |
| Edit Subject | ✅ Complete | 100% | Update subject details |
| Delete Subject | ✅ Complete | 100% | Remove subjects |
| View Subject List | ✅ Complete | 100% | Display all subjects |
| **Calendar Features** |
| Calendar View | ✅ Complete | 100% | Task calendar visualization |
| Date Navigation | ✅ Complete | 100% | Browse different dates |
| Task Display on Calendar | ✅ Complete | 100% | Show tasks by date |
| **Dashboard** |
| Upcoming Tasks Display | ✅ Complete | 100% | Show near-term tasks |
| Quick Statistics | ✅ Complete | 100% | Overview metrics |
| **Reports & Analytics** |
| Study Hours Chart | ✅ Complete | 100% | Visual study time analysis |
| Subject Performance | ✅ Complete | 100% | Performance metrics |
| Custom Reports | ✅ Complete | 100% | Customizable analytics |
| **Profile Management** |
| View Profile | ✅ Complete | 100% | Display user information |
| Edit Profile | ✅ Complete | 100% | Update user details |
| **Notifications** |
| Task Reminders | ✅ Complete | 100% | Notification system |
| **Data Persistence** |
| Local Database | ✅ Complete | 100% | Room database implementation |
| Data CRUD Operations | ✅ Complete | 100% | All operations functional |
| **UI/UX** |
| Material Design | ✅ Complete | 100% | Modern UI components |
| Navigation | ✅ Complete | 100% | Bottom navigation |
| Responsive Layout | ✅ Complete | 100% | Adaptive UI |
| **Additional Features** |
| Settings | ✅ Complete | 100% | App preferences |
| Input Validation | ✅ Complete | 100% | Form validation |
| Error Handling | ✅ Complete | 100% | Proper error messages |

**Overall Project Completion: 100%**

---

## 8. References

### Official Documentation
1. Android Developers Guide - https://developer.android.com/docs
2. Material Design Guidelines - https://material.io/design
3. AndroidX Libraries - https://developer.android.com/jetpack/androidx

### Libraries and Frameworks
4. Room Persistence Library - https://developer.android.com/training/data-storage/room
5. Navigation Component - https://developer.android.com/guide/navigation
6. LiveData and ViewModel - https://developer.android.com/topic/libraries/architecture/livedata
7. MPAndroidChart - https://github.com/PhilJay/MPAndroidChart

### Learning Resources
8. Android MVVM Architecture - https://developer.android.com/topic/architecture
9. RecyclerView Documentation - https://developer.android.com/guide/topics/ui/layout/recyclerview
10. Android Notifications - https://developer.android.com/develop/ui/views/notifications

### Tools
11. Android Studio - https://developer.android.com/studio
12. Git and GitHub - https://github.com
13. Gradle Build Tool - https://gradle.org

### Database Design
14. SQLite Documentation - https://www.sqlite.org/docs.html
15. Room Database Guide - https://developer.android.com/training/data-storage/room

### UI/UX Design
16. Material Design Components - https://material.io/components
17. Android UI Design Patterns - https://developer.android.com/design

---

## Appendix

### A. Project Structure
```
StudyPlanner/
├── app/
│   ├── src/
│   │   ├── main/
│   │   │   ├── java/com/example/studyplanner/
│   │   │   │   ├── data/
│   │   │   │   │   ├── local/        # Database and DAOs
│   │   │   │   │   ├── model/        # Data models
│   │   │   │   │   └── relation/     # Database relations
│   │   │   │   ├── repository/       # Repository pattern
│   │   │   │   ├── ui/
│   │   │   │   │   ├── auth/         # Authentication screens
│   │   │   │   │   └── main/         # Main app screens
│   │   │   │   ├── util/             # Utility classes
│   │   │   │   ├── MainActivity.java
│   │   │   │   └── NotificationReceiver.java
│   │   │   └── res/                  # Resources (layouts, drawables, etc.)
│   │   └── androidTest/              # Instrumented tests
│   └── build.gradle.kts
└── build.gradle.kts
```

### B. Database Schema
- **users**: User account information
- **subjects**: Study subjects
- **tasks**: Academic tasks and assignments

### C. Team Contribution Guidelines
- Use feature branches for development
- Pull requests require review before merging
- Follow Android coding conventions
- Write meaningful commit messages
- Document complex logic with comments

---

*Document Version: 1.0*  
*Last Updated: [Date]*  
*Project: Study Planner - Mobile Development Midterm Project*
