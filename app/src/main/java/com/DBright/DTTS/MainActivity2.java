package com.DBright.DTTS;

import android.content.Context;
import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.content.res.Configuration;
import android.os.Bundle;
import android.os.Handler;
import android.speech.tts.TextToSpeech;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.google.android.gms.ads.AdListener;
import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.AdView;
import com.google.android.gms.ads.InterstitialAd;
import com.google.android.gms.ads.MobileAds;
import com.google.android.gms.ads.initialization.InitializationStatus;
import com.google.android.gms.ads.initialization.OnInitializationCompleteListener;

import java.util.Date;
import java.util.Locale;

public class MainActivity2 extends AppCompatActivity {

    private static final String TAG = "TESTING";
    EditText story;
    TextView wcount;
    TextView ld_seconds;
    ImageButton Play;
    public TextToSpeech tts;
    Date d = new Date();
    int f  = 0;
    int play_count = 0;
    final int[] dSeconds = {0};

    String[] play = {"play","pause","play"};

    String Itext = "";
    private void ConvertTextToSpeech(String text, final ImageButton Play) {
        // TODO Auto-generated method stub

        if (text == null || "".equals(text)) {
            text = "Content not available";
        }
        tts.speak(text, TextToSpeech.QUEUE_FLUSH, null);


    }



    public void DTalk(final String text, final ImageButton Play) {
        try {
            tts = new TextToSpeech(MainActivity2.this, new TextToSpeech.OnInitListener() {

                @Override
                public void onInit(int status) {

                    if (status == TextToSpeech.SUCCESS) {
                        int result = tts.setLanguage(Locale.US);
                        if (result == TextToSpeech.LANG_MISSING_DATA ||
                                result == TextToSpeech.LANG_NOT_SUPPORTED) {
                            Log.e("error", "This Language is not supported");

                        } else {
                            ConvertTextToSpeech(text, Play);

                        }
                    } else {
                        Log.e("error", "Initilization Failed!");
                        f = 1;
                    }


                    new Thread(
                            new Runnable() {
                                @Override
                                public void run() {
                                    dSeconds[0] = 0;
                                    ld_seconds = (TextView) findViewById(R.id.loading_seconds);
                                    while(tts.isSpeaking()){
                                        try {
                                            Thread.sleep(1000);
                                        } catch (InterruptedException e) {

                                            e.printStackTrace();
                                        }
                                        Log.e("working", String.valueOf(dSeconds[0]));

                                        Play.setImageResource(R.drawable.pause_button);
                                        ld_seconds.setText(String.valueOf(dSeconds[0]) + "Seconds");

                                        dSeconds[0]++;
                                    }
                                    Play.setImageResource(R.drawable.play_button);
                                    play_count = 0;

                                }
                            }
                    ).start();

                    if(tts.isSpeaking()){

                        DDisplay("Reading !!!!");
                        Log.e("Running ", " Itz working");
                    }
                }
            });

        }catch(Exception e){
            Log.e("Android", String.valueOf(e));
        }finally{

            ConvertTextToSpeech(text, Play);

        };
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

    public void DDisplay(String text) {
        Context context = getApplicationContext();

        int duration = Toast.LENGTH_LONG;

        Toast toast = Toast.makeText(context, text, duration);
        toast.show();
    }
    AdView madView1;
    AdView madView2;
    InterstitialAd iad;
    AdRequest adRequest;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main2);
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

        MobileAds.initialize(this, new OnInitializationCompleteListener() {
            @Override
            public void onInitializationComplete(InitializationStatus initializationStatus) {
            }
        });
        DDisplay(greetin());
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
        try {
            Bundle bundle = getIntent().getExtras();
            Itext = bundle.getString("message");
        }catch(Exception e){
            Log.e("Hmmm","sth went wrong");
        }
    }


    @Override
    protected void onStart() {
        super.onStart();

        //
        ImageButton snap;
        snap = (ImageButton) findViewById(R.id.DCamera);
        snap.setOnClickListener( // this is the action that redirect to where you will snap
                new View.OnClickListener() {

                    @Override
                    public void onClick(View v) {
                        Intent textPicx = new Intent(MainActivity2.this,textCamera.class);
                        startActivity(textPicx);
                    }
                }
        );


        story = (EditText) findViewById(R.id.editTextTextMultiLine);
        if(!(Itext.equals(" "))) {
            story.setText(Itext);
        }


    }
    public void play(View v){


        String[] words = {};
        int word_count = 0;
        final String[] dn = {story.getText().toString()}; // words captured to be calculated in length
        dn[0] = dn[0].replace("\n"," ");// it removes next line from the program
        words = dn[0].split(".", -1);

        if(play[play_count].equals("play")){
            play_count = 1;
            final Handler h1 = new Handler();
            final String[][] dn1 = {{}};
            final int[] dct = {0};
            if(tts != null){

                tts.stop();
                tts.shutdown();
            }
            story = (EditText) findViewById(R.id.editTextTextMultiLine);
            wcount = (TextView) findViewById(R.id.wc);
            Play = (ImageButton) findViewById(R.id.play);
            new Thread(
                    new Runnable() {
                        @Override
                        public void run() {

                            Play.setImageResource(R.drawable.pause_button);
                        }
                    }

            ).start();
            // Here is where all files are being processed from the Text given
            //starting program


            Runnable r1 = new Runnable() {
                @Override
                public void run() {
                    if(dn[0].length() != 0){

                        dn1[0] = dn[0].split(" ",-1);
                        for(int x = 0 ; x < dn1[0].length; x ++){
                            Log.e(TAG, dn1[0][x]);
                            if((dn1[0][x].equals(" ")) || (dn1[0][x].equals(""))){

                            }else{
                                dct[0]++;
                            }
                        }



                        h1.post(new Runnable() {
                            @Override
                            public void run() {

                                if(dct[0] == 1){
                                    wcount.setText("1 word");
                                    dct[0] = 0;
                                }else{
                                    wcount.setText(String.valueOf(dct[0])+" words");
                                    dct[0] = 0;

                                }


                            }
                        });
                    }
                }
            };
            Thread t1 = new Thread(r1);
            t1.start();

            new Thread(
                    new Runnable() {
                        @Override
                        public void run() {
                            DTalk(dn[0], Play);
                        }
                    }
            ).start();

        } else if (play[play_count].equals("pause")) {
            pause_button();
            play_count = 0;
            Play.setImageResource(R.drawable.pause_button);
        }else{
            Log.e("Play","Sth went wrong");
        }

    }
    public void pause_button(){

        if (tts != null) {

            tts.stop();
            tts.shutdown();
        }
    }
    public void stop_button(View w){
        if (tts != null) {

            tts.stop();
            tts.shutdown();
        }
        dSeconds[0] = 0;
    }
    @Override
    protected void onPause(){
        super.onPause();
        if (tts != null) {

            tts.stop();
            tts.shutdown();
        }
    }

}