# kidsplace-api-samples
Kids Place API Usage Example

This project includes sample code to demonstrate how to use Kids Place API. This can assist android developers who develop Apps for Children with following features:

Locking of home button
Android Parental Controls Settings
Central authentication mechanism (to make sure only parents can access the area of app like app settings; in-app purchases and upgrades).
Add/Remove approved apps from Kids Place.
Kids Place - Parental Control, by kiddoware, is a free Android app that provides parental controls with child lock to childproof android devices. Kiddoware provides a simple to use integration code that android developers can use in their project.

Kids Place SDK Can be downloaded from here: Download Kids Place SDK Jar File

SDK Javadocs can be browsed from here: Browse Kids Place SDK Javadoc

For quick and basic implementation refer to source code of simple project and for more advanced integration refer to advanced project.

Please click here to contact us if you have any questions. 
Some of the apps on Google Play that uses Kids Place SDK:

Kids Video Player
Letters With Ally
Basic Setup

Step by step installation of the Kids Place library in an existing application project, with the simplest settings:

Download the Kids Place library http://www.kiddoware.com/apk/kidsplace_sdk.jar
Open your Eclipse project
Create a libs folder in your project
Add the kidsplace_sdk_1.0.jar from your download directory in the libs folder
Add following code in your activity from where you want to invoke Kids Place API
Import Kids Place library
import com.kiddoware.kidsplace.sdk.KPUtility;
Add following code in onCreate Method of your activity
KPUtility.handleKPIntegration(this, KPUtility.GOOGLE_MARKET);
For sample source code for simple integration please refer simple project code.
For advanced integration please see Advanced Usage Guide.
