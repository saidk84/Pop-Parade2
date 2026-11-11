# V0.3 Android upload-key recovery — keep your private key safe

The release AAB is signed with a generated 4096-bit RSA **Play upload key**. The public repository contains the release AAB and an **encrypted** recovery file `aab/PopParade-v0.3-upload-key-recovery.p7m`, but **not the private upload key**. The recovery file includes the upload JKS, alias and passwords, encrypted to the public certificate `play-store/upload-recovery-public.crt`.

**IMPORTANT:** Download and back up the private *recovery key* supplied separately in the ChatGPT conversation: `PopParade_V0.3_Recovery_PrivateKey.pem`. It must never be committed to GitHub, shared publicly or lost. Anyone with that private recovery key plus the public encrypted bundle can extract the Play upload key.

To recover credentials on your own trusted computer:
```bash
openssl cms -decrypt -inform DER \
  -in aab/PopParade-v0.3-upload-key-recovery.p7m \
  -recip play-store/upload-recovery-public.crt \
  -inkey /path/to/PopParade_V0.3_Recovery_PrivateKey.pem \
  -out upload-key-recovery.json
python3 - <<'PY'
import base64, json
data = json.load(open('upload-key-recovery.json'))
open('popparade-upload-v03.jks','wb').write(base64.b64decode(data['keystoreBase64']))
print('Alias:', data['alias'])
print('Store/key password:', data['keystorePassword'])
PY
```
Delete the unencrypted JSON once you have securely stored the keystore and credentials. For future Android updates, **reuse the same upload key** and increment versionCode. Configure GitHub Actions Secrets for signing later, never store passwords or JKS in a public repository.

Even a signed AAB requires Play Console setup, content ratings, privacy policy, target audience/Families declarations, screenshots, data safety and release testing. A valid signing signature does not guarantee approval.
