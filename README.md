# WheelNavCode

Code of our CS102 project, WheelNav.

Our project is an android mobile app.

We used Android Studio, Firebase Database, Google Maps API and Graph Algorithms (Djikstra's Algorithm).

The app can be executed by using Android Studio's emulator or by connecting a physical device to a computer and running the app on Android Studio.

Dependencies: 

To execute this code, you have to define "MAPS_API_KEY=" in your local.properties file of your gradle folder. You have to provide and assign a google maps android studio key which can be taken by following the steps from this site: https://developers.google.com/maps/documentation/android-sdk/start?hl=tr as we cannot publicly share our API key.

Also make sure that in your AndroidManifest.xml file there are components like this:

<meta-data
android:name="com.google.android.maps.v2.API_KEY"
 android:value= "${MAPS_API_KEY}"></meta-data>
 
and other permissions of Google Maps Android Studio Sdk. If the repository is cloned correctly, they should be already visible in the project. 

To clone a repository to Android Studio, you can take a look at this site: https://www.geeksforgeeks.org/how-to-clone-android-project-from-github-in-android-studio/.

For further questions please contact us from: ilsu.karadag@ug.bilkent.edu.tr
