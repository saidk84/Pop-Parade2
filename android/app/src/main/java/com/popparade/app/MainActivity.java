package com.popparade.app;

import android.app.Activity;
import android.app.AlertDialog;
import android.graphics.Color;
import android.net.Uri;
import android.os.Bundle;
import android.speech.tts.TextToSpeech;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.JavascriptInterface;
import android.webkit.WebResourceRequest;
import android.webkit.WebResourceResponse;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.FrameLayout;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.Collections;
import java.util.Locale;

/** Offline Android shell for the original Pop Parade HTML game. */
public final class MainActivity extends Activity {
    private static final String LOCAL_HOST = "appassets.androidplatform.net";
    private static final String GAME_URL = "https://" + LOCAL_HOST + "/assets/index.html";
    private WebView webView;
    private TextToSpeech tts;
    private boolean speechReady;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        updateTheme(false);
        FrameLayout layout = new FrameLayout(this);
        layout.setFitsSystemWindows(true);
        webView = new WebView(this);
        webView.setOverScrollMode(View.OVER_SCROLL_NEVER);
        webView.setBackgroundColor(Color.rgb(255, 245, 223));
        layout.addView(webView, new FrameLayout.LayoutParams(
                ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT));
        setContentView(layout);

        WebSettings settings = webView.getSettings();
        settings.setJavaScriptEnabled(true);
        settings.setDomStorageEnabled(true);
        settings.setAllowFileAccess(false);
        settings.setAllowContentAccess(false);
        settings.setJavaScriptCanOpenWindowsAutomatically(false);
        settings.setSupportMultipleWindows(false);
        settings.setMediaPlaybackRequiresUserGesture(false);
        settings.setMixedContentMode(WebSettings.MIXED_CONTENT_NEVER_ALLOW);
        settings.setBlockNetworkLoads(true);

        webView.addJavascriptInterface(new DeviceBridge(), "PopParadeAndroid");
        webView.setWebViewClient(new WebViewClient() {
            @Override
            public WebResourceResponse shouldInterceptRequest(WebView view, WebResourceRequest request) {
                Uri uri = request.getUrl();
                if ("https".equalsIgnoreCase(uri.getScheme())
                        && LOCAL_HOST.equalsIgnoreCase(uri.getHost())
                        && "/assets/index.html".equals(uri.getPath())) {
                    try {
                        InputStream stream = getAssets().open("index.html");
                        return new WebResourceResponse("text/html", "UTF-8", stream);
                    } catch (IOException ignored) {
                        return reject(404);
                    }
                }
                return reject(403);
            }

            @Override
            public boolean shouldOverrideUrlLoading(WebView view, WebResourceRequest request) {
                // No external navigation or internet traffic from the children's game.
                return !GAME_URL.equals(request.getUrl().toString());
            }
        });

        tts = new TextToSpeech(this, status -> {
            if (status == TextToSpeech.SUCCESS && tts != null) {
                int result = tts.setLanguage(Locale.US);
                speechReady = result != TextToSpeech.LANG_MISSING_DATA
                        && result != TextToSpeech.LANG_NOT_SUPPORTED;
            }
        });
        webView.loadUrl(GAME_URL);
    }

    private static WebResourceResponse reject(int code) {
        return new WebResourceResponse("text/plain", "UTF-8", code,
                code == 404 ? "Not Found" : "Forbidden", Collections.emptyMap(),
                new ByteArrayInputStream(new byte[0]));
    }

    private void updateTheme(boolean challenging) {
        final int color = Color.parseColor(challenging ? "#111426" : "#fff5df");
        getWindow().setStatusBarColor(color);
        getWindow().setNavigationBarColor(color);
        int flags = challenging ? 0 : View.SYSTEM_UI_FLAG_LIGHT_STATUS_BAR
                | View.SYSTEM_UI_FLAG_LIGHT_NAVIGATION_BAR;
        getWindow().getDecorView().setSystemUiVisibility(flags);
    }

    public final class DeviceBridge {
        @JavascriptInterface
        public void speak(String phrase) {
            if (phrase == null || phrase.length() > 200) return;
            runOnUiThread(() -> {
                if (speechReady && tts != null) {
                    tts.speak(phrase, TextToSpeech.QUEUE_FLUSH, null, "pop-parade-spoken-hint");
                }
            });
        }

        @JavascriptInterface
        public void setChallengeTheme(boolean challenging) {
            runOnUiThread(() -> updateTheme(challenging));
        }
    }

    @Override
    public void onBackPressed() {
        // The HTML app uses screen transitions rather than browser history.
        final String backScript = "(function(){"
                + "if(document.body.classList.contains('challenge-theme')){"
                + "let x=!document.getElementById('chPlay').classList.contains('ch-hide')?'chExit':"
                + "!document.getElementById('chLevels').classList.contains('ch-hide')?'chBackWorlds':"
                + "!document.getElementById('chResult').classList.contains('ch-hide')?'chResultsMap':'chHome';"
                + "document.getElementById(x).click();return true;}"
                + "let page=document.querySelector('#app>.screen.active');"
                + "if(!page||page.id==='home')return false;"
                + "let buttons={map:'mapHome',levels:'backMap',choose:'chooseBack',"
                + "game:'quitGame',result:'resultMap',collection:'collectionBack',settings:'settingsBack'};"
                + "let button=document.getElementById(buttons[page.id]);"
                + "if(button){button.click();return true;}return false;})()";
        webView.evaluateJavascript(backScript, answer -> {
            if (!"true".equals(answer) && !isFinishing()) {
                new AlertDialog.Builder(this)
                        .setTitle("Leave Pop Parade?")
                        .setMessage("Your completed levels are saved on this device.")
                        .setPositiveButton("Exit", (dialog, which) -> finish())
                        .setNegativeButton("Keep playing", null)
                        .show();
            }
        });
    }

    @Override
    protected void onPause() {
        super.onPause();
        if (webView != null) webView.onPause();
        if (tts != null) tts.stop();
    }

    @Override
    protected void onResume() {
        super.onResume();
        if (webView != null) webView.onResume();
    }

    @Override
    protected void onDestroy() {
        if (webView != null) {
            webView.removeJavascriptInterface("PopParadeAndroid");
            webView.destroy();
            webView = null;
        }
        if (tts != null) {
            tts.stop();
            tts.shutdown();
            tts = null;
        }
        super.onDestroy();
    }
}
