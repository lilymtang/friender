# Android Application Overview

This is a "Friender" application that allows users to find people around them, view user profiles and create nearby friends list.

The application provides the following features:
* Ability to sign in and create an account
* Ability to create a profile with the bio and the list of interests
* Ability to see a list of users that are nearby 
* Ability to create a list of friends
* Ability to sort and filter nearby user list
* Ability to update the status “Available to hangout” / “Not available to hangout”

# Application Requirements Checklist

## Front End

| Requirement | Status | How Was Implemented |
|:-----------:|:------:|:-------------------:|
| XML | ✅ | All Fragments have corresponding xml files |
| Usage of multiple Material Design Components | ✅ | Application is built using components provided by Android Material Design component library. Example components used in the app: Lists (Recycler View), Bottom Navigation, FAB, Switch and others. |
| Animations | ✅  | |
| ConstraintLayout | ✅ | |
| RecyclerView | ✅ | The list of friends and list of other users nearby are displayed using Recycler View  |
| Empty state corner case handling | ✅ | |
| Loading and error screens | ✅  | |

## Back End

| Requirement | Status | How Was Implemented |
|:-----------:|:------:|:-------------------:|
| RxJava | ✅ | Room and Repository layer use RxJava |
| Retrofit |  | |
| Room | ✅ | Offline data is stored using Android Room persistence library |
| Repository Pattern | ✅ | Repository is retrieving the data from 2 data source types (local database, local resources |
| Geolocation API | ✅  | |
| JSON (for stubbed friend objects) |  | |
| *Testing* | ✅  | |
| Testing fundamentals | ✅ | Provided basic set of Unit tests for Repository and Dao objects |
| Mockito for ‘mocking’ objects |  | |
| Espresso for UI testing | ✅  | |

# Application UI Design

Application UI is built using Android Material Design Component Library.

## Home Screen

Displays the list of nearby users 

* Tapping on the list item allows to navigate to the User Profile screen 

## User Profile Screen

Displays information about selected user (other user or friend)

* It is possible to add / remove profile from the friends list

## Login Screen

Provides ability to login into application to be able to save users as friends

## User Registration Screen

Provides ability to register new user for application. 

* As application doesn't have backend integration, registration data is stored locally

## User Account Screen

Provides ability to view and edit logged in user profile

## Friends Screen

Displays the list of friends for currently logged in user.

* Tap to navigate to friend's user profile 

## Overall Navigation

For overall navigation application is using Navigation Component + Navigation Graph as follows:

**TBD: Navigation Diagram + Description**

# High Level Architectural Overview

Overall application is using MVVM architecture. The following diagram represents high level application architecture

**TBD: Application Architecture Diagram + Description**

## Technologies Used

This application is 100% Kotlin based and demonstrates the use of the following technologies:
* [Material Design Component Library](https://material.io/develop/android) for building UI aligned with material design guidelines
* [DataBinding](https://developer.android.com/topic/libraries/data-binding) for defining layouts in declarative format
* [Navigation Component](https://developer.android.com/guide/navigation/navigation-getting-started) for navigation
* [Hilt](https://developer.android.com/training/dependency-injection/hilt-android) for Dependency Injection
* [Coroutines](https://developer.android.com/kotlin/coroutines) for making async calls to APIs and Database
* [Room](https://developer.android.com/training/data-storage/room) for working with data stored in the Database
* [Retrofit](https://square.github.io/retrofit/) for data retrieval
* [Jackson](https://github.com/FasterXML/jackson) for JSON data deserialization
* [Android Studio sample data resources](https://developer.android.com/studio/write/tool-attributes#toolssample_resources) to preview actual data in design time
