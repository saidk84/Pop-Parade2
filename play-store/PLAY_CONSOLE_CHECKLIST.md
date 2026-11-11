# Pop Parade V0.3 — Google Play checklist (November 11, 2026)

## Prepared in the project
- [x] Android App Bundle (.aab), API level 36 target/compile, versionCode 3, R8 minification and resource shrinking.
- [x] Local gameplay/progress without ads, analytics, login, network/location/microphone permissions.
- [x] Privacy notice accessible in children's screens and Challenging Mode, plus hosted policy page.
- [x] Android 16 system bars and system Back handling.
- [x] No native .so libraries, so no native 16KB page-size alignment to adjust in the present build.

## Play Console and developer review still required
- [ ] Sign upload bundle with an RSA 2048+ key kept off GitHub; configure Play App Signing, preserve the private upload keystore, verify certificate.
- [ ] Ensure applicationId `com.popparade.app` is correct and versionCode 3 exceeds any already-published version.
- [ ] Publish and verify the privacy URL `https://saidk84.github.io/Pop-Parade2/privacy.html`, and confirm the operator and contact mechanism are accurate.
- [ ] Complete Data safety disclosures, content rating, target audience and app-content declarations accurately for the built binary.
- [ ] Kids' Free Pop and 45 levels mean the app **includes children**, alongside a 13+ challenge mode. Follow Google Play Families rules. If ads are added in future, use age-appropriate certified SDKs/neutral age screen as applicable.
- [ ] Prepare genuine store listing icon (512x512 PNG), feature graphic (1024x500), at least two actual screenshots and accurate descriptions.
- [ ] Test the optimized signed build on devices for game progress, all modes, spoken hints, Android Back, rotation/tablets, WebView, accessibility, and local storage.
- [ ] Test in Play Console internal testing, address pre-launch report findings and any policy warnings. Some personal developer accounts require >=12 testers opted in to closed testing continuously for >=14 days before production access.
- [ ] Recheck policy at submission time. A successful build does not guarantee acceptance.

Official guidance:
https://developer.android.com/google/play/requirements/target-sdk
https://developer.android.com/topic/performance/app-optimization/enable-app-optimization
https://support.google.com/googleplay/android-developer/answer/9842756
https://support.google.com/googleplay/android-developer/answer/9893335
https://support.google.com/googleplay/android-developer/answer/9859455
https://support.google.com/googleplay/android-developer/answer/9866151
https://support.google.com/googleplay/android-developer/answer/14151465
