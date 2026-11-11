# Pop Parade V0.3 — Android App Bundle (AAB) with R8

Preserves 45 gentle kids' levels, Free Pop, and 100 timed teen/adult challenge levels.

## Android and Google Play technical baseline
- Application ID: `com.popparade.app`; versionName `0.3` / versionCode `3`; minSdk 24.
- Compile/target Android 16 API level 36, required for current Play phone submissions.
- R8 minify/obfuscation, shrinking and resource optimization enabled for release.
- HTML/CSS/JS bundled offline; R8 optimizes native Java bytecode/resources, **not the JavaScript**.
- No advertising, analytics, accounts, or Android INTERNET/location/microphone permissions.
- In-game policy modal and public `privacy.html` for the Play Console privacy-policy URL.
- Edge-to-edge/system Back handling for Android 16. No packaged native .so libraries.

## Build the unsigned bundle
JDK 17, Gradle 8.13, Android SDK 36 and build tools 36.0.0:
```bash
gradle -p android --no-daemon :app:bundleRelease
```
Output `android/app/build/outputs/bundle/release/app-release.aab`. GitHub Actions also saves the unsigned AAB and R8 mapping output.

## Play upload signing
**An unsigned AAB is not Play-uploadable.** The file under `aab/PopParade-v0.3-play-upload.aab` should be signed with a private, stable RSA-2048-or-stronger upload key. The JKS and passwords must never be put in this public repository. Preserve/back up the upload keystore for future releases.

```bash
jarsigner -keystore /private/path/upload-key.jks -sigalg SHA256withRSA -digestalg SHA-256 app-release.aab YOUR_ALIAS
jarsigner -verify -verbose -certs app-release.aab
```

A new Play-signed installation may not update the V0.2 debug-signed APK directly; removing the debug app can erase locally saved progress.

## Review required for Google Play
See `play-store/PLAY_CONSOLE_CHECKLIST.md`. Signed bundle compilation is only one step: complete Play App Signing, mixed child/teen Families target-audience declarations, privacy-policy hosting and contact, Data safety, content rating, accurate store assets, and device/pre-launch tests. Google Play approval is not guaranteed.
