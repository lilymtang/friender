# Android Application Overview

Find your next friends on Friender! This app allows you to browse profiles and find people with similar interests. Get started now by creating a profile and adding your bio and interests.

The application provides the following features:
* Ability to create an account and sign in
* Ability to create a profile with a bio and add interests
* Ability to browse other users using Friender and view their profiles 
* Ability to find users through search
* Ability to add users to your friends list
* Ability to update the status “Available to hangout” / “Not available to hangout”

# Features
## Registration
- The user journey begins on the login/registration screen. For new users, click Create New Account to begin the signup process.
  - The form features live form validation including: age validation (users must be ages 16+), matching password validation, phone number formatting, and form completion validation (Get Started button is only active once all fields are copmleted.)

<img src="https://user-images.githubusercontent.com/79869473/112940390-f0631a00-90e1-11eb-9ef1-6a9311b9e144.png" width="300"/> <img src="https://user-images.githubusercontent.com/79869473/112940325-d6c1d280-90e1-11eb-9c3a-b440ccbed937.png" width="300"/> <img src="https://user-images.githubusercontent.com/79869473/112940648-4a63df80-90e2-11eb-9c55-837924456022.png" width="300"/>

## Login
- The login screen validates the login credentials against the existing accounts and displays the relevant error message.

<img src="https://user-images.githubusercontent.com/79869473/112941285-2bb21880-90e3-11eb-9c59-131b38195e26.png" width="300"/> <img src="https://user-images.githubusercontent.com/79869473/112941315-3a98cb00-90e3-11eb-88d2-3c92030f7411.png" width="300"/>

## Account Screen
- The Account screen allows the currently logged in user to make changes to his or her profile and preview it
- Users can select interests and featured interests (featured interests will show on the Discover screen)
- The interests fields feature search-able dropdown lists of prepopulated interests
  - Once an interest is selected, it will not show again in the current list or the other interests list  
- After saving their profile, users can preview their updated profile by clicking the icon in the upper right hand corner

<img src="https://user-images.githubusercontent.com/79869473/112941643-cdd20080-90e3-11eb-9cbe-847409933218.png" width="300"/> <img src="https://user-images.githubusercontent.com/79869473/112942846-96645380-90e5-11eb-90c4-cb7914cf501c.gif" width="300"/>
  
## Discover Screen
- The Discover screen allows users to browse other users and their profiles
- The Discover screen cards show a preview of a user, which includes their name, age, and featured interests
- Clicking on a user card will reveal the profile details, which includes additionally a user's live-updating distance from the current user, their bio, and both featured and non-featured interests
- The user can add a friend from the profile details page
- The user can search by keyword to find users more relevant to him or her

<img src="https://user-images.githubusercontent.com/79869473/112948790-56a16a00-90ed-11eb-9ba7-2806799603fb.gif" width="300"/>
<img src="https://user-images.githubusercontent.com/79869473/112948411-e5fa4d80-90ec-11eb-82d9-40522e3003ce.gif" width="300"/> 

## Friends Screen
- The Friends screen allows users to view their friends
- Users can swipe to delete friends as well as undo delete via the snackbar
- The SMS button opens the SMS app with a pre-populated message and phone number

<img src="https://user-images.githubusercontent.com/79869473/112944335-8b122780-90e7-11eb-9949-8c0ff1468c07.gif" width="300"/>
<img src="https://user-images.githubusercontent.com/79869473/112945891-b5fd7b00-90e9-11eb-8c46-ec48e5b52cc8.gif" width="300"/>

# Planning
We began the 4 week project by creating a lofi prototype and setting up the project structure and choosing technologies. We divided responsibilities by screen, creating a Jira epic for each screen and tasks to break the epics down into smaller features. Viktorija was responsible for Registration, Login, and Account, and Lily was responsible for Discover, Friends, and Profile.

<img width="1286" alt="Screen Shot 2021-03-29 at 11 56 47 PM" src="https://user-images.githubusercontent.com/79869473/112946770-c4986200-90ea-11eb-96ca-689cc001ce21.png">

# Architecture and Technologies

Our app uses the MVVM architecture pattern and uses the following technologies:
* [Material Design Component Library](https://material.io/develop/android) for building UI aligned with material design guidelines
* [DataBinding](https://developer.android.com/topic/libraries/data-binding) for defining layouts in declarative format
* [Navigation Component](https://developer.android.com/guide/navigation/navigation-getting-started) for navigation
* [Hilt](https://developer.android.com/training/dependency-injection/hilt-android) for Dependency Injection
* [Coroutines](https://developer.android.com/kotlin/coroutines) for making async calls to APIs and Database
* [Room](https://developer.android.com/training/data-storage/room) for working with data stored in the Database
* [Retrofit](https://square.github.io/retrofit/) for data retrieval
* [Jackson](https://github.com/FasterXML/jackson) for JSON data deserialization
* [Android Studio sample data resources](https://developer.android.com/studio/write/tool-attributes#toolssample_resources) to preview actual data in design time
* Espresso for UI testing

# Attributions
- This app was built over 4 weeks in March 2021 by Lily Tang and Viktorija Urzika as part of the Onramp + Twitch Android Apprenticeship. 
- Our mentors, Joel Camargo Jr. and Bennett Lee, helped lead our daily standups as well as conduct sprint planning and code reviews. 
