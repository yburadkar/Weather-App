# Weather App

The app allows users to search for a city and displays matching results as the user types with the current weather details (for simplicity, only cities from GB, IN and AUS are displayed). Clicking on a city in the search result, shows the detail 3-hourly forcast for the next 5 days. 

When the user can navigates back to the search screen, duplicate network calls for the same search string are avoided. Appropriate loading and error messages are displayed to provide feedback to the user. Pull to refresh option has been provided for both screens.


## App Architecture:

The app structure is based on a single activity MVVM architecture with Repository pattern. The data and domain layers are separated into their own modules. 

The app also provides a fake weather repository to allow for network independent integration UI tests. 

**Libraries Used:** Retrofit2, RxJava2, Dagger2, JUnit, Mockk, Robolectric, Timber, Gson

### Packages
**domain** - Includes all domain interfaces for models and repositories

**data** - Includes implementations for the api models and repos, with the retrofit service interfaces

**di** - Includes all the dependency injection classes. It also includes a generic ViewModelFactory generator for all view models

**features** - Includes all the UI layer classes including activities, fragments, viewmodels, adapters and custom views.

**common**  - Includes all the helper and utility files or extensions


## Screens

### Main Activity

Once the user logs in, this is the single activity for the whole app. Its main responsibilities are: 
- Switch different feature fragments or child fragments in or out
- Handle the navigation and toolbar events for the app
- Handle the back stack
This would also be the place to include and handle bottom navigation if we add one later.

When the app is launched, this activity starts the SearchFragment.

###Search Fragment
This fragment has its own view model to handle all the network and navigation operations. The search edittext text changes are converted into a Rxstream with a debounce operator. The debounced stream is them mapped to a list of matching city IDs from the local resource file, and a network call is made with this list of city IDs to fetch the current weather for the matching cities. As the user types, the switchMap operator creates new network requests cancelling the previous ones.

### Detail Forecast Fragment
This fragment also has its own view model to handle the network calls for loading the details weather forecasts for the selected city. It uses a recyclerview with two view types, one for the date and the other for the forecast entry.


## Test Setup
Abstraction layers are used throughout the app to allow dependencies to be swapped during tests with fakes or mocks. Fake repos and test data are stored in the sharedTest source set so that they can be shared between by the test and androidTest sets.
TestAppModule replaces the production dependencies in tests. For Android tests TestApplication is used with a custom instrumentation tests runner.