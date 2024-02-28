# SportsBook Project

## Overview
SportsBook is an Android application that showcases upcoming sports events. Built with Kotlin and utilizing the Jetpack Compose framework along with Material3 design components, the app offers a modern UI and seamless user experience. Users can browse events by sport type, mark events as favorites, and see a countdown timer for upcoming events.

## Features
- Display a list of upcoming sports events.
- Filter events by sport type and favorites.
- Countdown timer for each event.
- Dark mode toggle for theme preference.

## Installation
Instructions on how to clone/download and set up the project locally.

## Architecture
The app follows the Model-View-ViewModel (MVVM) architecture pattern:

- **Model**: Represents the data layer, handling the business logic of the app.
- **View**: Comprises the UI components, presenting data to the user.
- **ViewModel**: Acts as a bridge between the Model and View, managing UI-related data.


### Data
The data layer includes:

- **PreferencesHelper**: Manages user preferences, including favorites and theme settings.
- **SportsApi**: Defines the network calls to fetch sports events.
- **SportsResponseItem & GameDetailsInfo**: Data classes for parsing the API responses.
- **SportsRepositoryImpl**: Provides an abstraction over the data source and exposes data to the domain layer.


### Domain
The domain layer of the SportsBook application encapsulates the core business logic and defines interfaces for data access, error handling, and user preferences.

#### Error Handling

The error handling mechanism is robust, ensuring that the app can gracefully handle and recover from errors. It includes:

- `ErrorState`: Captures the current state of an error, including the message, type, and any corrective action.
- `ErrorType`: An enumeration that classifies errors into categories such as network issues, server errors, or general errors.
- `ErrorAction`: Defines an action to resolve an error, such as a retry operation.

#### Preferences
The `Preferences` interface abstracts the underlying storage mechanism for user preferences, providing methods to:

- Save and load favorite events.
- Toggle the dark theme.
- General toggle settings for various configurations.

#### Repository

The `SportsRepository` interface defines the contract for the data layer to fetch sports events. This abstraction allows for a clear separation of concerns and easier testing.

### ViewModel
`MainViewModel` is responsible for managing UI-related data and handling user interactions. It serves as an intermediary between the repository layer and the UI components, ensuring a separation of concerns and cleaner codebase.

#### Key Responsibilities

- **Fetching Data**: The `fetchData` function retrieves sports events data asynchronously and updates the UI state accordingly.
- **Error Handling**: Errors are managed gracefully, providing user-friendly feedback and recovery options.
- **Favorites Management**: Users can mark events as favorites, which are then stored and retrieved using shared preferences.
- **Dark Theme Toggle**: The ViewModel listens for theme changes and updates the UI to reflect the user's dark or light theme preference.
- **Sorting Preferences**: Sorting preferences for sports events are stored and used to sort the events based on user preference.
- **UI Refresh**: Handles UI refresh triggers to ensure the screen is updated with the latest data.

#### LiveData Observables

- `_sportsEvents`: LiveData that holds the list of sports events.
- `_isLoading`: LiveData that reflects the loading state of data fetching.
- `_isDarkTheme`: LiveData that indicates whether the dark theme is enabled.
- `_errorState`: LiveData that holds the current error state, if any.

#### Functions

- `sortFavoritesBySport`: Sorts the sports events based on favorites when the sorting preference is enabled.
- `resetSportSort`: Resets the sorting order of sports events to the original state.
- `clearErrorState`: Clears the current error state to prevent duplicate error messages.
- `toggleSportSorting`: Toggles the sorting preference for the sports events.

## Presentation

The presentation layer in SportsBook consists of three primary screens, each designed with Jetpack Compose using Material3 design components for a cohesive and modern user experience.

### Profile Screen

`ProfileScreen` is a user-centric screen that allows users to view and interact with their personal information. It features:

- A `CenterAlignedTopAppBar` for easy navigation.
- A welcoming message within a `Card` composable that greets users to their profile.

## Settings Screen

`SettingsScreen` offers users the ability to customize their app experience. Key functionalities include:

- An integrated `SwitchWithIcon` composable for toggling settings such as dark mode.
- Reactive UI updates that reflect changes in user preferences.

### SportsBook Screen

The main screen of the app, `SportsBookScreen`, presents the upcoming sports events and allows users to interact with them:

- A `LazyColumn` lists sports events, which users can mark as favorites.
- Includes a `SnackbarHost` for showing messages and actions based on events such as errors or updates.
- A dynamic UI that responds to live data changes and user interactions.




### UI
The UI layer of the SportsBook app consists of a collection of reusable components and composables that adhere to Material Design principles for a consistent and modern user interface.

#### Components

- `ActionIconButton`: A customizable icon button used throughout the app for user actions.
- `AppSwitch`: A toggle switch for settings such as the dark mode.
- `AppText`: A stylized text component used for displaying information.
- `DropDownMenu`: Provides a dropdown menu for user selections.
- `FavoriteIcon`: Indicates and toggles the favorite status of sports events.
- `Loader`: Displays a loading animation during data fetching operations.
- `SportIcon`: Represents sports with icons.

#### Composites

- `CountDownTimer`: Shows the remaining time until an event starts.
- `ErrorMessage`: Displays errors in a user-friendly manner.
- `SportCard`: Presents detailed information for a specific sports event.
- `SportsBookTopBar`: The top app bar providing navigation and access to different screens.
- `SportsEventsList`: Lists all sports events.
- `SwitchWithIcon`: A switch component paired with an icon for a more informative UI toggle.


### Utils
Discuss the utility classes and extensions that are part of the project.

## Main Dependencies
Retrofit: For network calls.
- Moshi: For JSON parsing.
- Coroutine Flow: For asynchronous data streaming.
- Hilt: For dependency injection.
- Jetpack Compose: For UI development.

## Screenshots
(Optionally include screenshots of the app in action.)


