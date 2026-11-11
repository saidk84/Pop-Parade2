# The offline game calls these Java methods from JavaScript.
# Keep the exposed bridge methods without disabling R8 for the app.
-keepclassmembers class com.popparade.app.MainActivity$DeviceBridge {
    @android.webkit.JavascriptInterface <methods>;
}
