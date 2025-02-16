This is a Kotlin Multiplatform project targeting Android, iOS.

* `/nativeApp` is for code that will be shared across your Compose Multiplatform applications.
  It contains several subfolders:
  - `commonMain` is for code that’s common for all targets.
  - Other folders are for Kotlin code that will be compiled for only the platform indicated in the folder name.
    For example, if you want to use Apple’s CoreCrypto for the iOS part of your Kotlin app,
    `iosMain` would be the right folder for such calls.

* `/iosApp` contains iOS applications. Even if you’re sharing your UI with Compose Multiplatform, 
  you need this entry point for your iOS app. This is also where you should add SwiftUI code for your project.


Learn more about [Kotlin Multiplatform](https://www.jetbrains.com/help/kotlin-multiplatform-dev/get-started.html)…

**Building project**

before opening project in XCode first build the project on android studio (you might need to run `chmod +x ./build_nativeApp.sh`) to give the script execute privileges

**framework not found in Xcode**

under add framework, remove nativeApp.framework (if exists)
click on + button and select add other ... in dropdown
select add files ...
select folder nativeApp / build /fat-framework /debug /nativeApp.framework

try run the app on xcode