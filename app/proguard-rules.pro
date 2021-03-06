# Add project specific ProGuard rules here.
# By default, the flags in this file are appended to flags specified
# in /Users/keithsmyth/Workspace/AndroidDev/sdk/tools/proguard/proguard-android.txt
# You can edit the include path and order by changing the proguardFiles
# directive in build.gradle.
#
# For more details, see
#   http://developer.android.com/guide/developing/tools/proguard.html

# Add any project specific keep options here:

# If your project uses WebView with JS, uncomment the following
# and specify the fully qualified class name to the JavaScript interface
# class:
#-keepclassmembers class fqcn.of.javascript.interface.for.webview {
#   public *;
#}

# Retrofit, OkHttp, Gson
-keepattributes Signature
-keepattributes *Annotation*
-keep class com.squareup.okhttp.** { *; }
-keep interface com.squareup.okhttp.** { *; }
-dontwarn com.squareup.okhttp.**
-dontwarn rx.**
-dontwarn retrofit.**
-keep class retrofit.** { *; }
-keepclasseswithmembers class * {
    @retrofit.http.* <methods>;
}
-dontwarn okio.**

-keep class sun.misc.Unsafe { *; }

#models / api interface
-keep class com.keithsmyth.pokemans.model.** { *; }
-keep interface com.keithsmyth.pokemans.api.** { *; }