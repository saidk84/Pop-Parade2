# Pop Parade for Android — V0.2

This Android app packages the playable Pop Parade game as a self-contained, offline Android WebView app.

## Modes

- **Play Parade** — the original 45 gentle kids' levels across 5 worlds.
- **Free Pop** — relaxed balloon popping.
- **Challenging Mode (13+)** — 100 progressively faster timed levels in 10 arenas, neon styling, combos, accuracy and local score saving.

All progress is saved in the WebView's local storage on the current Android device. The native Android shell adds on-device text-to-speech where available, Android Back navigation, and light/dark system bar styling.

## Build

Requirements: Java 17, Android SDK 35 and Gradle 8.13 (or Android Studio with supported Android Gradle Plugin 8.13.2).

```sh
gradle -p android assembleDebug
```

The output is `android/app/build/outputs/apk/debug/app-debug.apk`.

For GitHub builds, the repository's Actions workflow saves `apk/PopParade-v0.2-debug.apk` and an Actions artifact. This V0.2 APK is **debug-signed for personal testing and sideloading**, not a Google Play release. Release signing should use a securely stored key, not a keystore committed to a public repository. Future independently built debug APKs may use different keys and require uninstalling the old app before installing the next one (which can reset locally saved game progress).

### App configuration

- Package ID: `com.popparade.app`
- Version name: `0.2`, version code: `2`
- Minimum Android: 7.0 (API 24)
- Target Android: API 35
- No account, advertising SDK, analytics SDK, external CDN, or requested Android internet permission

The game code is embedded as `android/app/src/main/assets/index.html`. The publicly hosted web game remains at the repository root as `index.html`. The native source is under `android/`.

### Installing the test APK

On the Android phone, download the `.apk`, open it, and approve the device's **Install unknown apps** prompt for the trusted app used to download it. Install and open **Pop Parade**. Only install an APK from the trusted repository linked by the project owner.