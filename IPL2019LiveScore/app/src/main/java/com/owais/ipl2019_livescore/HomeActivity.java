package com.owais.ipl2019_livescore;

import android.annotation.SuppressLint;
import android.content.ActivityNotFoundException;
import android.content.Intent;
import android.net.Uri;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.KeyEvent;
import android.view.Menu;
import android.view.MenuItem;
import android.view.MotionEvent;
import android.view.View;
import android.webkit.WebChromeClient;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;

import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.AdView;
import com.google.android.gms.ads.InterstitialAd;
import com.google.android.gms.ads.MobileAds;

import static java.security.AccessController.getContext;

public class HomeActivity extends AppCompatActivity {

    WebView mWebView;
    InterstitialAd mInterstitialAd;
    AdView mAdView;
    String TAG = this.getClass().getSimpleName();

    @SuppressLint("ClickableViewAccessibility")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);
String url = "https://www.google.com/search?ei=L6yNXLG2AoKwkwWgh7vQBA&q=ipl&oq=ipl&gs_l=psy-ab.3..35i39l2j0i131i67l2j0i67j0i131j0i131i67j0i67l3.3728016.3728528..3729027...0.0..0.299.579.2-2......0....1..gws-wiz.......0i71.v9wnQzZgCXw#sie=lg;/g/11f6593n2n;5;/m/03b_lm1;mt;fp;1;;";
        MobileAds.initialize(this, getString(R.string.appid));

        mInterstitialAd = new InterstitialAd(this);
        mInterstitialAd.setAdUnitId(getString(R.string.interstitial));
        mInterstitialAd.loadAd(new AdRequest.Builder().build());

        mAdView = findViewById(R.id.adView);
        AdRequest adRequest = new AdRequest.Builder().build();
        mAdView.loadAd(adRequest);

        mWebView = (WebView) findViewById(R.id.webview);
        WebSettings webSettings = mWebView.getSettings();
        webSettings.setJavaScriptEnabled(true);
        mWebView.getSettings().setDatabaseEnabled(true);

        mWebView.setWebViewClient(new HomeActivity.VideoWebViewClient());
        mWebView.loadUrl("https://www.google.com/search?ei=L6yNXLG2AoKwkwWgh7vQBA&q=ipl&oq=ipl&gs_l=psy-ab.3..35i39l2j0i131i67l2j0i67j0i131j0i131i67j0i67l3.3728016.3728528..3729027...0.0..0.299.579.2-2......0....1..gws-wiz.......0i71.v9wnQzZgCXw#sie=lg;/g/11f6593n2n;5;/m/03b_lm1;mt;fp;1;;");
        mWebView.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View view, MotionEvent motionEvent) {
                WebView.HitTestResult hr = ((WebView) view).getHitTestResult();
                Log.i(TAG, "getExtra = " + hr.getExtra() + "\t\t Type= " + hr.getType());
                AdRequest request = new AdRequest.Builder()
                        .addTestDevice(AdRequest.DEVICE_ID_EMULATOR)
                        .build();
                mInterstitialAd.loadAd(request);
                if(mInterstitialAd.isLoaded())

                {
                    mInterstitialAd.show();
                } else

                {
                    Log.d("TAG", "The interstitial wasn't loaded yet.");
                }

                return false;

            }






        });


        mWebView.setWebChromeClient(new WebChromeClient() {
            @Override
            public void onReceivedTitle(WebView view, String h2) {
                super.onReceivedTitle(view, h2);
                if (!TextUtils.isEmpty(h2)) {
                    HomeActivity.this.setTitle(h2);
                }
                mWebView.getSettings().setAllowContentAccess(onSearchRequested());

            }
        });


    }

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if (event.getAction() == KeyEvent.ACTION_DOWN) {
            switch (keyCode) {
                case KeyEvent.KEYCODE_BACK:
                    if (mWebView.canGoBack()) {
                        mWebView.goBack();
                    } else {
                        finish();
                    }
                    return true;
            }

        }
        return super.onKeyDown(keyCode, event);
    }

    private class VideoWebViewClient extends WebViewClient {
        @Override
        public boolean shouldOverrideUrlLoading(WebView view, String url) {
            view.loadUrl(url);

            AdRequest request = new AdRequest.Builder()
                    .addTestDevice(AdRequest.DEVICE_ID_EMULATOR)
                    .build();
            mInterstitialAd.loadAd(request);
            if(mInterstitialAd.isLoaded())

            {
                mInterstitialAd.show();
            } else

            {
                Log.d("TAG", "The interstitial wasn't loaded yet.");
            }

            return true;
        }

    }
}
