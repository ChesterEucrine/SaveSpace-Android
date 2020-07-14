<a href="https://circleci.com/gh/ChesterEucrine/SaveSpace-Android"><img src="https://circleci.com/gh/ChesterEucrine/SaveSpace-Android.svg?style=svg" alt='CircleCi status'></a># SaveSpace
 
An android app to save and edit notes and reminders

Functionalities:
* Add and save sticky note
* Set nptes with alarms
* Locked notes(Personal notes)
* Image noting

Building
========
To build you will need:

 * A Java compiler compatible with Java 1.8
 * The Android SDK with platform 26 installed

Building from command-line
--------------------------
> Note: at the time of this writing, the current version of Gradle ([4.5.1](https://gradle.org/releases/)) is not compatible with the current version of JDK ([9.0.4](http://www.oracle.com/technetwork/java/javase/downloads/jdk9-downloads-3848520.html)). To have the build succeed, use JDK version [1.8.0_162](http://www.oracle.com/technetwork/java/javase/downloads/jdk8-downloads-2133151.html).
 * `gradle build` to build the APK
 * Optional: `gradle installDebug` to install the APK to a connected device

Building with Android Studio
---------------------
You can also build with Android Studio by importing this project into it.

Building from Eclipse
---------------------
You can also build from Eclipse. Create a new Android Project, choosing "Create
project from exisiting source", then set the compiler compliance level to 1.6
in project settings.

Nightly Builds
---------------------
Automatically created builds are available from [To be inserted]

Documentation
=============
Javadocs can be generated using `gradle javadoc` or `ant doc`

  [1]: https://github.com/ChesterEucrine/SaveSpace-Android/issues
  [2]: https://github.com/ChesterEucrine/SaveSpace-Android/wiki
