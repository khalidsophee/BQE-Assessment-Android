Created by Khalid

---------------
To-Do List Android App
---------------

Description:
---------------
This is a simple To-Do list Android application built using Jetpack Compose. It allows users to:

- Add tasks with optional descriptions
- Mark tasks as completed
- Delete tasks from the list

UI Framework:
----------------
- Jetpack Compose (Material 3)
- Designed with a clean and minimal interface

Features:
-----------
- Add new tasks
- Check/uncheck tasks as complete/incomplete
- Delete tasks
- Line-through styling for completed tasks
- Dynamic list updates
- UI testing using Jetpack Compose Test APIs

Testing:
-----------
- Unit tests using `JUnit4`
- UI automation tests with `AndroidComposeTestRule`
- Test coverage includes:
    - Adding tasks
    - Marking tasks complete
    - Deleting tasks
    - Validating list content

Code Structure:
------------------
- `MainActivity.kt` – Entry point, contains the `TodoApp()` composable
- `TodoItem.kt` – Data model for each task
- `TodoAppTest.kt` – Contains Compose UI test cases
- All UI nodes have `testTag`s for precise testing

How to Run:
--------------
1. Open in Android Studio
2. Connect a device or emulator
3. Run the app or the test class `TodoAppTest`

Sample Test Tags:
--------------------
- `title_{id}` – For task title node
- `delete_{id}` – For delete button
- `checkbox_{id}` – For checkbox toggle
- `todo_row_{id}` – For the whole row of an item

Notes:
---------
- Ensure your project uses Material 3 Compose components
- The `id` for each task is managed by a counter (`idCounter`)
- Test failures may happen due to Compose test timing; always validate UI state updates are immediate

Author:
----------
Khalid Aziz
