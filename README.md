# DMS: A Data Management System designed for simplicity and flexibility. 

## Overview

- DMS (Data Management System) is an minimalist small scale relational note-taking/management app for external entities.
- This version is the bare-bones minimum version geared towards daily note-taking/task management usage.

## News

- Beta Version 0.0.1 is out! Give DMS a shot! 

## 🚀 Quick Start

💻 System Requirements
- macOS: 10.15 or higher
- Windows: Windows 10 or higher
- Linux: Ubuntu 18.04+ / Debian 10+ / Fedora 32+
- Memory: Recommended 4GB or more
- Storage: At least 500MB available space

 

### Tested on: 

- Windows 11
- Fedora Workstation 43 

## 🚀 Deployment

- 1. Prerequisites are for amazon-corretto-21 and mysql (docker image or local installation) to be installed  and project settings for the IDE to have recognized corretto.
- 2. You would have to connect to a mysql server (docker image or not) with a user with appropriate privileges.
- 3. Add to application.properties and application-dev.properties the password of the mySQL server user
- 4. Navigate to the project directory and run in sequence
   - ./gradlew clean build
   - ./gradlew bootRun (for Windows and Unix it works) OR java -jar ./build/libs/dmsapp.jar
     (The build appears in the same directory as the project)

- To connect to the database you can use the provided .env 
     
##  Key Features

- Java-based backend & MySQL Database with the UI powered by the Thymeleaf Engine for the UI, avoiding the need for multiple engines to be used (React/Angular Stack + Backend).

## Notes 

Disclaimer: Burnout due to doing the MSc in parallel and I have tried to not vibe-code it so that I can learn, that is why you see this mess.
 It works, but it is ugly and I had to cut a lot of corners and did not have time to test docker deployment. In the future I will clean it up, make the UI prettier 
 add docker-compose and custom functionalities to make it more functional and less ugly.

# LICENSE

Please read LICENSE.md for the detailed rights of this app. Will make public later until I fix stuff.


