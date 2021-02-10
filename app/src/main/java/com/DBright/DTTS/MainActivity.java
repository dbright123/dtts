package com.DBright.DTTS;

import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.content.res.Configuration;
import android.os.Bundle;
import android.os.Handler;
import android.speech.tts.TextToSpeech;
import android.util.Log;

import androidx.appcompat.app.AppCompatActivity;

import com.google.android.gms.ads.AdListener;
import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.AdView;
import com.google.android.gms.ads.InterstitialAd;
import com.google.android.gms.ads.MobileAds;

import java.util.Date;
import java.util.Locale;

public class MainActivity extends AppCompatActivity {
    private static final String TAG = "Testing " ;
    public TextToSpeech text_to_speech;
    boolean ttsIsInitialized = false;
    Date d = new Date();
    AdView madView1;
    AdView madView2;
    InterstitialAd iad;
    AdRequest adRequest;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        setRequestedOrientation (ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
        Configuration config = getResources().getConfiguration();
        try {
            Class configClass = config.getClass();
            if (configClass.getField("SEM_DESKTOP_MODE_ENABLED").getInt(configClass)
                    == configClass.getField("semDesktopModeEnabled").getInt(config)) {
                // Samsung DeX mode enabled
            }
        } catch (NoSuchFieldException | IllegalAccessException | IllegalArgumentException e) {
            // Device does not support Samsung DeX
        }
        madView1 = findViewById(R.id.adView);
        madView2 = findViewById(R.id.adView2);

        MobileAds.initialize(this,"ca-app-pub-3041136835684961~8023196183");
        adRequest = new AdRequest.Builder().build();
        madView1.loadAd(adRequest);
        madView2.loadAd(adRequest);

        iad = new InterstitialAd(this);
        iad.setAdUnitId("ca-app-pub-3041136835684961/8523025105");
        iad.loadAd(new AdRequest.Builder().build());
        madView1.setAdListener(new AdListener() {
            @Override
            public void onAdFailedToLoad(int errorCode) {
                Log.e(TAG, "This is why: " + errorCode);
            }
        });
        text_to_speech = new TextToSpeech(this, new TextToSpeech.OnInitListener() {
            @Override
            public void onInit(int status) {
                if (status == TextToSpeech.SUCCESS){
                    int result = text_to_speech.setLanguage(Locale.ENGLISH);
                    if (result == TextToSpeech.LANG_MISSING_DATA || result == TextToSpeech.LANG_NOT_SUPPORTED){
                        Log.e("TTS", "Language not supported");
                    } else {
                        ttsIsInitialized = true; // flag tts as initialized
                    }
                } else {
                    Log.e("TTS", "Failed");
                }

            }
        });
    }


    public void DTalk(final String text) {
        if (!ttsIsInitialized) {return;}

        text_to_speech.speak(text, TextToSpeech.QUEUE_FLUSH, null);
    }


    public String greetin() {
        String greetinz = "";
        int DHours = d.getHours();
        if (DHours <= 12) {
            greetinz = "Good Morning";

        } else if (DHours > 12 && DHours <= 16) {
            greetinz = "Good Afternoon";
        } else {
            greetinz = "Good Evening";

        }
        return greetinz;
    }


    @Override
    protected void onStart() {
        super.onStart();
        //t4.interrupt();
        final Handler idl = new Handler();
        Runnable r1 = new Runnable() {
            @Override
            public void run() {
                try {
                    Thread.sleep(4000);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                idl.post(new Runnable() {
                    @Override
                    public void run() {
                        Intent dl = new Intent(MainActivity.this, MainActivity2.class);
                        startActivity(dl);
                    }
                });



            }
        };
        Thread t1 = new Thread(r1);
        t1.start();
        DTalk(greetin());
    }


}