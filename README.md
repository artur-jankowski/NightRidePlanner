# Night ride planner!
This project is a WIP application for organising car meets and contacting with car-fascinated people. It currently consists of server and UI project.
# Projects
## UI
UI Application is a responsive web app created with Angular 6.
It allows user to: 
* browse car fan groups and events created by their moderators
* join or create groups and events
* communicate with car friends (To be implemented)
* add friends from all registered car fans
* describe fan group purpose
* describe event place and plan (WIP)
## Android app (To be implemented)
Android application written in Android/Kotlin
It fills the same purpose as web app with some additional features:
* get notifications about upcoming events, changes in followed events and new events in joined groups
* receive reccomended events and groups based on current group membership and participated events
* quickly modify events on the fly (even if an event is ongoing)
## Server
Backbone of an application implemented in Java with SpringBoot. It provides endpoints for user applications with Spring MVC and authorization/authentication features with Spring Security and JWT. It also provides connection to the PostgreSQL 11 database with SpringData.
