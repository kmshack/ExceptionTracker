# Android Exception Tracker Library

Android Exception Tracker is a simple library that demonstrates an exception occurs in the log in screen notifications or app. However, the time when with a tool to find the log speed can view the log as well as in environments without the tool transmits the log from a variety of sources.

  - Very Easy! Simple!
  - No conflicts with other libraries.
  
### Installation

Allows the initialization as shown below in Application onCreate () app.

```java
Tracker.setup(getApplicationContext());
```


Add the following to the activity AndroidManifest to use the log screen.

```xml
<activity android:name="com.kmshack.library.exceptiontracker.LogActivity"/>
```

### Property

Log may be used as a notification alert, screen both. Tracker can be set when initialized. The notification alerts after pressing the Knotty claimed to be the simplest information is allow you to launch your screen.

```java
Tracker.setup(getApplicationContext())
.setAlarmType(Tracker.ALARM_TYPE_NOTIFICATION | Tracker.ALARM_TYPE_ACTIVITY);
```

### Gradle

```java
compile ‘com.kmshack.library:android-exception-tracker:1.0.2’  
```

[ ![Download](https://api.bintray.com/packages/kmshack/maven/android-exception-tracker/images/download.svg) ](https://bintray.com/kmshack/maven/android-exception-tracker/_latestVersion)

### Version
1.0.2


License
----
Apache License, Version 2.0

