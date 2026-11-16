# Pop Parade V0.4

- Challenging Mode (7+) has 300 levels across 30 arenas, balloon strings, and distinct correct, incorrect, and combo sounds. Cleared levels earn medals and each completed arena earns a trophy with its own celebration. Existing Challenge clears and best scores carry forward. Earlier clears have a bronze medal until improved by replay.
- Kids can place and drag their unlocked stickers from Collection → Sticker playground. Layouts are saved on the device. The first world also unlocks a sun and sunflower.
- Seniors Mode (13+) has 80 levels across eight arenas. Monsters, aliens, ghosts, and zombies move faster and expire sooner. The target changes every five correct pops and mistakes cost more time. Progress is saved separately.
- Phone layouts leave extra space around the playfield and account for browser safe areas. The Android wrapper continues to apply system bar and display cutout insets.

## Android

`com.popparade.app` · versionName `0.4` · versionCode `4` · minimum API 24 · target API 36.

With JDK 17, Gradle 8.13 and Android SDK 36, run:

```sh
gradle -p android --no-daemon :app:assembleDebug :app:bundleRelease
```

The V0.4 Android workflow verifies the bundled game assets, debug APK version and signature, and the release R8 mapping. It archives the debug APK and unsigned AAB on the release branch. For Google Play, sign the optimized AAB with the existing registered upload key and verify the resulting signature before uploading. Keep the keystore and its credentials private. The V0.3 signing workflow is historical.
