# Task Management System (MediaLab Assistant)

## Project Overview

This project is a Task Management System developed as part of the "Multimedia Technology" course at the National Technical University of Athens (NTUA). The application allows users to create, edit, and manage tasks, categories, priorities, and reminders through a graphical interface built with JavaFX. Data is persisted in JSON files for cross-session storage.

## Key Features

- **Task Management**:
  - Create, edit, and delete tasks with title, description, category, priority, deadline, and status.
  - Automatic status updates (e.g., tasks become "Delayed" if the deadline passes).
  - Predefined statuses: "Open", "In Progress", "Postponed", "Completed", "Delayed".

- **Category Management**:
  - Create, rename, and delete categories.
  - Deleting a category also deletes all associated tasks and reminders.

- **Priority Management**:
  - Create, rename, and delete priority levels (except the default "Default" priority).
  - Tasks with a deleted priority revert to the "Default" priority.

- **Reminders**:
  - Set reminders for tasks (1 day, 1 week, 1 month before deadline, or a custom date).
  - Reminders are automatically deleted if a task is marked as "Completed" or deleted.

- **Search & Filter**:
  - Search tasks by title, category, or priority.
  - Filter tasks based on status, category, or priority.

- **Data Persistence**:
  - All data is stored in JSON files within the `medialab` folder (`tasks.json`, `categories.json`, `priorities.json`, `reminders.json`).
  - Data is loaded on startup and saved on exit.

- **User Interface**:
  - Displays summary counters for total tasks, completed tasks, delayed tasks, and tasks due within 7 days.
  - Clean and intuitive GUI built with JavaFX and SceneBuilder.

## Folder Structure

```
src/
  - models/          # Contains core classes (Task, Category, Priority, Status, Reminder)
  - controllers/     # Handles user interactions (MainSceneController, AddTaskController, etc.)
  - views/           # FXML files for the GUI
  - utils/           # Utility classes (DataManager, LocalDateAdapter)
lib/                 # External dependencies
bin/                 # Compiled output files
medialab/            # JSON data files (automatically created if missing)
```

## Getting Started

### Prerequisites
- Java JDK 11 or later
- JavaFX SDK (included in the project dependencies)
- An IDE like Visual Studio Code or IntelliJ IDEA

### How to Run
1. Clone the repository or download the project files.
2. Open the project in your IDE.
3. Ensure the JavaFX SDK is properly linked in your IDE.
4. Run the `Main` class to start the application.

### Dependencies
- JavaFX for the graphical interface.
- Gson for JSON serialization/deserialization (included in the `lib` folder).

## Data Schema
The application uses the following JSON files for data persistence:
- `tasks.json`: Stores all tasks with their attributes.
- `categories.json`: Stores all categories.
- `priorities.json`: Stores all priority levels.
- `reminders.json`: Stores all reminders linked to tasks.

## Additional Notes
- The project follows the MVC (Model-View-Controller) architecture.
- All public methods in controllers and models are documented using Javadoc.
- Error handling includes alerts for invalid user actions (e.g., deleting without selection).

## Future Improvements
- Add user authentication for multi-user support.
- Implement task sharing or collaboration features.
- Enhance the UI with themes or customizable layouts.

For more details, refer to the project report or the original assignment PDF.
