# kidsplace-api-samples
Kids Place SDK allows your Android application to integrate with Kids Place app to provide unified way of Android Parental controls; Locking of Home Button and Security features for kids apps on users device.

Implementing Kids Place SDK will allow developers to focus on implementing the core functionality of app and let Kids Place do the grunt work of making the app Kids friendly and childproofing the app.

Kids Place API Usage Example

This project includes sample code to demonstrate how to use Kids Place API. This can assist android developers who develop Apps for Children with following features:

   1. Locking of home button
   2. Android Parental Controls Settings
   3. Central authentication mechanism (to make sure only parents can access the area of app like app settings; in-app purchases and upgrades).
   4. Add/Remove approved apps from Kids Place.
Kids Place - Parental Control app (https://play.google.com/store/apps/details?id=com.kiddoware.kidsplace), by kiddoware (http://www.kiddoware.com/), is a free Android app that provides parental controls with child lock to childproof android devices. Kiddoware provides a simple to use integration code that android developers can use in their project.

Kids Place SDK Can be downloaded from here:  http://www.kiddoware.com/apk/kidsplace_sdk.jar

SDK Javadocs can be browsed from here: http://kiddoware.com/doc/com/kiddoware/kidsplace/sdk/KPUtility.html

For quick and basic implementation refer to source code of simple project: https://github.com/kiddoware/kidsplace-api-samples/tree/master/samples/simple

and for more advanced integration refer to advanced project: https://github.com/kiddoware/kidsplace-api-samples/tree/master/samples/advanced

Please open defects/enhancements requests in the issue tracker: https://github.com/kiddoware/kidsplace-api-samples/issues

Please email support@kiddoware.com if you have any questions. 

Some of the apps on Google Play that uses Kids Place SDK:

   1. Kids Video Player: https://play.google.com/store/apps/details?id=com.kiddoware.kidsvideoplayer
   2. Letters With Ally: https://play.google.com/store/apps/details?id=com.kiddoware.letters

Basic Setup

Step by step installation of the Kids Place library in an existing application project, with the simplest settings:

1. Download the Kids Place library http://www.kiddoware.com/apk/kidsplace_sdk.jar
2. Open your Eclipse project
3. Create a libs folder in your project
4. Add the kidsplace_sdk_1.0.jar from your download directory in the libs folder
5. Add following code in your activity from where you want to invoke Kids Place API
  1. Import Kids Place library
  ```
     import com.kiddoware.kidsplace.sdk.KPUtility;
```
  2. Add following code in onCreate Method of your activity
  ```
   KPUtility.handleKPIntegration(this, KPUtility.GOOGLE_MARKET);
   ```
For sample source code for simple integration please refer simple project code:  https://github.com/kiddoware/kidsplace-api-samples/tree/master/samples/simple

For advanced integration please see Advanced Usage Guide: https://github.com/kiddoware/kidsplace-api-samples/wiki/Advanced-Usage
